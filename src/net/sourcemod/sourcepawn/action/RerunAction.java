package net.sourcemod.sourcepawn.action;

import com.intellij.CommonBundle;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.util.IconLoader;

public class RerunAction extends AnAction {

  private final ProcessHandler processHandler;
  private Runnable rerunTask;

  public RerunAction(ConsoleView consoleView, ProcessHandler processHandler, Runnable rerunTask) {
    super(CommonBundle.message("action.rerun"),
        CommonBundle.message("action.rerun"),
        IconLoader.getIcon("/actions/refreshUsages.png"));
    this.processHandler = processHandler;
    this.rerunTask = rerunTask;

    registerCustomShortcutSet(CommonShortcuts.getRerun(), consoleView.getComponent());
  }

  @Override
  public void update(AnActionEvent e) {
    e.getPresentation().setEnabled(processHandler.isProcessTerminated());
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    rerunTask.run();
  }


}
