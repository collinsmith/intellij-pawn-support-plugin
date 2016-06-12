package net.sourcemod.sourcepawn.sdk;

import com.intellij.openapi.projectRoots.Sdk;

import net.sourcemod.sourcepawn.SpSupport;

import java.nio.file.Path;
import java.nio.file.Paths;

public class SpSdkUtils {

  public static Path getCompilerPath(Sdk sdk) {
    String path = sdk.getHomeDirectory().findChild(SpSupport.getCompiler()).getPath();
    return Paths.get(path);
  }

  private SpSdkUtils() {
  }

}
