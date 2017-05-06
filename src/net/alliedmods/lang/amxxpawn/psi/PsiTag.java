package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ArrayFactory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PsiTag extends Cloneable {

  @SuppressWarnings("StaticInitializerReferencesSubClass")
  PsiPrimitiveTag ANY = new PsiPrimitiveTag("any");


  PsiTag[] EMPTY_ARRAY = new PsiTag[0];
  ArrayFactory<PsiTag> ARRAY_FACTORY = count -> count == 0 ? EMPTY_ARRAY : new PsiTag[count];

  @NotNull
  static PsiTag[] createArray(int count) {
    return ARRAY_FACTORY.create(count);
  }

  /**
   * Returns the text of a tag that can be presented to a user.
   */
  @NotNull String getPresentableText();

  /**
   * Indicates whether or not the tag is currently valid.
   *
   * @see PsiElement#isValid()
   */
  boolean isValid();

  /**
   * Indicates whether or not the specified string is equivalent to the identifier of this tag.
   */
  boolean equalsToText(@NotNull String text);

  /**
   * Returns a list of super tags for a tag, or an empty array if the tag has none. This is intended
   * to provide some inheritance for the {@code any} tag.
   */
  @NotNull PsiTag[] getSuperTags();

  /**
   * Passes the tag to the specified visitor.
   */
  <A> A accept(@NotNull PsiTagVisitor<A> visitor);

  /**
   * Returns the scope in which the reference to the underlying file of a tag is searched, or
   * {@code null} if the type is a primitive.
   */
  @Nullable GlobalSearchScope getResolveScope();

  abstract class Stub implements PsiTag {
    @Override @NotNull public abstract String getPresentableText();
  }

}
