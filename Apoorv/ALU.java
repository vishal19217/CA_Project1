package CA_Project;

public class ALU {

    public static int compute(int op1, int op2, int imm, String operation){
        if(operation.equals("ADD")){
            return op1+op2;
        }else if(operation.equals("SUB")){
            return op1-op2;
        }else if(operation.equals("AND")){
            return op1 & op2;
        }else if(operation.equals("OR")){
            return op1 | op2;
        }else if(operation.equals("XOR")){
            return op1 ^ op2;
        }else if(operation.equals("SRL")){
            return op1 >> op2;
        }else if(operation.equals("SLL")){
            return op1 << op2;
        }else if(operation.equals("ADDI")){
            return op1 + imm;
        }
        return 0;
    }

    public static void main(String args[]){
        System.out.println("This is the ALU!");
    }
}
