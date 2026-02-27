<template>
  <nav class="navbar">
    <div class="navbar-container">
      <div class="navbar-content">
        <!-- Logo Section -->
        <div class="navbar-brand">
          <div class="brand-info">
             <div class="brand-icon">S</div>
             <span class="brand-text">Student System</span>
          </div>
        </div>

        <!-- Navigation Links (Desktop) -->
        <div class="navbar-links">
          <router-link to="/dashboard" class="nav-link" exact-active-class="active">Dashboard</router-link>
          <router-link to="/timetable" class="nav-link" exact-active-class="active">Timetable</router-link>
          <router-link to="/attendance" class="nav-link" exact-active-class="active">Attendance</router-link>
        </div>
        
        <!-- Action Section (Optional) -->
        <div class="navbar-actions">
           <div class="notif-wrapper">
             <NotificationBell class="icon" />
           </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script setup>
import NotificationBell from './NotificationBell.vue';
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

.brand-icon {
  width: 32px;
  height: 32px;
  background: linear-gradient(135deg, #6366f1, #d946ef);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 800;
  font-size: 16px;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
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

/* Responsive Task 5 Task 5 Responsive System Task 5 */
@media (max-width: 640px) {
  .navbar-links {
    display: none; /* Hide on mobile in basic version */
  }
}
</style>
