import api from './api';

export default {
    /**
     * Retrieves all notifications for a given student
     * @param {number} studentId - The ID of the student
     */
    getNotifications(studentId) {
        return api.get(`/api/notifications/${studentId}`);
    },

    /**
     * Optional: Mark a notification as read
     * @param {number} notificationId 
     */
    markAsRead(notificationId) {
        return api.put(`/api/notifications/${notificationId}/read`);
    },

    /**
     * Retrieves upcoming alerts (deadlines, classes) for today/tomorrow
     * @param {number} studentId 
     */
    getUpcomingAlerts(studentId) {
        return api.get(`/api/notifications/alerts/${studentId}`);
    }
};
