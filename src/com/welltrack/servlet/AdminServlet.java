package com.welltrack.servlet;

import com.welltrack.dao.AdminStatsDAO;
import com.welltrack.dao.UserDAO;
import com.welltrack.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("user") == null) {

            response.sendRedirect("login.html");
            return;
        }

        User loggedUser =
                (User) session.getAttribute("user");

        if (!"ADMIN".equalsIgnoreCase(
                loggedUser.getRole())) {

            response.setContentType("text/html");

            response.getWriter().println(
                "<h1>Access Denied</h1>" +
                "<p>You are not authorized to access this page.</p>" +
                "<a href='login.html'>Login</a>"
            );

            return;
        }

        AdminStatsDAO statsDAO =
                new AdminStatsDAO();

        UserDAO userDAO =
                new UserDAO();

        int totalUsers =
                statsDAO.getTotalUsers();

        int totalTimeEntries =
                statsDAO.getTotalTimeEntries();

        int totalWellnessRecords =
                statsDAO.getTotalWellnessRecords();

        double averageWellness =
                statsDAO.getAverageWellnessScore();

        List<User> users =
                userDAO.getAllUsers();

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        response.getWriter().println(

            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "<title>WellTrack Admin Dashboard</title>" +

            "<style>" +

            "*{" +
            "box-sizing:border-box;" +
            "margin:0;" +
            "padding:0;" +
            "font-family:Arial,sans-serif" +
            "}" +

            "body{" +
            "min-height:100vh;" +
            "padding:30px;" +
            "background:linear-gradient(135deg,#667eea,#764ba2,#f093fb);" +
            "}" +

            ".navbar{" +
            "background:rgba(255,255,255,.18);" +
            "backdrop-filter:blur(15px);" +
            "padding:18px 30px;" +
            "border-radius:18px;" +
            "color:white;" +
            "display:flex;" +
            "justify-content:space-between;" +
            "align-items:center;" +
            "margin-bottom:30px;" +
            "}" +

            ".logo{" +
            "font-size:25px;" +
            "font-weight:bold" +
            "}" +

            ".container{" +
            "max-width:1200px;" +
            "margin:auto" +
            "}" +

            ".title{" +
            "color:white;" +
            "margin-bottom:25px" +
            "}" +

            ".title h1{" +
            "font-size:32px;" +
            "margin-bottom:8px" +
            "}" +

            ".title p{" +
            "opacity:.9" +
            "}" +

            ".stats{" +
            "display:grid;" +
            "grid-template-columns:repeat(auto-fit,minmax(220px,1fr));" +
            "gap:20px;" +
            "margin-bottom:30px" +
            "}" +

            ".stat-card{" +
            "background:rgba(255,255,255,.95);" +
            "padding:25px;" +
            "border-radius:20px;" +
            "box-shadow:0 10px 30px rgba(0,0,0,.15)" +
            "}" +

            ".stat-icon{" +
            "font-size:35px;" +
            "margin-bottom:12px" +
            "}" +

            ".stat-card h3{" +
            "color:#555;" +
            "margin-bottom:10px" +
            "}" +

            ".number{" +
            "font-size:30px;" +
            "font-weight:bold;" +
            "color:#667eea" +
            "}" +

            ".section{" +
            "background:rgba(255,255,255,.95);" +
            "padding:30px;" +
            "border-radius:22px;" +
            "box-shadow:0 10px 30px rgba(0,0,0,.15);" +
            "margin-bottom:30px" +
            "}" +

            ".section h2{" +
            "color:#333;" +
            "margin-bottom:20px" +
            "}" +

            "table{" +
            "width:100%;" +
            "border-collapse:collapse" +
            "}" +

            "th,td{" +
            "padding:14px;" +
            "text-align:left;" +
            "border-bottom:1px solid #eee" +
            "}" +

            "th{" +
            "background:#667eea;" +
            "color:white" +
            "}" +

            "tr:hover{" +
            "background:#f7f5ff" +
            "}" +

            ".parameter{" +
            "display:grid;" +
            "grid-template-columns:repeat(auto-fit,minmax(200px,1fr));" +
            "gap:15px" +
            "}" +

            "input{" +
            "width:100%;" +
            "padding:12px;" +
            "border:1px solid #ddd;" +
            "border-radius:10px;" +
            "margin-top:6px" +
            "}" +

            "label{" +
            "font-weight:bold;" +
            "color:#555" +
            "}" +

            ".button{" +
            "display:inline-block;" +
            "margin-top:20px;" +
            "padding:12px 25px;" +
            "border:none;" +
            "border-radius:12px;" +
            "background:linear-gradient(135deg,#667eea,#764ba2);" +
            "color:white;" +
            "font-weight:bold;" +
            "cursor:pointer" +
            "}" +

            ".logout{" +
            "background:#555;" +
            "text-decoration:none;" +
            "margin-top:0" +
            "}" +

            ".chart{" +
            "margin-top:20px;" +
            "display:flex;" +
            "align-items:flex-end;" +
            "gap:25px;" +
            "height:180px;" +
            "padding:20px;" +
            "background:#f7f5ff;" +
            "border-radius:15px" +
            "}" +

            ".bar-container{" +
            "height:140px;" +
            "display:flex;" +
            "flex-direction:column;" +
            "justify-content:flex-end;" +
            "align-items:center;" +
            "flex:1" +
            "}" +

            ".bar{" +
            "width:55px;" +
            "background:linear-gradient(180deg,#667eea,#764ba2);" +
            "border-radius:8px 8px 0 0;" +
            "min-height:10px" +
            "}" +

            ".bar-label{" +
            "margin-top:8px;" +
            "font-size:12px;" +
            "color:#555;" +
            "text-align:center" +
            "}" +

            "</style>" +
            "</head>" +

            "<body>" +

            "<div class='container'>" +

            "<div class='navbar'>" +
            "<div class='logo'>💚 WellTrack Admin</div>" +
            "<div>👤 " +
            loggedUser.getName() +
            "</div>" +
            "</div>" +

            "<div class='title'>" +
            "<h1>Admin Dashboard 📊</h1>" +
            "<p>Monitor users, time management and wellness performance.</p>" +
            "</div>" +

            "<div class='stats'>" +

            "<div class='stat-card'>" +
            "<div class='stat-icon'>👥</div>" +
            "<h3>Total Users</h3>" +
            "<div class='number'>" +
            totalUsers +
            "</div>" +
            "</div>" +

            "<div class='stat-card'>" +
            "<div class='stat-icon'>⏱️</div>" +
            "<h3>Time Entries</h3>" +
            "<div class='number'>" +
            totalTimeEntries +
            "</div>" +
            "</div>" +

            "<div class='stat-card'>" +
            "<div class='stat-icon'>💚</div>" +
            "<h3>Wellness Records</h3>" +
            "<div class='number'>" +
            totalWellnessRecords +
            "</div>" +
            "</div>" +

            "<div class='stat-card'>" +
            "<div class='stat-icon'>📊</div>" +
            "<h3>Average Wellness</h3>" +
            "<div class='number'>" +
            String.format("%.1f", averageWellness) +
            "%</div>" +
            "</div>" +

            "</div>" +

            "<div class='section'>" +

            "<h2>📈 Performance Overview</h2>" +

            "<div class='chart'>" +

            "<div class='bar-container'>" +
            "<div class='bar' style='height:" +
            Math.min(totalUsers * 20 + 10, 130) +
            "px'></div>" +
            "<div class='bar-label'>Users</div>" +
            "</div>" +

            "<div class='bar-container'>" +
            "<div class='bar' style='height:" +
            Math.min(totalTimeEntries * 15 + 10, 130) +
            "px'></div>" +
            "<div class='bar-label'>Time Entries</div>" +
            "</div>" +

            "<div class='bar-container'>" +
            "<div class='bar' style='height:" +
            Math.min(totalWellnessRecords * 15 + 10, 130) +
            "px'></div>" +
            "<div class='bar-label'>Wellness</div>" +
            "</div>" +

            "<div class='bar-container'>" +
            "<div class='bar' style='height:" +
            Math.min(averageWellness * 1.3 + 10, 130) +
            "px'></div>" +
            "<div class='bar-label'>Avg Score</div>" +
            "</div>" +

            "</div>" +

            "</div>" +

            "<div class='section'>" +

            "<h2>⚙️ Wellness Parameters</h2>" +

            "<form action='wellness-parameters' method='post'>" +

            "<div class='parameter'>" +

            "<div>" +
            "<label>Maximum Work Hours</label>" +
            "<input type='number' name='maxWorkHours' " +
            "value='8' step='0.5' required>" +
            "</div>" +

            "<div>" +
            "<label>Minimum Break Hours</label>" +
            "<input type='number' name='minBreakHours' " +
            "value='1' step='0.5' required>" +
            "</div>" +

            "<div>" +
            "<label>Minimum Activity Level</label>" +
            "<input type='number' name='minActivityLevel' " +
            "value='60' required>" +
            "</div>" +

            "</div>" +

            "<button class='button' type='submit'>" +
            "Save Parameters" +
            "</button>" +

            "</form>" +

            "</div>" +

            "<div class='section'>" +

            "<h2>👥 Registered Users</h2>" +

            "<table>" +

            "<tr>" +
            "<th>ID</th>" +
            "<th>Name</th>" +
            "<th>Email</th>" +
            "<th>Role</th>" +
            "</tr>"
        );

        for (User user : users) {

            response.getWriter().println(

                "<tr>" +

                "<td>" +
                user.getId() +
                "</td>" +

                "<td>" +
                user.getName() +
                "</td>" +

                "<td>" +
                user.getEmail() +
                "</td>" +

                "<td>" +
                user.getRole() +
                "</td>" +

                "</tr>"
            );
        }

        response.getWriter().println(

            "</table>" +

            "</div>" +

            "<div style='text-align:center;margin-top:20px'>" +

            "<a class='button logout' href='logout'>" +
            "Logout" +
            "</a>" +

            "</div>" +

            "</div>" +

            "</body>" +
            "</html>"
        );
    }
}