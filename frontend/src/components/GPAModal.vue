<template>
  <div v-if="isOpen" class="modal-overlay">
    <div class="backdrop" @click="closeModal"></div>

    <div class="modal-card advanced-estimator">
      <div class="modal-glow"></div>
      
      <header class="modal-header">
        <h2 class="modal-title">GPA Estimator Pro</h2>
        <div class="mode-toggle">
          <button 
            :class="['toggle-btn', { active: calcMode === 'simple' }]" 
            @click="calcMode = 'simple'"
          >Simple</button>
          <button 
            :class="['toggle-btn', { active: calcMode === 'advanced' }]" 
            @click="calcMode = 'advanced'"
          >Advanced</button>
        </div>
        <button @click="closeModal" class="close-btn">
          <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </header>

      <div class="modal-body">
        <!-- Weightage Configuration (Only for Advanced Mode) -->
        <div v-if="calcMode === 'advanced'" class="weightage-config">
          <p class="config-label">Configure Weightage (%)</p>
          <div class="config-inputs">
            <div class="weight-item">
              <span>Assignments</span>
              <input type="number" v-model.number="weights.assignment" min="0" max="100" class="weight-input">
            </div>
            <div class="weight-item">
              <span>Mid-term</span>
              <input type="number" v-model.number="weights.midterm" min="0" max="100" class="weight-input">
            </div>
            <div class="weight-item">
              <span>Final Exam</span>
              <input type="number" v-model.number="weights.final" min="0" max="100" class="weight-input">
            </div>
            <div :class="['weight-total', { 'text-error': totalWeight !== 100 }]">
              <span>Total: {{ totalWeight }}%</span>
              <span v-if="totalWeight !== 100" class="weight-warning">! Must be 100%</span>
            </div>
          </div>
        </div>

        <div class="table-container no-scrollbar">
          <table class="estimator-table">
            <thead>
              <tr>
                <th class="col-name">Subject</th>
                <th class="col-credits">Credits</th>
                <!-- Advanced Mode Columns -->
                <template v-if="calcMode === 'advanced'">
                  <th class="col-mark">Asmt</th>
                  <th class="col-mark">Mid</th>
                  <th class="col-mark">Final</th>
                  <th class="col-total">Total</th>
                </template>
                <th class="col-grade">Grade</th>
                <th class="col-action"></th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(sub, index) in subjects" :key="index">
                <td>
                  <input type="text" v-model="sub.name" placeholder="Subject Name" class="table-input">
                </td>
                <td>
                  <input type="number" v-model.number="sub.credits" min="1" max="10" class="table-input text-center">
                </td>
                <!-- Advanced Mode Inputs -->
                <template v-if="calcMode === 'advanced'">
                  <td>
                    <input type="number" v-model.number="sub.assignment" min="0" max="100" class="table-input text-center">
                  </td>
                  <td>
                    <input type="number" v-model.number="sub.midterm" min="0" max="100" class="table-input text-center">
                  </td>
                  <td>
                    <input type="number" v-model.number="sub.final" min="0" max="100" class="table-input text-center">
                  </td>
                  <td class="text-center font-bold text-accent">
                    {{ calculateTotal(sub).toFixed(1) }}
                  </td>
                </template>
                <!-- Grade Column (Select in Simple, Auto in Advanced) -->
                <td class="text-center font-black">
                  <select v-if="calcMode === 'simple'" v-model="sub.simpleGrade" class="table-select">
                    <option v-for="g in GRADE_POINTS" :key="g.label" :value="g.label">{{ g.label }}</option>
                  </select>
                  <span v-else :class="['grade-badge', getGradeInfo(calculateTotal(sub)).label.toLowerCase().replace('-', 'n')]">
                    {{ getGradeInfo(calculateTotal(sub)).label }}
                  </span>
                </td>
                <td>
                  <button @click="removeSubject(index)" class="table-remove-btn" v-if="subjects.length > 1">
                    &times;
                  </button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <div class="table-footer">
          <button @click="addSubject" class="btn-add-row">+ Add Subject</button>
        </div>

        <!-- Real-time Result Section -->
        <div class="live-result-section">
          <div class="result-display">
            <div class="gpa-circle">
              <span class="gpa-label">ESTIMATED GPA</span>
              <h3 class="gpa-value">{{ estimatedGPA }}</h3>
            </div>
            <div class="result-details">
              <p class="motivation-quote">{{ getMotivationalText(estimatedGPA) }}</p>
              <div class="stats-pills">
                <span class="stat-pill">Total Credits: {{ totalCredits }}</span>
                <span class="stat-pill">Subjects: {{ subjects.length }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';

const props = defineProps({
  isOpen: Boolean
});

const emit = defineEmits(['close']);

const calcMode = ref('advanced'); // 'simple' or 'advanced'

const subjects = ref([
  { name: '', credits: 3, assignment: 0, midterm: 0, final: 0, simpleGrade: 'A' }
]);

const weights = ref({
  assignment: 20,
  midterm: 30,
  final: 50
});

const totalWeight = computed(() => {
  return (weights.value.assignment || 0) + (weights.value.midterm || 0) + (weights.value.final || 0);
});

const GRADE_POINTS = [
  { min: 85, label: 'A', points: 4.0 },
  { min: 80, label: 'A-', points: 3.7 },
  { min: 75, label: 'B+', points: 3.3 },
  { min: 70, label: 'B', points: 3.0 },
  { min: 65, label: 'B-', points: 2.7 },
  { min: 60, label: 'C+', points: 2.3 },
  { min: 55, label: 'C', points: 2.0 },
  { min: 50, label: 'C-', points: 1.7 },
  { min: 45, label: 'D+', points: 1.3 },
  { min: 40, label: 'D', points: 1.0 },
  { min: 0, label: 'F', points: 0.0 }
];

const calculateTotal = (sub) => {
  if (totalWeight.value === 0) return 0;
  const score = (sub.assignment * (weights.value.assignment / 100)) + 
                (sub.midterm * (weights.value.midterm / 100)) + 
                (sub.final * (weights.value.final / 100));
  
  return score * (100 / (totalWeight.value || 100));
};

const getGradeInfo = (total) => {
  return GRADE_POINTS.find(g => total >= g.min) || GRADE_POINTS[GRADE_POINTS.length - 1];
};

const totalCredits = computed(() => {
  return subjects.value.reduce((acc, sub) => acc + (sub.credits || 0), 0);
});

const estimatedGPA = computed(() => {
  let totalPoints = 0;
  let credits = 0;

  subjects.value.forEach(sub => {
    let point = 0;
    if (calcMode.value === 'advanced') {
      const total = calculateTotal(sub);
      point = getGradeInfo(total).points;
    } else {
      point = GRADE_POINTS.find(g => g.label === sub.simpleGrade)?.points || 0;
    }
    
    totalPoints += (point * (sub.credits || 0));
    credits += (sub.credits || 0);
  });

  if (credits === 0) return '0.00';
  return (totalPoints / credits).toFixed(2);
});

const addSubject = () => {
  subjects.value.push({ name: '', credits: 3, assignment: 0, midterm: 0, final: 0, simpleGrade: 'A' });
};

const removeSubject = (index) => {
  subjects.value.splice(index, 1);
};

const closeModal = () => {
  emit('close');
  subjects.value = [{ name: '', credits: 3, assignment: 0, midterm: 0, final: 0 }];
};

const getMotivationalText = (gpaValue) => {
    const gpa = parseFloat(gpaValue);
    if (gpa >= 3.7) return "On track for a First Class! 🏆";
    if (gpa >= 3.0) return "Impressive! Keep maintaining this pace. 🚀";
    if (gpa >= 2.0) return "You're safe, but keep aiming higher! 📈";
    return "Time to hit the books harder! 📚";
};
</script>

<style scoped>
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
  background: rgba(3, 3, 11, 0.95);
  backdrop-filter: blur(12px);
}

.modal-card {
  position: relative;
  width: 100%;
  max-width: 900px;
  background: #0d0d1f;
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: 28px;
  padding: 32px;
  z-index: 10;
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.8);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.modal-title {
  color: white;
  font-size: 22px;
  font-weight: 900;
  letter-spacing: -0.03em;
}

/* Mode Toggle Styling */
.mode-toggle {
  display: flex;
  background: rgba(255, 255, 255, 0.05);
  padding: 4px;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.08);
}

.toggle-btn {
  padding: 6px 16px;
  border: none;
  background: transparent;
  color: #64748b;
  font-size: 11px;
  font-weight: 700;
  border-radius: 8px;
  cursor: pointer;
  transition: 0.3s;
}

.toggle-btn.active {
  background: #6366f1;
  color: white;
  box-shadow: 0 4px 12px rgba(99, 102, 241, 0.3);
}

.close-btn {
  background: rgba(255, 255, 255, 0.05);
  border: none;
  width: 36px;
  height: 36px;
  border-radius: 50%;
  color: #64748b;
  cursor: pointer;
}

/* Table Spreadsheet Styling */
.table-container {
  max-height: 400px;
  overflow-y: auto;
  margin-bottom: 20px;
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.01);
}

.estimator-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 13px;
}

.estimator-table th {
  background: rgba(255, 255, 255, 0.03);
  color: #64748b;
  text-align: center;
  padding: 12px;
  font-weight: 800;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  position: sticky;
  top: 0;
  z-index: 5;
}

.estimator-table td {
  padding: 8px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.03);
}

.table-input, .table-select {
  width: 100%;
  background: transparent;
  border: 1px solid transparent;
  padding: 8px;
  color: white;
  border-radius: 8px;
  outline: none;
  transition: 0.2s;
}

.table-select {
  cursor: pointer;
  background: rgba(255, 255, 255, 0.03);
}

.table-select option {
  background: #0d0d1f;
  color: white;
}

.table-input:focus, .table-select:focus {
  background: rgba(255, 255, 255, 0.04);
  border-color: #6366f1;
}

.text-center { text-align: center; }
.font-bold { font-weight: 700; }
.font-black { font-weight: 900; }

.text-accent { color: #818cf8; }

.grade-badge {
    padding: 4px 8px;
    border-radius: 6px;
    font-size: 11px;
}

.grade-badge.a, .grade-badge.an { color: #10b981; background: rgba(16, 185, 129, 0.1); }
.grade-badge.b, .grade-badge.bn { color: #6366f1; background: rgba(99, 102, 241, 0.1); }
.grade-badge.c, .grade-badge.cn { color: #f59e0b; background: rgba(245, 158, 11, 0.1); }
.grade-badge.f { color: #ef4444; background: rgba(239, 68, 68, 0.1); }

.table-remove-btn {
  background: transparent;
  border: none;
  color: #ef4444;
  font-size: 20px;
  cursor: pointer;
  opacity: 0.5;
}

.table-remove-btn:hover { opacity: 1; }

.btn-add-row {
  background: transparent;
  color: #6366f1;
  border: 1px dashed rgba(99, 102, 241, 0.4);
  width: 100%;
  padding: 12px;
  border-radius: 12px;
  cursor: pointer;
  font-weight: 700;
  transition: 0.2s;
}

.btn-add-row:hover {
  background: rgba(99, 102, 241, 0.05);
  border-style: solid;
}

/* Live Result Section */
.live-result-section {
  margin-top: 32px;
  background: linear-gradient(135deg, rgba(99, 102, 241, 0.08), rgba(217, 70, 239, 0.08));
  border-radius: 24px;
  padding: 24px;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.result-display {
  display: flex;
  align-items: center;
  gap: 32px;
}

.gpa-circle {
  width: 120px;
  height: 120px;
  border: 4px solid #6366f1;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background: rgba(99, 102, 241, 0.1);
  box-shadow: 0 0 30px rgba(99, 102, 241, 0.3);
  flex-shrink: 0;
}

.gpa-label {
  font-size: 9px;
  font-weight: 800;
  color: #818cf8;
}

.gpa-value {
  font-size: 32px;
  font-weight: 900;
  color: white;
  margin: 0;
}

.result-details {
  flex: 1;
}

.motivation-quote {
  font-size: 16px;
  font-weight: 700;
  color: white;
  margin: 0 0 12px;
}

.stats-pills {
  display: flex;
  gap: 12px;
}

.stat-pill {
  background: rgba(255, 255, 255, 0.05);
  padding: 6px 12px;
  border-radius: 99px;
  font-size: 11px;
  color: #94a3b8;
  font-weight: 600;
}

/* Weightage Setup Styles */
.weightage-config {
  background: rgba(255, 255, 255, 0.03);
  padding: 16px;
  border-radius: 16px;
  border: 1px solid rgba(255, 255, 255, 0.05);
}

.config-label {
  font-size: 11px;
  font-weight: 800;
  color: #475569;
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: 12px;
}

.config-inputs {
  display: flex;
  gap: 20px;
  align-items: center;
  flex-wrap: wrap;
}

.weight-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  font-weight: 600;
  color: #94a3b8;
}

.weight-input {
  width: 60px;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 8px;
  padding: 6px;
  color: white;
  text-align: center;
  outline: none;
}

.weight-input:focus {
  border-color: #6366f1;
}

.weight-total {
  margin-left: auto;
  font-weight: 800;
  font-size: 12px;
  color: #10b981;
  display: flex;
  align-items: center;
  gap: 8px;
}

.weight-total.text-error {
  color: #ef4444;
}

.weight-warning {
  font-size: 10px;
  background: rgba(239, 68, 68, 0.1);
  padding: 2px 8px;
  border-radius: 4px;
}

.modal-glow {
  position: absolute;
  top: -20%;
  right: -20%;
  width: 60%;
  height: 60%;
  background: radial-gradient(circle, rgba(99, 102, 241, 0.15), transparent);
  filter: blur(60px);
  pointer-events: none;
}
</style>
