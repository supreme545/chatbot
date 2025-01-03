<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Chatbot System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center align-items-center min-vh-100">
            <div class="col-md-6 col-lg-4">
                <div class="card shadow-lg">
                    <div class="card-body p-5">
                        <h2 class="text-center mb-4">Login</h2>
                        
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${fn:escapeXml(errorMessage)}
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty param.registered}">
                            <div class="alert alert-success" role="alert">
                                Registration successful! Please login with your credentials.
                            </div>
                        </c:if>

                        <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post" class="needs-validation" novalidate>
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" 
                                       value="${fn:escapeXml(param.username)}" required>
                                <div class="invalid-feedback">
                                    Please enter your username.
                                </div>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                                <div class="invalid-feedback">
                                    Please enter your password.
                                </div>
                            </div>
                            
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe"
                                       <c:if test="${not empty param.rememberMe}">checked</c:if>>
                                <label class="form-check-label" for="rememberMe">Remember me</label>
                            </div>
                            
                            <button type="submit" class="btn btn-primary w-100 mb-3">Login</button>
                            
                            <div class="text-center">
                                <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">
                                    Don't have an account? Register
                                </a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <!-- Form validation script -->
    <script>
        document.getElementById('loginForm').addEventListener('submit', function(event) {
            if (!this.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            this.classList.add('was-validated');
        });
    </script>
</body>
</html>
