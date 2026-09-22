<template>
  <div class="login">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
      <h3 class="title">怪兽玩代售账号管理系统</h3>
      <el-form-item prop="username"  v-if="loginType == 'account'">
        <el-input name="account" v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="password" v-if="loginType == 'account'">
        <el-input name="psd" v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"
          @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="code" v-if="captchaOnOff&&loginType == 'account'">
        <el-input v-model="loginForm.code" auto-complete="off" placeholder="验证码" style="width: 63%"
          @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <img :src="codeUrl" @click="getCode" class="login-code-img" />
        </div>
      </el-form-item>
      <el-form-item prop="phone" v-if="loginType == 'phone'">
        <el-input name="phone" v-model="loginForm.phone" type="text" auto-complete="off" placeholder="手机号">
          <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
        </el-input>
      </el-form-item>
      <el-form-item prop="dxyzm" v-if="loginType == 'phone'">
        <el-input name="phonecode" v-model="loginForm.dxyzm" auto-complete="off" placeholder="短信验证码" style="width: 63%"
          @keyup.enter.native="handleLogin">
          <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
        </el-input>
        <div class="login-code">
          <el-button type="primary" class="yzm-btn" :disabled="timer !== 0" @click="sendYzm">
            <span v-if="timer">{{ timer }}秒</span>
            <span v-else>发送验证码</span>
          </el-button>
        </div>
      </el-form-item>
      <el-checkbox v-if="loginType == 'account'" v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
      <el-form-item style="width:100%;">
        <el-button :loading="loading" size="medium" type="primary" style="width:100%;"
          @click.native.prevent="handleLogin">
          <span v-if="!loading">登 录</span>
          <span v-else>登 录 中...</span>
        </el-button>
        <div>
          <div v-if="loginType == 'account'" class="link-type" style="text-align: center;" @click="switchLogin('phone')">
            手机号码登录</div>
          <div v-if="loginType == 'phone'" class="link-type" style="text-align: center;" @click="switchLogin('account')">
            账号密码登录</div>
          <!-- <h3>
            注册成功后+V
            <span style="coloe:red">jiaoyitang88</span> 授权角色
          </h3> -->
          <div class="">
            <a class="link-type" style="margin-right: 15px;" href="#">电脑端选号站</a>
            <a class="link-type" href="#">移动端选号站</a>
          </div>
          <!-- <router-link class="link-type" :to="'/register'">立即注册</router-link> -->
        </div>
      </el-form-item>
    </el-form>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2018-2022 ruoyi.vip All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg, sendSMSCode } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from "@/utils/jsencrypt";
import { getUserProfile } from "@/api/system/user";
export default {
  name: "Login",
  data() {
    var phoneCheck = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'));
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('手机号格式不正确'));
      } else {
        callback()
      }
    };
    return {
      loginType: 'account',
      timer: 0,
      codeUrl: "",
      cookiePassword: "",
      loginForm: {
        username: "",
        password: "",
        rememberMe: false,
        code: "",
        uuid: "",
        phone: '',
        yzmRankey: '',
        dxyzm: ''
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }],
        phone: [{ validator: phoneCheck, trigger: 'blur' } ],
        dxyzm: [{ required: true, trigger: "blur", message: "请输入验证码" }],

      },
      loading: false,
      // 验证码开关
      captchaOnOff: true,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function (route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  created() {
    this.getCode();
    // this.getCookie();
  },
  methods: {
    // 验证码相关 start
    switchLogin(txt) {
      this.loginType = txt
    },
    // 发送短信验证码
    sendYzm() {
      if (!/^1[3-9]\d{9}$/.test(this.loginForm.phone)) {
        this.$message({
          type: "error",
          message: '手机号错误！'
        })
        return
      }
      this.timer = 60
      this.changeTimer()
      let obj = {
        phone: this.loginForm.phone,
        type: 'phoneLogin'
      }
      sendSMSCode(obj).then(res => {
        this.$message({
          type: "success",
          message: res.msg
        })
        this.loginForm.yzmRankey = res.data.yzmRankey
      })
    },
    changeTimer() {
      if (this.timer > 0) {
        this.timer -= 1
        setTimeout(() => {
          this.changeTimer()
        }, 1000)
      }
    },
    // 验证码相关end
    getCode() {
      getCodeImg().then(res => {
        this.captchaOnOff =
          res.captchaOnOff === undefined ? true : res.captchaOnOff;
        if (this.captchaOnOff) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("username");
      const password = Cookies.get("password");
      const rememberMe = Cookies.get("rememberMe");
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password:
          password === undefined ? this.loginForm.password : decrypt(password),
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {

          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("username", this.loginForm.username, { expires: 30 });
            Cookies.set("password", encrypt(this.loginForm.password), {
              expires: 30
            });
            Cookies.set("rememberMe", this.loginForm.rememberMe, {
              expires: 30
            });
          } else {
            Cookies.remove("username");
            Cookies.remove("password");
            Cookies.remove("rememberMe");
          }
          this.loginForm.loginType = this.loginType
          this.$store
            .dispatch("Login", this.loginForm)
            .then(() => {
              getUserProfile().then(response => {
                if (response.roleGroup.indexOf('商户中心') > -1) {
                  console.log('零售客户')
                  this.$router.push({ name: "Business/myAccount" }).catch(() => { });
                } else {
                  console.log('非零售客户')
                  this.$router.push({ path: this.redirect || "/" }).catch(() => { });
                }
              })
            })
            .catch(() => {
              this.loading = false;
              if (this.captchaOnOff) {
                this.getCode();
              }
            });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: cover;
}

.title {
  margin: 0px auto 30px auto;
  text-align: center;
  color: #707070;
}

.login-form {
  border-radius: 6px;
  background: #ffffff;
  width: 400px;
  padding: 25px 25px 5px 25px;

  .el-input {
    height: 38px;

    input {
      height: 38px;
    }
  }

  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}

.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}

.login-code {
  width: 33%;
  height: 38px;
  float: right;

  img {
    cursor: pointer;
    vertical-align: middle;
  }
}

.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}

.login-code-img {
  height: 38px;
}
</style>
