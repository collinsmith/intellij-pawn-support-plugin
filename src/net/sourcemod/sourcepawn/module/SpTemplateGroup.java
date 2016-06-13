package net.sourcemod.sourcepawn.module;

import com.intellij.ide.fileTemplates.FileTemplateDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

import net.sourcemod.sourcepawn.SpBundle;
import net.sourcemod.sourcepawn.SpIcons;

class SpTemplateGroup implements FileTemplateGroupDescriptorFactory {

  @Override
  public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
    FileTemplateGroupDescriptor descriptor
        = new FileTemplateGroupDescriptor(SpBundle.message("sp"), SpIcons.SOURCEPAWN16);
    descriptor.addTemplate(
        new FileTemplateDescriptor("SourcePawn Plugin.sp"));
    descriptor.addTemplate(
        new FileTemplateDescriptor("SourcePawn Include.inc"));
    return descriptor;
  }
}
