<template>
  <div class="login-page bg-fixed-image">
    <!-- Cinematic Background Overlay -->
    <div class="background-overlay"></div>
    
    <!-- Main Content Canvas -->
    <main class="login-main">
      <!-- Brand Identity -->
      <div class="brand-identity">
        <img src="../assets/studentx_logo.png" alt="StudentX Logo" class="brand-logo" />
        <p class="brand-subtitle">Academic Support Portal</p>
      </div>

      <!-- Login Card -->
      <div class="glass-card">
        <div class="card-header-text">
          <h2>Welcome Back</h2>
          <p>Please enter your academic credentials.</p>
        </div>

        <form @submit.prevent="handleLogin" class="form-grid">
          <!-- Email Field -->
          <div class="form-input-container">
            <label class="form-label" for="email">Student Email</label>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">alternate_email</span>
              </div>
              <input 
                class="form-input" 
                :class="{ 'border-error': emailError }"
                id="email" 
                v-model="form.email"
                placeholder="username@studentx.edu" 
                type="email"
                required
              />
            </div>
            <span v-if="emailError" class="error-msg">{{ emailError }}</span>
          </div>

          <!-- Password Field -->
          <div class="form-input-container">
            <div class="label-row">
              <label class="form-label" for="password">Security Key</label>
              <a class="forgot-link" href="#">Forgot?</a>
            </div>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">lock</span>
              </div>
              <input 
                class="form-input pr-12" 
                id="password" 
                v-model="form.password"
                placeholder="••••••••••••" 
                :type="showPassword ? 'text' : 'password'"
                required
              />
              <div class="password-toggle">
                <button 
                  class="toggle-btn" 
                  type="button" 
                  @click="showPassword = !showPassword"
                >
                  <span class="material-symbols-outlined">{{ showPassword ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
            </div>
          </div>

          <!-- Remember Me -->
          <div class="remember-me-row">
            <input 
              class="custom-checkbox" 
              id="remember" 
              v-model="rememberMe"
              type="checkbox"
            />
            <label class="checkbox-label" for="remember">Remember this device</label>
          </div>

          <!-- Sign In Button -->
          <div class="submit-section">
            <button 
              class="submit-btn" 
              type="submit" 
              :disabled="isLoading || !!emailError"
            >
              <span>{{ isLoading ? 'Checking...' : 'Sign In' }}</span>
              <span v-if="!isLoading" class="material-symbols-outlined">arrow_forward</span>
            </button>
          </div>
        </form>

        <!-- Social Login / Secondary Action -->
        <!-- SSO Section Remotion per User Request -->
      </div>

      <!-- Footer Help -->
      <div class="footer-help">
        <p>
          New to StudentX? 
          <router-link class="register-link" to="/register">Create an account</router-link>
        </p>
        <div class="footer-links">
          <a href="#">Privacy</a>
          <a href="#">Terms</a>
          <a href="#">Support</a>
        </div>
      </div>
    </main>

    <!-- Contextual Floating Elements -->
    <div class="floating-header lg-only">
      <div class="floating-header-content">
        <span class="material-symbols-outlined">hub</span>
        <span class="tracking-tighter">INTELLIGENT CAMPUS</span>
      </div>
    </div>
    
    <div class="floating-status lg-only">
      <div class="status-container glass-card">
        <div class="status-dot"></div>
        <span class="status-text">System Status: Optimal</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { useRouter } from 'vue-router';
import api from '../services/api';

const router = useRouter();
const isLoading = ref(false);
const showPassword = ref(false);
const rememberMe = ref(false);

const form = ref({
  email: '',
  password: ''
});

const emailError = computed(() => {
  if (!form.value.email) return '';
  const regex = /^[a-zA-Z0-9._%+-]+@my\.sliit\.lk$/i;
  // Fallback to allow studentx.edu if testing with provided design placeholder
  if (!regex.test(form.value.email) && !form.value.email.endsWith('@studentx.edu')) {
    return 'Only university email addresses (@my.sliit.lk) are allowed';
  }
  return '';
});

const handleLogin = async () => {
    isLoading.value = true;
    try {
        const response = await api.post('api/auth/login', form.value);
        
        // Success: Only if the backend returns 200 OK
        if (response.status === 200 && response.data.token) {
            // Store token, role and username in localStorage
            localStorage.setItem('token', response.data.token);
            localStorage.setItem('role', response.data.role);
            localStorage.setItem('username', response.data.username);
            localStorage.setItem('studentId', response.data.id.toString());
            
            // Redirect based on role
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
:root {
  --on-surface-variant: #5a6159;
  --secondary-container: #dfe3e7;
  --on-primary-container: #415366;
  --tertiary-fixed-dim: #c7ceec;
  --inverse-surface: #0d0f0c;
  --on-secondary-container: #4d5256;
  --secondary: #5b6063;
  --on-secondary: #f5f9fd;
  --on-primary: #f4f8ff;
  --tertiary-fixed: #d5dcfb;
  --surface-bright: #fafaf5;
  --on-primary-fixed: #2f4153;
  --on-tertiary-container: #474d67;
  --surface-dim: #d5dcd0;
  --secondary-fixed: #dfe3e7;
  --tertiary-container: #d5dcfb;
  --surface-container-low: #f3f4ee;
  --on-tertiary: #faf8ff;
  --background: #fafaf5;
  --surface-variant: #dee4da;
  --primary-fixed: #d1e4fb;
  --on-error: #fff7f6;
  --inverse-on-surface: #9c9d99;
  --inverse-primary: #ccdff6;
  --surface-container-high: #e5eae0;
  --primary-fixed-dim: #c3d6ed;
  --outline: #767c74;
  --outline-variant: #adb4aa;
  --surface-container: #ecefe7;
  --surface-container-highest: #dee4da;
  --tertiary: #575e78;
  --on-primary-fixed-variant: #4b5d70;
  --on-secondary-fixed-variant: #575c60;
  --surface-tint: #4E6073;
  --surface: #fafaf5;
  --on-surface: #2e342d;
  --on-secondary-fixed: #3b4043;
  --error-container: #fe8983;
  --secondary-fixed-dim: #d1d5d9;
  --surface-container-lowest: #ffffff;
  --error: #9f403d;
  --on-tertiary-fixed-variant: #505771;
  --primary: #4E6073;
  --on-error-container: #752121;
  --on-tertiary-fixed: #343b53;
  --on-background: #2e342d;
  --primary-container: #d1e4fb;
}

* {
  box-sizing: border-box;
}

.login-page {
  font-family: 'Manrope', sans-serif;
  color: #2e342d; /* on-surface */
  overflow-x: hidden;
  min-height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fafaf5; /* surface */
}

.bg-fixed-image {
  background-image: url(https://lh3.googleusercontent.com/aida-public/AB6AXuDG7XiiiamfM6vz1AskaPnnCmX7NypngGcscgYI372CgjzkiKvwhvvP3IsWy-sXeGBMtcDQqOtr8l1I-NKE0CNJOd3hJBp55oMUiuPyie6oUWsL3VFToPXWUcb6c_rBAcxvdZkNuk4lfabrEHl6D2lKg0AbfAZHNkkkgT38GcBnajPzlj22qNIiQUK0oRz7TiQiW9CUULo3aBpFKxOPVFVmsT8dnsiq2-MPRea08MZ1mfX8JbhjC2dM-0NqFracytg17YtK1CLxqo0);
  background-size: cover;
  background-position: center;
  background-attachment: fixed;
}

.background-overlay {
  position: fixed;
  inset: 0;
  z-index: 0;
  backdrop-filter: blur(12px);
  -webkit-backdrop-filter: blur(12px);
  background: rgba(255, 255, 255, 0.3);
}

.login-main {
  position: relative;
  z-index: 10;
  width: 100%;
  max-width: 512px; /* max-w-lg */
  padding: 48px 24px;
}

.brand-identity {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 40px;
  text-align: center;
}

.brand-logo {
  height: 120px;
  width: auto;
  object-fit: contain;
  margin-bottom: 24px;
}

.brand-subtitle {
  color: #5a6159; /* on-surface-variant */
  font-weight: 500;
  letter-spacing: 0.05em;
  text-transform: uppercase;
  font-size: 12px;
  opacity: 0.7;
}

.glass-card {
  background: rgba(250, 250, 245, 0.75);
  backdrop-filter: blur(32px);
  -webkit-backdrop-filter: blur(32px);
  border-radius: 0.75rem; /* rounded-xl */
  padding: 32px;
  box-shadow: 0 25px 50px -12px rgba(78, 96, 115, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.6);
}

@media (min-width: 768px) {
  .glass-card {
    padding: 48px;
  }
}

.card-header-text {
  margin-bottom: 32px;
  text-align: center;
}

@media (min-width: 768px) {
  .card-header-text {
    text-align: left;
  }
}

.card-header-text h2 {
  font-size: 24px; /* text-2xl */
  font-weight: 700;
  color: #2e342d;
  margin-bottom: 4px;
}

.card-header-text p {
  color: #5a6159;
  font-size: 14px;
}

.form-grid {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.form-input-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.form-label {
  display: block;
  font-size: 12px;
  font-weight: 700;
  color: #5a6159;
  margin-left: 16px;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.label-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 16px;
}

.form-input-wrapper {
  position: relative;
}

.input-icon {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  padding-left: 20px;
  display: flex;
  align-items: center;
  pointer-events: none;
}

.input-icon .material-symbols-outlined {
  color: #767c74; /* outline */
  font-size: 20px;
}

.form-input {
  width: 100%;
  height: 48px;
  padding-left: 48px;
  padding-right: 24px;
  border-radius: 9999px;
  background-color: rgba(243, 244, 238, 0.5); /* surface-container-low/50 */
  border: 1.5px solid transparent;
  transition: all 0.3s ease;
  color: #2e342d;
  font-size: 15px;
}

.form-input:focus {
  outline: none;
  border-color: rgba(78, 96, 115, 0.2);
  background-color: white;
  box-shadow: 0 0 0 4px rgba(78, 96, 115, 0.1);
}

.form-input::placeholder {
  color: rgba(118, 124, 116, 0.6);
}

.pr-12 {
  padding-right: 48px;
}

.password-toggle {
  position: absolute;
  top: 0;
  bottom: 0;
  right: 0;
  padding-right: 16px;
  display: flex;
  align-items: center;
}

.toggle-btn {
  background: none;
  border: none;
  color: #767c74;
  cursor: pointer;
  transition: color 0.2s;
  display: flex;
  align-items: center;
}

.toggle-btn:hover {
  color: #4E6073;
}

.remember-me-row {
  display: flex;
  align-items: center;
  padding: 0 16px;
}

.custom-checkbox {
  width: 20px;
  height: 20px;
  border-radius: 6px;
  border: 1px solid #adb4aa; /* outline-variant */
  cursor: pointer;
}

.checkbox-label {
  margin-left: 12px;
  font-size: 14px;
  font-weight: 500;
  color: #5a6159;
  cursor: pointer;
  user-select: none;
}

.submit-section {
  padding-top: 16px;
}

.submit-btn {
  width: 100%;
  height: 56px;
  border-radius: 9999px;
  background-color: #4E6073; /* primary */
  color: #f4f8ff; /* on-primary */
  font-weight: 700;
  font-size: 18px;
  border: none;
  box-shadow: 0 20px 25px -5px rgba(78, 96, 115, 0.25);
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.submit-btn:hover {
  box-shadow: 0 20px 25px -5px rgba(78, 96, 115, 0.35);
  transform: scale(1.01);
}

.submit-btn:active {
  transform: scale(0.98);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
  transform: none;
}

.sso-section {
  margin-top: 40px;
}

.sso-divider {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 32px;
}

.sso-divider::before {
  content: "";
  position: absolute;
  width: 100%;
  border-top: 1px solid rgba(173, 180, 170, 0.3);
}

.sso-divider-text {
  position: relative;
  background-color: transparent;
  padding: 0 16px;
  font-size: 12px;
  font-weight: 700;
  color: #767c74;
  text-transform: uppercase;
  letter-spacing: 0.1em;
}

.sso-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.sso-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  height: 48px;
  border-radius: 9999px;
  background-color: white;
  border: 1px solid rgba(173, 180, 170, 0.3);
  cursor: pointer;
  transition: background-color 0.3s;
}

.sso-btn:hover {
  background-color: #f3f4ee;
}

.sso-btn span {
  font-size: 14px;
  font-weight: 700;
  color: #2e342d;
}

.google-icon {
  width: 20px;
  height: 20px;
}

.footer-help {
  margin-top: 48px;
  text-align: center;
}

.footer-help p {
  color: #5a6159;
  font-size: 14px;
  font-weight: 500;
}

.register-link {
  color: #4E6073;
  font-weight: 800;
  text-decoration: none;
  margin-left: 4px;
  transition: text-decoration 0.2s;
}

.register-link:hover {
  text-decoration: underline;
  text-underline-offset: 4px;
}

.footer-links {
  margin-top: 32px;
  display: flex;
  justify-content: center;
  gap: 24px;
  opacity: 0.4;
}

.footer-links a {
  text-decoration: none;
  font-size: 10px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 0.1em;
  color: inherit;
  transition: color 0.2s;
}

.footer-links a:hover {
  color: #4E6073;
}

.floating-header {
  position: fixed;
  top: 32px;
  left: 32px;
  z-index: 20;
}

.floating-header-content {
  display: flex;
  align-items: center;
  gap: 16px;
  color: #4E6073;
  font-weight: 700;
}

.floating-status {
  position: fixed;
  bottom: 32px;
  right: 32px;
  z-index: 20;
}

.status-container {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border-radius: 9999px;
  border: 1px solid rgba(255, 255, 255, 0.4);
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #10b981; /* emerald-500 */
  animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: .5; }
}

.status-text {
  font-size: 10px;
  font-weight: 700;
  color: #5a6159;
  text-transform: uppercase;
  letter-spacing: -0.025em;
}

.lg-only {
  display: none;
}

@media (min-width: 1024px) {
  .lg-only {
    display: block;
  }
}

.error-msg {
  color: #9f403d; /* error */
  font-size: 11px;
  font-weight: 600;
  margin-top: 4px;
  margin-left: 16px;
}

.border-error {
  border-color: #9f403d !important;
}

.forgot-link {
  font-size: 11px;
  font-weight: 700;
  color: #4E6073;
  text-decoration: none;
}

.material-symbols-outlined {
  font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
}
</style>


