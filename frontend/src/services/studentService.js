import api from './api';

export default {
    /**
     * Registers a new student
     * @param {Object} data - { name, email, password }
     */
    registerStudent(data) {
        return api.post('/auth/register', data);
    },

    /**
     * Authenticates a student
     * @param {Object} data - { email, password }
     */
    loginStudent(data) {
        return api.post('/auth/login', data);
    }
};
