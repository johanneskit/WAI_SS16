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

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Registrate {
	
	private static Logger log = Logger.getLogger(Registrate.class);
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
		Connection connection;

		 String pw = "waiss16";
		 String username = "postgres";
		 String url = "jdbc:postgresql://wai-db.hopto.org:5432/wai-db";

		String driverName = "org.postgresql.Driver";

		try {
			Class.forName(driverName);

			connection = DriverManager.getConnection(url, username, pw);
			
			if (!request.getParameter("password1").equals(request.getParameter("password2"))) {
				this.pw_notEqual = true;
				return;
			}

			String newUser = request.getParameter("newUsername");

//			PreparedStatement pstmt = connection.prepareStatement("SELECT ? FROM benutzer");// WHERE name = ? ;");
//			pstmt.setString(1, "*");//"'" + newUser + "'");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM benutzer");

			//rs.next();

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
			
			//log.info("new User : " + rs.getString("benutzername"));

			pstmt.close();
			rs.close();
			connection.close();
			return;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
