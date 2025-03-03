package tes;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Serv2
 */
@WebServlet("/Serv2")
public class Serv2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Serv2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		try {
			String USN=request.getParameter("USN");
			String Name=request.getParameter("Name");
			int SG1=Integer.parseInt(request.getParameter("SG1"));
			int SG2=Integer.parseInt(request.getParameter("SG2"));
			int SG3=Integer.parseInt(request.getParameter("SG3"));
			int SG4=Integer.parseInt(request.getParameter("SG4"));
			float cgpa=(SG1+SG2+SG3+SG4)/4;
			out.println("Your cgpa is:" +cgpa);
			
		}catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
