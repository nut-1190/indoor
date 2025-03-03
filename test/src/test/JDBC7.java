package test;
import java.sql.*;
public class JDBC7 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Registration","root","");
			if(!con.isClosed()) {
				System.out.println("connected to db");
			}
			Statement stmt=con.createStatement();
			System.out.println("students enrolled in 2023:");
			ResultSet rs=stmt.executeQuery("select * from student where YOA=2023");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+" "+rs.getString(2));
			}
			System.out.println("students having BE program:");
			ResultSet rs1=stmt.executeQuery("select * from student where Program='BE'");
			while(rs1.next()) {
				System.out.println(rs1.getInt(1)+" "+rs1.getString(2));
			}
			
		}catch(Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
