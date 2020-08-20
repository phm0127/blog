import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Intro from '../views/Home.vue'
import Navbar from '../components/Nav.vue'
import NotFoundPage from '../views/NotFoundPage.vue'
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name : "Main",
        component : Main
    },
    {
        path: '/1000min',
        name : "Intro",
        component : Intro
    },
    {
        path: '/nav',
        name : "Navbar",
        component : Navbar
    },
    {
        path: '*',
        name : "NotFoundPage",
        component : NotFoundPage
    }
    
]

const router = new VueRouter({
    mode : 'history',
    base : process.env.BASE_URL,
    routes
})

export default router