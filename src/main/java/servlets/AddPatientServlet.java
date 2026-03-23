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
import model.Model;
import model.ModelFactory;


@WebServlet("/addpatient")
public class AddPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HashMap<String, String> patientData = new HashMap<>();

        try {

            Model model = ModelFactory.getModel();

            request.getParameterMap().forEach((key, value) -> {
                patientData.put(key, value[0]);
            });

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/modify_data/addPatientResponse.jsp");

            if (model.addPatient(patientData))
            {
                request.setAttribute("patientAdded", "Patient added succesfully");
                dispatch.forward(request, response);
            }
            else
            {
                request.setAttribute("errorMessage", "patient not added, please fill in mandatory fields");
                dispatch.forward(request, response);
            }
        } catch(IOException e)
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
