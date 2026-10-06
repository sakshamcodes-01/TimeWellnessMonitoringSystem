package com.welltrack.servlet;

import com.welltrack.dao.WellnessDAO;
import com.welltrack.dao.RecommendationDAO;
import com.welltrack.model.User;
import com.welltrack.model.Wellness;
import com.welltrack.service.RecommendationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/wellness")
public class WellnessServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Get existing login session
        HttpSession session = request.getSession(false);

        // Check whether user is logged in
        if (session == null ||
            session.getAttribute("user") == null) {

            response.getWriter().println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <title>WellTrack - Login Required</title>

                    <style>

                        * {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                            font-family: Arial, sans-serif;
                        }

                        body {
                            min-height: 100vh;
                            display: flex;
                            justify-content: center;
                            align-items: center;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2,
                                #f093fb
                            );

                            padding: 25px;
                        }

                        .card {
                            background: rgba(255,255,255,0.96);
                            padding: 45px;
                            border-radius: 25px;
                            text-align: center;

                            box-shadow:
                                0 15px 40px
                                rgba(0,0,0,0.2);

                            max-width: 450px;
                        }

                        .icon {
                            font-size: 60px;
                            margin-bottom: 15px;
                        }

                        h1 {
                            color: #667eea;
                            margin-bottom: 12px;
                        }

                        p {
                            color: #555;
                            line-height: 1.6;
                        }

                        a {
                            display: inline-block;
                            margin-top: 22px;
                            padding: 13px 25px;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2
                            );

                            color: white;
                            text-decoration: none;
                            border-radius: 12px;
                            font-weight: bold;
                        }

                    </style>
                </head>

                <body>

                    <div class="card">

                        <div class="icon">
                            🔐
                        </div>

                        <h1>
                            Login Required
                        </h1>

                        <p>
                            Please login before checking
                            your wellness report.
                        </p>

                        <a href="login.html">
                            Go to Login
                        </a>

                    </div>

                </body>
                </html>
            """);

            return;
        }

        // Get logged-in user
        User user =
                (User) session.getAttribute("user");

        // Automatically get actual user ID
        int userId = user.getId();

        try {

            // Get form values
            int activityLevel =
                    Integer.parseInt(
                        request.getParameter(
                            "activityLevel"
                        )
                    );

            double workHours =
                    Double.parseDouble(
                        request.getParameter(
                            "workHours"
                        )
                    );

            double breakHours =
                    Double.parseDouble(
                        request.getParameter(
                            "breakHours"
                        )
                    );

            // Create Wellness object
            Wellness wellness =
                    new Wellness(
                        activityLevel,
                        workHours,
                        breakHours
                    );

            // Calculate wellness score
            double score =
                    wellness.calculateScore();

            // Save wellness data
            WellnessDAO wellnessDAO =
                    new WellnessDAO();

            boolean saved =
                    wellnessDAO.saveWellness(
                        userId,
                        wellness
                    );

            // Generate recommendations
            RecommendationService service =
                    new RecommendationService();

            List<String> recommendations =
                    service.generateRecommendations(
                        wellness
                    );

            // Save recommendations into MySQL
            RecommendationDAO recommendationDAO =
                    new RecommendationDAO();

            for (String recommendation :
                    recommendations) {

                recommendationDAO.saveRecommendation(
                    userId,
                    recommendation,
                    "Pending"
                );
            }

            // Start HTML response
            response.getWriter().println("""
                <!DOCTYPE html>
                <html>

                <head>

                    <title>
                        WellTrack - Wellness Report
                    </title>

                    <style>

                        * {
                            box-sizing: border-box;
                            margin: 0;
                            padding: 0;
                            font-family: Arial, sans-serif;
                        }

                        body {
                            min-height: 100vh;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2,
                                #f093fb
                            );

                            padding: 30px;
                        }

                        .navbar {
                            background: rgba(
                                255,255,255,0.18
                            );

                            backdrop-filter: blur(15px);

                            padding: 18px 30px;

                            border-radius: 18px;

                            color: white;

                            display: flex;

                            justify-content:
                                space-between;

                            align-items: center;

                            margin-bottom: 30px;
                        }

                        .logo {
                            font-size: 25px;
                            font-weight: bold;
                        }

                        .user {
                            font-size: 15px;
                        }

                        .container {
                            max-width: 850px;
                            margin: auto;
                        }

                        .title {
                            color: white;
                            margin-bottom: 20px;
                        }

                        .title h1 {
                            font-size: 32px;
                            margin-bottom: 8px;
                        }

                        .title p {
                            opacity: 0.9;
                        }

                        .score-card {
                            background:
                                rgba(
                                    255,
                                    255,
                                    255,
                                    0.96
                                );

                            padding: 35px;

                            border-radius: 25px;

                            text-align: center;

                            box-shadow:
                                0 15px 40px
                                rgba(0,0,0,0.18);

                            margin-bottom: 25px;
                        }

                        .score-label {
                            color: #555;
                            font-size: 18px;
                        }

                        .score {
                            font-size: 65px;

                            font-weight: bold;

                            color: #667eea;

                            margin: 15px 0;
                        }

                        .stats {
                            display: grid;

                            grid-template-columns:
                                repeat(
                                    auto-fit,
                                    minmax(180px, 1fr)
                                );

                            gap: 15px;

                            margin-bottom: 25px;
                        }

                        .stat {
                            background:
                                rgba(
                                    255,
                                    255,
                                    255,
                                    0.96
                                );

                            padding: 22px;

                            border-radius: 18px;

                            text-align: center;

                            box-shadow:
                                0 8px 25px
                                rgba(0,0,0,0.12);
                        }

                        .stat-icon {
                            font-size: 30px;
                            margin-bottom: 8px;
                        }

                        .stat h3 {
                            color: #555;
                            font-size: 15px;
                        }

                        .stat-value {
                            color: #667eea;

                            font-size: 25px;

                            font-weight: bold;

                            margin-top: 6px;
                        }

                        .recommendations {
                            background:
                                rgba(
                                    255,
                                    255,
                                    255,
                                    0.96
                                );

                            padding: 30px;

                            border-radius: 22px;

                            box-shadow:
                                0 10px 30px
                                rgba(0,0,0,0.15);
                        }

                        .recommendations h2 {
                            color: #333;
                            margin-bottom: 18px;
                        }

                        .recommendation {
                            background: #f3f0ff;

                            padding: 16px;

                            border-radius: 13px;

                            margin-bottom: 10px;

                            color: #555;
                        }

                        .saved {
                            margin-top: 18px;

                            padding: 12px;

                            border-radius: 12px;

                            background: #d4edda;

                            color: #155724;

                            text-align: center;

                            font-weight: bold;
                        }

                        .buttons {
                            display: flex;

                            gap: 12px;

                            flex-wrap: wrap;

                            margin-top: 22px;
                        }

                        .button {
                            display: inline-block;

                            padding: 12px 22px;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2
                            );

                            color: white;

                            text-decoration: none;

                            border-radius: 12px;

                            font-weight: bold;
                        }

                        .secondary {
                            background: #555;
                        }

                    </style>

                </head>

                <body>

                    <div class="navbar">

                        <div class="logo">
                            💚 WellTrack
                        </div>

                        <div class="user">
                            👤
                """);

            response.getWriter().println(
                    user.getName()
            );

            response.getWriter().println("""
                        </div>

                    </div>


                    <div class="container">

                        <div class="title">

                            <h1>
                                📊 Your Wellness Report
                            </h1>

                            <p>
                                Personalized wellness
                                summary for your daily routine.
                            </p>

                        </div>


                        <div class="score-card">

                            <div class="score-label">
                                Your Wellness Score
                            </div>

                            <div class="score">
                """);

            response.getWriter().println(
                    String.format("%.0f%%", score)
            );

            response.getWriter().println("""
                            </div>

                            <div class="score-label">
                                Keep improving your
                                daily routine 💚
                            </div>

                        </div>


                        <div class="stats">


                            <div class="stat">

                                <div class="stat-icon">
                                    🏃
                                </div>

                                <h3>
                                    Activity
                                </h3>

                                <div class="stat-value">
                """);

            response.getWriter().println(
                    activityLevel + " min"
            );

            response.getWriter().println("""
                                </div>

                            </div>


                            <div class="stat">

                                <div class="stat-icon">
                                    ⏱️
                                </div>

                                <h3>
                                    Work Hours
                                </h3>

                                <div class="stat-value">
                """);

            response.getWriter().println(
                    workHours + " hrs"
            );

            response.getWriter().println("""
                                </div>

                            </div>


                            <div class="stat">

                                <div class="stat-icon">
                                    ☕
                                </div>

                                <h3>
                                    Break Hours
                                </h3>

                                <div class="stat-value">
                """);

            response.getWriter().println(
                    breakHours + " hrs"
            );

            response.getWriter().println("""
                                </div>

                            </div>


                            <div class="stat">

                                <div class="stat-icon">
                                    👤
                                </div>

                                <h3>
                                    User ID
                                </h3>

                                <div class="stat-value">
                """);

            response.getWriter().println(
                    userId
            );

            response.getWriter().println("""
                                </div>

                            </div>

                        </div>


                        <div class="recommendations">

                            <h2>
                                💡 Recommendations
                            </h2>
                """);

            // Display recommendations
            for (String recommendation :
                    recommendations) {

                response.getWriter().println(
                    "<div class='recommendation'>💡 "
                    + recommendation
                    + "</div>"
                );
            }

            // Database status
            if (saved) {

                response.getWriter().println("""
                            <div class="saved">
                                ✓ Wellness data and
                                recommendations saved
                                successfully!
                            </div>
                    """);

            } else {

                response.getWriter().println("""
                            <div class="saved">
                                ⚠️ Wellness score calculated,
                                but wellness data could
                                not be saved.
                            </div>
                    """);
            }

            response.getWriter().println("""
                            <div class="buttons">

                                <a
                                    href="wellness.html"
                                    class="button">
                                    💚 Check Again
                                </a>

                                <a
                                    href="time-entry.html"
                                    class="button">
                                    ⏱️ Add Time Entry
                                </a>

                                <a
                                    href="login.html"
                                    class="button secondary">
                                    Logout
                                </a>

                            </div>

                        </div>

                    </div>

                </body>

                </html>
            """);

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println("""
                <!DOCTYPE html>
                <html>

                <head>

                    <title>
                        WellTrack - Error
                    </title>

                    <style>

                        body {
                            margin: 0;

                            min-height: 100vh;

                            display: flex;

                            justify-content: center;

                            align-items: center;

                            font-family: Arial, sans-serif;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2,
                                #f093fb
                            );
                        }

                        .card {
                            background: white;

                            padding: 45px;

                            border-radius: 25px;

                            text-align: center;

                            box-shadow:
                                0 15px 40px
                                rgba(0,0,0,0.2);

                            max-width: 500px;
                        }

                        h1 {
                            color: #e74c3c;
                        }

                        p {
                            color: #555;

                            line-height: 1.6;
                        }

                        a {
                            display: inline-block;

                            margin-top: 20px;

                            padding: 12px 25px;

                            background: linear-gradient(
                                135deg,
                                #667eea,
                                #764ba2
                            );

                            color: white;

                            text-decoration: none;

                            border-radius: 12px;
                        }

                    </style>

                </head>

                <body>

                    <div class="card">

                        <h1>
                            ⚠️ Invalid Input
                        </h1>

                        <p>
                            Please enter valid wellness
                            information and try again.
                        </p>

                        <a href="wellness.html">
                            Go Back
                        </a>

                    </div>

                </body>

                </html>
            """);
        }
    }
}