import { createRouter, createWebHistory } from 'vue-router';

// Lazy loading views
const Dashboard = () => import('../views/Dashboard.vue');
const Login = () => import('../views/Login.vue');
const Register = () => import('../views/Register.vue');
const Timetable = () => import('../views/Timetable.vue');
const Attendance = () => import('../views/Attendance.vue');

const routes = [
    {
        path: '/',
        redirect: '/dashboard'
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard
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
        component: Timetable
    },
    {
        path: '/attendance',
        name: 'Attendance',
        component: Attendance
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes
});

export default router;
