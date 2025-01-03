# Technical Documentation - AI Chatbot System

## Architecture Overview

### System Architecture
```
[Client Browser] <--> [Web Container (Tomcat)]
         ^               ^
         |               |
         v               v
[Frontend (JSP/JS)] <--> [Java Servlets]
                           ^
                           |
                           v
                    [Service Layer]
                           ^
                           |
                           v
                      [DAO Layer]
                           ^
                           |
                           v
                    [MySQL Database]
```

## Database Schema

### Users Table
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Chat Messages Table
```sql
CREATE TABLE chat_messages (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    response TEXT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_processed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## Component Details

### 1. Authentication System
- Implementation: `LoginServlet`, `UserService`
- Features:
  - Session-based authentication
  - Password hashing using BCrypt
  - Remember-me functionality using secure tokens
- Security measures:
  - CSRF protection
  - SQL injection prevention
  - XSS protection

### 2. Chat System
- Implementation: `ChatServlet`, `ChatService`
- Features:
  - Asynchronous message handling
  - Real-time updates using AJAX
  - Message persistence
- Performance optimizations:
  - Connection pooling
  - Prepared statements
  - Message batching

### 3. User Management
- Implementation: `UserServlet`, `UserService`
- Features:
  - Profile management
  - Password reset
  - Email verification
- Validation:
  - Input sanitization
  - Data validation
  - Error handling

## API Endpoints

### Authentication API
```
POST /login
Request:
{
    "username": string,
    "password": string,
    "rememberMe": boolean
}
Response:
{
    "success": boolean,
    "user": {
        "id": number,
        "username": string,
        "email": string,
        "role": string
    }
}
```

### Chat API
```
POST /chat/message
Request:
{
    "message": string,
    "userId": number
}
Response:
{
    "id": number,
    "message": string,
    "response": string,
    "timestamp": string
}
```

### User API
```
PUT /user/profile
Request:
{
    "id": number,
    "email": string,
    "currentPassword": string,
    "newPassword": string (optional)
}
Response:
{
    "success": boolean,
    "user": {
        "id": number,
        "username": string,
        "email": string,
        "role": string
    }
}
```

## Security Implementation

### Password Hashing
```java
public String hashPassword(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt(12));
}
```

### SQL Injection Prevention
```java
// Using PreparedStatement
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement stmt = conn.prepareStatement(sql);
stmt.setString(1, username);
```

### XSS Prevention
```jsp
<!-- Using JSTL's fn:escapeXml -->
${fn:escapeXml(userInput)}
```

## Testing Strategy

### Unit Tests
- Service layer tests using Mockito
- DAO layer tests with H2 in-memory database
- Servlet tests using MockHttpServletRequest/Response

### Integration Tests
- End-to-end API tests
- Database integration tests
- Session management tests

## Performance Considerations

### Database Optimization
- Proper indexing on frequently queried columns
- Connection pooling configuration
- Query optimization

### Frontend Optimization
- Minified CSS/JS
- Lazy loading of chat history
- Efficient DOM manipulation

## Deployment Guide

### Prerequisites
- JDK 11
- MySQL 8.0
- Maven 3.6+
- Tomcat 9

### Configuration Steps
1. Database setup
2. Application properties configuration
3. Building WAR file
4. Deploying to Tomcat

### Monitoring
- Log file locations
- Error tracking
- Performance metrics

## Troubleshooting Guide

### Common Issues
1. Database connection errors
   - Check MySQL service status
   - Verify connection properties

2. Authentication failures
   - Clear browser cache
   - Reset session data

3. Chat system issues
   - Check WebSocket connection
   - Verify message queue status
