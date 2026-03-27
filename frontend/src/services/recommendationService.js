import api from './api';

export default {
    /**
     * Retrieves personalized recommendations for a student
     * @param {number} studentId - The ID of the student
     */
    getRecommendations(studentId) {
        return api.get(`/recommendations/${studentId}`);
    }
};