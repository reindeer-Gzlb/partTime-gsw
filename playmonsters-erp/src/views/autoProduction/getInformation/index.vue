<template>
  <div class="app-container">
    <!-- <h2>生成器</h2> -->
    <div class="page-content" v-loading="loading">
      <el-form labelWidth="100px" :inline="true" :model="formData" ref="form" :rules="rules" class>
        <div class="recovery-header">
          <el-form-item label="游戏类型" prop="type" @click="showGameType = true">
            <el-select v-model="formData.type" placeholder @change="gameTypeSelect">
              <el-option v-for="item2 in dict.type.game_type" :key="item2.label" :label="item2.label"
                :value="item2.label"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="campId" v-if="formData.type == '王者荣耀'" label="营地ID" class="attr-input-content">
            <div class="input-btn">
              <el-input v-model="formData.campId" color="#333" placeholder="请输入营地ID加载数据"
                @keyup.enter.native="getRoleList"></el-input>
              <el-button type="primary" @click="getRoleList" style="margin-left:20px"
                :loading="btnLoading">加载数据</el-button>
            </div>
          </el-form-item>
          <!-- <el-form-item prop="campId" v-if="formData.type == '和平精英'" label="QQ授权" class="attr-input-content">
            <div class="input-btn">
              <el-button type="primary" @click="openQRcodeQQ" style="margin-left:20px"
                :loading="btnLoading">授权营地</el-button>

            </div>
          </el-form-item> -->
          <el-form-item prop="roleId" v-if="formData.type == '王者荣耀'" label="角色列表" class="attr-input-content">
            <div class="input-btn">
              <el-select v-model="formData.roleId" placeholder @change="roleSelect">
                <el-option v-for="item2 in roleList" :key="item2.zdytext" :label="item2.zdytext"
                  :value="item2.roleId"></el-option>
              </el-select>
            </div>
          </el-form-item>
          <!-- <el-form-item label="手机号">
            <el-input v-model="formData.tel" color="#333" placeholder="请输入您的手机号"></el-input>
          </el-form-item>-->
          <!-- <el-form-item label="账号估值">
            <el-input v-model="formData.valuNum" readonly color="#333" placeholder></el-input>
          </el-form-item>-->
        </div>
        <div class="attr-list input-ml" v-if="attrList && attrList.length">
          <template v-for="item in attrList">
            <el-form-item :label="item.fieldName" :prop="item.remark" v-if="item.fieldType == 'checkbox'" :key="item.id">
              <el-checkbox-group v-model="formData[item.remark]" @change="change($event, item.fieldName, item.id)">
                <el-checkbox :label="check2.fieldName" v-for="(check2) in item.attributes" :key="check2.id">{{
                  check2.fieldName }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
            <el-form-item :label="item.fieldName" :prop="item.remark" class="attr-input-content" :key="item.id"
              v-else-if="item.fieldType == 'regional' || item.fieldType == 'dan' || item.fieldType == 'radio'">
              <el-radio-group v-model="formData[item.remark]">
                <el-radio :label="check2.fieldName" v-for="(check2, index) in item.attributes" :key="index"
                  @change="change($event, item.fieldName, item.id)">{{ check2.fieldName }}</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item :label="item.fieldName" :prop="item.remark" class="attr-input-content" :key="item.id"
              v-else-if="item.fieldType == 'number'">
              <el-input color="#333" placeholder v-model="formData[item.remark]" border="surround" type="number"
                @change="change({ detail: { data: item } }, item.fieldName, item.id)"></el-input>
            </el-form-item>

            <el-form-item :label="item.fieldName" v-else-if="item.fieldName != '营地ID'" :prop="item.remark"
              class="attr-input-content" :key="item.id">
              <el-input border="surround" v-model="formData[item.remark]" color="#333" placeholder></el-input>
            </el-form-item>
          </template>
          <!-- 20230214新增 -->
          <el-form-item :label="'是否二次'" prop="shiming" v-if="formData.type == '王者荣耀'">
            <el-radio v-model="formData['shiming']" :label="1">有二次</el-radio>
            <el-radio v-model="formData['shiming']" :label="0">包人脸</el-radio>
          </el-form-item>
          <el-form-item :label="'主图类型'" prop="textImg" v-if="formData.type == '王者荣耀'">
            <el-radio v-model="titleImgType" :label="1">正方形</el-radio>
            <el-radio v-model="titleImgType" :label="2">长方形</el-radio>
          </el-form-item>
          <!-- <el-form-item :label="'文字图'" prop="textImg">
            <el-radio v-model="formData['textImg']" :label="1">生成</el-radio>
            <el-radio v-model="formData['textImg']" :label="0">不生成</el-radio>
          </el-form-item>
          <el-form-item :label="'详情图'" prop="detailsImg">
            <el-radio v-model="formData['detailsImg']" :label="1">生成</el-radio>
            <el-radio v-model="formData['detailsImg']" :label="0">不生成</el-radio>
          </el-form-item> -->
        </div>

        <el-form-item label="生成结果" v-if="formData.type == '王者荣耀'">
          <el-input type="textarea" :rows="5" placeholder="生成结果后显示..." v-model="formData.morInfo"
            style="width:700px"></el-input>
        </el-form-item>
        <el-form-item label="自动识别" v-if="formData.type != '王者荣耀'">
          <el-input type="textarea" :rows="5" placeholder="请输入详情...点击空白区域自动识别" v-model="zidong" style="width:700px"
            @change="zidongChange"></el-input>
        </el-form-item>
      </el-form>
      <div class="btns">
        <el-button type="primary" @click="subForm" class="merge-sub" :loading="prodloading">生成</el-button>
        <el-button type="primary" @click="copyForm" class="merge-sub width-w"
          v-if="formData.type == '王者荣耀'">立即复制</el-button>
        <el-button type="primary" @click="submit" class="merge-sub width-w" v-if="flag == 'addDialog'">确认</el-button>
        <el-button type="primary" @click="openAdd" class="merge-sub width-w" :disabled="!isReady"
          v-if="flag != 'addDialog'">上架</el-button>
      </div>
      <div class="imgs-box">
        <el-image v-for="(img, index) in imgs" :key="index" style="width: 100%;" :src="img"
          @click="preview(index)"></el-image>
      </div>
    </div>
    <addDialog ref="addDialog" v-if="flag !== 'addDialog'"></addDialog>
    <QQcodeDialog ref="QQcodeDialog"></QQcodeDialog>
  </div>
</template>

<script>
import { postUpload } from "@/api/upload";
import infoApi from "@/api/autoProduction/information.js";
import addDialog from "../../businessCenter/myAccount/add";
import QQcodeDialog from "./QQcodeDialog.vue";
import makeImgsV2 from "@/utils/makeImgs-v2";
export default {
  dicts: ["game_type"],
  name: "information",
  props: ["ydid", "flag"],
  components: { addDialog, QQcodeDialog },
  data() {
    return {
      loading: false,
      btnLoading: false,
      prodloading: false,
      isReady: false,
      type: '',
      titleImgType: 1,//主图类型 1正方形，2长方形
      formData: {
        type: "",
        morInfo: "",
        roleValue: "",
        nobNum: "",
        inscriptions: "",
        yinji: "",
        zijing: "",
        xingyung: "",
        detailsImg: 0,
        shiming: 1,
        textImg: 1,
        skinList: []
      },
      zidong: '',
      gameTypeList: [],
      attrList: [],
      valuationList: [], //估值列表
      codeImg: null,
      isWin: false,
      showRoleList: false, //角色弹窗
      roleList: [], //角色数据
      allSkins: [], //所有皮肤
      selectList: [], //王者选中的皮肤
      titleImg: "",
      detailImg: [],
      imgs: [], //生成的皮肤图片
      rules: {
        campId: [{ required: true, message: "请输入营地ID", trigger: "blur" }],
        roleId: [{ required: true, message: "请选择角色", trigger: "change" }]
      }
    };
  },
  watch: {
    // ydid(val){
    //   console.log('%c 🍦 val: ', 'font-size:20px;background-color: #E41A6A;color:#fff;', val);
    //   this.formData.campId = val
    //   this.$forceUpdate()
    // }
  },
  mounted() {
    // this.init();
  },
  activated() {
    this.isReady = false;
  },
  methods: {
    // 打开新增弹窗

    openAdd() {
      let obj = this.formData;
      obj.detailImg = this.detailImg;
      obj.titleImg = this.titleImg;
      this.$refs.addDialog.open("SCQ", obj);
    },
    // 确认按钮
    submit() {
      let obj = JSON.parse(JSON.stringify(this.formData));
      if (this.titleImg) {
        obj.titleImg = this.titleImg;
      }
      if (this.detailImg && this.detailImg.length) {
        obj.detailImg = JSON.parse(JSON.stringify(this.detailImg));
      }
      if (this.formData.type != '王者荣耀') {
        obj.morInfo = this.zidong
      }
      this.$emit("submit", obj);
      this.$refs.form.resetFields();
      this.zidong = ''
      this.formData.morInfo = "";
      this.formData.type = obj.typed;
      this.formData.skinList = [];
      this.detailImg = [];
      this.titleImg = "";
      this.imgs = [];
    },
    // 从新增弹窗打开
    updateCamp(data, flag) {
      // console.log("%c Line:194 🍓 flag", "color:#e41a6a", flag);
      // console.log("%c Line:194 🍅 data", "color:#4fff4B", data);
      if (data.type == '王者荣耀') {
        this.$set(this.formData, 'type', data.type)
        // console.log("%c Line:209 🍫", "color:#93c0a4", 'fouze ');
        this.$refs.form.resetFields();
        this.formData.morInfo = "";
        this.formData.skinList = [];
        // if (this.type) {
        //   this.formData.type = this.type
        // }
        this.detailImg = [];
        this.titleImg = "";
        this.imgs = [];
        this.formData.campId = data.campId;
        this.$forceUpdate();
        this.init(data.type)
      } else {
        this.$set(this.formData, 'type', data.type)
        // console.log("%c Line:198 🍭  this.formData.type", "color:#e41a6a", this.formData.type);
        this.zidong = data.des
        this.$refs.form.resetFields();
        this.formData.morInfo = "";
        this.formData.skinList = [];
        this.detailImg = [];
        this.titleImg = "";
        this.imgs = [];
        this.$forceUpdate();
        this.init(data.type)
      }

    },
    // 初始化
    init(flag) {
      // console.log("%c Line:228 🥚 flag", "color:#ed9ec7", flag);
      this.loading = true;
      // this.formData.type
      // console.log("%c Line:230 🥛 this.formData", "color:#ea7e5c", this.formData);
      this.valuationList = [];
      this.formData.type = flag
      this.getAttrList(flag);
    },
    //选择游戏类型
    gameTypeSelect(e) {
      this.showGameType = false;
      this.formData.type = e;
      this.getAttrList(e);
      this.formData = {
        type: e,
        tel: this.formData.tel,
        morInfo: ""
      };
      this.valuationList = [];
    },
    // 获取选项类型
    getAttrList(name) {
      this.loading = true;
      infoApi
        .attrTypeList({
          type: name,
          cfgType: 2
        })
        .then(res => {
          this.loading = false;
          res.forEach(item => {
            if (item.fieldType == "checkbox") {
              this.$set(this.formData, `checkboxValue${item.id}`, []);
              if (!item.remark) {
                item.remark = `checkboxValue${item.id}`;
              }
              if (this.formData.type != '王者荣耀') {
                this.allSkins = item.attributes;
              }

              this.rules[item.remark] = {
                required: true,
                type: "array",
                min: 1,
                message: "请选择",
                trigger: ["change"]
              };
            } else {
              if (!item.remark) {
                item.remark = `input${item.id}`;
              }
              this.$set(this.formData, item.remark, '');
              this.rules[item.remark] = {
                required: true,
                message: "请输入内容",
                trigger: ["blur", "change"]
              };
            }
          });
          this.formData.skinList = []
          this.selectList = []
          this.$nextTick(() => {
            if (this.zidong) {
              this.zidongChange()
            }
          })
          this.attrList = res;
        });
    },
    // 和平精英授权QQ营地
    openQRcodeQQ() {
      this.$refs.QQcodeDialog.open()
//       let obj = {
//         filterParams:
//           { "210": [{ "value": "6377", "cmd": "params_new23=123009", "style": "201" }], "st7": [{ "selectType": 1, "value": "sp1", "style": "320", "cmd": "sortpolicy=1" }], "st6": [{ "selectType": 1, "value": "2123001,10874,0", "key": "st6", "style": "209", "cmd": "pg_cate_brand_model=2123001,10874,0", "type": "brand" }], "sysbrand": [{ "value": "sysbrand_2123001,0,0", "style": "510", "cmd": "pg_cate_brand_model=2123001,0,0", "type": "sysCateWall" }] },
//         keyword: '游戏交易',
//         keywordExtParam:
//           [{ "pgCate": { "pgModelId": "0", "pgBrandId": "0", "pgCateId": "2123001", "openHistory": false, "filterCateType": "1" }, "style": "510", "t": 2, "cateName": "游戏账号", "value": "sysbrand_2123001,0,0", "key": "sysbrand", "type": "sysCateWall", "cmd": "pg_cate_brand_model=2123001,0,0" }],
//         pageIdParams:
//           { "refpagequery": "", "refpagetype": "O1046", "refsubpageid": "" },
//         pagenum: 1,
//         pagesize: 20,
//         searchRecommendRankAB: "A",
//         searchStandardPropertiesAB: 'B',
//         search_9_0_0: 'B',
//         searchfrom: 3,
//         searchsuggestcate: 1,
//         sessionStr:
//           { "session": "9bcff43d47665045125da3851778bc4e9ac44e9d" },
//         zpm: `	
// 1^V1008^2^2^游戏交易`,
//         pushcode: 3
//       }
//       infoApi.transferSearch(obj).then(res => {
//         console.log("%c Line:341 🥤 res", "color:#93c0a4", res);
//       })
    },
    //获取角色列表
    getRoleList() {
      if (this.formData.campId) {
        this.btnLoading = true;
        infoApi
          .getRoleList({
            campId: this.formData.campId
          })
          .then(res => {
            if (res.data) {
              this.btnLoading = false;
              this.roleList = res.data;
              if (this.roleList.length) {
                //遍历添加展示数据
                this.roleList.forEach(item => {
                  item.zdytext = `${item.areaName}/${item.roleJob}/${item.roleName}`;
                });
                this.formData.roleValue = this.roleList[0].zdytext;
                this.formData.roleId = this.roleList[0].roleId;
                this.getRoleInfo();
              }
            }
          })
          .catch(err => {
            this.btnLoading = false;
          });
      } else {
        this.$alert("请先填写营地ID！");
      }
    },
    //角色选中
    roleSelect(e) {
      this.roleList.forEach(item => {
        if (item.roleId == e) {
          this.formData.roleValue = item.zdytext;
        }
      });
      this.getRoleInfo();
    },
    aa() {
      this.showRoleList = true;
    },
    //获取角色详情
    getRoleInfo() {
      if (this.formData.roleId) {
        this.btnLoading = true;
        infoApi
          .getRoleInfo({
            campId: this.formData.campId,
            roleId: this.formData.roleId
          })
          .then(res => {
            this.btnLoading = false;
            for (let key in res.data.skinMap) {
              if (
                typeof res.data.skinMap[key] == "string" ||
                typeof res.data.skinMap[key] == "number"
              ) {
                this.$set(this.formData, key, String(res.data.skinMap[key]));
              } else {
                this.$set(this.formData, key, res.data.skinMap[key]);
              }
            }
            // 排序
            this.attrList.forEach(item => {
              if (item.fieldType == "checkbox") {
                this.arrSort(res.data.listJson, item);
                this.allSkins = item.attributes;
              }
            });
            // 2023.2.14注释，写在arrSort方法中了
            // this.form.skinList = res.data.listJson.map((item)=> item.szTitle)
          });
      } else {
        this.$alert("请先选择角色信息！");
      }
    },
    // 找出选中项的排序号，给选中项排序，方便按顺序生成复制资料
    arrSort(list, item, flag) {
      let arr = [];
      if (list && list.length) {
        item.attributes.forEach(t2 => {
          list.forEach(t => {
            if (flag == "name") {
              if (t2.fieldName == t) {
                arr.push(t2);
              }
            } else {
              if (t2.fieldName == t.szTitle) {
                arr.push(t2);
              }
            }
          });
        });
      }
      // let arr2 = arr.sort(this.sortBy("sort"));

      // this.valuationList.push({
      //   fieldName: item.fieldName,
      //   id: item.id,
      //   list: arr2
      // });
      if (this.valuationList.length) {
        // console.log("%c Line:410 🍬排序前 this.valuationList", "color:#33a5ff", this.valuationList);
        let arr3 = []
        this.valuationList.forEach(r => {
          if (r.id == item.id) {
            item.attributes.forEach(t2 => {
              r.list.forEach(t => {
                if (t2.fieldName == t) {
                  arr3.push(t);
                }
              });
            });
            r.list = arr3
          }
        })
      }
      this.selectList = arr.map(item => item.fieldName);
      this.formData.skinList = arr.map(item => item.fieldName);
      this.$forceUpdate();
    },
    // 排序方法
    sortBy(field) {
      //根据传过来的字段进行排序
      return (x, y) => {
        return x[field] - y[field];
      };
    },
    // 自动识别
    zidongChange() {
      // this.zidong
      let arr = []
      if (this.zidong) {
        arr = this.zidong.split(' ')
      }
      // 回显勾选
      this.attrList.forEach(item => {
        if (this.formData[item.remark]) {
          if (item.fieldType == "checkbox") {
            item.attributes.forEach(t2 => {
              arr.forEach(t3 => {
                if (t3 == t2.fieldName) {
                  if (this.formData[item.remark].indexOf(t3) == -1) {
                    this.formData[item.remark].push(t3)
                  }
                }
              })
            });
            this.selectList = JSON.parse(JSON.stringify(this.formData[item.remark]))
            // 赋值到selectlist

          }
        }
      });
      // this.formData.skinList = arr
    },
    //生成
    async subForm() {
      this.zidongChange()
      this.prodloading = true;
      // this.$refs.uForm1.validate().then(res => {
      let data = {
        type: this.formData.type,
        attrList: []
      };
      this.valuationList.forEach((item, index) => {
        data.attrList.push({
          id: item.id,
          fieldName: item.fieldName,
          valuNum: 0
        });
        if (item.list instanceof Array) {
          item.list.forEach(option => {
            if (option.valuation) {
              data.attrList[index].valuNum += Number(option.valuation);
            }
          });
        } else if (item.list instanceof Object) {
          if (item.list.fieldType == "number") {
            data.attrList[index].valuNum =
              item.list.valuation * this.form[item.list.remark];
          } else {
            data.attrList[index].valuNum = item.list.valuation;
          }
        }
      });

      this.attrList.forEach(item => {
        if (
          ["checkbox", "regional", "dan", "radio", "number"].indexOf(
            item.fieldType
          ) > -1
        ) {
          const attrListIndex = data.attrList.findIndex(
            attrItem => attrItem.id == item.id
          );
          if (attrListIndex == -1) {
            data.attrList.push({
              id: item.id,
              fieldName: item.fieldName,
              valuNum: 0
            });
          }
        }
      });
      // 生成图片
      // 生成之前先把选中的皮肤排序

      let str = "";
      if (this.formData.tel) {
        str += `手机号:${this.formData.tel} `;
      }

      this.attrList.forEach(item => {
        if (this.formData[item.remark]) {
          if (item.fieldType == "checkbox") {
            // 排序  多选的皮肤
            this.arrSort(this.selectList, item, "name");

            str += `${item.fieldName}:`;
            str += this.formData[item.remark].join(" ");
            str += " ";
          } else {
            if (item.fieldName == "区服") {
              str += this.formData[item.remark];
              str += " ";
            } else if (item.fieldName == "当前段位") {
              str += " ";
            } else if (item.fieldName == "VIP等级") {
              str += `V`;
              str += this.formData[item.remark];
              str += " ";
            } else {
              str += `${item.fieldName}:`;
              str += this.formData[item.remark];
              str += " ";
            }
          }
        }
      });
      if (this.formData.campId) {
        str += `营地ID:${this.formData.campId} `;
      }
      this.$set(this.formData, "morInfo", str);
      //
      let obj = JSON.parse(JSON.stringify(this.formData));
      delete obj.morInfo;
      delete obj.ppxiangqing;
      delete obj.skinList;
      // this.selectList
      // this.valuationList
      // console.log("%c Line:552 🥚 this.selectList", "color:#33a5ff", this.selectList);
      // 本地生成
      let arr = [];
      if (this.formData.type == '王者荣耀') {

        if (this.allSkins && this.allSkins.length) {
          this.allSkins.forEach(t => {
            if (this.selectList.indexOf(t.fieldName) > -1) {
              arr.push(t);
            }
          });
        }
        let img
        if (this.titleImgType == 1) {
          img = await makeImgsV2.makeTitleImg(obj, arr)
          // console.log("首图");
        } else {
          img = await makeImgsV2.makeTitleImgChang(obj, arr)
        }
        let img2 = await makeImgsV2.makeDetailImg(arr)
        this.prodloading = false;
        this.isReady = true
        // console.log("详情图");
        this.imgs = [img];
        this.imgs = this.imgs.concat([img2]);
        // 上传生成的base64图片
        let titImg = this.base64ToBlob(img);
        let detailImg = this.base64ToBlob(img2);
        let formData = new FormData();
        let formData2 = new FormData();
        formData.append("file", titImg);
        formData2.append("file", detailImg);
        postUpload(formData)
          .then(res => {
            this.titleImg = res.msg;
          })
          .catch(e => { });
        postUpload(formData2)
          .then(res => {
            this.detailImg = [res.msg];
          })
          .catch(e => { });
      } else {
        // this.valuationList
        this.prodloading = false;
        let list1 = JSON.parse(JSON.stringify(this.valuationList))
        let list2 = JSON.parse(JSON.stringify(this.attrList))
        let img
        img = await makeImgsV2.makeTitleImgYjwj(obj, list1, list2)
        this.prodloading = false;
        this.isReady = true
        // console.log("首图");
        this.imgs = [img];
        // 上传生成的base64图片
        let titImg = this.base64ToBlob(img);
        let formData = new FormData();
        formData.append("file", titImg);
        postUpload(formData)
          .then(res => {
            this.titleImg = res.msg;
          })
          .catch(e => { });
      }

    },
    // 图片转换
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
    // 选择项改变
    change(e, fieldName, id) {
      let currentData;
      for (let i = 0; i < this.valuationList.length; i++) {
        if (this.valuationList[i].id == id) {
          currentData = this.valuationList[i];
          break;
        }
      }
      if (currentData) {
        currentData.list = e;
      } else {
        this.valuationList.push({
          fieldName,
          id,
          list: e
        });
      }
      // this.valuationList
      // console.log("%c Line:657 🍫 this.valuationList", "color:#f5ce50", this.valuationList);
      this.selectList = e;
      // if (this.formData.type == "王者荣耀" || this.formData.type == "永劫无间") {
      //   this.selectList = e;
      // }
    },
    copyForm() {
      let that = this;
      this.$copyText(that.formData.morInfo).then(
        function (e) {
          uni.showToast({
            title: "已复制，请联系客服",
            icon: "none",
            duration: 1500
          });
        },
        function (e) { }
      );
    },
    // 预览图片
    preview(index) {
      this.$viewerApi({
        images: this.imgs,
        options: {
          initialViewIndex: index
        }
      });
    }
    // saveImg() {
    // 	if (this.codeImg) {
    // 		const image = new Image()
    // 		image.setAttribute('crossOrigin', 'anonymous')
    // 		image.onload = function() {
    // 			const canvas = document.createElement('canvas')
    // 			canvas.width = image.width
    // 			canvas.height = image.height
    // 			const context = canvas.getContext('2d')
    // 			context.drawImage(image, 0, 0, image.width, image.height)
    // 			const url = canvas.toDataURL('image/png') // 得到图片的base64编码数据
    // 			const a = document.createElement('a') // 生成一个a元素
    // 			const event = new MouseEvent('click') // 创建一个单击事件
    // 			a.download = name || '下载' // 设置图片名称
    // 			a.href = url // 将生成的URL设置为a.href属性
    // 			a.dispatchEvent(event) // 触发a的单击事件
    // 		}
    // 		image.src = this.codeImg
    // 	}

    // },
  }
};
</script>
<style scoped lang="scss">
.app-container {
  position: relative;
  width: 870px;
  // margin: 0 auto;
  height: calc(100vh - 100px);
  overflow-y: auto;
}

.app-container ::v-deep .el-input {
  width: 250px;
}

.app-container ::v-deep .el-checkbox-group {
  max-width: 680px;
}

h2 {
  text-align: center;
}

.pointer {
  cursor: pointer;
}

.btns {
  text-align: center;
}

.imgs-box {
  width: 500px;
  margin: 0 auto;
  margin-top: 20px;
}
</style>
