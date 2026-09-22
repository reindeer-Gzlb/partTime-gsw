import Vue from "vue";

import Cookies from "js-cookie";

import Element from "element-ui";
Element.Dialog.props.closeOnClickModal.default = false
import "./assets/styles/element-variables.scss";

import "@/assets/styles/index.scss"; // global css
import "@/assets/styles/ruoyi.scss"; // ruoyi css
import App from "./App";
import store from "./store";
import router from "./router";
import directive from "./directive"; //directive
import plugins from "./plugins"; // plugins

import "./assets/icons"; // icon
import "./permission"; // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import {
  parseTime,
  resetForm,
  addDateRange,
  selectDictLabel,
  selectDictLabels,
  handleTree,
} from "@/utils/ruoyi";
import commonMaps from "@/utils/commonMaps";
import VueClipboard from "vue-clipboard2";
import { getToken } from '@/utils/auth'

import dataV from '@jiaminghi/data-view'

Vue.use(dataV)

Vue.use(VueClipboard);

// 分页组件
import Pagination from "@/components/Pagination";
// 自定义表格工具组件
import RightToolbar from "@/components/RightToolbar";
import ZdyRightToolbar from "@/components/ZdyRightToolbar";

// 富文本组件
import Editor from "@/components/Editor";
// 文件上传组件
import FileUpload from "@/components/FileUpload";
// 图片上传组件
import ImageUpload from "@/components/ImageUpload";
// 字典标签组件
import DictTag from "@/components/DictTag";
// 头部标签组件
import VueMeta from "vue-meta";
// 字典数据组件
import DictData from "@/components/DictData";
import $ from "jquery";
import 'default-passive-events'

window.jQuery = $;
window.$ = $;

Vue.prototype.commonMaps = commonMaps;
// 全局方法挂载
Vue.prototype.getDicts = getDicts;
Vue.prototype.getConfigKey = getConfigKey;
Vue.prototype.parseTime = parseTime;
Vue.prototype.resetForm = resetForm;
Vue.prototype.addDateRange = addDateRange;
Vue.prototype.selectDictLabel = selectDictLabel;
Vue.prototype.selectDictLabels = selectDictLabels;
Vue.prototype.handleTree = handleTree;
//即时通讯
Vue.prototype.openIM = (hxname, chatType = 'singleChat') => {
  const token = 'Bearer ' + getToken()
  // //console.log("%c Line:67 🍡 联系客服链接", "color:#7f2b82", `${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}&goodsInfo=${encodeURIComponent(state.goodsInfo)}`);

  // window.open(`http://192.168.3.101:9001/chat/conversation?hxuser=${hxname}&str=${token}&chatType=${chatType}`)
  // window.open(`https://im.feihengdaishou.com/chat/conversation?hxuser=${hxname}&str=${token}&chatType=${chatType}`)
  window.open(`https://im.feihengdaishou.com/chat/conversation?hxuser=${hxname}&str=${token}&chatType=${chatType}`)
}

// 全局组件挂载
Vue.component("DictTag", DictTag);
Vue.component("Pagination", Pagination);
Vue.component("RightToolbar", RightToolbar);
Vue.component("ZdyRightToolbar", ZdyRightToolbar);
Vue.component("Editor", Editor);
Vue.component("FileUpload", FileUpload);
Vue.component("ImageUpload", ImageUpload);

Vue.use(directive);
Vue.use(plugins);
Vue.use(VueMeta);
DictData.install();

// 引用图片预览插件
import 'viewerjs/dist/viewer.css'
import VueViewer from 'v-viewer'
Vue.use(VueViewer, {
  defaultOptions: {
    zIndex: 9999
  }
})
// 全局变量
// Vue.prototype.h5Url = 'http://ikbh.top/show.html?ZHID=';
/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get("size") || "medium", // set element-ui default size
});

Vue.config.productionTip = false;

new Vue({
  el: "#app",
  router,
  store,
  render: (h) => h(App),
});
