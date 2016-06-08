#define TEST2 2
const TEST3 = 3;
const TEST4 = 4;

public void test() {
  switch (6) {
    case 1:
      return;
    case TEST2:
      return;
    case TEST3:
      return;
    case TEST4::
      return;
    case (_:5):
      return;
    default:
      return;
  }
}