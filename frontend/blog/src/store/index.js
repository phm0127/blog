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
        backaddr: 'localhost:8080',
        frontaddr : 'localhost',
        page:'/',
        pageIndex:1,
        token:'',
        //authToken: VueCookies.get('auth-token'),
        user:{
            email:'',
            name:'',
            nickname:'',
            access_token:'',
        },

        
        
    },
    getters: {
        isLoggedIn: state => !!state.authToken,
      },
    mutations:{
        SET_TOKEN(state, token) {
            state.authToken = token
            //VueCookies.set('auth-token', token)
        },
    },
    
    

})