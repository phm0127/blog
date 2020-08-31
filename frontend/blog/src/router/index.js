import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Agreement from '../views/Agreement.vue'
import Intro from '../views/Home.vue'
import Portfolio from '../views/Portfolio.vue'
import PortfolioViewer from '../views/PortfolioViewer.vue'
import NotFoundPage from '../views/NotFoundPage.vue'
import Error from '../views/Error.vue'
Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name : "Main",
        component : Main
    },
    {
        path: '/agreement',
        name : "Agreement",
        component : Agreement
    },
    {
        path: '/1000min',
        name : "Intro",
        component : Intro
    },
    
    {
        path: '/portfolio',
        name : "Portfolio",
        component : Portfolio
    },
    {
        path: '/portfolio/:boardID',
        name : "Portfolioviewer",
        props: true,
        component : PortfolioViewer
    },
    {
        path: '/error',
        name : "Error",
        component : Error
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