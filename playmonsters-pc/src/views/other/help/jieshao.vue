<template>
  <div class="main" v-loading="loading">
    <div class="tit flex">
      <span>{{ title }}<i></i></span>
      <div></div>
    </div>
    <div class="cont">
      <div class="texts" v-html="text"></div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, markRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import kefuApi from '@/api/kefu';
export default {
  name: 'zhinan',
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      loading: false,
      text: ``,
      type: null

    });

    onMounted(() => {
      state.type = route.currentRoute.value.query.notice
      getData()
    });
    watch(
      () => route.currentRoute.value.query,
      (newValue, oldValue) => {
        state.type = route.currentRoute.value.query.notice;
        getData()
      },
      { deep: true }
    );
    const getData = (val) => {
      state.loading = true
      kefuApi.getNotices(state.type).then(res => {
        state.loading = false
        if (res.length) {
          state.text = res[0].noticeContent
          state.title = res[0].noticeTitle
        } else {
          state.text = ''
          state.title = ''
        }
      })
    };

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

  font-size: 16px;
  font-weight: bold;
  color: @primaryColor;
  line-height: 50px;

  span {
    flex-shrink: 0;
    width: 100px;
    border-top: 4px solid @primaryColor;
    margin-left: -20px;
    text-align: center;
    position: relative;

    i {
      position: absolute;
      display: inline-block;
      height: 0;
      width: 0;
      border-top: 5px solid @primaryColor;
      border-bottom: 5px solid transparent;
      border-left: 5px solid transparent;
      border-right: 5px solid transparent;
      left: 50%;
      top: -1px;
    }
  }

  div {
    width: 1000px;
    height: 50px;
    background: #F8F8F8;
    border: 1px solid #E5E5E5;
  }
}

.cont {
  padding: 20px 0;
}
.texts ::v-deep img{
  width: 750px !important;
}
</style>
