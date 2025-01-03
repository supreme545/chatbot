<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Chatbot System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center align-items-center min-vh-100">
            <div class="col-md-8 col-lg-6">
                <div class="card shadow-lg">
                    <div class="card-body p-5">
                        <h2 class="text-center mb-4">Register</h2>
                        
                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger" role="alert">
                                ${fn:escapeXml(errorMessage)}
                            </div>
                        </c:if>
                        
                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success" role="alert">
                                ${fn:escapeXml(successMessage)}
                            </div>
                        </c:if>

                        <form id="registerForm" action="${pageContext.request.contextPath}/register" method="post" class="needs-validation" novalidate>
                            <div class="mb-3">
                                <label for="username" class="form-label">Username</label>
                                <input type="text" class="form-control" id="username" name="username" 
                                       value="${fn:escapeXml(param.username)}" required 
                                       pattern="[A-Za-z0-9_]{3,20}">
                                <div class="invalid-feedback">
                                    Username must be 3-20 characters long and contain only letters, numbers, and underscores.
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" 
                                       value="${fn:escapeXml(param.email)}" required>
                                <div class="invalid-feedback">
                                    Please enter a valid email address.
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">Password</label>
                                <input type="password" class="form-control" id="password" name="password" 
                                       required minlength="6">
                                <div class="invalid-feedback">
                                    Password must be at least 6 characters long.
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" 
                                       name="confirmPassword" required>
                                <div class="invalid-feedback">
                                    Passwords do not match.
                                </div>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 mb-3">Register</button>
                            
                            <div class="text-center">
                                <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">
                                    Already have an account? Login
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
        document.getElementById('registerForm').addEventListener('submit', function(event) {
            if (!this.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            
            const password = document.getElementById('password');
            const confirmPassword = document.getElementById('confirmPassword');
            
            if (password.value !== confirmPassword.value) {
                confirmPassword.setCustomValidity('Passwords do not match');
                event.preventDefault();
            } else {
                confirmPassword.setCustomValidity('');
            }
            
            this.classList.add('was-validated');
        });

        document.getElementById('confirmPassword').addEventListener('input', function() {
            const password = document.getElementById('password');
            if (this.value !== password.value) {
                this.setCustomValidity('Passwords do not match');
            } else {
                this.setCustomValidity('');
            }
        });
    </script>
</body>
</html>
