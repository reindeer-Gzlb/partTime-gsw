<template>
  <div class="app-container">
  </div>
</template>

<script>
import { roleList,gengerTitle } from '@/api/gentitle/black'
import { postUpload } from '@/api/upload'
export default {
  name: 'GengerSkin',
  data() {
    return {
      returnData: {},
      formData: {},
      roleList: [],
      uploadImgUrl: '',
      instancesUrl: [],
      skinList: [],
      imgurl: null,
      titleimgurl: null,
    }
  },
  props: {
    props: ['formData'],
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
  created() {
    this.getImge()
  },
  methods: {
    getImge(){
      this.gengertiImge()
      this.filesToInstances()
      this.returnRes()
    },
    gengertiImge(){
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

          let one =   '英雄： '+this.formData.heroNum + '    '+
            '水晶：  '+this.formData.shuijingnum + '      ' +
            '星元：  '+this.formData.xingyuannum + '      ' +
            '皮肤： ' + this.formData.skinNum + '  ' +
            '贵族： ' + this.formData.noble_level + ''

          let titlName = areaId
          let skin = this.skinList

          context.fillStyle = '#fff';

          context.font = '66px Adobe Ming Std';

          context.fillText(titlName,750,130);

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
          context.fillText('营地ID：123231',750,800)

          const base64Url = canvas.toDataURL("image/jpeg", this.quality);
          this.titleimgurl = base64Url;
          this.downloadFile('怪兽玩代售', base64Url);
          let blo = this.base64ToBlob(base64Url);
          let formData = new FormData();
          formData.append("file",blo)
          postUpload(formData).then((res) => {
            this.returnData.titleImge = res.msg
          }).catch((e) => {
          })
        }
      })
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
        console.log(w)
        context.drawImage(item, w, y, width, height);
        w += width;
      });
      const base64Url = canvas.toDataURL("image/jpeg", this.quality);
      this.dealImages(base64Url);
      let blo = this.base64ToBlob(base64Url);
      let formData = new FormData();
      formData.append("file",blo)
      postUpload(formData).then((res) => {
        this.returnData.detailsImge = res.msg
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
  }
}
</script>

<style scoped>

</style>
