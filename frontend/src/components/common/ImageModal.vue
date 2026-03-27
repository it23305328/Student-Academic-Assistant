<template>
  <div
    v-if="show"
    class="fixed inset-0 z-50 flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm transition-all duration-300"
    @click="close"
  >
    <div
      class="relative max-w-[90vw] max-h-[90vh] bg-transparent"
      @click.stop
    >
      <button
        @click="close"
        class="absolute top-4 right-4 md:-top-12 md:right-0 bg-black/50 md:bg-transparent hover:bg-black/70 md:hover:text-[#c5a358] text-white rounded-full md:rounded-none p-2 md:p-2 transition-all duration-200 backdrop-blur-sm md:backdrop-blur-none z-10"
        title="Close (Esc)"
        aria-label="Close image viewer"
      >
        <svg
          class="w-6 h-6 md:w-8 md:h-8"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M6 18L18 6M6 6l12 12"
          ></path>
        </svg>
      </button>
      <img
        :src="imageUrl"
        alt="Full size image"
        class="max-w-full max-h-[85vh] object-contain rounded-lg shadow-2xl"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, onUnmounted } from "vue";

const props = defineProps({
  imageUrl: {
    type: String,
    required: true,
  },
});

const emit = defineEmits(["close"]);

const show = ref(false);

const handleEscape = (e) => {
  if (e.key === "Escape" && show.value) {
    close();
  }
};

watch(
  () => props.imageUrl,
  (newVal) => {
    if (newVal) {
      show.value = true;
      document.body.style.overflow = "hidden";
      document.addEventListener("keydown", handleEscape);
    } else {
      show.value = false;
      document.body.style.overflow = "";
      document.removeEventListener("keydown", handleEscape);
    }
  },
  { immediate: true }
);

const close = () => {
  show.value = false;
  document.body.style.overflow = "";
  document.removeEventListener("keydown", handleEscape);
  emit("close");
};

onUnmounted(() => {
  document.body.style.overflow = "";
  document.removeEventListener("keydown", handleEscape);
});
</script>

<style scoped>
.fixed {
  animation: fadeIn 0.2s ease-out;
}

@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
</style>