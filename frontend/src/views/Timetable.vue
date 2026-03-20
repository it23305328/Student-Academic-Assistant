<template>
  <div class="dashboard-root">
    
    <!-- Sidebar -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="brand-wrapper">
          <img src="../assets/images/logo.png" alt="StudentX Logo" class="brand-logo" />
          <div class="brand-text">
            <h1 class="brand-title">StudentX</h1>
            <p class="brand-subtitle">Academic Support</p>
          </div>
        </div>
      </div>
      
      <nav class="sidebar-nav">
        <router-link to="/student-dashboard" class="nav-link">
          <span class="material-symbols-outlined">dashboard</span>
          <span>Overview</span>
        </router-link>
        <router-link to="/timetable" class="nav-link">
          <span class="material-symbols-outlined">calendar_month</span>
          <span>Timetable</span>
        </router-link>
        <router-link to="/attendance" class="nav-link">
          <span class="material-symbols-outlined">assignment_turned_in</span>
          <span>Attendance</span>
        </router-link>
        <router-link to="/summarization" class="nav-link">
          <span class="material-symbols-outlined">auto_awesome</span>
          <span>Summarization</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="user-mini-card">
          <img :src="student.profilePic || 'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'" alt="User" class="nav-avatar" />
          <div class="nav-user-info">
            <p class="nav-user-name">{{ student.name }}</p>
            <p class="nav-user-id">#{{ student.studentId || 'ID1042' }}</p>
          </div>
          <button @click="handleLogout" class="btn-logout-mini" title="Logout">
            <span class="material-symbols-outlined">logout</span>
          </button>
        </div>
      </div>
    </aside>

    <!-- Main Content Wrapper -->
    <div class="main-wrapper">
      
      <!-- App Header -->
      <header class="app-header">
        <div class="search-bar">
          <span class="material-symbols-outlined">search</span>
          <input type="text" v-model="searchQuery" placeholder="Search sessions or topics..." />
        </div>
        
        <div class="header-actions">
          <div class="notif-wrapper">
            <button class="action-btn">
              <span class="material-symbols-outlined">notifications</span>
            </button>
          </div>
          <button class="action-btn">
            <span class="material-symbols-outlined">help_outline</span>
          </button>
          <div class="divider"></div>
          <div class="user-profile">
            <img :src="student.profilePic || 'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'" alt="Profile" />
          </div>
        </div>
      </header>

      <!-- Content Canvas -->
      <main class="content-canvas">
        
        <!-- Header Section -->
        <section class="page-title-section">
          <div class="title-details">
            <h2 class="main-heading">Weekly <span class="highlight-text">Curriculum.</span></h2>
            <p class="sub-heading">Your academic roadmap for the week. Stay focused, stay ahead.</p>
          </div>
          <div class="view-toggles">
            <button @click="viewMode = 'grid'" :class="['toggle-btn', viewMode === 'grid' ? 'active' : '']">Grid View</button>
            <button @click="viewMode = 'list'" :class="['toggle-btn', viewMode === 'list' ? 'active' : '']">List View</button>
          </div>
        </section>

        <!-- Loading state -->
        <div v-if="isLoading" class="loading-state">
           <div class="spinner"></div>
           <p>Syncing schedule...</p>
        </div>

        <!-- Timetable Bento Grid -->
        <div v-else class="timetable-grid">
          
          <!-- LEFT COLUMN: Today's Focus -->
          <div class="grid-left-col">
            <div class="focus-card">
              <div class="focus-header">
                <h3>Today's Focus</h3>
                <span class="focus-badge">{{ todayClasses.length }} CLASSES</span>
              </div>

              <div class="focus-list">
                <div v-if="todayClasses.length === 0" class="empty-focus">
                  No classes scheduled for today.
                </div>
                <div v-for="(cls, idx) in todayClasses" :key="cls.id" 
                     class="focus-item" :class="'border-' + getBorderColor(idx)">
                  <p class="focus-time" :class="'text-' + getBorderColor(idx)">{{ formatTime(cls.startTime) }} - {{ formatTime(cls.endTime) }}</p>
                  <h4 class="focus-subject">{{ cls.subjectName }}</h4>
                  <p class="focus-venue">
                    <span class="material-symbols-outlined">location_on</span>
                    {{ cls.venue || 'Virtual Lab' }}
                  </p>
                  <div class="focus-actions">
                     <button @click.stop="editEntry(cls)" class="mini-btn text-primary">Edit</button>
                     <button @click.stop="deleteEntry(cls.id)" class="mini-btn text-error">Delete</button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Quick Stats / Progress -->
            <div class="study-goal-card">
              <div class="goal-content">
                <h4>Study Goal</h4>
                <div class="goal-stats">
                  <span class="goal-perc">72%</span>
                  <span class="goal-sub">18/25 hrs this week</span>
                </div>
                <div class="progress-track">
                  <div class="progress-fill" style="width: 72%"></div>
                </div>
              </div>
              <div class="goal-glow"></div>
            </div>
          </div>

          <!-- RIGHT COLUMN: Weekly Grid or List View -->
          <div class="grid-right-col">
            
            <div v-if="viewMode === 'list'" class="future-events-section">
              <h2 class="future-section-title">All Timetable Events</h2>
              <div class="future-cards-grid">
                <div v-for="event in filteredTimetable" :key="event.id" class="future-card" @click="editEntry(event)">
                  <div class="card-glass-effect"></div>
                  <div class="future-card-header">
                    <h3 class="future-subject-name">{{ event.subjectName }}</h3>
                    <div class="days-remaining-badge">{{ getDaysRemaining(event.date) }}</div>
                  </div>
                  <div class="future-card-body">
                    <div class="future-detail">
                      <span class="material-symbols-outlined accent-blue">calendar_today</span>
                      <span>{{ formatDateLong(event.date) }}</span>
                    </div>
                    <div class="future-detail">
                      <span class="material-symbols-outlined accent-purple">schedule</span>
                      <span>{{ formatTime(event.startTime) }} - {{ formatTime(event.endTime) }}</span>
                    </div>
                    <div class="future-detail">
                      <span class="material-symbols-outlined accent-pink">location_on</span>
                      <span>{{ event.venue || 'Virtual Lab' }}</span>
                    </div>
                  </div>
                  <button @click.stop="deleteEntry(event.id)" class="del-card-btn">Delete</button>
                </div>
              </div>
            </div>

            <div v-else class="weekly-grid-container table-responsive">
              <table class="grid-table">
                <thead>
                  <tr>
                    <th class="time-col-header"></th>
                    <th v-for="day in weekDays" :key="day" class="day-col-header">{{ day.toUpperCase() }}</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="hour in timeSlots" :key="hour">
                    <td class="time-cell">{{ hour.toString().padStart(2, '0') }}:00</td>
                    <td v-for="day in weekDays" :key="day" class="grid-cell">
                      <div v-for="entry in weeklyGrid[day][hour]" :key="entry.id" 
                           class="grid-entry" @click="editEntry(entry)">
                        <div class="entry-inner">
                          <span class="entry-sub-tag">Class</span>
                          <span class="entry-subject-title">{{ entry.subjectName }}</span>
                        </div>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
          </div>
        </div>
      </main>

      <!-- Floating Action Button -->
      <button @click="toggleForm" class="fab-add-btn group" title="New Entry">
        <span class="material-symbols-outlined group-hover">add</span>
        <span class="fab-tooltip">New Entry</span>
      </button>

      <!-- Modals & Overlays -->
      <div v-if="showForm" class="modal-overlay" @click.self="cancelForm">
        <div class="modal-content crud-form">
          <h3 class="form-title">{{ editingId ? 'Edit Entry' : 'Add New Subject' }}</h3>
          <div class="form-group-grid">
            <div class="form-field">
              <label>Subject Name</label>
              <input type="text" v-model="form.subjectName" placeholder="e.g. Data Science">
            </div>
            <div class="form-field">
              <label>Date</label>
              <input type="date" v-model="form.date">
            </div>
            <div class="form-field">
              <label>Start Time</label>
              <input type="time" v-model="form.startTime">
            </div>
            <div class="form-field">
              <label>End Time</label>
              <input type="time" v-model="form.endTime">
            </div>
            <div class="form-field">
              <label>Venue</label>
              <input type="text" v-model="form.venue" placeholder="e.g. Lab 01">
            </div>
          </div>
          <div class="modal-actions">
            <button @click="cancelForm" class="btn-cancel">Cancel</button>
            <button @click="saveEntry" class="btn-save">{{ editingId ? 'Update Entry' : 'Save Entry' }}</button>
          </div>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import timetableService from '../services/timetableService';
import studentService from '../services/studentService';

const router = useRouter();

const student = ref({ name: 'Julian', studentId: 'ID1042' });

const viewMode = ref('grid');
const timetableData = ref([]);
const searchQuery = ref('');
const isLoading = ref(true);
const studentId = ref(null);

const showForm = ref(false);
const editingId = ref(null);
const form = ref({
  subjectName: '',
  date: '',
  startTime: '',
  endTime: '',
  venue: ''
});

const weekDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
const timeSlots = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18];

const getBorderColor = (idx) => {
    const colors = ['primary', 'tertiary', 'secondary', 'error'];
    return colors[idx % colors.length];
};

const checkAuth = () => {
  const storedId = localStorage.getItem('studentId');
  if (!storedId) {
    router.push('/login');
    return false;
  }
  studentId.value = parseInt(storedId);
  return true;
};

const fetchStudentData = async () => {
    if(!studentId.value) return;
    try {
        const response = await studentService.getStudentById(studentId.value);
        if(response.data) student.value = response.data;
    } catch (e) {
        console.error('Error fetching student data:', e);
    }
};

const fetchData = async () => {
  if (!checkAuth()) return;
  isLoading.value = true;
  try {
    const response = await timetableService.getAllTimetable(studentId.value);
    timetableData.value = response.data || [];
  } catch (error) {
    console.error('Failed to fetch timetable:', error);
  } finally {
    isLoading.value = false;
  }
};

const toggleForm = () => {
  showForm.value = !showForm.value;
  if (!showForm.value) cancelForm();
};

const cancelForm = () => {
  editingId.value = null;
  form.value = { subjectName: '', date: '', startTime: '', endTime: '', venue: '' };
  showForm.value = false;
};

const saveEntry = async () => {
  if (!form.value.subjectName || !form.value.date || !form.value.startTime || !form.value.endTime) {
    alert("Please fill in all required fields.");
    return;
  }

  try {
    const payload = { ...form.value, studentId: studentId.value };
    if (editingId.value) {
      await timetableService.updateEntry(editingId.value, studentId.value, payload);
    } else {
      await timetableService.addEntry(payload);
    }
    cancelForm();
    fetchData();
  } catch (error) {
    alert(error.response?.data || "Operation failed.");
  }
};

const editEntry = (entry) => {
  editingId.value = entry.id;
  form.value = { ...entry };
  showForm.value = true;
};

const deleteEntry = async (id) => {
  if (!confirm("Are you sure you want to delete this schedule?")) return;
  try {
    await timetableService.deleteEntry(id, studentId.value);
    fetchData();
  } catch (error) {
    alert("Delete failed.");
  }
};

const handleLogout = () => {
  localStorage.removeItem('studentId');
  localStorage.removeItem('token');
  router.push('/login');
};

const formatTime = (time) => {
  if (!time) return '';
  return time.length === 5 ? time : time.substring(0, 5);
};

const formatDateLong = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-US', { month: 'long', day: 'numeric' });
};

const getDaysRemaining = (dateStr) => {
  const eventDate = new Date(dateStr);
  eventDate.setHours(0, 0, 0, 0);
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  const diffTime = eventDate - today;
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  
  if (diffDays === 0) return 'Today';
  if (diffDays === 1) return 'Tomorrow';
  if (diffDays < 0) return `${Math.abs(diffDays)} Days Ago`;
  return `In ${diffDays} Days`;
};

const getCurrentWeekRange = () => {
  const now = new Date();
  const day = now.getDay();
  const diffToMonday = (day === 0 ? -6 : 1 - day);
  
  const monday = new Date(now);
  monday.setDate(now.getDate() + diffToMonday);
  monday.setHours(0, 0, 0, 0);
  
  const friday = new Date(monday);
  friday.setDate(monday.getDate() + 4);
  friday.setHours(23, 59, 59, 999);
  
  return { monday, friday };
};

const filteredTimetable = computed(() => {
  if (!searchQuery.value) return timetableData.value;
  return timetableData.value.filter(entry => 
    entry.subjectName.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const todayClasses = computed(() => {
    const todayStr = new Date().toISOString().split('T')[0];
    return filteredTimetable.value.filter(entry => entry.date.startsWith(todayStr))
      .sort((a,b) => {
         const tA = parseInt(a.startTime.split(':')[0]) * 60 + parseInt(a.startTime.split(':')[1]);
         const tB = parseInt(b.startTime.split(':')[0]) * 60 + parseInt(b.startTime.split(':')[1]);
         return tA - tB;
      });
});

const currentWeekEvents = computed(() => {
  const { monday, friday } = getCurrentWeekRange();
  return filteredTimetable.value.filter(entry => {
    const d = new Date(entry.date);
    return d >= monday && d <= friday;
  });
});

const weeklyGrid = computed(() => {
  const grid = {};
  weekDays.forEach(day => {
    grid[day] = {};
    timeSlots.forEach(hour => {
      grid[day][hour] = [];
    });
  });

  currentWeekEvents.value.forEach(entry => {
    const date = new Date(entry.date);
    const dayName = date.toLocaleDateString('en-US', { weekday: 'long' });
    
    if (weekDays.includes(dayName)) {
      const hour = parseInt(entry.startTime.split(':')[0]);
      if (grid[dayName] && grid[dayName][hour]) {
        grid[dayName][hour].push(entry);
      }
    }
  });

  return grid;
});

onMounted(() => {
  if(checkAuth()) {
      fetchStudentData();
      fetchData();
  }
});
</script>

<style scoped>
/* Base Styles */
.material-symbols-outlined {
  font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
}

.dashboard-root {
  font-family: 'Manrope', sans-serif;
  background-color: #fafaf5;
  color: #2e342d;
  min-height: 100vh;
  display: flex;
}

/* Sidebar - Exact match with StudentDashboard */
.sidebar {
  width: 260px;
  background-color: #ecefe7;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  flex-direction: column;
  padding: 32px 0;
  z-index: 50;
  border-right: 1px solid rgba(0,0,0,0.05);
}

.sidebar-header { padding: 0 24px; margin-bottom: 40px; }
.brand-wrapper { display: flex; align-items: center; gap: 12px; }
.brand-logo { width: 32px; height: 32px; object-fit: contain; }
.brand-title { font-size: 18px; font-weight: 800; color: #4e6073; margin: 0; line-height: 1; }
.brand-subtitle { font-size: 10px; font-weight: 600; color: #5a6159; letter-spacing: 0.05em; margin-top: 4px; }

.sidebar-nav { padding: 0 16px; display: flex; flex-direction: column; gap: 8px; }
.nav-link {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px 24px;
  border-radius: 16px;
  text-decoration: none;
  color: #5b6063;
  font-weight: 600;
  transition: all 0.2s ease;
}
.nav-link:hover, .nav-link.router-link-active { background-color: rgba(255, 255, 255, 0.4); }
.nav-link.router-link-exact-active { background-color: #ffffff; color: #4e6073; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }

.sidebar-footer { margin-top: auto; padding: 24px 16px; border-top: 1px solid rgba(0,0,0,0.05); }
.user-mini-card { background-color: #ffffff; padding: 12px 16px; border-radius: 20px; display: flex; align-items: center; gap: 12px; box-shadow: 0 4px 12px rgba(0,0,0,0.03); }
.nav-avatar { width: 40px; height: 40px; border-radius: 50%; object-fit: cover; }
.nav-user-info { flex: 1; min-width: 0; }
.nav-user-name { font-size: 13px; font-weight: 700; margin: 0; color: #2e342d; white-space: nowrap; overflow: hidden; text-overflow: ellipsis; }
.nav-user-id { font-size: 11px; font-weight: 600; margin: 0; color: #5a6159; }
.btn-logout-mini { background: none; border: none; color: #9f403d; cursor: pointer; padding: 8px; border-radius: 12px; display: flex; }
.btn-logout-mini:hover { background-color: rgba(159, 64, 61, 0.05); }

/* Main Wrapper */
.main-wrapper { margin-left: 260px; flex: 1; display: flex; flex-direction: column; }

/* Header */
.app-header {
  padding: 24px 48px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgba(243, 244, 238, 0.82);
  backdrop-filter: blur(20px);
  position: sticky;
  top: 0;
  z-index: 40;
}
.search-bar { position: relative; width: 380px; }
.search-bar .material-symbols-outlined { position: absolute; left: 16px; top: 50%; transform: translateY(-50%); color: #5a6159; font-size: 20px; }
.search-bar input { width: 100%; background-color: rgba(222, 228, 218, 0.5); border: none; padding: 10px 16px 10px 48px; border-radius: 99px; font-family: inherit; font-size: 14px; outline: none; transition: box-shadow 0.2s; }
.search-bar input:focus { box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.2); }

.header-actions { display: flex; align-items: center; gap: 24px; }
.action-btn { background: none; border: none; color: #4e6073; cursor: pointer; position: relative; padding: 8px; border-radius: 50%; transition: background 0.2s; }
.action-btn:hover { background-color: #dee4da; }
.divider { width: 1px; height: 32px; background: rgba(0,0,0,0.1); margin: 0 8px; }
.user-profile { width: 40px; height: 40px; border-radius: 50%; overflow: hidden; border: 2px solid rgba(78, 96, 115, 0.1); }
.user-profile img { width: 100%; height: 100%; object-fit: cover; }

/* Content Canvas */
.content-canvas { padding: 48px; }

.page-title-section { display: flex; align-items: flex-end; justify-content: space-between; margin-bottom: 48px; }
.main-heading { font-size: 48px; font-weight: 800; color: #2e342d; line-height: 1; margin: 0 0 8px 0; letter-spacing: -0.02em; }
.highlight-text { color: #4e6073; }
.sub-heading { color: #5a6159; font-size: 16px; margin: 0; max-width: 400px; }

.view-toggles { display: flex; gap: 8px; background: #e5eae0; padding: 4px; border-radius: 16px; }
.toggle-btn { background: transparent; border: none; padding: 8px 24px; border-radius: 12px; font-weight: 700; color: #5a6159; cursor: pointer; font-size: 14px; transition: 0.2s; }
.toggle-btn:hover { background: rgba(255,255,255,0.5); }
.toggle-btn.active { background: white; color: #4e6073; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }

/* Bento Grid */
.timetable-grid {
  display: grid;
  grid-template-columns: 4fr 8fr;
  gap: 32px;
}

/* Left Column - Today's Focus */
.grid-left-col { display: flex; flex-direction: column; gap: 32px; }

.focus-card {
  background: #f3f4ee;
  border-radius: 20px;
  padding: 32px;
  border: 1px solid rgba(0,0,0,0.05);
}
.focus-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; }
.focus-header h3 { margin: 0; font-size: 20px; font-weight: 700; color: #4e6073; }
.focus-badge { background: rgba(78, 96, 115, 0.1); color: #4e6073; font-size: 10px; font-weight: 800; padding: 4px 12px; border-radius: 99px; letter-spacing: 0.05em; }

.focus-list { display: flex; flex-direction: column; gap: 24px; }
.empty-focus { text-align: center; color: #5a6159; font-size: 14px; font-style: italic; }
.focus-item {
  background: white;
  padding: 24px;
  border-radius: 16px;
  border-left: 4px solid transparent;
  box-shadow: 0 4px 12px rgba(0,0,0,0.02);
  position: relative;
}
.border-primary { border-left-color: #4e6073; }
.border-tertiary { border-left-color: #575e78; }
.border-secondary { border-left-color: #5b6063; }
.border-error { border-left-color: #9f403d; }
.text-primary { color: #4e6073; }
.text-tertiary { color: #575e78; }
.text-secondary { color: #5b6063; }
.text-error { color: #9f403d; }

.focus-time { font-size: 10px; font-weight: 800; letter-spacing: 0.05em; margin: 0 0 4px 0; }
.focus-subject { font-size: 18px; font-weight: 700; color: #2e342d; margin: 0 0 8px 0; }
.focus-venue { display: flex; align-items: center; gap: 4px; font-size: 14px; color: #5a6159; margin: 0 0 16px 0; }
.focus-venue .material-symbols-outlined { font-size: 16px; }

.focus-actions { display: flex; gap: 12px; }
.mini-btn { background: none; border: none; font-size: 12px; font-weight: 700; cursor: pointer; padding: 0; opacity: 0.7; transition: opacity 0.2s; }
.mini-btn:hover { opacity: 1; }

.study-goal-card {
  background: #4e6073;
  color: white;
  padding: 32px;
  border-radius: 20px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 20px 40px rgba(78, 96, 115, 0.2);
}
.goal-content { position: relative; z-index: 10; }
.goal-content h4 { font-size: 20px; font-weight: 700; margin: 0 0 16px 0; }
.goal-stats { display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 8px; }
.goal-perc { font-size: 36px; font-weight: 800; line-height: 1; }
.goal-sub { font-size: 14px; font-weight: 500; opacity: 0.8; }
.progress-track { height: 10px; background: rgba(255,255,255,0.2); border-radius: 99px; }
.progress-fill { height: 100%; background: white; border-radius: 99px; }
.goal-glow { position: absolute; top: -48px; right: -48px; width: 128px; height: 128px; background: rgba(255,255,255,0.1); filter: blur(40px); border-radius: 50%; }

/* Right Column - Table */
.weekly-grid-container {
  background: white;
  border-radius: 20px;
  border: 1px solid rgba(0,0,0,0.05);
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0,0,0,0.02);
}
.grid-table { width: 100%; border-collapse: collapse; min-width: 600px; }
.time-col-header { width: 60px; background: #f3f4ee; border-bottom: 1px solid rgba(0,0,0,0.05); border-right: 1px solid rgba(0,0,0,0.05); }
.day-col-header { padding: 24px; font-size: 12px; font-weight: 800; color: #5a6159; letter-spacing: 0.05em; text-align: center; border-bottom: 1px solid rgba(0,0,0,0.05); border-right: 1px solid rgba(0,0,0,0.05); }
.time-cell { text-align: center; font-size: 12px; font-weight: 700; color: #5a6159; padding: 16px 0; background: #f3f4ee; border-right: 1px solid rgba(0,0,0,0.05); border-bottom: 1px solid rgba(0,0,0,0.05); }
.grid-cell { height: 140px; border-right: 1px solid rgba(0,0,0,0.05); border-bottom: 1px solid rgba(0,0,0,0.05); padding: 8px; vertical-align: top; }

.grid-entry {
  background: rgba(78, 96, 115, 0.1);
  border: 1px solid rgba(78, 96, 115, 0.2);
  border-radius: 12px;
  padding: 16px;
  height: 100%;
  cursor: pointer;
  transition: 0.2s;
  display: flex; flex-direction: column; justify-content: center;
}
.grid-entry:hover { transform: translateY(-2px); background: rgba(78, 96, 115, 0.15); }
.entry-sub-tag { font-size: 10px; font-weight: 800; color: #4e6073; text-transform: uppercase; margin-bottom: 4px; display: block; }
.entry-subject-title { font-size: 13px; font-weight: 800; color: #2e342d; line-height: 1.2; }

/* Future List View */
.future-events-section { animation: fadeIn 0.4s ease-out; }
.future-section-title { font-size: 16px; font-weight: 800; color: #4e6073; margin-bottom: 24px; border-bottom: 1px solid rgba(0,0,0,0.05); padding-bottom: 16px; }
.future-cards-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 16px; }
.future-card { background: white; border: 1px solid rgba(0,0,0,0.05); border-radius: 16px; padding: 24px; transition: 0.3s; cursor: pointer; position: relative; }
.future-card:hover { transform: translateY(-4px); box-shadow: 0 10px 30px rgba(0,0,0,0.05); }
.future-card-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 16px; }
.future-subject-name { font-size: 16px; font-weight: 700; color: #2e342d; margin: 0; }
.days-remaining-badge { background: #f3f4ee; color: #4e6073; font-size: 10px; font-weight: 800; padding: 4px 8px; border-radius: 8px; }
.future-card-body { display: flex; flex-direction: column; gap: 8px; margin-bottom: 16px; }
.future-detail { display: flex; align-items: center; gap: 8px; font-size: 13px; color: #5a6159; }
.future-detail .material-symbols-outlined { font-size: 16px; }
.del-card-btn { background: none; border: none; color: #9f403d; font-size: 12px; font-weight: 700; padding: 0; cursor: pointer; }

/* FAB */
.fab-add-btn { position: fixed; bottom: 40px; right: 40px; width: 64px; height: 64px; border-radius: 50%; background: #4e6073; color: white; display: flex; align-items: center; justify-content: center; border: none; cursor: pointer; box-shadow: 0 20px 40px rgba(78, 96, 115, 0.3); transition: 0.2s; z-index: 100; }
.fab-add-btn:hover { transform: scale(1.1); }
.fab-add-btn .material-symbols-outlined { font-size: 32px; transition: 0.2s; }
.fab-add-btn:hover .material-symbols-outlined { transform: rotate(90deg); }
.fab-tooltip { position: absolute; right: 80px; background: #2e342d; color: white; font-size: 12px; font-weight: 700; padding: 8px 16px; border-radius: 8px; opacity: 0; transition: 0.3s; pointer-events: none; white-space: nowrap; }
.fab-add-btn:hover .fab-tooltip { opacity: 1; }

/* Modals */
.modal-overlay { position: fixed; inset: 0; background: rgba(0,0,0,0.4); backdrop-filter: blur(4px); display: flex; align-items: center; justify-content: center; z-index: 1000; }
.crud-form { background: white; padding: 32px; border-radius: 24px; width: 100%; max-width: 500px; box-shadow: 0 20px 60px rgba(0,0,0,0.2); }
.form-title { margin: 0 0 24px; font-size: 24px; color: #2e342d; }
.form-group-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
.form-field { display: flex; flex-direction: column; gap: 8px; }
.form-field.full { grid-column: 1 / -1; }
.form-field label { font-size: 12px; font-weight: 700; color: #5a6159; }
.form-field input { padding: 12px 16px; border: 1px solid #dee4da; border-radius: 12px; font-family: inherit; font-size: 14px; outline: none; transition: 0.2s; }
.form-field input:focus { border-color: #4e6073; box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.1); }
.modal-actions { display: flex; justify-content: flex-end; gap: 12px; margin-top: 32px; }
.btn-cancel { padding: 12px 24px; border: none; background: #f3f4ee; color: #5a6159; font-weight: 700; border-radius: 12px; cursor: pointer; transition: 0.2s; }
.btn-cancel:hover { background: #dee4da; }
.btn-save { padding: 12px 24px; border: none; background: #4e6073; color: white; font-weight: 700; border-radius: 12px; cursor: pointer; transition: 0.2s; }
.btn-save:hover { background: #3b4958; transform: translateY(-2px); }

.loading-state { text-align: center; padding: 60px; color: #5a6159; font-weight: 600; font-size: 18px; }
.spinner { width: 40px; height: 40px; border: 4px solid rgba(78, 96, 115, 0.1); border-top-color: #4e6073; border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 16px; }
@keyframes spin { to { transform: rotate(360deg); } }

.accent-blue { color: #3b82f6; }
.accent-purple { color: #8b5cf6; }
.accent-pink { color: #ec4899; }

@keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }

@media(max-width: 1024px) {
  .timetable-grid { grid-template-columns: 1fr; }
  .sidebar { transform: translateX(-100%); transition: 0.3s; }
  .main-wrapper { margin-left: 0; }
}
</style>
