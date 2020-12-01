<template>
  <div class="wrapper" style="background-color:#f8f8f8">
    <div class="typing-demo">
      Hello, <span style="color:#4587f4;">W</span><span style="color:#ea4436;">o</span><span style="color:#fbbc05;">r</span><span style="color:#4587f4;">l</span><span style="color:#34a853;">d</span><span style="color:#ea4436;">!</span>
    </div>
</div>
</template>

<script>
export default {
 name : 'HomeBanner',
}
</script>

<style>
.wrapper {
  
  height: 10vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #ebebeb;
}

.typing-demo {
  width: 22ch;
  animation: typing 2s steps(22), blink .5s step-end infinite alternate;
  white-space: nowrap;
  overflow: hidden;
  border-right: 3px solid;
    font-family: 'Bebas Neue', cursive;
    font-family: 'Do Hyeon', sans-serif;
  font-size: 4em;
  text-align: center;
}

@keyframes typing {
  from {
    width: 0
  }
}
    
@keyframes blink {
  50% {
    border-color: transparent
  }
}
</style>