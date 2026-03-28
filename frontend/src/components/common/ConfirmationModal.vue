<template>
  <div class="fixed inset-0 backdrop-blur-md bg-[#111827]/40 flex items-center justify-center z-[60] p-4" @click.self="cancel">
    <div class="bg-white rounded-[2.5rem] shadow-2xl max-w-md w-full border border-gray-100 overflow-hidden transform transition-all">
      <div class="p-10">
        <div class="flex items-center justify-center mb-6">
          <div class="bg-[#fdf2f2] rounded-full p-5 text-[#991b1b]">
            <svg class="w-10 h-10" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path>
            </svg>
          </div>
        </div>

        <h3 class="text-2xl font-serif font-bold text-[#111827] text-center mb-3">
          {{ title }}
        </h3>
        <p class="text-gray-500 text-center mb-8 leading-relaxed font-serif italic px-2">
          "{{ message }}"
        </p>

        <div class="flex flex-col gap-3">
          <button 
            @click="confirm" 
            class="w-full bg-[#111827] hover:bg-red-800 text-white font-bold py-4 px-6 rounded-2xl transition-all duration-300 shadow-lg hover:shadow-red-900/20 transform hover:-translate-y-0.5 uppercase tracking-widest text-xs"
          >
            {{ confirmText }}
          </button>
          <button 
            @click="cancel" 
            class="w-full bg-[#f9f9f9] hover:bg-gray-100 text-gray-500 font-bold py-4 px-6 rounded-2xl transition-all duration-200 uppercase tracking-widest text-xs"
          >
            {{ cancelText }}
          </button>
        </div>
      </div>
      
      <div class="h-1.5 w-full bg-gradient-to-r from-[#a38a4d] via-[#111827] to-[#a38a4d]"></div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    default: 'Confirm Action'
  },
  message: {
    type: String,
    default: 'Are you sure you want to proceed?'
  },
  confirmText: {
    type: String,
    default: 'Confirm'
  },
  cancelText: {
    type: String,
    default: 'Cancel'
  }
});

const emit = defineEmits(['confirm', 'cancel']);

const confirm = () => {
  emit('confirm');
};

const cancel = () => {
  emit('cancel');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap');

.font-serif {
  font-family: 'Playfair Display', serif;
}
</style>