package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElementVisitor;

public abstract class ApElementVisitor extends PsiElementVisitor {

  public void visitTagElement(PsiTagElement tag) {
    visitElement(tag);
  }

}
