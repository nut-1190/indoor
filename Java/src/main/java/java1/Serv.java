package java1;
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
 * Servlet implementation class Serv
 */
@WebServlet("/Serv")
public class Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Serv() {
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
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Employee","root","");
			if (!con.isClosed()) {
				out.println("connected succesfully <br>");
			}
			
			String sql1="insert into emp values(?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql1);
			String Ename=request.getParameter("Ename");
			String Ehours=request.getParameter("Ehours");
			String Prate=request.getParameter("Prate");
			String Tax=request.getParameter("Tax");
			ps.setString(1,"Ename");
			ps.setInt(2, Integer.parseInt(Ehours));
			ps.setInt(3, Integer.parseInt(Prate));
			ps.setInt(4, Integer.parseInt(Tax));
			int r=ps.executeUpdate();
			if(r>0) {
				out.println("inserted succesfully <br>");
			} else {
				out.println("error inserting");
			}
			String sql="select * from emp";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
			}
		}catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
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
