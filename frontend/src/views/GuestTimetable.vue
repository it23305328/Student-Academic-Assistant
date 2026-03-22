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
        <h2 class="page-title">Weekly Schedule</h2>
        <button @click="handleAddSubject" class="btn-add-subject">+ Add Subject</button>
      </header>

      <main class="content-canvas">
        <div class="schedule-grid">
          <div v-for="day in weekDays" :key="day" class="day-col">
            <h4 class="day-label">{{ day }}</h4>
            <div class="day-slots">
              <div v-for="slot in sampleSlots[day]" :key="slot.time" class="time-slot" :class="slot.type">
                <span class="slot-time">{{ slot.time }}</span>
                <span class="slot-subj">{{ slot.subject }}</span>
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

const weekDays = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'];
const sampleSlots = {
    'Mon': [{ time: '08:00', subject: 'Math 101', type: 'lecture' }, { time: '13:00', subject: 'Lab: Physics', type: 'lab' }],
    'Tue': [{ time: '10:00', subject: 'Computer Sci', type: 'lecture' }],
    'Wed': [{ time: '09:00', subject: 'English Lib', type: 'other' }, { time: '14:00', subject: 'Algorithms', type: 'lecture' }],
    'Thu': [{ time: '11:00', subject: 'Data Struct', type: 'lecture' }],
    'Fri': [{ time: '08:00', subject: 'Seminar', type: 'other' }]
};

const handleAddSubject = () => {
    router.push('/login');
};
</script>

<style scoped>
@import "../assets/guest-styles.css";

.main-wrapper { margin-left: 280px; }
.app-header { padding: 24px 40px; display: flex; justify-content: space-between; align-items: center; background: white; border-bottom: 1px solid #f1f5f9; }
.btn-add-subject { background: #4e6073; color: white; border: none; padding: 10px 20px; border-radius: 12px; font-weight: 800; cursor: pointer; }

.schedule-grid { display: grid; grid-template-columns: repeat(5, 1fr); gap: 20px; padding: 10px; }
.day-col { background: #f8fafc; border-radius: 20px; padding: 16px; min-height: 400px; border: 1px solid #f1f5f9; }
.day-label { text-align: center; color: #4e6073; font-weight: 800; margin-bottom: 20px; text-transform: uppercase; letter-spacing: 0.05em; }

.day-slots { display: flex; flex-direction: column; gap: 12px; }
.time-slot { padding: 12px; border-radius: 16px; background: white; border-left: 4px solid #cbd5e1; box-shadow: 0 4px 10px rgba(0,0,0,0.02); }
.time-slot.lecture { border-left-color: #4e6073; background: #eff6ff; }
.time-slot.lab { border-left-color: #f59e0b; background: #fef3c7; }
.slot-time { display: block; font-size: 11px; font-weight: 800; color: #64748b; margin-bottom: 4px; }
.slot-subj { font-size: 13px; font-weight: 700; color: #1e293b; }

/* Sidebar Re-styling (Partial Reuse) */
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
