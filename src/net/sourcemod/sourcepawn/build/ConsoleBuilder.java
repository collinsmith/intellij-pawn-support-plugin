package net.sourcemod.sourcepawn.build;

import com.google.common.base.Preconditions;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.Executor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.actions.CloseAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import net.sourcemod.sourcepawn.action.RerunAction;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Map;

import javax.swing.*;

public class ConsoleBuilder implements Builder {

  @NotNull
  @NonNls
  private final String title;

  @NotNull
  private final Project project;

  private BuildConfiguration buildConfiguration;
  private Process process;
  private ProcessHandler processHandler;

  private Filter compilerOutputFilter;

  private ConsoleView console;
  private ActionToolbar toolbar;
  private DefaultActionGroup toolbarActions;

  private Runnable rerunAction;
  private Runnable onEndAction;

  public ConsoleBuilder(@NotNull @NonNls String title, @NotNull Project project) {
    Preconditions.checkArgument(title != null, "title cannot be null");
    Preconditions.checkArgument(project != null, "project cannot be null");
    this.title = title;
    this.project = project;
  }

  @NotNull
  @NonNls
  public String getTitle() {
    return title;
  }

  @NotNull
  public Project getProject() {
    return project;
  }

  @Nullable
  public BuildConfiguration getBuildConfiguration() {
    return buildConfiguration;
  }

  @NotNull
  public ConsoleBuilder setBuildConfiguration(@NotNull BuildConfiguration buildConfiguration) {
    Preconditions.checkArgument(buildConfiguration != null, "buildConfiguration cannot be null");
    this.buildConfiguration = buildConfiguration;
    return this;
  }

  @Nullable
  public Filter getCompilerOutputFilter() {
    return compilerOutputFilter;
  }

  public ConsoleBuilder setCompilerOutputFilter(@Nullable Filter filter) {
    this.compilerOutputFilter = filter;
    return this;
  }

  @Nullable
  public Runnable getRerunAction() {
    return rerunAction;
  }

  public ConsoleBuilder setRerunAction(@Nullable Runnable rerunAction) {
    this.rerunAction = rerunAction;
    return this;
  }

  @Nullable
  public Runnable getEndedAction() {
    return onEndAction;
  }

  public ConsoleBuilder setEndedAction(@Nullable Runnable onEndAction) {
    this.onEndAction = onEndAction;
    return this;
  }

  @Nullable
  public Process getProcess() {
    return process;
  }

  @Nullable
  public ProcessHandler getProcessHandler() {
    return processHandler;
  }

  @Nullable
  @Override
  public Process build() {
    ProcessBuilder processBuilder = new ProcessBuilder(buildConfiguration.getCommand());
    Map<String, String> environment = processBuilder.environment();
    if (buildConfiguration.getEnvironment().isEmpty()) {
      environment.clear();
      environment.putAll(buildConfiguration.getEnvironment());
    }

    try {
      Path workingDirectory = buildConfiguration.getWorkingDirectory();
      process = processBuilder
          .directory(workingDirectory == null ? null : workingDirectory.toFile()).start();
    } catch (IOException e) {
      Messages.showErrorDialog(project,
          String.format("Error launching %s,%n%s", buildConfiguration, e.getMessage()),
          "Problem Executing External Program");
      PrintStream printStream = new PrintStream(processHandler.getProcessInput());
      e.printStackTrace(printStream);
      return null;
    }

    processHandler = new OSProcessHandler(process, buildConfiguration.toString());
    processHandler.addProcessListener(new ProcessAdapter() {
      @Override
      public void startNotified(ProcessEvent event) {
        onProcessStarted();
      }

      @Override
      public void processTerminated(ProcessEvent event) {
        onProcessEnded();
        processHandler.notifyTextAvailable("Done." + System.getProperty("line.separator"),
            ProcessOutputTypes.SYSTEM);
        processHandler.removeProcessListener(this);
      }
    });


    JPanel panel = new JPanel(new BorderLayout());
    panel.add(createConsole().getComponent(), BorderLayout.CENTER);
    panel.add(createToolbar(panel).getComponent(), BorderLayout.WEST);

    Executor executor = DefaultRunExecutor.getRunExecutorInstance();
    final RunContentDescriptor descriptor
        = new RunContentDescriptor(console, processHandler, panel, title);
    toolbarActions.add(new CloseAction(executor, descriptor, project));
    ExecutionManager.getInstance(project).getContentManager()
        .showRunContent(executor, descriptor);

    return process;
  }

  @NotNull
  private ConsoleView createConsole() {
    console = TextConsoleBuilderFactory.getInstance()
        .createBuilder(project).getConsole();
    if (compilerOutputFilter != null) {
      console.addMessageFilter(compilerOutputFilter);
    }

    console.attachToProcess(processHandler);
    return console;
  }

  @NotNull
  private ActionToolbar createToolbar(@NotNull JComponent component) {
    assert component != null : "component cannot be null";
    this.toolbarActions = new DefaultActionGroup();
    toolbarActions.add(new RerunAction(console, processHandler, new Runnable() {
      @Override
      public void run() {
        Runnable rerunAction = ConsoleBuilder.this.rerunAction;
        if (rerunAction != null) {
          rerunAction.run();
        } else {
          build();
        }
      }
    }));

    boolean[] errorDetected = new boolean[1];
    ProgressManager.getInstance().run(new Task.Backgroundable(project, title, true) {
      @Override
      public void run(@NotNull ProgressIndicator indicator) {
        processHandler.startNotify();
        while (!processHandler.isProcessTerminated()) {
          synchronized (this) {
            try {
              wait(1000);
            } catch (InterruptedException e) {
              break;
            }

            try {
              ProgressManager.checkCanceled();
            } catch (RuntimeException e) {
              if (!processHandler.isProcessTerminated() && !processHandler.isProcessTerminating()) {
                processHandler.destroyProcess();
              }

              break;
            }
          }
        }

        Runnable onEndAction = ConsoleBuilder.this.onEndAction;
        if (!errorDetected[0] && onEndAction != null) {
          SwingUtilities.invokeLater(onEndAction);
        }
      }
    });

    this.toolbar = ActionManager.getInstance()
        .createActionToolbar(ActionPlaces.COMPILER_MESSAGES_TOOLBAR, toolbarActions, false);
    return this.toolbar;
  }

  protected void onProcessStarted() {
  }

  protected void onProcessEnded() {
  }

}
