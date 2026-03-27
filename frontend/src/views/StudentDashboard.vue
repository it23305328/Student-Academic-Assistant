<template>
  <div class="dashboard-wrapper flex h-screen bg-[#050511] overflow-hidden">
    <!-- Sidebar Navbar Component -->
    <Navbar />
    
    <!-- Main Content Area -->
    <div class="main-layout flex-1 flex flex-col overflow-hidden">
      
      <!-- Top Header Sticky -->
      <header class="header flex items-center justify-between h-14 px-3 mb-2 flex-shrink-0">
        <div class="header-left flex items-center gap-6">
           <div class="header-title-group">
             <h1 class="header-main-title text-base font-extrabold text-white m-0">Dashboard Overview</h1>
           </div>
           
           <div class="header-search relative">
             <div class="search-icon-wrapper absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-500">
               <Search class="w-4 h-4" />
             </div>
             <input type="text" placeholder="Search..." class="search-input bg-white/5 border border-white/10 rounded-full py-2 px-4 pl-9 w-60 text-sm text-gray-200 focus:outline-none focus:border-indigo-500/40 focus:bg-white/10 transition-all">
           </div>
        </div>
        
        <div class="header-right flex items-center gap-3">
          <button @click="isGpaModalOpen = true" class="btn-primary gpa-btn bg-indigo-600 text-white border-none rounded-full px-4 py-1.5 text-xs font-bold cursor-pointer transition-all hover:bg-indigo-500 hover:-translate-y-px shadow-md shadow-indigo-500/30">
            GPA Tracker
          </button>
          
          <div class="header-notif-group">
            <div class="notif-bell-wrapper relative z-[100]">
              <button @click="isNotifDropdownOpen = !isNotifDropdownOpen" class="notif-bell-btn w-9 h-9 flex items-center justify-center rounded-full bg-white/5 border border-white/10 text-gray-400 cursor-pointer transition-all hover:bg-white/10 hover:text-white relative" :class="{ 'has-notifs': notificationsList.length > 0 }">
                <Bell class="w-5 h-5" />
                <div v-if="notificationsList.length > 0" class="bell-badge absolute -top-1 -right-1 bg-red-500 text-white text-[9px] font-black min-w-4 h-4 rounded-full flex items-center justify-center px-1 border-2 border-[#050511] shadow-md shadow-red-500/50">{{ notificationsList.length }}</div>
              </button>

              <transition name="dropdown">
                <div v-if="isNotifDropdownOpen" class="bell-dropdown glass-morphism absolute top-full right-0 mt-3 w-80 bg-gray-900/90 backdrop-blur-2xl border border-white/10 rounded-2xl shadow-2xl z-[1000] overflow-hidden">
                  <div class="dropdown-header flex justify-between items-center px-5 py-4 border-b border-white/5">
                    <span class="dropdown-title text-sm font-extrabold text-white">Notifications</span>
                    <button v-if="notificationsList.length > 0" @click="clearAllNotifications" class="btn-clear-all text-[11px] font-bold text-indigo-400 bg-transparent border-none cursor-pointer">Clear All</button>
                  </div>
                  
                  <div class="dropdown-list max-h-96 overflow-y-auto">
                    <div v-for="notif in notificationsList" :key="notif.id" class="dropdown-item flex items-start gap-3 p-4 border-b border-white/5 transition-all hover:bg-white/5" :class="notif.type">
                      <div class="item-icon-box w-8 h-8 rounded-lg flex items-center justify-center flex-shrink-0" :class="{
                        'bg-indigo-500/10 text-indigo-400': notif.type === 'lecture',
                        'bg-amber-500/10 text-amber-400': notif.type === 'announcement',
                        'bg-red-500/10 text-red-400': notif.type === 'critical',
                        'bg-amber-500/10 text-amber-400': notif.type === 'warning'
                      }">
                        <Clock v-if="notif.type === 'lecture'" class="w-4 h-4" />
                        <Megaphone v-else-if="notif.type === 'announcement'" class="w-4 h-4" />
                        <AlertCircle v-else-if="notif.type === 'critical'" class="w-4 h-4" />
                        <Info v-else class="w-4 h-4" />
                      </div>
                      <div class="item-content flex-1">
                        <p class="item-title text-xs font-extrabold text-white m-0 mb-0.5">{{ notif.title }}</p>
                        <p class="item-msg text-[11px] text-gray-400 m-0 leading-relaxed">{{ notif.message }}</p>
                      </div>
                      <button @click="markAsRead(notif.id)" class="btn-read-check bg-transparent border-none text-gray-500 cursor-pointer p-1 rounded transition-all hover:bg-white/5 hover:text-green-400" title="Mark as read">
                        <Check class="w-3.5 h-3.5" />
                      </button>
                    </div>
                    <div v-if="notificationsList.length === 0" class="dropdown-empty py-10 text-center text-gray-500 text-xs">
                      <p>No new notifications</p>
                    </div>
                  </div>
                </div>
              </transition>
            </div>
          </div>
        </div>
      </header>

      <!-- Upcoming Alerts Bar -->
      <NotificationAlertBar :alerts="upcomingAlerts" @dismiss="dismissAlert" />

      <!-- Announcements Banner -->
      <div v-if="recentAnnouncements.length > 0" class="announcements-banner bg-gradient-to-r from-indigo-500/10 to-purple-500/10 backdrop-blur-md border border-indigo-500/20 rounded-2xl mx-3 mb-5 p-4 flex items-center gap-4 cursor-pointer transition-all hover:bg-gradient-to-r hover:from-indigo-500/15 hover:to-purple-500/15 hover:border-indigo-500/40 hover:-translate-y-0.5">
        <div class="banner-icon text-3xl animate-bounce">
          <Megaphone class="w-8 h-8 text-indigo-400" />
        </div>
        <div class="banner-content flex-1">
          <div class="banner-title text-indigo-400 text-sm font-extrabold mb-2 tracking-wide">LATEST ANNOUNCEMENTS</div>
          <div class="banner-messages flex flex-col gap-1.5">
            <div 
              v-for="ann in recentAnnouncements.slice(0, 2)" 
              :key="ann.id" 
              class="banner-message flex items-center gap-3 text-sm cursor-pointer py-1 px-2 rounded-lg transition-all hover:bg-white/5"
              @click="viewAnnouncement(ann.id)"
            >
              <span class="message-topic font-bold text-white whitespace-nowrap">{{ ann.topic }}</span>
              <span class="message-preview text-gray-400 text-xs truncate">{{ truncateText(ann.content, 60) }}</span>
            </div>
          </div>
        </div>
        <router-link to="/announcements" class="view-all-btn bg-indigo-500/20 border border-indigo-500/30 text-indigo-400 px-4 py-2 rounded-full text-xs font-bold no-underline transition-all hover:bg-indigo-500/30 hover:translate-x-0.5 whitespace-nowrap">
          View All →
        </router-link>
      </div>

      <!-- Main Scrollable Inner Section -->
      <section class="content-scroll flex-1 overflow-y-auto pr-2">
        <div class="dashboard-grid grid grid-cols-12 gap-4">
          
          <!-- Today's Schedule Card -->
          <div class="card card-wide col-span-12 lg:col-span-8 bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg">
             <div class="card-inner p-4 relative z-10">
               <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
               <div class="card-header flex justify-between items-start mb-3">
                 <div class="card-title-group">
                   <h2 class="card-main-title text-sm font-extrabold text-white m-0 mb-1">Today's Classes & Gaps</h2>
                   <p class="card-sub-title text-[11px] text-gray-500 m-0">Manage your daily schedule and attendance.</p>
                 </div>
                 <div class="card-header-icon bg-indigo-500/10 p-1.5 rounded-lg">
                   <Clock class="w-4 h-4 text-indigo-400" />
                 </div>
               </div>
               
               <div class="schedule-list flex flex-col gap-2">
                 <!-- Classes Section -->
                 <div class="section-divider text-[9px] font-extrabold text-gray-500 uppercase flex items-center gap-3 my-2">
                   ACTIVE CLASSES
                   <span class="flex-1 h-px bg-white/5"></span>
                 </div>
                 <div v-if="isLoadingTodayClasses" class="loading-state text-center py-4">
                    <div class="pulse-text text-gray-400 text-xs">Loading classes...</div>
                 </div>
                 <template v-else>
                   <div v-for="cls in todayClasses" :key="cls.id" class="schedule-item class-item flex items-center justify-between p-2.5 bg-white/5 border border-white/10 rounded-xl transition-all hover:bg-white/10" :class="{ 'item-missed border-red-500/20 bg-red-500/5': getClassStatus(cls) === 'missed' }">
                      <div class="item-left flex items-center gap-3">
                        <div class="status-dot w-1.5 h-1.5 rounded-full" :class="{
                          'bg-green-500 shadow-md shadow-green-500/40': getClassStatus(cls) === 'ongoing',
                          'bg-gray-500': getClassStatus(cls) === 'upcoming',
                          'bg-red-500 shadow-md shadow-red-500/40': getClassStatus(cls) === 'missed'
                        }"></div>
                        <div class="item-info flex flex-col gap-0.5">
                          <span class="item-text font-bold text-white text-xs">{{ cls.subjectName }}</span>
                          <span class="item-subtext text-[10px] text-gray-500">{{ formatTime(cls.startTime) }} - {{ formatTime(cls.endTime) }}</span>
                        </div>
                      </div>
                      <div class="item-actions flex items-center gap-2">
                        <div v-if="cls.marked" class="status-badge checked flex items-center gap-1.5 bg-green-500/10 text-green-400 border border-green-500/20 px-2 py-1 rounded-full text-[10px] font-bold">
                          <Check class="w-3 h-3" />
                          <span>Checked-in</span>
                        </div>

                        <button v-else-if="getClassStatus(cls) === 'ongoing'" @click="markAsPresent(cls.subjectName)" class="btn-mark glow-indigo bg-green-500/10 border border-green-500/20 text-green-400 px-2 py-1 rounded-lg text-[10px] font-bold flex items-center gap-1.5 transition-all hover:bg-green-500/20">
                          <Check class="w-3 h-3" />
                          Mark Present
                        </button>

                        <div v-else-if="getClassStatus(cls) === 'missed'" class="status-badge missed flex items-center gap-1.5 bg-red-500/10 text-red-400 border border-red-500/20 px-2 py-1 rounded-full text-[10px] font-bold">
                          <X class="w-3 h-3" />
                          <span>Missed</span>
                        </div>

                        <div v-else class="status-badge upcoming bg-gray-500/10 text-gray-400 border border-gray-500/20 px-2 py-1 rounded-full text-[10px] font-bold">
                           <span>Scheduled</span>
                        </div>
                      </div>
                   </div>
                   <div v-if="!todayClasses.length" class="empty-state text-center py-4 text-gray-500 text-xs">No classes scheduled for today.</div>
                 </template>

                 <!-- Free Slots Section -->
                 <div class="section-divider text-[9px] font-extrabold text-gray-500 uppercase flex items-center gap-3 my-2">
                   FREE GAPS
                   <span class="flex-1 h-px bg-white/5"></span>
                 </div>
                 <div v-if="isLoadingFreeSlots" class="loading-state text-center py-4">
                    <div class="pulse-text text-gray-400 text-xs">Calculating gaps...</div>
                 </div>
                 <template v-else>
                   <div v-for="(slot, idx) in slots" :key="idx" class="schedule-item gap-item flex items-center justify-between p-2.5 bg-white/5 border border-white/10 rounded-xl">
                      <div class="item-left flex items-center gap-3">
                        <div class="status-dot idle w-1.5 h-1.5 rounded-full bg-gray-500"></div>
                        <span class="item-text text-xs text-white">{{ slot }}</span>
                      </div>
                      <span class="badge-accent text-[10px] font-extrabold text-indigo-400 bg-indigo-500/10 px-2 py-1 rounded-md border border-indigo-500/20 uppercase">Available Slot</span>
                   </div>
                   <div v-if="!slots.length" class="empty-state text-center py-4 text-gray-500 text-xs">No free gaps found.</div>
                 </template>
               </div>
             </div>
          </div>

          <!-- Attendance Tracker Card -->
          <div class="card card-small col-span-12 lg:col-span-4 bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg">
             <div class="card-inner p-4 relative z-10">
               <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
               <div class="card-header mb-3">
                 <div class="card-title-group">
                   <h2 class="card-main-title text-sm font-extrabold text-white m-0 mb-1">Attendance Tracker</h2>
                   <p class="card-sub-title text-[11px] text-gray-500 m-0">Minimum 80% required.</p>
                 </div>
               </div>
               
               <div class="attendance-list no-scrollbar flex flex-col gap-3 max-h-[400px] overflow-y-auto pr-1">
                 <div v-if="isLoadingAttendance" class="loading-state text-center py-4">
                    <div class="spinner-small w-6 h-6 border-2 border-orange-500/10 border-t-orange-500 rounded-full animate-spin mx-auto mb-2"></div>
                 </div>
                 <template v-else>
                    <div v-for="item in attendanceSummary" :key="item.subjectName" class="attendance-row flex flex-col gap-2 p-3 bg-white/5 rounded-xl border border-white/10 transition-all" :class="{ 'warning-row bg-red-500/5 border-red-500/20': item.lowAttendanceWarning }">
                      <div class="row-main flex justify-between items-center">
                        <span class="sub-name text-xs font-bold text-white">{{ item.subjectName }}</span>
                        <div class="status-box flex items-center gap-2">
                          <span v-if="item.lowAttendanceWarning" class="status-icon text-red-400 text-xs">⚠️</span>
                          <span v-else class="status-icon text-green-400 text-xs">✓</span>
                          <span class="perc-val text-sm font-extrabold" :class="item.lowAttendanceWarning ? 'text-red-400' : 'text-green-400'">{{ Math.round(item.percentage) }}%</span>
                        </div>
                      </div>
                      <div class="perc-track w-full h-1 bg-white/10 rounded-full overflow-hidden">
                        <div class="perc-fill h-full transition-all duration-1000" :class="item.lowAttendanceWarning ? 'bg-red-500 shadow-md shadow-red-500/30' : 'bg-green-500 shadow-md shadow-green-500/30'" :style="{ width: item.percentage + '%' }"></div>
                      </div>
                      <p v-if="item.lowAttendanceWarning" class="warning-msg text-[10px] font-bold text-red-400 m-0 uppercase tracking-wide">Action Required: Attendance below 80%</p>
                    </div>
                    <div v-if="!attendanceSummary.length" class="empty-state text-center py-4 text-gray-500 text-xs">No attendance data yet.</div>
                 </template>
               </div>
             </div>
          </div>

          <!-- Upcoming Deadlines Card -->
          <div class="card card-small col-span-12 lg:col-span-4 bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg">
             <div class="card-inner p-4 relative z-10">
               <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
               <div class="card-header flex justify-between items-start mb-3">
                 <div class="card-title-group">
                   <h2 class="card-main-title text-sm font-extrabold text-white m-0 mb-1">Upcoming Deadlines</h2>
                   <p class="card-sub-title text-[11px] text-gray-500 m-0">Live countdown to your tasks.</p>
                 </div>
                 <button @click="showDeadlineForm = !showDeadlineForm" class="btn-icon-bg bg-white/5 border border-white/10 text-white w-8 h-8 rounded-lg flex items-center justify-center cursor-pointer transition-all hover:bg-white/10 hover:border-indigo-500">
                   <Plus class="w-4 h-4" />
                 </button>
               </div>

               <!-- Add Deadline Form -->
               <div v-if="showDeadlineForm" class="deadline-form-mini bg-white/5 border border-white/10 rounded-xl p-3 mb-4 flex flex-col gap-2">
                  <div class="mini-form-row flex gap-2">
                    <input type="text" v-model="deadlineForm.subjectName" placeholder="Subject" class="mini-input flex-1 bg-black/20 border border-white/10 rounded-lg px-2 py-1.5 text-white text-[11px] focus:outline-none focus:border-indigo-500">
                    <input type="text" v-model="deadlineForm.title" placeholder="Title" class="mini-input flex-1 bg-black/20 border border-white/10 rounded-lg px-2 py-1.5 text-white text-[11px] focus:outline-none focus:border-indigo-500">
                  </div>
                  <input type="datetime-local" v-model="deadlineForm.deadline" class="mini-input full-width bg-black/20 border border-white/10 rounded-lg px-2 py-1.5 text-white text-[11px] focus:outline-none focus:border-indigo-500 [&::-webkit-calendar-picker-indicator]:filter [&::-webkit-calendar-picker-indicator]:invert [&::-webkit-calendar-picker-indicator]:cursor-pointer">
                  <button @click="handleAddDeadline" class="btn-mini-save bg-indigo-600 text-white border-none rounded-lg py-1.5 text-[11px] font-bold cursor-pointer transition-all hover:bg-indigo-500">Save Deadline</button>
               </div>
               
               <div class="deadline-list no-scrollbar flex flex-col gap-2 max-h-[300px] overflow-y-auto">
                  <div v-if="isLoadingAssignments" class="loading-state text-center py-4">
                     <div class="spinner-small w-6 h-6 border-2 border-orange-500/10 border-t-orange-500 rounded-full animate-spin mx-auto mb-2"></div>
                  </div>
                  <template v-else>
                    <div v-for="item in assignments" :key="item.id" class="deadline-item flex justify-between items-center p-2.5 bg-white/5 border border-white/10 rounded-xl transition-all" :class="{
                      'border-l-3 border-l-indigo-500': item.countdown?.urgency === 'normal',
                      'border-l-3 border-l-amber-500 shadow-md shadow-amber-500/10': item.countdown?.urgency === 'warning',
                      'border-l-3 border-l-red-500 shadow-md shadow-red-500/20': item.countdown?.urgency === 'critical',
                      'border-l-3 border-l-red-500 bg-red-500/5 animate-pulse': item.countdown?.urgency === 'urgent'
                    }">
                       <div class="deadline-info flex flex-col gap-0.5">
                          <span class="deadline-subject text-[9px] font-extrabold text-gray-500 uppercase">{{ item.subjectName }}</span>
                          <span class="deadline-title text-xs font-bold text-white">{{ item.title }}</span>
                       </div>
                       <div class="deadline-time flex flex-col items-end gap-0.5" :class="{ 'animate-pulse': item.countdown?.urgency === 'critical' }">
                          <span class="countdown-text font-mono text-xs font-extrabold text-gray-300">{{ item.countdown?.text }}</span>
                          <span v-if="item.countdown?.urgency === 'urgent'" class="urgent-tag text-[8px] font-extrabold bg-red-500 text-white px-1.5 py-0.5 rounded">URGENT</span>
                       </div>
                    </div>
                    <div v-if="!assignments.length" class="empty-state text-center py-4 text-gray-500 text-xs">No upcoming deadlines.</div>
                  </template>
               </div>
             </div>
          </div>

          <!-- GPA / Standing Card -->
          <div class="card card-small col-span-12 lg:col-span-4 bg-white/5 backdrop-blur-md border border-white/10 rounded-2xl overflow-hidden shadow-lg relative">
            <div class="card-inner p-4 relative z-10">
              <div class="card-glow absolute inset-0 bg-gradient-to-tr from-indigo-500/5 to-transparent pointer-events-none"></div>
              
              <!-- Status Badge -->
              <div class="status-badge-top absolute top-3 right-3 bg-indigo-500/15 border border-indigo-500/30 text-indigo-400 px-2 py-1 rounded-full text-[10px] font-extrabold uppercase z-20">Academic Excellence</div>
              
              <div class="gauge-section flex-1 flex items-center justify-center py-2">
                <div class="gauge-container relative w-[120px] h-[120px] flex items-center justify-center">
                  <svg viewBox="0 0 100 100" class="gauge-svg w-full h-full transform -rotate-90">
                    <defs>
                      <linearGradient id="gauge-gradient" x1="0%" y1="0%" x2="100%" y2="0%">
                        <stop offset="0%" style="stop-color: #6366f1; stop-opacity: 1" />
                        <stop offset="100%" style="stop-color: #d946ef; stop-opacity: 1" />
                      </linearGradient>
                    </defs>
                    <circle cx="50" cy="50" r="42" class="gauge-bg fill-none stroke-white/5 stroke-[8]" />
                    <circle cx="50" cy="50" r="42" class="gauge-progress fill-none stroke-[url(#gauge-gradient)] stroke-[8] stroke-linecap-round transition-all duration-1000" stroke-dasharray="263.9" stroke-dashoffset="18.5" />
                  </svg>
                  <div class="gauge-content absolute flex flex-col items-center justify-center">
                    <span class="gauge-value text-3xl font-extrabold text-white leading-none">3.72</span>
                    <span class="gauge-max text-[9px] font-extrabold text-gray-500 mt-1">GPA</span>
                  </div>
                </div>
              </div>

              <div class="target-info mt-auto text-center">
                <p class="target-text text-xs font-bold text-gray-300 mb-2">0.28 points to Dean's List</p>
                <div class="target-indicator flex items-center justify-center gap-2 opacity-70">
                  <span class="dot-pulse w-1.5 h-1.5 bg-green-500 rounded-full relative"></span>
                  <span class="indicator-label text-[10px] font-semibold text-gray-400">Top 5% Student</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Smart Planner Card -->
          <div class="card card-small col-span-12 lg:col-span-4 bg-white/5 backdrop-blur-md border border-cyan-500/20 rounded-2xl overflow-hidden shadow-lg">
             <div class="card-inner p-4 relative z-10">
               <div class="card-glow planner-glow absolute inset-0 bg-gradient-to-tr from-cyan-500/10 to-transparent pointer-events-none"></div>
               <div class="card-header flex justify-between items-start mb-3">
                 <div class="card-title-group">
                   <h2 class="card-main-title text-sm font-extrabold text-white m-0 mb-1">Smart Planner</h2>
                   <p class="card-sub-title text-[11px] text-gray-500 m-0">AI-powered gap analysis.</p>
                 </div>
                 <div class="card-header-icon highlight-cyan bg-cyan-500/10 p-1.5 rounded-lg">
                   <Zap class="w-4 h-4 text-cyan-400" />
                 </div>
               </div>
               
               <div class="planner-content mt-2 flex flex-col gap-4">
                  <div v-if="isLoadingTodayClasses || isLoadingAssignments" class="loading-state text-center py-4">
                     <div class="spinner-small cyan w-6 h-6 border-2 border-cyan-500/10 border-t-cyan-500 rounded-full animate-spin mx-auto mb-2"></div>
                  </div>
                  <template v-else>
                    <div class="suggestion-box bg-cyan-500/5 border border-cyan-500/10 rounded-xl p-3">
                       <div class="suggestion-header mb-1">
                          <span class="suggestion-title text-xs font-extrabold text-cyan-400 uppercase tracking-wide">{{ smartSuggestion?.title }}</span>
                       </div>
                       <p class="suggestion-msg text-xs text-gray-300 leading-relaxed m-0">{{ smartSuggestion?.message }}</p>
                    </div>
                    
                    <div class="gap-stats flex gap-2">
                       <div class="stat-pill flex items-center gap-2 bg-white/5 border border-white/10 px-3 py-1.5 rounded-full text-[11px] font-semibold text-gray-400">
                          <Clock class="w-3 h-3" />
                          <span>{{ freeSlotsCount }} Gaps Today</span>
                       </div>
                    </div>

                    <div class="gap-list-mini flex flex-col gap-2">
                      <div v-for="(gap, idx) in freeGaps" :key="idx" class="gap-item-mini flex items-center gap-2 p-2 bg-white/5 border border-white/10 rounded-lg transition-all hover:bg-cyan-500/5 hover:border-cyan-500/20">
                        <div class="gap-dot w-1 h-1 bg-cyan-400 rounded-full shadow-md shadow-cyan-400/60"></div>
                        <span class="gap-time-text text-[11px] font-semibold text-gray-300">{{ formatTime12h(gap.startTime) }} - {{ formatTime12h(gap.endTime) }}</span>
                      </div>
                      <div v-if="!freeGaps.length" class="empty-gap-msg text-center py-2 text-gray-500 text-[11px] bg-white/5 rounded-lg border border-white/10">
                        No free gaps available today
                      </div>
                    </div>
                  </template>
               </div>
             </div>
          </div>
          
        </div>
      </section>
      
      <!-- Modal Overlay -->
     <GPAModal :isOpen="isGpaModalOpen" @close="isGpaModalOpen = false" />

      <!-- Notification Toast Container -->
      <div class="toast-container fixed top-6 right-6 z-[9999] flex flex-col gap-3 w-80">
        <transition-group name="toast">
          <div v-for="toast in notificationToasts" :key="toast.id" class="toast-item relative bg-gray-900/90 backdrop-blur-2xl rounded-2xl p-4 shadow-2xl border border-white/10 overflow-hidden" :class="{
            'border-l-4 border-l-indigo-500': toast.type === 'lecture',
            'border-l-4 border-l-amber-500': toast.type === 'announcement',
            'border-l-4 border-l-red-500': toast.type === 'critical',
            'border-2 border-red-500': toast.type === 'critical'
          }">
            <div class="toast-glow absolute inset-0 pointer-events-none" :class="{
              'bg-gradient-to-r from-indigo-500/15 to-transparent': toast.type === 'lecture',
              'bg-gradient-to-r from-amber-500/10 to-transparent': toast.type === 'announcement',
              'bg-gradient-to-r from-red-500/20 to-transparent': toast.type === 'critical'
            }"></div>
            <div class="toast-content flex items-start gap-3 relative z-10">
              <div class="toast-icon w-8 h-8 rounded-lg flex items-center justify-center bg-white/5">
                <Clock v-if="toast.type === 'lecture'" class="w-4 h-4 text-indigo-400" />
                <Megaphone v-else-if="toast.type === 'announcement'" class="w-4 h-4 text-amber-400" />
                <AlertCircle v-else class="w-4 h-4 text-red-400" />
              </div>
              <div class="toast-body flex-1">
                <p class="toast-title text-xs font-extrabold text-white m-0 mb-0.5">{{ toast.title }}</p>
                <p class="toast-message text-[11px] text-gray-400 m-0 leading-relaxed">{{ toast.message }}</p>
              </div>
              <button @click="dismissToast(toast.id)" class="toast-close bg-transparent border-none text-gray-500 text-xl cursor-pointer p-0 leading-none transition-colors hover:text-white">&times;</button>
            </div>
          </div>
        </transition-group>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Search, Bell, Clock, Megaphone, AlertCircle, Info, Check, X, Plus, Zap } from 'lucide-vue-next';
import GPAModal from '../components/GPAModal.vue';
import NotificationAlertBar from '../components/NotificationAlertBar.vue';
import timetableService from '../services/timetableService';
import attendanceService from '../services/attendanceService';
import assignmentService from '../services/assignmentService';
import notificationService from '../services/notificationService';
import announcementService from '../services/announcementService';
import Navbar from '../components/Navbar.vue';

const router = useRouter();

const isGpaModalOpen = ref(false);
const slots = ref([]);
const todayClasses = ref([]);
const attendanceSummary = ref([]);
const assignments = ref([]);
const notificationToasts = ref([]);
const notificationsList = ref([]);
const isNotifDropdownOpen = ref(false);
const notifiedIds = new Set();
const smartSuggestion = ref(null);
const freeSlotsCount = ref(0);
const freeGaps = ref([]);
const isLoadingFreeSlots = ref(true);
const isLoadingTodayClasses = ref(true);
const isLoadingAttendance = ref(true);
const isLoadingAssignments = ref(true);
const upcomingAlerts = ref([]);
const isLoadingAlerts = ref(false);
const announcements = ref([]);
const recentAnnouncements = ref([]);

// Deadline Form
const showDeadlineForm = ref(false);
const deadlineForm = ref({
    subjectName: '',
    title: '',
    deadline: ''
});

// Student Info for filtering announcements
const studentInfo = ref({
    year: null,
    semester: null,
    course: null
});

const checkAuth = () => {
    const token = localStorage.getItem('token');
    const studentId = localStorage.getItem('studentId');
    if (!token || !studentId) {
        router.push('/login');
        return null;
    }
    return studentId;
};

const fetchAnnouncements = async () => {
    try {
        const response = await announcementService.getAllAnnouncements();
        announcements.value = response.data || [];
        
        // Filter announcements for this student
        const filtered = filterAnnouncementsForStudent(announcements.value);
        
        // Get recent announcements (last 3)
        recentAnnouncements.value = filtered
            .sort((a, b) => {
                const dateA = new Date(a.datetime || a.createdAt);
                const dateB = new Date(b.datetime || b.createdAt);
                return dateB - dateA;
            })
            .slice(0, 3);
            
        // Check for new announcements to notify
        checkNewAnnouncements();
    } catch (error) {
        console.error('Error fetching announcements:', error);
    }
};

const filterAnnouncementsForStudent = (anns) => {
    return anns.filter(ann => {
        const yearMatch = !ann.targetYear || 
                         ann.targetYear === 'all' || 
                         ann.targetYear === studentInfo.value.year;
        
        const semesterMatch = !ann.targetSemester || 
                             ann.targetSemester === 'all' || 
                             ann.targetSemester === studentInfo.value.semester;
        
        const facultyMatch = !ann.targetFaculty || 
                            ann.targetFaculty === 'all' || 
                            ann.targetFaculty === studentInfo.value.course;
        
        return yearMatch && semesterMatch && facultyMatch;
    });
};

const checkNewAnnouncements = () => {
    announcements.value.forEach(ann => {
        const annId = `announcement-${ann.id}`;
        if (!notifiedIds.has(annId)) {
            const annDate = new Date(ann.datetime || ann.createdAt);
            const now = new Date();
            const hourInMs = 60 * 60 * 1000;
            
            if (now - annDate < hourInMs) {
                triggerNotification({
                    type: 'announcement',
                    title: 'New Announcement',
                    message: `${ann.topic} - ${ann.content.substring(0, 80)}...`
                });
                notifiedIds.add(annId);
            }
        }
    });
};

const fetchFreeSlots = async (studentId) => {
    isLoadingFreeSlots.value = true;
    try {
        const todayStr = new Date().toISOString().split('T')[0];
        const response = await timetableService.getFreeSlots(studentId, todayStr);
        slots.value = response.data || [];
    } catch (error) {
        console.error('Error fetching gaps:', error);
        slots.value = [];
    } finally {
        isLoadingFreeSlots.value = false;
    }
};

const fetchTodayClasses = async (studentId) => {
    isLoadingTodayClasses.value = true;
    try {
        const response = await timetableService.getTodayTimetable(studentId);
        todayClasses.value = response.data || [];
    } catch (error) {
        console.error('Error fetching today classes:', error);
    } finally {
        isLoadingTodayClasses.value = false;
    }
};

const fetchAttendanceSummary = async (studentId) => {
    isLoadingAttendance.value = true;
    try {
        const response = await attendanceService.getAttendanceSummary(studentId);
        attendanceSummary.value = response.data || [];
    } catch (error) {
        console.error('Error fetching attendance summary:', error);
    } finally {
        isLoadingAttendance.value = false;
    }
};

const updateSmartPlanner = () => {
    const gaps = calculateFreeSlots();
    freeGaps.value = gaps;
    freeSlotsCount.value = gaps.length;
    smartSuggestion.value = getSmartSuggestion(gaps);
};

const calculateFreeSlots = () => {
    const dayStart = 8 * 60;
    const dayEnd = 18 * 60;
    
    const sortedClasses = [...todayClasses.value].sort((a, b) => {
        return timeToMinutes(a.startTime) - timeToMinutes(b.startTime);
    });

    const gaps = [];
    let lastEnd = dayStart;

    sortedClasses.forEach(cls => {
        const start = timeToMinutes(cls.startTime);
        const end = timeToMinutes(cls.endTime);

        if (start - lastEnd >= 60) {
            gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(start) });
        }
        lastEnd = Math.max(lastEnd, end);
    });

    if (dayEnd - lastEnd >= 60) {
        gaps.push({ startTime: minutesToTime(lastEnd), endTime: minutesToTime(dayEnd) });
    }

    return gaps;
};

const formatTime12h = (timeStr) => {
    if (!timeStr) return '';
    const [h, m] = timeStr.split(':').map(Number);
    const ampm = h >= 12 ? 'PM' : 'AM';
    const hours = h % 12 || 12;
    const minutes = m.toString().padStart(2, '0');
    return `${hours}:${minutes} ${ampm}`;
};

const timeToMinutes = (timeStr) => {
    if (!timeStr) return 0;
    const [h, m] = timeStr.split(':').map(Number);
    return h * 60 + m;
};

const minutesToTime = (mins) => {
    const h = Math.floor(mins / 60);
    const m = mins % 60;
    return `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}`;
};

const getSmartSuggestion = (gaps) => {
    if (gaps.length === 0) return { title: "Packed Day!", message: "No free gaps found today. Keep pushing!" };

    const urgentDeadline = assignments.value.find(a => {
        const diff = new Date(a.deadline) - new Date();
        return diff > 0 && diff < (3 * 24 * 60 * 60 * 1000);
    });

    const mainGap = gaps[0];
    if (urgentDeadline) {
        return {
            title: `Smart Gap: ${formatTime12h(mainGap.startTime)} - ${formatTime12h(mainGap.endTime)}`,
            message: `Perfect time to work on your ${urgentDeadline.title} assignment! ✍️`
        };
    } else {
        return {
            title: `Free Time: ${formatTime12h(mainGap.startTime)} - ${formatTime12h(mainGap.endTime)}`,
            message: "Free time found! Time for a coffee break? ☕"
        };
    }
};

const fetchAssignments = async (studentId) => {
    isLoadingAssignments.value = true;
    try {
        const response = await assignmentService.getAssignments(studentId);
        assignments.value = (response.data || []).map(a => ({
            ...a,
            countdown: calculateCountdown(a.deadline)
        }));
    } catch (error) {
        console.error('Error fetching assignments:', error);
    } finally {
        isLoadingAssignments.value = false;
    }
};

const handleAddDeadline = async () => {
    const studentId = checkAuth();
    if (!studentId) return;

    if (!deadlineForm.value.subjectName || !deadlineForm.value.title || !deadlineForm.value.deadline) {
        alert('Please fill all fields');
        return;
    }

    try {
        await assignmentService.addAssignment({
            ...deadlineForm.value,
            studentId,
            status: 'Pending'
        });
        showDeadlineForm.value = false;
        deadlineForm.value = { subjectName: '', title: '', deadline: '' };
        fetchAssignments(studentId);
    } catch (error) {
        alert('Failed to add deadline');
    }
};

const fetchUpcomingAlerts = async (studentId) => {
    isLoadingAlerts.value = true;
    try {
        const response = await notificationService.getUpcomingAlerts(studentId);
        upcomingAlerts.value = response.data || [];
    } catch (error) {
        console.error('Error fetching alerts:', error);
    } finally {
        isLoadingAlerts.value = false;
    }
};

const dismissAlert = (id) => {
    upcomingAlerts.value = upcomingAlerts.value.filter(a => a.id !== id);
};

const markAsPresent = async (subjectName) => {
    const studentId = checkAuth();
    if (!studentId) return;
    
    try {
        await attendanceService.markAttendance(studentId, subjectName, true);
        alert(`Attendance marked for ${subjectName}`);
        fetchAttendanceSummary(studentId);
    } catch (error) {
        alert('Failed to mark attendance.');
    }
};

const calculateCountdown = (deadlineStr) => {
    if (!deadlineStr) return null;
    const deadline = new Date(deadlineStr);
    const now = new Date();
    const diff = deadline - now;

    if (diff <= 0) return { expired: true, text: 'OVERDUE' };

    const days = Math.floor(diff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((diff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));

    return {
        expired: false,
        days,
        hours,
        minutes,
        text: `${days.toString().padStart(2, '0')}d : ${hours.toString().padStart(2, '0')}h : ${minutes.toString().padStart(2, '0')}m`,
        urgency: diff < (1000 * 60 * 60) ? 'urgent' : (diff < (1000 * 60 * 60 * 24) ? 'critical' : (diff < (1000 * 60 * 60 * 24 * 3) ? 'warning' : 'normal'))
    };
};

const formatTime = (timeStr) => {
    if (!timeStr) return '';
    return timeStr.substring(0, 5);
};

const getClassStatus = (cls) => {
  const now = new Date();
  const startTime = timeToMinutes(cls.startTime);
  const endTime = timeToMinutes(cls.endTime);
  const currentMins = now.getHours() * 60 + now.getMinutes();

  if (currentMins < startTime) return 'upcoming';
  if (currentMins >= startTime && currentMins <= endTime) return 'ongoing';
  return 'missed';
};

const viewAnnouncement = (announcementId) => {
    router.push('/announcements');
};

const truncateText = (text, length) => {
    if (!text) return '';
    if (text.length <= length) return text;
    return text.substring(0, length) + '...';
};

let countdownInterval;

onMounted(async () => {
    const studentId = checkAuth();
    if (studentId) {
        const userData = JSON.parse(localStorage.getItem('userData') || '{}');
        studentInfo.value = {
            year: userData.academicYear || 1,
            semester: userData.semester || 1,
            course: userData.course || 'IT'
        };
        
        fetchFreeSlots(studentId);
        fetchAttendanceSummary(studentId);
        await fetchAssignments(studentId);
        await fetchTodayClasses(studentId);
        fetchUpcomingAlerts(studentId);
        await fetchAnnouncements();
        updateSmartPlanner();

        checkNotifications();
        const notificationInterval = setInterval(() => {
            checkNotifications();
            checkNewAnnouncements();
        }, 60000);

        countdownInterval = setInterval(() => {
            assignments.value = assignments.value.map(a => ({
                ...a,
                countdown: calculateCountdown(a.deadline)
            }));
        }, 1000);

        onUnmounted(() => {
            clearInterval(notificationInterval);
            if (countdownInterval) clearInterval(countdownInterval);
        });
    }
});

const checkNotifications = () => {
    const now = new Date();
    
    todayClasses.value.forEach(cls => {
        if (!cls.startTime) return;
        const [hours, minutes] = cls.startTime.split(':').map(Number);
        const lectureDate = new Date();
        lectureDate.setHours(hours, minutes, 0, 0);
        
        const diffMs = lectureDate - now;
        const diffMins = Math.floor(diffMs / 60000);

        if (diffMins === 60 && !notifiedIds.has(`lecture-${cls.id}`)) {
            triggerNotification({
                type: 'lecture',
                title: 'Upcoming Lecture',
                message: `${cls.subjectName} starts in 60 minutes!`
            });
            notifiedIds.add(`lecture-${cls.id}`);
        }
    });

    checkDeadlines(now);
};

const checkDeadlines = (now) => {
    assignments.value.forEach(a => {
        if (!a.deadline) return;
        const deadlineDate = new Date(a.deadline);
        const diffMs = deadlineDate - now;
        const diffMins = Math.floor(diffMs / 60000);

        if (diffMins <= 1440 && diffMins > 1380 && !notifiedIds.has(`warn-1d-${a.id}`)) {
            triggerNotification({
                type: 'warning',
                title: 'Deadline Tomorrow',
                message: `${a.title} is due in 24 hours. Get ready!`
            });
            notifiedIds.add(`warn-1d-${a.id}`);
        }

        if (diffMins <= 120 && diffMins > 0 && !notifiedIds.has(`crit-2h-${a.id}`)) {
            triggerNotification({
                type: 'critical',
                title: 'URGENT: Submit Soon!',
                message: `${a.title} is due in 2 hours! Final push! 🚀`
            });
            notifiedIds.add(`crit-2h-${a.id}`);
        }
    });
};

const triggerNotification = (details) => {
    const id = Date.now() + Math.random();
    const toast = {
        ...details,
        id: id
    };
    notificationToasts.value.push(toast);

    notificationsList.value.unshift({
        ...details,
        id: id,
        timestamp: new Date()
    });
    
    setTimeout(() => {
        dismissToast(toast.id);
    }, 10000);
};

const clearAllNotifications = () => {
    notificationsList.value = [];
};

const markAsRead = (id) => {
    notificationsList.value = notificationsList.value.filter(n => n.id !== id);
};

const dismissToast = (id) => {
    notificationToasts.value = notificationToasts.value.filter(t => t.id !== id);
};

onUnmounted(() => {
    if (countdownInterval) clearInterval(countdownInterval);
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

@keyframes bounce {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-5px); }
}

.animate-bounce {
  animation: bounce 2s infinite;
}

/* Dropdown transitions */
.dropdown-enter-active, .dropdown-leave-active {
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}
.dropdown-enter-from, .dropdown-leave-to {
  opacity: 0;
  transform: translateY(-10px) scale(0.95);
}

/* Toast transitions */
.toast-enter-active, .toast-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
.toast-enter-from {
  opacity: 0;
  transform: translateX(50px) scale(0.9);
}
.toast-leave-to {
  opacity: 0;
  transform: translateX(20px) scale(0.95);
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

/* Dot pulse animation */
.dot-pulse::after {
  content: '';
  position: absolute;
  inset: -4px;
  border: 1px solid #10b981;
  border-radius: 50%;
  animation: pulse-ring 1.5s infinite;
}

@keyframes pulse-ring {
  0% { transform: scale(0.5); opacity: 1; }
  100% { transform: scale(1.5); opacity: 0; }
}
</style>