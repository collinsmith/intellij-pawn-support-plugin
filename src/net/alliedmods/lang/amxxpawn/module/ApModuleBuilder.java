package net.alliedmods.lang.amxxpawn.module;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;

import net.alliedmods.lang.amxxpawn.ApSupport;
import net.alliedmods.lang.amxxpawn.ApFileType;
import net.alliedmods.lang.amxxpawn.sdk.ApSdkUtils;
import net.alliedmods.lang.amxxpawn.template.TemplateUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ApModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {

  private Sdk sdk;
  private Path root;
  private List<Pair<String, String>> sources;

  @Override
  public void setupRootModel(ModifiableRootModel rootModel) throws ConfigurationException {
    if (sdk == null) {
      List<Sdk> sdks = ApSdkUtils.getApSdks();
      if (!sdks.isEmpty()) {
        sdk = sdks.get(0);
        rootModel.setSdk(sdk);
      }
    }

    String rootPath = getContentEntryPath();
    if (rootPath != null) {
      LocalFileSystem lfs = LocalFileSystem.getInstance();
      VirtualFile root = lfs.refreshAndFindFileByPath(FileUtil.toSystemIndependentName(rootPath));
      if (root != null) {
        ContentEntry entry = rootModel.addContentEntry(root);
        VirtualFile src = null, res = null, build = null;
        try {
          src = VfsUtil.createDirectoryIfMissing(root, "src");
          res = VfsUtil.createDirectoryIfMissing(root, "res");
          build = VfsUtil.createDirectoryIfMissing(root, "build");
        } catch (IOException e) {
          PluginManager.getLogger().error(e);
        }

        if (src != null) {
          addSourcePath(Pair.create(src.getPath(), ""));
        }

        if (sources != null) {
          for (Pair<String, String> p : sources) {
            VirtualFile sourceDir = VfsUtil.findRelativeFile(p.first, null);
            if (sourceDir != null) {
              entry.addSourceFolder(sourceDir, false);
              String name = rootModel.getModule().getName() + "." + ApFileType.INSTANCE.getDefaultExtension();
              TemplateUtils.createOrResetFileContentFromTemplate(sourceDir, name, ApSupport.PLUGIN_TEMPLATE);
            }
          }
        }
      }
    }
  }

  @NotNull
  @Override
  public ModuleType getModuleType() {
    return ApModuleType.getInstance();
  }

  @Override
  public List<Pair<String, String>> getSourcePaths() throws ConfigurationException {
    return sources;
  }

  @Override
  public void setSourcePaths(List<Pair<String, String>> sourcePaths) {
    this.sources = sourcePaths;
  }

  @Override
  public void addSourcePath(Pair<String, String> sourcePathInfo) {
    if (sources == null) {
      sources = new ArrayList<>();
    }

    sources.add(sourcePathInfo);
  }

}
