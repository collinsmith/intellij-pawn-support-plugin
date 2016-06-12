package net.sourcemod.sourcepawn.build.old;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.fileEditor.FileDocumentManager;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Deprecated
public class BuildState {

  private final List<String> runCommand;
  private final File runCommandDir;
  private final Map<String, String> commandLineProperties;

  private Process process;

  public BuildState(List<String> runCommand,
                    File runCommandDir,
                    Map<String, String> commandLineProperties) {
    this.runCommand = runCommand;
    this.runCommandDir = runCommandDir;
    this.commandLineProperties = commandLineProperties;
  }

  public void start() throws IOException {
    ProcessBuilder processBuilder = new ProcessBuilder(runCommand);
    Map<String, String> environment = processBuilder.environment();
    if (commandLineProperties != null) {
      environment.clear();
      environment.putAll(commandLineProperties);
    }

    System.out.println("Running: " + runCommand + " from " + runCommandDir);
    process = processBuilder.directory(runCommandDir).start();
  }

  public static void saveDocuments() {
    invokeOnEdtSynchronously(new Runnable() {
      @Override
      public void run() {
        FileDocumentManager.getInstance().saveAllDocuments();
      }
    });
  }

  public static void saveAll() {
    invokeOnEdtSynchronously(new Runnable() {
      @Override
      public void run() {
        ApplicationManager.getApplication().saveAll();
      }
    });
  }

  public static void invokeOnEdtSynchronously(Runnable saveRunnable) {
    Application application = ApplicationManager.getApplication();
    if (application.isDispatchThread()) {
      saveRunnable.run();
    } else {
      application.invokeAndWait(saveRunnable, ModalityState.defaultModalityState());
    }
  }

  public Process getProcess() {
    return process;
  }

  public String getRunCommand() {
    return StringUtils.join(runCommand, ' ');
  }

}
