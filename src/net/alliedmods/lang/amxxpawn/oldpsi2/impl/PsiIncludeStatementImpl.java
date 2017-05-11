package net.alliedmods.lang.amxxpawn.oldpsi2.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ArrayFactory;

import net.alliedmods.lang.amxxpawn.oldpsi2.ApElementVisitor;
import net.alliedmods.lang.amxxpawn.oldpsi2.reference.ApFileReferenceManager;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.ApStubElementTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.stubs.PsiIncludeStatementStub;
import net.alliedmods.lang.amxxpawn.lexer.ApTokenTypes;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiApFile;
import net.alliedmods.lang.amxxpawn.oldpsi2.PsiIncludeStatement;
import net.alliedmods.lang.amxxpawn.oldpsi2.reference.ApFileNameEvaluator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.annotation.Nullable;

public class PsiIncludeStatementImpl extends PsiIncludeStatementBaseImpl implements PsiIncludeStatement {
  public static final PsiIncludeStatementImpl[] EMPTY_ARRAY = new PsiIncludeStatementImpl[0];
  public static final ArrayFactory<PsiIncludeStatementImpl> ARRAY_FACTORY = i -> i == 0 ? EMPTY_ARRAY : new PsiIncludeStatementImpl[i];

  public PsiIncludeStatementImpl(PsiIncludeStatementStub stub) {
    super(stub, ApStubElementTypes.INCLUDE_STATEMENT);
  }

  public PsiIncludeStatementImpl(ASTNode node) {
    super(node);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof ApElementVisitor) {
      ((ApElementVisitor) visitor).visitIncludeStatement(this);
    } else {
      visitor.visitElement(this);
    }
  }

  @Override
  public String toString() {
    return "PsiIncludeStatement";
  }

  @Nullable
  @Override
  public PsiApFile getReferencedFile() {
    PsiIncludeStatementStub stub = getStub();
    if (stub != null) {
      return stub.getReferencedFile();
    }

    // TODO: Clean this up
    PsiElement include = findChildByType(ApTokenTypes.INCLUDE_PATH);
    if (include == null) {
      return null;
    }

    String text = include.getText();
    if (text.charAt(0) != '<' || text.charAt(text.length() - 1) != '>') {
      return null;
    }

    text = text.substring(1, text.length() - 1).trim();
    Module module = ModuleUtilCore.findModuleForPsiElement(this);
    if (module == null) {
      return null;
    }

    GlobalSearchScope scope = GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
    List<PsiApFile> files = ApFileReferenceManager.getInstance(getProject())
        .findApFiles(scope, text, ApFileNameEvaluator.DEFAULT);
    if (files.isEmpty()) {
      return null;
    }

    return files.get(0);
  }
}
