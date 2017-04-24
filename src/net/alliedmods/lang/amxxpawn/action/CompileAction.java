package net.alliedmods.lang.amxxpawn.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApSupport;
import net.alliedmods.lang.amxxpawn.build.BuildConfiguration;
import net.alliedmods.lang.amxxpawn.build.BuildUtils;
import net.alliedmods.lang.amxxpawn.build.ConsoleBuilder;
import net.alliedmods.lang.amxxpawn.build.ImmutableBuildConfiguration;
import net.alliedmods.lang.amxxpawn.sdk.ApSdkType;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CompileAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(LangDataKeys.VIRTUAL_FILE);
    compile(project, file);
  }

  private void compile(@NotNull Project project, @NotNull VirtualFile file) {
    compile(project, file, null);
  }

  private void compile(@NotNull Project project, @Nonnull VirtualFile file, @Nullable BuildConfiguration config) {
    if (!ApSupport.isApFile(file)) {
      Messages.showErrorDialog(project, ApBundle.message("amxx.error.compiler.filetype.msg", file),
          ApBundle.message("amxx.error.compiler.filetype.title"));
      return;
    }

    if (config == null) {
      Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
      if (sdk == null) {
        Messages.showErrorDialog(project,
            ApBundle.message("amxx.error.compiler.sdk.missing.msg"),
            ApBundle.message("amxx.error.compiler.sdk.missing.title"));
        return;
      } else if (!(sdk.getSdkType() instanceof ApSdkType)) {
        Messages.showErrorDialog(project,
            ApBundle.message("amxx.error.compiler.sdk.invalid.msg", sdk),
            ApBundle.message("amxx.error.compiler.sdk.invalid.title"));
        return;
      }

      String process = ApSdkType.getCompilerPath(sdk);
      if (process == null || process.isEmpty()) {
        Messages.showErrorDialog(project,
            ApBundle.message("amxx.error.compiler.missing.msg", process),
            ApBundle.message("amxx.error.compiler.missing.title"));
        return;
      }

      config = BuildConfiguration.create()
          .setProcess(process)
          .setWorkingDirectory(Paths.get(file.getParent().getPath()))
          .appendTarget(Paths.get(file.getPath()));
          // My projects won't compile without:
          //.appendArg("\\")
          //.appendArg(";");
    }

    ImmutableBuildConfiguration savedBuildConfiguration = config.commit();
    ConsoleBuilder builder = new ConsoleBuilder(file.getNameWithoutExtension(), project)
        .setBuildConfiguration(savedBuildConfiguration)
        .setRerunAction(() -> compile(project, file, savedBuildConfiguration));
    // TODO: Add support for settings
    //if (ApSettings.getInstance().saveDocuments) {
      BuildUtils.saveDocuments();
    //}

    builder.build();
  }

}
