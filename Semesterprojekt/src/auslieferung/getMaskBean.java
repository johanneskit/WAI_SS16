package auslieferung;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import utils.JNDIFactory;

public class getMaskBean {

	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	HttpSession session = null;
	
	String webcams = "", cam_id = "-1";
	String user;
	int prio;

	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	private static Logger jlog = Logger.getLogger(getImagesBean.class);

	int numCams, numYears, numMonths, numDays, numHours, numMinutes = 0;
	String cam, year, month, day, hour, minute = null;
	String[] cams, years, months, days, hours, minutes = null;

	public getMaskBean() {
		
	}
	
	public void initDB() throws NamingException, SQLException {
		if(connection == null)
			connection = jndiFactory.getConnection("jdbc/waiDB");
		
		if (statement == null)
			statement = connection.createStatement();
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
	
	public boolean processRequest(HttpServletRequest request) throws NamingException, SQLException {
		session = request.getSession(false);
		
		if (session.getAttribute("user") == null) {
			//keine ausführung für unangemeldte!
			return false;
		}
		
		initDB();
		
		if(cam == null) {
			user = session.getAttribute("user").toString();
			prio = (int) session.getAttribute("priority");
			
			setCams();
		}
		
		if (request.getParameter("reset") == null) {
			cam = request.getParameter("cam");
			year = request.getParameter("year");
			month = request.getParameter("month");
			day = request.getParameter("day");
			hour = request.getParameter("hour");
			
			if(cam != null && cam != "")
				setYears();
			if(year != null && year != "")
				setMonths();
			if(month != null && month != "")
				setDays();
			if(day != null && day != "")
				setHours();
			if(hour != null && hour != "")
				setMinutes();
			
			closeDB();
			
		} else {
			cam = year = month = day = hour = minute = null;
			cams = years = months = days = hours = minutes = null;
			numCams = numYears = numMonths = numDays = numHours = numMinutes = 0;
			
			setCams();
			closeDB();
		}
		
		return true;
	}
	
	//Camera
	public int getNumCams() {
		return numCams;
	}
	
	public String getSelectedCam() {
		if(cam != null)
			return cam;
		else
			return "";
	}
	
	private void setCams() {
		
		if (prio == 0)
		{
			try {
				// Count webcams for admin
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
			
		} else {
			
			try {
				// Count webcams for user
				String query = "SELECT webcams FROM benutzer WHERE benutzername ='" + user + "';";
				resultSet = statement.executeQuery(query);
				
				if(resultSet.next()) {
					webcams = resultSet.getString("webcams");
				}
				
				String[] parts = null;
				
				if (webcams != null)
				{
					parts = webcams.split(" ");
					numCams = parts.length;
				} else {
					numCams = 0;
					return;
				}
				
				cams = new String[numCams];
				
				//wie heißen die webcams des users
				for(int i=0; i<numCams; i++)
				{
					query = "SELECT name FROM webcams where id = '" + parts[i] + "';";
					resultSet = statement.executeQuery(query);
				
					if(resultSet.next())
						cams[i] = resultSet.getString("name");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getCam(int i) {
		return cams[i];
	}

	//Year
	public int getNumYears() {
		return numYears;
	}
	
	public String getSelectedYear() {
		if(year != null)
			return year;
		else
			return "";
	}
	
	public String getYear(int i) {
		return years[i];
	}
	
	private void setYears() {

		try {
			// Count years
			String query = "SELECT COUNT(DISTINCT year) FROM images WHERE cam_name='" + cam + "';";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numYears = resultSet.getInt("COUNT");
			}
			
			years = new String[numYears];
			
			query = "SELECT DISTINCT year FROM images WHERE cam_name='" + cam + "';";
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

	//Month
	public int getNumMonths() {
		return numMonths;
	}
	
	public String getSelectedMonth() {
		if(month != null)
			return month;
		else
			return "";
	}
	
	public String getMonth(int i) {
		return months[i];
	}
	
	private void setMonths() {
		try {
			// Count months
			String query = "SELECT COUNT(DISTINCT month) FROM images WHERE cam_name='" + cam + "' AND year='" + year + "';";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numMonths = resultSet.getInt("COUNT");
			}
			
			months = new String[numMonths];
			
			query = "SELECT DISTINCT month FROM images WHERE cam_name='" + cam + "' AND year='" + year + "';";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				months[i] = resultSet.getString("month");
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Day
	public int getNumDays() {
		return numDays;
	}
	
	public String getSelectedDay() {
		if(day != null)
			return day;
		else
			return "";
	}

	public String getDay(int i) {
		return days[i];
	}
	
	private void setDays() {
		try {
			// Count days
			String query = "SELECT COUNT(DISTINCT day) FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "';";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numDays = resultSet.getInt("COUNT");
			}
			
			days = new String[numDays];
			
			query = "SELECT DISTINCT day FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "';";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				days[i] = resultSet.getString("day");
				i++;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Hour
	public int getNumHours() {
		return numHours;
	}
	
	public String getSelectedHour() {
		if(hour != null)
			return hour;
		else
			return "";
	}

	public String getHour(int i) {
		return hours[i];
	}
	
	private void setHours() {
		try {
			// Count hours
			String query = "SELECT COUNT(DISTINCT hour) FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "' AND day='" + day + "';";
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numHours = resultSet.getInt("COUNT");
			}
			numHours++;
			
			hours = new String[numHours];
			
			query = "SELECT DISTINCT hour FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "' AND day='" + day + "';";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				hours[i] = resultSet.getString("hour");
				i++;
			}
			
			hours[i] = "*";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//Minute
	public int getNumMinutes() {
		return numMinutes;
	}

	public String getMinute(int i) {
		return minutes[i];
	}
	
	private void setMinutes() {
		try {
			
			// If hours is wildcarded, minutes can only be wildcarded too
			if (hour.equals("*")) {
				numMinutes = 1;
				minutes = new String[1];
				minutes[0] = "*";
				
				jlog.info("hours is wildcarded, this should return wildcarding minutes!");
				
				return;
			}

			// Count minutes
			String query = "SELECT COUNT(DISTINCT minute) FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "' AND day='" + day + "' AND hour='" + hour + "';";		
			resultSet = statement.executeQuery(query);
			
			while (resultSet.next()) {
				numMinutes = resultSet.getInt("COUNT") + 1;
			}
			
			minutes = new String[numMinutes];
			
			query = "SELECT DISTINCT minute FROM images WHERE cam_name='" + cam + "' AND year='" + year + "' AND month='" + month + "' AND day='" + day + "' AND hour='" + hour + "';";
			resultSet = statement.executeQuery(query);
			
			int i=0;
			while (resultSet.next()) {
				minutes[i] = resultSet.getString("minute");
				i++;
			}
			
			minutes[i] = "*";
			
			closeDB();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
