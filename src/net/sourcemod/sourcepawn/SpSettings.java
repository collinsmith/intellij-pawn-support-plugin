package net.sourcemod.sourcepawn;

import com.intellij.openapi.actionSystem.DataProvider;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDialog;
import com.intellij.openapi.fileChooser.FileChooserFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;

import javax.swing.*;

@State(name = "SourcePawnSettings",
    storages = {
        @Storage(StoragePathMacros.WORKSPACE_FILE)
    })
public class SpSettings
    implements ApplicationComponent, Configurable, PersistentStateComponent<SpSettings> {

  public boolean saveDocuments = true;

  private Form form;

  public static SpSettings getInstance() {
    return ServiceManager.getService(SpSettings.class);
  }

  public SpSettings() {
  }

  @Nullable
  @Override
  public SpSettings getState() {
    return this;
  }

  @Override
  public void loadState(SpSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  @NotNull
  @Override
  public String getComponentName() {
    return "SourcePawn.Settings";
  }

  @Override
  public void initComponent() {
  }

  @Override
  public void disposeComponent() {
  }

  @Nls
  @Override
  public String getDisplayName() {
    return "SourcePawn";
  }

  @Nullable
  @Override
  public String getHelpTopic() {
    return null;
  }

  @Nullable
  @Override
  public JComponent createComponent() {
    //if (form == null) {
    //  form = new Form(state.compilerPath);
    //}

    return form.panel;
  }

  @Override
  public boolean isModified() {
    return false;// !state.compilerPath.toString().equals(form.compilerPathBrowser.getTextField().getText());
  }

  @Override
  public void apply() throws ConfigurationException {
    //state.compilerPath = Paths.get(form.compilerPathBrowser.getTextField().getText());
  }

  @Override
  public void reset() {
    //form.reset(state.compilerPath);
  }

  @Override
  public void disposeUIResources() {
    form = null;
  }

  public class Form {

    private TextFieldWithBrowseButton compilerPathBrowser;
    private JPanel panel;

    Form(Path compilerPath) {
      FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(
          false, true, false, false, false, false) {
        public boolean isFileSelectable(VirtualFile virtualFile) {
          return virtualFile.findChild("spcomp.exe") != null;
        }
      };
      fileChooserDescriptor.setTitle(SpBundle.message("sp.gui.paths.spcomp.title"));
      setupDirectoryBrowseButton(compilerPathBrowser, fileChooserDescriptor);

      reset(compilerPath);
    }

    void reset(Path compilerPath) {
      compilerPathBrowser.getTextField().setText(compilerPath.toString());
    }

  }

  @NonNls
  public static final String PATH_SEPARATOR = ";";

  public static void setupDirectoryBrowseButton(final TextFieldWithBrowseButton browser,
                                                final FileChooserDescriptor fileChooserDescriptor) {
    browser.getButton().addActionListener(
        new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            Window mostRecentFocusedWindow = WindowManagerEx.getInstanceEx()
                .getMostRecentFocusedWindow();
            Project project = null;
            while (mostRecentFocusedWindow != null) {
              if (mostRecentFocusedWindow instanceof DataProvider) {
                project = PlatformDataKeys.PROJECT.getData((DataProvider)mostRecentFocusedWindow);
                if (project != null) break;
              }
              mostRecentFocusedWindow = (Window) mostRecentFocusedWindow.getParent();
            }

            FileChooserDialog fileChooser = FileChooserFactory.getInstance().createFileChooser(
                fileChooserDescriptor,
                project,
                WindowManagerEx.getInstanceEx().suggestParentWindow(project)
            );

            String lastPathToMSVS = browser.getText();
            VirtualFile file = lastPathToMSVS != null && lastPathToMSVS.length() > 0 ? VfsUtil
                .findRelativeFile(lastPathToMSVS, null) : null;

            final VirtualFile[] virtualFiles = fileChooser.choose(project, file);
            if (virtualFiles != null && virtualFiles.length == 1) {
              browser.setText(virtualFiles[0].getPresentableUrl());
            }
          }
        }
    );
  }

}
