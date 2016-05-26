
package jobs;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.io.*;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import model.WebCam;
import utils.JNDIFactory;

public class GetCameraImage implements Job {
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
			statement = connection.createStatement();

			// Count number of Webcams in DB
			resultSet = statement.executeQuery("SELECT COUNT(*) FROM webcams;");

			while (resultSet.next()) {
				numCams = resultSet.getInt("COUNT");
				cams = new WebCam[numCams];
			}
			
			jlog.info("Found " + numCams + " Webcams:");

			// Get all Webcam data
			resultSet = statement.executeQuery("SELECT id, name, url FROM webcams;");

			int i = 0;
			while (resultSet.next()) {
				int cam_id = resultSet.getInt("id");
				String cam_name = resultSet.getString("name");
				String url = resultSet.getString("url");

				jlog.info("Webcam #" + i + " with ID " + cam_id + "\"" + cam_name + "\" @ " + url);

				cams[i] = new WebCam();

				cams[i].setCamID(cam_id);
				cams[i].setCamName(cam_name);
				cams[i].setUrl(url);

				i++;
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

	/*
	 * execute() method is called by the org.quartz.Scheduler when a
	 * org.quartz.Trigger fires that is associated with the org.quartz.Job
	 */
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
				for (int i=0; i < numCams; i++) {
					Date timestamp = new Date();
					BufferedImage bufImg, bufImg_t;

					// Prepare temporary files
					File img = new File("/tmp/img.jpg");
					File img_t = new File("/tmp/img_t.jpg");

					// Read image from URL and save to temporary file
					bufImg = ImageIO.read(cams[i].getUrl());
					ImageIO.write(bufImg, "jpg", img);

					// Render thumbnail and save to temporary file
					bufImg_t = new BufferedImage(100, 100, bufImg.getType());
					Graphics2D g = bufImg_t.createGraphics();
					g.drawImage(bufImg, 0, 0, 100, 100, null);
					g.dispose();
					ImageIO.write(bufImg_t, "jpg", img_t);
					
					// Prepare streams
					FileInputStream fis = new FileInputStream(img);
					FileInputStream fis_t = new FileInputStream(img_t);

					// Prepare SQL Query
					p_statement = connection.prepareStatement(
									"INSERT INTO images (cam_name, cam_id, year, month, day, hour, minute, image, image_t) VALUES ('"
									+ cams[i].getCamName() + "','"
									+ cams[i].getCamID() + "','"
									+ new SimpleDateFormat("yyyy").format(timestamp) + "','"
									+ new SimpleDateFormat("MM").format(timestamp) + "','"
									+ new SimpleDateFormat("dd").format(timestamp) + "','"
									+ new SimpleDateFormat("HH").format(timestamp) + "','"
									+ new SimpleDateFormat("mm").format(timestamp) + "',?,?);"
									);

					jlog.info(p_statement.toString());
					
					// Write image and thumbnail into SQL Query and execute
					p_statement.setBinaryStream(1, fis, (int) (img.length()));
					p_statement.setBinaryStream(2, fis_t, (int) (img_t.length()));
					
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

			if (p_statement != null)
				try {
					p_statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}
}
