<%@ page import="model.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>Bibliothek</title>
  </head>  
  <body>
	<form name="edit" action="edit" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>Benutzername:</td>
					<td><c:out value="${benutzer.benutzername}"/></td>		
				</tr>			
				<tr>		
					<td>Priorit�t:</td>	
					<td><input type="text" name="prioritaet" value="${benutzer.prioritaet}"></td>
				</tr>				
				<tr>	
					<td colspan="2">
					<input type="submit" name="btnSave" value="Save">
					</td>
				</tr>				
			</tbody>
		</table>
		<input type="hidden" name="id" value="${benutzer.id}">
	</form>
  </body>
</html>
