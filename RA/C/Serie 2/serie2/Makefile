CC      = gcc
CFLAGS  = -ansi -pedantic -Wall

OBJ = mips.o 

mips: main.o $(OBJ)
	$(CC) $(CFLAGS) -o mips main.o $(OBJ)

test: test.o $(OBJ)
	$(CC) $(CFLAGS) -o test test.o $(OBJ)
	./test

clean:
	-rm -f *.o mips test 

%.o: %.c
	$(CC) $(CFLAGS) -c $<
