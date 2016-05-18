<HTML>
<HEAD><TITLE>Calendar: A JSP APPLICATION</TITLE></HEAD>

<BODY BGCOLOR="white">

<%@ page language="java" import="cal.*" %>
<jsp:useBean id="login" scope="session" class="cal.Login" />

<%
	login.processRequest(request);
	if (login.getProcessError() == false) {
%>

<!-- html table goes here -->
<CENTER>
		<TR>
			<font size = 5>
				Inputs are correct.
			</font>
	 	</TR>
</FORM>
<BR>
<%
	} else {
%>
<font size=5>
	Wrong input
	You must enter your name and Password correctly.
</font>
<%
	}
%>
</BODY>
</HTML>




