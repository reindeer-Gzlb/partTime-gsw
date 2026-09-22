<template>
  <!-- 选择支付方式的组件 -->
  <div class="payway-box flex">
        <div
          @click="selectPayway(item.id)"
          :class="{ active: payId == item.id }"
          class="payway-item"
          v-for="item in paywayList"
          :key="item.id"
        >
        
          <img :src="item.icon" alt="" />
          <span>
            {{ item.name }}
          </span>
          <span class="icon el-icon-success" >
           
          </span>
          
        </div>
      </div>
</template>

<script>
export default {
  name: "",
  props:['payWay'],
  data() {
    return {
      payId:'',
      paywayList: [
        {
            name: '支付宝',
            id: 2,
            icon: '/static/icon/zfblogo.png'
          },
          {
            name: '微信支付',
            id: 1,
            icon: '/static/icon/wxlogo.png'
          }
          
        ]
    };
  },
  watch:{
    payWay:{
      handler(newVal){
        this.payId = newVal
      },
      immediate:true
      
    }
  },
  created() {
  },
  methods: {
    selectPayway(paywayId){
      this.payId = paywayId
      this.$emit('selectPayway',paywayId)
    }
  },
};
</script>
<style scoped lang="scss">
.payway-box {
    justify-content: flex-end;
  }
  .payway-item {
    width: 150px;
    border: 1px solid #999;
    border-radius: 2px;
    padding: 5px 10px;
    margin-left: 15px;
    margin-bottom: 15px;
    font-size: 18px;
    cursor: pointer;
    box-sizing: border-box;
    position: relative;

    img {
      width: 30px;
      height: 30px;
      vertical-align: middle;
      margin-right: 5px;
    }
    .icon{
      display: none;
      position: absolute;
      top: 4px;
      right: 3px;
      color: red;
    }
    &.active {
      border: 2px solid #f7423f;
      .icon{
      display: block;
    }
    }
  }
</style>
