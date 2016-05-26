/*
 * Created on 22.11.2004
 */
package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.WebcamDao;
import dao.DaoFactory;
import exception.WebcamNotDeletedException;
import exception.WebcamNotFoundException;
import exception.WebcamNotSavedException;

import model.Webcam;

public class WebcamEdit extends HttpServlet {	
	
	private static final long serialVersionUID = 1L;
	
	final WebcamDao webcamDao = DaoFactory.getInstance().getWebcamDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		int prio = (int) session.getAttribute("priority");
		if(session == null || prio != 0)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		String action = request.getParameter("action");
		
		if (action == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			dispatcher.forward(request, response);
		}
				
		Integer id = null;
		
		if (request.getParameter("id") != null) {
			id = Integer.valueOf(request.getParameter("id"));
		}
				
		if(action.equals("add")){
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/addCam.jsp");
			dispatcher.forward(request, response);	
		}
		else if(action.equals("edit")) {			
			try {
				Webcam webcam = webcamDao.get(id);
				request.setAttribute("webcam", webcam);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/editCam.jsp");
				dispatcher.forward(request, response);
			} catch (WebcamNotFoundException e) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
				request.setAttribute("exception", e.getMessage());
				dispatcher.forward(request, response);
			}				
		} else if(action.equals("delete")) {			
			try {
				webcamDao.delete(id);
				response.sendRedirect(request.getContextPath() + "/listCam");
			} catch (WebcamNotDeletedException e) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
				request.setAttribute("exception", e.getMessage());
				dispatcher.forward(request, response);
			}
		}		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession(false);
		int prio = (int) session.getAttribute("priority");
		if(session == null || prio != 0)
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);//403
			return;
		}
		
		Integer id = null;
		
		if(request.getParameter("id") != null) {
			id = Integer.valueOf(request.getParameter("id"));
		}
		
		
		String name = request.getParameter("name");
		String url = request.getParameter("url");	
		
		String user_id = session.getAttribute("user_id").toString();
		
		
		Webcam webcam = new Webcam();		
		webcam.setName(name);
		webcam.setUrl(url);	
		if(id != null)
			webcam.setId(id);
		
		try {		
			webcamDao.save(webcam, user_id);
			response.sendRedirect(request.getContextPath() + "/listCam");
		}  catch (WebcamNotSavedException e) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			request.setAttribute("exception", e.getMessage());
			dispatcher.forward(request, response);
		}
	}
}
