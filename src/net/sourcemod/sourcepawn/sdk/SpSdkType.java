package net.sourcemod.sourcepawn.sdk;

import com.intellij.openapi.projectRoots.Sdk;

import org.jetbrains.annotations.NonNls;

public interface SpSdkType {

  @NonNls
  String getBinPath(Sdk sdk);

}
