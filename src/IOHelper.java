import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// this class does the heavy lifting of "compiling" the ARM assembly code in assembler.txt
public class IOHelper {

    // singleton & class objects
    static IOHelper instance;
    static String FILENAME = "assembler.txt";

    // attributes
    private BufferedReader reader;
    private List<Instruction> codeLines;
    private boolean initialised = false;

    // private constructor to prevent object creation
    private IOHelper () {}

    // returns class IOHelper object
    public static IOHelper getInstance () {
        if (IOHelper.instance == null) IOHelper.instance = new IOHelper();
        return IOHelper.instance;
    }

    // initialises FileReader, programme counter, and loads code from assembler.txt into memory
    public void initialise () {

        // initialises buffered reader
        try {
            this.reader = new BufferedReader(new FileReader(IOHelper.FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The IOHelper could not be initialised.");
        }

        // set flags for other methods to check
        this.initialised = true;
        // TODO check if file is empty => throw error => create custom exceptions to throw for errors is assembler code
        // TODO is there something missing here?

        // loads code into memory
        this.codeLines = this.loadCode();
    }

    // setter for filename
    public void setFileName (String filename) {
        IOHelper.FILENAME = filename;
    }

    // getter for code list
    public List<Instruction> getProgramme () {
        return this.codeLines;
    }

    // mainly closes the buffered reader
    public void terminate () {
        try {
            this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The IOHelper could not be terminated.");
        }
    }

    // turns the assembler.txt file into a List of Instruction object, from which the programme can be executed
    private List<Instruction> loadCode () {
        List<Instruction> code = new ArrayList<>();
        String nextLine = this.nextLine();
        while (nextLine != null) {
            code.add(this.generateInstruction(nextLine));
            nextLine = this.nextLine();
        }
        return code;
    }

    // takes a string and tries to decompose it into a new instruction
    private Instruction generateInstruction (String line) {

        // TODO write generateInstruction method
        Instruction instruction = null;

        try {
            // split input String
            String[] lineSplit = line.split(" ");

            // extract instruction arguments
            int lineNo = Integer.parseInt(lineSplit[0].substring(0, lineSplit[0].length()-1));              // gets the line number of the instruction
            INSTRUCTION_TYPE type = IOHelper.getType(lineSplit[1].substring(0, lineSplit[1].length()-1));   // gets instruction type
            int targetID = Integer.parseInt(lineSplit[2].substring(1, lineSplit[2].length()-1));            // gets target register id
            Register target = Register.registers[targetID];                                                 // gets reference to target register object

            // generate Instruction object
            // MOV, ADD, SUB, MUL, DIV, B, CMP, BEQ, BNE;
            switch (type) {
                case MOV -> {
                    // check if load constant or copy from register
                    if (lineSplit[3].charAt(0) == 'r') {
                        instruction = new MovInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))));
                    } else {
                        instruction = new MovInstruction(lineNo, target, Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1)));
                    }
                }
                case ADD -> {
                    // 200: ADD: r0, r1, r2;
                    // 200: ADD: r0, r1, #123;
                    // 0    1    2   3   4
                    if (lineSplit[4].charAt(0) == 'r') {
                        instruction = new AddInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                                Register.get(Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1))));
                    } else {
                        instruction = new AddInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                                Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1)));
                    }
                }
                case SUB -> {
                    if (lineSplit[4].charAt(0) == 'r') {
                        instruction = new SubInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                                Register.get(Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1))));
                    } else {
                        instruction = new SubInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                                Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1)));
                    }
                }
                case MUL -> {
                    instruction = new MulInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                            Register.get(Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1))));
                }
                case DIV -> {
                    instruction = new DivInstruction(lineNo, target, Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))),
                            Register.get(Integer.parseInt(lineSplit[4].substring(1, lineSplit[4].length()-1))));
                }
                case B -> {
                    // 040: B: 000;
                    instruction = new BInstruction(lineNo, Integer.parseInt(lineSplit[2].substring(0, lineSplit[2].length()-1)));
                }
                case CMP -> {
                    // 044: CMP: r1, r0;
                    instruction = new CmpInstruction(lineNo, Register.get(Integer.parseInt(lineSplit[2].substring(1, lineSplit[2].length()-1))),
                            Register.get(Integer.parseInt(lineSplit[3].substring(1, lineSplit[3].length()-1))));
                }
                case BEQ -> {
                    instruction = new BeqInstruction(lineNo, Integer.parseInt(lineSplit[2].substring(0, lineSplit[2].length()-1)));
                }
                case BNE -> {
                    instruction = new BneInstruction(lineNo, Integer.parseInt(lineSplit[2].substring(0, lineSplit[2].length()-1)));
                }
                /*
                case STR -> {

                }
                case LDR -> {

                }
                 */
            }
        } catch (NullPointerException e) {
            System.out.println("Encountered NullPointerException while trying to generate an instruction.");
            e.printStackTrace();
        }

        // return value
        return instruction;
    }

    private static INSTRUCTION_TYPE getType (String s) {
        return switch (s) {
            case "MOV" -> INSTRUCTION_TYPE.MOV;
            case "ADD" -> INSTRUCTION_TYPE.ADD;
            case "SUB" -> INSTRUCTION_TYPE.SUB;
            case "MUL" -> INSTRUCTION_TYPE.MUL;
            case "DIV" -> INSTRUCTION_TYPE.DIV;
            case "B"   -> INSTRUCTION_TYPE.B;
            case "CMP" -> INSTRUCTION_TYPE.CMP;
            case "BEQ" -> INSTRUCTION_TYPE.BEQ;
            case "BNE" -> INSTRUCTION_TYPE.BNE;
            /*
            case "STR" -> INSTRUCTION_TYPE.STR;
            case "LDR" -> INSTRUCTION_TYPE.LDR;
             */
            default -> null;
        };
    }

    // get next line from this.reader => null if there is none, so check for null when calling this method
    private String nextLine () {
        if (!this.initialised) return null;
        String nextLine;
        try {
            if ((nextLine = this.reader.readLine()) != null) return nextLine;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
