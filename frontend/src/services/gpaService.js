import api from './api';

export default {
    /**
     * Calculates the GPA based on a list of subjects and grades
     * @param {Object} data - The payload containing an array of subjects
     */
    calculateGPA(data) {
        return api.post('/gpa/calculate', data);
    }
};
