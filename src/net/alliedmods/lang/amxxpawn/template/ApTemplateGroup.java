package net.alliedmods.lang.amxxpawn.template;

import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor;
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;
import net.alliedmods.lang.amxxpawn.ApSupport;

class ApTemplateGroup implements FileTemplateGroupDescriptorFactory {

  @Override
  public FileTemplateGroupDescriptor getFileTemplatesDescriptor() {
    FileTemplateGroupDescriptor descriptor
        = new FileTemplateGroupDescriptor(ApBundle.message("amxx.name"), ApIcons.Amxx16);
    descriptor.addTemplate(ApSupport.PLUGIN_TEMPLATE);
    descriptor.addTemplate(ApSupport.INCLUDE_TEMPLATE);
    return descriptor;
  }

}
