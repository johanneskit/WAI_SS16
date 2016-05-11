
package jobs;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import utils.JNDIFactory;
import utils.WebCam;

@SuppressWarnings("resource")
public class GetCameraImage implements Job
{
	private static Logger jlog = Logger.getLogger(GetCameraImage.class);
	
	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	
	WebCam[] cams;
	int numCams;
	
	static Integer i = 0;
	
	static String dir;
	
    // Required public empty constructor for job initialization
    public GetCameraImage() {
    	
    }
    
	private void getCamURLs() throws Exception {

		try {
			connection = jndiFactory.getConnection("jdbc/waiDB");
			statement  = connection.createStatement();
			
			resultSet = statement.executeQuery("select id from webcams where id=(select max(id) from webcams);");
			
			while (resultSet.next()) {
				numCams = resultSet.getInt("id");
				cams = new WebCam[numCams];
			}
			
			resultSet = statement.executeQuery("select id, name, url from webcams;");
			
			while (resultSet.next()) {
				int j = resultSet.getInt("id")-1;
				String name = resultSet.getString("name");
				String url  = resultSet.getString("url");
				
				jlog.info("Webcam #" + resultSet.getInt("id") + ": " + resultSet.getString("name") + " | " + resultSet.getString("url"));
				
				cams[j] = new WebCam();
				
				cams[j].setName(name);
				cams[j].setUrl(url);
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
    	
    	// 1000 Files pro Ordner
    	if(i % 1000 == 0) {
    		dir = "/tmp/img_" + i.toString() + "/";
    		File newdir = new File(dir);
    		newdir.mkdir();
    	}
    	
    	File file;
    	BufferedImage img;
    	
    	try {
	    	connection = jndiFactory.getConnection("jdbc/waiDB");
			statement  = connection.createStatement();
	    	
	    	try {
	    		for(int j=0; j<numCams; j++) {
	    	    	i++;
	    	    	String timestamp = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
	    	    	
	    	    	file = new File(dir + cams[j].getName() + "_" + timestamp + ".jpg");
	    			
	    			img = ImageIO.read(cams[j].getUrl());
	    			
	    			ImageIO.write(img, "jpg", file);
	    			
	    			statement.executeUpdate("insert into images (name, time, path) VALUES ('" + cams[j].getName() + "', '"
	    					+ timestamp + "', '" + file.toString() + "');" );
	    		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
    	} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
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

		}
    }
}