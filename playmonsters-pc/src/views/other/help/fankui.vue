<template>
  <div class="main">
    <div class="tit flex">
      <span>投诉建议 <i></i></span>
      <div></div>
    </div>
    <div class="cont">

      <el-form ref="ruleFormRef" :model="ruleForm" :rules="rules" label-width="auto" class="demo-ruleForm">
        <el-form-item label="反馈类型" prop="type">
          <el-select v-model="ruleForm.type" class="m-2" placeholder="Select" size="large">
            <el-option v-for="item in typeOption" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="详情内容" prop="content">
          <el-input type="textarea" rows="4" v-model="ruleForm.content" placeholder="请输入不低于15个字的详细说明" maxlength="200"
            show-word-limit>
            <template #prefix>
              <el-icon class="el-input__icon">
                <UserFilled />
              </el-icon>
            </template>
          </el-input>

        </el-form-item>
        <el-form-item label="问题图片" prop="imgurl">
          <el-upload class="avatar-uploader" :action="uploadUrl" :show-file-list="true" :on-success="handleAvatarSuccess"
            list-type="picture-card" :file-list="fileList" :limit="5" :on-remove="removeImg" accept=".jpg,.png,.jpeg">
            <el-icon class="avatar-uploader-icon">
              <Plus />
            </el-icon>
          </el-upload>
          <div>支持jpg，png格式，最多上传5张</div>
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input style="width: 250px;" v-model="ruleForm.tel" placeholder="请输入联系电话"></el-input>
        </el-form-item>
        <el-form-item label="联系QQ" prop="qq">
          <el-input style="width: 250px;" v-model="ruleForm.qq" placeholder="请输入联系QQ"></el-input>
        </el-form-item>
        <el-form-item>
          <div class=" btns">
            <el-button type="info" class="cancel" @click="cancel">取消</el-button>
            <el-button type="primary" @click="submitForm">提交</el-button>
          </div>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { useRouter } from 'vue-router';
import { Plus, SuccessFilled, WarningFilled } from '@element-plus/icons-vue'
import kefu from "@/api/kefu"
export default {
  name: 'zhinan',
  components: {
    Plus
  },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      title: '',
      text: ``,
      uploadUrl: '/api/upload/image',
      fileList: [],
      info:{},
      typeOption: [
        {
          name: '改进建议',
          id: 1
        },
        {
          name: '问题反馈',
          id: 2
        },
        {
          name: '投诉举报',
          id: 3
        },
      ]
    })
    const ruleFormRef = ref()
    const ruleForm = reactive({
      qq: '',
      tel: '',
      imgurl: '',
      createId: '',
      title: '',
      content: '',
      type: 1

    })
    const getInfo = () => {
      let info = window.localStorage.getItem('userInfo')
      if (info) {
        state.info = JSON.parse(info)
      } else {
        route.replace({ path: '/login' })
      }
    }

    const rules = reactive<FormRules>({
      tel: [
        { required: true, message: '请输入手机号', trigger: 'blur' },
        { min: 11, max: 11, message: '请输入11位手机号', trigger: 'blur' },
      ],
      qq: [
        { required: true, message: '请输入QQ', trigger: 'blur' }
      ],
      content: [
        { required: true, message: '请输入反馈内容', trigger: 'blur' }
      ],
    })
    const handleAvatarSuccess = (red,file,fileList) => {
      //console.log("%c Line:45 🌶 fileList", "color:#7f2b82", fileList);
      // state.fileList = fileList

    }
    // 详情图片删除
    const removeImg = (response, fileList, file) => {
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      state.fileList.forEach(function (element, key) {
        if (element.url === filePath) {
          state.fileList.splice(key, 1);
        }
      });
    }
    const submitForm = async (formEl: ruleFormRef | undefined) => {
      if (state.fileList && state.fileList.length) {
        let newArr = []
        state.fileList.forEach(img => {
          newArr.push(img.response.data.filePath)
        })
        ruleForm.imgurl = newArr.join(',')
      }
      let info = window.localStorage.getItem('userInfo')
      if (info) {
        state.info = JSON.parse(info)
      }
      ruleForm.createId = state.info.userId
      kefu.addProblem(ruleForm)?.then(res => {
        ElMessageBox.confirm(
          '反馈成功！',
          '错误',
          {
            showCancelButton: false,
            'confirm-button-text': '确定',
            type: 'warning',
            icon: markRaw(SuccessFilled),
          }
        )
        ruleForm.qq = ''
        ruleForm.tel = ''
        ruleForm.imgurl = ''
        ruleForm.content = ''
        ruleForm.type = 1
        state.fileList = []


      }).catch(err => {
        // 异常
        ElMessageBox.confirm(
          '网络错误',
          '错误',
          {
            showCancelButton: false,
            'confirm-button-text': '确定',
            type: 'warning',
            icon: markRaw(WarningFilled),
          }
        )
      })
      // ruleForm
      //console.log("%c Line:104 🍬 ruleForm", "color:#2eafb0", ruleForm);
      //console.log('submit!')
      // await formEl.validate((valid, fields) => {

      //   // if (valid) {

      //   // } else {
      //   //   //console.log('error submit!', fields)
      //   // }
      // })
    }

    onMounted(() => {
      getInfo()

    });
    const cancel = () => {
      ruleForm.qq = ''
      ruleForm.tel = ''
      ruleForm.imgurl = ''
      ruleForm.content = ''
      ruleForm.type = 1
      state.fileList = []
    }

    route.afterEach((to) => {
      // //console.log('to', to)
      // const { id } = to.query
      window.scrollTo(0, 0);
      // if (id && to.name == 'add') {
      //   state.name = '编辑商品'
      // }
    });

    return {
      ...toRefs(state),
      cancel,
      rules,
      ruleFormRef,
      ruleForm,
      submitForm,
      handleAvatarSuccess,
      removeImg,
      getInfo
    };
  }
};
</script>

<style scoped lang="less">
.main {
  font-size: 16px;
  background-color: #fff;
  padding-left: 20px;
  padding-bottom: 20px;
}

.tit {
  width: 100%;

  font-size: 16px;
  font-weight: bold;
  color: @primaryColor;
  line-height: 50px;

  span {
    flex-shrink: 0;
    width: 100px;
    border-top: 4px solid @primaryColor;
    margin-left: -20px;
    text-align: center;
    position: relative;

    i {
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

  div {
    width: 1000px;
    height: 50px;
    background: #F8F8F8;
    border: 1px solid #E5E5E5;
  }
}

.cont {
  padding-top: 20px;

  .btns {
    .el-button {
      width: 140px;
      height: 40px;
      border-radius: 4px;
    }

    .cancel.el-button {
      background: #E5E5E5;
      color: #666;
      border: 0;
    }

  }

}
</style>
