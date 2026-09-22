<template>
  <div>
    <el-upload
      :action="uploadImgUrl"
      :headers="headers"
      :multiple="multiple"
      :data="data"
      :name="name"
      :drag="drag"
      :accept="accept"
      :on-success="success"
      :before-upload="beforeUpload"
      :file-list="imgShowList"
      :limit="limit"
      :on-exceed="exceed"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
    >
      <i class="el-icon-plus"></i>
    </el-upload>
  </div>
</template>

<script>
export default {
  props: {
    headers: {
      default: null
    },
    multiple: {
      default: true
    },
    data: {
      default: null
    },
    name: {
      default: "file"
    },
    drag: {
      default: true
    },
    accept: {
      default: null
    },
    limit: {
      default: 10
    },
    oldList:{
      default:[]
    }
  },
  name: "uploadImage",
  data() {
    return {
      uploadImgUrl: "",
      imgShowList:[],
      newList:[],
      resultList:[]
    };
  },
  watch:{
oldList:{
  handler(val){
    console.log('%c 🥤 val: ', 'font-size:20px;background-color: #FCA650;color:#fff;', val);
this.resultList = val.concat(this.newList)

  },
  immediate:true,
  deep:true
}
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
  },
  methods: {
    // 详情图片删除
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.oldList.forEach(function(element, key) {
        if (element.url === filePath) {
          that.oldList.splice(key, 1); //回显的图片
        }
      });
      this.newList.forEach(function(element, key) {
        if (element.url === filePath) {
          that.newList.splice(key, 1); //新上传的图片
        }
      });
      this.$emit('change',this.imgShowList)
    },
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    exceed() {
      this.$alert(`最多上传${this.limit}张图片！`);
    },
    beforeUpload(){

    },
    // 图片上传成功
    success(res, file, fileList) {
      if (res.code == 200) {
      // 取得新上传的文件
        this.newList = fileList.filter(t=>{
          return t.url.indexOf('blob:')>-1
        })
        this.newList.forEach(item=>{
          item.url = item.response.data.filePath
        })
        this.resultList = this.oldList.concat(this.newList)
        this.$emit('change',this.resultList)
      }
    },
  }
};
</script>
<style scoped lang="scss">
</style>
