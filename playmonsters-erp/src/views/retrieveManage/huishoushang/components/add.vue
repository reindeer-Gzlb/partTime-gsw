<template>
  <!-- 报价弹窗 -->
  <el-dialog :title="title" top="100px" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1160px">
    <div class="form-box">
      
      <el-form ref="form" :model="form" :inline="true" :rules="rules" label-width="auto" class="demo-ruleForm">
        <div class=" flex fbt">
          <el-descriptions  class="margin-top" title="游戏内容" :column="3" border>
            <el-descriptions-item :labelStyle="{width:'120px'}" label="竞价编号">
              {{form.id||form.backId}}
            </el-descriptions-item>
        
            <el-descriptions-item :labelStyle="{width:'120px'}" label="游戏名称">
              {{form.type}}
            </el-descriptions-item>
           
            <el-descriptions-item label="区服" :labelStyle="{width:'120px'}">
              {{form.region}}
            </el-descriptions-item>
            <el-descriptions-item label="实名" :labelStyle="{width:'120px'}">
              <div style="min-width:100px">
                {{form.relname}}
              </div>
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
       
          <div class="inputBox" >
            <el-descriptions  class="margin-top" title="报价信息" :column="1" border>
              <el-descriptions-item label="" :span="3" :labelStyle="{width:'0',padding:0}">
                <el-form :model="form"  :rules="rules"  class="demo-ruleForm">
                  <el-form-item label="报价" prop="bprice">
                    <el-input-number :disabled="!isEdit" :precision="2" :controls="false" v-model="form.bprice" placeholder="请输入您的报价" />
                  </el-form-item>
                  <div style="text-align: center;" v-if="isEdit">
                    <el-button type="danger" @click="refuseLianmeng('form')" v-loading="loading">拒收</el-button>
                    <el-button type="primary" @click="submitForm('form')" v-loading="loading">报价</el-button>
                  </div>
                </el-form>
              </el-descriptions-item>
            </el-descriptions>
            <div style="height:30px">
              <!-- <span v-if="userInfo&&userInfo.userId==scope.row.supplyUserId">{{commonMaps.bstatusMap[scope.row.status||0].label}}</span>
                  <span v-else>{{scope.row.status!=0?'已拒绝':commonMaps.bstatusMap[0].label}}</span>
              {{commonMaps.bstatusMap[this.form.status||0].label}} -->
            </div>
            <el-descriptions  class="margin-top" title="当前进度" :column="1" border>
              <!-- <el-descriptions-item label="当前最高报价"  >
                {{form.maxPrice||0}}元
              </el-descriptions-item> -->
              <el-descriptions-item label="已参与报价人数"  >
                {{form.bcountt||0}}
              </el-descriptions-item>
            </el-descriptions>
            <el-table border :data="priceList" style="width: 100%;" v-if="priceList&&priceList.length">
              <!-- <el-table-column prop="account" label="游戏账号" >
                <template>
                  <div>
                    {{form.account}}
                  </div>
                </template>
              </el-table-column> -->
              <el-table-column prop="bprice" label="报价" >
              </el-table-column>
              <el-table-column prop="" label="报价人">
                <template slot-scope="scope">
                  <div>
                    <span v-if="userId==scope.row.createId">我的报价</span>
                    <span v-else>回收商{{scope.row.id}}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="account" label="状态" >
                <template slot-scope="scope">
                  <el-tag :type="commonMaps.kefubstatusMap[scope.row.status||0].type">{{commonMaps.kefubstatusMap[scope.row.status||0].label}}</el-tag>
                
                </template>
              </el-table-column>
            
            </el-table>
          </div>
        </div>
        <!-- <div class="center">
          <el-button @click="close()">关闭</el-button>
          
        </div> -->
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
      isEdit:true,
      title: "报价",
      list: [],//图片list
      priceList:[],
      // 表单参数
      form: {
        bprice: ''
      },
      userId:'',
      rules: {
        bprice: [
          { required: true, message: "请输入报价", trigger: "blur" }
        ]
      }
    };
  },
  created() {
  },
  methods: {
    // 打开弹窗
    open(flag, data,userId) {
      this.userId = userId
      this.priceList = []
      // console.log("%c Line:91 🍪 data", "color:#fca650", data);
      this.dialogVisible = true;
      this.$nextTick(() => {
        this.form = JSON.parse(JSON.stringify(data))
        if (flag == "edit") {
          this.isEdit = true;
          this.form.backId = data.id
          delete this.form.id
        } else if(flag == "view") {
          this.isEdit = false;
          if(this.form.status!=0){
            this.getPriceList()
          }
          
        }
        this.list = []
          if (data.img) {
            let arr = []
            let list = JSON.parse(data.img)
            if (list.length) {
              list.forEach(element => {
                arr.push(element.url)
              });
              this.list = arr
            }
          }else{
            this.list = []
          }
          if(data.relnameImg){
            this.list.unshift(data.relnameImg)
          }
      });
    },
    // 获取报价列表
    getPriceList() {
      let obj = {
        backId :this.form.id,
        showName:2
      }
      onSelfApi.quotationkfList(obj).then(res => {
        // console.log("%c Line:112 🥤 res", "color:#fca650", res);
        this.priceList = res.rows
        this.total = res.total
      })
    },
    // 回收联盟-拒收
    refuseLianmeng(row) {
      this.$prompt('请输入拒收原因', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        // inputErrorMessage: '请输入卖家ID',
        type: 'warning'
      }).then(({ value }) => {
        let obj = {
          backId: this.form.backId,
          rmsg: value,
          status: 5,
        }
        onSelfApi.refauseLianmengOrder(obj).then(res => {
          this.dialogVisible = false
          if (res.code == 200) {
            this.$parent.getList()
            this.$message({
              type: "success",
              message: res.msg
            })
            
          }
        })
      }).catch(() => {

      });
    },
    submitForm(formName) {
      if(Number(this.form.bprice)<=0){
        return this.$alert('报价必须大于0')
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$confirm('每个账号只能报价一次,是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then((r) => {
            onSelfApi.quotation(this.form).then(res => {
              console.log("%c Line:82 🥟 res", "color:#7f2b82", res);
              if (res.code == 200) {
                this.$alert('报价成功！')
                this.$parent.getList()
              } else {
                this.$alert(res.msg)
              }
            })

            this.dialogVisible = false
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });

    },
    // 上传图片相关
    // 账号图片上传成功
    handleUploadSuccess1(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages1 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove1(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList1.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList1.splice(key, 1); //回显的图片
        }
      });
      this.newImages1.forEach(function (element, key) {
        if (element.url === filePath) {
          that.newImages1.splice(key, 1); //新上传的图片
        }
      });
    },
    // 账号图片上传成功
    handleUploadSuccess2(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages2 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove2(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList2.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList2.splice(key, 1); //回显的图片
        }
      });
      this.newImages2.forEach(function (element, key) {
        if (element.url === filePath) {
          that.newImages2.splice(key, 1); //新上传的图片
        }
      });
    },
    // 账号图片上传成功
    handleUploadSuccess3(res, file, fileList) {
      console.log(
        "%c 🍯 res: ",
        "font-size:20px;background-color: #B03734;color:#fff;",
        res
      );
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages3 = fileList.filter(t => {
          return t.url.indexOf("blob:") > -1;
        });
      }
    },
    // 账号图片删除
    handleRemove3(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList3.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList3.splice(key, 1); //回显的图片
        }
      });
      this.newImages3.forEach(function (element, key) {
        if (element.url === filePath) {
          that.newImages3.splice(key, 1); //新上传的图片
        }
      });
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传50张图片");
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
    // // 上传图片相关结束
    close() {
      this.dialogVisible = false;
    }
  }
};
</script>
<style scoped lang="scss">
.form-box{
  height: calc(100vh - 300px);
  overflow: auto;
}
.center {
  text-align: center;
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

.inputBox{
  width: 350px;
  margin-left: 20px;
  flex-shrink: 0;
  // border: 1px solid #ccc;
  // border-radius: 5px;
  // padding: 15px;

}

</style>
