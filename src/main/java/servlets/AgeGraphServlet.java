package servlets;


import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import model.ModelFactory;

@WebServlet("/agedistribution")
public class AgeGraphServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Model model = ModelFactory.getModel();

            Map<String, Integer> ageGraphMap = model.getAgeDistribution();

            ObjectMapper mapper = new ObjectMapper();
            String ageGraphJson = mapper.writeValueAsString(ageGraphMap);
            request.setAttribute("ageGraphJson", ageGraphJson); 

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/ageGraph.jsp");
            dispatch.forward(request, response);

        }catch (IOException e)
        {
            request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
            dispatch.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}
