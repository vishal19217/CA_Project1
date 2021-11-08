import java.io.*;
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
            System.out.println("Branch Target VAlue"+branchTarget);
            pc_val = pc_val+bt;
            if(s.isJalr){
                pc = branchTarget;
            }
        }
        else{
            pc_val = pc_val+1;
        }
        pc = Integer.toBinaryString(pc_val);

    }
    public static void storeInstructions(String mem[]) throws IOException {
        //reading the machineCode and storing in the memory
        int id=0;
        File assemblyFile = new File("C:\\Users\\HP\\IdeaProjects\\CA_Project1\\Final\\machineCode.txt");
        BufferedReader br = new BufferedReader(new FileReader(assemblyFile));
        String codeLine;
        while((codeLine=br.readLine())!=null){
            mem[id] = codeLine;
            id++;
        }
    }
    public static void main(String[] args) throws IOException {
        MemInitialise();
        storeInstructions(mem);
        mem[7] = "00000000000000000000000000000000";//data stored at mem[7] = 0
        pc = Integer.toBinaryString(0);
        Simulator s = new Simulator(registerFile,mem);
        registerFile[0] = 0;
        registerFile[1] = 5;  //rd
        registerFile[2] = 27; //rs1
        registerFile[3] = 7; //rs2
        int i=0;
        while(mem[Integer.parseInt(pc,2)]!=null){
            System.out.println("PC ki value:"+pc);

            s.initialise();
//            dumpRF();
            s.fetch(pc);
            dumpRF();
            updatePC(s.branchTarget,s);
            System.out.println("new PC:"+pc);
            i++;

        }
        System.out.println(registerFile[1]);

    }
}