package net.alliedmods.lang.amxxpawn.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

import net.alliedmods.lang.amxxpawn.ApIcons;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ApRunConfigurationType implements ConfigurationType {

  private final ConfigurationFactory factory;

  public ApRunConfigurationType() {
    factory = new ConfigurationFactory(this) {
      @NotNull
      @Override
      public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new ApRunConfiguration(project, this, "AmxxPawn Script");
      }
    };
  }

  @Override
  public String getDisplayName() {
    return "AMXModX";
  }

  @Override
  public String getConfigurationTypeDescription() {
    return "AMXModX Run";
  }

  @Override
  public ConfigurationFactory[] getConfigurationFactories() {
    return new ConfigurationFactory[] { factory };
  }

  @NotNull
  @Override
  public String getId() {
    return "AmxxPawnRunConfigurationType";
  }

  @Override
  public Icon getIcon() {
    return ApIcons.Amxx16;
  }
}
