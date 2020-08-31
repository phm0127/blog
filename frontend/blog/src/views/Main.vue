<template>
    <div>

  <main-logo style="display : flex; margin : 0 auto; left:0; right:0; "/>
  <p style="text-align : center; font-size:8rem; font-weight:bold;"><span style="font-size:9rem; font-weight:bold; ">1000min</span>'s <span style="color:#00bb00; font-weight:bold;">Dev</span>LOG</p>
  <div style="width:100%; text-align:center;">
      <p style="font-size:3rem;" >contact me by</p>
  <div style="display: inline-block;">
       <a href="https://github.com/phm0127">
            <i class="fab fa-github" style="font-size : 100px; color:#222222;"></i>
       </a>
       <a href="https://www.instagram.com/1000min_">
            <i class="fab fa-instagram" style="font-size : 100px; color:#e1003e; padding-left:150px;"></i>
       </a>
       <a href="https://www.linkedin.com/in/%ED%98%95%EB%AF%BC-%EB%B0%95-0148091aa/">
            <i class="fab fa-linkedin" style="font-size : 100px; color:#0077b4; padding-left:150px;"></i>
       </a>
       <a class="social-link rounded-circle text-white mr-3" href="mailto:phm0127@gmail.com?subject=">            
            <i class="fas fa-envelope-square" style="font-size : 100px; color:#333333; padding-left:150px;"></i>
       </a>
      
  </div>
  </div>
    </div>
</template>

<script>
import MainLogo from '../components/Main/MainLogo.vue'
// import axios from 'axios'
//import '@/assets/css/fontawesome.css'

export default {
    name:'Main',
    components : {
        
        MainLogo,
        
    },
    
    date() {
        return {
           token:'',
            
        }
    },
    created() {
        
        
    }
}
</script>

<style>

</style>