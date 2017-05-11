package net.alliedmods.lang.amxxpawn.psi.preprocessor.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.tree.ChildRoleBase;
import com.intellij.psi.tree.IElementType;

import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.psi.ApElementTypes;
import net.alliedmods.lang.amxxpawn.psi.ChildRole;
import net.alliedmods.lang.amxxpawn.psi.ElementTypes;

import org.jetbrains.annotations.Nullable;

public class IncludeStatementElement extends CompositeElement {
  private static final Logger LOG = Logger.getInstance("#net.alliedmods.lang.amxxpawn.psi.preprocessor.impl.IncludeStatementElement");

  public IncludeStatementElement() {
    super(ApElementTypes.INCLUDE_STATEMENT);
  }

  @Nullable
  @Override
  public ASTNode findChildByRole(int role) {
    LOG.assertTrue(ChildRole.isUnique(role));
    switch (role) {
      case ChildRole.HASH:
        return getFirstChildNode();
      case ChildRole.INCLUDE_KEYWORD:
        return findChildByType(ElementTypes.AMXX_INCLUDE_KEYWORDS);
      case ChildRole.FILE_REFERENCE:
        return getLastChildNode();
      default:
        return null;
    }
  }

  @Override
  public int getChildRole(ASTNode child) {
    LOG.assertTrue(child.getTreeParent() == this);
    IElementType i = child.getElementType();
    if (i == ApTokenTypes.HASH) {
      return ChildRole.HASH;
    } else if (i == ApElementTypes.FILE_REFERENCE) {
      return ChildRole.INCLUDE_REFERENCE;
    } else if (ElementTypes.AMXX_INCLUDE_KEYWORDS.contains(i)) {
      return ChildRole.INCLUDE_KEYWORD;
    }

    return ChildRoleBase.NONE;
  }
}
