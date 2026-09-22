<template>
  <div class="main">
    <div class="top">
      <Header> s</Header>
    </div>
    <div class="center ">
      <div class="w1000">
        <div class="formbox">
          <div class="tabbox flex fbt">
            <div class="zhdl" :class="{ active: active == 1 }" @click="active = 1">
              账号登录
              <span class="hengxian"></span>
            </div>
            <div class="sjdl" :class="{ active: active == 2 }" @click="active = 2">
              手机登录
              <span class="hengxian"></span>
            </div>
          </div>
          <div class="form">
            <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="auto" class="demo-ruleForm">
              <el-form-item label="" prop="username">
                <el-input v-model="ruleForm.username" placeholder="请输入账号/手机号">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <UserFilled />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" prop="password" v-if="active == 1">
                <el-input type="password" v-model="ruleForm.password" placeholder="请输入登录密码">
                  <template #prefix>
                    <el-icon class="el-input__icon">
                      <GoodsFilled />
                    </el-icon>
                  </template>
                </el-input>
              </el-form-item>
              <el-form-item label="" prop="code" v-if="active == 1">
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

              <el-form-item label="" prop="dxyzm" v-if="active == 2">
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
              </el-form-item>
              <el-form-item>
                <div class="flex" style="flex-direction: row-reverse;">
                  <!-- <a href="/#/register?type=2" class="zhuce">忘记密码</a> -->
                  <a href="/#/register" class="zhuce">注册账号</a>
                </div>

              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm(ruleFormRef)">
                  登录
                </el-button>
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
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { useRouter } from 'vue-router';
import { WarningFilled, UserFilled, GoodsFilled, Message } from '@element-plus/icons-vue'
import Footer from '@/components/Footer.vue';
import Header from '@/components/Header.vue';

import kefu from "@/api/kefu"
import md5 from "js-md5";
export default {
  name: 'zhinan',
  components: {
    UserFilled, GoodsFilled, Footer, Message, Header
  },
  setup(props, ctx) {
    const router = useRouter();
    const state = reactive({
      active: 1,
      timer: 0,
      codeImg: '',
    });

    const ruleFormRef = ref<FormInstance>()
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
    })

    const rules = reactive<FormRules>({
      username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
      ],
      password: [
        { required: true, message: '请输入密码', trigger: 'blur' }
      ],
      code: [
        { required: true, message: '请输入验证码', trigger: 'blur' }
      ]
    })
    onMounted(() => {
      localStorage.clear()
      getImg()
    })
    // 获取验证码图片
    const getImg = () => {
      kefu.getCodeImg().then(res => {
        state.codeImg = "data:image/gif;base64," + res.img;
        ruleForm.uuid = res.uuid
      })
    }
    // 获取用户信息
    const getUser = () => {
      kefu.getUserInfo().then(res => {
        //console.log("%c Line:133 🥓用户信息 res", "color:#f5ce50", res);
        if (res.code == 200) {
          window.localStorage.setItem('userInfo', JSON.stringify(res.data))
          setTimeout(() => {
            router.push({ path: '/index/home' })
          }, 500)
        }

      })
    }
    const sendYzm = () => {

      if (!ruleForm.username || ruleForm.username.length != 11) {
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
      // ruleForm.username
      //console.log("%c Line:132 🍏 ruleForm.username", "color:#3f7cff", ruleForm.username);
      kefu.sendSms(ruleForm.username)?.then((res: { code: number; data: { yzmRankey: string; }; msg: any; }) => {
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
      await formEl.validate((valid: any, fields: any) => {
        if (valid) {
          // md5(ruleForm.password)
          // //console.log("%c Line:104 🍎 md5(ruleForm.password)", "color:#e41a6a", md5(ruleForm.password));
          if (state.active == 1) {
            kefu.login(ruleForm).then(res => {
              //console.log("%c Line:105 🧀 res", "color:#42b983", res);
              if (res.code && res.code == 500) {
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
                return
              }
              ElMessage({
                message: '登录成功！',
                type: 'success',
              })
              getUser()
              window.localStorage.setItem('token', JSON.stringify(res.token))

            }).catch(err => {
              // 异常
              ElMessageBox.confirm(
                '账号或密码错误',
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
          // else{
          //   // 验证码登录
          //   kefu.yzmLogin(ruleForm).then(res => {
          //   //console.log("%c Line:105 🧀 res", "color:#42b983", res);
          //   if (res.code && res.code == 500) {
          //     ElMessageBox.confirm(
          //       res.msg,
          //       '错误',
          //       {
          //         showCancelButton: false,
          //         'confirm-button-text': '确定',
          //         type: 'warning',
          //         icon: markRaw(WarningFilled),
          //       }
          //     )
          //     return
          //   }
          //   ElMessage({
          //     message: '登录成功！',
          //     type: 'success',
          //   })
          //   window.localStorage.setItem('userInfo', JSON.stringify(res))
          //   setTimeout(() => {
          //     router.push({ path: '/index/home' })
          //   }, 500)
          // }).catch(err => {
          //   // 异常
          //   ElMessageBox.confirm(
          //     '账号或密码错误',
          //     '错误',
          //     {
          //       showCancelButton: false,
          //       'confirm-button-text': '确定',
          //       type: 'warning',
          //       icon: markRaw(WarningFilled),
          //     }
          //   )
          // })
          // }

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
      sendYzm,
      getImg,
      goLink
    };
  }
};
</script>

<style scoped lang="less">
div {
  box-sizing: border-box;
}

.footer {
  margin-top: 0px;
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
  width: 100px;
}

.top {
  box-sizing: border-box;
  background: #FFFFFF;

  .w1000 {
    img {
      vertical-align: middle;

    }

    img:first-child {
      height: 80px;
      // width: 232px;
    }

    // img:last-child {
    //   width: 248px;
    //   height: 25px;
    // }
  }
}

.center {
  height: calc(100vh - 260px - 200px);
  background: url(../../assets/fh-img/bg3.png);
  background-position: center center;
  background-size: 100% 100%;
  background-repeat: no-repeat;

  .w1000 {
    position: relative;
  }

  .formbox {
    width: 430px;
    height: 400px;
    background: #FFFFFF;
    border-radius: 20px;
    position: absolute;
    right: 0;
    top: 40%;
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

:deep(.el-input__inner) {
  padding-left: 60px;
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
}

</style>
