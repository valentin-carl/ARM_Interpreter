public class Main {

    /**
     * @param args ARRAY OF LENGTH 2 --
     *             FIRST ARGUMENT: NUMBER OF REGISTERS --
     *             SECOND ARGUMENT: INITIAL VALUE OF PC --
     *             THIRD ARGUMENT: ASSEMBLER CODE FILE NAME
     */
    public static void main (String[] args) {

        // create new IOHelper
        IOHelper compiler = IOHelper.getInstance();

        // get input values
        //int noRegs = Integer.parseInt(args[0]);
        //int programmeCounterInitialValue = Integer.parseInt(args[2]);
        //String filename = args[2];
        int noRegs = 13;
        String filename = "assembler.txt";

        // set file name
        compiler.setFileName(filename);

        // init
        compiler.initialise();

        // run programme
        Programme.generate(compiler.getProgramme());
        Programme.getInstance().run();

        // end of the programme
        compiler.terminate();

        // print end states of registers
        Programme.getInstance().printRegisters();

        // print Status Register
        System.out.println("StatusRegister = " + StatusRegister.getInstance().getStatus());
    }
}
