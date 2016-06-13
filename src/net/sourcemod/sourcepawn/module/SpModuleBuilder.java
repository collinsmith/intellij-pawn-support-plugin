package net.sourcemod.sourcepawn.module;

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

import net.sourcemod.sourcepawn.file.SpScript;
import net.sourcemod.sourcepawn.sdk.SpSdkUtils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SpModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {

  private Sdk sdk;
  private Path root;
  private List<Pair<String, String>> sources;

  @Override
  public void setupRootModel(ModifiableRootModel modifiableRootModel) throws ConfigurationException {
    if (sdk == null) {
      List<Sdk> sdks = SpSdkUtils.getSpSdks();
      if (!sdks.isEmpty()) {
        sdk = sdks.get(0);
        modifiableRootModel.setSdk(sdk);
      }
    }

    VirtualFile directory = sdk.getHomeDirectory();
    String rootPath = getContentEntryPath();
    if (rootPath != null) {
      LocalFileSystem lfs = LocalFileSystem.getInstance();
      VirtualFile root = lfs.refreshAndFindFileByPath(FileUtil.toSystemIndependentName(rootPath));
      if (root != null) {
        ContentEntry entry = modifiableRootModel.addContentEntry(root);
        if (sources != null) {
          for (Pair<String, String> p : sources) {
            VirtualFile sourceDir = VfsUtil.findRelativeFile(p.getFirst(), null);
            if (sourceDir != null) {
              entry.addSourceFolder(sourceDir, false);
              if (entry != null) {
                String name = modifiableRootModel.getModule().getName();
                name += SpScript.getInstance().getDefaultExtension();
                TemplateUtils.createOrResetFileContentFromTemplate(sourceDir, name, "Empty Plugin");
              }
            }
          }
        }
      }
    }
  }

  @Override
  public ModuleType getModuleType() {
    return SpModule.getInstance();
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
