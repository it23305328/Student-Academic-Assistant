import api from './api';

export default {
  getAllGroups() {
    return api.get('/api/study-groups/groups');
  },
  
  getGroupById(id) {
    return api.get(`/api/study-groups/groups/${id}`);
  },
  
  createGroup(groupData) {
    return api.post('/api/study-groups/groups/create', groupData);
  },
  
  updateGroup(id, groupData) {
    return api.put(`/api/study-groups/groups/update/${id}`, groupData);
  },
  
  joinGroup(groupId, memberData) {
    return api.post(`/api/study-groups/groups/join/${groupId}`, memberData);
  },
  
  leaveGroup(groupId, userEmail) {
    return api.delete(`/api/study-groups/groups/leave/${groupId}?userEmail=${userEmail}`);
  },
  
  deleteGroup(groupId, creatorEmail) {
    return api.delete(`/api/study-groups/groups/delete/${groupId}?creatorEmail=${creatorEmail}`);
  },
  
  getGroupsByCreator(email) {
    return api.get(`/api/study-groups/groups/creator/${email}`);
  },
  
  filterGroups(params) {
    return api.post('/api/study-groups/groups/filter', params);
  }
};