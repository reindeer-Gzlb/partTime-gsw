<template>
  <div style="background-color: #211c38; border: 1px solid #6f6d6d">
    <div class="cooperate" v-loading="loading">
      <TopTitle :title="'关于我们'"></TopTitle>
      <div class="content" v-html="list.noticeContent"></div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, markRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'
import kefuApi from '@/api/kefu';
export default {
  name: 'cooperate',
  components: {
    TopTitle,
  },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      loading: false,
      text: ``,
      type: 208,
      list: []
    });

    onMounted(() => {
      getData()
    });

    const getData = (val) => {
      state.loading = true
      kefuApi.getNotices("guanyuwomen").then(res => {
        state.loading = false
        //console.log("111111111",res)
        if (res.code == 200 || res != []) {
          state.list = res[0]
          //console.log("222222222222",state.list)
        }
      })
    };

    return {
      ...toRefs(state)
    };
  }
};
</script>

<style lang="less" scoped>
.cooperate {
  width: 100%;
  background-color: #fff;
  min-height: 700px;

  .content {
    background-color: #211c38;
    padding: 0 15px;
    padding-top: 10px;
    img {
      width: 100%;
    }
  }
}
</style>


