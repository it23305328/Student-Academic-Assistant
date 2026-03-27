<template>
  <div class="admin-wrapper">
    <aside class="admin-sidebar">
      <div class="sidebar-header">
        <div class="logo">A</div>
        <h2>Admin Console</h2>
      </div>
      <nav class="sidebar-nav">
        <a href="#" class="nav-item" :class="{ active: currentView === 'dashboard' }" @click.prevent="currentView = 'dashboard'">Dashboard</a>
        <a href="#" class="nav-item" :class="{ active: currentView === 'add-lecture' }" @click.prevent="currentView = 'add-lecture'">Add Lecture</a>
        <a href="#" class="nav-item" :class="{ active: currentView === 'manage-lectures' }" @click.prevent="currentView = 'manage-lectures'">Manage Lectures</a>
        <a href="#" class="nav-item" :class="{ active: currentView === 'students' }" @click.prevent="currentView = 'students'">Student Overview</a>
        <a href="#" class="nav-item" :class="{ active: currentView === 'manage-resources' }" @click.prevent="currentView = 'manage-resources'">Manage Resources</a>
      </nav>
      <div class="sidebar-footer">
        <button @click="handleLogout" class="logout-btn">Logout</button>
      </div>
    </aside>

    <main class="admin-main">
      <header class="admin-header">
        <h1>{{ viewTitle }}</h1>
        <div class="user-profile">
          <span>{{ adminName }}</span>
        </div>
      </header>

      <section class="admin-content">
        <div v-if="currentView === 'dashboard'" class="dashboard-overview">
          <div class="admin-stats">
            <div class="stat-card">
              <h3>Total Students</h3>
              <p class="stat-value">{{ students.length }}</p>
            </div>
            <div class="stat-card">
              <h3>Total Lectures</h3>
              <p class="stat-value">{{ lectures.length }}</p>
            </div>
            <div class="stat-card">
              <h3>Server Status</h3>
              <p class="stat-value text-green">Online</p>
            </div>
          </div>
        </div>

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

            <button type="submit" class="submit-btn" :disabled="isSubmitting">
              {{ isEditing ? 'Update Lecture' : 'Publish Lecture' }}
            </button>
            <button v-if="isEditing" type="button" @click="resetForm" class="cancel-btn">Cancel Edit</button>
          </form>
        </div>

        <div v-if="currentView === 'manage-lectures'" class="admin-table-container">
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
                    <button @click="editLecture(lec)" class="btn-edit">Edit</button>
                    <button @click="deleteLecture(lec.id)" class="btn-delete">Delete</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div v-if="currentView === 'students'" class="admin-table-container">
          <table class="admin-table">
            <thead>
              <tr>
                <th>Reg No</th>
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
            </tbody>
          </table>
        </div>

        <div v-if="currentView === 'manage-resources'" class="admin-table-container">
          <table class="admin-table">
            <thead>
              <tr>
                <th>File Name</th>
                <th>Subject</th>
                <th>Semester</th>
                <th>Uploaded By</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="res in pendingResources" :key="res.id">
                <td><strong>{{ res.fileName || res.file_name }}</strong></td>
                <td>{{ res.subject }}</td>
                <td>{{ res.semester }}</td>
                <td>{{ res.uploadedBy || res.uploaded_by }}</td>
                <td>
                  <div class="action-btns">
                    <button @click="handleStatusUpdate(res.id, 'APPROVED')" class="btn-edit" style="color: #10b981; background: #ecfdf5; border: 1px solid #10b981;">Approve</button>
                    <button @click="handleStatusUpdate(res.id, 'REJECTED')" class="btn-delete">Reject</button>
                  </div>
                </td>
              </tr>
              <tr v-if="pendingResources.length === 0">
                <td colspan="5" class="text-center">No pending resources found. Everything is clear!</td>
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
import resourceService from '../services/resourceService';

const router = useRouter();
const adminName = ref(localStorage.getItem('username') || 'Administrator');
const currentView = ref('dashboard');
const isSubmitting = ref(false);
const isEditing = ref(false);
const editingId = ref(null);

const lectures = ref([]);
const students = ref([]);
const pendingResources = ref([]);

// --- API FETCH FUNCTIONS ---
const fetchPendingResources = async () => {
    try {
        const response = await resourceService.getPendingResources();
        pendingResources.value = response.data;
        console.log("Pending data loaded:", response.data);
    } catch (error) {
        console.error("Error loading pending resources:", error);
    }
};

const fetchInitialData = async () => {
  try {
    const [lecRes, stuRes] = await Promise.all([
      lectureService.getAllLectures(),
      lectureService.getAllStudents()
    ]);
    lectures.value = lecRes.data;
    students.value = stuRes.data;
    await fetchPendingResources();
  } catch (error) {
    console.error('Failed to fetch admin data:', error);
  }
};

// --- FIXED APPROVAL LOGIC ---
//  Function to handle Approve or Reject button clicks
const handleStatusUpdate = async (id, status) => {
    if (!id) return;
    
    try {
        // Directly update the resource status in the backend via PUT request
        await resourceService.updateResourceStatus(id, status);
        
        alert(`Resource ${status} successfully!`);
        
        // 💡 Crucial: Refresh the table data after the update to reflect changes
        await fetchPendingResources(); 
    } catch (error) {
        console.error("Update failed:", error);
        alert("Failed to update status. Please try again.");
    }
};


// --- LECTURE FORM LOGIC ---
const lectureForm = ref({ academicYear: '', semester: '', course: '', moduleName: '', title: '', youtubeUrl: '', description: '' });

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
    case 'add-lecture': return isEditing.value ? 'Edit Lecture' : 'Upload New Lecture';
    case 'manage-lectures': return 'Manage Uploads';
    case 'students': return 'Registered Students';
    case 'manage-resources': return 'Approve Learning Materials';
    default: return 'Admin Panel';
  }
});

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
    alert('Error saving lecture.');
  } finally { isSubmitting.value = false; }
};

const deleteLecture = async (id) => {
  if (!confirm('Are you sure?')) return;
  try { await lectureService.deleteLecture(id); await fetchInitialData(); } catch (error) { alert('Failed'); }
};

const editLecture = (lec) => {
  lectureForm.value = { ...lec };
  editingId.value = lec.id;
  isEditing.value = true;
  currentView.value = 'add-lecture';
};

const resetForm = () => {
  lectureForm.value = { academicYear: '', semester: '', course: '', moduleName: '', title: '', youtubeUrl: '', description: '' };
  isEditing.value = false;
  editingId.value = null;
};

const handleLogout = () => { localStorage.clear(); router.push('/login'); };

onMounted(() => {
  // 1. Security Check: First, check if the user is an ADMIN
  if (localStorage.getItem('role') !== 'ADMIN') {
    // If not an admin, redirect to student dashboard
    router.push('/student-dashboard');
    return;
  }

  // 2. Fetch Data: If the user is an ADMIN, load the table data
  // Call both your initial data and the pending resources list
  if (typeof fetchInitialData === 'function') {
    fetchInitialData();
  }
  
  fetchPendingResources(); 
});
</script>
<style scoped>
/* Admin General Styles */
.admin-wrapper {
  display: flex;
  min-height: 100vh;
  background-color: #f8fafc;
  color: #1e293b;
}

.admin-sidebar {
  width: 260px;
  background-color: #ffffff;
  border-right: 1px solid #e2e8f0;
  display: flex;
  flex-direction: column;
  padding: 24px;
  position: fixed;
  height: 100vh;
}

.sidebar-header {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 40px;
}

.logo {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #6366f1, #4f46e5);
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  font-weight: 800;
}

.sidebar-header h2 { font-size: 18px; font-weight: 700; margin: 0; }

.sidebar-nav { flex-grow: 1; display: flex; flex-direction: column; gap: 8px; }

.nav-item {
  padding: 12px 16px;
  text-decoration: none;
  color: #64748b;
  font-weight: 600;
  border-radius: 8px;
  transition: 0.2s;
  font-size: 14px;
}

.nav-item:hover, .nav-item.active { background-color: #f1f5f9; color: #4f46e5; }
.nav-item.active { border-right: 4px solid #4f46e5; }

.logout-btn {
  width: 100%;
  padding: 10px;
  background-color: #ef4444;
  color: white;
  border: none;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
  margin-top: 20px;
}

/* Main Content area */
.admin-main { flex-grow: 1; margin-left: 260px; padding: 40px; }

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
}

.admin-header h1 { font-size: 28px; font-weight: 800; }

.user-profile {
  background: white;
  padding: 8px 16px;
  border-radius: 99px;
  box-shadow: 0 4px 6px -1px rgba(0,0,0,0.1);
  font-weight: 600;
  font-size: 13px;
}

/* Stats */
.admin-stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 24px; }
.stat-card {
  background: white;
  padding: 24px;
  border-radius: 16px;
  box-shadow: 0 4px 6px -1px rgba(0,0,0,0.05);
  border: 1px solid #f1f5f9;
}
.stat-card h3 { color: #64748b; font-size: 12px; text-transform: uppercase; margin-bottom: 8px; }
.stat-value { font-size: 24px; font-weight: 800; margin: 0; }
.text-green { color: #10b981; }

/* Table and Form Common */
.admin-card, .admin-table-container {
  background: white;
  padding: 30px;
  border-radius: 16px;
  box-shadow: 0 10px 15px -3px rgba(0,0,0,0.05);
}

/* Form Styles */
.lecture-form { display: flex; flex-direction: column; gap: 20px; }
.form-grid { display: grid; grid-template-columns: repeat(2, 1fr); gap: 20px; }
.form-group { display: flex; flex-direction: column; gap: 8px; }
.form-group label { font-size: 13px; font-weight: 700; color: #475569; }
.form-group input, .form-group select, .form-group textarea {
  padding: 12px;
  border: 1px solid #e2e8f0;
  border-radius: 8px;
  font-size: 14px;
  outline: none;
  transition: 0.2s;
}
.form-group input:focus { border-color: #6366f1; box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1); }

.submit-btn {
  background-color: #4f46e5;
  color: white;
  padding: 14px;
  border: none;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
}
.submit-btn:hover { background-color: #4338ca; }
.cancel-btn { background: none; border: 1px solid #cbd5e1; padding: 10px; border-radius: 8px; cursor: pointer; }

/* Table Styles */
.admin-table { width: 100%; border-collapse: collapse; }
.admin-table th { text-align: left; padding: 16px; border-bottom: 2px solid #f1f5f9; font-size: 13px; color: #64748b; }
.admin-table td { padding: 16px; border-bottom: 1px solid #f1f5f9; font-size: 14px; }

.action-btns { display: flex; gap: 8px; }
.btn-edit, .btn-delete {
  padding: 6px 12px;
  border-radius: 6px;
  border: none;
  font-size: 12px;
  font-weight: 700;
  cursor: pointer;
}
.btn-edit { background-color: #f1f5f9; color: #4f46e5; }
.btn-delete { background-color: #fef2f2; color: #ef4444; }

.text-center { text-align: center; color: #94a3b8; padding: 40px; }

@media (max-width: 1024px) {
  .admin-stats { grid-template-columns: 1fr; }
  .form-grid { grid-template-columns: 1fr; }
}
</style>
