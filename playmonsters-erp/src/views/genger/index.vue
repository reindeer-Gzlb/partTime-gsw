<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="营地ID" prop="content">
        <el-input
          v-model="queryParams.campId"
          placeholder="请输入营地ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
      </el-form-item>

      <el-form-item label="我的编号" prop="content">
        <el-input
          v-model="formData.accountId"
          clearable
          size="small"
          :disabled="true"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="synchronous">一键上架</el-button>
      </el-form-item>
      <el-form-item label="模板">
          <el-radio v-model="radio"  label="1">模板1</el-radio>
          <el-radio v-model="radio" label="2">模板2</el-radio>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="genImg">生成图片</el-button>
      </el-form-item>
    </el-form>

    <el-table
      :data="roleList"
      v-loading="loading"
      :border="true"
      @cell-dblclick="gengerTitle"
      style="width: 100%">
      <el-table-column
        prop="areaName"
        label="大区"
        width="180">
      </el-table-column>
      <el-table-column
        prop="grade"
        label="等级"
        width="180">
      </el-table-column>
      <el-table-column
        prop="roleJob"
        label="段位">
      </el-table-column>
      <el-table-column
        prop="roleName"
        label="昵称">
      </el-table-column>
      <el-table-column
        prop="serverName"
        label="服务器">
      </el-table-column>
    </el-table>

    <br>
    <br>
    <br>

    <el-form ref="form" :model="formData" label-width="80px">
      <el-row>
        <el-col :span="6">
          <el-form-item label="段位">
            <el-input v-model="formData.jobName"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="水晶数量">
            <el-input v-model="formData.shuijingnum"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="营地号">
            <el-input v-model="formData.yingdiIdsd"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="贵族等级">
            <el-input v-model="formData.noble_level"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item label="英雄数量">
            <el-input v-model="formData.heroNum"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="皮肤数量">
            <el-input v-model="formData.skinNum"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="战斗力：">
            <el-input v-model="formData.zhandouli"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="水晶数量">
            <el-input v-model="formData.shuijingnum"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
<!--      <el-row>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="内测数量">-->
<!--            <el-input v-model="formData.neicenum"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="水晶数量">-->
<!--            <el-input v-model="formData.shuijingnum"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--        <el-col :span="8">-->
<!--          <el-form-item label="星元数量">-->
<!--            <el-input v-model="formData.xingyuannum"></el-input>-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->
      <!-- <el-row>
        <el-col :span="8">
          <el-form-item label="主页图">
            <el-input v-model="formData.uploadPicUrl"></el-input>
          </el-form-item>
        </el-col>
      </el-row> -->
      <el-row>
        <el-col :span="12">
          <el-form-item label="简介内容">
            <el-input type="textarea" :rows = '20' v-model="formData.ppxiangqing"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="热门皮肤">
            <el-input type="textarea" :rows = '20' v-model="jingmeie"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div v-if="isPreview">
      <img :src="titleimgurl" style="width: 80%"/>
      <img :src="imgurl" />
    </div>
  </div>
</template>

<script>
import Splicing from '../components/Splicing.vue';
import GengerSkin from '../components/GengerSkin.vue';
import { roleList,gengerTitle } from '@/api/gentitle/black'
import {synchronousAcount } from '@/api/info/info'
import { postUpload } from '@/api/upload'

export default {
  name: "Black",
  data() {
    return {
      uploadImgUrl: '',
      instancesUrl: [],
      skinList: [],
      imgurl: null,
      titleimgurl: null,
      // 显示搜索条件
      showSearch: true,
      loading: false,
      // 总条数
      total: 0,
      radio: 0,
      formData:{
        jingmeie: ''
      },
      // 弹出层标题
      jingmeie: '',
      title: "",
      // 是否显示弹出层
      open: false,
      roleList: [],
      // 查询参数
      queryParams: {
        campId: null,
        roleId: null,
      },
      // 表单校验
      rules: {
      }
    };
  },
  props: {
    isPreview: {
      type: Boolean,
      required: false,
      default: true,
    },
    isAutoDownload: {
      type: Boolean,
      required: false,
      default: false,
    },
    returnType: {
      type: String,
      required: false,
      default: "base64",
      validator: (value) => {
        return ["base64", "file"].indexOf(value) !== -1;
      },
    },
    widths: {
      type: [String, Number],
      required: false,
      default: 1366,
    },
    quality: {
      type: [String, Number],
      required: false,
      default: 0.618,
    },
  },
  components: {
    Splicing,GengerSkin
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + '/upload/image'
    
  },
  methods: {
    genImg(){
      // this.filesToInstances()
      this.gengertiImge()
    },
    synchronous(){
      this.loading = true;
      //一键同步
      let form =  this.formData
      synchronousAcount(form).then((res)=>{
          if (res.code == 200){
            this.loading = false;
            this.$modal.msgSuccess("添加成功");
          }else {
            this.$modal.msgError("添加成功");
          }
      })
    },
    gengertiImge(){
      //https://game.ikbh.top/logo/model1.jpg
      if(2 == this.radio){
          
      }
     if(1 == this.radio){
        this.getURLBase64('https://game.ikbh.top/logo/back-img.jpg').then(res => {
        const imageaaa = new Image();
        imageaaa.src = res;
        imageaaa.onload = () => {
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.width = imageaaa.width;
          canvas.height = imageaaa.height;
          context.drawImage(imageaaa, 0,0,imageaaa.width,imageaaa.height);
          context.fillStyle = '#fff';   // 文字填充颜色
          context.font = '33px Adobe Ming Std';


          var areaId = this.formData.areaId
          if (("1" == areaId)){
            areaId = "安卓Q";
          }else if (("2" ==areaId)){
            areaId = "苹果Q";
          }else if (("3" == areaId)){
            areaId = "安卓WX";
          }else if (("4" == areaId)){
            areaId = "苹果WX";
          }


          let one =   '英雄数量： '+this.formData.heroNum + '    '+
            '皮肤数量：  '+this.formData.skinNum + '      ' +
            '战斗力：  '+this.formData.zhandouli + '      ' +
            '水晶数量： ' + this.formData.shuijingnum

          let titlName = areaId

/*          let one =   '英雄： '+100 + '    '+
            '水晶：  '+100 + '      ' +
            '星元：  '+100 + '      ' +
            '皮肤： ' +100 + '      ' +
            '贵族： ' + 100 +'     '*/



          // let skin = ['至尊宝','冰霜恋舞曲','猪悟能','星空之诺','银河之约','暗影游猎','原初追逐者','黎明之约',
          // '梁祝','狂想玩偶喵','云间偶戏','电玩小子','曙光守护者','水晶猎龙者','无限星赏官','摇滚巨星','节奏热浪',
          // '梁祝','狂想玩偶喵','云间偶戏','电玩小子','曙光守护者','水晶猎龙者','无限星赏官','摇滚巨星','节奏热浪',
          //   '梁祝','狂想玩偶喵','云间偶戏','电玩小子','曙光守护者','水晶猎龙者','无限星赏官','摇滚巨星','节奏热浪']
          let skin = this.formData.jingmei.split(' ')

          context.fillStyle = '#fff';

          context.font = '66px Adobe Ming Std';

          context.fillText(titlName,700,130);

          context.font = '45px Adobe Ming Std';
          context.fillStyle = '#6BE61A';
          context.fillText(one,150,210);


          context.font = '33px Adobe Ming Std';
          context.fillStyle = '#fff';
          var x1 = 150;
          var h = 260;
          skin.map((item,index) =>{
            if (index < 35){
              if (index % 7 == 0){
                x1 = 150;
                h = h + 80;
              }
              console.log(x1)
              context.fillText(skin[index],x1,h)
              x1 = x1 + 220;
            }
          })

          context.font = '33px Adobe Ming Std';
          context.fillStyle = '#09F7F7';
          context.fillText('营地ID：' + this.queryParams.campId,750,800)

          const base64Url = canvas.toDataURL("image/jpeg", this.quality);
          this.titleimgurl = base64Url;
          this.downloadFile('怪兽玩代售', base64Url);
          let blo = this.base64ToBlob(base64Url);
          let formData = new FormData();
          formData.append("file",blo)
          postUpload(formData).then((res) => {
            this.formData.titleImge = res.msg
          }).catch((e) => {
          })
        }
      })
     }
    },
    getURLBase64(url) {
      return new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest()
        xhr.open('get', url, true)
        xhr.responseType = 'blob'
        xhr.onload = function() {
          if (this.status === 200) {
            var blob = this.response
            var fileReader = new FileReader()
            fileReader.onloadend = function(e) {
              var result = e.target.result
              resolve(result)
            }
            fileReader.readAsDataURL(blob)
          }
        }
        xhr.onerror = function() {
          reject()
        }
        xhr.send()
      })
    },

    filesToInstances() {
      let instancesUrl = this.instancesUrl;
      const length = instancesUrl.length;
      let instances = [];
      let finished = 0;

      instancesUrl.forEach((file, index) => {
        this.getURLBase64(file).then(res => {
          const image = new Image();
          image.src = res;
          image.setAttribute("width","150px")
          image.setAttribute("height","200px")
          image.onload = () => {
            // 图片实例化成功后存起来
            instances[index] = image;
            finished++;
            if (finished === length) {
              this.drawImages(instances);
            }
          };
        })
      });
    },
    drawImages(images) {
      console.log(images)
      const width = this.widths;
      const heights = images.map((item) =>item.height);
      const canvas = document.createElement("canvas");
      const widths = images.map((item) => item.width)
      let  sumWidth = 0;
      for (var i = 0; i < 4; i++){
        sumWidth = widths[i] + sumWidth;
      }
      canvas.width = sumWidth;
      let a = parseInt(images.length / 4);
      canvas.height = a * heights[0];
      const context = canvas.getContext("2d");

      let w = 0;
      let y = 0;
      let n = 0;
      images.forEach((item, index) => {
        const height = heights[index];
        const width = widths[index];
        let a = parseInt(index / 4);
        if (a > n){
          n = a;
          w = 0;
        }
        y = a * height;
        context.drawImage(item, w, y, width, height);
        w += width;
      });
      const base64Url = canvas.toDataURL("image/jpeg", this.quality);
      this.dealImages(base64Url);
      let blo = this.base64ToBlob(base64Url);
      let formData = new FormData();
      formData.append("file",blo)
      postUpload(formData).then((res) => {
        this.formData.detailsImge = res.msg
      }).catch((e) => {
        
      })


      // this.downloadFile('111',  base64Url);
      // if (this.returnType == "base64" && this.isAutoDownload) {
      //   this.downloadFile('111',  this.returnData);
      // }
    },
    dealImages(url) {
      this.imgurl = url;
      this.returnData = url;
      this.returnRes();
    },
    returnRes() {
      this.$emit("getRes", this.returnData);
    },
    base64ToBlob(code) {
      let parts = code.split(";base64,");
      let contentType = parts[0].split(":")[1];
      let raw = window.atob(parts[1]);
      let rawLength = raw.length;
      let uInt8Array = new Uint8Array(rawLength);

      for (let i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i);
      }
      return new Blob([uInt8Array], { type: contentType });
    },
    downloadFile(fileName, content) {
      let aLink = document.createElement("a");
      let blob = this.base64ToBlob(content); // new Blob([content]);
      let evt = document.createEvent("HTMLEvents");
      evt.initEvent("click", true, true); // initEvent 不加后两个参数在FF下会报错  事件类型，是否冒泡，是否阻止浏览器的默认行为
      aLink.download = fileName;
      aLink.href = URL.createObjectURL(blob);
      aLink.dispatchEvent(
        new MouseEvent("click", {
          bubbles: true,
          cancelable: true,
          view: window,
        })
      ); // 兼容火狐
    },
    gengerTitle(row){
      this.loading = true;
      this.queryParams.roleId = row.roleId
      this.formData.regional =  row.areaName
      gengerTitle(this.queryParams).then((res) =>{
        this.loading = false;
        if (res.code == 200){
          this.instancesUrl = res.data.qiniuUrl
          this.formData = res.data.skinMap
          this.skinList = res.data.goodSkinName
          // let jingmeie = this.skinList.join(' ') + '';
          // this.formData.jingmeie = jingmeie
          this.jingmeie =  this.skinList.join(' ')
          console.log(this.jingmeie)
          this.formData.jingmeie = this.jingmeie
          // this.gengertiImge()
          // this.filesToInstances()
        }
        
      })
    },
    genTitle(){
      this.loading = true;
      roleList(this.queryParams).then(res =>{
        if (res.code == 200){
          this.roleList = res.data
          this.loading = false;
        }
      })
    },
    /** 查询黑名单列表列表 */
    getList() {
      this.loading = true;
      listBlack(this.queryParams).then(response => {
        this.blackList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.campId = '459926613'
      if ('' == this.queryParams.game_sn || null == this.queryParams.campId){
        this.$message.error('请输入营地ID!');
        return true;
      }
      this.genTitle()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
  }
};
</script>
