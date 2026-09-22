<template>
  <el-dialog
    :title="'图片'"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="800px"
  >
    <div class="form-box">
      <div v-for="(item,ind) in list" :key="item" class="lin-item">
        <el-image :src="item" @click="review(ind)"></el-image>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import financeApi from "@/api/financeCenter/index.js";
export default {
  name: "typeConfig",
  data() {
    return {
      dialogVisible: false,
      list: []
    };
  },
  created() {},
  methods: {
    // 打开弹窗
    open(data) {
      this.list = [];
      this.dialogVisible = true;
      this.list = data;
    },
    review(ind) {
      this.$viewerApi({
        images: this.list,
        options: {
          initialViewIndex: ind
        }
      });
    }
  }
};
</script>
<style scoped lang="scss">
.form-box {
  min-height: 600px;
  max-height: calc(100vh - 250px);
  overflow-y: auto;
}
.lin-item ::v-deep .el-image{
  width: 100%;
}
</style>
