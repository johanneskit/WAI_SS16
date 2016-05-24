<HTML>
<HEAD><TITLE>Admin</TITLE></HEAD>

<BODY BGCOLOR="white">

<%@ page language="java" import="cal.*" %>
<jsp:useBean id="login" scope="session" class="user.LoginBean" />
<jsp:useBean id="manage" scope="session" class="cal.Verwaltung" />



<CENTER>
<br>
	<form method=GET action=registrate.html>

		<input type=submit name=action value="Benutzerverwaltung">
		<input type=submit name=action value="Kamera anlegen">
		<input type=submit name=action value="Bilder anzeigen">

	</form>
<hr>

<BR>
</CENTER>
</BODY>
</HTML>




