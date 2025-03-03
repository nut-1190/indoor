package test1;
import java.sql.*;
import java.util.*;
public class JDBC9 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected to the db");
			}
			Scanner sc=new Scanner(System.in);
			System.out.println("enter the details:");
			String sql="insert into Stud values(?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			String Sname=sc.next();
			String Grade=sc.next();
			ps.setString(1, Sname);
			ps.setString(2, Grade);
			int r=ps.executeUpdate();
			if(r>0) {
				System.out.println("inserted succesfully");
			}
			Statement stmt=con.createStatement();
			System.out.println("Before updating:");
			ResultSet rs=stmt.executeQuery("select * from Stud");
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2));
			}
			PreparedStatement ps1=con.prepareStatement("update Stud set Grade='S' where Sname='Ram'");
			int r1=ps1.executeUpdate();
			if (r>0) {
				System.out.println("updated succesfully.");
			}
			System.out.println("after updating:");
			Statement stmt1=con.createStatement();
			ResultSet rs1=stmt1.executeQuery("select * from Stud");
			while(rs1.next()) {
				System.out.println(rs1.getString(1)+" "+rs1.getString(2));
			}
			
		}catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
