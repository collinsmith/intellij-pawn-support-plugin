package net.sourcemod.sourcepawn.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public class SpTokenTypes {

  // GENERIC
  public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
  public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
  public static final IElementType NEW_LINE = new SpTokenType("EOL");

  // PREPROCESSOR DIRECTIVES
  public static final IElementType PREPROCESSOR_ASSERT = new SpTokenType("ASSERT");
  public static final IElementType PREPROCESSOR_DEFINE = new SpTokenType("DEFINE");
  public static final IElementType PREPROCESSOR_ELSE = new SpTokenType("ELSE");
  public static final IElementType PREPROCESSOR_ELSEIF = new SpTokenType("ELSEIF");
  public static final IElementType PREPROCESSOR_ENDIF = new SpTokenType("ENDIF");
  public static final IElementType PREPROCESSOR_ENDINPUT = new SpTokenType("ENDINPUT");
  public static final IElementType PREPROCESSOR_ENDSCRIPT = new SpTokenType("ENDSCRIPT");
  public static final IElementType PREPROCESSOR_ERROR = new SpTokenType("ERROR");
  public static final IElementType PREPROCESSOR_FILE = new SpTokenType("FILE");
  public static final IElementType PREPROCESSOR_IF = new SpTokenType("IF");
  public static final IElementType PREPROCESSOR_INCLUDE = new SpTokenType("INCLUDE");
  public static final IElementType PREPROCESSOR_LINE = new SpTokenType("LINE");
  public static final IElementType PREPROCESSOR_PRAGMA = new SpTokenType("PRAGMA");
  public static final IElementType PREPROCESSOR_TRYINCLUDE = new SpTokenType("TRYINCLUDE");
  public static final IElementType PREPROCESSOR_UNDEF = new SpTokenType("UNDEF");

  // INCLUDE FILE FORMATS
  public static final IElementType PREPROCESSOR_INCLUDE_SYSTEMPATH
      = new SpTokenType("-SYSTEM FILE-");
  public static final IElementType PREPROCESSOR_INCLUDE_RELATIVEPATH
      = new SpTokenType("-RELATIVE PATH-");

  // OPERATORS
  public static final IElementType HASH = new SpTokenType("HASH");

  private SpTokenTypes() {
  }

}
