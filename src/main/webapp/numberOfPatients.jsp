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

  <p style="position: fixed; bottom: 10px; width: 100%; text-align: center;">
    <a href = "index.html">Go back to the main page</a>
  </p>
  
</body>
</html>