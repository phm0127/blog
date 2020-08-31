import Vue from 'vue'
import router from './router'
import App from './App.vue'
import AOS from "aos";
import store from './store'
import "aos/dist/aos.css";

import VueSidebarMenu from 'vue-sidebar-menu'
import 'vue-sidebar-menu/dist/vue-sidebar-menu.css'
import VModal from 'vue-js-modal'

import vuetify from './plugins/vuetify';
//import cors from 'cors'

Vue.use(VModal, { dynamic: true })

Vue.config.productionTip = false
//Vue.use(cors())
Vue.use(VueSidebarMenu)
new Vue({
  created() {
    AOS.init({ disable: "phone" });
  },

  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
