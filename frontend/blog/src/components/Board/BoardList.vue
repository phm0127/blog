<template>
  <div></div>
</template>

<script>
import axios from 'axios'
export default {
    data() {
      return{
          pageIndex:0,
          
          boardlist:[],


      }  
    },
    props:{
        type:Number,
        subCategoryID:Number
    },
    mounted() {
        console.log(this.subCategoryID)
        axios.get('http://localhost:8080/board/allboardlist',{
            params:{
                key:this.type,
                pageIndex:this.pageIndex
            }
            })
            .then(res => {
               
               console.log(res.data.object)
            
            })
            .catch(err=>{
                console.log(err)
            })
    }
}
</script>

<style>

</style>