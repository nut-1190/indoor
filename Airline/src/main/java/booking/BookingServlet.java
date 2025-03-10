package booking;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Retrieve form parameters
            String fname = request.getParameter("fname");
            String lname = request.getParameter("lname");
            String arrivalDate = request.getParameter("arrivalDate");
            String departureDate = request.getParameter("departureDate");
            String sourceStation = request.getParameter("sourceStation");
            String destinationStation = request.getParameter("destinationStation");
            String bookingDate = request.getParameter("bookingDate");
            String amount = request.getParameter("amount");
            String seat = request.getParameter("seat");
            String flightID = request.getParameter("Flight_ID");

            // JDBC driver and database URL
            String jdbcDriver = "com.mysql.cj.jdbc.Driver";
            String dbURL = "jdbc:mysql://localhost:3306/Airline";
            String dbUser = "root";
            String dbPassword = "";

            try {
                // Register JDBC driver
                Class.forName(jdbcDriver);

                // Open a connection
                try (Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
                    // Create a SQL statement
                    String sql = "INSERT INTO Customer (Fname, Lname, Departure, Arrival, Source, Destination, Date, Amount, Seat, Flight_ID) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
                    try (PreparedStatement statement = conn.prepareStatement(sql)) {
                        // Set the parameters
                        statement.setString(1, fname);
                        statement.setString(2, lname);
                        statement.setString(3, departureDate);
                        statement.setString(4, arrivalDate);
                        statement.setString(5, sourceStation);
                        statement.setString(6, destinationStation);
                        statement.setString(7, bookingDate);
                        statement.setString(8, amount);
                        statement.setString(9, seat);
                        statement.setString(10, flightID);

                        // Execute the update
                        int rowsAffected = statement.executeUpdate();

                        // Check if the data was successfully inserted
                        if (rowsAffected > 0) {
                        	String sql1 = "SELECT * FROM Customer WHERE Fname = ?";
                        	try (PreparedStatement preparedStatement = conn.prepareStatement(sql1)) {
                        	    // Set the parameter value for the WHERE condition
                        	    preparedStatement.setString(1, fname);

                        	    // Process ResultSet and store data in a List 
                        	    List<MyData> dataList = new ArrayList<>();
                        	    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        	        while (resultSet.next()) {
                        	            MyData data = new MyData();
                        	            data.setColumn1(resultSet.getString("Fname"));
                        	            data.setColumn2(resultSet.getString("Lname"));
                        	            data.setColumn3(resultSet.getString("Departure"));
                        	            data.setColumn4(resultSet.getString("Arrival"));
                        	            data.setColumn5(resultSet.getString("Source"));
                        	            data.setColumn6(resultSet.getString("Destination"));
                        	            data.setColumn7(resultSet.getString("ID"));
                        	            data.setColumn8(resultSet.getString("Date"));
                        	            data.setColumn9(resultSet.getString("Amount"));
                        	            data.setColumn10(resultSet.getString("Seat"));
                        	            data.setColumn11(resultSet.getString("Flight_ID"));
                        	            dataList.add(data);
                        	        }
                        	    }

                        	    // Set data as a request attribute
                        	    request.setAttribute("dataList", dataList);

                        	    // Dispatch to JSP page
                        	    RequestDispatcher dispatcher = request.getRequestDispatcher("/bookingdetails.jsp");
                        	    dispatcher.forward(request, response);
                        	}
                        	catch (SQLException e) {
                        	    e.printStackTrace();
                        	    // Handle database exception
                        	}
                            
                        }
                         else {
                            out.println("<h2>Error: Booking failed.</h2>");
                        }
                    }
                }
            } catch (ClassNotFoundException | SQLException e) {
                out.println("<h2>Error: " + e.getMessage() + "</h2>");
            }
        }
    }
}
