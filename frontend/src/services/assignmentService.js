import api from './api';

export default {
    /**
     * Retrieves assignments for a given student
     * @param {number} studentId - The ID of the student
     */
    getAssignments(studentId) {
        return api.get(`/assignments/${studentId}`);
    },

    /**
     * Adds a new assignment/deadline
     * @param {Object} assignment - The assignment object
     */
    addAssignment(assignment) {
        return api.post('/assignments/add', assignment);
    }
};
