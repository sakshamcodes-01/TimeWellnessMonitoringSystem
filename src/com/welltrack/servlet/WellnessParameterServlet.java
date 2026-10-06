package com.welltrack.servlet;

import com.welltrack.dao.WellnessParameterDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/wellness-parameters")
public class WellnessParameterServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Check existing session
        HttpSession session =
                request.getSession(false);

        // Check login
        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.html");
            return;
        }

        // Get role
        com.welltrack.model.User user =
                (com.welltrack.model.User)
                session.getAttribute("user");

        // Only ADMIN allowed
        if (!"ADMIN".equalsIgnoreCase(user.getRole())) {

            response.getWriter().println(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Access Denied</title>" +
                "<style>" +
                "body {" +
                "margin:0;" +
                "min-height:100vh;" +
                "display:flex;" +
                "justify-content:center;" +
                "align-items:center;" +
                "font-family:Arial,sans-serif;" +
                "background:linear-gradient(135deg,#667eea,#764ba2,#f093fb);" +
                "}" +
                ".card {" +
                "background:white;" +
                "padding:45px;" +
                "border-radius:25px;" +
                "text-align:center;" +
                "box-shadow:0 15px 40px rgba(0,0,0,0.2);" +
                "}" +
                "h1 {color:#e74c3c;}" +
                "a {" +
                "display:inline-block;" +
                "margin-top:20px;" +
                "padding:12px 25px;" +
                "background:linear-gradient(135deg,#667eea,#764ba2);" +
                "color:white;" +
                "text-decoration:none;" +
                "border-radius:12px;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='card'>" +
                "<h1>🔒 Access Denied</h1>" +
                "<p>Only administrators can update wellness parameters.</p>" +
                "<a href='login.html'>Go to Login</a>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

            return;
        }

        try {

            double maxWorkHours =
                    Double.parseDouble(
                        request.getParameter("maxWorkHours")
                    );

            double minBreakHours =
                    Double.parseDouble(
                        request.getParameter("minBreakHours")
                    );

            int minActivityLevel =
                    Integer.parseInt(
                        request.getParameter("minActivityLevel")
                    );

            // Save parameters
            WellnessParameterDAO dao =
                    new WellnessParameterDAO();

            boolean saved =
                    dao.saveParameters(
                        maxWorkHours,
                        minBreakHours,
                        minActivityLevel
                    );

            if (saved) {

                response.getWriter().println(
                    "<!DOCTYPE html>" +
                    "<html>" +

                    "<head>" +

                    "<meta charset='UTF-8'>" +

                    "<title>Parameters Saved</title>" +

                    "<style>" +

                    "body {" +
                    "margin:0;" +
                    "min-height:100vh;" +
                    "display:flex;" +
                    "justify-content:center;" +
                    "align-items:center;" +
                    "font-family:Arial,sans-serif;" +
                    "background:linear-gradient(135deg,#667eea,#764ba2,#f093fb);" +
                    "}" +

                    ".card {" +
                    "background:white;" +
                    "padding:45px;" +
                    "border-radius:25px;" +
                    "text-align:center;" +
                    "box-shadow:0 15px 40px rgba(0,0,0,0.2);" +
                    "max-width:500px;" +
                    "}" +

                    ".icon {" +
                    "font-size:60px;" +
                    "margin-bottom:15px;" +
                    "}" +

                    "h1 {" +
                    "color:#27ae60;" +
                    "margin-bottom:15px;" +
                    "}" +

                    "p {" +
                    "color:#555;" +
                    "line-height:1.6;" +
                    "}" +

                    ".values {" +
                    "background:#f3f0ff;" +
                    "padding:20px;" +
                    "border-radius:15px;" +
                    "margin-top:20px;" +
                    "color:#555;" +
                    "line-height:2;" +
                    "}" +

                    "a {" +
                    "display:inline-block;" +
                    "margin-top:25px;" +
                    "padding:13px 25px;" +
                    "background:linear-gradient(135deg,#667eea,#764ba2);" +
                    "color:white;" +
                    "text-decoration:none;" +
                    "border-radius:12px;" +
                    "font-weight:bold;" +
                    "}" +

                    "</style>" +

                    "</head>" +

                    "<body>" +

                    "<div class='card'>" +

                    "<div class='icon'>✅</div>" +

                    "<h1>Parameters Saved!</h1>" +

                    "<p>" +
                    "Wellness parameters have been successfully saved." +
                    "</p>" +

                    "<div class='values'>" +

                    "⏱️ Maximum Work Hours: " +
                    maxWorkHours +

                    "<br>" +

                    "☕ Minimum Break Hours: " +
                    minBreakHours +

                    "<br>" +

                    "🏃 Minimum Activity: " +
                    minActivityLevel +
                    " minutes" +

                    "</div>" +

                    "<a href='admin'>" +
                    "← Back to Admin Dashboard" +
                    "</a>" +

                    "</div>" +

                    "</body>" +

                    "</html>"
                );

            } else {

                response.getWriter().println(
                    "<h1>❌ Failed to Save Parameters</h1>" +
                    "<p>Please try again.</p>" +
                    "<a href='admin'>Back to Admin Dashboard</a>"
                );
            }

        } catch (NumberFormatException e) {

            response.getWriter().println(
                "<!DOCTYPE html>" +
                "<html>" +
                "<body style='font-family:Arial;text-align:center;padding:50px;'>" +

                "<h1 style='color:#e74c3c;'>" +
                "❌ Invalid Input" +
                "</h1>" +

                "<p>Please enter valid numeric values.</p>" +

                "<a href='admin'>Back to Admin Dashboard</a>" +

                "</body>" +
                "</html>"
            );
        }
    }
}