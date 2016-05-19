<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="site.*"%>
<jsp:useBean id="getMask" scope="session" class="site.getMaskBean" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Auswahlmaske Kamera</title>
</head>
<body>

	<%getMask.init();%>

	<form method=GET action="selectYear.jsp">
		<select name="cam">
			<% for (int i=0; i < getMask.getNumCams(); i++) { %>
			<option value="<%out.print(getMask.getCam(i));%>"><%out.print(getMask.getCam(i));%></option>
			<% } //for %>
		</select>
	</form>

</body>
</html>