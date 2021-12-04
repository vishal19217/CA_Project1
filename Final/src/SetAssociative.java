import java.util.Scanner;

public class SetAssociative extends Cache {

//    int size,missPenalty,hitTime,blockSize;
//    String tagArray[];

//    int dataArray[][];
    int offset,k,tagsize,set_index;
    int associativity;
    SetAssociative(int size, int blockSize,String mem[]) {
        super(size, blockSize,mem);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter k for k-way Associativity:-");
        k = sc.nextInt();
        offset = (int) (Math.log(blockSize)/Math.log(2));
        set_index = (int) (Math.log(size/k)/Math.log(2));
        tagsize = 32-set_index-offset;
        tagArray = new String[size];
//        dataArray = new String[][][size][blockSize];

    }

    @Override
    public void insert(String address,String  data) {

        String tag = address.substring(0,tagsize);
        String idxBin = address.substring(tagsize,tagsize+set_index);
        String dataLocBin = address.substring(tagsize+set_index,32);
        int dataLoc = Integer.parseInt(dataLocBin,2);
        int setNum = Integer.parseInt(idxBin,2);
        System.out.println(tagsize+":"+set_index+":"+offset);
//        System.out.println(address+":"+address.length());
        System.out.println(data);
//        System.out.println(setNum+":"+k);
        associativity = k;
        String loc = tag+idxBin;
        String zeros = "0".repeat(offset);
        loc = loc+zeros;
        int startIdx = Integer.parseInt(loc,2);
        for(int i=0;i<k;i++){
            // empty block in  a set
            if(tagArray[setNum*k + i]==null){
                System.out.println("hi");
                tagArray[setNum*k + i] = "11"+tag;
                System.out.println();
                for(int j=0;j<blockSize;j++){
//                    if((startIdx+j)!=dataLoc){
                        dataArray[k*setNum+i][j] = mem[startIdx+j];
//                    }
                }
                dataArray[setNum*k + i][dataLoc] = data;
                return;
            }
            //if block is present then update the location in that block
            else if(tagArray[setNum*k +i].substring(2).equals(tag)){
                tagArray[setNum*k + i] = "11"+tag;
                System.out.print("HI");
                dataArray[setNum*k +i][dataLoc] = data;
                return;
            }
        }

        //if no blocks in the set then change block 1
        tagArray[setNum*k] = "11"+tag;
        for(int j=0;j<blockSize;j++){
            if((startIdx+j)!=dataLoc){
                dataArray[k*setNum][j] = mem[startIdx+j];
            }
        }
        dataArray[k*setNum][dataLoc] = data;

    }

    @Override
    public void write() {

    }

    @Override
    public void search() {

    }
    @Override
    public void print(){
        for(int i=0;i<size;i++){
            if(i%k==0) {

                System.out.println("Set No:-" + (i / k));
            }
            for(int j=0;j<blockSize;j++){
                System.out.print(dataArray[i][j]+" ");
            }
            System.out.println();

        }
    }
}
