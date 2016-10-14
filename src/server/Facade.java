package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.concurrent.locks.ReentrantLock;

public class Facade implements BusinessIO {
	private Armazem armazem;
	private Tarefas tarefas;
	private Clientes clientes;
	ReentrantLock notify_lock;
	private HashMap< String,ArrayList< String >>notificacoes;
	
	public Facade ()
	{
		this.armazem = new Armazem();
		this.clientes = new Clientes();
		this.tarefas = new Tarefas( armazem,clientes );
		this.notificacoes = new HashMap< String,ArrayList< String > >();
		clientes.addListner( this );
		this.notify_lock = new ReentrantLock();
	}
	
	
	public boolean registar_cliente( String cliente,String password )
	{
		return clientes.registar_cliente( cliente,password );
	}
	
	
	public boolean cliente_login( String cliente,String password )
	{
		return clientes.cliente_login( cliente,password );
	}
	
	
	public String listar_clientes()
	{
		return clientes.toString();
	}
	
	
	public void abastecer( String item,int quantidade )
	{ 
		tarefas.abastecer( item,quantidade );
	}

	
	public boolean definir_tarefa( String nome,TreeMap< String,Integer >items )
	{	
		return tarefas.definir_tarefa( nome,items ); 
	}
	
	
	public long iniciar_tarefa( String tarefa )
	{ 
		return tarefas.iniciar_tarefa( tarefa );
	}
	
	
	public String concluir_tarefa( Long tarefa_id )
	{
		return tarefas.concluir_tarefa( tarefa_id );
	}
	
	
	public boolean pedido_notificacao( String cliente,ArrayList< Long > tarefas ) 
	{
		return clientes.pedido_notificacao( cliente,tarefas );
	}
	
	
	public String listar_items()
	{
		return armazem.toString();
	}
	
	
	public String listar_tarefas()
	{ 
		return tarefas.listar_tarefas();
	}
	
	
	public String listar_tarefas_activas()
	{ 
		return tarefas.listar_tarefas_ativas();
	}
	
	
	public String listar_tarefas_concluidas()
	{ 
		return tarefas.listar_tarefas_concluidas();
	}
	
	
	protected void notificar( String cliente, String tarefa )
	{
		if( notificacoes.containsKey( cliente ))
			notificacoes.get( cliente ).add( tarefa );
		
		else
		{
			ArrayList< String >tarefas = new ArrayList< String >();
			tarefas.add( tarefa );
			notificacoes.put( cliente,tarefas );
		}
	}
	
	// WARNING : This is a Critical Zone, where the API user must assure correct 
	//           use of notifications and its consumption.
	//			 - Notification use was delegated to the client. -
	
	public HashMap< String,ArrayList< String >>listar_notificacoes()
	{
		this.notify_lock.lock();
		
		try { return notificacoes; }
		
		finally { this.notify_lock.unlock(); }
	}
	
	
	public void consumir_notificacao( String cliente )
	{
		this.notify_lock.lock();
		try
		{
			if( this.notificacoes.containsKey( cliente ) )
				this.notificacoes.remove( cliente );
		}
		finally { this.notify_lock.unlock(); }
	}
}
