<template>
<div>
    <sidebar-menu :menu="menu" @item-click="onItemClick" :relative="true" style="float:left; height:170vh; z-index:1;"   />
    <board-list v-if="!isWrite" :type="1" v-bind:subCategoryID="subCategoryID" />
    <write-form v-if="isWrite" style="text-align:left;" :type="1"/>
   
    <v-speed-dial
        v-model="fab"
        absolute
        fixed
        bottom
        right
        direction="top"
        open-on-hover
        transition="slide-y-reverse-transition"
        v-if="role=='ADMIN'"
      >
      
        <template v-slot:activator>
          <v-btn
            v-model="fab"
            color="blue darken-2"
            dark
            fab
          >
            <v-icon v-if="fab">mdi-close</v-icon>
            <v-icon v-else>mdi-account-circle</v-icon>
          </v-btn>
        </template>
        <v-btn
          fab
          dark
          small
          color="green"
          @click="write"
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
        <v-btn
          fab
          dark
          small
          color="indigo"
          @click="modal_render"
        >
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn
          fab
          dark
          small
          color="red"
        >
          <v-icon>mdi-delete</v-icon>
        </v-btn>
      </v-speed-dial>
</div>
  
</template>

<script>
import axios from 'axios'
import WriteForm from '../components/Board/WriteForm.vue'
import BoardList from '../components/Board/BoardList.vue'
import CategoryModal from '@/components/Board/CategoryModal.vue'
import { SidebarMenu } from 'vue-sidebar-menu'
export default {
name:'Blog',
    components : {
        SidebarMenu,
        WriteForm,
        BoardList,
    },
    props:{
        type:Number,
        subCategoryID:Number
    },
    created(){
      if(this.subCategoryID==null){
        this.subCategoryID=0
      }
      
    },
    mounted() {
        window.scrollTo(0,0);
      
        if(sessionStorage.getItem('role')!=null){
            this.role=sessionStorage.getItem('role')
        }

        axios.get('http://www.1000min.kr:8080/board/maincategory',{
            params:{
                key:1
            }
        })
        .then(res=>{
            let maincategoryArr = res.data.object;
            for(let i=0; i<maincategoryArr.length;i++){
                let tempChild=[]
                
                for(let j=0;j<maincategoryArr[i].subcategories.length;j++){
                    
                    tempChild.push({
                        functionType : 2,
                        subCategoryID: maincategoryArr[i].subcategories[j].id,
                        title: maincategoryArr[i].subcategories[j].name,
                        
                    })
                }
                this.menu.push({
                    functionType : 1,
                    title: maincategoryArr[i].name,
                    child: tempChild
                })
            } 
            
        })
        
    },
    data() {
            return {
                role :'guest',
                isWrite: false,
                pageIndex:0,
                fab: false,
                menu: [
                    {
                        header: true,
                        title: '블로그',
                        hiddenOnCollapse: true,
                        
                       
                    },
                    {
                        functionType : 0,
                        title: '전체 글 보기',
                        
                    },
                    
                ]
            }
        },
        computed: {
    activeFab () {
      switch (this.tabs) {
        case 'one': return { class: 'purple', icon: 'account_circle' }
        case 'two': return { class: 'red', icon: 'edit' }
        case 'three': return { class: 'green', icon: 'keyboard_arrow_up' }
        default: return {}
      }
    },
  },

  watch: {
    top (val) {
      this.bottom = !val
    },
    right (val) {
      this.left = !val
    },
    bottom (val) {
      this.top = !val
    },
    left (val) {
      this.right = !val
    },
    

  },
  methods:{
      write(){
          this.isWrite=true;
      },
      modal_render(){
              this.$modal.show(CategoryModal,{
                    type : "1",
                    modal : this.$modal },{
                        name: 'dynamic-modal',
                        width : '500px',
                        height : '300px',
                        draggable: true,
            })
    },
      onItemClick(event, item) {
          if(item.functionType==0){
              this.isWrite=false;
              this.subCategoryID=0;
              this.pageIndex=0;
              this.$router.push("/blogs/"+0)
          }
          else if(item.functionType==2){
            this.subCategoryID=item.subCategoryID
            this.pageIndex=0;
            this.isWrite=false;
            this.$router.push("/blogs/"+item.subCategoryID)
            
          } 
          
      },
      
  }
}
</script>

<style>
html, body {

    margin: 0;
    padding :0;

}

#create .v-speed-dial {
  position: absolute;
}

#create .v-btn--floating {
  position: relative;
}


</style>