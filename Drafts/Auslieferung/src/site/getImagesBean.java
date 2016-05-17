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

	public getImagesBean() throws NamingException, SQLException {
		connection = jndiFactory.getConnection("jdbc/waiDB");
		statement = connection.createStatement();
	}

	public boolean getProcessError() {
		return this.processError;
	}
	
	public int getNumImages() {
		return this.numImages;
	}
	
	//Hochzählen im Kontext?!
	public int getImageID(int i) {
		return this.images[i].getID();
	}
	
	public String getImageText(int i) {
		
		return this.images[i].getText();
	}

	public void processRequest(HttpServletRequest request) throws SQLException {

		String cam, year, month, day, hour, minute = null;
		String query = null;

		processError = false;
		if (request == null || request.equals("")) {
			// Eingabefehler seitens User abfangen
			processError = true;
			return;
		}

		// wenn Feld leer => '*' übergeben (in Johannes Eingabematrix)

		cam = request.getParameter("cam");
		year = request.getParameter("year");
		month = request.getParameter("month");
		day = request.getParameter("day");
		hour = request.getParameter("hour");
		minute = request.getParameter("minute");

		// Anzahl Bilder die anzuzeigen sind ermitteln
		query = "SELECT COUNT(*) FROM images WHERE "
				+ "name='" + cam
				+"' AND year='" + year
				+ "' AND month='" + month
				+ "' AND day='" + day + "';";
			 // + "' AND hour='" + hour
			 // + "' AND minute='" + minute + "';";

		// Beispiel für Range:
		// "SELECT COUNT(*) FROM images WHERE name='Hamburg' AND minute BETWEEN '35' AND '45';"

		resultSet = statement.executeQuery(query);

		while (resultSet.next()) {
			numImages = resultSet.getInt("count");

			jlog.info("Anzahl Bilder: " + numImages);
		}
		
		images = new Image[numImages];

		// ID und Timestamp der angeforderten Bilder mittels SQL Query holen
		query = "SELECT id, prio, year, month, day, hour, minute from images WHERE "
				+ "name='" + cam
				+ "' AND year='" + year
				+ "' AND month='" + month
				+ "' AND day='" + day + "';";
				//+ "' AND hour='" + hour
				//+ "' AND minute='" + minute + "';";

		jlog.info("query: " + query);

		resultSet = statement.executeQuery(query);
		
		int i = 0;
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String text = resultSet.getString("day") + "." + resultSet.getString("month") + "."
						+ resultSet.getString("year") + " " + resultSet.getString("hour") + ":"
						+ resultSet.getString("minute");
			int prio = resultSet.getInt("prio");
			
			images[i] = new Image(id, text, prio);
			i++;
		}

		// statement und connection schließen?
		if(connection != null)
			connection.close();
	}

}
