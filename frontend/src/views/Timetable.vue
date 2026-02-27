<template>
  <div class="timetable-wrapper">
    
    <!-- Ambient Background Bubbles -->
    <div class="ambient-glows">
      <div class="glow-bubble top-left"></div>
      <div class="glow-bubble bottom-right"></div>
    </div>

    <!-- Sidebar Aside -->
    <aside class="sidebar">
      <div class="sidebar-inner">
        <div class="sidebar-mesh"></div>
        <div class="sidebar-brand">
          <div class="brand-logo-icon">S</div>
          <span class="brand-name">StudentX</span>
        </div>
        
        <nav class="sidebar-nav">
          <router-link to="/dashboard" class="nav-item">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path>
            </svg>
            <span class="nav-text">Overview</span>
          </router-link>

          <router-link to="/timetable" class="nav-item active">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
            </svg>
            <span class="nav-text">Timetable</span>
          </router-link>

          <router-link to="/attendance" class="nav-item">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
            </svg>
            <span class="nav-text">Attendance</span>
          </router-link>
        </nav>

        <div class="sidebar-footer">
          <router-link to="/login" class="logout-link" @click="handleLogout">
            <svg class="icon logout-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
            </svg>
            <span class="nav-text">Logout</span>
          </router-link>
        </div>
      </div>
    </aside>

    <!-- Main Content Area -->
    <div class="main-layout">
      
      <!-- Top Header Sticky -->
      <header class="header">
        <div class="header-left">
           <h1 class="header-title">Timetable</h1>
           <div class="view-toggle">
             <button @click="setViewMode('today')" :class="['toggle-btn', viewMode === 'today' ? 'active' : '']">Today</button>
             <button @click="setViewMode('all')" :class="['toggle-btn', viewMode === 'all' ? 'active' : '']">Full Schedule</button>
           </div>
        </div>

        <div class="header-right">
           <button @click="toggleForm" class="btn-add">
             {{ showForm ? 'Close Form' : '+ New Entry' }}
           </button>
           <div class="header-notif-group">
            <div class="notif-bell-circle">
              <NotificationBell class="icon notif-bell" />
            </div>
           </div>
        </div>
      </header>

      <!-- Main Content Scroll Area -->
      <main class="content-scroll">
        <div class="timetable-container">
          
          <!-- Add/Edit Form Section (Task 2 & 3) -->
          <transition name="fade">
            <div v-if="showForm" class="crud-form-card">
              <div class="form-header">
                <h3 class="form-title">{{ editingId ? 'Edit Entry' : 'Add New Subject' }}</h3>
              </div>
              <div class="form-grid">
                <div class="input-field">
                  <label>Subject Name</label>
                  <input type="text" v-model="form.subjectName" placeholder="e.g. Data Science">
                </div>
                <div class="input-field">
                  <label>Date</label>
                  <input type="date" v-model="form.date">
                </div>
                <div class="input-field">
                  <label>Start Time</label>
                  <input type="time" v-model="form.startTime">
                </div>
                <div class="input-field">
                  <label>End Time</label>
                  <input type="time" v-model="form.endTime">
                </div>
                <div class="input-field">
                  <label>Venue</label>
                  <input type="text" v-model="form.venue" placeholder="e.g. Lab 01">
                </div>
              </div>
              <div class="form-actions">
                <button @click="cancelForm" class="btn-cancel">Cancel</button>
                <button @click="saveEntry" class="btn-save">{{ editingId ? 'Update Entry' : 'Save Entry' }}</button>
              </div>
            </div>
          </transition>

          <!-- Search (Only for Full View) -->
          <div v-if="viewMode === 'all'" class="search-section">
             <div class="search-box">
               <svg class="icon search-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
               </svg>
               <input type="text" v-model="searchQuery" placeholder="Filter subjects..." class="search-input">
             </div>
          </div>

          <!-- Loading Overlay -->
          <div v-if="isLoading" class="loading-overlay">
            <div class="spinner"></div>
            <p class="loading-text">Loading secure data...</p>
          </div>

          <!-- Empty State -->
          <div v-else-if="filteredTimetable.length === 0" class="empty-placeholder">
            <svg class="icon large-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
            </svg>
            <p>Your timetable is empty. Add a new entry to get started.</p>
          </div>

          <!-- Timetable Content -->
          <div v-else class="timetable-content">
            
            <!-- Today's View List -->
            <div v-if="viewMode === 'today'" class="timetable-today-grid">
              <div v-for="entry in filteredTimetable" :key="entry.id" class="entry-card">
                <div class="card-glow"></div>
                <div class="entry-header">
                  <div class="subject-group">
                    <div class="accent-bar"></div>
                    <h3 class="subject-name">{{ entry.subjectName }}</h3>
                  </div>
                  <div class="action-icons">
                    <button @click="editEntry(entry)" class="icon-btn edit">
                      <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
                      </svg>
                    </button>
                    <button @click="deleteEntry(entry.id)" class="icon-btn delete">
                      <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
                      </svg>
                    </button>
                  </div>
                </div>
                <div class="entry-details">
                  <div class="detail-row">
                    <svg class="icon detail-icon accent-indigo" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                    </svg>
                    <span>{{ formatTime(entry.startTime) }} - {{ formatTime(entry.endTime) }}</span>
                  </div>
                  <div class="detail-row">
                    <svg class="icon detail-icon accent-fuchsia" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                    </svg>
                    <span>{{ entry.venue || 'Virtual Lab' }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Full Grouped View -->
            <div v-else class="timetable-full-list">
              <div v-for="(group, date) in groupedTimetable" :key="date" class="date-group">
                <div class="date-header">
                   <div class="date-badge">{{ formatDate(date) }}</div>
                   <div class="date-line"></div>
                </div>
                <div class="entries-mini-grid">
                  <div v-for="entry in group" :key="entry.id" class="mini-card" @click="editEntry(entry)">
                    <div class="mini-card-header">
                      <h4 class="mini-subject">{{ entry.subjectName }}</h4>
                      <button @click.stop="deleteEntry(entry.id)" class="mini-delete">×</button>
                    </div>
                    <div class="mini-info">
                      <p><svg class="icon mini-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg> {{ formatTime(entry.startTime) }}</p>
                      <p><svg class="icon mini-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path></svg> {{ entry.venue }}</p>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>
      </main>
      
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import timetableService from '../services/timetableService';
import NotificationBell from '../components/NotificationBell.vue';

const router = useRouter();

// Task 2: Data State
const viewMode = ref('today');
const timetableData = ref([]);
const searchQuery = ref('');
const isLoading = ref(true);
const studentId = ref(null);

// Form Control
const showForm = ref(false);
const editingId = ref(null);
const form = ref({
  subjectName: '',
  date: '',
  startTime: '',
  endTime: '',
  venue: ''
});

// Privacy Redirect (Task 2)
const checkAuth = () => {
  const storedId = localStorage.getItem('studentId');
  if (!storedId) {
    router.push('/login');
    return false;
  }
  studentId.value = parseInt(storedId);
  return true;
};

const fetchData = async () => {
  if (!checkAuth()) return;
  isLoading.value = true;
  try {
    let response;
    if (viewMode.value === 'today') {
      response = await timetableService.getTodayTimetable(studentId.value);
    } else {
      response = await timetableService.getAllTimetable(studentId.value);
    }
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
  router.push('/login');
};

const setViewMode = (mode) => {
  viewMode.value = mode;
  fetchData();
};

// Formatting
const formatTime = (time) => {
  if (!time) return '';
  // Handling both LocalTime (08:30:00) and HH:mm
  const timeStr = time.length === 5 ? time : time.substring(0, 5);
  return timeStr;
};

const formatDate = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric' });
};

const filteredTimetable = computed(() => {
  if (!searchQuery.value) return timetableData.value;
  return timetableData.value.filter(entry => 
    entry.subjectName.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const groupedTimetable = computed(() => {
  if (viewMode.value !== 'all') return {};
  const groups = {};
  filteredTimetable.value.forEach(entry => {
    const date = entry.date;
    if (!groups[date]) groups[date] = [];
    groups[date].push(entry);
  });
  return groups;
});

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
/* Base Layout & Theme */
.timetable-wrapper {
  display: flex;
  height: 100vh;
  background-color: #050511;
  color: #cadbee;
  font-family: 'Inter', system-ui, sans-serif;
  overflow: hidden;
  position: relative;
}

/* Strict Icons Size Task 3 */
.icon {
  width: 20px !important;
  height: 20px !important;
  min-width: 20px !important;
  min-height: 20px !important;
}

/* Sidebar & Header (Clean CSS) */
.sidebar { width: 260px; padding: 12px; z-index: 20; }
.sidebar-inner {
  height: 100%;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  padding: 12px;
}
.sidebar-nav { flex: 1; display: flex; flex-direction: column; gap: 4px; }
.nav-item { display: flex; align-items: center; padding: 10px 14px; border-radius: 12px; color: #94a3b8; text-decoration: none; font-size: 13px; font-weight: 600; }
.nav-item.active { background: rgba(255, 255, 255, 0.05); color: white; }

.main-layout { flex: 1; display: flex; flex-direction: column; padding: 12px 12px 12px 0; }
.header { height: 56px; display: flex; align-items: center; justify-content: space-between; padding: 0 12px; margin-bottom: 12px; }
.header-title { font-size: 16px; font-weight: 800; color: white; }

.view-toggle { display: flex; background: rgba(255, 255, 255, 0.04); border-radius: 999px; padding: 3px; border: 1px solid rgba(255, 255, 255, 0.08); }
.toggle-btn { background: transparent; border: none; color: #94a3b8; font-size: 10px; font-weight: 700; padding: 6px 18px; border-radius: 999px; cursor: pointer; transition: 0.3s; }
.toggle-btn.active { background: #6366f1; color: white; box-shadow: 0 0 15px rgba(99, 102, 241, 0.5); }

.btn-add { background: #6366f1; color: white; border: none; border-radius: 999px; padding: 8px 18px; font-size: 11px; font-weight: 800; cursor: pointer; transition: 0.2s; box-shadow: 0 4px 15px rgba(99, 102, 241, 0.3); }
.btn-add:hover { background: #4f46e5; transform: translateY(-1px); }

/* CRUD Form Card (Task 2 & 3) */
.crud-form-card {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  padding: 24px;
  margin-bottom: 24px;
  box-shadow: 0 10px 40px rgba(0,0,0,0.5);
}
.form-header { margin-bottom: 20px; }
.form-title { font-size: 15px; font-weight: 900; color: white; }

.form-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 16px;
  margin-bottom: 24px;
}
.input-field { display: flex; flex-direction: column; gap: 8px; }
.input-field label { font-size: 10px; font-weight: 800; color: #64748b; text-transform: uppercase; }
.input-field input { background: rgba(255, 255, 255, 0.03); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 12px; padding: 12px; color: white; font-size: 13px; outline: none; }
.input-field input:focus { border-color: #6366f1; background: rgba(255, 255, 255, 0.05); }

.form-actions { display: flex; justify-content: flex-end; gap: 12px; }
.btn-save { background: #6366f1; color: white; border: none; padding: 10px 24px; border-radius: 12px; font-weight: 800; font-size: 13px; cursor: pointer; }
.btn-cancel { background: transparent; color: #64748b; border: 1px solid rgba(255,255,255,0.05); padding: 10px 24px; border-radius: 12px; font-weight: 700; font-size: 13px; cursor: pointer; }

/* Content List Styles */
.content-scroll { flex: 1; overflow-y: auto; padding-right: 8px; }
.search-section { margin-bottom: 24px; }
.search-box { position: relative; width: 300px; }
.search-icon { position: absolute; left: 12px; top: 50%; transform: translateY(-50%); color: #475569; }
.search-input { width: 100%; background: rgba(255, 255, 255, 0.04); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 999px; padding: 10px 16px 10px 40px; color: white; font-size: 12px; outline: none; }

.timetable-today-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(300px, 1fr)); gap: 16px; }
.entry-card { background: rgba(255, 255, 255, 0.03); backdrop-filter: blur(15px); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 20px; padding: 24px; position: relative; overflow: hidden; }
.entry-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 20px; }
.action-icons { display: flex; gap: 8px; }
.icon-btn { background: rgba(255,255,255,0.03); border: none; color: #64748b; padding: 6px; border-radius: 8px; cursor: pointer; transition: 0.2s; }
.icon-btn.edit:hover { color: #6366f1; background: rgba(99,102,241,0.1); }
.icon-btn.delete:hover { color: #f87171; background: rgba(248,113,113,0.1); }

.subject-name { font-size: 15px; font-weight: 800; color: white; margin: 0; }
.accent-bar { width: 3px; height: 18px; background: #6366f1; border-radius: 99px; }
.subject-group { display: flex; align-items: center; gap: 12px; }

.entry-details { display: flex; flex-direction: column; gap: 12px; }
.detail-row { display: flex; align-items: center; gap: 10px; font-size: 12px; color: #94a3b8; }
.accent-indigo { color: #6366f1; }
.accent-fuchsia { color: #d946ef; }

/* Sidebar Footer & Others */
.sidebar-footer { border-top: 1px solid rgba(255,255,255,0.05); margin-top: auto; padding-top: 12px; }
.logout-link { display: flex; align-items: center; gap: 12px; padding: 10px 14px; text-decoration: none; color: #64748b; font-size: 13px; font-weight: 600; }
.logout-link:hover { color: #f87171; }

.glow-bubble { position: absolute; border-radius: 50%; filter: blur(120px); opacity: 0.05; pointer-events: none; }
.top-left { width: 40vw; height: 40vw; top: -10%; left: -10%; background: #6366f1; }
.bottom-right { width: 30vw; height: 30vw; bottom: -10%; right: -5%; background: #d946ef; }

.loading-overlay { display: flex; flex-direction: column; align-items: center; justify-content: center; padding: 60px; }
.spinner { width: 32px; height: 32px; border: 3px solid rgba(99, 102, 241, 0.2); border-top-color: #6366f1; border-radius: 50%; animation: spin 0.8s linear infinite; margin-bottom: 16px; }
@keyframes spin { to { transform: rotate(360deg); } }

.fade-enter-active, .fade-leave-active { transition: opacity 0.3s, transform 0.3s; }
.fade-enter-from, .fade-leave-to { opacity: 0; transform: translateY(-10px); }

@media (max-width: 768px) {
  .sidebar { position: fixed; left: -260px; }
  .main-layout { padding: 12px; }
}
</style>
