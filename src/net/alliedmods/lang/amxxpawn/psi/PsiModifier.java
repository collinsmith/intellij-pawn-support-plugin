package net.alliedmods.lang.amxxpawn.psi;

import org.intellij.lang.annotations.MagicConstant;

/**
 * Provides a list of possible modifier keywords for AMXX Pawn variables or functions.
 */
public interface PsiModifier {
  String NEW = "new";
  String CONST = "const";
  String PUBLIC = "public";
  String STATIC = "static";
  String STOCK = "stock";

  String FORWARD = "forward";
  String NATIVE = "native";

  String[] MODIFIERS = {NEW, CONST, PUBLIC, STATIC, STOCK, FORWARD, NATIVE };

  @MagicConstant(stringValues = { NEW, CONST, PUBLIC, STATIC, STOCK, FORWARD, NATIVE })
  @interface ModifierConstant {}

}
