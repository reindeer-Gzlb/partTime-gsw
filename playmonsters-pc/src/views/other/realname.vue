<template>
  <div class="realname">
    <!-- 实名认证 -->
    <TopTitle title="身份认证" />
    <div class="context">
      <div class="left">
        <p style="color: #FF8E00;text-align: center;">信息仅用于身份验证，怪兽玩代售严格保护您的信息安全</p>
        <div class="li">
          <p>姓名</p>
          <input :disabled="realStatus==2" type="text" placeholder="姓名" v-model="userArr.name">
        </div>
        <div class="li">
          <p>身份证号码</p>
          <input :disabled="realStatus==2" type="text" placeholder="身份证号码" v-model="userArr.id">
        </div>
        <div class="li">
          <p class="pone">温馨提示: 根据国家规定，本平台禁止未成年人交易。</p>
          <p class="ptwo">所有用户均须使用有效身份证信息方可交易游戏虚拟物品</p>

        </div>
        <div class="li">
          <button v-if="realStatus==1" @click="submitTo">提交</button>
          <button class="yirenz" v-if="realStatus==2" disabled>已认证</button>
        </div>
      </div>
      <!-- <div class="right">
                <div class="li">
                    <div class="img">
                        <img :src="zheng" alt="" v-if="fileList1.length == 0">
                        <el-upload v-model:file-list="fileList1" action="#" accept="jpg,jpeg,png" list-type="picture-card"
                            :http-request="uploadImg" :limit="1" :on-remove="handleRemove">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </div>
                    <p>身份证正面照</p>
                </div>
                <div class="li">
                    <div class="img">
                        <img :src="fan" alt="" v-if="fileList2.length == 0">
                        <el-upload v-model:file-list="fileList2" action="#" accept="jpg,jpeg,png" list-type="picture-card"
                            :http-request="uploadImg1" :limit="1" :on-remove="handleRemove1">
                            <i class="el-icon-plus"></i>
                        </el-upload>
                    </div>
                    <p>身份证反面照</p>
                </div>
                
            </div> -->
    </div>
  </div>
</template>
<script>
import { reactive, toRefs, onMounted } from 'vue';
import kefu from '@/api/kefu';
import commodityList from '@/components/commodityList.vue'
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'
import infoApi from "@/api/gujia";
import { ElMessage } from 'element-plus'
import zheng from '../../assets/fh-img/zf_0.png'
import fan from '../../assets/fh-img/zf_1.png'
import { Plus } from '@element-plus/icons-vue'
export default {
  name: 'realname',
  components: {
    TopTitle,
    commodityList
  },
  setup() {
    const router = useRouter();
    const state = reactive({
      userInfo: {},
      userArr: {
        name: '',
        id: '',
      },
      fileList1: [],
      fileList2: [],
      zheng: zheng,
      fan: fan,
      realStatus:1

    })

    // 提交信息
    const submitTo = () => {

      // 对姓名和身份证号码进行校验
      // 验证姓名格式，只允许输入中文和英文
      const nameReg = /^[\u4E00-\u9FA5A-Za-z]+$/;
      if (!nameReg.test(state.userArr.name)) {
        ElMessage.error('请输入正确的姓名格式')
        return false;
      }
      // 验证身份证号码格式，只允许输入数字和字母
      const idCardReg = /^[0-9a-zA-Z]+$/;
      if (!idCardReg.test(state.userArr.id)) {
        ElMessage.error('请输入正确的身份证号码格式')
        return false;
      }
      // 验证身份证号格式，只允许输入18位数字或17位数字加X
      const idCardNumberReg = /^\d{17}(\d|X)$/;
      if (!idCardNumberReg.test(state.userArr.id)) {
        ElMessage.error('请输入正确的身份证号格式')
        return false;
      }
      // // 验证身份证正反面是否上传
      // if (state.fileList1.length == 0 || state.fileList2.length == 0) {
      //     ElMessage.error('请上传身份证正反面照片')
      //     return false;
      // }



      var obj = {
        id: state.userInfo.userId,
        name: state.userArr.name,
        idNumber: state.userArr.id,
        // frontIdCardImg: state.fileList1[0].url,
        // reverseIdCardImg: state.fileList2[0].url
      }

      // 提交信息
      kefu.getIdentity(obj).then(res => {
        if (res.code == 200) {
          ElMessage({
            message: '认证成功',
            type: 'success',
          })
          router.push('/index/other/mycenter')
          getUser();
        } else {
          // alert(res.msg);
          ElMessage.error('认证')
        }
      }).catch(e => {
        //console.log("%c Line:41 🥪 e", "color:#b03734", e);
      })
    }
    // 获取用户信息
    const getUser = () => {
      kefu.getUserInfo().then(res => {
        //console.log("%c Line:133 🥓用户信息 res", "color:#f5ce50", res);
        if (res.code == 200) {
          // if (res.data.idCard.length > 11 || !isNaN(res.data.idCard)) {
          //     // 截取res.data.idCard前三位在加上15个*号
          //     // res.data.idCard = res.data.idCard.substring(0, 3) + "***************";
          //     // res.data.idCard = res.data.idCard.replace(/(\d{3})\d{9}(\d{4})/, '$1****$2')
          // }
          // realStatus
          state.userInfo = res.data
          state.realStatus = res.data.realStatus
          window.localStorage.setItem('userInfo', JSON.stringify(res.data))
          // 数据中的身份证正反面图片为空时，显示默认图片
          // if (res.data.frontIdCardImg == null) {
          //   res.data.frontIdCardImg = zheng;
          // }
          // if (res.data.reverseIdCardImg == null) {
          //   res.data.reverseIdCardImg = fan;
          // }
          // state.zheng = res.data.frontIdCardImg;
          // state.fan = res.data.reverseIdCardImg;
          state.userArr.name = res.data.realName;
          state.userArr.id = res.data.idCard;
        }
      })
    }
    onMounted(() => {
      // // 获取本地用户信息
      // let userInfo = JSON.parse(localStorage.getItem("userInfo"));
      // //console.log("%c Line:49 🥪 userInfo", "color:#b03734", userInfo);
      // state.userInfo = userInfo;
      getUser()
    })

    // 正面——图片上传
    const uploadImg = (UploadRequestOptions) => {
      let formData = new FormData();
      formData.append("file", UploadRequestOptions.file);
      // 获取本地token
      let token = localStorage.getItem("token");
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: token
        }
      }
      infoApi.postUpload(formData)
        .then(res => {
          state.fileList1 = [{
            url: res.data.filePath,
            name: res.data.fileName
          }]
          //console.log("%c Line:567 🥪 res", "color:#b03734", res);
        })
        .catch(e => {
          //console.log("%c Line:567 🥪 e", "color:#b03734", e);
        });
    }
    // 移除
    const handleRemove = () => {
      state.fileList1 = []
    }

    // 正面——图片上传
    const uploadImg1 = (UploadRequestOptions) => {
      let formData = new FormData();
      formData.append("file", UploadRequestOptions.file);
      // 获取本地token
      let token = localStorage.getItem("token");
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: token
        }
      }
      infoApi.postUpload(formData)
        .then(res => {
          state.fileList2 = [{
            url: res.data.filePath,
            name: res.data.fileName
          }]
        })
        .catch(e => {
          //console.log("%c Line:567 🥪 e", "color:#b03734", e);
        });
    }
    // 移除
    const handleRemove1 = () => {
      state.fileList1 = []
    }

    return {
      ...toRefs(state),
      submitTo,
      uploadImg,
      handleRemove,
      uploadImg1,
      handleRemove1
    }
  }
}
</script>

<style lang="less" scoped>
.realname {
  width: 100%;
  min-height: 580px;
  background: #fff;

  .context {
    display: flex;
    align-items: flex-end;
    justify-content: center;
    padding: 20px 30px;
    box-sizing: border-box;
    height: 100%;
    align-items: flex-start;

    .left {
      .li {
        p {
          font-size: 17px;
          font-weight: 500;
        }

        input {
          width: 500px;
          height: 50px;
          padding: 0 20px;
          box-sizing: border-box;
          border-color: #000;
        }

        // 设置input输入框焦点状态的样式
        input:focus {
          outline: none;
          border: 1px solid #409eff;
        }


      }

      .li:nth-of-type(3) {
        margin: 20px 30px;

        .pone {
          margin-left: 20px;
        }

        p {
          margin: 0;
          color: #FF8E00;
          line-height: 1.5;
        }
      }

      .li:nth-of-type(4) {
        button {
          margin-top: 30px;
          margin-left: 130px;
          width: 250px;
          height: 50px;
          line-height: 50px;
          background: #FF8E00;
          border-radius: 8px;
          color: #fff;
          cursor: pointer;
          border: none;
        }
        .yirenz{
          background-color: #fa8791;
        }
      }
    }

    .right {
      display: flex;
      flex-direction: column;
      align-items: center;

      .li {
        .img {
          position: relative;
          width: 233px;
          height: 135px;

          img {
            width: 100%;
            height: 100%;
          }
        }

        p {
          margin: 0;
          font-size: 17px;
          font-weight: 500;
          text-align: center;
          line-height: 3;
        }
      }

      .li:nth-of-type(3) {
        p {
          font-size: 14px;
          color: #FF8E00;
        }
      }
    }
  }
}

:deep(.el-upload) {
  width: 233px !important;
  height: 135px !important;
  background-color: rgba(0, 0, 0, 0) !important;
  position: absolute !important;
  top: 0;
  // border: none !important;
}

:deep(.el-carousel__indicators) {
  display: none !important;
}</style>