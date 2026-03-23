package servlets;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import model.ModelFactory;

@WebServlet("/citypatients")
public class CityPatientsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //requests the first and last name of the patient
        String city = request.getParameter("city");
        
        try 
        {
            Model model = ModelFactory.getModel();

            if (city == null || city.trim().isEmpty()) 
            {
                // if input is empty send error message
                request.setAttribute("errorMessage", "Please enter city.");
            } 
            else 
            {
                String searchResult = "" + model.numberOfPetientsCity(city);
                request.setAttribute("cityPatients", searchResult);
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/cityPatientsResponse.jsp");
            dispatch.forward(request, response);

        } catch (IOException e) 
    {
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }

    }

}
