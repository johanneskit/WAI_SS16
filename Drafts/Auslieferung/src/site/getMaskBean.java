package site;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import utils.JNDIFactory;

public class getMaskBean {

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private static Logger jlog = Logger.getLogger(getImagesBean.class);

	int numCams, numYears, numMonths, numDays, numHours, numMinutes = 0;
	String cam, year, month, day, hour, minute = null;
	String[] cams, years = null;

	public getMaskBean() {
		
	}
	
	public void initDB() throws NamingException, SQLException {
		connection = jndiFactory.getConnection("jdbc/waiDB");
		statement = connection.createStatement();
	}
	
	public void init() {
		try {
			initDB();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setCams();
		setYears();
	}

	public void closeDB() {
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
	
	public int getNumCams() {
		return numCams;
	}
	
	public void setCams() {

		try {
			// Count webcams
			String query = "SELECT COUNT(DISTINCT cam_name) FROM images;";
			
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numCams = resultSet.getInt("COUNT");
			}
			
			cams = new String[numCams];
			
			query = "SELECT DISTINCT cam_name FROM images;";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				cams[i] = resultSet.getString("cam_name");
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCam(int i) {
		return cams[i];
	}

	public int getNumYears() {
		return numYears;
	}
	
	public String getYear(int i) {
		return years[i];
	}
	
	public void setYears() {

		try {
			// Count years
			String query = "SELECT COUNT(DISTINCT year) FROM images;";
			
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numYears = resultSet.getInt("COUNT");
			}
			
			years = new String[numYears];
			
			query = "SELECT DISTINCT year FROM images;";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				years[i] = resultSet.getString("year");
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public int getNumMonths() {
		return numMonths;
	}

	public int getNumDays() {
		return numDays;
	}

	public int getNumHours() {
		return numHours;
	}

	public int getNumMinutes() {
		return numMinutes;
	}
	
}
