<template>
  <div class="attendance-container">
    <div class="chart-header">
      <div class="header-info">
        <h3 class="chart-title">Weekly Attendance</h3>
        <p class="chart-subtitle">Lecture participation over the last 7 days</p>
      </div>
      <div class="header-status">
        <span class="status-indicator"></span>
        <span class="status-text">Live Sync</span>
      </div>
    </div>
    
    <div class="chart-area" ref="chartContainer">
      <!-- Simple Visualization of Attendance using CSS bars -->
      <div class="bars-wrapper">
        <div v-for="(day, index) in days" :key="index" class="bar-column">
          <div class="bar-bar-group">
             <div class="bar-label-top" :style="{ opacity: day.value > 80 ? 1 : 0.6 }">{{ day.value }}%</div>
             <div class="bar-track">
               <div class="bar-fill" :style="{ height: day.value + '%', background: getBarGradient(day.value) }">
                 <div class="bar-glow"></div>
               </div>
             </div>
          </div>
          <span class="day-label">{{ day.name }}</span>
        </div>
      </div>
    </div>
    
    <div class="chart-footer">
       <div class="stat-box">
         <span class="stat-label">Average</span>
         <span class="stat-value">84%</span>
       </div>
       <div class="stat-box">
         <span class="stat-label">Target</span>
         <span class="stat-value text-indigo">85%</span>
       </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const days = ref([
  { name: 'Mon', value: 85 },
  { name: 'Tue', value: 72 },
  { name: 'Wed', value: 94 },
  { name: 'Thu', value: 68 },
  { name: 'Fri', value: 88 },
  { name: 'Sat', value: 45 },
  { name: 'Sun', value: 30 }
]);

const getBarGradient = (val) => {
  if (val < 50) return 'linear-gradient(to top, #ef4444, #f87171)';
  if (val < 75) return 'linear-gradient(to top, #f59e0b, #fbbf24)';
  return 'linear-gradient(to top, #6366f1, #d946ef)';
};
</script>

<style scoped>
.attendance-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 12px;
}

.chart-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

.chart-title {
  font-size: 14px;
  font-weight: 800;
  color: white;
  margin: 0 0 4px;
}

.chart-subtitle {
  font-size: 10px;
  color: #64748b;
  margin: 0;
}

.header-status {
  display: flex;
  align-items: center;
  gap: 6px;
  background: rgba(255, 255, 255, 0.03);
  padding: 4px 10px;
  border-radius: 99px;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.status-indicator {
  width: 5px;
  height: 5px;
  background: #10b981;
  border-radius: 50%;
  box-shadow: 0 0 8px #10b981;
}

.status-text {
  font-size: 9px;
  font-weight: 800;
  color: #10b981;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.chart-area {
  flex: 1;
  display: flex;
  align-items: flex-end;
  padding-bottom: 12px;
}

.bars-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  width: 100%;
  height: 160px;
  gap: 12px;
}

.bar-column {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.bar-bar-group {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
}

.bar-label-top {
  font-size: 9px;
  font-weight: 800;
  color: #94a3b8;
  margin-bottom: 6px;
}

.bar-track {
  width: 12px;
  height: 100%;
  background: rgba(255, 255, 255, 0.03);
  border-radius: 99px;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
}

.bar-fill {
  width: 100%;
  border-radius: 99px;
  transition: height 1s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  position: relative;
}

.bar-glow {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 10px;
  background: inherit;
  filter: blur(8px);
  opacity: 0.5;
}

.day-label {
  font-size: 10px;
  font-weight: 600;
  color: #475569;
}

.chart-footer {
  display: flex;
  gap: 24px;
  margin-top: 16px;
  padding-top: 16px;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
}

.stat-box {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-label {
  font-size: 9px;
  font-weight: 700;
  color: #475569;
  text-transform: uppercase;
}

.stat-value {
  font-size: 14px;
  font-weight: 900;
  color: white;
}

.text-indigo {
  color: #818cf8;
}

/* Responsive Task 5 */
@media (max-width: 640px) {
  .bars-wrapper { gap: 6px; }
  .bar-track { width: 8px; }
}
</style>
