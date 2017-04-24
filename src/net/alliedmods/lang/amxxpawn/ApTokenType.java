package net.alliedmods.lang.amxxpawn;

import com.intellij.psi.tree.IElementType;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ApTokenType extends IElementType {

  public ApTokenType(@NotNull @NonNls String debugName) {
    super(debugName, ApLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "." + super.toString();
  }

}
