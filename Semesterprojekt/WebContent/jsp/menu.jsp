<%@ page language="java" contentType="text/html; charset=UTF-8" import="user.*"
    pageEncoding="UTF-8"%>
    
	<jsp:useBean id="login" scope="session" class="user.LoginBean" />
	<jsp:useBean id="manage" scope="session" class="user.Verwaltung" />
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hauptmenü</title>
</head>
<body>

	<%  login.processRequest(request);
		if (login.getProcessError() == false) {

			manage.getContextValues(request);
			if (manage.isAdmin == true) {
	%>
	
	<CENTER>
	
		<br>
		<a href="/Semesterprojekt_Webcams/listBenutzer">Benutzerverwaltung</a>
		<a href="/Semesterprojekt_Webcams/listCam">Kamera anlegen</a>
		<a href="/Semesterprojekt_Webcams/jsp/select.jsp">Bilder anzeigen</a>
		<a href="/Semesterprojekt_Webcams/testform.html">Bilder anzeigen (test)</a>
		<a href="/Semesterprojekt_Webcams/verwaltung?logout">Abmelden(test)</a>
		<hr>

	</CENTER>
	
	<%
		}
			else if (manage.isAdmin == false) {
	%>
	
	<CENTER>
		
		<br>
		<a href="/Semesterprojekt_Webcams/listCam">Kamera anlegen</a>
		<a href="/Semesterprojekt_Webcams/jsp/select.jsp">Bilder anzeigen</a>
		<a href="/Semesterprojekt_Webcams/verwaltung?logout">Abmelden(test)</a>
		<hr>

	</CENTER>
	
	<%
			}

		} else {
	%>
	
	<font size=5> Wrong input. You must enter your name and password correctly. </font>
	
	<%
		}
	%>
	
</BODY>
</HTML>
