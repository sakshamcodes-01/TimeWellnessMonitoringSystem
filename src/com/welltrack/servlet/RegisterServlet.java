package com.welltrack.servlet;
import com.welltrack.dao.UserDAO;
import com.welltrack.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        User user = new User(
                name,
                email,
                password,
                role
        );
        UserDAO userDAO = new UserDAO();
        boolean registered = userDAO.registerUser(user);
        response.setContentType("text/html");
        if (registered) {
            response.getWriter().println(
                "<h1>Registration Successful!</h1>"
            );
            response.getWriter().println(
                "<p>Welcome, " + name + "</p>"
            );
            response.getWriter().println(
                "<a href='login.html'>Go to Login</a>"
            );
        } else {
            response.getWriter().println(
                "<h1>Registration Failed!</h1>"
            );
            response.getWriter().println(
                "<p>Email may already exist.</p>"
            );
            response.getWriter().println(
                "<a href='register.html'>Try Again</a>"
            );
        }
    }
}