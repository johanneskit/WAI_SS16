<HTML>
<HEAD><TITLE>Registrierungsinfo</TITLE></HEAD>

<BODY BGCOLOR="white">

<%@ page language="java" import="user.*" %>
<jsp:useBean id="Reg" scope="session" class="user.Registrate" />

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




