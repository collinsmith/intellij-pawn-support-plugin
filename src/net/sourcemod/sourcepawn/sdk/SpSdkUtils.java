package net.sourcemod.sourcepawn.sdk;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;

import net.sourcemod.sourcepawn.SpSupport;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpSdkUtils {

  public static Path getCompilerPath(Sdk sdk) {
    String path = sdk.getHomeDirectory().findChild(SpSupport.getCompiler()).getPath();
    return Paths.get(path);
  }

  @NotNull
  public static List<Sdk> getSpSdks() {
    List<Sdk> sdks = null;
    final SpSdk spSdk = SpSdk.getInstance();
    for (Sdk sdk : ProjectJdkTable.getInstance().getAllJdks()) {
      if (sdk.getSdkType() == spSdk) {
        if (sdks == null) {
          sdks = new ArrayList<>();
        }

        sdks.add(sdk);
      }
    }

    if (sdks == null) {
      return Collections.emptyList();
    }

    return sdks;
  }

  private SpSdkUtils() {
  }

}
