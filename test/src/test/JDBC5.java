package test;
import java.sql.*;
import java.util.*;
public class JDBC5 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected.");
			}
			
			PreparedStatement ps1=con.prepareStatement("update Prof set designation='Asso' where designation='Assistant'");
			int r1=ps1.executeUpdate();
			if (r1>0) {
				System.out.println("updated.");
			}
			System.out.println("after updation:");
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Prof");
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2));
			}
		}catch(Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
