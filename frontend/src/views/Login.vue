<template>
  <div class="login-wrapper">
    <div class="glow-container">
      <div class="glow-ball glow-1"></div>
      <div class="glow-ball glow-2"></div>
    </div>
    
    <div class="login-card">
      <div class="card-header">
        <div class="logo-circle">S</div>
        <h1>Welcome Back</h1>
        <p>Login to your student account</p>
      </div>
      
      <form @submit.prevent="handleLogin" class="login-form">
        <div class="input-group">
          <label for="email">Email Address</label>
          <input type="email" id="email" v-model="form.email" placeholder="example@student.com" required>
        </div>
        
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="form.password" placeholder="••••••••" required>
        </div>
        
        <div class="form-footer">
          <label class="remember-me">
            <input type="checkbox"> Remember me
          </label>
          <a href="#" class="forgot-link">Forgot password?</a>
        </div>
        
        <button type="submit" class="login-btn" :disabled="isLoading">
          {{ isLoading ? 'Checking...' : 'Sign In' }}
        </button>
      </form>
      
      <div class="card-footer">
        <p>Don't have an account? <router-link to="/register">Create Account</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import api from '../services/api';

const router = useRouter();
const isLoading = ref(false);
const form = ref({
  email: '',
  password: ''
});

const handleLogin = async () => {
    isLoading.value = true;
    try {
        const response = await api.post('/login', form.value);
        
        // Success: Only if the backend returns 200 OK
        if (response.status === 200 && response.data.id) {
            localStorage.setItem('studentId', response.data.id.toString());
            router.push('/dashboard');
        } else {
            alert('Invalid Email or Password');
        }
    } catch (error) {
        console.error('Login error:', error);
        // Unauthorized (401) will throw catch block in axios
        alert('Invalid Email or Password');
    } finally {
        isLoading.value = false;
    }
};
</script>

<style>
/* Pure CSS - Consistent Theme */
.login-wrapper {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #050511;
  font-family: 'Inter', -apple-system, sans-serif;
  position: relative;
  overflow: hidden;
  color: #e2e8f0;
}

.glow-container {
  position: absolute;
  inset: 0;
  pointer-events: none;
}

.glow-ball {
  position: absolute;
  border-radius: 50%;
  filter: blur(120px);
  opacity: 0.1;
}

.glow-1 {
  width: 40vw;
  height: 40vw;
  top: -10%;
  left: -10%;
  background-color: #4f46e5;
}

.glow-2 {
  width: 30vw;
  height: 30vw;
  bottom: -10%;
  right: -5%;
  background-color: #c026d3;
}

.login-card {
  width: 100%;
  max-width: 400px;
  background: rgba(255, 255, 255, 0.03);
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 24px;
  padding: 40px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  z-index: 10;
}

.card-header {
  text-align: center;
  margin-bottom: 32px;
}

.logo-circle {
  width: 48px;
  height: 48px;
  background: linear-gradient(135deg, #6366f1, #d946ef);
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 16px;
  font-weight: 900;
  font-size: 20px;
  color: white;
  box-shadow: 0 10px 20px -5px rgba(99, 102, 241, 0.4);
}

.card-header h1 {
  font-size: 24px;
  font-weight: 800;
  color: white;
  margin: 0 0 8px;
}

.card-header p {
  font-size: 14px;
  color: #64748b;
}

.login-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.input-group {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.input-group label {
  font-size: 12px;
  font-weight: 600;
  color: #94a3b8;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.input-group input {
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 12px;
  padding: 12px 16px;
  color: white;
  font-size: 14px;
  transition: all 0.2s;
}

.input-group input:focus {
  outline: none;
  border-color: #6366f1;
  background: rgba(255, 255, 255, 0.05);
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
}

.login-btn {
  background: #6366f1;
  color: white;
  border: none;
  border-radius: 12px;
  padding: 12px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
}

.login-btn:hover:not(:disabled) {
  background: #4f46e5;
  transform: translateY(-1px);
}

.login-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 13px;
}

.remember-me {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #94a3b8;
  cursor: pointer;
}

.forgot-link {
  color: #6366f1;
  text-decoration: none;
  font-weight: 600;
}

.card-footer {
  text-align: center;
  margin-top: 32px;
  font-size: 14px;
  color: #64748b;
}

.card-footer a {
  color: #6366f1;
  text-decoration: none;
  font-weight: 600;
}

.card-footer a:hover {
  text-decoration: underline;
}
</style>
