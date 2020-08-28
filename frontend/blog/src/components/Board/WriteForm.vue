<template>
<div>
<div id="editor"></div>
  <div style="display:flex; padding : 20px;">
    <select v-model="selected" style="width:10%; font-size:20px; font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif;">
        <option disabled value="">카테고리 선택</option>
        <option v-for="option in categoryOptions" v-bind:key="option.value">
            {{ option.value }}
        </option>
        
    </select>
    <input v-model="title" placeholder="제목을 입력해주세요." style="margin-left : 20px;height : 4vh; width:40%; font-size:30px; font-family: 'Bebas Neue', cursive; font-family: 'Do Hyeon', sans-serif;">
  </div>
  <editor 
    ref="toastuiEditor"
    id="editor"
    :initialValue="editorText"
    :options="editorOptions"
    height="91vh"
    initialEditType="wysiwyg"
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
    
    data() {
        return {
          editorText: '',
          title:'',
          editorOptions:{
            plugins:[colorSyntax,[codeSyntaxHighlight, { hljs }] ],
            minHeight:"1000px"
            
          },
          category : '',
          categoryOptions:[
              {value: 'A'},
              {value: 'B'},
          ]
          
        }
    },
    mounted() {
        console.log(this.$refs.toastuiEditor.$el)
        
    }
}
</script>

<style scoped>
/* default UI Styles */
#editor {
    font-size: 100px;
}
</style>