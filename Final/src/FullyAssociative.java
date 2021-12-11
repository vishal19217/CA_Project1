import java.util.*;


    public class FullyAssociative extends Cache{

        int offset, tagSize;

        public FullyAssociative(int size, int blockSize, String mem[], int writePolicy,int replacePolicy){
            super(size, blockSize, mem, writePolicy,replacePolicy);
            offset = (int)(Math.log(blockSize)/Math.log(2));
            tagSize = 32 - offset;
        }

        public int insert(String address) {
            String tag = address.substring(0, tagSize);
            String loc = tag;
            String zeros = "0".repeat(offset);
            loc = loc + zeros;
            int startIdx = Integer.parseInt(loc, 2); // start index for a block
//            String[] blockdata = new String[blockSize];
//            String[] blockadd = new String[blockSize];
            for (int i = 0; i < size; i++) {
                if (tagArray[i] == null) {
                    tagArray[i] = "10"+tag;
                    for (int j = 0; j < blockSize; j++) {
                        dataArray[i][j] = mem[startIdx + j];
                    }
                }
                return i;
            }
            return 0;
        }
//
//            // FIFO Implementation
//            String[] tempd = blockdata;
//            String tempa = "10"+tag;
//
//            for(int j = 0;j<size;j++) {
//                String[] TEMPD = tempd;
//                String TEMPA = tempa;
//                tempd = dataArray[j];
//                tempa = tagArray[j];
//                dataArray[j] = TEMPD;
//                tagArray[j] = TEMPA;
//            }
//
//            if(tempa.substring(1,2).equals("1")) {            // Checking if the evicted block is modified and if yes
//                String tag2 = tempa.substring(2,tagSize+2);   // then updating it in the memory.
//                String loc2 = tag;
//                String zeros2 = "0".repeat(offset);
//                loc2 = loc2 + zeros2;
//                int startIdx2 = Integer.parseInt(loc2, 2);
//                for(int i = 0;i<blockSize;i++) {
//                    mem[startIdx2+i] = tempd[i];
//                }
//            }
//
//            int pos = 0;
//            return(pos);            // Returns position of inserted block in Cache
//
//        }


//        if(writePolicy == 1){ // write policy is write through
//            tagArray[i] = "10"+tag;
//            dataArray[i][dataLoc] = data;
//            mem[Integer.parseInt(address, 2)] = data;
//        }
//                    else{ // write policy is write back
//            tagArray[i] = "11"+tag;
//            dataArray[i][dataLoc] = data;
//        }
        @Override
        public void write(String address, String data){
            String tag = address.substring(0, tagSize);
            //String idxBin = address.substring(tagSize, tagSize+index);
            String dataLocBin = address.substring(tagSize, 32);
            int dataLoc = Integer.parseInt(dataLocBin, 2);
            int done = 0;
            int blockPos =-1;
            //checking cache hit
            for(int i = 0;i<size;i++) {
                if(tagArray[i]!=null && tagArray[i].substring(2).equals(tag)) {  // Cache hit
                    done = 1;
                    blockPos = i;
                }
            }

            if(done==0) {     // Cache miss
                int f2 = 0;
                //check if empty slots
                for (int i = 0; i < size; i++) {
                    if (tagArray[i] == null) {
                        blockPos = insert(address);
                        f2 = 1;
                        break;
                    }
                }
                if (f2 == 0) {
                    evict(address);
                    blockPos = insert(address);
                }
            }

            dataArray[blockPos][dataLoc] = data;

                if(writePolicy == 1){ // write policy is write through
                    mem[Integer.parseInt(address, 2)] = data;
                }
                else{ // write policy is write back
                    tagArray[blockPos] = "11"+tag;
                }

            //LRU
            if(replacePolicy==1){
                updateCounterArray(blockPos);
            }
            //FIFO
            else if(replacePolicy==2){
                updateFIFO(blockPos);
            }

        }


        @Override
        public void evict(String address){
            String tag = address.substring(0, tagSize);
            //String idxBin = address.substring(tagSize, tagSize+index); // index in binary
            //int idxInt = Integer.parseInt(idxBin, 2);
            String loc = tag;
            String zeros = "0".repeat(offset);
            loc = loc + zeros;
            int startIdx = Integer.parseInt(loc, 2); // start index for a block
            //LRU
            int blockPos = -1;
            if(replacePolicy==1){
                int mx = -1;
                for (int i = 0; i < size; i++) {
                    if (counterArray[i] > mx) {
                        mx = counterArray[i];
                        blockPos = i;
                    }
                }
            }
            else if(replacePolicy==2){

            }
            else{

            }
            if(writePolicy!=1){

            }

            for(int i=0;i<blockSize;i++){
                //writeBack
                if(tagArray[blockPos].substring(1,2).equals("1")&&writePolicy!=1){
                    mem[startIdx+i] = dataArray[blockPos][i];
                }

                dataArray[blockPos][i] = null;
            }
            tagArray[blockPos] = null;

            return;
        }

        @Override
        public String read(String address){
            String tag = address.substring(0, tagSize);
            //String idxBin = address.substring(tagSize, tagSize+index);
            String dataLocBin = address.substring(tagSize, 32);
            int dataLoc = Integer.parseInt(dataLocBin, 2);
            //Check Cache Hit
            for(int i = 0;i<size;i++) {
                if(tagArray[i]!=null && tagArray[i].substring(2,tagSize).equals(tag)){ // cache hit
                    return(dataArray[i][dataLoc]);
                }
            }
//            Cache Miss
            int f=1;
            //checking if empty slots
            int blockPos= -1;
            for(int i=0;i<size;i++){
                if(tagArray[i]==null){
                    f=1;
                    break;
                }
            }
            //if empty slots present
            if(f==1){
                blockPos = insert(address);

            }

            //no empty slots
            else{
                evict(address);
                blockPos = insert(address);
            }

//            System.out.println("data:"+dataArray[blockPos][dataLoc]);
            return dataArray[blockPos][dataLoc];
        }

        @Override
        public void search(){

        }

        @Override
        public void print(){
            System.out.println("Set No:-"+0);

            for(int i=0;i<size;i++){
                for(int j=0;j<blockSize;j++){
                    System.out.print(dataArray[i][j]+" ");
                }
                System.out.println();
            }
        }

    }


