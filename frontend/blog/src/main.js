import Vue from 'vue'
import router from './router'
import App from './App.vue'
import AOS from "aos";
import store from './store'
import "aos/dist/aos.css";



Vue.config.productionTip = false

new Vue({
  created() {
    AOS.init({ disable: "phone" });
  },
  router,
  store,
  render: h => h(App),
}).$mount('#app')
