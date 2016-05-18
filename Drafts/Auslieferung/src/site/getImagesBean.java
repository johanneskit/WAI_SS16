package site;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.http.*;

import org.apache.log4j.Logger;

import utils.JNDIFactory;

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

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");
			statement = connection.createStatement();

			String cam, year, month, day, hour, minute;
			cam = year = month = day = hour = minute = null;
			
			String query = null, selectString = null;
	
			processError = false;
			if (request == null || request.equals("")) {
				// Eingabefehler seitens User abfangen
				processError = true;
				return;
			}
	
			// wenn Feld leer => '*' übergeben (in Eingabematrix)
			// !!!!!!!!!!!!!!!!!!! ACHTUNG SQL-INJEKTION MÖGLICH !!!!!!!!!!!!!!!!!!!
	
			cam = request.getParameter("cam");
			year = request.getParameter("year");
			month = request.getParameter("month");
			day = request.getParameter("day");
			hour = request.getParameter("hour");
			minute = request.getParameter("minute");
			
			
			// SQL Query selectString bauen
			selectString = "cam_name='" + cam
					+"' AND year='" + year
					+ "' AND month='" + month
					+ "' AND day='" + day;
			
			if (hour != null && hour != "")
				selectString = selectString + "' AND hour='" + hour;
			
			if (minute == null || minute == "")
				selectString = selectString + "';";
			else
				selectString = selectString + "' AND minute='" + minute + "';";
	
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
				String text = cam + resultSet.getString("day") + "." + resultSet.getString("month") + "."
							+ resultSet.getString("year") + " " + resultSet.getString("hour") + ":"
							+ resultSet.getString("minute");
				
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
