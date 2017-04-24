package net.alliedmods.lang.amxxpawn.module;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;

import net.alliedmods.lang.amxxpawn.ApBundle;
import net.alliedmods.lang.amxxpawn.ApIcons;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

// TODO: All the icons could be changed to better to match the intended sizes
public class ApModuleType extends ModuleType<ApModuleBuilder> implements ApplicationComponent {

  @NotNull
  public static ApModuleType getInstance() {
    return ApplicationManager.getApplication().getComponent(ApModuleType.class);
  }

  public ApModuleType() {
    super("AmxxModule");
  }

  @Override
  public void initComponent() {
    ModuleTypeManager.getInstance().registerModuleType(this);
  }

  @Override
  public void disposeComponent() {}

  @NotNull
  @Override
  public String getComponentName() {
    return "AmxxPawn.AmxxModule";
  }

  @NotNull
  @Override
  public ApModuleBuilder createModuleBuilder() {
    return new ApModuleBuilder();
  }

  @NotNull
  @Override
  public String getName() {
    return ApBundle.message("amxx.module");
  }

  @NotNull
  @Override
  public String getDescription() {
    return ApBundle.message("amxx.module.desc");
  }

  @Override
  public Icon getBigIcon() {
    return ApIcons.Amxx16;
  }

  @Override
  public Icon getNodeIcon(@Deprecated boolean isOpened) {
    return ApIcons.Amxx16;
  }

}
