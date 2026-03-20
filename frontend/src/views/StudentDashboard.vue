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

    <!-- Main Content -->
    <div class="main-wrapper">
      <header class="app-header">
        <div class="search-bar">
          <span class="material-symbols-outlined">search</span>
          <input type="text" v-model="searchQuery" @input="handleSearch" placeholder="Search resources, notes..." />
        </div>
        
        <div class="header-actions">
          <button @click="isGpaModalOpen = true" class="btn-gpa-tracker">GPA Tracker</button>
          <div class="notif-wrapper">
            <button class="action-btn" @click="isNotifDropdownOpen = !isNotifDropdownOpen">
              <span class="material-symbols-outlined">notifications</span>
              <span class="notification-badge" v-if="notificationsList.length > 0">{{ notificationsList.length }}</span>
            </button>
            <div v-if="isNotifDropdownOpen" class="notif-dropdown">
              <div class="dropdown-header">
                <h4>Notifications</h4>
                <button @click="clearAllNotifications" class="btn-clear-all">Clear All</button>
              </div>
              <div class="notif-list">
                <div v-for="notif in notificationsList" :key="notif.id" class="notif-item">
                  <p class="notif-msg">{{ notif.message }}</p>
                  <span class="notif-time">{{ formatTime(notif.createdAt) }}</span>
                </div>
                <div v-if="notificationsList.length === 0" class="empty-notif">No new notifications</div>
              </div>
            </div>
          </div>
          <button class="action-btn">
            <span class="material-symbols-outlined">settings</span>
          </button>
          <div class="divider"></div>
          <router-link to="/profile" class="user-profile">
            <img :src="student.profilePic || 'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'" alt="Profile" />
          </router-link>
        </div>
      </header>

      <main class="content-canvas">
        <!-- Notification Alert Bar (Urgent Deadlines) -->
        <div v-if="upcomingAlerts.length > 0" class="alerts-container">
          <div v-for="alert in upcomingAlerts" :key="alert.id" class="alert-bar" :class="alert.countdown?.urgency">
            <div class="alert-left">
              <div class="status-dot"></div>
              <span class="material-symbols-outlined alert-icon">warning</span>
              <div class="alert-text">
                <span class="alert-tag">URGENT</span>
                <span class="alert-msg"><strong>{{ alert.subjectName }}</strong>: {{ alert.title }} due in <strong>{{ alert.countdown?.text }}</strong></span>
              </div>
            </div>
            <button @click="dismissAlert(alert.id)" class="close-alert">
              <span class="material-symbols-outlined">close</span>
            </button>
          </div>
        </div>

        <!-- Hero Section -->
        <section class="hero-card">
          <div class="hero-content">
            <span class="session-tag">ACADEMIC SESSION 2024</span>
            <h2 class="hero-title">Welcome back, {{ student.name.split(' ')[0] }}</h2>
            <p class="hero-description">You've completed 75% of your weekly targets. Great momentum for your upcoming {{ nextExamSubject }} exam.</p>
            <button class="btn-primary-action" @click="resumeFocus">
              <span class="material-symbols-outlined">play_circle</span>
              <span>Resume {{ nextExamSubject }}</span>
            </button>
          </div>
          <div class="hero-illustration">
             <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuCdJ40bkENiJQ8dw-OvLLOvXUVRF-vHcA1sreB7YPPL8de5n5LGSXdNn0bxp4Mr_1cw4Gbc_RAGwsKm91xmC_G7248GeYL44Nu_z-9uZLh3LkbkQP7KGXqayIKwVh1C9kLinjdVE_JwkRhfCc7COu-iEpBQGGkusOpXb6Ys_QKQHzoTo9Q4tG3MDOqnJy6PwFES98IlS9G0gbgTSwvO_iL7QtTFRcYsLPCcXrj1Hd3LJOn9w90eVtmp8XQ658lW-qb153VSsGz0AHI" alt="Illustration" />
          </div>
        </section>

        <!-- Smart Suggestion Tip -->
        <div v-if="smartSuggestion" class="smart-tip-box">
          <div class="tip-icon">
             <span class="material-symbols-outlined">psychology</span>
          </div>
          <div class="tip-content">
            <h4>Smart Tip</h4>
            <p>{{ smartSuggestion.message }}</p>
          </div>
        </div>

        <div class="bento-grid">
          <!-- Left Column -->
          <div class="grid-col-8">
            <!-- Today's Classes -->
            <section class="content-card">
              <div class="card-header-row">
                <div>
                  <h3 class="card-title">Today's Classes</h3>
                  <p class="card-subtitle">{{ formattedDate }}</p>
                </div>
                <router-link to="/timetable" class="view-all">View Calendar</router-link>
              </div>
              
              <div class="class-list">
                <div v-for="cls in filteredClasses" :key="cls.id" class="class-item">
                  <div class="class-info">
                    <div class="class-icon-box" :class="getClassStatus(cls)">
                       <span class="material-symbols-outlined">{{ cls.subjectName.toLowerCase().includes('math') ? 'functions' : 'biotech' }}</span>
                    </div>
                    <div>
                      <div class="class-name-row">
                        <h4 class="class-name">{{ cls.subjectName }}</h4>
                        <span v-if="getClassStatus(cls) === 'ongoing'" class="live-tag">LIVE NOW</span>
                      </div>
                      <div class="class-details">
                        <span class="detail-item">
                          <span class="material-symbols-outlined">schedule</span>
                          {{ formatTime(cls.startTime) }} - {{ formatTime(cls.endTime) }}
                        </span>
                        <span class="detail-item">
                          <span class="material-symbols-outlined">location_on</span>
                          {{ cls.venue || 'No Venue' }}
                        </span>
                      </div>
                    </div>
                  </div>
                  <button v-if="getClassStatus(cls) === 'ongoing' && !cls.marked" @click="markAsPresent(cls.subjectName, cls.id)" class="btn-attendance">Mark Attendance</button>
                  <span v-else-if="cls.marked" class="marked-status">Checked-in ✅</span>
                  <span v-else-if="getClassStatus(cls) === 'missed'" class="missed-status">Missed ❌</span>
                  <span v-else class="upcoming-status">Scheduled</span>
                </div>
                <div v-if="filteredClasses.length === 0" class="empty-state">No classes found matching your search.</div>
              </div>
            </section>

            <!-- Smart Planner -->
            <section class="content-card">
              <h3 class="card-title">Smart Planner</h3>
              <div class="planner-timeline">
                <div v-for="(gap, idx) in freeGaps" :key="idx" class="timeline-item">
                  <div class="timeline-dot"></div>
                  <div class="timeline-content">
                    <div class="timeline-row">
                      <div>
                         <p class="timeline-time">{{ formatTime12h(gap.startTime) }} — {{ formatTime12h(gap.endTime) }}</p>
                         <h4 class="timeline-title">Study Gap ({{ calculateGapDuration(gap) }})</h4>
                         <p class="timeline-desc">Optimal time for Research Project drafting based on your focus peaks.</p>
                      </div>
                      <button class="btn-edit-cal">
                        <span class="material-symbols-outlined">edit_calendar</span>
                      </button>
                    </div>
                  </div>
                </div>
                <div v-if="freeGaps.length === 0" class="empty-state">No study gaps found for today.</div>
              </div>
            </section>
          </div>

          <!-- Right Column -->
          <div class="grid-col-4">
            <!-- Academic Standing -->
            <section class="content-card centered">
              <h3 class="card-title">Academic Standing</h3>
              <div class="gpa-gauge-container">
                <svg viewBox="0 0 100 100" class="gpa-svg">
                  <circle class="gpa-bg" cx="50" cy="50" r="42"></circle>
                  <circle class="gpa-progress" cx="50" cy="50" r="42" :stroke-dashoffset="gpaStrokeOffset" stroke-dasharray="264" stroke-linecap="round"></circle>
                </svg>
                <div class="gpa-inner-text">
                  <span class="gpa-val">{{ currentGpa }}</span>
                  <span class="gpa-sub">GPA</span>
                </div>
              </div>
              <div class="stats-mini-grid">
                <div class="stat-mini">
                  <span class="stat-label">Credits</span>
                  <span class="stat-value">124/160</span>
                </div>
                <div class="stat-mini">
                  <span class="stat-label">Rank</span>
                  <span class="stat-value">Top 5%</span>
                </div>
              </div>
              <p class="gpa-hint">0.28 points to Dean’s List</p>
            </section>

            <!-- Attendance Tracker Card -->
            <section class="content-card">
              <h3 class="card-title">Attendance Tracker</h3>
              <p class="card-subtitle">Minimum 80% required.</p>
              
              <div class="attendance-stack">
                <div v-for="item in attendanceSummary" :key="item.subjectName" class="attendance-item">
                  <div class="attendance-info">
                    <span class="subj-name">{{ item.subjectName }}</span>
                    <div class="attendance-stats-text">
                      <span class="session-count">{{ item.attendedSessions || 0 }}/{{ item.totalSessions || 0 }} Sessions</span>
                      <span class="perc-val" :class="{ 'warning': item.percentage < 80 }">{{ Math.round(item.percentage) }}%</span>
                    </div>
                  </div>
                  <div class="mini-progress">
                    <div class="mini-bar" :style="{ width: item.percentage + '%' }" :class="{ 'low': item.percentage < 80 }"></div>
                  </div>
                </div>
                <div v-if="attendanceSummary.length === 0" class="empty-state">No attendance data yet.</div>
              </div>
            </section>

            <!-- Upcoming Deadlines -->
            <section class="upcoming-deadlines">
              <div class="deadlines-header">
                <h3 class="card-title">Upcoming</h3>
                <div class="header-right-meta">
                  <span class="badge-critical">{{ assignments.length }} Tasks</span>
                  <button @click="showDeadlineForm = true" class="btn-add-mini">
                    <span class="material-symbols-outlined">add</span>
                  </button>
                </div>
              </div>
              
              <div class="deadline-stack">
                <div v-for="item in assignments.slice(0, 3)" :key="item.id" class="deadline-card">
                  <div class="deadline-card-top">
                    <div>
                      <span class="subject-tag" :class="item.subjectName.toLowerCase()">{{ item.subjectName }}</span>
                      <h4 class="deadline-name">{{ item.title }}</h4>
                    </div>
                    <span class="deadline-urgency" :class="item.countdown?.urgency">{{ item.countdown?.text }}</span>
                  </div>
                  <div class="progress-container">
                    <div class="progress-track">
                      <div class="progress-bar-fill" :style="{ width: item.status === 'Completed' ? '100%' : '20%' }"></div>
                    </div>
                  </div>
                  <div class="deadline-card-footer">
                    <span class="perc-text">{{ item.status === 'Completed' ? '100% Complete' : 'In Progress' }}</span>
                    <span class="material-symbols-outlined arrow-icon" :class="item.countdown?.urgency">timer</span>
                  </div>
                </div>
              </div>
            </section>
          </div>
        </div>
      </main>
    </div>

    <!-- Modals & Overlays -->
    <GPAModal :isOpen="isGpaModalOpen" @close="isGpaModalOpen = false" />
    
    <div v-if="showDeadlineForm" class="modal-overlay" @click.self="showDeadlineForm = false">
      <div class="modal-content deadline-modal">
        <h3>Add New Deadline</h3>
        <div class="form-group">
          <input type="text" v-model="deadlineForm.subjectName" placeholder="Subject (e.g. IT2030)">
          <input type="text" v-model="deadlineForm.title" placeholder="Assignment Title">
          <input type="date" v-model="deadlineForm.date">
          <input type="time" v-model="deadlineForm.time">
        </div>
        <div class="modal-actions">
          <button @click="showDeadlineForm = false" class="btn-cancel">Cancel</button>
          <button @click="handleAddDeadline" class="btn-save">Save Task</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import timetableService from '../services/timetableService';
import attendanceService from '../services/attendanceService';
import assignmentService from '../services/assignmentService';
import notificationService from '../services/notificationService';
import studentService from '../services/studentService';
import GPAModal from '../components/GPAModal.vue';

const router = useRouter();

const isGpaModalOpen = ref(false);
const showDeadlineForm = ref(false);
const deadlineForm = ref({
    subjectName: '',
    title: '',
    date: '',
    time: ''
});

const searchQuery = ref('');
const student = ref({ name: 'Julian', studentId: 'ID1042' });
const todayClasses = ref([]);
const attendanceSummary = ref([]);
const assignments = ref([]);
const notificationsList = ref([]);
const isNotifDropdownOpen = ref(false);
const upcomingAlerts = ref([]);
const notifiedIds = new Set();
const freeGaps = ref([]);
const smartSuggestion = ref(null);
const currentGpa = ref(3.72);
const nextExamSubject = ref('Advanced Physics');

const formattedDate = computed(() => {
    return new Date().toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
});

const gpaStrokeOffset = computed(() => {
    const total = 264;
    const progress = (currentGpa.value / 4.0) * total;
    return total - progress;
});

const filteredClasses = computed(() => {
    if (!searchQuery.value) return todayClasses.value;
    const q = searchQuery.value.toLowerCase();
    return todayClasses.value.filter(c => 
        c.subjectName.toLowerCase().includes(q)
    );
});

const checkAuth = () => {
    const studentId = localStorage.getItem('studentId');
    if (!studentId) {
        router.push('/login');
        return null;
    }
    return studentId;
};

const fetchStudentData = async (studentId) => {
    try {
        const response = await studentService.getStudentById(studentId);
        if (response.data) student.value = response.data;
    } catch (e) {
        console.error('Error fetching student data:', e);
    }
};

const fetchTodayClasses = async (studentId) => {
    try {
        const response = await timetableService.getTodayTimetable(studentId);
        todayClasses.value = response.data || [];
        updateSmartPlanner();
    } catch (error) {
        console.error('Error fetching today classes:', error);
    }
};

const updateSmartPlanner = () => {
    freeGaps.value = calculateFreeSlots();
};

const calculateFreeSlots = () => {
    const dayStart = 8 * 60; 
    const dayEnd = 18 * 60; 
    const sortedClasses = [...todayClasses.value].sort((a, b) => timeToMinutes(a.startTime) - timeToMinutes(b.startTime));
    const gaps = [];
    let lastEnd = dayStart;

    sortedClasses.forEach(cls => {
        const start = timeToMinutes(cls.startTime);
        const end = timeToMinutes(cls.endTime);
        if (start - lastEnd >= 60) {
            gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(start) });
        }
        lastEnd = Math.max(lastEnd, end);
    });

    if (dayEnd - lastEnd >= 60) {
        gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(dayEnd) });
    }
    return gaps;
};

const calculateGapDuration = (gap) => {
    const start = timeToMinutes(gap.startTime);
    const end = timeToMinutes(gap.endTime);
    const diff = end - start;
    const hours = Math.floor(diff / 60);
    const mins = diff % 60;
    return mins === 0 ? `${hours} Hours` : `${hours}h ${mins}m`;
};

const timeToMinutes = (timeStr) => {
    if (!timeStr) return 0;
    const [h, m] = timeStr.split(':').map(Number);
    return h * 60 + m;
};

const minutesToTime = (mins) => {
    const h = Math.floor(mins / 60);
    const m = mins % 60;
    return `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;
};

const formatTime = (timeStr) => timeStr ? timeStr.substring(0, 5) : '';

const formatTime12h = (timeStr) => {
    if (!timeStr) return '';
    const [h, m] = timeStr.split(':').map(Number);
    const ampm = h >= 12 ? 'PM' : 'AM';
    const hours = h % 12 || 12;
    return `${hours}:${m.toString().padStart(2, '0')} ${ampm}`;
};

const fetchAssignments = async (studentId) => {
    try {
        const response = await assignmentService.getAssignments(studentId);
        assignments.value = (response.data || []).map(a => ({
            ...a,
            countdown: calculateCountdown(a.deadline)
        })).filter(a => !a.countdown?.expired);
        syncAlerts();
        updateSmartSuggestion();
    } catch (error) {
        console.error('Error fetching assignments:', error);
    }
};

const syncAlerts = () => {
    upcomingAlerts.value = assignments.value
        .filter(a => a.countdown?.urgency === 'critical' || a.countdown?.urgency === 'warning' || a.countdown?.urgency === 'high')
        .map(a => ({ ...a }));
};

const checkClassNotifications = () => {
    const now = new Date();
    const currentMins = now.getHours() * 60 + now.getMinutes();
    
    todayClasses.value.forEach(cls => {
        const start = timeToMinutes(cls.startTime);
        const end = timeToMinutes(cls.endTime);
        
        // 1. Alert when class starts
        if (currentMins === start && !notifiedIds.has(`start_${cls.id}`)) {
            notificationsList.value.unshift({
                id: Date.now(),
                message: `Class Starting: Your ${cls.subjectName} lecture has just begun!`,
                createdAt: new Date().toISOString(),
                isRead: false
            });
            notifiedIds.add(`start_${cls.id}`);
        }
        
        // 2. Alert if class was missed (ended without mark)
        if (currentMins > end && !cls.marked && !notifiedIds.has(`missed_${cls.id}`)) {
            notificationsList.value.unshift({
                id: Date.now(),
                message: `Missed Lecture: You missed the ${cls.subjectName} lecture today.`,
                createdAt: new Date().toISOString(),
                isRead: false
            });
            notifiedIds.add(`missed_${cls.id}`);
        }
    });
};

const updateSmartSuggestion = () => {
    const nextTask = assignments.value.find(a => a.countdown && !a.countdown.expired);
    if (nextTask) {
        smartSuggestion.value = {
            message: `Priority Focus: Your "${nextTask.title}" assignment is due in ${nextTask.countdown?.text}. Reserve your 2:00 PM study gap for this.`
        };
        nextExamSubject.value = nextTask.subjectName;
    } else {
        smartSuggestion.value = { message: "All caught up! Use your free gaps to review previous lectures or start pre-reading." };
    }
};

const calculateCountdown = (deadlineStr) => {
    if (!deadlineStr) return null;
    const deadline = new Date(deadlineStr);
    const now = new Date();
    const diff = deadline - now;
    if (diff <= 0) return { expired: true, text: 'OVERDUE', urgency: 'critical' };
    const hours = Math.floor(diff / (1000 * 60 * 60));
    const days = Math.floor(hours / 24);
    if (hours < 24) return { text: `${hours}h left`, urgency: 'critical' };
    if (days === 1) return { text: 'Tomorrow', urgency: 'warning' };
    if (days < 3) return { text: `${days} days left`, urgency: 'low' };
    return { text: `${days} days left`, urgency: 'low' };
};

const dismissAlert = (alertId) => {
    upcomingAlerts.value = upcomingAlerts.value.filter(a => a.id !== alertId);
};

const getClassStatus = (cls) => {
  const now = new Date();
  const startTime = timeToMinutes(cls.startTime);
  const endTime = timeToMinutes(cls.endTime);
  const currentMins = now.getHours() * 60 + now.getMinutes();
  if (currentMins < startTime) return 'upcoming';
  if (currentMins >= startTime && currentMins <= endTime) return 'ongoing';
  return 'missed';
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    localStorage.removeItem('token');
    router.push('/login');
};

const fetchAttendanceSummary = async (studentId) => {
    try {
        const response = await attendanceService.getAttendanceSummary(studentId);
        attendanceSummary.value = response.data || [];
    } catch (error) {
        console.error('Error fetching attendance summary:', error);
    }
};

const handleAddDeadline = async () => {
    const studentId = checkAuth();
    if (!studentId) return;
    if (!deadlineForm.value.subjectName || !deadlineForm.value.title || !deadlineForm.value.date || !deadlineForm.value.time) {
        alert('Please fill all fields');
        return;
    }
    const combinedDeadline = `${deadlineForm.value.date}T${deadlineForm.value.time}:00`;
    try {
        await assignmentService.addAssignment({
            subjectName: deadlineForm.value.subjectName,
            title: deadlineForm.value.title,
            deadline: combinedDeadline,
            studentId,
            status: 'Pending'
        });
        showDeadlineForm.value = false;
        deadlineForm.value = { subjectName: '', title: '', date: '', time: '' };
        fetchAssignments(studentId);
    } catch (error) { alert('Failed to add deadline'); }
};

const markAsPresent = async (subjectName, timetableId) => {
    const studentId = checkAuth();
    if (!studentId) return;
    try {
        await attendanceService.markAttendance(studentId, subjectName, true, timetableId);
        alert(`Attendance marked for ${subjectName}`);
        fetchTodayClasses(studentId); // Re-fetch classes to update the UI
        fetchAttendanceSummary(studentId);
    } catch (error) { alert('Failed to mark attendance.'); }
};

const clearAllNotifications = () => {
    notificationsList.value = [];
    isNotifDropdownOpen.value = false;
};

const handleSearch = () => {
    // Computed property handles the filtering
};

const resumeFocus = () => {
    alert(`Starting focus session for ${nextExamSubject.value}...`);
};

let countdownInterval;

onMounted(async () => {
    const studentId = checkAuth();
    if (studentId) {
        fetchStudentData(studentId);
        fetchTodayClasses(studentId);
        fetchAssignments(studentId);
        fetchAttendanceSummary(studentId);
        countdownInterval = setInterval(() => {
            assignments.value = assignments.value.map(a => ({
                ...a,
                countdown: calculateCountdown(a.deadline)
            })).filter(a => !a.countdown?.expired);
            syncAlerts();
            updateSmartSuggestion();
            checkClassNotifications();
        }, 60000);
    }
});

onUnmounted(() => { if (countdownInterval) clearInterval(countdownInterval); });
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

/* Sidebar */
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
.search-bar input { width: 100%; background-color: rgba(222, 228, 218, 0.5); border: none; padding: 10px 16px 10px 48px; border-radius: 99px; font-family: inherit; font-size: 14px; }

.header-actions { display: flex; align-items: center; gap: 24px; }
.btn-gpa-tracker { background-color: #4e6073; color: white; border: none; padding: 8px 16px; border-radius: 99px; font-weight: 700; cursor: pointer; font-size: 13px; }
.btn-gpa-tracker:hover { background-color: #3b4958; }
.action-btn { background: none; border: none; color: #4e6073; cursor: pointer; position: relative; padding: 8px; border-radius: 50%; transition: background 0.2s; }
.action-btn:hover { background-color: #dee4da; }
.notification-badge { position: absolute; top: -4px; right: -4px; background-color: #9f403d; color: white; font-size: 10px; font-weight: 800; min-width: 18px; height: 18px; border-radius: 50%; display: flex; align-items: center; justify-content: center; box-shadow: 0 2px 4px rgba(159, 64, 61, 0.3); }

.notif-wrapper { position: relative; }
.notif-dropdown { position: absolute; top: calc(100% + 12px); right: 0; width: 320px; background-color: #ffffff; border-radius: 20px; box-shadow: 0 20px 40px rgba(0,0,0,0.1); padding: 24px; z-index: 100; border: 1px solid rgba(0,0,0,0.05); }
.dropdown-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.dropdown-header h4 { margin: 0; color: #4e6073; }
.btn-clear-all { background: none; border: none; font-size: 12px; color: #9f403d; font-weight: 700; cursor: pointer; }
.notif-list { display: flex; flex-direction: column; gap: 16px; max-height: 300px; overflow-y: auto; }
.notif-item { border-bottom: 1px solid #f3f4ee; padding-bottom: 12px; }
.notif-item:last-child { border: none; }
.notif-msg { font-size: 14px; margin: 0 0 4px; line-height: 1.4; color: #2e342d; }
.notif-time { font-size: 11px; color: #5a6159; font-weight: 600; }
.empty-notif { text-align: center; color: #5a6159; padding: 20px; font-style: italic; font-size: 14px; }

.divider { width: 1px; height: 32px; background: rgba(0,0,0,0.1); margin: 0 8px; }
.user-profile { width: 40px; height: 40px; border-radius: 50%; overflow: hidden; border: 2px solid rgba(78, 96, 115, 0.1); display: block; cursor: pointer; transition: 0.2s; }
.user-profile:hover { border-color: #4e6073; transform: scale(1.05); }
.user-profile img { width: 100%; height: 100%; object-fit: cover; }

/* Content */
.content-canvas { padding: 32px 48px; }

/* Alerts */
.alerts-container { display: flex; flex-direction: column; gap: 12px; margin-bottom: 32px; }
.alert-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 18px 28px;
  border-radius: 20px;
  font-size: 14px;
  backdrop-filter: blur(10px);
  box-shadow: 0 10px 25px rgba(0,0,0,0.04);
}

.alert-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.alert-bar.critical {
  background-color: #fff1f2;
  color: #9f1239;
  border: 1px solid rgba(159, 18, 57, 0.1);
  border-left: 6px solid #e11d48;
}

.alert-bar.warning {
  background-color: #fff7ed;
  color: #9a3412;
  border: 1px solid rgba(154, 52, 18, 0.1);
  border-left: 6px solid #f97316;
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: currentColor;
}

.close-alert { background: none; border: none; color: inherit; cursor: pointer; opacity: 0.6; }
.close-alert:hover { opacity: 1; }

.hero-card {
  background: linear-gradient(135deg, #4e6073, #3b4958);
  border-radius: 24px;
  padding: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 32px;
  color: white;
  box-shadow: 0 20px 40px rgba(78, 96, 115, 0.2);
}

.session-tag { font-size: 11px; font-weight: 800; letter-spacing: 0.05em; opacity: 0.8; }
.hero-title { font-size: 32px; font-weight: 800; margin: 8px 0; }
.hero-description { font-size: 15px; opacity: 0.9; line-height: 1.5; max-width: 400px; margin-bottom: 24px; }

.btn-primary-action {
  background-color: white;
  color: #4e6073;
  border: none;
  padding: 12px 24px;
  border-radius: 99px;
  font-weight: 700;
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  transition: transform 0.2s;
}
.btn-primary-action:hover { transform: translateY(-2px); box-shadow: 0 10px 20px rgba(0,0,0,0.1); }
.hero-illustration img { width: 180px; }

.smart-tip-box { display: flex; background: white; border-radius: 16px; padding: 16px 24px; gap: 16px; margin-bottom: 32px; border: 1px solid rgba(0,0,0,0.05); }
.tip-icon { color: #f59e0b; }
.tip-content h4 { margin: 0 0 4px; font-size: 14px; color: #4e6073; }
.tip-content p { margin: 0; font-size: 13px; color: #5a6159; }

.bento-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 24px; }
.grid-col-8 { display: flex; flex-direction: column; gap: 24px; }
.grid-col-4 { display: flex; flex-direction: column; gap: 24px; }

.content-card { background: white; border-radius: 24px; padding: 24px; border: 1px solid rgba(0,0,0,0.05); box-shadow: 0 4px 12px rgba(0,0,0,0.02); }
.card-header-row { display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px; }
.card-title { font-size: 18px; font-weight: 800; color: #2e342d; margin: 0; }
.card-subtitle { font-size: 13px; color: #5a6159; margin: 4px 0 0; }
.view-all { color: #4e6073; font-size: 13px; font-weight: 700; text-decoration: none; }

.class-list { display: flex; flex-direction: column; gap: 16px; }
.class-item { display: flex; align-items: center; justify-content: space-between; padding: 16px; background: #fafaf5; border-radius: 16px; border: 1px solid rgba(0,0,0,0.03); }
.class-info { display: flex; align-items: center; gap: 16px; }
.class-icon-box { width: 48px; height: 48px; border-radius: 12px; display: flex; align-items: center; justify-content: center; color: white; }
.class-icon-box.ongoing { background-color: #f97316; }
.class-icon-box.upcoming { background-color: #4e6073; }
.class-icon-box.missed { background-color: #9f403d; }
.class-name-row { display: flex; align-items: center; gap: 8px; margin-bottom: 4px; }
.class-name { margin: 0; font-size: 15px; font-weight: 700; color: #2e342d; }
.live-tag { font-size: 9px; background: #fee2e2; color: #ef4444; padding: 2px 6px; border-radius: 4px; font-weight: 800; }
.class-details { display: flex; gap: 16px; font-size: 12px; color: #5a6159; }
.detail-item { display: flex; align-items: center; gap: 4px; }
.detail-item .material-symbols-outlined { font-size: 14px; }
.btn-attendance { background: #4e6073; color: white; border: none; padding: 8px 16px; border-radius: 8px; font-weight: 700; cursor: pointer; font-size: 12px; }
.marked-status { color: #10b981; font-weight: 700; font-size: 13px; }
.missed-status { color: #ef4444; font-weight: 700; font-size: 13px; }
.upcoming-status { color: #64748b; font-weight: 700; font-size: 13px; }

.planner-timeline { margin-top: 24px; position: relative; }
.timeline-item { display: flex; gap: 16px; position: relative; padding-bottom: 24px; }
.timeline-item::before { content: ''; position: absolute; left: 5px; top: 10px; bottom: 0; width: 1px; background: #ecefe7; z-index: 1; }
.timeline-item:last-child::before { display: none; }
.timeline-dot { width: 12px; height: 12px; background: white; border: 2px solid #4e6073; border-radius: 50%; position: relative; z-index: 2; margin-top: 4px; }
.timeline-content { flex: 1; background: #fafaf5; padding: 16px; border-radius: 12px; border: 1px solid rgba(0,0,0,0.03); }
.timeline-row { display: flex; justify-content: space-between; align-items: flex-start; }
.timeline-time { font-size: 12px; color: #4e6073; font-weight: 700; margin: 0 0 4px; }
.timeline-title { font-size: 14px; font-weight: 800; color: #2e342d; margin: 0 0 4px; }
.timeline-desc { font-size: 12px; color: #5a6159; margin: 0; }
.btn-edit-cal { background: none; border: none; color: #94a3b8; cursor: pointer; }

/* Academic Standing */
.gpa-gauge-container { display: flex; align-items: center; justify-content: center; position: relative; margin: 24px 0; }
.gpa-svg { width: 160px; height: 160px; transform: rotate(-90deg); }
.gpa-bg { fill: none; stroke: #ecefe7; stroke-width: 8; }
.gpa-progress { fill: none; stroke: #4e6073; stroke-width: 8; transition: stroke-dashoffset 1s ease-out; }
.gpa-inner-text { position: absolute; display: flex; flex-direction: column; align-items: center; }
.gpa-val { font-size: 32px; font-weight: 900; color: #4e6073; line-height: 1; }
.gpa-sub { font-size: 12px; font-weight: 700; color: #94a3b8; }
.stats-mini-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; background: #fafaf5; padding: 16px; border-radius: 16px; margin-bottom: 12px; }
.stat-mini { display: flex; flex-direction: column; align-items: center; }
.stat-label { font-size: 11px; color: #64748b; font-weight: 600; text-transform: uppercase; }
.stat-value { font-size: 15px; font-weight: 800; color: #2e342d; }
.gpa-hint { text-align: center; font-size: 12px; color: #10b981; font-weight: 600; margin: 0; }

.attendance-stack { display: flex; flex-direction: column; gap: 16px; margin-top: 16px; }
.attendance-item { display: flex; flex-direction: column; gap: 8px; }
.attendance-info { display: flex; justify-content: space-between; align-items: center; }
.subj-name { font-size: 13px; font-weight: 700; color: #2e342d; }
.session-count { font-size: 11px; color: #64748b; margin-right: 8px; }
.perc-val { font-size: 13px; font-weight: 800; color: #4e6073; }
.perc-val.warning { color: #f87171; }
.mini-progress { height: 6px; background: #ecefe7; border-radius: 99px; overflow: hidden; }
.mini-bar { height: 100%; background: #4e6073; border-radius: 99px; }
.mini-bar.low { background: #f87171; }

.deadlines-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16px; }
.header-right-meta { display: flex; align-items: center; gap: 8px; }
.badge-critical { background: #fee2e2; color: #ef4444; font-size: 10px; font-weight: 800; padding: 4px 8px; border-radius: 8px; }
.btn-add-mini { background: #4e6073; color: white; border: none; width: 24px; height: 24px; border-radius: 6px; display: flex; align-items: center; justify-content: center; cursor: pointer; }

.deadline-stack { display: flex; flex-direction: column; gap: 12px; }
.deadline-card { background: #fafaf5; border: 1px solid rgba(0,0,0,0.03); border-radius: 12px; padding: 16px; }
.deadline-card-top { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 12px; }
.subject-tag { font-size: 10px; font-weight: 800; padding: 2px 8px; border-radius: 4px; background: #e0e7ff; color: #4338ca; }
.deadline-name { margin: 8px 0 0; font-size: 14px; color: #2e342d; font-weight: 700; }
.deadline-urgency { font-size: 11px; font-weight: 800; padding: 4px 8px; border-radius: 6px; }
.deadline-urgency.critical { background: #fee2e2; color: #ef4444; }
.deadline-urgency.warning { background: #ffedd5; color: #f97316; }
.deadline-urgency.low { background: #dcfce7; color: #10b981; }

.progress-track { height: 4px; background: #ecefe7; border-radius: 99px; margin-bottom: 12px; }
.progress-bar-fill { height: 100%; background: #4e6073; border-radius: 99px; }
.deadline-card-footer { display: flex; justify-content: space-between; align-items: center; }
.perc-text { font-size: 11px; color: #64748b; font-weight: 600; }
.arrow-icon { font-size: 16px; color: #94a3b8; }
.arrow-icon.critical { color: #ef4444; }

/* Modals */
.modal-overlay { position: fixed; inset: 0; background: rgba(0,0,0,0.5); backdrop-filter: blur(4px); display: flex; align-items: center; justify-content: center; z-index: 1000; }
.deadline-modal { background: white; padding: 32px; border-radius: 24px; width: 100%; max-width: 400px; }
.deadline-modal h3 { margin: 0 0 24px; color: #2e342d; }
.form-group { display: flex; flex-direction: column; gap: 16px; }
.form-group input { padding: 12px 16px; border: 1px solid #ecefe7; border-radius: 12px; font-family: inherit; font-size: 14px; }
.modal-actions { display: flex; justify-content: flex-end; gap: 12px; margin-top: 24px; }
.btn-cancel { padding: 10px 20px; border: none; background: #ecefe7; color: #64748b; font-weight: 700; border-radius: 12px; cursor: pointer; }
.btn-save { padding: 10px 20px; border: none; background: #4e6073; color: white; font-weight: 700; border-radius: 12px; cursor: pointer; }

@media(max-width: 1024px) {
  .bento-grid { grid-template-columns: 1fr; }
  .grid-col-4 { grid-row: 1; }
  .sidebar { transform: translateX(-100%); transition: 0.3s; }
  .main-wrapper { margin-left: 0; }
}
</style>