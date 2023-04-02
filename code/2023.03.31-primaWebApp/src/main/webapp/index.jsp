<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %></h1>
<br/>
<a href="hello-servlet">Hello Servlet</a>

<br/>
<%
  int a=5, b=7;
  int somma = a+b;
  String str ="Benvenuti a lezione";
%>
somma calcolata server-side: <%= somma %> <BR/>
carattere alla posizione: <%= str.charAt(somma) %><BR/>

<form action="search-servlet" method="POST">

  <input type="text" name="q" />
  <input type="submit" value="Cerca" />

</form>
</body>
</html>