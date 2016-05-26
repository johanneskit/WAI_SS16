package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.apache.log4j.Logger;

import utils.JNDIFactory;


@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {

	Connection connection = null;
	PreparedStatement p_statement = null;
	ResultSet resultSet = null;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private static Logger jlog = Logger.getLogger(ImageServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = null;
		session = request.getSession(false);
		
		if (session == null) {
			//kein bild an unangemeldte ausliefern!
			response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403
			return;
		}

		boolean thumb = false;

		if (request.getParameter("thumb") != null)
			thumb = true;

		String imageID = request.getParameter("id");
		if (imageID == null || imageID.equals(""))
		{
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
			return;
		}
		
		String webcams = "", cam_id = "-1"; 

		jlog.info("Bild mit ID " + imageID + " angefordert");
		if (thumb)
			jlog.info(" als Thumbnail");

		try {

			connection = jndiFactory.getConnection("jdbc/waiDB");
			
			//Rechteprüfung:
			
			//Benutzer ID aus Context holen
			String user = session.getAttribute("user").toString();
			
			//liste "webcams" der cam_id's aus benutzer DB holen

			p_statement = connection.prepareStatement("SELECT webcams FROM benutzer WHERE benutzername = ?");
			p_statement.setString(1, user);
			
			try (ResultSet resultSet = p_statement.executeQuery()) {
				if (resultSet.next()) {
					webcams = resultSet.getString("webcams");
					
					if (webcams == null) {
						try {
							if (connection != null)
								connection.close();
							if (p_statement != null)
								p_statement.close();
							if (resultSet != null)
								resultSet.close();

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						return;
					}
				}
			} catch (SQLException e) {
				throw new ServletException("Something failed at SQL/DB level.", e);
			}
			
			//cam_id aus images DB holen
			p_statement = connection.prepareStatement("SELECT cam_id FROM images WHERE id = ?");
			p_statement.setInt(1, Integer.parseInt(imageID));
			
			try (ResultSet resultSet = p_statement.executeQuery()) {
				if (resultSet.next()) {

					cam_id = String.valueOf(resultSet.getInt("cam_id"));

				}
				
			} catch (SQLException e) {
				throw new ServletException("Something failed at SQL/DB level.", e);
			}
			
			//cam_id in Liste webcams suchen
			String[] parts = webcams.split(" ");
			for(int i = 0; i < parts.length; i++) {
			    if(!parts[i].equals(cam_id)) {
					//kein bild an unauthorisierte ausliefern!
					response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403
					
					try {
						if (connection != null)
							connection.close();
						if (p_statement != null)
							p_statement.close();
						if (resultSet != null)
							resultSet.close();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					return;
			    }
			}
			
			// ENDE Rechteprüfung

			if (thumb)
				p_statement = connection.prepareStatement("SELECT image_t FROM images WHERE id = ?");
			else
				p_statement = connection.prepareStatement("SELECT image FROM images WHERE id = ?");

			p_statement.setInt(1, Integer.parseInt(imageID));

			jlog.info("Query: " + p_statement.toString());

			try (ResultSet resultSet = p_statement.executeQuery()) {
				if (resultSet.next()) {

					byte[] content;

					if (thumb)
						content = resultSet.getBytes("image_t");
					else
						content = resultSet.getBytes("image");

					response.setContentType("image/jpg");
					response.setContentLength(content.length);

					response.getOutputStream().write(content);

				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
				}
			}
		} catch (SQLException | NamingException e) {
			throw new ServletException("Something failed at SQL/DB level.", e);
		}

		// TODO: wirft "org.postgresql.util.PSQLException: This statement has been
		// closed" !!!
		try {
			if (connection != null)
				connection.close();
			if (p_statement != null)
				p_statement.close();
			if (resultSet != null)
				resultSet.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}