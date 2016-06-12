package net.sourcemod.sourcepawn.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationType;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class SpConfigurationFactory extends ConfigurationFactory {

  protected SpConfigurationFactory(ConfigurationType type) {
    super(type);
  }

  @NotNull
  @Override
  public RunConfiguration createTemplateConfiguration(@NotNull Project project) {
    return new SpRunConfiguration(project, this, "SourceMod Script");
  }

  @Override
  public String getName() {
    return "SourcePawn Configuration Factory";
  }

}
