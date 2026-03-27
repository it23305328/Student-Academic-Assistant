<template>
  <div class="register-wrapper min-h-screen flex items-center justify-center bg-[#F8F9FA] font-sans relative overflow-hidden">
    <div class="absolute inset-0 pointer-events-none opacity-40">
      <div class="absolute top-0 right-0 w-full h-1 bg-[#1A1A1A]"></div>
      <div class="absolute top-20 left-10 w-48 h-48 border border-[#A89060]/10 rounded-full"></div>
    </div>
    
    <div class="register-card w-full max-w-[500px] bg-white rounded-3xl p-12 shadow-xl shadow-black/[0.03] border border-gray-100 z-10 my-10">
      <div class="card-header text-center mb-10">
        <div class="logo-box w-14 h-14 bg-[#1A1A1A] rounded-2xl flex items-center justify-center mx-auto mb-6 shadow-lg shadow-black/10">
          <span class="font-serif italic text-2xl text-[#A89060]">S</span>
        </div>
        <div class="flex items-center justify-center gap-2 mb-2">
          <div class="h-[1px] w-4 bg-[#A89060]"></div>
          <span class="text-[10px] font-black uppercase tracking-[0.3em] text-[#A89060]">New Enrollment</span>
          <div class="h-[1px] w-4 bg-[#A89060]"></div>
        </div>
        <h1 class="text-3xl font-serif font-bold text-[#1A1A1A] m-0 mb-2">Create Account</h1>
        <p class="text-[11px] font-bold text-gray-400 uppercase tracking-widest leading-relaxed">Join the StudentX ecosystem</p>
      </div>
      
      <form @submit.prevent="handleRegister" class="register-form flex flex-col gap-6">
        <div class="input-group flex flex-col gap-2">
          <label for="name" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Full Name</label>
          <div class="relative">
            <User class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060]" />
            <input 
              type="text" 
              id="name" 
              v-model="form.name" 
              placeholder="Enter your full name" 
              class="w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-4 text-[#1A1A1A] text-sm transition-all focus:outline-none focus:border-[#A89060] focus:bg-white"
              required
            >
          </div>
        </div>

        <div class="input-group flex flex-col gap-2">
          <label for="email" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Email Address</label>
          <div class="relative">
            <Mail class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060]" />
            <input 
              type="email" 
              id="email" 
              v-model="form.email" 
              placeholder="it22xxxx@my.sliit.lk" 
              :class="[
                'w-full bg-[#F8F9FA] border rounded-xl py-4 pl-11 pr-4 text-[#1A1A1A] text-sm transition-all focus:outline-none focus:border-[#A89060] focus:bg-white',
                emailError ? 'border-red-500' : 'border-transparent'
              ]"
              required
            >
          </div>
          <span v-if="emailError" class="error-text text-red-500 text-[10px] font-bold uppercase mt-1">{{ emailError }}</span>
        </div>
        
        <div class="input-group flex flex-col gap-2">
          <label for="password" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Secure Password</label>
          <div class="relative">
            <Lock class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060]" />
            <input 
              type="password" 
              id="password" 
              v-model="form.password" 
              placeholder="••••••••" 
              class="w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-4 text-[#1A1A1A] text-sm transition-all focus:outline-none focus:border-[#A89060] focus:bg-white"
              required
            >
          </div>
        </div>

        <div class="dropdown-row grid grid-cols-2 gap-4">
          <div class="input-group flex flex-col gap-2">
            <label for="academicYear" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Academic Year</label>
            <div class="relative">
              <Calendar class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060] pointer-events-none" />
              <select 
                id="academicYear" 
                v-model="form.academicYear" 
                class="w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-8 text-[#1A1A1A] text-sm appearance-none cursor-pointer transition-all focus:outline-none focus:border-[#A89060] focus:bg-white"
                required
              >
                <option value="" disabled>Select Year</option>
                <option value="Year 1">Year 1</option>
                <option value="Year 2">Year 2</option>
                <option value="Year 3">Year 3</option>
                <option value="Year 4">Year 4</option>
              </select>
              <ChevronDown class="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 pointer-events-none" />
            </div>
          </div>

          <div class="input-group flex flex-col gap-2">
            <label for="semester" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Semester</label>
            <div class="relative">
              <BookOpen class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060] pointer-events-none" />
              <select 
                id="semester" 
                v-model="form.semester" 
                class="w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-8 text-[#1A1A1A] text-sm appearance-none cursor-pointer transition-all focus:outline-none focus:border-[#A89060] focus:bg-white"
                required
              >
                <option value="" disabled>Select Semester</option>
                <option value="Semester 1">Semester 1</option>
                <option value="Semester 2">Semester 2</option>
              </select>
              <ChevronDown class="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 pointer-events-none" />
            </div>
          </div>
        </div>

        <div class="dropdown-row grid grid-cols-1 md:grid-cols-2 gap-4">
          <div class="input-group flex flex-col gap-2">
            <label for="faculty" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Faculty</label>
            <div class="relative">
              <Building2 class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060] pointer-events-none" />
              <select 
                id="faculty" 
                v-model="form.faculty" 
                @change="handleFacultyChange" 
                class="w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-8 text-[#1A1A1A] text-sm appearance-none cursor-pointer transition-all focus:outline-none focus:border-[#A89060] focus:bg-white"
                required
              >
                <option value="" disabled>Select Faculty</option>
                <option v-for="faculty in faculties" :key="faculty" :value="faculty">
                  {{ faculty }}
                </option>
              </select>
              <ChevronDown class="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 pointer-events-none" />
            </div>
          </div>

          <div class="input-group flex flex-col gap-2">
            <label for="course" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Specialization</label>
            <div class="relative">
              <GraduationCap class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-[#A89060] pointer-events-none" />
              <select 
                id="course" 
                v-model="form.course" 
                :disabled="!form.faculty" 
                :class="[
                  'w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-8 text-[#1A1A1A] text-sm appearance-none transition-all focus:outline-none focus:border-[#A89060] focus:bg-white',
                  !form.faculty ? 'opacity-50 cursor-not-allowed' : 'cursor-pointer'
                ]"
                required
              >
                <option value="" disabled>Select Course</option>
                <option v-for="course in availableCourses" :key="course" :value="course">
                  {{ course }}
                </option>
              </select>
              <ChevronDown class="absolute right-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400 pointer-events-none" />
            </div>
          </div>
        </div>
        
        <button 
          type="submit" 
          class="register-btn bg-[#1A1A1A] text-white border-none rounded-xl py-4 text-[11px] font-black uppercase tracking-[0.2em] cursor-pointer transition-all hover:bg-[#A89060] hover:-translate-y-px disabled:opacity-50 disabled:cursor-not-allowed shadow-lg shadow-black/5 mt-4" 
          :disabled="isLoading || !isFormValid"
        >
          {{ isLoading ? 'Processing...' : 'Complete Registration' }}
        </button>
      </form>
      
      <div class="card-footer text-center mt-10 text-[11px] font-bold text-gray-400 uppercase tracking-widest">
        <p>Already have an account? <router-link to="/login" class="text-[#A89060] hover:text-[#1A1A1A] transition-colors">Sign In</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { User, Mail, Lock, Calendar, BookOpen, Building2, GraduationCap, ChevronDown } from 'lucide-vue-next';
import api from '../services/api';

const router = useRouter();
const isLoading = ref(false);
const form = ref({
  name: '',
  email: '',
  password: '',
  academicYear: '',
  semester: '',
  faculty: '',
  course: ''
});

const faculties = ['Computing', 'Business', 'Hotel Management'];
const coursesByFaculty = {
  'Computing': ['IT', 'SE', 'CS'],
  'Business': ['BM', 'HR', 'Marketing'],
  'Hotel Management': ['Hospitality', 'Culinary Arts', 'Event Management']
};

const availableCourses = computed(() => {
  if (!form.value.faculty) return [];
  return coursesByFaculty[form.value.faculty] || [];
});

const handleFacultyChange = () => {
  form.value.course = '';
};

const emailError = computed(() => {
  if (!form.value.email) return '';
  const regex = /^[a-zA-Z0-9._%+-]+@my\.sliit\.lk$/i;
  if (!regex.test(form.value.email)) {
    return 'University email required (@my.sliit.lk)';
  }
  return '';
});

const isFormValid = computed(() => {
  return form.value.name && 
         form.value.email && 
         !emailError.value && 
         form.value.password && 
         form.value.academicYear && 
         form.value.semester && 
         form.value.faculty && 
         form.value.course;
});

const handleRegister = async () => {
    if (!isFormValid.value) return;
    isLoading.value = true;
    try {
        const response = await api.post('/register', form.value);
        
        if (response.status === 201) {
            alert('Account created successfully! Please login.');
            router.push('/login');
        }
    } catch (error) {
        console.error('Registration error:', error);
        if (error.response && error.response.data) {
            alert(error.response.data);
        } else {
            alert('Registration failed. Please try again.');
        }
    } finally {
        isLoading.value = false;
    }
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:ital,wght@0,700;1,700&family=Inter:wght@400;500;700;900&display=swap');

.font-serif {
  font-family: 'Playfair Display', serif;
}

.font-sans {
  font-family: 'Inter', sans-serif;
}

input::placeholder {
  color: #9CA3AF;
  font-weight: 500;
}

select {
  background-image: none;
}

/* Custom Scrollbar for the card if it overflows */
.register-card::-webkit-scrollbar {
  width: 4px;
}
.register-card::-webkit-scrollbar-thumb {
  background: #E5E7EB;
  border-radius: 10px;
}
</style>