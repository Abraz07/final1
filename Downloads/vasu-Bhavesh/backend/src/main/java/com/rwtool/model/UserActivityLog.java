package com.rwtool.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "user_activity_logs")
public class UserActivityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Instant timestamp;
    
    @Column(nullable = false)
    private String userEmail;
    
    @Column(nullable = false)
    private String userName;
    
    @Column(nullable = false)
    private String userRole;
    
    @Column(nullable = false)
    private String action;
    
    @Column(length = 1000)
    private String details;
    
    @Column(nullable = false)
    private String status; // SUCCESS or FAILED
    
    @Column(length = 500)
    private String ipAddress;
    
    public UserActivityLog() {}
    
    public UserActivityLog(String userEmail, String userName, String userRole, String action, String details, String status) {
        this.timestamp = Instant.now();
        this.userEmail = userEmail;
        this.userName = userName;
        this.userRole = userRole;
        this.action = action;
        this.details = details;
        this.status = status;
    }
    
    @PrePersist
    public void prePersist() {
        if (this.timestamp == null) {
            this.timestamp = Instant.now();
        }
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserRole() {
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
