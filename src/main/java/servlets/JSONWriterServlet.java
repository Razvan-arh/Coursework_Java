package servlets;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Model;
import model.ModelFactory;


@WebServlet("/writeJson")
public class JSONWriterServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Model model = ModelFactory.getModel();

        // creates JSON and returns if it was succesful
        boolean success = model.writeJSON();

        // creates the message to be forwarded
        String msg = success ? "JSON created successfully!" : "Error creating JSON!";

        // redirects to index plus message
        response.sendRedirect("index.html?message=" + java.net.URLEncoder.encode(msg, "UTF-8"));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doGet(request, response);
    }
}

