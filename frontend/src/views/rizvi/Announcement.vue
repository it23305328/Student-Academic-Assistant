<template>
  <div class="dashboard-wrapper flex h-screen bg-[#F8F9FA] overflow-hidden font-sans">
    <Navbar />
    
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      <!-- Announcements Banner -->
      <div v-if="recentAnnouncements.length > 0" class="announcements-banner bg-white border border-gray-200 rounded-2xl mx-6 mt-6 mb-4 p-4 flex items-center gap-4 cursor-pointer transition-all hover:shadow-md">
        <div class="banner-icon text-3xl">
          <Megaphone class="w-8 h-8 text-[#A89060]" />
        </div>
        <div class="banner-content flex-1">
          <div class="banner-title text-[#A89060] text-[10px] font-black mb-1 tracking-[0.2em] uppercase">LATEST ANNOUNCEMENTS</div>
          <div class="banner-messages flex flex-col gap-1.5">
            <div 
              v-for="ann in recentAnnouncements.slice(0, 2)" 
              :key="ann.id" 
              class="banner-message flex items-center gap-3 text-sm cursor-pointer py-1 px-2 rounded-lg transition-all hover:bg-gray-50"
              @click="viewAnnouncement(ann.id)"
            >
              <span class="message-topic font-bold text-[#1A1A1A] whitespace-nowrap">{{ ann.topic }}</span>
              <span class="message-preview text-gray-500 text-xs truncate">{{ truncateText(ann.content, 60) }}</span>
            </div>
          </div>
        </div>
        <router-link to="/announcements" class="view-all-btn bg-[#1A1A1A] text-white px-5 py-2 rounded-full text-[10px] font-bold no-underline transition-all hover:opacity-90 whitespace-nowrap">
          View All →
        </router-link>
      </div>

      <section class="content-scroll flex-1 overflow-y-auto pr-2">
        <div class="max-w-4xl mx-auto px-6 py-8">
          <div class="mb-10">
            <h1 class="text-4xl md:text-5xl font-serif font-bold text-[#1A1A1A] mb-3">Official Announcements</h1>
            <p class="text-sm text-gray-500 max-w-lg">Stay updated with important notices and curatorial updates from the academic administration.</p>
          </div>

          <div class="flex flex-col md:flex-row gap-4 mb-8">
            <div class="relative flex-1">
              <Search class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
              <input 
                type="text" 
                v-model="searchQuery" 
                placeholder="Search archive..."
                class="w-full pl-11 pr-4 py-3 bg-white border border-gray-200 rounded-xl text-sm text-[#1A1A1A] focus:outline-none focus:border-[#A89060] transition-all placeholder:text-gray-400 shadow-sm"
              />
            </div>
            <div class="flex gap-2 flex-wrap items-center">
              <button 
                v-for="filter in filters" 
                :key="filter.value"
                @click="currentFilter = filter.value"
                :class="[
                  'px-4 py-2 rounded-full text-[11px] font-bold tracking-wider uppercase transition-all duration-200 whitespace-nowrap border',
                  currentFilter === filter.value 
                    ? 'bg-[#1A1A1A] text-white border-[#1A1A1A]' 
                    : 'bg-white text-gray-500 border-gray-200 hover:border-gray-300'
                ]"
              >
                {{ filter.label }}
              </button>
            </div>
          </div>

          <div>
            <div v-if="loading" class="bg-white border border-gray-200 rounded-3xl p-20 text-center shadow-sm">
              <div class="inline-block w-8 h-8 border-2 border-gray-100 border-t-[#A89060] rounded-full animate-spin mb-4"></div>
              <p class="text-gray-400 text-xs font-bold tracking-widest uppercase">Fetching Data...</p>
            </div>
            
            <div v-else-if="filteredAnnouncements.length === 0" class="bg-white border border-gray-200 rounded-3xl p-20 text-center shadow-sm">
              <Megaphone class="w-12 h-12 text-gray-200 mx-auto mb-4" />
              <h3 class="text-lg font-serif font-bold text-[#1A1A1A] mb-1">Archive Empty</h3>
              <p class="text-gray-400 text-sm">No announcements match your current filter.</p>
            </div>
            
            <div v-else class="space-y-4">
              <div 
                v-for="announcement in filteredAnnouncements" 
                :key="announcement.id" 
                class="group bg-white border border-gray-200 rounded-2xl transition-all duration-300 hover:shadow-xl hover:-translate-y-1 cursor-pointer overflow-hidden"
                @click="toggleReadMore(announcement.id)"
              >
                <div class="p-6">
                  <div class="flex items-start gap-5">
                    <div class="flex-shrink-0 w-12 h-12 bg-[#F8F9FA] rounded-xl flex items-center justify-center group-hover:bg-[#A89060]/10 transition-colors">
                      <Megaphone class="w-6 h-6 text-[#A89060]" />
                    </div>
                    
                    <div class="flex-1 min-w-0">
                      <div class="flex flex-wrap items-baseline justify-between gap-3 mb-2">
                        <h2 class="text-xl font-serif font-bold text-[#1A1A1A] truncate pr-2">{{ announcement.topic }}</h2>
                        <div class="flex gap-2 flex-shrink-0">
                          <span class="inline-flex items-center gap-1.5 bg-[#F8F9FA] text-gray-600 px-3 py-1 rounded-full text-[10px] font-bold border border-gray-100 uppercase tracking-tighter">
                            <Users class="w-3 h-3" />
                            {{ getTargetShortText(announcement) }}
                          </span>
                          <span class="inline-flex items-center gap-1.5 bg-[#F8F9FA] text-gray-600 px-3 py-1 rounded-full text-[10px] font-bold border border-gray-100 uppercase tracking-tighter">
                            <Calendar class="w-3 h-3" />
                            {{ formatDateShort(announcement.datetime || announcement.createdAt) }}
                          </span>
                        </div>
                      </div>
                      
                      <!-- Content area with proper structure - matches admin side display -->
                      <div class="text-gray-600 text-sm leading-relaxed mb-4">
                        <div 
                          :class="{ 'line-clamp-2': !expandedAnnouncements.includes(announcement.id) }"
                          class="whitespace-pre-wrap"
                        >
                          {{ announcement.content }}
                        </div>
                      </div>
                      
                      <div v-if="expandedAnnouncements.includes(announcement.id)" class="mt-4 pt-6 border-t border-gray-100 animate-fade-in">
                        <div v-if="announcement.imageUrl" class="mb-4">
                          <img 
                            :src="announcement.imageUrl" 
                            alt="Announcement image" 
                            @click.stop="openImageModal(announcement.imageUrl)" 
                            class="w-full max-h-64 rounded-xl cursor-pointer object-cover shadow-sm transition-opacity hover:opacity-90"
                          />
                        </div>
                      </div>
                      
                      <button 
                        @click.stop="toggleReadMore(announcement.id)" 
                        class="text-[#A89060] text-[11px] font-black uppercase tracking-widest hover:text-[#1A1A1A] transition-colors flex items-center gap-1"
                      >
                        {{ expandedAnnouncements.includes(announcement.id) ? 'Show less ↑' : 'Read details →' }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      <GPAModal :isOpen="isGpaModalOpen" @close="isGpaModalOpen = false" />

      <div class="toast-container fixed top-6 right-6 z-[9999] flex flex-col gap-3 w-80">
        <transition-group name="toast">
          <div v-for="toast in notificationToasts" :key="toast.id" class="toast-item relative bg-white rounded-2xl p-5 shadow-2xl border border-gray-100 overflow-hidden" :class="{
            'border-l-4 border-l-[#1A1A1A]': toast.type === 'lecture',
            'border-l-4 border-l-[#A89060]': toast.type === 'announcement',
            'border-l-4 border-l-red-600': toast.type === 'critical'
          }">
            <div class="toast-content flex items-start gap-4 relative z-10">
              <div class="toast-icon w-10 h-10 rounded-xl flex items-center justify-center bg-gray-50">
                <Clock v-if="toast.type === 'lecture'" class="w-5 h-5 text-[#1A1A1A]" />
                <Megaphone v-else-if="toast.type === 'announcement'" class="w-5 h-5 text-[#A89060]" />
                <AlertCircle v-else class="w-5 h-5 text-red-600" />
              </div>
              <div class="toast-body flex-1">
                <p class="toast-title text-[10px] font-black text-[#A89060] uppercase tracking-widest mb-1">{{ toast.title }}</p>
                <p class="toast-message text-xs text-gray-700 font-medium leading-relaxed">{{ toast.message }}</p>
              </div>
              <button @click="dismissToast(toast.id)" class="toast-close text-gray-300 hover:text-gray-900 transition-colors">&times;</button>
            </div>
          </div>
        </transition-group>
      </div>
    </div>

    <div v-if="modalImage" class="fixed inset-0 bg-[#1A1A1A]/95 flex items-center justify-center z-[1000] cursor-pointer" @click="closeImageModal">
      <div class="relative max-w-[90%] max-h-[90%]" @click.stop>
        <img :src="modalImage" alt="Full size announcement" class="max-w-full max-h-[90vh] rounded-2xl shadow-2xl border border-white/10" />
        <button class="absolute -top-12 right-0 bg-white/10 text-white w-10 h-10 rounded-full flex items-center justify-center hover:bg-red-600 transition-colors" @click="closeImageModal">×</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { Search, Bell, Clock, Megaphone, AlertCircle, Info, Check, Users, Calendar } from 'lucide-vue-next';
import announcementService from '../../services/announcementService';
import GPAModal from '../../components/GPAModal.vue';
import Navbar from '../../components/Navbar.vue';

const router = useRouter();
const isGpaModalOpen = ref(false);
const notificationsList = ref([]);
const isNotifDropdownOpen = ref(false);
const notificationToasts = ref([]);
const notifiedIds = new Set();

const announcements = ref([]);
const loading = ref(true);
const searchQuery = ref('');
const currentFilter = ref('all');
const expandedAnnouncements = ref([]);
const modalImage = ref(null);
const recentAnnouncements = ref([]);

const studentInfo = ref({
  year: null,
  semester: null,
  course: null
});

const filters = [
  { label: 'All', value: 'all' },
  { label: 'Academic Year', value: 'year' },
  { label: 'Faculty', value: 'faculty' },
  { label: 'General', value: 'everyone' }
];

const fetchAnnouncements = async () => {
  loading.value = true;
  try {
    const response = await announcementService.getAllAnnouncements();
    announcements.value = response.data || [];
    
    const userData = JSON.parse(localStorage.getItem('userData') || '{}');
    studentInfo.value = {
      year: userData.academicYear || 1,
      semester: userData.semester || 1,
      course: userData.course || 'IT'
    };
    
    const filtered = filterAnnouncementsForStudent(announcements.value);
    recentAnnouncements.value = filtered
      .sort((a, b) => {
        const dateA = new Date(a.datetime || a.createdAt);
        const dateB = new Date(b.datetime || b.createdAt);
        return dateB - dateA;
      })
      .slice(0, 3);
      
    checkNewAnnouncements();
  } catch (error) {
    console.error('Failed to fetch announcements:', error);
  } finally {
    loading.value = false;
  }
};

const filterAnnouncementsForStudent = (anns) => {
  return anns.filter(ann => {
    const yearMatch = !ann.targetYear || ann.targetYear === 'all' || ann.targetYear === studentInfo.value.year;
    const semesterMatch = !ann.targetSemester || ann.targetSemester === 'all' || ann.targetSemester === studentInfo.value.semester;
    const facultyMatch = !ann.targetFaculty || ann.targetFaculty === 'all' || ann.targetFaculty === studentInfo.value.course;
    return yearMatch && semesterMatch && facultyMatch;
  });
};

const checkNewAnnouncements = () => {
  announcements.value.forEach(ann => {
    const annId = `announcement-${ann.id}`;
    if (!notifiedIds.has(annId)) {
      const annDate = new Date(ann.datetime || ann.createdAt);
      const now = new Date();
      if (now - annDate < 3600000) {
        triggerNotification({
          type: 'announcement',
          title: 'Curatorial Update',
          message: `${ann.topic}`
        });
        notifiedIds.add(annId);
      }
    }
  });
};

const filteredAnnouncements = computed(() => {
  let filtered = announcements.value;
  if (searchQuery.value) {
    filtered = filtered.filter(ann => 
      ann.topic.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      ann.content.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }
  if (currentFilter.value !== 'all') {
    filtered = filtered.filter(ann => {
      if (currentFilter.value === 'everyone') return (!ann.targetYear || ann.targetYear === 'all');
      if (currentFilter.value === 'year') return ann.targetYear === studentInfo.value.year;
      if (currentFilter.value === 'faculty') return ann.targetFaculty === studentInfo.value.course;
      return true;
    });
  }
  return filtered.sort((a, b) => new Date(b.datetime || b.createdAt) - new Date(a.datetime || a.createdAt));
});

const getTargetShortText = (ann) => {
  if (ann.targetYear === 'all' || !ann.targetYear) return 'Open';
  return `Y${ann.targetYear}${ann.targetSemester ? ' S' + ann.targetSemester : ''}`;
};

const formatDateShort = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
};

const toggleReadMore = (id) => {
  if (expandedAnnouncements.value.includes(id)) {
    expandedAnnouncements.value = expandedAnnouncements.value.filter(i => i !== id);
  } else {
    expandedAnnouncements.value.push(id);
  }
};

const openImageModal = (imageUrl) => {
  modalImage.value = imageUrl;
  document.body.style.overflow = 'hidden';
};

const closeImageModal = () => {
  modalImage.value = null;
  document.body.style.overflow = '';
};

const viewAnnouncement = (id) => router.push('/announcements');

const truncateText = (text, length) => {
  if (!text) return '';
  return text.length <= length ? text : text.substring(0, length) + '...';
};

const triggerNotification = (details) => {
  const id = Date.now();
  notificationToasts.value.push({ ...details, id });
  setTimeout(() => dismissToast(id), 8000);
};

const dismissToast = (id) => {
  notificationToasts.value = notificationToasts.value.filter(t => t.id !== id);
};

onMounted(() => {
  fetchAnnouncements();
  const interval = setInterval(checkNewAnnouncements, 60000);
  onUnmounted(() => clearInterval(interval));
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;700;900&display=swap');

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

.toast-enter-active, .toast-leave-active {
  transition: all 0.5s cubic-bezier(0.19, 1, 0.22, 1);
}
.toast-enter-from { opacity: 0; transform: translateY(-20px) scale(0.9); }
.toast-leave-to { opacity: 0; transform: translateX(100px); }

.content-scroll::-webkit-scrollbar {
  width: 5px;
}
.content-scroll::-webkit-scrollbar-thumb {
  background: #E5E7EB;
  border-radius: 10px;
}

.animate-fade-in {
  animation: fadeIn 0.3s ease-out;
}

@keyframes fadeIn {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* Added for proper text formatting */
.whitespace-pre-wrap {
  white-space: pre-wrap;
  word-wrap: break-word;
  line-height: 1.6;
}
</style>