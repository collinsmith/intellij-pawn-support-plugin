package net.sourcemod.sourcepawn.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;

import net.sourcemod.sourcepawn.SpIcons;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SpConfigurationType implements ConfigurationType {

  @Override
  public String getDisplayName() {
    return "SourceMod Script";
  }

  @Override
  public String getConfigurationTypeDescription() {
    return "Configure options for running a SourceMod script";
  }

  @Override
  public Icon getIcon() {
    return SpIcons.SOURCEPAWN16;
  }

  @NotNull
  @Override
  public String getId() {
    return "SourcePawn Run Configuration";
  }

  @Override
  public ConfigurationFactory[] getConfigurationFactories() {
    return new ConfigurationFactory[] {
        new SpConfigurationFactory(this)
    };
  }
}
