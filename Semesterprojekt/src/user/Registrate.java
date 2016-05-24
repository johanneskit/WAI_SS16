package user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import utils.JNDIFactory;

public class Registrate {
	
	Connection connection = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	
	String name = null;
	String password1 = null;
	String password2 = null;
	int priority = 4;

	boolean userExists = false;
	boolean pw_notEqual = false;

	public boolean UserAlreadyExists() {
		return userExists;
	}

	public boolean PwAreNotEqual() {
		return pw_notEqual;
	}

	public void feedUser(HttpServletRequest request) throws SQLException {
		this.userExists = false;
		this.pw_notEqual = false;

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");
			stmt = connection.createStatement();
			
			if (!request.getParameter("password1").equals(request.getParameter("password2"))) {
				this.pw_notEqual = true;
				return;
			}

			String newUser = request.getParameter("newUsername");
			
			stmt = connection.createStatement();
			rs = stmt.executeQuery("SELECT * FROM benutzer");

			while(rs.next())
			{
				if(newUser.equals(rs.getString("benutzername")))
				{
					this.userExists = true;
					return;
				}
			}
			
			// sha256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = request.getParameter("password1");
			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			// format to hex with left zero padding
			text = String.format("%064x", new java.math.BigInteger(1, digest));
			
			PreparedStatement pstmt = null;
			pstmt = connection.prepareStatement
					("INSERT INTO benutzer(benutzername, passwort, prioritaet) VALUES(?, ?, ?)");
			pstmt.setString(1, newUser);
			pstmt.setString(2, text);
			pstmt.setInt(3, 3);
			pstmt.executeUpdate();

			pstmt.close();
			rs.close();
			connection.close();
			return;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

}
