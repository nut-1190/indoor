package tes;
import java.io.*;
import javax.servlet.http.Cookie;
import java.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class cook1
 */
@WebServlet("/cook1")
public class cook1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cook1() {
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
		
		Cookie cookie1=new Cookie("cookieOne","value1");
		Cookie cookie2=new Cookie("cookieTwo","value2");
		Cookie cookie3=new Cookie("cookieThree","value3");
		Cookie cookie4=new Cookie("cookieFour","value4");
		
		cookie1.setMaxAge(60);
		cookie2.setMaxAge(60);
		
		response.addCookie(cookie1);
		response.addCookie(cookie2);
		response.addCookie(cookie3);
		response.addCookie(cookie4);
		
		
		Cookie[] cookies=request.getCookies();
		if (cookies!=null) {
			out.println("cookies found!<br>");
			for (Cookie cookie: cookies) {
				out.println("<p> Name: " +cookie.getName()+"value: " +cookie.getValue()+"</p>");
			}
		}else {
			out.println("cookies not found:(");
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
