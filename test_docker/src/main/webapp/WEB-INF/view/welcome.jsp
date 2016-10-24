<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>welcome</title>
<style>
  body { background:#00dddd; }
</style>
</head>
<body>
	<h3>welcome (spring web mvc) - menu secondaire</h3>
	message=<b>${message}</b>
	<hr/>
	<a href="../customer/info">infos customer (version simple)</a> <br/>
	<hr/>
	<a href="../../index.html">retour index.html</a> <br/>
	<hr/>
	<a href="memberOnly">memberOnly (acces restreint / spring-security)</a> <br/>
	<hr/>
	<a href="../app/finSession">fin de session / deconnexion</a>
	num session http/jee= <%=session.getId()%>
</body>
</html>