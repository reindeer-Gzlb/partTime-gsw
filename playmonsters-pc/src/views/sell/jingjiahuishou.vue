<template>
  <div class="BasicInformation">
    <div class="location">
      <!-- <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>基本信息</el-breadcrumb-item>
      </el-breadcrumb> -->
    </div>

    <div class="title" v-if="currentGame&&currentGame.name">
      <span></span>
      <h5>当前选择游戏为</h5>
      <img :src="currentGame.icon" alt="">
      <p>{{ currentGame.name }}</p>
      <button @click="goback()">重选</button>
    </div>

    <div class="content" v-if="showFillInformation">
      <div class="top">
        <ul>
          <!-- <li >
            <div class="h6 flex">
              <span>出售模式</span>
              <img @click="modeDesShow = true" style="width:18px;height:18px;cursor: pointer;" src="@/assets/icon/icon37.png" alt="" />
            </div>
            <ol>
              <li
                v-for="(item, index) in modeOptions"
                :key="item.id"
                :class="{ active: formData.inType == item.id }"
                @click="areaClick(item, 'inType')"
              >
                <p class="left">{{ item.name }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="" />
                </p>
              </li>
            </ol>
          </li> -->
          <li v-if="sysList && sysList.length && currentGame.type == 0">
            <div class="h6">操作系统</div>
            <ol>
              <li v-for="(item, index) in sysList" :key="item.id" :class="{ active: formData.systemId == item.id }"
                @click="areaClick(item, 'system')">
                <p class="left">{{ item.fieldName }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="">
                </p>
              </li>
            </ol>
          </li>
          <li v-if="carrierList && carrierList.length && currentGame.type == 0">
            <div class="h6">运营商</div>
            <ol>
              <li v-for="(item, index) in carrierList" :key="item.id" :class="{ active: formData.carrierId == item.id }"
                @click="areaClick(item, 'carrierId')">
                <p class="left">{{ item.fieldName }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="">
                </p>
              </li>
            </ol>
          </li>
          <li v-if="areaList && areaList.length&& currentGame.type == 1">
            <div class="h6">大区</div>
            <ol>
              <li v-for="(item, index) in areaList" :key="item.id" :class="{ active: formData.groupId == item.id }"
                @click="areaClick(item, 'area')">
                <p class="left">{{ item.fieldName }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="">
                </p>
              </li>
            </ol>
          </li>
          <li v-if="serverList && serverList.length&& currentGame.type == 1">
            <div class="h6">服务器</div>
            <ol>
              <li v-for="(item, index) in serverList" :key="item.id" :class="{ active: formData.serverId == item.id }"
                @click="areaClick(item, 'server')">
                <p class="left">{{ item.fieldName }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="">
                </p>
              </li>
            </ol>
          </li>
          <li v-show="formData.serverId || formData.systemId">
            <div class="h6">账号来源</div>
            <ol>
              <!-- five -->
              <li
                v-for="(item, index) in laiyuan_list"
                :key="item.id"
                :class="{ active: formData.laiyuan == item.name }"
                @click="areaClick(item, 'laiyuan')"
              >
                <p class="left">{{ item.name }}</p>
                <p class="right">
                  <img src="@/assets/icon/icon27.png" alt="" />
                </p>
              </li>
            </ol>
          </li>
          
          <li v-show="formData.laiyuan">
            <div class="h6">系统信息</div>
            <ol>
              <li>
                <div class="titl">
                  <span></span>
                  <p>联系电话</p>
                </div>
                <input type="text" placeholder="请输入联系电话" v-model="formData.tel" />
              </li>
              <li>
                <div class="titl">
                  <span></span>
                  <p>联系QQ</p>
                </div>
                <input type="text" placeholder="请输入联系QQ" v-model="formData.qq" />
              </li>
              <li>
                <div class="titl">
                  <span></span>
                  <p>联系微信</p>
                </div>
                <input type="text" placeholder="请输入联系微信" v-model="formData.weixin" />
              </li>
              <li>
                <div class="titl">
                  <span></span>
                  <p>交易保障合同</p>
                  
                </div>
              </li>
            </ol>
          </li>


        
         
        </ul>
      </div>
      <!-- <div class="bottom">
        您的选择：
        <span v-if="gameActive.one.name">{{ gameActive.one.fieldName }}&ensp; {{ gameActive.two.fieldName }} &ensp;
          {{
            gameActive.three.fieldName }}</span>
        <span v-else>暂未选择</span>
      </div> -->
    </div>
    <button class="button" v-if="showFillInformation" @click="nextStep()"> 下一步 填写信息</button>
  </div>
  <!-- 自定义字段展示 -->
  <div class="fillInformation " v-show="!showFillInformation">

    <div class="title">
      <img src="@/assets/icon/icon38.png" alt="">
      <span>当前选择</span>
    </div>
    <div class="Selected" v-if="currentGame&&currentGame.name">
      <span>{{ currentGame.name }}&ensp;---&ensp;</span>
      <span>{{ formData.systemName }}{{ formData.carrierName }}{{ formData.groupName }}{{ formData.serverName }}
        &ensp;---&ensp;</span>
      <span>{{ formData.laiyuan }} &ensp;---&ensp;</span>
      <button @click="goback2">重新选择</button>
    </div>
    <div class="title">
      <img src="@/assets/icon/icon39.png" alt="">
      <span>账号信息</span>
    </div>
    <div class="form-box">
      <el-form v-if="attrs && attrs.length" ref="ruleFormRef" :model="formData" :rules="rules" label-width="auto"
        class="demo-ruleForm" status-icon>
        <template v-for="(custom, i) in attrs" :key="custom.id">
          <el-form-item :label="custom.fieldName" :prop="custom.aavName"
            v-if="custom.fieldType == 'input' || custom.fieldType == 'number'" :key="custom.id">
            <el-input @change="makeDes" v-model="formData[custom.aavName]" style="width: 300px;">
            </el-input>
          </el-form-item>

          <el-form-item :label="custom.fieldName" :prop="custom.aavName" v-if="custom.fieldType == 'radio'"
            :key="custom.id">
            <el-select v-model="formData[custom.aavName]" style="width: 300px;" @change="radioClick($event, custom)"
              filterable>
              <el-option v-for="checkItem in custom.childAttributes" :key="checkItem.id" :label="checkItem.fieldName"
                :value="checkItem.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item :label="custom.fieldName" :prop="custom.aavName" v-if="custom.fieldType == 'checkbox'"
            :key="custom.id">
            <el-checkbox-group v-model="formData[custom.aavName + 'rander']"
              @change="checkboxChange($event, custom.aavName)">
              <el-checkbox :label="checkItem.id" v-for="checkItem in custom.childAttributes" :value="checkItem.id"
                :key="checkItem.id">{{ checkItem.fieldName }}</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
          <el-form-item :label="custom.fieldName" :prop="custom.aavName" v-if="custom.fieldType == 'boxradio'"
            :key="custom.id">
            <div @click="setOffset($event)">
              <el-checkbox v-for="(checkItem, i) in custom.childAttributes" :key="checkItem.id"
                v-model="checkItem.ischeck" @change="boxradioCheckClick($event, custom, checkItem, i)">
                {{ checkItem.fieldName }}
                <el-radio-group class="boxradioRadioBox" :style="{ left: boxradioRadioBoxLeft, top: boxradioRadioBoxTop }"
                  :class="{ 'show': checkItem.id == boxradioIsShow }" v-model="formData[custom.aavName + i]"
                  @change="boxradioChange($event, checkItem, custom, i)">
                  <el-radio v-for="item2 in checkItem.childGameAttributes" :key="item2.id" :label="item2.id">{{
                    item2.fieldName }}</el-radio>
                </el-radio-group>
              </el-checkbox>
            </div>
          </el-form-item>
        </template>

        <!-- account -->
        <el-form-item label="游戏账号" prop="account">
          <el-input v-model="formData['account']" style="width: 300px;">
          </el-input>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="formData['price']" style="width: 300px" @change="priceChange">
          </el-input>
          <div style="margin-left: 320px; margin-top: -50px">
            <p style="margin: 0; height: 20px">
              手续费标准：5%（平台收取售价的5%，单笔最低收取50），当前商品交易服务费{{ fuwufei }}元,
            </p>
            <span>实际交易服务费以商品最终成交价为计算标准。</span>
          </div>
        </el-form-item>
        <el-form-item label="能否提供包赔材料" prop="assurance">
          <span style="cursor: pointer;">
            <el-icon @click="bpzlShowFn">
              <QuestionFilled />
            </el-icon>
          </span>
          <el-radio-group style="display:flex" v-model="formData['assurance']" >
            <el-radio label="能提供" size="large">能提供</el-radio>
            <el-radio label="不能提供" size="large">不能提供</el-radio>
            
          </el-radio-group>
        </el-form-item>
        <!-- <el-form-item label="商品标题" prop="title">
          <el-input v-model="formData['title']" style="width: 300px"> </el-input>
        </el-form-item> -->
        <el-form-item label="简介" prop="des">
          <el-input type="textarea" :rows="6" v-model="formData['des']" style="width: 600px;">
          </el-input>
        </el-form-item>
        <el-form-item label="游戏实名截图" prop="smImg" >
          <el-upload v-model:file-list="fileList1" action="#" accept="jpg,jpeg,png" list-type="picture-card"
            :http-request="uploadImg" :limit="1" :on-remove="handleRemove">
            <el-icon>
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="游戏截图" prop="des">
          <el-upload v-model:file-list="fileList2" action="#" accept="jpg,jpeg,png" list-type="picture-card"
            :http-request="uploadImg2" multiple :limit="100" :on-remove="handleRemove2">
            <el-icon>
              <Plus />
            </el-icon>
          </el-upload>
        </el-form-item>

        <div class="sub-box">
          <el-button :loading="loading" class="sub-btn" round type="primary" v-if="!showFillInformation" @click.stop="submit">
            提交</el-button>
        </div>

      </el-form>

    </div>
    <el-dialog v-model="bpzlShow" append-to-body>
      <div class="bpzl-box ql-editor" v-html="baopeiInfo">

      </div>
    </el-dialog>
    <!-- <el-dialog v-model="modeDesShow" title="出售模式说明" append-to-body>
      <div class="bpzl-box ql-editor" v-html="modeDes">

      </div>
    </el-dialog> -->
  </div>
</template>

<script lang="ts">
import { reactive, toRefs, onMounted, watch, onUnmounted, ref, getCurrentInstance } from 'vue';
import { House, Plus,QuestionFilled } from '@element-plus/icons-vue';
import { useRouter } from 'vue-router';
import kefu from '@/api/kefu';
import huishouApi from '@/api/huishou';
import api from '@/api/index';
import infoApi from "@/api/gujia";
import kefuApi from "@/api/kefu"
import { ElMessage, ElMessageBox } from 'element-plus'
import { stat } from 'fs';
// import { resolve } from 'path';
export default {
  name: 'BasicInformation',
  components: { House, Plus ,QuestionFilled},

  setup(props, ctx) {
    const internalInstance = getCurrentInstance()
    const router = useRouter();
    const ruleFormRef = ref()
    const state = reactive({
      modeDesShow:false,
      modeDes:'',

      baopeiInfo:'',
      imgUrl: '',
      loading: false,
      pageType: 'sale',
      // 填写信息模块是否显示
      showFillInformation: true,
      fileList1: [],
      fileList2: [],
      formData: {
        gameId: '',
        systemId: '',
        carrierId: '',
        groupId: '',
        serverId: '',
        title: '',
        systemName: '',
        carrierName: '',
        groupName: '',
        serverName: '',
        weixin: '',
        qq: '',
        laiyuan: '',
        account:'',

        des: "",
        roleValue: "",
        price: '',
        importantRemark: '',

        tel: '',
        smImg:'',
        assurance:'',
        inType:'2'
      },
      bpzlShow:false,
      fuwufei:'',
      // 账号来源
      laiyuan_list: [
        {
          id: 1,
          name: '自己注册'
        },
        {
          id: 2,
          name: '他人注册'
        }
      ],
      // 发布模式
      modeOptions: [{
        name: '官方代售+竞价回收',
						id: 1
					},
					{
						name: '只发布官方代售',
						id: 2
					},

				],
      userInfo:{},


      currentGame: {}, //当前选择的游戏
      sysList: [], //系统列表
      carrierList: [], //运营商列表
      areaList: [], //大区列表
      serverList: [], //服务器列表
      attrs: [],//全部属性
      boxradioIsShow: '',
      boxradioRadioBoxLeft: '',
      boxradioRadioBoxTop: '',
      rules: {
        price: [
          { required: true, message: '请输入价格', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        des: [
          { required: true, message: '请输入商品描述', trigger: 'blur' }
        ],
        smImg: [
          { required: true, message: '请上传商品主图', trigger: 'blur' }
        ],
        assurance: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        account:[
        { required: true, message: '请输入账号', trigger: 'blur' }
        ]
      },
      shimingLabel:''

    });

    onMounted(() => {
      initPage()
    });
    // 监听路由参数
    watch(
      () => router.currentRoute.value.query,
      (newValue, oldValue) => {
        if(router.currentRoute.value.path=='/index/jingjiahuishou'){
          initPage()
        }
      },
      { deep: true }
    );
    // 初始化
    const initPage = () => {
      state.attrs = []
      state.showFillInformation = true
      state.sysList = [] //系统列表
      state.carrierList = [] //运营商列表
      state.areaList = [] //大区列表
      state.serverList = [] //服务器列表
      state.formData = {
        gameId: '',
        systemId: '',
        carrierId: '',
        groupId: '',
        serverId: '',
        systemName: '',
        carrierName: '',
        groupName: '',
        serverName: '',
        des: "",
        roleValue: "",
        price: '',
        importantRemark: '',
        tel: '',
        weixin: '',
        qq: '',
        laiyuan: '',
        account: '',
        smImg:'',
        assurance:'',
        inType:'2'
      }
      // let currentGame = {
      //   name: router.currentRoute.value.query.name,
      //   id: router.currentRoute.value.query.id,
      //   type: router.currentRoute.value.query.type,
      //   icon: router.currentRoute.value.query.pcIconUrl
      // }
      state.pageType = router.currentRoute.value.query.pageType
      state.currentGame = {
        name: router.currentRoute.value.query.name,
        id: router.currentRoute.value.query.id,
        type: router.currentRoute.value.query.type,
        icon: router.currentRoute.value.query.pcIconUrl
      }
      state.formData.gameId = state.currentGame.id
      state.formData.gameName = state.currentGame.name
      if (state.currentGame.type == 0) {
        // 手游
        // 获取系统列表和运营商列表
        getSysList()
        getCarrierList()
      } else {
        // 端游
        // 获取大区列表
        getAreaList()
      }
      getUserInfo()
      getBaopeiInfo()
      getModeDes()
    }
    const getBaopeiInfo = ()=>{
      kefuApi.getNotices('bpzl').then(res => {
        if (res && res.length) {
          state.baopeiInfo = res[0].noticeContent
        }

      })
    }
    const getModeDes = ()=>{
      kefuApi.getNotices('fbms').then(res => {
        if (res && res.length) {
          state.modeDes = res[0].noticeContent
        }

      })
    }
    const getUserInfo = ()=>{
       // 获取个人信息
       let info = window.localStorage.getItem('userInfo')
        if (info) {
          state.userInfo = JSON.parse(info)
        }
    }
    const priceChange = (e) => {
      //console.log(e);
      let num = (e * 0.05).toFixed(2);
      if (num < 50) {
        num = 50;
      }
      state.fuwufei = num;
    };
    // 
    // 选择区服等
    const areaClick = (item, type) => {
      //console.log("%c Line:366 🍞 item", "color:#e41a6a", item);
      if (type == 'system') {
        state.formData.systemId = item.id
        state.formData.systemName = item.fieldName
      } else if (type == 'carrierId') {
        state.formData.carrierId = item.id
        state.formData.carrierName = item.fieldName
        // //console.log("%c Line:248 🍌 state.formData", "color:#fca650", state.formData);
        // 获取大区列表
        getAreaList(item.id)
      } else if (type == 'area') {
        state.formData.groupId = item.id
        state.formData.groupName = item.fieldName
        // 获取服务器列表
        getServerList(item.id)
      } else if (type == 'server') {
        // 选择服务器
        state.formData.serverId = item.id
        state.formData.serverName = item.fieldName
      } else if (type == 'laiyuan') {
        state.formData.laiyuan = item.name
      }else if (type == 'inType') {
        state.formData.inType = item.id
      }
      makeDes()
    }

    // 跳转
    const goLink = (link, query) => {
      if (query) {
        router.push({ path: link, query: query });
      } else {
        router.push({ path: link });
      }
    };
    // 返回
    // 跳转
    const goback = () => {
      router.replace({ path: '/index/sell', query: { pageType: state.pageType } });

    };
    // 
    const goback2 = () => {
      state.showFillInformation = true
    }
    // 获取顶级分类
    // const getDingjiList = () => {
    //   // //console.log('gameActive', );
    // };

    // 获取自定义字段
    // 获取游戏属性列表
    const constgetAttrList = () => {
      state.loading = true
      api.getGameConfig({ gameId: state.currentGame.id }).then(res => {
        state.loading = false
        if (res.code == 200) {
          //console.log("%c Line:414 🍌 res", "color:#fca650", res);
          state.attrs = []
          let selectAttrs = []
          let attrs = res.rows
          if (attrs.length) {
            attrs.forEach(r => {
             
              if (r.isEnable === '0' && r.hsisShow == 1) {
                if (r.fieldType == 'checkbox') {
                  state.formData[r.aavName + 'rander'] = []
                  state.formData[r.aavName] = ''
                } else {
                  state.formData[r.aavName] = ''
                  if(r.must == 'true'){
                    state.rules[r.aavName] = [{ required: true, message: '请输入', trigger: 'change' }]
                  }
                }
                selectAttrs.push(r)
              }
            });
          }
          // state.selectAttrs = selectAttrs
          state.attrs = JSON.parse(JSON.stringify(selectAttrs))
        }
      });
    }


    // 获取系统列表
    const getSysList = () => {
      kefu.getSystemList({
        gameId: state.currentGame.id,
        systemKey: 'system'
      }).then(res => {
        //console.log('res', res);
        if (res.code == 200) {
          state.sysList = res.rows;
        }
      });
    };

    // 获取运营商列表
    const getCarrierList = () => {
      let obj = {
        gameId: state.currentGame.id,
        systemKey: 'carrier'
      }
      kefu.getSystemList(obj).then(res => {
        if (res.code == 200) {
          state.carrierList = res.rows;
        }
      });
    };
    // 获取大区列表
    const getAreaList = (e) => {
      // //console.log("%c Line:308 🍒 e", "color:#3f7cff", e);
      var obj = {
        gameId: state.currentGame.id,
        systemKey: 'group'
      }
      if (e) {
        state.carrierList.forEach(i => {
          if (i.id == e) {
            obj.systemKey = i.systemKey + 'group'
          }
        })
      }

      kefu.getSystemList(obj).then(res => {
        if (res.code == 200) {
          state.areaList = res.rows;
        }
      });
    };


    // 获取服务器列表
    const getServerList = (e) => {
      var obj = {
        parentId: 1,
      }
      if (e) {
        state.areaList.forEach(i => {
          if (i.id == e) {
            obj.parentId = e
          }
        })
      }
      kefu.getServerList2(obj).then(res => {
        //console.log('获取服务器列表', res);
        if (res.code == 200) {
          state.serverList = res.rows;
        }
      });
    };



    // 填写信息——下一步
    const nextStep = () => {

      // 判断gameActive是否有值 state.formData state.currentGame
      console.log("%c Line:562 🍭 state.currentGame", "color:#7f2b82", state.currentGame);
      console.log("%c Line:562 🥪 state.formData", "color:#93c0a4", state.formData);
      if ((state.currentGame.type == '0' && state.formData.systemId && state.formData.carrierId) || (state.currentGame.type == 1&& state.formData.groupId && state.formData.serverId)) {
        state.showFillInformation = false;
        // state.formData
        console.log("%c Line:355 🍭 state.formData", "color:#ffdd4d", state.formData);
        constgetAttrList()
      } else {
        alert('请填写完整信息');
      }

    }

    // 单选项改变
    const radioClick = (item, custom) => {
      if(custom.fieldKey=='sm'){
        // state.shimingLabel = 
        custom.childAttributes&&custom.childAttributes.forEach(r=>{
          if(r.id==item){
            state.shimingLabel = r.fieldName
            console.log("%c Line:528 🍑 state.shimingLabel", "color:#fca650", state.shimingLabel);
          }
        })
      }
      makeDes()
    }
    // 多选项改变
    const checkboxChange = (e, key) => {
      if (e && e.length) {
        state.formData[key] = e.join(',')
      } else {
        state.formData[key] = ''
      }
      makeDes()
    }
    // 获取鼠标位置
    const setOffset = (event) => {
      let e2 = event || window.event;
      state.boxradioRadioBoxLeft = e2.clientX + 'px'
      state.boxradioRadioBoxTop = e2.clientY + 'px'
    }
    // 点击多选框单选的多选框点击事件
    const boxradioCheckClick = (e, custom, checkItem, i) => {
      if (state.boxradioIsShow == checkItem.id) {
        state.boxradioIsShow = ''
      } else {
        state.boxradioIsShow = checkItem.id
      }
      checkItem.fieldName = checkItem.fieldName.split('(')[0]
      setValue(custom)
      internalInstance.ctx.$forceUpdate()
    }
    // 点击多选单选框中的单选框事件
    const boxradioChange = (e, checkItem, custom, i) => {

      state.boxradioIsShow = ''
      checkItem.ischeck = true
      checkItem.childGameAttributes.forEach(r => {
        if (r.id == e) {
          checkItem.fieldName = checkItem.fieldName.split('(')[0] + '(' + r.fieldName + ')'
        }
      })
      setValue(custom)
    }
    // 原神多选框赋值
    const setValue = (custom) => {
      // 赋值
      let arr = []
      custom.childAttributes.forEach((item, ind) => {
        if (item.ischeck) {
          // 多选框选中 取子级单选选中的id
          arr.push(state.formData[custom.aavName + ind])
        }
      })
      if (arr.length) {
        state.formData[custom.aavName] = arr.join(',')
      } else {
        state.formData[custom.aavName] = ''
      }
      makeDes()
    }

    //上传主图
    // infoApi 
    // 图片上传
    const uploadImg = (UploadRequestOptions) => {
      let formData = new FormData();
      formData.append("file", UploadRequestOptions.file);
      // 获取本地token
      let token = localStorage.getItem("token");
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: token
        }
      }
      infoApi.postUpload(formData)
        .then(res => {
          state.formData.smImg = res.data.filePath;
          state.fileList1 = [{
            url: res.data.filePath,
            name: res.data.fileName
          }]
          //console.log("%c Line:567 🥪 res", "color:#b03734", res.data.filePath);
        })
        .catch(e => {
          //console.log("%c Line:567 🥪 e", "color:#b03734", e);
        });
    }
    // 图片预览
    const handlePictureCardPreview = () => {

    }
    // 移除
    const handleRemove = () => {
      state.formData.smImg = ''
      state.fileList1 = []
    }
    // 详情 图片上传
    const uploadImg2 = (UploadRequestOptions) => {
      let formData = new FormData();
      formData.append("file", UploadRequestOptions.file);
      infoApi.postUpload(formData)
        .then(res => {
          state.fileList2.push({
            url: res.data.filePath,
            name: res.data.fileName
          })
        })
        .catch(e => {
          //console.log("%c Line:567 🥪 e", "color:#b03734", e);

        });

    }
    // 移除详情图
    const handleRemove2 = (file, list) => {
      state.fileList2 = list
    }
    // 生成商品描述
    const makeDes = () => {
      let str = ''
      // if (this.form.tel) {
      // 	str += `手机号:${this.form.tel} `
      // }
      if (state.currentGame.name) {
        str += `游戏类型:${state.currentGame.name} `
      }
      if (state.formData.laiyuan) {
        str += `账号来源:${state.formData.laiyuan} `
      }
      str += `区服:${state.formData.systemName ? state.formData.systemName : ''}${state.formData.carrierName ? state.formData.carrierName : ''} ${state.formData.groupName} ${state.formData.serverName}  `
      state.attrs.forEach(item => {
        if (item.isEnable == '0' && item.hsisShow == '1' && state.formData[item.aavName] && (item.fieldType == 'number' || item.fieldType == 'input')) {
          // 输入框
          str += `${item.fieldName}:`
          str += state.formData[item.aavName]
          str += " "
        } else if (item.isEnable == '0' && item.hsisShow == '1' && item.fieldType == 'checkbox' && item.childAttributes && state.formData[item.aavName]) {
          str += `${item.fieldName}:`
          item.childAttributes.forEach(t1 => {
            if (state.formData[item.aavName].indexOf(t1.id) > -1) {
              str += t1.fieldName
              str += " "
            }
          })
        }
        else if (item.isEnable == '0' && item.hsisShow == '1' && item.fieldType == 'radio' && item.childAttributes && state.formData[item.aavName]) {
          str += `${item.fieldName}:`
          item.childAttributes.forEach(t1 => {
            if (state.formData[item.aavName] == t1.id) {
              str += t1.fieldName
              str += " "
            }
          })
        }
        else if (item.isEnable == '0' && item.hsisShow == '1' && item.fieldType == 'boxradio' && item.childAttributes && state.formData[item.aavName]) {
          str += `${item.fieldName}:`
          item.childAttributes.forEach((t1, ind) => {
            if (t1.ischeck) {
              t1.childGameAttributes.forEach(t2 => {
                if (t2.id == state.formData[item.aavName + ind]) {
                  str += t2.fieldName
                  str += " "
                }
              })

            }
          })
        }

      })
      state.formData.des = str
    }

// 打开包赔弹窗
const bpzlShowFn = ()=>{
      state.bpzlShow = true
    }
    const submit = async () => {
      console.log("%c Line:847 🍆 ruleFormRef", "color:#2eafb0", ruleFormRef);
      //console.log("%c Line:666 🥛 formData", "color:#e41a6a", state.formData);
      // return
      if (!ruleFormRef) return
      
      // 表单校验 
      await ruleFormRef.value.validate((valid, fields) => {
        if (valid) {
              console.log('submit!')
              if (state.formData.smImg == '') {
            ElMessageBox('请上传游戏实名截图')
            return
          }
          if (state.fileList2.length) {
            state.formData.urlArr = JSON.parse(JSON.stringify(state.fileList2))
          } else {
            ElMessageBox('请上传游戏截图')
            return
          }
          if(!/^[^\u4e00-\u9fa5]*$/.test(state.formData.account)){
            ElMessageBox('游戏账号不正确')
            return
          }
          let tip = ''
          let tit = ''
          tip = `<p>1、竞价回收：用户发布商品竞价后，平台入驻商家会以竞拍的方式进行在线报价，接受报价之后进行交易。</p>
              <p>2、官方代售：用户发布商品代售后，平台客服在线审核商品通过后在商品列表展示，等待买家付款后交易。</p>
                `
          tit = '出售说明'
          ElMessageBox.confirm(
           tip,
            tit,
              {
                dangerouslyUseHTMLString: true,
                confirmButtonText: '确认',
                cancelButtonText: '取消'
              }
            )
              .then(() => {
                submitSure()
              })
              .catch(() => {
              
              })
          
        } else {
          console.log('error submit!', fields)
        }
      })
    }
    const submitSure = async () => {
        // 填写完成后提交到客服后台
				// 组装商品表所需数据 goodsInfoAndAttrValueDto
				let goodsInfoAndAttrValueDto = state.formData
				if (state.fileList2.length) {
					// 提取首图 this.fileList1
					goodsInfoAndAttrValueDto.titleImg = state.fileList2[0].url
				}
				goodsInfoAndAttrValueDto.accountNumber = goodsInfoAndAttrValueDto.account

				let obj = {
					gameId: state.currentGame.id,
					type: state.currentGame.name,
					des: state.formData.des,
					region: state.currentGame.type==1?`${state.formData.groupName}/${state.formData.serverName}`:`${state.formData.systemName ? state.formData.systemName : ''}${state.formData.carrierName ? state.formData.carrierName : ''}/${state.formData.groupName}/${state.formData.serverName}`,
					account: state.formData.account,
					tel: state.userInfo.phonenumber,
					img: JSON.stringify(state.fileList2),
					content: '',
          relnameImg:'',
          inType: state.formData.inType
				}
				// 找出实名状态
				obj.relname = state.shimingLabel
				// 拼接游戏实名图片
				if (state.fileList1.length) {
					obj.relnameImg = state.fileList1[0].url
          goodsInfoAndAttrValueDto.shimingImg = obj.relnameImg
				}
        // 配置重要信息备注
				goodsInfoAndAttrValueDto.importantRemark =
					`联系QQ：${state.formData.qq};联系微信：${state.formData.weixin};联系电话：${state.formData.tel}`
        obj.content = JSON.stringify(goodsInfoAndAttrValueDto)
        state.loading = true
        huishouApi.addGoodsBack(obj)
          .then(res => {
            state.loading = false
            //console.log("%c Line:626 🍯 res", "color:#4fff4B", res);
            if (res.code == 200) {
              if (state.formData.inType == 2) {
                ElMessageBox('提交成功，可以在个人中心查询')
                router.push({
                  path: '/index/other/Mybrowsing'
                })
              }
              // else{
              //   ElMessageBox('提交成功，请等待回收商进行估价,同时代售账号可以在个人中心查询')
              //   router.push({
              //     path: '/index/other/myJingjiahuishou'
              //   })
              // }
              
            }
          })
          .catch(e => {
            //console.log("%c Line:633 🍯 e", "color:#4fff4B", e);
          })
    }





    return {
      ...toRefs(state),
      areaClick,
      goLink, // 跳转
      goback,//返回
      goback2,
      constgetAttrList,
      nextStep,   // 下一步
      getSysList, // 获取系统列表
      getCarrierList, // 获取运营商列表
      getAreaList, // 获取大区列表
      getServerList, // 获取服务器列表
      setOffset,
      boxradioCheckClick,
      boxradioChange,
      radioClick,
      checkboxChange,
      submit,
      uploadImg,
      handlePictureCardPreview,
      handleRemove,
      uploadImg2,
      handleRemove2,
      makeDes,
      bpzlShowFn,
      priceChange,
      ruleFormRef
      
    }
  }
};

</script>

<style lang="less" scoped>
.demo-ruleForm {
  background-color: #fff;
  padding: 20px;
  // border-radius: 20px;
}



.BasicInformation {
  width: 1200px;
  margin: 0 auto;


  .title {
    width: 1200px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    margin-top: 20px;

    span {
      width: 8px;
      height: 18px;
      background-color: #FF8E00;
      margin-right: 10px;
      margin-left: 0;
      border-radius: 0;
    }

    h5 {
      font-size: 17px;
      color: #000;
      font-weight: 700;
      display: block;
      position: relative;
    }

    img {
      width: 50px;
      height: 50px;
      display: block;
      border-radius: 5px;
      margin-left: 10px;
      margin-right: 10px;
    }

    p {
      font-size: 14px;
      color: #000;
      font-weight: 400;
    }

    button {
      width: 65px;
      height: 26px;
      background-color: #FF8E00;
      border-radius: 50px;
      line-height: 26px;
      text-align: center;
      margin-left: 20px;
      color: #fff;
      font-size: 14px;
      cursor: pointer;
      border: none;
    }
  }

  .content {
    width: 1200px;
    min-height: 200px;
    background-color: #fff;
    box-shadow: 0 0 8px #ddd;
    margin: 0 auto;
    margin-top: 20px;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    padding: 30px;
    box-sizing: border-box;

    ul,
    li,
    ol,
    p {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      list-style: none;
    }

    .top {
      width: 100%;

      ul {
        width: 100%;
        display: flex;
        justify-content: flex-start;
        flex-wrap: wrap;

        >li {
          margin: 0 10px;
          display: flex;
          flex-direction: column;
          align-items: center;

          .h6 {
            font-size: 16px;
            color: #000;
            height: 65px;
            line-height: 65px;
            &.flex{
              align-items: center;
            }
          }

          ol {
            width: 190px;
            height: 292px;
            border: 1px solid #e4e4e4;
            // y轴滚动条
            overflow-y: auto;

            li {
              width: 100%;
              height: 40px;
              display: flex;
              align-items: center;
              justify-content: space-between;
              cursor: pointer;
              padding: 0 10px;

              .left {
                font-size: 14px;
                color: #000;
                margin-left: 10px;
              }

              .right {
                img {
                  width: 16px;
                  height: 16px;
                  margin-right: 10px;
                  display: none;
                }
              }
            }

            .active {
              background-color: #fef0f0;

              .left {
                color: #FF8E00;
              }

              .right {
                img {
                  display: block;
                }
              }
            }
          }
        }

        li:last-child {
          ol {
            li {
              display: block;
              padding: 15px 0;
              height: 77px;
              border-bottom: 1px solid #e4e4e4;

              .titl {
                display: flex;
                align-items: center;
                width: 150px;
                margin: 0 auto;
                margin-bottom: 5px;

        //         span {
        //           display: inline-block;
        //           width: 8px;
        //           height: 8px;
        //           background-color: #FF8E00;
        //           margin-right: 5px;
        //         }

                p {
                  font-size: 12px;
                  color: #000;
                }

                img {
                  width: 16px;
                  height: 16px;
                  display: block;
                  margin-left: 15px;
                }
              }

              input {
                width: 150px;
                margin: 0 10px;
                background-color: #f7f7f7;
                font-size: 12px;
                color: #000;
                height: 30px;
                padding-left: 10px;
                border: none;
                outline: none;
              }
            }

            li:nth-of-type(4) {
              height: 40px;
              border: none;
            }
          }
        }
      }
    }

    .bottom {
      width: 1045px;
      margin-top: 20px;
      display: flex;
      align-items: center;

      span {
        font-size: 14px;
        color: #FF8E00;
        margin-right: 5px;
      }
    }
  }


}

.button {
  width: 300px;
  height: 50px;
  background-color: #FF8E00;
  border-radius: 50px;
  margin: 0 450px;
  margin-top: 50px;
  font-size: 16px;
  color: #fff;
  line-height: 50px;
  text-align: center;
  cursor: pointer;
  border: none;
}

.sub-box {
  text-align: center;
}

.sub-btn {
  width: 300px;


}

.el-button {
  background-color: #FF8E00;
}

.fillInformation {
  width: 1200px;
  margin: 0 auto;

  .title {
    width: 1200px;
    height: 45px;
    background-color: #565656;
    background-image: linear-gradient(90deg, #565656, #b6b6b6);
    margin: 0 auto;
    display: flex;
    align-items: center;
    border-radius: 4px;

    img {
      width: 20px;
      height: 20px;
      display: block;
      margin-left: 10px;
      margin-right: 5px;
    }

    span {
      font-size: 16px;
      color: #fff;
    }
  }

  .Selected {
    width: 1180px;
    margin: 0 auto;
    height: 60px;
    display: flex;
    align-items: center;
    padding-left: 15px;

    button {
      width: 100px;
      height: 30px;
      background-color: #FF8E00;
      margin-left: 20px;
      border-radius: 5px;
      line-height: 30px;
      text-align: center;
      font-size: 14px;
      color: #fff;
      cursor: pointer;
      border: none;
    }
  }

}

:deep(.el-form-item__label) {
  font-size: 14px;
  font-weight: bold;
}

:deep(.el-checkbox) {
  margin-right: 15px;
  height: 30px;
}

.boxradioRadioBox {
  background: #fff;
  box-shadow: 0 0 7px #e3e3e3;
  padding: 10px;
  position: fixed;
  z-index: 99;
  display: none;
  border-radius: 8px;

}

.boxradioRadioBox.show {
  display: block;
}

.el-radio {
  display: block;
  height: 26px;
}
.bpzl-box{
  height: calc(100vh - 300px);
  overflow-y: auto;
  
}
:deep(img){
    max-width: 100%;
  }
</style>