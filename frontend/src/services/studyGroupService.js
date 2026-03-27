import api from './api';

export default {
  getAllGroups() {
    return api.get('/study-groups/groups');
  },
  
  getGroupById(id) {
    return api.get(`/study-groups/groups/${id}`);
  },
  
  createGroup(groupData) {
    return api.post('/study-groups/groups/create', groupData);
  },
  
  joinGroup(groupId, memberData) {
    return api.post(`/study-groups/groups/join/${groupId}`, memberData);
  },
  
  leaveGroup(groupId, userEmail) {
    return api.delete(`/study-groups/groups/leave/${groupId}?userEmail=${userEmail}`);
  },
  
  deleteGroup(groupId, creatorEmail) {
    return api.delete(`/study-groups/groups/delete/${groupId}?creatorEmail=${creatorEmail}`);
  },
  
  getGroupsByCreator(email) {
    return api.get(`/study-groups/groups/creator/${email}`);
  },
  
  filterGroups(params) {
    return api.post('/study-groups/groups/filter', params);
  }
};