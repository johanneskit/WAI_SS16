

import java.awt.BorderLayout;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class SessionContent{
	String path_var;
	//URL url;
	BufferedImage img;
}

@WebServlet("/db_session")
public class db_session extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
//    public void init () throws ServletException
//	{
//		ServletContext context = this.getServletContext();
//		Path_storage path = (Path_storage)context.getAttribute("currentPath");
//		if (path == null) {
//			path = new Path_storage();
//			context.setAttribute("currentPath", path);
//		}
//	}
    
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException
    	    {
    	        response.setContentType("text/html");
    	        PrintWriter out = response.getWriter();
    	        
    	        HttpSession mySession = request.getSession(true);

    	        // print infos

    	        out.println("<html>");
    	        out.println("<body>");

    	        if (mySession.isNew()) {
    	            out.println("<p>Neue Session</p>");
    	            mySession.setMaxInactiveInterval(3600000);
    	        }

    	        // print context contents
//    			ServletContext context = this.getServletContext();
//    			Counter contextCounter;
//    			synchronized(this)
//    			{
//    				contextCounter = (Counter) context.getAttribute ("meinContextCounter");
//    		        contextCounter.count++;
//    				context.setAttribute("meinContextCounter", contextCounter);
//    			}
//    	        out.println("<p>ContextZ&auml;hler=" + contextCounter.count + "</p>");


    	        SessionContent content = (SessionContent) mySession.getAttribute ("testContent");
    			if (content == null) {
    				content = new SessionContent();
//    				System.out.println("Bitte Pfad eingeben:");
//    				InputStreamReader isr = new InputStreamReader(System.in);
//    			    BufferedReader br = new BufferedReader(isr);
//    				SessionContent.path_var = br.readLine();
    				
    				content.path_var = "D:\\Dokumente\\test";
    				//URL url = new URL("http://cam.mannheim-wetter.info/cam1/mannheim-himmel-0.jpg");
    				URL url = new URL("http://www.boys-day.de/mediaserve/filestore/1/5/6/5/4_0b2c2d65d0368d5/15654_77c7e127bb7c519.jpg");
    				content.img = ImageIO.read(url);
    				
    				mySession.setAttribute("testContent", content);
    				

    			} 
    			else {
    				SessionContent contOUT = new SessionContent();
    				contOUT = (SessionContent) mySession.getAttribute("testContent");
    				
    				String pathname = (String) contOUT.path_var;
    				File file = new File(pathname + "test.png");
    				BufferedImage pic_new = contOUT.img;
    				ImageIO.write(pic_new, "png", file);
    				
    				BufferedImage thumbnail = new BufferedImage(100, 100, pic_new.getType());
    				Graphics2D g = thumbnail.createGraphics();
    				g.drawImage(pic_new, 0, 0, 100, 100, null);
    				g.dispose();
    				ImageIO.write(thumbnail, "png", new File(pathname + "\\thumbnailFolder\\thumbnail.png"));
    				
    				
    				JFrame frame = new JFrame();
    				JLabel testImage = new JLabel(new ImageIcon(pic_new));
    				JPanel mainPanel = new JPanel(new BorderLayout());
    				mainPanel.add(testImage);
    				// add more components here
    				frame.add(mainPanel);
    				frame.setVisible(true);
    				
    				
    				
    			}
    			
    				
    			

    	        
    	        Date created = new Date(mySession.getCreationTime());
    	        Date accessed = new Date(mySession.getLastAccessedTime());
    	        Date validTo = new Date(mySession.getLastAccessedTime() + mySession.getMaxInactiveInterval());

    	        out.println("<p>" + "ID " + mySession.getId() + "</p>");
    	        out.println("<p>" + "Created: " + created + "</p>");
    	        out.println("<p>" + "Last Accessed: " + accessed + "</p>");
    	        out.println("<p>" + "Valid To: " + validTo + "</p>");

    	        // set session info if needed

//    	        String dataName = request.getParameter("dataName");
//    	        if (dataName != null && dataName.length() > 0) {
//    	            String dataValue = request.getParameter("dataValue");
//    	            mySession.setAttribute(dataName, dataValue);
//    	        }

    	        // print session contents

//    	        Enumeration e = session.getAttributeNames();
//    	        while (e.hasMoreElements()) {
//    	            String name = (String)e.nextElement();
//    	            String value = session.getAttribute(name).toString();
//    	            out.println("<p>" + name + " = " + value + "</p>");
    	        }
}
