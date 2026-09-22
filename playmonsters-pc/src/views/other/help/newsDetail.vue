<template>
  <div class="main" v-loading="loading">
    <div class="tit flex">
      <span>{{ obj[type] }}<i></i></span>
      <div></div>
    </div>
    <div class="cont">
      <div class="texts" v-html="text"></div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted ,watch} from 'vue';
import { useRouter } from 'vue-router';
import kefuApi from '@/api/kefu';
export default {
  name: 'zhinan',
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      loading:false,
      text: ``,
      type:null,
      id:'',
      obj:{
        '20':'公告列表',
        '21':'新闻列表',
        '100':'找回案例',
      }

    });

    onMounted(() => {
      state.type = route.currentRoute.value.query.noticeType
      state.id = route.currentRoute.value.query.id
      getData()
    });
    watch(
      () => route.currentRoute.value.query,
      (newValue, oldValue) => {
        state.type = route.currentRoute.value.query.noticeType
      state.id = route.currentRoute.value.query.id
        getData()
      },
      { deep: true }
    );
    const getData = (val) => {
      state.loading = true
      kefuApi.getNoticeDetail(state.id).then(res => {
        state.text = res.data.noticeContent
        state.title = res.data.noticeTitle
        state.loading = false
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
</style>
