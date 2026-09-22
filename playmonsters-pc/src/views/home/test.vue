<template>
  <div class="main">
    <h1>测试页面</h1>
    <!-- 测试 -->
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
    <el-form-item label="商品名">
      <el-input v-model="formInline.title" placeholder="商品名" clearable />
    </el-form-item>
    <el-form-item label="价格">
      <el-input v-model="formInline.totalAmount" placeholder="价格" clearable />
    </el-form-item>
    <el-form-item label="业务订单号">
      <el-input v-model="formInline.no" placeholder="业务订单号" clearable />
    </el-form-item>

    <el-form-item label="商户唯一标识">
      <el-input v-model="formInline.merchantCode" placeholder="商户唯一标识" clearable />
    </el-form-item>
    <el-form-item label="支付方式">
      <el-input v-model="formInline.payWay" placeholder="支付方式" clearable />
    </el-form-item>
    <el-form-item label="支付场景">
      <el-input v-model="formInline.payScene" placeholder="支付场景" clearable />
    </el-form-item>
    <el-form-item label="回调地址">
      <el-input v-model="formInline.notifyUrl" placeholder="回调地址" clearable />
    </el-form-item>

    <el-form-item label="跳转地址">
      <el-input v-model="formInline.returnUrl" placeholder="跳转地址" clearable />
    </el-form-item>
    <el-form-item label="用户userid">
      <el-input v-model="formInline.userId" placeholder="用户userid" clearable />
    </el-form-item>
   
  </el-form>
    <el-button @click="payZFB" type="primary">支付宝支付</el-button>
  </div>
</template>

<script setup  lang="ts">
import payApi from '@/api/pay';
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';


    const router = useRouter();
    const state = reactive({
      title: '',
      text: ``
    });
    const formInline = reactive({
      title: '',
      totalAmount: '',
      no: '',
      merchantCode:'',
      payWay:'',
      payScene:'',
      notifyUrl:'',
      returnUrl:'',
      userId:''
})
    const payZFB = () => {
      // openUrl("http://192.168.3.102:12310/pay/unify")
      // formInline
      //console.log("%c Line:62 🥥 formInline", "color:#fca650", formInline);
      payApi.pay(formInline).then(res=>{
        //console.log("%c Line:38 🍫 res", "color:#2eafb0", res);
        alert(res.data.formStr)
        document.write(res.data.formStr)
      })
    }

    onMounted(() => {

    });
   
  
    const openUrl = (val) => {
      window.open(val)
    }

 
</script>

<style scoped lang="less">
.main {
  line-height: 17px;
  font-size: 12px;
  font-weight: 700;
  color: #6c6c6c;

  .fixed-bar {
    width: 66px;
    color: #000000;
    text-align: center;
    background: #fff;
    // border-bottom-left-radius: 15px;
    // border-top-left-radius: 15px;
    padding: 10px 0 5PX 0;
    box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
    position: relative;

    .imgss {
      position: absolute;
      top: -66px;
      left: 0;
      width: 66px;
      height: 66px;
    }

    >div {
      margin-bottom: 15px;
      cursor: pointer;
      font-size: 12PX;
      font-weight: 400;

      img {
        width: 26px;
        height: 26px;
      }

      .imgss {
        width: 60px;
        height: 60px;
      }
    }
  }
}</style>
