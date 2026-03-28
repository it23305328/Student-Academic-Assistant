<template>
  <div class="dashboard-root">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ 'open': isSidebarOpen }">
      <div v-if="isSidebarOpen" class="sidebar-overlay" @click="isSidebarOpen = false"></div>
      <div class="sidebar-header">
        <div class="brand-wrapper">
          <img src="../../assets/studentx_logo.png" alt="StudentX Logo" class="brand-logo" />
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
        <router-link to="/announcements" class="nav-link">
          <span class="material-symbols-outlined">campaign</span>
          <span>Announcements</span>
        </router-link>
        <router-link to="/peer-tutoring" class="nav-link">
          <span class="material-symbols-outlined">groups</span>
          <span>Peer Tutoring</span>
        </router-link>
        <router-link to="/study-group-finder" class="nav-link">
          <span class="material-symbols-outlined">group_work</span>
          <span>Study Group</span>
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

    <!-- Main Content Wrapper -->
    <div class="main-wrapper">
      <!-- App Header -->
      <header class="app-header">
        <button class="hamburger-btn" @click="isSidebarOpen = !isSidebarOpen">
          <span class="material-symbols-outlined">{{ isSidebarOpen ? 'close' : 'menu' }}</span>
        </button>
        <div class="search-bar">
          <span class="material-symbols-outlined">search</span>
          <input type="text" v-model="searchQuery" placeholder="Search sessions or topics..." />
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

      <!-- Content Canvas -->
      <main class="content-canvas">
        <div class="max-w-6xl mx-auto px-6 py-12">
          <div class="mb-12">
            <div class="flex items-center gap-2 mb-2">
              <div class="h-[1px] w-8 bg-[#A89060]"></div>
              <span class="text-[10px] font-black uppercase tracking-[0.3em] text-[#A89060]">Community Knowledge</span>
            </div>
            <h1 class="text-5xl md:text-6xl font-serif font-bold text-[#1A1A1A] mb-4 italic">Peer Tutoring</h1>
            <p class="text-sm text-gray-500 max-w-lg leading-relaxed">Volunteer to teach or find professional guidance for specific academic modules and chapters.</p>
          </div>

          <div class="mb-12 flex gap-4">
            <button 
              @click="openCreateModal" 
              class="bg-[#1A1A1A] text-white px-8 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all flex items-center gap-3 shadow-lg shadow-black/5"
            >
              <GraduationCap class="w-5 h-5" />
              Offer Tutoring Session
            </button>
            
            <button 
              @click="loadSessions" 
              class="border-2 border-[#1A1A1A] text-[#1A1A1A] px-8 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#1A1A1A] hover:text-white transition-all flex items-center gap-3"
            >
              <RefreshCw class="w-5 h-5" />
              Refresh
            </button>
          </div>

          <div v-if="loading" class="bg-white border border-gray-100 rounded-3xl p-24 text-center shadow-sm">
            <div class="inline-block w-10 h-10 border-2 border-gray-50 border-t-[#A89060] rounded-full animate-spin mb-6"></div>
            <p class="text-gray-400 text-[10px] font-black tracking-[0.2em] uppercase">Retrieving Sessions</p>
          </div>

          <div v-else-if="tutoringSessions.length === 0" class="bg-white border border-gray-100 rounded-3xl p-24 text-center shadow-sm">
            <GraduationCap class="w-16 h-16 text-gray-100 mx-auto mb-6" />
            <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">No Active Sessions</h3>
            <p class="text-gray-400 text-sm italic">Be the first to curate a learning experience.</p>
          </div>

          <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div 
              v-for="session in filteredSessions" 
              :key="session.id" 
              class="group bg-white border border-gray-100 rounded-2xl transition-all duration-500 hover:shadow-2xl hover:shadow-[#A89060]/10 hover:-translate-y-2 overflow-hidden flex flex-col"
            >
              <div class="p-8 flex-1">
                <div class="flex items-start gap-5 mb-6">
                  <div class="w-14 h-14 bg-[#F8F9FA] rounded-2xl flex items-center justify-center group-hover:bg-[#1A1A1A] transition-colors duration-500">
                    <GraduationCap class="w-7 h-7 text-[#A89060]" />
                  </div>
                  <div class="flex-1">
                    <span class="text-[9px] font-black uppercase tracking-widest text-[#A89060] mb-1 block">Module</span>
                    <h3 class="font-serif font-bold text-xl text-[#1A1A1A] mb-1 leading-tight">{{ session.module }}</h3>
                    <p class="text-[11px] font-bold text-gray-400 uppercase tracking-tighter">Instructor: {{ session.tutorName }}</p>
                  </div>
                </div>

                <div class="space-y-4 mb-8">
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <BookOpen class="w-4 h-4 text-[#A89060]" />
                    <span>{{ session.chapter }}</span>
                  </div>
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <Calendar class="w-4 h-4 text-[#A89060]" />
                    <span>{{ formatDate(session.date) }} • {{ session.time }}</span>
                  </div>
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <Users class="w-4 h-4 text-[#A89060]" />
                    <span>Year {{ session.year }}, Sem {{ session.semester }}</span>
                  </div>
                  <div class="inline-block px-3 py-1 bg-[#F8F9FA] rounded text-[10px] font-black text-gray-400 uppercase tracking-widest border border-gray-100">
                    {{ session.specialization }}
                  </div>
                </div>

                <!-- Show different buttons based on session ownership -->
                <div v-if="isSessionCreator(session)" class="flex gap-3">
                  <button 
                    @click="openEditModal(session)" 
                    class="flex-1 border-2 border-[#A89060] text-[#A89060] px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-[#A89060] hover:text-white transition-all"
                  >
                    <Edit class="w-4 h-4 inline mr-2" />
                    Edit Session
                  </button>
                  <button 
                    @click="confirmDelete(session)" 
                    class="flex-1 border-2 border-red-500 text-red-500 px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-red-500 hover:text-white transition-all"
                  >
                    <Trash2 class="w-4 h-4 inline mr-2" />
                    Delete
                  </button>
                </div>
                <a 
                  v-else
                  :href="session.meetingLink" 
                  target="_blank"
                  class="block w-full border-2 border-[#1A1A1A] text-[#1A1A1A] px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-[#1A1A1A] hover:text-white transition-all text-center"
                >
                  Join Session
                </a>
              </div>
            </div>
          </div>
        </div>
      </main>
    </div>

    <!-- Create/Edit Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4">
      <div class="relative bg-white rounded-3xl w-full max-w-lg p-10 max-h-[90vh] overflow-y-auto shadow-2xl">
        <!-- Close button added here -->
        <button 
          @click="closeCreateModal" 
          class="absolute top-6 right-6 text-gray-400 hover:text-[#1A1A1A] transition-colors text-2xl leading-none z-10"
          aria-label="Close modal"
        >
          &times;
        </button>
        
        <div class="mb-8">
          <h2 class="text-3xl font-serif font-bold text-[#1A1A1A] mb-2">{{ isEditing ? 'Edit Session' : 'Offer Session' }}</h2>
          <div class="h-1 w-12 bg-[#A89060]"></div>
        </div>
        
        <form @submit.prevent="saveSession" class="space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Your Name</label>
              <input v-model="newSession.tutorName" type="text" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Email</label>
              <input v-model="newSession.tutorEmail" type="email" required readonly class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all bg-gray-100 cursor-not-allowed">
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Phone Number</label>
            <input 
              v-model="newSession.tutorPhone" 
              type="tel" 
              required 
              pattern="07[0-9]{8}"
              maxlength="10"
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              placeholder="0712345678"
            >
            <p class="text-xs text-gray-400 mt-1">Must start with 07 and be exactly 10 digits</p>
          </div>
          
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Year</label>
              <select v-model="newSession.year" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Year</option>
                <option value="1">1st Year</option>
                <option value="2">2nd Year</option>
                <option value="3">3rd Year</option>
                <option value="4">4th Year</option>
              </select>
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Semester</label>
              <select v-model="newSession.semester" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Semester</option>
                <option value="1">Semester 1</option>
                <option value="2">Semester 2</option>
              </select>
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Module Title</label>
            <input 
              v-model="newSession.module" 
              type="text" 
              required 
              placeholder="e.g., Computer Architecture" 
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              @input="validateTextAndNoSpecialChars($event, 'module')"
            >
            <p v-if="validationErrors.module" class="text-xs text-red-500 mt-1">{{ validationErrors.module }}</p>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specialization</label>
            <input 
              v-model="newSession.specialization" 
              type="text" 
              required 
              placeholder="e.g., Computer Science" 
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              @input="validateTextAndNoSpecialChars($event, 'specialization')"
            >
            <p v-if="validationErrors.specialization" class="text-xs text-red-500 mt-1">{{ validationErrors.specialization }}</p>
          </div>

          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specific Chapter</label>
            <input 
              v-model="newSession.chapter" 
              type="text" 
              required 
              placeholder="e.g., Memory Management" 
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              @input="validateTextAndNoSpecialChars($event, 'chapter')"
            >
            <p v-if="validationErrors.chapter" class="text-xs text-red-500 mt-1">{{ validationErrors.chapter }}</p>
          </div>
          
          <div class="grid grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Session Date</label>
              <input v-model="newSession.date" type="date" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Start Time</label>
              <input v-model="newSession.time" type="time" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Meeting/WhatsApp Link</label>
            <input v-model="newSession.meetingLink" type="url" required placeholder="https://..." class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div class="flex gap-4 pt-6">
            <button type="button" @click="closeCreateModal" class="flex-1 px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all">Cancel</button>
            <button type="submit" :disabled="submitting || hasValidationErrors" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
              {{ submitting ? (isEditing ? 'Updating...' : 'Publishing...') : (isEditing ? 'Update' : 'Publish') }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div v-if="showDeleteModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeDeleteModal">
      <div class="relative bg-white rounded-3xl w-full max-w-md p-8 shadow-2xl" @click.stop>
        <div class="text-center">
          <div class="w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-4">
            <Trash2 class="w-10 h-10 text-red-500" />
          </div>
          <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">Delete Session</h3>
          <p class="text-gray-500 mb-6">Are you sure you want to delete this tutoring session? This action cannot be undone.</p>
          <div class="flex gap-4">
            <button @click="closeDeleteModal" class="flex-1 px-6 py-3 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all border-2 border-gray-200">Cancel</button>
            <button @click="deleteSession" :disabled="deleting" class="flex-1 bg-red-500 text-white px-6 py-3 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-red-600 transition-all disabled:opacity-50 disabled:cursor-not-allowed">
              {{ deleting ? 'Deleting...' : 'Delete' }}
            </button>
          </div>
        </div>
      </div>
    </div>

    <!-- Toast Notifications Container - Top Right -->
    <div class="toast-container fixed top-6 right-6 z-[9999] flex flex-col gap-3">
      <transition-group name="toast">
        <div v-for="toast in notificationToasts" :key="toast.id" class="toast-item bg-white rounded-2xl shadow-2xl border border-gray-100 overflow-hidden flex items-center gap-4 p-4 min-w-[280px] max-w-[380px]" :class="{
          'border-l-4 border-l-[#1A1A1A]': toast.type === 'success',
          'border-l-4 border-l-red-500': toast.type === 'error'
        }">
          <div class="toast-icon flex-shrink-0">
            <CheckCircle v-if="toast.type === 'success'" class="w-5 h-5 text-[#1A1A1A]" />
            <XCircle v-else-if="toast.type === 'error'" class="w-5 h-5 text-red-500" />
          </div>
          <div class="toast-content flex-1">
            <p class="text-xs font-medium text-gray-800">{{ toast.message }}</p>
          </div>
          <button @click="dismissToast(toast.id)" class="toast-close text-gray-300 hover:text-gray-600 transition-colors text-xl leading-none">&times;</button>
        </div>
      </transition-group>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { GraduationCap, Calendar, BookOpen, Users, RefreshCw, Edit, Trash2, CheckCircle, XCircle } from 'lucide-vue-next';
import tutoringService from '../../services/tutoringService';
import studentService from '../../services/studentService';

const router = useRouter();
const tutoringSessions = ref([]);
const loading = ref(true);
const showCreateModal = ref(false);
const showDeleteModal = ref(false);
const submitting = ref(false);
const deleting = ref(false);
const isEditing = ref(false);
const editingId = ref(null);
const notificationToasts = ref([]);
const searchQuery = ref('');
const isSidebarOpen = ref(false);
const student = ref({ name: '', id: '', profilePic: '' });
const studentId = ref(null);

// Validation errors object
const validationErrors = ref({
  module: '',
  specialization: '',
  chapter: ''
});

// Computed property to check if there are any validation errors
const hasValidationErrors = computed(() => {
  return validationErrors.value.module !== '' || 
         validationErrors.value.specialization !== '' || 
         validationErrors.value.chapter !== '';
});

// Filter sessions based on search query
const filteredSessions = computed(() => {
  if (!searchQuery.value) return tutoringSessions.value;
  const query = searchQuery.value.toLowerCase();
  return tutoringSessions.value.filter(session => 
    session.module.toLowerCase().includes(query) ||
    session.chapter.toLowerCase().includes(query) ||
    session.specialization.toLowerCase().includes(query) ||
    session.tutorName.toLowerCase().includes(query)
  );
});

// Get current user from localStorage
const currentUser = ref({
  email: localStorage.getItem('userEmail') || '',
  name: localStorage.getItem('userName') || ''
});

const newSession = ref({
  tutorName: '',
  tutorEmail: '',
  tutorPhone: '',
  year: '',
  semester: '',
  specialization: '',
  module: '',
  chapter: '',
  date: '',
  time: '',
  meetingLink: ''
});

const checkAuth = () => {
  const storedId = localStorage.getItem('studentId');
  if (!storedId) {
    router.push('/login');
    return false;
  }
  studentId.value = parseInt(storedId);
  return true;
};

const fetchStudentData = async () => {
  if(!studentId.value) return;
  try {
    const response = await studentService.getStudentById(studentId.value);
    if(response.data) {
      student.value = response.data;
      // Update current user with fetched student data
      if (student.value.name) currentUser.value.name = student.value.name;
      if (student.value.email) currentUser.value.email = student.value.email;
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

// Validation function to check if input contains only numbers or special characters
const validateTextAndNoSpecialChars = (event, field) => {
  const value = event.target.value;
  const numberRegex = /^\d+$/;
  // Special characters regex: anything that is not a letter (a-z A-Z), not a space, not a hyphen, not a slash, not a period, not a comma, not an apostrophe
  const specialCharRegex = /[^a-zA-Z\s\-/.,']/;
  
  if (numberRegex.test(value)) {
    validationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain only numbers. Please enter valid text.`;
  } else if (specialCharRegex.test(value)) {
    validationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain special characters. Please use letters, spaces, hyphens, slashes, periods, commas, or apostrophes only.`;
  } else {
    validationErrors.value[field] = '';
  }
};

const showToast = (message, type = 'success') => {
  const id = Date.now();
  notificationToasts.value.push({ id, message, type });
  setTimeout(() => {
    dismissToast(id);
  }, 4000);
};

const dismissToast = (id) => {
  notificationToasts.value = notificationToasts.value.filter(t => t.id !== id);
};

// Load sessions from API
const loadSessions = async () => {
  try {
    loading.value = true;
    const response = await tutoringService.getAllSessions();
    tutoringSessions.value = response.data;
  } catch (error) {
    console.error('Error loading tutoring sessions:', error);
    showToast('Failed to load sessions. Please try again.', 'error');
  } finally {
    loading.value = false;
  }
};

const validatePhoneNumber = (phone) => {
  const phoneRegex = /^07[0-9]{8}$/;
  return phoneRegex.test(phone);
};

const openCreateModal = () => {
  // Reset validation errors
  validationErrors.value = {
    module: '',
    specialization: '',
    chapter: ''
  };
  
  isEditing.value = false;
  editingId.value = null;
  newSession.value = {
    tutorName: currentUser.value.name || '',
    tutorEmail: currentUser.value.email || '',
    tutorPhone: '',
    year: '',
    semester: '',
    specialization: '',
    module: '',
    chapter: '',
    date: '',
    time: '',
    meetingLink: ''
  };
  showCreateModal.value = true;
};

const openEditModal = (session) => {
  // Reset validation errors
  validationErrors.value = {
    module: '',
    specialization: '',
    chapter: ''
  };
  
  isEditing.value = true;
  editingId.value = session.id;
  newSession.value = {
    tutorName: session.tutorName,
    tutorEmail: session.tutorEmail,
    tutorPhone: session.tutorPhone || '',
    year: session.year.toString(),
    semester: session.semester.toString(),
    specialization: session.specialization,
    module: session.module,
    chapter: session.chapter,
    date: session.date,
    time: session.time,
    meetingLink: session.meetingLink
  };
  showCreateModal.value = true;
};

const closeCreateModal = () => {
  showCreateModal.value = false;
  isEditing.value = false;
  editingId.value = null;
};

const saveSession = async () => {
  // Check if there are any validation errors
  if (hasValidationErrors.value) {
    showToast('Please fix validation errors before submitting', 'error');
    return;
  }
  
  // Validate phone number
  if (!validatePhoneNumber(newSession.value.tutorPhone)) {
    showToast('Phone number must start with "07" and be exactly 10 digits', 'error');
    return;
  }
  
  submitting.value = true;
  try {
    const sessionData = {
      ...newSession.value,
      year: parseInt(newSession.value.year),
      semester: parseInt(newSession.value.semester)
    };
    
    let response;
    if (isEditing.value) {
      response = await tutoringService.updateSession(editingId.value, sessionData);
      // Update the session in the array
      const index = tutoringSessions.value.findIndex(s => s.id === editingId.value);
      if (index !== -1) {
        tutoringSessions.value[index] = response.data;
      }
      showToast('Tutoring session updated successfully!', 'success');
    } else {
      response = await tutoringService.addSession(sessionData);
      tutoringSessions.value.unshift(response.data);
      showToast('Tutoring session published successfully!', 'success');
    }
    closeCreateModal();
  } catch (error) {
    console.error('Error saving tutoring session:', error);
    showToast(error.response?.data?.message || 'Failed to save session. Please try again.', 'error');
  } finally {
    submitting.value = false;
  }
};

const confirmDelete = (session) => {
  editingId.value = session.id;
  showDeleteModal.value = true;
};

const closeDeleteModal = () => {
  showDeleteModal.value = false;
  editingId.value = null;
};

const deleteSession = async () => {
  deleting.value = true;
  try {
    await tutoringService.deleteSession(editingId.value);
    tutoringSessions.value = tutoringSessions.value.filter(s => s.id !== editingId.value);
    showToast('Tutoring session deleted successfully!', 'success');
    closeDeleteModal();
  } catch (error) {
    console.error('Error deleting tutoring session:', error);
    showToast('Failed to delete session. Please try again.', 'error');
  } finally {
    deleting.value = false;
  }
};

const isSessionCreator = (session) => {
  // Check if current user's email matches the session's tutor email
  return currentUser.value.email && session.tutorEmail === currentUser.value.email;
};

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
};

onMounted(() => {
  if(checkAuth()) {
    fetchStudentData();
    loadSessions();
  }
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
.content-canvas { 
  flex: 1;
  overflow-y: auto;
}

.content-canvas::-webkit-scrollbar {
  width: 5px;
}
.content-canvas::-webkit-scrollbar-thumb {
  background: #E5E7EB;
  border-radius: 10px;
}

/* Font imports */
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,700;1,700&family=Inter:wght@400;500;700;900&family=Manrope:wght@400;500;600;700;800&display=swap');

.font-serif {
  font-family: 'Playfair Display', serif;
}

.font-sans {
  font-family: 'Inter', sans-serif;
}

.animate-spin {
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* Toast animations - Top Right */
.toast-enter-active, .toast-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
.toast-enter-from {
  opacity: 0;
  transform: translateX(50px) translateY(-20px) scale(0.9);
}
.toast-leave-to {
  opacity: 0;
  transform: translateX(100px) translateY(-10px);
}

/* Responsive */
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