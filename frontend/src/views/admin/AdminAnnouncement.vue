<template>
  <div class="announcement-management space-y-8 pb-12 font-sans text-[#1a1a1a]">
    <div
      class="bg-white rounded-[2rem] shadow-sm p-8 md:p-10 border border-gray-100"
    >
      <div
        class="flex flex-col md:flex-row justify-between items-start md:items-center gap-6"
      >
        <div>
          <h2
            class="text-4xl font-serif font-bold text-[#111827] tracking-tight"
          >
            Announcements
          </h2>
          <p class="text-gray-500 mt-2 text-lg">
            Curate and distribute university-wide insights
          </p>
        </div>
        <button
          @click="openCreateModal"
          class="flex items-center gap-2 bg-[#111827] hover:bg-[#1f2937] text-white font-medium py-3 px-8 rounded-full transition-all duration-300 shadow-lg hover:shadow-xl transform hover:-translate-y-0.5"
        >
          <svg
            class="w-5 h-5 text-[#c5a358]"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M12 4v16m8-8H4"
            ></path>
          </svg>
          Create New Announcement
        </button>
      </div>
    </div>

    <div class="bg-[#f9f9f9] rounded-[2rem] p-8 border border-gray-200/60">
      <h3
        class="text-sm font-bold uppercase tracking-widest text-[#a38a4d] mb-6 flex items-center gap-2"
      >
        <svg
          class="w-4 h-4"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z"
          ></path>
        </svg>
        Refine Archive
      </h3>
      <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4">
        <div class="relative lg:col-span-1">
          <label class="block text-xs font-semibold text-gray-400 mb-1.5 ml-1"
            >Search</label
          >
          <div class="relative">
            <input
              type="text"
              v-model="filters.searchQuery"
              placeholder="Topic or content..."
              class="w-full pl-4 pr-4 py-2.5 bg-white border border-gray-200 rounded-xl focus:ring-2 focus:ring-[#a38a4d] focus:border-transparent outline-none transition-all"
            />
          </div>
        </div>

        <div
          v-for="filter in ['Faculty', 'Course', 'Year', 'Semester']"
          :key="filter"
        >
          <label
            class="block text-xs font-semibold text-gray-400 mb-1.5 ml-1"
            >{{ filter }}</label
          >
          <select
            v-model="filters[filter.toLowerCase()]"
            class="w-full px-4 py-2.5 bg-white border border-gray-200 rounded-xl focus:ring-2 focus:ring-[#a38a4d] outline-none transition-all appearance-none cursor-pointer"
          >
            <template v-if="filter === 'Faculty'">
              <option value="">All Faculties</option>
              <option value="COMPUTING">Faculty of Computing</option>
              <option value="BUSINESS">Faculty of Business</option>
              <option value="HOTEL_MANAGEMENT">
                Faculty of Hotel Management
              </option>
            </template>
            <template v-else-if="filter === 'Course'">
              <option value="">All Courses</option>
              <option
                v-for="course in availableCourses"
                :key="course"
                :value="course"
              >
                {{ course }}
              </option>
            </template>
            <template v-else-if="filter === 'Year'">
              <option value="">All Years</option>
              <option value="all">Any Year</option>
              <option v-for="y in 4" :key="y" :value="y">Year {{ y }}</option>
            </template>
            <template v-else-if="filter === 'Semester'">
              <option value="">All Semesters</option>
              <option value="all">Any Semester</option>
              <option value="1">Semester 1</option>
              <option value="2">Semester 2</option>
            </template>
          </select>
        </div>
      </div>

      <div class="flex justify-end mt-4">
        <button
          @click="resetFilters"
          class="text-xs font-bold text-[#a38a4d] hover:text-[#8a743d] flex items-center gap-1 transition-colors"
        >
          <svg
            class="w-3 h-3"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
            ></path>
          </svg>
          RESET FILTERS
        </button>
      </div>
    </div>

    <div class="space-y-4">
      <div class="flex justify-between items-end mb-6 px-4">
        <h3 class="text-xl font-serif font-bold text-gray-800">
          Latest Entries
          <span class="text-sm font-sans font-normal text-gray-400 ml-2"
            >/ {{ filteredAnnouncements.length }} items</span
          >
        </h3>
      </div>

      <div class="space-y-4">
        <div
          v-for="announcement in filteredAnnouncements"
          :key="announcement.id"
          class="bg-white border border-gray-100 rounded-[1.5rem] overflow-hidden hover:shadow-xl transition-all duration-300 group"
        >
          <div
            @click="toggleExpand(announcement.id)"
            class="flex items-center justify-between p-6 cursor-pointer"
          >
            <div class="flex-1 flex items-center gap-6">
              <div
                class="hidden md:flex flex-col items-center justify-center min-w-[80px] text-center border-r border-gray-100 pr-6"
              >
                <span
                  class="text-xs font-bold text-[#a38a4d] uppercase tracking-tighter"
                  >Entry</span
                >
                <span class="text-lg font-serif font-bold text-[#111827]"
                  >#{{ announcement.id.toString().slice(-3) }}</span
                >
              </div>
              <div class="flex-1">
                <h4
                  class="text-xl font-serif font-semibold text-[#111827] group-hover:text-[#a38a4d] transition-colors"
                >
                  {{ announcement.topic }}
                </h4>
                <div class="flex flex-wrap items-center gap-4 mt-2">
                  <span
                    class="text-[10px] font-bold uppercase tracking-widest bg-gray-100 text-gray-600 px-3 py-1 rounded-full"
                  >
                    {{ getTargetText(announcement) }}
                  </span>
                  <span
                    class="flex items-center gap-1.5 text-gray-400 text-xs font-medium"
                  >
                    <svg
                      class="w-3.5 h-3.5"
                      fill="none"
                      stroke="currentColor"
                      viewBox="0 0 24 24"
                    >
                      <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                      ></path>
                    </svg>
                    {{
                      formatDate(
                        announcement.datetime || announcement.createdAt
                      )
                    }}
                  </span>
                </div>
              </div>
            </div>

            <div class="flex items-center gap-3 ml-4" @click.stop>
              <button
                @click="openEditModal(announcement)"
                class="p-2.5 text-gray-400 hover:text-[#a38a4d] hover:bg-gray-50 rounded-full transition-all"
                title="Edit Entry"
              >
                <svg
                  class="w-5 h-5"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"
                  ></path>
                </svg>
              </button>
              <button
                @click="confirmDelete(announcement.id)"
                class="p-2.5 text-gray-400 hover:text-red-600 hover:bg-red-50 rounded-full transition-all"
                title="Delete Entry"
              >
                <svg
                  class="w-5 h-5"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                  ></path>
                </svg>
              </button>
              <div class="w-px h-6 bg-gray-100 mx-1"></div>
              <svg
                class="w-5 h-5 text-gray-300 transition-transform duration-300"
                :class="{
                  'rotate-180 text-[#a38a4d]': expandedId === announcement.id,
                }"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  stroke-width="2"
                  d="M19 9l-7 7-7-7"
                ></path>
              </svg>
            </div>
          </div>

          <div
            v-show="expandedId === announcement.id"
            class="border-t border-gray-50 p-8 bg-[#fdfdfd]"
          >
            <div class="max-w-4xl">
              <div
                class="text-[#4b5563] leading-relaxed whitespace-pre-wrap text-lg font-serif"
              >
                {{ announcement.content }}
              </div>
              <div v-if="announcement.imageUrl" class="mt-6">
                <img
                  :src="announcement.imageUrl"
                  alt="Announcement image"
                  class="rounded-[1.5rem] border border-gray-200 shadow-sm max-w-[200px] max-h-[200px] object-cover cursor-pointer hover:opacity-90 transition-opacity"
                  @click.stop="openImageModal(announcement.imageUrl)"
                />
              </div>
            </div>
          </div>
        </div>

        <div
          v-if="filteredAnnouncements.length === 0"
          class="text-center py-24 bg-white rounded-[2rem] border-2 border-dashed border-gray-100"
        >
          <div
            class="w-16 h-16 bg-gray-50 rounded-full flex items-center justify-center mx-auto mb-4"
          >
            <svg
              class="w-8 h-8 text-gray-300"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253"
              ></path>
            </svg>
          </div>
          <p class="text-gray-400 font-serif italic text-lg">
            Your archive is currently empty.
          </p>
        </div>
      </div>
    </div>

    <AnnouncementFormModal
      v-if="showFormModal"
      :is-editing="isEditing"
      :announcement-data="editingAnnouncement"
      @close="closeFormModal"
      @saved="onAnnouncementSaved"
    />

    <ConfirmationModal
      v-if="showDeleteModal"
      title="Discard Entry"
      message="Are you certain you wish to remove this entry from the archive? This action cannot be reversed."
      confirm-text="Discard"
      cancel-text="Keep Entry"
      @confirm="deleteAnnouncement"
      @cancel="closeDeleteModal"
    />

    <ImageModal
      v-if="showImageModal"
      :image-url="modalImageUrl"
      @close="closeImageModal"
    />

    <!-- Toast Notifications Container - Top Right -->
    <div class="toast-container fixed top-6 right-6 z-[9999] flex flex-col gap-3">
      <transition-group name="toast">
        <div v-for="toast in notificationToasts" :key="toast.id" class="toast-item bg-white rounded-2xl shadow-2xl border border-gray-100 overflow-hidden flex items-center gap-4 p-4 min-w-[280px] max-w-[380px]" :class="{
          'border-l-4 border-l-[#1A1A1A]': toast.type === 'success',
          'border-l-4 border-l-red-500': toast.type === 'error'
        }">
          <div class="toast-icon flex-shrink-0">
            <CheckCircle v-if="toast.type === 'success'" class="w-5 h-5 text-[#1A1A1A]" />
            <XCircle v-else-if="toast.type === 'error'" class="w-5 h-5 text-red-500" />
          </div>
          <div class="toast-content flex-1">
            <p class="text-xs font-medium text-gray-800">{{ toast.message }}</p>
          </div>
          <button @click="dismissToast(toast.id)" class="toast-close text-gray-300 hover:text-gray-600 transition-colors text-xl leading-none">&times;</button>
        </div>
      </transition-group>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import announcementService from "../../services/announcementService";
import AnnouncementFormModal from "../../components/AnnouncementFormModal.vue";
import ConfirmationModal from "../../components/common/ConfirmationModal.vue";
import ImageModal from "../../components/common/ImageModal.vue";
import { CheckCircle, XCircle } from 'lucide-vue-next';

const announcements = ref([]);
const expandedId = ref(null);
const showFormModal = ref(false);
const isEditing = ref(false);
const editingAnnouncement = ref(null);
const showDeleteModal = ref(false);
const deleteId = ref(null);
const showImageModal = ref(false);
const modalImageUrl = ref("");
const notificationToasts = ref([]);

const filters = ref({
  searchQuery: "",
  faculty: "",
  course: "",
  year: "",
  semester: "",
});

const availableCourses = computed(() => {
  if (!filters.value.faculty) return [];
  const courses = {
    COMPUTING: [
      "Computer Science",
      "Software Engineering",
      "Information Technology",
      "Data Science",
    ],
    BUSINESS: [
      "Business Administration",
      "Marketing",
      "Finance",
      "International Business",
    ],
    HOTEL_MANAGEMENT: [
      "Hotel Management",
      "Tourism Management",
      "Culinary Arts",
      "Event Management",
    ],
  };
  return courses[filters.value.faculty] || [];
});

const filteredAnnouncements = computed(() => {
  let filtered = announcements.value;

  if (filters.value.searchQuery) {
    const query = filters.value.searchQuery.toLowerCase();
    filtered = filtered.filter(
      (ann) =>
        ann.topic.toLowerCase().includes(query) ||
        ann.content.toLowerCase().includes(query)
    );
  }

  if (filters.value.faculty) {
    filtered = filtered.filter(
      (ann) => ann.targetFaculty === filters.value.faculty
    );
  }

  if (filters.value.course) {
    filtered = filtered.filter(
      (ann) => ann.targetCourse === filters.value.course
    );
  }

  if (filters.value.year) {
    if (filters.value.year !== "all") {
      filtered = filtered.filter(
        (ann) => ann.targetYear === filters.value.year
      );
    }
  }

  if (filters.value.semester) {
    if (filters.value.semester !== "all") {
      filtered = filtered.filter(
        (ann) => ann.targetSemester === filters.value.semester
      );
    }
  }

  return filtered;
});

const showToast = (message, type = 'success') => {
  const id = Date.now();
  notificationToasts.value.push({ id, message, type });
  setTimeout(() => {
    dismissToast(id);
  }, 4000);
};

const dismissToast = (id) => {
  notificationToasts.value = notificationToasts.value.filter(t => t.id !== id);
};

const fetchAnnouncements = async () => {
  try {
    const response = await announcementService.getAllAnnouncements();
    announcements.value = response.data;
  } catch (error) {
    console.error("Failed to fetch announcements:", error);
    showToast('Failed to load announcements. Please try again.', 'error');
  }
};

const toggleExpand = (id) => {
  expandedId.value = expandedId.value === id ? null : id;
};

const openCreateModal = () => {
  isEditing.value = false;
  editingAnnouncement.value = null;
  showFormModal.value = true;
};

const openEditModal = (announcement) => {
  isEditing.value = true;
  editingAnnouncement.value = announcement;
  showFormModal.value = true;
};

const closeFormModal = () => {
  showFormModal.value = false;
  editingAnnouncement.value = null;
};

const onAnnouncementSaved = async () => {
  closeFormModal();
  await fetchAnnouncements();
  showToast('Announcement saved successfully!', 'success');
};

const confirmDelete = (id) => {
  deleteId.value = id;
  showDeleteModal.value = true;
};

const closeDeleteModal = () => {
  showDeleteModal.value = false;
  deleteId.value = null;
};

const deleteAnnouncement = async () => {
  try {
    await announcementService.deleteAnnouncement(deleteId.value);
    await fetchAnnouncements();
    closeDeleteModal();
    showToast('Announcement deleted successfully!', 'success');
  } catch (error) {
    console.error("Failed to delete announcement:", error);
    showToast('Failed to delete announcement. Please try again.', 'error');
  }
};

const resetFilters = () => {
  filters.value = {
    searchQuery: "",
    faculty: "",
    course: "",
    year: "",
    semester: "",
  };
  showToast('Filters reset successfully', 'success');
};

const getTargetText = (announcement) => {
  const parts = [];

  if (announcement.targetYear === "all" || !announcement.targetYear) {
    parts.push("All Years");
  } else {
    parts.push(`Year ${announcement.targetYear}`);
  }

  if (announcement.targetType === "all") {
    parts.push("Universal");
  } else if (announcement.targetType === "faculty") {
    parts.push(`${announcement.targetFaculty}`);
  } else if (announcement.targetType === "course") {
    parts.push(`${announcement.targetCourse}`);
  }

  return parts.join(" • ");
};

const formatDate = (dateString) => {
  if (!dateString) return "Undated";
  const date = new Date(dateString);
  return date.toLocaleString("en-US", {
    month: "short",
    day: "numeric",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  });
};

const openImageModal = (imageUrl) => {
  modalImageUrl.value = imageUrl;
  showImageModal.value = true;
};

const closeImageModal = () => {
  showImageModal.value = false;
  modalImageUrl.value = "";
};

onMounted(() => {
  fetchAnnouncements();
});
</script>

<style scoped>
@import url("https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&display=swap");

.font-serif {
  font-family: "Playfair Display", serif;
}

/* Toast animations - Top Right */
.toast-enter-active, .toast-leave-active {
  transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}
.toast-enter-from {
  opacity: 0;
  transform: translateX(50px) translateY(-20px) scale(0.9);
}
.toast-leave-to {
  opacity: 0;
  transform: translateX(100px) translateY(-10px);
}
</style>