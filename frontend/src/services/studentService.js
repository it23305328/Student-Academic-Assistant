import api from './api';

export default {
    /**
     * Registers a new student
     * @param {Object} data - { name, email, password }
     */
    registerStudent(data) {
        return api.post('/api/register', data);
    },

    /**
     * Authenticates a student
     * @param {Object} data - { email, password }
     */
    loginStudent(data) {
        return api.post('/api/login', data);
    },

    /**
     * Gets student details by ID
     * @param {number} id 
     */
    getStudentById(id) {
        return api.get(`/api/v1/students/details/${id}`);
    }
};
