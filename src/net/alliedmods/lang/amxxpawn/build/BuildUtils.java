package net.alliedmods.lang.amxxpawn.build;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.fileEditor.FileDocumentManager;

import org.jetbrains.annotations.NonNls;

public class BuildUtils {

  private BuildUtils() {}

  public static void invokeOnEdtSynchronously(Runnable saveRunnable) {
    Application application = ApplicationManager.getApplication();
    if (application.isDispatchThread()) {
      saveRunnable.run();
    } else {
      application.invokeAndWait(saveRunnable, ModalityState.defaultModalityState());
    }
  }

  public static void saveDocuments() {
    invokeOnEdtSynchronously(() -> FileDocumentManager.getInstance().saveAllDocuments());
  }

  public static void saveAll() {
    invokeOnEdtSynchronously(() -> ApplicationManager.getApplication().saveAll());
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

}
