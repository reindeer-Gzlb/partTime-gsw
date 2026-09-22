<template>
  <el-dialog :title="title" :visible.sync="dialogVisible" :close-on-click-modal="false" width="850px">
    <div class="form-box">
      <el-descriptions  class="margin-top" title="游戏内容" :column="3" border>
        
            <el-descriptions-item :labelStyle="{width:'120px'}" label="游戏名称">
              {{form.type}}
            </el-descriptions-item>
            <el-descriptions-item label="区服" :labelStyle="{width:'120px'}">
              {{form.region}}
            </el-descriptions-item>
            <el-descriptions-item label="实名" :labelStyle="{width:'120px'}">
              {{form.relname}}
            </el-descriptions-item >
            <el-descriptions-item label="简介" :span="3" :labelStyle="{width:'120px'}">
              {{form.des}}
            </el-descriptions-item>
            <el-descriptions-item label="图片" :span="3" :labelStyle="{width:'120px'}">
              <div class="flex img-box">
                <el-image :src="item" @click="imgView(list,ind)" v-for="(item, ind) in list" :key="item" class="lin-item"></el-image>
              </div>
              
            </el-descriptions-item>
            
          </el-descriptions>
          <div class="center">
          <el-button @click="close()">关闭</el-button>
        </div>
    </div>
  </el-dialog>
</template>

<script>
import onSelfApi from '@/api/retrieveManage/onSelf.js'
export default {
  name: "addFina",
  dicts: ["game_type", "account_status"],
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
        this.list = []
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
        if(res.data.relnameImg){
            this.list.unshift(res.data.relnameImg)
          }
      })
    },
    // 图片数组预览
    imgView(list,and){
      this.$viewerApi({
        images: list,
        options: {
          initialViewIndex: and,
        },
      });
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
  margin-top: 15px;
}

.img-box{
  width: 500px;
}
.img-box{
  flex-wrap: wrap;
}
.lin-item{
  width: 100px;
  height: 100px;
  cursor: pointer;
  margin-right: 10px;
  margin-bottom: 10px;
  
}
</style>
