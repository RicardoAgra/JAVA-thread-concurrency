package bentchmarks;

import java.util.Random;

import server.*;

public class Tests {
	
	public static void main( String[] args ) throws InterruptedException
	{
		int thread_num = 100000;
		Random rnd = new Random();
	
		Item item = new Item( "Item", 1000 );
		
		int c=1;
		while( c < thread_num )
		{
			int r = rnd.nextInt( 2 )+1;
			TestThread t = new TestThread( c,item,r );
			t.start();
			c++;
		}
	}
	
}
