<template>
  <div class="dashboard-wrapper">
    
    <!-- Ambient Background Bubbles -->
    <div class="ambient-glows">
      <div class="glow-bubble top-left"></div>
      <div class="glow-bubble bottom-right"></div>
    </div>

    <!-- Sidebar Aside -->
    <aside class="sidebar">
      <div class="sidebar-inner">
        <!-- Sidebar Gradient Overlay -->
        <div class="sidebar-mesh"></div>
        
        <!-- Sidebar Brand Logo -->
        <div class="sidebar-brand">
          <div class="brand-logo-icon">S</div>
          <span class="brand-name">StudentX</span>
        </div>
        
        <!-- Sidebar Navigation Menu -->
        <nav class="sidebar-nav">
          <router-link to="/dashboard" class="nav-item active">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path>
            </svg>
            <span class="nav-text">Overview</span>
          </router-link>

          <router-link to="/timetable" class="nav-item">
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

        <!-- Sidebar User Quick Logout -->
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
           <div class="header-title-group">
             <h1 class="header-main-title">Dashboard Overview</h1>
           </div>
           
           <div class="header-search">
             <div class="search-icon-wrapper">
               <svg class="icon search-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
               </svg>
             </div>
             <input type="text" placeholder="Search..." class="search-input">
           </div>
        </div>
        
        <div class="header-right">
          <button @click="isGpaModalOpen = true" class="btn-primary gpa-btn">GPA Tracker</button>
          
          <div class="header-notif-group">
            <div class="notif-bell-circle">
              <NotificationBell class="icon notif-bell" />
            </div>
          </div>
        </div>
      </header>

      <!-- Main Scrollable Inner Section -->
      <section class="content-scroll">
        <div class="dashboard-grid">
          
          <!-- Today's Schedule Card -->
          <div class="card card-wide schedule-card">
             <div class="card-inner">
               <div class="card-glow"></div>
               <div class="card-header">
                 <div class="card-title-group">
                   <h2 class="card-main-title">Today's Classes & Gaps</h2>
                   <p class="card-sub-title">Manage your daily schedule and attendance.</p>
                 </div>
                 <div class="card-header-icon">
                   <svg class="icon accent-indigo" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                     <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                   </svg>
                 </div>
               </div>
               
               <div class="schedule-list">
                 <!-- Classes Section -->
                 <div class="section-divider">ACTIVE CLASSES</div>
                 <div v-if="isLoadingTodayClasses" class="loading-state">
                    <div class="pulse-text">Loading classes...</div>
                 </div>
                 <template v-else>
                   <div v-for="cls in todayClasses" :key="cls.id" class="schedule-item class-item">
                      <div class="item-left">
                        <div class="status-dot online"></div>
                        <div class="item-info">
                          <span class="item-text font-bold">{{ cls.subjectName }}</span>
                          <span class="item-subtext">{{ formatTime(cls.startTime) }} - {{ formatTime(cls.endTime) }}</span>
                        </div>
                      </div>
                      <button @click="markAsPresent(cls.subjectName)" class="btn-mark">
                        <svg class="icon btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                        </svg>
                        Mark Present
                      </button>
                   </div>
                   <div v-if="!todayClasses.length" class="empty-state">No classes scheduled for today.</div>
                 </template>

                 <!-- Free Slots Section -->
                 <div class="section-divider">FREE GAPS</div>
                 <div v-if="isLoadingFreeSlots" class="loading-state">
                    <div class="pulse-text">Calculating gaps...</div>
                 </div>
                 <template v-else>
                   <div v-for="(slot, idx) in slots" :key="idx" class="schedule-item gap-item">
                      <div class="item-left">
                        <div class="status-dot idle"></div>
                        <span class="item-text">{{ slot }}</span>
                      </div>
                      <span class="badge-accent">Available Slot</span>
                   </div>
                   <div v-if="!slots.length" class="empty-state">No free gaps found.</div>
                 </template>
               </div>
             </div>
          </div>

          <!-- Attendance Tracker Card -->
          <div class="card card-small attendance-card">
             <div class="card-inner">
               <div class="card-glow"></div>
               <div class="card-header">
                 <div class="card-title-group">
                   <h2 class="card-main-title">Attendance Tracker</h2>
                   <p class="card-sub-title">Minimum 80% required.</p>
                 </div>
               </div>
               
               <div class="attendance-list no-scrollbar">
                 <div v-if="isLoadingAttendance" class="loading-state">
                    <div class="spinner-small"></div>
                 </div>
                 <template v-else>
                    <div v-for="item in attendanceSummary" :key="item.subjectName" class="attendance-row" :class="{ 'warning-row': item.lowAttendanceWarning }">
                      <div class="row-main">
                        <span class="sub-name">{{ item.subjectName }}</span>
                        <div class="status-box">
                          <span v-if="item.lowAttendanceWarning" class="status-icon text-red">⚠️</span>
                          <span v-else class="status-icon text-green">✅</span>
                          <span class="perc-val" :class="item.lowAttendanceWarning ? 'text-red' : 'text-green'">{{ Math.round(item.percentage) }}%</span>
                        </div>
                      </div>
                      <div class="perc-track">
                        <div class="perc-fill" :class="item.lowAttendanceWarning ? 'bg-red' : 'bg-green'" :style="{ width: item.percentage + '%' }"></div>
                      </div>
                      <p v-if="item.lowAttendanceWarning" class="warning-msg">Action Required: Attendance below 80%</p>
                    </div>
                    <div v-if="!attendanceSummary.length" class="empty-state">No attendance data yet.</div>
                 </template>
               </div>
             </div>
          </div>

          <!-- Upcoming Deadlines Card -->
          <div class="card card-small deadlines-card">
             <div class="card-inner">
               <div class="card-glow"></div>
               <div class="card-header">
                 <div class="card-title-group">
                   <h2 class="card-main-title">Upcoming Deadlines</h2>
                   <p class="card-sub-title">Live countdown to your tasks.</p>
                 </div>
                 <button @click="showDeadlineForm = !showDeadlineForm" class="btn-icon-bg">
                   <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                     <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4v16m8-8H4"></path>
                   </svg>
                 </button>
               </div>

               <!-- Add Deadline Form -->
               <div v-if="showDeadlineForm" class="deadline-form-mini">
                  <div class="mini-form-row">
                    <input type="text" v-model="deadlineForm.subjectName" placeholder="Subject" class="mini-input">
                    <input type="text" v-model="deadlineForm.title" placeholder="Title" class="mini-input">
                  </div>
                  <input type="datetime-local" v-model="deadlineForm.deadline" class="mini-input full-width">
                  <button @click="handleAddDeadline" class="btn-mini-save">Save Deadline</button>
               </div>
               
               <div class="deadline-list no-scrollbar">
                  <div v-if="isLoadingAssignments" class="loading-state">
                     <div class="spinner-small"></div>
                  </div>
                  <template v-else>
                    <div v-for="item in assignments" :key="item.id" class="deadline-item" :class="item.countdown?.urgency">
                       <div class="deadline-info">
                          <span class="deadline-subject">{{ item.subjectName }}</span>
                          <span class="deadline-title">{{ item.title }}</span>
                       </div>
                       <div class="deadline-time" :class="{ 'blinking': item.countdown?.urgency === 'critical' }">
                          <span class="countdown-text">{{ item.countdown?.text }}</span>
                          <span v-if="item.countdown?.urgency === 'urgent'" class="urgent-tag">URGENT</span>
                       </div>
                    </div>
                    <div v-if="!assignments.length" class="empty-state">No upcoming deadlines.</div>
                  </template>
               </div>
             </div>
          </div>

          <!-- GPA / Standing Card -->
          <div class="card card-small gpa-card">
             <div class="card-inner">
               <div class="card-glow"></div>
             <span class="label-muted">Academic Standing</span>
             <div class="gpa-value-group">
                <span class="gpa-score">3.72</span>
                <span class="gpa-max">/4.0</span>
             </div>
             <div class="progress-section">
                <div class="progress-labels">
                  <span class="label-tiny">Dean's List Target</span>
                  <span class="label-tiny accent">93%</span>
                </div>
                <div class="progress-bar-bg">
                   <div class="progress-bar-fill indigo-fuchsia-gradient" style="width: 93%"></div>
                </div>
             </div>
             </div>
          </div>

          <!-- Smart Planner Card -->
          <div class="card card-small smart-planner-card">
             <div class="card-inner">
               <div class="card-glow planner-glow"></div>
               <div class="card-header">
                 <div class="card-title-group">
                   <h2 class="card-main-title">Smart Planner</h2>
                   <p class="card-sub-title">AI-powered gap analysis.</p>
                 </div>
                 <div class="card-header-icon highlight-cyan">
                   <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                     <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                   </svg>
                 </div>
               </div>
               
               <div class="planner-content">
                  <div v-if="isLoadingTodayClasses || isLoadingAssignments" class="loading-state">
                     <div class="spinner-small cyan"></div>
                  </div>
                  <template v-else>
                    <div class="suggestion-box">
                       <div class="suggestion-header">
                          <span class="suggestion-title">{{ smartSuggestion?.title }}</span>
                       </div>
                       <p class="suggestion-msg">{{ smartSuggestion?.message }}</p>
                    </div>
                    
                    <div class="gap-stats">
                       <div class="stat-pill">
                          <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                          </svg>
                          <span>{{ freeSlotsCount }} Gaps Today</span>
                       </div>
                    </div>
                  </template>
               </div>
             </div>
          </div>
          
        </div>
      </section>
      
      <!-- Modal Overlay -->
     <GPAModal :isOpen="isGpaModalOpen" @close="isGpaModalOpen = false" />

      <!-- Notification Toast Container -->
      <div class="toast-container">
        <transition-group name="toast">
          <div v-for="toast in notificationToasts" :key="toast.id" class="toast-item" :class="toast.type">
            <div class="toast-content">
              <div class="toast-icon">
                <svg v-if="toast.type === 'lecture'" class="icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <svg v-else class="icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div class="toast-body">
                <p class="toast-title">{{ toast.title }}</p>
                <p class="toast-message">{{ toast.message }}</p>
              </div>
              <button @click="dismissToast(toast.id)" class="toast-close">&times;</button>
            </div>
            <div class="toast-glow"></div>
          </div>
        </transition-group>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import GPAModal from '../components/GPAModal.vue';
import NotificationBell from '../components/NotificationBell.vue';
import timetableService from '../services/timetableService';
import attendanceService from '../services/attendanceService';
import assignmentService from '../services/assignmentService';

const router = useRouter();

const isGpaModalOpen = ref(false);
const slots = ref([]);
const todayClasses = ref([]);
const attendanceSummary = ref([]);
const assignments = ref([]);
const notificationToasts = ref([]);
const notifiedIds = new Set();
const smartSuggestion = ref(null);
const freeSlotsCount = ref(0);
const isLoadingFreeSlots = ref(true);
const isLoadingTodayClasses = ref(true);
const isLoadingAttendance = ref(true);
const isLoadingAssignments = ref(true);

// Deadline Form
const showDeadlineForm = ref(false);
const deadlineForm = ref({
    subjectName: '',
    title: '',
    deadline: ''
});

const checkAuth = () => {
    const studentId = localStorage.getItem('studentId');
    if (!studentId) {
        router.push('/login');
        return null;
    }
    return studentId;
};

const fetchFreeSlots = async (studentId) => {
    isLoadingFreeSlots.value = true;
    try {
        const todayStr = new Date().toISOString().split('T')[0];
        const response = await timetableService.getFreeSlots(studentId, todayStr);
        slots.value = response.data || [];
    } catch (error) {
        console.error('Error fetching gaps:', error);
        slots.value = [];
    } finally {
        isLoadingFreeSlots.value = false;
    }
};

const fetchTodayClasses = async (studentId) => {
    isLoadingTodayClasses.value = true;
    try {
        const response = await timetableService.getTodayTimetable(studentId);
        todayClasses.value = response.data || [];
    } catch (error) {
        console.error('Error fetching today classes:', error);
    } finally {
        isLoadingTodayClasses.value = false;
    }
};

const fetchAttendanceSummary = async (studentId) => {
    isLoadingAttendance.value = true;
    try {
        const response = await attendanceService.getAttendanceSummary(studentId);
        attendanceSummary.value = response.data || [];
    } catch (error) {
        console.error('Error fetching attendance summary:', error);
    } finally {
        isLoadingAttendance.value = false;
    }
};

const updateSmartPlanner = () => {
    const gaps = calculateFreeSlots();
    freeSlotsCount.value = gaps.length;
    smartSuggestion.value = getSmartSuggestion(gaps);
};

const calculateFreeSlots = () => {
    const dayStart = 8 * 60; // 08:00 AM
    const dayEnd = 18 * 60; // 06:00 PM
    
    const sortedClasses = [...todayClasses.value].sort((a, b) => {
        return timeToMinutes(a.startTime) - timeToMinutes(b.startTime);
    });

    const gaps = [];
    let lastEnd = dayStart;

    sortedClasses.forEach(cls => {
        const start = timeToMinutes(cls.startTime);
        const end = timeToMinutes(cls.endTime);

        if (start - lastEnd >= 60) {
            gaps.push({ start: minutesToTime(lastEnd), end: minutesToTime(start) });
        }
        lastEnd = Math.max(lastEnd, end);
    });

    if (dayEnd - lastEnd >= 60) {
        gaps.push({ start: minutesToTime(lastEnd), end: minutesToTime(dayEnd) });
    }

    return gaps;
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

const getSmartSuggestion = (gaps) => {
    if (gaps.length === 0) return { title: "Packed Day!", message: "No free gaps found today. Keep pushing!" };

    const urgentDeadline = assignments.value.find(a => {
        const diff = new Date(a.deadline) - new Date();
        return diff > 0 && diff < (3 * 24 * 60 * 60 * 1000);
    });

    const mainGap = gaps[0];
    if (urgentDeadline) {
        return {
            title: `Smart Gap: ${mainGap.start} - ${mainGap.end}`,
            message: `Perfect time to work on your ${urgentDeadline.title} assignment! ✍️`
        };
    } else {
        return {
            title: `Free Time: ${mainGap.start} - ${mainGap.end}`,
            message: "Free time found! Time for a coffee break? ☕"
        };
    }
};

const fetchAssignments = async (studentId) => {
    isLoadingAssignments.value = true;
    try {
        const response = await assignmentService.getAssignments(studentId);
        assignments.value = (response.data || []).map(a => ({
            ...a,
            countdown: calculateCountdown(a.deadline)
        }));
    } catch (error) {
        console.error('Error fetching assignments:', error);
    } finally {
        isLoadingAssignments.value = false;
    }
};

const handleAddDeadline = async () => {
    const studentId = checkAuth();
    if (!studentId) return;

    if (!deadlineForm.value.subjectName || !deadlineForm.value.title || !deadlineForm.value.deadline) {
        alert('Please fill all fields');
        return;
    }

    try {
        await assignmentService.addAssignment({
            ...deadlineForm.value,
            studentId,
            status: 'Pending'
        });
        showDeadlineForm.value = false;
        deadlineForm.value = { subjectName: '', title: '', deadline: '' };
        fetchAssignments(studentId);
    } catch (error) {
        alert('Failed to add deadline');
    }
};

const markAsPresent = async (subjectName) => {
    const studentId = checkAuth();
    if (!studentId) return;
    
    try {
        await attendanceService.markAttendance(studentId, subjectName, true);
        alert(`Attendance marked for ${subjectName}`);
        fetchAttendanceSummary(studentId);
    } catch (error) {
        alert('Failed to mark attendance.');
    }
};

const calculateCountdown = (deadlineStr) => {
    if (!deadlineStr) return null;
    const deadline = new Date(deadlineStr);
    const now = new Date();
    const diff = deadline - now;

    if (diff <= 0) return { expired: true, text: 'OVERDUE' };

    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    return {
        expired: false,
        days,
        hours,
        minutes,
        text: `${days.toString().padStart(2, '0')}d : ${hours.toString().padStart(2, '0')}h : ${minutes.toString().padStart(2, '0')}m`,
        urgency: diff < (1000 * 60 * 60) ? 'urgent' : (diff < (1000 * 60 * 60 * 24) ? 'critical' : (diff < (1000 * 60 * 60 * 24 * 3) ? 'warning' : 'normal'))
    };
};

const formatTime = (timeStr) => {
    if (!timeStr) return '';
    return timeStr.substring(0, 5);
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    router.push('/login');
};

let countdownInterval;

onMounted(async () => {
    const studentId = checkAuth();
    if (studentId) {
        fetchFreeSlots(studentId);
        fetchAttendanceSummary(studentId);
        await fetchAssignments(studentId);
        await fetchTodayClasses(studentId);
        updateSmartPlanner();

        // Notification Check System
        checkNotifications();
        const notificationInterval = setInterval(() => {
            checkNotifications();
        }, 60000); // Run every 1 minute

        countdownInterval = setInterval(() => {
            assignments.value = assignments.value.map(a => ({
                ...a,
                countdown: calculateCountdown(a.deadline)
            }));
        }, 1000);

        onUnmounted(() => {
            clearInterval(notificationInterval);
            if (countdownInterval) clearInterval(countdownInterval);
        });
    }
});

const checkNotifications = () => {
    const now = new Date();
    
    // Check Lectures (1 hour before)
    todayClasses.value.forEach(cls => {
        if (!cls.startTime) return;
        const [hours, minutes] = cls.startTime.split(':').map(Number);
        const lectureDate = new Date();
        lectureDate.setHours(hours, minutes, 0, 0);
        
        const diffMs = lectureDate - now;
        const diffMins = Math.round(diffMs / 60000);

        if (diffMins === 60 && !notifiedIds.has(`lecture-${cls.id}`)) {
            triggerNotification({
                id: `lecture-${cls.id}`,
                type: 'lecture',
                title: 'Upcoming Lecture',
                message: `${cls.subjectName} starts in 60 minutes!`
            });
            notifiedIds.add(`lecture-${cls.id}`);
        }
    });

    // Check Exams/Deadlines (24 hours before)
    assignments.value.forEach(a => {
        if (!a.deadline) return;
        const deadlineDate = new Date(a.deadline);
        const diffMs = deadlineDate - now;
        const diffHours = Math.round(diffMs / 3600000);

        if (diffHours === 24 && !notifiedIds.has(`deadline-${a.id}`)) {
            triggerNotification({
                id: `deadline-${a.id}`,
                type: 'exam',
                title: 'Deadline Reminder',
                message: `${a.title} is due in 24 hours!`
            });
            notifiedIds.add(`deadline-${a.id}`);
        }
    });
};

const triggerNotification = (details) => {
    const toast = {
        ...details,
        id: Date.now() + Math.random()
    };
    notificationToasts.value.push(toast);
    
    // Auto-dismiss after 10 seconds
    setTimeout(() => {
        dismissToast(toast.id);
    }, 10000);
};

const dismissToast = (id) => {
    notificationToasts.value = notificationToasts.value.filter(t => t.id !== id);
};

onUnmounted(() => {
    if (countdownInterval) clearInterval(countdownInterval);
});
</script>

<style scoped>
/* Reset and Font Base */
:deep(*), :deep(*::before), :deep(*::after) {
  box-sizing: border-box;
}

.icon {
  width: 20px !important;
  height: 20px !important;
}

.dashboard-wrapper {
  display: flex;
  height: 100vh;
  background-color: #050511;
  color: #cadbee;
  font-family: 'Inter', system-ui, -apple-system, sans-serif;
  overflow: hidden;
  position: relative;
}

/* Icons Sizing Task 4 */
.icon {
  width: 20px !important;
  height: 20px !important;
  min-width: 20px !important;
  min-height: 20px !important;
  flex-shrink: 0;
}

/* Ambient Ambient Glows */
.ambient-glows {
  position: fixed;
  inset: 0;
  z-index: 0;
  pointer-events: none;
  overflow: hidden;
}

.glow-bubble {
  position: absolute;
  border-radius: 50%;
  filter: blur(120px);
  opacity: 0.05;
}

.top-left {
  width: 40vw;
  height: 40vw;
  top: -10%;
  left: -10%;
  background: #6366f1;
}

.bottom-right {
  width: 30vw;
  height: 30vw;
  bottom: -10%;
  right: 0%;
  background: #d946ef;
}

/* Sidebar Styling Component-like */
.sidebar {
  width: 260px;
  padding: 12px;
  height: 100%;
  z-index: 20;
  flex-shrink: 0;
}

.sidebar-inner {
  height: 100%;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  padding: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.5);
  position: relative;
  overflow: hidden;
}

.sidebar-mesh {
  position: absolute;
  inset: 0;
  background: linear-gradient(to bottom, rgba(255,255,255,0.02), transparent);
  pointer-events: none;
}

.sidebar-brand {
  display: flex;
  align-items: center;
  padding: 16px 8px;
  margin-bottom: 24px;
}

.brand-logo-icon {
  width: 28px;
  height: 28px;
  background: linear-gradient(135deg, #6366f1, #d946ef);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 900;
  font-size: 16px;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
}

.brand-name {
  margin-left: 12px;
  font-weight: 700;
  font-size: 16px;
  letter-spacing: -0.02em;
  color: white;
}

.sidebar-nav {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.nav-item {
  display: flex;
  align-items: center;
  padding: 10px 14px;
  border-radius: 12px;
  color: #94a3b8;
  text-decoration: none;
  font-size: 13px;
  font-weight: 600;
  transition: all 0.2s;
}

.nav-item:hover {
  background: rgba(255, 255, 255, 0.05);
  color: white;
}

.nav-item.active {
  background: rgba(255, 255, 255, 0.06);
  color: white;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.nav-link-icon {
  margin-right: 12px;
  opacity: 0.7;
}

.nav-item.active .nav-link-icon {
  color: #6366f1;
  opacity: 1;
}

.sidebar-footer {
  padding-top: 12px;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  margin-top: auto;
}

.logout-link {
  display: flex;
  align-items: center;
  padding: 10px 14px;
  border-radius: 12px;
  color: #64748b;
  text-decoration: none;
  font-size: 13px;
  font-weight: 600;
  transition: 0.2s;
}

.logout-link:hover {
  color: #f87171;
  background: rgba(248, 113, 113, 0.05);
}

/* Main Main Section Area Area */
.main-layout {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 12px 12px 12px 0;
}

/* Header Header System System */
.header {
  height: 56px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 12px;
  margin-bottom: 8px;
  flex-shrink: 0;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.header-main-title {
  font-size: 16px;
  font-weight: 800;
  color: white;
  margin: 0;
}

.header-search {
  display: flex;
  align-items: center;
  position: relative;
}

.search-icon-wrapper {
  position: absolute;
  left: 12px;
  color: #475569;
}

.search-input {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 999px;
  padding: 8px 16px 8px 36px;
  width: 240px;
  font-size: 12px;
  color: #e2e8f0;
  transition: 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: rgba(99, 102, 241, 0.4);
  background: rgba(255, 255, 255, 0.05);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 12px;
}

.btn-primary {
  background: #6366f1;
  color: white;
  border: none;
  border-radius: 999px;
  padding: 6px 16px;
  font-size: 11px;
  font-weight: 700;
  cursor: pointer;
  transition: 0.2s;
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.3);
}

.btn-primary:hover {
  background: #4f46e5;
  transform: translateY(-1px);
}

.header-notif-group {
  display: flex;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 999px;
  padding: 2px;
}

.notif-bell-circle {
  width: 28px;
  height: 28px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;
}

.notif-bell-circle:hover {
  background: rgba(255, 255, 255, 0.05);
}

/* Grid Scroll Area System Area System Area System Area System Scroll Area Area Scroll Area Scroll Area Area Scroll Area Area Scroll Area System Area System Area Scroll Area Scroll Area Scroll Area Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Area Scroll Area Area Scroll Area Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Scroll Area Area Area Scroll Area Area Area Scroll Area Area Scroll Area Area Area Area Scroll Area Area Area Scroll Area Area Scroll Area Area Scroll Area Area Area Scroll Area Scroll Area Scroll Area Area Area Scroll Area Scroll Area Scroll Area Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Scroll Area Area */
.content-scroll {
  flex: 1;
  overflow-y: auto;
  padding-right: 8px;
}

.content-scroll::-webkit-scrollbar {
  width: 4px;
}

.content-scroll::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 4px;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 16px;
}

/* Card Card Component Component Component Card Component Component Card Component Card Component Card Component Card Component Component Component Component Component Component Card Card Card Component Component Component Component Component Component Component Component Card Component Card Card Card Component Component Card Card Component Component Component Card Component Component Card Component Component Card Component Component Card Card Component Component Card Card Card Component Component Card Card Card Component Component Card Card Card Card Card Card Card Component Card Component Card Component Card Component Component Component Component Component Card Card Card Card Component Component Card Component Component Component Component Card Card Card Card Card Card Card Card */
.card {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 16px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0,0,0,0.2);
}

.card-inner {
  padding: 16px;
  height: 100%;
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 10;
}

.card-glow {
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at top left, rgba(99, 102, 241, 0.05), transparent);
  pointer-events: none;
}

.card-wide {
  grid-column: span 8;
}

.card-small {
  grid-column: span 4;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.card-main-title {
  font-size: 15px;
  font-weight: 800;
  color: white;
  margin: 0 0 4px;
}

.card-sub-title {
  font-size: 11px;
  color: #64748b;
  margin: 0;
}

.card-header-icon {
  background: rgba(99, 102, 241, 0.1);
  padding: 6px;
  border-radius: 10px;
}

.accent-indigo { color: #6366f1; }

.schedule-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.schedule-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 10px 14px;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  transition: 0.3s;
}

.schedule-item:hover {
  background: rgba(255, 255, 255, 0.04);
  border-color: rgba(99, 102, 241, 0.2);
}

.item-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.status-dot {
  width: 6px;
  height: 6px;
  background: #6366f1;
  border-radius: 50%;
  box-shadow: 0 0 8px #6366f1;
}

.item-text {
  font-size: 12px;
  font-weight: 600;
  color: #e2e8f0;
}

.badge-accent {
  font-size: 10px;
  font-weight: 800;
  color: #6366f1;
  background: rgba(99, 102, 241, 0.1);
  padding: 4px 10px;
  border-radius: 6px;
  border: 1px solid rgba(99, 102, 241, 0.1);
  text-transform: uppercase;
  letter-spacing: 0.02em;
}

/* GPA Card Specific */
.gpa-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.label-muted {
  font-size: 10px;
  font-weight: 900;
  color: #475569;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  margin-bottom: 12px;
}

.gpa-value-group {
  margin-bottom: 24px;
}

.gpa-score {
  font-size: 42px;
  font-weight: 900;
  color: white;
  letter-spacing: -0.05em;
}

.gpa-max {
  font-size: 14px;
  color: #475569;
  margin-left: 4px;
}

.progress-section {
  width: 100%;
}

.progress-labels {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.label-tiny {
  font-size: 10px;
  font-weight: 600;
  color: #64748b;
}

.label-tiny.accent { color: #818cf8; }

.progress-bar-bg {
  width: 100%;
  height: 6px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 99px;
  overflow: hidden;
}

.progress-bar-fill {
  height: 100%;
  border-radius: 99px;
}

.indigo-fuchsia-gradient {
  background: linear-gradient(to right, #6366f1, #d946ef);
}

.section-divider {
  font-size: 9px;
  font-weight: 800;
  color: #475569;
  letter-spacing: 0.15em;
  margin: 16px 0 8px;
  display: flex;
  align-items: center;
  gap: 12px;
}

.section-divider::after {
  content: '';
  flex: 1;
  height: 1px;
  background: rgba(255, 255, 255, 0.05);
}

.status-dot { width: 8px; height: 8px; border-radius: 50%; }
.status-dot.online { background: #10b981; box-shadow: 0 0 10px rgba(16, 185, 129, 0.4); }
.status-dot.idle { background: #64748b; }

.item-info { display: flex; flex-direction: column; gap: 2px; }
.item-subtext { font-size: 10px; color: #64748b; }
.font-bold { font-weight: 700; color: white; }

.btn-mark {
  background: rgba(16, 185, 129, 0.1);
  border: 1px solid rgba(16, 185, 129, 0.2);
  color: #34d399;
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 10px;
  font-weight: 700;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 6px;
  transition: 0.2s;
}

.btn-mark:hover {
  background: rgba(16, 185, 129, 0.2);
  transform: translateY(-1px);
}

.btn-icon { width: 14px !important; height: 14px !important; }

/* Attendance Card Styles */
.attendance-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  max-height: 400px;
  overflow-y: auto;
  padding-right: 4px;
}

.attendance-row {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 12px;
  background: rgba(255, 255, 255, 0.02);
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.05);
  transition: 0.3s;
}

.attendance-row.warning-row {
  background: rgba(239, 68, 68, 0.03);
  border-color: rgba(239, 68, 68, 0.15);
  box-shadow: inset 0 0 20px rgba(239, 68, 68, 0.02);
}

.row-main {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.sub-name { font-size: 13px; font-weight: 700; color: #e2e8f0; }
.status-box { display: flex; align-items: center; gap: 8px; }
.status-icon { font-size: 12px; }
.perc-val { font-size: 14px; font-weight: 800; }

.text-red { color: #ef4444; }
.text-green { color: #10b981; }

.perc-track {
  width: 100%;
  height: 4px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 99px;
  overflow: hidden;
}

.perc-fill { height: 100%; transition: width 1s cubic-bezier(0.4, 0, 0.2, 1); }
.bg-red { background: #ef4444; box-shadow: 0 0 10px rgba(239, 68, 68, 0.3); }
.bg-green { background: #10b981; box-shadow: 0 0 10px rgba(16, 185, 129, 0.3); }

.warning-msg {
  font-size: 10px;
  font-weight: 700;
  color: #f87171;
  margin: 4px 0 0;
  text-transform: uppercase;
  letter-spacing: 0.02em;
}

.spinner-small {
  width: 24px;
  height: 24px;
  border: 2px solid rgba(249, 115, 22, 0.1);
  border-top-color: #fb923c;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
  margin: 20px auto;
}

@keyframes spin { to { transform: rotate(360deg); } }

.no-scrollbar::-webkit-scrollbar { display: none; }

/* Deadlines Card Styles */
.deadlines-card {
  max-height: 500px;
}

.btn-icon-bg {
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  color: white;
  width: 32px;
  height: 32px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: 0.2s;
}

.btn-icon-bg:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: #6366f1;
}

.deadline-form-mini {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 12px;
  margin-bottom: 16px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.mini-form-row {
  display: flex;
  gap: 8px;
}

.mini-input {
  flex: 1;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 6px;
  padding: 8px;
  color: white;
  font-size: 11px;
}

.mini-input:focus {
  outline: none;
  border-color: #6366f1;
}

.full-width { width: 100%; }

.btn-mini-save {
  background: #6366f1;
  color: white;
  border: none;
  border-radius: 6px;
  padding: 8px;
  font-size: 11px;
  font-weight: 700;
  cursor: pointer;
}

.mini-input::-webkit-calendar-picker-indicator {
  filter: invert(1);
  cursor: pointer;
}

.deadline-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
  overflow-y: auto;
}

.deadline-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  transition: 0.3s;
}

.deadline-item.normal { border-left: 3px solid #6366f1; }
.deadline-item.warning { border-left: 3px solid #f59e0b; box-shadow: 0 0 15px rgba(245, 158, 11, 0.1); }
.deadline-item.critical { border-left: 3px solid #ef4444; box-shadow: 0 0 15px rgba(239, 68, 68, 0.2); }
.deadline-item.urgent { 
  border-left: 3px solid #ef4444; 
  background: rgba(239, 68, 68, 0.05);
  animation: pulse-urgent 2s infinite;
}

@keyframes pulse-urgent {
  0% { box-shadow: 0 0 0 0 rgba(239, 68, 68, 0.4); }
  70% { box-shadow: 0 0 0 10px rgba(239, 68, 68, 0); }
  100% { box-shadow: 0 0 0 0 rgba(239, 68, 68, 0); }
}

.blinking {
  animation: blink-anim 1s steps(5, start) infinite;
}

@keyframes blink-anim {
  to { visibility: hidden; }
}

.deadline-info { display: flex; flex-direction: column; gap: 4px; }
.deadline-subject { font-size: 9px; font-weight: 800; color: #64748b; text-transform: uppercase; }
.deadline-title { font-size: 13px; font-weight: 700; color: white; }

.deadline-time { display: flex; flex-direction: column; align-items: flex-end; gap: 4px; }
.countdown-text { font-family: 'Monaco', 'Consolas', monospace; font-size: 12px; font-weight: 800; color: #e2e8f0; }
.urgent-tag { font-size: 8px; font-weight: 900; background: #ef4444; color: white; padding: 2px 6px; border-radius: 4px; }

/* Notification Toast Styles */
.toast-container {
  position: fixed;
  top: 24px;
  right: 24px;
  z-index: 9999;
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 320px;
}

.toast-item {
  background: rgba(15, 23, 42, 0.9);
  backdrop-filter: blur(20px);
  border-radius: 16px;
  padding: 16px;
  position: relative;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.toast-item.lecture { border-left: 4px solid #6366f1; }
.toast-item.exam { border-left: 4px solid #ef4444; }

.toast-item.lecture .toast-glow {
  background: radial-gradient(circle at top right, rgba(99, 102, 241, 0.15), transparent);
}

.toast-item.exam .toast-glow {
  background: radial-gradient(circle at top right, rgba(239, 68, 68, 0.15), transparent);
}

.toast-glow {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.toast-content {
  display: flex;
  align-items: flex-start;
  gap: 12px;
  position: relative;
  z-index: 10;
}

.toast-icon {
  width: 32px;
  height: 32px;
  background: rgba(255, 255, 255, 0.05);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.toast-item.lecture .toast-icon { color: #818cf8; }
.toast-item.exam .toast-icon { color: #f87171; }

.toast-body { flex: 1; }
.toast-title { font-size: 13px; font-weight: 800; color: white; margin: 0 0 2px; }
.toast-message { font-size: 11px; color: #94a3b8; margin: 0; line-height: 1.4; }

.toast-close {
  background: none;
  border: none;
  color: #64748b;
  font-size: 18px;
  cursor: pointer;
  padding: 0;
  line-height: 1;
  transition: 0.2s;
}

.toast-close:hover { color: white; }

/* Toast Transitions */
.toast-enter-active, .toast-leave-active { transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1); }
.toast-enter-from { opacity: 0; transform: translateX(50px) scale(0.9); }
.toast-leave-to { opacity: 0; transform: translateX(20px) scale(0.95); }

/* Smart Planner Styles */
.smart-planner-card {
  border-color: rgba(34, 211, 238, 0.2);
}

.planner-glow {
  background: radial-gradient(circle at top left, rgba(34, 211, 238, 0.1), transparent);
}

.highlight-cyan { color: #22d3ee; }

.planner-content {
  margin-top: 12px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.suggestion-box {
  background: rgba(34, 211, 238, 0.05);
  border: 1px solid rgba(34, 211, 238, 0.1);
  border-radius: 12px;
  padding: 14px;
}

.suggestion-header {
  margin-bottom: 6px;
}

.suggestion-title {
  font-size: 13px;
  font-weight: 800;
  color: #22d3ee;
  text-transform: uppercase;
  letter-spacing: 0.02em;
}

.suggestion-msg {
  font-size: 13px;
  color: #e2e8f0;
  line-height: 1.5;
  margin: 0;
}

.gap-stats {
  display: flex;
  gap: 8px;
}

.stat-pill {
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.05);
  padding: 6px 12px;
  border-radius: 99px;
  font-size: 11px;
  font-weight: 600;
  color: #94a3b8;
}

.spinner-small.cyan {
  border-top-color: #22d3ee;
  border-right-color: rgba(34, 211, 238, 0.1);
}

/* Existing responsive queries below... */
/* Task 4: Responsive Media Queries Task 5 Responsive Media Queries Task 5 Task 5 Task 5 Responsive Media Queries */
@media (max-width: 1024px) {
  .card-wide { grid-column: span 12; }
  .card-small { grid-column: span 12; }
}

@media (max-width: 768px) {
  .sidebar {
    position: fixed;
    left: -260px;
    top: 0;
    bottom: 0;
    transition: left 0.3s;
  }
  
  .main-layout {
    padding: 12px;
  }
  
  .header-search { display: none; }
}
</style>