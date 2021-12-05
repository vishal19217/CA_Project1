import java.util.Scanner;

public class SetAssociative extends Cache {

//    int size,missPenalty,hitTime,blockSize;
//    String tagArray[];

//    int dataArray[][];
    int offset,k,tagsize,set_index;
    int associativity;

    SetAssociative(int size, int blockSize,String mem[],int writePolicy) {
        super(size, blockSize,mem,writePolicy);
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
    public int insert(String address) {

        String tag = address.substring(0, tagsize);
        String idxBin = address.substring(tagsize, tagsize + set_index);
        String dataLocBin = address.substring(tagsize + set_index, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);
        int setNum = Integer.parseInt(idxBin, 2);
        System.out.println(tagsize + ":" + set_index + ":" + offset);
        associativity = k;
        String loc = tag + idxBin;
        String zeros = "0".repeat(offset);
        loc = loc + zeros;
        int startIdx = Integer.parseInt(loc, 2);  //for a block
        for (int i = 0; i < k; i++) {
            // empty block in  a set
            if (tagArray[setNum * k + i] == null) {
                tagArray[setNum * k + i] = "10" + tag;
              for (int j = 0; j < blockSize; j++) {
                  dataArray[k * setNum + i][j] = mem[startIdx + j];
              }
                return setNum*k+i;
            }
        }
        return 0;
    }




@Override
    public void write(String address,String data) {
        String tag = address.substring(0,tagsize);
        String idxBin = address.substring(tagsize,tagsize+set_index);
        String dataLocBin = address.substring(tagsize+set_index,32);
        int dataLoc = Integer.parseInt(dataLocBin,2);
        int setNum = Integer.parseInt(idxBin,2);
        int f=0;
        int blockPos = -1;
        for(int i=0;i<k;i++){
            if(tagArray[setNum*k+i].substring(2).equals(tag)){
                dataArray[setNum*k+i][dataLoc] = data;
                blockPos = setNum*k+i;
                f=1;

            }
            if(f==1)break;
        }
        //cache miss
        if(f==0){
            //check if the set has empty slots
            int f2 = 0;
            for(int i=0;i<k;i++){
                if(tagArray[setNum*k+i]==null){
                    f2=1;
                    blockPos = insert(address);
                }
            }
            //if no empty slots evict and insert
            if(f2==0){
                evict(address);
                blockPos = insert(address);
            }
        }
        dataArray[blockPos][dataLoc] = data;
       //writePolicy = 1 (writeThrough) else writeBack
        if(writePolicy==1){
            int add = Integer.parseInt(address,2);
            mem[add] = data;
        }
        else{
            tagArray[blockPos] = "11"+tagArray[blockPos].substring(2);
        }

        for(int i=0;i<size;i++){
            counterArray[i]+=1;
        }
        counterArray[blockPos] = 0;
    }

    @Override
    public String read(String address){
        String tag = address.substring(0,tagsize);
        String idxBin = address.substring(tagsize,tagsize+set_index);
        String dataLocBin = address.substring(tagsize+set_index,32);
        int dataLoc = Integer.parseInt(dataLocBin,2);
        int setNum = Integer.parseInt(idxBin,2);
        int blockPos = -1;
        System.out.println(tagsize+":"+set_index+":"+offset);
//        System.out.println(address+":"+address.length());

        for(int i=0;i<k;i++) {

            //if memory location present in cache(block)  CACHE HIT
            if (tagArray[setNum * k + i].substring(2).equals(tag)) {
                blockPos = setNum*k + i;
                return dataArray[blockPos][dataLoc];
            }
        }
            //CACHE MISS
            blockPos = insert(address);
            int f= 0;
            for(int i=0;i<k;i++){
                if(tagArray[setNum*k+i]==null){
                    f=1;
                    break;
                }
            }
            //if empty slots present
            if(f==1){
                insert(address);
            }
            else{
                evict(address);
                insert(address);
            }


            return dataArray[blockPos][dataLoc];
    }


    @Override
    public void evict(String address) {
        String tag = address.substring(0, tagsize);
        String idxBin = address.substring(tagsize, tagsize + set_index);
        String dataLocBin = address.substring(tagsize + set_index, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);
        int setNum = Integer.parseInt(idxBin, 2);
        System.out.println(tagsize + ":" + set_index + ":" + offset);
        associativity = k;
        int blockPos = -1,mx = -1;
        for(int i=0;i<k;i++){
            if(counterArray[setNum*k+i]>mx){
                mx = counterArray[setNum*k+i];
                blockPos = setNum*k+i;
            }
        }
        //writePolicy = 1 (writeThrough) else writeBack
        if(writePolicy!=1){
            if(tagArray[blockPos].substring(1,2).equals("1")){
                String loc = tagArray[blockPos].substring(2) + idxBin;
                String zeros = "0".repeat(offset);
                loc = loc + zeros;
                int startIdx = Integer.parseInt(loc, 2);  //for a block
                for(int i=0;i<blockSize;i++){
                    mem[startIdx+i] = dataArray[blockPos][i];
                    dataArray[blockPos][i] = null;
                }

            }
            tagArray[blockPos] = null;
        }

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
