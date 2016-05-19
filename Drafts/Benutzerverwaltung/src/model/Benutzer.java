package model;

public class Benutzer {
	 	private Integer id;
	    private String benutzername;
	    private int prioritaet;
	    private String passwort;
	    
	    public Integer getId() 
	    {
	        return id;
	    }

	    public void setId(int id) 
	    {
	        this.id = id;
	    }
		
		public String getBenutzername() 
		{
			return benutzername;
		}

		public void setBenutzername(String benutzername) 
		{
			this.benutzername = benutzername;
		}

		public int getPrioritaet() 
		{
			return prioritaet;
		}
		
		public void setPrioritaet(int prioritaet) 
		{
			this.prioritaet = prioritaet;
		}
		
		public String getPasswort() 
		{
			return passwort;
		}

		public void setPasswort(String passwort) 
		{
			this.passwort = passwort;
		}
	}