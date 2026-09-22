<template>
  <section class="container">
    <div class="location w1200">
      <el-breadcrumb :separator-icon="ArrowRight">
        <el-breadcrumb-item style="color:#fff;" :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path: '/index/accountOrder' }" v-if="golist=='buy'">我要买</el-breadcrumb-item>
        <!-- <el-breadcrumb-item v-if="golist=='kefu'">咨询客服</el-breadcrumb-item> -->
        <!-- <el-breadcrumb-item>选择游戏</el-breadcrumb-item> -->
      </el-breadcrumb>
    </div>
    
    <YouxiList :golist="golist"></YouxiList>
  </section>
  <Footer></Footer>
  <!-- <div class="fixed">
    <fixedBar />
  </div> -->
</template>

<script setup>
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, watch ,ref} from 'vue';
import fixedBar from '../home/fixedBox.vue';
import { useRouter } from 'vue-router';
import api from '@/api/index';
import { useStore } from "vuex";
import YouxiList from '@/components/YouxiList.vue'
import { ArrowRight } from '@element-plus/icons-vue'
const router = useRouter()
const golist = ref('buy')
// onMounted(()=>{
//   // router.currentRoute.value.path
//   //console.log("%c Line:32 🥥 router.currentRoute.value", "color:#ed9ec7", router.currentRoute.value.query);
//   if(router.currentRoute.value.query){
//     golist.value = router.currentRoute.value.query.to ||'buy'
//   }
// })
watch(() => router.currentRoute.value.query, (newValue, oldValue) => {
      if (router.currentRoute.value.path == '/index/accountOrder') {
        if(router.currentRoute.value.query){
          golist.value = router.currentRoute.value.query.to ||'buy'
        }
      }

    }, { deep: true, immediate: true })

</script>

<style scoped lang="less">
.container {
  // width: 1200px;
  // margin: 0 auto;
  // margin-top: 20px;
  background-color: #1C1632;
  .location {
        margin: 0 auto;
        padding-top: 25px;
        margin-bottom: 20px;
    }
    .fixed {
      position: fixed;
      right: 0px;
      bottom: 44.2%;
      transform: translateY(50%);
      z-index: 999;
    }
}
:deep(.el-breadcrumb__inner){
  color: #fff;
}

:deep(.el-breadcrumb__inner.is-link){
  color: #fff !important;
}
</style>