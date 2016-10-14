package server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Clientes {
	private Map< String,Cliente >clientes;
	private Map< Long,ArrayList< String >>notificacoes;
	private Facade facade;
	
	
	public Clientes()
	{
		this.clientes = new HashMap< String,Cliente >();
		this.notificacoes = new HashMap< Long,ArrayList< String >>();
	}
	
	
	public void addListner( Facade facade ){ this.facade = facade; }
	
	
	public boolean registar_cliente( String cliente,String password )
	{ 
		if( clientes.containsKey( cliente ) )
			return false;
		
		clientes.put( cliente,new Cliente( cliente,password ) );
		return true;
	}
	
	
	public boolean cliente_login( String cliente,String password )
	{ 
		if( !clientes.containsKey( cliente ) )
			return false;
		
		return clientes.get( cliente ).login( password );
	}
	
	
	public boolean pedido_notificacao( String cliente, ArrayList< Long >tarefas )
	{
		boolean b = false;
		if( clientes.containsKey( cliente ) )
			if( clientes.get( cliente ).getvalidado() )
			{
				b = true;
				for( Long tarefa : tarefas )
				{
					if( notificacoes.containsKey( tarefa ) )
						notificacoes.get( tarefa ).add( cliente );
					else
					{
						ArrayList< String > clientes = new ArrayList< String >();
						clientes.add( cliente );
						notificacoes.put( tarefa,clientes );
					}
				}
			}
		return b;
	}
	
	public void notificar( Long tarefa_id,String tarefa_nome )
	{
		if( notificacoes.containsKey( tarefa_id ) )
		{
			for( String cliente : notificacoes.get( tarefa_id ) )
			{
				facade.notificar( cliente,tarefa_nome );
			}
		}
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append( "- Clientes\n" );
		for( Cliente cliente : clientes.values() )
			s.append( cliente.toString()+"\n" );
		return s.toString();
	}
}
