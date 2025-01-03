# AI Chatbot System - College Project

A Maven-based web application developed as part of the college curriculum. This project implements a user-friendly interface for interacting with an AI chatbot, featuring user authentication, real-time chat functionality, and profile management.

## Project Overview

**Course:** [Your Course Name]
**Submitted By:** [Your Name]
**Roll Number:** [Your Roll Number]
**Semester:** [Your Semester]

## Features

- User Authentication
  - Secure login and registration
  - Password hashing and validation
  - Session management
  - Remember me functionality

- Profile Management
  - View and update user profile
  - Email management
  - Password change functionality

- Chat Interface
  - Real-time message display
  - Message history
  - Responsive design
  - AI-powered responses

## Technology Stack

- **Backend**
  - Java 11
  - Servlet API 4.0.1
  - MySQL 8.0.27
  - JDBC for database connectivity
  - Jackson for JSON processing

- **Frontend**
  - JSP/JSTL
  - Bootstrap 5.1.3
  - JavaScript
  - HTML5/CSS3

- **Testing**
  - JUnit Jupiter 5.8.2
  - Mockito 4.2.0
  - Hamcrest

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/chatbot/
│   │       ├── dao/          # Data Access Objects
│   │       ├── model/        # Entity classes
│   │       ├── servlet/      # Servlet controllers
│   │       ├── service/      # Business logic
│   │       └── util/         # Utility classes
│   ├── resources/
│   │   ├── application.properties
│   │   └── schema.sql
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── jsp/         # JSP views
│       │   └── web.xml      # Web configuration
│       ├── css/            # Stylesheets
│       ├── js/             # JavaScript files
│       └── *.jsp           # Main JSP pages
└── test/
    └── java/
        └── com/chatbot/     # Test classes
```

## Setup Instructions

1. **Database Setup**
   ```sql
   mysql -u root -p < src/main/resources/schema.sql
   ```

2. **Configuration**
   - Update database credentials in `application.properties`
   ```properties
   db.url=jdbc:mysql://localhost:3306/chatbot_db
   db.username=your_username
   db.password=your_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn tomcat7:run
   ```

4. **Access Application**
   - Open browser and navigate to `http://localhost:8080/chatbot`

## Testing

Run the test suite:
```bash
mvn test
```

## Security Features

- Passwords are hashed before storage
- Input validation on both client and server side
- SQL injection prevention using prepared statements
- XSS protection through input sanitization
- CSRF protection implemented

## API Documentation

### User Management Endpoints

- `POST /user/register` - Register new user
- `POST /user/login` - Authenticate user
- `GET /user/profile` - Get user profile
- `PUT /user/profile` - Update user profile

### Chat Endpoints

- `GET /chat` - Get chat interface
- `POST /chat/message` - Send message
- `GET /chat/history` - Get chat history

## Project Screenshots

[Add screenshots of your application here]

## Future Enhancements

- Integration with advanced AI models
- Real-time notifications
- Group chat functionality
- Voice message support
- Multi-language support

## References

1. [List your references and learning resources]
2. [Include links to tutorials or documentation you used]
3. [Add any academic papers or articles referenced]
