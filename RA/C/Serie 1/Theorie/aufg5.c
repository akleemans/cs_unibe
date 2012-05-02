#include <stdio.h>

int main(int argc, char **argv) {
  short x = 3;
  short *px = &x;
  *(px--) = 10;
  *px = 11;
  printf("%i %i\n", x, *px);
  return 0;
}
