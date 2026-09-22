<template>
  <div class="main">
    <div class="tit">怪兽玩代售隐私政策</div>
    <div class="cont" v-html="text">
      
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted  } from 'vue';
import kefuApi from '@/api/kefu';
export default {
  name: 'zhinan',
  setup(props, ctx) {
    const state = reactive({
      title: '',
      text: ``
    });

    onMounted(() => {
      kefuApi.getNotices('yinsizhengce').then(res => {
          if (res.code == 200 || res != []) {
            state.text = res[0].noticeContent
          }
        })
    });

    return {
      ...toRefs(state)
    };
  }
};
</script>

<style scoped lang="less">
.main {
  font-size: 16px;
}
.tit {
  width: 100%;
  text-align: center;
font-size: 20px;
  height: 38px;
  border-bottom: 1px solid #ffebeb;
}
.cont {
  padding: 20px;
}
.demo-form {
  width: 535px;
  margin: 0 auto;
}
.btn{
  width: 70px;
    height: 40px;
    font-size: 14px;
    font-weight: 500;
    background: #df4545;
    border: none;
}
:deep(.el-form-item__label) {
  font-size: 16px;
  font-weight: bold;
}
</style>
