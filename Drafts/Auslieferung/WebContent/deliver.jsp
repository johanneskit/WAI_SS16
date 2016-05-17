<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebCam Results</title>
</head>
<body>

	<%@ page language="java" import="site.*"%>
	<jsp:useBean id="getImages" scope="session" class="site.getImagesBean" />

	<%getImages.processRequest(request);%>
	
	<p><%out.print(getImages.getNumImages());%> Bilder angefordert.

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
					<TD>
						<a href="/Auslieferung/imageServlet?id=<%out.print(getImages.getImageID(i));%>">
							<img src="/Auslieferung/imageServlet?id=<%out.print(getImages.getImageID(i));%>&thumb=true">
						</a>
						<br>
						<font size="1"><%out.print(getImages.getImageText(i));%></font>
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

</body>
</html>