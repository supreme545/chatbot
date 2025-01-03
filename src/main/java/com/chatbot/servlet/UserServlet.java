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

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // List all users (admin only)
                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                
                if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
                    request.setAttribute("users", userDAO.findAll());
                    request.getRequestDispatcher("/WEB-INF/jsp/users.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            } else if (pathInfo.equals("/profile")) {
                // Show user profile
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                
                if (user != null) {
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo.equals("/register")) {
                // Handle registration
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String email = request.getParameter("email");
                
                // Validate input
                if (username == null || password == null || email == null ||
                    username.trim().isEmpty() || password.trim().isEmpty() || email.trim().isEmpty()) {
                    request.setAttribute("error", "All fields are required");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                
                // Check if username exists
                if (userDAO.findByUsername(username) != null) {
                    request.setAttribute("error", "Username already exists");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                    return;
                }
                
                // Create new user
                User user = new User();
                user.setUsername(username);
                user.setPassword(password); // In production, hash the password
                user.setEmail(email);
                user.setRole("USER");
                
                userDAO.create(user);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                
            } else if (pathInfo.equals("/update")) {
                // Handle profile update
                HttpSession session = request.getSession();
                User currentUser = (User) session.getAttribute("user");
                
                if (currentUser == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                
                if (email != null && !email.trim().isEmpty()) {
                    currentUser.setEmail(email);
                }
                if (password != null && !password.trim().isEmpty()) {
                    currentUser.setPassword(password); // In production, hash the password
                }
                
                userDAO.update(currentUser);
                session.setAttribute("user", currentUser);
                
                request.setAttribute("message", "Profile updated successfully");
                request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
