<template>
  <div class="register-wrapper">
    <!-- Ambient Background Bubbles -->
    <div class="glow-container">
      <div class="glow-ball glow-1"></div>
      <div class="glow-ball glow-2"></div>
    </div>
    
    <div class="register-card">
      <div class="card-header">
        <div class="logo-circle">S</div>
        <h1>Create Account</h1>
        <p>Join StudentX dashboard system</p>
      </div>
      
      <form @submit.prevent="handleRegister" class="register-form">
        <div class="input-group">
          <label for="name">Full Name</label>
          <input type="text" id="name" v-model="form.name" placeholder="John Doe" required>
        </div>

        <div class="input-group">
          <label for="email">Email Address</label>
          <input 
            type="email" 
            
            id="email" 
            v-model="form.email" 
            placeholder="example@my.sliit.lk" 
            :class="{ 'input-error': emailError }"
            required
          >
          <span v-if="emailError" class="error-text">{{ emailError }}</span>
        </div>
        
        <div class="input-group">
          <label for="password">Password</label>
          <input type="password" id="password" v-model="form.password" placeholder="••••••••" required>
        </div>

        <div class="dropdown-row">
            <div class="input-group">
                <label for="academicYear">Academic Year</label>
                <select id="academicYear" v-model="form.academicYear" required>
                    <option value="" disabled>Select Year</option>
                    <option value="Year 1">Year 1</option>
                    <option value="Year 2">Year 2</option>
                    <option value="Year 3">Year 3</option>
                    <option value="Year 4">Year 4</option>
                </select>
            </div>

            <div class="input-group">
                <label for="semester">Semester</label>
                <select id="semester" v-model="form.semester" required>
                    <option value="" disabled>Select Semester</option>
                    <option value="Semester 1">Semester 1</option>
                    <option value="Semester 2">Semester 2</option>
                </select>
            </div>
        </div>

        <div class="dropdown-row">
            <div class="input-group">
                <label for="faculty">Faculty/Department</label>
                <select id="faculty" v-model="form.faculty" @change="handleFacultyChange" required>
                    <option value="" disabled>Select Faculty</option>
                    <option v-for="faculty in faculties" :key="faculty" :value="faculty">
                        {{ faculty }}
                    </option>
                </select>
            </div>

            <div class="input-group">
                <label for="course">Course/Specialization</label>
                <select id="course" v-model="form.course" :disabled="!form.faculty" required>
                    <option value="" disabled>Select Course</option>
                    <option v-for="course in availableCourses" :key="course" :value="course">
                        {{ course }}
                    </option>
                </select>
            </div>
        </div>
        
        <button type="submit" class="register-btn" :disabled="isLoading || !isFormValid">
          {{ isLoading ? 'Creating account...' : 'Create Account' }}
        </button>
      </form>
      
      <div class="card-footer">
        <p>Already have an account? <router-link to="/login">Sign In</router-link></p>
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
            alert(error.response.data); // Email already exists etc
        } else {
            alert('Registration failed. Please try again.');
        }
    } finally {
        isLoading.value = false;
    }
};
</script>

<style scoped>
/* Pure CSS Register View Styles */
.register-wrapper {
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

.register-card {
  width: 100%;
  max-width: 450px;
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

.register-form {
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

.input-group input, 
.input-group select {
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 12px;
  padding: 12px 16px;
  color: white;
  font-size: 14px;
  transition: all 0.2s;
  width: 100%;
  appearance: none;
}

.input-group select {
  background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='%2394a3b8' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3E%3Cpolyline points='6 9 12 15 18 9'%3E%3C/polyline%3E%3C/svg%3E");
  background-repeat: no-repeat;
  background-position: right 12px center;
  background-size: 16px;
  cursor: pointer;
}

.input-group input:focus,
.input-group select:focus {
  outline: none;
  border-color: #6366f1;
  background: rgba(255, 255, 255, 0.05);
  box-shadow: 0 0 0 4px rgba(99, 102, 241, 0.1);
}

.input-group select:disabled {
  opacity: 0.4;
  cursor: not-allowed;
}

.dropdown-row {
  display: flex;
  gap: 16px;
}

.dropdown-row .input-group {
  flex: 1;
}

.input-group input.input-error {
  border-color: #ef4444;
}

.error-text {
  color: #ef4444;
  font-size: 11px;
  font-weight: 600;
  margin-top: 4px;
}

.register-btn {
  background: #6366f1;
  color: white;
  border: none;
  border-radius: 12px;
  padding: 12px;
  font-size: 14px;
  font-weight: 700;
  cursor: pointer;
  transition: all 0.2s;
  margin-top: 10px;
}

.register-btn:hover:not(:disabled) {
  background: #4f46e5;
  transform: translateY(-1px);
}

.register-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
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
