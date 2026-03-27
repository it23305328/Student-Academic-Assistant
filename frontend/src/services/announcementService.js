// services/announcementService.js
import api from './api';

export default {
    /**
     * Retrieves all announcements
     */
    getAllAnnouncements() {
        return api.get('/announcements');
    },

    /**
     * Adds a new announcement with image
     * @param {FormData} formData - The form data containing announcement and image
     */
    addAnnouncement(formData) {
        return api.post('/announcements/add', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    },

    /**
     * Updates an existing announcement
     * @param {number} id - The announcement ID
     * @param {FormData} formData - The form data containing updated announcement and image
     */
    updateAnnouncement(id, formData) {
        return api.put(`/announcements/update/${id}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    },

    /**
     * Deletes an announcement
     * @param {number} id - The announcement ID
     */
    deleteAnnouncement(id) {
        return api.delete(`/announcements/delete/${id}`);
    },

    /**
     * Gets announcements for a specific student based on their year, semester, and faculty
     * @param {Object} studentInfo - Student information object
     */
    getFilteredAnnouncements(studentInfo) {
        return api.post('/announcements/filter', studentInfo);
    }
};