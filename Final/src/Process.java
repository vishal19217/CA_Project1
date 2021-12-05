import java.io.*;
import java.util.*;
import java.lang.*;
//
public class Process {
    //register r1 stores the return address
    //Control signals
    static String pc=null;
    static int totalTime=0;
    static String []mem;
    static int [] registerFile = new int[32];
    public static void MemInitialise(int memCap){
        mem = new String[memCap];
    }
    public static void MemInitialise(){
        MemInitialise(256);
    }

    public static void dumpPC(){
        System.out.println("Current Address stored in PC:"+pc);
    }
    public static void dumpTiming(int timeTaken){
        totalTime+=timeTaken;
        System.out.println("Time Taken by the current instruction(in cycles):-"+timeTaken);
    }
    public static  void dumpRF(int registerFile[]){
        System.out.println("Status of Register File:");
        for(int i=0;i<32;i++){
            System.out.println("R"+i+":"+registerFile[i]);
        }
    }


    public static void dumpMem(){
        System.out.println("Memory Status:");
        for(int i=0;i<mem.length;i++){
            System.out.println("Memory Location("+i+"):"+mem[i]);
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

        if(s.branchPC!=0){

            pc_val = pc_val+ s.branchPC;
            pc = Integer.toBinaryString(pc_val);

            if(s.isJalr){
                pc_val = s.branchPC;
                pc = branchTarget;
            }
        }
        else{
            pc_val = pc_val+1;
            pc = Integer.toBinaryString(pc_val);
        }


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
        //Assumptions:- ra ->r31
        //no concurrent data and instruction at a memory location
        //first load the instructions in memory
        //each instruction will be processed after previous inst completes it's 5-stage pipeline


        Assembler assembler = new Assembler();
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to provide the size of Main Memory press 1 for yes else 2 for no");
        int ch = sc.nextInt();
        if(ch==1){
            System.out.println("Input the size of Main Memory (each location is 32bit):-");
            int t = sc.nextInt();
            MemInitialise(t);
        }
        else {
            MemInitialise();
        }
        System.out.println("Enter the Access Time(in cycles where 1 access time = 1 cycle) for Memory Operations:-");
        int at = sc.nextInt();
        assembler.convertAssembly();//converting assembly to binary
        storeInstructions(mem);

        pc = Integer.toBinaryString(0);
        Simulator s = new Simulator(registerFile,mem,at);
        Cache cache = new SetAssociative(4,4,mem,1);
        s.cache = cache;
//        cache.print();
        while(mem[Integer.parseInt(pc,2)]!=null){
            s.initialise();
            s.fetch(pc);
//            dumpRF(registerFile);
//            dumpPC();
//            dumpTiming(s.timeTaken);
            updatePC(s.branchTarget,s);
//            System.out.println("new PC value:-"+pc);


        }
//        System.out.println("TotalTimeTaken(in Cycles):"+totalTime);
        dumpMem();
        cache.print();


    }
}
//Assumptions:- ra ->r31
//no concurrent data and instruction at a memory location
//first load the instructions in memory
//each instruction will be processed after previous inst completes it's 5-stage pipeline