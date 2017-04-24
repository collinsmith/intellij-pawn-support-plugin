package net.alliedmods.lang.amxxpawn.sdk;

import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApSdkUtils {

  private ApSdkUtils() {}

  //@NotNull
  //public static Path getCompilerPath(@NotNull Sdk sdk) {
  //  return Paths.get(sdk.getHomeDirectory().findChild(ApSupport.getCompilerPath()).getPath());
  //}

  @NotNull
  public static List<Sdk> getApSdks() {
    List<Sdk> sdks = null;
    final ApSdkType apSdkType = ApSdkType.getInstance();
    for (Sdk sdk : ProjectJdkTable.getInstance().getAllJdks()) {
      if (sdk.getSdkType() == apSdkType) {
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

}
