<template>
<div>
    <sidebar-menu :menu="menu" :relative="true" style="float:left; height:100vh; z-index:1;"   />
    
    <write-form/>
</div>
  
</template>

<script>
import WriteForm from '../components/Board/WriteForm.vue'
import { SidebarMenu } from 'vue-sidebar-menu'
export default {
name:'Portfolio',
    components : {
        SidebarMenu,
        WriteForm,
        
    },
    data() {
            return {
                menu: [
                    {
                        header: true,
                        title: 'Main Navigation',
                        hiddenOnCollapse: true
                    },
                    {
                        href: '/',
                        title: 'Dashboard',
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