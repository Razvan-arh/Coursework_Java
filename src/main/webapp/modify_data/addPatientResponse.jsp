<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<div class="main">
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
    else
    {
      String success = (String) request.getAttribute("patientAdded");
      %>
        <p><%=success%></p>
      <%
    }
    %>

  <p style="position: fixed; bottom: 10px; width: 100%; text-align: center;">
    <a href = "index.html">Go back to the main page</a>
  </p>
  
</div>
</body>