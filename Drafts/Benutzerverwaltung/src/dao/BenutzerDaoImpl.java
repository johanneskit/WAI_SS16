package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.*;
import jndi.JndiFactory;

import model.Benutzer;

public class BenutzerDaoImpl implements BenutzerDao {
	
	final JndiFactory jndi = JndiFactory.getInstance();
	
	@Override
	public void delete(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");		
			PreparedStatement pstmt = connection.prepareStatement("delete from benutzer where id = ?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();			
		} catch (Exception e) {
			throw new BenutzerNotDeletedException(id);
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public Benutzer get(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");			
			PreparedStatement pstmt = connection.prepareStatement("select benutzername, passwort, prioritaet from benutzer where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				Benutzer benutzer = new Benutzer();
				benutzer.setId(id);
				benutzer.setBenutzername(rs.getString("benutzername"));
				benutzer.setPasswort(rs.getString("passwort"));
				benutzer.setPrioritaet(rs.getInt("prioritaet"));
				return benutzer;
			} else {
				throw new BenutzerNotFoundException(id);
			}			
		} catch (Exception e) {
			throw new BenutzerNotFoundException(id);
		} finally {	
			closeConnection(connection);
		}
	}

	@Override
	public void save(Benutzer benutzer) {
		
		if (benutzer == null)
			throw new IllegalArgumentException("benutzer can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");			
				PreparedStatement pstmt = connection.prepareStatement("update benutzer set prioritaet = ? where id = ?");
				pstmt.setInt(1, benutzer.getPrioritaet());
				pstmt.setInt(2, benutzer.getId());
				pstmt.executeUpdate();
			//}			
		} catch (Exception e) {
			throw new BenutzerNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}	

	@Override
	public List<Benutzer> list() {
		
		List<Benutzer> benutzerList = new ArrayList<Benutzer>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");			
			
				PreparedStatement pstmt = connection.prepareStatement("select benutzername, passwort, id, prioritaet from benutzer");				
				ResultSet rs = pstmt.executeQuery();
								
				while (rs.next()) {
					Benutzer benutzer = new Benutzer();
					benutzer.setId(rs.getInt("id"));
					benutzer.setBenutzername(rs.getString("benutzername"));
					benutzer.setPasswort(rs.getString("passwort"));
					benutzer.setPrioritaet(rs.getInt("prioritaet"));
					benutzerList.add(benutzer);
				}			
			
			return benutzerList;
		} catch (Exception e) {
			throw new BenutzerNotFoundException();
		} finally {	
			closeConnection(connection);
		}
	}
	
	private void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				// nothing to do
				e.printStackTrace();
			}				
		}
	}
}
