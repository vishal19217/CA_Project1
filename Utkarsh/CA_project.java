package Practice;
import java.util.*;
public class CA_project {
		
	static String assembler_binary(String Instruction) {
		
		String s1 = "mylabel: add r1 r2 imm";
		if(Instruction.indexOf(":")!=-1) {
			s1 = Instruction.substring(Instruction.indexOf(":")+2,Instruction.length());
		}
		else {
			s1 = Instruction;
			//System.out.println(s1);
		}
		System.out.println(s1);
		String[] arr = s1.split(" ");
		System.out.println(s1.indexOf(":"));
		int Colfinder = Instruction.indexOf(":");
		String opcode = "";
		String imm = "";
		String rd = "";
		String rs1 = "";
		String rs2 = "";
		String func = "";
		String result = "";
		
		if(arr[0].equals("add")) {
			System.out.println("HERE1");
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
			func = "000";
			rd = rdt;
			rs1 = rs1t;
			rs2 = rs2t;
			
			result = "0000000"+rs2+rs1+func+rd+opcode;
		}
		else if(arr[0].equals("addi")) {
			opcode = "0000001";
			System.out.println("HERE");
			System.out.println(Integer.toBinaryString(Integer.parseInt(arr[3])));
			imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
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
			//System.out.println(arr[2]);
			String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
			//System.out.println(rs1t);
			while(rs1t.length()<5) {
				String temp0 = "0"+rs1t;
				rs1t = temp0;
			}
			//System.out.println(arr[3]);
			func = "000";
			rd = rdt;
			rs1 = rs1t;
			result = imm+rs1+func+rd+opcode;
		}
		else if(arr[0].equals("sub")) {
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
			func = "001";
			rd = rdt;
			rs1 = rs1t;
			rs2 = rs2t;
			
			result = "0000000"+rs2+rs1+func+rd+opcode;
		}
		else if(arr[0].equals("lw")) {
			opcode = "0000001";
			//System.out.println("HERE");
			//System.out.println(Integer.toBinaryString(Integer.parseInt(arr[3])));
			imm = Integer.toBinaryString(Integer.parseInt(arr[3]));
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
			//System.out.println(arr[2]);
			String rs1t = Integer.toBinaryString(Integer.parseInt(arr[2].substring(1,arr[2].length())));
			//System.out.println(rs1t);
			while(rs1t.length()<5) {
				String temp0 = "0"+rs1t;
				rs1t = temp0;
			}
			//System.out.println(arr[3]);
			func = "001";
			rd = rdt;
			rs1 = rs1t;
			result = imm.substring(20,32)+rs1+func+rd+opcode;
		}
		else if(arr[0].equals("sw")) {
			opcode = "0000010";
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
			result = imm.substring(20, 27)+rs2+rs1+func+imm.substring(27,32)+opcode;
		}
		else if(arr[0].equals("jalr")) {
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
		String Answer = assembler_binary("MyLabel: sub r29 r7 r1");
			System.out.println(Answer);
			System.out.println(Answer.length());
		//System.out.println(Integer.toBinaryString(-10));
	}
}
//ASK ABOUT LABEL REPRESENTATION!!