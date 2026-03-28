import api from './api';

export default {
    /**
     * Retrieves the entire timetable for a student
     * @param {number} studentId - The ID of the student
     */
    getAllTimetable(studentId) {
        return api.get(`/api/timetable/all/${studentId}`);
    },

    /**
     * Retrieves today's timetable for a student
     * @param {number} studentId - The ID of the student
     */
    getTodayTimetable(studentId) {
        return api.get(`/api/timetable/today/${studentId}`);
    },

    /**
     * Retrieves computed free slots for a given student and date
     * @param {number} studentId - The ID of the student
     * @param {string} date - ISO Date string (e.g., '2024-11-20')
     */
    getFreeSlots(studentId, date) {
        return api.get(`/api/timetable/free-slots/${studentId}`, {
            params: { date }
        });
    },

    /**
     * Adds a new timetable entry
     * @param {Object} entry - The timetable entry object
     */
    addEntry(entry) {
        return api.post('/api/timetable/add', entry);
    },

    /**
     * Updates an existing timetable entry
     * @param {number} id - The entry ID
     * @param {number} studentId - The owner's ID
     * @param {Object} entry - The updated data
     */
    updateEntry(id, studentId, entry) {
        return api.put(`/api/timetable/update/${id}`, entry, {
            params: { studentId }
        });
    },

    /**
     * Deletes a timetable entry
     * @param {number} id - The entry ID
     * @param {number} studentId - The owner's ID
     */
    deleteEntry(id, studentId) {
        return api.delete(`/api/timetable/delete/${id}`, {
            params: { studentId }
        });
    }
};
