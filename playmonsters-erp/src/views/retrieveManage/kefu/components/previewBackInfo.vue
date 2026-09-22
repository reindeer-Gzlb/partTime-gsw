<template>
  <el-dialog :title="'账号详情'" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px">
    <div class="form-box flex" v-loading="loading">
      <div class="infoBox">
        <el-descriptions :column="2" title="账号信息" v-if="goodsInfo" border :labelStyle="{width:'100px'}">
          <el-descriptions-item label="游戏类型">{{ goodsInfo.type }}</el-descriptions-item>
          <el-descriptions-item label="区服">{{ goodsInfo.region }}</el-descriptions-item>
          <el-descriptions-item label="简介" :span="2">{{ goodsInfo.des }}</el-descriptions-item>
          <el-descriptions-item label="图片" :span="2">
            <div class="img-box">
              <div v-for="(item, ind) in list" :key="ind" class="lin-item">
                <el-image :src="item" @click="review(ind)"></el-image>
              </div>
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
      <div class="baopeiInfo" >
        <el-descriptions title="包赔信息" v-if="baopeiInfo" :column="1" border :labelStyle="{width:'100px'}">
          <el-descriptions-item label="卖家姓名">{{ baopeiInfo.saleName }}</el-descriptions-item>
          <el-descriptions-item label="身份证号">{{ baopeiInfo.saleCard }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ baopeiInfo.saleMobile }}</el-descriptions-item>
          <el-descriptions-item label="家庭住址"> {{ baopeiInfo.saleAddress }}</el-descriptions-item>
          <el-descriptions-item label="父亲姓名">{{ baopeiInfo.fnickName }} </el-descriptions-item>
          <el-descriptions-item label="父亲手机号"> {{ baopeiInfo.fmobile }}</el-descriptions-item>
          <el-descriptions-item label="母亲姓名"> {{ baopeiInfo.mnickName }}</el-descriptions-item>
          <el-descriptions-item label="母亲手机号"> {{ baopeiInfo.mmobile }}</el-descriptions-item>
          <el-descriptions-item label="身份证"> 
            <div class="idcards">
              <el-image :src="baopeiInfo.idCardF" @click="review2(baopeiInfo.idCardF)"></el-image>
              <el-image :src="baopeiInfo.idCardZ" @click="review2(baopeiInfo.idCardZ)"></el-image>
            </div>
          </el-descriptions-item>
          <el-descriptions-item label="其他图片">
            <div class="img-box">
              <div v-for="(item, ind) in images" :key="ind" class="lin-item">
                <el-image :src="item" @click="review(ind)"></el-image>
              </div>
            </div>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import baopeiApi from '@/api/retrieveManage/baopei'
export default {
  name: "typeConfig",
  data() {
    return {
      dialogVisible: false,
      loading:false,
      list: [],
      baopeiInfo:null,
      goodsInfo:null,
      backId:'',
      images:[]
    };
  },
  created() { },
  methods: {
    // 打开弹窗
    open(id) {
      this.list = [];
      this.dialogVisible = true;
      this.backId = id
      this.init()
    },
    init(){
      baopeiApi.getHuishouByBackid(this.backId).then(res=>{
        console.log("%c Line:59 🍧 回收res", "color:#ffdd4d", res);
        if(res.code==200){
          this.goodsInfo = res.data
          if(this.goodsInfo.img){
            let imgs = JSON.parse(this.goodsInfo.img)
            imgs.forEach(element => {
              this.list.push(element.url)
            });
            // this.list = JSON.parse(this.goodsInfo.img)
            console.log("%c Line:66 🍋 this.list", "color:#93c0a4", this.list);
          }
        }
      })
      baopeiApi.getBaopeiByBackid(this.backId).then(res=>{
        console.log("%c Line:63 🧀 包赔res", "color:#ed9ec7", res);
        if(res.code==200){
          this.baopeiInfo = res.data
          if(this.baopeiInfo.images){
            this.images = this.baopeiInfo.images.split(',')
          }
        }
      })
    },
    review(ind) {
      this.$viewerApi({
        images: this.list,
        options: {
          initialViewIndex: ind
        }
      });
    },
    review2(url){
      this.$viewerApi({
        images: [url],
        options: {
          initialViewIndex: 0
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
  .infoBox{
    width: 70%;
    flex-shrink: 0;
    border: 1px solid #f3f3f3;
  }
  .baopeiInfo{
    width: 30%;
    flex-shrink: 0;
    border: 1px solid #f3f3f3;
  }
}

.lin-item ::v-deep .el-image {
  width: 100%;
}
</style>
