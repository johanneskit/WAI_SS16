
package utils;

import java.io.IOException;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public class QuartzInitializerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private boolean performShutdown = true;

	private Scheduler scheduler = null;

	// Interface

	public void init(ServletConfig cfg) throws javax.servlet.ServletException {

		super.init(cfg);

		StdSchedulerFactory sf;
		Scheduler sched = null;

		try {

			String config = JNDIFactory.getInstance().getEnvironmentAsString("projectPath")
			+ JNDIFactory.getInstance().getEnvironmentAsString("configPath") + "/quartz.properties";

			// get Properties
			sf = new StdSchedulerFactory(config);

			// get a reference to a scheduler
			try {
				sched = sf.getScheduler();
			} catch (SchedulerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// start the schedule
			sched.start();

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
