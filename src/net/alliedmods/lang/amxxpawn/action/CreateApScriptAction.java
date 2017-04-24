package net.alliedmods.lang.amxxpawn.action;

import com.intellij.ide.actions.CreateFileAction;
import com.intellij.ide.actions.CreateFileFromTemplateAction;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.ide.fileTemplates.FileTemplateUtil;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.InputValidatorEx;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;

import net.alliedmods.lang.amxxpawn.ApIcons;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class CreateApScriptAction extends CreateFileFromTemplateAction implements DumbAware {

  private static final String NEW_PLUGIN = "New AMXModX Plugin";

  private static final String EMPTY_PLUGIN_TEMPLATE = "AMXModX Plugin";

  public CreateApScriptAction() {
    super(NEW_PLUGIN, "", ApIcons.AmxxScript16);
  }

  @Override
  protected void buildDialog(Project project, PsiDirectory psiDirectory, CreateFileFromTemplateDialog.Builder builder) {
    builder.setTitle(NEW_PLUGIN)
        .addKind("Empty Plugin", ApIcons.AmxxScript16, EMPTY_PLUGIN_TEMPLATE)
        .setValidator(ClassNameValidator.INSTANCE);
  }

  @Override
  protected PsiFile createFileFromTemplate(String name, FileTemplate template, PsiDirectory dir) {
    final String validName = name;

    final List<String> pathParts = StringUtil.split(validName, ".");
    // Create any intermediate subdirectories.
    PsiDirectory subDir = dir;
    for (int i = 0; i < pathParts.size() - 1; ++i) {
      subDir = subDir.createSubdirectory(pathParts.get(i));
    }
    String moduleName = pathParts.get(pathParts.size() - 1);
    return createFileFromTemplate(moduleName, template, subDir, getDefaultTemplateProperty());
  }

  public static @Nullable
  VirtualFile getSourceRoot(Project project, VirtualFile file) {
    if (project == null || file == null) {
      return null;
    }
    VirtualFile[] roots = ProjectRootManager.getInstance(project).getContentSourceRoots();
    for (VirtualFile root : roots) {
      final String rootPath = root.getCanonicalPath();
      if (rootPath == null) {
        continue;
      }
      VirtualFile directory = file;
      while (directory != null) {
        if (rootPath.equals(directory.getCanonicalPath())) {
          return directory;
        }
        directory = directory.getParent();
      }
    }
    return null;
  }

  public static @Nullable
  List<String> getPathFromSourceRoot(Project project, VirtualFile file) {
    VirtualFile root = getSourceRoot(project, file);
    final String rootPath;
    if (root == null || (rootPath = root.getCanonicalPath()) == null) {
      return null;
    }
    ArrayList<String> result = new ArrayList<String>(0);
    VirtualFile directory = file;
    while (directory != null) {
      if (rootPath.equals(directory.getCanonicalPath())) {
        return result;
      }
      result.add(0, directory.getName());
      directory = directory.getParent();
    }
    return null;
  }

  @SuppressWarnings("DialogTitleCapitalization")
  @Nullable
  private PsiFile createFileFromTemplate(@SuppressWarnings("NullableProblems") @NotNull String name,
                                         @NotNull FileTemplate template,
                                         @NotNull PsiDirectory dir,
                                         @Nullable String defaultTemplateProperty) {
    // TODO: Do we *have* to hack the IntelliJ source?
    // This is a roughly a copy/paste then slight adaptation from the IntelliJ definition of this method.
    // We can't override it directly, and more importantly we can't override its call to
    // FileTemplateUtil.createFromTemplate()
    List<String> pathItems = getPathFromSourceRoot(dir.getProject(), dir.getVirtualFile());
    // modulePrefix is the empty string if the module is either in the top
    // level directory or one of the subdirectories start with a lower-case
    // letter.
    final String modulePrefix = pathItems == null || pathItems.parallelStream().anyMatch(String::isEmpty) ? "" : StringUtil.join(pathItems, ".");


    // Adapted from super definition.
    CreateFileAction.MkDirs mkdirs = new CreateFileAction.MkDirs(name, dir);
    name = mkdirs.newName;
    dir = mkdirs.directory;

    final Project project = dir.getProject();

    try {
      // Patch props with custom property.
      final Properties props = FileTemplateManager.getInstance(project).getDefaultProperties();
      props.setProperty("DLANGUAGE_MODULE_NAME", modulePrefix.isEmpty() ? name : String.format("%s.%s", modulePrefix, name));
      props.setProperty("DLANGUAGE_CLASS_NAME", name);

      final PsiElement element = FileTemplateUtil.createFromTemplate(template, name, props, dir);

      final PsiFile psiFile = element.getContainingFile();

      final VirtualFile virtualFile = psiFile.getVirtualFile();
      if (virtualFile != null) {
        FileEditorManager.getInstance(project).openFile(virtualFile, true);
        if (defaultTemplateProperty != null) {
          PropertiesComponent.getInstance(project).setValue(defaultTemplateProperty, template.getName());
        }
        return psiFile;
      }
    } catch (ParseException e) {
      Messages.showErrorDialog(project, String.format("Error parsing Velocity template: %s", e.getMessage()), "Create File from Template");
    } catch (IncorrectOperationException e) {
      LOG.error(e);
      throw e;
    } catch (Exception e) {
      LOG.error(e);
    }

    return null;
  }

  @Override
  protected String getActionName(PsiDirectory directory, String newName, String templateName) {
    return NEW_PLUGIN;
  }

  public enum ClassNameValidator implements InputValidatorEx {
    INSTANCE;

    // TODO: Update this to the proper pawn file name spec
    private final Pattern VALID_MODULE_NAME_REGEX = Pattern.compile("[A-z_.]+");

    @Nullable
    @Override
    public String getErrorText(final String inputString) {
      if (inputString.isEmpty()) {
        return null;
      }

      if (VALID_MODULE_NAME_REGEX.matcher(inputString).matches()) {
        return null;
      }

      return String.format("\'%s\' is not a valid AMXModX script name.", inputString);
    }

    @Override
    public boolean checkInput(String inputString) {
      return true;
    }

    @Override
    public boolean canClose(String inputString) {
      return getErrorText(inputString) == null;
    }
  }
}
