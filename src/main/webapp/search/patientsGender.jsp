<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Gender distribution</title>
</head>
<body>
  <div class = "main"> 
  <%
    Map<String, Integer> patientsGenderMap = 
        (Map<String, Integer>) request.getAttribute("patientsGender");
    
    String errorMessage = (String) request.getAttribute("errorMessage");
    if (patientsGenderMap == null || patientsGenderMap.isEmpty() || errorMessage != null) {
  %>
    <p style = "color:red"><%= errorMessage %></p>
  <%
    }
    else
    {
  %>
    <table border="1">
      <tr>
        <th>Gender</th>
        <th>Count</th>
      </tr>

      <%
        for (String key : patientsGenderMap.keySet()) {
      %>
        <tr>
          <td><%= key %></td>
          <td><%= patientsGenderMap.get(key) %></td>
        </tr>
      <%
        }
      %>
    </table>
  <%}
  %>
  </div>

  <p style="position: fixed; bottom: 10px; width: 100%; text-align: center;">
    <a href = "index.html">Go back to the main page</a>
  </p>
  
</body>
</html>