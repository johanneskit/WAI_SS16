<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="auslieferung.*"%>
<jsp:useBean id="getMask" scope="session" class="auslieferung.getMaskBean" />

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
		Webcam:
		<select <% if (getMask.getSelectedCam().equals("")) {%> name="cam" <%} else {%> disabled <% }//if %>>
			<% for (int i=0; i < getMask.getNumCams(); i++) { %>
			<option <% if (getMask.getSelectedCam().equals(getMask.getCam(i))) { %>selected<% }//if %> value="<%out.print(getMask.getCam(i));%>"><%out.print(getMask.getCam(i));%></option>
			<% } //for %>
		</select><br>
		<% if (!getMask.getSelectedCam().equals("")) {%> Jahr: <% }//if %>
		<select <% if (getMask.getSelectedYear().equals("")) {%> name="year" <%} else {%> disabled <% }//if %> <% if (getMask.getSelectedCam().equals("")) {%> hidden=true <% }//if %>>
			<% for (int i=0; i < getMask.getNumYears(); i++) { %>
			<option <% if (getMask.getSelectedYear().equals(getMask.getYear(i))) { %>selected<% }//if %> value="<%out.print(getMask.getYear(i));%>"><%out.print(getMask.getYear(i));%></option>
		<% } //for %>
		</select><br>
		<% if (!getMask.getSelectedYear().equals("")) {%> Monat: <% }//if %>
		<select <% if (getMask.getSelectedMonth().equals("")) {%> name="month" <%} else {%> disabled <% }//if %> <% if (getMask.getSelectedYear().equals("")) {%> hidden=true <% }//if %>>
			<% for (int i=0; i < getMask.getNumMonths(); i++) { %>
			<option <% if (getMask.getSelectedMonth().equals(getMask.getMonth(i))) { %>selected<% }//if %> value="<%out.print(getMask.getMonth(i));%>"><%out.print(getMask.getMonth(i));%></option>
			<% } //for %>
		</select><br>
		<% if (!getMask.getSelectedMonth().equals("")) {%> Tag: <% }//if %>
		<select <% if (getMask.getSelectedDay().equals("")) {%> name="day" <%} else {%>  disabled <% }//if %> <% if (getMask.getSelectedMonth().equals("")) {%> hidden=true <% }//if %>>
			<% for (int i=0; i < getMask.getNumDays(); i++) { %>
			<option <% if (getMask.getSelectedDay().equals(getMask.getDay(i))) { %>selected<% }//if %> value="<%out.print(getMask.getDay(i));%>"><%out.print(getMask.getDay(i));%></option>
			<% } //for %>
		</select><br>
		<% if (!getMask.getSelectedDay().equals("")) {%> Stunde: <% }//if %>
		<select <% if (getMask.getSelectedHour().equals("")) {%> name="hour" <%} else {%> disabled <% }//if %> <% if (getMask.getSelectedDay().equals("")) {%> hidden=true <% }//if %>>
			<% for (int i=0; i < getMask.getNumHours(); i++) { %>
			<option <% if (getMask.getSelectedHour().equals(getMask.getHour(i))) { %>selected<% }//if %> value="<%out.print(getMask.getHour(i));%>"><%out.print(getMask.getHour(i));%></option>
			<% } //for %>
		</select><br>
		<select name="minute" <% if (getMask.getSelectedHour().equals("")) {%> hidden=true <% }//if %>>
			<% for (int i=0; i < getMask.getNumMinutes(); i++) { %>
			<option value="<%out.print(getMask.getMinute(i));%>"><%out.print(getMask.getMinute(i));%></option>
			<% } //for %>
		</select><br>
		
		<input type=hidden name="cam" value="<%out.print(getMask.getSelectedCam());%>">
		<input type=hidden name="year" value="<%out.print(getMask.getSelectedYear());%>">
		<input type=hidden name="month" value="<%out.print(getMask.getSelectedMonth());%>">
		<input type=hidden name="day" value="<%out.print(getMask.getSelectedDay());%>">
		<input type=hidden name="hour" value="<%out.print(getMask.getSelectedHour());%>">
		
		<!-- "Autoclick" nach jedem Ändern des Formulars irgendwie hinbasteln -->
		<% if (getMask.getSelectedHour().equals("")) { %>
		<input type="submit" value="Einloggen">
		<% } else { %>
		<input type="submit" value="Absenden">
		<% } //if %>
		<input type="submit" value="Reset" name="reset">
	</form>
	
	<!-- DB Connection schließen! -->

</body>
</html>