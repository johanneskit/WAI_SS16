<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
					<td><input type="text" name="name" value=""></td>		
					</tr><tr>		
					<td>Url:</td>	
					<td><input type="text" name="url" value=""></td>
					</tr><tr>	
					<td colspan="2"><input type="submit" name="btnSave" value="Save"></td>
				</tr>
			</tbody>
		</table>
	</form>
  </body>
</html>
