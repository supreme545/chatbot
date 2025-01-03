package com.chatbot.servlet;

import com.chatbot.dao.ChatMessageDAO;
import com.chatbot.dao.impl.ChatMessageDAOImpl;
import com.chatbot.model.ChatMessage;
import com.chatbot.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/chat/*")
public class ChatServlet extends HttpServlet {
    private ChatMessageDAO chatMessageDAO;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        chatMessageDAO = new ChatMessageDAOImpl();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String pathInfo = request.getPathInfo();
        
        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // Show chat interface
                request.getRequestDispatcher("/chat.jsp").forward(request, response);
            } else if (pathInfo.equals("/messages")) {
                // Get chat history
                List<ChatMessage> messages = chatMessageDAO.findByUserId(user.getId());
                response.setContentType("application/json");
                objectMapper.writeValue(response.getOutputStream(), messages);
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            String message = request.getParameter("message");
            if (message == null || message.trim().isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Message cannot be empty");
                return;
            }

            // Create new chat message
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setUserId(user.getId());
            chatMessage.setMessage(message);
            chatMessage.setTimestamp(LocalDateTime.now());
            chatMessage.setProcessed(false);

            // Save message to database
            chatMessageDAO.create(chatMessage);

            // Generate bot response (simple example)
            String botResponse = generateBotResponse(message);
            chatMessage.setResponse(botResponse);
            chatMessage.setProcessed(true);
            chatMessageDAO.update(chatMessage);

            // Send response
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("status", "success");
            responseData.put("message", chatMessage);

            response.setContentType("application/json");
            objectMapper.writeValue(response.getOutputStream(), responseData);

        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }

    private String generateBotResponse(String message) {
        // Simple bot response logic (replace with actual AI implementation)
        message = message.toLowerCase();
        if (message.contains("hello") || message.contains("hi")) {
            return "Hello! How can I help you today?";
        } else if (message.contains("help")) {
            return "I'm here to help! Please let me know what you need assistance with.";
        } else if (message.contains("bye")) {
            return "Goodbye! Have a great day!";
        } else {
            return "I understand you're saying: " + message + ". How can I assist you further?";
        }
    }
}
