package dao;

import dao.WebcamDaoImpl;

public class DaoFactory {
	
	private static DaoFactory instance = new DaoFactory();
	
	private DaoFactory() {		
	}
	
	public static DaoFactory getInstance() {
		return instance;
	}
	
	public WebcamDao getWebcamDao() {
		return new WebcamDaoImpl();
	}
	
	public BenutzerDao getBenutzerDao() {
		return new BenutzerDaoImpl();
	}
}
