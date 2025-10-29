import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/auth';

const authService = {
    /**
     * Sign up a new user
     */
    signup: async (signupData) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/signup`, {
                fullName: signupData.name,
                email: signupData.email,
                phoneNumber: signupData.phone,
                domain: signupData.domain || '',
                password: signupData.password,
                role: signupData.role.toUpperCase() // USER, ADMIN, OPS
            });
            
            // Store token and user info in localStorage
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify({
                    id: response.data.id,
                    email: response.data.email,
                    fullName: response.data.fullName,
                    role: response.data.role,
                    phoneNumber: response.data.phoneNumber,
                    domain: response.data.domain
                }));
            }
            
            return response.data;
        } catch (error) {
            console.error('Signup error:', error);
            if (error.response && error.response.data) {
                throw new Error(error.response.data.message || error.response.data);
            }
            throw new Error('Signup failed. Please try again.');
        }
    },

    /**
     * Login user
     */
    login: async (loginData) => {
        try {
            const response = await axios.post(`${API_BASE_URL}/login`, {
                email: loginData.email,
                password: loginData.password
            });
            
            // Store token and user info in localStorage
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify({
                    id: response.data.id,
                    email: response.data.email,
                    fullName: response.data.fullName,
                    role: response.data.role,
                    phoneNumber: response.data.phoneNumber,
                    domain: response.data.domain
                }));
            }
            
            return response.data;
        } catch (error) {
            console.error('Login error:', error);
            if (error.response && error.response.data) {
                throw new Error(error.response.data.message || error.response.data);
            }
            throw new Error('Login failed. Please check your credentials.');
        }
    },

    /**
     * Logout user
     */
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },

    /**
     * Get current user from localStorage
     */
    getCurrentUser: () => {
        const userStr = localStorage.getItem('user');
        if (userStr) {
            try {
                return JSON.parse(userStr);
            } catch (e) {
                return null;
            }
        }
        return null;
    },

    /**
     * Get auth token
     */
    getToken: () => {
        return localStorage.getItem('token');
    },

    /**
     * Check if user is authenticated
     */
    isAuthenticated: () => {
        return !!localStorage.getItem('token');
    }
};

export default authService;
