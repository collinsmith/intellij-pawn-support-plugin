package net.sourcemod.sourcepawn.build.old;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;

@Deprecated
public class BuildHandler {

  private final Project project;
  private final VirtualFile file;

  private ConsoleBuilder consoleBuilder;

  public BuildHandler(Project project, VirtualFile file) {
    this.project = project;
    this.file = file;
  }

  public Project getProject() {
    return project;
  }

  public VirtualFile getFile() {
    return file;
  }

  public List<String> getCommandLine(@NotNull SpBuildTarget buildTarget) {
    List<String> result = new ArrayList<>();
    return result;
  }

  private void addLineToOutput(String line, boolean error) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        consoleBuilder.getProcessHandler().notifyTextAvailable(
            line + System.getProperty("line.separator"),
            error ? ProcessOutputTypes.STDERR : ProcessOutputTypes.STDOUT);
      }
    });
  }

  @Nullable
  public BuildState createBuildState(@NotNull SpBuildTarget target) {
    List<String> runCommand = getCommandLine(target);
    if (runCommand == null) {
      return null;
    }

    if (target.getAdditionalCommandLineParameters() != null) {
      runCommand = BuildUtils
          .appendAllOptions(runCommand, target.getAdditionalCommandLineParameters());
    }

    return new BuildState(runCommand,
        new File(file.getParent().getPath()),
        getEnvironmentVariables());
  }

  @Nullable
  protected Map<String, String> getEnvironmentVariables() {
    return null;
  }

  public void doBuild(@NotNull SpBuildTarget target, Runnable rerunCallback) {
    BuildState buildState = createBuildState(target);
    if (buildState == null) return;
    consoleBuilder = new ConsoleBuilder(
        getBuildTitle(target),
        buildState,
        project,
        getOutputFormatFilter(),
        rerunCallback,
        null,
        null
    ) {

      public void afterProcessStarted() {
        BuildHandler.this.afterProcessStarted();
      }

      public void afterProcessFinished() {
        BuildHandler.this.afterProcessFinished();
      }
    };

    consoleBuilder.start();
  }

  public void afterProcessStarted() {
  }

  public void afterProcessFinished() {
  }

  @NotNull
  @Nls
  public String getBuildTitle(SpBuildTarget target) {
    return "SourcePawn Compiler";
  }

  @Nullable
  public Filter getOutputFormatFilter() {
    return null;
  }

}
