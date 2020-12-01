<template>
<v-app>
<v-top-naviation>
  <navbar/>
  <div style='float:right; z-index:3; width: 13vh; margin-top:0.5vh; text-align=center;'>
        <div style="position:fixed; z-index:3; padding: 0 auto; margin-top : 0.9vh;" v-if=!this.$store.state.isLogin><a v-on:click="modal_rendar()" style="padding: 0.6em 2em;
        font-size: 2vh; color:white; font-weight:bold;" >Login</a></div>
        
        <div style="position:fixed; z-index:3; padding: 0 auto; margin-top : 0.9vh;"  v-if=this.$store.state.isLogin>
          <a v-on:click="logout()" style="padding: 0.6em 2em; font-size: 2vh; color:white; font-weight:bold;">Logout</a>
        </div>
    </div>
  <v-snackbar
      :timeout="7000"
      :value="isMobile"
      absolute
      centered
      color="deep-purple accent-4"
      elevation="24"
    >
      이 블로그는 PC 화면에 최적화 되어있습니다.
      모바일로 이용 시 약간의 불편함이 있을 수 있습니다.
    </v-snackbar>
</v-top-naviation>
      
      <modals-container />
      
  <v-main>
      <router-view :key="$route.fullPath"/>
  </v-main>
</v-app>
  
    
</template>

<script>
import Navbar from '@/components/Nav.vue'
import LoginModal from '@/components/LoginModal.vue'
export default {
  name: 'App',
  data: () => {
    return {
      isMobile:false,
      is_show: false,
      isLogin : false,
      name : null,
    }
  },
  components:{
    Navbar,
  },
  computed:{
    
  },
  created(){
    var filter = "win16|win32|win64|mac|macintel"; 
    if ( navigator.platform ) {
        if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
          //mobile
          this.isMobile=true
          
          //this.$router.push({name:'Error',query:{msg:'현재, 모바일 환경은 지원하지 않습니다.'}})
        }
        else { 
          //pc
         
        }
    }
  },
  mounted(){
    var filter = "win16|win32|win64|mac|macintel"; 
    if ( navigator.platform ) {
        if ( filter.indexOf( navigator.platform.toLowerCase() ) < 0 ) {
          //mobile
          this.isMobile=true
          //this.$router.push({name:'Error',query:{msg:'현재, 모바일 환경은 지원하지 않습니다.'}})
        }
        else { 
          //pc
         
        }

      if(sessionStorage.getItem("name")!=null){
        this.name=sessionStorage.getItem("name")
        
        this.$store.state.isLogin=true;
      }else{
        this.$store.state.isLogin=false;
      }
    }
  },
  methods:{
    modal_rendar(){
              this.$modal.show(LoginModal,{
                    modal : this.$modal },{
                        name: 'dynamic-modal',
                        width : '330px',
                        height : '500px',
                        draggable: false,
            })
    },
    logout(){
      this.isLogin=false;
      sessionStorage.removeItem("userID")
      sessionStorage.removeItem("email")
      sessionStorage.removeItem("name")
      sessionStorage.removeItem("nickname")
      sessionStorage.removeItem("role")
      this.$store.state.isLogin=false;
      this.$router.go()
    }
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