<template>
  <el-dialog
    title="商品详情"
    :visible.sync="centerDialogVisible"
    width="430px"
    center>
    <div class="web-box">
      <iframe :src="url" frameborder="0" style="width: 100%;height: 100%;"></iframe>
    </div>
  </el-dialog>
</template>

<script>


export default {
  name: "pick",
  data() {
    return {
    centerDialogVisible:false,
    url:''
    };
  },
  created() {

  },
  methods: {
open(url){
this.url = url
this.centerDialogVisible = true
}
  }
};
</script>
<style scoped lang="css">
  .web-box{
    width: 400px;
    height: 700px;
    overflow: hidden;
  }
</style>
