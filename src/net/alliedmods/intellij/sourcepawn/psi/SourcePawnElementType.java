package net.alliedmods.intellij.sourcepawn.psi;

import com.intellij.psi.tree.IElementType;

import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SourcePawnElementType extends IElementType {

  public SourcePawnElementType(@NotNull @NonNls String debugName) {
    super(debugName, SourcePawnLanguage.INSTANCE);
  }

}
