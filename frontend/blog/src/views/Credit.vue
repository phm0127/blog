<template>
<div class="credit" >
 <div class="console" style="width:100%; text-align : left;">
  <div class="top"> <span class="options">⦿ ○ ○</span> <span class="title">1000Min's DevLOG </span></div>
  <div class="tabs"> </div>
  <div class="text">
    <br>[06:24:55] Frontend <span class="pink1">'Vue.js'</span> Javascript
    <br>[06:24:55] Backend <span class="pink1">'Spring'</span> Java, JPA
    <br>[06:24:55] Database <span class="pink1">'MariaDB'</span> on Docker
    <br>[06:25:00] <span class="blue1"> Develop from scratch</span>
    <br> [06:25:02] find ~/ -name "<span class="orange1">도움을 주신 분</span>"
    <br> Kakao/Tim Aeom
    <br> Naver/Sejin
    <br> SSAFY/sooya12
    <br> SSAFY/Chaeyeong
    <br>[06:24:55] Started <span class="pink1">'2020-08-28'</span>, after deploy <span class="blue1">? </span> Would you like to continue this project? <span class="orange1">Yes</span>
    <br> [06:25:14] Forwards <span class="blue1">1000</span> steps, <span class="blue1">1000</span> passes, <span class="blue1">0</span> failures: <span class="pink1">SUCCESS </span>
    <br>
    <p class="typewriter"> root@1000min sudo <span class="pink1"> Thanks to Tim, Sejin, sooya, Chaeyeong</span> again</p>
    
  </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.credit {
  background: #222;
  font-family: helvetica;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 400px;
}
  .blue1 {
    color: #53d9d1;
  }
  .pink1 {
    color: #f27b9b;
  }
  .orange1 {
    color: #eb7132;
  }
  .console {
    border: 1px solid #444;
    border-radius: 3px;
    margin: 2rem;
 
    box-shadow: 0 0 15px 0px rgba(0, 0, 0, 0.75);
  }
    .top {
      background: #333;
      color: #666;
      text-align: center;
      font-size: 12px;
      padding: 5px;
    }
      .options {
        font-size: 16px;
        float: left;
      }
    
    .text {
      font-family: courier;
      color: #fff;
      font-size: 14px;
      padding: 0.25rem;
    }
  


.typewriter {
  overflow: hidden;
  width: fit-content;
  white-space: nowrap;
  border-right: 0.15em solid #eb7132;
  animation: typing 3s steps(15, end), blink-caret 0.75s step-end infinite;
}

/* The typing effect */
@keyframes typing {
  from {
    width: 0;
  }
  to {
    width: 60%;
  }
}

/* The typewriter cursor effect */
@keyframes blink-caret {
  from,
  to {
    border-color: transparent;
  }
  50% {
    border-color: orange;
  }
}

</style>

