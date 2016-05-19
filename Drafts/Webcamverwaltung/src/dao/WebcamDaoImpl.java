package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exception.*;
import jndi.JndiFactory;

import model.Webcam;

public class WebcamDaoImpl implements WebcamDao {
	
	final JndiFactory jndi = JndiFactory.getInstance();
	
	@Override
	public void delete(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");		
			PreparedStatement pstmt = connection.prepareStatement("delete from webcams where id = ?");
			pstmt.setInt(1, id);
			pstmt.executeUpdate();			
		} catch (Exception e) {
			throw new WebcamNotDeletedException(id);
		} finally {
			closeConnection(connection);
		}
	}

	@Override
	public Webcam get(Integer id) {
		
		if (id == null)
			throw new IllegalArgumentException("id can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");			
			PreparedStatement pstmt = connection.prepareStatement("select name, url from webcams where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();							
			if (rs.next()) {
				Webcam webcam = new Webcam();
				webcam.setId(id);
				webcam.setName(rs.getString("name"));
				webcam.setUrl(rs.getString("url"));
				return webcam;
			} else {
				throw new WebcamNotFoundException(id);
			}			
		} catch (Exception e) {
			throw new WebcamNotFoundException(id);
		} finally {	
			closeConnection(connection);
		}
	}

	@Override
	public void save(Webcam webcam) {
		
		if (webcam == null)
			throw new IllegalArgumentException("webcam can not be null");
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");	
			if (webcam.getId() == null) {
				PreparedStatement pstmt = connection.prepareStatement("insert into webcams (name, url) values (?,?)");
				pstmt.setString(1, webcam.getName());
				pstmt.setString(2, webcam.getUrl());
				pstmt.executeUpdate();
			} else{
				PreparedStatement pstmt = connection.prepareStatement("update webcams set name = ?, url = ? where id = ?");
				pstmt.setString(1, webcam.getName());
				pstmt.setString(2, webcam.getUrl());
				pstmt.setInt(3, webcam.getId());
				pstmt.executeUpdate();
			}			
		} catch (Exception e) {
			throw new WebcamNotSavedException();
		} finally {
			closeConnection(connection);
		}
	}	

	@Override
	public List<Webcam> list() {
		
		List<Webcam> webcamList = new ArrayList<Webcam>();
		
		Connection connection = null;		
		try {
			connection = jndi.getConnection("jdbc/libraryDB");			
			
				PreparedStatement pstmt = connection.prepareStatement("select name, url, id from webcams");				
				ResultSet rs = pstmt.executeQuery();
								
				while (rs.next()) {
					Webcam webcam = new Webcam();
					webcam.setId(rs.getInt("id"));
					webcam.setName(rs.getString("name"));
					webcam.setUrl(rs.getString("url"));
					webcamList.add(webcam);
				}			
			
			return webcamList;
		} catch (Exception e) {
			throw new WebcamNotFoundException();
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
