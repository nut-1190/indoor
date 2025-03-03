<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>7B</title>
</head>
<body>
<%
	response.setContentType("text/html");
	String name=request.getParameter("name");
	String dept=request.getParameter("dept");
	String addr=request.getParameter("addr");
	int id=Integer.parseInt(request.getParameter("id"));
	double salary=Float.parseFloat(request.getParameter("salary"));
	if (salary<20000){
		salary=salary+(salary*(0.1));
		out.println("Salary Increased<br>");
	}
	else{
		out.println("Salary Not Increased<br>");
	}
	out.println("The Details are<br>");
	out.println(name+" "+dept+" "+id+" "+salary);
	
%>
</body>
</html>