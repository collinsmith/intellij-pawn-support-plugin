package net.sourcemod.sourcepawn.build;

import com.intellij.execution.process.ProcessHandler;

import org.jetbrains.annotations.Nullable;

public interface Builder {

  Process getProcess();

  ProcessHandler getProcessHandler();

  @Nullable
  Process build();

}
