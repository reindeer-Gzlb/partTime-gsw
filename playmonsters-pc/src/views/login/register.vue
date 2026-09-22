<template>
  <div class="main">
    <div class="top">
      <div class="w1200">
        <img class="logo" src="../../assets/img/LOGO_1.png" alt="" @click="goLink('index')">
        <img class="logo" src="../../assets/img/LOGO_2.png" alt="" @click="goLink('index')">
      </div>
    </div>
    <div class="center ">
      <div class="w1000">
        <div class="formbox">
          <div class="tabbox flex fbt">
            <div class="zhdl" :class="{ active: active == 1 }">
              <span v-if="type == 2">忘记密码</span>
              <span v-else>账号注册</span>
              <span class="hengxian"></span>
            </div>
          </div>
          <div class="form">
            <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="auto" class="demo-ruleForm">
              <el-form-item label="" prop="username">
                <el-input v-model="ruleForm.username" placeholder="请输入账号">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <UserFilled />
                    </el-icon>
                  </template>
                </el-input>

              </el-form-item>
              <!-- <el-form-item label="" prop="dxyzm">
                <el-input v-model="ruleForm.dxyzm" placeholder="请输入验证码" style="width: 250px;">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Message />
                    </el-icon>
                  </template>
                </el-input>
                <el-button type="primary" class="yzm-btn" :disabled="timer !== 0" @click="sendYzm">
                  <span v-if="timer">{{ timer }}秒</span>
                  <span v-else>发送验证码</span>
                </el-button>
              </el-form-item> -->
              <el-form-item label="" prop="password">
                <el-input type="password" v-model="ruleForm.password" placeholder="请输入6-16位内的登录密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <GoodsFilled />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" prop="confirmPassword">
                <el-input type="password" v-model="ruleForm.confirmPassword" placeholder="请再次输入密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <GoodsFilled />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" prop="code">
                <el-input v-model="ruleForm.code" placeholder="请输入验证码" style="width: 250px;">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <Message />
                    </el-icon>
                  </template>
                </el-input>
                <img :src="codeImg" alt=""
                  style="height: 40px;width: 100px;vertical-align: top;margin-left: 20px;cursor: pointer;"
                  @click="getImg()">
              </el-form-item>
              <el-form-item v-if="type != 2">
                <div class="tongyi">
                  <el-checkbox v-model="checked1" size="large">
                    <span>我已阅读并接受</span>
                    <a>《用户协议》</a>
                  </el-checkbox>
                </div>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm(ruleFormRef)">

                  <span v-if="type == 2">提交</span>
                  <span v-else>注册</span>
                </el-button>
              </el-form-item>
              <el-form-item>
                <div class="tologin">
                  <span>已有账号，</span>
                  <a href="/#/login">直接登录 &gt;</a>
                </div>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>

    </div>
    <div class="footer">
      <Footer :bgfff="true"></Footer>
    </div>

  </div>
</template>

<script lang="ts">
import { reactive, toRefs, ref, markRaw, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { WarningFilled, UserFilled, GoodsFilled, Message } from '@element-plus/icons-vue'
import Footer from '@/components/Footer.vue';
import kefu from "@/api/kefu"
export default {
  name: 'zhinan',
  components: {
    UserFilled, GoodsFilled, Footer, Message
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      active: 1,
      checked1: false,
      timer: 0,
      type: 1,
      codeImg: '',
    });

    const ruleFormRef = ref<FormInstance>()
    const ruleForm = reactive({
      phonenumber: '',
      password: '',
      confirmPassword: '',
      dxyzm: '',
      yzmRankey: '',
      code: '',
      username: '',
      uuid: ''
    })

    const rules = reactive<FormRules>({
      username: [
        { required: true, message: '请输入账号', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ],
      confirmPassword: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ],
      code: [
        { required: true, message: '请输入验证码', trigger: 'blur' }
      ],
    })
    // 
    onMounted(() => {
      localStorage.clear()
      //console.log("%c Line:300 🍬 注册、忘记密码 router.currentRoute.value.params", "color:#b03734", router.currentRoute.value.query);
      state.type = router.currentRoute.value.query.type
      getImg()
    });
    // 获取验证码图片
    const getImg = () => {
      kefu.getCodeImg().then(res => {
        state.codeImg = "data:image/gif;base64," + res.img;
        ruleForm.uuid = res.uuid
      })
    }
    // 发送验证码
    const sendYzm = () => {
      if (!ruleForm.phonenumber || ruleForm.phonenumber.length != 11) {
        ElMessageBox.confirm(
          '手机号填写不正确',
          '错误',
          {
            showCancelButton: false,
            'confirm-button-text': '确定',
            type: 'warning',
            icon: markRaw(WarningFilled),
          }
        )
        return
      }
      state.timer = 60
      changeTimer()
      // ruleForm.phonenumber
      //console.log("%c Line:132 🍏 ruleForm.phonenumber", "color:#3f7cff", ruleForm.phonenumber);
      kefu.sendSms(ruleForm.phonenumber)?.then(res => {
        //console.log("%c Line:119 🍎 res", "color:#b03734", res);
        if (res.code == 200) {
          ruleForm.yzmRankey = res.data.yzmRankey
        } else {
          ElMessageBox.confirm(
            res.msg,
            '错误',
            {
              showCancelButton: false,
              'confirm-button-text': '确定',
              type: 'warning',
              icon: markRaw(WarningFilled),
            }
          )
        }

      })
    }
    // 倒计时
    const changeTimer = () => {
      if (state.timer > 0) {
        state.timer -= 1
        setTimeout(() => {
          changeTimer()
        }, 1000)
      }

    }

    const submitForm = async (formEl: FormInstance | undefined) => {
      if (!formEl) return
      await formEl.validate((valid, fields) => {
        if (valid) {

          if (ruleForm.password != ruleForm.confirmPassword) {
            ElMessageBox.confirm(
              '两次输入的密码不一致',
              '错误',
              {
                showCancelButton: false,
                'confirm-button-text': '确定',
                type: 'warning',
                icon: markRaw(WarningFilled),
              }
            )
            return
          }
          if (state.type == 2) {
            // 忘记密码
            kefu.SmsforPwd(ruleForm)?.then(res => {
              //console.log("%c Line:105 🧀 res", "color:#42b983", res);
              if (res.code && res.code == 500) {
                ElMessage({
                  message: res.msg,
                  type: 'success',
                })
                return
              }
              ElMessage({
                message: "重置密码成功",
                type: 'success',
              })
              window.localStorage.setItem('userInfo', JSON.stringify(res))
              setTimeout(() => {
                router.push({ path: '/login' })
              }, 500)
            }).catch(err => {
              ElMessageBox.confirm(
                '网络异常，请稍后重试！',
                '错误',
                {
                  showCancelButton: false,
                  'confirm-button-text': '确定',
                  type: 'warning',
                  icon: markRaw(WarningFilled),
                }
              )
            })

          } else {
            // 注册
            if (!state.checked1 && state.type != 2) {
              ElMessageBox.confirm(
                '请阅读并接受《用户协议》',
                '错误',
                {
                  showCancelButton: false,
                  'confirm-button-text': '确定',
                  type: 'warning',
                  icon: markRaw(WarningFilled),
                }
              )
              return
            }
            kefu.register(ruleForm)?.then(res => {
              //console.log("%c Line:105 🧀 res", "color:#42b983", res);
              if (res.code && res.code == 500) {
                ElMessage({
                  message: res.msg,
                  type: 'success',
                })
                return
              }
              ElMessage({
                message: "注册成功！",
                type: 'success',
              })
              window.localStorage.setItem('userInfo', JSON.stringify(res))
              setTimeout(() => {
                router.push({ path: '/login' })
              }, 500)
            }).catch(err => {
              ElMessageBox.confirm(
                '网络异常，请稍后重试！',
                '错误',
                {
                  showCancelButton: false,
                  'confirm-button-text': '确定',
                  type: 'warning',
                  icon: markRaw(WarningFilled),
                }
              )
            })
          }

        } else {
          //console.log('error submit!', fields)
        }
      })
    }
    // 跳转
    const goLink = (link: any, query: any) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    }


    return {
      ...toRefs(state),
      submitForm,
      rules,
      ruleFormRef,
      ruleForm,
      changeTimer,
      sendYzm,
      goLink,
      getImg
    };
  }
};
</script>

<style scoped lang="less">
div {
  box-sizing: border-box;
}

.footer {
  margin-top: -10px;
}

.main {
  font-size: 16px;
  width: 100%;
}

.logo {
  width: 200px;
  cursor: pointer;

}

.logo:nth-child(1) {
  margin-right: 0px;
  width: 115px;
}

.top {
  box-sizing: border-box;
  // height: 144px;
  background: #FFFFFF;
  padding-top: 0px;

  .w1000 {
    img {
      vertical-align: middle;
    }

    img:first-child {
      height: 80px;
      // width: 232px;
      // 鼠标样式
    }
  }
}

.center {
  height: calc(100vh - 120px - 175px);
  background: url(../../assets/icon/bg-login.png);
  background-size: 1928px 100%;
  background-position: center center;

  .w1000 {
    position: relative;
  }

  .formbox {
    width: 430px;
    height: 540px;
    background: #FFFFFF;
    border-radius: 20px;
    position: absolute;
    right: 0;
    top: 50%;
    transform: translateY(10%);
    padding: 30px;

    .tabbox {
      height: 70px;

      >div {
        width: 100%;
        text-align: center;
        font-size: 24px;
        color: #666;
        position: relative;
        cursor: pointer;

        &.active {
          font-weight: bold;
          color: @primaryColor;

          .hengxian {
            display: block;
            height: 5px;
            width: 50px;
            border-radius: 3px;
            background-color: @primaryColor;
            position: absolute;
            top: 36px;
            left: calc(50% - 25px);
          }
        }
      }
    }

    .el-input {
      width: 370px;
      // text-indent: 100px;

    }

    .el-input__icon {
      color: @primaryColor;
      font-size: 20px;
      border-right: 1px solid #ededed;
      padding: 10px 10px;
    }

    .tologin {
      cursor: pointer;
      text-align: center;
      font-size: 14px;
      color: #999999;
      line-height: 18px;
    }
  }
}

:deep(.el-input__inner) {
  padding-left: 60px !important;
}

.el-button {
  width: 370px;
  height: 50px;
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
}</style>
