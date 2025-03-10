package test;
import java.util.*;
import java.io.*;
import java.sql.*;
public class JDBC4 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected succesfully");
			}
			Scanner sc=new Scanner(System.in);
			System.out.println("insert the values:");
			String sql="insert into Stud values(?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			String Sname=sc.next();
			String grade=sc.next();
			ps.setString(1, Sname);
			ps.setString(2, grade);
			int r=ps.executeUpdate();
			if(r>0) {
				System.out.println("inserted succesfully");
			}
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Stud");
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2));
			}
			PreparedStatement ps1=con.prepareStatement("update Stud set grade='S' where Sname='Ram'");
			int r1=ps1.executeUpdate();
			if(r1>0) {
				System.out.println("updated");
			}
			Statement stmt1=con.createStatement();
			System.out.println("after updation the records are:");
			ResultSet rs1=stmt1.executeQuery("select * from Stud");
			while(rs1.next()) {
				System.out.println(rs1.getString(1)+" "+rs1.getString(2));
			}
			
		} catch(Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
