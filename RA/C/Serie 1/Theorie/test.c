#include <stdio.h>

int main(int argc, char **argv) {
  long a = 1234567890;
  long b = 987654321;

  void * p = &b;
  printf("%x\n", p);
  printf("%x\n", *(long *)p++);
  printf("It works!! %x\n", *(long *)p);
  printf("%x\n", *(char *)p++);
  printf("%x\n", *(unsigned char *)p++);
  printf("%x\n", p);
  return 0;
}
