package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.ILightStubElementType;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.ICompositeElementType;

import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.psi.stubs.PsiApFileStub;
import net.alliedmods.lang.amxxpawn.psi.stubs.StubPsiFactory;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public abstract class ApStubElementType<StubT extends StubElement, PsiT extends PsiElement>
    extends ILightStubElementType<StubT, PsiT>
    implements ICompositeElementType {
  private final boolean leftBound;

  protected ApStubElementType(@NotNull @NonNls String debugName) {
    this(debugName, false);
  }

  protected ApStubElementType(@NotNull @NonNls String debugName, boolean leftBound) {
    super(debugName, ApLanguage.INSTANCE);
    this.leftBound = leftBound;
  }

  @Override
  public boolean isLeftBound() {
    return leftBound;
  }

  @NotNull
  @Override
  public String getExternalId() {
    return "amxxpawn." + toString();
  }

  protected StubPsiFactory getPsiFactory(StubT stub) {
    return getFileStub(stub).getPsiFactory();
  }

  private PsiApFileStub getFileStub(StubT stub) {
    StubElement parent = stub;
    while (!(parent instanceof PsiFileStub)) {
      parent = parent.getParentStub();
    }

    return (PsiApFileStub) parent;
  }

  public abstract PsiT createPsi(@NotNull ASTNode node);

  @NotNull
  @Override
  public StubT createStub(@NotNull PsiT psi, StubElement stubElement) {
    throw new UnsupportedOperationException("Should not be called. " +
        "Element=" + psi + "; class" + psi.getClass() + "; " +
        "file=" + (psi.isValid() ? psi.getContainingFile() : "-"));
  }
}
