package net.alliedmods.lang.amxxpawn;

import com.intellij.CommonBundle;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

public class ApBundle {

  private ApBundle() {}

  @Nullable
  private static Reference<ResourceBundle> apBundle;

  @NotNull
  @NonNls
  private static final String BUNDLE = "net.alliedmods.lang.amxxpawn.i18n";

  public static String message(@NotNull @NonNls @PropertyKey(resourceBundle = BUNDLE) String key,
                               @NotNull @NonNls Object... params) {
    return CommonBundle.message(getBundle(), key, params);
  }

  // Method copied from IntelliJ
  @NotNull
  private static ResourceBundle getBundle() {
    ResourceBundle bundle = com.intellij.reference.SoftReference.dereference(apBundle);
    if (bundle == null) {
      bundle = ResourceBundle.getBundle(BUNDLE);
      apBundle = new SoftReference<>(bundle);
    }

    return bundle;
  }

}
