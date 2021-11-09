import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.io.FileReader;
public class Assembler {
    Assembler(){

    }
    String truncate(String str, int size){
        String temp = "";
        for(int i = str.length()-1 ; i>=0 ; i--){
            temp = str.charAt(i) + temp;
            if(temp.length()==size){
                break;
            }
        }
        return temp;
    }

    String assembler_binary(String Instruction) {

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
//             format add rd rs1 rs2
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

            while(offset.length()<20) {
                offset = "0"+offset;
            }
            if(offset.length()>20){
                offset = truncate(offset, 20);
            }
            System.out.println("offset after truncation:"+offset);
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
            result = offset.charAt(0)+offset.substring(2,8)+rs2+rs1+func+offset.substring(8,12)+offset.charAt(1)+opcode;
        }

        else if(arr[0].equals("bne")) {
            // format bne rs1 rs2 offset
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
            func = "001";
            result = offset.charAt(0)+offset.substring(2,8)+rs2+rs1+func+offset.substring(8,12)+offset.charAt(1)+opcode;
        }

        else if(arr[0].equals("blt")) {
            // format blt rs1 rs2 offset
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
            func = "100";
            result = offset.charAt(0)+offset.substring(2,8)+rs2+rs1+func+offset.substring(8,12)+offset.charAt(1)+opcode;
        }

        else if(arr[0].equals("bge")) {
            // format bge rs1 rs2 offset
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
            func = "101";
            result = offset.charAt(0)+offset.substring(2,8)+rs2+rs1+func+offset.substring(8,12)+offset.charAt(1)+opcode;
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


     void treatLabel(HashMap<String,Integer> mp,ArrayList<String> labelArr) throws IOException {
        File assemblyFile = new File("C:\\Users\\HP\\IdeaProjects\\CA_Project1\\Final\\AssemblyCode.txt");
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
    public String check(String line,ArrayList<String> labelArr){
        for(String i:labelArr){
            if(line.indexOf(i)!=-1){
//                System.out.println(line+" --"+i);
                return i;
            }
        }
        return null;
    }
    public  void createMachineCodeFile(){
        try {
            File myObj = new File("C:\\Users\\HP\\IdeaProjects\\CA_Project1\\Final\\machineCode.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



    public void convertAssembly() throws IOException {
        ArrayList<String> labelArr = new ArrayList<>();
        HashMap<String,Integer> mp=new HashMap<>();
        treatLabel(mp,labelArr);
        createMachineCodeFile();
        File assemblyFile = new File("C:\\Users\\HP\\IdeaProjects\\CA_Project1\\Final\\AssemblyCode.txt");
        BufferedReader br = new BufferedReader(new FileReader(assemblyFile));
        String codeLinee;
        int id = 0;
        createMachineCodeFile();
        FileWriter myWriter = new FileWriter("C:\\Users\\HP\\IdeaProjects\\CA_Project1\\Final\\machineCode.txt");
            int freq=0;
        while((codeLinee=br.readLine())!=null){
                freq++;
            String codeLine = codeLinee;
//            System.out.println("loop se hu:-"+codeLine);
            String arr[] = codeLine.split(" ");
            String labelPresent = check(codeLine,labelArr);
            //if instruction contains only label but not jumps etc then simply remove label name from instruction
            if(labelPresent!=null && codeLine.indexOf(":")!=-1){
                int idx = codeLine.indexOf(arr[1]);
                codeLine = codeLine.substring(idx);
            }
            //if the instruction is branch only  then simply replace the label with offset
            else if(labelPresent!=null && !arr[0].equals("jal")){
                int offset = mp.get(labelPresent)-id;
                codeLine = arr[0]+" "+arr[1]+" "+arr[2]+" "+Integer.toString(offset);
            }
            //instruction is jal (jump and link) remove label name and include return register(r31) and offset in codeLine
            else if(labelPresent!=null){
                int offset = mp.get(labelPresent)-id;
//                System.out.println("offset-"+offset);
                codeLine = arr[0]+" r31 "+Integer.toString(offset);
            }
            System.out.println("freq:"+freq);

            try {
                String Answer = assembler_binary(codeLine);
                System.out.println(Answer);
                myWriter.write(Answer+"\n");

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            //          System.out.println(codeLine);
            id++;
        }
        myWriter.close();

    }


}

