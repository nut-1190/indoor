package test;
import java.util.*;
import java.sql.*;
public class JDBC {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected succesfully");
			}
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Employee");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getInt(5));
			}
			ResultSet rs1=stmt.executeQuery("select * from Employee where Project='WebDev'");
			System.out.println("employees working in webdev:");
			while(rs1.next()) {
				System.out.println(rs1.getInt(1)+" "+rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getString(4)+" "+rs1.getInt(5));
			}
			
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}
	

}
