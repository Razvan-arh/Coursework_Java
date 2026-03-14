package Coursework.src.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Coursework.src.java.model.ModelFactory;


@WebServlet("/getpatientbyname")
public class GetPatientByName 
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //requests the first and last name of the patient
        String firstNameString = request.getParameter("firstname");
        String lastNameString = request.getParameter("lastname");
        
        
        try 
        {
            Model model = ModelFactory.getModel();


        } catch (IOException e) 
    {
      request.setAttribute("errorMessage", "Error loading data: " + e.getMessage());
      ServletContext context = getServletContext();
      RequestDispatcher dispatch = context.getRequestDispatcher("/error.jsp");
      dispatch.forward(request, response);
    }

    }
}
