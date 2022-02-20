public class Register {

    // keeps track of number of registers
    private static int countRegs = 0; // TODO unnötig, auf 13+1 Register festgelegt

    // register
    static Register[] registers = Register.INIT();
    static boolean[] usedFlags = new boolean[13]; // TODO

    // attributes
    int registerID = Register.countRegs++;
    int value;

    // constructor for different initial value
    public Register (int value) {
        this.value = value;
    }

    // default constructor
    public Register () {
        this.value = 0; // default register value is 0
    }

    private static Register[] INIT () {
        Register[] regs = new Register[13];
        for (int i = 0; i < 13; i++) {
            regs[i] = new Register();
        }
        return regs;
    }

    public static Register get (int id) {
        return Register.registers[id];
    }

    public void set (int value) {
        this.value = value;
    }

    @Override
    public String toString () {
        return "r" + this.registerID + "\t(" + this.value + ")";
    }
}

class StatusRegister extends Register {

    static StatusRegister singleton;

    private StatusRegister () {
        this.value = 0;
    }

    public static StatusRegister getInstance() {
        if (StatusRegister.singleton == null) StatusRegister.singleton = new StatusRegister();
        return StatusRegister.singleton;
    }

    public boolean getStatus () {
        return this.value == 1;
    }

    public void setStatus (boolean b) {
        this.value = b ? 1 : 0; // status == true => this.value == 1, staus == false => this.value = 0
    }
}
