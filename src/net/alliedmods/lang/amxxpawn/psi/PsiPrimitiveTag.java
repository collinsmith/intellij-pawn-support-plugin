package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.search.GlobalSearchScope;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Represents primitive tags of AMXX Pawn, namely the {@code any} tag.
 */
public class PsiPrimitiveTag extends PsiTag.Stub {
  private final String name;

  PsiPrimitiveTag(@NotNull @NonNls String name) {
    this.name = name;
  }

  @NotNull
  @Override
  public String getPresentableText() {
    return name;
  }

  /**
   * {@inheritDoc}
   *
   * Always returns {@code true}.
   */
  @Override
  public boolean isValid() {
    return true;
  }

  @Override
  public boolean equalsToText(@NotNull String text) {
    return name.equals(text);
  }

  @Override
  public <A> A accept(@NotNull PsiTagVisitor<A> visitor) {
    return visitor.visitTag(this);
  }

  @NotNull
  @Override
  public GlobalSearchScope getResolveScope() {
    return null;
  }

  @NotNull
  @Override
  public PsiTag[] getSuperTags() {
    return EMPTY_ARRAY;
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof PsiPrimitiveTag && name.equals(((PsiPrimitiveTag) obj).name);
  }
}
