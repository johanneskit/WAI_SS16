
package jobs;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import utils.JNDIFactory;

public class GetCameraImage implements Job
{
	private static Logger jlog = Logger.getLogger(GetCameraImage.class);
	
	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	
	static Integer i = 0;
	
	static String dir;
	
	File file;
	
	URL url = null;
	
	BufferedImage img;
	
    // Required public empty constructor for job initialization
    public GetCameraImage() {
    	
    }
    
    private void getCamURLs() throws Exception {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");

			statement = connection.createStatement();
			resultSet = statement.executeQuery("select id, name, url from webcams");		
			
			
			while (resultSet.next()) {
				jlog.info(resultSet.getInt("id") + "::" + resultSet.getString("name") + "::"+ resultSet.getString("url"));
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
   
    /* execute() method is called by the org.quartz.Scheduler when a org.quartz.Trigger
     * fires that is associated with the org.quartz.Job */
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    	try {
			getCamURLs();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	if(i % 1000 == 0) {
    		dir = "/tmp/img_" + i.toString() + "/";
    		File newdir = new File(dir);
    		newdir.mkdir();
    	}
    	
    	i++;
    	
    	file = new File(dir + "img_" + new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date()) + ".jpg");
       
    	try {
    		url = new URL("http://www.thueringer-webcams.de/kunden/mdr/erfurt-mdr/livebild-pal.jpg");
    	} catch (MalformedURLException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	try {
			ImageIO.write(img, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}