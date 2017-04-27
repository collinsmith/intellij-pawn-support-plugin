package net.alliedmods.lang.amxxpawn.page;

import com.intellij.codeHighlighting.RainbowHighlighter;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.RainbowColorSettingsPage;
import com.intellij.psi.codeStyle.DisplayPriority;
import com.intellij.psi.codeStyle.DisplayPrioritySortable;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApLanguage;
import net.alliedmods.lang.amxxpawn.highlighter.ApHighlightingColors;
import net.alliedmods.lang.amxxpawn.highlighter.ApSyntaxHighlighter;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import javax.swing.*;

public class ApColorSettingsPage implements RainbowColorSettingsPage, DisplayPrioritySortable {

  private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.keyword"), ApHighlightingColors.KEYWORD),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.number"), ApHighlightingColors.NUMBER),

      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.string"), ApHighlightingColors.STRING),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.valid.escape.in.string"), ApHighlightingColors.VALID_STRING_ESCAPE),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.invalid.escape.in.string"), ApHighlightingColors.INVALID_STRING_ESCAPE),
      
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.operator.sign"), ApHighlightingColors.OPERATION_SIGN),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.parentheses"), ApHighlightingColors.PARENTHESES),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.braces"), ApHighlightingColors.BRACES),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.brackets"), ApHighlightingColors.BRACKETS),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.comma"), ApHighlightingColors.COMMA),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.semicolon"), ApHighlightingColors.SEMICOLON),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.dot"), ApHighlightingColors.DOT),
  
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.line.comment"), ApHighlightingColors.LINE_COMMENT),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.block.comment"), ApHighlightingColors.BLOCK_COMMENT),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.javadoc.comment"), ApHighlightingColors.DOC_COMMENT),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.javadoc.tag"), ApHighlightingColors.DOC_COMMENT_TAG),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.javadoc.tag.value"), ApHighlightingColors.DOC_COMMENT_TAG_VALUE),
      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.javadoc.markup"), ApHighlightingColors.DOC_COMMENT_MARKUP),

      new AttributesDescriptor(ApBundle.message("options.amxxpawn.attribute.descriptor.preprocessor"), ApHighlightingColors.PREPROCESSOR)
  };

  @NonNls
  private static final Map<String, TextAttributesKey> ourTags = RainbowHighlighter.createRainbowHLM();
  static {
    ourTags.put("javadocTagValue", ApHighlightingColors.DOC_COMMENT_TAG_VALUE);
  }

  @NotNull
  @Override
  public String getDisplayName() {
    return ApBundle.message("amxx.amxxpawn.name");
  }

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
    // TODO: Add demo text
    return "";
  }

  @Nullable
  @Override
  public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
    return ourTags;
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

  @Override
  public boolean isRainbowType(TextAttributesKey type) {
    return ApHighlightingColors.DOC_COMMENT_TAG_VALUE.equals(type);
  }

  @Nullable
  @Override
  public Language getLanguage() {
    return ApLanguage.INSTANCE;
  }

  @Override
  public DisplayPriority getPriority() {
    return DisplayPriority.KEY_LANGUAGE_SETTINGS;
  }

}
