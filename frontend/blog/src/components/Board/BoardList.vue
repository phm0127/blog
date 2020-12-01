<template>
  <div>
      <div style="height:7vh;"></div>
        <div v-for="board of boardlist" v-bind:key=board.id>
                <div style="text-align:left; color : black;  margin-bottom : 10%; width: 100%; " class="viewer" @click="readBoard(board.id)">
                    <div class="board" style="position:relative">

                    <h1 style="padding-top : 20px; ">{{ board.title }}</h1>
                    <div style="margin-bottom: 20px;">
                        <i class="fas fa-calendar-alt"></i> <span style="margin-left : 5px;">{{ board.createdDate.substring(0,4)+"년 "+board.createdDate.substring(5,7)+"월 "+board.createdDate.substring(8,10)+"일" }}</span>
                    </div>
                
                    <viewer 
                
                    v-if="board.content != null"
                    :initialValue="board.content"
                    height="10em"
                    initialEditType="markdown"
                    previewStyle="vertical"
                    style="text-align:left; height: 10em; overflow:hidden;"
                    />
                    </div>
                    
                </div>
        </div>
      
      <v-pagination
      v-model="page"
      :length="totalpage"
      :page="pageIndex+1"
      :total-visible="10"
      
      ></v-pagination>
  </div>
</template>

<script>
import axios from 'axios'
import { Viewer } from '@toast-ui/vue-editor';

export default {
     components : {
        Viewer,
    },
    data() {
      return{
          pageIndex:0,
          totalpage:0,
          boardlist:[],
          page:1,
          

      }  
    },
    props:{
        type:Number,
        subCategoryID:Number
    },
    mounted() {
        window.scrollTo(0,0);
        if(this.subCategoryID<=0||this.subCategoryID==null){
            axios.get(this.$store.state.backAddr+'/board/allboardlist',{
                params:{
                    key:this.type,
                    pageIndex:this.pageIndex
                }
                })
                .then(res => {
                   this.boardlist=res.data.object.boards
                   
                   if((res.data.object.totalPage-1)>=0){
                       this.totalpage=Math.floor((res.data.object.totalPage-1)/5)+1
                   }else{
                       this.totalpage=0;
                   }
                   
                })
                .catch(err=>{
                    console.log(err)
                })

        }
        else{
            axios.get(this.$store.state.backAddr+'/board/boardlist',{
                params:{
                    subCategoryID:this.subCategoryID,
                    pageIndex:this.pageIndex
                }
                })
                .then(res => {
                   this.boardlist=res.data.object.boards
                   
                   if((res.data.object.totalPage-1)>=0){
                       this.totalpage=Math.floor((res.data.object.totalPage-1)/5)+1
                   }else{
                       this.totalpage=0;
                   }
                   
                })
                .catch(err=>{
                    console.log(err)
                })

        }
    },
    methods: {
        readBoard(id){
            window.scrollTo(0,0);
            if(this.type==0){
                this.$router.push("/portfolio/"+id)
            }else{
                this.$router.push("/blog/"+id)
            }
        },

        
    },
    watch:{
        page(newVal){
        window.scrollTo(0,0);
        if(this.subCategoryID<=0||this.subCategoryID==null){
            axios.get(this.$store.state.backAddr+'/board/allboardlist',{
                params:{
                    key:this.type,
                    pageIndex:newVal-1
                }
                })
                .then(res => {

                   this.boardlist=res.data.object.boards
                   this.pageIndex=newVal-1
                   if((res.data.object.totalPage-1)>=0){
                       this.totalpage=Math.floor((res.data.object.totalPage-1)/5)+1
                   }else{
                       this.totalpage=0;
                   }
                   
                })
                .catch(err=>{
                    console.log(err)
                })

        }
        else{
            axios.get(this.$store.state.backAddr+'/board/boardlist',{
                params:{
                    subCategoryID:this.subCategoryID,
                    pageIndex:newVal-1
                }
                })
                .then(res => {
                   this.boardlist=res.data.object.boards
                   this.pageIndex=newVal-1
                   if((res.data.object.totalPage-1)>=0){
                       this.totalpage=Math.floor((res.data.object.totalPage-1)/5)+1
                   }else{
                       this.totalpage=0;
                   }
                   
                })
                .catch(err=>{
                    console.log(err)
                })

        }
        }
    },

}
</script>

<style>
.board:hover{
    background-color:#e0e0e0;
    transition : 2s;
}
.board::after{
    content : '';
    position: absolute;
    height: 5em;
    top: 12em;
    width: 100%;
    left:0;
    background: linear-gradient(rgba(255, 255, 255, 0) 0px, #fff);
}
</style>