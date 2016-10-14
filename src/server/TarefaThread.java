package server;

public class TarefaThread extends Thread {
	private Armazem armazem;
	private Long tarefa_id;
	private Tarefa tarefa;
	private Tarefas listner;
	
	public TarefaThread( Armazem armazem,Long tarefa_id,Tarefa tarefa,Tarefas listner )
	{
		this.armazem = armazem;
		this.tarefa_id = tarefa_id;
		this.tarefa = tarefa;
		this.listner = listner;
	}
	
	public void notifyListner(){ listner.listen( this.tarefa_id ); }
	public Tarefa getTarefa(){ return this.tarefa; }
	
	public void run() 
	{
		try { armazem.consumir( tarefa.getItems() ); } 
		
		catch( InterruptedException e ){} 
		
		finally { notifyListner(); }
	}

}
