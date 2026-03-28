<template>
  <div class="dashboard-root">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ 'open': isSidebarOpen }">
      <div v-if="isSidebarOpen" class="sidebar-overlay" @click="isSidebarOpen = false"></div>
      <div class="sidebar-header">
        <div class="brand-wrapper">
          <img src="../../assets//studentx_logo.png" alt="StudentX Logo" class="brand-logo" />
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
          <input type="text" v-model="searchQuery" placeholder="Search groups or topics..." />
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

          <div v-else-if="filteredGroups.length === 0" class="bg-white border border-gray-100 rounded-3xl p-24 text-center shadow-sm">
            <Users class="w-16 h-16 text-gray-100 mx-auto mb-6" />
            <h3 class="text-2xl font-serif font-bold text-[#1A1A1A] mb-2">No Active Groups</h3>
            <p class="text-gray-400 text-sm italic">Initiate a session and invite your peers.</p>
          </div>

          <div v-else class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
            <div 
              v-for="group in filteredGroups" 
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
                    @click="openEditModal(group)" 
                    class="flex-1 border-2 border-blue-500 text-blue-500 px-4 py-3 rounded-xl text-[11px] font-black uppercase tracking-widest hover:bg-blue-500 hover:text-white transition-all"
                  >
                    <Edit class="w-4 h-4 inline mr-2" />
                    Edit
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
      </main>
    </div>

    <!-- Create Modal -->
    <div v-if="showCreateModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4">
      <div class="relative bg-white rounded-3xl w-full max-w-lg p-10 max-h-[90vh] overflow-y-auto shadow-2xl">
        <button 
          @click="closeCreateModal" 
          class="absolute top-6 right-6 text-gray-400 hover:text-[#1A1A1A] transition-colors text-2xl leading-none z-10"
          aria-label="Close modal"
        >
          &times;
        </button>
        
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
              <input 
                v-model="newGroup.module" 
                type="text" 
                required 
                placeholder="e.g., Software Eng." 
                class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
                @input="validateTextAndNoSpecialChars($event, 'module')"
              >
              <p v-if="validationErrors.module" class="text-xs text-red-500 mt-1">{{ validationErrors.module }}</p>
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specialization</label>
              <input 
                v-model="newGroup.specialization" 
                type="text" 
                required 
                placeholder="e.g., IT / CS" 
                class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
                @input="validateTextAndNoSpecialChars($event, 'specialization')"
              >
              <p v-if="validationErrors.specialization" class="text-xs text-red-500 mt-1">{{ validationErrors.specialization }}</p>
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Discussion Topic</label>
            <input 
              v-model="newGroup.topic" 
              type="text" 
              required 
              placeholder="What will the group focus on?" 
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              @input="validateTextAndNoSpecialChars($event, 'topic')"
            >
            <p v-if="validationErrors.topic" class="text-xs text-red-500 mt-1">{{ validationErrors.topic }}</p>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Max Capacity</label>
            <input v-model="newGroup.maxMembers" type="number" min="2" max="10" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
          </div>
          
          <div class="flex gap-4 pt-6">
            <button type="button" @click="closeCreateModal" class="flex-1 px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all">Cancel</button>
            <button type="submit" :disabled="submitting || hasValidationErrors" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
              {{ submitting ? 'Creating...' : 'Create Group' }}
            </button>
          </div>
        </form>
      </div>
    </div>

    <!-- Edit Modal -->
    <div v-if="showEditModal" class="fixed inset-0 bg-[#1A1A1A]/90 backdrop-blur-sm flex items-center justify-center z-[1000] p-4">
      <div class="relative bg-white rounded-3xl w-full max-w-lg p-10 max-h-[90vh] overflow-y-auto shadow-2xl">
        <button 
          @click="closeEditModal" 
          class="absolute top-6 right-6 text-gray-400 hover:text-[#1A1A1A] transition-colors text-2xl leading-none z-10"
          aria-label="Close modal"
        >
          &times;
        </button>
        
        <div class="mb-8">
          <h2 class="text-3xl font-serif font-bold text-[#1A1A1A] mb-2">Edit Group</h2>
          <div class="h-1 w-12 bg-[#A89060]"></div>
          <p class="text-sm text-gray-500 mt-2">Update group details. Members will see these changes.</p>
        </div>
        
        <form @submit.prevent="updateStudyGroup" class="space-y-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Your Name</label>
              <input v-model="editGroupData.creatorName" type="text" required readonly class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl bg-gray-100 cursor-not-allowed">
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Email</label>
              <input v-model="editGroupData.creatorEmail" type="email" required readonly class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl bg-gray-100 cursor-not-allowed">
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Phone Number</label>
            <input 
              v-model="editGroupData.creatorPhone" 
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
              <select v-model="editGroupData.year" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Year</option>
                <option value="1">1st Year</option>
                <option value="2">2nd Year</option>
                <option value="3">3rd Year</option>
                <option value="4">4th Year</option>
              </select>
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Semester</label>
              <select v-model="editGroupData.semester" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
                <option value="">Select Semester</option>
                <option value="1">Semester 1</option>
                <option value="2">Semester 2</option>
              </select>
            </div>
          </div>
          
          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Module</label>
              <input 
                v-model="editGroupData.module" 
                type="text" 
                required 
                placeholder="e.g., Software Eng." 
                class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
                @input="validateEditTextAndNoSpecialChars($event, 'module')"
              >
              <p v-if="editValidationErrors.module" class="text-xs text-red-500 mt-1">{{ editValidationErrors.module }}</p>
            </div>
            <div>
              <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Specialization</label>
              <input 
                v-model="editGroupData.specialization" 
                type="text" 
                required 
                placeholder="e.g., IT / CS" 
                class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
                @input="validateEditTextAndNoSpecialChars($event, 'specialization')"
              >
              <p v-if="editValidationErrors.specialization" class="text-xs text-red-500 mt-1">{{ editValidationErrors.specialization }}</p>
            </div>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Discussion Topic</label>
            <input 
              v-model="editGroupData.topic" 
              type="text" 
              required 
              placeholder="What will the group focus on?" 
              class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all"
              @input="validateEditTextAndNoSpecialChars($event, 'topic')"
            >
            <p v-if="editValidationErrors.topic" class="text-xs text-red-500 mt-1">{{ editValidationErrors.topic }}</p>
          </div>
          
          <div>
            <label class="block text-[10px] font-black text-gray-400 uppercase tracking-widest mb-2">Max Capacity</label>
            <input v-model="editGroupData.maxMembers" type="number" min="2" max="10" required class="w-full px-4 py-3 bg-[#F8F9FA] border border-transparent rounded-xl focus:outline-none focus:border-[#A89060] focus:bg-white transition-all">
            <p class="text-xs text-gray-400 mt-1">Note: Cannot be less than current member count ({{ editGroupData.currentMembers }})</p>
          </div>
          
          <div class="flex gap-4 pt-6">
            <button type="button" @click="closeEditModal" class="flex-1 px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest text-gray-400 hover:text-[#1A1A1A] transition-all">Cancel</button>
            <button type="submit" :disabled="updating || hasEditValidationErrors || editGroupData.maxMembers < editGroupData.currentMembers" class="flex-1 bg-[#1A1A1A] text-white px-6 py-4 rounded-xl text-xs font-black uppercase tracking-widest hover:bg-[#A89060] transition-all shadow-lg shadow-black/10 disabled:opacity-50 disabled:cursor-not-allowed">
              {{ updating ? 'Updating...' : 'Update Group' }}
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
import { ref, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Users, Calendar, BookOpen, Phone, RefreshCw, Trash2, CheckCircle, XCircle, AlertCircle, Edit } from 'lucide-vue-next';
import studyGroupService from '../../services/studyGroupService';
import studentService from '../../services/studentService';

const router = useRouter();
const studyGroups = ref([]);
const loading = ref(true);
const showCreateModal = ref(false);
const showEditModal = ref(false);
const showJoinModal = ref(false);
const showMembersModal = ref(false);
const showDeleteConfirmModal = ref(false);
const showLeaveConfirmModal = ref(false);
const selectedGroup = ref(null);
const submitting = ref(false);
const updating = ref(false);
const joining = ref(false);
const deleting = ref(false);
const leaving = ref(false);
const notificationToasts = ref([]);
const searchQuery = ref('');
const isSidebarOpen = ref(false);
const student = ref({ name: '', id: '', profilePic: '' });
const studentId = ref(null);

// Validation errors object
const validationErrors = ref({
  module: '',
  specialization: '',
  topic: ''
});

// Edit validation errors object
const editValidationErrors = ref({
  module: '',
  specialization: '',
  topic: ''
});

// Computed property to check if there are any validation errors
const hasValidationErrors = computed(() => {
  return validationErrors.value.module !== '' || 
         validationErrors.value.specialization !== '' || 
         validationErrors.value.topic !== '';
});

// Computed property for edit validation errors
const hasEditValidationErrors = computed(() => {
  return editValidationErrors.value.module !== '' || 
         editValidationErrors.value.specialization !== '' || 
         editValidationErrors.value.topic !== '';
});

// Filter groups based on search query
const filteredGroups = computed(() => {
  if (!searchQuery.value) return studyGroups.value;
  const query = searchQuery.value.toLowerCase();
  return studyGroups.value.filter(group => 
    group.topic.toLowerCase().includes(query) ||
    group.module.toLowerCase().includes(query) ||
    group.specialization.toLowerCase().includes(query) ||
    group.creatorName.toLowerCase().includes(query)
  );
});

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

const editGroupData = ref({
  id: null,
  creatorName: '',
  creatorEmail: '',
  creatorPhone: '',
  year: '',
  semester: '',
  specialization: '',
  module: '',
  topic: '',
  maxMembers: 5,
  currentMembers: 0
});

const joinData = ref({
  name: '',
  email: '',
  phone: ''
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
  const specialCharRegex = /[^a-zA-Z\s\-/.,']/;
  
  if (numberRegex.test(value)) {
    validationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain only numbers. Please enter valid text.`;
  } else if (specialCharRegex.test(value)) {
    validationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain special characters. Please use letters, spaces, hyphens, slashes, periods, commas, or apostrophes only.`;
  } else {
    validationErrors.value[field] = '';
  }
};

const validateEditTextAndNoSpecialChars = (event, field) => {
  const value = event.target.value;
  const numberRegex = /^\d+$/;
  const specialCharRegex = /[^a-zA-Z\s\-/.,']/;
  
  if (numberRegex.test(value)) {
    editValidationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain only numbers. Please enter valid text.`;
  } else if (specialCharRegex.test(value)) {
    editValidationErrors.value[field] = `${field.charAt(0).toUpperCase() + field.slice(1)} cannot contain special characters. Please use letters, spaces, hyphens, slashes, periods, commas, or apostrophes only.`;
  } else {
    editValidationErrors.value[field] = '';
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
  // Reset validation errors
  validationErrors.value = {
    module: '',
    specialization: '',
    topic: ''
  };
  
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
  // Check if there are any validation errors
  if (hasValidationErrors.value) {
    showToast('Please fix validation errors before submitting', 'error');
    return;
  }
  
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

const openEditModal = (group) => {
  // Reset edit validation errors
  editValidationErrors.value = {
    module: '',
    specialization: '',
    topic: ''
  };
  
  editGroupData.value = {
    id: group.id,
    creatorName: group.creatorName,
    creatorEmail: group.creatorEmail,
    creatorPhone: group.creatorPhone,
    year: group.year.toString(),
    semester: group.semester.toString(),
    specialization: group.specialization,
    module: group.module,
    topic: group.topic,
    maxMembers: group.maxMembers,
    currentMembers: group.currentMembers
  };
  showEditModal.value = true;
};

const closeEditModal = () => {
  showEditModal.value = false;
  editGroupData.value = {
    id: null,
    creatorName: '',
    creatorEmail: '',
    creatorPhone: '',
    year: '',
    semester: '',
    specialization: '',
    module: '',
    topic: '',
    maxMembers: 5,
    currentMembers: 0
  };
};

const updateStudyGroup = async () => {
  // Check if there are any validation errors
  if (hasEditValidationErrors.value) {
    showToast('Please fix validation errors before submitting', 'error');
    return;
  }
  
  // Validate phone number
  if (!validatePhoneNumber(editGroupData.value.creatorPhone)) {
    showToast('Phone number must start with "07" and be exactly 10 digits', 'error');
    return;
  }
  
  // Validate max capacity is not less than current member count
  if (editGroupData.value.maxMembers < editGroupData.value.currentMembers) {
    showToast(`Max capacity cannot be less than current member count (${editGroupData.value.currentMembers})`, 'error');
    return;
  }
  
  updating.value = true;
  try {
    const response = await studyGroupService.updateGroup(editGroupData.value.id, {
      creatorName: editGroupData.value.creatorName,
      creatorEmail: editGroupData.value.creatorEmail,
      creatorPhone: editGroupData.value.creatorPhone,
      year: parseInt(editGroupData.value.year),
      semester: parseInt(editGroupData.value.semester),
      specialization: editGroupData.value.specialization,
      module: editGroupData.value.module,
      topic: editGroupData.value.topic,
      maxMembers: editGroupData.value.maxMembers
    });
    
    // Update the group in the local list
    const index = studyGroups.value.findIndex(g => g.id === editGroupData.value.id);
    if (index !== -1) {
      studyGroups.value[index] = response.data;
    }
    
    showToast('Study group updated successfully!', 'success');
    closeEditModal();
  } catch (error) {
    console.error('Error updating study group:', error);
    showToast(error.response?.data?.message || 'Failed to update group. Please try again.', 'error');
  } finally {
    updating.value = false;
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
  if(checkAuth()) {
    fetchStudentData();
    loadGroups();
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