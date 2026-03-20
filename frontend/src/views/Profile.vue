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
          <img :src="student.profilePic || defaultAvatar" alt="User" class="nav-avatar" />
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
            <img :src="student.profilePic || defaultAvatar" alt="Profile" />
          </router-link>
        </div>
      </header>

      <!-- Content Canvas -->
      <main class="content-canvas">
        
        <!-- Page Header -->
        <section class="page-title-section">
          <h2 class="main-heading">
              User <span class="highlight-text">Profile</span>
          </h2>
          <p class="sub-heading">
              Manage your personal information and academic preferences.
          </p>
        </section>

        <!-- Profile Bento Layout -->
        <div class="profile-bento-grid">
          
          <!-- LEFT COL: Information Grid -->
          <div class="info-card">
             <div class="card-header">
                <h3>Personal Information</h3>
                <span class="material-symbols-outlined header-icon">badge</span>
             </div>
             
             <div class="info-grid">
                <div class="info-item">
                   <span class="info-label">Full Name</span>
                   <p class="info-val">{{ student.name || 'Not provided' }}</p>
                </div>
                <div class="info-item">
                   <span class="info-label">Email Address</span>
                   <p class="info-val">{{ student.email || 'Not provided' }}</p>
                </div>
                <div class="info-item">
                   <span class="info-label">Faculty</span>
                   <p class="info-val">{{ student.faculty || 'Not provided' }}</p>
                </div>
                <div class="info-item">
                   <span class="info-label">Course / Specialization</span>
                   <p class="info-val">{{ student.course || 'Not provided' }}</p>
                </div>
                <div class="info-item">
                   <span class="info-label">Academic Year</span>
                   <p class="info-val">{{ student.academicYear || 'Not provided' }}</p>
                </div>
                <div class="info-item">
                   <span class="info-label">Semester</span>
                   <p class="info-val">{{ student.semester || 'Not provided' }}</p>
                </div>
                <div class="info-item full-width mt-4">
                   <span class="info-label">Academic Bio</span>
                   <p class="info-bio">Passionate {{ student.course || 'software engineering' }} student at StudentX. Continuously learning and building amazing projects.</p>
                </div>
             </div>

             <div class="info-actions">
                <button class="btn-primary">
                   <span class="material-symbols-outlined">edit</span>
                   Edit Profile
                </button>
             </div>
          </div>

           <!-- RIGHT COL: Profile Picture & Status -->
          <div class="status-col">
             
             <!-- Profile Pic Card -->
             <div class="avatar-card">
               <div class="avatar-glow"></div>
               <div class="avatar-wrapper">
                  <img :src="student.profilePic || defaultAvatar" alt="Profile Layout" class="large-avatar"/>
                  <button class="btn-edit-avatar">
                     <span class="material-symbols-outlined">photo_camera</span>
                  </button>
               </div>
               
               <h3 class="profile-name">{{ student.name }}</h3>
               <p class="profile-id">Student ID: #{{ student.id || 'N/A' }}</p>
             </div>

             <!-- Mini Status Card -->
             <div class="mini-status-card">
                <div class="status-icon-box">
                   <span class="material-symbols-outlined text-green">verified_user</span>
                </div>
                <div>
                   <h4 class="status-title">Active Status</h4>
                   <p class="status-desc">Enrolled in {{ student.academicYear || 'current' }} {{ student.semester || 'semester' }}.</p>
                </div>
             </div>

          </div>

        </div>

      </main>

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import studentService from '../services/studentService';

const router = useRouter();

const defaultAvatar = 'https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&q=80&w=250&h=250';

const student = ref({ 
  name: '', 
  id: '',
  email: '',
  faculty: '',
  course: '',
  academicYear: '',
  semester: ''
});

const checkAuth = () => {
    const storedId = localStorage.getItem('studentId');
    if (!storedId) {
        router.push('/login');
        return null;
    }
    return parseInt(storedId);
};

const fetchStudentData = async (uid) => {
    try {
        const response = await studentService.getStudentById(uid);
        if(response.data) {
           student.value = response.data;
        }
    } catch (e) {
        console.error('Error fetching student data:', e);
    }
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    localStorage.removeItem('token');
    router.push('/login');
};

onMounted(() => {
    const uid = checkAuth();
    if(uid) fetchStudentData(uid);
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

.action-btn { background: none; border: none; color: #4e6073; cursor: pointer; padding: 8px; border-radius: 50%; transition: background 0.2s; position: relative; }
.action-btn:hover { background-color: #dee4da; }
.divider { width: 1px; height: 32px; background: rgba(0,0,0,0.1); margin: 0 8px; }
.user-profile { width: 40px; height: 40px; border-radius: 50%; overflow: hidden; border: 2px solid rgba(78, 96, 115, 0.1); cursor: pointer; transition: 0.2s; display: block; }
.user-profile:hover { border-color: #4e6073; transform: scale(1.05); }
.user-profile img { width: 100%; height: 100%; object-fit: cover; }

/* Content Canvas */
.content-canvas { padding: 48px; }

.page-title-section { margin-bottom: 48px; }
.main-heading { font-size: 48px; font-weight: 800; color: #2e342d; line-height: 1.1; margin: 0 0 16px 0; letter-spacing: -0.02em; }
.highlight-text { color: #4e6073; }
.sub-heading { color: #5a6159; font-size: 18px; margin: 0; max-width: 650px; line-height: 1.6; }

/* Profile Bento Grid */
.profile-bento-grid {
  display: grid;
  grid-template-columns: 8fr 4fr;
  gap: 32px;
  align-items: start;
}

/* Info Card (Left) */
.info-card {
  background: white;
  border-radius: 24px;
  padding: 40px;
  box-shadow: 0 20px 50px rgba(46,52,45,0.04);
  border: 1px solid rgba(0,0,0,0.02);
}

.card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; padding-bottom: 24px; border-bottom: 1px solid #ecefe7; }
.card-header h3 { font-size: 20px; font-weight: 800; color: #4e6073; margin: 0; }
.header-icon { color: #adb4aa; font-size: 28px; }

.info-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
}

.info-item { display: flex; flex-direction: column; gap: 8px; }
.full-width { grid-column: 1 / -1; }
.mt-4 { margin-top: 16px; }

.info-label { font-size: 12px; font-weight: 800; color: #adb4aa; text-transform: uppercase; letter-spacing: 0.05em; }
.info-val { font-size: 16px; font-weight: 700; color: #2e342d; margin: 0; }
.info-bio { font-size: 15px; font-weight: 500; color: #5a6159; margin: 0; line-height: 1.6; background: #f3f4ee; padding: 24px; border-radius: 16px; }

.info-actions { margin-top: 48px; display: flex; justify-content: flex-start; }
.btn-primary { 
  display: flex; align-items: center; gap: 8px;
  padding: 14px 32px; background: #4e6073; color: white; border: none; border-radius: 99px;
  font-size: 14px; font-weight: 800; cursor: pointer; box-shadow: 0 10px 25px rgba(78, 96, 115, 0.25);
  transition: transform 0.2s, box-shadow 0.2s;
}
.btn-primary:hover { box-shadow: 0 15px 35px rgba(78, 96, 115, 0.35); transform: translateY(-2px); }

/* Avatar Card (Right Status Col) */
.status-col { display: flex; flex-direction: column; gap: 24px; }

.avatar-card {
  background: white;
  border-radius: 24px;
  padding: 48px 32px;
  box-shadow: 0 20px 50px rgba(46,52,45,0.04);
  border: 1px solid rgba(0,0,0,0.02);
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  position: relative;
  overflow: hidden;
}

.avatar-glow {
  position: absolute;
  top: -50%;
  left: 50%;
  transform: translateX(-50%);
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(78, 96, 115, 0.1), transparent 70%);
  pointer-events: none;
}

.avatar-wrapper { position: relative; width: 140px; height: 140px; margin-bottom: 24px; }
.large-avatar { width: 100%; height: 100%; border-radius: 50%; object-fit: cover; border: 4px solid white; box-shadow: 0 10px 30px rgba(0,0,0,0.1); }
.btn-edit-avatar { position: absolute; bottom: 0; right: 0; background: #4e6073; color: white; width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; border: 3px solid white; cursor: pointer; transition: 0.2s; }
.btn-edit-avatar:hover { transform: scale(1.1); }
.btn-edit-avatar .material-symbols-outlined { font-size: 18px; }

.profile-name { font-size: 24px; font-weight: 800; color: #2e342d; margin: 0 0 8px 0; letter-spacing: -0.01em; }
.profile-id { font-size: 13px; font-weight: 700; color: #5a6159; margin: 0; background: #ecefe7; padding: 6px 16px; border-radius: 99px; display: inline-block; }

/* Mini Status Card */
.mini-status-card { background: #f3f4ee; border-radius: 24px; padding: 24px; display: flex; align-items: center; gap: 16px; border: 1px solid rgba(0,0,0,0.02); transition: 0.3s; }
.mini-status-card:hover { background: #e5eae0; }
.status-icon-box { width: 48px; height: 48px; border-radius: 50%; background: #ecfdf5; border: 1px solid #d1fae5; display: flex; align-items: center; justify-content: center; }
.text-green { color: #10b981; }

.status-title { font-size: 15px; font-weight: 800; color: #2e342d; margin: 0 0 4px 0; }
.status-desc { font-size: 12px; font-weight: 600; color: #5a6159; margin: 0; }

@media(max-width: 1024px) {
  .profile-bento-grid { grid-template-columns: 1fr; }
  .sidebar { transform: translateX(-100%); transition: 0.3s; }
  .main-wrapper { margin-left: 0; }
  .content-canvas { padding: 24px; }
}

@media(max-width: 640px) {
  .info-grid { grid-template-columns: 1fr; gap: 24px; }
}
</style>
