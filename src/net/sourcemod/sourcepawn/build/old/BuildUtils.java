package net.sourcemod.sourcepawn.build.old;

import com.google.common.base.Strings;

import com.intellij.openapi.util.text.StringUtil;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Deprecated
public class BuildUtils {

  public static List<String> appendAllOptions(List<String> runCommand, String additionalCommandLineParameters) {
    if (StringUtil.isEmpty(additionalCommandLineParameters)) {
      return runCommand;
    }

    StringTokenizer tokenizer = new StringTokenizer(additionalCommandLineParameters, " ");
    while (tokenizer.hasMoreElements()) {
      runCommand = appendOptions(runCommand, tokenizer.nextToken());
    }

    return runCommand;
  }

  @NotNull
  public static List<String> appendOptions(@NotNull List<String> command, String opts) {
    try {
      if (!Strings.isNullOrEmpty(opts)) {
        command.add(opts);
      }
    } catch (Exception e) {
      command = new ArrayList<>(command);
      command.add(opts);
    }

    return command;
  }

  public static String quote(String str, boolean addQuotes) {
    final @NonNls StringBuilder buffer = new StringBuilder(str.length() + (addQuotes ? 2 : 0));
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
