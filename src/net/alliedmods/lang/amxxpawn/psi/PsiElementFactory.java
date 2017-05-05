package net.alliedmods.lang.amxxpawn.psi;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

public interface PsiElementFactory extends ApElementFactory {
  class SERVICE {
    private SERVICE() {
    }

    public static PsiElementFactory getInstance(Project project) {
      return ServiceManager.getService(project, PsiElementFactory.class);
    }
  }
}
