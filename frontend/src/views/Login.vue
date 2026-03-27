<template>
  <div class="login-wrapper min-h-screen flex items-center justify-center bg-[#F8F9FA] font-sans relative overflow-hidden">
    <div class="absolute inset-0 pointer-events-none opacity-40">
      <div class="absolute top-0 left-0 w-full h-1 bg-[#1A1A1A]"></div>
      <div class="absolute bottom-10 right-10 w-32 h-32 border border-[#A89060]/20 rounded-full"></div>
    </div>
    
    <div class="login-card w-full max-w-md bg-white rounded-3xl p-12 shadow-xl shadow-black/[0.03] border border-gray-100 z-10">
      <div class="card-header text-center mb-10">
        <div class="logo-box w-14 h-14 bg-[#1A1A1A] rounded-2xl flex items-center justify-center mx-auto mb-6 shadow-lg shadow-black/10">
          <span class="font-serif italic text-2xl text-[#A89060]">S</span>
        </div>
        <div class="flex items-center justify-center gap-2 mb-2">
          <div class="h-[1px] w-4 bg-[#A89060]"></div>
          <span class="text-[10px] font-black uppercase tracking-[0.3em] text-[#A89060]">Portal Entry</span>
          <div class="h-[1px] w-4 bg-[#A89060]"></div>
        </div>
        <h1 class="text-3xl font-serif font-bold text-[#1A1A1A] m-0 mb-2">Welcome Back</h1>
        <p class="text-[11px] font-bold text-gray-400 uppercase tracking-widest leading-relaxed">Login to your student account</p>
      </div>
      
      <form @submit.prevent="handleLogin" class="login-form flex flex-col gap-6">
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
                'w-full bg-[#F8F9FA] border border-transparent rounded-xl py-4 pl-11 pr-4 text-[#1A1A1A] text-sm transition-all focus:outline-none focus:border-[#A89060] focus:bg-white',
                emailError ? 'border-red-500' : ''
              ]"
              required
            >
          </div>
          <span v-if="emailError" class="error-text text-red-500 text-[10px] font-bold uppercase mt-1">{{ emailError }}</span>
        </div>
        
        <div class="input-group flex flex-col gap-2">
          <label for="password" class="text-[10px] font-black text-gray-400 uppercase tracking-widest">Password</label>
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
        
        <div class="form-footer flex justify-between items-center text-[11px] font-bold uppercase tracking-tighter">
          <label class="remember-me flex items-center gap-2 text-gray-400 cursor-pointer hover:text-[#1A1A1A] transition-colors">
            <input type="checkbox" class="w-3.5 h-3.5 accent-[#1A1A1A] rounded border-gray-200"> Remember me
          </label>
          <a href="#" class="forgot-link text-[#A89060] hover:text-[#1A1A1A] transition-colors">Forgot password?</a>
        </div>
        
        <button 
          type="submit" 
          class="login-btn bg-[#1A1A1A] text-white border-none rounded-xl py-4 text-[11px] font-black uppercase tracking-[0.2em] cursor-pointer transition-all hover:bg-[#A89060] hover:-translate-y-px disabled:opacity-50 disabled:cursor-not-allowed shadow-lg shadow-black/5 mt-2" 
          :disabled="isLoading || !!emailError"
        >
          {{ isLoading ? 'Checking credentials...' : 'Sign In' }}
        </button>
      </form>
      
      <div class="card-footer text-center mt-10 text-[11px] font-bold text-gray-400 uppercase tracking-widest">
        <p>Don't have an account? <router-link to="/register" class="text-[#A89060] hover:text-[#1A1A1A] transition-colors">Create Account</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import { Mail, Lock } from 'lucide-vue-next';
import api from '../services/api';

const router = useRouter();
const isLoading = ref(false);
const form = ref({
  email: '',
  password: ''
});

const emailError = computed(() => {
  if (!form.value.email) return '';
  const regex = /^[a-zA-Z0-9._%+-]+@my\.sliit\.lk$/i;
  if (!regex.test(form.value.email)) {
    return 'University email required (@my.sliit.lk)';
  }
  return '';
});

const handleLogin = async () => {
    isLoading.value = true;
    try {
        const response = await api.post('/login', form.value);
        
        if (response.status === 200 && response.data.token) {
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('role', response.data.role);
            localStorage.setItem('username', response.data.username);
            localStorage.setItem('studentId', response.data.id.toString());
            localStorage.setItem('userEmail', form.value.email);
            localStorage.setItem('userName', response.data.username);
            
            if (response.data.role === 'ADMIN') {
                router.push('/admin-panel');
            } else {
                router.push('/student-dashboard');
            }
        } else {
            alert('Invalid Email or Password');
        }
    } catch (error) {
        console.error('Login error:', error);
        alert('Invalid Email or Password');
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
  letter-spacing: normal;
  text-transform: none;
}
</style>