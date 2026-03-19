import { createRouter, createWebHistory } from 'vue-router';

// Lazy loading views
const StudentDashboard = () => import('../views/StudentDashboard.vue');
const AdminPanel = () => import('../views/AdminPanel.vue');
const Login = () => import('../views/Login.vue');
const Register = () => import('../views/Register.vue');
const Timetable = () => import('../views/Timetable.vue');
const Attendance = () => import('../views/Attendance.vue');
const Summarization = () => import('../views/Summarization.vue');

const routes = [
    {
        path: '/',
        redirect: () => {
            const role = localStorage.getItem('role');
            if (role === 'ADMIN') return '/admin-panel';
            if (role === 'STUDENT') return '/student-dashboard';
            return '/login';
        }
    },
    {
        path: '/dashboard',
        redirect: '/student-dashboard'
    },
    {
        path: '/student-dashboard',
        name: 'StudentDashboard',
        component: StudentDashboard,
        meta: { requiresAuth: true, role: 'STUDENT' }
    },
    {
        path: '/admin-panel',
        name: 'AdminPanel',
        component: AdminPanel,
        meta: { requiresAuth: true, role: 'ADMIN' }
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/timetable',
        name: 'Timetable',
        component: Timetable,
        meta: { requiresAuth: true }
    },
    {
        path: '/attendance',
        name: 'Attendance',
        component: Attendance,
        meta: { requiresAuth: true }
    },
    {
        path: '/summarization',
        name: 'Summarization',
        component: Summarization,
        meta: { requiresAuth: true, role: 'STUDENT' }
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

// Navigation Guard
router.beforeEach((to, from) => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');

    if (to.meta.requiresAuth && !token) {
        return '/login';
    }

    if (to.meta.role && to.meta.role !== role) {
        // Role mismatch: Redirect based on actual role
        if (role === 'STUDENT') return '/student-dashboard';
        if (role === 'ADMIN') return '/admin-panel';
        return '/login';
    }

    // Allow navigation
    return true;
});

export default router;
