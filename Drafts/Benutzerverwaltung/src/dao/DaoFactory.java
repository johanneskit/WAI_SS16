package dao;

import dao.BenutzerDaoImpl;

public class DaoFactory {
	
	private static DaoFactory instance = new DaoFactory();
	
	private DaoFactory() {		
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public BenutzerDao getBenutzerDao() {
		return new BenutzerDaoImpl();
	}
}
