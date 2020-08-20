<template>
<v-app>
<v-top-naviation>
  <navbar/>
  <div style="height : 4em;"></div>
</v-top-naviation>
  <v-main>
      <router-view :key="$route.fullPath"/>
  </v-main>
</v-app>
  
    
</template>

<script>

import Navbar from '@/components/Nav.vue'
export default {
  name: 'App',
  components:{
    Navbar,

  }
  
  
}
import '@/assets/css/Main.css'
</script>

<style>
#app {
 font-family: 'Do Hyeon', sans-serif;
 font-family: 'Noto Sans KR', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  

}
</style>
