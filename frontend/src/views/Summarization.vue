<template>
  <div class="summarization-wrapper">
    
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
          <router-link to="/student-dashboard" class="nav-item">
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

          <router-link to="/summarization" class="nav-item active">
            <svg class="icon nav-link-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
            </svg>
            <span class="nav-text">Summarization</span>
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
      <header class="header">
        <div class="header-left">
           <h1 class="header-title">AI Lecture Summarizer</h1>
           <p class="header-subtitle">Turn complex lectures into concise study guides.</p>
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
        <div class="summarization-container">
          <SummarizerCard />
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import SummarizerCard from '../components/SummarizerCard.vue';
import NotificationBell from '../components/NotificationBell.vue';

const router = useRouter();

const handleLogout = () => {
    localStorage.removeItem('studentId');
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    router.push('/login');
};
</script>

<style scoped>
.summarization-wrapper {
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

.summarization-container { 
  max-width: 1000px;
  margin: 0 auto;
  padding-bottom: 40px;
}

@media (max-width: 768px) {
  .sidebar { position: fixed; left: -260px; }
  .main-layout { padding: 12px; }
}
</style>
