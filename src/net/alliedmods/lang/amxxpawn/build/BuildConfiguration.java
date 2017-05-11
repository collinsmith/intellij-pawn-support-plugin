package net.alliedmods.lang.amxxpawn.build;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ReadOnly;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

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
  private String process;

  @Nullable
  private List<Path> targets;

  @NonNls
  @Nullable
  private List<String> args;

  @NonNls
  @Nullable
  private List<String> consts;

  @NonNls
  @Nullable
  Map<String, String> environment;

  private BuildConfiguration() {}

  protected BuildConfiguration(@NotNull BuildConfiguration src) {
    this.process = src.process;
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
  @ReadOnly
  public ImmutableBuildConfiguration commit() {
    return new ImmutableBuildConfiguration(this);
  }

  @NonNls
  @NotNull
  @ReadOnly
  public List<String> getCommand() {
    return ImmutableList.<String>builder()
        .add(getProcess())
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
  public BuildConfiguration setWorkingDirectory(@NotNull Path workingDirectory) {
    Preconditions.checkArgument(workingDirectory != null, "workingDirectory cannot be null");
    this.workingDirectory = workingDirectory;
    return this;
  }

  @NonNls
  @NotNull
  public String getProcess() {
    return Strings.nullToEmpty(process);
  }

  @NotNull
  public BuildConfiguration setProcess(@NotNull @NonNls String process) {
    Preconditions.checkArgument(!process.isEmpty(), "process cannot be empty");
    this.process = process;
    return this;
  }

  @NotNull
  @ReadOnly
  public List<Path> getTargets() {
    if (targets == null) {
      return Collections.emptyList();
    }

    return ImmutableList.copyOf(targets);
  }

  @NotNull
  public BuildConfiguration appendTarget(@NotNull Path file) {
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
  public BuildConfiguration appendArg(@NotNull @NonNls String arg) {
    Preconditions.checkArgument(!arg.isEmpty(), "arg cannot be empty");
    if (args == null) {
      args = new ArrayList<>();
    }

    args.add(ARG_PREFIX + arg);
    return this;
  }

  @NotNull
  public BuildConfiguration appendArg(@NotNull @NonNls String arg, @NotNull @NonNls String value) {
    Preconditions.checkArgument(!arg.isEmpty(), "arg cannot be empty");
    Preconditions.checkArgument(!value.isEmpty(), "value cannot be empty");
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
  public BuildConfiguration appendConst(@NotNull @NonNls String sym) {
    Preconditions.checkArgument(!sym.isEmpty(), "sym cannot be empty");
    if (consts == null) {
      consts = new ArrayList<>();
    }

    if (sym.charAt(sym.length() - 1) != SYM_VALUE_SEPARATOR) {
      sym += SYM_VALUE_SEPARATOR;
    }

    consts.add(sym);
    return this;
  }

  @NotNull
  public BuildConfiguration appendConst(@NotNull @NonNls String sym, @NotNull @NonNls String value) {
    Preconditions.checkArgument(!sym.isEmpty(), "sym cannot be empty");
    Preconditions.checkArgument(!value.isEmpty(), "value cannot be empty");
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
