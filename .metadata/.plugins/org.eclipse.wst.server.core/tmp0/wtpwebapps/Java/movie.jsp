<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String name=request.getParameter("name");
int age=Integer.parseInt(request.getParameter("age"));
if (age>62){
	out.println("The movie ticket price is: Rs.50");
} else if(age<10){
	out.println("The movie ticket price is: Rs.30");
} else{
	out.println("The movie ticket price is: Rs.80");
}
%>