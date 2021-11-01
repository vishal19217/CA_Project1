 public class Simulator{
    boolean isSt;
    boolean isLd;
    boolean isBeq;
    boolean isBgt;
    boolean isRet;
    boolean isImmediate;
    boolean isWb;
    boolean isUBranch;
    boolean isCall;
    boolean isAdd;
    boolean isSub;
    boolean isCmp;
    boolean isMul;
    boolean isDiv;
    boolean isMod;
    boolean isLsl;
    boolean isAsr;
    boolean isOr;
    boolean isXor;
    boolean isAnd;
    boolean isNot;
    boolean isMov;
    boolean isSra;
    boolean isSll;
    boolean isJalr;
    boolean isJal;
    boolean isBne;
    boolean isBlt;
    boolean isBge;
    boolean isLui;
    boolean isOffset;
    boolean isBranchTaken=false;
    String branchPC,branchTarget;
    String finst;
    int [] registerFile;
     String []mem;
    Simulator(int[] registerFile,String []mem){
        this.registerFile = registerFile;
        this.mem = mem;
        System.out.println("Hi");
    }
    void initialise(){
        isSt = false;
        isOffset = false;
        isLd = false;
        isBeq = false;
        isBgt = false;
        isRet = false;
        isImmediate = false;
        isWb = false;
        isUBranch = false;
        isCall = false;
        isAdd = false;
        isSub = false;
        isCmp = false;
        isMul = false;
        isDiv = false;
        isMod = false;
        isLsl = false;
        isAsr = false;
        isOr = false;
        isAnd = false;
        isNot = false;
        isMov = false;
        isXor = false;
        isSra = false;
        isSll = false;
        isBlt = false;
        isJal = false;
        isJalr = false;
        isBge = false;
        isBne = false;
        isLui = false;
        branchPC = null;
        branchTarget = null;
    }
    public void updateControlSignal(String fh_inst){
//        System.out.println(fh_inst);
//        System.out.println(fh_inst.substring(25,30)+"-"+fh_inst.substring(17,20)+"-"+fh_inst.substring(0,5));
        if(fh_inst.substring(25,30).equals("01100")){
            if(fh_inst.substring(17,20).equals("000")){
                if(fh_inst.substring(0,5).equals("00000")){
                    isAdd = true;
                }
                else{
                    isSub = true;
                }
            }

            else if(fh_inst.substring(17,20).equals("111")){
                isAnd = true;
            }
            else if(fh_inst.substring(17,20).equals("110")){
                isOr = true;
            }
            else if(fh_inst.substring(17,20).equals("100")){
                isXor = true;
            }
            else if(fh_inst.substring(17,20).equals("001")){
                isSll = true;
            }
            else if(fh_inst.substring(17,20).equals("101")){
                isSra = true;
            }
        }
        //addi
        else if(fh_inst.substring(25,30).equals("00100")){
            isImmediate = true;
            isAdd = true;
        }
        //load inst
        else if(fh_inst.substring(25,30).equals("00000")){
            isOffset = true;
            isLd = true;
        }
        //store inst
        else if(fh_inst.substring(25,30).equals("01000")){
            isOffset = true;
            isSt = true;
        }
        //jump and link reg
        else if(fh_inst.substring(25,30).equals("11001")){
            isJalr = true;
            isOffset = true;
        }
        //jump and link
        else if(fh_inst.substring(25,30).equals("11011")){
            isJal = true;
            isOffset = true;
        }

        else if(fh_inst.substring(25,30).equals("11000")){
            isOffset = true;
            if(fh_inst.substring(17,20).equals("000")){
                isBeq = true;
            }
            else if(fh_inst.substring(17,20).equals("001")){
                isBne = true;
            }
            else if(fh_inst.substring(17,20).equals("100")){
                isBlt = true;
            }
            else if(fh_inst.substring(17,20).equals("101")){
                isBge = true;
            }
        }
        else if(fh_inst.substring(25,30).equals("01101")){
            isLui = true;
            isImmediate = true;
        }

    }
    public  void fetch(String pc){
        int adr = Integer.parseInt(pc,2);
        String fh_inst = mem[adr];
        finst = fh_inst;
        System.out.println(fh_inst);
        updateControlSignal(fh_inst);
        decode(fh_inst);

    }

    public void decode(String inst){
        //tasks: calculating the value of immediate,read the src registers

        //Immediate Operands Decode

        //contains op1,op2,immx,branchTarget
        Object[] arr = new Object[4];
        int op1,op2;
        //Register type(add,sub,and,or,xor,sll,sra
        op1 = Integer.parseInt(inst.substring(12,17),2);
        op1 = (Integer) registerFile[op1];
        op2 = Integer.parseInt((inst.substring(7,12)),2);
        op2 = (Integer)registerFile[op2];
        arr[0] = op1;
        arr[1] = op2;
        if(isBlt||isBeq||isBne||isBge){
            branchTarget =  inst.substring(0,1)+inst.substring(24,25)+inst.substring(1,7)+inst.substring(20,24);
            System.out.println(branchTarget);
            arr[3] = branchTarget;
        }
        if(isImmediate){
            int immx;
            if(isAdd){
                String imm = inst.substring(0,12);
                if(imm.substring(0,1).equals("1")){
                    imm = "11111111111111111111"+imm;
                    immx = Integer.parseInt(imm,2);
                }
                else{
                    imm = "00000000000000000000"+imm;
                    immx = Integer.parseInt(imm,2);
                }
            }
            else{

            }
        }
        if(isLd){

        }
        if(isSt){

        }
        if(isJalr){

        }
        if(isJal){

        }
        if(isLui){

        }

        execute(arr);
//        else if(inst.substring())
    }
    public void execute(Object[] val){
        Object aluResult=null;
        System.out.println(val[0]+" -"+val[1]);
        //add
        if(isAdd){
            isAdd = false;
            if(isImmediate){
                aluResult=(int)val[0]+(int)val[2];
            }
            else
                aluResult = (int)val[0]+(int)val[1];
        }
        if(isSub){
            isSub = false;
            aluResult = (int)val[0]-(int)val[1];
        }
        if(isAnd){
            isAnd = false;
            aluResult = (int)val[0]&(int)val[1];
        }
        if(isOr){
            isOr = false;
            aluResult = (int)val[0]|(int)val[1];
        }
        if(isXor){
            isXor = false;
            aluResult = (int)val[0]^(int)val[1];
        }
        //logical shift left
        if(isSll){
            isSll = false;
            aluResult = (int)val[0]<<(int)val[1];
        }
//        arithmetic shift right
        if(isSra){
            isSra = false;
            aluResult = (int)val[0]>>(int)val[1];
        }
//        if(isAdd){
//            isSub = false;
//            aluResult = (int)val[0]-(int)val[1];
//        }if(isSub){
//            isSub = false;
//            aluResult = (int)val[0]-(int)val[1];
//        }if(isSub){
//            isSub = false;
//            aluResult = (int)val[0]-(int)val[1];
//        }

        memory((int)aluResult);

    }
    public void memory(int res){
     if(isLd|| isSt){

     }
     else{
         writeBack(res);
     }
    }

    public void writeBack(int ans){
        //writing args=1
        String adr = finst.substring(20,25);
        int idx = Integer.parseInt(adr,2);
        registerFile[idx] = ans;
        return;
    }

}