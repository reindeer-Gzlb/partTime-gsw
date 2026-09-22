<template>
    <span
      class="image-item"
      :style="{
            width: width,
            height: height,
        }"
    >
        <span class="warp" @click="showImage">
            <span class="el-icon-view"></span>
        </span>
        <el-image
          ref="Image"
          class="image"
          :src="imgSrc"
          :preview-src-list="previewSrc"
        ></el-image>
    </span>
</template>

<script>
  export default {
    components: {},
    props: {
      src: [Array, String],
      width: {
        typeof: String,
        default: '100px',
      },
      height: {
        typeof: String,
        default: '100px',
      },
    },
    data() {
      return {
        srcList: [],
        baseurl: '',
      };
    },
    mounted() {},
    watch: {},
    computed: {
      imgSrc() {
        if (typeof this.src == 'string') {
          return this.src;
        } else {
          return this.src[0];
        }
      },
      previewSrc() {
        if (typeof this.src == 'string') {
          return [this.src];
        } else {
          return this.src;
        }
      },
    },
    methods: {
      // 显示图片
      showImage() {
        this.$refs.Image.clickHandler();
        this.$emit('image-show', this.src);
      },
    },
  };
</script>

<style lang='scss' scoped>
  .image-item {
    // width: 100px;
    // height: 100px;
    position: relative;
    display: inline-block;
    cursor: pointer;
    & + .image-item {
      margin-left: 10px;
    }
    .image {
      width: 100%;
      height: 100%;
    }
    .warp {
      position: absolute;
      width: 100%;
      height: 100%;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #fff;
    }
    &:hover {
      .warp {
        z-index: 1;
        background-color: rgba(0, 0, 0, 0.7);
      }
    }
  }
</style>
