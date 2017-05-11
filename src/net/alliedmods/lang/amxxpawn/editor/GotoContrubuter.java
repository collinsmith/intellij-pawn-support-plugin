package net.alliedmods.lang.amxxpawn.editor;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;

import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.annotations.NotNull;

public class GotoContrubuter implements ChooseByNameContributor {

  @NotNull
  @Override
  public String[] getNames(Project project, boolean includeNonProjectItems) {
    return ArrayUtils.EMPTY_STRING_ARRAY;
  }

  @NotNull
  @Override
  public NavigationItem[] getItemsByName(String name, String pattern, Project project,
                                         boolean includeNonProjectItems) {
    return NavigationItem.EMPTY_NAVIGATION_ITEM_ARRAY;
  }

}
