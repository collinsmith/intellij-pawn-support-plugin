package net.alliedmods.lang.amxxpawn.parser;

import com.intellij.AbstractBundle;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

public class ApErrorMessages extends AbstractBundle {
  public static final ApErrorMessages INSTANCE = new ApErrorMessages();

  @NonNls public static final String BUNDLE = "net.alliedmods.lang.amxxpawn.parser.ApErrorMessages";

  private ApErrorMessages() {
    super(BUNDLE);
  }

  @NotNull
  public static String message(@PropertyKey(resourceBundle = BUNDLE) String key, Object... params) {
    return INSTANCE.getMessage(key, params);
  }

}
