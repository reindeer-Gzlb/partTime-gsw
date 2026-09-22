<template>
  <el-config-provider :locale="zhCn">
    <div class="layout">
      <el-container class="container">
        <router-view />
      </el-container>
    </div>
    <div>
      <!-- 1233423434324 -->
      <div class="fixed" v-if="main_swiper == false">
        <rightBox />
      </div>
    </div>
  </el-config-provider>
</template>

<script>
import { ElConfigProvider } from 'element-plus'
// import zhCn from 'element-plus/lib/locale/lang/zh-cn'
import { onUnmounted, reactive, toRefs, onMounted, watch } from 'vue'
// import Header from '@/components/Header.vue'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import rightBox from './views/home/fixedBox.vue'
import { useRouter } from 'vue-router';
export default {
  name: 'App',
  components: {
    // Header,
    rightBox,
    [ElConfigProvider.name]: ElConfigProvider,
  },
  setup() {
    const router = useRouter();
    const state = reactive({
      locale: {},
      main_swiper: false
    })

    onUnmounted(() => { })

    onMounted(() => {
      // if (router.name !== '/') {
      //   state.main_swiper = false
      // } else {
      //   state.main_swiper = true
      // }
    })

    watch(
      () => router.currentRoute.value,
      (newValue, oldValue) => {
        console.log('22222', router.currentRoute.value);
        // 判断当前页面
        if (newValue.path === '/login') {
          state.main_swiper = true;
        } else {
          state.main_swiper = false;
        }
      },
      { deep: true, immediate: true }
    );

    return {
      ...toRefs(state),
      zhCn
      // locale: zhCn,
    }
  },
}
</script>

<style scoped lang="less">
@import url(@/styles/main.less);

.layout {
  min-height: 100vh;
  background-color: #ffffff;
}
</style>
<style lang="less">
@import url(element-plus/dist/index.css);

body {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
  font-size: 14px;
  background: #f8f8f8;
  color: #333;
}

.w1200 {
  width: 1200px;
  margin: 0 auto;
}

.w1000 {
  width: 1000px;
  margin: 0 auto;
}
.w1300 {
  width: 1300px;
  margin: 0 auto;
}

.el-menu {
  border-right: none !important;
}

.el-submenu {
  border-top: 1px solid hsla(0, 0%, 100%, 0.05);
  border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.el-submenu:first-child {
  border-top: none;
}

.el-submenu [class^='el-icon-'] {
  vertical-align: -1px !important;
}

a {
  color: #409eff;
  text-decoration: none;
}

.el-pagination {
  text-align: center;
  margin-top: 20px;
}

.el-popper__arrow {
  display: none;
}

.flex {
  display: flex;
}

.fbt {
  justify-content: space-between;
}

.el-input {
  --el-input-focus-border: @primaryColor;
}

.el-button--primary {
  background-color: @primaryColor !important;
  color: @ContrastColor !important;
  border: none !important;
}

.el-radio-button {
  --el-radio-button-checked-bg-color: @primaryColor !important;
  --el-radio-button-checked-border-color: @primaryColor !important;
}

.el-button.is-plain {
  --el-button-active-text-color: @primaryColor !important;
  --el-button-active-border-color: @primaryColor !important;
}

.cooperate {
  img {
    width: 100%;
  }
}
/* .el-button{
        background: @primaryColor ;
        border: none;
      } */
.fixed {
  position: fixed;
  right: 18px;
  bottom: 50%;
  transform: translateY(50%);
  // top: 200px;
  z-index: 999;
}
</style>
