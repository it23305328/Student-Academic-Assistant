<template>
  <div class="dashboard-wrapper flex h-screen bg-[#F8F9FA] overflow-hidden font-sans">
    <Navbar />
    
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      <section class="content-scroll flex-1 overflow-y-auto pr-2">
        <div class="max-w-6xl mx-auto px-6 py-12">
          <div class="mb-12">
            <div class="flex items-center gap-2 mb-2">
              <div class="h-[1px] w-8 bg-[#A89060]"></div>
              <span class="text-[10px] font-black uppercase tracking-[0.3em] text-[#A89060]">Collaborative Learning</span>
            </div>
            <h1 class="text-5xl md:text-6xl font-serif font-bold text-[#1A1A1A] mb-4 italic">Study Groups</h1>
            <p class="text-sm text-gray-500 max-w-lg leading-relaxed">Connect with fellow students to master complex topics through collective intelligence.</p>
          </div>

          <div class="mb-12 flex gap-4">
            <button 
              @click="openCreateModal" 
              class="bg-[#1A1A1A] text-white px-8 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all flex items-center gap-3 shadow-lg shadow-black/5"
            >
              <Users class="w-5 h-5" />
              Create New Study Group
            </button>
            
            <button 
              @click="loadGroups" 
              class="border-2 border-[#1A1A1A] text-[#1A1A1A] px-8 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#1A1A1A] hover:text-white transition-all flex items-center gap-3"
            >
              <RefreshCw class="w-5 h-5" />
              Refresh
            </button>
          </div>

          <div v-if="loading" class="bg-white border border-gray-100 rounded-3xl p-24 text-center shadow-sm">
            <div class="inline-block w-10 h-10 border-2 border-gray-50 border-t-[#A89060] rounded-full animate-spin mb-6"></div>
            <p class="text-gray-400 text-[10px] font-black tracking-[0.2em] uppercase">Searching for Groups</p>
          </div>

          <div v-else-if="studyGroups.length === 0" class="bg-white border border-gray-100 rounded-3xl p-24 text-center shadow-sm">
            <Users class="w-16 h-16 text-gray-100 mx-auto mb-6" />
            <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">No Active Groups</h3>
            <p class="text-gray-400 text-sm italic">Initiate a session and invite your peers.</p>
          </div>

          <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div 
              v-for="group in studyGroups" 
              :key="group.id" 
              class="group bg-white border border-gray-100 rounded-2xl transition-all duration-500 hover:shadow-2xl hover:shadow-[#A89060]/10 hover:-translate-y-2 overflow-hidden flex flex-col"
            >
              <div class="p-8 flex-1">
                <div class="flex items-start gap-5 mb-6">
                  <div class="w-14 h-14 bg-[#F8F9FA] rounded-2xl flex items-center justify-center group-hover:bg-[#1A1A1A] transition-colors duration-500">
                    <Users class="w-7 h-7 text-[#A89060]" />
                  </div>
                  <div class="flex-1">
                    <span class="text-[9px] font-black uppercase tracking-widest text-[#A89060] mb-1 block">Discussion Topic</span>
                    <h3 class="font-serif font-bold text-xl text-[#1A1A1A] mb-1 leading-tight">{{ group.topic }}</h3>
                    <p class="text-[11px] font-bold text-gray-400 uppercase tracking-tighter">By {{ group.creatorName }}</p>
                  </div>
                </div>

                <div class="space-y-4 mb-8">
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <BookOpen class="w-4 h-4 text-[#A89060]" />
                    <span>{{ group.module }}</span>
                  </div>
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <Calendar class="w-4 h-4 text-[#A89060]" />
                    <span>Year {{ group.year }}, Sem {{ group.semester }}</span>
                  </div>
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <Users class="w-4 h-4 text-[#A89060]" />
                    <span>{{ group.currentMembers }} / {{ group.maxMembers }} Members</span>
                  </div>
                  <div class="flex items-center gap-3 text-sm text-gray-600 font-medium">
                    <Phone class="w-4 h-4 text-[#A89060]" />
                    <span>{{ group.creatorPhone }}</span>
                  </div>
                  <div class="inline-block px-3 py-1 bg-[#F8F9FA] rounded text-[10px] font-black text-gray-400 uppercase tracking-widest border border-gray-100">
                    {{ group.specialization }}
                  </div>
                </div>

                <!-- Creator Actions -->
                <div v-if="isCreator(group)" class="flex gap-3 mb-4">
                  <button 
                    @click="openMembersModal(group)" 
                    class="flex-1 border-2 border-[#A89060] text-[#A89060] px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-[#A89060] hover:text-white transition-all"
                  >
                    <Users class="w-4 h-4 inline mr-2" />
                    Members ({{ group.currentMembers }})
                  </button>
                  <button 
                    @click="openDeleteConfirmModal(group)" 
                    class="flex-1 border-2 border-red-500 text-red-500 px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-red-500 hover:text-white transition-all"
                  >
                    <Trash2 class="w-4 h-4 inline mr-2" />
                    Delete
                  </button>
                </div>
                
                <!-- Join/Leave Button for Non-Creators -->
                <div v-else>
                  <button 
                    v-if="!isMember(group)"
                    :disabled="group.currentMembers >= group.maxMembers"
                    @click="openJoinModal(group)" 
                    :class="[
                      'w-full px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest transition-all text-center',
                      group.currentMembers >= group.maxMembers 
                        ? 'bg-[#F8F9FA] text-gray-300 border border-gray-100 cursor-not-allowed'
                        : 'border-2 border-[#1A1A1A] text-[#1A1A1A] hover:bg-[#1A1A1A] hover:text-white'
                    ]"
                  >
                    {{ group.currentMembers >= group.maxMembers ? 'Group Full' : 'Join Group' }}
                  </button>
                  <button 
                    v-else
                    @click="openLeaveConfirmModal(group)" 
                    class="w-full border-2 border-red-500 text-red-500 px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-red-500 hover:text-white transition-all"
                  >
                    Leave Group
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>

    <!-- Create Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeCreateModal">
      <div class="relative bg-white rounded-3xl w-full max-w-lg p-10 max-h-[90vh] overflow-y-auto shadow-2xl" @click.stop>
        <div class="mb-8">
          <h2 class="text-3xl font-serif font-bold text-[#1A1A1A] mb-2">Create Group</h2>
          <div class="h-1 w-12 bg-[#A89060]"></div>
        </div>
        
        <form @submit.prevent="createStudyGroup" class="space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Your Name</label>
              <input v-model="newGroup.creatorName" type="text" required readonly class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all bg-gray-100 cursor-not-allowed">
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Email</label>
              <input v-model="newGroup.creatorEmail" type="email" required readonly class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all bg-gray-100 cursor-not-allowed">
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Phone Number</label>
            <input 
              v-model="newGroup.creatorPhone" 
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
              <select v-model="newGroup.year" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Year</option>
                <option value="1">1st Year</option>
                <option value="2">2nd Year</option>
                <option value="3">3rd Year</option>
                <option value="4">4th Year</option>
              </select>
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Semester</label>
              <select v-model="newGroup.semester" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Semester</option>
                <option value="1">Semester 1</option>
                <option value="2">Semester 2</option>
              </select>
            </div>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Module</label>
              <input v-model="newGroup.module" type="text" required placeholder="e.g., Software Eng." class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specialization</label>
              <input v-model="newGroup.specialization" type="text" required placeholder="e.g., IT / CS" class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Discussion Topic</label>
            <input v-model="newGroup.topic" type="text" required placeholder="What will the group focus on?" class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Max Capacity</label>
            <input v-model="newGroup.maxMembers" type="number" min="2" max="10" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div class="flex gap-4 pt-6">
            <button type="button" @click="closeCreateModal" class="flex-1 px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all">Cancel</button>
            <button type="submit" :disabled="submitting" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
              {{ submitting ? 'Creating...' : 'Create Group' }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Join Modal -->
    <div v-if="showJoinModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeJoinModal">
      <div class="relative bg-white rounded-3xl w-full max-w-md p-10 shadow-2xl" @click.stop>
        <div class="mb-8">
          <h2 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">Join Group</h2>
          <p class="text-[10px] font-black uppercase tracking-[0.2em] text-[#A89060]">Topic: {{ selectedGroup?.topic }}</p>
        </div>
        
        <form @submit.prevent="joinStudyGroup" class="space-y-6">
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Your Name</label>
            <input v-model="joinData.name" type="text" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Email</label>
            <input v-model="joinData.email" type="email" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Phone Number</label>
            <input 
              v-model="joinData.phone" 
              type="tel" 
              required 
              pattern="07[0-9]{8}"
              maxlength="10"
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              placeholder="0712345678"
            >
            <p class="text-xs text-gray-400 mt-1">Must start with 07 and be exactly 10 digits</p>
          </div>
          
          <div class="flex gap-4 pt-6">
            <button type="button" @click="closeJoinModal" class="flex-1 px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all">Cancel</button>
            <button type="submit" :disabled="joining" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
              {{ joining ? 'Joining...' : 'Join' }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Members Modal (Only visible to creator) -->
    <div v-if="showMembersModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeMembersModal">
      <div class="relative bg-white rounded-3xl w-full max-w-2xl p-10 max-h-[90vh] overflow-y-auto shadow-2xl" @click.stop>
        <div class="mb-8">
          <h2 class="text-3xl font-serif font-bold text-[#1A1A1A] mb-2">Group Members</h2>
          <div class="h-1 w-12 bg-[#A89060] mb-4"></div>
          <p class="text-sm text-gray-500">Topic: {{ selectedGroup?.topic }}</p>
        </div>
        
        <div class="space-y-4">
          <div v-for="member in selectedGroup?.members" :key="member.id" class="bg-[#F8F9FA] rounded-xl p-4 flex justify-between items-center">
            <div>
              <p class="font-bold text-[#1A1A1A]">{{ member.name }}</p>
              <p class="text-xs text-gray-500">{{ member.email }} • {{ member.phone }}</p>
            </div>
            <div class="text-xs text-gray-400">
              Joined: {{ formatDate(member.joinedAt) }}
            </div>
          </div>
        </div>
        
        <div class="flex gap-4 pt-6 mt-4">
          <button @click="closeMembersModal" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all">Close</button>
        </div>
      </div>
    </div>

    <!-- Custom Confirmation Modal for Delete -->
    <div v-if="showDeleteConfirmModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeDeleteConfirmModal">
      <div class="relative bg-white rounded-3xl w-full max-w-md p-8 text-center shadow-2xl" @click.stop>
        <div class="mb-4 flex justify-center">
          <div class="w-16 h-16 bg-red-50 rounded-full flex items-center justify-center">
            <Trash2 class="w-8 h-8 text-red-500" />
          </div>
        </div>
        <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">Delete Group</h3>
        <p class="text-gray-500 text-sm mb-6">Are you sure you want to delete this study group? This action cannot be undone.</p>
        <div class="flex gap-3">
          <button @click="closeDeleteConfirmModal" class="flex-1 px-4 py-3 rounded-xl text-xs font-black uppercase tracking-widest border border-gray-200 text-gray-500 hover:bg-gray-50 transition-all">Cancel</button>
          <button @click="confirmDeleteGroup" :disabled="deleting" class="flex-1 bg-red-500 text-white px-4 py-3 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-red-600 transition-all disabled:opacity-50">
            {{ deleting ? 'Deleting...' : 'Delete' }}
          </button>
        </div>
      </div>
    </div>

    <!-- Custom Confirmation Modal for Leave -->
    <div v-if="showLeaveConfirmModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4" @click="closeLeaveConfirmModal">
      <div class="relative bg-white rounded-3xl w-full max-w-md p-8 text-center shadow-2xl" @click.stop>
        <div class="mb-4 flex justify-center">
          <div class="w-16 h-16 bg-amber-50 rounded-full flex items-center justify-center">
            <AlertCircle class="w-8 h-8 text-amber-500" />
          </div>
        </div>
        <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">Leave Group</h3>
        <p class="text-gray-500 text-sm mb-6">Are you sure you want to leave this study group? You can rejoin if the group is still open.</p>
        <div class="flex gap-3">
          <button @click="closeLeaveConfirmModal" class="flex-1 px-4 py-3 rounded-xl text-xs font-black uppercase tracking-widest border border-gray-200 text-gray-500 hover:bg-gray-50 transition-all">Cancel</button>
          <button @click="confirmLeaveGroup" :disabled="leaving" class="flex-1 bg-amber-500 text-white px-4 py-3 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-amber-600 transition-all disabled:opacity-50">
            {{ leaving ? 'Leaving...' : 'Leave' }}
          </button>
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
import { ref, onMounted } from 'vue';
import { Users, Calendar, BookOpen, Phone, RefreshCw, Trash2, CheckCircle, XCircle, AlertCircle } from 'lucide-vue-next';
import Navbar from '../../components/Navbar.vue';
import studyGroupService from '../../services/studyGroupService';

const studyGroups = ref([]);
const loading = ref(true);
const showCreateModal = ref(false);
const showJoinModal = ref(false);
const showMembersModal = ref(false);
const showDeleteConfirmModal = ref(false);
const showLeaveConfirmModal = ref(false);
const selectedGroup = ref(null);
const submitting = ref(false);
const joining = ref(false);
const deleting = ref(false);
const leaving = ref(false);
const notificationToasts = ref([]);

// Get current user from localStorage
const currentUser = ref({
  email: localStorage.getItem('userEmail') || '',
  name: localStorage.getItem('userName') || ''
});

const newGroup = ref({
  creatorName: '',
  creatorEmail: '',
  creatorPhone: '',
  year: '',
  semester: '',
  specialization: '',
  module: '',
  topic: '',
  maxMembers: 5
});

const joinData = ref({
  name: '',
  email: '',
  phone: ''
});

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

const formatDate = (dateString) => {
  if (!dateString) return '';
  const date = new Date(dateString);
  return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric', year: 'numeric' });
};

const validatePhoneNumber = (phone) => {
  const phoneRegex = /^07[0-9]{8}$/;
  return phoneRegex.test(phone);
};

const loadGroups = async () => {
  try {
    loading.value = true;
    const response = await studyGroupService.getAllGroups();
    studyGroups.value = response.data;
  } catch (error) {
    console.error('Error loading study groups:', error);
    showToast('Failed to load study groups. Please try again.', 'error');
  } finally {
    loading.value = false;
  }
};

const isCreator = (group) => {
  return currentUser.value.email && group.creatorEmail === currentUser.value.email;
};

const isMember = (group) => {
  if (!currentUser.value.email) return false;
  return group.members?.some(member => member.email === currentUser.value.email);
};

const openCreateModal = () => {
  newGroup.value = {
    creatorName: currentUser.value.name || '',
    creatorEmail: currentUser.value.email || '',
    creatorPhone: '',
    year: '',
    semester: '',
    specialization: '',
    module: '',
    topic: '',
    maxMembers: 5
  };
  showCreateModal.value = true;
};

const closeCreateModal = () => {
  showCreateModal.value = false;
};

const createStudyGroup = async () => {
  // Validate phone number
  if (!validatePhoneNumber(newGroup.value.creatorPhone)) {
    showToast('Phone number must start with "07" and be exactly 10 digits', 'error');
    return;
  }
  
  submitting.value = true;
  try {
    const response = await studyGroupService.createGroup({
      ...newGroup.value,
      year: parseInt(newGroup.value.year),
      semester: parseInt(newGroup.value.semester)
    });
    studyGroups.value.unshift(response.data);
    showToast('Study group created successfully!', 'success');
    closeCreateModal();
  } catch (error) {
    console.error('Error creating study group:', error);
    showToast(error.response?.data?.message || 'Failed to create group. Please try again.', 'error');
  } finally {
    submitting.value = false;
  }
};

const openJoinModal = (group) => {
  selectedGroup.value = group;
  joinData.value = {
    name: currentUser.value.name || '',
    email: currentUser.value.email || '',
    phone: ''
  };
  showJoinModal.value = true;
};

const closeJoinModal = () => {
  showJoinModal.value = false;
  selectedGroup.value = null;
};

const joinStudyGroup = async () => {
  if (!selectedGroup.value) return;
  
  // Validate phone number
  if (!validatePhoneNumber(joinData.value.phone)) {
    showToast('Phone number must start with "07" and be exactly 10 digits', 'error');
    return;
  }
  
  joining.value = true;
  try {
    const response = await studyGroupService.joinGroup(selectedGroup.value.id, joinData.value);
    const index = studyGroups.value.findIndex(g => g.id === selectedGroup.value.id);
    if (index !== -1) {
      studyGroups.value[index] = response.data;
    }
    showToast('Successfully joined the group!', 'success');
    closeJoinModal();
  } catch (error) {
    console.error('Error joining group:', error);
    showToast(error.response?.data?.message || 'Failed to join group. Please try again.', 'error');
  } finally {
    joining.value = false;
  }
};

const openLeaveConfirmModal = (group) => {
  selectedGroup.value = group;
  showLeaveConfirmModal.value = true;
};

const closeLeaveConfirmModal = () => {
  showLeaveConfirmModal.value = false;
  selectedGroup.value = null;
};

const confirmLeaveGroup = async () => {
  if (!selectedGroup.value) return;
  
  leaving.value = true;
  try {
    const response = await studyGroupService.leaveGroup(selectedGroup.value.id, currentUser.value.email);
    const index = studyGroups.value.findIndex(g => g.id === selectedGroup.value.id);
    if (index !== -1) {
      studyGroups.value[index] = response.data;
    }
    showToast('Successfully left the group!', 'success');
    closeLeaveConfirmModal();
  } catch (error) {
    console.error('Error leaving group:', error);
    showToast(error.response?.data?.message || 'Failed to leave group. Please try again.', 'error');
  } finally {
    leaving.value = false;
  }
};

const openDeleteConfirmModal = (group) => {
  selectedGroup.value = group;
  showDeleteConfirmModal.value = true;
};

const closeDeleteConfirmModal = () => {
  showDeleteConfirmModal.value = false;
  selectedGroup.value = null;
};

const confirmDeleteGroup = async () => {
  if (!selectedGroup.value) return;
  
  deleting.value = true;
  try {
    await studyGroupService.deleteGroup(selectedGroup.value.id, currentUser.value.email);
    studyGroups.value = studyGroups.value.filter(g => g.id !== selectedGroup.value.id);
    showToast('Group deleted successfully!', 'success');
    closeDeleteConfirmModal();
  } catch (error) {
    console.error('Error deleting group:', error);
    showToast(error.response?.data?.message || 'Failed to delete group. Please try again.', 'error');
  } finally {
    deleting.value = false;
  }
};

const openMembersModal = (group) => {
  selectedGroup.value = group;
  showMembersModal.value = true;
};

const closeMembersModal = () => {
  showMembersModal.value = false;
  selectedGroup.value = null;
};

onMounted(() => {
  loadGroups();
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

.content-scroll::-webkit-scrollbar {
  width: 4px;
}
.content-scroll::-webkit-scrollbar-thumb {
  background: #E5E7EB;
  border-radius: 10px;
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
</style>