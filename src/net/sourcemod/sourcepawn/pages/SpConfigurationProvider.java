package net.sourcemod.sourcepawn.pages;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurableProvider;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.Nullable;

public class SpConfigurationProvider extends ConfigurableProvider {

  private Project project;

  public SpConfigurationProvider(Project project) {
    this.project = project;
  }

  @Nullable
  @Override
  public Configurable createConfigurable() {
    return new SpConfigurationPage(project);
  }

}
