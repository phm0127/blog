<template>
<div>
<div id="editor"></div>
  <div style="display:flex; padding : 20px;">
    <select v-model="selected" style="width:10%; font-size:20px; font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif; margin-right : 20px;">
        <option selected disabled hidden value="default">다시 선택하기</option>
        <option v-for="option in categoryOptions" v-bind:key="option.value" v-bind:value="option.id">
            {{ option.value }}
        </option>
        
    </select>
     <select v-model="subselected" style="width:10%; font-size:20px; font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif;">
        <option selected value="subCategoryID">서브 카테고리를 선택해주세요.</option>
        <option v-for="option in subcategoryOptions" v-bind:key="option.value" v-bind:value="option.id">
            {{ option.value }}
        </option>
        
    </select>
    <input v-model="title" placeholder="제목을 입력해주세요." style="margin-left : 20px;height : 4vh; width:40%; font-size:30px; font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif;">
     <v-btn
        
        
        color='#3F51B5'
        
        
        style="color:white; margin: 0 auto;"
        @click="write"
        >
            수정하기
        </v-btn> 
  </div>
  <editor 
    ref="toastuiEditor"
    id="editor"
    :initialValue="viewerText"
    :options="editorOptions"
    height="91vh"
    initialEditType="markdown"
    previewStyle="vertical"
    v-model="editorText"
    style="font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif;"
        
     />
    
</div>
</template>

<script>
import 'codemirror/lib/codemirror.css';
import '@toast-ui/editor/dist/toastui-editor.css';
import 'tui-color-picker/dist/tui-color-picker.css';
import colorSyntax from '@toast-ui/editor-plugin-color-syntax';
import 'highlight.js/styles/github.css';
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight';
import { Editor } from '@toast-ui/vue-editor';

//
// Step 1. Import highlight.js
import hljs from 'highlight.js/lib/highlight';
 
// Step 2. Import language files of highlight.js that you need
import javascript from 'highlight.js/lib/languages/javascript';
import clojure from 'highlight.js/lib/languages/clojure';
import java from 'highlight.js/lib/languages/java';
import bash from 'highlight.js/lib/languages/bash';
import dockerfile from 'highlight.js/lib/languages/dockerfile';
import json from 'highlight.js/lib/languages/json';
import markdown from 'highlight.js/lib/languages/markdown';
import properties from 'highlight.js/lib/languages/properties';
import shell from 'highlight.js/lib/languages/shell';
import sql from 'highlight.js/lib/languages/sql';
import vim from 'highlight.js/lib/languages/vim';
import xml from 'highlight.js/lib/languages/xml';
import yaml from 'highlight.js/lib/languages/yaml';
import python from 'highlight.js/lib/languages/python';
import nginx from 'highlight.js/lib/languages/nginx';
import kotlin from 'highlight.js/lib/languages/kotlin';
import gradle from 'highlight.js/lib/languages/gradle';
import axios from 'axios'
hljs.registerLanguage('javascript', javascript);
hljs.registerLanguage('clojure', clojure);
hljs.registerLanguage('java', java);
hljs.registerLanguage('bash', bash);
hljs.registerLanguage('dockerfile', dockerfile);
hljs.registerLanguage('json', json);
hljs.registerLanguage('markdown', markdown);
hljs.registerLanguage('properties', properties);
hljs.registerLanguage('shell', shell);
hljs.registerLanguage('sql', sql);
hljs.registerLanguage('vim', vim);
hljs.registerLanguage('xml', xml);
hljs.registerLanguage('yaml', yaml);
hljs.registerLanguage('python', python);
hljs.registerLanguage('nginx', nginx);
hljs.registerLanguage('kotlin', kotlin);
hljs.registerLanguage('gradle', gradle);

//console.log(colorSyntax)
console.log(codeSyntaxHighlight)

//

export default {
    components : {
        editor : Editor
    },
    props:{ 
        subCategoryID:Number,
        boardID:Number,
        title:String,
        viewerText:String
    },
    data() {
        return {
          editorText: '',
          
          selected:'default',
          subselected:'default',
          editorOptions:{
            plugins:[colorSyntax,[codeSyntaxHighlight, { hljs }] ],
            minHeight:"1000px"
            
          },
          category : '',
          categoryOptions:[
             
          ],
          subcategoryOptions:[

          ],

          
        }
    },
    mounted() {
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
        this.subselected=this.subCategoryID

    },
    methods:{
        write: function(){
          axios.post('http://localhost:8080/board/editboard',{
            boardID:this.boardID,
            type:0,
            title:this.title,
            content:this.$refs.toastuiEditor.invoke("getMarkdown"),
            subCategoryID:this.subselected
          })
          .then(()=>{
              
              this.$router.push('/portfolio')
          })
          .catch(res=>{
              console.log(this.boardID)
              console.log(res)
          })
          
        }
    },
     watch:{
        selected : function (newVal) {
          axios.get('http://localhost:8080/board/subcategory',{
            params:{
                mainCategoryID:newVal
            }
          })
          .then(res=>{
            for(let i=0;i<res.data.object.length;i++){
              this.subcategoryOptions.push({
                value:res.data.object[i].name,
                id:res.data.object[i].id
              })
            }
          })
        }
     }
}
</script>

<style scoped>
/* default UI Styles */
#editor {
    font-size: 100px;
}
</style>