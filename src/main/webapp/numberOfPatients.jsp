<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Number of patients</title>
</head>
<body>
  <div class = "main"> 
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
    <p style = "color:red"><%= errorMessage %></p>
  <%
    }
    else
    {
    int numberOfPatients = 0;
    numberOfPatients = request.getAttribute("numberOfPatients");
  %>
  <p><%= numberOfPatients %></p>
  <%}%>
  </div>
</body>
</html>