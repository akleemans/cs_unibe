/* 
 * author(s):   Adrianus Kleemans [07-111-693]
 *              Christa Biberstein [09-104-381]
 * modified:    2012-03-27
 *
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "mips.h"

/* The "Hardware" */
byte memory[MEMORY_SIZE];
word registers[REGISTER_COUNT];
word pc;

/* To stop the MIPS machine */
int doRun;

/* In case you want to watch the machine working */
int verbose = FALSE;

/* Operation and function dispatcher */
Operation operations[OPERATION_COUNT];
Function functions[FUNCTION_COUNT];

/* ======================================================================= */
/* Some useful helpers */

/* Assembles the given parts of an I-type instruction into a single word*/
word create_itype_hex(unsigned immediate, unsigned rt, unsigned rs, unsigned opcode) {
	return immediate + (rt << 16)+ (rs <<21) + (opcode << 26);
}

/* Assembles the given parts of an J-type instruction into a single word*/
word create_jtype_hex(unsigned address, unsigned opcode) {
	return address + (opcode << 26);
}

/* Assembles the given parts of an R-type instruction into a single word*/
word create_rtype_hex(unsigned funct, unsigned shamt, unsigned rd, unsigned rt, unsigned rs, unsigned opcode) {
	return funct + (shamt << 6) + (rd << 11) + (rt << 16) + (rs <<21) + (opcode << 26);
}

/* Extends a 16 bit halfword to a 32 bit word with the value of the most significant bit */
word signExtend(halfword value) {
    return (value ^ 0x8000) - 0x8000;
}

/* Extends a 16 bit halfword to a 32 bit word by adding leading zeros */
word zeroExtend(halfword value) {
    return (value | 0x00000000);
}

/* To make some noise */
void printInstruction(Instruction *i) {
    Operation o = operations[i->i.opcode];
    Function f;
    switch (o.type) {
        case iType: 
            printf("%-4s %02i=0x%08ux, %02i=0x%08ux, 0x%04x\n", o.name, i->i.rt, registers[i->i.rt], i->i.rs, registers[i->i.rs], i->i.immediate ); 
            break;
        case jType:
            printf("%-4s 0x%08x\n", o.name, i->j.address); 
            break;
        case rType:
            f = functions[i->r.funct];
            printf("%-4s %02i=0x%08ux, %02i=0x%08ux, %02i=0x%08ux, 0x%04x\n", f.name, i->r.rd, registers[i->r.rd], i->r.rs, registers[i->r.rs],i->r.rt, registers[i->r.rt],i->r.shamt); 
            break;
        case specialType: 
            printf("%-4s\n", o.name); 
            break;
    }
}

/* ======================================================================= */
/* Memory operations */

/* Store a word to memory */
void storeWord(word w, word location) {
	memory[location]   = (w & 0xFF000000) >> (8*3);
	memory[location+1] = (w & 0x00FF0000) >> (8*2);
	memory[location+2] = (w & 0x0000FF00) >> (8*1);
	memory[location+3] = (w & 0x000000FF);
}

/* Load a word from memory */
word loadWordFrom(word location) {
	word w = 0;
	w += (memory[location]   << (8*3));
	w += (memory[location+1] << (8*2));
	w += (memory[location+2] << (8*1));
	w +=  memory[location+3];
	return w;
}

/* ======================================================================= */
/* Initialize and run */
void assignOperation(unsigned short opCode, const char name[OP_NAME_LENGTH+1], InstructionType type, void (*operation)(Instruction*)) {
    strcpy(operations[opCode].name, name);
    operations[opCode].type=type;
    operations[opCode].operation = operation;
}

void assignFunction(unsigned short funct, const char name[FUNC_NAME_LENGTH+1], void (*function)(Instruction*)) {
    strcpy(functions[funct].name, name);
    functions[funct].function = function;
}

/* Initialize the "hardware" and operation and function dispatcher */
void initialize() {
	int i;
	/* Initialize operations */
	for (i=0; i<OPERATION_COUNT; ++i) {
		assignOperation(i, "ndef", specialType, &undefinedOperation);
	}
	assignOperation(OC_ZERO, "zero", rType, &opCodeZeroOperation);
	/* To stop the MIPS machine */
	assignOperation(OC_STOP,"stop", specialType, &stopOperation);
	
	assignOperation(OC_ADDI, "addi", iType, &mips_addi);
        assignOperation(OC_JAL, "jal", jType ,&mips_jal);
	assignOperation(OC_LUI, "lui", iType ,&mips_lui);
	assignOperation(OC_LW, "lw", iType, &mips_lw);
	assignOperation(OC_ORI, "ori", iType, &mips_ori);
	assignOperation(OC_SW, "sw", iType, &mips_sw);
	
	/* Initialize operations with OpCode = 0 and corresponding functions */
	for (i=0; i<FUNCTION_COUNT; ++i) {
		assignFunction(i, "ndef", &undefinedFunction);
	}
	assignFunction(FC_ADD, "add", &mips_add);
	assignFunction(FC_SUB, "sub", &mips_sub);
	
        /* Initialize memory */
	for (i=0; i<MEMORY_SIZE; ++i) {
		memory[i] = 0;
	}
    
	/* Initialize registers */
	for (i=0; i<REGISTER_COUNT; ++i) {
		registers[i]= 0;
	}
	
	/* Stack pointer */
	SP=65535;
	
	/* Initialize program counter */
	pc = 0;
	
	/* Yes, we want the machine to run */
	doRun = TRUE;       	
}

/* Fetch and execute */
void run() {
	while (doRun) {
		/* Fetch Instruction*/
		word w  = loadWordFrom(pc);
		Instruction *instruction = (Instruction *) &w;
		/* Please note: the program counter is incremented before the operation is executed */
		pc += 4;
		/* Execute Instruction*/
		operations[instruction->i.opcode].operation(instruction);
		/* In case you want to watch the machine */
                if (verbose) {
                    printInstruction(instruction);
                }
	}
}

/* ======================================================================= */
/* "Special" operations --- only for "internal" usage */

/* To deal with "undefined" behaviour */
void undefinedOperation(Instruction *instruction) {
    printf("%s in %s, line %i: Unknown opcode: %x\n",__func__, __FILE__, __LINE__, instruction->i.opcode);
    exit(0);
}

/* To deal with "undefined" behaviour */
void undefinedFunction(Instruction *instruction) {
    printf("%s in %s, line %i: Unknown funct: %x\n",__func__, __FILE__, __LINE__, instruction->r.funct);
    exit(0);
}

/* To deal with operations with opcode = 0 */
void opCodeZeroOperation(Instruction *instruction) {
	functions[instruction->r.funct].function(instruction);
}

/* To stop the machine */
void stopOperation(Instruction *instruction) {
    doRun = FALSE;
}

/* ========================================================================== */
/* Implemented MIPS operations */

/* ADD */
void mips_add(Instruction *instruction) {
	InstructionTypeR r = instruction->r;
	registers[r.rd] = (signed)registers[r.rs] + (signed)registers[r.rt];
}

/* ADDI */
void mips_addi(Instruction *instruction) {
	InstructionTypeI i = instruction->i;
	registers[i.rt] = (signed)registers[i.rs] + (signed)signExtend(i.immediate);
}

/* JAL */
void mips_jal(Instruction *instruction) {
	InstructionTypeJ j = instruction->j;
	registers[31] = pc;
	pc = (pc & 0xf0000000) | (j.address << 2); 
}

/* LUI */
void mips_lui(Instruction *instruction) {
	InstructionTypeI i = instruction->i;
	registers[i.rt] = i.immediate << 16;
}

/* LW */
void mips_lw(Instruction *instruction) {
	InstructionTypeI i = instruction->i;
	registers[i.rt] = loadWordFrom(registers[i.rs] + (signed)signExtend(i.immediate));
} 

/* ORI */
void mips_ori(Instruction *instruction) {
	InstructionTypeI i = instruction->i;
	registers[i.rt] = registers[i.rs] | zeroExtend(i.immediate);
}

/* SUB */
void mips_sub(Instruction *instruction) {
	InstructionTypeR r = instruction->r;
	registers[r.rd] = (signed)registers[r.rs] - (signed)registers[r.rt];
}

/* SW */
void mips_sw(Instruction *instruction) {
	InstructionTypeI i = instruction->i;
	storeWord(registers[i.rt], registers[i.rs] + (signed)signExtend(i.immediate));
}

