package Backend;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CombinationIterator implements Iterator<int[]> {
	  private final int positions = 5;
	    private final int total = 60000;
	    private int index = 0;
	    private final int[] buffer = new int[positions];

	    public CombinationIterator() {
	    }

	    @Override
	    public boolean hasNext() {                 
	        return index < total;
	    }

	    @Override  
	    public int[] next() {

	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }

	        int v = index; 
	        for (int i = 0; i < positions; i++) {
	            buffer[i] = (v % 9) + 1;
	            v /= 9;
	        }

	        index++;
	        return buffer;
	    }

	}   
	

