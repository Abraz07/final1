package com.rwtool.controller;

import com.rwtool.model.Domain;
import com.rwtool.service.DomainService;
import com.rwtool.service.UserActivityLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domains")
@CrossOrigin(origins = "http://localhost:3000")
public class DomainController {

    @Autowired
    private DomainService domainService;
    
    @Autowired
    private UserActivityLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<Domain>> getAllDomains() {
        try {
            List<Domain> domains = domainService.getAllDomains();
            return ResponseEntity.ok(domains);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domain> getDomainById(@PathVariable String id) {
        try {
            Domain domain = domainService.getDomainById(id);
            return ResponseEntity.ok(domain);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addDomain(
            @RequestBody Domain domain,
            @RequestParam(required = false) String adminEmail,
            @RequestParam(required = false) String adminName) {
        try {
            Domain savedDomain = domainService.addDomain(domain);
            // Log domain addition with actual admin info
            auditLogService.logSuccess(
                adminEmail != null ? adminEmail : "admin@rwtool.com",
                adminName != null ? adminName : "Admin",
                "Admin",
                "DOMAIN_ADDED",
                "Added new domain: " + savedDomain.getName()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDomain);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDomain(
            @PathVariable String id,
            @RequestBody Domain domain,
            @RequestParam(required = false) String adminEmail,
            @RequestParam(required = false) String adminName) {
        try {
            Domain updatedDomain = domainService.updateDomain(id, domain);
            // Log domain update with actual admin info
            auditLogService.logSuccess(
                adminEmail != null ? adminEmail : "admin@rwtool.com",
                adminName != null ? adminName : "Admin",
                "Admin",
                "DOMAIN_UPDATED",
                "Updated domain: " + updatedDomain.getName()
            );
            return ResponseEntity.ok(updatedDomain);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDomain(
            @PathVariable String id,
            @RequestParam(required = false) String adminEmail,
            @RequestParam(required = false) String adminName) {
        try {
            Domain domain = domainService.getDomainById(id);
            domainService.deleteDomain(id);
            // Log domain deletion with actual admin info
            auditLogService.logSuccess(
                adminEmail != null ? adminEmail : "admin@rwtool.com",
                adminName != null ? adminName : "Admin",
                "Admin",
                "DOMAIN_DELETED",
                "Deleted domain: " + domain.getName()
            );
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
