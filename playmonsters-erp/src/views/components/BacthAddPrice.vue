<template>
  <div>
    <el-form ref="form" :model="form" label-width="160px">

      <el-form-item
        :label-width="formLabelWidth"
        label="加减价"
        prop="jianjiajia"
      >
        <el-select
          v-model="form.jianjiajia"
          placeholder="请选择加减价"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in jiajian"
            :key="dict.value"
            :label="dict.name"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>



      <el-form-item
        :label-width="formLabelWidth"
        label="供货客服"
        prop="supplyId"
      >
        <el-select
          @change="setData(1)"
          v-model="form.supplyId"
          placeholder="请选择供货客服"
          clearable
          :disabled="dis"
          size="small"
        >
          <el-option
            v-for="dict in supplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        :label-width="formLabelWidth"
        label="终端客服"
        prop="supplyId"
      >
        <el-select
          @change="setData(2)"
          v-model="form.cmdSupplyId"
          placeholder="请选择终端客服"
          clearable
          size="small"
          :disabled="cmdDis"
        >
          <el-option
            v-for="dict in cmdSupplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>

      <el-form>
        <el-form-item label="百分比"   :label-width="formLabelWidth">
          <el-input-number v-model="form.rise" @change="setData" autocomplete="off"></el-input-number>
        </el-form-item>
      </el-form>
    </el-form>
  </div>
</template>

<script>
import {
  getSupplyList,getCmdSupplyList
} from "@/api/info/info";
import {
  bacthAddPriceDetails
} from "@/api/info/account";
export default {
  name: 'BacthAddPrice',
  data() {
    return {
      dis: false,
      cmdDis: false,
      formLabelWidth: "120px",
      form: {},
      supplyList: [],
      cmdSupplyList: [],
      jiajian:[{name:'加价',value: '1'},{name:'减价',value: '2'}]
    }
  },
  created() {
    this.getSupplyList();
    // this.getCmdSupplyList();
    this.dis = false
    this.cmdDis = false
  },
  methods: {
    getCmdSupplyList() {
      getCmdSupplyList().then((res) => {
        console.log(res)
        this.cmdSupplyList = res
      })
    },
    setData(type){


      //回显分销批量加价百分比
      let params = {}
      params.supplyId = this.form.supplyId
      params.cmdSupplyId = this.form.cmdSupplyId
      bacthAddPriceDetails(params).then((res)=>{
        if(res.code == 200){
          this.form.rise = res.data.rise
          // this.form.jianjiajia = res.data.jianjiajia
        }
      }) 


      if(1 == type){
        this.dis = false
        this.cmdDis = true
      }else if(2 == type){
        this.dis = true
        this.cmdDis = false
      }

      console.log(this.form)
      this.$emit("addFrom", this.form);
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then((res) => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>
