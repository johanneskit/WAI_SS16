package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import utils.JNDIFactory;

public class AppCore implements Job {

	JNDIFactory jndiFactory = JNDIFactory.getInstance();

	public AppCore() {
		
	}

	private void process() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select id, value from test");			
			
			while (resultSet.next()) {
				//resultSet.getInt("int"), resultSet.getString("value")), ...
			}

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

	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			AppCore core = new AppCore();
			core.process();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
