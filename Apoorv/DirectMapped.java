package CA_Project;

public class DirectMapped extends Cache{

    int offset, index, tagSize;

    public DirectMapped(int size, int blockSize, String mem[], int writePolicy){
        super(size, blockSize, mem, writePolicy);
        offset = (int)(Math.log(blockSize)/Math.log(2));
        index = (int)(Math.log(size)/Math.log(2));
        tagSize = 32 - index - offset;
    }

    @Override
    public int insert(String address){
        String tag = address.substring(0, tagSize);
        String idxBin = address.substring(tagSize, tagSize+index); // index in binary
        int idxInt = Integer.parseInt(idxBin, 2);
        String loc = tag + idxBin;
        String zeros = "0".repeat(offset);
        loc = loc + zeros;
        int startIdx = Integer.parseInt(loc, 2); // start index for a block
        if(tagArray[idxInt] == null){
            tagArray[idxInt] = "10" + tag;
            for(int j = 0 ; j<blockSize ; j++){
                dataArray[idxInt][j] = mem[startIdx+j];
            }
            return idxInt;
        }
        return 0;
    }

    @Override
    public void write(String address, String data){
        String tag = address.substring(0, tagSize);
        String idxBin = address.substring(tagSize, tagSize+index);
        String dataLocBin = address.substring(tagSize+index, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);
        int idxInt = Integer.parseInt(idxBin, 2);
        if(tagArray[idxInt] == null){
            insert(address);
            dataArray[idxInt][dataLoc] = data;
        }else if(tagArray[idxInt].substring(2).equals(tag)){ // cache hit
            dataArray[idxInt][dataLoc] = data;
        }else{ // cache miss
            evict(address);
            insert(address);
            dataArray[idxInt][dataLoc] = data;
        }
        if(writePolicy == 1){ // write policy is write through
            mem[Integer.parseInt(address, 2)] = data;
        }else{ // write policy is write back
            tagArray[idxInt] = "11"+tagArray[idxInt].substring(2);
        }
        return;
    }

    @Override
    public void search(){

    }

    @Override
    public void print(){

    }

    @Override
    public String read(String address){
        String tag = address.substring(0, tagSize);
        String idxBin = address.substring(tagSize, tagSize+index);
        String dataLocBin = address.substring(tagSize+index, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);
        int idxInt = Integer.parseInt(idxBin, 2);
        if(tagArray[idxInt] == null){
            insert(address);
            return dataArray[idxInt][dataLoc];
        }else if(tagArray[idxInt].substring(2).equals(tag)){ // cache hit
            return dataArray[idxInt][dataLoc];
        }
        evict(address); // cache miss
        insert(address);
        return dataArray[idxInt][dataLoc];
    }

    @Override
    public void evict(String address){
        String tag = address.substring(0, tagSize);
        String idxBin = address.substring(tagSize, tagSize+index); // index in binary
        int idxInt = Integer.parseInt(idxBin, 2);
        String loc = tag + idxBin;
        String zeros = "0".repeat(offset);
        loc = loc + zeros;
        int startIdx = Integer.parseInt(loc, 2); // start index for a block
        if(writePolicy != 1){ // write policy is write back
            for(int i = 0 ; i<blockSize ; i++){
                mem[startIdx+i] = dataArray[idxInt][i];
            }
        }
        for(int i = 0 ; i<blockSize ; i++){
            dataArray[idxInt][i] = null;
        }
        tagArray[idxInt] = null;
        return;
    }

    public static void main(String args[]){

    }
}
