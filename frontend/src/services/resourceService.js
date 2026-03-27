
import apiClient from './api';

const resourceService = {
    // 1. Fetch pending resources for Admin
    getPendingResources() {
        return apiClient.get('/resources/pending');
    },

    // 2. Approve or Reject a resource
    updateResourceStatus(id, status) {
        return apiClient.put(`/resources/${id}/status/${status}`);
    },

    // 3. Fetch approved resources for Students
    getApprovedResources() {
        return apiClient.get('/resources/approved');
    }
};

export default resourceService;