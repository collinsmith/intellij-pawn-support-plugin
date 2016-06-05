// This is a generated file. Not intended for manual editing.
package net.alliedmods.intellij.sourcepawn.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static net.alliedmods.intellij.sourcepawn.psi.SourcePawnTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import net.alliedmods.intellij.sourcepawn.psi.*;

public class SourcePawnPragmaDirectiveImpl extends ASTWrapperPsiElement implements SourcePawnPragmaDirective {

  public SourcePawnPragmaDirectiveImpl(ASTNode node) {
    super(node);
  }

  public void accept(@NotNull SourcePawnVisitor visitor) {
    visitor.visitPragmaDirective(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof SourcePawnVisitor) accept((SourcePawnVisitor)visitor);
    else super.accept(visitor);
  }

}
