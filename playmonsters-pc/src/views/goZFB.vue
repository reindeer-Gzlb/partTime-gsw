<template>
  <div class="container" style="text-align: center;;height: 500px;margin-top: 100px;">
    <div>
      <img src="https://img.cdn.heshecasa.com/upload/20220113/e4d2f28d3bffa5a7dca571fed96a25ea.jpg" alt="" style="text-align: center;">
      <div class="noDataTip" style="margin-top: 20px;font-size: 20px;">即将跳转到支付宝...</div>
    </div>
  </div>
</template>


<script setup lang="ts">
import { reactive, toRefs, onMounted, onUnmounted, ref, watch ,nextTick} from 'vue';

  onMounted(()=>{
      let alipayForm = localStorage.getItem('aliPayForm')
      console.log("%c Line:16 🍅 alipayForm", "color:#b03734", alipayForm);
      if (alipayForm) {
       const div = document.createElement('div')
        div.id = 'alipay'
        div.innerHTML = alipayForm
        document.body.appendChild(div)
        document.querySelector('#alipay').children[0].submit() // 执行后会唤起支付宝
      }
  }) 

</script>

