
import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public final class SystemStartup implements ServletContextListener {

	public void contextInitialized(ServletContextEvent event) {
		JNDIFactory jndiFactory = JNDIFactory.getInstance();

		try {
			// Initialize Logging
			File logConfigFile = new File(jndiFactory.getEnvironmentAsString("projectPath")
					  					+ jndiFactory.getEnvironmentAsString("configPath")
					  					+ "/log4j.properties");

			if (!logConfigFile.exists()) {
				System.out.println("ERROR: Logging configuration file not found: " 
								  + jndiFactory.getEnvironmentAsString("projectPath")
								  + jndiFactory.getEnvironmentAsString("configPath")
								  + "/log4j.properties");
			} else {
				logConfigFile.getPath();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void contextDestroyed(ServletContextEvent event) {
		
	}

}
