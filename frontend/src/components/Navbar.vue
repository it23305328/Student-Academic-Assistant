<template>
  <nav class="navbar">
    <div class="navbar-container">
      <div class="navbar-content">
        <!-- Logo Section -->
        <div class="navbar-brand">
          <router-link to="/" class="brand-link">
            <img src="../assets/studentx_logo.png" alt="StudentX Logo" class="navbar-logo" />
          </router-link>
        </div>

        <!-- Navigation Links (Desktop) -->
        <div class="navbar-links">
          <template v-if="role === 'STUDENT'">
            <router-link to="/student-dashboard" class="nav-link" exact-active-class="active">Dashboard</router-link>
            <router-link to="/timetable" class="nav-link" exact-active-class="active">Timetable</router-link>
            <router-link to="/attendance" class="nav-link" exact-active-class="active">Attendance</router-link>
          </template>
          <template v-else-if="role === 'ADMIN'">
            <router-link to="/admin-panel" class="nav-link" exact-active-class="active">Admin Panel</router-link>
          </template>
        </div>
        
        <!-- Action Section (Optional) -->
        <div class="navbar-actions">
           <div class="notif-wrapper">
             <NotificationBell class="icon" />
           </div>
           <button v-if="role" @click="handleLogout" class="logout-mini">Logout</button>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import NotificationBell from './NotificationBell.vue';

const router = useRouter();
const role = ref(localStorage.getItem('role'));

onMounted(() => {
  window.addEventListener('storage', () => {
    role.value = localStorage.getItem('role');
  });
});

const handleLogout = () => {
  localStorage.clear();
  role.value = null;
  router.push('/login');
};
</script>

<style scoped>
/* Reset and Icon Sizing Task 4 */
.icon {
  width: 20px !important;
  height: 20px !important;
  min-width: 20px !important;
  min-height: 20px !important;
  flex-shrink: 0;
}

.navbar {
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(15px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 50;
  width: 100%;
}

.navbar-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.navbar-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 64px;
}

.navbar-brand {
  flex-shrink: 0;
}

.brand-info {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-link {
  display: flex;
  align-items: center;
  text-decoration: none;
}

.navbar-logo {
  height: 40px;
  width: auto;
  object-fit: contain;
}

.brand-text {
  color: white;
  font-weight: 800;
  font-size: 18px;
  letter-spacing: -0.02em;
}

.navbar-links {
  display: flex;
  gap: 8px;
}

.nav-link {
  color: #94a3b8;
  text-decoration: none;
  font-size: 13px;
  font-weight: 600;
  padding: 8px 16px;
  border-radius: 12px;
  transition: all 0.2s cubic-bezier(0.4, 0, 0.2, 1);
}

.nav-link:hover {
  background: rgba(255, 255, 255, 0.05);
  color: white;
}

.nav-link.active {
  background: rgba(99, 102, 241, 0.1);
  color: #818cf8;
  border: 1px solid rgba(99, 102, 241, 0.2);
}

.navbar-actions {
  display: flex;
  align-items: center;
}

.notif-wrapper {
  padding: 6px;
  border-radius: 50%;
  cursor: pointer;
  transition: 0.2s;
}

.notif-wrapper:hover {
  background: rgba(255, 255, 255, 0.05);
}

.logout-mini {
  background: rgba(239, 68, 68, 0.1);
  color: #f87171;
  border: 1px solid rgba(239, 68, 68, 0.2);
  padding: 6px 12px;
  border-radius: 8px;
  font-size: 11px;
  font-weight: 700;
  cursor: pointer;
  margin-left: 12px;
  transition: 0.2s;
}

.logout-mini:hover {
  background: rgba(239, 68, 68, 0.2);
  transform: translateY(-1px);
}

/* Responsive Task 5 Task 5 Responsive System Task 5 */
@media (max-width: 640px) {
  .navbar-links {
    display: none; /* Hide on mobile in basic version */
  }
}
</style>
