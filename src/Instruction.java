public abstract class Instruction {

    // attributes
    int lineNo;
    Register target;
    INSTRUCTION_TYPE type;

    public abstract void execute();
}

class MovInstruction extends Instruction {

    boolean loadsConstant; // to differentiate between loading constants and copying from another register
    Object from; // either an integer or another register, check this.loadsConstant and then cast

    // load constant
    public MovInstruction (int lineNo, Register target, int value) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.MOV;
        this.loadsConstant = true;
        this.from = value;
    }

    // copy value from another register
    public MovInstruction (int lineNo, Register target, Register r) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.MOV;
        this.loadsConstant = false;
        this.from = r;
    }

    @Override
    public void execute() {
        this.target.set(this.loadsConstant ? (int) this.from : ((Register) this.from).value);
    }
}

class AddInstruction extends Instruction {

    // attributes
    boolean twoRegs; // true => Register + Register; false => Register + Immediate
    Register summand_1;
    Object summand_2; // is either a register object or an integer

    // Register + Register
    public AddInstruction (int lineNo, Register target, Register summand_1, Register summand_2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.ADD;
        this.twoRegs = true;
        this.summand_1 = summand_1;
        this.summand_2 = summand_2;
    }

    // Register + Immediate
    public AddInstruction (int lineNo, Register target, Register summand_1, int summand_2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.ADD;
        this.twoRegs = false;
        this.summand_1 = summand_1;
        this.summand_2 = summand_2;
    }

    @Override
    public void execute() {
        super.target.set(this.twoRegs ?
                        (this.summand_1.value + ((Register) this.summand_2).value) :
                        (this.summand_1.value + (int) this.summand_2));
    }
}

class SubInstruction extends Instruction {

    // attributes
    boolean twoRegs; // true => Register + Register; false => Register + Immediate
    Register summand_1;
    Object summand_2; // is either a register object or an integer

    // Register - Register
    public SubInstruction (int lineNo, Register target, Register summand_1, Register summand_2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.SUB;
        this.twoRegs = true;
        this.summand_1 = summand_1;
        this.summand_2 = summand_2;
    }

    // Register - Immediate
    public SubInstruction (int lineNo, Register target, Register summand_1, int summand_2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.SUB;
        this.twoRegs = false;
        this.summand_1 = summand_1;
        this.summand_2 = summand_2;
    }

    @Override
    public void execute() {
        super.target.set(this.twoRegs ?
                (this.summand_1.value - ((Register) this.summand_2).value) :
                (this.summand_1.value - (int) this.summand_2));
    }
}

class MulInstruction extends Instruction {

    // attributes
    Register m1;
    Register m2;

    public MulInstruction (int lineNo, Register target, Register m1, Register m2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.MUL;
        this.m1 = m1;
        this.m2 = m2;
    }

    @Override
    public void execute() {
        this.target.value = m1.value * m2.value;
    }
}

class DivInstruction extends Instruction {

    // attributes
    Register d1;
    Register d2;

    public DivInstruction (int lineNo, Register target, Register d1, Register d2) {
        super.lineNo = lineNo;
        super.target = target;
        super.type = INSTRUCTION_TYPE.DIV;
        this.d1 = d1;
        this.d2 = d2;
    }

    @Override
    public void execute() {
        this.target.value = (int) (d1.value / d2.value);
    }
}

class BInstruction extends Instruction {

    int lineToJumpTo;

    public BInstruction (int lineNo, int lineToJumpTo) {
        super.lineNo = lineNo;
        super.target = null; // to satisfy inheritance -- do not call!
        super.type = INSTRUCTION_TYPE.B;
        this.lineToJumpTo = lineToJumpTo;
    }

    @Override
    public void execute() {
        //Programme.getInstance().setProgrammeCounter(this.lineToJumpTo);
        System.out.println("b instruction to " + this.lineToJumpTo);
    }
}

class CmpInstruction extends Instruction {

    Register r1;
    Register r2;

    public CmpInstruction (int lineNo, Register r1, Register r2) {
        super.lineNo = lineNo;
        super.target = StatusRegister.getInstance();
        super.type = INSTRUCTION_TYPE.CMP;
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public void execute() {
        StatusRegister.getInstance().setStatus(r1.value == r2.value);
    }
}

class BeqInstruction extends Instruction {

    int lineToJumpTo;

    public BeqInstruction (int lineNo, int lineToJumpTo) {
        super.lineNo = lineNo;
        super.target = null; // to satisfy inheritance -- do not call!
        super.type = INSTRUCTION_TYPE.BEQ;
        this.lineToJumpTo = lineToJumpTo;
    }

    @Override
    public void execute() {
        System.out.println("beq instruction to " + this.lineToJumpTo);
    }
}

class BneInstruction extends Instruction {

    int lineToJumpTo;

    public BneInstruction (int lineNo, int lineToJumpTo) {
        super.lineNo = lineNo;
        super.target = null; // to satisfy inheritance -- do not call!
        super.type = INSTRUCTION_TYPE.BEQ;
        this.lineToJumpTo = lineToJumpTo;
    }

    @Override
    public void execute() {
        System.out.println("bne instruction to " + this.lineToJumpTo);
    }
}

/*
class StrInstruction extends Instruction {



    @Override
    public void execute() {

    }
}

class LdrInstruction extends Instruction {



    @Override
    public void execute() {

    }
}

 */