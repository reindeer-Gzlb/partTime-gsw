<!-- PlatformRecycling -->
<template>
  <div class="sale">
    <div class="content">
      <div class="top">
        <div class="left">
          <ul>
            <li v-for="(item,index) in list" :key="item.noticeId" v-html="item.noticeContent">

            </li>
          </ul>
        </div>
        <div class="right">
          <div></div>
          <button @click="goLink('/index/other/contactkefu')">去评估</button>
        </div>
      </div>
      <div class="bottom">
        <YouxiList :golist="'huishou'"></YouxiList>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
// 导入top_title
import YouxiList from '@/components/YouxiList.vue'
import kefu from '@/api/kefu'
export default {
  name: 'sale',
  components: {
    YouxiList
  },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      list:[]
    });

    // 跳转
    const goLink = (link, query) => {
      if (query) {
        route.push({ path: link, query: query });
      } else {
        route.push({ path: link });
      }
    };

    // 获取客服信息
    const getAccountRecovery = () => {
      kefu.getAccountRecovery("zhhs").then(res => {
        //console.log(res)
        state.list = res.rows
      })
    }

    onMounted(() => {
      getAccountRecovery()
    });

    return {
      ...toRefs(state),
      goLink
    };
  }
}
</script>

<style lang="less" scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style: none;
  font-family: 'Microsoft YaHei';
}

.sale {
  width: 100%;
  min-height: 400px;

  .content {
    width: 1200px;
    min-height: 600px;
    // background: pink;
    margin: 0 auto;

    .top {
      width: 1200px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
      margin-top: 10px;

      .left {
        width: 685px;
        height: 170px;
        background-color: #fff;
        box-shadow: 0 0 8px #c7c7c7;
        padding: 10px;
        padding-top: 0;
        overflow: hidden;
        padding-bottom: 10px;
        border-radius: 5px;
        box-sizing: border-box;
        // 显示滚动条
        overflow-y: scroll;
        // 滚动条样式

        ul {
          margin: 0;
          padding: 0;
        }
      }

      .right {
        width: 500px;
        height: 170px;
        background-color: #fff;
        box-shadow: 0 0 8px #c7c7c7;
        padding: 15px;
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        align-items: flex-end;
        border-radius: 5px;

        button {
          width: 160px;
          height: 40px;
          background: linear-gradient(90deg, #FF8E00, #f77979);
          border-radius: 5px;
          color: #fff;
          line-height: 40px;
          text-align: center;
          font-size: 16px;
          cursor: pointer;
          border: none;
        }
      }
    }
  }
}
</style>