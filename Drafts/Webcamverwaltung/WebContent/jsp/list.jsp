<%@ page language="java" contentType="text/html" %>
<%@ page import="model.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>      
    <title>Bibliothek</title>     
  </head>
  <body>
  	<table border="1">
  		<tbody>
	  		<tr>	  						
				<td>Name:</td>
				<td>Url:</td>
				<td>Id:</td>	
			</tr>			
			<c:forEach var="webcam" items="${webcam}">
				<tr>
					<td><c:out value="${webcam.name}"/></td>					
					<td><c:out value="${webcam.url}"/></td>
					<td><c:out value="${webcam.id}"/></td>
					<td><a href="edit?action=edit&id=${webcam.id}">Ändern</a></td>
					<td><a href="edit?action=delete&id=${webcam.id}">Löschen</a></td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  	<br>
  	<a href="edit?action=add">Neue Webcam hinzufügen</a>
  </body>
</html>