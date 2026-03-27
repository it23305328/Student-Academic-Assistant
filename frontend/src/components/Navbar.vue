<template>
  <aside class="w-64 h-full flex-shrink-0 mr-5">
    <div class="h-full bg-[#F8F9FA] border-r border-gray-200 flex flex-col p-4 relative overflow-hidden font-sans">
      
      <div class="flex items-center px-2 py-6 mb-8">
        <div class="w-8 h-8 bg-[#1A1A1A] rounded-lg flex items-center justify-center text-[#A89060] font-serif font-black text-xl">
          D
        </div>
        <div class="flex flex-col ml-3">
          <span class="font-serif font-bold text-sm tracking-tight text-[#1A1A1A] leading-none">Digital Curator</span>
          <span class="text-[8px] font-black uppercase tracking-[0.2em] text-[#A89060] mt-1">Academic Excellence</span>
        </div>
      </div>
      
      <nav class="flex-1 flex flex-col gap-2">
        <router-link to="/student-dashboard" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2V6zM14 6a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2V6zM4 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2H6a2 2 0 01-2-2v-2zM14 16a2 2 0 012-2h2a2 2 0 012 2v2a2 2 0 01-2 2h-2a2 2 0 01-2-2v-2z"></path>
          </svg>
          <span>Dashboard</span>
        </router-link>

        <router-link to="/timetable" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
          </svg>
          <span>Timetable</span>
        </router-link>

        <router-link to="/attendance" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"></path>
          </svg>
          <span>Attendance</span>
        </router-link>

        <router-link to="/announcements" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5.882V19.24a1.76 1.76 0 01-1.76 1.76c-1.01 0-1.853-.8-1.96-1.802L7 13.5M11 5.882c0-.718.522-1.298 1.214-1.288 1.162.016 2.296.268 3.344.741l.166.074c1.119.5 2.064 1.237 2.806 2.152.155.19.244.44.244.704V13.5a1.76 1.76 0 01-1.76 1.76c-.742 0-1.357.535-1.475 1.26l-.42 2.568M11 5.882c0-.718-.522-1.298-1.214-1.288-1.162.016-2.296.268-3.344.741l-.166.074c-1.119.5-2.064 1.237-2.806 2.152-.155.19-.244.44-.244.704V13.5a1.76 1.76 0 001.76 1.76c.742 0 1.357.535 1.475 1.26l.42 2.568"></path>
          </svg>
          <span>Announcements</span>
        </router-link>

        <router-link to="/study-group-finder" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"></path>
          </svg>
          <span>Study Groups</span>
        </router-link>

        <router-link to="/peer-tutoring" class="flex items-center px-4 py-3 rounded-xl text-gray-500 hover:text-[#1A1A1A] hover:bg-white/50 text-sm font-bold transition-all duration-200 group" exact-active-class="!text-[#1A1A1A] !bg-white !shadow-sm !border !border-gray-200">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100 transition-opacity" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"></path>
          </svg>
          <span>Peer Tutoring</span>
        </router-link>
      </nav>

      <div class="pt-4 mt-auto border-t border-gray-200">
        <button @click="handleLogout" class="flex items-center w-full px-4 py-3 rounded-xl text-gray-400 hover:text-red-600 hover:bg-red-50 text-sm font-bold transition-all duration-200 group">
          <svg class="w-5 h-5 mr-3 opacity-60 group-hover:opacity-100" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
          </svg>
          <span>Logout</span>
        </button>
      </div>
    </div>
  </aside>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const role = ref(localStorage.getItem('role'));

onMounted(() => {
  window.addEventListener('storage', () => {
    role.value = localStorage.getItem('role');
  });
});

const handleLogout = () => {
  localStorage.clear();
  role.value = null;
  router.push('/login');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;700;900&display=swap');

.font-serif {
  font-family: 'Playfair Display', serif;
}
.font-sans {
  font-family: 'Inter', sans-serif;
}
</style>