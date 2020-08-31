<template>
<div>
    <sidebar-menu :menu="menu" @item-click="onItemClick" :relative="true" style="float:left; height:100vh; z-index:1;"   />
    <div style="padding-left: 400px;" v-if="!isWrite">
    <h1 style="padding-top : 20px;">{{ title }}</h1>
    <viewer 
    v-if="viewerText != null"
    :initialValue="viewerText"
    height="91vh"
    initialEditType="markdown"
    previewStyle="vertical"
    style="text-align:left;"
    />
    
    </div>
    <edit-form v-if="isWrite" style="text-align:left;" :subCategoryID="subCategoryID" :boardID="boardID" :title="title" :viewerText="viewerText" />
    <v-speed-dial
        v-model="fab"
        absolute
        fixed
        bottom
        right
        direction="top"
        open-on-hover
        transition="slide-y-reverse-transition"
        v-if="role=='USER'"
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
import CategoryModal from '@/components/Board/CategoryModal.vue'
import { SidebarMenu } from 'vue-sidebar-menu'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';
import EditForm from '../components/Board/EditForm.vue'

export default {
name:'Portfolio',
    components : {
        SidebarMenu,
        Viewer,
        EditForm
    },
    props: {
        boardID: {
            type: Number,
            required: true
        }
    },
    mounted() {
        if(sessionStorage.getItem('role')!=null){
            this.role=sessionStorage.getItem('role')
        }

        axios.get('http://localhost:8080/board/board',{
            params:{
                boardID:Number(this.boardID)
            }
        })
        .then(res=>{
            this.viewerText=res.data.object.content
            this.title=res.data.object.title
            this.subCategoryID=Number(res.data.data)
            console.log(res.data.object.content)
            
        })
        

        
        axios.get('http://localhost:8080/board/maincategory',{
            params:{
                key:0
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
                        title: maincategoryArr[i].subcategories[j].name
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
    created(){
        
        
    },
    data() {
            return {
                role :'guest',
                subCategoryID:0,
                fab: false,
                title : '',
                viewerText:null,
                isWrite:false,
                menu: [
                    {
                        header: true,
                        title: '포트폴리오',
                        hiddenOnCollapse: true,
                        
                       
                    },
                    {
                        functionType : 0,
                        title: '전체 글 보기',
                        href:'/portfolio'
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
          }
          console.log(item)
          
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