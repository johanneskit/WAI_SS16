<%@ page language="java" contentType="text/html; charset=UTF-8" import="user.*"
    pageEncoding="UTF-8"%>
    
	<jsp:useBean id="Reg" scope="session" class="user.Registrate" />
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registrierung</title>
</head>
<body>
<%
	Reg.feedUser(request);
	if (Reg.UserAlreadyExists() == true) {
%>
	<font size=5>
		User already exists
	</font>
<%
	}
	else if(Reg.PwAreNotEqual() == true){
%>
	<Center>		
		<font size=5>
			You typed two different passwords.
			Please try again!
		</font>
	</Center>
	
<%		}
	else{
%>


	<Center>		
		<font size=5>
			registrated!
		</font>
	</Center>

<% } %>

</BODY>
</HTML>




