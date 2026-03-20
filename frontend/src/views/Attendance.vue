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
            <p class="nav-user-name">{{ student.name || 'Loading...' }}</p>
            <p class="nav-user-id">#{{ student.id || 'N/A' }}</p>
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
          <input type="text" placeholder="Search insights..." />
        </div>
        
        <div class="header-actions">
          <div class="live-sync-badge">
             <div class="pulse-container">
               <span class="pulse-ring"></span>
               <span class="pulse-core"></span>
             </div>
             <span>LIVE SYNC</span>
          </div>
          <div class="notif-wrapper">
            <button class="action-btn">
              <span class="material-symbols-outlined">notifications</span>
            </button>
          </div>
          <button class="action-btn">
            <span class="material-symbols-outlined">help_outline</span>
          </button>
          <div class="divider"></div>
          <router-link to="/profile" class="user-profile">
            <img :src="student.profilePic || 'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'" alt="Profile" />
          </router-link>
        </div>
      </header>

      <!-- Content Canvas -->
      <main class="content-canvas">
        
        <!-- Page Header -->
        <section class="page-title-section">
          <h2 class="main-heading">
              Attendance <span class="highlight-text">Insights</span>
          </h2>
          <p class="sub-heading">
              Real-time analysis of your academic presence. You've maintained a {{ globalStats.percentage }}% consistency rate this semester. Keep the momentum going!
          </p>
        </section>

        <!-- Loading State -->
        <div v-if="isLoading" class="loading-state">
           <div class="spinner"></div>
           <p>Syncing attendance data...</p>
        </div>

        <!-- Insights Grid -->
        <div v-else class="insights-grid">
          
          <!-- Weekly Attendance Chart -->
          <section class="chart-section left-col">
            <div class="chart-header">
              <div>
                <p class="chart-tag">Weekly Activity</p>
                <h3 class="chart-title">Presence Distribution</h3>
              </div>
              <div class="time-toggles">
                <button class="toggle-btn text-muted">7 Days</button>
                <button class="toggle-btn active-purple">30 Days</button>
              </div>
            </div>
            
            <!-- Real Chart Component -->
            <div class="real-chart-container">
               <AttendanceChart />
            </div>
          </section>

          <!-- Circular Gauges (Right Sidebar) -->
          <section class="gauges-section right-col">
            
            <div class="global-stats-row">
              <div class="global-avg-card">
                <div>
                  <p class="avg-label">Global Average</p>
                  <p class="avg-val">{{ globalStats.percentage }}%</p>
                </div>
                <div class="avg-icon-box">
                  <span class="material-symbols-outlined">trending_up</span>
                </div>
              </div>
              
              <div class="attendance-counts-card">
                 <div class="count-box text-green">
                    <span class="material-symbols-outlined text-xl mb-1">how_to_reg</span>
                    <p class="count-label">Present</p>
                    <p class="count-val">{{ globalStats.present }}</p>
                 </div>
                 <div class="count-divider"></div>
                 <div class="count-box text-red">
                    <span class="material-symbols-outlined text-xl mb-1">person_off</span>
                    <p class="count-label">Absent</p>
                    <p class="count-val">{{ globalStats.absent }}</p>
                 </div>
              </div>
            </div>

            <div class="breakdown-card">
              <h4 class="breakdown-title">Subject Breakdowns</h4>
              
              <div v-if="attendanceSummary.length === 0" class="empty-breakdown">
                 <span class="material-symbols-outlined empty-icon">folder_open</span>
                 <p>No subjects found for attendance tracking.</p>
              </div>

              <div class="subjects-list">
                <div v-for="(item, idx) in attendanceSummary" :key="item.subjectName" class="subject-row">
                  
                  <div class="circular-progress">
                    <svg class="progress-svg">
                      <circle class="bg-circle" cx="28" cy="28" r="24"></circle>
                      <circle class="fg-circle" cx="28" cy="28" r="24" 
                              :stroke="getSubjectColor(idx)"
                              :stroke-dasharray="150.8" 
                              :stroke-dashoffset="getDashOffset(item.percentage)"></circle>
                    </svg>
                    <span class="progress-text" :style="{ color: getSubjectColor(idx) }">{{ Math.round(item.percentage) }}%</span>
                  </div>

                  <div class="subject-info">
                    <div class="subj-header-row">
                      <p class="subj-name" :class="{'txt-error': item.lowAttendanceWarning}">{{ item.subjectName }}</p>
                      <span v-if="item.lowAttendanceWarning" class="warning-tag">Action Required</span>
                    </div>
                    <div class="subj-stats-badges">
                      <span class="stat-badge badge-green">
                        <span class="material-symbols-outlined icon-micro">how_to_reg</span> Present: {{ item.attendedSessions }}
                      </span>
                      <span class="stat-badge badge-red">
                        <span class="material-symbols-outlined icon-micro">person_off</span> Absent: {{ item.totalSessions - item.attendedSessions }}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
          </section>

          <!-- Bottom Row Bento Features (Placeholders integrated) -->
          <section class="bottom-features span-full">
            
            <div class="bento-box normal-hover">
              <span class="material-symbols-outlined feature-icon txt-primary">event_available</span>
              <div>
                <h4 class="feature-title txt-primary">Upcoming Class</h4>
                <p class="feature-desc">Review your timetable for the next immediate class.</p>
                <router-link to="/timetable" class="feature-link">
                   View Schedule <span class="material-symbols-outlined">chevron_right</span>
                </router-link>
              </div>
            </div>

            <div class="bento-box accent-box">
              <span class="material-symbols-outlined feature-icon txt-white-alpha">auto_awesome</span>
              <div>
                <h4 class="feature-title">Perfect Record!</h4>
                <p class="feature-desc-light">Maintain your consistency to qualify for the Academic Excellence Badge.</p>
                <button class="btn-claim">Keep it up</button>
              </div>
            </div>

            <div class="bento-box normal-hover">
              <div class="avatars-group">
                <img alt="Peer" class="avatar-sm" src="https://lh3.googleusercontent.com/aida-public/AB6AXuD-waJMSf-eaLpWKa5xnwljiHHzH7HuDEvNYjAec3TB4Myo122vpEPKZu5dGButOsOHtGg0LolU2iwZBqcy7ZLsxDOHm5pYwBrsI_X1OUn_4js-cnDoTA5nNDL8-bWTbKb_jPcM258jYn5xVcJCiMul6TT7JNp4fMNJ3EN5maYY3apuasLBX4Wd0YEmhnihWaLRUQSFlAEVXeIriMtL8lLNuqgdMTft8kImV7hVn0VOvaXe_AzNoJnZOepTgSJRL6hWjd_uESOzZsw"/>
                <img alt="Peer" class="avatar-sm" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBmCxMvGmGXNVnSUGVavC22zmsh8NaZA3mUqlWqI5Sjx-uKQXCq64Z6VboQpad5mvgmRm00geuoKnPSVFDYNFowZB3ZdcMPbO9CmTcLFLmFLSBexF_IfjO8Whbx8VfR2iDZagffRUMrG6M07Y4oezVGpnckEj3HKZYRWPBmlRTyUq5hmKxKPKi0OJsvo8app2v4ErmQkOQKK3ouVb7jUlG6SOpOqvXSJ7tqpeq2mWPwruIckS2I3xDsAgtJTsqRXl20PJ-PXGBWGcQ"/>
                <div class="avatar-more">+12</div>
              </div>
              <div>
                <h4 class="feature-title txt-primary">Study Group</h4>
                <p class="feature-desc">{{ globalStats.percentage > 80 ? 'You are among the top attenders.' : 'Join a study group to stay motivated.' }}</p>
              </div>
            </div>

          </section>

        </div>
      </main>

    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import AttendanceChart from '../components/AttendanceChart.vue';
import attendanceService from '../services/attendanceService';
import studentService from '../services/studentService';

const router = useRouter();

const student = ref({ name: '', id: '' });
const attendanceSummary = ref([]);
const isLoading = ref(true);

const colors = ['#4e6073', '#575e78', '#5b6063', '#394654', '#5a6159'];

const getSubjectColor = (idx) => {
    return colors[idx % colors.length];
};

const getDashOffset = (percentage) => {
    const p = Math.max(0, Math.min(100, percentage));
    return 150.8 * (1 - p / 100);
};

const globalStats = computed(() => {
    if (attendanceSummary.value.length === 0) return { present: 0, absent: 0, percentage: 0 };
    
    let totalClasses = 0;
    let attendedClasses = 0;

    attendanceSummary.value.forEach(item => {
        totalClasses += item.totalSessions;
        attendedClasses += item.attendedSessions;
    });

    return {
        total: totalClasses,
        present: attendedClasses,
        absent: totalClasses - attendedClasses,
        percentage: totalClasses === 0 ? 0 : Math.round((attendedClasses / totalClasses) * 100 * 10) / 10
    };
});

const checkAuth = () => {
    const storedId = localStorage.getItem('studentId');
    if (!storedId) {
        router.push('/login');
        return null;
    }
    return storedId;
};

const fetchStudentData = async (uid) => {
    try {
        const response = await studentService.getStudentById(uid);
        if(response.data) student.value = response.data;
    } catch (e) {
        console.error('Error fetching student data:', e);
    }
};

const fetchAttendance = async () => {
    const studentId = checkAuth();
    if (!studentId) return;

    fetchStudentData(studentId);

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
    localStorage.removeItem('token');
    router.push('/login');
};

onMounted(() => {
    fetchAttendance();
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

/* Sidebar - Exact match with Light Theme */
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
  border-bottom: 1px solid rgba(0,0,0,0.05);
}
.search-bar { position: relative; width: 320px; }
.search-bar .material-symbols-outlined { position: absolute; left: 16px; top: 50%; transform: translateY(-50%); color: #5a6159; font-size: 20px; }
.search-bar input { width: 100%; background-color: rgba(222, 228, 218, 0.5); border: none; padding: 10px 16px 10px 48px; border-radius: 99px; font-family: inherit; font-size: 14px; outline: none; transition: 0.2s; }
.search-bar input:focus { box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.2); }

.header-actions { display: flex; align-items: center; gap: 24px; }
.live-sync-badge { display: flex; align-items: center; gap: 8px; padding: 4px 12px; background: #ecfdf5; border: 1px solid #d1fae5; border-radius: 99px; }
.live-sync-badge span { font-size: 10px; font-weight: 800; color: #15803d; letter-spacing: 0.05em; }

.pulse-container { position: relative; width: 8px; height: 8px; display: flex; align-items: center; justify-content: center; }
.pulse-ring { position: absolute; width: 100%; height: 100%; border-radius: 50%; background: #4ade80; opacity: 0.75; animation: ping 1.5s cubic-bezier(0, 0, 0.2, 1) infinite; }
.pulse-core { position: relative; width: 8px; height: 8px; border-radius: 50%; background: #22c55e; }
@keyframes ping { 75%, 100% { transform: scale(2.5); opacity: 0; } }

.action-btn { background: none; border: none; color: #4e6073; cursor: pointer; padding: 8px; border-radius: 50%; transition: background 0.2s; position: relative; }
.action-btn:hover { background-color: #dee4da; }
.divider { width: 1px; height: 32px; background: rgba(0,0,0,0.1); margin: 0 8px; }
.user-profile { width: 40px; height: 40px; border-radius: 50%; overflow: hidden; border: 2px solid rgba(78, 96, 115, 0.1); display: block; cursor: pointer; transition: 0.2s; }
.user-profile:hover { border-color: #4e6073; transform: scale(1.05); }
.user-profile img { width: 100%; height: 100%; object-fit: cover; }

/* Content Canvas */
.content-canvas { padding: 48px; }

.page-title-section { margin-bottom: 48px; }
.main-heading { font-size: 48px; font-weight: 800; color: #2e342d; line-height: 1.1; margin: 0 0 16px 0; letter-spacing: -0.02em; }
.highlight-text { color: #4e6073; }
.sub-heading { color: #5a6159; font-size: 18px; margin: 0; max-width: 650px; line-height: 1.6; }

/* Grid Layout */
.insights-grid { display: grid; grid-template-columns: 8fr 4fr; gap: 32px; align-items: start; }

/* Left Col: Chart Component */
.chart-section { background: #f3f4ee; border-radius: 20px; padding: 32px; box-shadow: 0 4px 20px rgba(0,0,0,0.02); }
.chart-header { display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px; }
.chart-tag { font-size: 10px; font-weight: 800; color: #4e6073; letter-spacing: 0.05em; text-transform: uppercase; margin: 0 0 8px 0; }
.chart-title { font-size: 20px; font-weight: 700; color: #4e6073; margin: 0; }

.time-toggles { display: flex; gap: 4px; padding: 4px; background: #ecefe7; border-radius: 99px; }
.time-toggles button { background: none; border: none; padding: 6px 16px; border-radius: 99px; font-size: 12px; font-weight: 700; cursor: pointer; transition: 0.2s; }
.text-muted { color: #5a6159; }
.text-muted:hover { background: rgba(255,255,255,0.5); }
.active-purple { background: #4e6073; color: white; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }

/* Injecting the exact Vue component here */
.real-chart-container { min-height: 250px; border-radius: 16px; overflow: hidden; background: white; padding: 16px; }

/* Right Col: Gauges */
.gauges-section { display: flex; flex-direction: column; gap: 24px; }

.global-stats-row { display: flex; gap: 16px; align-items: stretch; }

.global-avg-card { flex: 1; background: #f3f4ee; border-radius: 20px; padding: 24px; display: flex; align-items: center; justify-content: space-between; border-left: 4px solid #4e6073; box-shadow: 0 4px 20px rgba(0,0,0,0.02); }
.avg-label { font-size: 12px; font-weight: 700; color: #5a6159; text-transform: uppercase; letter-spacing: 0.05em; margin: 0 0 4px 0; }
.avg-val { font-size: 32px; font-weight: 800; color: #4e6073; margin: 0; line-height: 1; }
.avg-icon-box { width: 48px; height: 48px; border-radius: 50%; background: rgba(78, 96, 115, 0.1); display: flex; align-items: center; justify-content: center; color: #4e6073; }
.avg-icon-box .material-symbols-outlined { font-size: 24px; }

.attendance-counts-card { background: white; border-radius: 20px; padding: 16px 24px; display: flex; align-items: center; justify-content: center; gap: 24px; border: 1px solid rgba(0,0,0,0.05); box-shadow: 0 4px 20px rgba(0,0,0,0.02); }
.count-box { display: flex; flex-direction: column; align-items: center; text-align: center; }
.count-label { font-size: 11px; font-weight: 700; text-transform: uppercase; margin: 0 0 2px 0; letter-spacing: 0.05em; }
.count-val { font-size: 24px; font-weight: 800; margin: 0; line-height: 1; }
.count-divider { width: 1px; height: 40px; background: rgba(0,0,0,0.05); }
.text-green { color: #10b981; }
.text-red { color: #f43f5e; }
.text-xl { font-size: 24px; }
.mb-1 { margin-bottom: 4px; }

.breakdown-card { background: #f3f4ee; border-radius: 20px; padding: 32px; box-shadow: 0 4px 20px rgba(0,0,0,0.02); flex: 1; }
.breakdown-title { font-size: 18px; font-weight: 700; color: #4e6073; margin: 0 0 32px 0; }

.subjects-list { display: flex; flex-direction: column; gap: 24px; }
.subject-row { display: flex; align-items: center; gap: 24px; }

/* SVG Circular Gauge */
.circular-progress { position: relative; width: 56px; height: 56px; display: flex; align-items: center; justify-content: center; }
.progress-svg { width: 100%; height: 100%; transform: rotate(-90deg); }
.bg-circle { fill: transparent; stroke: #dee4da; stroke-width: 5; }
.fg-circle { fill: transparent; stroke-width: 5; stroke-linecap: round; transition: stroke-dashoffset 1s ease-out; }
.progress-text { position: absolute; font-size: 10px; font-weight: 800; }

.subject-info { flex: 1; }
.subj-header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 6px; }
.subj-name { font-size: 14px; font-weight: 700; color: #2e342d; margin: 0; }
.subj-name.txt-error { color: #9f403d; }

.subj-stats-badges { display: flex; gap: 8px; }
.stat-badge { font-size: 10px; font-weight: 800; padding: 4px 8px; border-radius: 6px; display: flex; align-items: center; gap: 4px; letter-spacing: 0.02em; }
.badge-green { background: #ecfdf5; color: #10b981; border: 1px solid #d1fae5; }
.badge-red { background: #fff1f2; color: #f43f5e; border: 1px solid #ffe4e6; }
.icon-micro { font-size: 12px; }

.warning-tag { font-size: 9px; font-weight: 800; color: white; background: #ef4444; padding: 2px 6px; border-radius: 4px; text-transform: uppercase; letter-spacing: 0.05em; }
.empty-breakdown { text-align: center; color: #5a6159; font-size: 13px; font-weight: 600; padding: 20px 0; }
.empty-icon { font-size: 32px; opacity: 0.5; margin-bottom: 8px; }

/* Bottom Features */
.bottom-features { grid-column: 1 / -1; display: grid; grid-template-columns: repeat(3, 1fr); gap: 32px; margin-top: 16px; }

.bento-box { border-radius: 20px; padding: 32px; display: flex; flex-direction: column; justify-content: space-between; min-height: 220px; transition: 0.3s; cursor: pointer; }
.normal-hover { background: #f3f4ee; border: 1px solid rgba(0,0,0,0.02); }
.normal-hover:hover { background: #e5eae0; }
.accent-box { background: #4e6073; color: white; box-shadow: 0 20px 40px rgba(78, 96, 115, 0.2); transition: transform 0.2s; }
.accent-box:hover { transform: scale(1.02); }

.feature-icon { font-size: 36px; margin-bottom: 24px; }
.txt-primary { color: #4e6073; }
.txt-white-alpha { color: rgba(255,255,255,0.5); }

.feature-title { font-size: 18px; font-weight: 700; margin: 0 0 8px 0; }
.feature-desc { font-size: 14px; color: #5a6159; font-weight: 500; margin: 0 0 16px 0; }
.feature-desc-light { font-size: 14px; color: rgba(255,255,255,0.8); font-weight: 500; margin: 0 0 24px 0; line-height: 1.5; }

.feature-link { display: flex; align-items: center; font-size: 10px; font-weight: 800; color: #4e6073; text-transform: uppercase; letter-spacing: 0.05em; text-decoration: none; }
.feature-link .material-symbols-outlined { font-size: 16px; margin-left: 4px; }

.btn-claim { background: white; color: #4e6073; border: none; padding: 10px 24px; border-radius: 12px; font-size: 12px; font-weight: 800; cursor: pointer; box-shadow: 0 4px 12px rgba(0,0,0,0.1); transition: 0.2s; }
.btn-claim:hover { box-shadow: 0 8px 20px rgba(0,0,0,0.15); }

.avatars-group { display: flex; margin-bottom: 24px; }
.avatar-sm { width: 40px; height: 40px; border-radius: 50%; border: 2px solid white; margin-right: -12px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
.avatar-more { width: 40px; height: 40px; border-radius: 50%; border: 2px solid white; background: #dee4da; color: #4e6073; font-size: 10px; font-weight: 800; display: flex; align-items: center; justify-content: center; z-index: 10; padding-left: 2px; }

.loading-state { text-align: center; padding: 80px; color: #5a6159; font-weight: 600; font-size: 18px; }
.spinner { width: 40px; height: 40px; border: 4px solid rgba(78, 96, 115, 0.1); border-top-color: #4e6073; border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 16px; }
@keyframes spin { to { transform: rotate(360deg); } }

@media(max-width: 1024px) {
  .insights-grid { grid-template-columns: 1fr; }
  .bottom-features { grid-template-columns: 1fr; }
  .sidebar { transform: translateX(-100%); transition: 0.3s; }
  .main-wrapper { margin-left: 0; }
  .content-canvas { padding: 24px; }
}
</style>
