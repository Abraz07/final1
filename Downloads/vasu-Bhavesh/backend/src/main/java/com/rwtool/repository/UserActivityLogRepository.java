package com.rwtool.repository;

import com.rwtool.model.UserActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;

public interface UserActivityLogRepository extends JpaRepository<UserActivityLog, Long> {
    
    // Find logs by user email
    List<UserActivityLog> findByUserEmailOrderByTimestampDesc(String userEmail);
    
    // Find logs by action type
    List<UserActivityLog> findByActionOrderByTimestampDesc(String action);
    
    // Find logs by role
    List<UserActivityLog> findByUserRoleOrderByTimestampDesc(String userRole);
    
    // Find logs by status
    List<UserActivityLog> findByStatusOrderByTimestampDesc(String status);
    
    // Find recent logs (limit)
    @Query("SELECT l FROM UserActivityLog l ORDER BY l.timestamp DESC")
    List<UserActivityLog> findRecentLogs();
    
    // Find logs within date range
    List<UserActivityLog> findByTimestampBetweenOrderByTimestampDesc(Instant startDate, Instant endDate);
    
    // Find logs by multiple filters
    @Query("SELECT l FROM UserActivityLog l WHERE " +
           "(:userRole IS NULL OR l.userRole = :userRole) AND " +
           "(:action IS NULL OR l.action = :action) AND " +
           "(:status IS NULL OR l.status = :status) AND " +
           "l.timestamp BETWEEN :startDate AND :endDate " +
           "ORDER BY l.timestamp DESC")
    List<UserActivityLog> findByFilters(
        @Param("userRole") String userRole,
        @Param("action") String action,
        @Param("status") String status,
        @Param("startDate") Instant startDate,
        @Param("endDate") Instant endDate
    );
    
    // Search logs
    @Query("SELECT l FROM UserActivityLog l WHERE " +
           "LOWER(l.userEmail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(l.userName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(l.action) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(l.details) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "ORDER BY l.timestamp DESC")
    List<UserActivityLog> searchLogs(@Param("searchTerm") String searchTerm);
}
