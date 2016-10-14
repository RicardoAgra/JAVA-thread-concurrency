package server;

public class Item {
	private String nome = "-Empty-";
	private int quantidade = 0;
	
	public Item( String nome,int quantidade )
	{
		this.nome=nome;
		this.quantidade=quantidade;
	}
	
	public synchronized void remove( int quantidade ) throws InterruptedException
	{ 
		while( this.quantidade<quantidade )
		{
			wait();
		}
		this.quantidade -= quantidade;
	}
	
	public synchronized void add( int quantidade )
	{ 
		this.quantidade += quantidade;
		/* Since it is always legal to add more quantity to the items
		 * threads will never be put to sleep here.
		 * And since this is the only action that allows sleeping threads on
		 * not enough quantity to be removed, it is the only time threads
		 * sleeping threads need to be notified.
		 */
		notifyAll();
	}
	
	@Override
	public synchronized String toString()
	{
		return nome+" : "+quantidade+"\n";
	}
}
