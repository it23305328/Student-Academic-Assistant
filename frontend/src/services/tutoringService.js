import api from './api';

export default {
    /**
     * Retrieves all tutoring sessions
     */
    getAllSessions() {
        return api.get('/api/tutoring/sessions');
    },

    /**
     * Adds a new tutoring session
     * @param {Object} session - The tutoring session object
     */
    addSession(session) {
        return api.post('/api/tutoring/sessions/add', session);
    },

    /**
     * Updates an existing tutoring session
     * @param {number} id - The session ID
     * @param {Object} session - The updated session object
     */
    updateSession(id, session) {
        return api.put(`/api/tutoring/sessions/update/${id}`, session);
    },

    /**
     * Deletes a tutoring session
     * @param {number} id - The session ID
     */
    deleteSession(id) {
        return api.delete(`/api/tutoring/sessions/delete/${id}`);
    },

    /**
     * Gets filtered tutoring sessions based on student info
     * @param {Object} filterParams - Filter parameters (year, semester, specialization)
     */
    getFilteredSessions(filterParams) {
        return api.post('/api/tutoring/sessions/filter', filterParams);
    },

    /**
     * Gets upcoming tutoring sessions
     */
    getUpcomingSessions() {
        return api.get('/api/tutoring/sessions/upcoming');
    }
};