<template>
  <div class="main">
    <el-dialog v-model="dialogTableVisible" top="0" style="margin-bottom: 0;"  append-to-body width="1000px">
      <div class="is_show3 is_shows" @click.stop="on">
      <div class="content">
        <!-- <p> 服务时间：<span> 10:00 - 02:00 </span></p> -->
        <!-- <img class="fhds" src="../../assets/fh-img/fhds.png" alt=""> -->
        <h2>平台客服</h2>
        <h4>服务时间：<span style="color: #FF8E00;">10:00 - 02:00</span></h4>
        <ul>
          <li v-for="(item,index) in kefu_list" :key="item.id">
            <img :src="item.qrCode" alt="">
            <p>{{ item.name }}</p>
            <p><el-button style="width:150px;" type="primary" @click="openQQ(item)">立即联系</el-button></p>
          </li>
        </ul>
        <div class="bottom">
          <div class="ke3">
            公司主管投诉/举报电话：
            <span class="red">15778693755</span>
          </div>
          <p class="red">
            温馨提示：非平台公示的任何联系方式请勿相信
          </p>
        </div>
      </div>
    </div>
    </el-dialog>
  </div>
</template>

<script>
import kefu from '@/api/kefu'
import config from '@/api/config'
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import useClipboard from 'vue-clipboard3'
export default {
  name: 'zhinan',
  setup(props, ctx) {
    const state = reactive({
      dialogTableVisible:false,
      list:[],
      allList:[],
      kefu_list:[],
      type:'',
      gameId:''
    });

    onMounted(() => {
      // getList()
    });
    // 获取客服数据
    const getsecurity = () => {
      kefu.getsecurity({
        gameIdsArr:state.gameId
      }).then(res => {
        //console.log("获取客服数据", res)
        state.kefu_list = res
      })
    }
    const open = (type,gameId)=>{
      state.type = type
      state.gameId = gameId
      state.dialogTableVisible = true
      getsecurity()
      // //console.log("%c Line:50 🍖 dialogTableVisible", "color:#2eafb0", dialogTableVisible);
    }
    const getList = ()=>{
      kefu.getkefu().then(res=>{
        //console.log('%c 🍇 res: ', 'font-size:20px;background-color: #42b983;color:#fff;', res);
        state.allList = JSON.parse(JSON.stringify(res.data))
        state.list = JSON.parse(JSON.stringify(res.data))
      })
    }
    const { toClipboard } = useClipboard()
    const copyInfo = async (info) => {
      //console.log('%c 🥟 info: ', 'font-size:20px;background-color: #4b4b4b;color:#fff;', info);
      try {
        await toClipboard(info)
        ElMessage.success('复制成功')
      } catch (e) {
        ElMessage.warning('您的浏览器不支持复制：', e)
      }
    }
    //即时通讯
    const openQQ = (val) => {
      //console.log(val);
      let token = window.localStorage.getItem('token')
      if (token) {
        token = JSON.parse(token)
      } else {
        token = ''
      }
      // //console.log("%c Line:67 🍡 联系客服链接", "color:#7f2b82", `${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}&goodsInfo=${encodeURIComponent(state.goodsInfo)}`);
      window.open(`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}`)
    }



    return {
      ...toRefs(state),
      open,
      getList,
      copyInfo,
      openQQ
    };
  }
};
</script>

<style scoped lang="less">
.is_show3{
  height: calc(100vh - 165px);
  overflow: hidden;
}
  .content {
    width: 100%;
    height: 100%;
    overflow-y: auto;
    flex-wrap: wrap;
    text-align: center;
    .fhds{
      width: 800px;
      height:100px;
    }
    p {
      width: 100%;
      text-align: center;

      span {
        color: #ff0000;
      }
    }

    ul {
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
      margin-top: 10px;
      padding: 10px 0;
      display: flex;
      flex-wrap: wrap;

      li {
        margin: 0px 25px;

        img {
          width: 158px;
          height: 157px;
        }

        p {
          text-align: center;
        }
      }
    }

    .bottom {
      .red {
        color: #FF8E00;
      }

      .ke3 {
        width: 542px;
        height: 50px;
        line-height: 50px;
        background: #eee;
        border-radius: 8px;
        text-align: center;
        font-size: 18px;
        color: #666;
        margin: 10px auto 15px;
      }

      p {
        font-size: 17px;
        color: #FF8E00;
        text-align: center;
      }
    }
  }
 
</style>
