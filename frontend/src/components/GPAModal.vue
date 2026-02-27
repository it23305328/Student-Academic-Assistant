<template>
  <div v-if="isOpen" class="modal-overlay">
    <!-- Backdrop Close -->
    <div class="backdrop" @click="closeModal"></div>

    <!-- Modal Card -->
    <div class="modal-card">
      <div class="modal-glow"></div>
      
      <header class="modal-header">
        <h2 class="modal-title">GPA Estimator</h2>
        <button @click="closeModal" class="close-btn">
          <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </header>

      <div class="modal-body">
        <!-- Input Table/List -->
        <div class="subject-list">
          <div v-for="(sub, index) in subjects" :key="index" class="subject-row">
            <div class="input-field grow">
              <label>Subject Name</label>
              <input type="text" v-model="sub.name" placeholder="e.g. Algorithms">
            </div>
            <div class="input-field small">
              <label>Credits</label>
              <input type="number" v-model.number="sub.credits" min="1" max="10">
            </div>
            <div class="input-field medium">
              <label>Grade</label>
              <select v-model="sub.grade">
                <option v-for="g in GRADE_POINTS" :key="g.label" :value="g.label">{{ g.label }}</option>
              </select>
            </div>
            <button @click="removeSubject(index)" class="remove-btn" v-if="subjects.length > 1">
               <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                 <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"></path>
               </svg>
            </button>
          </div>
        </div>

        <div class="action-buttons">
          <button @click="addSubject" class="btn-secondary">+ Add Subject</button>
          <button @click="handleCalculate" class="btn-primary">Calculate GPA</button>
        </div>

        <!-- Result Display -->
        <div v-if="calculatedGPA !== null" class="result-section">
           <div class="result-box">
             <span class="result-label">Estimated GPA</span>
             <h3 class="result-value">{{ calculatedGPA.toFixed(2) }}</h3>
             <div class="result-glow"></div>
           </div>
           <p class="motivation-text">{{ getMotivationalText(calculatedGPA) }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  isOpen: Boolean
});

const emit = defineEmits(['close']);

// Grade to Points Mapping Logic
const GRADE_POINTS = [
  { label: 'A', points: 4.0 },
  { label: 'A-', points: 3.7 },
  { label: 'B+', points: 3.3 },
  { label: 'B', points: 3.0 },
  { label: 'B-', points: 2.7 },
  { label: 'C+', points: 2.3 },
  { label: 'C', points: 2.0 },
  { label: 'C-', points: 1.7 },
  { label: 'D+', points: 1.3 },
  { label: 'D', points: 1.0 },
  { label: 'F', points: 0.0 }
];

const subjects = ref([
  { name: '', credits: 3, grade: 'A' }
]);

const calculatedGPA = ref(null);

const addSubject = () => {
  subjects.value.push({ name: '', credits: 3, grade: 'A' });
};

const removeSubject = (index) => {
  subjects.value.splice(index, 1);
};

const handleCalculate = () => {
  let totalGradePoints = 0;
  let totalCredits = 0;

  subjects.value.forEach(sub => {
    const gradePoint = GRADE_POINTS.find(g => g.label === sub.grade)?.points || 0;
    totalGradePoints += (gradePoint * sub.credits);
    totalCredits += sub.credits;
  });

  calculatedGPA.value = totalCredits > 0 ? (totalGradePoints / totalCredits) : 0;
};

const closeModal = () => {
  emit('close');
  calculatedGPA.value = null;
  subjects.value = [{ name: '', credits: 3, grade: 'A' }];
};

const getMotivationalText = (gpa) => {
  if (gpa >= 3.7) return "Dean's List material! Outstanding performance. 🌟";
  if (gpa >= 3.0) return "Solid stats! You're on the right track. 🚀";
  if (gpa >= 2.0) return "Passing well, but there's room to climb! 📚";
  return "Keep pushing. Every credit counts! 💪";
};
</script>

<style scoped>
/* Pure CSS Layout */
.modal-overlay {
  position: fixed;
  inset: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
}

.backdrop {
  position: absolute;
  inset: 0;
  background: rgba(5, 5, 17, 0.9);
  backdrop-filter: blur(10px);
}

.modal-card {
  position: relative;
  width: 100%;
  max-width: 500px;
  background: #0b0b1a; /* Dark background as requested */
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 24px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  overflow: hidden;
  z-index: 10;
  padding: 32px;
}

.modal-glow {
  position: absolute;
  top: -20%;
  right: -20%;
  width: 60%;
  height: 60%;
  background: radial-gradient(circle, rgba(217, 70, 239, 0.1), transparent);
  filter: blur(60px);
  pointer-events: none;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.modal-title {
  font-size: 20px;
  font-weight: 900;
  color: white;
  margin: 0;
  letter-spacing: -0.02em;
}

.close-btn {
  background: rgba(255, 255, 255, 0.05);
  border: none;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #64748b;
  cursor: pointer;
  transition: 0.2s;
}

.close-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  color: white;
}

/* Strict Icon Sizing Task 3 */
.icon {
  width: 20px !important;
  height: 20px !important;
  min-width: 20px !important;
  min-height: 20px !important;
}

.modal-body {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.subject-list {
  max-height: 300px;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 16px;
  padding-right: 8px;
}

.subject-list::-webkit-scrollbar {
  width: 4px;
}

.subject-list::-webkit-scrollbar-thumb {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 4px;
}

.subject-row {
  display: flex;
  gap: 12px;
  align-items: flex-end;
}

.input-field {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.input-field.grow { flex: 1; }
.input-field.small { width: 70px; }
.input-field.medium { width: 90px; }

.input-field label {
  font-size: 10px;
  font-weight: 800;
  color: #475569;
  text-transform: uppercase;
  letter-spacing: 0.05em;
}

.input-field input, .input-field select {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 12px;
  padding: 10px 12px;
  color: white;
  font-size: 13px;
  outline: none;
  transition: 0.2s;
}

.input-field input:focus, .input-field select:focus {
  border-color: #6366f1;
  background: rgba(255, 255, 255, 0.05);
}

.remove-btn {
  background: transparent;
  border: none;
  color: #f87171;
  cursor: pointer;
  padding: 8px;
  opacity: 0.5;
  transition: 0.2s;
}

.remove-btn:hover {
  opacity: 1;
}

.action-buttons {
  display: flex;
  gap: 12px;
}

.btn-primary {
  flex: 1;
  background: linear-gradient(135deg, #6366f1, #d946ef);
  color: white;
  border: none;
  border-radius: 12px;
  padding: 12px;
  font-weight: 800;
  font-size: 14px;
  cursor: pointer;
  transition: 0.2s;
  box-shadow: 0 10px 20px -5px rgba(99, 102, 241, 0.4);
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 15px 25px -5px rgba(99, 102, 241, 0.5);
}

.btn-secondary {
  flex: 1;
  background: rgba(255, 255, 255, 0.03);
  color: #94a3b8;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 12px;
  padding: 12px;
  font-weight: 700;
  font-size: 14px;
  cursor: pointer;
  transition: 0.2s;
}

.btn-secondary:hover {
  background: rgba(255, 255, 255, 0.05);
  color: white;
}

/* Large Glowing Result Display Task integration */
.result-section {
  padding: 24px;
  background: rgba(99, 102, 241, 0.05);
  border: 1px solid rgba(99, 102, 241, 0.1);
  border-radius: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 12px;
}

.result-box {
  position: relative;
  text-align: center;
}

.result-label {
  font-size: 11px;
  font-weight: 800;
  color: #818cf8;
  text-transform: uppercase;
  display: block;
  margin-bottom: 8px;
}

.result-value {
  font-size: 64px;
  font-weight: 900;
  color: white;
  margin: 0;
  letter-spacing: -2px;
  text-shadow: 0 0 20px rgba(99, 102, 241, 0.5);
}

.result-glow {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100px;
  height: 100px;
  background: #6366f1;
  filter: blur(50px);
  opacity: 0.2;
  z-index: -1;
}

.motivation-text {
  margin-top: 16px;
  font-size: 12px;
  color: #94a3b8;
  font-weight: 600;
  text-align: center;
}
</style>
