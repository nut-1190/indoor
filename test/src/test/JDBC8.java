package test;
import java.sql.*;
import java.util.*;
public class JDBC8 {
	public static void main(String args[]) {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
			if(!con.isClosed()) {
				System.out.println("connected");
			}
			Scanner sc=new Scanner(System.in);
			String sql="insert into Employee values(?,?,?)";
			PreparedStatement ps=con.prepareStatement(sql);
			int ID=sc.nextInt();
			String Fname=sc.next();
			int Salary=sc.nextInt();
			ps.setInt(1, ID);
			ps.setString(2, Fname);
			ps.setInt(3, Salary);
			int r=ps.executeUpdate();
			if(r>0) {
				System.out.println("inserted");
			}
			Statement stmt=con.createStatement();
			ResultSet rs=stmt.executeQuery("select * from Employee where Salary>50000");
			while(rs.next()) {
				System.out.println(rs.getString(2));
			}
		}catch(Exception e) {
			System.err.print(e.getMessage());
		}
	}

}
