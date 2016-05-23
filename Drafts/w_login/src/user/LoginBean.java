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
import java.util.Hashtable;
import user.*;
//import utils.JNDIFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unused")
public class LoginBean {

	String name = null;
	String password = null;
	//private static Logger log = Logger.getLogger(LoginBean.class);

	boolean processError = true;

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public boolean getProcessError() {
		return this.processError;
	}

	public void processRequest(HttpServletRequest request) throws SQLException {
		this.processError = true;
		Connection connection;
		

//		String username = "ss16-gruppe8";
//		String pw = "Znvgsrn6";
//		String url = "jdbc:postgresql://141.19.96.94/ss16-gruppe8_DB";
		 String pw = "waiss16";
		 String username = "postgres";
		 String url = "jdbc:postgresql://wai-db.hopto.org:5432/wai-db";

		String driverName = "org.postgresql.Driver";

		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, pw);

			String temp_name = request.getParameter("name");
			String temp_pw = request.getParameter("pw");
			
			if (temp_name == null || temp_name == "" || temp_pw == null || temp_pw == "") {
				// nichts eingegeben
				//this.processError = true;
				return;
			}

			// sha256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = temp_pw;
			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			byte[] digest = md.digest();
			// format to hex with left zero padding
			text = String.format("%064x", new java.math.BigInteger(1, digest));

//			PreparedStatement pstmt = connection
//					.prepareStatement("SELECT ? FROM benutzer");// WHERE name = ? AND passwort = ? ");
//			pstmt.setString(1, "*");
//			pstmt.setString(2, "'" + temp_name + "'");
//			pstmt.setString(3, "'" + text + "'");
			Statement stmt = connection.createStatement();
			
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM benutzer");

			//rs.next();
//			String s1 = rs.getString("name");
//			String s2 = rs.getString("passwort");
			//rs.next();
			while (rs.next()) {
				if (temp_name.equals(rs.getString("benutzername"))) {
					if (text.equals(rs.getString("passwort"))) {

						//pstmt.close();
//						log.info(rs.getString("benutzername") + " has logged in");
						stmt.close();
						rs.close();
						connection.close();
						this.processError = false;
						return;
					}
				}
			}
			//Wrong password or username
			return;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
}