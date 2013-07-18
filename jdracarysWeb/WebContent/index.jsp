<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JDracarys Web</title>
</head>
<%
	System.out.println("passei");
%>
<body>
	<form action="JDracarysServlet" method="POST">
		<label for="url">URL:</label>
		<input type="text" id="url" name="url">
		<br>
		<label for="connections">Connections:</label>
		<input type="text" id="connections" name="connections">
		<br>
		<label for="log">Log:</label>
		<input type="text" id="log" name="log">
		<br>
		<input type="submit" value="submit"/>
	</form>
</body>
</html>