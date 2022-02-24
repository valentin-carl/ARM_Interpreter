# ARM Interpreter

### Overview

This is a simple Java programme to simulate ARM Assembly programmes. It has 13 registers (r0 – r12) and a status register that contains *true* or *false* after `CMP` was called. It currently supports the following instructions:

`MOV`, `ADD`, `SUB`, `MUL`, `DIV`, `B`, `CMP`, `BEQ`, and `BNE`.

`STR` and `LDR` are yet to be implemented (need to add memory...).

After executing all instructions of the ARM programme, it prints the registers on the console that were used at some point in the programme (the status register will always be printed; it is *false* by default).



### User's guide

Write your assembler code into the `assembler.txt` file and run the `Main.java` class. (This repository includes a simple programme in the `assembler.txt` file, simply replace the content of that file.) Note: the `.txt` file has to be in the same folder as the Java project (or change the `FILENAME` variable in the `IOHelper.java` class...).



### Syntax

Important: the line number has to be in decimal, **not hexadecimal**! After the line number, there needs to be a colon; as is the case after the instruction. The instruction has to be in all caps. Seperate each argument by a comma and end each line with a semicolon. To load, add, or subtract integers instead of register values, add a *#* before the number. Do not add a *#* before the line in jump instructions (`B`, `BEQ`, and `BNE`).

> *LineNo*: *InstructionType*: *Arguments*;



### Sample programme

*Input (in the `assembler.txt` file):*

~~~Assembly
400: MOV: r0, #0;
404: MOV: r1, #0;
408: MOV: r3, #32;
412: ADD: r2, r0, #4;
416: ADD: r2, r2, r2;
420: CMP: r2, r3;
424: BEQ: 436;
428: ADD: r1, r1, #1;
432: B: 416;
436: MOV: r2, r0;
~~~

*Output:*

> r0 (0)
> 
> r1 (2)
> 
> r2 (0)
> 
> r3 (32)
> 
> StatusRegister = true
