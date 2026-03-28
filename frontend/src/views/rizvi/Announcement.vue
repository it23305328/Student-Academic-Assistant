<template>
  <div class="dashboard-root">
    <!-- Sidebar -->
    <aside class="sidebar" :class="{ open: isSidebarOpen }">
      <div
        v-if="isSidebarOpen"
        class="sidebar-overlay"
        @click="isSidebarOpen = false"
      ></div>
      <div class="sidebar-header">
        <div class="brand-wrapper">
          <img
            src="../../assets//studentx_logo.png"
            alt="StudentX Logo"
            class="brand-logo"
          />
          <div class="brand-text">
            <h1 class="brand-title">StudentX</h1>
            <p class="brand-subtitle">Academic Support</p>
          </div>
        </div>
      </div>

      <nav class="sidebar-nav">
        <router-link to="/student-dashboard" class="nav-link">
          <span class="material-symbols-outlined">dashboard</span>
          <span>Overview</span>
        </router-link>

        <router-link to="/timetable" class="nav-link">
          <span class="material-symbols-outlined">calendar_month</span>
          <span>Timetable</span>
        </router-link>

        <router-link to="/attendance" class="nav-link">
          <span class="material-symbols-outlined">assignment_turned_in</span>
          <span>Attendance</span>
        </router-link>

        <router-link to="/summarization" class="nav-link">
          <span class="material-symbols-outlined">auto_awesome</span>
          <span>Summarization</span>
        </router-link>

        <router-link to="/announcements" class="nav-link">
          <span class="material-symbols-outlined">campaign</span>
          <span>Announcements</span>
        </router-link>

        <router-link to="/peer-tutoring" class="nav-link">
          <span class="material-symbols-outlined">groups</span>
          <span>Peer Tutoring</span>
        </router-link>

        <router-link to="/study-group-finder" class="nav-link">
          <span class="material-symbols-outlined">group_work</span>
          <span>Study Group</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="user-mini-card">
          <img
            :src="
              student.profilePic ||
              'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'
            "
            alt="User"
            class="nav-avatar"
          />
          <div class="nav-user-info">
            <p class="nav-user-name">{{ student.name || "Loading..." }}</p>
            <p class="nav-user-id">#{{ student.id || "N/A" }}</p>
          </div>
          <button @click="handleLogout" class="btn-logout-mini" title="Logout">
            <span class="material-symbols-outlined">logout</span>
          </button>
        </div>
      </div>
    </aside>

    <!-- Main Content Wrapper -->
    <div class="main-wrapper">
      <!-- App Header -->
      <header class="app-header">
        <button class="hamburger-btn" @click="isSidebarOpen = !isSidebarOpen">
          <span class="material-symbols-outlined">{{
            isSidebarOpen ? "close" : "menu"
          }}</span>
        </button>
        <div class="search-bar">
          <span class="material-symbols-outlined">search</span>
          <input
            type="text"
            v-model="searchQuery"
            placeholder="Search sessions or topics..."
          />
        </div>

        <div class="header-actions">
          <div class="notif-wrapper">
            <button class="action-btn">
              <span class="material-symbols-outlined">notifications</span>
            </button>
          </div>
          <button class="action-btn">
            <span class="material-symbols-outlined">help_outline</span>
          </button>
          <div class="divider"></div>
          <router-link to="/profile" class="user-profile">
            <img
              :src="
                student.profilePic ||
                'https://lh3.googleusercontent.com/aida-public/AB6AXuBZLMFloQFw3dHPJPKEU3rkHcCXUhI-PejPet3H4YP4B8NeytxNNK7zizEHWtbfP6tLCoST-1XN2WsL9kna8pz5YAPc_f-mcHLIrvpuVMA-T3k_esCMMJXIni3cVGivY_rrTBhAmG8J0vzr43evcjecQm-Np8gcxwwfnuknyQGaUxlHRen09dOamPt7M9Ks8rbXwFxRwkceKVtPXqxwMQBqkkmFnD3Y9cVFeW4-UxfulRlUS_3I23ehETw977EMAClzjBnHVkOGI7o'
              "
              alt="Profile"
            />
          </router-link>
        </div>
      </header>

      <!-- Content Canvas -->
      <main class="content-canvas">
        <!-- Announcements Banner -->
        <div
          v-if="recentAnnouncements.length > 0"
          class="announcements-banner bg-white border border-gray-200 rounded-2xl mx-6 mt-6 mb-4 p-4 flex items-center gap-4 cursor-pointer transition-all hover:shadow-md"
        >
          <div class="banner-icon text-3xl">
            <Megaphone class="w-8 h-8 text-[#A89060]" />
          </div>
          <div class="banner-content flex-1">
            <div
              class="banner-title text-[#A89060] text-[10px] font-black mb-1 tracking-[0.2em] uppercase"
            >
              LATEST ANNOUNCEMENTS
            </div>
            <div class="banner-messages flex flex-col gap-1.5">
              <div
                v-for="ann in recentAnnouncements.slice(0, 2)"
                :key="ann.id"
                class="banner-message flex items-center gap-3 text-sm cursor-pointer py-1 px-2 rounded-lg transition-all hover:bg-gray-50"
                @click="viewAnnouncement(ann.id)"
              >
                <span
                  class="message-topic font-bold text-[#1A1A1A] whitespace-nowrap"
                  >{{ ann.topic }}</span
                >
                <span class="message-preview text-gray-500 text-xs truncate">{{
                  truncateText(ann.content, 60)
                }}</span>
              </div>
            </div>
          </div>
          <router-link
            to="/announcements"
            class="view-all-btn bg-[#1A1A1A] text-white px-5 py-2 rounded-full text-[10px] font-bold no-underline transition-all hover:opacity-90 whitespace-nowrap"
          >
            View All →
          </router-link>
        </div>

        <div class="max-w-4xl mx-auto px-6 py-8">
          <div class="mb-10">
            <h1
              class="text-4xl md:text-5xl font-serif font-bold text-[#1A1A1A] mb-3"
            >
              Official Announcements
            </h1>
            <p class="text-sm text-gray-500 max-w-lg">
              Stay updated with important notices and curatorial updates from
              the academic administration.
            </p>
          </div>

          <div class="flex flex-col md:flex-row gap-4 mb-8">
            <div class="relative flex-1">
              <Search
                class="absolute left-4 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400"
              />
              <input
                type="text"
                v-model="searchQuery"
                placeholder="Search archive..."
                class="w-full pl-11 pr-4 py-3 bg-white border border-gray-200 rounded-xl text-sm text-[#1A1A1A] focus:outline-none focus:border-[#A89060] transition-all placeholder:text-gray-400 shadow-sm"
              />
            </div>
            <div class="flex gap-2 flex-wrap items-center">
              <button
                v-for="filter in filters"
                :key="filter.value"
                @click="currentFilter = filter.value"
                :class="[
                  'px-4 py-2 rounded-full text-[11px] font-bold tracking-wider uppercase transition-all duration-200 whitespace-nowrap border',
                  currentFilter === filter.value
                    ? 'bg-[#1A1A1A] text-white border-[#1A1A1A]'
                    : 'bg-white text-gray-500 border-gray-200 hover:border-gray-300',
                ]"
              >
                {{ filter.label }}
              </button>
            </div>
          </div>

          <div>
            <div
              v-if="loading"
              class="bg-white border border-gray-200 rounded-3xl p-20 text-center shadow-sm"
            >
              <div
                class="inline-block w-8 h-8 border-2 border-gray-100 border-t-[#A89060] rounded-full animate-spin mb-4"
              ></div>
              <p
                class="text-gray-400 text-xs font-bold tracking-widest uppercase"
              >
                Fetching Data...
              </p>
            </div>

            <div
              v-else-if="filteredAnnouncements.length === 0"
              class="bg-white border border-gray-200 rounded-3xl p-20 text-center shadow-sm"
            >
              <Megaphone class="w-12 h-12 text-gray-200 mx-auto mb-4" />
              <h3 class="text-lg font-serif font-bold text-[#1A1A1A] mb-1">
                Archive Empty
              </h3>
              <p class="text-gray-400 text-sm">
                No announcements match your current filter.
              </p>
            </div>

            <div v-else class="space-y-4">
              <div
                v-for="announcement in filteredAnnouncements"
                :key="announcement.id"
                class="group bg-white border border-gray-200 rounded-2xl transition-all duration-300 hover:shadow-xl hover:-translate-y-1 cursor-pointer overflow-hidden"
                @click="toggleReadMore(announcement.id)"
              >
                <div class="p-6">
                  <div class="flex items-start gap-5">
                    <div
                      class="flex-shrink-0 w-12 h-12 bg-[#F8F9FA] rounded-xl flex items-center justify-center group-hover:bg-[#A89060]/10 transition-colors"
                    >
                      <Megaphone class="w-6 h-6 text-[#A89060]" />
                    </div>

                    <div class="flex-1 min-w-0">
                      <div
                        class="flex flex-wrap items-baseline justify-between gap-3 mb-2"
                      >
                        <h2
                          class="text-xl font-serif font-bold text-[#1A1A1A] truncate pr-2"
                        >
                          {{ announcement.topic }}
                        </h2>
                        <div class="flex gap-2 flex-shrink-0">
                          <span
                            class="inline-flex items-center gap-1.5 bg-[#F8F9FA] text-gray-600 px-3 py-1 rounded-full text-[10px] font-bold border border-gray-100 uppercase tracking-tighter"
                          >
                            <Users class="w-3 h-3" />
                            {{ getTargetShortText(announcement) }}
                          </span>
                          <span
                            class="inline-flex items-center gap-1.5 bg-[#F8F9FA] text-gray-600 px-3 py-1 rounded-full text-[10px] font-bold border border-gray-100 uppercase tracking-tighter"
                          >
                            <Calendar class="w-3 h-3" />
                            {{
                              formatDateShort(
                                announcement.datetime || announcement.createdAt
                              )
                            }}
                          </span>
                        </div>
                      </div>

                      <!-- Content area with proper structure - matches admin side display -->
                      <div class="text-gray-600 text-sm leading-relaxed mb-4">
                        <div
                          :class="{
                            'line-clamp-2': !expandedAnnouncements.includes(
                              announcement.id
                            ),
                          }"
                          class="whitespace-pre-wrap"
                        >
                          {{ announcement.content }}
                        </div>
                      </div>

                      <div
                        v-if="expandedAnnouncements.includes(announcement.id)"
                        class="mt-4 pt-6 border-t border-gray-100 animate-fade-in"
                      >
                        <div v-if="announcement.imageUrl" class="mb-4">
                          <img
                            :src="announcement.imageUrl"
                            alt="Announcement image"
                            @click.stop="openImageModal(announcement.imageUrl)"
                            class="w-full max-h-64 rounded-xl cursor-pointer object-cover shadow-sm transition-opacity hover:opacity-90"
                          />
                        </div>
                      </div>

                      <button
                        @click.stop="toggleReadMore(announcement.id)"
                        class="text-[#A89060] text-[11px] font-black uppercase tracking-widest hover:text-[#1A1A1A] transition-colors flex items-center gap-1"
                      >
                        {{
                          expandedAnnouncements.includes(announcement.id)
                            ? "Show less ↑"
                            : "Read details →"
                        }}
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </main>

      <GPAModal :isOpen="isGpaModalOpen" @close="isGpaModalOpen = false" />

      <div
        class="toast-container fixed top-6 right-6 z-[9999] flex flex-col gap-3 w-80"
      >
        <transition-group name="toast">
          <div
            v-for="toast in notificationToasts"
            :key="toast.id"
            class="toast-item relative bg-white rounded-2xl p-5 shadow-2xl border border-gray-100 overflow-hidden"
            :class="{
              'border-l-4 border-l-[#1A1A1A]': toast.type === 'lecture',
              'border-l-4 border-l-[#A89060]': toast.type === 'announcement',
              'border-l-4 border-l-red-600': toast.type === 'critical',
            }"
          >
            <div class="toast-content flex items-start gap-4 relative z-10">
              <div
                class="toast-icon w-10 h-10 rounded-xl flex items-center justify-center bg-gray-50"
              >
                <Clock
                  v-if="toast.type === 'lecture'"
                  class="w-5 h-5 text-[#1A1A1A]"
                />
                <Megaphone
                  v-else-if="toast.type === 'announcement'"
                  class="w-5 h-5 text-[#A89060]"
                />
                <AlertCircle v-else class="w-5 h-5 text-red-600" />
              </div>
              <div class="toast-body flex-1">
                <p
                  class="toast-title text-[10px] font-black text-[#A89060] uppercase tracking-widest mb-1"
                >
                  {{ toast.title }}
                </p>
                <p
                  class="toast-message text-xs text-gray-700 font-medium leading-relaxed"
                >
                  {{ toast.message }}
                </p>
              </div>
              <button
                @click="dismissToast(toast.id)"
                class="toast-close text-gray-300 hover:text-gray-900 transition-colors"
              >
                &times;
              </button>
            </div>
          </div>
        </transition-group>
      </div>
    </div>

    <div
      v-if="modalImage"
      class="fixed inset-0 bg-[#1A1A1A]/95 flex items-center justify-center z-[1000] cursor-pointer"
      @click="closeImageModal"
    >
      <div class="relative max-w-[90%] max-h-[90%]" @click.stop>
        <img
          :src="modalImage"
          alt="Full size announcement"
          class="max-w-full max-h-[90vh] rounded-2xl shadow-2xl border border-white/10"
        />
        <button
          class="absolute -top-12 right-0 bg-white/10 text-white w-10 h-10 rounded-full flex items-center justify-center hover:bg-red-600 transition-colors"
          @click="closeImageModal"
        >
          ×
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from "vue";
import { useRouter } from "vue-router";
import {
  Search,
  Bell,
  Clock,
  Megaphone,
  AlertCircle,
  Info,
  Check,
  Users,
  Calendar,
} from "lucide-vue-next";
import announcementService from "../../services/announcementService";
import GPAModal from "../../components/GPAModal.vue";
import studentService from "../../services/studentService";

const router = useRouter();
const isGpaModalOpen = ref(false);
const notificationsList = ref([]);
const isNotifDropdownOpen = ref(false);
const notificationToasts = ref([]);
const notifiedIds = new Set();

const announcements = ref([]);
const loading = ref(true);
const searchQuery = ref("");
const currentFilter = ref("all");
const expandedAnnouncements = ref([]);
const modalImage = ref(null);
const recentAnnouncements = ref([]);
const student = ref({ name: "", id: "" });
const studentId = ref(null);
const isSidebarOpen = ref(false);

const studentInfo = ref({
  year: null,
  semester: null,
  course: null,
});

const filters = [
  { label: "All", value: "all" },
  { label: "Academic Year", value: "year" },
  { label: "Faculty", value: "faculty" },
  { label: "General", value: "everyone" },
];

const checkAuth = () => {
  const storedId = localStorage.getItem("studentId");
  if (!storedId) {
    router.push("/login");
    return false;
  }
  studentId.value = parseInt(storedId);
  return true;
};

const fetchStudentData = async () => {
  if (!studentId.value) return;
  try {
    const response = await studentService.getStudentById(studentId.value);
    if (response.data) student.value = response.data;
  } catch (e) {
    console.error("Error fetching student data:", e);
  }
};

const fetchAnnouncements = async () => {
  loading.value = true;
  try {
    const response = await announcementService.getAllAnnouncements();
    announcements.value = response.data || [];

    const userData = JSON.parse(localStorage.getItem("userData") || "{}");
    studentInfo.value = {
      year: userData.academicYear || 1,
      semester: userData.semester || 1,
      course: userData.course || "IT",
    };

    const filtered = filterAnnouncementsForStudent(announcements.value);
    recentAnnouncements.value = filtered
      .sort((a, b) => {
        const dateA = new Date(a.datetime || a.createdAt);
        const dateB = new Date(b.datetime || b.createdAt);
        return dateB - dateA;
      })
      .slice(0, 3);

    checkNewAnnouncements();
  } catch (error) {
    console.error("Failed to fetch announcements:", error);
  } finally {
    loading.value = false;
  }
};

const filterAnnouncementsForStudent = (anns) => {
  return anns.filter((ann) => {
    const yearMatch =
      !ann.targetYear ||
      ann.targetYear === "all" ||
      ann.targetYear === studentInfo.value.year;
    const semesterMatch =
      !ann.targetSemester ||
      ann.targetSemester === "all" ||
      ann.targetSemester === studentInfo.value.semester;
    const facultyMatch =
      !ann.targetFaculty ||
      ann.targetFaculty === "all" ||
      ann.targetFaculty === studentInfo.value.course;
    return yearMatch && semesterMatch && facultyMatch;
  });
};

const checkNewAnnouncements = () => {
  announcements.value.forEach((ann) => {
    const annId = `announcement-${ann.id}`;
    if (!notifiedIds.has(annId)) {
      const annDate = new Date(ann.datetime || ann.createdAt);
      const now = new Date();
      if (now - annDate < 3600000) {
        triggerNotification({
          type: "announcement",
          title: "Curatorial Update",
          message: `${ann.topic}`,
        });
        notifiedIds.add(annId);
      }
    }
  });
};

const filteredAnnouncements = computed(() => {
  let filtered = announcements.value;
  if (searchQuery.value) {
    filtered = filtered.filter(
      (ann) =>
        ann.topic.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        ann.content.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }
  if (currentFilter.value !== "all") {
    filtered = filtered.filter((ann) => {
      if (currentFilter.value === "everyone")
        return !ann.targetYear || ann.targetYear === "all";
      if (currentFilter.value === "year")
        return ann.targetYear === studentInfo.value.year;
      if (currentFilter.value === "faculty")
        return ann.targetFaculty === studentInfo.value.course;
      return true;
    });
  }
  return filtered.sort(
    (a, b) =>
      new Date(b.datetime || b.createdAt) - new Date(a.datetime || a.createdAt)
  );
});

const getTargetShortText = (ann) => {
  if (ann.targetYear === "all" || !ann.targetYear) return "Open";
  return `Y${ann.targetYear}${
    ann.targetSemester ? " S" + ann.targetSemester : ""
  }`;
};

const formatDateShort = (dateString) => {
  if (!dateString) return "";
  const date = new Date(dateString);
  return date.toLocaleDateString("en-US", { month: "short", day: "numeric" });
};

const toggleReadMore = (id) => {
  if (expandedAnnouncements.value.includes(id)) {
    expandedAnnouncements.value = expandedAnnouncements.value.filter(
      (i) => i !== id
    );
  } else {
    expandedAnnouncements.value.push(id);
  }
};

const openImageModal = (imageUrl) => {
  modalImage.value = imageUrl;
  document.body.style.overflow = "hidden";
};

const closeImageModal = () => {
  modalImage.value = null;
  document.body.style.overflow = "";
};

const viewAnnouncement = (id) => router.push("/announcements");

const truncateText = (text, length) => {
  if (!text) return "";
  return text.length <= length ? text : text.substring(0, length) + "...";
};

const triggerNotification = (details) => {
  const id = Date.now();
  notificationToasts.value.push({ ...details, id });
  setTimeout(() => dismissToast(id), 8000);
};

const dismissToast = (id) => {
  notificationToasts.value = notificationToasts.value.filter(
    (t) => t.id !== id
  );
};

const handleLogout = () => {
  localStorage.removeItem("studentId");
  localStorage.removeItem("token");
  router.push("/login");
};

onMounted(() => {
  if (checkAuth()) {
    fetchStudentData();
    fetchAnnouncements();
  }
  const interval = setInterval(checkNewAnnouncements, 60000);
  onUnmounted(() => clearInterval(interval));
});
</script>

<style scoped>
/* Base Styles */
.material-symbols-outlined {
  font-variation-settings: "FILL" 0, "wght" 400, "GRAD" 0, "opsz" 24;
}

.dashboard-root {
  font-family: "Manrope", sans-serif;
  background-color: #fafaf5;
  color: #2e342d;
  min-height: 100vh;
  display: flex;
}

/* Sidebar - Exact match with StudentDashboard */
.sidebar {
  width: 260px;
  background-color: #ecefe7;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  flex-direction: column;
  padding: 32px 0;
  z-index: 50;
  border-right: 1px solid rgba(0, 0, 0, 0.05);
}

.sidebar-header {
  padding: 0 24px;
  margin-bottom: 40px;
}
.brand-wrapper {
  display: flex;
  align-items: center;
  gap: 12px;
}
.brand-logo {
  height: 60px;
  width: auto;
  object-fit: contain;
}
.brand-title {
  font-size: 20px;
  font-weight: 800;
  color: #4e6073;
  margin: 0;
  line-height: 1;
}
.brand-subtitle {
  font-size: 14px;
  font-weight: 600;
  color: #5a6159;
  letter-spacing: 0.05em;
  margin-top: 4px;
}

.sidebar-nav {
  padding: 0 16px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.nav-link {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 12px 24px;
  border-radius: 16px;
  text-decoration: none;
  color: #5b6063;
  font-weight: 600;
  transition: all 0.2s ease;
}
.nav-link:hover,
.nav-link.router-link-active {
  background-color: rgba(255, 255, 255, 0.4);
}
.nav-link.router-link-exact-active {
  background-color: #ffffff;
  color: #4e6073;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}

.sidebar-footer {
  margin-top: auto;
  padding: 24px 16px;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
}
.user-mini-card {
  background-color: #ffffff;
  padding: 12px 16px;
  border-radius: 20px;
  display: flex;
  align-items: center;
  gap: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.03);
}
.nav-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
.nav-user-info {
  flex: 1;
  min-width: 0;
}
.nav-user-name {
  font-size: 13px;
  font-weight: 700;
  margin: 0;
  color: #2e342d;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.nav-user-id {
  font-size: 11px;
  font-weight: 600;
  margin: 0;
  color: #5a6159;
}
.btn-logout-mini {
  background: none;
  border: none;
  color: #9f403d;
  cursor: pointer;
  padding: 8px;
  border-radius: 12px;
  display: flex;
}
.btn-logout-mini:hover {
  background-color: rgba(159, 64, 61, 0.05);
}

/* Main Wrapper */
.main-wrapper {
  margin-left: 260px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* Header */
.app-header {
  padding: 24px 48px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: rgba(243, 244, 238, 0.82);
  backdrop-filter: blur(20px);
  position: sticky;
  top: 0;
  z-index: 40;
}
.search-bar {
  position: relative;
  width: 380px;
}
.search-bar .material-symbols-outlined {
  position: absolute;
  left: 16px;
  top: 50%;
  transform: translateY(-50%);
  color: #5a6159;
  font-size: 20px;
}
.search-bar input {
  width: 100%;
  background-color: rgba(222, 228, 218, 0.5);
  border: none;
  padding: 10px 16px 10px 48px;
  border-radius: 99px;
  font-family: inherit;
  font-size: 14px;
  outline: none;
  transition: box-shadow 0.2s;
}
.search-bar input:focus {
  box-shadow: 0 0 0 2px rgba(78, 96, 115, 0.2);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 24px;
}
.action-btn {
  background: none;
  border: none;
  color: #4e6073;
  cursor: pointer;
  position: relative;
  padding: 8px;
  border-radius: 50%;
  transition: background 0.2s;
}
.action-btn:hover {
  background-color: #dee4da;
}
.divider {
  width: 1px;
  height: 32px;
  background: rgba(0, 0, 0, 0.1);
  margin: 0 8px;
}
.user-profile {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid rgba(78, 96, 115, 0.1);
  display: block;
  cursor: pointer;
  transition: 0.2s;
}
.user-profile:hover {
  border-color: #4e6073;
  transform: scale(1.05);
}
.user-profile img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* Content Canvas */
.content-canvas {
  padding: 0;
  flex: 1;
  overflow-y: auto;
}

/* Toast Animation */
.toast-enter-active,
.toast-leave-active {
  transition: all 0.5s cubic-bezier(0.19, 1, 0.22, 1);
}
.toast-enter-from {
  opacity: 0;
  transform: translateY(-20px) scale(0.9);
}
.toast-leave-to {
  opacity: 0;
  transform: translateX(100px);
}

/* Custom Animations */
.animate-spin {
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to {
    transform: rotate(360deg);
  }
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

.animate-fade-in {
  animation: fadeIn 0.3s ease-out;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.whitespace-pre-wrap {
  white-space: pre-wrap;
  word-wrap: break-word;
  line-height: 1.6;
}

.content-canvas::-webkit-scrollbar {
  width: 5px;
}
.content-canvas::-webkit-scrollbar-thumb {
  background: #e5e7eb;
  border-radius: 10px;
}

/* Responsive */
@media (max-width: 1024px) {
  .sidebar {
    transform: translateX(-100%);
    transition: transform 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    z-index: 2000;
  }
  .sidebar.open {
    transform: translateX(0);
  }
  .sidebar-overlay {
    position: fixed;
    inset: 0;
    background: rgba(0, 0, 0, 0.5);
    backdrop-filter: blur(4px);
    z-index: -1;
  }
  .main-wrapper {
    margin-left: 0;
    width: 100%;
  }
  .app-header {
    padding: 12px 16px;
    gap: 12px;
  }
  .hamburger-btn {
    display: flex !important;
  }
  .search-bar {
    display: none;
  }
  .header-actions {
    gap: 8px;
  }
  .content-canvas {
    padding: 16px;
  }
  .announcements-banner {
    margin: 0 0 16px 0;
    flex-wrap: wrap;
  }
  .banner-content {
    order: 2;
    width: 100%;
  }
  .view-all-btn {
    order: 1;
  }
}

.hamburger-btn {
  display: none;
  background: none;
  border: none;
  color: #4e6073;
  cursor: pointer;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
}

/* Font imports */
@import url("https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700&family=Inter:wght@400;500;700;900&family=Manrope:wght@400;500;600;700;800&display=swap");

.font-serif {
  font-family: "Playfair Display", serif;
}

.font-sans {
  font-family: "Inter", sans-serif;
}
</style>
