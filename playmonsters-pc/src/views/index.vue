<template>
  <el-container class="content">
    <Header @click="open_mi" ref="RefHeaderComponent" />
    <div class="main" id="maincontent">
      <!-- <router-view /> -->

      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component"/>
        </keep-alive>
      </router-view>
    </div>
  </el-container>
</template>

<script>
import Header from '@/components/Header.vue'
import { ElConfigProvider } from 'element-plus'
export default {
  name: 'index',
  components: {
    Header,
    [ElConfigProvider.name]: ElConfigProvider,
  },
  setup() {
    const open_mi = () => {
      //console.log('open_mi')
    }
    return {
      open_mi,
    }
  },
}
</script>

<style scoped lang="less">
@import url(@/styles/main.less);
.content {
  display: flex;
  flex-direction: column;
  max-height: 100vh;
  // overflow: hidden;
}

.main {
  height: calc(100vh - 200px);
  // overflow: auto;
  box-sizing: border-box;
  background-color: #F8F8F8;
}

</style>