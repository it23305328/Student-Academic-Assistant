<template>
  <div class="admin-wrapper">
    <!-- Sidebar -->
    <aside class="admin-sidebar">
      <div class="sidebar-header">
        <div class="logo">A</div>
        <h2>Admin Console</h2>
      </div>
      <nav class="sidebar-nav">
        <a href="#" class="nav-item" :class="{ active: currentView === 'dashboard' }" @click="currentView = 'dashboard'">
          <LayoutDashboard class="nav-icon" :size="20" />
          <span>Dashboard</span>
        </a>

        <a href="#" class="nav-item" :class="{ active: currentView === 'announcements' }" @click="currentView = 'announcements'">
          <Megaphone class="nav-icon" :size="20" />
          <span>Announcements</span>
        </a>

        <a href="#" class="nav-item" :class="{ active: currentView === 'add-lecture' }" @click="currentView = 'add-lecture'">
          <Video class="nav-icon" :size="20" />
          <span>Add Lecture</span>
        </a>

        <a href="#" class="nav-item" :class="{ active: currentView === 'manage-lectures' }" @click="currentView = 'manage-lectures'">
          <BookOpen class="nav-icon" :size="20" />
          <span>Manage Lectures</span>
        </a>

        <a href="#" class="nav-item" :class="{ active: currentView === 'students' }" @click="currentView = 'students'">
          <Users class="nav-icon" :size="20" />
          <span>Student Overview</span>
        </a>
      </nav>
      <div class="sidebar-footer">
        <button @click="handleLogout" class="logout-btn">
          <LogOut class="logout-icon" :size="18" />
          <span>Logout</span>
        </button>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="admin-main">
      <header class="admin-header">
        <h1>{{ viewTitle }}</h1>
        <div class="user-profile">
          <User class="user-icon" :size="16" />
          <span>{{ adminName }}</span>
        </div>
      </header>

      <section class="admin-content">
        <!-- Dashboard Overview -->
        <div v-if="currentView === 'dashboard'" class="dashboard-overview">
          <div class="admin-stats">
            <div class="stat-card">
              <div class="stat-icon">
                <Users :size="24" />
              </div>
              <div class="stat-info">
                <h3>Total Students</h3>
                <p class="stat-value">{{ students.length }}</p>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <BookOpen :size="24" />
              </div>
              <div class="stat-info">
                <h3>Total Lectures</h3>
                <p class="stat-value">{{ lectures.length }}</p>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <Megaphone :size="24" />
              </div>
              <div class="stat-info">
                <h3>Total Announcements</h3>
                <p class="stat-value">{{ announcements.length }}</p>
              </div>
            </div>
            <div class="stat-card">
              <div class="stat-icon">
                <Server :size="24" />
              </div>
              <div class="stat-info">
                <h3>Server Status</h3>
                <p class="stat-value text-green">Online</p>
              </div>
            </div>
          </div>
        </div>

        <!-- Announcements Management -->
        <div v-if="currentView === 'announcements'">
          <AdminAnnouncement />
        </div>

        <!-- Add Lecture Form -->
        <div v-if="currentView === 'add-lecture'" class="admin-card">
          <form @submit.prevent="handleSubmitLecture" class="lecture-form">
            <div class="form-grid">
              <div class="form-group">
                <label>Academic Year</label>
                <select v-model="lectureForm.academicYear" required>
                  <option value="" disabled>Select Year</option>
                  <option v-for="y in 4" :key="y" :value="y">Year {{ y }}</option>
                </select>
              </div>
              <div class="form-group">
                <label>Semester</label>
                <select v-model="lectureForm.semester" required>
                  <option value="" disabled>Select Semester</option>
                  <option value="1">Semester 1</option>
                  <option value="2">Semester 2</option>
                </select>
              </div>
              <div class="form-group">
                <label>Course</label>
                <select v-model="lectureForm.course" required>
                  <option value="" disabled>Select Course</option>
                  <option value="IT">IT</option>
                  <option value="SE">SE</option>
                  <option value="CS">CS</option>
                </select>
              </div>
              <div class="form-group">
                <label>Module</label>
                <select v-model="lectureForm.moduleName" required>
                  <option value="" disabled>Select Module</option>
                  <option v-for="mod in availableModules" :key="mod" :value="mod">{{ mod }}</option>
                </select>
              </div>
            </div>

            <div class="form-group">
              <label>Lecture Title</label>
              <input type="text" v-model="lectureForm.title" placeholder="e.g., Introduction to Neural Networks" required />
            </div>

            <div class="form-group">
              <label>YouTube Video URL</label>
              <input type="url" v-model="lectureForm.youtubeUrl" placeholder="https://youtube.com/watch?v=..." required />
            </div>

            <div class="form-group">
              <label>Lecture Description</label>
              <textarea v-model="lectureForm.description" rows="4" placeholder="Briefly describe what this lecture covers..." required></textarea>
            </div>

            <div class="form-actions">
              <button type="submit" class="submit-btn" :disabled="isSubmitting">
                {{ isEditing ? 'Update Lecture' : 'Publish Lecture' }}
              </button>
              <button v-if="isEditing" type="button" @click="resetForm" class="cancel-btn">Cancel Edit</button>
            </div>
          </form>
        </div>

        <!-- Manage Lectures Table -->
        <div v-if="currentView === 'manage-lectures'" class="admin-table-container">
          <div class="table-header">
            <h3>Lecture Library</h3>
            <div class="table-stats">{{ lectures.length }} lectures total</div>
          </div>
          <table class="admin-table">
            <thead>
              <tr>
                <th>Module</th>
                <th>Title</th>
                <th>Year/Sem</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="lec in lectures" :key="lec.id">
                <td><strong>{{ lec.moduleName }}</strong></td>
                <td>{{ lec.title }}</td>
                <td>Y{{ lec.academicYear }} S{{ lec.semester }}</td>
                <td>
                  <div class="action-btns">
                    <button @click="editLecture(lec)" class="btn-edit">
                      <Pencil :size="14" />
                      Edit
                    </button>
                    <button @click="deleteLecture(lec.id)" class="btn-delete">
                      <Trash2 :size="14" />
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
              <tr v-if="lectures.length === 0">
                <td colspan="4" class="text-center">No lectures found. Upload your first lecture!</td>
              </tr>
            </tbody>
          </table>
        </div>

        <!-- Student Overview Table -->
        <div v-if="currentView === 'students'" class="admin-table-container">
          <div class="table-header">
            <h3>Student Directory</h3>
            <div class="table-stats">{{ students.length }} registered students</div>
          </div>
          <table class="admin-table">
            <thead>
              <tr>
                <th>Registration No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Batch</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="student in students" :key="student.id">
                <td>#{{ student.id + 1000 }}</td>
                <td>{{ student.name }}</td>
                <td>{{ student.email }}</td>
                <td>Y{{ student.academicYear }} S{{ student.semester }} ({{ student.course }})</td>
              </tr>
              <tr v-if="students.length === 0">
                <td colspan="4" class="text-center">No students registered yet.</td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import lectureService from '../services/lectureService';
import announcementService from '../services/announcementService';
import AdminAnnouncement from './admin/AdminAnnouncement.vue';
import {
  LayoutDashboard,
  Video,
  BookOpen,
  Users,
  LogOut,
  User,
  Server,
  Pencil,
  Trash2,
  Megaphone
} from 'lucide-vue-next';

const router = useRouter();
const adminName = ref(localStorage.getItem('username') || 'Administrator');
const currentView = ref('dashboard');
const isSubmitting = ref(false);
const isEditing = ref(false);
const editingId = ref(null);

const lectures = ref([]);
const students = ref([]);
const announcements = ref([]);

const lectureForm = ref({
  academicYear: '',
  semester: '',
  course: '',
  moduleName: '',
  title: '',
  youtubeUrl: '',
  description: ''
});

// Mock modules data for the dynamic dropdown
const modulesMatrix = {
  '1-1': ['Introduction to IT', 'Mathematics for IT', 'PC Applications'],
  '1-2': ['Programming in C', 'Database Systems I', 'English for Professionals'],
  '2-1': ['Data Structures', 'Object Oriented Programming', 'Software Engineering I'],
  '2-2': ['Computer Networks', 'Operating Systems', 'Probability & Statistics'],
  '3-1': ['DBMS II', 'Software Quality Assurance', 'Mobile App Development'],
  '3-2': ['Distributed Systems', 'Cloud Computing', 'Enterprise Application Dev'],
  '4-1': ['Big Data Analytics', 'Machine Learning', 'Research Project I'],
  '4-2': ['Advanced Cybersecurity', 'AI Applications', 'Research Project II']
};

const availableModules = computed(() => {
  if (!lectureForm.value.academicYear || !lectureForm.value.semester) return [];
  const key = `${lectureForm.value.academicYear}-${lectureForm.value.semester}`;
  return modulesMatrix[key] || [];
});

const viewTitle = computed(() => {
  switch (currentView.value) {
    case 'dashboard': return 'Admin Overview';
    case 'announcements': return 'Manage Announcements';
    case 'add-lecture': return isEditing.value ? 'Edit Lecture' : 'Upload New Lecture';
    case 'manage-lectures': return 'Manage Uploads';
    case 'students': return 'Registered Students';
    default: return 'Admin Panel';
  }
});

const fetchInitialData = async () => {
  try {
    const [lecRes, stuRes, annRes] = await Promise.all([
      lectureService.getAllLectures(),
      lectureService.getAllStudents(),
      announcementService.getAllAnnouncements()
    ]);
    lectures.value = lecRes.data;
    students.value = stuRes.data;
    announcements.value = annRes.data;
  } catch (error) {
    console.error('Failed to fetch admin data:', error);
  }
};

const handleSubmitLecture = async () => {
  isSubmitting.value = true;
  try {
    if (isEditing.value) {
      await lectureService.updateLecture(editingId.value, lectureForm.value);
      alert('Lecture updated successfully!');
    } else {
      await lectureService.saveLecture(lectureForm.value);
      alert('Lecture published successfully!');
    }
    resetForm();
    await fetchInitialData();
    currentView.value = 'manage-lectures';
  } catch (error) {
    alert('Error saving lecture. Please check console.');
  } finally {
    isSubmitting.value = false;
  }
};

const deleteLecture = async (id) => {
  if (!confirm('Are you sure you want to delete this lecture?')) return;
  try {
    await lectureService.deleteLecture(id);
    await fetchInitialData();
  } catch (error) {
    alert('Failed to delete lecture');
  }
};

const editLecture = (lec) => {
  lectureForm.value = { ...lec };
  editingId.value = lec.id;
  isEditing.value = true;
  currentView.value = 'add-lecture';
};

const resetForm = () => {
  lectureForm.value = {
    academicYear: '',
    semester: '',
    course: '',
    moduleName: '',
    title: '',
    youtubeUrl: '',
    description: ''
  };
  isEditing.value = false;
  editingId.value = null;
};

const handleLogout = () => {
  localStorage.clear();
  router.push('/guest-dashboard');
};

onMounted(() => {
  const role = localStorage.getItem('role');
  if (role !== 'ADMIN') {
    router.push('/student-dashboard');
    return;
  }
  fetchInitialData();
});
</script>

<style scoped>
/* Admin General Styles - Matching Student Dashboard Theme */
.admin-wrapper {
  display: flex;
  min-height: 100vh;
  background-color: #fafaf5;
  color: #2e342d;
  font-family: 'Manrope', sans-serif;
}

.admin-sidebar {
  width: 260px;
  background-color: #ecefe7;
  border-right: 1px solid rgba(0,0,0,0.05);
  display: flex;
  flex-direction: column;
  padding: 32px 0;
  position: fixed;
  height: 100vh;
  z-index: 50;
}

.sidebar-header {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 0 24px;
  margin-bottom: 40px;
}

.logo {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #4e6073, #3b4958);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 16px;
  font-weight: 800;
  font-size: 20px;
  box-shadow: 0 4px 12px rgba(78, 96, 115, 0.2);
}

.sidebar-header h2 {
  font-size: 18px;
  font-weight: 800;
  margin: 0;
  color: #4e6073;
}

.sidebar-nav {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 0 16px;
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 24px;
  text-decoration: none;
  color: #5b6063;
  font-weight: 600;
  border-radius: 16px;
  transition: all 0.2s ease;
  font-size: 14px;
}

.nav-icon {
  flex-shrink: 0;
}

.nav-item:hover,
.nav-item.active {
  background-color: rgba(255, 255, 255, 0.4);
  color: #4e6073;
}

.nav-item.active {
  background-color: #ffffff;
  color: #4e6073;
  box-shadow: 0 4px 12px rgba(0,0,0,0.05);
}

.sidebar-footer {
  padding: 24px 16px;
  margin-top: auto;
  border-top: 1px solid rgba(0,0,0,0.05);
}

.logout-btn {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  padding: 12px;
  background-color: #ffffff;
  color: #9f403d;
  border: 1px solid rgba(159, 64, 61, 0.2);
  border-radius: 16px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 14px;
}

.logout-btn:hover {
  background-color: rgba(159, 64, 61, 0.05);
  border-color: #9f403d;
}

.logout-icon {
  flex-shrink: 0;
}

/* Main Content area */
.admin-main {
  flex-grow: 1;
  margin-left: 260px;
  padding: 32px 48px;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
}

.admin-header h1 {
  font-size: 28px;
  font-weight: 800;
  color: #2e342d;
  margin: 0;
}

.user-profile {
  background: white;
  padding: 8px 20px;
  border-radius: 99px;
  box-shadow: 0 4px 12px rgba(0,0,0,0.03);
  font-weight: 600;
  font-size: 13px;
  display: flex;
  align-items: center;
  gap: 8px;
  color: #4e6073;
  border: 1px solid rgba(0,0,0,0.05);
}

.user-icon {
  flex-shrink: 0;
}

/* Stats Cards */
.admin-stats {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.stat-card {
  background: white;
  padding: 24px;
  border-radius: 24px;
  border: 1px solid rgba(0,0,0,0.05);
  box-shadow: 0 4px 12px rgba(0,0,0,0.02);
  display: flex;
  align-items: center;
  gap: 16px;
  transition: transform 0.2s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  width: 56px;
  height: 56px;
  background: #ecefe7;
  border-radius: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #4e6073;
}

.stat-info h3 {
  color: #64748b;
  font-size: 12px;
  text-transform: uppercase;
  font-weight: 700;
  margin: 0 0 4px 0;
  letter-spacing: 0.05em;
}

.stat-value {
  font-size: 28px;
  font-weight: 800;
  margin: 0;
  color: #2e342d;
}

.text-green {
  color: #10b981;
}

/* Table and Form Common */
.admin-card,
.admin-table-container {
  background: white;
  padding: 28px;
  border-radius: 24px;
  border: 1px solid rgba(0,0,0,0.05);
  box-shadow: 0 4px 12px rgba(0,0,0,0.02);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #ecefe7;
}

.table-header h3 {
  font-size: 18px;
  font-weight: 800;
  margin: 0;
  color: #2e342d;
}

.table-stats {
  font-size: 12px;
  font-weight: 600;
  color: #5a6159;
  background: #fafaf5;
  padding: 4px 12px;
  border-radius: 99px;
}

/* Form Styles */
.lecture-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 20px;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-group label {
  font-size: 13px;
  font-weight: 700;
  color: #4e6073;
  text-transform: uppercase;
  letter-spacing: 0.03em;
}

.form-group input,
.form-group select,
.form-group textarea {
  padding: 12px 16px;
  border: 1px solid #ecefe7;
  border-radius: 16px;
  font-size: 14px;
  font-family: inherit;
  outline: none;
  transition: all 0.2s ease;
  background-color: #ffffff;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  border-color: #4e6073;
  box-shadow: 0 0 0 3px rgba(78, 96, 115, 0.1);
}

.form-actions {
  display: flex;
  gap: 12px;
  margin-top: 8px;
}

.submit-btn {
  background-color: #4e6073;
  color: white;
  padding: 14px 24px;
  border: none;
  border-radius: 99px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s ease;
  font-size: 14px;
}

.submit-btn:hover {
  background-color: #3b4958;
  transform: translateY(-1px);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.cancel-btn {
  background: none;
  border: 1px solid #ecefe7;
  padding: 14px 24px;
  border-radius: 99px;
  cursor: pointer;
  font-weight: 600;
  color: #5a6159;
  transition: all 0.2s ease;
}

.cancel-btn:hover {
  background-color: #fafaf5;
  border-color: #cbd5e1;
}

/* Table Styles */
.admin-table {
  width: 100%;
  border-collapse: collapse;
}

.admin-table th {
  text-align: left;
  padding: 16px;
  border-bottom: 2px solid #ecefe7;
  font-size: 12px;
  font-weight: 700;
  color: #4e6073;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.admin-table td {
  padding: 16px;
  border-bottom: 1px solid #f3f4ee;
  font-size: 14px;
  color: #2e342d;
}

.admin-table tr:last-child td {
  border-bottom: none;
}

.admin-table tbody tr:hover {
  background-color: #fafaf5;
}

.action-btns {
  display: flex;
  gap: 8px;
}

.btn-edit,
.btn-delete {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  border-radius: 12px;
  border: none;
  font-size: 12px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s ease;
}

.btn-edit {
  background-color: #f1f5f9;
  color: #4e6073;
}

.btn-edit:hover {
  background-color: #e2e8f0;
  transform: translateY(-1px);
}

.btn-delete {
  background-color: #fef2f2;
  color: #9f403d;
}

.btn-delete:hover {
  background-color: #fee2e2;
  transform: translateY(-1px);
}

.text-center {
  text-align: center;
  color: #94a3b8;
  padding: 40px !important;
}

/* Responsive */
@media (max-width: 1024px) {
  .admin-sidebar {
    transform: translateX(-100%);
    transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 2000;
  }
  
  .admin-main {
    margin-left: 0;
    padding: 20px;
  }
  
  .admin-stats {
    grid-template-columns: 1fr;
  }
  
  .form-grid {
    grid-template-columns: 1fr;
  }
  
  .admin-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }
  
  .admin-header h1 {
    font-size: 24px;
  }
  
  .admin-card,
  .admin-table-container {
    padding: 20px;
    overflow-x: auto;
  }
  
  .admin-table {
    min-width: 600px;
  }
  
  .form-actions {
    flex-direction: column;
  }
  
  .submit-btn,
  .cancel-btn {
    width: 100%;
    justify-content: center;
  }
}
</style>