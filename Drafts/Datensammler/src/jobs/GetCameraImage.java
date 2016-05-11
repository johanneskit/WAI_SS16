
package jobs;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class GetCameraImage implements Job
{
	static Integer i = 0;
	
	static String dir;
	File file;
	
	URL url = null;
	
	BufferedImage img;
	
    // Required public empty constructor for job initialization
    public GetCameraImage() {
    	
    }
   
    /* execute() method is called by the org.quartz.Scheduler when a org.quartz.Trigger
     * fires that is associated with the org.quartz.Job */
    public void execute(JobExecutionContext context) throws JobExecutionException {
    	
    	if(i % 10 == 0) {
    		dir = "/tmp/img_" + i.toString() + "/";
    		File newdir = new File(dir);
    		newdir.mkdir();
    	}
    	
    	i++;
    	
    	file = new File(dir + new java.util.Date() + ".jpg");
       
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