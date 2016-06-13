package net.sourcemod.sourcepawn.action;

import com.google.common.base.Preconditions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

import net.sourcemod.sourcepawn.SpSettings;
import net.sourcemod.sourcepawn.SpSupport;
import net.sourcemod.sourcepawn.build.BuildConfiguration;
import net.sourcemod.sourcepawn.build.BuildUtils;
import net.sourcemod.sourcepawn.build.ConsoleBuilder;
import net.sourcemod.sourcepawn.build.ImmutableBuildConfiguration;
import net.sourcemod.sourcepawn.sdk.SpSdkUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Paths;

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

  private void compile(@NotNull Project project,
                       @NotNull VirtualFile file,
                       @Nullable BuildConfiguration buildConfiguration) {
    Preconditions.checkArgument(project != null, "project cannot be null");
    Preconditions.checkArgument(file != null, "file cannot be null");
    if (!SpSupport.isSpFile(file)) {
      Messages.showErrorDialog(project,
          "Cannot compile " + file.getName() + " because it is not a SourcePawn file.",
          "Cannot Compile");
      return;
    }

    if (buildConfiguration == null) {
      Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
      buildConfiguration = BuildConfiguration.create()
          .setProcess(SpSdkUtils.getCompilerPath(sdk))
          .setWorkingDirectory(Paths.get(file.getParent().getPath()))
          .appendTarget(Paths.get(file.getPath()));
    }

    ImmutableBuildConfiguration savedBuildConfiguration = buildConfiguration.commit();
    ConsoleBuilder builder = new ConsoleBuilder(file.getNameWithoutExtension(), project)
        .setBuildConfiguration(savedBuildConfiguration)
        .setRerunAction(() -> compile(project, file, savedBuildConfiguration));
    if (SpSettings.getInstance().saveDocuments) {
      BuildUtils.saveDocuments();
    }

    builder.build();
  }

}
