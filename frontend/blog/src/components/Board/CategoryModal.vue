<template>
  <div style="z-index:3; text-align: center; ">
      <div style="font-size:20px; margin-top : 20px; margin-bottom : 40px;">
        메인 카테고리
        <input type="radio" name="selectCategory" value="main" v-model="selectCategory" style="margin-right : 20px;"/>
        서브 카테고리
        <input type="radio" name="selectCategory" value="sub" v-model="selectCategory"/>
      </div>
      <div v-if="this.selectCategory=='main'">
      <input v-model="message" placeholder="추가할 메인 카테고리명을 입력해주세요." style="width : 70%; height : 3rem; font-size:20px">
        <v-btn
        absolute
        bottom
        color='#3F51B5'
        left
        right
        style="color:white; margin: 0 auto;"
        @click="addMainCategory"
        >
            추가하기
        </v-btn>
      </div>
    <!-- 


    -->
      <div v-if="this.selectCategory=='sub'">
          <div>
      <select v-model="selected" style="width:50%; height : 3rem; font-size:20px">
        <option selected disabled hidden value="default">메인 카테고리를 선택해주세요.</option>
        <option v-for="option in categoryOptions" v-bind:key="option.value" v-bind:value="option.id">
            {{ option.value }}
        </option>
        
    </select>

          </div>
      <input v-model="message" placeholder="추가할 서브 카테고리명을 입력해주세요" style="width : 70%; height : 3rem; font-size:20px">
      <v-btn
        absolute
        bottom
        color='#3F51B5'
        left
        right
        style="color:white; margin: 0 auto;"
        @click="addSubCategory"
        >
            추가하기
        </v-btn>
      </div>
      
  </div>
</template>

<script>
import axios from 'axios'



export default {
    data() {
        return {
            selectCategory:'main',
            message:'',
            category : '',
            selected:'default',
            categoryOptions:[
              
            ],
        }
    },
    components:{
       
    },
    mounted(){
        axios.get('http://localhost:8080/board/maincategory',{
            params:{
                key:0
            }
        })
        .then(res=>{
            let maincategoryArr = res.data.object;
            for(let i=0; i<maincategoryArr.length;i++){
                this.categoryOptions.push({
                    value : maincategoryArr[i].name,
                    id : maincategoryArr[i].id
                })
            } 
            console.log(maincategoryArr)
        })
    },

    methods:{
       addMainCategory : function(){
           axios.post('http://localhost:8080/board/maincategory',{
               name: this.message,
               order:this.categoryOptions.length,
               type:0
           })
           .then(()=>{
               
           })
           .error(()=>{
                this.$router.push({name:'Error',query:{msg:'메인 카테고리를 추가할 수 없습니다.'}})
           })
       },


       addSubCategory : function(){
           console.log(this.selected)
           axios.post('http://localhost:8080/board/subcategory',{
               mainCategoryID:this.selected,
               name: this.message,
               order:this.categoryOptions.length,
               type:0
           })
           .then(res=>{
               console.log(res);
           })
           .error(()=>{
                this.$router.push({name:'Error',query:{msg:'서브 카테고리를 추가할 수 없습니다.'}})
           })
           
       }
       
       
    }
}

</script>

<style>

</style>