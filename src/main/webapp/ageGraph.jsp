<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    
<html>
<script
src="https://cdn.jsdelivr.net/npm/chart.js@4.5.0">
</script>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
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
       String ageGraphJson = (String) request.getAttribute("ageGraphJson");
         %>
      <canvas id="ageChart" width="400" height="200"></canvas>
      <script>
        const data = <%=ageGraphJson%>;        
        const labels = Object.keys(data);
        const values = Object.values(data);
        new Chart(document.getElementById('ageChart'), {
          type: 'bar',
          data: {
            labels: labels,
            datasets: [{
              label: 'Number of Patients',
              data: values,
              backgroundColor: 'rgba(54, 162, 235, 0.7)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1
            }]
          },
          options: {
            responsive: true,
            plugins: {
              legend: { display: false } // super minimalist
            },
            scales: {
              x: { title: { display: true, text: 'Age Range' } },
              y: { title: { display: true, text: 'Patients' }, beginAtZero: true }
            }
          }
        });
      </script>

</div>
</body>