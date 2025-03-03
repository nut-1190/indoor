import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
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
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Test","root","");
			if (!con.isClosed()) {
				out.println("succesfully connected\n");
			}
			String sql="select * from Customer";
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()) {
				out.println(rs.getInt(1)+" "+rs.getInt(2)+" "+rs.getString(3)+" "+rs.getInt(4)+"<br>");
			}
			String sql1="insert into Customer values(?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql1);
			String ID=request.getParameter("ID");
			String Age=request.getParameter("Age");
			String Fname=request.getParameter("Fname");
			String Salary=request.getParameter("Salary");
			ps.setInt(1,Integer.parseInt(ID));
			ps.setInt(2,Integer.parseInt(Age));
			ps.setString(3, Fname);
			ps.setInt(4,Integer.parseInt(Salary));
			int r=ps.executeUpdate();
			if(r>0) {
				out.println("added succesfully <br>");
			}
			else {
					out.println("error inserting");
				}
			PreparedStatement ps1=con.prepareStatement("update Customer set Salary=? where ID=?");
			String Salary1=request.getParameter("Salary1");
			String ID1=request.getParameter("ID1");
			ps1.setString(1, Salary1);
			ps1.setString(2, ID1);
			int r1=ps1.executeUpdate();
			if(r1>0) {
				out.println("updated");
			}
			else {
				out.println("error updating");
			}
			PreparedStatement ps2=con.prepareStatement("delete from Customer where ID=?");
			String ID2=request.getParameter("ID2");
			ps2.setString(1, ID2);
			int r2=ps2.executeUpdate();
			if(r2>0) {
				out.println("deleted <br>");
			}
			else {
					out.println("error deleting");
				}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
