package net.alliedmods.lang.amxxpawn.build;

import com.intellij.execution.process.ProcessHandler;

public interface Builder {

  Process getProcess();

  ProcessHandler getProcessHandler();

  Process build();

}
