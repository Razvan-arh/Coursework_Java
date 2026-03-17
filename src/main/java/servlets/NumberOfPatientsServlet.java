package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFactory;
import model.Model;

import java.io.IOException;


@WebServlet("/numberOfPatients")
public class NumberOfPatientsServlet extends HttpServlet
{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            Model model = ModelFactory.getModel();

            String numberOfPatients = String.valueOf(model.numberOfPatients());

            request.setAttribute("numberOfPatients", numberOfPatients);
            
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/numberOfPatients.jsp");
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
