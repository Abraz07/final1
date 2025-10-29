import axios from 'axios';
import authService from './authService';

const API_BASE_URL = 'http://localhost:8080/api';

const domainService = {
    // Get all domains
    getAllDomains: async () => {
        try {
            const response = await axios.get(`${API_BASE_URL}/domains`);
            return response.data;
        } catch (error) {
            console.error('Error fetching domains:', error);
            throw error;
        }
    },

    // Get domain by ID
    getDomainById: async (id) => {
        try {
            const response = await axios.get(`${API_BASE_URL}/domains/${id}`);
            return response.data;
        } catch (error) {
            console.error('Error fetching domain:', error);
            throw error;
        }
    },

    // Add new domain
    addDomain: async (domain) => {
        try {
            const currentUser = authService.getCurrentUser();
            const params = new URLSearchParams();
            if (currentUser) {
                params.append('adminEmail', currentUser.email);
                params.append('adminName', currentUser.fullName);
            }
            const response = await axios.post(
                `${API_BASE_URL}/domains?${params.toString()}`,
                domain
            );
            return response.data;
        } catch (error) {
            console.error('Error adding domain:', error);
            if (error.response && error.response.data) {
                throw new Error(error.response.data);
            }
            throw error;
        }
    },

    // Update domain
    updateDomain: async (id, domain) => {
        try {
            const currentUser = authService.getCurrentUser();
            const params = new URLSearchParams();
            if (currentUser) {
                params.append('adminEmail', currentUser.email);
                params.append('adminName', currentUser.fullName);
            }
            const response = await axios.put(
                `${API_BASE_URL}/domains/${id}?${params.toString()}`,
                domain
            );
            return response.data;
        } catch (error) {
            console.error('Error updating domain:', error);
            if (error.response && error.response.data) {
                throw new Error(error.response.data);
            }
            throw error;
        }
    },

    // Delete domain
    deleteDomain: async (id) => {
        try {
            const currentUser = authService.getCurrentUser();
            const params = new URLSearchParams();
            if (currentUser) {
                params.append('adminEmail', currentUser.email);
                params.append('adminName', currentUser.fullName);
            }
            await axios.delete(`${API_BASE_URL}/domains/${id}?${params.toString()}`);
        } catch (error) {
            console.error('Error deleting domain:', error);
            throw error;
        }
    }
};

export default domainService;
