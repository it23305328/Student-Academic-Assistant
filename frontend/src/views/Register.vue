<template>
  <div class="register-page bg-fixed-image">
    <!-- Cinematic Background Overlay -->
    <div class="background-overlay"></div>

    <!-- Main Content Canvas -->
    <main class="register-main">
      <!-- Brand Identity -->
      <div class="brand-identity">
        <img src="../assets/studentx_logo.png" alt="StudentX Logo" class="brand-logo" />
        <p class="brand-subtitle">Academic Support Portal</p>
      </div>

      <!-- Registration Card -->
      <div class="glass-card">
        <div class="card-header-text">
          <h2 class="card-title">Create Account</h2>
          <p class="card-subtitle">Join StudentX dashboard system</p>
        </div>

        <form @submit.prevent="handleRegister" class="form-grid">
          <!-- Full Name -->
          <div class="form-input-container">
            <label class="form-label" for="name">Full Name</label>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">person</span>
              </div>
              <input 
                class="form-input" 
                id="name" 
                v-model="form.name" 
                placeholder="Alex Sterling" 
                type="text"
                required
              />
            </div>
          </div>

          <!-- Email Address -->
          <div class="form-input-container">
            <label class="form-label" for="email">Email Address</label>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">alternate_email</span>
              </div>
              <input 
                class="form-input" 
                :class="{ 'border-error': emailError }"
                id="email" 
                v-model="form.email" 
                placeholder="it21000000@my.sliit.lk" 
                type="email"
                required
              />
            </div>
            <p v-if="emailError" class="error-msg">
              <span class="material-symbols-outlined">error</span>
              {{ emailError }}
            </p>
          </div>

          <!-- Password -->
          <div class="form-input-container">
            <label class="form-label" for="password">Password</label>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">lock</span>
              </div>
              <input 
                class="form-input pr-12" 
                id="password" 
                v-model="form.password" 
                placeholder="••••••••" 
                :type="showPassword ? 'text' : 'password'"
                required
              />
              <div class="password-toggle">
                <button class="toggle-btn" type="button" @click="showPassword = !showPassword">
                  <span class="material-symbols-outlined">{{ showPassword ? 'visibility_off' : 'visibility' }}</span>
                </button>
              </div>
            </div>
          </div>

          <!-- Academic Selection Grid -->
          <div class="academic-grid">
            <!-- Academic Year -->
            <div class="form-input-container">
              <label class="form-label" for="academicYear">Academic Year</label>
              <select class="form-select" id="academicYear" v-model="form.academicYear" required>
                <option value="" disabled>Select Year</option>
                <option value="Year 1">Year 1</option>
                <option value="Year 2">Year 2</option>
                <option value="Year 3">Year 3</option>
                <option value="Year 4">Year 4</option>
              </select>
            </div>

            <!-- Semester -->
            <div class="form-input-container">
              <label class="form-label" for="semester">Semester</label>
              <select class="form-select" id="semester" v-model="form.semester" required>
                <option value="" disabled>Select Semester</option>
                <option value="Semester 1">Semester 1</option>
                <option value="Semester 2">Semester 2</option>
              </select>
            </div>
          </div>

          <!-- Faculty/Department -->
          <div class="form-input-container">
            <label class="form-label" for="faculty">Faculty/Department</label>
            <select class="form-select" id="faculty" v-model="form.faculty" @change="handleFacultyChange" required>
              <option value="" disabled>Select Faculty</option>
              <option v-for="faculty in faculties" :key="faculty" :value="faculty">
                  {{ faculty }}
              </option>
            </select>
          </div>

          <!-- Course/Specialization -->
          <div class="form-input-container">
            <label class="form-label" for="course">Course/Specialization</label>
            <select class="form-select" id="course" v-model="form.course" :disabled="!form.faculty" required>
              <option value="" disabled>Select Course</option>
              <option v-for="course in availableCourses" :key="course" :value="course">
                  {{ course }}
              </option>
            </select>
          </div>

          <!-- Phone Number -->
          <div class="form-input-container">
            <label class="form-label" for="phone">Phone Number</label>
            <div class="form-input-wrapper">
              <div class="input-icon">
                <span class="material-symbols-outlined">call</span>
              </div>
              <input 
                class="form-input pr-12" 
                id="phone" 
                v-model="form.phone" 
                placeholder="+94770000000" 
                type="tel"
                :disabled="otpSent || isVerifying"
                required
              />
              <div class="password-toggle otp-action">
                <button v-if="!otpSent && !isVerified" class="otp-btn" type="button" @click="sendOtp" :disabled="isVerifying || !form.phone">
                  <span v-if="isVerifying" class="material-symbols-outlined spinning action-icon">hourglass_empty</span>
                  <span v-else>{{ 'Send OTP' }}</span>
                </button>
                <span v-else-if="isVerified" class="material-symbols-outlined verified-icon">check_circle</span>
              </div>
            </div>
            <div id="recaptcha-container" style="display: none;"></div>
          </div>

          <!-- OTP Input -->
          <div v-if="otpSent && !isVerified" class="form-input-container">
            <div class="otp-header-row">
              <label class="form-label">Enter OTP</label>
              <button class="text-btn" type="button" @click="changeNumber">Change Number</button>
            </div>
            <div class="otp-boxes-wrapper">
              <input 
                v-for="(digit, index) in otpArray" 
                :key="index"
                ref="otpInputRefs"
                class="otp-box form-input" 
                type="text"
                maxlength="1"
                v-model="otpArray[index]"
                @input="handleOtpInput(index, $event)"
                @keydown.delete="handleOtpDelete(index, $event)"
                :disabled="isVerifying"
              />
            </div>
            <button class="submit-btn otp-verify-btn" type="button" @click="verifyOtp" :disabled="isVerifying || otpCode.length !== 6">
              <span v-if="isVerifying" class="material-symbols-outlined spinning action-icon">hourglass_empty</span>
              <span>{{ isVerifying ? 'Verifying...' : 'Verify OTP' }}</span>
            </button>
            <p v-if="verificationError" class="error-msg">
              <span class="material-symbols-outlined">error</span>
              {{ verificationError }}
            </p>
          </div>

          <!-- Create Account Button -->
          <div class="submit-section">
            <button 
              class="submit-btn" 
              type="submit" 
              :disabled="isLoading || !isFormValid"
            >
              <span>{{ isLoading ? 'Creating account...' : 'Create Account' }}</span>
              <span v-if="!isLoading" class="material-symbols-outlined">arrow_forward</span>
            </button>
          </div>
        </form>
      </div>

      <!-- Footer Help -->
      <div class="footer-help">
        <p>
          Already have an account? 
          <router-link class="login-link" to="/login">Sign In</router-link>
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
import { ref, computed, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { RecaptchaVerifier, signInWithPhoneNumber } from "firebase/auth";
import { auth } from '../firebase'; // Import from local module
import api from '../services/api';

const router = useRouter();
const isLoading = ref(false);
const showPassword = ref(false);

const form = ref({
  name: '',
  email: '',
  password: '',
  academicYear: '',
  semester: '',
  faculty: '',
  course: '',
  phone: ''
});

const otpArray = ref(['', '', '', '', '', '']);
const otpCode = computed(() => otpArray.value.join(''));
const otpInputRefs = ref([]);
const otpSent = ref(false);

const handleOtpInput = (index, event) => {
  const value = event.target.value;
  if (!/^\d*$/.test(value)) {
    otpArray.value[index] = '';
    return;
  }
  if (value && index < 5 && otpInputRefs.value[index + 1]) {
    otpInputRefs.value[index + 1].focus();
  }
};

const handleOtpDelete = (index, event) => {
  if (!otpArray.value[index] && index > 0 && otpInputRefs.value[index - 1]) {
    otpInputRefs.value[index - 1].focus();
  }
};

const changeNumber = () => {
    otpSent.value = false;
    otpArray.value = ['', '', '', '', '', ''];
    verificationError.value = '';
    isVerified.value = false;
};
const isVerified = ref(false);
const isVerifying = ref(false);
const verificationError = ref('');
let confirmationResult = null;

onMounted(() => {
  if (!window.recaptchaVerifier) {
    window.recaptchaVerifier = new RecaptchaVerifier(auth, 'recaptcha-container', {
      'size': 'invisible',
      'callback': (response) => {
        console.log("reCAPTCHA solved");
      },
      'expired-callback': () => {
        verificationError.value = "reCAPTCHA expired. Please try again.";
      }
    });
  }
});

const sendOtp = async () => {
  verificationError.value = '';
  isVerifying.value = true;
  try {
    const appVerifier = window.recaptchaVerifier;
    confirmationResult = await signInWithPhoneNumber(auth, form.value.phone, appVerifier);
    otpSent.value = true;
  } catch (error) {
    console.error("Error sending OTP:", error);
    verificationError.value = "Failed to send OTP. Check your number (+123...).";
    if (window.recaptchaVerifier) {
       window.recaptchaVerifier.render().then(function(widgetId) {
         grecaptcha.reset(widgetId);
       });
    }
  } finally {
    isVerifying.value = false;
  }
};

const verifyOtp = async () => {
  verificationError.value = '';
  isVerifying.value = true;
  try {
    const result = await confirmationResult.confirm(otpCode.value);
    console.log("Firebase Auth User:", result.user);
    isVerified.value = true;
    verificationError.value = '';
  } catch (error) {
    console.error("Error verifying OTP:", error);
    verificationError.value = "Invalid OTP. Please try again.";
  } finally {
    isVerifying.value = false;
  }
};

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
    return 'Only university email addresses (@my.sliit.lk) are allowed';
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
         form.value.course &&
         form.value.phone &&
         isVerified.value; // Require verification before final submit
});

const handleRegister = async () => {
    if (!isFormValid.value) return;
    isLoading.value = true;
    try {
        // Now appending Firebase UID internally through ID token in auth header or as param if Spring expects it.
        // If your Spring boot expecting token, send it, otherwise standard payload.
        // const idToken = await auth.currentUser.getIdToken();
        const response = await api.post('api/register', form.value);
        
        if (response.status === 201 || response.status === 200) {
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

.register-page {
  font-family: 'Manrope', sans-serif;
  color: #2e342d;
  overflow-x: hidden;
  min-height: 100vh;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fafaf5;
  padding: 48px 0;
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

.register-main {
  position: relative;
  z-index: 10;
  width: 100%;
  max-width: 672px; /* 2xl */
  padding: 0 24px;
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
  color: #5a6159;
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
  border-radius: 0.75rem;
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

.card-title {
  font-size: 24px;
  font-weight: 700;
  color: #2e342d;
  margin-bottom: 4px;
}

.card-subtitle {
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
  color: #767c74;
  font-size: 20px;
}

.form-input, .form-select {
  width: 100%;
  height: 56px;
  padding-left: 48px;
  padding-right: 24px;
  border-radius: 9999px;
  background-color: rgba(243, 244, 238, 0.5);
  border: none;
  transition: all 0.3s ease;
  color: #2e342d;
  font-size: 16px;
  outline: none;
}

.form-select {
  padding-left: 24px;
  appearance: none;
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' fill='none' viewBox='0 0 20 20'%3e%3cpath stroke='%23767c74' stroke-linecap='round' stroke-linejoin='round' stroke-width='1.5' d='M6 8l4 4 4-4'/%3e%3c/svg%3e");
  background-position: right 1.25rem center;
  background-repeat: no-repeat;
  background-size: 1.25em 1.25em;
  cursor: pointer;
}

.form-input:focus, .form-select:focus {
  background-color: white;
  box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.1);
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
}

.toggle-btn:hover {
  color: #4E6073;
}

.otp-action {
  right: 12px;
}

.otp-btn {
  background: none;
  border: none;
  color: #4E6073;
  font-weight: 700;
  font-size: 13px;
  cursor: pointer;
  transition: opacity 0.2s;
}

.otp-btn:hover:not(:disabled) {
  opacity: 0.8;
  text-decoration: underline;
}

.otp-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.verified-icon {
  color: #10b981;
  font-size: 24px !important;
}

.otp-header-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
  margin-left: 16px;
  margin-bottom: 4px;
}

.otp-header-row .form-label {
    margin-left: 0;
    margin-bottom: 0;
}

.text-btn {
  background: none;
  border: none;
  color: #4E6073;
  font-size: 12px;
  font-weight: 700;
  text-decoration: underline;
  cursor: pointer;
  padding: 0;
}

.text-btn:hover {
  color: #3b4958;
}

.otp-boxes-wrapper {
  display: flex;
  gap: 8px;
  justify-content: space-between;
  margin-bottom: 12px;
}

.otp-box {
  width: 100%;
  height: 56px;
  text-align: center;
  padding: 0 !important;
  font-size: 24px;
  font-weight: 700;
  border-radius: 12px;
  color: #4E6073;
}

.otp-verify-btn {
  height: 48px;
  font-size: 16px;
  border-radius: 12px;
  background-color: #10b981;
  box-shadow: 0 10px 15px -3px rgba(16, 185, 129, 0.25);
}

.otp-verify-btn:hover:not(:disabled) {
  background-color: #0d9e6e;
  box-shadow: 0 10px 15px -3px rgba(16, 185, 129, 0.35);
}

.spinning {
  animation: spin 1s linear infinite;
  display: inline-block;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.action-icon {
  font-size: 18px !important;
  vertical-align: middle;
  margin-right: 4px;
}

.academic-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 16px;
}

@media (min-width: 640px) {
  .academic-grid {
    grid-template-columns: 1fr 1fr;
  }
}

.submit-section {
  padding-top: 16px;
}

.submit-btn {
  width: 100%;
  height: 56px;
  border-radius: 9999px;
  background-color: #4E6073;
  color: white;
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

.submit-btn:hover:not(:disabled) {
  transform: scale(1.01);
  box-shadow: 0 20px 25px -5px rgba(78, 96, 115, 0.35);
}

.submit-btn:active:not(:disabled) {
  transform: scale(0.98);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
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

.login-link {
  color: #4E6073;
  font-weight: 700;
  text-decoration: none;
  margin-left: 4px;
}

.login-link:hover {
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
}

.status-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: #10b981;
  animation: pulse 2s infinite;
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

.error-msg {
  color: #9f403d;
  font-size: 11px;
  font-weight: 600;
  margin-top: 4px;
  margin-left: 16px;
  display: flex;
  align-items: center;
  gap: 4px;
}

.error-msg .material-symbols-outlined {
  font-size: 14px;
}

.border-error {
  border: 2px solid #9f403d !important;
}

.lg-only {
  display: none;
}

@media (min-width: 1024px) {
  .lg-only {
    display: block;
  }
}
</style>

