package server;

public class Counter {
	private long c;
	
	public Counter(){ c=0; }
	
	public synchronized long getC() 
	{ 		
		if( c>9999998 ) // Never Return 0, by convention.
			c = 0;
		
		c++;
		
		return c;
	}
}
