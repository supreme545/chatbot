package com.chatbot.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private Long id;
    private Long userId;
    private String message;
    private String response;
    private LocalDateTime timestamp;
    private boolean isProcessed;
    
    // Constructors
    public ChatMessage() {}
    
    public ChatMessage(Long id, Long userId, String message, String response, LocalDateTime timestamp, boolean isProcessed) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.response = response;
        this.timestamp = timestamp;
        this.isProcessed = isProcessed;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isProcessed() { return isProcessed; }
    public void setProcessed(boolean processed) { isProcessed = processed; }
}
