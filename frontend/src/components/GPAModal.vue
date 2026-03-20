<template>
  <div v-if="isOpen" class="modal-overlay">
    <div class="backdrop" @click="closeModal"></div>

    <!-- Make modal wider for advanced mode to accommodate the side-by-side grid -->
    <div class="modal-card" :class="{ 'modal-wide': calcMode === 'advanced' }">
      <div class="modal-glow"></div>
      
      <!-- Header Section -->
      <header class="modal-header">
        <div class="header-titles">
          <h2 class="modal-title">
             <template v-if="calcMode === 'simple'">Semester <span class="highlight-text">Estimation</span></template>
             <template v-else>Advanced <span class="highlight-text">Calculator</span></template>
          </h2>
          <p class="modal-subtitle">
             <template v-if="calcMode === 'simple'">Input your course details below to calculate your projected performance.</template>
             <template v-else>Fine-tune your academic projections with granular component weighting and detailed grade tracking.</template>
          </p>
        </div>
        
        <div class="header-actions">
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
            <span class="material-symbols-outlined">close</span>
          </button>
        </div>
      </header>

      <div class="modal-body">
        
        <!-- ======================= SIMPLE MODE LAYOUT ======================= -->
        <template v-if="calcMode === 'simple'">
          <section class="estimation-card">
            <div class="table-container">
              <table class="estimator-table">
                <thead>
                  <tr>
                    <th class="col-name">Subject Name</th>
                    <th class="col-credits">Credits</th>
                    <th class="col-grade">Grade</th>
                    <th class="col-action"></th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(sub, index) in subjects" :key="index">
                    <td class="px-2">
                      <input type="text" v-model="sub.name" placeholder="e.g. Advanced Calculus" class="table-input w-full">
                    </td>
                    <td class="px-2 w-32">
                      <input type="number" v-model.number="sub.credits" min="1" max="10" class="table-input w-full text-center">
                    </td>
                    <td class="px-2 w-40 text-center font-bold">
                      <select v-model="sub.simpleGrade" class="table-select w-full">
                        <option v-for="g in GRADE_POINTS" :key="g.label" :value="g.label">{{ g.label }} ({{ g.points.toFixed(1) }})</option>
                      </select>
                    </td>
                    <td class="px-2 text-center">
                      <button @click="removeSubject(index)" class="table-remove-btn" v-if="subjects.length > 1">
                        <span class="material-symbols-outlined">delete</span>
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <div class="add-row-wrapper">
              <button @click="addSubject" class="btn-add-row">
                <span class="material-symbols-outlined">add</span>
                Add Subject
              </button>
            </div>
          </section>

          <section class="results-grid">
            <!-- GPA Result Card -->
            <div class="gpa-result-card">
              <div class="gpa-result-bg-icon">
                <span class="material-symbols-outlined">auto_awesome</span>
              </div>
              <div class="gpa-result-content">
                <p class="gpa-result-label">Estimated GPA</p>
                <div class="gpa-score-row">
                  <span class="gpa-black-text">{{ estimatedGPA }}</span>
                  <span class="gpa-slash-text">/ 4.00</span>
                </div>
                <div class="gpa-motivation-box">
                  <span class="material-symbols-outlined icon-verified">verified</span>
                  <p class="gpa-motivation-text">{{ getMotivationalText(estimatedGPA) }}</p>
                </div>
              </div>
            </div>
            
            <!-- Stats Card -->
            <div class="stats-card">
              <div>
                <p class="stats-card-label">Course Summary</p>
                <div class="stats-space-y">
                  <div class="stat-info-row">
                    <span class="stat-label-text">Total Credits</span>
                    <span class="stat-val-text">{{ totalCredits.toString().padStart(2, '0') }}</span>
                  </div>
                  <div class="mini-progress-track">
                    <div class="mini-progress-fill" style="width: 70%"></div>
                  </div>
                  <div class="stat-info-row">
                    <span class="stat-label-text">Subjects</span>
                    <span class="stat-val-text">{{ subjects.length }}</span>
                  </div>
                </div>
              </div>
              <button @click="clearAll" class="btn-history">
                Clear All 
                <span class="material-symbols-outlined">refresh</span>
              </button>
            </div>
          </section>
        </template>


        <!-- ======================= ADVANCED MODE LAYOUT ======================= -->
        <template v-else>
          <div class="advanced-grid">
            
            <!-- Sidebar (Weightage Config & Action Box) -->
            <aside class="advanced-sidebar">
              <div class="bg-surface-low rounded-xl p-8 space-y-8">
                <div>
                  <h2 class="advanced-card-title">Weightage Config</h2>
                  <p class="advanced-card-desc">Adjust how each category impacts your final grade.</p>
                </div>
                
                <div class="space-y-6">
                  <div class="space-y-3">
                    <div class="flex-justify-between">
                      <label class="weight-label">Assignments</label>
                      <span class="weight-val">{{ weights.assignment }}%</span>
                    </div>
                    <input class="weight-range" type="range" v-model.number="weights.assignment" min="0" max="100"/>
                  </div>
                  <div class="space-y-3">
                    <div class="flex-justify-between">
                      <label class="weight-label">Mid-term</label>
                      <span class="weight-val">{{ weights.midterm }}%</span>
                    </div>
                    <input class="weight-range" type="range" v-model.number="weights.midterm" min="0" max="100"/>
                  </div>
                  <div class="space-y-3">
                    <div class="flex-justify-between">
                      <label class="weight-label">Final Exam</label>
                      <span class="weight-val">{{ weights.final }}%</span>
                    </div>
                    <input class="weight-range" type="range" v-model.number="weights.final" min="0" max="100"/>
                  </div>
                </div>
                
                <div class="weight-info-bar" :class="{ 'error-bar': totalWeight !== 100 }">
                  <span class="material-symbols-outlined text-sm">{{ totalWeight === 100 ? 'info' : 'warning' }}</span>
                  <p class="text-xs">
                    {{ totalWeight === 100 ? 'Weights total 100% for accurate estimation.' : `Total is ${totalWeight}%. Must be 100%!` }}
                  </p>
                </div>
              </div>

              <div class="ready-action-card">
                <span class="material-symbols-outlined text-3xl">auto_stories</span>
                <h3 class="ready-title">Ready to compute?</h3>
                <p class="ready-desc">Your data is automatically calculated. Make sure weights total 100%.</p>
                <button @click="addSubject" class="btn-add-calc">
                  <span class="material-symbols-outlined text-sm">add</span> New Subject
                </button>
              </div>
            </aside>

            <!-- Main Panel (Table + Ribbon) -->
            <div class="advanced-main-panel">
              <div class="advanced-panel-header">
                <h2 class="advanced-card-title m-0">Academic Breakdown</h2>
                <button @click="addSubject" class="btn-ghost-primary">
                  <span class="material-symbols-outlined text-lg">add_circle</span>
                  Add Subject
                </button>
              </div>
              
              <div class="advanced-table-wrap">
                <table class="adv-table">
                  <thead>
                    <tr>
                      <th>Subject</th>
                      <th>Credits</th>
                      <th>ASMT</th>
                      <th>MID</th>
                      <th>FINAL</th>
                      <th>TOTAL</th>
                      <th class="text-right">GRADE</th>
                      <th></th>
                    </tr>
                  </thead>
                  <tbody class="divide-y">
                    <tr v-for="(sub, index) in subjects" :key="index" class="adv-tr">
                      <td class="font-bold text-primary">
                        <input class="trans-input w-full max-w-[150px]" placeholder="Subject Name" type="text" v-model="sub.name"/>
                      </td>
                      <td>
                        <input class="trans-input w-12 text-center" type="number" v-model.number="sub.credits" min="1" max="10"/>
                      </td>
                      <td><input class="trans-input w-12 text-center" type="number" v-model.number="sub.assignment" min="0" max="100"/></td>
                      <td><input class="trans-input w-12 text-center" type="number" v-model.number="sub.midterm" min="0" max="100"/></td>
                      <td><input class="trans-input w-12 text-center" type="number" v-model.number="sub.final" min="0" max="100"/></td>
                      <td class="font-bold text-muted">{{ calculateTotal(sub).toFixed(1) }}</td>
                      <td class="text-right font-black">
                         <span :class="['grade-badge', getGradeInfo(calculateTotal(sub)).label.toLowerCase().replace('-', 'n')]">
                            {{ getGradeInfo(calculateTotal(sub)).label }}
                         </span>
                      </td>
                      <td class="text-right">
                         <button @click="removeSubject(index)" class="btn-mini-del" v-if="subjects.length > 1">
                           <span class="material-symbols-outlined">delete</span>
                         </button>
                      </td>
                    </tr>
                  </tbody>
                </table>

                <div v-if="subjects.length === 0" class="empty-state">
                  <div class="empty-icon-box"><span class="material-symbols-outlined">sentiment_neutral</span></div>
                  <p class="empty-title">Time to hit the books harder!</p>
                  <p class="empty-desc">Start entering your marks to see your estimated GPA trajectory.</p>
                </div>
              </div>

              <!-- Footer GPA Ribbon -->
              <div class="ribbon-footer">
                <div class="ribbon-left">
                  <div class="ribbon-dash"></div>
                  <div>
                    <p class="ribbon-label">Estimated Semester GPA</p>
                    <p class="ribbon-val">{{ estimatedGPA }}</p>
                  </div>
                </div>
                <div class="ribbon-actions">
                  <button @click="clearAll" class="ribbon-btn-outline">Clear All</button>
                  <button @click="closeModal" class="ribbon-btn-solid">Save Report</button>
                </div>
              </div>
            </div>

          </div>

          <!-- Secondary Insights Section -->
          <section class="insights-row mt-12">
            <div class="insight-box">
              <span class="material-symbols-outlined text-primary">calculate</span>
              <h4 class="font-bold">Target GPA Solver</h4>
              <p class="text-sm text-muted">Calculate what scores you need in finals to reach a specific GPA goal.</p>
            </div>
            <div class="insight-box">
              <span class="material-symbols-outlined text-primary">history_edu</span>
              <h4 class="font-bold">Grade Scale</h4>
              <p class="text-sm text-muted">Current scale: Standard 4.0. You can modify thresholds in settings.</p>
            </div>
            <div class="insight-box">
              <span class="material-symbols-outlined text-primary">share</span>
              <h4 class="font-bold">Export Data</h4>
              <p class="text-sm text-muted">Download your academic forecast as a .csv or .pdf file.</p>
            </div>
          </section>

        </template>
        
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

const calcMode = ref('advanced'); // Starts in advanced mode based on current context. Users can switch.

const subjects = ref([
  { name: 'Advanced Algorithms', credits: 4, assignment: 85, midterm: 70, final: 90, simpleGrade: 'A' },
  { name: 'Microeconomics II', credits: 3, assignment: 75, midterm: 60, final: 80, simpleGrade: 'B+' },
  { name: 'Software Engineering', credits: 4, assignment: 90, midterm: 85, final: 88, simpleGrade: 'A' }
]);

const weights = ref({
  assignment: 40,
  midterm: 25,
  final: 35
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
  const score = ((sub.assignment||0) * (weights.value.assignment / 100)) + 
                ((sub.midterm||0) * (weights.value.midterm / 100)) + 
                ((sub.final||0) * (weights.value.final / 100));
  
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

const clearAll = () => {
    subjects.value = [{ name: '', credits: 3, assignment: 0, midterm: 0, final: 0, simpleGrade: 'A' }];
};

const closeModal = () => {
  emit('close');
};

const getMotivationalText = (gpaValue) => {
    const gpa = parseFloat(gpaValue);
    if (gpa >= 3.7) return "On track for a First Class!";
    if (gpa >= 3.0) return "Impressive! Keep maintaining this pace.";
    if (gpa >= 2.0) return "You're safe, but keep aiming higher!";
    return "Time to hit the books harder!";
};
</script>

<style scoped>
/* Base Font and Layout */
.modal-overlay {
  position: fixed;
  inset: 0;
  z-index: 1000;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  font-family: 'Manrope', sans-serif;
}

.backdrop {
  position: absolute;
  inset: 0;
  background: rgba(0, 0, 0, 0.4);
  backdrop-filter: blur(8px);
}

.modal-card {
  position: relative;
  width: 100%;
  max-width: 900px;
  background: #fafaf5;
  border-radius: 28px;
  padding: 40px;
  z-index: 10;
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.1);
  max-height: 90vh;
  overflow-y: auto;
  transition: max-width 0.3s ease;
}

.modal-wide {
  max-width: 1100px;
}

/* Custom Scrollbar */
.modal-card::-webkit-scrollbar, .advanced-table-wrap::-webkit-scrollbar { width: 6px; height: 6px; }
.modal-card::-webkit-scrollbar-thumb, .advanced-table-wrap::-webkit-scrollbar-thumb { background: rgba(78, 96, 115, 0.2); border-radius: 4px; }

/* Header */
.modal-header { display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 32px; }
.header-titles { flex: 1; }
.modal-title { color: #4e6073; font-size: 36px; font-weight: 800; letter-spacing: -0.02em; margin: 0 0 8px 0; line-height: 1.1; }
.highlight-text { color: #5a6159; }
.modal-subtitle { color: #5a6159; font-size: 16px; margin: 0; max-width: 500px; line-height: 1.5; }

.header-actions { display: flex; align-items: center; gap: 16px; }

/* Mode Toggle Styling */
.mode-toggle { display: flex; background: #e5eae0; padding: 4px; border-radius: 99px; }
.toggle-btn { padding: 8px 20px; border: none; background: transparent; color: #5a6159; font-size: 12px; font-weight: 700; border-radius: 99px; cursor: pointer; transition: 0.2s; }
.toggle-btn.active { background: white; color: #4e6073; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }

.close-btn { background: transparent; border: none; width: 40px; height: 40px; border-radius: 50%; color: #4e6073; cursor: pointer; display: flex; align-items: center; justify-content: center; transition: 0.2s; }
.close-btn:hover { background: #e5eae0; }

/* ================ SIMPLE MODE STYLES ================ */
.estimation-card { background: #f3f4ee; border-radius: 20px; padding: 8px; margin-bottom: 32px; }
.table-container { background: white; border-radius: 16px; padding: 32px; overflow-x: auto; }

.estimator-table { width: 100%; border-collapse: separate; border-spacing: 0 16px; }
.estimator-table th { padding-bottom: 8px; padding-left: 16px; padding-right: 16px; font-weight: 600; color: #5a6159; font-size: 12px; text-transform: uppercase; letter-spacing: 0.05em; text-align: left; }
.estimator-table th.text-center { text-align: center; }

.table-input, .table-select { width: 100%; background: #dee4da; border: none; border-radius: 12px; padding: 12px 16px; color: #2e342d; font-family: inherit; font-size: 14px; font-weight: 500; outline: none; transition: 0.2s; }
.table-select { cursor: pointer; appearance: none; }
.table-input:focus, .table-select:focus { background: #e5eae0; box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.2); }

.add-row-wrapper { display: flex; justify-content: center; margin-top: 16px; margin-bottom: 8px; }
.btn-add-row { display: flex; align-items: center; gap: 8px; padding: 12px 32px; background: #4e6073; color: white; border-radius: 99px; font-weight: 700; font-size: 14px; border: none; cursor: pointer; box-shadow: 0 4px 12px rgba(78, 96, 115, 0.2); transition: 0.2s; }
.btn-add-row:hover { opacity: 0.9; transform: scale(0.98); }

.results-grid { display: grid; grid-template-columns: 2fr 1fr; gap: 24px; }

/* Simple Mode GPA Card */
.gpa-result-card { background: linear-gradient(135deg, #4e6073, #425467); border-radius: 20px; padding: 40px; color: white; position: relative; overflow: hidden; box-shadow: 0 20px 40px rgba(78, 96, 115, 0.2); }
.gpa-result-bg-icon { position: absolute; top: 0; right: 0; padding: 32px; opacity: 0.1; }
.gpa-result-bg-icon .material-symbols-outlined { font-size: 120px; }
.gpa-result-content { position: relative; z-index: 10; }
.gpa-result-label { font-size: 12px; font-weight: 800; text-transform: uppercase; letter-spacing: 0.1em; opacity: 0.8; margin: 0 0 8px 0; }
.gpa-score-row { display: flex; align-items: baseline; gap: 16px; margin-bottom: 16px; }
.gpa-black-text { font-size: 72px; font-weight: 900; line-height: 1; }
.gpa-slash-text { font-size: 20px; font-weight: 500; opacity: 0.7; }

.gpa-motivation-box { display: inline-flex; align-items: center; gap: 12px; background: rgba(255, 255, 255, 0.1); backdrop-filter: blur(8px); padding: 16px; border-radius: 12px; }
.icon-verified { color: #d5dcfb; font-size: 24px; }
.gpa-motivation-text { margin: 0; font-size: 16px; font-weight: 600; letter-spacing: -0.01em; }

/* Stats Card */
.stats-card { background: #ecefe7; border-radius: 20px; padding: 32px; display: flex; flex-direction: column; justify-content: space-between; }
.stats-card-label { color: #5a6159; font-size: 12px; font-weight: 700; text-transform: uppercase; letter-spacing: 0.05em; margin: 0 0 24px 0; }
.stats-space-y { display: flex; flex-direction: column; gap: 16px; }
.stat-info-row { display: flex; justify-content: space-between; align-items: center; }
.stat-label-text { color: rgba(46, 52, 45, 0.7); font-size: 14px; font-weight: 500; }
.stat-val-text { color: #4e6073; font-size: 16px; font-weight: 800; }
.mini-progress-track { width: 100%; background: #e5eae0; height: 8px; border-radius: 99px; overflow: hidden; }
.mini-progress-fill { background: #4e6073; height: 100%; }
.btn-history { margin-top: 32px; color: #4e6073; font-size: 14px; font-weight: 800; display: flex; align-items: center; gap: 8px; background: transparent; border: none; cursor: pointer; transition: 0.2s; padding: 0; }
.btn-history:hover { opacity: 0.7; }

/* ================ ADVANCED MODE STYLES ================ */
.advanced-grid { display: grid; grid-template-columns: 4fr 8fr; gap: 32px; }

/* Sidebar */
.advanced-sidebar { display: flex; flex-direction: column; gap: 32px; }
.bg-surface-low { background: #f3f4ee; border-radius: 20px; padding: 32px; }
.advanced-card-title { font-size: 20px; font-weight: 800; color: #4e6073; margin: 0 0 8px 0; }
.advanced-card-desc { font-size: 14px; color: #5a6159; margin: 0 0 24px 0; }
.space-y-8 { display: flex; flex-direction: column; gap: 32px; }
.space-y-6 { display: flex; flex-direction: column; gap: 24px; }
.space-y-3 { display: flex; flex-direction: column; gap: 12px; }
.flex-justify-between { display: flex; justify-content: space-between; align-items: center; }
.weight-label { font-size: 14px; font-weight: 700; color: #5b6063; }
.weight-val { font-size: 14px; font-weight: 800; color: #4e6073; }

.weight-range {
  -webkit-appearance: none;
  width: 100%;
  height: 8px;
  background: #dee4da;
  border-radius: 99px;
  outline: none;
  accent-color: #4e6073;
}
.weight-range::-webkit-slider-thumb {
  -webkit-appearance: none;
  width: 16px; height: 16px;
  background: #4e6073; border-radius: 50%; cursor: pointer;
}

.weight-info-bar { display: flex; align-items: center; gap: 12px; border-top: 1px solid rgba(0,0,0,0.05); padding-top: 16px; color: #5a6159; }
.error-bar { color: #f43f5e; font-weight: 700; border-top-color: rgba(244,63,94,0.2); }

/* Ready Box */
.ready-action-card { background: #4e6073; color: white; padding: 32px; border-radius: 20px; display: flex; flex-direction: column; gap: 16px; }
.ready-title { font-size: 18px; font-weight: 800; margin: 0; }
.ready-desc { font-size: 14px; color: rgba(255,255,255,0.7); margin: 0; line-height: 1.5; }
.btn-add-calc { margin-top: 16px; background: #d1e4fb; color: #415366; font-weight: 800; padding: 12px; border-radius: 99px; border: none; cursor: pointer; transition: 0.2s; display: flex; align-items: center; justify-content: center; gap: 8px; }
.btn-add-calc:hover { opacity: 0.9; }

/* Main Table Panel */
.advanced-main-panel { background: white; border-radius: 20px; box-shadow: 0 20px 50px rgba(46,52,45,0.04); display: flex; flex-direction: column; overflow: hidden; border: 1px solid rgba(0,0,0,0.05); }
.advanced-panel-header { padding: 32px; border-bottom: 1px solid #ecefe7; display: flex; justify-content: space-between; align-items: center; }
.m-0 { margin: 0; }
.btn-ghost-primary { display: flex; align-items: center; gap: 8px; font-size: 14px; font-weight: 700; color: #4e6073; background: transparent; border: none; padding: 8px 16px; border-radius: 99px; cursor: pointer; transition: 0.2s; }
.btn-ghost-primary:hover { background: #f3f4ee; }

.advanced-table-wrap { overflow-x: auto; flex: 1; }
.adv-table { width: 100%; border-collapse: collapse; text-align: left; }
.adv-table th { padding: 16px 24px; font-size: 11px; font-weight: 800; text-transform: uppercase; letter-spacing: 0.05em; color: #5a6159; background: rgba(243,244,238,0.5); }
.adv-tr { transition: background 0.2s; }
.adv-tr:hover { background: rgba(243,244,238,0.3); }
.adv-table td { padding: 16px 24px; border-bottom: 1px solid #ecefe7; }

.trans-input { background: transparent; border: none; font-family: inherit; font-size: 14px; font-weight: 600; color: #2e342d; outline: none; transition: 0.2s; border-radius: 8px; padding: 4px 8px; margin: -4px -8px; }
.trans-input:focus { background: #f3f4ee; color: #4e6073; }
.text-muted { color: #adb4aa; }

.grade-badge { padding: 4px 8px; border-radius: 6px; font-size: 12px; font-weight: 900; }
.btn-mini-del { background: transparent; border: none; color: #9c9d99; cursor: pointer; opacity: 0.7; transition: 0.2s; }
.btn-mini-del:hover { color: #f43f5e; opacity: 1; }

.empty-state { padding: 48px; text-align: center; background: rgba(243,244,238,0.2); flex: 1; display: flex; flex-direction: column; align-items: center; justify-content: center; }
.empty-icon-box { width: 64px; height: 64px; background: #ecefe7; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin-bottom: 16px; }
.empty-icon-box .material-symbols-outlined { font-size: 32px; color: #adb4aa; }
.empty-title { font-size: 18px; font-weight: 800; color: #2e342d; margin: 0 0 4px 0; }
.empty-desc { font-size: 14px; color: #5a6159; margin: 0; max-width: 300px; }

/* Footer Ribbon */
.ribbon-footer { background: #ecefe7; padding: 24px 32px; display: flex; justify-content: space-between; align-items: center; }
.ribbon-left { display: flex; align-items: center; gap: 16px; }
.ribbon-dash { width: 6px; height: 48px; background: #4e6073; border-radius: 99px; }
.ribbon-label { font-size: 11px; font-weight: 800; text-transform: uppercase; letter-spacing: -0.02em; color: #5a6159; margin: 0 0 2px 0; }
.ribbon-val { font-size: 28px; font-weight: 900; color: #4e6073; margin: 0; line-height: 1; }

.ribbon-actions { display: flex; gap: 16px; }
.ribbon-btn-outline { background: white; color: #4e6073; padding: 12px 24px; border-radius: 99px; font-weight: 800; font-size: 14px; border: none; box-shadow: 0 2px 8px rgba(0,0,0,0.05); cursor: pointer; transition: 0.2s; }
.ribbon-btn-outline:hover { box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
.ribbon-btn-solid { background: #4e6073; color: white; padding: 12px 24px; border-radius: 99px; font-weight: 800; font-size: 14px; border: none; box-shadow: 0 2px 8px rgba(0,0,0,0.05); cursor: pointer; transition: 0.2s; }
.ribbon-btn-solid:hover { box-shadow: 0 6px 16px rgba(78,96,115,0.3); }

/* Insights row */
.insights-row { display: grid; grid-template-columns: repeat(3, 1fr); gap: 32px; margin-top: 24px; }
.insight-box { background: #f3f4ee; border-radius: 16px; padding: 32px; display: flex; flex-direction: column; gap: 16px; }
.insight-box .material-symbols-outlined { font-size: 28px; color: #4e6073; }
.insight-box h4 { font-size: 16px; font-weight: 800; margin: 0; color: #2e342d; }
.insight-box p { font-size: 14px; color: #5a6159; margin: 0; line-height: 1.5; }

/* Utility */
.mt-12 { margin-top: 48px; }

@media(max-width: 1024px) {
  .advanced-grid { grid-template-columns: 1fr; }
  .insights-row { grid-template-columns: 1fr; }
}
@media(max-width: 768px) {
  .results-grid { grid-template-columns: 1fr; }
  .modal-card { padding: 24px; }
  .ribbon-footer { flex-direction: column; gap: 24px; align-items: flex-start; }
}
</style>
