package bentchmarks;

import server.Item;

public class TestThread extends Thread {
	private Item item;
	private int number;
	private int op;
	private long start;
	
	public TestThread( Item i ){ this.item = i; }
	public TestThread( int n,Item i,int op ){ this.number = n; this.item = i; this.op=op; }
	
	public void run()
	{
		start = System.currentTimeMillis();
		try
		{ 
			if( op==1 ) { this.item.remove( 1 ); }
			else { this.item.add( 1 ); }
		}
		
		catch( InterruptedException e ){ e.printStackTrace(); }
		
		finally
		{
			StringBuilder sb = new StringBuilder();
			sb.append( this.item.toString() + " :: " );
			sb.append( "Thread_"+ this.number );
			sb.append( ", ExcTime :"+( System.currentTimeMillis() - this.start ) );
			System.out.println( sb.toString() );
		}
		
	}
}
