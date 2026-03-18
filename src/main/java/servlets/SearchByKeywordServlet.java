package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelFactory;
import model.Model;

@WebServlet("/searchbykeyword")
public class SearchByKeywordServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String keyword = request.getParameter("keyword");        
        try 
        {
            Model model = ModelFactory.getModel();

            if (keyword == null || keyword.trim().isEmpty()) 
            {
                // if either input is empty send error message
                request.setAttribute("errorMessage", "Please enter a proper keyword.");
            } 
            else 
            {
                List<String> searchResult = model.searchByKeyword(keyword); //TODO change this part aproapriately
                request.setAttribute("resultId", searchResult);
            }

            ServletContext context = getServletContext();
            RequestDispatcher dispatch = context.getRequestDispatcher("/patientNameResult.jsp");
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
