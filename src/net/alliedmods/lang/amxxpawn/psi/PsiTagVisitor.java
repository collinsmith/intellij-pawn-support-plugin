package net.alliedmods.lang.amxxpawn.psi;

import org.jetbrains.annotations.Nullable;

/**
 * Visitor which can be used to visit AMXX Pawn tags.
 */
public class PsiTagVisitor<A> {

  @Nullable
  public A visitTag(PsiTag tag) {
    return null;
  }

  @Nullable
  public A visitPrimitiveTag(PsiPrimitiveTag primitiveTag) {
    return visitTag(primitiveTag);
  }

}
