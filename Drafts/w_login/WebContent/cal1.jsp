<HTML>
<HEAD><TITLE>BilderApp</TITLE></HEAD>

<BODY BGCOLOR="white">

<%@ page language="java" import="cal.*" %>
<jsp:useBean id="login" scope="session" class="user.LoginBean" />
<jsp:useBean id="manage" scope="session" class="cal.Verwaltung" />

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
	manage.getContextValues(request);
	if(manage.isAdmin == true)
	{
		String redirectURL = "http://localhost:8080/w_bean/admin.jsp"; 
		response.sendRedirect(redirectURL);
	}
	if(manage.isAdmin == false)
	{
		String redirectURL = "http://localhost:8080/w_bean/noAdmin.jsp"; 
		response.sendRedirect(redirectURL);
	}
	
	
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




