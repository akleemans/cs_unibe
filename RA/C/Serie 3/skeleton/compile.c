/* 
 * authors:     Adrianus Kleemans  [07-111-693]
 *              Christa Biberstein [09-104-381]
 * modified:    2012-04-18
 *
 */

#include <stdlib.h>
#include <stdio.h>
#include "memory.h"
#include "mips.h"
#include "compiler.h"
 
int main ( int argc, char** argv ) {
    /* Task (c) implement main */
    
	if(argc<3) {
		printf("%s \n", "usage: compile expression filename");
	}
	else {
		
		printf("%s" "%s\n", "Input: ", argv[1]);
		verbose = TRUE;
		printf("%s", "Postfix: ");
		compiler(argv[1], argv[2]);
		verbose = FALSE;
		printf("%s" "%s\n", "MIPS Binary saved to ", argv[2]);
	}
	
	return EXIT_SUCCESS;
}

