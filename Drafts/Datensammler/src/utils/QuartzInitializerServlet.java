
package utils;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzInitializerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private boolean performShutdown = true;

    private Scheduler scheduler = null;

    // Interface

    public void init(ServletConfig cfg) throws javax.servlet.ServletException {
        
        super.init(cfg);

        StdSchedulerFactory factory;
        try {
            if (JNDIFactory.getInstance().getEnvironmentAsBoolean("db").booleanValue() == true)
            {

	            String configFile = JNDIFactory.getInstance().getEnvironmentAsString("projectPath")
	    						  + JNDIFactory.getInstance().getEnvironmentAsString("configPath")
	    						  + "/quartz.properties";
	            
	            // get Properties
                factory = new StdSchedulerFactory(configFile);
	
	            // Always want to get the scheduler, even if it isn't starting,
	            // to make sure it is both initialized and registered.
	            scheduler = factory.getScheduler();
	
	            // Should the Scheduler being started now or later
	            String startOnLoad = cfg.getInitParameter("start-scheduler-on-load");
	            /*
	             * If the "start-scheduler-on-load" init-parameter is not
	             * specified, the scheduler will be started. This is to maintain
	             * backwards compatability.
	             */
	            if (startOnLoad == null || (Boolean.valueOf(startOnLoad).booleanValue())) {
	                // Start now
	                scheduler.start();
	            }
	
	            String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
	            if (shutdownPref != null) {
	                performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
	            }
	            
            }
            
        } catch (NamingException e) {
            log("Quartz Scheduler failed to initialize: " + e.toString());
            e.printStackTrace();
            throw new ServletException(e);
        } catch (Exception e) {
            log("Quartz Scheduler failed: " + e.toString());
            e.printStackTrace();
            throw new ServletException(e);
    	}
    }

    public void destroy() {

        if (!performShutdown) {
            return;
        }

        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }

}
