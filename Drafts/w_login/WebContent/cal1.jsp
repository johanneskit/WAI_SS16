<HTML>
<HEAD>
<TITLE>BilderApp</TITLE>
</HEAD>

<BODY BGCOLOR="white">

	<%@ page language="java" import="cal.*"%>
	<jsp:useBean id="login" scope="session" class="user.LoginBean" />
	<jsp:useBean id="manage" scope="session" class="cal.Verwaltung" />

	<%
		login.processRequest(request);
		if (login.getProcessError() == false) {

			manage.getContextValues(request);
			if (manage.isAdmin == true) {
	%>
	<CENTER>
		<br>
		<form ><!--  method=GET action=registrate.html anpassen -->
		
			<input type=submit name=action value="Benutzerverwaltung"> 
			<input type=submit name=action value="Kamera anlegen"> 
			<input type=submit name=action value="Bilder anzeigen">

		</form>
		<hr>

	</CENTER>
	<%
		}
			if (manage.isAdmin == false) {
				String redirectURL = "http://localhost:8080/w_bean/noAdmin.jsp";
				response.sendRedirect(redirectURL);
				//oder direkt an die bilder anzeigen kamera anlegen seite
			}

		} else {
	%>
	<font size=5> Wrong input You must enter your name and Password
		correctly. </font>
	<%
		}
	%>
</BODY>
</HTML>




