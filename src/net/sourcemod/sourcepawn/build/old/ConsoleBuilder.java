package net.sourcemod.sourcepawn.build.old;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.Executor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessListener;
import com.intellij.execution.process.ProcessOutputTypes;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.actions.CloseAction;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionPlaces;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Key;

import net.sourcemod.sourcepawn.action.RerunAction;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

@Deprecated
public class ConsoleBuilder {

  private final BuildState buildState;
  private final Runnable showOptionsCallback;
  private final Runnable onEndAction;
  private final Runnable onRerunAction;
  private final String title;
  private final Project project;

  private OSProcessHandler processHandler;
  private Filter filter;

  public ConsoleBuilder(String title,
                        BuildState buildState,
                        Project project,
                        Filter filter,
                        Runnable showOptionsCallback,
                        Runnable onEndAction,
                        Runnable onRerunAction) {
    this.title = title;
    this.buildState = buildState;
    this.project = project;
    this.filter = filter;
    this.showOptionsCallback = showOptionsCallback;
    this.onEndAction = onEndAction;
    this.onRerunAction = onRerunAction;
  }

  public Process getProcess() {
    return buildState.getProcess();
  }

  public OSProcessHandler getProcessHandler() {
    return processHandler;
  }

  public void onProcessStarted() {
  }

  public void onProcessFinished() {
  }

  public void start() {
    try {
      BuildState.saveDocuments();
      buildState.start();
    } catch (IOException e) {
      Messages.showErrorDialog(project,
          String.format("Error launching %s,%n%s", buildState.getRunCommand(), e.getMessage()),
          "Problem Executing External Program");
      return;
    }

    processHandler = new OSProcessHandler(buildState.getProcess(), buildState.getRunCommand());

    processHandler.addProcessListener(new ProcessListener() {
      @Override
      public void startNotified(ProcessEvent event) {
        onProcessStarted();
      }

      @Override
      public void processTerminated(ProcessEvent event) {
        onProcessFinished();
        processHandler.notifyTextAvailable("Done" + System.getProperty("line.separator"),
            ProcessOutputTypes.SYSTEM);
        processHandler.removeProcessListener(this);
      }

      @Override
      public void processWillTerminate(ProcessEvent event, boolean willBeDestroyed) {
      }

      @Override
      public void onTextAvailable(ProcessEvent event, Key outputType) {
      }
    });

    ConsoleView consoleView = TextConsoleBuilderFactory.getInstance()
        .createBuilder(project).getConsole();

    if (filter != null) {
      consoleView.addMessageFilter(filter);
    }

    consoleView.attachToProcess(processHandler);

    final Executor defaultRunner = DefaultRunExecutor.getRunExecutorInstance();
    final DefaultActionGroup toolbarActions = new DefaultActionGroup();

    JPanel content = new JPanel(new BorderLayout());
    content.add(consoleView.getComponent(), BorderLayout.CENTER);
    content.add(ActionManager.getInstance()
        .createActionToolbar(ActionPlaces.UNKNOWN, toolbarActions, false)
        .getComponent(),
        BorderLayout.WEST);

    final boolean[] errorDetected = new boolean[1];
    final RunContentDescriptor descriptor
        = new RunContentDescriptor(consoleView, processHandler, content, title);

    toolbarActions.add(new RerunAction(consoleView, processHandler, new Runnable() {
      @Override
      public void run() {
        if (onRerunAction != null) {
          onRerunAction.run();
        } else {
          start();
        }
      }
    }));

    toolbarActions.add(new CloseAction(defaultRunner, descriptor, project));

    ExecutionManager.getInstance(project).getContentManager()
        .showRunContent(defaultRunner, descriptor);
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
      }
    });
  }

}
