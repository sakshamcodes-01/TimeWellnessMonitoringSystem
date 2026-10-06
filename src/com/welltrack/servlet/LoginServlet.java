package com.welltrack.servlet;

import com.welltrack.dao.LoginDAO;
import com.welltrack.dao.TimeEntryDAO;
import com.welltrack.dao.WellnessDAO;
import com.welltrack.dao.RecommendationDAO;
import com.welltrack.model.User;
import com.welltrack.model.TimeEntry;
import com.welltrack.model.Wellness;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginDAO loginDAO = new LoginDAO();

        User user = loginDAO.login(email, password);

        response.setContentType("text/html;charset=UTF-8");

        if (user == null) {

            response.getWriter().println(
                "<!DOCTYPE html>" +
                "<html><head><meta charset='UTF-8'>" +
                "<title>Login Failed</title>" +
                "<style>" +
                "body{margin:0;min-height:100vh;display:flex;" +
                "justify-content:center;align-items:center;" +
                "font-family:Arial;background:linear-gradient(135deg,#667eea,#764ba2,#f093fb);}" +
                ".box{background:white;padding:45px;border-radius:25px;text-align:center;" +
                "box-shadow:0 15px 40px rgba(0,0,0,.2)}" +
                "h1{color:#e74c3c} a{display:inline-block;margin-top:20px;" +
                "padding:12px 25px;background:#667eea;color:white;" +
                "text-decoration:none;border-radius:12px}" +
                "</style></head>" +
                "<body><div class='box'>" +
                "<h1>❌ Login Failed!</h1>" +
                "<p>Invalid email or password.</p>" +
                "<a href='login.html'>Try Again</a>" +
                "</div></body></html>"
            );

            return;
        }

        request.getSession().setAttribute("user", user);

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect("admin");
            return;
        }

        TimeEntryDAO timeEntryDAO = new TimeEntryDAO();
        WellnessDAO wellnessDAO = new WellnessDAO();
        RecommendationDAO recommendationDAO =
                new RecommendationDAO();

        TimeEntry latestEntry =
                timeEntryDAO.getLatestEntry(user.getId());

        Wellness latestWellness =
                wellnessDAO.getLatestWellness(user.getId());

        List<String> recommendations =
                recommendationDAO.getRecommendations(user.getId());

        double workHours = 0;
        double breakHours = 0;
        int activityLevel = 0;
        double wellnessScore = 0;

        if (latestEntry != null) {
            workHours = latestEntry.getWorkHours();
            breakHours = latestEntry.getBreakHours();
        }

        if (latestWellness != null) {
            activityLevel =
                    latestWellness.getActivityLevel();

            wellnessScore =
                    wellnessDAO.getLatestWellnessScore(
                            user.getId()
                    );
        }

        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
            "<title>WellTrack Dashboard</title>" +

            "<style>" +

            "*{box-sizing:border-box;margin:0;padding:0;font-family:Arial,sans-serif}" +

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

            ".logo{font-size:25px;font-weight:bold}" +

            ".welcome{color:white;margin-bottom:25px}" +

            ".welcome h1{font-size:32px;margin-bottom:8px}" +

            ".welcome p{opacity:.9}" +

            ".cards{" +
            "display:grid;" +
            "grid-template-columns:repeat(auto-fit,minmax(200px,1fr));" +
            "gap:20px;margin-bottom:30px;" +
            "}" +

            ".card{" +
            "background:rgba(255,255,255,.95);" +
            "padding:25px;" +
            "border-radius:20px;" +
            "box-shadow:0 10px 30px rgba(0,0,0,.15);" +
            "}" +

            ".icon{font-size:32px;margin-bottom:12px}" +

            ".card h3{color:#555;margin-bottom:8px}" +

            ".value{font-size:28px;font-weight:bold;color:#667eea}" +

            ".main{" +
            "background:rgba(255,255,255,.95);" +
            "padding:30px;" +
            "border-radius:22px;" +
            "box-shadow:0 10px 30px rgba(0,0,0,.15);" +
            "}" +

            ".main h2{color:#333;margin-bottom:20px}" +

            ".recommendation{" +
            "background:#f3f0ff;" +
            "padding:18px;" +
            "border-radius:14px;" +
            "margin-bottom:12px;" +
            "color:#555;" +
            "border-left:5px solid #667eea;" +
            "}" +

            ".empty{" +
            "background:#e8f5e9;" +
            "padding:18px;" +
            "border-radius:14px;" +
            "color:#2e7d32;" +
            "}" +

            ".status{" +
            "display:inline-block;" +
            "margin-top:15px;" +
            "padding:10px 18px;" +
            "background:#d4edda;" +
            "color:#155724;" +
            "border-radius:20px;" +
            "font-weight:bold;" +
            "}" +

            ".buttons{" +
            "display:flex;" +
            "gap:15px;" +
            "flex-wrap:wrap;" +
            "margin-top:25px;" +
            "}" +

            ".button{" +
            "display:inline-block;" +
            "padding:12px 22px;" +
            "background:linear-gradient(135deg,#667eea,#764ba2);" +
            "color:white;text-decoration:none;" +
            "border-radius:12px;font-weight:bold;" +
            "}" +

            ".logout{background:#555}" +

            "</style>" +
            "</head>" +

            "<body>" +

            "<div class='navbar'>" +
            "<div class='logo'>💚 WellTrack</div>" +
            "<div>👤 " +
            user.getName() +
            "</div>" +
            "</div>" +

            "<div class='welcome'>" +
            "<h1>Welcome back! 👋</h1>" +
            "<p>Track your time. Improve your wellness.</p>" +
            "</div>" +

            "<div class='cards'>" +

            "<div class='card'>" +
            "<div class='icon'>⏱️</div>" +
            "<h3>Work Hours</h3>" +
            "<div class='value'>" +
            workHours +
            " hrs</div>" +
            "</div>" +

            "<div class='card'>" +
            "<div class='icon'>☕</div>" +
            "<h3>Break Time</h3>" +
            "<div class='value'>" +
            breakHours +
            " hrs</div>" +
            "</div>" +

            "<div class='card'>" +
            "<div class='icon'>🏃</div>" +
            "<h3>Activity</h3>" +
            "<div class='value'>" +
            activityLevel +
            " min</div>" +
            "</div>" +

            "<div class='card'>" +
            "<div class='icon'>💚</div>" +
            "<h3>Wellness Score</h3>" +
            "<div class='value'>" +
            wellnessScore +
            "%</div>" +
            "</div>" +

            "</div>" +

            "<div class='main'>" +

            "<h2>💡 Your Wellness Recommendations</h2>"
        );

        if (recommendations.isEmpty()) {

            response.getWriter().println(
                "<div class='empty'>" +
                "🌟 No recommendations available yet. " +
                "Complete a wellness check to get personalized recommendations." +
                "</div>"
            );

        } else {

            for (String recommendation : recommendations) {

                response.getWriter().println(
                    "<div class='recommendation'>" +
                    "💡 " +
                    recommendation +
                    "</div>"
                );
            }
        }

        response.getWriter().println(

            "<span class='status'>" +
            "✓ Wellness data updated from database" +
            "</span>" +

            "<div class='buttons'>" +

            "<a class='button' href='time-entry.html'>" +
            "⏱️ Add Time Entry" +
            "</a>" +

            "<a class='button' href='wellness.html'>" +
            "💚 Check Wellness" +
            "</a>" +

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