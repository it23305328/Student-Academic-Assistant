<template>
  <div class="notif-bell-wrapper">
    <!-- Notification Bell Button -->
    <button @click="toggleDropdown" class="notif-btn">
      <svg class="icon bell-icon" :class="{ 'shake': hasUnread }" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
      </svg>
      
      <!-- Status Dot -->
      <span v-if="hasUnread" class="notif-dot"></span>
    </button>

    <!-- Dropdown Menu -->
    <transition name="dropdown">
      <div v-if="isOpen" class="notif-dropdown">
        <div class="notif-header">
          <p class="notif-title">Alerts</p>
          <span class="notif-count">{{ unreadCount }} New</span>
        </div>
        
        <div class="notif-list no-scrollbar">
          <div v-if="isLoading" class="notif-loading">
            <div class="spinner-small"></div>
          </div>
          <div v-else-if="notifications.length === 0" class="notif-empty">
            You're all caught up!
          </div>
          <div v-else>
            <div v-for="note in notifications" :key="note.id" class="notif-item" :class="{ 'unread': !note.read }">
              <div class="notif-icon-box">
                <svg class="icon" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              </div>
              <div class="notif-content">
                <p class="notif-message">{{ note.message }}</p>
                <p class="notif-time">{{ formatDate(note.createdAt) }}</p>
              </div>
              <div v-if="!note.read" class="unread-indicator"></div>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import notificationService from '../services/notificationService';

const isOpen = ref(false);
const isLoading = ref(true);
const notifications = ref([]);

const toggleDropdown = () => {
  isOpen.value = !isOpen.value;
};

const unreadCount = computed(() => notifications.value.filter(n => !n.read).length);
const hasUnread = computed(() => unreadCount.value > 0);

const formatDate = (dateString) => {
  if (!dateString) return 'Just now';
  const date = new Date(dateString);
  const diff = Math.round((date - new Date()) / (1000 * 60 * 60 * 24));
  if (diff === 0) return 'Today';
  return `${Math.abs(diff)}d ago`;
};

const closeOnEscape = (e) => {
  if (isOpen.value && e.key === 'Escape') isOpen.value = false;
};

onMounted(async () => {
  document.addEventListener('keydown', closeOnEscape);
  const studentId = localStorage.getItem('studentId');
  if (!studentId) {
      isLoading.value = false;
      return;
  }

  try {
    const res = await notificationService.getNotifications(studentId);
    notifications.value = res.data || [];
  } catch (err) {
    console.error('Failed to load notifications');
    notifications.value = [];
  } finally {
    isLoading.value = false;
  }
});

onUnmounted(() => {
  document.removeEventListener('keydown', closeOnEscape);
});
</script>

<style scoped>
.notif-bell-wrapper {
  position: relative;
  z-index: 50;
}

.notif-btn {
  position: relative;
  padding: 8px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 50%;
  color: #94a3b8;
  cursor: pointer;
  transition: 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.notif-btn:hover {
  background: rgba(255, 255, 255, 0.06);
  color: white;
}

.icon {
  width: 20px !important;
  height: 20px !important;
}

.notif-dot {
  position: absolute;
  top: 6px;
  right: 6px;
  width: 8px;
  height: 8px;
  background: #ef4444;
  border-radius: 50%;
  border: 2px solid #050511;
  box-shadow: 0 0 10px #ef4444;
}

.notif-dropdown {
  position: absolute;
  top: calc(100% + 12px);
  right: 0;
  width: 320px;
  background: rgba(15, 23, 42, 0.95);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 20px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}

.notif-header {
  padding: 16px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid rgba(255, 255, 255, 0.05);
}

.notif-title { font-size: 14px; font-weight: 700; color: white; margin: 0; }
.notif-count { font-size: 10px; font-weight: 800; background: rgba(34, 211, 238, 0.1); color: #22d3ee; padding: 4px 10px; border-radius: 99px; text-transform: uppercase; }

.notif-list { max-height: 320px; overflow-y: auto; }
.notif-loading { padding: 40px; display: flex; justify-content: center; }
.notif-empty { padding: 40px; text-align: center; color: #64748b; font-size: 13px; }

.notif-item {
  display: flex;
  padding: 16px 20px;
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
  transition: 0.2s;
  cursor: pointer;
  position: relative;
}

.notif-item:hover { background: rgba(255, 255, 255, 0.03); }
.notif-item.unread { background: rgba(34, 211, 238, 0.02); }

.notif-icon-box {
  width: 36px;
  height: 36px;
  background: rgba(249, 115, 22, 0.1);
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fb923c;
  flex-shrink: 0;
}

.notif-content { flex: 1; }
.notif-message { font-size: 13px; color: #cbd5e1; margin: 0 0 4px; line-height: 1.4; }
.notif-item.unread .notif-message { color: white; font-weight: 600; }
.notif-time { font-size: 11px; color: #64748b; margin: 0; }

.unread-indicator {
  width: 6px;
  height: 6px;
  background: #22d3ee;
  border-radius: 50%;
  margin-top: 6px;
  box-shadow: 0 0 8px #22d3ee;
}

.spinner-small {
  width: 20px;
  height: 20px;
  border: 2px solid rgba(34, 211, 238, 0.2);
  border-top-color: #22d3ee;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin { to { transform: rotate(360deg); } }

/* Transitions */
.dropdown-enter-active, .dropdown-leave-active { transition: all 0.2s ease-out; }
.dropdown-enter-from, .dropdown-leave-to { opacity: 0; transform: translateY(-10px) scale(0.95); }

.no-scrollbar::-webkit-scrollbar { display: none; }
</style>
