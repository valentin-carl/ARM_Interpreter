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
        for (Instruction i : this.code) i.execute();

        int numberOfLines = this.code.size();
        // assume: every line number is incremented by +4 (in decimal!)
        int lineIndex = 0;
        while (lineIndex < numberOfLines) {

            // working on this instruction in the current iteration
            Instruction current = this.code.get(lineIndex);

            // if instruction deviates from adding programme counter + 1 (maybe) => jump instructions
            if (current.type == INSTRUCTION_TYPE.B || current.type == INSTRUCTION_TYPE.BEQ || current.type == INSTRUCTION_TYPE.BNE) {
                System.out.println("hello");
                switch (current.type) {
                    case B -> {
                        for (int i = 0; i < this.code.size(); i++) {
                            if (this.code.get(i).lineNo == ((BInstruction) current).lineToJumpTo) {
                                lineIndex = ((BInstruction) current).lineToJumpTo;
                                break;
                            }
                        }
                    }
                    case BEQ -> {
                        if (StatusRegister.singleton.getStatus()) {
                            for (int i = 0; i < this.code.size(); i++) {
                                if (this.code.get(i).lineNo == ((BeqInstruction) current).lineToJumpTo) {
                                    lineIndex = ((BeqInstruction) current).lineToJumpTo;
                                    break;
                                }
                            }
                        }
                    }
                    case BNE -> {
                        if (!StatusRegister.singleton.getStatus()) {
                            for (int i = 0; i < this.code.size(); i++) {
                                if (this.code.get(i).lineNo == ((BneInstruction) current).lineToJumpTo) {
                                    lineIndex = ((BneInstruction) current).lineToJumpTo;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else {
                // if reached, current instruction is not affecting the programme counter => boring!
                lineIndex++;
            }
        }

    }

    // print registers lists if existing
    public void printRegisters () {
        for (Register r : Register.registers) {
            System.out.println(r);
        }
    }
}
