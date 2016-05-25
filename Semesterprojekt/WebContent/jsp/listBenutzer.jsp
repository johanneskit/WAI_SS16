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
				<td>Benutzername</td>
				<td>Passwort</td>
				<td>Priorit�t</td>
				<td>Webcams</td>
				<td>Id</td>	
			</tr>			
			<c:forEach var="benutzer" items="${benutzer}">
				<tr>
					<td><c:out value="${benutzer.benutzername}"/></td>					
					<td><c:out value="${benutzer.passwort}"/></td>
					<td><c:out value="${benutzer.prioritaet}"/></td>
					<td><c:out value="${benutzer.webcams}"/></td>
					<td><c:out value="${benutzer.id}"/></td>
					<td><a href="editBenutzer?action=edit&id=${benutzer.id}">�ndern</a></td>
					<td><a href="editBenutzer?action=delete&id=${benutzer.id}">L�schen</a></td>
				</tr>
			</c:forEach>	
  		</tbody>
  	</table>
  </body>
</html>