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
          <router-link to="/student-dashboard" class="nav-item active">
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

          <router-link to="/summarization" class="nav-item">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
            </svg>
            <span class="nav-text">Summarization</span>
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
            <div class="notif-bell-wrapper">
              <button @click="isNotifDropdownOpen = !isNotifDropdownOpen" class="notif-bell-btn" :class="{ 'has-notifs': notificationsList.length > 0 }">
                <svg class="icon notif-bell-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"></path>
                </svg>
                <div v-if="notificationsList.length > 0" class="bell-badge">{{ notificationsList.length }}</div>
              </button>

              <transition name="dropdown">
                <div v-if="isNotifDropdownOpen" class="bell-dropdown glass-morphism">
                  <div class="dropdown-header">
                    <span class="dropdown-title">Notifications</span>
                    <button v-if="notificationsList.length > 0" @click="clearAllNotifications" class="btn-clear-all">Clear All</button>
                  </div>
                  
                  <div class="dropdown-list no-scrollbar">
                    <div v-for="notif in notificationsList" :key="notif.id" class="dropdown-item" :class="notif.type">
                      <div class="item-icon-box">
                        <svg v-if="notif.type === 'lecture'" class="icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <svg v-else-if="notif.type === 'critical'" class="icon vibrating-icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <svg v-else class="icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                      </div>
                      <div class="item-content">
                        <p class="item-title">{{ notif.title }}</p>
                        <p class="item-msg">{{ notif.message }}</p>
                      </div>
                      <button @click="markAsRead(notif.id)" class="btn-read-check" title="Mark as read">
                        <svg class="icon-small" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
                        </svg>
                      </button>
                    </div>
                    <div v-if="notificationsList.length === 0" class="dropdown-empty">
                      <p>No new notifications</p>
                    </div>
                  </div>
                </div>
              </transition>
            </div>
          </div>
        </div>
      </header>

      <!-- Upcoming Alerts Bar -->
      <NotificationAlertBar :alerts="upcomingAlerts" @dismiss="dismissAlert" />

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
                   <div v-for="cls in todayClasses" :key="cls.id" class="schedule-item class-item" :class="{ 'item-missed': getClassStatus(cls) === 'missed' }">
                      <div class="item-left">
                        <div class="status-dot" :class="getClassStatus(cls)"></div>
                        <div class="item-info">
                          <span class="item-text font-bold">{{ cls.subjectName }}</span>
                          <span class="item-subtext">{{ formatTime(cls.startTime) }} - {{ formatTime(cls.endTime) }}</span>
                        </div>
                      </div>
                      <div class="item-actions">
                        <div v-if="cls.marked" class="status-badge checked">
                          <svg class="icon anim-pop" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                          </svg>
                          <span>Checked-in ✅</span>
                        </div>

                        <button v-else-if="getClassStatus(cls) === 'ongoing'" @click="markAsPresent(cls.subjectName, cls.id)" class="btn-mark glow-indigo">
                          <svg class="icon btn-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                          </svg>
                          Mark Present
                        </button>

                        <div v-else-if="getClassStatus(cls) === 'missed'" class="status-badge missed">
                          <svg class="icon anim-shake" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                          </svg>
                          <span>Missed ❌</span>
                        </div>

                        <div v-else class="status-badge upcoming">
                           <span>Scheduled</span>
                        </div>
                      </div>
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
          <div class="card card-small gpa-card-upgrade">
            <div class="card-inner">
              <div class="card-glow"></div>
              
              <!-- Status Badge -->
              <div class="status-badge-top">Academic Excellence</div>
              
              <div class="gauge-section">
                <div class="gauge-container">
                  <svg viewBox="0 0 100 100" class="gauge-svg">
                    <defs>
                      <linearGradient id="gauge-gradient" x1="0%" y1="0%" x2="100%" y2="0%">
                        <stop offset="0%" style="stop-color: #6366f1; stop-opacity: 1" />
                        <stop offset="100%" style="stop-color: #d946ef; stop-opacity: 1" />
                      </linearGradient>
                    </defs>
                    <circle cx="50" cy="50" r="42" class="gauge-bg" />
                    <!-- stroke-dasharray is 2 * PI * r = 2 * 3.14159 * 42 = 263.89 -->
                    <!-- offset for 93% (3.72/4.0) is 263.89 * (1 - 0.93) = 18.47 -->
                    <circle cx="50" cy="50" r="42" class="gauge-progress" stroke-dasharray="263.9" stroke-dashoffset="18.5" />
                  </svg>
                  <div class="gauge-content">
                    <span class="gauge-value">3.72</span>
                    <span class="gauge-max">GPA</span>
                  </div>
                </div>
              </div>

              <div class="target-info">
                <p class="target-text">0.28 points to Dean’s List</p>
                <div class="target-indicator">
                  <span class="dot-pulse"></span>
                  <span class="indicator-label">Top 5% Student</span>
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

                    <div class="gap-list-mini">
                      <div v-for="(gap, idx) in freeGaps" :key="idx" class="gap-item-mini">
                        <div class="gap-dot"></div>
                        <span class="gap-time-text">{{ formatTime12h(gap.startTime) }} - {{ formatTime12h(gap.endTime) }}</span>
                      </div>
                      <div v-if="!freeGaps.length" class="empty-gap-msg">
                        No free gaps available today
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
import NotificationAlertBar from '../components/NotificationAlertBar.vue';
import timetableService from '../services/timetableService';
import attendanceService from '../services/attendanceService';
import assignmentService from '../services/assignmentService';
import notificationService from '../services/notificationService';
import SummarizerCard from '../components/SummarizerCard.vue';

const router = useRouter();

const isGpaModalOpen = ref(false);
const slots = ref([]);
const todayClasses = ref([]);
const attendanceSummary = ref([]);
const assignments = ref([]);
const notificationToasts = ref([]);
const notificationsList = ref([]);
const isNotifDropdownOpen = ref(false);
const notifiedIds = new Set();
const smartSuggestion = ref(null);
const freeSlotsCount = ref(0);
const freeGaps = ref([]);
const isLoadingFreeSlots = ref(true);
const isLoadingTodayClasses = ref(true);
const isLoadingAttendance = ref(true);
const isLoadingAssignments = ref(true);
const upcomingAlerts = ref([]);
const isLoadingAlerts = ref(false);

// Deadline Form
const showDeadlineForm = ref(false);
const deadlineForm = ref({
    subjectName: '',
    title: '',
    deadline: ''
});

const checkAuth = () => {
    const token = localStorage.getItem('token');
    const studentId = localStorage.getItem('studentId');
    if (!token || !studentId) {
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
    freeGaps.value = gaps;
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
            gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(start) });
        }
        lastEnd = Math.max(lastEnd, end);
    });

    if (dayEnd - lastEnd >= 60) {
        gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(dayEnd) });
    }

    return gaps;
};

const formatTime12h = (timeStr) => {
    if (!timeStr) return '';
    const [h, m] = timeStr.split(':').map(Number);
    const ampm = h >= 12 ? 'PM' : 'AM';
    const hours = h % 12 || 12;
    const minutes = m.toString().padStart(2, '0');
    return `${hours}:${minutes} ${ampm}`;
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
            title: `Smart Gap: ${formatTime12h(mainGap.startTime)} - ${formatTime12h(mainGap.endTime)}`,
            message: `Perfect time to work on your ${urgentDeadline.title} assignment! ✍️`
        };
    } else {
        return {
            title: `Free Time: ${formatTime12h(mainGap.startTime)} - ${formatTime12h(mainGap.endTime)}`,
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

const fetchUpcomingAlerts = async (studentId) => {
    isLoadingAlerts.value = true;
    try {
        const response = await notificationService.getUpcomingAlerts(studentId);
        upcomingAlerts.value = response.data || [];
    } catch (error) {
        console.error('Error fetching alerts:', error);
    } finally {
        isLoadingAlerts.value = false;
    }
};

const dismissAlert = (id) => {
    upcomingAlerts.value = upcomingAlerts.value.filter(a => a.id !== id);
};

const markAsPresent = async (subjectName, timetableId) => {
    const studentId = checkAuth();
    if (!studentId) return;
    
    try {
        await attendanceService.markAttendance(studentId, subjectName, true, timetableId);
        alert(`Attendance marked for ${subjectName}`);
        fetchAttendanceSummary(studentId);
        fetchTodayClasses(studentId); // Re-fetch classes to update the UI
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
        fetchUpcomingAlerts(studentId);
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
        const diffMins = Math.floor(diffMs / 60000);

        if (diffMins === 60 && !notifiedIds.has(`lecture-${cls.id}`)) {
            triggerNotification({
                type: 'lecture',
                title: 'Upcoming Lecture',
                message: `${cls.subjectName} starts in 60 minutes!`
            });
            notifiedIds.add(`lecture-${cls.id}`);
        }
    });

    checkDeadlines(now);
};

const checkDeadlines = (now) => {
    assignments.value.forEach(a => {
        if (!a.deadline) return;
        const deadlineDate = new Date(a.deadline);
        const diffMs = deadlineDate - now;
        const diffMins = Math.floor(diffMs / 60000);

        // 1 Day Warning (approx 1440 mins)
        // Check if it's within the next 24 hours but more than 23.5 hours
        if (diffMins <= 1440 && diffMins > 1380 && !notifiedIds.has(`warn-1d-${a.id}`)) {
            triggerNotification({
                type: 'warning',
                title: 'Deadline Tomorrow',
                message: `${a.title} is due in 24 hours. Get ready!`
            });
            notifiedIds.add(`warn-1d-${a.id}`);
        }

        // 2 Hour Critical Warning (approx 120 mins)
        if (diffMins <= 120 && diffMins > 0 && !notifiedIds.has(`crit-2h-${a.id}`)) {
            triggerNotification({
                type: 'critical',
                title: 'URGENT: Submit Soon!',
                message: `${a.title} is due in 2 hours! Final push! 🚀`
            });
            notifiedIds.add(`crit-2h-${a.id}`);
        }
    });
};

const triggerNotification = (details) => {
    const id = Date.now() + Math.random();
    const toast = {
        ...details,
        id: id
    };
    notificationToasts.value.push(toast);

    // Add to persistent dropdown list
    notificationsList.value.unshift({
        ...details,
        id: id,
        timestamp: new Date()
    });
    
    // Auto-dismiss toast after 10 seconds
    setTimeout(() => {
        dismissToast(toast.id);
    }, 10000);
};

const clearAllNotifications = () => {
    notificationsList.value = [];
};

const markAsRead = (id) => {
    notificationsList.value = notificationsList.value.filter(n => n.id !== id);
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

.notif-bell-wrapper {
  position: relative;
  z-index: 100;
}

.notif-bell-btn {
  width: 36px;
  height: 36px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  color: #94a3b8;
  cursor: pointer;
  position: relative;
  transition: 0.3s;
}

.notif-bell-btn:hover {
  background: rgba(255, 255, 255, 0.08);
  color: white;
}

.notif-bell-btn.has-notifs .notif-bell-icon {
  animation: bell-shake 2s infinite;
}

@keyframes bell-shake {
  0%, 100% { transform: rotate(0); }
  5%, 15%, 25% { transform: rotate(10deg); }
  10%, 20%, 30% { transform: rotate(-10deg); }
  35% { transform: rotate(0); }
}

.bell-badge {
  position: absolute;
  top: -2px;
  right: -2px;
  background: #ef4444;
  color: white;
  font-size: 9px;
  font-weight: 900;
  min-width: 16px;
  height: 16px;
  border-radius: 99px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0 4px;
  border: 2px solid #050511;
  box-shadow: 0 0 10px rgba(239, 68, 68, 0.5);
}

/* Bell Dropdown Styles */
.bell-dropdown {
  position: absolute;
  top: calc(100% + 12px);
  right: 0;
  width: 320px;
  background: rgba(15, 23, 42, 0.9);
  backdrop-filter: blur(25px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.6);
  z-index: 1000;
  overflow: hidden;
}

.dropdown-header {
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.dropdown-title { font-size: 14px; font-weight: 800; color: white; }
.btn-clear-all {
  font-size: 11px;
  font-weight: 700;
  color: #6366f1;
  background: none;
  border: none;
  cursor: pointer;
}

.dropdown-list {
  max-height: 380px;
  overflow-y: auto;
}

.dropdown-list::-webkit-scrollbar {
  width: 5px;
}

.dropdown-list::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 5px;
}

.dropdown-item {
  display: flex;
  align-items: flex-start;
  padding: 16px 20px;
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  transition: 0.2s;
}

.dropdown-item:hover {
  background: rgba(255, 255, 255, 0.02);
}

.item-icon-box {
  width: 32px;
  height: 32px;
  background: rgba(255,255,255,0.05);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.dropdown-item.lecture .item-icon-box { color: #818cf8; background: rgba(99, 102, 241, 0.1); }
.dropdown-item.critical .item-icon-box { color: #ef4444; background: rgba(239, 68, 68, 0.1); }
.dropdown-item.warning .item-icon-box { color: #f59e0b; background: rgba(245, 158, 11, 0.1); }

.item-content { flex: 1; }
.item-title { font-size: 12px; font-weight: 800; color: white; margin: 0 0 2px; }
.item-msg { font-size: 11px; color: #94a3b8; margin: 0; line-height: 1.4; }

.btn-read-check {
  background: none;
  border: none;
  color: #475569;
  cursor: pointer;
  padding: 4px;
  border-radius: 4px;
  transition: 0.2s;
}

.btn-read-check:hover {
  background: rgba(255,255,255,0.05);
  color: #10b981;
}

.icon-small { width: 14px !important; height: 14px !important; }

.dropdown-empty {
  padding: 40px 20px;
  text-align: center;
  color: #475569;
  font-size: 12px;
}

.vibrating-icon {
  animation: icon-vibrate 0.3s infinite;
}

/* Transitions */
.dropdown-enter-active, .dropdown-leave-active { transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1); }
.dropdown-enter-from, .dropdown-leave-to { opacity: 0; transform: translateY(-10px) scale(0.95); }

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
/* GPA Card Upgraded Styles */
.gpa-card-upgrade {
  overflow: visible;
}

.status-badge-top {
  position: absolute;
  top: 12px;
  right: 12px;
  background: rgba(99, 102, 241, 0.15);
  border: 1px solid rgba(99, 102, 241, 0.3);
  color: #818cf8;
  padding: 4px 10px;
  border-radius: 99px;
  font-size: 10px;
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  z-index: 20;
}

.gauge-section {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px 0;
}

.gauge-container {
  position: relative;
  width: 140px;
  height: 140px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.gauge-svg {
  width: 100%;
  height: 100%;
  transform: rotate(-90deg);
}

.gauge-bg {
  fill: none;
  stroke: rgba(255, 255, 255, 0.03);
  stroke-width: 8;
}

.gauge-progress {
  fill: none;
  stroke: url(#gauge-gradient);
  stroke-width: 8;
  stroke-linecap: round;
  transition: stroke-dashoffset 1.5s cubic-bezier(0.4, 0, 0.2, 1);
}

.gauge-content {
  position: absolute;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.gauge-value {
  font-size: 32px;
  font-weight: 900;
  color: white;
  line-height: 1;
  letter-spacing: -0.02em;
}

.gauge-max {
  font-size: 9px;
  font-weight: 800;
  color: #64748b;
  margin-top: 4px;
}

.target-info {
  margin-top: auto;
  text-align: center;
}

.target-text {
  font-size: 13px;
  font-weight: 700;
  color: #e2e8f0;
  margin-bottom: 8px;
}

.target-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  opacity: 0.7;
}

.dot-pulse {
  width: 6px;
  height: 6px;
  background: #10b981;
  border-radius: 50%;
  position: relative;
}

.dot-pulse::after {
  content: '';
  position: absolute;
  inset: -4px;
  border: 1px solid #10b981;
  border-radius: 50%;
  animation: pulse-ring 1.5s infinite;
}

@keyframes pulse-ring {
  0% { transform: scale(0.5); opacity: 1; }
  100% { transform: scale(1.5); opacity: 0; }
}

.indicator-label {
  font-size: 10px;
  font-weight: 600;
  color: #94a3b8;
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
  opacity: 0.8;
  transition: 0.2s;
}

.mini-input::-webkit-calendar-picker-indicator:hover {
  opacity: 1;
  transform: scale(1.1);
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
.toast-item.warning { border-left: 4px solid #f59e0b; border-right: 1px solid rgba(245, 158, 11, 0.2); }
.toast-item.critical { 
  border: 2px solid #ef4444; 
  animation: critical-pulse 1.5s infinite;
}

.toast-item.lecture .toast-glow {
  background: radial-gradient(circle at top right, rgba(99, 102, 241, 0.15), transparent);
}

.toast-item.warning .toast-glow {
  background: radial-gradient(circle at top right, rgba(245, 158, 11, 0.1), transparent);
}

.toast-item.critical .toast-glow {
  background: radial-gradient(circle at top right, rgba(239, 68, 68, 0.2), transparent);
}

@keyframes critical-pulse {
  0% { box-shadow: 0 0 0 0 rgba(239, 68, 68, 0.4), 0 10px 30px rgba(0, 0, 0, 0.3); }
  70% { box-shadow: 0 0 0 10px rgba(239, 68, 68, 0), 0 10px 30px rgba(0, 0, 0, 0.3); }
  100% { box-shadow: 0 0 0 0 rgba(239, 68, 68, 0), 0 10px 30px rgba(0, 0, 0, 0.3); }
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
.toast-item.warning .toast-icon { color: #f59e0b; }
.toast-item.critical .toast-icon { 
  color: #ef4444; 
  animation: icon-vibrate 0.3s infinite;
}

@keyframes icon-vibrate {
  0% { transform: translate(0, 0); }
  25% { transform: translate(1px, -1px); }
  50% { transform: translate(-1px, 1px); }
  75% { transform: translate(1px, 1px); }
  100% { transform: translate(0, 0); }
}

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

/* Task 2: Attendance Status Badges & Glows */
.item-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.status-badge {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  border-radius: 99px;
  font-size: 11px;
  font-weight: 700;
  backdrop-filter: blur(10px);
}

.status-badge.checked {
  background: rgba(16, 185, 129, 0.1);
  color: #10b981;
  border: 1px solid rgba(16, 185, 129, 0.2);
}

.status-badge.missed {
  background: rgba(239, 68, 68, 0.1);
  color: #ef4444;
  border: 1px solid rgba(239, 68, 68, 0.2);
  box-shadow: 0 0 15px rgba(239, 68, 68, 0.2);
}

.status-badge.upcoming {
  background: rgba(148, 163, 184, 0.1);
  color: #94a3b8;
  border: 1px solid rgba(148, 163, 184, 0.2);
}

/* Status Dots */
.status-dot.ongoing { background: #10b981; box-shadow: 0 0 8px #10b981; }
.status-dot.upcoming { background: #94a3b8; }
.status-dot.missed { background: #ef4444; box-shadow: 0 0 8px #ef4444; }

/* Glowing Buttons & Animations */
.glow-indigo {
  box-shadow: 0 0 15px rgba(99, 102, 241, 0.4);
}

.anim-pop { animation: pop 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.275); }
@keyframes pop {
  0% { transform: scale(0.8); opacity: 0; }
  100% { transform: scale(1); opacity: 1; }
}

.anim-shake { animation: shake 0.5s cubic-bezier(.36,.07,.19,.97) both; }
@keyframes shake {
  10%, 90% { transform: translate3d(-1px, 0, 0); }
  20%, 80% { transform: translate3d(2px, 0, 0); }
  30%, 50%, 70% { transform: translate3d(-4px, 0, 0); }
  40%, 60% { transform: translate3d(4px, 0, 0); }
}

.item-missed {
  border-color: rgba(239, 68, 68, 0.2) !important;
  background: rgba(239, 68, 68, 0.05) !important;
}

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

.gap-list-mini {
  margin-top: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.gap-item-mini {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 10px;
  transition: 0.2s;
}

.gap-item-mini:hover {
  background: rgba(34, 211, 238, 0.05);
  border-color: rgba(34, 211, 238, 0.2);
}

.gap-dot {
  width: 5px;
  height: 5px;
  background: #22d3ee;
  border-radius: 50%;
  box-shadow: 0 0 8px rgba(34, 211, 238, 0.6);
}

.gap-time-text {
  font-size: 11px;
  font-weight: 600;
  color: #e2e8f0;
}

.empty-gap-msg {
  font-size: 11px;
  color: #64748b;
  text-align: center;
  padding: 10px;
  background: rgba(255, 255, 255, 0.02);
  border-radius: 10px;
  border: 1px dashed rgba(255, 255, 255, 0.1);
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