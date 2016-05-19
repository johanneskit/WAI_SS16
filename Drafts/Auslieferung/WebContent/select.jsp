<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="site.*"%>
<jsp:useBean id="getMask" scope="session" class="site.getMaskBean" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auswahlmaske Bilder</title>
</head>
<body>

	<%getMask.init();%>
	<%getMask.processRequest(request);%>

	<form method=GET <% if (getMask.getSelectedHour().equals("")) { %>action="select.jsp"<% } else { %>action="deliver.jsp"<% }//if %>>
		<select name="cam">
			<% for (int i=0; i < getMask.getNumCams(); i++) { %>
			<option <% if (getMask.getSelectedCam().equals(getMask.getCam(i))) { %>selected<% }//if %> value="<%out.print(getMask.getCam(i));%>"><%out.print(getMask.getCam(i));%></option>
			<% } //for %>
		</select><br>
		<select name="year">
			<% for (int i=0; i < getMask.getNumYears(); i++) { %>
			<option <% if (getMask.getSelectedYear().equals(getMask.getYear(i))) { %>selected<% }//if %> value="<%out.print(getMask.getYear(i));%>"><%out.print(getMask.getYear(i));%></option>
			<% } //for %>
		</select><br>
		<select name="month">
			<% for (int i=0; i < getMask.getNumMonths(); i++) { %>
			<option <% if (getMask.getSelectedMonth().equals(getMask.getMonth(i))) { %>selected<% }//if %> value="<%out.print(getMask.getMonth(i));%>"><%out.print(getMask.getMonth(i));%></option>
			<% } //for %>
		</select><br>
		<select name="day">
			<% for (int i=0; i < getMask.getNumDays(); i++) { %>
			<option <% if (getMask.getSelectedDay().equals(getMask.getDay(i))) { %>selected<% }//if %> value="<%out.print(getMask.getDay(i));%>"><%out.print(getMask.getDay(i));%></option>
			<% } //for %>
		</select><br>
		<select name="hour">
			<% for (int i=0; i < getMask.getNumHours(); i++) { %>
			<option <% if (getMask.getSelectedHour().equals(getMask.getHour(i))) { %>selected<% }//if %> value="<%out.print(getMask.getHour(i));%>"><%out.print(getMask.getHour(i));%></option>
			<% } //for %>
		</select><br>
		<select name="minute">
			<% for (int i=0; i < getMask.getNumMinutes(); i++) { %>
			<option value="<%out.print(getMask.getMinute(i));%>"><%out.print(getMask.getMinute(i));%></option>
			<% } //for %>
		</select><br>
		
		<!-- "Autoclick" nach jedem Ändern des Formulars irgendwie hinbasteln -->
		<% if (getMask.getSelectedHour().equals("")) { %>
		<input type="submit" value="Einloggen">
		<% } else { %>
		<input type="submit" value="Absenden">
		<% } //if %>
	</form>
	
	<!-- DB Connection schließen! -->

</body>
</html>