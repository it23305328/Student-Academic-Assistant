import apiClient from './api';

const lectureService = {
    getAllLectures() {
        return apiClient.get('/admin/lectures');
    },
    saveLecture(lecture) {
        return apiClient.post('/admin/lectures', lecture);
    },
    updateLecture(id, lecture) {
        return apiClient.put(`/admin/lectures/${id}`, lecture);
    },
    deleteLecture(id) {
        return apiClient.delete(`/admin/lectures/${id}`);
    },
    getAllStudents() {
        return apiClient.get('/admin/students');
    }
};

export default lectureService;
