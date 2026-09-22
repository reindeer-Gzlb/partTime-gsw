<template>
  <el-dialog title="一键生成" :visible.sync="centerDialogVisible" width="900px" append-to-body center>
    <div class="web-box">
      <infoDialog :flag="'addDialog'" @submit="submit" ref="infoDialog"></infoDialog>
    </div>
  </el-dialog>
</template>

<script>

import infoDialog from "../getInformation/index"
export default {
  name: "pick",
  components: { infoDialog },
  data() {
    return {
      centerDialogVisible: false,
      campId: ''
    };
  },
  created() {

  },
  methods: {
    open(data, flag) {
      console.log("%c Line:31 🥚 data", "color:#ea7e5c", data);
      this.centerDialogVisible = true
      this.$nextTick(() => {
        this.$refs.infoDialog.updateCamp(data, flag)
      })

    },
    submit(data) {
      this.$emit('autoGetSubmit', data)
      this.centerDialogVisible = false
    }
  }
};
</script>
<style scoped lang="css">
.web-box {
  width: 100%;
  height: calc(100% - 300px);
  overflow-y: auto;
}
</style>
