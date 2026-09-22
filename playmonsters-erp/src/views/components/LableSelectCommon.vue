<template>
  <div>
    <el-form ref="form" :model="form" label-width="80px">
      <el-form-item :label="item.fieldName" v-for="(item,index) in fieldConfisg" :key ="index">


        <div v-if="item.fieldType=='checkbox'">
          <el-checkbox-group v-model="selecyType" @change="selectLable">
            <el-checkbox :label="checkItem" v-for="(checkItem,checkIndex) in item.fields" :key="checkIndex">{{checkItem}}</el-checkbox>
          </el-checkbox-group>
        </div>

        <div v-if="item.fieldType=='radio'">
          <el-radio-group v-model="form.type" @change="selectLable">
            <el-radio :label="checkItem"  v-for="(checkItem,checkIndex) in item.fields" :key="checkIndex">{{checkItem}}</el-radio>
          </el-radio-group>
        </div>
        <div v-if="item.fieldType=='regional'">
          <el-radio-group v-model="form.regional" @change="selectLable">
            <el-radio :label="checkItem"  v-for="(checkItem,checkIndex) in item.fields" :key="checkIndex">{{checkItem}}</el-radio>
          </el-radio-group>
        </div>
      </el-form-item>
       <el-form-item v-if="ifInput" label="备注">
          <el-input v-model="form.remake" v-on:input="selectInput"></el-input>
        </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { labelType } from "@/api/fieldconfig/config";

export default {
  name: 'LableSelectCommon',
  props: ['type','des','inputType'],
  data() {
    return {
      ifInput: false,
      fieldConfisg: [],
      form:{},
      selecyType: [],
      resultDate: [],
      createDate: [],
      resultInput: ''
    }
  },
  created() {
    this.onloadFun()
    console.log(this.type)
    console.log(this.inputType)
    if('1' == this.inputType){
      this.ifInput = true
    }else{
      this.ifInput = false
    }
  },
  methods: {
    onloadFun(){
      let desArr = this.des.split(" ");
      console.log(desArr)
      let params = {}
      params.type = this.type
      labelType(params).then(res =>{
        this.fieldConfisg = res
        for(let j=0; j<desArr.length; j++){
          for (let i=0; i< this.fieldConfisg.length; i++) {
            let fields = this.fieldConfisg[i].fields
            for (let n=0; n< fields.length; n++){
              if (desArr[j]){
                if (desArr[j] == fields[n] || desArr[j].includes(fields[n])){
                  if ('regional' == this.fieldConfisg[i].fieldType){
                    this.form.regional = fields[n]
                  }
                  if ('radio' == this.fieldConfisg[i].fieldType){
                    this.form.type = fields[n]
                  }
                  if ('checkbox' == this.fieldConfisg[i].fieldType){
                    this.selecyType.push(fields[n])
                  }
                }
              }
            }
          }
        }
        this.selectLable()
        console.log(this.resultDate)
      })
    },
    selectLable(){
      this.resultDate = []
      if (this.selecyType){
        for (var i = 0; i < this.selecyType.length; i++){
          this.resultDate.push(this.selecyType[i])
        }
      }
      if (this.form.type){
        this.resultDate.push(this.form.type)
      }
      if (this.form.regional){
        this.resultDate.push(this.form.regional)
      }
      console.log(this.selecyType)
      this.$emit("selectLable", this.resultDate);
    },
    selectInput(){
      console.log(this.form.remake)
      this.resultInput = this.form.remake;
      this.$emit("selectInput", this.resultInput);
    }
  }
}
</script>

<style scoped>

</style>
