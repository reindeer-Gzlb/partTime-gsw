<template>
  <div class="main">
    <div style="margin-top: 33px; margin-left: 50px; margin-bottom: 81px">
      <img style="width: 126px; height: 81px" src="@/assets/index/logoname.png" alt="" />
    </div>
    <!-- <div class="top">
      <Header> </Header>
    </div> -->
    <div class="center">
      <!-- 登录 -->
      <div class="logo" @click="goLink('/index/home')"></div>
      <div class="formbox" v-show="show == 1">
        <el-tabs v-model="activeName" class="demo-tabs">
          <el-tab-pane label="验证码登录" name="first">
            <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
            >
              <el-form-item label="">
                <el-input v-model="yzm.phone" placeholder="请输入账号/手机号">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <User />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" v-if="active == 1">
                <el-input type="password" v-model="yzm.code" placeholder="请输入验证码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Message />
                    </el-icon>
                  </template>
                </el-input>
                <button class="yanzhengma" :disabled="timer !== 0" @click="sendYzm">
                  <span v-if="timer">{{ timer }}秒</span> <span v-else>发送验证码</span>
                </button>
              </el-form-item>
              <el-form-item>
                <div class="con">
                  <div class="le"></div>
                  <div class="ri" @click="show = 2" v-text="show == 1 ? '忘记密码?' : '登录'"></div>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button
                  style="background-color: #b42c1d !important; border: none !important"
                  type="primary"
                  @click="submitYzm"
                >
                  登录
                </el-button>
              </el-form-item>
              <el-form-item>
                <div class="bom">
                  <el-checkbox v-model="checked" size="large" />
                  <div class="text">
                    已阅读并同意怪兽玩代售
                    <span @click="getNoticeByType('yonghuxieyi')">《用户协议》</span> 和
                    <span @click="getNoticeByType('yinsizhengce')">《隐私政策》</span>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="账号密码" name="second">
            <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
            >
              <el-form-item label="">
                <el-input v-model="yzm.phone" placeholder="请输入账号/手机号">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <User />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" v-if="active == 1">
                <el-input type="password" v-model="password" placeholder="请输入登录密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Lock />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" v-if="active == 1">
                <el-input v-model="uuid" placeholder="请输入验证码" style="width: 250px">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Message />
                    </el-icon>
                  </template>
                </el-input>
                <img
                  :src="codeImg"
                  alt=""
                  style="
                    height: 40px;
                    width: 100px;
                    vertical-align: top;
                    margin-left: 20px;
                    cursor: pointer;
                  "
                  @click="getImg()"
                />
              </el-form-item>
              <el-form-item>
                <div class="con">
                  <div class="le"></div>
                  <div class="ri" @click="show = 2" v-text="show == 1 ? '忘记密码?' : '登录'"></div>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button
                  style="background-color: #b42c1d !important; border: none !important"
                  type="primary"
                  @click="submitAccount()"
                >
                  登录
                </el-button>
              </el-form-item>
              <el-form-item>
                <div class="bom">
                  <el-checkbox v-model="checked" size="large" />
                  <div class="text" @click="checked = !checked">
                    已阅读并同意怪兽玩代售
                    <span @click="getNoticeByType('yonghuxieyi')">《用户协议》</span> 和
                    <span @click="getNoticeByType('yinsizhengce')">《隐私政策》</span>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>

      <!-- 忘记密码 -->
      <div class="formbox" v-show="show == 2">
        <el-tabs v-model="activeName1" class="demo-tabs">
          <el-tab-pane label="重置密码" name="first">
            <el-form
              ref="ruleFormRef"
              :model="ruleForm"
              :rules="rules"
              label-width="auto"
              class="demo-ruleForm"
            >
              <el-form-item label="">
                <el-input v-model="yzm.phone" placeholder="请输入账号/手机号">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <User />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>

              <el-form-item label="">
                <el-input type="password" v-model="yzm.code" placeholder="请输入验证码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Message />
                    </el-icon>
                  </template>
                </el-input>
                <button class="yanzhengma" :disabled="timer !== 0" @click="sendYzm">
                  <span v-if="timer">{{ timer }}秒</span> <span v-else>发送验证码</span>
                </button>
              </el-form-item>

              <el-form-item>
                <div class="con">
                  <div class="le"></div>
                  <div class="ri" @click="show = 1" v-text="show == 1 ? '忘记密码?' : '登录'"></div>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button
                  style="background-color: #b42c1d !important; border: none !important"
                  type="primary"
                  @click="xiayibu"
                >
                  下一步
                </el-button>
              </el-form-item>
              <el-form-item>
                <div class="bom">
                  <el-checkbox v-model="checked" size="large" />
                  <div class="text">
                    已阅读并同意怪兽玩代售
                    <span @click="getNoticeByType('yonghuxieyi')">《用户协议》</span> 和
                    <span @click="getNoticeByType('yinsizhengce')">《隐私政策》</span>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>

      <!-- 设置新密码 -->
      <div class="formbox" v-show="show == 4">
        <el-tabs v-model="activeName3" class="demo-tabs">
          <el-tab-pane label="设置登录密码" name="first">
            <el-form ref="ruleFormRef" :model="ruleForm" label-width="auto" class="demo-ruleForm">
              <el-form-item label="">
                <el-input v-model="password0" type="password" placeholder="请设置登录密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Lock />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" v-if="active == 1">
                <el-input type="password" v-model="password" placeholder="请再次输入登录密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Lock />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>

              <el-form-item>
                <div class="con">
                  <div class="le"></div>
                  <div class="ri" @click="show = 2" v-text="show == 3 ? '登录' : '忘记密码?'"></div>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button
                  @click="findPassword"
                  style="background-color: #b42c1d !important; border: none !important"
                  type="primary"
                >
                  登录
                </el-button>
              </el-form-item>
              <el-form-item>
                <div class="bom">
                  <el-checkbox v-model="checked" size="large" />
                  <div class="text">
                    已阅读并同意怪兽玩代售 <span>《用户协议》</span> 和 <span>《隐私政策》</span>
                  </div>
                </div>
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
    <!-- <div class="footer">
      <Footer :bgfff="true"></Footer>
    </div> -->
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { useRouter } from 'vue-router';
import {
  WarningFilled,
  UserFilled,
  User,
  Lock,
  GoodsFilled,
  Message
} from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';
import ElMessageBox from '../../components/ElMessageBox.vue';
import kefu from '@/api/kefu';
import md5 from 'js-md5';
import { stat } from 'fs';
export default {
  name: 'zhinan',
  components: {
    UserFilled,
    GoodsFilled,
    Footer,
    Message,
    User,
    Header,
    Lock,
    ElMessageBox
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      is_show1: false,
      is_show2: false,
      yonghuxieyi: [],
      yinsizhengce: [],

      active: 1,
      timer: 0,
      codeImg: '',
      activeName: 'first',
      activeName1: 'first',
      activeName2: 'first',
      activeName3: 'first',
      show: 1,

      checked: false, // 是否同意协议
      // 验证码登录
      yzm: {
        phone: '', // 手机号
        code: '' // 验证码
      },
      yzmRankey: '', // 验证码key
      password0: '', // 密码0
      password: '', // 密码
      uuid: '' // 验证码uuid
    });

    const ruleFormRef = ref<FormInstance>();
    /**code:'',
          username:'',
          password:'',
          uuid:'' */
    const ruleForm = reactive({
      username: '',
      password: '',
      dxyzm: '',
      yzmRankey: '',
      uuid: '',
      code: ''
    });

    const rules = reactive<FormRules>({
      username: [{ required: true, message: '请输入手机号码', trigger: 'blur' }],
      password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
      code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
    });
    onMounted(() => {
      localStorage.clear();
      getImg();
    });
    // 获取验证码图片
    const getImg = () => {
      kefu.getCodeImg().then((res) => {
        state.codeImg = 'data:image/gif;base64,' + res.img;
        ruleForm.uuid = res.uuid;
      });
    };
    // 获取用户信息
    const getUser = () => {
      kefu.getUserInfo().then((res) => {
        //console.log("%c Line:133 🥓用户信息 res", "color:#f5ce50", res);
        if (res.code == 200) {
          window.localStorage.setItem('userInfo', JSON.stringify(res.data));
          setTimeout(() => {
            router.push({ path: '/index/home' });
          }, 500);
        }
      });
    };

    // 倒计时
    const changeTimer = () => {
      if (state.timer > 0) {
        state.timer -= 1;
        setTimeout(() => {
          changeTimer();
        }, 1000);
      }
    };

    // 跳转
    const goLink = (link: any, query: any) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };

    // 发送验证码
    const sendYzm = () => {
      state.timer = 60;
      changeTimer();
      // ruleForm.username
      //console.log("%c Line:132 🍏 ruleForm.username", "color:#3f7cff", ruleForm.username);
      var obj: object = {
        phone: state.yzm.phone,
        type: 'phoneLogin'
      };
      kefu.sendSms(obj).then((res) => {
        if (res.code == 200) {
          ElMessage({
            message: '验证码发送成功',
            type: 'success'
          });
          state.yzmRankey = res.data.yzmRankey;
        } else {
          ElMessage({
            message: res.msg,
            type: 'error'
          });
        }
      });
    };
    // 验证码登录
    const submitYzm = () => {
      var obj: object = {
        phone: state.yzm.phone,
        dxyzm: state.yzm.code,
        yzmRankey: state.yzmRankey
      };
      if (!state.checked) {
        ElMessage({
          message: '请勾选协议！',
          type: 'error'
        });
        return;
      }
      kefu.loginByPhone(obj).then((res) => {
        if (res.code == 200) {
          ElMessage({
            message: '登录成功！',
            type: 'success'
          });
          getUser();
          window.localStorage.setItem('token', JSON.stringify(res.token));
        } else {
          ElMessage({
            message: res.msg,
            type: 'error'
          });
        }
      });
    };

    // 账号登陆
    const submitAccount = () => {
      var arr = {
        username: state.yzm.phone,
        password: state.password,
        code: state.uuid,
        dxyzm: '',
        yzmRankey: '',
        uuid: ruleForm.uuid
      };
      if (!state.checked) {
        ElMessage({
          message: '请勾选协议！',
          type: 'error'
        });
        return;
      }
      kefu.login(arr).then((res) => {
        if (res.code == 200) {
          ElMessage({
            message: '登录成功！',
            type: 'success'
          });
          getUser();
          window.localStorage.setItem('token', JSON.stringify(res.token));
        } else {
          ElMessage({
            message: res.msg,
            type: 'error'
          });
        }
      });
    };

    // 找回密码
    const findPassword = () => {
      if (state.password0 != state.password) {
        ElMessage({
          message: '两次密码不一致',
          type: 'error'
        });
        return;
      } else if (state.password0 == '' || state.password == '') {
        ElMessage({
          message: '密码不能为空',
          type: 'error'
        });
        return;
      }
      var obj: object = {
        phonenumber: state.yzm.phone, // 手机号
        dxyzm: state.yzm.code, // 验证码
        password: state.password, // 密码
        yzmRankey: state.yzmRankey // 验证码key
      };
      if (!state.checked) {
        ElMessage({
          message: '请勾选协议！',
          type: 'error'
        });
        return;
      }
      kefu.changePasswore(obj).then((res) => {
        // //console.log(res);
        if (res.code == 200) {
          ElMessage({
            message: '修改成功,去登录吧！',
            type: 'success'
          });
          state.show = 1;
        } else {
          alert(res.msg);
        }
      });
    };

    // 下一步
    const xiayibu = () => {
      if (state.yzm.phone == '') {
        ElMessage({
          message: '请输入手机号码',
          type: 'error'
        });
        return;
      } else if (state.yzm.code == '') {
        ElMessage({
          message: '请输入验证码',
          type: 'error'
        });
        return;
      }
      state.show = 4;
    };

    // 关
    const handleChildEvent = (item) => {
      if (item == 1) {
        state.is_show1 = false;
      } else if (item == 2) {
        state.is_show2 = false;
      }
    };

    // 开启弹窗
    const setshow = (item) => {
      if (item == 1) {
        getData(1);
        state.is_show1 = true;
      } else if (item == 2) {
        getData(2);
        state.is_show2 = true;
      }
    };

    const getData = (num) => {
      if (num == 1) {
        kefu.getNotices('yonghuxieyi').then((res) => {
          //console.log("111111111", res)
          if (res.code == 200 || res != []) {
            //console.log("222222222222", res)
            state.yonghuxieyi = res[0];
          }
        });
      } else {
        kefu.getNotices('yinsizhengce').then((res) => {
          //console.log("111111111", res)
          if (res.code == 200 || res != []) {
            //console.log("222222222222", res)
            state.yinsizhengce = res[0];
          }
        });
      }
    };
    // 按类型获取公告列表，并取第一个进行查看详情
    const getNoticeByType = (type) => {
      kefu.getNoticeList(type).then((res) => {
        if (res && res.length) {
          router.push({ path: `/index/articleDetail`, query: { id: res[0].noticeId } });
        }
      });
    };

    onMounted(() => {
      getImg();
    });

    const on = () => {
      return;
    };
    return {
      ...toRefs(state),
      submitAccount,
      rules,
      ruleFormRef,
      ruleForm,
      sendYzm,
      getImg,
      goLink,
      submitYzm,
      xiayibu,
      findPassword,
      on,
      handleChildEvent,
      setshow,
      getNoticeByType
    };
  }
};
</script>

<style scoped lang="less">
div {
  box-sizing: border-box;
}

// .footer {
//   margin-top: 0px;
// }

.main {
  font-size: 16px;
  width: 100%;
  height: 100vh;
  background: linear-gradient(to right, 
#141e30
, #243b55);
  // display: flex;
  // align-items: center;
}

// .logo {
//   width: 200px;
//   cursor: pointer;
// }

// .logo:nth-child(1) {
//   margin-right: 0px;
//   width: 100px;
// }

// .top {
//   box-sizing: border-box;
//   background: #FFFFFF;

//   .w1000 {
//     img {
//       vertical-align: middle;

//     }

//     img:first-child {
//       height: 80px;
//       // width: 232px;
//     }

//     // img:last-child {
//     //   width: 248px;
//     //   height: 25px;
//     // }
//   }
// }

.center {
  height: 620px;
  width: 1100px;
  margin: 0 auto;
  // background-image: url('../../assets/taohaoqu/login-bg.png');
  // background-position: center center;
  // background-repeat: no-repeat;
  // background-size: 100% 100%;
  position: relative;
  margin-top: -50px;
  // .logo {
  //   width: 168px;
  //   height: 70px;
  //   position: absolute;
  //   top: 50px;
  //   left: 50px;
  //   cursor: pointer;
  // }
  .formbox {
    width: 450px;
    min-height: 430px;
    background: #ffffff;
    border-radius: 20px;
    // position: absolute;
    // right: 80px;
    // top: 80px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -55%);
    padding: 30px;
    padding-bottom: 0px;
    .el-input {
      width: 370px;
      // text-indent: 100px;
      overflow: hidden;
    }

    .el-input__icon {
      color: #909399;
      font-size: 20px;
      border-right: 1px solid #ededed;
      padding: 9px 14px;
      margin: 1px;
      margin-left: -4px;
      background-color: #f5f7fa;
      border-radius: 5px;
    }

    .zhuce {
      cursor: pointer;
      text-align: right;
      font-size: 14px;
      color: #999999;
      line-height: 18px;
      margin-left: 10px;
    }
  }
}

.con {
  display: flex;
  align-items: center;
  font-size: 16px;
  justify-content: space-between;

  .le {
    color: #b42c1d;
    cursor: pointer;
  }

  .ri {
    cursor: pointer;
    color: #aaaaaa;
  }
}

:deep(.el-input__inner) {
  padding-left: 60px !important;
  overflow: hidden !important;
  height: 40px;
}

.el-button {
  width: 370px;
  height: 40px;
  background: @primaryColor;
  border-radius: 6px;
}

.el-button.is-disabled {
  background-color: rgb(160, 207, 255);
}

.yzm-btn {
  width: 110px;
  height: 40px;
  border-radius: 6px;
  margin-left: 10px;
}

:deep(.el-tabs__item) {
  font-size: 22px;
  color: #000;
  font-weight: 400;
  padding: 0px 20px;
  height: 40px;
  line-height: 20px;

  &:hover {
    color: #000 !important;
  }
}

:deep(.is-active) {
  color: #b42c1d !important;

  &:hover {
    color: #b42c1d !important;
  }
}

:deep(.el-tabs__active-bar) {
  background-color: #b42c1d;
  height: 3px !important;
  width: 90px !important;
}

:deep(.el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-form-item):nth-of-type(1) {
  margin-top: 20px;
  margin-bottom: 30px;
}

:deep(.el-form-item):nth-of-type(2) {
  margin-bottom: 10px;
  position: relative;

  .yanzhengma {
    position: absolute;
    z-index: 9999999999;
    top: 9px;
    right: 30px;
    color: #b42c1d;
    cursor: pointer;
    background-color: #fff;
    border: none;
    font-size: 14px;
  }
}

:deep(.el-form-item):nth-of-type(3) {
  margin-bottom: 10px;
  margin-top: 30px;
}

:deep(.el-form-item):nth-of-type(4) {
  margin-bottom: 15px;
}

:deep(.el-checkbox__input) {
  span {
    border-color: #ced1d9 !important;

    &:hover {
      border-color: #ced1d9 !important;
    }
  }
}

:deep(.is-checked) {
  span {
    background-color: #b42c1d !important;
    border-color: #b42c1d !important;

    &:hover {
      border-color: #b42c1d !important;
    }
  }
}

.bom {
  display: flex;
  padding: 0px 8px;

  .text {
    margin-left: 8px;

    span {
      color: #3390ff;
      font-size: 14px;
      cursor: pointer;
    }
  }
}

/deep/ .el-checkbox {
  margin-top: -3.9px;
}
</style>
