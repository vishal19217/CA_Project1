import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Cache {
    /*
    here word addressable so 1 block location is 1 word
     */
    int size,missPenalty,hitTime,blockSize;
    String tagArray[];
    String mem[];
    String dataArray[][];
    int writePolicy;
    int counterArray[];
    int replacePolicy;
    Queue<Integer>queue;
    Cache(int size,int blockSize,String mem[],int writePolicy,int replacePolicy){
        this.size = size;
        this.replacePolicy = replacePolicy;
        this.writePolicy = writePolicy;
        this.mem = mem;
        this.blockSize = blockSize;
        tagArray = new String[size];
        counterArray = new int[size];
        dataArray = new String[size][blockSize];
        queue = new LinkedList<Integer>();
        for(int i=0;i<size;i++){
            counterArray[i] = 1;
        }

    }

    public abstract int insert(String add);
    public abstract void write(String add,String data);
    public abstract void search();
    public abstract void print();
    public abstract String read(String addr);
    public abstract void evict(String addr);
    public void updateCounterArray(int blockPos){

        for(int i=0;i<size;i++){
            counterArray[i]+=1;
        }
        counterArray[blockPos] = 0;
    }
    public void updateFIFO(int blockPos){
        queue.add(blockPos);
    }
    public void updateRandom(){

    }
//    public void updateReplacement(int blockPos,boolean isInsert){
//
//    }
}
