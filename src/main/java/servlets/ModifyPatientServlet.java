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

@WebServlet("/modifypatient")
public class ModifyPatientServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String patientID = new String();
        patientID = request.getParameter("PatientId");

        // patient info to be modified
        String field = new String();
        String value = new String();

        field = request.getParameter("field");
        value = request.getParameter("value");


        try{

            Model model = ModelFactory.getModel();
            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/modifyPatientResponse.jsp");

            if(patientID == null || patientID.trim().isEmpty()
                || field == null || field.trim().isEmpty()
                ||value == null || value.trim().isEmpty())
            {
                request.setAttribute("errorMessage", "Please fill in the values properly");
                dispatch.forward(request, response);
            }
            else
            {
                request.setAttribute("patientModified", "Patient modified succesfully");
                dispatch.forward(request, response);
                // modify the patient data
                model.changePatientData(field, patientID, value);
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
