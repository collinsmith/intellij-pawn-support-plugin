package net.sourcemod.sourcepawn.action;

import com.intellij.execution.filters.Filter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;

import net.sourcemod.sourcepawn.build.old.ConsoleBuilder;
import net.sourcemod.sourcepawn.build.old.BuildState;
import net.sourcemod.sourcepawn.build.old.BuildUtils;

import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Deprecated
public class SpCompileAction extends AnAction {

  @Override
  public void actionPerformed(AnActionEvent e) {
    Project project = e.getProject();
    VirtualFile file = e.getData(LangDataKeys.VIRTUAL_FILE);
    compile(project, file);
  }

  private void compile(Project project, VirtualFile file) {
    compile(project, file, null);
  }

  private void compile(Project project, VirtualFile file, CompileOptions options) {
    Sdk sdk = ProjectRootManager.getInstance(project).getProjectSdk();
    CompileHandler handler = new CompileHandler();

    options = new CompileOptions() {
      public String getCompilerOptions() {
        return "";
      }

      public String getProjectCompilerOptions() {
        return "";
      }

      public boolean doRun() {
        return false;
      }

      public String getOutputFileName() {
        return null;
      }

      public Project getProject() {
        return project;
      }

      public boolean isSpFile() {
        return file.getExtension().equals("sp");
      }

      @Override
      public Sdk getSdk() {
        return sdk;
      }
    };

    handler = new CompileHandler();
    List<String> runCommand = handler.buildCommand(
        file,
        options
    );

    if (runCommand == null) throw new RuntimeException("Cannot invoke compilation");

    final Map<String, String> commandLineProperties = handler.getCommandLineProperties();

    final String baseForExecutableFile = file.getParent().getPath() + File.separatorChar;
    final String fileToExecute = baseForExecutableFile + handler.getOutputFileName(file, options);
    new File(fileToExecute).delete();

    final CompileOptions preservedOptions = options;
    final ConsoleBuilder consoleBuilderRef[] = new ConsoleBuilder[1];
    Runnable action = new Runnable() {
      public void run() {
        if (preservedOptions.doRun() && new File(fileToExecute).exists()) {
          new ConsoleBuilder(
              "Run " + file.getName(),
              new BuildState(Arrays.asList(fileToExecute), new File(file.getParent().getPath()),
                  commandLineProperties),
              project,
              null,
              new Runnable() {
                public void run() {
                  compile(project, file, preservedOptions);
                }
              },
              new Runnable() {
                public void run() {
                  consoleBuilderRef[0].start();
                }
              },
              null
          ).start();
        }
      }
    };

    final ConsoleBuilder consoleBuilder = new ConsoleBuilder(
        "Compile File " + file.getName(),
        new BuildState(runCommand, new File(file.getParent().getPath()), commandLineProperties),
        project,
        handler.getCompileLogFilter(file, options),
        new Runnable() {
          public void run() {
            compile(project, file, preservedOptions);
          }
        },
        null,
        action
    );
    consoleBuilderRef[0] = consoleBuilder;
    consoleBuilder.start();
  }

  interface CompileOptions {
    String getCompilerOptions();
    String getProjectCompilerOptions();
    boolean doRun();
    String getOutputFileName();
    Project getProject();
    Sdk getSdk();
    boolean isSpFile();
  }

  public static class CompileHandler {
    private static final String MARKER = "$filename";

    @Nullable
    List<String> buildCommand(VirtualFile file, CompileOptions options) {
      List<String> command = new ArrayList<>(4);
      if (!options.doRun()) {

      }

      command.add(options.getSdk().getHomeDirectory().findChild("spcomp.exe").getPath());
      command.add(file.getPath());
      command = defaultAppendOptions(options, command, file);
      return command;
    }

    @Nullable
    Map<String, String> getCommandLineProperties() {
      return Collections.EMPTY_MAP;
    }

    @Nullable
    Filter getCompileLogFilter(VirtualFile file, CompileOptions options) {
      return null;
    }

    String buildProjectCompileOptions(Project project) {
      return null;
    }

    String getOutputFileName(VirtualFile file, CompileOptions compileOptions) {
      final String fileName = compileOptions.getOutputFileName();
      return fileName != null ? fileName : file.getNameWithoutExtension() + ".amx";
    }

    protected List<String> defaultAppendOptions(CompileOptions options,
                                                List<String> command,
                                                VirtualFile file) {
      command = BuildUtils.appendAllOptions(command, options.getProjectCompilerOptions());
      command = BuildUtils.appendAllOptions(command,
          options.getCompilerOptions().replace(MARKER, getEscapedPathToFile(file.getPath())));
      return command;
    }
  }

  public static String getEscapedPathToFile(String path) {
    return BuildUtils.quote(path, SystemInfo.isWindows);
  }

}
