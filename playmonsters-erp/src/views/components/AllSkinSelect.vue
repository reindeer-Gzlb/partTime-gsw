<template>
  <div>
    <div v-for="(item,index) in classTypeList" :key="index">
      <h3>{{item.typeName}}</h3>
      <p>----------------------</p>
      <el-checkbox-group @change="setData" v-model="checkedCities" size="small">
        <el-checkbox-button v-for="(skin,skinIndex) in item.child" :label="skin.skinName" :key="skinIndex">{{skin.skinName}}</el-checkbox-button>
      </el-checkbox-group>
    </div>
  </div>
</template>

<script>
import {
  listSkin, classTypeList
} from '@/api/skin/skin'

export default {
  name: 'AllSkinSelect',
  data() {
    return {
      checkAll: false,
      checkedCities: [],
      isIndeterminate: true,
      classTypeList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    setData(){
      console.log(this.checkedCities)
      this.$emit("skinType", this.checkedCities);
    },
    getList() {
      let params = {}
      params.parentId = 1
      classTypeList().then((res) => {
        if (res.code == 200) {
          this.classTypeList = res.data
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
