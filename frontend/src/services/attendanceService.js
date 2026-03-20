import api from './api';

export default {
    /**
     * Retrieves attendance summary for a given student
     * @param {number} studentId - The ID of the student
     */
    getAttendanceSummary(studentId) {
        return api.get(`/attendance/summary/${studentId}`);
    },

    /**
     * Marks attendance for a specific subject
     * @param {number} studentId - The ID of the student
     * @param {string} subjectName - Name of the subject
     * @param {boolean} present - Whether the student is present
     */
    markAttendance(studentId, subjectName, present = true, timetableId = null) {
        return api.post('/attendance/mark', {
            studentId,
            subjectName,
            present,
            timetableId
        });
    }
};
