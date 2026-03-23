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
       String cityGraphJson = (String) request.getAttribute("cityGraphJson");
         %>
  <div id="chart-container">
      <canvas id="cityChart"></canvas>
  </div>

  <script>
    const data = <%=cityGraphJson%>;        
    const labels = Object.keys(data);
    const values = Object.values(data);

    // Predefined colors for better distinction
    const backgroundColors = [
      '#4dc9f6', '#f67019', '#f53794', '#537bc4', 
      '#acc236', '#166a8f', '#00a950', '#58595b', '#8549ba'
    ];

    new Chart(document.getElementById('cityChart'), {
      type: 'pie',
      data: {
        labels: labels,
        datasets: [{
          data: values,
          backgroundColor: backgroundColors.slice(0, labels.length), // match number of slices
          borderColor: '#fff',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false, // important to respect our container size
        plugins: {
          legend: { display: true, position: 'right' }
        }
      }
    });
  </script>

</div>
</body>