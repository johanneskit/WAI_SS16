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

import dao.BenutzerDao;
import dao.DaoFactory;
import exception.BenutzerNotDeletedException;
import exception.BenutzerNotFoundException;
import exception.BenutzerNotSavedException;

import model.Benutzer;

public class BenutzerEdit extends HttpServlet {	
	
	private static final long serialVersionUID = 1L;
	
	final BenutzerDao benutzerDao = DaoFactory.getInstance().getBenutzerDao();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/add.jsp");
			dispatcher.forward(request, response);		
		} else if(action.equals("edit")) {			
			try {
				Benutzer benutzer = benutzerDao.get(id);
				request.setAttribute("benutzer", benutzer);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/edit.jsp");
				dispatcher.forward(request, response);
			} catch (BenutzerNotFoundException e) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
				request.setAttribute("exception", e.getMessage());
				dispatcher.forward(request, response);
			}				
		} else if(action.equals("delete")) {			
			try {
				benutzerDao.delete(id);
				response.sendRedirect(request.getContextPath() + "/list");
			} catch (BenutzerNotDeletedException e) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
				request.setAttribute("exception", e.getMessage());
				dispatcher.forward(request, response);
			}
		}		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		Integer id = null;
		
		if(request.getParameter("id") != null) {
			id = Integer.valueOf(request.getParameter("id"));
		}
		
		String benutzername = request.getParameter("benutzername");
		int prioritaet = Integer.parseInt(request.getParameter("prioritaet"));
		String passwort = request.getParameter("passwort");		
		
		Benutzer benutzer = new Benutzer();		
		benutzer.setBenutzername(benutzername);
		benutzer.setPrioritaet(prioritaet);	
		benutzer.setPasswort(passwort);
		if(id !=  null)
			benutzer.setId(id);
		
		try {		
			benutzerDao.save(benutzer);
			response.sendRedirect(request.getContextPath() + "/list");
		}  catch (BenutzerNotSavedException e) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/error.jsp");
			request.setAttribute("exception", e.getMessage());
			dispatcher.forward(request, response);
		}
	}
}
