<template>
  <div class="dashboard-root">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ 'open': isSidebarOpen }">
      <div v-if="isSidebarOpen" class="sidebar-overlay" @click="isSidebarOpen = false"></div>
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
        <router-link to="/announcements" class="nav-link">
          <span class="material-symbols-outlined">campaign</span>
          <span>Announcements</span>
        </router-link>
        <router-link to="/peer-tutoring" class="nav-link">
          <span class="material-symbols-outlined">groups</span>
          <span>Peer Tutoring</span>
        </router-link>
        <router-link to="/study-group-finder" class="nav-link">
          <span class="material-symbols-outlined">group_work</span>
          <span>Study Group</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="guest-identity-card">
          <div class="guest-avatar-wrapper">
            <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o" alt="Guest" class="guest-avatar" />
            <div class="status-indicator"></div>
          </div>
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

    <!-- Main Content -->
    <div class="main-wrapper">
      <header class="app-header">
        <button class="hamburger-btn" @click="isSidebarOpen = !isSidebarOpen">
          <span class="material-symbols-outlined">{{ isSidebarOpen ? 'close' : 'menu' }}</span>
        </button>
        <div class="header-left">
          <h2 class="page-title">Dashboard Overview</h2>
        </div>
        
        <div class="header-actions">
          <button @click="router.push('/login')" class="btn-gpa-tracker">GPA Tracker</button>
          <div class="divider"></div>
          <router-link to="/login" class="user-profile">
            <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o" alt="Profile" />
          </router-link>
        </div>
      </header>

      <main class="content-canvas">
        <!-- Hero Section -->
        <section class="hero-card">
          <div class="hero-content">
            <span class="session-tag">EXPLORE STUDENTX</span>
            <h2 class="hero-title">Experience Smarter Studying</h2>
            <p class="hero-description">Join thousands of students using AI to streamline their academic life. Get started today for free.</p>
            <button class="btn-primary-action" @click="router.push('/register')">
              <span class="material-symbols-outlined">rocket_launch</span>
              <span>Create Free Account</span>
            </button>
          </div>
          <div class="hero-illustration">
             <img src="https://lh3.googleusercontent.com/aida-public/AB6AXuCdJ40bkENiJQ8dw-OvLLOvXUVRF-vHcA1sreB7YPPL8de5n5LGSXdNn0bxp4Mr_1cw4Gbc_RAGwsKm91xmC_G7248GeYL44Nu_z-9uZLh3LkbkQP7KGXqayIKwVh1C9kLinjdVE_JwkRhfCc7COu-iEpBQGGkusOpXb6Ys_QKQHzoTo9Q4tG3MDOqnJy6PwFES98IlS9G0gbgTSwvO_iL7QtTFRcYsLPCcXrj1Hd3LJOn9w90eVtmp8XQ658lW-qb153VSsGz0AHI" alt="Illustration" />
          </div>
        </section>

        <!-- Preview Grid -->
        <div class="bento-grid">
          <div class="grid-col-8">
            <section class="content-card">
              <div class="card-header-row">
                <h3 class="card-title">Sample Schedule</h3>
                <router-link to="/guest-timetable" class="view-link">View Detailed</router-link>
              </div>
              <div class="sample-schedule">
                <div class="sample-item">
                  <div class="time-block">08:00 AM</div>
                  <div class="subject-block">Advanced Mathematics</div>
                </div>
                <div class="sample-item">
                  <div class="time-block">10:30 AM</div>
                  <div class="subject-block">Software Engineering</div>
                </div>
              </div>
            </section>
          </div>

          <div class="grid-col-4">
            <section class="content-card centered">
              <h3 class="card-title">Academic Standing</h3>
              <div class="radial-placeholder">
                <span class="val">3.85</span>
                <span class="lbl">Expected GPA</span>
              </div>
              <p class="card-hint">Sign in to calculate your real GPA</p>
            </section>
          </div>
        </div>
      </main>
    </div>

    <!-- Mobile Bottom Navigation -->
    <nav class="mobile-bottom-nav">
      <router-link to="/guest-dashboard" class="mobile-nav-item">
        <span class="material-symbols-outlined">dashboard</span>
        <span>Home</span>
      </router-link>
      <router-link to="/guest-timetable" class="mobile-nav-item">
        <span class="material-symbols-outlined">calendar_month</span>
        <span>Schedule</span>
      </router-link>
      <router-link to="/guest-attendance" class="mobile-nav-item">
        <span class="material-symbols-outlined">assignment_turned_in</span>
        <span>Presence</span>
      </router-link>
      <router-link to="/guest-summarizer" class="mobile-nav-item">
        <span class="material-symbols-outlined">auto_awesome</span>
        <span>AI</span>
      </router-link>
    </nav>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
const isSidebarOpen = ref(false);
const router = useRouter();
</script>

<style scoped>
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
  width: 280px;
  background-color: #ffffff;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  flex-direction: column;
  padding: 32px 0;
  border-right: 1px solid rgba(0,0,0,0.05);
  z-index: 50;
}

.sidebar-header { padding: 0 24px; margin-bottom: 40px; }
.brand-wrapper { display: flex; align-items: center; gap: 12px; }
.brand-logo { height: 50px; width: auto; }
.brand-title { font-size: 20px; font-weight: 800; color: #4e6073; margin: 0; }
.brand-subtitle { font-size: 13px; color: #64748b; margin: 0; font-weight: 600; }

.sidebar-nav { padding: 0 16px; display: flex; flex-direction: column; gap: 8px; }
.nav-link {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 14px 24px;
  border-radius: 16px;
  text-decoration: none;
  color: #64748b;
  font-weight: 600;
  transition: all 0.2s;
}
.nav-link:hover { background-color: #f8fafc; color: #4e6073; }
.nav-link.router-link-active { background-color: #eff6ff; color: #4e6073; }

.sidebar-footer {
  margin-top: auto;
  padding: 24px;
  border-top: 1px solid #f1f5f9;
}

.guest-identity-card {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
}

.guest-avatar-wrapper { position: relative; }
.guest-avatar { width: 44px; height: 44px; border-radius: 14px; background: #f1f5f9; object-fit: cover; }
.status-indicator { position: absolute; bottom: -2px; right: -2px; width: 12px; height: 12px; background: #10b981; border: 2px solid white; border-radius: 50%; }

.guest-name { font-size: 14px; font-weight: 800; color: #1e293b; margin: 0; }
.guest-status { font-size: 12px; color: #64748b; margin: 0; }

.save-data-cta {
  background: linear-gradient(135deg, #4e6073, #334155);
  padding: 16px;
  border-radius: 20px;
  color: white;
  text-align: center;
}
.save-data-cta p { font-size: 12px; font-weight: 700; margin-bottom: 12px; opacity: 0.9; }
.btn-sign-in-small {
  display: block;
  background: white;
  color: #4e6073;
  text-decoration: none;
  font-size: 13px;
  font-weight: 800;
  padding: 8px;
  border-radius: 12px;
  transition: transform 0.2s;
}
.btn-sign-in-small:hover { transform: scale(1.02); }

/* Main Wrapper */
.main-wrapper { margin-left: 280px; flex: 1; }

.app-header {
  padding: 24px 48px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  border-bottom: 1px solid #f1f5f9;
}
.page-title { font-size: 20px; font-weight: 800; color: #1e293b; }

.header-actions { display: flex; align-items: center; gap: 20px; }
.btn-gpa-tracker { background: #4e6073; color: white; border: none; padding: 10px 20px; border-radius: 14px; font-weight: 700; cursor: pointer; }
.divider { width: 1px; height: 30px; background: #e2e8f0; }
.user-profile img { width: 40px; height: 40px; border-radius: 12px; cursor: pointer; }

.content-canvas { padding: 40px 48px; }

.hero-card {
  background: #4e6073;
  background-image: radial-gradient(circle at 20% 150%, rgba(255,255,255,0.1) 0%, transparent 50%);
  border-radius: 24px;
  padding: 48px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: white;
  margin-bottom: 40px;
}
.hero-title { font-size: 36px; font-weight: 800; margin: 12px 0; }
.hero-description { font-size: 16px; opacity: 0.9; max-width: 450px; margin-bottom: 32px; }
.btn-primary-action {
  background: white;
  color: #4e6073;
  border: none;
  padding: 14px 28px;
  border-radius: 99px;
  font-weight: 800;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.hero-illustration img { height: 200px; }

.bento-grid { display: grid; grid-template-columns: repeat(12, 1fr); gap: 24px; }
.grid-col-8 { grid-column: span 8; }
.grid-col-4 { grid-column: span 4; }

.content-card { background: white; border-radius: 24px; padding: 32px; box-shadow: 0 4px 20px rgba(0,0,0,0.02); }
.card-header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 24px; }
.card-title { font-size: 18px; font-weight: 800; color: #1e293b; }
.view-link { color: #4e6073; text-decoration: none; font-weight: 700; font-size: 14px; }

.sample-item { display: flex; gap: 20px; padding: 16px; border-radius: 16px; background: #f8fafc; margin-bottom: 12px; }
.time-block { font-weight: 800; color: #4e6073; width: 80px; }
.subject-block { font-weight: 600; color: #334155; }

.radial-placeholder {
  width: 140px;
  height: 140px;
  border-radius: 50%;
  border: 8px solid #eff6ff;
  border-top-color: #4e6073;
  margin: 0 auto 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.radial-placeholder .val { font-size: 28px; font-weight: 800; color: #4e6073; }
.radial-placeholder .lbl { font-size: 12px; font-weight: 700; color: #64748b; }
.card-hint { font-size: 13px; color: #94a3b8; text-align: center; }
.hamburger-btn {
  display: none;
  background: none;
  border: none;
  color: #4e6073;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

/* Mobile Bottom Nav Styles */
.mobile-bottom-nav {
  display: none;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 70px;
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(15px);
  border-top: 1px solid rgba(0, 0, 0, 0.05);
  z-index: 1000;
  justify-content: space-around;
  align-items: center;
  padding: 0 10px;
}

.mobile-nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  text-decoration: none;
  color: #64748b;
  font-size: 10px;
  font-weight: 700;
  transition: 0.3s;
}

.mobile-nav-item.router-link-active {
  color: #4e6073;
}

.mobile-nav-item .material-symbols-outlined {
  font-size: 24px;
}

@media(max-width: 1024px) {
  .mobile-bottom-nav { display: flex; }
  .content-canvas { padding-bottom: 90px; }
  .sidebar { display: none !important; } /* Hide sidebar completely when bottom nav is active */
  .hamburger-btn { display: none !important; }
}

@media(max-width: 1024px) {
  .sidebar { 
    transform: translateX(-100%); 
    transition: transform 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 2000;
  }
  .sidebar.open {
    transform: translateX(0);
  }
  .sidebar-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,0.5);
    backdrop-filter: blur(8px);
    z-index: -1;
  }
  .main-wrapper { margin-left: 0; width: 100%; }
  .app-header { 
    padding: 16px; 
    position: sticky; 
    top: 0; 
    z-index: 100;
    background: rgba(255, 255, 255, 0.9);
    backdrop-filter: blur(10px);
  }
  .hamburger-btn { display: flex !important; }
  .page-title { font-size: 18px; }
  
  .hero-card { 
    padding: 40px 24px; 
    border-radius: 32px;
    background: linear-gradient(135deg, #4e6073 0%, #334155 100%);
  }
  .hero-title { font-size: 24px; line-height: 1.3; }
  .hero-description { font-size: 14px; margin-bottom: 24px; }
  .hero-illustration img { height: 160px; margin-top: 16px; }

  .bento-grid { gap: 16px; }
  .content-card { padding: 24px; border-radius: 28px; border: 1px solid rgba(0,0,0,0.03); }
  .sample-item { padding: 14px; gap: 12px; }
  .time-block { width: 70px; font-size: 13px; }
  .subject-block { font-size: 14px; }
}
</style>
