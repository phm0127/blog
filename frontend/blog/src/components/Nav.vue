<template>
    <div style="z-index:2; margin : 0 auto;">
    <ul class="nav" style="position:fixed; z-index:2; margin : 0 auto; left:0; right:0; ">
        <li><a v-on:click="movepage('Main',1)" class="slid" style="color: #ffffff;">Home</a></li>
        <li><a v-on:click="movepage('Intro',2)" style="color: #ffffff;">About me</a></li>
        <li><a v-on:click="movepage('Portfolio',3)" style="color: #ffffff;">Portfolio</a></li>
        <li><a v-on:click="movepage('Blog',3)" style="color: #ffffff;">Blog</a></li>
        
        <li class="slide1"></li>
        <li class="slide2"></li>
    </ul>
        
        
    
   
    
    
    </div>
</template>

<script>
import $ from 'jquery'



export default {
   
     name : 'Nav',
     
    mounted : function(){
    
    localStorage.setItem("pageIndex",1)
    
    
    $(".nav a").on("click", function () {
    var position = $(this).parent().position();
    var width = $(this).parent().width();
    $(".nav .slide1").css({ opacity: 1, left: +position.left, width: width });
});

$(".nav a").on("mouseover", function () {
    var position = $(this).parent().position();
    var width = $(this).parent().width();
    $(".nav .slide2").css({
        opacity: 1,
        left: +position.left,
        width: width
    });
});

$(".nav a").on("mouseout", function () {
    $(".nav .slide2").css({ opacity: 0 });
});

var currentWidth = $(".nav").find("li:nth-of-type(1) a").parent("li").width();
var current = $("li:nth-of-type("+localStorage.getItem('pageIndex')+") a").position();
$(".nav .slide1").css({ left: +current.left, width: currentWidth });

   

  

        

     },
    methods:{
       movepage : function(page,index){
          this.$store.state.pageIndex=index
          localStorage.setItem("pageIndex",index)
          this.$router.push({name: page}).catch(()=>{})
       },
      
     }
      
}


</script>


<style scoped>
  
  .nav {
    position: relative;
    display: flex;
    list-style: none;
    padding: 10px 20px;
    background-color: #000000;
  }
    a {
        position: relative;
        padding: 0.6em 2em;
        font-size: 20px;
        border: none;
        outline: none;
        color: #ffffff;
        display: inline-block;
        text-decoration: none;
        text-shadow: 1px 1px 0 #888;
        z-index: 3;
    }

    .slide1,
    .slide2 {
        position: absolute;
        display: inline-block;
        height: 0.4em;
        box-shadow: 1px 1px 0 #666;
        transition: all 0.6s cubic-bezier(0.23, 1, 0.32, 1.05);
        transform: skew(-20deg);
        bottom: 0px;
        height: 100%;
    }
    .slide1 {
       
        background-color: rgba(255, 255, 255, 0.5);
        z-index: 2;
    }
    .slide2 {
        opacity: 0;
        background-color: transparent;
        border: 1px solid #10014e70;
        z-index: 1;
    }


@import url("https://fonts.googleapis.com/css?family=Poppins:300,400,800&display=swap");

*,
*::before,
*::after {
    box-sizing: border-box;
}
html,
body {
    height: 100%;
}

body {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #e0e0e0;
    font-family: poppins;
    line-height: 1.5;
    background: linear-gradient(110deg, #333 50%, #444 50%);
}


  
</style>
