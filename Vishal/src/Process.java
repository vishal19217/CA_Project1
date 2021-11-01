import java.util.*;
import java.lang.*;
//
public class Process {
    //register r1 stores the return address
    //Control signals
    static String pc=null;
    static String []mem;
    static int [] registerFile = new int[32];
    public static void Meminitialise(int memCap){
        mem = new String[memCap];
    }
    public static void Meminitialise(){
        Meminitialise(256);
    }

    public static void dumpPC(){
        System.out.println("Address stored in PC:"+pc);
    }
    public static  void dumpRF(){
        System.out.println("Status of Register File");
        for(int i=0;i<32;i++){
            System.out.println("R"+i+":"+registerFile[i]);
        }
    }
//    public static void updatePC(){
//        if(isBranchTaken){
//            pc = branchPC;
//        }
//
//        adr = adr+1;
//        pc = Integer.toHexString(adr);
//
//    }
    public static void main(String[] args){
        Meminitialise();
        //00000-00-00100-00011-000-00001-01100-11
          //111110011100-00011-000-00001-00100-11
        //add
        mem[0] = "00000000010000011000000010110011";
        //sub
        mem[0] = "00000000010000011101000010110011";
        //addi
        mem[0] = "11111001110000011101000010110011";

        pc = Integer.toBinaryString(0);
        Simulator s = new Simulator(registerFile,mem);
        registerFile[1] = 2;
        registerFile[4] = 1;
        registerFile[3] = 5;
        dumpRF();
        s.fetch(pc);
        dumpRF();
        System.out.println(mem[0]);

    }
}



//    initialise the memory
//      pc = 0
//        halted = false;
//        white(not halted)
//        {
//            Instruction = MEM.getData(PC); // Get current instruction
//            halted, new_PC = EE.execute(Instruction); // Update RF compute new_PC
//            PC.dump(); // Print PC
//            RF.dump(); // Print RF state
//
//            TIMING.dump() // Cycles spent to Process Inst.
//            PC.update(new_PC); // Update PC
//        }
//        MEM.dump()














//        while(true) {
//
//
//            String fh_inst = fetch();
//            Object[] val = decode(fh_inst);
//            int res = execute(val);
//            if (fh_inst.substring(25, 29) == "00000" || fh_inst.substring(25, 29) == "01000") {
////
//            }
//            else if (fh_inst.substring(25, 29) == "01100") {
//                writeBack(res, fh_inst.substring(20, 24), 1);
//            }
//            break;
//        }
