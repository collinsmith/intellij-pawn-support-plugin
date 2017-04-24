package net.alliedmods.lang.amxxpawn.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.ComponentWithBrowseButton;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class ApRunSettingsEditor extends SettingsEditor<ApRunConfiguration> {

  private JPanel panel;
  private LabeledComponent<ComponentWithBrowseButton> mainClass;

  public ApRunSettingsEditor(Project project) {}

  @Override
  protected void resetEditorFrom(ApRunConfiguration s) {}

  @Override
  protected void applyEditorTo(ApRunConfiguration s) throws ConfigurationException {}

  @NotNull
  @Override
  protected JComponent createEditor() {
    return panel;
  }

  private void createUIComponents() {
    mainClass = new LabeledComponent<>();
    mainClass.setComponent(new TextFieldWithBrowseButton());
  }

}
