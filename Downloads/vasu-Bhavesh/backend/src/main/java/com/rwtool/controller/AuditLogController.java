package com.rwtool.controller;

import com.rwtool.model.UserActivityLog;
import com.rwtool.service.UserActivityLogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@CrossOrigin(origins = "http://localhost:3000")
public class AuditLogController {
    
    private final UserActivityLogService auditLogService;
    
    public AuditLogController(UserActivityLogService auditLogService) {
        this.auditLogService = auditLogService;
    }
    
    /**
     * Get all audit logs
     */
    @GetMapping
    public ResponseEntity<List<UserActivityLog>> getAllLogs() {
        List<UserActivityLog> logs = auditLogService.getAllLogs();
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get recent audit logs (last 100)
     */
    @GetMapping("/recent")
    public ResponseEntity<List<UserActivityLog>> getRecentLogs(
            @RequestParam(defaultValue = "100") int limit) {
        List<UserActivityLog> logs = auditLogService.getRecentLogs(limit);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get logs by user email
     */
    @GetMapping("/user/{email}")
    public ResponseEntity<List<UserActivityLog>> getLogsByUser(@PathVariable String email) {
        List<UserActivityLog> logs = auditLogService.getLogsByUser(email);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get logs by action type
     */
    @GetMapping("/action/{action}")
    public ResponseEntity<List<UserActivityLog>> getLogsByAction(@PathVariable String action) {
        List<UserActivityLog> logs = auditLogService.getLogsByAction(action);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get logs by role
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserActivityLog>> getLogsByRole(@PathVariable String role) {
        List<UserActivityLog> logs = auditLogService.getLogsByRole(role);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get logs by date range
     */
    @GetMapping("/date-range/{range}")
    public ResponseEntity<List<UserActivityLog>> getLogsByDateRange(@PathVariable String range) {
        List<UserActivityLog> logs = auditLogService.getLogsByDateRange(range);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Get logs with filters
     */
    @GetMapping("/filter")
    public ResponseEntity<List<UserActivityLog>> getLogsWithFilters(
            @RequestParam(required = false) String userRole,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "7days") String dateRange) {
        
        List<UserActivityLog> logs = auditLogService.getLogsWithFilters(
            userRole, action, status, dateRange);
        return ResponseEntity.ok(logs);
    }
    
    /**
     * Search logs
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserActivityLog>> searchLogs(
            @RequestParam String searchTerm) {
        List<UserActivityLog> logs = auditLogService.searchLogs(searchTerm);
        return ResponseEntity.ok(logs);
    }
}
