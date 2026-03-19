<template>
  <div class="card card-wide summarizer-card">
    <div class="card-inner">
      <div class="card-glow"></div>
      <div class="card-header">
        <div class="card-title-group">
          <h2 class="card-main-title">AI Lecture Summarizer</h2>
          <p class="card-sub-title">Upload PDFs or paste notes to get a clean AI summary.</p>
        </div>
        <div class="card-header-icon highlight-purple">
          <svg class="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z"></path>
          </svg>
        </div>
      </div>

      <div class="summarizer-content">
        <div class="input-tabs">
          <button @click="activeTab = 'upload'" :class="{ active: activeTab === 'upload' }" class="tab-btn">Upload PDF</button>
          <button @click="activeTab = 'paste'" :class="{ active: activeTab === 'paste' }" class="tab-btn">Paste Text</button>
        </div>

        <div v-if="activeTab === 'upload'" class="upload-section">
          <div 
            class="drop-zone" 
            :class="{ 'dragging': isDragging }"
            @dragover.prevent="isDragging = true"
            @dragleave.prevent="isDragging = false"
            @drop.prevent="handleDrop"
            @click="$refs.fileInput.click()"
          >
            <input type="file" ref="fileInput" @change="handleFileUpload" accept="application/pdf" hidden>
            <div class="drop-zone-content">
              <svg class="icon-large mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"></path>
              </svg>
              <p v-if="!selectedFile">Click or drag PDF here to summarize</p>
              <p v-else class="file-name">{{ selectedFile.name }}</p>
            </div>
          </div>
        </div>

        <div v-if="activeTab === 'paste'" class="paste-section">
          <textarea 
            v-model="pastedText" 
            placeholder="Paste your lecture notes here..." 
            class="notes-textarea"
          ></textarea>
        </div>

        <div class="actions-row mt-4">
          <button 
            @click="generateSummary" 
            :disabled="isLoading || (!selectedFile && !pastedText)" 
            class="btn-generate"
          >
            <span v-if="isLoading" class="spinner-small mr-2"></span>
            {{ isLoading ? 'Generating...' : 'Summarize Notes' }}
          </button>
        </div>

        <transition name="fade">
          <div v-if="summary" class="summary-result mt-6">
            <div class="result-header">
              <h3 class="result-title">Generated Summary</h3>
              <button @click="downloadPDF" class="btn-download">
                <svg class="icon-small mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"></path>
                </svg>
                Download PDF
              </button>
            </div>
            <div class="summary-text no-scrollbar">
              <div v-for="(line, idx) in formattedSummary" :key="idx" class="summary-line">
                <span v-if="line.startsWith('-') || line.startsWith('*')" class="bullet">•</span>
                <span class="line-content">{{ line.replace(/^[-*]\s*/, '') }}</span>
              </div>
            </div>
          </div>
        </transition>

        <div v-if="error" class="error-msg mt-3">
          {{ error }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import api from '../services/api';
import axios from 'axios'; // We still need raw axios for FormData if needed, but we can use api for consistency
import jsPDF from 'jspdf';

const activeTab = ref('upload');
const selectedFile = ref(null);
const pastedText = ref('');
const summary = ref('');
const isLoading = ref(false);
const isDragging = ref(false);
const error = ref('');

const formattedSummary = computed(() => {
  if (!summary.value) return [];
  return summary.value.split('\n').filter(line => line.trim().length > 0);
});

const handleFileUpload = (event) => {
  const file = event.target.files[0];
  if (file && file.type === 'application/pdf') {
    selectedFile.value = file;
    error.value = '';
  } else {
    error.value = 'Please select a valid PDF file.';
  }
};

const handleDrop = (event) => {
  isDragging.value = false;
  const file = event.dataTransfer.files[0];
  if (file && file.type === 'application/pdf') {
    selectedFile.value = file;
    error.value = '';
  } else {
    error.value = 'Please drop a valid PDF file.';
  }
};

const generateSummary = async () => {
  isLoading.value = true;
  summary.value = '';
  error.value = '';

  try {
    let response;
    if (activeTab.value === 'upload' && selectedFile.value) {
      const formData = new FormData();
      formData.append('file', selectedFile.value);
      
      // We use raw axios for FormData to avoid issues with default JSON headers in the API instance
      const token = localStorage.getItem('token');
      response = await axios.post('http://localhost:8082/api/summarizer/upload-pdf', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          'Authorization': `Bearer ${token}`
        }
      });
    } else if (activeTab.value === 'paste' && pastedText.value) {
      response = await api.post('/summarizer/text', {
        text: pastedText.value
      }, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
    }

    if (response?.data?.summary) {
      summary.value = response.data.summary;
    } else {
      error.value = 'Failed to get summary. Please try again.';
    }
  } catch (err) {
    console.error('Summarizer error:', err);
    error.value = err.response?.data?.error || 'An error occurred during summarization.';
  } finally {
    isLoading.value = false;
  }
};

const downloadPDF = () => {
  const doc = new jsPDF();
  
  // Title
  doc.setFontSize(20);
  doc.setTextColor(99, 102, 241); // Indigo color
  doc.text('Lecture Summary', 10, 20);
  
  // Content
  doc.setFontSize(12);
  doc.setTextColor(31, 41, 55); // Gray-800
  
  const splitText = doc.splitTextToSize(summary.value, 180);
  let y = 30;
  
  splitText.forEach(line => {
    if (y > 280) {
      doc.addPage();
      y = 20;
    }
    doc.text(line, 10, y);
    y += 7;
  });
  
  doc.save('lecture-summary.pdf');
};
</script>

<style scoped>
.summarizer-card {
  margin-bottom: 1.5rem;
}

.summarizer-content {
  padding: 1.5rem;
  color: #fff;
}

.input-tabs {
  display: flex;
  gap: 1rem;
  margin-bottom: 1.5rem;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 0.5rem;
}

.tab-btn {
  background: none;
  border: none;
  color: rgba(255, 255, 255, 0.6);
  padding: 0.5rem 1rem;
  cursor: pointer;
  font-weight: 600;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.tab-btn.active {
  color: #fff;
  background: rgba(255, 255, 255, 0.1);
}

.drop-zone {
  border: 2px dashed rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  padding: 2.5rem;
  text-align: center;
  transition: all 0.3s ease;
  cursor: pointer;
  background: rgba(255, 255, 255, 0.05);
}

.drop-zone:hover, .drop-zone.dragging {
  border-color: #a855f7;
  background: rgba(168, 85, 247, 0.1);
}

.drop-zone-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
}

.notes-textarea {
  width: 100%;
  height: 200px;
  background: rgba(0, 0, 0, 0.2);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  padding: 1rem;
  color: #fff;
  resize: vertical;
  font-family: inherit;
}

.btn-generate {
  width: 100%;
  padding: 0.8rem;
  background: linear-gradient(135deg, #6366f1 0%, #a855f7 100%);
  border: none;
  border-radius: 10px;
  color: #fff;
  font-weight: 700;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  display: flex;
  justify-content: center;
  align-items: center;
}

.btn-generate:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 15px rgba(99, 102, 241, 0.4);
}

.btn-generate:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.summary-result {
  background: rgba(255, 255, 255, 0.05);
  border-radius: 12px;
  padding: 1.5rem;
  border: 1px solid rgba(255, 255, 255, 0.1);
}

.result-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.result-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #a855f7;
}

.btn-download {
  background: rgba(255, 255, 255, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
  color: #fff;
  padding: 0.4rem 0.8rem;
  border-radius: 6px;
  font-size: 0.85rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  transition: all 0.2s ease;
}

.btn-download:hover {
  background: rgba(255, 255, 255, 0.2);
}

.summary-text {
  max-height: 300px;
  overflow-y: auto;
  padding-right: 0.5rem;
}

.summary-line {
  display: flex;
  gap: 0.8rem;
  margin-bottom: 0.6rem;
  line-height: 1.5;
}

.bullet {
  color: #a855f7;
  font-weight: bold;
}

.error-msg {
  color: #f87171;
  font-size: 0.9rem;
  text-align: center;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.highlight-purple {
  color: #a855f7;
}

.icon-large { width: 48px; height: 48px; }
.icon-small { width: 16px; height: 16px; }
.mr-2 { margin-right: 0.5rem; }
.mr-1 { margin-right: 0.25rem; }
.mt-4 { margin-top: 1rem; }
.mt-6 { margin-top: 1.5rem; }
.mt-3 { margin-top: 0.75rem; }
.mb-2 { margin-bottom: 0.5rem; }
.font-bold { font-weight: bold; }
</style>
