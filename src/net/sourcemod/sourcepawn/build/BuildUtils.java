package net.sourcemod.sourcepawn.build;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;

import org.jetbrains.annotations.NonNls;

import static net.sourcemod.sourcepawn.build.old.BuildState.invokeOnEdtSynchronously;

public class BuildUtils {

  public static void saveDocuments() {
    invokeOnEdtSynchronously(new Runnable() {
      @Override
      public void run() {
        FileDocumentManager.getInstance().saveAllDocuments();
      }
    });
  }

  public static void saveAll() {
    invokeOnEdtSynchronously(new Runnable() {
      @Override
      public void run() {
        ApplicationManager.getApplication().saveAll();
      }
    });
  }

  public static String quote(String str, boolean addQuotes) {
    @NonNls
    StringBuilder buffer = new StringBuilder(str.length() + (addQuotes ? 2 : 0));
    if (addQuotes) {
      buffer.append('"');
    }

    for (int i = 0; i < str.length(); ++i) {
      final int ch = str.codePointAt(i);
      if (ch == '\\' || ch == '\"' || ch == '\'' || ch == '[' || ch == ']' || ch == '$') {
        buffer.append('\\');
      } else if (ch == '\r' || ch == '\n' || ch == '\t') {
        buffer.append(ch == '\r' ? "\\r" : ch == '\n' ? "\\n" : "\\t");
        continue;
      }

      buffer.append(ch);
    }

    if (addQuotes) {
      buffer.append('"');
    }

    return buffer.toString();
  }

  public static String quote(String str) {
    return quote(str, true);
  }

  private BuildUtils() {
  }

}
