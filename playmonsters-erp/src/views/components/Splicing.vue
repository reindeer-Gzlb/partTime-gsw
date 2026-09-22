<template>
  <div>
    <div>
      <input
        type="file"
        id="upFile"
        @change="upFile($event)"
        accept="image/*"
        multiple="multiple"
        style="display:none"
      />
      <label for="upFile">
        <span class="choosefile-btn">选择文件</span>
      </label>
    </div>
    <div v-if="isPreview">
      <img :src="imgurl" />
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      returnData: null,
      imgurl: null,
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
  methods: {
    base64ToBlob(code) {
      debugger
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

    upFile() {
      if (this.returnType !== "file") {
        const files = Array.from(event.target.files);
        this.filesToInstances(files);
      } else {
        this.returnData = event.target.files;
        this.returnRes();
      }
    },



   /* filesToInstances(files) {
      const length = files.length;
      let instances = [];
      let finished = 0;

      files.forEach((file, index) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = (e) => {
          const image = new Image();
          let resu = e.target.result;
          console.log(resu)
          debugger
          image.src = e.target.result;
          image.onload = () => {
            // 图片实例化成功后存起来
            instances[index] = image;
            finished++;
            if (finished === length) {
              this.drawImages(instances);
            }
          };
        };
      });*/
   filesToInstances(files) {
     // let instancesUrl = [
     //   'http://shp.qpic.cn/tgos/201702m1/ab4238c7934adc1c44c928d6dd439f52/0',
     //   'http://shp.qpic.cn/tgos/201702m1/ab4238c7934adc1c44c928d6dd439f52/0',
     //   'http://shp.qpic.cn/tgos/14679/59df0c1dcc14a968260ffbe691b89280/0',
     //   'http://shp.qpic.cn/tgos/4225/dd73054061c53a1b76a63845ae7d0816/0',
     //   'http://shp.qpic.cn/tgos/2457/6adc508e90bfc0182ac2a4ed825fd460/0',
     //   'http://shp.qpic.cn/tgos/8413/77d8d6cea7fe19ec479be1462a6f244c/0',
     //   'http://shp.qpic.cn/tgos/5068/f9dd8088e88dcfb16d96abce75bb7826/0',
     // ];
     let instancesUrl = [
       'https://game.ikbh.top/2c918082819b40a201819b40a2e90000'
     ];
      const length = instancesUrl.length;
      let instances = [];
      let finished = 0;

     instancesUrl.forEach((file, index) => {
       this.getURLBase64(file).then(res => {
         console.log(res)
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
      /*  reader.onload = (e) => {
          const image = new Image();
          let resu = e.target.result;
          console.log(resu)
          debugger
          image.src = e.target.result;
          image.onload = () => {
            // 图片实例化成功后存起来
            instances[index] = image;
            finished++;
            if (finished === length) {
              this.drawImages(instances);
            }
          };
        };*/
      });
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

    drawImages(images) {
      console.log(images)
      const width = this.widths;
      // const heights = images.map((item) => (width / item.width) * item.height);
      const heights = images.map((item) =>item.height);
      const canvas = document.createElement("canvas");
      // canvas.width = width;
      const widths = images.map((item) => item.width)
      let  sumWidth = 0;
      for (var i = 0; i < 4; i++){
        sumWidth = widths[i] + sumWidth;
      }
      canvas.width = sumWidth;
      let a = parseInt(images.length / 4);
      // canvas.height = heights.reduce((total, current) => total + current);
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
      console.log(canvas)
      const base64Url = canvas.toDataURL("image/jpeg", this.quality);
      debugger
      this.dealImages(base64Url);
      this.downloadFile(this.createFileName(), base64Url);
      // if (this.returnType == "base64" && this.isAutoDownload) {
      //   this.downloadFile(this.createFileName(), base64Url);
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
    createFileName() {
      return "图片拼接" + new Date().getTime();
    },
  },
};
</script>

<style>
.choosefile-btn {
  display: inline-block;
  padding: 10px 20px;
  margin-top: 40px;
  background: rgb(83, 170, 148);
  border-radius: 8px;
  color: #efeefe;
}
</style>
