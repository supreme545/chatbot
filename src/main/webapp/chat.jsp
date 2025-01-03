<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat - Chatbot System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
    <style>
        .chat-container {
            height: calc(100vh - 100px);
            display: flex;
            flex-direction: column;
        }
        .chat-messages {
            flex-grow: 1;
            overflow-y: auto;
            padding: 20px;
            background: #f8f9fa;
        }
        .message {
            margin-bottom: 15px;
            padding: 10px 15px;
            border-radius: 10px;
            max-width: 70%;
        }
        .user-message {
            background: #007bff;
            color: white;
            margin-left: auto;
        }
        .bot-message {
            background: #e9ecef;
            color: #212529;
        }
        .chat-input {
            padding: 20px;
            background: white;
            border-top: 1px solid #dee2e6;
        }
    </style>
</head>
<body class="bg-light">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Chatbot System</a>
            <div class="navbar-nav ms-auto">
                <c:if test="${not empty sessionScope.user}">
                    <span class="nav-item nav-link text-light">Welcome, ${sessionScope.user.username}!</span>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
                </c:if>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <div class="card chat-container">
            <div class="chat-messages" id="chatMessages">
                <c:if test="${not empty messages}">
                    <c:forEach var="message" items="${messages}">
                        <div class="message ${message.userId eq sessionScope.user.id ? 'user-message' : 'bot-message'}">
                            <div class="message-content">${message.message}</div>
                            <small class="text-muted">
                                <fmt:formatDate value="${message.timestamp}" pattern="HH:mm:ss"/>
                            </small>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
            
            <div class="chat-input">
                <form id="chatForm" class="d-flex gap-2">
                    <input type="text" id="messageInput" class="form-control" placeholder="Type your message..." required>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-paper-plane"></i> Send
                    </button>
                </form>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <!-- Custom JS -->
    <script>
        $(document).ready(function() {
            // Scroll to bottom of chat
            function scrollToBottom() {
                const chatMessages = document.getElementById('chatMessages');
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }
            scrollToBottom();

            // Handle form submission
            $('#chatForm').on('submit', function(e) {
                e.preventDefault();
                const message = $('#messageInput').val().trim();
                if (message) {
                    $.ajax({
                        url: '${pageContext.request.contextPath}/chat',
                        type: 'POST',
                        data: { message: message },
                        success: function(response) {
                            // Add user message
                            $('#chatMessages').append(
                                `<div class="message user-message">
                                    <div class="message-content">${message}</div>
                                    <small class="text-muted">${new Date().toLocaleTimeString()}</small>
                                </div>`
                            );
                            
                            // Add bot response
                            if (response && response.message) {
                                setTimeout(() => {
                                    $('#chatMessages').append(
                                        `<div class="message bot-message">
                                            <div class="message-content">${response.message}</div>
                                            <small class="text-muted">${new Date().toLocaleTimeString()}</small>
                                        </div>`
                                    );
                                    scrollToBottom();
                                }, 500);
                            }
                            
                            $('#messageInput').val('').focus();
                            scrollToBottom();
                        },
                        error: function() {
                            alert('Error sending message. Please try again.');
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>
