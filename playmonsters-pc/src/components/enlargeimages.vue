<template>
    <div class="goods-image">
      <!-- 预览大图 -->
      <div class="large" :style='[{backgroundImage: `url(${images[currIndex]})`}, bgPosition]' v-show='isShow'></div>
      <div class="middle" ref='target'>
        <!-- 左侧的大图 -->
        <img :src="images[currIndex]" alt="">
        <!-- 遮罩层 -->
        <div class="layer" :style='[position]' v-show='isShow'></div>
      </div>
      <ul class="small">
        <!-- 右侧的缩略图 -->
         <!-- <li v-for="(img,i) in images" :key="img" :class="{active:i===currIndex}">
          <img @mouseenter="currIndex=i" :src="img" alt="">
        </li> -->
      </ul>
    </div>
  </template>
  <script>
  import { ref, watch, reactive } from 'vue'
  import { useMouseInElement } from '@vueuse/core'
  
  export default {
    name: 'EnlargeImages',
    props: {
      images: {
        type: Array,
        default: () => []
      }
    },
    setup (props) {
      const currIndex = ref(0)
      const target = ref(null)
      const isShow = ref(false)
      // 遮罩层的坐标
      const position = reactive({
        left: 0,
        top: 0
      })
      // 控制背景图的位置
      const bgPosition = reactive({
        backgroundPositionX: 0,
        backgroundPositionY: 0
      })
      const { elementX, elementY, isOutside } = useMouseInElement(target)
      // 侦听鼠标移动后信息
      watch([elementX, elementY, isOutside], () => {
        // 每次有值发生变化，就读取新的数据即可
        isShow.value = !isOutside.value
        // 鼠标在图片的区域之外，不需要计算坐标
        if (isOutside.value) return
        // 水平方向
        if (elementX.value < 100) {
          // 左边界
          position.left = 0
        } else if (elementX.value > 300) {
          // 右边界
          position.left = 310
        } else {
          // 中间的状态
          position.left = elementX.value - 100
        }
        // 垂直方向
        if (elementY.value < 100) {
          // 上边界
          position.top = 0
        } else if (elementY.value > 200) {
          // 下边界
          position.top = 190
        } else {
          // 中间的状态
          position.top = elementY.value - 100
        }
        // console.log(elementX.value, elementY.value, isOutside.value)
        // 计算预览大图背景的位置
        bgPosition.backgroundPositionX = -position.left * 2 + 'px'
        bgPosition.backgroundPositionY = -position.top * 2 + 'px'
        // 计算左侧遮罩层位置
        position.left += 'px'
        position.top += 'px'
      })
      return { currIndex, target, isShow, position, bgPosition }
    }
  }
  </script>
  <style scoped lang="less">
  .goods-image {
    box-sizing: border-box;
    width: 1200px;
    height: 290px;
    position: relative;
    display: flex;
    z-index: 1000000;
     img {
          width: 100%;
          height: 100%;
      }
    .large {
      position: absolute;
      top: 0;
      left: 526px;
      width: 510px;
      height: 290px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      background-repeat: no-repeat;
      background-size: 800px 800px;
      background-color: #f8f8f8;
      z-index: 1000000;
    }
    .middle {
      width: 510px;
      height: 290px;
      background: #f5f5f5;
      position: relative;
      cursor: move;
      img{
        width: 510px;
      }
      .layer {
        width: 200px;
        height: 100px;
        background: rgba(124, 121, 94,.6);
        left: 0;
        top: 0;
        position: absolute;
        z-index: 10000;
      }
    }
    .small {
      margin: 0;
      padding: 0;
      width: 80px;
      li {
        width: 68px;
        height: 68px;
        margin: 10px;
        list-style: none;
        cursor: pointer;
        img{
            // width: 500px;
        }
        &:hover,
        &.active {
          border: 1px solid #27ba9b;
        }
      }
    }
  }
  </style>
  
  