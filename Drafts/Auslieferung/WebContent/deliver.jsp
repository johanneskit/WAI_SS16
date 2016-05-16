<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebCam Results</title>
</head>
<body>

	<%@ page language="java" import="site.*"%>
	<jsp:useBean id="getImages" scope="session" class="site.getImagesBean" />

	<% getImages.processRequest(request); %>

	<center>
	<TABLE WIDTH="60%" BGCOLOR="lightgray" CELLPADDING="15">
		<%
		int num = 0;
		for(int i=0; i < (getImages.getNumImages() / 7); i++)
		{
		%>
 		<TR> <!-- 7 Thumbs pro Row // BESSER: getNext() oder while( hasNext() )-->
	 		<%
			for(int j=0; j < 7; j++)
			{
			%>
			<TD> <a href="/Auslieferung/imageServlet?id=<%out.print(getImages.getImageID(num));%>">
			     <img src="/Auslieferung/imageServlet?id=<%out.print(getImages.getImageID(num));%>" style="width:60px;height:60px;">
				 </a>
				 <br><font size="2"><%out.print(getImages.getImageText(num)); num++;%></font>
			</TD>
			<% }//for %>
		</TR>
		<% }//for %>
	</TABLE>
	</center>

</body>
</html>