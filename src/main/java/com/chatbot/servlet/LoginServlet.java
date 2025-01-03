package com.chatbot.servlet;

import com.chatbot.dao.UserDAO;
import com.chatbot.dao.impl.UserDAOImpl;
import com.chatbot.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/chat");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        try {
            // Validate input
            if (username == null || password == null || 
                username.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("error", "Username and password are required");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            // Authenticate user
            User user = userDAO.findByUsername(username);
            if (user != null && password.equals(user.getPassword())) { // In production, verify hashed password
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                if ("on".equals(rememberMe)) {
                    session.setMaxInactiveInterval(7 * 24 * 60 * 60); // 7 days
                }

                response.sendRedirect(request.getContextPath() + "/chat");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
