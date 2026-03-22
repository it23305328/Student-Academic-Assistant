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

    <!-- Main Content -->
    <div class="main-wrapper">
      <!-- Header -->
      <header class="app-header">
        <button class="hamburger-btn" @click="isSidebarOpen = !isSidebarOpen">
          <span class="material-symbols-outlined">{{ isSidebarOpen ? 'close' : 'menu' }}</span>
        </button>
        <div class="search-bar">
          <span class="material-symbols-outlined">search</span>
          <input type="text" placeholder="Search resources, notes..." />
        </div>
        
        <div class="header-actions">
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

      <main class="content-canvas">
        <!-- Hero Section -->
        <section class="summary-hero">
          <h2 class="hero-title">
            AI Lecture <br/> <span class="text-fade">Summarizer</span>
          </h2>
          <p class="hero-desc">
            Transform hours of academic recordings into structured insights in seconds. Our AI distills complex concepts while preserving academic rigor.
          </p>
        </section>

        <!-- Main Workspace: Bento Grid Layout -->
        <div class="summary-bento-grid">
          
          <!-- Upload Zone -->
          <div class="grid-left">
            <div class="upload-card">
               <div class="input-tabs">
                 <button @click="activeTab = 'upload'" :class="{ active: activeTab === 'upload' }" class="tab-btn">Upload PDF</button>
                 <button @click="activeTab = 'paste'" :class="{ active: activeTab === 'paste' }" class="tab-btn">Paste Text</button>
               </div>
               
               <!-- Upload Area -->
               <div v-if="activeTab === 'upload'" class="custom-dashed drop-zone" :class="{ 'dragging': isDragging }" 
                   @dragover.prevent="isDragging = true" @dragleave.prevent="isDragging = false" 
                   @drop.prevent="handleDrop" @click="$refs.fileInput.click()">
                   <input type="file" ref="fileInput" @change="handleFileUpload" accept="application/pdf" hidden>
                   
                   <div class="drop-content">
                      <div class="icon-circle">
                        <span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1;">cloud_upload</span>
                      </div>
                      <h3 class="drop-title">Drop your lecture here</h3>
                      <p v-if="!selectedFile" class="drop-desc">PDF format (Up to 500MB)</p>
                      <p v-else class="file-name">{{ selectedFile.name }}</p>
                      <button class="btn-browse" @click.stop="$refs.fileInput.click()">Browse Files</button>
                   </div>
               </div>

               <!-- Paste Area -->
               <div v-if="activeTab === 'paste'" class="paste-section">
                 <textarea v-model="pastedText" placeholder="Paste your lecture notes here..." class="notes-textarea"></textarea>
               </div>

               <!-- Actions -->
               <div class="actions-row">
                 <button @click="generateSummary" :disabled="isLoading || (!selectedFile && !pastedText)" class="btn-generate">
                   <span v-if="isLoading" class="spinner-small"></span>
                   {{ isLoading ? 'Generating...' : 'Summarize Notes' }}
                 </button>
                 <div v-if="error" class="error-msg">{{ error }}</div>
               </div>
            </div>
          </div>

          <!-- Summary Results Canvas -->
          <div class="grid-right">
            
            <div class="results-header">
              <div class="header-left-title">
                <span class="pulse-dot"></span>
                <h4>Recent Insights</h4>
              </div>
              <button v-if="summary" @click="downloadPDF" class="btn-download">
                <span class="material-symbols-outlined">download</span> Download PDF
              </button>
            </div>

            <!-- Pre-generation mock data cards -->
            <div v-if="!summary && !isLoading" class="insight-cards-stack">
               <div class="insight-card">
                  <div class="insight-icon bg-lightbulb">
                    <span class="material-symbols-outlined">lightbulb</span>
                  </div>
                  <div class="insight-text">
                    <h5>Core Thesis & Objectives</h5>
                    <p>The lecture focused on the intersection of Quantum Computing and Cryptography, specifically focusing on the vulnerability of current RSA encryption standards.</p>
                  </div>
               </div>
               
               <div class="insight-card">
                  <div class="insight-icon bg-list">
                    <span class="material-symbols-outlined">list_alt</span>
                  </div>
                  <div class="insight-text w-full">
                    <h5>Key Conceptual Takeaways</h5>
                    <ul class="insight-list">
                      <li>The concept of "Quantum Supremacy" and its practical implications for blockchain security.</li>
                      <li>Difference between Qubits and classical bits regarding superposition and entanglement.</li>
                    </ul>
                  </div>
               </div>

               <div class="insight-card">
                  <div class="insight-icon bg-error">
                    <span class="material-symbols-outlined">priority_high</span>
                  </div>
                  <div class="insight-text">
                    <h5>Critical Deadlines mentioned</h5>
                    <p>Midterm project proposal due: <span class="txt-error-bold">October 24th, 2023</span>. Must include a simulation of a quantum key distribution protocol.</p>
                  </div>
               </div>
            </div>

            <!-- Loading State -->
            <div v-else-if="isLoading" class="loading-state">
               <div class="spinner"></div>
               <p>Analyzing and synthesizing your lecture...</p>
            </div>

            <!-- Actual Generated Summary -->
            <div v-else-if="summary" class="insight-cards-stack">
               <div class="insight-card generated-card">
                  <div class="insight-icon bg-lightbulb">
                    <span class="material-symbols-outlined">article</span>
                  </div>
                  <div class="insight-text w-full">
                    <h5>Generated Extraction</h5>
                    <ul class="insight-list generated-list mt-4">
                      <li v-for="(line, idx) in formattedSummary" :key="idx">
                         {{ line.replace(/^[-*]\s*/, '') }}
                      </li>
                    </ul>
                  </div>
               </div>
            </div>

          </div>
        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import api from '../services/api';
import axios from 'axios';
import jsPDF from 'jspdf';
import studentService from '../services/studentService';

const router = useRouter();

const isSidebarOpen = ref(false);
const student = ref({ name: '', id: '' });

const activeTab = ref('upload');
const selectedFile = ref(null);
const pastedText = ref('');
const summary = ref('');
const isLoading = ref(false);
const isDragging = ref(false);
const error = ref('');

const formattedSummary = computed(() => {
  if (!summary.value) return [];
  return summary.value.split('\n').filter(line => line.trim().length > 0);
});

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file && file.type === 'application/pdf') {
    selectedFile.value = file;
    error.value = '';
  } else {
    error.value = 'Please select a valid PDF file.';
  }
};

const handleDrop = (event) => {
  isDragging.value = false;
  const file = event.dataTransfer.files[0];
  if (file && file.type === 'application/pdf') {
    selectedFile.value = file;
    error.value = '';
  } else {
    error.value = 'Please drop a valid PDF file.';
  }
};

const generateSummary = async () => {
  isLoading.value = true;
  summary.value = '';
  error.value = '';

  try {
    let response;
    const token = localStorage.getItem('token');
    
    if (activeTab.value === 'upload' && selectedFile.value) {
      const formData = new FormData();
      formData.append('file', selectedFile.value);
      
      response = await axios.post('http://localhost:8082/api/summarizer/upload-pdf', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`
        }
      });
    } else if (activeTab.value === 'paste' && pastedText.value) {
      response = await api.post('/summarizer/text', {
        text: pastedText.value
      }, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
    }

    if (response?.data?.summary) {
      summary.value = response.data.summary;
    } else {
      error.value = 'Failed to get summary. Please try again.';
    }
  } catch (err) {
    console.error('Summarizer error:', err);
    error.value = err.response?.data?.error || 'An error occurred during summarization.';
  } finally {
    isLoading.value = false;
  }
};

const downloadPDF = () => {
  const doc = new jsPDF();
  doc.setFontSize(20);
  doc.setTextColor(78, 96, 115);
  doc.text('Lecture Summary', 10, 20);
  doc.setFontSize(12);
  doc.setTextColor(46, 52, 45);
  
  const splitText = doc.splitTextToSize(summary.value, 180);
  let y = 30;
  
  splitText.forEach(line => {
    if (y > 280) {
      doc.addPage();
      y = 20;
    }
    doc.text(line, 10, y);
    y += 7;
  });
  doc.save('lecture-summary.pdf');
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    localStorage.removeItem('token');
    router.push('/login');
};

const fetchStudentData = async () => {
    const studentId = localStorage.getItem('studentId');
    if(studentId) {
        try {
            const response = await studentService.getStudentById(studentId);
            if(response.data) student.value = response.data;
        } catch (e) {
            console.error('Error fetching student data:', e);
        }
    }
};

onMounted(() => {
    fetchStudentData();
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

/* Sidebar - Exact match with StudentDashboard */
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
.brand-logo { height: 60px; width: auto; object-fit: contain; }
.brand-title { font-size: 20px; font-weight: 800; color: #4e6073; margin: 0; line-height: 1; }
.brand-subtitle { font-size: 14px; font-weight: 600; color: #5a6159; letter-spacing: 0.05em; margin-top: 4px; }



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
.search-bar input { width: 100%; background-color: rgba(222, 228, 218, 0.5); border: none; padding: 10px 16px 10px 48px; border-radius: 99px; font-family: inherit; font-size: 14px; outline: none; transition: box-shadow 0.2s; }
.search-bar input:focus { box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.2); }

.header-actions { display: flex; align-items: center; gap: 24px; }
.action-btn { background: none; border: none; color: #4e6073; cursor: pointer; position: relative; padding: 8px; border-radius: 50%; transition: background 0.2s; }
.action-btn:hover { background-color: #dee4da; }
.divider { width: 1px; height: 32px; background: rgba(0,0,0,0.1); margin: 0 8px; }
.user-profile { width: 40px; height: 40px; border-radius: 50%; overflow: hidden; border: 2px solid rgba(78, 96, 115, 0.1); display: block; cursor: pointer; transition: 0.2s; }
.user-profile:hover { border-color: #4e6073; transform: scale(1.05); }
.user-profile img { width: 100%; height: 100%; object-fit: cover; }

/* Content Canvas */
.content-canvas { padding: 32px 48px; }

/* Hero Section */
.summary-hero {
  max-width: 800px;
  margin-bottom: 48px;
}
.hero-title {
  font-size: 48px;
  font-weight: 800;
  color: #4e6073;
  line-height: 1.1;
  letter-spacing: -0.02em;
  margin: 0 0 16px 0;
}
.text-fade { color: #2e342d; opacity: 0.9; }
.hero-desc {
  font-size: 18px;
  color: #5a6159;
  line-height: 1.6;
  max-width: 600px;
  margin: 0;
}

/* Bento Grid */
.summary-bento-grid {
  display: grid;
  grid-template-columns: 5fr 7fr;
  gap: 32px;
  align-items: start;
}

/* Upload Card (Left) */
.upload-card {
  background: #f3f4ee;
  border-radius: 20px;
  padding: 32px;
  border: 1px solid rgba(78, 96, 115, 0.05);
  display: flex;
  flex-direction: column;
  height: 100%;
}

.input-tabs {
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
}
.tab-btn { background: none; border: none; font-size: 14px; font-weight: 700; color: #5a6159; cursor: pointer; padding: 8px 16px; border-radius: 12px; transition: 0.2s; }
.tab-btn:hover { background: rgba(255,255,255,0.4); }
.tab-btn.active { background: white; color: #4e6073; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }

.custom-dashed {
  background-image: url("data:image/svg+xml,%3csvg width='100%25' height='100%25' xmlns='http://www.w3.org/2000/svg'%3e%3crect width='100%25' height='100%25' fill='none' rx='24' ry='24' stroke='%234e6073' stroke-width='2' stroke-dasharray='8%2c 12' stroke-dashoffset='0' stroke-linecap='square'/%3e%3c/svg%3e");
  border-radius: 24px;
}

.drop-zone {
  min-height: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s ease;
  background: transparent;
}
.drop-zone:hover, .drop-zone.dragging { background: rgba(255,255,255,0.4); }

.drop-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 32px;
}
.icon-circle {
  width: 80px; height: 80px;
  background: rgba(78, 96, 115, 0.1);
  border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  margin-bottom: 32px;
  transition: transform 0.3s;
}
.drop-zone:hover .icon-circle { transform: scale(1.1); }
.icon-circle .material-symbols-outlined { font-size: 40px; color: #4e6073; }
.drop-title { font-size: 24px; font-weight: 700; color: #4e6073; margin: 0 0 8px 0; }
.drop-desc { font-size: 14px; color: #5a6159; margin: 0 0 40px 0; }
.file-name { font-size: 14px; font-weight: 700; color: #4e6073; margin: 0 0 40px 0; background: white; padding: 8px 16px; border-radius: 99px; }
.btn-browse {
  background: #4e6073;
  color: white;
  padding: 16px 40px;
  border-radius: 16px;
  font-weight: 700;
  border: none; cursor: pointer;
  box-shadow: 0 10px 20px rgba(78, 96, 115, 0.1);
  transition: transform 0.2s;
}
.btn-browse:hover { transform: scale(1.02); }
.btn-browse:active { transform: scale(0.95); }

.paste-section { min-height: 400px; display: flex; }
.notes-textarea {
  width: 100%;
  border-radius: 24px;
  border: 1px solid rgba(78, 96, 115, 0.1);
  padding: 24px;
  font-family: inherit;
  font-size: 14px;
  resize: vertical;
  background: white;
  color: #2e342d;
}

.actions-row { margin-top: 24px; }
.btn-generate {
  width: 100%;
  background: #4e6073;
  color: white;
  padding: 16px;
  border-radius: 16px;
  font-size: 15px;
  font-weight: 700;
  border: none; cursor: pointer;
  display: flex; align-items: center; justify-content: center; gap: 8px;
  box-shadow: 0 10px 20px rgba(78, 96, 115, 0.1);
}
.btn-generate:disabled { opacity: 0.6; cursor: not-allowed; }
.spinner-small { width: 20px; height: 20px; border: 3px solid rgba(255,255,255,0.3); border-top-color: white; border-radius: 50%; animation: spin 1s linear infinite; }
@keyframes spin { to { transform: rotate(360deg); } }
.error-msg { color: #9f403d; font-size: 13px; font-weight: 600; text-align: center; margin-top: 12px; }

/* Right Column: Insights */
.grid-right { display: flex; flex-direction: column; gap: 32px; }

.results-header { display: flex; justify-content: space-between; align-items: center; }
.header-left-title { display: flex; align-items: center; gap: 12px; }
.pulse-dot { width: 12px; height: 12px; background: #4e6073; border-radius: 50%; box-shadow: 0 0 10px rgba(78,96,115,0.4); animation: pulse 2s infinite; }
@keyframes pulse { 0% { opacity: 1; transform: scale(1); } 50% { opacity: 0.5; transform: scale(1.2); } 100% { opacity: 1; transform: scale(1); } }
.header-left-title h4 { font-size: 20px; font-weight: 700; color: #4e6073; margin: 0; }
.btn-download { background: #dee4da; color: #4e6073; padding: 10px 24px; border-radius: 99px; font-weight: 700; font-size: 14px; border: none; display: flex; align-items: center; gap: 8px; cursor: pointer; transition: background 0.2s; }
.btn-download:hover { background: #d5dcd0; }

.insight-cards-stack { display: flex; flex-direction: column; gap: 16px; }
.insight-card {
  background: #f3f4ee;
  padding: 32px;
  border-radius: 20px;
  border: 1px solid rgba(78, 96, 115, 0.05);
  display: flex;
  gap: 24px;
  transition: border-color 0.3s;
}
.insight-card:hover { border-color: rgba(78, 96, 115, 0.2); }
.insight-icon { width: 48px; height: 48px; border-radius: 16px; display: flex; align-items: center; justify-content: center; flex-shrink: 0; }
.bg-lightbulb { background: rgba(78, 96, 115, 0.1); color: #4e6073; }
.bg-list { background: rgba(87, 94, 120, 0.1); color: #575e78; }
.bg-error { background: rgba(159, 64, 61, 0.1); color: #9f403d; }
.insight-text h5 { font-size: 18px; font-weight: 700; color: #4e6073; margin: 0 0 12px 0; }
.insight-text p { font-size: 14px; color: #5a6159; line-height: 1.6; margin: 0; }
.txt-error-bold { color: #9f403d; font-weight: 700; }
.w-full { width: 100%; }

.insight-list { list-style: none; padding: 0; margin: 0; display: flex; flex-direction: column; gap: 16px; }
.insight-list li { display: flex; align-items: flex-start; gap: 16px; font-size: 14px; color: #5a6159; line-height: 1.5; }
.insight-list li::before {
  content: '';
  display: block; width: 6px; height: 6px; border-radius: 50%;
  background: #4e6073; margin-top: 8px; flex-shrink: 0;
  transition: transform 0.2s;
}
.insight-list li:hover::before { transform: scale(1.5); }

.loading-state { text-align: center; padding: 60px; color: #5a6159; font-weight: 600; }
.spinner { width: 40px; height: 40px; border: 4px solid rgba(78, 96, 115, 0.1); border-top-color: #4e6073; border-radius: 50%; animation: spin 1s linear infinite; margin: 0 auto 16px; }

.generated-card { flex-direction: column; }
.generated-list { margin-top: 24px; padding-left: 20px; }

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
  .search-bar { display: none; }
  .header-actions { gap: 8px; }
  .content-canvas { padding: 16px; }
  .hero-title { font-size: 32px; }
  .summary-bento-grid { grid-template-columns: 1fr; gap: 16px; }
  .upload-card { padding: 20px; }
  .drop-zone { min-height: 250px; }
  .insight-card { padding: 20px; gap: 16px; }
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
</style>

