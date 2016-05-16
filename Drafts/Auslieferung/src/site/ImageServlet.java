package site;

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

//TODO
//Fallunterscheidung ob thumbs oder vollbild ausgeliefert werden soll
//Rechte prüfen

@SuppressWarnings("serial")
public class ImageServlet extends HttpServlet {
	
	private static final String SQL_FIND = "SELECT image FROM images WHERE id = ?";

	Connection connection = null;
	PreparedStatement p_statement = null;
	ResultSet resultSet = null;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private static Logger jlog = Logger.getLogger(ImageServlet.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String imageID = request.getParameter("id");
		jlog.info("Bild mit ID " + imageID + " angefordert");
		
		try {
		
		connection = jndiFactory.getConnection("jdbc/waiDB");
		p_statement = connection.prepareStatement(SQL_FIND);
		p_statement.setInt(1, Integer.parseInt(imageID));
		
		jlog.info("Query: " + p_statement.toString());

			try (ResultSet resultSet = p_statement.executeQuery()) {
				if (resultSet.next()) {
					byte[] content = resultSet.getBytes("image");
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
	}

}