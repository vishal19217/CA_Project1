import java.util.*;
import java.lang.*;
//
public class Process {
    //register r1 stores the return address
    //Control signals
    static String pc=null;
    static String []mem;
    static int [] registerFile = new int[32];
    public static void MemInitialise(int memCap){
        mem = new String[memCap];
    }
    public static void MemInitialise(){
        MemInitialise(256);
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
    public static int twoComplement(String num){
    String temp="";
    for(int i=0;i<num.length();i++){
        if(num.charAt(i)=='0'){
            temp+='1';
        }
        else{
            temp+='0';
        }
    }
    return -1*(Integer.parseInt(temp,2)+1);
    }
    public static void updatePC(String branchTarget,Simulator s){
        int pc_val = Integer.parseInt(pc,2);

        if(branchTarget!=null){
            int bt;

            if(branchTarget.charAt(0)=='1')
                bt = twoComplement(branchTarget);
            else
                bt = Integer.parseInt(branchTarget,2);
            pc_val = pc_val+bt;
            if(s.isJalr){
                pc = branchTarget;
            }
        }
        else{
            pc_val = pc_val+1;
        }
        pc = Integer.toBinaryString(pc_val);
//        System.out.println("PC VAlue:"+pc_val);
//        System.out.println("Pc in string val:"+pc);

    }
    public static void main(String[] args){
        //pc=pc+1 // 4 byte addressble memory
        MemInitialise();
        //00000-00-00010-00001-110-00001-01100-11
        //00000000000000010010000010000011


        mem[0] = "00000000001000001110000010110011"; //for or
        mem[0] = "00000000000000010010000010000011";//for load
        mem[2] = "00000000000000000000000000001010"; //data for loading from this memory location
        mem[0] = "00000000000000010010000010000011";//for load
        mem[0] = "00000000001100010010001100100011";//for store

        mem[0] = "00000000000001100100001010110111";

        //program for multiplication rs1*rs2
        mem[0] = "00000000000000000010000010000011" ;
        mem[1] = "00000000000000001000000010110011";
        mem[2] = "11111111111100010000000100010011";
        mem[3] = "00000000000000010101000101100011";

//        mem[1] = "01000000001100010000000100110011";
//        mem[2] = "11111110001100010101111011100011";
//        1111 1111 1110
        pc = Integer.toBinaryString(0);
        Simulator s = new Simulator(registerFile,mem);
        registerFile[0] = 0;
        registerFile[1] = 5;  //rd
        registerFile[2] = 3; //rs1
        registerFile[3] = 10; //rs2
        //memory access for load = 2
        int i=0;
//        System.out.println(mem[8]);
//        System.out.println(registerFile[5]);
        while(mem[Integer.parseInt(pc,2)]!=null){
//            System.out.println("PC ki value:"+pc);

            s.initialise();
//            dumpRF();
            s.fetch(pc);
//            dumpRF();
            updatePC(s.branchTarget,s);
            i++;

        }
        System.out.println(registerFile[1]);

    }
}
//Program for multiplication
//load t0 to M[0+rs0] rs0 = 0 ;t0 = rs1
//load rs2
//add t0<-t0+r1
//addi rs2<-rs2-1
//bgt rs2,rs0,accumulate:
//load rs0 = t0
//return
//00000-00-00010-00001-110-00001-01100-11


////program for multiplication rs1*rs2
//mem[0] = "00000000000000000010000010000011" lw rs1 0(rs0)
//mem[1] = "00000000000000001000000010110011" add rs1<-rs1+rs0
//mem[2] = "11111111111100010000000100010011" addi rs2<-rs2-1
//mem[3] = "00000000000000010101000101100011" bgt rs2,a0,label=1
////mem[4] = "00000-00-0000-00001-000-00001-01100-11"
////r1 = rs1
////r2 = rs2
//000000
//
