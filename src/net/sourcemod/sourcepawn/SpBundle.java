package net.sourcemod.sourcepawn;

import com.intellij.CommonBundle;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

public class SpBundle {

  private static Reference<ResourceBundle> spBundle;

  @NotNull
  @NonNls
  public static final String BASE_NAME = "net.sourcemod.sourcepawn.SpBundle";

  public static String message(@NonNls @PropertyKey(resourceBundle = BASE_NAME) String key,
                               Object... params) {
    return CommonBundle.message(getBundle(), key, params);
  }

  private static ResourceBundle getBundle() {
    ResourceBundle bundle = null;
    if (spBundle != null) {
      bundle = spBundle.get();
    }

    if (bundle == null) {
      bundle = ResourceBundle.getBundle(BASE_NAME);
      spBundle = new SoftReference<>(bundle);
    }

    return bundle;
  }

}
