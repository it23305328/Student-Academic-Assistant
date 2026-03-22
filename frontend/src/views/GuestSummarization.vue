<template>
  <div class="dashboard-root">
    <!-- Sidebar (Same as Dashboard) -->
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
        <button class="hamburger-btn" @click="isSidebarOpen = !isSidebarOpen">
          <span class="material-symbols-outlined">{{ isSidebarOpen ? 'close' : 'menu' }}</span>
        </button>
        <h2 class="page-title">AI Knowledge Summarizer</h2>
      </header>

      <main class="content-canvas">
        <div class="summarizer-box">
          <textarea v-model="text" placeholder="Paste your lecture notes or paste a link to an article here..." class="main-input"></textarea>
          <div class="actions">
            <span class="word-count">Words: {{ wordCount }}</span>
            <button @click="handleSummarize" class="btn-summarize">
               <span class="material-symbols-outlined">auto_awesome</span>
               <span>Summarize (Pro Feature)</span>
            </button>
          </div>
        </div>

        <div class="demo-output">
            <h4 class="demo-title">Example Output Preview</h4>
            <div class="demo-card" @click="handleSummarize">
              <p class="demo-text">Sign in to see how our AI turns thousands of words into bulleted, actionable insights with automated flashcards and study guides.</p>
              <div class="glass-overlay">
                <span class="material-symbols-outlined">lock</span>
                <span>Login Required</span>
              </div>
            </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
const isSidebarOpen = ref(false);
const router = useRouter();

const text = ref('');
const wordCount = computed(() => text.value.split(/\s+/).filter(w => w.length > 0).length);

const handleSummarize = () => {
    router.push('/login');
};
</script>

<style scoped>
@import "../assets/guest-styles.css";

.main-wrapper { margin-left: 280px; }
.app-header { padding: 24px 40px; background: white; border-bottom: 1px solid #f1f5f9; }

.summarizer-box { background: white; border-radius: 24px; padding: 24px; box-shadow: 0 4px 20px rgba(0,0,0,0.03); margin-bottom: 40px; }
.main-input { width: 100%; height: 200px; border: 2px solid #f1f5f9; border-radius: 16px; padding: 20px; font-family: 'Manrope', sans-serif; font-size: 15px; color: #334155; outline: none; transition: border-color 0.2s; resize: none; background: #fafafa; }
.main-input:focus { border-color: #4e6073; background: white; }

.actions { display: flex; justify-content: space-between; align-items: center; margin-top: 20px; }
.word-count { font-size: 13px; font-weight: 700; color: #64748b; }
.btn-summarize { background: #4e6073; color: white; border: none; padding: 12px 24px; border-radius: 14px; font-weight: 800; cursor: pointer; display: flex; align-items: center; gap: 8px; }

.demo-output { margin-top: 40px; }
.demo-title { font-size: 14px; color: #4e6073; margin-bottom: 16px; font-weight: 800; text-transform: uppercase; }
.demo-card { position: relative; background: white; padding: 32px; border-radius: 24px; border: 1px solid #e2e8f0; cursor: pointer; transition: 0.2s; }
.demo-card:hover { border-color: #4e6073; }
.demo-text { color: #64748b; font-size: 14px; line-height: 1.6; }

.glass-overlay { position: absolute; inset: 0; background: rgba(255,255,255,0.7); backdrop-filter: blur(4px); border-radius: 24px; display: flex; flex-direction: column; align-items: center; justify-content: center; gap: 8px; color: #4e6073; font-weight: 800; }

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
@media(max-width: 1024px) {
  .sidebar { 
    transform: translateX(-100%); 
    transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 2000;
  }
  .sidebar.open {
    transform: translateX(0);
  }
  .sidebar-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0,0,0,0.5);
    backdrop-filter: blur(4px);
    z-index: -1;
  }
  .main-wrapper { margin-left: 0; width: 100%; }
  .app-header { padding: 12px 16px; gap: 12px; }
  .hamburger-btn { display: flex !important; }
  .content-canvas { padding: 16px; }
  .page-title { font-size: 20px; }
  .main-input { height: 150px; }
}

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
