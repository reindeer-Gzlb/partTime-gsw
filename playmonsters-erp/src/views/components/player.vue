<template>
   <!--播放器-->
  <div class="prism-player" id="J_prismPlayer" ></div>
</template>
<script>

  export default {
    props:{
     //组件需要的参数
      video: Object
    },
    data() {
      return {
      }
    },
    created() {
      //console.log("组件成功")
    },
    mounted(){
      this.initPlayer()
    },
    methods: {
      initPlayer(){
        //console.log(this.video)
        new Aliplayer({
          id: 'J_prismPlayer',
          vid: this.video.vid, // 视频id
          source: this.video.playauth, // 播放凭证
          width: '100%',
          height: '800px',
          //cover: 'http://guli.shop/photo/banner/1525939573202.jpg', // 封面
          qualitySort: 'asc', // 清晰度排序
          mediaType: 'video', // 返回音频还是视频
          autoplay: true, // 自动播放
          isLive: true, // 直播
          rePlay: true, // 循环播
          preload: true,
          controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
          useH5Prism: true, // 播放器类型：html5
        }, function(player) {
          console.log('播放器创建成功')
        })
      }
    }
  }
</script>

