package net.sourcemod.sourcepawn;

import com.google.common.collect.ImmutableSet;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SpSupport {

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

  private SpSupport() {
  }

}
