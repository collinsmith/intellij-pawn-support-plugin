package net.sourcemod.sourcepawn.build;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ReadOnly;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildConfiguration {

  private static final char ARG_PREFIX = '-';
  private static final char ARG_VALUE_SEPARATOR = '=';
  private static final char SYM_VALUE_SEPARATOR = '=';

  @NotNull
  public static BuildConfiguration create() {
    return new BuildConfiguration();
  }

  @Nullable
  private Path workingDirectory;

  @Nullable
  private Path process;

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
  Map<String, String> environment;

  private BuildConfiguration() {
  }

  protected BuildConfiguration(@NotNull BuildConfiguration src) {
    Preconditions.checkArgument(src != null, "source BuildConfiguration cannot be null");
    this.process = src.getProcess();
    if (src.targets != null) {
      this.targets = new ArrayList<>(src.targets);
    }

    if (src.args != null) {
      this.args = new ArrayList<>(src.args);
    }

    if (src.consts != null) {
      this.consts = new ArrayList<>(src.consts);
    }

    if (src.environment != null) {
      this.environment = new HashMap<>(src.environment);
    }
  }

  @NotNull
  public ImmutableBuildConfiguration commit() {
    return new ImmutableBuildConfiguration(this);
  }

  @NotNull
  @NonNls
  @ReadOnly
  public List<String> getCommand() {
    return ImmutableList.<String>builder()
        .add(getProcess().toString())
        .addAll(getTargets().stream().map(path -> path.toString()).iterator())
        .addAll(getArgs())
        .addAll(getConstants())
        .build();
  }

  @Nullable
  public Path getWorkingDirectory() {
    return workingDirectory;
  }

  @NotNull
  public BuildConfiguration setWorkingDirectory(@Nullable Path workingDirectory) {
    this.workingDirectory = workingDirectory;
    return this;
  }

  @Nullable
  public Path getProcess() {
    return process;
  }

  @NotNull
  public BuildConfiguration setProcess(@NotNull Path process) {
    Preconditions.checkArgument(process != null, "process cannot be null");
    this.process = process;
    return this;
  }

  @NotNull
  @NonNls
  @ReadOnly
  public List<Path> getTargets() {
    if (targets == null) {
      return Collections.emptyList();
    }

    return ImmutableList.copyOf(targets);
  }

  @NotNull
  @NonNls
  public BuildConfiguration appendTarget(@NotNull @NonNls Path file) {
    Preconditions.checkArgument(file != null, "file cannot be null");
    Preconditions.checkArgument(file.toFile().exists(), file + " does not exist");
    if (targets == null) {
      targets = new ArrayList<>();
    }

    targets.add(file);
    return this;
  }

  @NotNull
  @NonNls
  @ReadOnly
  public List<String> getArgs() {
    if (args == null) {
      return Collections.emptyList();
    }

    return ImmutableList.copyOf(args);
  }

  @NotNull
  @NonNls
  public BuildConfiguration appendArg(@NotNull @NonNls String arg) {
    Preconditions.checkArgument(arg != null, "arg cannot be null");
    if (args == null) {
      args = new ArrayList<>();
    }

    args.add(ARG_PREFIX + arg);
    return this;
  }

  @NotNull
  @NonNls
  public BuildConfiguration appendArg(@NotNull @NonNls String arg, @NotNull @NonNls String value) {
    Preconditions.checkArgument(arg != null, "arg cannot be null");
    Preconditions.checkArgument(value != null, "value cannot be null");
    return appendArg(arg + ARG_VALUE_SEPARATOR + value);
  }

  @NotNull
  @NonNls
  @ReadOnly
  public List<String> getConstants() {
    if (consts == null) {
      return Collections.emptyList();
    }

    return ImmutableList.copyOf(consts);
  }

  @NotNull
  @NonNls
  public BuildConfiguration appendConst(@NotNull @NonNls String sym) {
    Preconditions.checkArgument(sym != null, "sym cannot be null");
    if (consts == null) {
      consts = new ArrayList<>();
    }

    if (sym.charAt(sym.length()-1) != SYM_VALUE_SEPARATOR) {
      sym += SYM_VALUE_SEPARATOR;
    }

    consts.add(sym);
    return this;
  }

  @NotNull
  @NonNls
  public BuildConfiguration appendConst(@NotNull @NonNls String sym, @NotNull @NonNls String value) {
    Preconditions.checkArgument(sym != null, "sym cannot be null");
    Preconditions.checkArgument(value != null, "value cannot be null");
    return appendArg(sym + SYM_VALUE_SEPARATOR + value);
  }

  @NotNull
  @NonNls
  public Map<String, String> getEnvironment() {
    if (environment == null) {
      return Collections.emptyMap();
    }

    return environment;
  }

  @NotNull
  @NonNls
  public BuildConfiguration setEnvironment(@NotNull @NonNls Map<String, String> environment) {
    Preconditions.checkArgument(environment != null, "environment cannot be null");
    this.environment = environment;
    return this;
  }

  @Override
  public String toString() {
    return StringUtils.join(getCommand(), ' ');
  }
}
