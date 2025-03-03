package test;
import java.sql.*;
import java.util.*;
public class JDBC6 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected to the db");
			}
			Scanner sc=new Scanner(System.in);
			System.out.println("enter details:");
			String sql="insert into Stud1 values(?,?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			String Sname=sc.next();
			String branch=sc.next();
			String USN=sc.next();
			String CET_CK=sc.next();
			ps.setString(1, Sname);
			ps.setString(2, branch);
			ps.setString(3, USN);
			ps.setString(4, CET_CK);
			int r=ps.executeUpdate();
			if(r>0) {
				System.out.println("inserted succesfully.");
			}
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Stud1 where branch='CSE' and CET_CK='CET'");	
			while(rs.next()) {
				System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
			}
			
	}catch(Exception e) {
		System.err.print(e.getMessage());
	}

}
}
