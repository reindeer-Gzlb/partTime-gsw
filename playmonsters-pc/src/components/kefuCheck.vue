<template>
  <!-- 客服验证弹窗 -->
    <el-dialog
      v-model="dialogTableVisible"
      style="margin-bottom: 0"
      append-to-body
      width="800px"
      title="客服验证"
    >
      <div class="is_show4 is_shows" @click.stop="on">
        <!-- <h2>客服验证</h2> -->
        <div class="content flex fbt">
          <div class="left">
            <div class="p1">请在下方输入需要查询的账号</div>
            <ul>
              <li>
                <p>官方客服微信验证</p>
                <div class="input">
                  <input type="text" placeholder="请输入客服微信号" v-model="weixin" />
                  <button @click="kefu_weixin">查询</button>
                </div>
              </li>
              <li>
                <p>平台收款账号查询</p>
                <div class="input">
                  <input type="text" placeholder="请输入平台收款账号" v-model="zhifubao" />
                  <button @click="kefu_zhifubao">查询</button>
                </div>
              </li>
              <li>
                <p>黑号查询（QQ/微信账号有申诉/找回记录的用户）</p>
                <div class="input">
                  <input
                    type="text"
                    placeholder="请输入黑号被举报人的手机号/QQ/微信"
                    v-model="heihao"
                  />
                  <button @click="kefu_heihao">查询</button>
                </div>
              </li>
            </ul>
            <div class="p2">
              提示：输入客服的联系方式/收款方式查询是否为官方账号，若无查询结果，请谨防上当受骗
            </div>
          </div>
          <div class="right">
            <p>如何辨别真伪教程</p>
            <div class="con cooperate">
              <!-- <img :src="kfyzimg" alt=""> -->
              <div class="img" v-html="kfyzimg"></div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
    <el-dialog v-model="resultShow" title="查询结果" @close="closeResult" width="300px">
      <div class="result-box ">
          <img v-if="is_show6||is_show7||is_show8" src="../assets/fh-icon/jinggao.png" alt="" />
          <img v-if="is_show66||is_show77||is_show88" src="../assets/fh-icon/fangxin.png" alt="" />
          <p v-if="is_show6">该账号不是怪兽玩代售官方客服，谨防受骗！</p>
          <p v-if="is_show66">该账号是怪兽玩代售官方客服，请放心咨询！</p>
          <p v-if="is_show7">该账号不是怪兽玩代售官方账号，谨防受骗！</p>
          <p v-if="is_show77">该账号是怪兽玩代售官方客服，请放心咨询！</p>
          <p v-if="is_show8">当前查询账号有找回记录，谨防受骗！</p>
          <p v-if="is_show88">当前查询账号没有找回记录，请放心咨询！</p>
      </div>
    </el-dialog>
</template>

<script>
import kefu from '@/api/kefu';
import { reactive, toRefs, onMounted } from 'vue';
export default {
  name: 'check',
  setup(props, ctx) {
    const state = reactive({
      dialogTableVisible: false,
      heihao: '',
      weixin: '',
      zhifubao: '',
      kfyzimg: '',
      kfyzname: 'kfyz',
      resultShow:false,
      is_show66:false,
      is_show6:false,
      is_show77:false,
      is_show7:false,
      is_show88:false,
      is_show8:false,
    });

    onMounted(() => {
    });
    const open = () => {
      state.dialogTableVisible = true;
    };
  
    // 1，官方客服微信验证
    const kefu_weixin = () => {
      console.log('kefu_weixin', state.weixin);
      kefu.checkAccount({ weixin: state.weixin }).then((res) => {
        state.resultShow = true
        if (res.code == 200) {
          state.is_show66 = true;
        } else {
          state.is_show6 = true;
        }
      });
    };
    // 2，平台收款账号查询
    const kefu_zhifubao = () => {
      kefu.checkAccount({ collection: state.zhifubao }).then((res) => {
        state.resultShow = true
        if (res.code == 200) {
          state.is_show77 = true;
        } else {
          state.is_show7 = true;
        }
      });
    };
    // 3，黑号查询（QQ/微信账号有申诉/找回记录的用户）
    const kefu_heihao = () => {
      console.log('kefu_heihao', state.heihao);
      kefu.checkHeihao({ content: state.heihao }).then((res) => {
        if (res.code == 401) {
          return;
        }
        state.resultShow = true
        if (res.code == 200 && res.total == 0) {
          state.is_show88 = true;
        } else {
          state.is_show8 = true;
        }
      });
    };
    // //客服验证
    // const getkfyzImg = () => {
    //   kefu.getdetailywjs(state.kfyzname).then((res) => {
    //     state.kfyzimg = res[0].noticeContent;
    //     // state.kfyztitleImg = res[0].titleImg
    //   });
    // };
    const closeResult = ()=>{
      state.resultShow = false
      state.is_show6 = false
      state.is_show66 = false
      state.is_show7 = false
      state.is_show77 = false
      state.is_show8 = false
      state.is_show88 = false
    }

    return {
      ...toRefs(state),
      open,
      kefu_weixin,
      kefu_zhifubao,
      kefu_heihao,
      closeResult
    };
  }
};
</script>

<style scoped lang="less">
.is_show4 {
  min-width: 700px;
  // height: 580px;
  box-sizing: border-box;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;

  .content {
    width: 100%;
    height: 100%;
    display: flex;

    .left {
      width: 400px;
      height: 100%;

      .p1 {
        font-size: 18px;
        font-weight: 500;
        color: #FF8E00;
      }

      ul {
        padding: 0;

        li {
          margin-top: 24px;

          p {
            font-size: 17px;
            color: #1d1d1d;
          }

          .input {
            margin-top: 17px;
            display: flex;
            align-items: center;

            input {
              width: 250px;
              height: 42px;
              border: 1px solid #e4e7ed;
              border-radius: 8px;
              padding-left: 14px;
            }

            button {
              width: 100px;
              margin-left: 15px;
              text-align: center;
              height: 42px;
              line-height: 42px;
              background: #FF8E00;
              border-radius: 8px;
              color: #fff;
              cursor: pointer;
              border: none;
            }
          }
        }
      }

      .p2 {
        font-size: 17px;
        color: #FF8E00;
        line-height: 30px;
        margin-top: 50px;
      }
    }

    .right {
      width: 297px;
      height: 460px;
      padding-left: 60px;

      p {
        font-size: 18px;
        font-weight: 500;
        color: #FF8E00;
        margin-top: 0;
      }

      .con {
        overflow-y: scroll;
        width: 100%;
        height: 415px;

        img {
          width: 100%;
        }
      }
    }
  }
}
.result-box{
  width: 280px;
  margin: 0 auto;
  text-align: center;

}
</style>
