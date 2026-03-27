<template>
  <div class="dashboard-wrapper flex h-screen bg-[#F8F9FA] overflow-hidden font-sans">
    <Navbar />
    
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      <section class="content-scroll flex-1 overflow-y-auto pr-2">
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
              v-for="session in tutoringSessions" 
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
      </section>
    </div>

    <!-- Create/Edit Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeCreateModal">
      <div class="relative bg-white rounded-3xl w-full max-w-lg p-10 max-h-[90vh] overflow-y-auto shadow-2xl" @click.stop>
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
            <input v-model="newSession.module" type="text" required placeholder="e.g., Computer Architecture" class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specialization</label>
            <input v-model="newSession.specialization" type="text" required placeholder="e.g., Computer Science" class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>

          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specific Chapter</label>
            <input v-model="newSession.chapter" type="text" required placeholder="e.g., Memory Management" class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
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
            <button type="submit" :disabled="submitting" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
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

    <!-- Toast Notifications -->
    <div v-if="showSuccessToast" class="fixed bottom-8 right-8 bg-green-500 text-white px-6 py-3 rounded-lg shadow-lg z-[1000] animate-fade-in-up">
      {{ successMessage }}
    </div>
    
    <div v-if="showErrorToast" class="fixed bottom-8 right-8 bg-red-500 text-white px-6 py-3 rounded-lg shadow-lg z-[1000] animate-fade-in-up">
      {{ errorMessage }}
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { GraduationCap, Calendar, BookOpen, Users, RefreshCw, Edit, Trash2 } from 'lucide-vue-next';
import Navbar from '../../components/Navbar.vue';
import tutoringService from '../../services/tutoringService';

const tutoringSessions = ref([]);
const loading = ref(true);
const showCreateModal = ref(false);
const showDeleteModal = ref(false);
const submitting = ref(false);
const deleting = ref(false);
const isEditing = ref(false);
const editingId = ref(null);
const showSuccessToast = ref(false);
const showErrorToast = ref(false);
const successMessage = ref('');
const errorMessage = ref('');

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

// Load sessions from API
const loadSessions = async () => {
  try {
    loading.value = true;
    const response = await tutoringService.getAllSessions();
    tutoringSessions.value = response.data;
  } catch (error) {
    console.error('Error loading tutoring sessions:', error);
    showErrorMessage('Failed to load sessions. Please try again.');
  } finally {
    loading.value = false;
  }
};

const showSuccessMessage = (message) => {
  successMessage.value = message;
  showSuccessToast.value = true;
  setTimeout(() => {
    showSuccessToast.value = false;
  }, 3000);
};

const showErrorMessage = (message) => {
  errorMessage.value = message;
  showErrorToast.value = true;
  setTimeout(() => {
    showErrorToast.value = false;
  }, 3000);
};

const validatePhoneNumber = (phone) => {
  const phoneRegex = /^07[0-9]{8}$/;
  return phoneRegex.test(phone);
};

const openCreateModal = () => {
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
  // Validate phone number
  if (!validatePhoneNumber(newSession.value.tutorPhone)) {
    showErrorMessage('Phone number must start with "07" and be exactly 10 digits');
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
      showSuccessMessage('Tutoring session updated successfully!');
    } else {
      response = await tutoringService.addSession(sessionData);
      tutoringSessions.value.unshift(response.data);
      showSuccessMessage('Tutoring session published successfully!');
    }
    closeCreateModal();
  } catch (error) {
    console.error('Error saving tutoring session:', error);
    showErrorMessage(error.response?.data?.message || 'Failed to save session. Please try again.');
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
    showSuccessMessage('Tutoring session deleted successfully!');
    closeDeleteModal();
  } catch (error) {
    console.error('Error deleting tutoring session:', error);
    showErrorMessage('Failed to delete session. Please try again.');
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
  loadSessions();
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,700;1,700&family=Inter:wght@400;500;700;900&display=swap');

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

@keyframes fade-in-up {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in-up {
  animation: fade-in-up 0.3s ease-out;
}

.content-scroll::-webkit-scrollbar {
  width: 4px;
}
.content-scroll::-webkit-scrollbar-thumb {
  background: #E5E7EB;
  border-radius: 10px;
}
</style>