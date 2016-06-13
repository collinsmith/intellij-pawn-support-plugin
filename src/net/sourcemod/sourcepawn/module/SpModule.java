package net.sourcemod.sourcepawn.module;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.ModuleTypeManager;

import net.sourcemod.sourcepawn.SpBundle;
import net.sourcemod.sourcepawn.SpIcons;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SpModule extends ModuleType<SpModuleBuilder> implements ApplicationComponent {

  @NotNull
  public static SpModule getInstance() {
    return ApplicationManager.getApplication().getComponent(SpModule.class);
  }

  public SpModule() {
    super("SourceMod");
  }

  @Override
  public void initComponent() {
    ModuleTypeManager.getInstance().registerModuleType(this);
  }

  @Override
  public void disposeComponent() {

  }

  @NotNull
  @Override
  public String getComponentName() {
    return "SourcePawn.ModuleType";
  }

  @NotNull
  @Override
  public SpModuleBuilder createModuleBuilder() {
    return new SpModuleBuilder();
  }

  @NotNull
  @Override
  public String getName() {
    return SpBundle.message("sp.module");
  }

  @NotNull
  @Override
  public String getDescription() {
    return SpBundle.message("sp.module.description");
  }

  @Override
  public Icon getBigIcon() {
    return SpIcons.SOURCEPAWN16;
  }

  @Override
  public Icon getNodeIcon(@Deprecated boolean isOpened) {
    return SpIcons.SOURCEPAWN16;
  }
}
