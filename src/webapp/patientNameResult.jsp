<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<div class="main">
  <h1>Search Result</h1>
  <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (errorMessage != null)
    {
  %>
      <p style="color: red;"><%= errorMessage %></p>
  <%
    }
    List<String> patientsId = (List<String>) request.getAttribute("resultId");
    if (patientsId != null && patientsId.size() != 0)
    {
    %>
    <ul>
      <%
        for (String patientId : patientsId)
        {
      %>
      <li><%=patientId%></li>
     <% }
    } else if (errorMessage == null)
    {%>
      <p>Nothing found</p>
  <%}%>
  </ul>
</div>
</body>
</html>