<template>
  <div></div>
</template>

<script>
import axios from 'axios'

export default {
    name:'Agreement',
    created () {
    this.$store.commit('SET_TOKEN', this.$route.query.token)
    //this.access_token = this.$route.query.token
    this.$store.state.token=this.$route.query.token
    let curURL = document.location.href
    let token = curURL.substring(curURL.indexOf('=')+1,curURL.indexOf('&'))
    

    axios({
      method: 'post',
      url : this.$store.state.backAddr+'/user/naverlogin',
      data : {
        token
        //Authorization : 'Bearer ' + token
      },
      headers : {
        Authorization : 'Bearer ' + token
      }
    }).then(response => {
        //this.$store.state.user=response.data.object
        sessionStorage.setItem("userID",response.data.object.id)
        sessionStorage.setItem("name",response.data.object.name)
        sessionStorage.setItem("email",response.data.object.email)
        sessionStorage.setItem("nickname",response.data.object.nickname)
        sessionStorage.setItem("role",response.data.object.role)
        
        localStorage.setItem("pageIndex",1)
        this.$store.state.isLogin=true;
        if(sessionStorage.getItem("lastVisit")!=null){
          this.$router.push(sessionStorage.getItem("lastVisit"))
        }else{
          this.$router.push('/')

        }

      }).catch(()=>{
          
          this.$router.push({name:'Error',query:{msg:'로그인 오류'}})
      })


    }

}
</script>

<style>

</style>