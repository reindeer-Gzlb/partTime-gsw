<template>
  <el-dialog :title="title" :visible.sync="centerDialogVisible" width="430px" center>
    <div class="web-box">
      <el-form :model="ruleForm" ref="ruleForm" label-width="100px" :rules="rules" class="demo-ruleForm">
        <el-form-item label="接口:" prop="config">
          <el-select v-model="ruleForm.config" placeholder="请选择接口" clearable size="small" @change="configChange">
            <el-option v-for="dict in pushApiList" :key="dict.id" :label="dict.name" :value="dict.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="供货人:" prop="createId">
          <el-select v-model="ruleForm.createId" placeholder="请选择供货人" clearable size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态:" prop="saleStatus" v-if="!flag">
          <el-select v-model="ruleForm.saleStatus" placeholder="请选择状态" clearable size="small">
            <el-option v-for="dict in statusOptions" :key="dict.value" :label="dict.name" :value="dict.value" />
          </el-select>
        </el-form-item>
        <div class="center">
          <el-button @click="close('ruleForm')">取消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')" v-loading="loading">提交</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>

import {
  getSupplyList,
  getPushList
} from "@/api/info/info";
export default {
  name: "delPushedDialog",
  data() {
    return {
      centerDialogVisible: false,
      loading:false,
      flag:'',
      title:'',
      ruleForm: {
        createId: '',
        configId: '',
        saleStatus: '',
        name:'',
        config:''
      },
      supplyList: [],
      pushApiList:[],
      statusOptions: [
        {
          value: 0,
          name: '待审核'
        },
        {
          value: 1,
          name: '未出售'
        },
        {
          value: 2,
          name: '出售中'
        },
        {
          value: 3,
          name: '已售'
        },
        {
          value: 4,
          name: '问题账号'
        },
        {
          value: 5,
          name: '找回账号'
        },
        {
          value: 6,
          name: '超时未售'
        },
        {
          value: 7,
          name: '自售账号'
        },
        {
          value: 8,
          name: '已下架'
        }
      ],
      rules: {
        // createId: [
        //   { required: true, message: '请选择', trigger: 'change' }
        // ],
        // saleStatus: [
        //   { required: true, message: '请选择', trigger: 'change' }
        // ],
      }
    };
  },
  created() {
    this.getSupplyList()
      this.getPushList()
  },
  methods: {
    open(row,flag) {
      this.flag = flag
      this.title = row.title
      this.ruleForm = {
        createId: '',
        configId: '',
        saleStatus: '',
        name:'',
        config:''
      }
      this.centerDialogVisible = true
    },
    close(){
      this.ruleForm = {
        createId: '',
        configId: '',
        saleStatus: '',
        name:'',
        config:''
      }
      this.$refs.ruleForm.resetFields()
      this.centerDialogVisible = false
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // 获取全部接口列表
    getPushList() {
      getPushList().then(res => {
        if (res.code == 200) {
          this.pushApiList = res.rows;
        }
      });
    },
    //接口选择改变
    configChange(e){
      this.ruleForm.configId = e
      let arr = this.pushApiList.filter(t=>{
        return t.id==e
      })
      this.ruleForm.name = arr[0].name
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          if(this.flag=='编辑'){
            this.$parent.batchEdit(this.ruleForm)
          }else if(this.flag=='下架'){
            this.$parent.batchOffshelf(this.ruleForm)
          }else if(this.flag=='错误记录'){
            this.$parent.delWrong(this.ruleForm)
          }else{
            // 删除已推送
            this.$parent.delPushed(this.ruleForm)
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
  }
};
</script>
<style scoped lang="css">
/* .web-box {
  width: 600px;
  height: 500px;
  overflow: hidden;
  
} */
.center{
  width: 100%;
    text-align: center;
  }
</style>
