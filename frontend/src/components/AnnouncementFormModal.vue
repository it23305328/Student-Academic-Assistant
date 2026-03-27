<template>
  <div class="fixed inset-0 backdrop-blur-md bg-[#111827]/40 flex items-center justify-center z-50 p-4" @click.self="close">
    <div class="bg-white rounded-[2.5rem] shadow-2xl max-w-3xl w-full max-h-[90vh] overflow-y-auto border border-gray-100">
      <div class="sticky top-0 bg-white/80 backdrop-blur-sm border-b border-gray-100 px-8 py-6 flex justify-between items-center z-10">
        <h3 class="text-2xl font-serif font-bold text-[#111827] flex items-center gap-3">
          <div class="w-10 h-10 rounded-full bg-[#f9f9f9] flex items-center justify-center">
            <svg class="w-5 h-5 text-[#a38a4d]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"></path>
            </svg>
          </div>
          {{ isEditing ? 'Edit Entry' : 'New Announcement' }}
        </h3>
        <button @click="close" class="text-gray-400 hover:text-[#111827] transition-colors p-2 hover:bg-gray-100 rounded-full">
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>
      
      <form @submit.prevent="handleSubmit" class="p-8 space-y-8">
        <div class="form-group">
          <label class="block text-xs font-bold uppercase tracking-widest text-[#a38a4d] mb-2 ml-1">Announcement Topic *</label>
          <input 
            type="text" 
            v-model="formData.topic" 
            placeholder="e.g., Modernism in Digital Spaces"
            required 
            class="w-full px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] focus:border-transparent outline-none transition-all duration-300 font-serif text-lg"
          />
        </div>

        <div class="form-group">
          <label class="block text-xs font-bold uppercase tracking-widest text-[#a38a4d] mb-2 ml-1">Target Audience Configuration *</label>
          <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
            <select v-model="formData.targetYear" required class="px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none appearance-none cursor-pointer">
              <option value="">Select Year</option>
              <option value="all">All Years</option>
              <option v-for="y in 4" :key="y" :value="y">Year {{ y }}</option>
            </select>
            <select v-model="formData.targetSemester" required class="px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none appearance-none cursor-pointer">
              <option value="">Select Semester</option>
              <option value="all">All Semesters</option>
              <option value="1">Semester 1</option>
              <option value="2">Semester 2</option>
            </select>
            <select v-model="formData.targetType" required class="px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none appearance-none cursor-pointer">
              <option value="">Select Target Type</option>
              <option value="all">All Students</option>
              <option value="faculty">Specific Faculty</option>
              <option value="course">Specific Course</option>
            </select>
          </div>
          
          <div v-if="formData.targetType === 'faculty' || formData.targetType === 'course'" class="grid grid-cols-1 md:grid-cols-2 gap-4 mt-4 animate-in fade-in slide-in-from-top-2">
            <select v-model="formData.targetFaculty" required class="px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none appearance-none cursor-pointer">
              <option value="">Select Faculty</option>
              <option value="COMPUTING">Faculty of Computing</option>
              <option value="BUSINESS">Faculty of Business</option>
              <option value="HOTEL_MANAGEMENT">Faculty of Hotel Management</option>
            </select>
            
            <select v-if="formData.targetType === 'course'" v-model="formData.targetCourse" required class="px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none appearance-none cursor-pointer">
              <option value="">Select Course</option>
              <option v-for="course in getCoursesForFaculty(formData.targetFaculty)" :key="course" :value="course">
                {{ course }}
              </option>
            </select>
          </div>
        </div>

        <div class="form-group">
          <label class="block text-xs font-bold uppercase tracking-widest text-[#a38a4d] mb-2 ml-1">Curatorial Content *</label>
          <textarea 
            v-model="formData.content" 
            rows="5" 
            placeholder="Compose the details of your announcement..."
            required
            class="w-full px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] focus:border-transparent outline-none transition-all duration-300 resize-y font-serif leading-relaxed"
          ></textarea>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div class="form-group">
            <label class="block text-xs font-bold uppercase tracking-widest text-[#a38a4d] mb-2 ml-1">Schedule Publication</label>
            <input 
              type="datetime-local" 
              v-model="formData.datetime"
              :min="minDateTime"
              class="w-full px-5 py-3.5 bg-[#f9f9f9] border border-gray-200 rounded-2xl focus:ring-2 focus:ring-[#a38a4d] outline-none transition-all"
            />
          </div>

          <div class="form-group">
            <label class="block text-xs font-bold uppercase tracking-widest text-[#a38a4d] mb-2 ml-1">Visual Media (Optional)</label>
            <div class="flex flex-col gap-3">
              <label class="cursor-pointer group">
                <div class="flex items-center gap-3 px-5 py-3.5 bg-white border-2 border-dashed border-gray-200 rounded-2xl group-hover:border-[#a38a4d] group-hover:bg-[#fdfbf7] transition-all duration-300">
                  <svg class="w-5 h-5 text-gray-400 group-hover:text-[#a38a4d]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                  </svg>
                  <span class="text-sm font-medium text-gray-500 group-hover:text-[#a38a4d]">{{ formData.imageFile ? formData.imageFile.name : 'Upload Media Asset' }}</span>
                </div>
                <input 
                  type="file" 
                  @change="handleImageUpload" 
                  accept="image/*"
                  ref="imageInput"
                  class="hidden"
                />
              </label>

              <div v-if="formData.imagePreview" class="relative mt-2 w-fit group">
                <img :src="formData.imagePreview" alt="Preview" class="max-w-[180px] max-h-[120px] rounded-xl border border-gray-100 shadow-sm transition-transform group-hover:scale-105" />
                <button type="button" @click="removeImage" class="absolute -top-2 -right-2 bg-[#111827] text-white rounded-full w-6 h-6 flex items-center justify-center text-xs shadow-lg hover:bg-red-600 transition-colors">
                  <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                  </svg>
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="flex flex-col sm:flex-row gap-4 pt-6 border-t border-gray-100">
          <button 
            type="submit" 
            class="flex-1 flex items-center justify-center gap-3 bg-[#111827] hover:bg-[#1f2937] text-white font-bold py-4 px-8 rounded-2xl transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5 disabled:opacity-50 disabled:transform-none" 
            :disabled="isSubmitting"
          >
            <svg v-if="isSubmitting" class="animate-spin h-5 w-5 text-[#a38a4d]" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            <span v-else class="tracking-wide">{{ isEditing ? 'Update Entry' : 'Publish to Archive' }}</span>
          </button>
          <button 
            type="button" 
            @click="close" 
            class="flex-1 flex items-center justify-center gap-2 bg-[#f9f9f9] hover:bg-gray-100 text-gray-600 font-bold py-4 px-8 rounded-2xl transition-all duration-200 tracking-wide"
          >
            Cancel
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import announcementService from '../services/announcementService';

const props = defineProps({
  isEditing: {
    type: Boolean,
    default: false
  },
  announcementData: {
    type: Object,
    default: null
  }
});

const emit = defineEmits(['close', 'saved']);

const isSubmitting = ref(false);
const imageInput = ref(null);

const minDateTime = computed(() => {
  const now = new Date();
  now.setMinutes(now.getMinutes() - now.getTimezoneOffset());
  return now.toISOString().slice(0, 16);
});

const formData = ref({
  topic: '',
  targetYear: '',
  targetSemester: '',
  targetType: '',
  targetFaculty: '',
  targetCourse: '',
  content: '',
  datetime: '',
  imageUrl: '',
  imagePreview: '',
  imageFile: null
});

if (props.isEditing && props.announcementData) {
  formData.value = {
    ...props.announcementData,
    imagePreview: props.announcementData.imageUrl,
    imageFile: null
  };
}

const getCoursesForFaculty = (faculty) => {
  const courses = {
    'COMPUTING': ['Computer Science', 'Software Engineering', 'Information Technology', 'Data Science'],
    'BUSINESS': ['Business Administration', 'Marketing', 'Finance', 'International Business'],
    'HOTEL_MANAGEMENT': ['Hotel Management', 'Tourism Management', 'Culinary Arts', 'Event Management']
  };
  return courses[faculty] || [];
};

const handleImageUpload = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      formData.value.imagePreview = e.target.result;
      formData.value.imageFile = file;
    };
    reader.readAsDataURL(file);
  }
};

const removeImage = () => {
  formData.value.imagePreview = '';
  formData.value.imageFile = null;
  formData.value.imageUrl = '';
  if (imageInput.value) {
    imageInput.value.value = '';
  }
};

const handleSubmit = async () => {
  if (!formData.value.topic || !formData.value.content) {
    return;
  }

  if (!formData.value.targetType) {
    return;
  }

  isSubmitting.value = true;
  try {
    const formDataToSend = new FormData();
    
    const announcementData = {
      topic: formData.value.topic,
      content: formData.value.content,
      targetYear: formData.value.targetYear,
      targetSemester: formData.value.targetSemester,
      targetType: formData.value.targetType,
      targetFaculty: formData.value.targetFaculty,
      targetCourse: formData.value.targetCourse,
      datetime: formData.value.datetime,
      imageUrl: formData.value.imageUrl
    };
    
    formDataToSend.append('announcement', new Blob([JSON.stringify(announcementData)], { type: 'application/json' }));
    
    if (formData.value.imageFile) {
      formDataToSend.append('image', formData.value.imageFile);
    }

    if (props.isEditing) {
      await announcementService.updateAnnouncement(props.announcementData.id, formDataToSend);
    } else {
      await announcementService.addAnnouncement(formDataToSend);
    }
    
    emit('saved');
  } catch (error) {
    console.error('Error saving announcement:', error);
  } finally {
    isSubmitting.value = false;
  }
};

const close = () => {
  emit('close');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap');

.font-serif {
  font-family: 'Playfair Display', serif;
}

input::placeholder, textarea::placeholder {
  font-family: sans-serif;
  color: #9ca3af;
  font-size: 0.95rem;
}

/* Custom scrollbar for curatorial feel */
div::-webkit-scrollbar {
  width: 6px;
}
div::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}
div::-webkit-scrollbar-thumb {
  background: #d1d5db;
  border-radius: 10px;
}
div::-webkit-scrollbar-thumb:hover {
  background: #a38a4d;
}
</style>