package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElementVisitor;

import net.alliedmods.lang.amxxpawn.psi.preprocessor.*;
import net.alliedmods.lang.amxxpawn.psi.preprocessor.PsiApFileReference;

public abstract class ApElementVisitor extends PsiElementVisitor {
  public void visitApFile(PsiApFile file) {
    visitFile(file);
  }

  public void visitIncludeStatement(PsiIncludeStatement statement) {
    visitElement(statement);
  }

  public void visitReferenceElement(PsiApFileReference reference) {
    visitElement(reference);
  }
}
