package test;
import java.util.*;
import java.sql.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBC1 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Dept","root","");
			if(!con.isClosed()) {
				System.out.println("connected succesfully");
			}
			Scanner sc=new Scanner(System.in);
				System.out.println("Enter the records");
				String sql="insert into Department values(?,?,?,?,?)";
				PreparedStatement ps=con.prepareStatement(sql);
				int ID=sc.nextInt();
				String Dname=sc.next();
				int YEst=sc.nextInt();
				String Hname=sc.next();
				int No_of_emp=sc.nextInt();
				ps.setInt(1, ID);
				ps.setString(2, Dname);
				ps.setInt(3,YEst);
				ps.setString(4,Hname);
				ps.setInt(5, No_of_emp);
				int r=ps.executeUpdate();
				if(r>0) {
					System.out.println("Added Successfully");
				}
				Statement stmt=con.createStatement();
				ResultSet rs=stmt.executeQuery("select * from Department");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getString(4)+" "+rs.getInt(5));
				}
			
		} catch (Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
