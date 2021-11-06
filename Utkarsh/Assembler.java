package CA_Project;
public class Assembler {

//    static String reverse_string(String str){
//        String temp = "";
//        for(int i = 0 ; i<str.length() ; i++){
//            temp = str.charAt(i)+temp;
//        }
//        return temp;
//    }

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
            opcode = "0000101";
            imm = Integer.toBinaryString(Integer.parseInt(arr[2]));
            while(imm.length()<32) {
                String temp0 = "0"+imm;
                imm = temp0;
            }

            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }

            rd = rdt;

            result = imm.substring(11,12)+imm.substring(21,31)+imm.substring(20,21)+imm.substring(12,20)+rd+opcode;
        }
        else if(arr[0].equals("beq")) {
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
            func = "000";
            rs1 = rs1t;
            rs2 = rs2t;
            result = imm.substring(19, 20)+imm.substring(21, 27)+rs2+rs1+func+imm.substring(27, 31)+imm.substring(20, 21)+opcode;
            //result = imm.substring(20, 21)+imm.substring(22, 27)+rs2+rs1+func+imm.substring(27, 32)+imm.substring(21, 22)+opcode;
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
            opcode = "0000100";
            imm = Integer.toBinaryString(Integer.parseInt(arr[2]));
            while(imm.length()<32) {
                String temp0 = "0"+imm;
                imm = temp0;
            }

            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }

            rd = rdt;

            result = imm.substring(0,20)+rd+opcode;
        }
        else if(arr[0].equals("and")) {
            opcode = "0000000";
            int temp = Instruction.indexOf("add");
            //System.out.println(arr[1]);
            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }
            //System.out.println(arr[2]);
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[3]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1,arr[3].length())));
            //System.out.println(rs2t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            func = "010";
            rd = rdt;
            rs1 = rs1t;
            rs2 = rs2t;

            result = "0000000"+rs2+rs1+func+rd+opcode;
        }
        else if(arr[0].equals("or")) {
            opcode = "0000000";
            int temp = Instruction.indexOf("add");
            //System.out.println(arr[1]);
            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }
            //System.out.println(arr[2]);
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[3]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1,arr[3].length())));
            //System.out.println(rs2t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            func = "011";
            rd = rdt;
            rs1 = rs1t;
            rs2 = rs2t;

            result = "0000000"+rs2+rs1+func+rd+opcode;
        }
        else if(arr[0].equals("xor")) {
            opcode = "0000000";
            int temp = Instruction.indexOf("add");
            //System.out.println(arr[1]);
            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }
            //System.out.println(arr[2]);
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[3]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1,arr[3].length())));
            //System.out.println(rs2t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            func = "100";
            rd = rdt;
            rs1 = rs1t;
            rs2 = rs2t;

            result = "0000000"+rs2+rs1+func+rd+opcode;
        }
        else if(arr[0].equals("sll")) {
            opcode = "0000000";
            int temp = Instruction.indexOf("add");
            //System.out.println(arr[1]);
            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }
            //System.out.println(arr[2]);
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[3]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1,arr[3].length())));
            //System.out.println(rs2t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            func = "101";
            rd = rdt;
            rs1 = rs1t;
            rs2 = rs2t;

            result = "0000000"+rs2+rs1+func+rd+opcode;
        }
        else if(arr[0].equals("sra")) {
            opcode = "0000000";
            int temp = Instruction.indexOf("add");
            //System.out.println(arr[1]);
            String rdt = Integer.toBinaryString(Integer.parseInt(arr[1].substring(1,arr[1].length())));
            //System.out.println(rdt);
            while(rdt.length()<5) {
                String temp0 = "0"+rdt;
                rdt = temp0;
            }
            //System.out.println(arr[2]);
            String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
            //System.out.println(rs1t);
            while(rs1t.length()<5) {
                String temp0 = "0"+rs1t;
                rs1t = temp0;
            }
            //System.out.println(arr[3]);
            String rs2t = Integer.toBinaryString(Integer.parseInt(arr[3].substring(1,arr[3].length())));
            //System.out.println(rs2t);
            while(rs2t.length()<5) {
                String temp0 = "0"+rs2t;
                rs2t = temp0;
            }
            func = "110";
            rd = rdt;
            rs1 = rs1t;
            rs2 = rs2t;

            result = "0000000"+rs2+rs1+func+rd+opcode;
        }

        return(result);
    }
    public static void main(String[] args) {
        // Test
        String Answer = assembler_binary("MyLabel: lw r17 32(r6)");
        System.out.println(Answer);
        System.out.println(Answer.length());
        //System.out.println(Integer.toBinaryString(-10));
    }
}
