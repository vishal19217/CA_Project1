package CA_Project;

public class CPU {

    static private int[] registers;
    static private int pc; // program counter
    static private boolean isBranch;
    static private boolean isImm;
    static private boolean isLoad;
    static private boolean isStore;
    static private int branchTarget;
    static private int aluResult;
    static private int ldResult;

    public CPU(){
        registers = new int[32];
        for(int i = 0 ; i<32 ; i++){
            registers[i] = 0;
        }
        pc = 0;
        isBranch = false;
        isImm = false;
        isLoad = false;
        isStore = false;
        branchTarget = 0;
        aluResult = 0;
        ldResult = 0;
    }

    public static String branch_taken(String INS){ // Function to check for branch instruction
        String opcode = INS.substring(2,7);
        isBranch = true;
        if(opcode.equals("11011")){ // JAL
            return "JAL";
        }else if(opcode.equals("11001")){ // JALR
            return "JALR";
        }
        if(opcode.equals("11000")){ // conditional branch statements
            int rs1 = Integer.parseInt(INS.substring(15,20),2);
            int rs2 = Integer.parseInt(INS.substring(20,25),2);
            String sub_code = INS.substring(12,15);
            if(sub_code.equals("000") && rs1 == rs2){ // BEQ
                return "BEQ";
            }
            if(sub_code.equals("001") && rs1 != rs2){ // BNE
                return "BNE";
            }
            if(sub_code.equals("100") && rs1 < rs2){ // BLT
                return "BLT";
            }
            if(sub_code.equals("101") && rs1 >= rs2){ // BGE
                return "BGE";
            }
        }
        isBranch = false;
        return "NO_BRANCH";
    }

    public static void update_pc(){
        if(isBranch){
            pc = branchTarget;
        }else{
            pc++;
        }
        return;
    }

    public static String fetch_unit(String[] main_memory){
        String INS = main_memory[pc];
        return INS;
    }

    public static String[] decode_unit(String INS){

        String arr[] = new String[5]; // array that will be returned by this function
        for(int i = 0 ; i<5 ; i++){
            arr[i] = "";
        }
        String operation = "";
        int rs1 = 0;
        int rs2 = 0;
        int imm = 0;
        int offset = 0;
        String opcode = INS.substring(2,7);
        String branch_ins = branch_taken(INS);

        if(!branch_ins.equals("NO_BRANCH")){ // For BEQ, BNE, BLT, BGE instructions
            String str_offset = INS.substring(8,12)+INS.substring(25,31)+INS.substring(7,8)+INS.substring(31,32);
            offset = Integer.parseInt(str_offset);
            operation = branch_ins;
            branchTarget = pc + offset;
        }

        if(opcode.equals("01100")){ // For ALU operations

            String sub_code1 = INS.substring(12,15);
            String sub_code2 = INS.substring(27,32);
            rs1 = Integer.parseInt(INS.substring(15,20));
            rs2 = Integer.parseInt(INS.substring(20,25));

            if(sub_code1.equals("000") && sub_code2.equals("00000")){
                operation = "ADD";
            }else if(sub_code1.equals("000") && sub_code2.equals("01000")){
                operation = "SUB";
            }else if(sub_code1.equals("100")){
                operation = "XOR";
            }else if(sub_code1.equals("101")){
                operation = "SRA";
            }else if(sub_code1.equals("001")){
                operation = "SLL";
            }else if(sub_code1.equals("110")){
                operation = "OR";
            }else if(sub_code1.equals("111")){
                operation = "AND";
            }
        }

        else if(opcode.equals("00100")){ // For Add Immediate
            operation = "ADDI";
            isImm = true;
            rs1 = Integer.parseInt(INS.substring(15,20));
            imm = Integer.parseInt(INS.substring(20,32),2);
        }

        else if(opcode.equals("00000")){ // For load
            operation = "LW";
            isLoad = true;
            rs1 = Integer.parseInt(INS.substring(15,20));
            offset = Integer.parseInt(INS.substring(20,32),2);
        }

        else if(opcode.equals("01000")){ // For store
            operation = "SW";
            isStore = true;
            rs1 = Integer.parseInt(INS.substring(15,20));
            rs2 = Integer.parseInt(INS.substring(20,25));
            offset = Integer.parseInt(INS.substring(7,12)+INS.substring(5,32),2);
        }

        else if(opcode.equals("01101")){ // For load immediate
            operation = "LUI";
            isLoad = true;
            isImm = true;
            imm = Integer.parseInt(INS.substring(12,32)+"0000000000",2);
        }
        arr[0] = operation;
        arr[1] = rs1+"";
        arr[2] = rs2+"";
        arr[3] = imm+"";
        arr[4] = offset+"";
        return arr;
    }

    public static void execute_unit(String arr[]){
        String operation = arr[0];
        if(!isBranch && !operation.equals("LW") && !operation.equals("SW") && !operation.equals("LUI")){
            int op1 = registers[Integer.parseInt(arr[1])];
            int op2 = registers[Integer.parseInt(arr[2])];
            int imm = registers[Integer.parseInt(arr[3])];
            aluResult = ALU.compute(op1, op2, imm, operation);
        }
        return;
    }

    public static void memory_unit(String arr[], String main_memory[]){
        String operation = arr[0];
        int imm = Integer.parseInt(arr[3]);
        int op1 = registers[Integer.parseInt(arr[1])];
        int rs2 = Integer.parseInt(arr[2]);
        int offset = Integer.parseInt(arr[3]);
        if(isLoad && operation.equals("LUI")){
            ldResult = imm;
        }
        else if(isLoad && operation.equals("LW")){
            ldResult = Integer.parseInt(main_memory[op1+offset]);
        }
        else if(isStore){
            main_memory[op1+offset] = Integer.toBinaryString(registers[rs2]);
        }
        return;
    }

    public static void write_back_unit(String INS){
        int rd = Integer.parseInt(INS.substring(7,12));
        if(!isBranch && !isLoad && !isStore){
            registers[rd] = aluResult;
        }else if(isLoad){
            registers[rd] = ldResult;
        }
        return;
    }

    public static void setDefault(){
        isBranch = false;
        isImm = false;
        isLoad = false;
        isStore = false;
        branchTarget = 0;
        aluResult = 0;
        ldResult = 0;
    }

    public static void main(String args[]){
        System.out.println("This is the CPU!");
    }
}
