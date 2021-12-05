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
    Cache(int size,int blockSize,String mem[],int writePolicy){
        this.size = size;
        this.writePolicy = writePolicy;
        this.mem = mem;
        this.blockSize = blockSize;
        tagArray = new String[size];
        counterArray = new int[size];
        dataArray = new String[size][blockSize];
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
}


