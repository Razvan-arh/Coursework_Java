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
    String numberOfPatients = (String) request.getAttribute("numberOfPatients");
    if (numberOfPatients != null)
    {
  %>
  <ul>
    <li><%= numberOfPatients %></li>
  </ul>
  <%}
    else
    {
      %>
        <p>Something went wrong</p>
      <%
    }
  %>
  </div>
</body>
</html>