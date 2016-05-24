<%@ page import="model.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>Bibliothek</title>
  </head>  
  <body>
	<form name="edit" action="editCam" method="post">		
		<table border="1">
			<tbody>
				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" value="${webcam.name}"></td>		
				</tr>			
				<tr>		
					<td>Url:</td>	
					<td><input type="text" name="url" value="${webcam.url}"></td>
				</tr>				
				<tr>	
					<td colspan="2">
					<input type="submit" name="btnSave" value="Save">
					</td>
				</tr>				
			</tbody>
		</table>
		<input type="hidden" name="id" value="${webcam.id}">
	</form>
  </body>
</html>
