package com.revature.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getRootLogger();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("home").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.info(request.getParameter("destination"));
		
		switch (request.getParameter("destination")) {
		
		case "login":
			request.getRequestDispatcher("login").forward(request, response);
			break;
		case "submitReimb":
			request.getRequestDispatcher("SubmitRequest").forward(request, response);
			break;
		case "updateUserProf":
			request.getRequestDispatcher("SubmitRequest").forward(request, response);
			break;
		case "processReimb":
			request.getRequestDispatcher("SubmitRequest").forward(request, response);
			break;
		default:
			response.sendRedirect("home");
		}
		
	}

}
