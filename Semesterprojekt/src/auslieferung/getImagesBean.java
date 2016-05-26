package auslieferung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import utils.JNDIFactory;
import model.Image;

public class getImagesBean {

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private static Logger jlog = Logger.getLogger(getImagesBean.class);

	int numImages;
	Image[] images;

	boolean processError = false;

	public getImagesBean() {
		
	}

	public boolean getProcessError() {
		return this.processError;
	}
	
	public int getNumImages() {
		return this.numImages;
	}
	
	public int getImageID(int i) {
		return this.images[i].getID();
	}
	
	public String getImageText(int i) {
		
		return this.images[i].getText();
	}

	public void processRequest(HttpServletRequest request) {

		HttpSession session = null;
		session = request.getSession(false);
		
		if (session.getAttribute("user") == null) {
			//keine bilderliste an unangemeldte ausliefern!
			return;
		}
		
		try {
			
			connection = jndiFactory.getConnection("jdbc/waiDB");
			statement = connection.createStatement();

			String cam, year, month, day, hour, minute = null;
			String user;
			String webcams = "", cam_id = "-1"; 
			
			String query = null, selectString = null;
	
			processError = false;
			if (request == null || request.equals("")) {
				// Eingabefehler seitens User abfangen
				processError = true;
				return;
			}
	
			// !!!!!!!!!!!!!!!!!!! ACHTUNG SQL-INJEKTION MÖGLICH !!!!!!!!!!!!!!!!!!!
	
			cam = request.getParameter("cam");
			year = request.getParameter("year");
			month = request.getParameter("month");
			day = request.getParameter("day");
			hour = request.getParameter("hour");
			minute = request.getParameter("minute");
			
			
			// SQL Query selectString bauen
			selectString = "cam_name='" + cam
					+ "' AND year='" + year
					+ "' AND month='" + month
					+ "' AND day='" + day;
			
			if (hour.equals("*"))
				selectString = selectString + "';";
			else if (minute.equals("*"))
				selectString = selectString + "' AND hour='" + hour + "';";
			else
				selectString = selectString + "' AND minute='" + minute + "';";
	
			//Rechteprüfung:
			
			//Benutzer ID aus Context holen
			user = session.getAttribute("user").toString();
			
			//liste "webcams" der cam_id's aus benutzer DB holen

			PreparedStatement p_statement;
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
				//TODO
			}
			
			//cam_id aus images DB holen
			query = "SELECT cam_id FROM images WHERE " + selectString;
			
			resultSet = statement.executeQuery(query);
			
			if (resultSet.next()) {
				cam_id = String.valueOf(resultSet.getInt("cam_id"));

			}
			
			//cam_id in Liste webcams suchen
			String[] parts = webcams.split(" ");
			for(int i = 0; i < parts.length; i++) {
			    if(parts[i].equals(cam_id))
			    	break;
			    
			    if(i == parts.length - 1)
			    {
					//kein bild an unauthorisierte ausliefern!
					
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
					
					numImages = 0;
					return;
			    }
			}
			
			// ENDE Rechteprüfung
			
			// Anzahl Bilder die anzuzeigen sind ermitteln
			query = "SELECT COUNT(*) FROM images WHERE " + selectString;
	
			// Beispiel für Range:
			// "SELECT COUNT(*) FROM images WHERE name='Hamburg' AND minute BETWEEN '35' AND '45';"
	
			jlog.info("COUNT query: " + query);
			
			resultSet = statement.executeQuery(query);
	
			while (resultSet.next()) {
				numImages = resultSet.getInt("COUNT");
	
				jlog.info("Anzahl Bilder: " + numImages);
			}
			
			images = new Image[numImages];
	
			// ID und Timestamp der angeforderten Bilder mittels SQL Query holen
			query = "SELECT id, year, month, day, hour, minute from images WHERE " + selectString;
	
			jlog.info("SELECT query: " + query);
	
			resultSet = statement.executeQuery(query);
			
			int i = 0;
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String text = String.format("%02d", resultSet.getInt("day")) + "."
							+ String.format("%02d", resultSet.getInt("month")) + "."
							+ String.format("%04d", resultSet.getInt("year")) + "<br>"
							+ String.format("%02d", resultSet.getInt("hour")) + ":"
							+ String.format("%02d", resultSet.getInt("minute"));
				
				images[i] = new Image(id, text);
				i++;
			}
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {

			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			if (statement != null)
				try {
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}

}
