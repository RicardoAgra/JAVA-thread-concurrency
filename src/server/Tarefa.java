package server;
import java.util.TreeMap;
/*
 * 
 * 
 */
public class Tarefa
{
	private String nome;
	private TreeMap< String,Integer >items;
	
	public Tarefa( String nome,TreeMap< String,Integer >items )
	{
		this.nome = nome;
		this.items = items;
	}
	
	public String getNome(){ return this.nome; }
	
	public TreeMap< String,Integer >getItems(){ return this.items; }
	
	@Override
	public String toString()
	{
		return this.items.toString();
	}
}
