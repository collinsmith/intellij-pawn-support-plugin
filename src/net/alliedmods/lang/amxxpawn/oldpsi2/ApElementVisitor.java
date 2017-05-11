package net.alliedmods.lang.amxxpawn.oldpsi2;

import com.intellij.psi.PsiElementVisitor;

public abstract class ApElementVisitor extends PsiElementVisitor {

  public void visitApFile(PsiApFile file) {
    visitFile(file);
  }

  public void visitIncludeStatement(PsiIncludeStatement includeStatement) {
    visitElement(includeStatement);
  }

  public void visitRelativeIncludeStatement(PsiRelativeIncludeStatement includeStatement) {
    visitElement(includeStatement);
  }

}
