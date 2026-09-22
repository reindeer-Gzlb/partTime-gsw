<template>
  <div class="main">
    <div class="top">
      <img src="@/assets/icon/icon21.png" alt="">
      <span>基本信息</span>
    </div>
    <div class="cont">
      <div class="touxiang flex">
        <!-- <div class="tit">用户头像：</div> -->
        <div class="img">
          <el-upload class="avatar-uploader" :action="uploadUrl" :show-file-list="false" :on-success="handleAvatarSuccess"
            accept=".jpg,.png,.jpeg">
            <img v-if="info.avatar" :src="info.avatar" class="avatar" />
            <div src="../../assets/icon/hanadimg.png" alt="" class="moren"></div>
          </el-upload>
          <div class="editicon"></div>
        </div>
      </div>
      <div class=" flex li">
        <div class="tit">用户ID：</div>
        <div class="info">{{ info.userId }}</div>
      </div>
      <div class=" flex li">
        <div class="tit">手机号：</div>
        <div class="info">
          <input type="text" v-model="info.phonenumber" placeholder="请输入手机号" @change="changPhone()">
        </div>

      </div>
      <div class=" flex li">
        <div class="tit">昵称：</div>
        <div class="info">
          <input type="text" v-model="info.nickName" placeholder="请输入昵称">
        </div>
      </div>
      <div class=" flex li">
        <div class="tit">联系QQ：</div>
        <div class="info">
          <input type="text" v-model="info.qq" placeholder="请输入QQ" @change="">
        </div>
      </div>
      <div class=" flex li">
        <div class="tit">联系微信：</div>
        <div class="info">
          <input type="text" v-model="info.weixin" placeholder="请输入微信" @change="">
        </div>
      </div>
      <div class=" btns">
        <el-button type="info" plain class="cancel" @click="getInfo">取消</el-button>
        <el-button type="primary" @click="submit">保存</el-button>
        <!-- <el-button plain type="dange" @click="outlogin">退出登录</el-button> -->
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { Plus } from '@element-plus/icons-vue'
import kefu from '@/api/kefu'
import { ElMessage } from 'element-plus';
export default {
  name: 'zhinan',
  components: { Plus },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      text: ``,
      uploadUrl: '/api/upload/image',
      info: {
        avatar: '', // 头像
        phonenumber: "", // 手机号
        nickName: '', // 昵称
        qq: '', // qq
        weixin: '', // 微信
      }
    });
    


    // 本地获取用户信息
    const getInfo = () => {
      let info = window.localStorage.getItem('userInfo')
      if (info) {
        state.info = JSON.parse(info)
        //console.log("%c Line:47 🌶 state.info", "color:#7f2b82", state.info);
      } else {
        route.replace({ path: '/login' })
      }
    }
    const outlogin = () => {
      ElMessageBox.confirm(
        '确定退出登录吗？',
        '提示',
        {
          showCancelButton: false,
          'confirm-button-text': '确定',
          type: 'warning'
        }
      ).then(() => {
        window.localStorage.clear()
        route.push({ path: '/login' });
      })
    }
    // 修改用户信息
    const submit = () => {
      kefu.updateZeuser(state.info).then(res => {
        ElMessage({
          type: 'success',
          message: '修改成功'
        })
        // 对state.info.nickName判断是否为手机号或大于11位的数字，是就私密处理
        if (state.info.nickName.length > 11 || !isNaN(state.info.nickName)) {
          state.info.nickName = state.info.nickName.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2')
          window.localStorage.setItem('userInfo', JSON.stringify(state.info))
        } else {
          // 本地存储用户信息
          window.localStorage.setItem('userInfo', JSON.stringify(state.info))
        }
        // 刷新网页
        setTimeout(() => {
          window.location.reload()
        }, 1000);
      })
    }

    const handleAvatarSuccess = (file) => {
      //console.log("%c Line:45 🌶 file", "color:#7f2b82", file);
      state.info.avatar = file.data.filePath

    }

    route.afterEach((to) => {
      // //console.log('to', to)
      // const { id } = to.query
      window.scrollTo(0, 0);
      // if (id && to.name == 'add') {
      //   state.name = '编辑商品'
      // }
    });

    // 手机号校验
    const changPhone = () => {
      let reg = /^1[3|4|5|7|8][0-9]{9}$/;
      if (!reg.test(state.info.phonenumber)) {
        alert('请输入正确的手机号');
        state.info.phonenumber = '';
      }
    }

    // 邮箱校验
    const changEmail = () => {
      let reg = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
      if (!reg.test(state.info.email)) {
        alert('请输入正确的邮箱');
        state.info.email = '';
      }
    }

    onMounted(() => {
      // 获取本地存储的用户信息
      getInfo()
    });

    return {
      ...toRefs(state),
      handleAvatarSuccess,
      getInfo,
      submit,
      outlogin,
      changPhone,
      changEmail
    };
  }
};
</script>

<style scoped lang="less">
.main {
  font-size: 16px;
  background-color: #fff;

  .top {
    width: 100%;
    height: 50px;
    background-color: #434343;
    display: flex;
    align-items: center;
    border-radius: 10px 10px 0 0;

    img {
      width: 24px;
      height: 24px;
      display: block;
      margin-right: 5px;
      margin-left: 8px;
      margin-top: 3px;
    }

    span {
      font-size: 18px;
      color: #fff;
    }
  }
}

// .tit {
//   width: 100%;
//   text-align: center;
//   height: 38px;
//   border-bottom: 1px solid #ffebeb;
// }
.cont {
  padding: 20px;
  line-height: 50px;
  display: flex;
  flex-direction: column;
  align-items: center;

  .li {
    display: flex;
    align-items: center;
    background-color: #f7f7f7;
    border-radius: 5px;
    width: 400px;
    background-color: #f7f7f7;
    margin-bottom: 25px;
    height: 40px;
    padding: 0;

    .info {
      height: 100%;

      input {
        position: relative;
        display: block;
        margin-top: 2%;
        height: 90%;
        background: none;
        outline: none;
        border: none;
        padding: 0;
        margin: 0;
        text-decoration: inherit;
      }
    }
  }

  >div {
    padding: 10px 0;

    .tit {
      width: 100px;
      text-align: center;
    }

    .img {
      width: 60px;
      height: 60px;
      border-radius: 50%;
      background-image: url(../../assets/icon/hanadimg.png);
      background-size: 60px 60px;
      margin-left: 10px;
      position: relative;

      .moren {
        width: 60px;
        height: 60px;
      }

      .editicon {
        pointer-events: none;
        position: absolute;
        bottom: 0;
        left: 0;
        width: 60px;
        height: 30px;
        background-image: url(../../assets/icon/editicon.png);
        background-size: 60px 30px;
      }

      .avatar {
        width: 60px;
        height: 60px;
        border-radius: 50%;
      }
    }
  }

  .btns {
    display: flex;
    justify-content: space-between;

    .el-button {
      width: 170px;
      margin-right: 50px;
    }

    .el-button:nth-child(2) {
      margin-right: 0;
    }
  }
}

:deep(.cancel.el-button) {

  background-color: #E5E5E5 !important;
  border: none;
  color: #666;
}
</style>
