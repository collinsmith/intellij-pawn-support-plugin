package net.alliedmods.lang.amxxpawn.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.impl.source.tree.FileElement;

import net.alliedmods.lang.amxxpawn.psi.ApStubElementTypes;

import org.jetbrains.annotations.Nullable;

public class ApFileElement extends FileElement {
  private static final Logger LOG = Logger.getInstance("#net.alliedmods.lang.amxxpawn.psi.impl.ApFileElement");

  public ApFileElement(CharSequence text) {
    super(ApStubElementTypes.AP_FILE, text);
  }

  @Nullable
  @Override
  public ASTNode findChildByRole(int role) {
    // TODO: custom return globals, methods, etc?
    return super.findChildByRole(role);
  }

  @Override
  public int getChildRole(ASTNode child) {
    // TODO: custom return globals, methods, etc?
    return super.getChildRole(child);
  }
}
