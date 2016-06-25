package net.sourcemod.sourcepawn.lexer;

import com.intellij.psi.tree.IElementType;

import net.sourcemod.sourcepawn.SpTokenType;

public class SpPreprocessorTokenTypes {

  public static final IElementType ASSERT = new SpTokenType("ASSERT");
  public static final IElementType DEFINE = new SpTokenType("DEFINE");
  public static final IElementType ELSE = new SpTokenType("ELSE");
  public static final IElementType ELSEIF = new SpTokenType("ELSEIF");
  public static final IElementType ENDIF = new SpTokenType("ENDIF");
  public static final IElementType ENDINPUT = new SpTokenType("ENDINPUT");
  public static final IElementType ENDSCRIPT = new SpTokenType("ENDSCRIPT");
  public static final IElementType ERROR = new SpTokenType("ERROR");
  public static final IElementType FILE = new SpTokenType("FILE");
  public static final IElementType IF = new SpTokenType("IF");
  public static final IElementType INCLUDE = new SpTokenType("INCLUDE");
  public static final IElementType LINE = new SpTokenType("LINE");
  public static final IElementType PRAGMA = new SpTokenType("PRAGMA");
  public static final IElementType TRYINCLUDE = new SpTokenType("TRYINCLUDE");
  public static final IElementType UNDEF = new SpTokenType("UNDEF");

  public static final IElementType PATTERN_DEFINITION = new SpTokenType("UNDEF");

  private SpPreprocessorTokenTypes() {
  }

}
