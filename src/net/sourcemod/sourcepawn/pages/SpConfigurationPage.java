package net.sourcemod.sourcepawn.pages;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;

import net.sourcemod.sourcepawn.SpBundle;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SpConfigurationPage implements SearchableConfigurable {

  private Project project;

  private TextFieldWithBrowseButton compilerPathBrowser;
  private JPanel panel;

  public SpConfigurationPage(Project project) {
    this.project = project;
  }

  @NotNull
  @Override
  public String getId() {
    return "editor.preferences.sourcepawnoptions";
  }

  @Nullable
  @Override
  public Runnable enableSearch(String option) {
    return null;
  }

  @Nls
  @Override
  public String getDisplayName() {
    return SpBundle.message("sp.gui.options.title");
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return "reference.settings.ide.settings.sourcepawn";
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    return panel;
  }

  @Override
  public boolean isModified() {
    return false;
  }

  @Override
  public void apply() throws ConfigurationException {

  }

  @Override
  public void reset() {

  }

  @Override
  public void disposeUIResources() {

  }
}
