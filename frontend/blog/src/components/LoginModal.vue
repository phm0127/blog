<template>
  <div style="z-index:3; text-align: center; ">
        <h5>로그인</h5>
        
        <div id="naverIdLogin" style="">
        
        </div>
            <NaverLogin
            client-id="RnP9opRdc0Dm21kuldCI"
            callback-url="http://www.1000min.kr/agreement"
            v-bind:is-popup="false"
            v-bind:button-type="3"
            v-bind:button-height="50"
            button-color="green"
            :callbackFunction=callbackFunction
            />

        
        
        <div @click="close" style="position:absolute; margin:0 auto; bottom:20px; left:30%; width:120px; border-style:solid; height:25px;">
        <p style="font-size:20px; margin:auto;">
            취소
        </p>

        </div>
    </div>
</template>

<script>
import axios from 'axios'

import NaverLogin from 'vue-naver-login'

let callbackFunction = (status) => {
   
    if (status) {
    /* (5) 필수적으로 받아야하는 프로필 정보가 있다면 callback처리 시점에 체크 */
    var email = NaverLogin.user.getEmail();
    alert(email)
    if( email == undefined || email == null) {
      alert("이메일은 필수정보입니다. 정보제공을 동의해주세요.");
      /* (5-1) 사용자 정보 재동의를 위하여 다시 네아로 동의페이지로 이동함 */
      NaverLogin.reprompt();
      return;
    }
    window.location.replace("http://" + window.location.hostname + ( (location.port==""||location.port==undefined)?"":":" + location.port) + "/1000min");
  } else {
    console.log("callback 처리에 실패하였습니다.");
  }
}


export default {
    data() {
        return {
             naverLoginURL: 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=6yYgEm5_qFsTYNEyzwaO&redirect_uri=http://www.1000min.kr:8080/user/signin/naver/access&state=20200708',
        }
    },
    components:{
       NaverLogin,
   },
   mounted(){
       sessionStorage.setItem("lastVisit",this.$route.path)
       
   },
    methods:{
       callbackFunction,
       close(){
          
          this.$emit('close')
       },
       getuser(){
        this.$emit('close')
           axios.get('http://www.1000min.kr:8080/user/getuser',{
            // headers:{
            //     'jwt-auth-token':this.$store.state.token
            // },
            
            })
            .then(response => {
                
                this.$store.state.user=response.data.object
            
            })
            .catch(err=>{
                console.log(err)
            })
            
       },
       logout(){
        this.$emit('close')
           axios.get('http://www.1000min.kr:8080/user/logout',{
            headers:{
                "token":this.$store.state.authToken,
                 Authorization:this.token
            },
            
            })
            .then(response => {
                this.$store.state.user=response.data.object
                this.$router.go()
            })
            .catch(err=>{
                console.log(err)
            })
       },
       
    }
}

</script>

<style>

</style>