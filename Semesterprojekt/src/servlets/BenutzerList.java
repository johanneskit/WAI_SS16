package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Benutzer;

import dao.BenutzerDao;
import dao.DaoFactory;

public class BenutzerList extends HttpServlet {	
	
	private static final long serialVersionUID = 1L;
	
	final BenutzerDao benutzerDao = DaoFactory.getInstance().getBenutzerDao();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		List<Benutzer> collection = benutzerDao.list();
		request.setAttribute("benutzer", collection);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/listBenutzer.jsp");
		dispatcher.forward(request, response);		
	}
}
