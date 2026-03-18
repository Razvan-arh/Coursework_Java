<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <jsp:include page="/meta.jsp"/>
    <title>
      Patient Information
    </title>
  </head>
  <div class="main">
    <body>
      <%
        String errorMessage = (String) request.getAttribute("errorMessage");

        if(errorMessage != null)
        {
          %>
            <p style="color: red;"><%= errorMessage %></p>
          <%
        }
        else
        {
          HashMap <String, String> patientInfo = (HashMap <String, String>) request.getAttribute("patientInfo");
          if(patientInfo == null || patientInfo.isEmpty())
          {
            %>
            <p>Something went wrong</p>
            <%
          }
          else{
          for (String key : patientInfo.keySet())
          {
          %>
            <u>
              <li><%=key%> : <%=patientInfo.get(key)%></li>
            </u>
          <%
          }
        }
        }
      %>
    </body>
  </div>
  </html>