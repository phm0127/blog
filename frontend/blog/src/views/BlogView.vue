<template>
<div>
    <sidebar-menu :menu="menu" @item-click="onItemClick" :relative="true" style="float:left; height:170vh; z-index:1;"   />
    <div style="padding-left: 400px; text-align:left; color : black;" v-if="!isWrite">
      <h1 style="padding-top : 20px; ">{{ title }}</h1>
      <div style="margin-bottom: 20px;">
      <i class="fas fa-calendar-alt"></i> <span style="margin-left : 5px;">{{ createdDate }}</span>
      </div>
      
      <viewer 
      v-if="viewerText != null"
      :initialValue="viewerText"
      height="91vh"
      initialEditType="markdown"
      previewStyle="vertical"
      style="text-align:left;"
      />
      <div style="margin-top:80px; text-align:left;">
      <p>댓글</p>
      
       <template v-for="(item, index) in commentlist">
          <v-list-item :key="item.title">
            <template v-slot:default="{  }">
              <v-list-item-content v-if="!item.editstate">
                <v-list-item-title v-text="item.title" style="color : #3F51B5; font-weight:bold;" v-if="item.uid==1"></v-list-item-title>
                <v-list-item-title v-text="item.title"  style="font-weight:bold;" v-if="item.uid!=1"></v-list-item-title>
                <v-list-item-content class="text--primary" v-text="item.headline" expand></v-list-item-content>
                <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
                
              </v-list-item-content>
              <v-list-item-content v-if="item.editstate">
                <v-list-item-title v-text="item.title" style="color : #3F51B5; font-weight:bold;" v-if="item.uid==1"></v-list-item-title>
                <v-list-item-title v-text="item.title"  style="font-weight:bold;" v-if="item.uid!=1"></v-list-item-title>
                <v-list-item-content class="text--primary" v-text="item.headline" expand></v-list-item-content>
                <v-list-item-subtitle v-text="item.subtitle"></v-list-item-subtitle>
                <div>
                  <v-textarea
                    v-model="item.headline"
                    :rules="rules"
                    counter="1000"
                    label="수정"
                    outlined
                    style="margin-top:50px; width:100%;"
                    
                  ></v-textarea>
                  
                </div> 
              </v-list-item-content>

              <v-list-item-action>
                <v-list-item-action-text v-text="item.action"></v-list-item-action-text>
                <div v-if="item.editstate" style="float:right;">
                  <v-btn class="ma-2" tile outlined color="success"  @click.stop="editcomment(item.cid,item.headline)" >
                      <v-icon left bottom>mdi-pencil</v-icon> 수정
                  </v-btn>
                  <v-btn class="ma-2" tile outlined color="red"  @click="item.editstate=false" >
                      <v-icon left bottom>mdi-cancel</v-icon> 취소
                  </v-btn>
                </div>
                <div v-if="(role==admin||item.uid==uid)&&!item.editstate">
                  <v-btn icon color="green" @click="item.editstate = true">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                  <v-btn icon color="pink" @click="deletedialog = true">
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                   <v-dialog
                      v-model="deletedialog"
                      max-width="340"
                    >
                      <v-card>
                        <v-card-title class="headline">댓글을 삭제하시겠습니까?</v-card-title>

                        <v-card-text>
                          <span style="color:red;">삭제된 댓글</span>은 다시 <span style="color:red;">복구</span>할 수 없습니다.
                        </v-card-text>

                        <v-card-actions>
                          <v-spacer></v-spacer>

                          <v-btn
                            color="black darken-1"
                            text
                            @click="deletedialog = false"
                          >
                            취소
                          </v-btn>

                          <v-btn
                            color="red darken-1"
                            text
                            @click="deleteComment(item.cid)"
                          >
                            확인
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                </div>

               
              </v-list-item-action>
              
            </template>
            
          </v-list-item>
          

          <v-divider
            v-if="index + 1 < commentlist.length"
            :key="index"
          ></v-divider>
      </template>

      </div>
      <div v-if="role!='guest'">
        <v-textarea
          v-model="comment"
          :rules="rules"
          counter="1000"
          label="댓글 달기"
          outlined
          style="margin-top:50px; width:100%;"
          
        ></v-textarea>
        <v-btn class="ma-2" tile outlined color="success" style="float:right;" @click.stop="addComment" :disabled="adding">
          <v-icon left bottom>mdi-pencil</v-icon> Comment
        </v-btn>
      </div>
      <div v-if="role=='guest'"  @click="login_rendar">
        <v-textarea
          v-model="commentnotlogin"
          :rules="rules"
          counter="1000"
          label="댓글 달기"
          outlined
          style="margin-top:50px; width:70%;"
          disabled
        
        ></v-textarea>
      </div>
    </div>
    <edit-form v-if="isWrite" style="text-align:left;" :subCategoryID="subCategoryID" :boardID="boardID" :title="title" :viewerText="viewerText" :type="1" />
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
import CategoryModal from '@/components/Board/CategoryModal.vue'
import { SidebarMenu } from 'vue-sidebar-menu'
import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/vue-editor';
import EditForm from '../components/Board/EditForm.vue'
import LoginModal from '@/components/LoginModal.vue'

export default {
name:'Blog',
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
      window.scrollTo(0,0);
        if(sessionStorage.getItem('role')!=null){
            this.role=sessionStorage.getItem('role')
        }
        if(sessionStorage.getItem('userID')!=null){
            this.uid=sessionStorage.getItem('userID')
        }

        axios.get('http://www.1000min.kr:8080/board/board',{
            params:{
                boardID:Number(this.boardID)
            }
        })
        .then(res=>{
            let date = res.data.object.createdDate
            this.createdDate=date.substring(0,4)+"년 "+date.substring(5,7)+"월 "+date.substring(8,10)+"일"
            this.viewerText=res.data.object.content
            this.title=res.data.object.title
            this.subCategoryID=Number(res.data.data)
           
            
        })
        

        
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

        axios.get('http://www.1000min.kr:8080/board/comment',{
          params:{
            boardId:Number(this.boardID)
          }
        })
        .then(res=>{
          
          let clist = res.data.object
          if(clist.length==0){
            this.commentlist.push({
                action: '',
                headline: '댓글이 없습니다.',
                title: '',
                subtitle: '',
            })
          }
          for(let i = 0; i<clist.length; i++){
            this.commentlist.push({
                action: clist[i].comment.createdDate.substring(0,4)+"년 "+clist[i].comment.createdDate.substring(5,7)+"월 "+clist[i].comment.createdDate.substring(8,10)+"일",
                headline: clist[i].comment.contents,
                title: clist[i].user.nickname,
                cid : clist[i].comment.id,
                uid : clist[i].user.id,
                editstate:false,
                subtitle: '',
                
            })
            
          }
          
        })
        .catch(res=>{
          console.log(res)
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
                createdDate:'',
                adding:false,
                uid : 0,
                deletedialog: false,
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
                    
                ],
                comment: '',
                commentnotlogin:'로그인 후 작성 가능합니다.',
                description: 'Comment',
                rules: [v => v.length <= 1000 || 'Max 1000 characters'],
                selected: [],
                commentlist: [
                        
                      ],
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
      login_rendar(){
              this.$modal.show(LoginModal,{
                    modal : this.$modal },{
                        name: 'dynamic-modal',
                        width : '330px',
                        height : '500px',
                        draggable: true,
            })
      },
      addComment(){
        if(this.adding){
          return;
        }
        this.adding=true
        
        axios.post('http://www.1000min.kr:8080/board/comment',{
          userId:sessionStorage.getItem("userID"),
          boardId:Number(this.boardID),
          comment:this.comment
        })
        .then(()=>{
          
          
          this.$router.go()
        })
        .catch((res)=>{
          console.log(res)
        })
      },

       editcomment(id,comment){
        axios.post('http://www.1000min.kr:8080/board/editcomment',{
          commentId: id,
          boardId:Number(this.boardID),
          comment : comment
        })
        .then(()=>{
          this.$router.go()
        })
        .catch((res)=>{
          console.log(res)
        })
      },

      deleteComment(cid){
        
        this.deletedialog=false
        axios.get('http://www.1000min.kr:8080/board/deletecomment',{
          params:{
            boardId:Number(this.boardID),
            commentId:cid
          }
        })
        .then(()=>{
          
          this.$router.go()
        })
        .catch((res)=>{
          console.log(res)
        })
      }
      
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