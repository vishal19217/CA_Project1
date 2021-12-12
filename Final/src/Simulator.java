import javax.sound.midi.Instrument;

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
    boolean isCacheUsed;
    boolean isBranchTaken=false;
    String pc,branchTarget,fhinst;
    int [] registerFile;
    String []mem;
    int accessTime;
    int timeTaken,branchPC;
    public Cache cache;
    Simulator(int[] registerFile,String []mem,int accessTime){
        this.registerFile = registerFile;
        this.mem = mem;
        this.accessTime = accessTime;

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

        branchTarget = null;
        branchPC = 0;
        timeTaken = 5;
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
            timeTaken+=(accessTime-1);
        }

        //store inst
        else if(fh_inst.substring(25,30).equals("01000")){
            isOffset = true;
            isSt = true;
            timeTaken+=(accessTime-1);
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
        this.pc = pc;

        if(isCacheUsed){
            pc = "0".repeat(32-pc.length())+pc;
            String fh_inst = cache.read(pc);
            System.out.println("Instrution:"+fh_inst);
            updateControlSignal(fh_inst);
            fhinst = fh_inst;
//        System.out.println("Instruction:-"+fh_inst);
            decode(fh_inst);
        }
        else{

            int adr = Integer.parseInt(pc,2);
            String fh_inst = mem[adr];
            fhinst = fh_inst;


            updateControlSignal(fh_inst);
//        System.out.println("Instruction:-"+fh_inst);

            decode(fh_inst);
        }

    }
    public int twoComplement(String num){
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
    public void decode(String inst){
        //tasks: calculating the value of immediate,read the src registers

        //Immediate Operands Decode

        //contains op1,op2,immx,offset
        Object[] arr = new Object[4];
        int op1,op2;
        //Register type(add,sub,and,or,xor,sll,sra
        op1 = Integer.parseInt(inst.substring(12,17),2);
        op1 = (Integer) registerFile[op1];
        op2 = Integer.parseInt((inst.substring(7,12)),2);
        op2 = (Integer)registerFile[op2];
        arr[0] = op1;
        arr[1] = op2;

//        System.out.println(op1+":"+op2);
        if(isBlt||isBeq||isBne||isBge){
            String offset = inst.substring(0,1)+inst.substring(24,25)+inst.substring(1,7)+inst.substring(20,24);
            if(offset.substring(0,1).equals("0")){
                arr[3] = Integer.parseInt(offset,2);
            }
            else{
                arr[3] = twoComplement(offset);
            }
        }
        if(isImmediate){
            int immx;
            if(isAdd){
                String imm = inst.substring(0,12);
//                System.out.println(imm);
                if(imm.substring(0,1).equals("1")){
                    immx = twoComplement(imm);
//                    System.out.println("value of immediate stored-"+immx);
                }
                else{
                    immx = Integer.parseInt(imm,2);
                }
            }
            else {
                String imm = inst.substring(0,20);
                //checking if number is negative or positive
                if(imm.substring(0,1).equals("1")){
                    immx = twoComplement(imm);
                }
                else{
                    immx = Integer.parseInt(imm,2);
                }
            }

            arr[2] = (Object) immx;

        }
        if(isLd){
            String offset = inst.substring(0,12);
            if(offset.substring(0,1).equals("0")){
                arr[3] = Integer.parseInt(offset,2);
            }
            else{
                arr[3] = twoComplement(offset);
            }
        }
        if(isSt){
            String offset = inst.substring(0,7)+inst.substring(20,25);
            if(offset.substring(0,1).equals("0")){
                arr[3] = Integer.parseInt(offset,2);
            }
            else{
                arr[3] = twoComplement(offset);
            }
        }
        if(isJalr){
            String offset = inst.substring(0,12);
            if(offset.substring(0,1).equals("0")){
                arr[3] = Integer.parseInt(offset,2);
            }
            else{
                arr[3] = twoComplement(offset);
            }
        }
        if(isJal){
            String offset = inst.substring(0,1)+inst.substring(12,20)+inst.substring(11,12)+inst.substring(1,11);
            if(offset.substring(0,1).equals("0")){
                arr[3] = Integer.parseInt(offset,2);
            }
            else{
                arr[3] = twoComplement(offset);
            }

        }
//        System.out.println(op1+"-"+op2+"-"+arr[2]+"-"+arr[3]);
        execute(arr);


    }

    //contains op1,op2,immx,offset

    public void execute(Object[] val){
        Object aluResult=null;
//        System.out.println(val[0]+":"+val[1]);
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
//            System.out.println(aluResult);
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
        if(isBeq){
            if((int)val[0]==(int)val[1]){
                branchPC = (int)val[3];
//               System.out.println("operation branchequal+branchoffset:"+val[3]);
                branchTarget = Integer.toBinaryString((int)val[3]);
            }
        }
        if(isBne) {
            if ((int) val[0] != (int) val[1]) {
//                System.out.println("operation branchnotequal+branchoffset:"+val[3]);
              branchPC = (int)val[3];
                branchTarget = Integer.toBinaryString((int)val[3]);}
        }
        if(isBlt) {
            if ((int) val[0] < (int) val[1]) {
                branchPC = (int)val[3];
//                 System.out.println("operation branchlessthan+branchoffset:"+val[3]);
                branchTarget = Integer.toBinaryString((int)val[3]);}
        }
        if(isBge) {
            if ((int) val[0] >= (int) val[1]) {
                branchPC = (int)val[3];
                System.out.println("Checking for bge");
//                 System.out.println("operation branchgreater+branchoffset:"+val[3]);
                branchTarget = Integer.toBinaryString((int)val[3]);
            }
        }
        if(isJal||isJalr){
            int pc_val = Integer.parseInt(pc,2);
//            System.out.println("MEi hu:-"+pc_val);
//            System.out.print("offset value"+val[3]);
            branchPC = (int)val[3];
            if(isJal){
                branchTarget = Integer.toBinaryString(branchPC);
            }
            if(isJalr){
                branchPC = (int)val[0]+branchPC;
                branchTarget = Integer.toBinaryString(branchPC);
            }
            System.out.println("Branch Target:"+branchTarget);

            aluResult = (int)(pc_val+1);
        }

//         op1,op2,immx,offset
        if(isLd||isSt){
            aluResult = (int)val[3]+(int)val[0];
        }
        if(isLui){
            aluResult = (int)val[2];
            aluResult = (int)aluResult<<12;

        }
        memory(val,aluResult);

    }
    public void memory(Object []val,Object res){
        if(isLd||isLui){
            if(isLd) {
                String add = Integer.toBinaryString((int)res);
                add = "0".repeat(32-add.length())+add;
                String ldResult = cache.read(add);

                res = Integer.parseInt(ldResult,2);
            }

            writeBack(res);
        }
        else if(isSt){
//            System.out.println("Data:"+val[1]);
            String store = Integer.toBinaryString((int)val[1]);
            String add = Integer.toBinaryString((int)res);
            add = "0".repeat(32-add.length())+add;
            if(store.length()<32){
                int l = store.length();
                for(int i=0;i<32-l;i++){
                    store = "0"+store;

                }
            }
//            System.out.println("Data:"+store);
            cache.write(add,store);
//            mem[(int)res] = store;
        }
        else{
            writeBack(res);
        }
    }

    public void writeBack(Object ans){

        if(ans!=null) {
            String adr = fhinst.substring(20, 25);
            int idx = Integer.parseInt(adr, 2);
            registerFile[idx] = (int)ans;

            return;
        }
    }
}

