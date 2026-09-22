<template>
  <div class="contactkefu">
    <TopTitle :title="'联系客服'"></TopTitle>

    <div class="content">
      <p>服务时间：<span> 10:00 - 02:00 </span></p>
      <ul>
        <li v-for="(item, index) in kefu_list" :key="item.id">
          <img :src="item.qrCode" alt="" />
          <p>{{ item.name }}</p>
          <p>
              <el-button style="width: 150px" type="primary" @click="openQQ(item)"
                >立即联系</el-button
              >
            </p>
        </li>
      </ul>
      <div class="bottom">
        <div class="ke3">
          公司主管投诉/举报电话：
          <span class="red">15778693755</span>
        </div>
        <p class="red">温馨提示：非平台公示的任何联系方式请勿相信</p>
      </div>
    </div>
  </div>
  <!-- 平台客服 -->
  <ElMessageBox :is_show="is_show3" @update="handleChildEvent" :text="text">
    <div class="is_show3 is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="../../assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(3)"
      />
      <h2>平台客服</h2>
      <div class="content">
        <p>服务时间：<span> 10:00 - 02:00 </span></p>
        <ul>
          <li v-for="(item, index) in kefu_list" :key="item.id">
            <img :src="item.qrCode" alt="" />
            <p>{{ item.name }}</p>
            <p>
              <el-button style="width: 150px" type="primary" @click="openQQ(item)"
                >立即联系</el-button
              >
            </p>
          </li>
        </ul>
        <div class="bottom">
          <div class="ke3">
            公司主管投诉/举报电话：
            <span class="red">15778693755</span>
          </div>
          <p class="red">温馨提示：非平台公示的任何联系方式请勿相信</p>
        </div>
      </div>
    </div>
  </ElMessageBox>
</template>

<script>
import config from '@/api/config'
import { reactive, toRefs, onMounted, onUnmounted, ref, watch, onActivated } from 'vue';
import kefu from '@/api/kefu';
import TopTitle from '@/components/TopTitle.vue';
export default {
  name: 'security',
  components: {
    TopTitle
  },
  setup(props, ctx) {
    const state = reactive({
      is_show3: false,
      kefu_list: []
    });
    // 获取客服数据
    const getsecurity = () => {
      kefu.getsecurity().then((res) => {
        //console.log('获取客服数据', res);
        state.kefu_list = res;
      });
    };

    onMounted(() => {
      getsecurity(), show();
    });

    const handleChildEvent = () => {
      state.is_show3 = false;
      // 让浏览器滚动条显示
      document.documentElement.style.overflow = 'auto';
    };

    // 开启弹窗
    const show = (item) => {
      state.is_show3 = true;
      // 让浏览器滚动条隐藏
      document.documentElement.style.overflow = 'hidden';
    };

    const openQQ = (val) => {
       //console.log(val);
       let token = window.localStorage.getItem('token')
      if (token) {
        token = JSON.parse(token)
      } else {
        token = ''
      }
      //console.log("%c Line:67 🍡 联系客服链接", "color:#7f2b82",`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}`);
      window.open(`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}`)  
    }

    return {
      ...toRefs(state),
      handleChildEvent,
      show,
      openQQ
    };
  }
};
</script>

<style lang="less" scoped>
ul,
ol,
li {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Microsoft YaHei';
  font-size: 14px;
  color: #333;
}

.contactkefu {
  width: 100%;
  background-color: #fff;
  min-height: 610px;

  .content {
    min-height: 450px;
    overflow-y: auto;

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
      // box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
      margin-top: 28px;
      padding: 20px 0;
      display: flex;
      flex-wrap: wrap;

      li {
        margin: 16px 25px;

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
        height: 58px;
        line-height: 58px;
        background: #eee;
        border-radius: 8px;
        text-align: center;
        font-size: 18px;
        color: #666;
        margin: 42px auto 17px;
      }

      p {
        font-size: 17px;
        color: #FF8E00;
        text-align: center;
      }
    }
  }
}

.is_show3 {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;
  width: 833px;

  .icon_cha {
    width: 25px;
    height: 25px;
    position: absolute;
    top: 20px;
    right: 20px;
    cursor: pointer;
  }

  h2 {
    text-align: center;
  }

  .content {
    min-height: 450px;
    overflow-y: auto;

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
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      margin-top: 28px;
      padding: 20px 0;
      display: flex;
      flex-wrap: wrap;

      li {
        margin: 16px 25px;

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
        height: 58px;
        line-height: 58px;
        background: #eee;
        border-radius: 8px;
        text-align: center;
        font-size: 18px;
        color: #666;
        margin: 42px auto 17px;
      }

      p {
        font-size: 17px;
        color: #FF8E00;
        text-align: center;
      }
    }
  }
}
</style>
