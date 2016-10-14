package server;

public class Cliente {
	private String nome;
	private String password;
	private boolean validado = false;

	public Cliente( String nome,String password )
	{
		this.nome = nome;
		this.password = password;
	}
	
	public String getNome(){ return this.nome; }
	public String getPassword(){ return this.password; }
	public boolean getvalidado(){ return this.validado; }
	
	public boolean login( String password )
	{ 
		this.validado = this.password.equals( password );
		return validado;
	}
	
	@Override
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		s.append( "Nome: "+nome+", " );
		s.append( "Validado: "+validado );
		return s.toString();
	}
}
