package net.alliedmods.lang.amxxpawn.page;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApSyntaxHighlighter;
import net.alliedmods.lang.amxxpawn.template.TemplateUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Map;

import javax.swing.*;

public class ApColorSettingsPage implements ColorSettingsPage {

  private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
      new AttributesDescriptor(ApBundle.message("amxx.syntax.keyword"), ApSyntaxHighlighter.KEYWORD),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.operators"), ApSyntaxHighlighter.OPERATORS),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.parenthesis"), ApSyntaxHighlighter.PARENTHESES),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.brackets"), ApSyntaxHighlighter.BRACKETS),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.braces"), ApSyntaxHighlighter.BRACES),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.line_comments"), ApSyntaxHighlighter.LINE_COMMENT),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.block_comments"), ApSyntaxHighlighter.BLOCK_COMMENT),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.doc_comments"), ApSyntaxHighlighter.DOC_COMMENT),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.semicolon"), ApSyntaxHighlighter.SEMICOLON),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.comma"), ApSyntaxHighlighter.COMMA),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.number"), ApSyntaxHighlighter.NUMBER),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.string"), ApSyntaxHighlighter.STRING),
      new AttributesDescriptor(ApBundle.message("amxx.syntax.pre_keyword"), ApSyntaxHighlighter.PRE_KEYWORD)
  };

  @Nullable
  @Override
  public Icon getIcon() {
    return ApIcons.Amxx16;
  }

  @NotNull
  @Override
  public SyntaxHighlighter getHighlighter() {
    return new ApSyntaxHighlighter();
  }

  @NotNull
  @Override
  public String getDemoText() {
    try {
      return TemplateUtils.getTemplateText("AMXModX Demo.sma");
    } catch (IOException e) {
      return "";
    }
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return null;
  }

  @NotNull
  @Override
  public AttributesDescriptor[] getAttributeDescriptors() {
    return DESCRIPTORS;
  }

  @NotNull
  @Override
  public ColorDescriptor[] getColorDescriptors() {
    return ColorDescriptor.EMPTY_ARRAY;
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return ApBundle.message("amxx.name");
  }

}
