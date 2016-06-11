package net.sourcemod.sourcepawn.sdk;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.projectRoots.AdditionalDataConfigurable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkAdditionalData;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.projectRoots.SdkModificator;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import net.sourcemod.sourcepawn.SpIcons;

import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.*;

public class SpSdk extends SdkType implements SpSdkType, ApplicationComponent {

  public SpSdk() {
    super("SourceModSdk");
  }

  @Override
  @NonNls
  public String getBinPath(Sdk sdk) {
    VirtualFile compiler = sdk.getHomeDirectory().findChild("spcomp.exe");
    if (compiler == null) {
      return null;
    }

    return compiler.getPath();
  }

  @Override
  public void initComponent() {

  }

  @Override
  public void disposeComponent() {

  }

  @NotNull
  @Override
  public String getComponentName() {
    return "SourcePawn.SourceModSdk";
  }

  @Nullable
  @Override
  public String suggestHomePath() {
    return null;
  }

  @Override
  public boolean isValidSdkHome(String path) {
    VirtualFile file = LocalFileSystem.getInstance().findFileByPath(path);
    return file != null && file.findChild("spcomp.exe") != null;
  }

  @Nullable
  @Override
  public String getVersionString(String sdkHome) {
    VirtualFile version_auto = LocalFileSystem.getInstance()
        .findFileByPath(sdkHome + "/include/version_auto.inc");
    try (InputStream in = version_auto.getInputStream();
         InputStreamReader reader = new InputStreamReader(in);
         BufferedReader buffer = new BufferedReader(reader)) {
      String line;
      while ((line = buffer.readLine()) != null) {
        if (line.startsWith("#define SOURCEMOD_VERSION")) {
          line = line.replaceFirst("#define SOURCEMOD_VERSION", "");
          line = line.replaceAll("\\\"", "");
          line = line.trim();
          return line;
        }
      }
    } catch (IOException e) {
      return null;
    }

    return null;
  }

  @Override
  public String suggestSdkName(String currentSdkName, String sdkHome) {
    String version = getVersionString(sdkHome);
    if (version != null) {
      return "SourceMod " + version;
    }

    return sdkHome;
  }

  @Override
  public Icon getIcon() {
    return SpIcons.SOURCEPAWN16;
  }

  @NotNull
  @Override
  public Icon getIconForAddAction() {
    return SpIcons.SOURCEPAWN16;
  }

  @Nullable
  @Override
  public String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
    return "https://sm.alliedmods.net/new-api/";
  }

  @Nullable
  public String getDownloadSdkUrl() {
    return "http://www.sourcemod.net/downloads.php?branch=stable";
  }

  @Nullable
  @Override
  public AdditionalDataConfigurable createAdditionalDataConfigurable(@NotNull SdkModel sdkModel,
      @NotNull SdkModificator sdkModificator) {
    return null;
  }

  @NotNull
  @Override
  public String getPresentableName() {
    return "SourceMod SDK";
  }

  @Override
  public void saveAdditionalData(@NotNull SdkAdditionalData additionalData,
                                 @NotNull Element additional) {

  }
}
