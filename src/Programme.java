import java.util.List;

public class Programme {

    // attributes
    List<Instruction> code;

    // singleton
    static Programme singleton;

    // constructor
    private Programme (List<Instruction> code) {
        this.code = code;
    }

    // getter for singleton
    public static Programme getInstance() {
        return Programme.singleton;
    }

    // initialises singleton
    public static void generate (List<Instruction> code) {
        Programme.singleton = new Programme(code);
    }

    // runs the programme
    public void run () {
        // TODO write run method
        //for (Instruction i : this.code) i.execute();

        int numberOfLines = this.code.size();
        // assume: every line number is incremented by +4 (in decimal!)
        int lineIndex = 0;
        whileLoop:
        while (lineIndex < numberOfLines) {

            // working on this instruction in the current iteration
            Instruction current = this.code.get(lineIndex);

            //System.out.println("CURRENT INSTRUCTION: " + current.lineNo +  ": " + current.type + ";");

            // execute current instruction
            this.code.get(lineIndex).execute();

            /*
            if (current.type == INSTRUCTION_TYPE.CMP) {
                System.out.println("comparing " + ((CmpInstruction) current).r1.value + " and " + ((CmpInstruction) current).r2.value);
            }
             */

            // if instruction deviates from adding programme counter + 1 (maybe) => jump instructions
            if (current.type == INSTRUCTION_TYPE.B || current.type == INSTRUCTION_TYPE.BEQ || current.type == INSTRUCTION_TYPE.BNE) {
                switch (current.type) {
                    case B -> {
                        for (int i = 0; i < this.code.size(); i++) {
                            if (this.code.get(i).lineNo == ((BInstruction) current).lineToJumpTo) {
                                //lineIndex = ((BInstruction) current).lineToJumpTo;
                                lineIndex = this.getLineIndex(((BInstruction) current).lineToJumpTo);
                                continue whileLoop;
                                //break;
                            }
                        }
                    }
                    case BEQ -> {
                        if (StatusRegister.singleton.getStatus()) {
                            //System.out.println("status register = " + StatusRegister.getInstance().getStatus());
                            for (int i = 0; i < this.code.size(); i++) {
                                if (this.code.get(i).lineNo == ((BeqInstruction) current).lineToJumpTo) {
                                    //lineIndex = ((BeqInstruction) current).lineToJumpTo;
                                    lineIndex = this.getLineIndex(((BeqInstruction) current).lineToJumpTo);
                                    continue whileLoop;
                                    //break;
                                }
                            }
                        }
                    }
                    case BNE -> {
                        if (!StatusRegister.singleton.getStatus()) {
                            for (int i = 0; i < this.code.size(); i++) {
                                if (this.code.get(i).lineNo == ((BneInstruction) current).lineToJumpTo) {
                                    //lineIndex = ((BneInstruction) current).lineToJumpTo;
                                    lineIndex = this.getLineIndex(((BneInstruction) current).lineToJumpTo);
                                    continue whileLoop;
                                    //break;
                                }
                            }
                        }
                    }
                }
            }

            // if reached, current instruction is not affecting the programme counter => boring!
            lineIndex++;

            /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             */
        }
    }

    private int getLineIndex (int lineNo) {
        for (int lineIndex = 0; lineIndex < this.code.size(); lineIndex++) {
            if (this.code.get(lineIndex).lineNo == lineNo) return lineIndex;
        }
        return -1;
    }

    // print registers lists if existing
    public void printRegisters () {
        for (Register r : Register.registers) {
            if (r.used) System.out.println(r);
        }
    }
}
