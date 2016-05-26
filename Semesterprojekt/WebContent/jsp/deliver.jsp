<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebCam Results</title>
</head>
<body>

	<%@ page language="java" import="auslieferung.*"%>
	<jsp:useBean id="getImages" scope="session" class="auslieferung.getImagesBean" />

	<%getImages.processRequest(request);%>
	
	<p>
	<% if (getImages.getNumImages() == 0) {%>
	Keine Bilder zu dieser Anfrage vorhanden oder keine Rechte!
	<a href="/Semesterprojekt_Webcams/jsp/select.jsp">zurück und neue Anfrage stellen</a>
	<% } else {
	out.print(getImages.getNumImages());%> Bilder zu dieser Anfrage vorhanden.

	<p>
	<center>
		<TABLE WIDTH="60%" BGCOLOR="lightgray" CELLPADDING="15">
			<%
				for (int i = 0; i < getImages.getNumImages(); i++) {
					if (i % 7 == 0) {
			%>
				<TR> <!-- 7 Thumbs pro Row // BESSER: getNext() oder while( hasNext() )-->
					<%
					} //if
					%>
					<TD align="center">
						<a href="/Semesterprojekt_Webcams/imageServlet?id=<%out.print(getImages.getImageID(i));%>">
							<img src="/Semesterprojekt_Webcams/imageServlet?id=<%out.print(getImages.getImageID(i));%>&thumb">
						</a>
						<br>
						<font size="2"><%out.print(getImages.getImageText(i));%></font>
					</TD>
				<%
				if (i % 7 == 6 && i != 1) {
				%>
				</TR>
			<%
				} //if
			} //for
			%>
		</TABLE>
	</center>
	
	<% }//if %>

</body>
</html>