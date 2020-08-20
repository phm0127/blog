import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';
// import axios from 'axios'
// import router from '../router'

Vue.use(Vuex)

export default new Vuex.Store({
    plugins: [
        createPersistedState()
    ],
    state:{
        address: 'localhost',
        page:'/',
        pageIndex:1,
    },
    

})