package net.alliedmods.lang.amxxpawn.sdk;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.projectRoots.AdditionalDataConfigurable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.projectRoots.SdkAdditionalData;
import com.intellij.openapi.projectRoots.SdkModel;
import com.intellij.openapi.projectRoots.SdkModificator;
import com.intellij.openapi.projectRoots.SdkType;
import com.intellij.openapi.roots.OrderRootType;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApSupport;

import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import javax.swing.*;

public class ApSdkType extends SdkType implements ApplicationComponent {

  private static final boolean ADD_INCLUDES_DIRECTLY = true;

  @NotNull
  @NonNls
  private static final String AMXMODX_VERSION = "scripting/include/amxmodx_version.inc";

  @NotNull
  public static ApSdkType getInstance() {
    return ApplicationManager.getApplication().getComponent(ApSdkType.class);
  }

  private ApSdkType() {
    super("AmxxSDK");
  }

  @NonNls
  @Nullable
  public static String getCompilerPath(@NotNull Sdk sdk) {
    VirtualFile sdkHome = sdk.getHomeDirectory();
    if (sdkHome == null) {
      return null;
    }

    VirtualFile compiler = sdkHome.findFileByRelativePath(ApSupport.getCompilerPath());
    if (compiler == null) {
      return null;
    }

    return compiler.getPath();
  }

  @Override
  public void initComponent() {}

  @Override
  public void disposeComponent() {}

  @NotNull
  @Override
  public String getComponentName() {
    return "AmxxPawn.AmxxSDK";
  }

  @Nullable
  @Override
  public String suggestHomePath() {
    return null;
  }

  @Override
  public boolean isValidSdkHome(String path) {
    VirtualFile file = LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    return file != null && file.findFileByRelativePath(AMXMODX_VERSION) != null;
  }

  @Nullable
  @Override
  public String getVersionString(String sdkHome) {
    VirtualFile versionInfo = LocalFileSystem.getInstance().refreshAndFindFileByPath(
        Paths.get(sdkHome).resolve(AMXMODX_VERSION).toString());
    if (versionInfo == null) {
      return null;
    }

    try (InputStream in = versionInfo.getInputStream();
         InputStreamReader reader = new InputStreamReader(in);
         BufferedReader buffer = new BufferedReader(reader)) {
      String line;
      while ((line = buffer.readLine()) != null) {
        if (line.matches("stock const AMXX_VERSION_STR\\[] = \".*\";")) {
          return line
              .replaceFirst("stock const AMXX_VERSION_STR\\[] = \"", "")
              .replaceFirst("\";", "");
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
      // This will make it easier to read dev versions, e.g., 1.8.3-dev+5110 -> 1.8.3-dev
      //version = version.substring(0, version.indexOf('+'));
      return ApBundle.message("amxx.sdk.name", version);
    }

    return sdkHome;
  }

  @Override
  public Icon getIcon() {
    return ApIcons.Amxx16;
  }

  @NotNull
  @Override
  public Icon getIconForAddAction() {
    // TODO: This can be a custom icon with a plus or something
    return getIcon();
  }

  @Nullable
  @Override
  public String getDefaultDocumentationUrl(@NotNull Sdk sdk) {
    return ApBundle.message("amxx.url.doc");
  }

  @Nullable
  @Override
  public String getDownloadSdkUrl() {
    return ApBundle.message("amxx.url.sdk");
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
    return ApBundle.message("amxx.sdk");
  }

  @Override
  public void saveAdditionalData(@NotNull SdkAdditionalData sdkAdditionalData, @NotNull Element element) {}

  @Override
  public void setupSdkPaths(@NotNull Sdk sdk) {
    VirtualFile homeDir = sdk.getHomeDirectory();
    if (homeDir == null) {
      return;
    }

    VirtualFile includes = homeDir.findFileByRelativePath(ApSupport.getIncludesPath());
    if (includes != null) {
      SdkModificator sdkModificator = sdk.getSdkModificator();
      if (ADD_INCLUDES_DIRECTLY) {
        for (VirtualFile include : includes.getChildren()) {
          sdkModificator.addRoot(include, OrderRootType.CLASSES);
        }
      } else {
        sdkModificator.addRoot(includes, OrderRootType.CLASSES);
      }

      sdkModificator.commitChanges();
    }
  }

}
