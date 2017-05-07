package net.alliedmods.lang.amxxpawn;

import com.google.common.collect.ImmutableSet;

import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;

import net.alliedmods.lang.amxxpawn.file.ApScriptFileType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ApSupport {

  private ApSupport() {}

  @NotNull
  @NonNls
  public static final String SMA = "sma";

  @NotNull
  @NonNls
  public static final String P = "p";

  @NotNull
  @NonNls
  public static final String PAWN = "pawn";

  @NotNull
  private static final Set<String> AmxxScriptExtensions = ImmutableSet.of(SMA, P, PAWN);

  @NotNull
  @NonNls
  public static final String INC = "inc";

  @NotNull
  @NonNls
  public static final String INL = "inl";

  @NotNull
  private static final Set<String> AmxxIncludeExtensions = ImmutableSet.of(INC, INL);

  @NotNull
  private static final Set<String> AmxxPawnExtensions = ImmutableSet.<String>builder()
      .addAll(AmxxIncludeExtensions)
      .addAll(AmxxScriptExtensions)
      .build();

  @NotNull
  public static Set<String> getScriptExtensions() {
    return AmxxScriptExtensions;
  }

  @NotNull
  public static Set<String> getIncludeExtensions() {
    return AmxxIncludeExtensions;
  }

  @NotNull
  public static Set<String> getExtensions() {
    return AmxxPawnExtensions;
  }

  public static boolean isApFile(@NotNull VirtualFile file) {
    return file.getFileType() instanceof ApScriptFileType;
  }

  @NotNull
  private static final String AMXXPC;
  static {
    if (SystemInfo.isWindows) {
      AMXXPC = "scripting/amxxpc.exe";
    } else if (SystemInfo.isLinux) {
      AMXXPC = "scripting/amxxpc";
    } else if (SystemInfo.isMac) {
      AMXXPC = "scripting/amxxpc";
    } else {
      throw new Error("Unsupported platform: " + SystemInfo.OS_NAME);
    }
  }

  @NotNull
  @NonNls
  public static String getCompilerPath() {
    return AMXXPC;
  }

  @NotNull
  private static final String INCLUDES_DIR = "scripting/include";

  @NotNull
  @NonNls
  public static String getIncludesPath() {
    return INCLUDES_DIR;
  }

  @NotNull
  @NonNls
  public static final String PLUGIN_TEMPLATE = "AMXModX Plugin.sma";

  @NotNull
  @NonNls
  public static final String INCLUDE_TEMPLATE = "AMXModX Include.inc";

}
