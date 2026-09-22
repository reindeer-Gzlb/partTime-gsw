<template>
  <div class="main">
    <div class="tit flex">
      <span>{{ obj[type] }} <i></i></span>
      <div></div>
    </div>
    <div class="cont">
      <div class="newlist">
              <div class="newitem" v-for="(li, ind) in list" :key="ind" @click="goDetail(li)">
                <span class="dia"></span>
                <span>{{ li.noticeTitle }}</span>
              </div>
            </div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted ,markRaw ,watch} from 'vue';
import { useRouter } from 'vue-router';
import { WarningFilled ,SuccessFilled  } from '@element-plus/icons-vue'
import kefuApi from '@/api/kefu';
export default {
  name: 'zhinan',
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      text: ``,
      list:[],
      type:null,
      obj:{
        '20':'公告列表',
        '21':'新闻列表',
        '100':'找回案例',

      }
    });

    onMounted(() => {
      state.type = route.currentRoute.value.query.notice
      getList()
    });
    const goDetail=(item)=>{
      route.push({
        path:'/index/other/newsDetail',
        query:{
          id:item.noticeId,
          noticeType:state.type
        }
      })
    }
    watch(
      () => route.currentRoute.value.query,
      (newValue, oldValue) => {
        state.type = route.currentRoute.value.query.notice;
        getList()
      },
      { deep: true }
    );
    const getList = (val) => {
      kefuApi.getNoticeList(state.type).then(res=>{
        state.list = res
      })
    };

    return {
      ...toRefs(state),
      getList,
      goDetail
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
span{
  flex-shrink: 0;
  width: 100px;
  border-top: 4px solid @primaryColor;
  margin-left: -20px;
  text-align: center;
  position: relative;
  i{
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
div{
  width: 1000px;
height: 50px;
background: #F8F8F8;
border: 1px solid #E5E5E5;
}
}
.cont {
  font-size: 16px;
  padding-top: 10px;
  .newlist {
          padding-top: 10px;
          box-sizing: border-box;
          color: #666;

          .newitem {
            width: 735px;
            line-height: 30px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;


            .dia {
              display: inline-block;
              width: 7px;
              height: 7px;
              border-radius: 50%;
              background: @primaryColor;
              vertical-align: middle;
              margin-right: 10px;
            }

          }
        }
}
</style>
