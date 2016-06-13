package net.sourcemod.sourcepawn;

import com.google.common.collect.ImmutableSet;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;

import net.sourcemod.sourcepawn.file.SpScript;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SpSupport implements ProjectComponent {

  @NotNull @NonNls public static final String SP_EXTENSION = "sp";
  @NotNull @NonNls public static final String P_EXTENSION = "p";
  @NotNull @NonNls public static final String PAWN_EXTENSION = "pawn";

  private static final Set<String> SCRIPT_EXTENSIONS
      = ImmutableSet.of(SP_EXTENSION, P_EXTENSION, PAWN_EXTENSION);

  @NotNull @NonNls public static final String INC_EXTENSION = "inc";
  @NotNull @NonNls public static final String INL_EXTENSION = "inl";

  private static final Set<String> INCLUDE_EXTENSIONS
      = ImmutableSet.of(INC_EXTENSION, INL_EXTENSION);

  @NotNull
  private static final Set<String> EXTENSIONS = ImmutableSet.<String>builder()
      .addAll(SCRIPT_EXTENSIONS)
      .addAll(INCLUDE_EXTENSIONS)
      .build();

  public static Set<String> getScriptExtensions() {
    return SCRIPT_EXTENSIONS;
  }

  public static Set<String> getIncludeExtensions() {
    return INCLUDE_EXTENSIONS;
  }

  public static Set<String> getExtensions() {
    return EXTENSIONS;
  }

  @NotNull
  @NonNls
  private static final String SP_COMPILER;

  static {
    if (SystemInfo.isWindows) {
      SP_COMPILER = "spcomp.exe";
    } else if (SystemInfo.isLinux) {
      SP_COMPILER = "spcomp";
    } else if (SystemInfo.isMac) {
      SP_COMPILER = "spcomp";
    } else {
      throw new Error("Unsupported platform: " + SystemInfo.OS_NAME);
    }
  }

  public static String getCompiler() {
    return SP_COMPILER;
  }

  public static boolean isSpFile(@NotNull VirtualFile file) {
    return !file.isDirectory() && file.getFileType() == SpScript.getInstance();
  }

  private SpSupport() {
  }

  @Override
  public void projectOpened() {

  }

  @Override
  public void projectClosed() {

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
    return "SourcePawn.Loader";
  }

}
