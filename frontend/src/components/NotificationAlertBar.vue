<template>
  <div v-if="alerts.length > 0" class="alerts-wrapper">
    <div 
      v-for="alert in alerts" 
      :key="alert.id"
      :class="['alert-item', alert.urgent ? 'alert-urgent' : 'alert-warning']"
    >
      <div class="alert-left">
        <div class="icon-box">
          <svg v-if="alert.urgent" class="icon-svg pulse-anim" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
          </svg>
          <svg v-else class="icon-svg" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        </div>
        <div class="text-content">
          <h4 class="alert-title">
            <span v-if="alert.urgent" class="badge-urgent">Urgent</span>
            {{ alert.title }}
          </h4>
          <p class="alert-message">
            {{ alert.message }} • <span class="time-label">{{ alert.time }}</span>
          </p>
        </div>
      </div>
      <button 
        @click="$emit('dismiss', alert.id)"
        class="dismiss-btn"
        title="Dismiss"
      >
        <svg class="icon-small" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
        </svg>
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  alerts: {
    type: Array,
    default: () => []
  }
});

defineEmits(['dismiss']);
</script>

<style scoped>
.alerts-wrapper {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 16px;
  z-index: 50;
}

.alert-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 16px;
  border-radius: 16px;
  border-left: 4px solid transparent;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.3);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

.alert-item:hover {
  transform: translateY(-2px) scale(1.005);
}

/* Urgent Styling (Red) */
.alert-urgent {
  background: rgba(239, 68, 68, 0.15);
  border-left-color: #ef4444;
  color: #fecaca;
}

.alert-urgent .icon-box {
  background: rgba(239, 68, 68, 0.25);
  color: #ef4444;
}

/* Warning Styling (Yellow) */
.alert-warning {
  background: rgba(234, 179, 8, 0.15);
  border-left-color: #eab308;
  color: #fef08a;
}

.alert-warning .icon-box {
  background: rgba(234, 179, 8, 0.25);
  color: #eab308;
}

.alert-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.icon-box {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.icon-svg {
  width: 24px;
  height: 24px;
}

.text-content {
  display: flex;
  flex-direction: column;
}

.alert-title {
  font-size: 16px;
  font-weight: 800;
  margin: 0 0 4px 0;
  display: flex;
  align-items: center;
  line-height: 1;
}

.badge-urgent {
  font-size: 10px;
  font-weight: 900;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  background: #ef4444;
  color: white;
  padding: 4px 8px;
  border-radius: 4px;
  margin-right: 10px;
}

.alert-message {
  font-size: 13px;
  font-weight: 500;
  opacity: 0.85;
  margin: 0;
}

.time-label {
  font-weight: 700;
}

.dismiss-btn {
  background: transparent;
  border: none;
  color: inherit;
  padding: 8px;
  border-radius: 40px;
  cursor: pointer;
  transition: background 0.2s;
}

.dismiss-btn:hover {
  background: rgba(255, 255, 255, 0.1);
}

.icon-small {
  width: 20px;
  height: 20px;
}

/* Animations */
.pulse-anim {
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}
</style>
