package cal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
	int priority = 0;

	void getUserfromDB() {
		Connection c = null;
		String pw = "waiss16";
		String username = "postgres";
		String url = "jdbc:postgresql://wai-db.hopto.org:5432/wai-db";
		String driverName = "org.postgresql.Driver";
		
		try {
			
			Class.forName(driverName);
			c = DriverManager.getConnection(url, username, pw);
			Statement st = c.createStatement();
			ResultSet rs = st.executeQuery("");
			
			this.priority = rs.getInt("prioritaet");
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
