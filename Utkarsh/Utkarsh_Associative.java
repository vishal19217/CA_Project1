package Practice;
import java.util.*;


abstract class Cache{
int writePolicy;
int counterArray[];
String tagArray[];
String mem[];
String dataArray[][];
int size;
int blockSize;
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

public abstract void insert(String add,String data);
public abstract void write();
public abstract int insert(String address);
public abstract void write(String add,String data);
public abstract void search();
public abstract void print();
public abstract String read(String addr);
public abstract void evict(String addr);

public class Associative extends Cache{

    int offset, tagSize;

    public Associative(int size, int blockSize, String mem[], int writePolicy){
        super(size, blockSize, mem, writePolicy);
        offset = (int)(Math.log(blockSize)/Math.log(2));
        tagSize = 32 - offset;
    }
    
    public int insert(String address) {
    	String tag = address.substring(0, tagSize);
    	int addInt = Integer.parseInt(address,2);
    	String loc = tag;
        String zeros = "0".repeat(offset);
        loc = loc + zeros;
        int startIdx = Integer.parseInt(loc, 2); // start index for a block 
        String[] blockdata = new String[blockSize];
        String[] blockadd = new String[blockSize];
        for(int i = 0;i<blockSize;i++) {
        	blockdata[i] = mem[startIdx+i];
        	blockadd[i] = Integer.toBinaryString(startIdx+i);
        }
        
        // FIFO Implementation
        String[] tempd = blockdata;
        String tempa = "10"+tag;
        
        for(int j = 0;j<size;j++) {
        	String[] TEMPD = tempd;
        	String TEMPA = tempa;
        	tempd = dataArray[j];
        	tempa = tagArray[j];
        	dataArray[j] = TEMPD;
        	tagArray[j] = TEMPA;
        }
        
        if(tempa.substring(1,2).equals("1")) {            // Checking if the evicted block is modified and if yes 
        	String tag2 = tempa.substring(2,tagSize+2);   // then updating it in the memory.
        	String loc2 = tag;
            String zeros2 = "0".repeat(offset);
            loc2 = loc2 + zeros2;
            int startIdx2 = Integer.parseInt(loc2, 2);
        	for(int i = 0;i<blockSize;i++) {
        		mem[startIdx2+i] = tempd[i];
        	}       	
        }
        
        int pos = 0;
        return(pos);            // Returns position of inserted block in Cache  
    	
    }
    
    @Override
    public void write(String address, String data){
        String tag = address.substring(0, tagSize);
        //String idxBin = address.substring(tagSize, tagSize+index);
        String dataLocBin = address.substring(tagSize, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);
        int done = 0;
        for(int i = 0;i<size;i++) {
        	if(tagArray[i].substring(2,tagSize+2).equals(tag)) {  // Cache hit
        		done = 1;
        		if(writePolicy == 1){ // write policy is write through
        			tagArray[i] = "10"+tag;
        			dataArray[i][dataLoc] = data;
        			mem[Integer.parseInt(address, 2)] = data;
        		}	
        		else{ // write policy is write back
        			tagArray[i] = "11"+tag;
        			dataArray[i][dataLoc] = data;
        		}
        	}
        }
        
        if(done==0) {     // Cache miss
    		int in = insert(address);
    		if(writePolicy == 1){ // write policy is write through
    			tagArray[in] = "10"+tag;
    			dataArray[in][dataLoc] = data;
    			mem[Integer.parseInt(address, 2)] = data;
    		}	
    		else{ // write policy is write back
    			tagArray[in] = "11"+tag;
    			dataArray[in][dataLoc] = data;
    		}
    	}
        
        return;
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
        for(int i = 0;i<size;i++) {
        	if(tagArray[i].substring(2,tagSize+2).equals(tag)) { 
        		if(writePolicy != 1){                    // write policy is write back
        			for(int j = 0 ; i<blockSize ; j++){
        				mem[startIdx+j] = dataArray[i][j];
        			}
        		}
        
        		for(int j = 0 ; j<blockSize ; j++){
        			dataArray[i][j] = null;
        		}
        		tagArray[i] = null;
        	}
        }
        
        return;
    }
    
    @Override
    public String read(String address){
        String tag = address.substring(0, tagSize);
        //String idxBin = address.substring(tagSize, tagSize+index);
        String dataLocBin = address.substring(tagSize, 32);
        int dataLoc = Integer.parseInt(dataLocBin, 2);

        for(int i = 0;i<size;i++) {
        	if(tagArray[idxInt] == null){
        		insert(address);
        		return dataArray[idxInt][dataLoc];
        	}
        	if(tagArray[i].substring(2,tagSize).equals(tag)){ // cache hit
        		return(dataArray[i][dataLoc]);
        	}
        }
        
        int in = insert(address);
        
        return(dataArray[in][dataLoc]);
    }
    
    @Override
    public void search(){

    }

    @Override
    public void print(){

    }
    
}
}


public class practice2 {
	static Scanner key = new Scanner(System.in);
	static void Full_associative_cache() {
		
	}
	
	public static void main(String[] args) {
		//String n = key.nextLine();
		for(int i = 1;i<=1;i++) {
			System.out.println("YES");
		}
		
		//System.out.println(countSort(n));
		/*int n = key.nextInt();
		int[] arr1 = new int[n];
		for(int i = 0;i<n;i++) {
			arr1[i] = key.nextInt();
		}
		int m = key.nextInt();
		int[] arr2 = new int[m];
		for(int i = 0;i<m;i++) {
			arr2[i] = key.nextInt();
		}
		
		System.out.println(findUnion(arr1, arr2, n, m));*/
	}

}
