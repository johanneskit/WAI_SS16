package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

class Priority extends Object{
	int priority = 0;
}


public class Verwaltung extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public boolean isAdmin = false;
	
	public void getContextValues(HttpServletRequest request) throws SQLException {
		Connection connection;

		String pw = "waiss16";
		String username = "postgres";
		String url = "jdbc:postgresql://wai-db.hopto.org:5432/wai-db";
		String driverName = "org.postgresql.Driver";

		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, pw);

			Statement stmt = connection.createStatement();

			ResultSet rs = stmt
					.executeQuery("select * from benutzer where benutzername ='" + request.getParameter("name") + "';");

			rs.next();

			
			
			HttpSession session = request.getSession(true);
			
			if(session.isNew())
			{
				session.setMaxInactiveInterval(3600000);
			}
			
			Priority userPriority = (Priority)session.getAttribute("priority");
			if(userPriority == null)
			{
				userPriority = new Priority();
				userPriority.priority = rs.getInt("prioritaet");
//				userPriority.priority = (Integer)request.getAttribute("priority");
			}
			session.setAttribute("priority", userPriority);
			if(userPriority.priority != 0){
				isAdmin = false;
				return;
			}
			else
			{
				isAdmin = true;
				return;
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}
	}
}
