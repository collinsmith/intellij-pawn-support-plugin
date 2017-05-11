package net.alliedmods.lang.amxxpawn.psi;

public interface ChildRole {
  int INCLUDE_REFERENCE = 1;
  int REFERENCE_NAME = 2;

  int INCLUDE_KEYWORD = 3;
  int FILE_REFERENCE = 4;
  int HASH = 5;

  static boolean isUnique(int role) {
    switch (role) {
      default:
        return true;
    }
  }
}
