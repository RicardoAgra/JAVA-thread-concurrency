package bentchmarks;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.TreeMap;

import server.*;

public class Console {
	private static BusinessIO facade;
	private static Scanner s;
        
	public static void main( String[] args )
	{
		try
		{
            facade = new Facade();
            int op=-1;
            
            teste1( facade );

            while( op != 0 )
            {
                    System.out.println( menu() );
                    System.out.print( "> " );
                    op = readOp( 14 );
                    menuHandler( op,facade );
            }
		}
		catch( Exception ex ){ ex.printStackTrace(); }
	}
	
	public static void teste1( BusinessIO facade )
	{
		facade.registar_cliente( "admin","admin" );
		facade.cliente_login( "admin","admin" );
		
		TreeMap<String, Integer> items0, items1, items2;
		
		facade.abastecer("a", 10);
		facade.abastecer("b", 10);
		facade.abastecer("c", 10);
		facade.abastecer("d", 10);
		facade.abastecer("e", 10);
		facade.abastecer("f", 10);
		facade.abastecer("g", 10);
		
		items0 = new TreeMap< String,Integer >();
		items1 = new TreeMap< String,Integer >();
		items2 = new TreeMap< String,Integer >();
		
		items0.put( "a",4 );
		items1.put( "b",1 );
		items2.put( "e",2 ); items2.put( "c",2 );
	
		facade.definir_tarefa( "Tarefa-A",items0 );
		facade.definir_tarefa( "Tarefa-B",items1 );
		facade.definir_tarefa( "Tarefa-C+E",items2 );
		
		facade.iniciar_tarefa( "Tarefa-A" );
		facade.iniciar_tarefa( "Tarefa-A" );
		facade.iniciar_tarefa( "Tarefa-A" );
		facade.iniciar_tarefa( "Tarefa-A" );
		facade.iniciar_tarefa( "Tarefa-A" );
		facade.iniciar_tarefa( "Tarefa-A" );
		//facade.abastecer( "a",10 );
		 
		System.out.println( facade.listar_tarefas() );
		System.out.println( facade.listar_tarefas_activas() );
		System.out.println( facade.listar_tarefas_concluidas() );
		System.out.println( facade.listar_items() );
	}
	
	public static String menu()
	{  
		StringBuilder s = new StringBuilder();
		s.append("\n1 : Registar Cliente\t\t2 : Cliente Login\n");
		s.append("3 : Abastecer\t\t\t4 : Definir Tarefa\n");
		s.append("5 : Iniciar Tarefa\t\t6 : Concluir Tarefa\n");
		s.append("7 : Pedido Notificacao\t\t8 : Listar Notificacoes\n");
		s.append("9 : Listar Items\t\t10: Listar Tarefas\n");
		s.append("11: Listar Tarefas Activas\t12: Listar Tarefas Concluidas\n");
		s.append("13: Listar Clientes\t\t14: Consumir Notificacao\n");
		s.append("0 : Sair");
		return s.toString();
	}
	
	public static void menuHandler( int op, BusinessIO facade )
	{
		boolean b;
		
		switch( op )
		{
			case 1 : b=facade.registar_cliente( read( "Nome" ),read( "Password" ) );
			 				 System.out.print( "> Server: "+b+"\n" );
							 break;
			
			case 2 : b=facade.cliente_login( read( "Nome" ),read( "Password" ) );
			 				 System.out.println( "> Server: "+b+"\n" );
							 break;
							 
			case 3 : facade.abastecer( read( "Item" ),readInt( "Quantidade" ) );
				 	 break;
				 	 
			case 4 : facade.definir_tarefa( read( "Tarefa" ),readItems() );
					 break;
					 
			case 5 : facade.iniciar_tarefa( read( "Tarefa" ) );
					 break;
			
			case 6 : System.out.println( facade.concluir_tarefa( new Long( readInt( "Tarefa Id" ) )));
					 break;
					 
			case 7 : System.out.println( facade.pedido_notificacao( read( "Cliente" ) , readLongs( "Id Tarefa" ) ));
					 break;
					 
			case 8 : System.out.println( facade.listar_notificacoes() );
					 break;
					 
			case 9 : System.out.println( facade.listar_items() );
					 break;
					 
			case 10 : System.out.println( facade.listar_tarefas() );
					  break;
					 
			case 11 : System.out.println( facade.listar_tarefas_activas() );
					  break;
					
			case 12 : System.out.println( facade.listar_tarefas_concluidas() );		 
					  break;
					  
			case 13 : System.out.println( facade.listar_clientes() );
					  break;
					  
			case 14 : facade.consumir_notificacao( read( "Cliente" ) );
					  break;
		}
	}
	
	private static int readOp( int max )
	{
		int op = -1;
		s = new Scanner( System.in );
		while( op<0 )
		{
			try{
				op = s.nextInt();
				if( op>max )
					op = -1;
			} catch( InputMismatchException e )
			{ 
				System.out.println( "Opcao Invalida!" );
				s.next();
			}
		}
		return op;	
	}
	
	private static String read( String campo )
	{
		s = new Scanner( System.in );
		
		System.out.print( campo+" : " );
		return s.nextLine();
	}
	
	private static int readInt( String campo )
	{
		s = new Scanner( System.in );
		
		System.out.print( campo+" : " );
		return s.nextInt();
	}
	
	private static TreeMap< String,Integer >readItems()
	{
		TreeMap< String,Integer >items = new TreeMap< String,Integer >();
		s = new Scanner( System.in );
		String nome;
		Integer quantidade;
		boolean more=true;
		
		while( items.isEmpty() || more )
		{
			System.out.print( "item : " );
			nome = s.nextLine();
			
			System.out.print( "Quantidade : " );
			quantidade = new Integer( s.nextInt() );
			
			items.put( nome,quantidade );
			
			s.nextLine();
			System.out.print( "Deseja adicionar mais items?( y ): " );
			String read = s.nextLine();
			more = "y".equals( read );
		}
		
		return items;
	}
	
	private static ArrayList< Long >readLongs( String campo )
	{
		ArrayList< Long > longs = new ArrayList< Long >();
		s = new Scanner( System.in );
		boolean more=true;
		
		while( longs.isEmpty() || more )
		{
			System.out.print( campo + " : " );
			longs.add( s.nextLong() );
			
			s.nextLine();
			System.out.print( "Deseja continuar?( y ): " );
			String read = s.nextLine();
			more = "y".equals( read );
		}
		return longs;
	}
	
}
