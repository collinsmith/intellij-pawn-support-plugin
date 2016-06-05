package net.alliedmods.intellij.sourcepawn.file;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import com.intellij.openapi.util.text.StringUtil;

import org.jetbrains.annotations.NotNull;

public class SourcePawnScriptFileTypeFactory extends FileTypeFactory {


  @Override
  public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
    String exts = StringUtil.join(SourcePawnScriptFileType.EXTS,
        FileTypeConsumer.EXTENSION_DELIMITER);
    fileTypeConsumer.consume(SourcePawnScriptFileType.INSTANCE, exts);
  }

}
