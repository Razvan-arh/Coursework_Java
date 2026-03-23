package servlets;

import java.io.IOException;
import java.util.HashMap;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFactory;
import model.Model;

@WebServlet("/viewpageservlet")
public class ViewPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String patientId = request.getParameter("PatientId");        
        try 
        {
            Model model = ModelFactory.getModel();

            if (patientId == null || patientId.trim().isEmpty()) 
            {
                // if either input is empty send error message
                request.setAttribute("errorMessage", "Patient doesn't exist or something went wrong.");
            } 
            else 
            {
                HashMap<String, String> patientHashMap = model.patientInfo(patientId); 
                request.setAttribute("patientInfo", patientHashMap);
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/search/patientPage.jsp");
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
