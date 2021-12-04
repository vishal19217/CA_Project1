public abstract class Cache {
    /*
    here word addressable so 1 block location is 1 word

     */
    int size,missPenalty,hitTime,blockSize;
    String tagArray[];
    String mem[];
    String dataArray[][];
    Cache(int size,int blockSize,String mem[]){
        this.size = size;
        this.mem = mem;
        this.blockSize = blockSize;
        tagArray = new String[size];
        dataArray = new String[size][blockSize];

    }

    public abstract void insert(String add,String data);
    public abstract void write();
    public abstract void search();
    public abstract void print();
}


