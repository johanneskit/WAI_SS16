package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Webcam;

import dao.WebcamDao;
import dao.DaoFactory;

public class WebcamList extends HttpServlet {

	private static final long serialVersionUID = 1L;

	final WebcamDao webcamDao = DaoFactory.getInstance().getWebcamDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		HttpSession session = null;
		session = request.getSession(false);
		String user;
		user = (String) session.getAttribute("user");
		if (user == null) {
				//kein bild an unangemeldte ausliefern!
				response.sendError(HttpServletResponse.SC_FORBIDDEN); // 403
				return;
			}

		List<Webcam> collection = webcamDao.list();
		request.setAttribute("webcam", collection);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/listCam.jsp");
		dispatcher.forward(request, response);		
	}
}
