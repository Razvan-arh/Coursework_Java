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
    String cityPatients = (String) request.getAttribute("cityPatients");
    if (cityPatients != null)
    {
  %>
  <ul>
    <li><%= cityPatients %></li>
  </ul>
  <%}
    else
    {
      %>
        <p>No patients live here</p>
      <%
    }
  %>
  </div>

  <p style="position: fixed; bottom: 10px; width: 100%; text-align: center;">
    <a href = "index.html">Go back to the main page</a>
  </p>
  
</body>
</html>