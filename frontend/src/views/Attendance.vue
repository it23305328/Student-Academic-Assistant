<template>
  <div class="dashboard-wrapper flex h-screen bg-[#050511] overflow-hidden">
    <!-- Sidebar Navbar Component -->
    <Navbar />
    
    <!-- Main Content Area -->
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      <!-- Top Header Sticky -->
      <header class="header flex items-center justify-between h-[72px] px-6 mb-6 flex-shrink-0">
        <div class="header-left">
          <h1 class="text-2xl font-extrabold text-white m-0">Attendance Insights</h1>
          <p class="text-sm text-gray-500 mt-1 m-0">Monitor and manage your academic presence.</p>
        </div>

        <div class="header-right flex items-center gap-4">
          <div class="header-notif-group flex bg-white/5 border border-white/10 rounded-full p-1">
            <div class="notif-bell-circle w-8 h-8 flex items-center justify-center rounded-full cursor-pointer">
              <Bell class="w-5 h-5 text-gray-400" />
            </div>
          </div>
        </div>
      </header>

      <!-- Main Scrollable Content -->
      <main class="content-scroll flex-1 overflow-y-auto pr-2">
        <div class="attendance-container flex flex-col gap-6 pb-10">
          
          <!-- Summary Chart Component -->
          <div class="card bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg">
            <div class="card-inner p-6 relative z-10">
              <AttendanceChart />
            </div>
          </div>

          <!-- Detailed Grid -->
          <div class="attendance-grid grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-5">
            <div 
              v-for="item in attendanceSummary" 
              :key="item.subjectName" 
              class="card bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg transition-all duration-300 hover:-translate-y-1 hover:border-indigo-500/30"
              :class="{ 'border-red-500/20 bg-red-500/5': item.lowAttendanceWarning }"
            >
              <div class="card-inner p-6 relative z-10">
                <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
                <div class="subject-header flex justify-between items-center mb-5">
                  <h3 class="subject-name text-lg font-bold text-white m-0">{{ item.subjectName }}</h3>
                  <div class="status-badge text-xs font-extrabold px-2.5 py-1 rounded-md uppercase" :class="item.lowAttendanceWarning ? 'bg-red-500/20 text-red-400 border border-red-500/20' : 'bg-green-500/20 text-green-400 border border-green-500/20'">
                    {{ item.lowAttendanceWarning ? 'Warning' : 'Good' }}
                  </div>
                </div>

                <div class="stats-row flex justify-between mb-5">
                  <div class="stat-item flex flex-col gap-1">
                    <span class="stat-lbl text-[11px] text-gray-500 font-semibold">Present</span>
                    <span class="stat-val text-base font-extrabold text-white">{{ item.attendedSessions }}</span>
                  </div>
                  <div class="stat-item flex flex-col gap-1">
                    <span class="stat-lbl text-[11px] text-gray-500 font-semibold">Absent</span>
                    <span class="stat-val text-base font-extrabold text-white">{{ item.totalSessions - item.attendedSessions }}</span>
                  </div>
                  <div class="stat-item flex flex-col gap-1">
                    <span class="stat-lbl text-[11px] text-gray-500 font-semibold">Percentage</span>
                    <span class="stat-val text-base font-extrabold" :class="item.lowAttendanceWarning ? 'text-red-400' : 'text-green-400'">{{ Math.round(item.percentage) }}%</span>
                  </div>
                </div>

                <div class="progress-container w-full h-2 bg-white/5 rounded-full overflow-hidden mb-3">
                  <div 
                    class="progress-fill h-full transition-all duration-1000 ease-out"
                    :class="item.lowAttendanceWarning ? 'bg-gradient-to-r from-red-500 to-red-400 shadow-md shadow-red-500/20' : 'bg-gradient-to-r from-green-500 to-green-400 shadow-md shadow-green-500/20'"
                    :style="{ width: item.percentage + '%' }"
                  ></div>
                </div>

                <p v-if="item.lowAttendanceWarning" class="action-msg text-[11px] font-semibold text-red-400 m-0 uppercase tracking-wide">Minimum 80% required. Need more presence.</p>
              </div>
            </div>
          </div>

          <div v-if="!attendanceSummary.length && !isLoading" class="empty-state text-center py-16 text-gray-500">
            <ClipboardList class="w-12 h-12 mx-auto mb-4 opacity-30" />
            <h3 class="text-lg font-bold text-white mb-2">No data available</h3>
            <p class="text-sm text-gray-500">Mark your attendance from the dashboard to see statistics here.</p>
          </div>

          <div v-if="isLoading" class="loading-overlay flex justify-center py-10">
            <div class="spinner w-10 h-10 border-3 border-indigo-500/10 border-t-indigo-500 rounded-full animate-spin"></div>
          </div>

        </div>
      </main>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { Bell, ClipboardList } from 'lucide-vue-next';
import Navbar from '../components/Navbar.vue';
import AttendanceChart from '../components/AttendanceChart.vue';
import attendanceService from '../services/attendanceService';

const router = useRouter();
const attendanceSummary = ref([]);
const isLoading = ref(true);

const checkAuth = () => {
    const studentId = localStorage.getItem('studentId');
    if (!studentId) {
        router.push('/login');
        return null;
    }
    return studentId;
};

const fetchAttendance = async () => {
    const studentId = checkAuth();
    if (!studentId) return;

    isLoading.value = true;
    try {
        const response = await attendanceService.getAttendanceSummary(studentId);
        attendanceSummary.value = response.data || [];
    } catch (error) {
        console.error('Error fetching attendance:', error);
    } finally {
        isLoading.value = false;
    }
};

const handleLogout = () => {
    localStorage.removeItem('studentId');
    router.push('/login');
};

onMounted(() => {
    fetchAttendance();
});
</script>

<style scoped>
/* Custom animations and scrollbar styling */
@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.animate-spin {
  animation: spin 1s linear infinite;
}

/* Scrollbar styling */
.content-scroll::-webkit-scrollbar {
  width: 4px;
}
.content-scroll::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.1);
  border-radius: 4px;
}
.content-scroll::-webkit-scrollbar-track {
  background: transparent;
}
</style>