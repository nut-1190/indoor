package tes;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class cook
 */
@WebServlet("/cook")
public class cook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Create cookies
        Cookie cookie1 = new Cookie("CookieOne", "Value1");
        Cookie cookie2 = new Cookie("CookieTwo", "Value2");
        Cookie cookie3 = new Cookie("CookieThree", "Value3");
        Cookie cookie4 = new Cookie("CookieFour", "Value4");

        // Set expiry for 1 minute for two cookies
        cookie1.setMaxAge(60); // 60 seconds
        cookie2.setMaxAge(60); // 60 seconds

        // Add cookies to response
        response.addCookie(cookie1);
        response.addCookie(cookie2);
        response.addCookie(cookie3);
        response.addCookie(cookie4);

        // Display cookies
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            out.println("<h2>Found Cookies:</h2>");
            for (Cookie cookie : cookies) {
                out.println("<p>Name: " + cookie.getName() + ", Value: " + cookie.getValue() + "</p>");
            }
        } else {
            out.println("<h2>No Cookies found</h2>");
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
