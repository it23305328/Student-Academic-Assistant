import { createRouter, createWebHistory } from "vue-router";

// Lazy loading views
const StudentDashboard = () => import("../views/StudentDashboard.vue");
const AdminPanel = () => import("../views/AdminPanel.vue");
const Login = () => import("../views/Login.vue");
const Register = () => import("../views/Register.vue");
const Timetable = () => import("../views/Timetable.vue");
const Attendance = () => import("../views/Attendance.vue");
const Summarization = () => import("../views/Summarization.vue");
const Profile = () => import("../views/Profile.vue");
const GuestDashboard = () => import("../views/GuestDashboard.vue");
const GuestTimetable = () => import("../views/GuestTimetable.vue");
const GuestSummarization = () => import("../views/GuestSummarization.vue");
const GuestAttendance = () => import("../views/GuestAttendance.vue");
const OTPVerification = () => import("../views/OTPVerification.vue");

const Announcements = () => import("../views/rizvi/Announcement.vue");
const AdminAnnouncement = () => import("../views/admin/AdminAnnouncement.vue");
const StudyGroupFinder = () => import("../views/rizvi/StudyGroupFinder.vue");
const PeerTutoring = () => import("../views/rizvi/PeerTutoring.vue");

const routes = [
  {
    path: "/",
    redirect: () => {
      const role = localStorage.getItem("role");
      if (role === "ADMIN") return "/admin-panel";
      if (role === "STUDENT") return "/student-dashboard";
      return "/guest-dashboard";
    },
  },
  {
    path: "/dashboard",
    redirect: () => {
      const token = localStorage.getItem("token");
      return token ? "/student-dashboard" : "/guest-dashboard";
    },
  },
  {
    path: "/guest-dashboard",
    name: "GuestDashboard",
    component: GuestDashboard,
  },
  {
    path: "/guest-timetable",
    name: "GuestTimetable",
    component: GuestTimetable,
  },
  {
    path: "/guest-attendance",
    name: "GuestAttendance",
    component: GuestAttendance,
  },
  {
    path: "/guest-summarizer",
    name: "GuestSummarization",
    component: GuestSummarization,
  },
  {
    path: "/student-dashboard",
    name: "StudentDashboard",
    component: StudentDashboard,
    meta: { requiresAuth: true, role: "STUDENT" },
  },
  {
    path: "/admin-panel",
    name: "AdminPanel",
    component: AdminPanel,
    meta: { requiresAuth: true, role: "ADMIN" },
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
  },
  {
    path: "/timetable",
    name: "Timetable",
    component: Timetable,
    meta: { requiresAuth: true },
  },
  {
    path: "/attendance",
    name: "Attendance",
    component: Attendance,
    meta: { requiresAuth: true },
  },
  {
    path: "/summarization",
    name: "Summarization",
    component: Summarization,
    meta: { requiresAuth: true, role: "STUDENT" },
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    meta: { requiresAuth: true },
  },
  {
    path: "/otp-verify",
    name: "OTPVerification",
    component: OTPVerification,
  },

  //rizvi
  {
    path: "/attendance",
    name: "Attendance",
    component: Attendance,
    meta: { requiresAuth: true },
  },
  {
    path: "/announcements",
    name: "Announcements",
    component: Announcements,
    meta: { requiresAuth: true, role: "STUDENT" },
  },
  {
    path: "/study-group-finder",
    name: "StudyGroupFinder",
    component: StudyGroupFinder,
    meta: { requiresAuth: true, role: "STUDENT" },
  },
  {
    path: "/peer-tutoring",
    name: "PeerTutoring",
    component: PeerTutoring,
    meta: { requiresAuth: true, role: "STUDENT" },
  },
  {
    path: "/admin-announcements",
    name: "AdminAnnouncement",
    component: AdminAnnouncement,
    meta: { requiresAuth: true, role: "ADMIN" },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

// Navigation Guard
router.beforeEach((to, from) => {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (to.meta.requiresAuth && !token) {
    return "/login";
  }

  if (to.meta.role && to.meta.role !== role) {
    // Role mismatch: Redirect based on actual role
    if (role === "STUDENT") return "/student-dashboard";
    if (role === "ADMIN") return "/admin-panel";
    return "/login";
  }

  // Allow navigation
  return true;
});

export default router;
