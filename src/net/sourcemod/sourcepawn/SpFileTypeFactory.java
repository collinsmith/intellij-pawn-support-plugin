package net.sourcemod.sourcepawn;

import com.intellij.openapi.fileTypes.ExtensionFileNameMatcher;
import com.intellij.openapi.fileTypes.FileNameMatcher;
import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;

import net.sourcemod.sourcepawn.file.SpInclude;
import net.sourcemod.sourcepawn.file.SpScript;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class SpFileTypeFactory extends FileTypeFactory {

  @Override
  public void createFileTypes(@NotNull FileTypeConsumer consumer) {
    consumer.consume(SpScript.getInstance(),
        createFileNameMatchersForExtensions(SpSupport.getScriptExtensions()));
    consumer.consume(SpInclude.getInstance(),
        createFileNameMatchersForExtensions(SpSupport.getIncludeExtensions()));
  }

  private FileNameMatcher[] createFileNameMatchersForExtensions(Set<String> extensions) {
    Collection<FileNameMatcher> fileNameMatchers = new ArrayList<>(extensions.size());
    for (String extension : extensions) {
      fileNameMatchers.add(new ExtensionFileNameMatcher(extension));
    }

    return fileNameMatchers.toArray(new FileNameMatcher[0]);
  }
}
