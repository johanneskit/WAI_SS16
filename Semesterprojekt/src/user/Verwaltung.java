package user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.JNDIFactory;

class Priority extends Object{
	int priority = 0;
}


public class Verwaltung extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public boolean isAdmin = false;
	
	Connection connection = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("logout") == null)
		{
			return;
		}
		else
		{
			HttpSession session = req.getSession(false);
			if(session == null)
			{
				return;
			}
			session.invalidate();
			resp.getWriter().println("Sie haben sich erfolgreich abgemeldet");
			resp.getWriter().println("<a href=");
			resp.getWriter().println("/Semesterprojekt_Webcams/login.html>");
			resp.getWriter().println("zum Login</a>");
		}
	}
	
	public void getContextValues(HttpServletRequest request) throws SQLException, NamingException {
		
		connection = jndiFactory.getConnection("jdbc/waiDB");
		stmt = connection.createStatement();

		rs = stmt.executeQuery("select * from benutzer where benutzername ='" + request.getParameter("name") + "';");

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
		}
		
		
		session.setAttribute("priority", userPriority);
		session.setAttribute("user", request.getParameter("name"));
		
		connection.close();
		stmt.close();
		rs.close();
		
		if(userPriority.priority != 0){
			isAdmin = false;
			return;
		}
		else
		{
			isAdmin = true;
			return;
		}
	}
}
