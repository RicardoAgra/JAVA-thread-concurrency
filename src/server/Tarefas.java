package server;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Tarefas {
	private Armazem armazem;
	private Clientes clientes;
	private Counter counter;
	private HashMap< String,Tarefa >tarefas;
	private HashMap< Long,TarefaThread >activas;
	private HashMap< Long,String >concluidas;
	
	
	public Tarefas( Armazem armazem, Clientes clientes )
	{
		this.armazem = armazem;
		this.clientes = clientes;
		counter = new Counter();
		tarefas = new HashMap< String,Tarefa >();
		activas = new HashMap< Long,TarefaThread >();
		concluidas = new HashMap< Long,String >();
	}
	
	
	public void abastecer( String item,int quantidade )
	{
		new AbastecerThread( armazem,item,quantidade ).start();
	}
	
	
	public boolean definir_tarefa( String nome,TreeMap< String,Integer >items )
	{
		if( tarefas.containsKey( nome ))
		{
			System.out.println( "FAIL : Nome da tarefa em uso." );
			return false;
		}
		
		if( !validar_tarefa( items ))
		{
			System.out.println( "FAIL : Tarefa contêm valores negativos." );
			return false;
		}
		
		tarefas.put( nome,new Tarefa( nome,items ));
		return true;
	}
	
	
	private boolean validar_tarefa( TreeMap< String,Integer >items )
	{		
		for( Map.Entry< String,Integer >entry : items.entrySet() )
		{
			if( entry.getValue() < 1 )
				return false;
		}
		
		return true;
	}
	
	
	public synchronized long iniciar_tarefa( String tarefa )
	{
		long tarefa_id = counter.getC();
		TarefaThread thread = new TarefaThread( armazem,tarefa_id,tarefas.get( tarefa ),this );
		
		activas.put( tarefa_id,thread );
		
		thread.start();
		return tarefa_id;
	}
	
	
	public synchronized String concluir_tarefa( Long tarefa_id )
	{
            TarefaThread thread;

            if( concluidas.containsKey( tarefa_id ) )
            {	
            	clientes.notificar( tarefa_id,concluidas.get( tarefa_id ) );
            	concluidas.remove( tarefa_id );
                return "Concluida";
            }
            
            if( activas.containsKey( tarefa_id ) )
            {
                thread = activas.get( tarefa_id );
                thread.interrupt();
                activas.remove( tarefa_id );                
                clientes.notificar( tarefa_id,thread.getTarefa().getNome() );
                
                return "Interrompida";
            }

            return "fail";
	}
	
	public synchronized void listen( Long tarefa_id )
	{
		if( activas.containsKey( tarefa_id ) )
		{
			concluidas.put( tarefa_id , activas.get( tarefa_id ).getTarefa().getNome() );
			activas.remove( tarefa_id );
		}
	}
	
	public synchronized String listar_tarefas()
	{
		StringBuilder s = new StringBuilder();
		s.append("- Lista de Tarefas: \n");
		for( Map.Entry< String,Tarefa >entry : tarefas.entrySet() ) 
		  s.append( entry.getKey()+":"+entry.getValue().toString()+" ");
		return s+"\n";
	}
	
	public synchronized String listar_tarefas_ativas()
	{
		StringBuilder s = new StringBuilder();
		s.append("- Tarefas Em Execucao: \n");
		for( Entry<Long, TarefaThread> entry : activas.entrySet() )
		  s.append( entry.toString()+"\n");
		return s.toString();
	}
	
	public synchronized String listar_tarefas_concluidas()
	{
		StringBuilder s = new StringBuilder();
		s.append("- Tarefas Conlcuidas: \n");
		for( Map.Entry< Long,String >entry : concluidas.entrySet() ) 
		  s.append( entry.toString()+"\n");
		return s.toString();
	}

}
