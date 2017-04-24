package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ApElementType extends IElementType {

  public ApElementType(@NotNull @NonNls String debugName) {
    super(debugName, ApLanguage.INSTANCE);
  }

}
