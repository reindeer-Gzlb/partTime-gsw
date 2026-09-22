<template>
  <!-- 回收订单详情 -->
  <el-dialog @close="close" class="pick-dialog" title="订单详情" :visible.sync="dialogVisible" :close-on-click-modal="false" width="1000px">
    <div class="form-box">
      <div class="order-info flex">
        <span>
          订单状态：
          <span class="status-txt">{{ commonMaps.retrieveOrderStatusMap[ruleForm.orderStatus||5].label }}</span>
        </span>
        <!-- <span>
        支付状态：
          <span class="status-txt">{{ commonMaps.buyerPayState[ruleForm.buyerPayStatus||1] }}</span>
        </span> -->
        <span>订单编号：{{ ruleForm.no }}</span>
        <span v-show="from=='kefu'">买家昵称：{{ ruleForm.saleChannel }}</span>
        <span v-show="from=='kefu'">卖家昵称：{{ ruleForm.supplyChannel }}</span>
        <span>创建时间：{{ ruleForm.createTime }}</span>
        <span v-if="ruleForm.orderStatus==3">超时时间：{{ ruleForm.closeTime }}</span>
        <span v-if="ruleForm.cancelTime">取消时间：{{ ruleForm.cancelTime }}</span>
      </div>
      <el-descriptions title="详细信息"  :column="3" border>
        <el-descriptions-item :labelStyle="labelStyle" label="竞价编号">{{ruleForm.backlistId}}</el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="游戏名称">{{ruleForm.gameName}}</el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="游戏账号" >{{ruleForm.accountNumber}}</el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="区服" :span="2">
          {{ruleForm.qufu}}
        </el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="实名">
          {{ruleForm.relname}}
        </el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="报价金额">{{ruleForm.sellerAcceptedAmount}}</el-descriptions-item>
        <el-descriptions-item :labelStyle="labelStyle" label="改价后金额" :span="2">{{ruleForm.changeAmount}}</el-descriptions-item>
        <el-descriptions-item label="简介" :labelStyle="labelStyle" :span="3">{{ruleForm.des}}</el-descriptions-item>
        <el-descriptions-item label="图片" :span="3" :labelStyle="labelStyle">
              <div class="flex img-box">
                <el-image :src="item" @click="imgView(imgList,ind)" v-for="(item, ind) in imgList" :key="item" class="lin-item"></el-image>
              </div>
              
            </el-descriptions-item>
      </el-descriptions>
     
      <div class="baopei-box flex" v-if="from=='zhuanshenhe'||(ruleForm.buyerPayStatus==2||from=='editPayState')">
        <div>选择包赔商</div>
        <el-select :disabled="ruleForm.buyerPayStatus==2||from=='editPayState'" @change="baopeiChange" clearable v-model="baopei" placeholder="请选择包赔商">
          <el-option :label="item.nickName" :value="item.userId" v-for="item in baopeoList" :key="item.userId"></el-option>
        </el-select>
      </div>
      <div class="baopei-box flex" v-if="from=='editPayState'">
        <div>买家支付状态</div>
        <el-select  clearable v-model="ruleForm.buyerPayStatus" placeholder="请选择买家支付状态">
          <el-option :label="item" :value="key" v-for="(item,key) in commonMaps.buyerPayState" :key="key"></el-option>
        </el-select>
      </div>
   
      <div class="price-box">
        <p >
          <span class="tit">报价金额：</span>
          <span class="price">¥{{ ruleForm.sellerAcceptedAmount || 0 }}</span>
        </p>
        <p >
          <span class="tit">改价后金额：</span>
          <span class="price">¥{{ ruleForm.changeAmount || 0 }}</span>
        </p>
        <p >
          <span class="tit">平台服务费：</span>
          <span class="price">¥{{ ruleForm.buyerServiceAmout || 0 }}</span>
        </p>
        <p style="border-top:1px solid #f2f2f2;padding-top:10px">
          <span class="tit">包赔费：</span>
          <span class="price">¥{{ ruleForm.guaranteeAmount || 0 }}</span>
        </p>
      </div>
      <div class="sfk">
        <span >实付款：</span>
        <span class="price" >
          <span>¥</span><span class="txt">{{ ruleForm.buyerActualAmount || 0 }}</span>
        </span>
        <!-- <span v-if="from=='seller'">结算金额：</span>
        <span class="price" v-if="from=='seller'">
          <span>¥</span><span class="txt">{{ ruleForm.supplyDiscountAmount || 0 }}</span>
        </span> -->
      </div>
     
      <!-- <div class="payway" v-if="ruleForm.orderStatus ==1&&ruleForm.buyerPayStatus==1&&from=='buyer'">
        <payWayLsit :payWay='payWay' ref="payWayLsit" @selectPayway="selectPayway"></payWayLsit>
      </div> -->

      <div class="center">
        <el-button @click="close('ruleForm')">关闭</el-button>
        <!-- <el-button v-if="ruleForm.orderStatus ==1&&ruleForm.buyerPayStatus==1&&from=='buyer'" type="primary" @click="submitForm('ruleForm')"
          :loading="loading">立即支付</el-button> -->
        <el-button v-if="from=='zhuanshenhe'||from=='editPayState'" type="primary" @click="zhuanshenhe('ruleForm')"
          :loading="loading">确认</el-button>
      </div>
    </div>
    <payQrcode ref="payQrcode" @checkOrderInterval="checkOrderInterval" @checkOrder="checkOrder"></payQrcode>
  </el-dialog>
</template>

<script>
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import payApi from '@/api/financeCenter/pay.js'
import payQrcode from '@/components/PayQrcodeCommon.vue';
import {getSupplyList} from "@/api/info/info.js"
import payWayLsit from '@/components/payWayLsit'
export default {
  name: "SaleOrderDetail",
  components: { payQrcode,payWayLsit },
  data() {
    return {
      labelStyle:{
        width:'120px'
      },
      dialogVisible: false,
      loading: false,
      isEdit: false,
      payQrShow: false,
      info: "",
      infoArr: [],
      ruleForm: {
        buyerPayStatus:'',
      },
      orderId: '',
      payForm: '',
      from:'',
      baopei:'',
      
      baopeoList:[ ],
      payWay:'2',
      imgList:[]

    };
  },
  created() {
    // this.orderId = this.$router.currentRoute.query.id
    // this.getDetail()
    
  },
  methods: {
    open(orderId,from) {
      this.from = from||''
      this.loading = false
      this.orderId = orderId
      this.getDetail()
      this.getBaopeiList()
      this.dialogVisible = true;
    },
    getBackListDetail(backId){
      onSelfApi.getPhone(backId).then(res=>{
        this.ruleForm.relname = res.data.relname
        if(res.data.relnameImg){
          this.imgList.unshift(res.data.relnameImg)
        }
      })
    },
    getDetail() {
      onSelfApi.getBackorderDetail(this.orderId).then(res => {
        this.ruleForm = res.data
        this.baopei = ''
        if(this.ruleForm.guaranteeId){
          this.baopei = this.ruleForm.guaranteeId
        }
        if(this.ruleForm.buyerPayStatus){
          this.ruleForm.buyerPayStatus = String(this.ruleForm.buyerPayStatus)
        }
        
        this.imgList = []
          if (this.ruleForm.img) {
            let arr = []
            let list = JSON.parse(this.ruleForm.img)
            if (list.length) {
              list.forEach(element => {
                arr.push(element.url)
              });
              this.imgList = arr
            }
          }else{
            this.imgList = []
          }
          this.getBackListDetail(this.ruleForm.backlistId)
          // if(this.ruleForm.relnameImg){
          //   this.imgList.unshift(this.ruleForm.relnameImg)
          // }
      })
    },
    // 获取包赔商列表
    getBaopeiList(){
      getSupplyList('baopeishang').then(res=>{
        console.log("%c Line:135 🥚 baopeishang res", "color:#ea7e5c", res);
        res.data.unshift({
          nickName:'不买包赔',
          userId:''
        })
        this.baopeoList = res.data
      })
    },
    // 选择包赔商
    baopeiChange(e){
      console.log("%c Line:140 🍎 e", "color:#4fff4B", e);
      let baopei = ''
      let baopeifei = 0
      let baopeiticheng = 0
      if(e){
        let arr = this.baopeoList.filter(r=>{
          return r.userId == e
        })
        baopei = arr[0]
      }
      if(baopei&&baopei.userId){
        // 计算包赔费
        baopeifei = Math.round(this.ruleForm.changeAmount * baopei.guaranteeRatio)/100
        baopeifei = baopeifei<20?20:baopeifei
        baopeiticheng = Math.round(baopeifei*baopei.guaranteeRoyalty)/100
      }
      this.ruleForm.guaranteeAmount = baopeifei
      this.ruleForm.baopeiticheng = baopeiticheng
      this.ruleForm.buyerActualAmount = Math.round((this.ruleForm.changeAmount + this.ruleForm.guaranteeAmount +this.ruleForm.buyerServiceAmout)*100)/100
    },
    // 选择支付方式
    selectPayway(val){
      this.payWay = val
    },
    // 轮询订单支付结果
    checkOrderInterval(){
      onSelfApi.buyerPayStatus(this.ruleForm.id).then(res=>{
        this.ruleForm.buyerPayStatus = res.data.buyer_pay_status
        if(this.ruleForm.buyerPayStatus==2){
          // 已支付
          this.$refs.payQrcode.close()
        }
      })
    },
    // 查询订单状态
    checkOrder() {
      onSelfApi.backOrderQueryOrder({
        id:this.ruleForm.id
      }).then(res=>{
        this.ruleForm = res.data
        if(this.ruleForm.buyerPayStatus==2){
          // 已支付
          this.$refs.payQrcode.close()
        }
      })
    },

    // 立即支付
    submitForm(formName) {
      this.loading = true
      // 支付参数
      let obj = {
        id: this.ruleForm.id,
        payWay: this.payWay,
        returnUrl: '',
        payScene: 3,
        amountType: 3,
        goodsTitle: '账号回收（' + this.ruleForm.accountNumber+'）',
        payTotalMoney: this.ruleForm.buyerActualAmount,
        guaranteeId:this.baopei,
        qrPayMode: 4,
        qrcodeWidth: 200
      }
      payApi.payHuishou(obj).then(res => {
        console.log("%c Line:38 🍫 res", "color:#2eafb0", res);
        this.loading = false
        if (res.code == 200) {
          this.$refs.payQrcode.open(res.data.formStr,this.payWay)
        }
      })
    },
    // 转审核
    zhuanshenhe(){
      if(this.from=='editPayState'){
        return this.editPay()
      }else{
        if(!this.baopei){
          this.$message.error('请先选择包赔商')
          return
        }
        this.$confirm('确定将此订单转为待审核吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let obj = {
            id:this.orderId,
            guaranteeId:this.baopei
          }
          onSelfApi.transToAudit(obj).then(res=>{
            this.$message.success(res.msg)
            this.$parent.getList()
            this.dialogVisible = false
          })
        }).catch(() => { });
      }
    },
    // 修改支付状态
    editPay(){
      if(!this.ruleForm.buyerPayStatus){
        this.$message.error('请先选择支付状态')
        return
      }
      let obj = {
          id:this.orderId,
          buyerPayStatus:this.ruleForm.buyerPayStatus
        }
        onSelfApi.editBuyPayState(obj).then(res=>{
          this.$message.success(res.msg)
          this.$parent.getList()
          this.dialogVisible = false
        })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.ruleForm = {
        tel: "",
        remark: "",
        ziliao: "",
        shopprice: this.info.newPrice,
        price: this.info.price,
      };
      this.checkList = []
      this.ruleForm.id = this.info.id;
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
    close(formName) {
      this.$parent.getList()
      this.dialogVisible = false
    }
  }
};
</script>
<style scoped lang="scss">
.form-box {
  padding: 15px;
  // width: 1000px;
  height: calc(100vh - 170px);
  overflow-y: auto;

}
.baopei-box{
  align-items: center;
  margin-top: 15px;
  >div{
    margin-right: 15px;
    &:first-of-type{
      width: 90px;
    }
  }
}
::v-deep .label-txt{
  width: 150px !important;
}

.title-box {
  // justify-content: space-between;
  font-size: 14px;
  line-height: 40px;
  padding: 0 15px;


}

.order-info {
  box-sizing: border-box;
  // height: 50px;
  background: #eee;
  padding: 15px;
  margin-bottom: 30px;
  flex-wrap: wrap;
  line-height: 26px;

  >span {
    margin-right: 20px;
  }
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

.price-box {
  text-align: right;
  border-bottom: 1px solid #eee;
  margin-bottom: 15px;
  padding-right: 20px;
}

.sfk {
  text-align: right;
  padding-right: 20px;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 10px;

  .txt {
    font-size: 26px;
    color: #ff0000;
    margin-left: 5px;
  }
}

.demo-ruleForm {
  padding-right: 20px;
}

.center {
  text-align: right;
}

::v-deep .el-dialog__body {
  padding-top: 10px !important;
}

.pick-dialog>>>.el-input--medium input {
  text-align: left;
}</style>
