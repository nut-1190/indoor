<%@ page import="java.util.List" %>
<%@ page import="booking.MyData" %>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>
    Home
    </title>
    <style>
        .carousel-caption h5,
        .carousel-caption p {
            color: #000 !important; /* Set the desired dark color */
        }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
  </head>
  <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="base.html">Airline Management System</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
      
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="base.html">Home</a>
            </li>
            
            <li class="nav-item">
                <a class="nav-link" href="bookings.html">New Booking</a>
              </li>
            <li class="nav-item">
                <a class="nav-link" href="/Customer">Booking Details</a>
              </li>           

          </ul>
          <form class="d-flex" action="/search" method="post">
            <input class="form-control me-2" type="search" placeholder="Flight" name='search' aria-label="Search">
            <button class="btn btn-outline-success" type="submit">Search</button>
          </form>
        </div>
    </nav>

<table class="table">
  <thead class="thead-light">
    <tr>
      <th scope="col">Fname</th>
      <th scope="col">Lname</th>
      <th scope="col">Arrival</th>
      <th scope="col">Departure</th>
      <th scope="col">Source</th>
      <th scope="col">Destination</th>
      <th scope="col">ID</th>
      <th scope="col">Booking Date</th>
      <th scope="col">Amount</th>
      <th scope="col">Seat</th>
      <th scope="col">Flight_ID</th>
    </tr>
  </thead>
  <tbody>
            <%
                List<MyData> dataList = (List<MyData>) request.getAttribute("dataList");
                if (dataList != null) {
                    for (MyData data : dataList) {
            %>
            <tr>
                <td><%= data.getColumn1() %></td>
                <td><%= data.getColumn2() %></td>
                <td><%= data.getColumn3() %></td>
                <td><%= data.getColumn4() %></td>
                <td><%= data.getColumn5() %></td>
                <td><%= data.getColumn6() %></td>
                <td><%= data.getColumn7() %></td>
                <td><%= data.getColumn8() %></td>
                <td><%= data.getColumn9() %></td>
                <td><%= data.getColumn10() %></td>
                <td><%= data.getColumn11() %></td>
                <!-- Add cells for other columns -->
            </tr>
            <%
                    }
                }
            %>
        </tbody>
</table>

