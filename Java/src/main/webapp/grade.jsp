<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
int AIML= Integer.parseInt(request.getParameter("AIML"));
int OS=Integer.parseInt(request.getParameter("OS"));
int SE=Integer.parseInt(request.getParameter("SE"));
int JAVA=Integer.parseInt(request.getParameter("JAVA"));
int DBS=Integer.parseInt(request.getParameter("DBS"));
int average = (AIML+OS+SE+JAVA+DBS)/5;
if (average > 90){
out.println("S grade");
} else if (average >80 && average <90)
{
out.println("A grade");
}else { 
	out.println("B");
}
%>