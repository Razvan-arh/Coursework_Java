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

@WebServlet("/deletepatient")
public class DeletePatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String patientID = new String();

        // because this is only called on a patients page
        // there is no need for checks
        patientID = request.getParameter("PatientId");
 

        try {

            Model model = ModelFactory.getModel();
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/modify_data/deletePatientResponse.jsp");

            if(patientID == null || patientID.trim().isEmpty())
            {
                request.setAttribute("errorMessage", "Something went wrong");
                dispatch.forward(request, response);
            }
            else
            {            
                model.deletePatientData(patientID);
                request.setAttribute("patientDeleted", "patient succesfully deleted");
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
