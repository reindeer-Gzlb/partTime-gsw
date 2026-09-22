<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="620px"
    append-to-body
  >
    <div class="form-box">
      <img src="/static/img/pay-success.png" alt=""><br/>
      <span class="font22">{{tip||'支付成功！'}}</span>
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: "",
  props:['tip'],
  data() {
    return {
      dialogVisible: false,
      loading: false,
      title: "充值积分",
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open() {
      this.dialogVisible = true;
    },
    close() {
      this.dialogVisible = false;
    },
  },
};
</script>
<style scoped lang="scss">
.form-box {
  text-align: center;
  color:blue;
  img{
    width: 90px;
    margin-bottom: 30px;
  }
}
</style>
