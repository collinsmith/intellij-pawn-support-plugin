package net.alliedmods.lang.amxxpawn;

import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ApFileTypeFactory extends FileTypeFactory {
  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    fileTypeConsumer.consume(ApFileType.INSTANCE,
        createFileNameMatchersForExtensions(ApSupport.getExtensions()));
  }

  private FileNameMatcher[] createFileNameMatchersForExtensions(Set<String> extensions) {
    Collection<FileNameMatcher> fileNameMatchers = new ArrayList<>(extensions.size());
    for (String extension : extensions) {
      fileNameMatchers.add(new ExtensionFileNameMatcher(extension));
    }

    return fileNameMatchers.toArray(new FileNameMatcher[fileNameMatchers.size()]);
  }
}
