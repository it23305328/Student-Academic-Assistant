<template>
  <div class="dashboard-wrapper flex h-screen bg-[#050511] overflow-hidden">
    <!-- Sidebar Navbar Component -->
    <Navbar />
    
    <!-- Main Content Area -->
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      <!-- Top Header Sticky -->
      <header class="header flex items-center justify-between h-14 px-3 mb-3 flex-shrink-0">
        <div class="header-left flex items-center gap-6">
          <h1 class="text-base font-extrabold text-white m-0">Timetable</h1>
          <div class="view-toggle flex bg-white/5 rounded-full p-1 border border-white/10">
            <button 
              @click="setViewMode('today')" 
              :class="[
                'bg-transparent border-none text-gray-400 text-[10px] font-bold px-5 py-1.5 rounded-full cursor-pointer transition-all duration-200',
                viewMode === 'today' ? 'bg-indigo-600 text-white shadow-md shadow-indigo-500/30' : 'hover:bg-white/5 hover:text-white'
              ]"
            >
              Today
            </button>
            <button 
              @click="setViewMode('all')" 
              :class="[
                'bg-transparent border-none text-gray-400 text-[10px] font-bold px-5 py-1.5 rounded-full cursor-pointer transition-all duration-200',
                viewMode === 'all' ? 'bg-indigo-600 text-white shadow-md shadow-indigo-500/30' : 'hover:bg-white/5 hover:text-white'
              ]"
            >
              Full Schedule
            </button>
          </div>
        </div>

        <div class="header-right flex items-center gap-3">
          <button @click="toggleForm" class="bg-indigo-600 text-white border-none rounded-full px-5 py-1.5 text-[11px] font-extrabold cursor-pointer transition-all hover:bg-indigo-500 hover:-translate-y-px shadow-md shadow-indigo-500/30">
            {{ showForm ? 'Close Form' : '+ New Entry' }}
          </button>
          <div class="header-notif-group flex bg-white/5 border border-white/10 rounded-full p-1">
            <div class="notif-bell-circle w-8 h-8 flex items-center justify-center rounded-full cursor-pointer">
              <Bell class="w-5 h-5 text-gray-400" />
            </div>
          </div>
        </div>
      </header>

      <!-- Main Content Scroll Area -->
      <main class="content-scroll flex-1 overflow-y-auto pr-2">
        <div class="timetable-container flex flex-col gap-6 pb-10">
          
          <!-- Add/Edit Form Section -->
          <transition name="fade">
            <div v-if="showForm" class="crud-form-card bg-white/5 backdrop-blur-2xl border border-white/10 rounded-2xl p-6 mb-6 shadow-2xl">
              <div class="form-header mb-5">
                <h3 class="form-title text-sm font-extrabold text-white">{{ editingId ? 'Edit Entry' : 'Add New Subject' }}</h3>
              </div>
              <div class="form-grid grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4 mb-6">
                <div class="input-field flex flex-col gap-2">
                  <label class="text-[10px] font-extrabold text-gray-500 uppercase">Subject Name</label>
                  <input 
                    type="text" 
                    v-model="form.subjectName" 
                    placeholder="e.g. Data Science"
                    class="bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500 focus:bg-white/10 transition-all"
                  >
                </div>
                <div class="input-field flex flex-col gap-2">
                  <label class="text-[10px] font-extrabold text-gray-500 uppercase">Date</label>
                  <input 
                    type="date" 
                    v-model="form.date"
                    class="bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500 focus:bg-white/10 transition-all [&::-webkit-calendar-picker-indicator]:filter [&::-webkit-calendar-picker-indicator]:invert [&::-webkit-calendar-picker-indicator]:opacity-80 [&::-webkit-calendar-picker-indicator]:cursor-pointer hover:[&::-webkit-calendar-picker-indicator]:opacity-100"
                  >
                </div>
                <div class="input-field flex flex-col gap-2">
                  <label class="text-[10px] font-extrabold text-gray-500 uppercase">Start Time</label>
                  <input 
                    type="time" 
                    v-model="form.startTime"
                    class="bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500 focus:bg-white/10 transition-all [&::-webkit-calendar-picker-indicator]:filter [&::-webkit-calendar-picker-indicator]:invert [&::-webkit-calendar-picker-indicator]:opacity-80 [&::-webkit-calendar-picker-indicator]:cursor-pointer"
                  >
                </div>
                <div class="input-field flex flex-col gap-2">
                  <label class="text-[10px] font-extrabold text-gray-500 uppercase">End Time</label>
                  <input 
                    type="time" 
                    v-model="form.endTime"
                    class="bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500 focus:bg-white/10 transition-all [&::-webkit-calendar-picker-indicator]:filter [&::-webkit-calendar-picker-indicator]:invert [&::-webkit-calendar-picker-indicator]:opacity-80"
                  >
                </div>
                <div class="input-field flex flex-col gap-2">
                  <label class="text-[10px] font-extrabold text-gray-500 uppercase">Venue</label>
                  <input 
                    type="text" 
                    v-model="form.venue" 
                    placeholder="e.g. Lab 01"
                    class="bg-white/5 border border-white/10 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500 focus:bg-white/10 transition-all"
                  >
                </div>
              </div>
              <div class="form-actions flex justify-end gap-3">
                <button @click="cancelForm" class="btn-cancel bg-transparent text-gray-500 border border-white/10 px-6 py-2.5 rounded-xl font-bold text-xs cursor-pointer transition-all hover:bg-white/5 hover:text-white">Cancel</button>
                <button @click="saveEntry" class="btn-save bg-indigo-600 text-white border-none px-6 py-2.5 rounded-xl font-extrabold text-xs cursor-pointer transition-all hover:bg-indigo-500 shadow-md shadow-indigo-500/30">{{ editingId ? 'Update Entry' : 'Save Entry' }}</button>
              </div>
            </div>
          </transition>

          <!-- Search (Only for Full View) -->
          <div v-if="viewMode === 'all'" class="search-section mb-4">
            <div class="search-box relative w-72">
              <Search class="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-500" />
              <input 
                type="text" 
                v-model="searchQuery" 
                placeholder="Filter subjects..." 
                class="search-input w-full bg-white/5 border border-white/10 rounded-full py-2.5 pl-9 pr-4 text-white text-xs outline-none focus:border-indigo-500/50 focus:bg-white/10 transition-all"
              >
            </div>
          </div>

          <!-- Loading Overlay -->
          <div v-if="isLoading" class="loading-overlay flex flex-col items-center justify-center py-16">
            <div class="spinner w-8 h-8 border-3 border-indigo-500/20 border-t-indigo-500 rounded-full animate-spin mb-4"></div>
            <p class="loading-text text-gray-500 text-sm">Loading secure data...</p>
          </div>

          <!-- Empty State -->
          <div v-else-if="filteredTimetable.length === 0" class="empty-placeholder text-center py-16">
            <Calendar class="w-12 h-12 text-gray-500 mx-auto mb-4 opacity-30" />
            <p class="text-gray-500 text-sm">Your timetable is empty. Add a new entry to get started.</p>
          </div>

          <!-- Timetable Content -->
          <div v-else class="timetable-content">
            
            <!-- Today's View List -->
            <div v-if="viewMode === 'today'" class="timetable-today-grid grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
              <div v-for="entry in filteredTimetable" :key="entry.id" class="entry-card bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl p-6 relative overflow-hidden transition-all duration-300 hover:-translate-y-1 hover:border-indigo-500/30">
                <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
                <div class="entry-header flex justify-between items-start mb-5">
                  <div class="subject-group flex items-center gap-3">
                    <div class="accent-bar w-1 h-5 bg-indigo-500 rounded-full"></div>
                    <h3 class="subject-name text-sm font-extrabold text-white m-0">{{ entry.subjectName }}</h3>
                  </div>
                  <div class="action-icons flex gap-2">
                    <button @click="editEntry(entry)" class="icon-btn edit bg-transparent border-none text-gray-500 p-1.5 rounded-lg cursor-pointer transition-all hover:text-indigo-400 hover:bg-indigo-500/10">
                      <Edit2 class="w-4 h-4" />
                    </button>
                    <button @click="deleteEntry(entry.id)" class="icon-btn delete bg-transparent border-none text-gray-500 p-1.5 rounded-lg cursor-pointer transition-all hover:text-red-400 hover:bg-red-500/10">
                      <Trash2 class="w-4 h-4" />
                    </button>
                  </div>
                </div>
                <div class="entry-details flex flex-col gap-3">
                  <div class="detail-row flex items-center gap-2.5 text-xs text-gray-400">
                    <Clock class="w-4 h-4 text-indigo-400" />
                    <span>{{ formatTime(entry.startTime) }} - {{ formatTime(entry.endTime) }}</span>
                  </div>
                  <div class="detail-row flex items-center gap-2.5 text-xs text-gray-400">
                    <MapPin class="w-4 h-4 text-purple-400" />
                    <span>{{ entry.venue || 'Virtual Lab' }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- Full Weekly Grid View -->
            <div v-else class="timetable-grid-wrapper bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-xl">
              <div class="table-responsive w-full overflow-x-auto">
                <table class="weekly-grid-table w-full border-separate border-spacing-0.5 min-w-[800px]">
                  <thead>
                    <tr>
                      <th class="time-col w-12 px-1 py-1.5 text-[8px] font-extrabold text-gray-500 text-center bg-white/5 rounded">TIME</th>
                      <th v-for="day in weekDays" :key="day" class="day-header px-1 py-1.5 text-[8px] font-extrabold text-gray-500 text-center bg-white/5 rounded">{{ day.toUpperCase() }}</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="hour in timeSlots" :key="hour">
                      <td class="time-cell text-center text-[8px] font-extrabold text-gray-600 py-1.5 bg-black/20 rounded">{{ hour.toString().padStart(2, '0') }}:00</td>
                      <td v-for="day in weekDays" :key="day" class="grid-cell bg-white/5 border border-white/5 rounded h-14 align-top p-0.5">
                        <div v-for="entry in weeklyGrid[day][hour]" :key="entry.id" class="grid-entry relative bg-indigo-500/10 border border-indigo-500/20 rounded p-1 h-full cursor-pointer transition-all hover:bg-indigo-500/20 hover:-translate-y-0.5 hover:shadow-md hover:shadow-indigo-500/20" @click="editEntry(entry)">
                          <div class="entry-slot-bg absolute inset-0 bg-gradient-to-tr from-indigo-500/20 to-transparent pointer-events-none rounded"></div>
                          <div class="entry-inner relative z-10 flex flex-col gap-1">
                            <div class="entry-header-mini flex justify-between items-start gap-1">
                              <span class="entry-subject text-[9px] font-extrabold text-white leading-tight break-words">{{ entry.subjectName }}</span>
                              <span class="entry-date-tag text-[8px] font-extrabold text-indigo-400 bg-indigo-500/10 px-1.5 py-0.5 rounded uppercase flex-shrink-0">{{ formatDateShort(entry.date) }}</span>
                            </div>
                            <div class="entry-meta flex items-center gap-1 text-[7px] text-gray-400">
                              <MapPin class="w-2.5 h-2.5" />
                              <span>{{ entry.venue || 'TBA' }}</span>
                            </div>
                          </div>
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <!-- Future Events Section -->
            <div v-if="viewMode === 'all' && futureEvents.length > 0" class="future-events-section mt-10">
              <h2 class="future-section-title text-sm font-extrabold text-gray-500 uppercase mb-5 flex items-center gap-4">
                Upcoming Beyond This Week
                <span class="flex-1 h-px bg-gradient-to-r from-white/10 to-transparent"></span>
              </h2>
              <div class="future-cards-grid grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                <div v-for="event in futureEvents" :key="event.id" class="future-card bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl p-5 relative overflow-hidden transition-all duration-300 cursor-pointer hover:-translate-y-2 hover:scale-[1.02] hover:bg-white/10 hover:border-indigo-500/40 hover:shadow-2xl" @click="editEntry(event)">
                  <div class="card-glass-effect absolute top-0 left-0 right-0 h-full bg-gradient-to-tr from-indigo-500/10 to-transparent pointer-events-none"></div>
                  <div class="future-card-header flex justify-between items-start mb-5 relative z-10">
                    <h3 class="future-subject-name text-base font-extrabold text-white max-w-[65%] leading-tight">{{ event.subjectName }}</h3>
                    <div class="days-remaining-badge bg-indigo-500/10 text-indigo-400 px-3 py-1.5 rounded-lg text-[10px] font-extrabold border border-indigo-500/20 whitespace-nowrap">{{ getDaysRemaining(event.date) }}</div>
                  </div>
                  <div class="future-card-body flex flex-col gap-3.5 relative z-10">
                    <div class="future-detail flex items-center gap-3 text-xs text-gray-400">
                      <Calendar class="w-4 h-4 text-blue-400" />
                      <span>{{ formatDateLong(event.date) }}</span>
                    </div>
                    <div class="future-detail flex items-center gap-3 text-xs text-gray-400">
                      <Clock class="w-4 h-4 text-purple-400" />
                      <span>{{ formatTime(event.startTime) }} - {{ formatTime(event.endTime) }}</span>
                    </div>
                    <div class="future-detail flex items-center gap-3 text-xs text-gray-400">
                      <MapPin class="w-4 h-4 text-pink-400" />
                      <span>{{ event.venue || 'Virtual Lab' }}</span>
                    </div>
                  </div>
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
import { Bell, Search, Calendar, Clock, MapPin, Edit2, Trash2 } from 'lucide-vue-next';
import timetableService from '../services/timetableService';
import Navbar from '../components/Navbar.vue';

const router = useRouter();

const viewMode = ref('today');
const timetableData = ref([]);
const searchQuery = ref('');
const isLoading = ref(true);
const studentId = ref(null);

const showForm = ref(false);
const editingId = ref(null);
const form = ref({
  subjectName: '',
  date: '',
  startTime: '',
  endTime: '',
  venue: ''
});

const weekDays = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'];
const timeSlots = [8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18];

const checkAuth = () => {
  const storedId = localStorage.getItem('studentId');
  if (!storedId) {
    router.push('/login');
    return false;
  }
  studentId.value = parseInt(storedId);
  return true;
};

const fetchData = async () => {
  if (!checkAuth()) return;
  isLoading.value = true;
  try {
    let response;
    if (viewMode.value === 'today') {
      response = await timetableService.getTodayTimetable(studentId.value);
    } else {
      response = await timetableService.getAllTimetable(studentId.value);
    }
    timetableData.value = response.data || [];
  } catch (error) {
    console.error('Failed to fetch timetable:', error);
  } finally {
    isLoading.value = false;
  }
};

const toggleForm = () => {
  showForm.value = !showForm.value;
  if (!showForm.value) cancelForm();
};

const cancelForm = () => {
  editingId.value = null;
  form.value = { subjectName: '', date: '', startTime: '', endTime: '', venue: '' };
  showForm.value = false;
};

const saveEntry = async () => {
  if (!form.value.subjectName || !form.value.date || !form.value.startTime || !form.value.endTime) {
    alert("Please fill in all required fields.");
    return;
  }

  try {
    const payload = { ...form.value, studentId: studentId.value };
    if (editingId.value) {
      await timetableService.updateEntry(editingId.value, studentId.value, payload);
    } else {
      await timetableService.addEntry(payload);
    }
    cancelForm();
    fetchData();
  } catch (error) {
    alert(error.response?.data || "Operation failed.");
  }
};

const editEntry = (entry) => {
  editingId.value = entry.id;
  form.value = { ...entry };
  showForm.value = true;
};

const deleteEntry = async (id) => {
  if (!confirm("Are you sure you want to delete this schedule?")) return;
  try {
    await timetableService.deleteEntry(id, studentId.value);
    fetchData();
  } catch (error) {
    alert("Delete failed.");
  }
};

const handleLogout = () => {
  localStorage.removeItem('studentId');
  router.push('/login');
};

const setViewMode = (mode) => {
  viewMode.value = mode;
  fetchData();
};

const formatTime = (time) => {
  if (!time) return '';
  const timeStr = time.length === 5 ? time : time.substring(0, 5);
  return timeStr;
};

const formatDateShort = (dateStr) => {
  if (!dateStr) return '';
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
};

const getCurrentWeekRange = () => {
  const now = new Date();
  const day = now.getDay();
  const diffToMonday = (day === 0 ? -6 : 1 - day);
  
  const monday = new Date(now);
  monday.setDate(now.getDate() + diffToMonday);
  monday.setHours(0, 0, 0, 0);
  
  const friday = new Date(monday);
  friday.setDate(monday.getDate() + 4);
  friday.setHours(23, 59, 59, 999);
  
  return { monday, friday };
};

const getDaysRemaining = (dateStr) => {
  const eventDate = new Date(dateStr);
  eventDate.setHours(0, 0, 0, 0);
  const today = new Date();
  today.setHours(0, 0, 0, 0);
  const diffTime = eventDate - today;
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  
  if (diffDays === 0) return 'Today';
  if (diffDays === 1) return 'Tomorrow';
  if (diffDays < 0) return `${Math.abs(diffDays)} Days Ago`;
  return `In ${diffDays} Days`;
};

const formatDateLong = (dateStr) => {
  const date = new Date(dateStr);
  return date.toLocaleDateString('en-US', { month: 'long', day: 'numeric' });
};

const filteredTimetable = computed(() => {
  if (!searchQuery.value) return timetableData.value;
  return timetableData.value.filter(entry => 
    entry.subjectName.toLowerCase().includes(searchQuery.value.toLowerCase())
  );
});

const currentWeekEvents = computed(() => {
  const { monday, friday } = getCurrentWeekRange();
  return filteredTimetable.value.filter(entry => {
    const d = new Date(entry.date);
    return d >= monday && d <= friday;
  });
});

const futureEvents = computed(() => {
  const { friday } = getCurrentWeekRange();
  return filteredTimetable.value.filter(entry => {
    const d = new Date(entry.date);
    return d > friday;
  });
});

const weeklyGrid = computed(() => {
  const grid = {};
  weekDays.forEach(day => {
    grid[day] = {};
    timeSlots.forEach(hour => {
      grid[day][hour] = [];
    });
  });

  currentWeekEvents.value.forEach(entry => {
    const date = new Date(entry.date);
    const dayName = date.toLocaleDateString('en-US', { weekday: 'long' });
    
    if (weekDays.includes(dayName)) {
      const hour = parseInt(entry.startTime.split(':')[0]);
      if (grid[dayName] && grid[dayName][hour]) {
        grid[dayName][hour].push(entry);
      }
    }
  });

  return grid;
});

onMounted(() => {
  fetchData();
});
</script>

<style scoped>
/* Animations */
@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.animate-spin {
  animation: spin 1s linear infinite;
}

/* Fade transitions */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s, transform 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
  transform: translateY(-10px);
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