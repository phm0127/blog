<template>
<div>
    <sidebar-menu :menu="menu" :relative="true" style="float:left; height:100vh; z-index:1;"   />
    <board-list v-if="!isWrite" :type="0" :subCategoryID="subCategoryID" />
    <write-form v-if="isWrite"/>
</div>
  
</template>

<script>
import WriteForm from '../components/Board/WriteForm.vue'
import BoardList from '../components/Board/BoardList.vue'
import { SidebarMenu } from 'vue-sidebar-menu'
export default {
name:'Portfolio',
    components : {
        SidebarMenu,
        WriteForm,
        BoardList,
    },
    data() {
            return {
                role :'guest',
                isWrite: false,
                subCategoryID:0,
                menu: [
                    {
                        header: true,
                        title: 'Main Navigation',
                        hiddenOnCollapse: true
                    },
                    {
                        href: '/',
                        title: '전체 글 보기',
                        //icon: 'fa fa-user'
                    },
                    {
                        href: '/portfolio',
                        title: 'Charts',
                        //icon: 'fa fa-chart-area',
                        child: [
                            {
                                href: '/portfolio/sublink',
                                title: 'Sub Link'
                                
                            },
                            {
                                href: '/portfolio/sublink',
                                title: 'Sub Link'
                                
                            }

                        ]
                        
                    }
                ]
            }
        }
}
</script>

<style>
html, body {

    margin: 0;
    padding :0;

}


</style>