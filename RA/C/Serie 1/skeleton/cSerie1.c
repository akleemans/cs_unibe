/* authors:   Christa Biberstein 09-104-381
 *            Adrianus Kleemans  07-111-693
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

/* Define abbreviations for the indices of the registers */
#define I_ZERO 0
#define I_AT 1
#define I_V0 2
#define I_V1 3
#define I_A0 4
#define I_A1 5
#define I_A2 6
#define I_A3 7
#define I_T0 8
#define I_T1 9
#define I_T2 10
#define I_T3 11
#define I_T4 12
#define I_T5 13
#define I_T6 14
#define I_T7 15
#define I_S0 16
#define I_S1 17
#define I_S2 18
#define I_S3 19
#define I_S4 20
#define I_S5 21
#define I_S6 22
#define I_S7 23
#define I_T8 24
#define I_T9 25
#define I_K0 26
#define I_K1 27
#define I_GP 28
#define I_SP 29
#define I_FP 30
#define I_RA 31

/* Define abbreviations for the operation codes (OC) and function codes (FC) */
#define OC_ADD		0x00
#define FC_ADD		0x20
#define OC_ADDI		0x08
#define OC_ADDIU	0x09
#define OC_ADDU		0x00
#define FC_ADDU		0x21
#define OC_AND		0x00
#define FC_AND		0x24
#define OC_ANDI		0x0C
#define OC_BEQ		0x04
#define OC_BNE		0x05
#define OC_DIV		0x00
#define FC_DIV		0x1A
#define OC_J		0x02
#define OC_JAL		0x03
#define OC_JR		0x00
#define FC_JR		0x08
#define OC_LBU		0x00
#define FC_LBU		0x24
#define OC_LHU		0x00
#define FC_LHU		0x25
#define OC_LUI		0x0F
#define OC_LW		0x23
#define OC_MULT		0x00
#define FC_MULT		0x18
#define OC_NOR		0x00
#define FC_NOR		0x27
#define OC_OR		0x00
#define FC_OR		0x25
#define OC_ORI		0x0D
#define OC_SLT		0x00
#define FC_SLT		0x2A
#define OC_SLTI		0x0A
#define OC_SLTIU	0x0B
#define OC_SLTU		0x00
#define FC_SLTU		0x2B
#define OC_SLL		0x00
#define FC_SLL		0x00
#define OC_SRL		0x00
#define FC_SRL		0x02
#define OC_SB		0x28
#define OC_SH		0x29
#define OC_SW		0x2B
#define OC_SUB		0x00
#define FC_SUB		0x22
#define OC_SUBU		0x00
#define FC_SUBU		0x23
/* Just a handy abbreviation */
#define OC_ZERO 0x00
/* Stop Operation --- not actual MIPS */
#define OC_STOP 0x3F

/* Number of operations, functions and registers */
#define OPERATION_COUNT 64
#define FUNCTION_COUNT 64
#define REGISTER_COUNT 32

/* Maximal length of operation and function names */
#define OP_NAME_LENGTH 4
#define FUNC_NAME_LENGTH 4

/* Define some types */
typedef unsigned long word;
typedef unsigned short halfword;
typedef unsigned char byte;

/* TODO Task (c) add bitfields InstructionTypeI, InstructionTypeJ and InstructionTypeR here */

typedef struct _InstructionTypeI 
{
	unsigned immediate :16;
	unsigned rt :5;
	unsigned rs	:5;
	unsigned opcode :6;
} InstructionTypeI;

typedef struct _InstructionTypeJ
{
	unsigned address :26;
	unsigned opcode :6;
} InstructionTypeJ;

typedef struct _InstructionTypeR
{
	unsigned funct :6;
	unsigned shamt :5;
	unsigned rd :5;
	unsigned rt :5;
	unsigned rs :5;
	unsigned opcode :6;
} InstructionTypeR;

	
/* TODO Task (d) add union Instruction here */

typedef union _Instruction
{
	InstructionTypeI i;
	InstructionTypeJ j;
	InstructionTypeR r;
} Instruction;

/* TODO Task (e) add enumeration InstructionType here */

typedef enum _InstructionType
{
	iType, jType, rType, specialType
} InstructionType;


/* TODO Task (f) add structure Operation here */

typedef struct _Operation
{
	char name[OP_NAME_LENGTH + 1];
	InstructionType type;
	void (*operation)(Instruction *);
} Operation;

/* TODO Task (g) add structure Function here */

typedef struct _Function
{
	char name[FUNC_NAME_LENGTH + 1];
	void (*function)(Instruction *);
} Function;


/* Operation and function dispatcher */
Operation operations[OPERATION_COUNT];
Function functions[FUNCTION_COUNT];

/* Assembles the given parts of an I-type instruction into a single word*/
word create_itype_hex(unsigned short immediate, unsigned short rt, unsigned short rs, unsigned short opcode) {
    return immediate + (rt << 16)+ (rs <<21) + (opcode << 26);
}

/* Assembles the given parts of an J-type instruction into a single word*/
word create_jtype_hex(unsigned long address, unsigned short opcode) {
    return address + (opcode << 26);
}

/* Assembles the given parts of an R-type instruction into a single word*/
word create_rtype_hex(unsigned short funct, unsigned short shamt, unsigned short rd, unsigned short rt, unsigned short rs, unsigned short opcode) {
    return funct + (shamt << 6) + (rd << 11) + (rt << 16) + (rs <<21) + (opcode << 26);
}

/* Assembles the given parts of an special-type instruction into a single word*/
word create_specialtype_hex(unsigned short opcode) {
    return create_jtype_hex(0x0000, opcode);
}

/* Copies operation into the operation dispatcher */
void assignOperation(unsigned short opCode, const char name[OP_NAME_LENGTH+1], InstructionType type, void (*operation)(Instruction*)) {
    strcpy(operations[opCode].name, name);
    operations[opCode].type=type;
    operations[opCode].operation = operation;
}

/* Copies functions into the function dispatcher */
void assignFunction(unsigned short funct, const char name[FUNC_NAME_LENGTH+1], void (*function)(Instruction*)) {
    strcpy(functions[funct].name, name);
    functions[funct].function = function;
}

/* Initialize the "hardware" and operation and function dispatcher */
void initialize() {
    int i;
    /* Initialize operations  with default values */
    for (i=0; i<OPERATION_COUNT; ++i) {
        assignOperation(i, "ndef", specialType, 0);
    }

    /* to deal with operations with OpCode = 0, i.e. R-Type */
    assignOperation(OC_ZERO, "zero", rType, 0);

    /* assign some actual operations */
    assignOperation(OC_ADDI, "addi", iType, 0);
    assignOperation(OC_J, "j", jType, 0);
    assignOperation(OC_LUI, "lui", iType, 0);
    assignOperation(OC_LW, "lw", iType, 0);
    assignOperation(OC_ORI, "ori", iType, 0);
    assignOperation(OC_SW, "sw", iType, 0);
    assignOperation(OC_STOP,"stop", specialType, 0);

    /* Initialize operations with OpCode = 0 and corresponding functions with default values*/
    for (i=0; i<FUNCTION_COUNT; ++i) {
        assignFunction(i, "ndef", 0);
    }

    /* assign some actual functions */
    assignFunction(FC_ADD, "add", 0);
    assignFunction(FC_SUB, "sub", 0);
}


void printInstruction(Instruction *i) 
{
/* TODO Task (h) complete printInstruction here */
    
	Operation o = operations[i->i.opcode];
	Function u = functions[i->r.funct];
	
	switch (o.type) {
		case iType:
			printf("%-4s %02i, %02i, 0x%04x\n", o.name, i->i.rt, i->i.rs, i->i.immediate);
			break;
			
		case jType:
			printf("%-4s 0x%08x\n", o.name, i->j.address);
			break;
			
		case rType:
			printf("%-4s %02i, %02i, %02i, 0x%04x\n", u.name, i->r.rd, i->r.rs, i->r.rt, i->r.shamt);
			break;

		case specialType:
			printf("%-4s\n", o.name);
			break;
	}
	
}

void testPrint(word w) {
    Instruction * instruction = (Instruction *) &w;
    printInstruction(instruction);
}

int main(int argc, const char * argv[]) {
    initialize();
    testPrint(create_rtype_hex(FC_ADD, 0x0000, I_T0, I_T1, I_T2, OC_ADD));
    testPrint(create_itype_hex(0x0001, I_T0, I_ZERO, OC_ADDI));
    testPrint(create_jtype_hex(0xCD1234, OC_J));
    testPrint(create_itype_hex(0xBBBB, I_T0, I_ZERO, OC_LUI));
    testPrint(create_itype_hex(0xA03B, I_T0, I_T1, OC_LW));
    testPrint(create_itype_hex(0x0101, I_T0, I_T0, OC_ORI));
    testPrint(create_rtype_hex(FC_SUB, 0x0002, I_T0, I_T1, I_T2, OC_SUB));
    testPrint(create_itype_hex(0xD070, I_T0, I_T1, OC_SW));
    testPrint(create_specialtype_hex(OC_STOP));
    return 0;
}






