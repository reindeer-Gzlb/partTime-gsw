<template>
  <!--查看回收商品信息 -->
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <!-- 游戏类型 大区 游戏账号 游戏密码   介绍 回收渠道 -->
        <el-form-item label="游戏类型" prop="iotype">
          <span>{{ form.type }}</span>
        </el-form-item>
        <el-form-item label="区服" prop="iotype">
          <span>{{ form.region }}</span>
        </el-form-item>
        <el-form-item label="期望价格" prop="iotype">
          <span>{{ form.expprice }}</span>
        </el-form-item>
        <el-form-item label="游戏账号" prop="remark">
          <div>{{ form.account }}</div>

        </el-form-item>
        <el-row>
          <el-form-item label="简介" prop="remark">
            <div style="width:640px">{{ form.des }}</div>

          </el-form-item>
        </el-row>
        <!-- <el-row>
          <el-form-item label="手机号" prop="remark">
            <div>{{ form.tel }}</div>
          
          </el-form-item>
        </el-row> -->
        <div class="img-box">
          <div v-for="(item, ind) in list" :key="item" class="lin-item">
            <el-image :src="item" @click="review(ind)"></el-image>
          </div>
        </div>
        <div class="center">
          <el-button @click="close()">关闭</el-button>
        </div>
      </el-form>
    </div>
  </el-dialog>
</template>

<script>
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "addFina",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "查看详情",
      list: [],

      // 表单参数
      form: {},
      rules: {}
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(data) {
      this.dialogVisible = true;
      this.handleUpdate(data);
    },
    handleUpdate(row) {
      this.form = row
      onSelfApi.getPhone(row.id).then(res => {
        console.log("%c Line:112 🥤 res", "color:#fca650", res);
        this.form = res.data
        if (res.data.img) {
          let arr = []
          let list = JSON.parse(res.data.img)
          if (list.length) {
            list.forEach(element => {
              arr.push(element.url)
            });
            this.list = arr
          }
        }
      })
    },
    close() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
}

.form-box {
  max-height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
  padding-top: 20px;
}

.img-box {
  width: 700px;
}
</style>
