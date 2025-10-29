import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/audit-logs';

const auditLogService = {
    /**
     * Get all audit logs
     */
    getAllLogs: async () => {
        try {
            const response = await axios.get(`${API_BASE_URL}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching all audit logs:', error);
            throw error;
        }
    },

    /**
     * Get recent audit logs
     */
    getRecentLogs: async (limit = 100) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/recent?limit=${limit}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching recent audit logs:', error);
            throw error;
        }
    },

    /**
     * Get logs by user email
     */
    getLogsByUser: async (email) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/user/${email}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching logs by user:', error);
            throw error;
        }
    },

    /**
     * Get logs by action type
     */
    getLogsByAction: async (action) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/action/${action}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching logs by action:', error);
            throw error;
        }
    },

    /**
     * Get logs by role
     */
    getLogsByRole: async (role) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/role/${role}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching logs by role:', error);
            throw error;
        }
    },

    /**
     * Get logs by date range
     */
    getLogsByDateRange: async (dateRange) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/date-range/${dateRange}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching logs by date range:', error);
            throw error;
        }
    },

    /**
     * Get logs with filters
     */
    getLogsWithFilters: async (userRole, action, status, dateRange) => {
        try {
            const params = new URLSearchParams();
            if (userRole && userRole !== 'All Users') params.append('userRole', userRole);
            if (action && action !== 'All Actions') params.append('action', action);
            if (status && status !== 'All Status') params.append('status', status);
            if (dateRange) params.append('dateRange', dateRange);

            const response = await axios.get(`${API_BASE_URL}/filter?${params.toString()}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching logs with filters:', error);
            throw error;
        }
    },

    /**
     * Search logs
     */
    searchLogs: async (searchTerm) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/search?searchTerm=${encodeURIComponent(searchTerm)}`);
            return response.data;
        } catch (error) {
            console.error('Error searching logs:', error);
            throw error;
        }
    }
};

export default auditLogService;
