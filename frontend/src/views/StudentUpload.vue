<template>
  <div class="upload-container">
    <button class="close-btn" @click="$emit('close')">&times;</button>

    <div class="upload-header">
      <h4>📤 Upload Study Material</h4>
      <p>Submit your resources to the library for approval</p>
    </div>

    <form @submit.prevent="uploadFile" class="styled-form">
      
      <div class="form-group">
        <label>Subject Name</label>
        <input v-model="subject" type="text" placeholder="e.g. ITPM, DBMS, Networking" required />
        <span v-if="errors.subject" class="error-text">{{ errors.subject }}</span>
      </div>

      <div class="form-group">
        <label>Semester</label>
        <select v-model="semester" required>
          <option value="" disabled>Select Semester</option>
          <optgroup label="Year 1">
            <option value="Y1S1">Year 1 - Semester 1</option>
            <option value="Y1S2">Year 1 - Semester 2</option>
          </optgroup>
          <optgroup label="Year 2">
            <option value="Y2S1">Year 2 - Semester 1</option>
            <option value="Y2S2">Year 2 - Semester 2</option>
          </optgroup>
          <optgroup label="Year 3">
            <option value="Y3S1">Year 3 - Semester 1</option>
            <option value="Y3S2">Year 3 - Semester 2</option>
          </optgroup>
          <optgroup label="Year 4">
            <option value="Y4S1">Year 4 - Semester 1</option>
            <option value="Y4S2">Year 4 - Semester 2</option>
          </optgroup>
        </select>
        <span v-if="errors.semester" class="error-text">{{ errors.semester }}</span>
      </div>

      <div class="form-group">
        <label>Select PDF/Docx File</label>
        <span v-if="errors.file" class="error-text">{{ errors.file }}</span>
        <div class="file-input-wrapper">
          <input 
            type="file" 
            @change="handleFile" 
            accept=".pdf,.docx" 
            id="file-upload"
            ref="fileInput"
            :required="!file" 
          />
          
          <label v-if="!file" for="file-upload" class="file-label">
            📁 Click to choose or drag a file here
          </label>

          <div v-else class="selected-file-display">
            <span class="file-info">✅ {{ file.name }}</span>
            <button type="button" class="remove-file-btn" @click="removeFile" title="Remove file">❌</button>
          </div>
        </div>
      </div>

      <button 
  type="button" 
  @click="validateAndUpload" 
  :disabled="loading" 
  class="submit-btn"
>
  <span v-if="loading">UPLOADING...</span>
  <span v-else>UPLOAD</span>
</button>

      <p v-if="message" :class="statusClass" class="status-msg">{{ message }}</p>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      subject: '',
      semester: '',
      file: null,
      loading: false,
      message: '',
      statusClass: '',
      errors: {}
    };
  },
  
  methods: {
    // Captures the file selected by user
    handleFile(event) {
      const selected = event.target.files[0];
      if (selected) {
        this.file = selected;
      }
    },

    // --- 🆕 NEW: Function to validate fields before uploading ---
validateAndUpload() {
  this.errors = {}; // Reset errors every time

  // Check if subject is empty (Supports short names like 'DS')
  if (!this.subject || this.subject.trim() === "") {
    this.errors.subject = "Subject name is required.";
  }

  // Check if semester is selected
  if (!this.semester) {
    this.errors.semester = "Please select a semester.";
  }

  // Check if a file is attached
  if (!this.file) {
    this.errors.file = "Please upload a PDF or Docx file.";
  }

  // If no errors, proceed to the actual upload function
  if (Object.keys(this.errors).length === 0) {
    this.uploadFile();
  }

},

    // Removes the selected file and resets the input
    removeFile() {
      this.file = null;
      // Resets the actual HTML input so user can pick the same file again if needed
      if (this.$refs.fileInput) {
        this.$refs.fileInput.value = '';
      }
    },
    
    // Uploads the data to the backend
    async uploadFile() {
      this.loading = true;
      this.message = ''; 
      
      const formData = new FormData();
      formData.append('file', this.file);
      formData.append('subject', this.subject);
      formData.append('semester', this.semester);
      formData.append('uploadedBy', 'Hasini');

      try {
        const res = await axios.post('http://localhost:8082/api/resources/upload', formData);
        this.message = "🎉 " + res.data;
        this.statusClass = 'success';

        setTimeout(() => {
            this.$emit('success'); 
        }, 1500);

      } catch (err) {
        this.message = "❌ Upload failed!";
        this.statusClass = 'error';
      } finally {
        this.loading = false;
      }
    }
  }
};
</script>

<style scoped>
.upload-container {
  font-family: 'Poppins', sans-serif;
  max-width: 550px;
  margin: 20px auto;
  padding: 40px; 
  background: #ffffff;
  border-radius: 15px;
  position: relative; /* Needed for positioning the close button */
  box-shadow: 0 10px 40px rgba(0,0,0,0.1);
}

/* ❌ Close Button Style */
.close-btn {
  position: absolute;
  top: 15px;
  right: 20px;
  background: none;
  border: none;
  font-size: 30px;
  color: #c6932e; /* Gold color */
  cursor: pointer;
  line-height: 1;
  transition: transform 0.2s;
}
.close-btn:hover {
  transform: scale(1.2);
  color: #000;
}

.upload-header h4 {
  color: #c6932e;
  font-size: 1.8rem;
  font-weight: 700;
  text-align: center;
  text-transform: uppercase;
}

.upload-header p { text-align: center; color: #718096; margin-bottom: 25px; }

.styled-form { display: flex; flex-direction: column; gap: 20px; }

.form-group label { font-weight: 600; color: #c6932e; margin-bottom: 8px; display: block; }

input[type="text"], select {
  padding: 15px;
  border: 2px solid #edf2f7;
  border-radius: 10px;
  width: 100%;
  outline: none;
}

input:focus, select:focus { border-color: #c6932e; }

/* 📂 File Upload Styling */
.file-label {
  display: block;
  padding: 40px 20px;
  background: #fffcf0;
  border: 2px dashed #cbd5e0;
  border-radius: 12px;
  text-align: center;
  cursor: pointer;
}

.selected-file-display {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 15px;
  background: #f0fff4;
  border: 2px solid #c6932e;
  border-radius: 10px;
}

.file-info { font-weight: bold; color: #2d3748; overflow: hidden; text-overflow: ellipsis; }

/* ❌ Remove File Button */
.remove-file-btn {
  background: none;
  border: none;
  cursor: pointer;
  font-size: 1.2rem;
  padding: 0 5px;
}

#file-upload { display: none; }

/* 🏆 Black Button with Gold Text */
.submit-btn {
  background: #1a1a1a;
  color: #c6932e;
  padding: 18px;
  border: none;
  border-radius: 10px;
  font-weight: 800;
  font-size: 1.2rem;
  cursor: pointer;
  letter-spacing: 2px;
  margin-top: 10px;
}

.submit-btn:hover:not(:disabled) { background: #000; transform: translateY(-2px); }

.status-msg { text-align: center; padding: 10px; border-radius: 8px; margin-top: 10px; }
.success { background: #f0fff4; color: #2f855a; }
.error { background: #fff5f5; color: #c53030; }
</style>