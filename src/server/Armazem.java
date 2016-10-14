package server;
import java.util.HashMap;
import java.util.Map;
/*
 * 
 * 
 */
public class Armazem {	
	private Map< String,Item >armazem = new HashMap< String,Item >();
	
	public void consumir( Map< String,Integer >items ) throws InterruptedException 
	{
		for( Map.Entry< String,Integer >entry : items.entrySet() ) 
		{
			Item i = armazem.get( entry.getKey() );
			if( i!=null )
				i.remove( entry.getValue() );
		}
	}
	
	public void abastecer( String item,int quantidade )
	{
		if( armazem.containsKey( item ))
			armazem.get( item ).add( quantidade );
		
		// The only critical zone is when adding new items to the warehouse :
		// - Two competing threads may get the same map address to store the new item, 
		// it can result in memory override.
		
		else 
			synchronized( this ){ armazem.put( item,new Item( item,quantidade )); }
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		
		s.append("- Armazem: \n");
		for( Item item : armazem.values() ) 
		  s.append( item.toString());
		
		return s.toString();
	}
}
