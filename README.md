# ARM Emulator

Currently supports the following instructions: 

- `MOV`
- `ADD`
- `SUB`
- `MUL`
- `DIV`
- `B`
- `CMP`
- `BEQ`
- `BNE`

`STR` and `LDR` are yet to be implemented (need to add memory...)

---

**How to use it?**

Write your assembler code into the `assembler.txt` file an run the `Main.java` class.

---

**Syntax**

*LineNo*: *InstructionType*: *Arguments*;

---

**Sample Programme**

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

> r0 (0)
> 
> r1 (2)
> 
> r2 (0)
> 
> r3 (32)
> 
> StatusRegister = true
