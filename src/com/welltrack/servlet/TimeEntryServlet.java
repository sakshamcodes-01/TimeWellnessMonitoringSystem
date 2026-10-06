package com.welltrack.servlet;
import com.welltrack.dao.TimeEntryDAO;
import com.welltrack.model.TimeEntry;
import com.welltrack.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/time-entry")
public class TimeEntryServlet extends HttpServlet {
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Get login session
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
                            Please login before adding
                            your time entry.
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
        User user = (User) session.getAttribute("user");
        // Automatically get actual user ID
        int userId = user.getId();
        try {
            double workHours = Double.parseDouble(
                    request.getParameter("workHours")
            );
            double breakHours = Double.parseDouble(
                    request.getParameter("breakHours")
            );
            String description =
                    request.getParameter("description");
            String entryDate =
                    request.getParameter("entryDate");
            // Create TimeEntry object
            TimeEntry entry = new TimeEntry(
                    userId,
                    workHours,
                    breakHours,
                    description,
                    entryDate
            );
            // Save into database
            TimeEntryDAO dao = new TimeEntryDAO();
            boolean saved = dao.addTimeEntry(entry);
            if (saved) {
                response.getWriter().println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>
                            WellTrack - Time Saved
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
                                background: rgba(
                                    255,255,255,0.96
                                );
                                padding: 45px;
                                border-radius: 25px;
                                text-align: center;
                                box-shadow:
                                    0 15px 40px
                                    rgba(0,0,0,0.2);
                                max-width: 550px;
                                width: 100%;
                            }
                            .icon {
                                font-size: 65px;
                                margin-bottom: 15px;
                            }
                            h1 {
                                color: #667eea;
                                margin-bottom: 12px;
                            }
                            p {
                                color: #555;
                                line-height: 1.6;
                                margin-bottom: 8px;
                            }
                            .user {
                                color: #764ba2;
                                font-weight: bold;
                                margin-top: 12px;
                            }
                            .buttons {
                                display: flex;
                                justify-content: center;
                                gap: 12px;
                                flex-wrap: wrap;
                                margin-top: 25px;
                            }
                            a {
                                display: inline-block;
                                padding: 13px 22px;
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
                        <div class="card">
                            <div class="icon">
                                ✅
                            </div>
                            <h1>
                                Time Entry Saved!
                            </h1>
                            <p>
                                Your daily time record has
                                been successfully saved.
                            </p>
                            <div class="user">
                                👤 User ID:
                """);
                response.getWriter().println(userId);
                response.getWriter().println("""
                            </div>
                            <div class="buttons">
                                <a href="time-entry.html">
                                    ⏱️ Add Another Entry
                                </a>
                                <a
                                    href="wellness.html"
                                    class="secondary">
                                    💚 Check Wellness
                                </a>
                            </div>
                        </div>
                    </body>
                    </html>
                """);
            } else {
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
                            }
                            h1 {
                                color: #e74c3c;
                            }
                            p {
                                color: #555;
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
                                ❌ Failed to Save Time Entry
                            </h1>
                            <p>
                                Please check your details
                                and try again.
                            </p>
                            <a href="time-entry.html">
                                Go Back
                            </a>
                        </div>
                    </body>
                    </html>
                """);
            }
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
                        }
                        h1 {
                            color: #e74c3c;
                        }
                        p {
                            color: #555;
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
                            Please enter valid time
                            information.
                        </p>
                        <a href="time-entry.html">
                            Go Back
                        </a>
                    </div>
                </body>
                </html>
            """);
        }
    }
}
