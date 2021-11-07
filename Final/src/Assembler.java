
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.io.FileReader;

public class Assembler {

    static String truncate(String str, int size){
        String temp = "";
        for(int i = str.length()-1 ; i>=0 ; i--){
            temp = str.charAt(i) + temp;
            if(temp.length()==size){
                break;
            }
        }
        return temp;
    }

    static String assembler_binary(String Instruction) {

        String s1 = "MyLabel: add r1 r2 imm";
        if(Instruction.indexOf(":") != -1) {
            s1 = Instruction.substring(Instruction.indexOf(":")+2);
        }
        else {
            s1 = Instruction;
        }
        System.out.println(s1);
        String[] arr = s1.split(" ");

        String opcode = "";
        String imm = "";
        String offset = "";
        String rd = "";
        String rs1 = "";
        String rs2 = "";
        String func = "";
        String result = "";

        if(arr[0].equals("add")){
            // format add rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "000";
            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("addi")) {
            // format addi rd rs1 imm
            opcode = "0010011";
            imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(imm.length()<12) {
                imm = "0"+imm;
            }
            if(imm.length()>12){
                imm = truncate(imm, 12);
            }
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            func = "000";
            result = imm+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("sub")) {
            // format sub rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "000";
            result = "0100000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("lw")) {
            // format lw rd offset(rs1)
            opcode = "0000011";
            int n1 = arr[2].indexOf('(');
            int n2 = arr[2].indexOf(')');
            offset = Integer.toBinaryString(Integer.parseInt(arr[2].substring(0,n1)));
            while(offset.length()<12) {
                offset = "0"+offset;
            }
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(n1+2,n2)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            func = "010";
            result = offset+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("sw")) {
            // format sw rs2 offset(rs1)
            opcode = "0100011";
            int n1 = arr[2].indexOf('(');
            int n2 = arr[2].indexOf(')');
            offset = Integer.toBinaryString(Integer.parseInt(arr[2].substring(0,n1)));
            while(offset.length()<12) {
                offset = "0"+offset;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(n1+2,n2)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rs2.length()<5) {
                rs2= "0"+rs2;
            }
            func = "010";
            result = offset.substring(0, 7)+rs2+rs1+func+offset.substring(7, 12)+opcode;
        }

        else if(arr[0].equals("jalr")) {
            // format jalr rd rs1 offset
            opcode = "1100111";
            offset = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(offset.length()<12) {
                offset = "0"+offset;
            }
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            func = "000";
            result = offset+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("jal")) {
            // format jal rd offset
            opcode = "1101111";
            offset = Integer.toBinaryString(Integer.parseInt(arr[2]));
            while(offset.length()<21) {
                offset = "0"+offset;
            }
            if(offset.length()>21){
                offset = truncate(offset, 21);
            }
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            result = offset.charAt(0)+offset.substring(10,20)+offset.charAt(9)+offset.substring(1,9)+rd+opcode;
        }

        else if(arr[0].equals("beq")) {
            // format beq rs1 rs2 offset
            opcode = "1100011";
            offset = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(offset.length()<12) {
                offset = "0"+offset;
            }
            if(offset.length()>12){
                offset = truncate(offset, 12);
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "000";
//            result =
        }

        else if(arr[0].equals("bne")) {
            opcode = "0000011";
            //System.out.println("HERE");
            //System.out.println(Integer.toBinaryString(Integer.parseInt(arr[3])));
            imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(imm.length()<32) {
                String temp0 = "0"+imm;
                imm = temp0;
            }
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[2]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            //System.out.println(arr[3]);
            func = "001";
            rs1 = rs1t;
            rs2 = rs2t;
            result = imm.substring(19, 20)+imm.substring(21, 27)+rs2+rs1+func+imm.substring(27, 31)+imm.substring(20, 21)+opcode;
            //result = imm.substring(20, 21)+imm.substring(22, 27)+rs2+rs1+func+imm.substring(27, 32)+imm.substring(21, 22)+opcode;
        }
        else if(arr[0].equals("blt")) {
            opcode = "0000011";
            //System.out.println("HERE");
            //System.out.println(Integer.toBinaryString(Integer.parseInt(arr[3])));
            imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(imm.length()<32) {
                String temp0 = "0"+imm;
                imm = temp0;
            }
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[2]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            //System.out.println(arr[3]);
            func = "010";
            rs1 = rs1t;
            rs2 = rs2t;
            result = imm.substring(19, 20)+imm.substring(21, 27)+rs2+rs1+func+imm.substring(27, 31)+imm.substring(20, 21)+opcode;
            //result = imm.substring(20, 21)+imm.substring(22, 27)+rs2+rs1+func+imm.substring(27, 32)+imm.substring(21, 22)+opcode;
        }
        else if(arr[0].equals("bge")) {
            opcode = "0000011";
            //System.out.println("HERE");
            //System.out.println(Integer.toBinaryString(Integer.parseInt(arr[3])));
            imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
            while(imm.length()<32) {
                String temp0 = "0"+imm;
                imm = temp0;
            }
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[2]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            //System.out.println(arr[3]);
            func = "011";
            rs1 = rs1t;
            rs2 = rs2t;
            result = imm.substring(19, 20)+imm.substring(21, 27)+rs2+rs1+func+imm.substring(27, 31)+imm.substring(20, 21)+opcode;
        }

        else if(arr[0].equals("lui")) {
            // format lui rd imm
            opcode = "0110111";
            imm = Integer.toBinaryString(Integer.parseInt(arr[2]));
            while(imm.length()<32) {
                imm = "0"+imm;
            }
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            result = imm.substring(0,20)+rd+opcode;
        }

        else if(arr[0].equals("and")) {
            // format and rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "111";
            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("or")) {
            // format or rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "110";
            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("xor")) {
            // format xor rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "100";
            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("sll")) {
            // format sll rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "001";
            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        else if(arr[0].equals("sra")) {
            // format sra rd rs1 rs2
            opcode = "0110011";
            rd = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1)));
            while(rd.length()<5) {
                rd = "0"+rd;
            }
            rs1 = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1)));
            while(rs1.length()<5) {
                rs1 = "0"+rs1;
            }
            rs2 = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1)));
            while(rs2.length()<5) {
                rs2 = "0"+rs2;
            }
            func = "101";
            result = "0100000"+rs2+rs1+func+rd+opcode;
        }
        return(result);
    }
    public static void treatLabel(HashMap<String,Integer> mp,ArrayList<String> labelArr) throws IOException {
        File assemblyFile = new File("D:\\IIITD\\SEMESTER5\\CATutorial\\AssemblyCode.txt");
        BufferedReader br = new BufferedReader(new FileReader(assemblyFile));
        String codeLine;

        int id=0;
        //extracting the labels and storing in the labelArr
        while((codeLine=br.readLine())!=null){
//            System.out.println(codeLine);
            String arr[] = codeLine.split(" ");
//            Instruction.indexOf(":") != -1
            if(arr[0].indexOf(":")!=-1){
                int len = arr[0].length();
                labelArr.add(arr[0].substring(0,len-1));
                mp.put(arr[0].substring(0,len-1),id);
            }
            id++;
        }
        for(String i:labelArr){
//            System.out.println(i);
        }



    }
    public static String check(String line,ArrayList<String> labelArr){
        for(String i:labelArr){
            if(line.indexOf(i)!=-1){
//                System.out.println(line+" --"+i);
                return i;
            }
        }
        return null;
    }
    public static void main(String[] args) throws Exception{
        // Test
        ArrayList<String> labelArr = new ArrayList<>();
        HashMap<String,Integer> mp=new HashMap<>();
        treatLabel(mp,labelArr);
        File assemblyFile = new File("D:\\IIITD\\SEMESTER5\\CATutorial\\AssemblyCode.txt");
        BufferedReader br = new BufferedReader(new FileReader(assemblyFile));
        String codeLinee;
        int id = 0;
        while((codeLinee=br.readLine())!=null){
            String codeLine = codeLinee;
            String arr[] = codeLine.split(" ");
            String labelPresent = check(codeLine,labelArr);
            //if instruction contains only label but not jumps etc then simply remove label name from instruction
            if(labelPresent!=null && codeLine.indexOf(":")!=-1){
                int idx = codeLine.indexOf(arr[1]);
                codeLine = codeLine.substring(idx);
            }
            //if the instruction is branch then simply replace the label with offset
            else if(labelPresent!=null){
                int offset = mp.get(labelPresent)-id;
                codeLine = arr[0]+" "+arr[1]+" "+arr[2]+" "+Integer.toString(offset);
            }



//          System.out.println(codeLine);
            String Answer = assembler_binary(codeLine);
            System.out.println(Answer);
            id++;
        }
////
//        File assemblyFile = new File("D:\\IIITD\\SEMESTER5\\CATutorial\\AssemblyCode.txt");
//        BufferedReader br = new BufferedReader(new FileReader(assemblyFile));
//        String codeLine;
//        while((codeLine=br.readLine())!=null){
//            System.out.println(codeLine);
//            String Answer =  assembler_binary(codeLine);
//            System.out.println(Answer);
//        }
//        System.out.println(Answer);
//        System.out.println(Answer.length());
//        System.out.println(Integer.toBinaryString(-10));
//        System.out.println(truncate(Integer.toBinaryString(-10),10));
    }
}
//    beq r0 r1 DONE
//    add r5  r6 r2
//    addi r1 r1 -1
//    DONE: sub r1 r3 r4


