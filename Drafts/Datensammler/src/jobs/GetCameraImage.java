
package jobs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

//KOMPLETTER REWRITE ERFORDERLICH!!!

public class GetCameraImage implements Job
{
	private static Logger jlog = Logger.getLogger(GetCameraImage.class);
	
	JNDIFactory jndiFactory = JNDIFactory.getInstance();
	
	Connection connection = null;
	Statement statement = null;
	PreparedStatement p_statement = null;
	ResultSet resultSet = null;
	
	WebCam[] cams;
	int numCams;
	
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
			
			resultSet = statement.executeQuery("select id, name, url, prio from webcams;");
			
			while (resultSet.next()) {
				int j = resultSet.getInt("id")-1;
				int prio = resultSet.getInt("prio");
				String name = resultSet.getString("name");
				String url  = resultSet.getString("url");
				
				jlog.info("Webcam #" + j + " Priorität: " + prio + " Name: " + name + " URL: " + url);
				
				cams[j] = new WebCam();
				
				cams[j].setPrio(prio);
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
    	
    	try {
	    	connection = jndiFactory.getConnection("jdbc/waiDB");
	    	
	    	try {
	    		for(int j=0; j<numCams; j++) {
	    	    	Date timestamp = new Date();
	    	    	
	    	    	File img = new File("/tmp/img.jpg");
	    	    	File img_t = new File("/tmp/img_t.jpg");
	    	    	
	    	    	BufferedImage bufImg, bufImg_t;
	    	    	bufImg = ImageIO.read(cams[j].getUrl());
	    	    	ImageIO.write(bufImg, "jpg", img);
	    	    	
	    	    	bufImg_t = new BufferedImage(50, 50, bufImg.getType());
    				Graphics2D g = bufImg_t.createGraphics();
    				g.drawImage(bufImg, 0, 0, 50, 50, null);
    				g.dispose();
    				ImageIO.write(bufImg_t, "jpg", img_t);
	    	    	
	    	    	FileInputStream fis = new FileInputStream(img);
	    	    	FileInputStream fis_t = new FileInputStream(img_t);
	    			
	    			p_statement = connection.prepareStatement("INSERT INTO images (name, prio, year, month, day, hour, minute, image, image_t) "
	    					+ "VALUES ('"
	    					+ cams[j].getName()
	    					+ "', '" + cams[j].getPrio()
	    					+ "', '" + new SimpleDateFormat("yyyy").format(timestamp)
	    					+ "', '" + new SimpleDateFormat("MM").format(timestamp) 
	    					+ "', '" + new SimpleDateFormat("dd").format(timestamp)
	    					+ "', '" + new SimpleDateFormat("HH").format(timestamp)
	    					+ "', '" + new SimpleDateFormat("mm").format(timestamp)
	    					+ "',?,?);" );
	    			
	    			p_statement.setBinaryStream(1, fis, (int)(img.length()));
	    			p_statement.setBinaryStream(2, fis_t, (int)(img_t.length()));
	    			p_statement.executeUpdate();
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
