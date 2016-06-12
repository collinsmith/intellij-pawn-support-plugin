package net.sourcemod.sourcepawn.run;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SpRunConfigurationSettings extends SettingsEditor<SpRunConfiguration> {

  private JPanel panel;

  @Override
  protected void resetEditorFrom(SpRunConfiguration s) {

  }

  @Override
  protected void applyEditorTo(SpRunConfiguration s) throws ConfigurationException {

  }

  @NotNull
  @Override
  protected JComponent createEditor() {
    return panel;
  }

}
