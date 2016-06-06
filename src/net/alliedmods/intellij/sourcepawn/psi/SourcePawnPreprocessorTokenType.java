package net.alliedmods.intellij.sourcepawn.psi;

import com.intellij.psi.tree.ILazyParseableElementType;

import net.alliedmods.intellij.sourcepawn.SourcePawnLanguage;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class SourcePawnPreprocessorTokenType extends ILazyParseableElementType {

  public SourcePawnPreprocessorTokenType(@NotNull @NonNls String debugName) {
    super(debugName, SourcePawnLanguage.INSTANCE);
  }

  @Override
  public String toString() {
    return SourcePawnPreprocessorTokenType.class.getSimpleName() + "." + super.toString();
  }

}
