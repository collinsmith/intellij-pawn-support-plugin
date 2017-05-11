package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.tree.IStubFileElementType;

import net.alliedmods.lang.amxxpawn.psi.impl.ApFileElementType;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.ApPreprocessorStubElementTypes;

public interface ApStubElementTypes extends ApPreprocessorStubElementTypes {
  IStubFileElementType AP_FILE = new ApFileElementType();
}
