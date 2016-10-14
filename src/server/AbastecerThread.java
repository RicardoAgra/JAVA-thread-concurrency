package server;
public class AbastecerThread extends Thread {
	private Armazem armazem;
	private String item;
	private int quantidade;
	
	public AbastecerThread( Armazem armazem,String item,int quantidade )
	{ 
		this.armazem = armazem;
		this.item = item;
		this.quantidade = quantidade;
	}
	
	public void run(){ armazem.abastecer( item,quantidade ); }

}
