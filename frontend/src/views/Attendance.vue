<template>
  <div class="attendance-wrapper">
    
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

          <router-link to="/timetable" class="nav-item">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
            </svg>
            <span class="nav-text">Timetable</span>
          </router-link>

          <router-link to="/attendance" class="nav-item active">
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
           <h1 class="header-title">Attendance Insights</h1>
           <p class="header-subtitle">Monitor and manage your academic presence.</p>
        </div>

        <div class="header-right">
           <div class="header-notif-group">
            <div class="notif-bell-circle">
              <NotificationBell class="icon notif-bell" />
            </div>
           </div>
        </div>
      </header>

      <main class="content-scroll">
        <div class="attendance-container">
          
          <!-- Summary Chart Component -->
          <div class="card chart-card">
            <div class="card-inner">
               <AttendanceChart />
            </div>
          </div>

          <!-- Detailed Grid -->
          <div class="attendance-grid">
             <div v-for="item in attendanceSummary" :key="item.subjectName" class="card subject-card" :class="{ 'warning-card': item.lowAttendanceWarning }">
                <div class="card-inner">
                   <div class="card-glow"></div>
                   <div class="subject-header">
                      <h3 class="subject-name">{{ item.subjectName }}</h3>
                      <div class="status-badge" :class="item.lowAttendanceWarning ? 'bg-red' : 'bg-green'">
                         {{ item.lowAttendanceWarning ? 'Warning' : 'Good' }}
                      </div>
                   </div>

                   <div class="stats-row">
                      <div class="stat-item">
                         <span class="stat-lbl">Present</span>
                         <span class="stat-val">{{ item.attendedSessions }}</span>
                      </div>
                      <div class="stat-item">
                         <span class="stat-lbl">Absent</span>
                         <span class="stat-val">{{ item.totalSessions - item.attendedSessions }}</span>
                      </div>
                      <div class="stat-item">
                         <span class="stat-lbl">Percentage</span>
                         <span class="stat-val" :class="item.lowAttendanceWarning ? 'txt-red' : 'txt-green'">{{ Math.round(item.percentage) }}%</span>
                      </div>
                   </div>

                   <div class="progress-container">
                      <div class="progress-bar">
                         <div class="progress-fill" :class="item.lowAttendanceWarning ? 'fill-red' : 'fill-green'" :style="{ width: item.percentage + '%' }"></div>
                      </div>
                   </div>

                   <p v-if="item.lowAttendanceWarning" class="action-msg">Minimum 80% required. Need more presence.</p>
                </div>
             </div>
          </div>

          <div v-if="!attendanceSummary.length && !isLoading" class="empty-state">
             <div class="empty-icon">📂</div>
             <h3>No data available</h3>
             <p>Mark your attendance from the dashboard to see statistics here.</p>
          </div>

          <div v-if="isLoading" class="loading-overlay">
             <div class="spinner"></div>
          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import NotificationBell from '../components/NotificationBell.vue';
import AttendanceChart from '../components/AttendanceChart.vue';
import attendanceService from '../services/attendanceService';

const router = useRouter();
const attendanceSummary = ref([]);
const isLoading = ref(true);

const checkAuth = () => {
    const studentId = localStorage.getItem('studentId');
    if (!studentId) {
        router.push('/login');
        return null;
    }
    return studentId;
};

const fetchAttendance = async () => {
    const studentId = checkAuth();
    if (!studentId) return;

    isLoading.value = true;
    try {
        const response = await attendanceService.getAttendanceSummary(studentId);
        attendanceSummary.value = response.data || [];
    } catch (error) {
        console.error('Error fetching attendance:', error);
    } finally {
        isLoading.value = false;
    }
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    router.push('/login');
};

onMounted(() => {
    fetchAttendance();
});
</script>

<style scoped>
.attendance-wrapper {
  display: flex;
  height: 100vh;
  background: #050511;
  color: white;
  overflow: hidden;
  position: relative;
}

.ambient-glows {
  position: absolute;
  inset: 0;
  pointer-events: none;
  z-index: 1;
}

.glow-bubble {
  position: absolute;
  border-radius: 50%;
  filter: blur(120px);
  opacity: 0.05;
}

.top-left { width: 40vw; height: 40vw; top: -10%; left: -10%; background: #6366f1; }
.bottom-right { width: 30vw; height: 30vw; bottom: -10%; right: 0%; background: #d946ef; }

/* Sidebar */
.sidebar { width: 260px; padding: 12px; height: 100%; z-index: 20; flex-shrink: 0; }
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
.sidebar-mesh { position: absolute; inset: 0; background: linear-gradient(to bottom, rgba(255,255,255,0.02), transparent); pointer-events: none; }
.sidebar-brand { display: flex; align-items: center; padding: 16px 8px; margin-bottom: 24px; }
.brand-logo-icon { width: 28px; height: 28px; background: linear-gradient(135deg, #6366f1, #d946ef); border-radius: 8px; display: flex; align-items: center; justify-content: center; color: white; font-weight: 900; font-size: 16px; box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3); }
.brand-name { margin-left: 12px; font-weight: 700; font-size: 16px; color: white; }
.sidebar-nav { flex: 1; display: flex; flex-direction: column; gap: 4px; }
.nav-item { display: flex; align-items: center; padding: 10px 14px; border-radius: 12px; color: #94a3b8; text-decoration: none; font-size: 13px; font-weight: 600; transition: all 0.2s; }
.nav-item:hover { background: rgba(255, 255, 255, 0.05); color: white; }
.nav-item.active { background: rgba(255, 255, 255, 0.06); color: white; border: 1px solid rgba(255, 255, 255, 0.1); }
.nav-link-icon { margin-right: 12px; opacity: 0.7; width: 20px !important; height: 20px !important; }
.nav-item.active .nav-link-icon { color: #6366f1; opacity: 1; }
.sidebar-footer { padding-top: 12px; border-top: 1px solid rgba(255, 255, 255, 0.05); }
.logout-link { display: flex; align-items: center; padding: 10px 14px; border-radius: 12px; color: #64748b; text-decoration: none; font-size: 13px; font-weight: 600; }
.logout-link:hover { color: #f87171; background: rgba(248, 113, 113, 0.05); }

/* Main Layout */
.main-layout { flex: 1; display: flex; flex-direction: column; padding: 12px 12px 12px 0; }
.header { height: 72px; display: flex; align-items: center; justify-content: space-between; padding: 0 24px; margin-bottom: 24px; flex-shrink: 0; }
.header-title { font-size: 24px; font-weight: 800; color: white; margin: 0; }
.header-subtitle { font-size: 13px; color: #64748b; margin: 4px 0 0; }
.header-right { display: flex; align-items: center; gap: 16px; }
.header-notif-group { display: flex; background: rgba(255, 255, 255, 0.03); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 999px; padding: 4px; }
.notif-bell-circle { width: 32px; height: 32px; display: flex; align-items: center; justify-content: center; border-radius: 50%; cursor: pointer; }
.icon { width: 20px !important; height: 20px !important; }

/* Content Scroll */
.content-scroll { flex: 1; overflow-y: auto; padding-right: 8px; }
.content-scroll::-webkit-scrollbar { width: 4px; }
.content-scroll::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.05); border-radius: 4px; }

.attendance-container { display: flex; flex-direction: column; gap: 24px; padding-bottom: 40px; }

/* Cards */
.card { background: rgba(255, 255, 255, 0.03); backdrop-filter: blur(15px); border: 1px solid rgba(255, 255, 255, 0.08); border-radius: 16px; position: relative; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.2); }
.card-inner { padding: 24px; position: relative; z-index: 10; }
.card-glow { position: absolute; inset: 0; background: radial-gradient(circle at top left, rgba(99, 102, 241, 0.05), transparent); pointer-events: none; }

.chart-card { min-height: 300px; }

.attendance-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(320px, 1fr)); gap: 20px; }

.subject-card { transition: 0.3s; }
.subject-card:hover { transform: translateY(-4px); border-color: rgba(99, 102, 241, 0.3); }
.warning-card { border-color: rgba(239, 68, 68, 0.2); background: rgba(239, 68, 68, 0.02); }

.subject-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
.subject-name { font-size: 18px; font-weight: 700; color: white; margin: 0; }
.status-badge { font-size: 10px; font-weight: 800; padding: 4px 10px; border-radius: 6px; text-transform: uppercase; }
.bg-red { background: rgba(239, 68, 68, 0.2); color: #f87171; border: 1px solid rgba(239, 68, 68, 0.2); }
.bg-green { background: rgba(16, 185, 129, 0.2); color: #34d399; border: 1px solid rgba(16, 185, 129, 0.2); }

.stats-row { display: flex; justify-content: space-between; margin-bottom: 20px; }
.stat-item { display: flex; flex-direction: column; gap: 4px; }
.stat-lbl { font-size: 11px; color: #64748b; font-weight: 600; }
.stat-val { font-size: 16px; font-weight: 800; }
.txt-red { color: #ef4444; }
.txt-green { color: #10b981; }

.progress-container { width: 100%; height: 8px; background: rgba(255, 255, 255, 0.05); border-radius: 4px; overflow: hidden; margin-bottom: 12px; }
.progress-fill { height: 100%; transition: width 1s ease-out; }
.fill-red { background: linear-gradient(to right, #ef4444, #f87171); box-shadow: 0 0 10px rgba(239, 68, 68, 0.2); }
.fill-green { background: linear-gradient(to right, #10b981, #34d399); box-shadow: 0 0 10px rgba(16, 185, 129, 0.2); }

.action-msg { font-size: 11px; font-weight: 600; color: #f87171; margin: 0; text-transform: uppercase; letter-spacing: 0.02em; }

.empty-state { text-align: center; padding: 60px 20px; color: #64748b; }
.empty-icon { font-size: 48px; margin-bottom: 16px; opacity: 0.3; }

.loading-overlay { display: flex; justify-content: center; padding: 40px; }
.spinner { width: 40px; height: 40px; border: 3px solid rgba(99, 102, 241, 0.1); border-top-color: #6366f1; border-radius: 50%; animation: spin 0.8s linear infinite; }
@keyframes spin { to { transform: rotate(360deg); } }

@media (max-width: 768px) {
  .sidebar { position: fixed; left: -260px; }
  .main-layout { padding: 12px; }
  .attendance-grid { grid-template-columns: 1fr; }
}
</style>
