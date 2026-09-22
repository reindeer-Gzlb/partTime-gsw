<template>
  <div class="">
    <el-button type="text" size="mini" class="">剩余次数：{{ autoData }}
    <span v-if="showTip&&!autoData">，次数已用完，请联系管理员增加次数,微信：yudu3518</span>
    </el-button>
  </div>
</template>

<script>
import infoApi from "@/api/autoProduction/information.js";
export default {
  name: "shengyu",
  props:['showTip'],
  data() {
    return {
      autoData:0
    };
  },
  created() {
    this.getNumber()
  },
  methods: {
    // 自动获取剩余次数
    getNumber(){
      infoApi.residueNumber().then(res=>{
        this.autoData = res.data
      })
    }
  }
};
</script>
<style lang="scss" scoped>
</style>
