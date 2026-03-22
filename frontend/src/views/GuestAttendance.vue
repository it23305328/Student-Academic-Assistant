<template>
  <div class="dashboard-root">
    <!-- Sidebar (Same as Dashboard) -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <div class="brand-wrapper">
          <img src="../assets/studentx_logo.png" alt="StudentX Logo" class="brand-logo" />
          <div class="brand-text">
            <h1 class="brand-title">StudentX</h1>
            <p class="brand-subtitle">Academic Support</p>
          </div>
        </div>
      </div>
      
      <nav class="sidebar-nav">
        <router-link to="/guest-dashboard" class="nav-link">
          <span class="material-symbols-outlined">dashboard</span>
          <span>Overview</span>
        </router-link>
        <router-link to="/guest-timetable" class="nav-link">
          <span class="material-symbols-outlined">calendar_month</span>
          <span>Timetable</span>
        </router-link>
        <router-link to="/guest-attendance" class="nav-link">
          <span class="material-symbols-outlined">assignment_turned_in</span>
          <span>Attendance</span>
        </router-link>
        <router-link to="/guest-summarizer" class="nav-link">
          <span class="material-symbols-outlined">auto_awesome</span>
          <span>Summarization</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="guest-identity-card">
          <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o" alt="Guest" class="guest-avatar" />
          <div class="guest-info">
            <p class="guest-name">Guest Explorer</p>
            <p class="guest-status">Preview Mode</p>
          </div>
        </div>
        <div class="save-data-cta">
          <p>Sign In to Save Data</p>
          <router-link to="/login" class="btn-sign-in-small">Login</router-link>
        </div>
      </div>
    </aside>

    <div class="main-wrapper">
      <header class="app-header">
        <h2 class="page-title">Attendance Insights</h2>
        <button @click="handleMarkAttendance" class="btn-mark-primary">+ Mark Today</button>
      </header>

      <main class="content-canvas">
        <div class="tracker-preview">
          <div class="main-chart-card">
             <div class="card-header">
                <h3 class="card-title">Overall Semester Attendance</h3>
                <span class="perc-val">84%</span>
             </div>
             <div class="bar-grid">
               <div v-for="bar in sampleBars" :key="bar.label" class="bar-item">
                  <div class="bar-fill-wrap">
                    <div class="bar-fill" :style="{ height: bar.val + '%' }"></div>
                  </div>
                  <span class="bar-label">{{ bar.label }}</span>
               </div>
             </div>
          </div>

          <div class="side-mini-cards">
            <div class="mini-card" @click="handleMarkAttendance">
              <span class="material-symbols-outlined">event_available</span>
              <div class="mini-info">
                <h4>Sessions Present</h4>
                <p>124 Total</p>
              </div>
            </div>
            <div class="mini-card warning" @click="handleMarkAttendance">
              <span class="material-symbols-outlined">event_busy</span>
              <div class="mini-info">
                <h4>Sessions Missed</h4>
                <p>12 Total</p>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
const router = useRouter();

const sampleBars = [
    { label: 'Jan', val: 90 },
    { label: 'Feb', val: 78 },
    { label: 'Mar', val: 84 },
    { label: 'Apr', val: 92 }
];

const handleMarkAttendance = () => {
    router.push('/login');
};
</script>

<style scoped>
@import "../assets/guest-styles.css";

.main-wrapper { margin-left: 280px; }
.app-header { padding: 24px 40px; background: white; border-bottom: 1px solid #f1f5f9; display: flex; justify-content: space-between; align-items: center; }
.btn-mark-primary { background: #4e6073; color: white; border: none; padding: 12px 24px; border-radius: 14px; font-weight: 800; cursor: pointer; }

.tracker-preview { display: grid; grid-template-columns: 2fr 1fr; gap: 32px; }

.main-chart-card { background: white; border-radius: 24px; padding: 32px; box-shadow: 0 4px 20px rgba(0,0,0,0.02); }
.card-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 32px; }
.card-title { font-size: 18px; font-weight: 800; color: #1e293b; }
.perc-val { font-size: 32px; font-weight: 800; color: #4e6073; }

.bar-grid { display: grid; grid-template-columns: repeat(4, 1fr); gap: 24px; height: 200px; align-items: flex-end; }
.bar-item { display: flex; flex-direction: column; align-items: center; gap: 12px; }
.bar-fill-wrap { width: 40px; height: 160px; background: #eff6ff; border-radius: 99px; position: relative; overflow: hidden; }
.bar-fill { position: absolute; bottom: 0; left: 0; width: 100%; background: #4e6073; border-radius: 99px; transition: 0.5s ease; }
.bar-label { font-size: 13px; font-weight: 700; color: #64748b; }

.side-mini-cards { display: flex; flex-direction: column; gap: 20px; }
.mini-card { background: white; padding: 24px; border-radius: 24px; display: flex; align-items: center; gap: 16px; border: 1px solid #f1f5f9; cursor: pointer; transition: 0.2s; }
.mini-card:hover { transform: translateY(-4px); border-color: #4e6073; }
.mini-card span { font-size: 32px; color: #4e6073; }
.mini-card.warning span { color: #f59e0b; }
.mini-info h4 { font-size: 14px; font-weight: 800; margin: 0; color: #1e293b; }
.mini-info p { font-size: 12px; color: #64748b; margin: 0; font-weight: 600; }

/* Sidebar Re-styling (Same as Dashboard) */
.sidebar { width: 280px; background: white; height: 100vh; position: fixed; display: flex; flex-direction: column; padding: 32px 0; border-right: 1px solid #f1f5f9; }
.sidebar-header { padding: 0 24px; margin-bottom: 30px; }
.brand-wrapper { display: flex; gap: 10px; align-items: center; }
.brand-logo { height: 40px; }
.brand-title { font-size: 18px; font-weight: 800; color: #4e6073; margin: 0; }
.sidebar-nav { padding: 0 16px; display: flex; flex-direction: column; gap: 8px; }
.nav-link { display: flex; align-items: center; gap: 12px; padding: 14px 20px; border-radius: 14px; text-decoration: none; color: #64748b; font-weight: 700; }
.nav-link.router-link-active { background: #eff6ff; color: #4e6073; }
.sidebar-footer { margin-top: auto; padding: 24px; border-top: 1px solid #f1f5f9; }
.guest-identity-card { display: flex; align-items: center; gap: 12px; margin-bottom: 16px; }
.guest-avatar { width: 40px; height: 40px; border-radius: 12px; }
.guest-name { font-size: 13px; font-weight: 800; margin: 0; color: #1e293b; }
.guest-status { font-size: 11px; color: #64748b; margin: 0; }
.save-data-cta { background: #4e6073; padding: 16px; border-radius: 20px; color: white; text-align: center; }
.save-data-cta p { font-size: 11px; font-weight: 800; margin-bottom: 10px; }
.btn-sign-in-small { background: white; color: #4e6073; text-decoration: none; font-size: 12px; font-weight: 800; padding: 6px; border-radius: 10px; display: block; }
</style>
