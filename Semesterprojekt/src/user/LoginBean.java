package user;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import utils.JNDIFactory;

public class LoginBean {

	Connection connection = null;
	Statement stmt = null;
	ResultSet rs = null;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();

	String name = null;
	String password = null;

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

	public void processRequest(HttpServletRequest request)
			throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {

		this.processError = true;

		HttpSession session = request.getSession(false);
		String user = (String) session.getAttribute("user");
		if (session != null)
			if(user != null)
				{return;}

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");
			stmt = connection.createStatement();

			String temp_name = request.getParameter("name");
			String temp_pw = request.getParameter("pw");
			
			if (temp_name == null || temp_name == "" || temp_pw == null || temp_pw == "") {
				// nichts eingegeben
				// this.processError = true;#
			}

			// sha256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = temp_pw;
			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if
												// needed
			byte[] digest = md.digest();
			// format to hex with left zero padding
			text = String.format("%064x", new java.math.BigInteger(1, digest));

			rs = stmt.executeQuery("SELECT * FROM benutzer");

			while (rs.next()) {
				if (temp_name.equals(rs.getString("benutzername"))) {
					if (text.equals(rs.getString("passwort"))) {

						stmt.close();
						rs.close();
						connection.close();
						this.processError = false;
						return;
					}
				}
			}
			// Wrong password or username
			return;

		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

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