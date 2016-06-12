package net.sourcemod.sourcepawn.build.old;

import com.intellij.openapi.compiler.generic.BuildTarget;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class SpBuildTarget extends BuildTarget {

  @Nullable
  private final String buildConfiguration;

  @Nullable
  private final String buildAction;

  @Nullable
  private final String additionalCommandLineParameters;

  public SpBuildTarget(@Nullable String buildConfiguration,
                       @Nullable String buildAction,
                       @Nullable String additionalCommandLineParameters) {
    this.buildConfiguration = buildConfiguration;
    this.buildAction = buildAction;
    this.additionalCommandLineParameters = additionalCommandLineParameters;
  }

  public String getBuildConfiguration() {
    return buildConfiguration;
  }

  public String getBuildAction() {
    return buildAction;
  }

  public String getAdditionalCommandLineParameters() {
    return additionalCommandLineParameters;
  }

  @NotNull
  @Override
  public String getId() {
    return "<sourcepawn>";
  }
}
