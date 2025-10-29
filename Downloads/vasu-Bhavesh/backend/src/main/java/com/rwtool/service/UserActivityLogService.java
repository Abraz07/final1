package com.rwtool.service;

import com.rwtool.model.UserActivityLog;
import com.rwtool.repository.UserActivityLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class UserActivityLogService {
    
    private final UserActivityLogRepository repository;
    
    public UserActivityLogService(UserActivityLogRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Log a user activity
     */
    @Transactional
    public void logActivity(String userEmail, String userName, String userRole, 
                           String action, String details, String status) {
        UserActivityLog log = new UserActivityLog(userEmail, userName, userRole, action, details, status);
        repository.save(log);
    }
    
    /**
     * Log a successful activity
     */
    @Transactional
    public void logSuccess(String userEmail, String userName, String userRole, 
                          String action, String details) {
        logActivity(userEmail, userName, userRole, action, details, "success");
    }
    
    /**
     * Log a failed activity
     */
    @Transactional
    public void logFailure(String userEmail, String userName, String userRole, 
                          String action, String details) {
        logActivity(userEmail, userName, userRole, action, details, "failed");
    }
    
    /**
     * Get all logs
     */
    public List<UserActivityLog> getAllLogs() {
        return repository.findAll();
    }
    
    /**
     * Get recent logs (last 100)
     */
    public List<UserActivityLog> getRecentLogs(int limit) {
        List<UserActivityLog> logs = repository.findRecentLogs();
        return logs.size() > limit ? logs.subList(0, limit) : logs;
    }
    
    /**
     * Get logs by user email
     */
    public List<UserActivityLog> getLogsByUser(String userEmail) {
        return repository.findByUserEmailOrderByTimestampDesc(userEmail);
    }
    
    /**
     * Get logs by action type
     */
    public List<UserActivityLog> getLogsByAction(String action) {
        return repository.findByActionOrderByTimestampDesc(action);
    }
    
    /**
     * Get logs by role
     */
    public List<UserActivityLog> getLogsByRole(String userRole) {
        return repository.findByUserRoleOrderByTimestampDesc(userRole);
    }
    
    /**
     * Get logs by date range
     */
    public List<UserActivityLog> getLogsByDateRange(String dateRange) {
        Instant endDate = Instant.now();
        Instant startDate;
        
        switch (dateRange.toLowerCase()) {
            case "today":
                startDate = endDate.truncatedTo(ChronoUnit.DAYS);
                break;
            case "7days":
                startDate = endDate.minus(7, ChronoUnit.DAYS);
                break;
            case "30days":
                startDate = endDate.minus(30, ChronoUnit.DAYS);
                break;
            case "90days":
                startDate = endDate.minus(90, ChronoUnit.DAYS);
                break;
            default:
                startDate = endDate.minus(7, ChronoUnit.DAYS);
        }
        
        return repository.findByTimestampBetweenOrderByTimestampDesc(startDate, endDate);
    }
    
    /**
     * Get logs with filters
     */
    public List<UserActivityLog> getLogsWithFilters(String userRole, String action, 
                                                     String status, String dateRange) {
        Instant endDate = Instant.now();
        Instant startDate;
        
        switch (dateRange != null ? dateRange.toLowerCase() : "7days") {
            case "today":
                startDate = endDate.truncatedTo(ChronoUnit.DAYS);
                break;
            case "7days":
                startDate = endDate.minus(7, ChronoUnit.DAYS);
                break;
            case "30days":
                startDate = endDate.minus(30, ChronoUnit.DAYS);
                break;
            case "90days":
                startDate = endDate.minus(90, ChronoUnit.DAYS);
                break;
            default:
                startDate = endDate.minus(7, ChronoUnit.DAYS);
        }
        
        // Convert "All Users" and "All Actions" to null for query
        String roleFilter = (userRole != null && !userRole.equals("All Users")) ? userRole : null;
        String actionFilter = (action != null && !action.equals("All Actions")) ? action : null;
        String statusFilter = (status != null && !status.equals("All Status")) ? status : null;
        
        return repository.findByFilters(roleFilter, actionFilter, statusFilter, startDate, endDate);
    }
    
    /**
     * Search logs
     */
    public List<UserActivityLog> searchLogs(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return repository.findRecentLogs();
        }
        return repository.searchLogs(searchTerm.trim());
    }
}
