package net.sourcemod.sourcepawn.build;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ReadOnly;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class ImmutableBuildConfiguration extends BuildConfiguration {

  @Nullable
  @NonNls
  private List<String> command;

  @Nullable
  @NonNls
  private List<Path> targets;

  @Nullable
  @NonNls
  private List<String> args;

  @Nullable
  @NonNls
  private List<String> consts;

  @Nullable
  @NonNls
  private Map<String, String> environment;

  ImmutableBuildConfiguration(@NotNull BuildConfiguration src) {
    super(src);
    Preconditions.checkArgument(src.getProcess() != null,
        "source build process path cannot be null");
  }

  @NotNull
  @NonNls
  @ReadOnly
  @Override
  public List<String> getCommand() {
    if (command == null) {
      command = super.getCommand();
    }

    return command;
  }

  @NotNull
  @NonNls
  @Override
  @ReadOnly
  public List<Path> getTargets() {
    if (targets == null) {
      targets = super.getTargets();
    }

    return targets;
  }

  @NotNull
  @NonNls
  @Override
  @ReadOnly
  public List<String> getArgs() {
    if (args == null) {
      args = super.getArgs();
    }

    return args;
  }

  @NotNull
  @NonNls
  @Override
  @ReadOnly
  public List<String> getConstants() {
    if (consts == null) {
      consts = super.getConstants();
    }

    return consts;
  }

  @NotNull
  @NonNls
  @Override
  @ReadOnly
  public Map<String, String> getEnvironment() {
    if (environment == null) {
      environment = super.getEnvironment();
    }

    return ImmutableMap.copyOf(environment);
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration setWorkingDirectory(@Nullable Path workingDirectory) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration setProcess(@NotNull Path process) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration appendTarget(@NotNull @NonNls Path file) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration appendArg(@NotNull @NonNls String arg) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration appendArg(@NotNull @NonNls String arg, @NotNull @NonNls String value) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration appendConst(@NotNull @NonNls String sym) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration appendConst(@NotNull @NonNls String sym, @NotNull @NonNls String value) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  @Override
  @Deprecated
  public BuildConfiguration setEnvironment(@NotNull @NonNls Map<String, String> environment) {
    throw new UnsupportedOperationException();
  }

}
