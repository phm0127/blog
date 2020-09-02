<template>
  <div>
      <home-banner style="margin-top: 0;margin-bottom: 0;"/>
          
      <home-scroll style="margin-top: 0;"/>
  </div>
</template>

<script>

import HomeScroll from '../components/Home/HomeScroll.vue'
import HomeBanner from '../components/Home/HomeBanner.vue'

export default {
    name:'Home',
    components : {
        
        HomeScroll,
        HomeBanner
    },
    mounted(){
        window.scrollTo(0,0);
    }
}
</script>

<style>

</style>