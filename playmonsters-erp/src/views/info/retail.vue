<template>
  <div class="app-container jklis">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
      class="zdy-form"
    >
      <el-form-item label="编号" prop="width=150" class="mm-b-8">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏类型" prop="type" class="mm-b-8">
        <el-select
          @change="selectGameType"
          v-model="queryParams.type"
          placeholder="请选择游戏类型"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in dict.type.game_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="区服" prop="regional" class="mm-b-8">
        <el-select
          @change="selectGameDaQu"
          multiple
          v-model="queryParams.regionalAddr"
          placeholder="请选择区服"
          size="small"
        >
          <el-option
            v-for="dict in regionalArr"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="实名认证" prop="level" class="mm-b-8">
        <el-select
          @change="selectLevel"
          multiple
          v-model="queryParams.levelAddr"
          placeholder="请选择实名认证"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in levelArr"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售状态" prop="type">
        <el-select v-model="queryParams.saleState" placeholder="请选择销售状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.account_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="价格区间" prop="price" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.priceStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.priceEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>

      <el-form-item v-if="show.select.heroes" label="英雄数量" prop="heroes" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.heroStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.heroEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>

      <el-form-item v-if="show.select.skins" label="皮肤数量" prop="skins" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.skinStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.skinEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.suit" label="套装数量" prop="suit" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.suitStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.suitEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.guns" label="枪皮" prop="guns" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.gunsStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.gunsEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <!--      <el-form-item-->
      <!--        label="提取账号"-->
      <!--        prop="extractAccount"-->
      <!--      >-->
      <!--        <el-input-->
      <!--          v-model="queryParams.extractAccount"-->
      <!--          placeholder="请输入提取账号"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="自定义" prop="accountId" class="mm-b-8">
        <el-input
          v-model="queryParams.des"
          placeholder="请输入自定义关键词"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="简介" prop="accountId" class="mm-b-8">
        <el-input
          v-model="queryParams.desQur"
          placeholder="请输入简介"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          class="zdy-btn"
        >搜索</el-button>
        <el-button
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
          class="zdy-btn zdy-btn-refresh"
        >重置</el-button>
      </el-form-item>
    </el-form>
    <div class="zdy-main">
      <div class="zdy-table-header">
        <div class="zdy-table-header-search">
          <el-input placeholder="请输入搜索内容" prefix-icon="el-icon-search" size="small"></el-input>
        </div>
        <div class="zdy-table-header-btn">
          <zdy-right-toolbar :isSearch="true" :showSearch.sync="showSearch" @queryTable="getList"></zdy-right-toolbar>
          <div class="zdy-header-btn-group">
            <el-button
              v-clipboard:copy="copyVlaue"
              type="danger"
              plain
              icon="el-icon-success"
              size="mini"
              v-clipboard:error="onError"
              v-clipboard:success="onCopy"
              class="zdy-plfz-btn"
              :disabled="multiple"
            >批量复制</el-button>
            <el-button
              v-clipboard:copy="mylink"
              type="danger"
              plain
              icon="el-icon-success"
              size="mini"
              v-clipboard:error="onError"
              v-clipboard:success="onCopy"
            >我的链接</el-button>
            <el-button size="mini" @click="openQRCode" class="zdy-btn zdy-btn-refresh">链接二维码</el-button>
            <!-- <el-button type="primary" @click="addPrice" plain icon="el-icon-success" size="mini">加价</el-button> -->
          </div>
        </div>
      </div>
      <el-table
        v-loading="loading"
        ref="singleTable"
        highlight-current-row
        :cell-style="cellStyle"
        :height="table.tableHeight"
        :data="infoList"
        fit
        :header-cell-style="{
          textAlign: 'center',
          height: '20px',
        }"
        :row-class-name="tableRowClassName"
        @sort-change="sortFun"
        @cell-dblclick="showImage"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="right">
              <el-form-item :style="{ marginRight: wihh + '%' }" label-width="110px">
                <!-- <span>{{ props.row.des }}</span> -->
                <template slot-scope="scope">
                  <!--  <el-tooltip :content="props.row.des" placement="top">
              <span   class="showOverTooltip">{{props.row.des}}</span>
                  </el-tooltip>-->
                  <span class="showOverTooltip">{{ props.row.des }}</span>
                </template>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="序号"
          sortable="custom"
          width="80"
          label-position="left"
          sort-by
          align="center"
          prop="id"
        />
        <el-table-column label="游戏类型" width="80" align="center" prop="type" />
        <el-table-column label="区服" width="100" align="center" prop="regional"></el-table-column>
        <!-- <el-table-column
          v-if="show.gameName"
          label="自编号"
          align="center"
          width="80"
          prop="gameName"
        />-->
        <el-table-column label="实名认证" align="center" width="100" prop="level" />
        <el-table-column
          sortable="custom"
          sort-by
          v-if="show.heroes"
          label="英雄"
          width="80"
          align="center"
          prop="heroes"
        />
        <el-table-column
          sortable="custom"
          sort-by
          v-if="show.skins"
          label="皮肤"
          width="80"
          align="center"
          prop="skins"
        />
        <el-table-column
          sortable="custom"
          sort-by
          v-if="show.suit"
          label="套装"
          align="center"
          width="80"
          prop="suit"
        />
        <el-table-column
          sortable="custom"
          sort-by
          v-if="show.guns"
          label="枪皮"
          align="center"
          width="80"
          prop="guns"
        />
        <el-table-column
          sortable="custom"
          sort-by
          label="新价格"
          width="80"
          align="center"
          prop="newPrice"
        />
        <el-table-column label="销售状态" width="80" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.saleStateName == '自售账号'" style="color: #ff6d6d">已下架</span>
            <span v-if="scope.row.saleStateName == '未出售'">{{scope.row.saleStateName}}</span>
          </template>
        </el-table-column>
        <el-table-column label="简介" align="center" min-width="600" prop="des">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <!-- <el-table-column
          sortable="custom"
          sort-by
          label="供货客服"
          width="160"
          align="center"
          prop="supplyName"
        />-->
        <el-table-column
          sortable="custom"
          sort-by
          label="供货价"
          width="160"
          align="center"
          prop="price"
        />
        <el-table-column
          sortable="custom"
          sort-by
          label="导入时间"
          width="160"
          align="center"
          prop="createTime"
        />
        <!-- <el-table-column label="销售状态" align="center" prop="saleStateName" /> -->
        <el-table-column
          sortable="custom"
          sort-by
          label="销售时间"
          width="160"
          align="center"
          min-width="100%"
          prop="saleTime"
        />
        <!-- <el-table-column
          sortable="custom"
          sort-by
          label="出售价格"
          width="100"
          align="center"
          prop="price"
        />-->
        <el-table-column
          sortable="custom"
          sort-by
          label="上浮"
          width="100"
          align="center"
          prop="rise"
        />
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="submitNum" type="text" size="small">提号</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="total > 0"
      :total="total"
      :page-sizes="[10, 20, 30, 40, 50, 60]"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="showDialogTitle" :visible.sync="dialogFormVisible" width="20%">
      <el-form>
        <el-form-item label="上浮百分比" :label-width="formLabelWidth">
          <el-input-number v-model="rise" autocomplete="off"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmSub()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="提示" :visible.sync="dialogImgVisible" width="30%" :before-close="handleClose">
      <div style="text-align:center;margin-bottom: 20px">
        <el-button type="primary" @click="coptImg">合成下载</el-button>
      </div>
      <el-image v-for="(item,index) in imgList" :key="index" :src="item.imgUrl"></el-image>
    </el-dialog>
    <el-dialog title="提号" :visible.sync="submitShow" width="20%">
      <span>微信：XZbishr666666</span>
      <br />
      <span>QQ：415593618</span>
      <br />
      <span>手机号：15225247351</span>
      <br />
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitShow = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="我的分销二维码" :visible.sync="qrCodeShow" width="340px">
      <div ref="qrcode" style="width:400px; height:300px;"></div>
    </el-dialog>
  </div>
</template>
<style>
#qrCode {
  display: inline-block;
  margin: 0 auto; /*水平居中*/
  position: relative;
  top: 15%;

  /* img: {
    width: 200px;
    height: 200px;
    background-color: #fff; //设置白色背景色
    padding: 6px; // 利用padding的特性，挤出白边
  } */
}
</style>
<style>
.el-table .warning-row {
  background: #eeeeee;
  height: 30px;
}

.el-table .success-row {
  height: 30px;
  /* background: #f0f9eb; */
}
.el-table .showOverTooltip {
  display: -webkit-box;
  text-overflow: ellipsis;
  line-height: 20px;
  overflow: hidden;
  /*这里是3行*/
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  margin-right: 20px;
}
</style>
<script>
import QRCode from "qrcodejs2";
import {
  getInfo,
  auditInfo,
  addInfo,
  updateInfo,
  exportInfo,
  getSupplyList,
  getTuisongList,
  importTemplateHp,
  getCmdSupplyList,
  getImage
} from "@/api/info/info";
import { listAccount, delAccount, bacthAddPrice } from "@/api/info/account";
import { getDicts } from "@/api/system/dict/data";
import { getToken } from "@/utils/auth";
import BacthAddPrice from "../components/BacthAddPrice.vue";
import moment from "moment";
import PackageUpload from "@/components/ImageUpload/packageUpload";
import AllSkinSelect from "@/views/components/AllSkinSelect";
import { getUserProfile } from "@/api/system/user";
import { updateUserProfile } from "@/api/system/user";
export default {
  name: "Info",
  dicts: ["game_type", "account_status"],
  components: { BacthAddPrice },
  data() {
    return {
      Code_url: "",
      qrcode: null,
      qrCodeShow: false,
      imgList: [],
      dialogImgVisible: false,
      user: {},
      submitShow: false,
      tuisongList: [],
      addOrsubPriceVisible: false,
      dialogFormVisibleJian: false,
      wihh: 10,
      uploadImgUrl: "",
      dialogImageUrl: "",
      dialogVisible: false,
      dialogGameType: false,
      dialogGameTypeHp: false,
      intType: null,
      operation: null,
      showDialogTitle: "",
      formLabelWidth: "120px",
      cellStyle: "color: #000000;font-size: 15px",
      dialogFormVisible: false,
      dialogTelFormVisible: false,
      //游戏大区
      regionalArr: [],
      levelArr: [],
      supplyList: [],
      saleList: [],
      // 遮罩层
      loading: false,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 游戏账号表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      saleTimeArr: [],
      createTimeArr: [],
      updateTimeArr: [],
      videoList: [],
      retailCode: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        accountId: null,
        type: null,
        regional: null,
        timeout: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        type: null,
        priceStart: null,
        priceEnd: null,
        saleTimeStart: null,
        saleTimeEnd: null,
        createTimeStart: null,
        createTimeEnd: null,
        updateTimeStart: null,
        updateTimeEnd: null,
        heroStart: null,
        heroEnd: null,
        skinStart: null,
        skinEnd: null,
        suitStart: null,
        suitEnd: null,
        gunsStart: null,
        gunsEnd: null,
        regionalAddr: [],
        levelAddr: [],
        supplyIdAddr: [],
        salesIdAddr: []
      },
      show: {
        exportExelShow: false,
        gameName: true,
        type: true,
        regional: true,
        level: true,
        extractTel: false,
        heroes: true,
        skins: true,
        suit: true,
        guns: true,
        des: false,
        price: false,
        supplyName: false,
        salesName: false,
        buyId: false,
        realPrice: false,
        remake: false,
        saleStateName: false,
        updateName: false,
        extractAccount: false,
        extractPwd: false,
        costPrice: false,
        sinNum: false,
        auditName: false,
        createTime: false,
        updateTime: false,
        note: false,
        saleTime: false,
        auditState: false,
        select: {
          suit: true,
          guns: true,
          heroes: true,
          skins: true
        },
        intParam: {
          extractAccount: false,
          saleTime: false,
          supplyName: true,
          salesName: false,
          sinNum: false,
          timeout: false,
          createTime: false,
          updateTime: false,
          salesStatus: false
        }
      },
      ifSpecial: true,
      // 表单参数
      form: {},
      unsoldFrom: {
        note: null
      },
      telFrom: {
        buyId: null,
        realPrice: null
      },
      // 表单校验
      rules: {},
      type: "",
      saleStatus: "",
      showAudit: false,
      extractPwd: false,
      sinceSale: false,
      extractTel: false,
      cancelIn: false,
      transfer: false,
      back: false,
      problem: false,
      edit: false,
      pending: false,
      handle: true,
      copyOpe: false,
      urlArr: [],
      fileNameArr: [],
      imgFileList: [],
      imgShowList: [], //传给后台的图片
      table: {
        tableData: [],
        tableHeight: $(window).height() - 350
      },
      show: {
        heroes: true,
        skins: true,
        suit: true,
        guns: true,
        select: {
          suit: true,
          guns: true,
          heroes: true,
          skins: true
        }
      },
      copyVlaue: "",
      mylink: "",
      copyPwd: "",
      copyTel: "",
      ifClick: true,
      hoste: "",
      rise: 0
    };
  },
  created() {
    this.getTuisongList();
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    const saleStatus = this.$route.query.saleStatus;
    this.type = this.$route.query.type;
    this.queryParams.saleState = saleStatus;
    this.queryParams.operType = this.type;
    if ("audit" == this.type) {
      this.showAudit = true;
      this.show.saleStateName = false;
      this.show.intParam.createTime = true;
    } else if ("admin" == this.type) {
      this.show.exportExelShow = true;
      this.show.intParam.salesName = true;
      this.show.intParam.supplyName = true;
      this.show.intParam.updateTime = true;
      this.show.intParam.createTime = true;
      this.show.intParam.salesStatus = true;
    } else if ("sale" == this.type) {
      this.handle = false;
      if ("1" == saleStatus) {
        this.extractPwd = true;
        this.copyOpe = true;
        this.show.exportExelShow = true;
      } else if ("2" == saleStatus) {
        this.extractTel = true;
        this.cancelIn = true;
        this.problem = true;
      } else if ("3" == saleStatus) {
        this.show.intParam.extractAccount = true;
        this.show.intParam.saleTime = true;
      }
    } else if ("supply" == this.type) {
      if ("1" == saleStatus) {
        this.show.exportExelShow = true;
        this.sinceSale = true;
        this.show.intParam.supplyName = false;
      } else if ("2" == saleStatus) {
        this.handle = false;
        this.show.intParam.supplyName = false;
        this.show.intParam.salesName = true;
      } else if ("3" == saleStatus) {
        this.problem = true;
        this.show.intParam.saleTime = true;
        this.show.intParam.extractAccount = true;
        this.show.intParam.salesName = true;
        this.show.intParam.supplyName = false;
        this.show.intParam.sinNum = true;
      } else if ("4" == saleStatus) {
        this.back = true;
        this.show.intParam.supplyName = false;
        this.show.intParam.salesName = true;
        this.pending = true;
      } else if ("6" == saleStatus) {
        this.show.intParam.timeout = true;
        this.queryParams.timeout = 30;
        this.queryParams.saleState = 1;
      } else if ("5" == saleStatus) {
        this.pending = true;
        this.handle = false;
        this.show.intParam.supplyName = false;
      } else if ("7" == saleStatus) {
        this.transfer = true;
        this.show.intParam.supplyName = false;
        this.handle = false;
      }
    }
    this.getList();
    this.getSaleList();
    this.getSupplyList();
    this.getHost();
    this.getUser();
    // this.getCmdSupplyList();
  },
  mounted: function() {
    this.$nextTick(function() {
      this.bindQRCode();
    });
  },
  methods: {
    coptImg() {
      let imgArr = [].concat(...this.imgList.map(o => o.imgUrl));
      this.instancesUrl = imgArr;
      this.filesToInstances();
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
        });
      });

      // https://game.ikbh.top/logo/kuang.jpeg
    },
    drawImages(images) {
      const heights = images.map(item => item.height);
      const canvas = document.createElement("canvas");
      const widths = images.map(item => item.width);
      let sumWidth = Math.max.apply(null, widths);
      // canvas.width = sumWidth;
      canvas.width = 1500;
      // canvas.height = eval(heights.join("+"));
      // canvas.height = eval(heights.join("+"));
      canvas.height = heights.length * 1200;
      const context = canvas.getContext("2d");

      let y = 0;

      images.forEach((item, index) => {
        // const height = heights[index];
        // const width = widths[index];
        const height = 1200;
        const width = 1500;
        context.drawImage(item, 0, y, width, height);
        y += height;
      });
      const base64Url = canvas.toDataURL("image/jpeg", 0.2);
      this.downloadFile("11", base64Url);
    },
    getURLBase64(url) {
      return new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest();
        xhr.open("get", url, true);
        xhr.responseType = "blob";
        xhr.onload = function() {
          if (this.status === 200) {
            var blob = this.response;
            var fileReader = new FileReader();
            fileReader.onloadend = function(e) {
              var result = e.target.result;
              resolve(result);
            };
            fileReader.readAsDataURL(blob);
          }
        };
        xhr.onerror = function() {
          reject();
        };
        xhr.send();
      });
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
          view: window
        })
      ); // 兼容火狐
    },
    openQRCode() {
      this.qrCodeShow = true;
      this.getUser();
      this.Code_url = this.mylink;
      this.$nextTick(() => {
        console.log("显示二维码的元素(div)=" + this.$refs.qrcode);
        if (this.qrcode != null) {
          this.qrcode.clear(); // 清除代码
        } else {
          this.qrcode = new QRCode(this.$refs.qrcode, {
            width: 300,
            height: 300,
            correctLevel: QRCode.CorrectLevel.L
          });

          // console.log(qrcode);
          //生成二维码
          this.qrcode.makeCode(this.Code_url);
        }
      });
    },
    submitNum() {
      this.submitShow = true;
    },
    addPrice() {
      getUserProfile().then(response => {
        this.user = response.data;
        console.log(this.user);
        this.rise = this.user.rise;
      });
      this.dialogFormVisible = true;
    },
    getUser() {
      getUserProfile().then(response => {
        this.user = response.data;
        console.log(this.user);
        var hoste = this.hoste.replace("xh.", "");
        var hoste = hoste.replace("www.", "");
        hoste = "http://rt.ikbh.top/" + this.user.userId;
        this.mylink = hoste;
      });
    },
    getTuisongList() {
      getSupplyList("supplyAirplane").then(res => {
        if (res.code == 200) {
          this.tuisongList = res.data;
        }
      });
    },
    getCmdSupplyList() {
      getCmdSupplyList().then(res => {
        console.log(res);
        this.cmdSupplyList = res;
      });
    },
    addOrsubPrice() {
      this.addOrsubPriceVisible = true;
    },
    addFrom(from) {
      this.addPriceparam = from;
    },
    confirmAddSub() {
      if (!this.addPriceparam.rise) {
        this.addPriceparam.rise = 0;
      }
      console.log(this.addPriceparam);
      const param = this.addPriceparam;

      // let sap
      // if(this.addPriceparam.supplyIdAddr != null a){
      //   sap = this.addPriceparam.supplyIdAddr.join(",");
      // }
      // param.supplyIdAddr = sap;
      param.saleType = "retail";
      // param.saleType = "sale";

      if (this.addPriceparam.cmdSupplyId) {
        param.cmdSupplyId = this.addPriceparam.cmdSupplyId;
      }
      console.log(param);
      this.loading = true;

      bacthAddPrice(param).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("修改成功");
          this.loading = false;
        } else {
          this.$modal.msgError("修改失败");
          this.loading = false;
        }
        this.addOrsubPriceVisible = false;
        this.getList();
      });
    },
    onOfOff() {
      this.getList();
    },
    changeSpecial() {
      console.log(this.form.accType);
      if (1 == this.form.accType) {
        this.ifSpecial = false;
      } else {
        this.form.specialPrice = null;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        // return "";
        return "success-row";
      } else {
        return "warning-row";
      }
    },
    getHost() {
      getDicts("apphost").then(res => {
        if (res.code == 200) {
          console.log(res);
          if (res.data.length > 0) {
            this.hoste = res.data[0].dictValue;
          }
        }
      });
    },
    // 在methods里面书写复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
    },
    onError() {
      this.$modal.msgError("复制失败");
    },
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$$modal.msgError("最多只能上传20张图片");
    },
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;

      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function(element, key) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //传给后台的图片
        }
      });
      console.log(this.imgFileList);
    },
    handleVideoSuccess(res, file) {
      if (res.code == 200) {
        console.log(res);
        this.form.videoUrl = res.data.filePath;
        this.form.videoName = res.data.fileName;
      }
    },
    handleVideoRemove(response, fileList, file) {
      this.form.videoUrl = "";
      this.form.videoName = "";
    },
    handleUploadSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.imgFileList.push(param);
        console.log(this.imgFileList);
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 转出金额校验
    checkPriceOut() {
      let checkPlan = "" + this.telFrom.realPrice;
      checkPlan = checkPlan
        .replace(/[^\d.]/g, "") // 清除“数字”和“.”以外的字符
        .replace(/\.{2,}/g, ".") // 只保留第一个. 清除多余的
        .replace(/^\./g, "") // 保证第一个为数字而不是.
        .replace(".", "$#$")
        .replace(/\./g, "")
        .replace("$#$", ".");
      if (checkPlan.indexOf(".") < 0 && checkPlan !== "") {
        // 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        checkPlan = parseFloat(checkPlan) + "";
      } else if (checkPlan.indexOf(".") >= 0) {
        checkPlan = checkPlan.replace(/^()*(\d+)\.(\d\d).*$/, "$1$2.$3"); // 只能输入两个小数
      }
      this.telFrom.realPrice = checkPlan;
    },
    selectSaleTime() {
      if (this.saleTimeArr.length > 1) {
        this.queryParams.saleTimeStart = this.saleTimeArr[0];
        this.queryParams.saleTimeEnd = this.saleTimeArr[1];
      }
    },
    selectupdateTime() {
      if (this.updateTimeArr.length > 1) {
        var startDate = moment(this.updateTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.updateTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.updateTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.updateTimeEnd = endDate + " 23:59:59";
      }
    },
    selectCreateTime() {
      if (this.createTimeArr.length > 1) {
        var startDate = moment(this.createTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.createTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.createTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.createTimeEnd = endDate + " 23:59:59";
      }
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    getSaleList() {
      getSupplyList("salesCustomer").then(res => {
        if (res.code == 200) {
          this.saleList = res.data;
        }
      });
    },
    selectDialogGameType() {
      let gameType = this.form.type;
      console.log(gameType);
      this.form.regional = null;
      getDicts(gameType + "段位").then(res => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
      } else {
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          console.log(res);
          this.regionalArr = res.data;
        }
      });
    },
    selectGameType() {
      let gameType = this.queryParams.type;
      console.log(gameType);
      getDicts(gameType + "段位").then(res => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
        this.show.heroes = false;
        this.show.skins = false;
        this.show.suit = true;
        this.show.guns = true;
        this.show.select.heroes = false;
        this.show.select.skins = false;
        this.show.select.guns = true;
        this.show.select.suit = true;
      } else {
        this.show.select.heroes = true;
        this.show.select.skins = true;
        this.show.select.guns = false;
        this.show.select.suit = false;
        this.show.heroes = true;
        this.show.skins = true;
        this.show.suit = false;
        this.show.guns = false;
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
      this.getList();
    },
    /** 查询游戏账号列表 */
    getList() {
      console.log(this.queryParams);
      let priceStart = typeof Number(this.queryParams.priceStart);
      let priceEnd = typeof Number(this.queryParams.priceEnd);
      if (priceStart > priceEnd) {
        this.$modal.msgError("价格数据不对");
        return true;
      }
      let heroStart = this.queryParams.heroStart;
      let heroEnd = this.queryParams.heroEnd;
      if (typeof Number(heroStart) > typeof Number(heroEnd)) {
        this.$modal.msgError("英雄数量输入不对");
        return true;
      }

      let skinStart = this.queryParams.skinStart;
      let skinEnd = this.queryParams.skinEnd;
      if (typeof Number(skinStart) > typeof Number(skinEnd)) {
        this.$modal.msgError("皮肤数量输入不对");
        return true;
      }

      let suitStart = this.queryParams.suitStart;
      let suitEnd = this.queryParams.suitEnd;
      if (typeof Number(suitStart) > typeof Number(suitEnd)) {
        this.$modal.msgError("套装数量输入不对");
        return true;
      }

      let gunsStart = this.queryParams.gunsStart;
      let gunsEnd = this.queryParams.gunsEnd;
      if (typeof Number(gunsStart) > typeof Number(gunsEnd)) {
        this.$modal.msgError("枪皮数量输入不对");
        return true;
      }
      // if (this.queryParams.saleState == 6) {
      //   this.queryParams.timeout = 30;
      //   this.queryParams.saleState = 1;
      // }
      this.loading = true;
      this.queryParams.accountType = "2";
      this.queryParams.operType = "supply";
      listAccount(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
        if (response.rows) {
          this.rise = response.rows[0].rise;
        }
      });
    },
    selectLevel() {
      this.getList();
    },
    selectGameDaQu() {
      this.getList();
    },
    // 取消按钮
    cancel() {
      console.log(1);
      this.imgFileList = [];
      this.imgShowList = [];
      this.open = false;
      this.reset();
    },
    closeDialog() {
      this.imgFileList = [];
      this.imgShowList = [];
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        createTime: null,
        accountId: null,
        type: null,
        regional: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        updateTime: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.priceStart = null;
      this.queryParams.priceEnd = null;
      this.queryParams.heroStart = null;
      this.queryParams.heroEnd = null;
      this.queryParams.skinStart = null;
      this.queryParams.skinEnd = null;
      this.queryParams.suitStart = null;
      this.queryParams.suitEnd = null;
      this.queryParams.gunsStart = null;
      this.queryParams.gunsEnd = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.assembleCopy(selection);
      this.copyPwd =
        "编号：" +
        selection[0].id +
        "\n" +
        "  账号：" +
        selection[0].extractAccount +
        "\n" +
        "  密码：" +
        selection[0].extractPwd +
        "\n";

      this.copyTel =
        "   编号：" +
        selection[0].id +
        "\n" +
        "  账号：" +
        selection[0].extractAccount +
        "\n" +
        "  密码：" +
        selection[0].extractPwd +
        "\n" +
        "  手机号：" +
        selection[0].extractTel;
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    //拼装批量复制
    assembleCopy(selection) {
      console.log(selection);
      this.copyVlaue = "";
      selection.map(item => {
        let urlArr = "";
        if (item.urlArr) {
          item.urlArr.map(ite => {
            urlArr = urlArr + ite.url + "\n";
          });
        }
        let value = "";

        console.log(this.user.userId);

        var hoste = this.hoste.replace("xh.", "");
        var hoste = hoste.replace("www.", "");
        hoste = hoste.replace("//", "//rt.");
        hoste = hoste.replace("/show.html?ZHID=", "");
        hoste = hoste + "/show.html?id=" + this.user.userId;

        let hostt = hoste + "&ZHID=" + item.id;
        // let hostt = this.hoste + item.id;

        value =
          "编号：" +
          item.id +
          "\n" +
          "自编号：" +
          item.gameName +
          "\n" +
          "大区：" +
          item.regional +
          "\n" +
          "段位：" +
          item.level +
          "\n" +
          "英雄：" +
          item.heroes +
          "\n" +
          "皮肤：" +
          item.skins +
          "\n" +
          "售价：" +
          item.newPrice +
          "\n" +
          "简介：" +
          item.des +
          "\n" +
          "商品截图：" +
          hostt;
        ("\n");
        let geduan =
          "\n" +
          "------------------------------" +
          "\n" +
          "------------------------------------" +
          "\n";
        this.copyVlaue = this.copyVlaue + value + geduan;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加游戏账号";
    },
    /** 修改按钮操作 */

    showImage(row) {
      let params = {};
      params.id = row.id;
      debugger;
      getImage(params).then(res => {
        if (res.code == 200) {
          this.imgList = res.data;
          this.dialogImgVisible = true;
        }
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            const that = this;
            this.form.urlArr = this.imgFileList;
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    bacthAdd() {
      this.showDialogTitle = "批量加价";
      this.dialogFormVisible = true;
      console.log("进来了");
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
        .then(function() {
          return delAccount(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    handleAudit(type, operation) {
      this.exportLoading = false;
      let params = {};
      if (null != this.intType) {
        params.type = this.intType;
      } else {
        params.type = type;
      }
      if (null != this.operation) {
        params.operation = this.operation;
      } else {
        params.operation = operation;
      }
      if ("" != this.unsoldFrom.note || null != this.unsoldFrom.note) {
        params.note = this.unsoldFrom.note;
      }
      if ("" != this.telFrom.remake || null != this.telFrom.remake) {
        params.remake = this.telFrom.remake;
      }
      if ("" != this.telFrom.realPrice || null != this.telFrom.realPrice) {
        params.realPrice = this.telFrom.realPrice;
      }
      if ("" != this.telFrom.buyId || null != this.telFrom.buyId) {
        params.buyId = this.telFrom.buyId;
      }
      const ids = this.ids;
      if ("审核" != operation) {
        if (this.ids.length > 1) {
          this.$modal.msgError("只能选择一条数据");
          return true;
        }
      }
      this.$modal
        .confirm(
          "是否确认" + operation + '游戏账号编号为"' + ids + '"的数据项？'
        )
        .then(function() {
          return auditInfo(ids, params);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(operation + "成功");
          if (2 == type) {
            this.excCopy(this.copyPwd);
          } else if (3 == type) {
            this.excCopy(this.copyTel);
          }
        })
        .catch(() => {});
    },
    excCopy(data) {
      const url = data;
      const oInput = document.createElement("input");
      oInput.value = url;
      document.body.appendChild(oInput);
      oInput.select(); // 选择对象;
      console.log(oInput.value);
      document.execCommand("Copy"); // 执行浏览器复制命令
    },
    extractTelFun() {
      this.dialogTelFormVisible = true;
      this.showDialogTitle = "提取手机号";
      this.intType = 3;
      this.operation = "提取手机号";
    },
    confirmSub() {
      this.user.rise = this.rise;
      updateUserProfile(this.user).then(response => {
        this.$modal.msgSuccess("修改成功");
      });
    },
    transferProblem() {
      this.dialogFormVisible = true;
      this.showDialogTitle = "转为问题号";
      this.intType = 4;
      this.operation = "转为问题号";
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有游戏账号数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportInfo(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    }
  }
};
</script>
<style scoped>
.zdy-plfz-btn,
.zdy-plfz-btn:hover,
.zdy-plfz-btn:focus,
.zdy-plfz-btn:active {
  color: #fff !important;
  background: #8333de !important;
  border: 1px solid #8333de !important;
}

.zdy-tqmm-btn,
.zdy-tqmm-btn:hover,
.zdy-tqmm-btn:focus,
.zdy-tqmm-btn:active {
  color: #fff !important;
  background: #4b8aff !important;
  border: 1px solid #4b8aff !important;
}
.zdy-table-header-btn {
  display: flex;
  align-items: center;
}

.zdy-header-btn-group {
  float: right;
  padding: 0 10px 0 20px;
}

::v-deep .zdy-table-header .el-input__inner {
  border: none;
}

.zdy-table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 6px;
  box-sizing: border-box;
}

.zdy-main {
  margin-top: 10px;
  border: 1px solid #dfe6ec;
  box-sizing: border-box;
}

.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 6px 0;
}

::v-deep.pagination-container {
  padding: 0 !important;
  margin-bottom: 0;
  margin-top: 5px;
}

::v-deep.pagination-container .el-pagination {
  left: 0 !important;
}

::v-deep.el-table .el-table__header-wrapper th {
  background-color: #f8fafd;
  border-top: 1px solid #e5e5e5;
}
.zdy-btn-refresh {
  color: #fff;
  background-color: #94aab9 !important;
  border-color: #94aab9 !important;
}

.zdy-btn {
  padding: 7px 20px;
}
.el-date-editor--daterange.el-input__inner {
  width: 215px;
}
.mm-b-8 {
  margin-bottom: 8px !important;
}
.zdy-form {
  padding: 8px;
  background-color: #f4f7f9;
  border-radius: 6px;
  box-sizing: border-box;
}

.two-input-content {
  display: flex;
  align-items: center;
}

.two-input-item {
  width: 90px;
}

.two-input-content span {
  width: 15px;
  height: 1px;
  margin: 0 10px;
  background: #aaaaaa;
}
.showOverTooltip {
  display: -webkit-box;
  text-overflow: ellipsis;
  line-height: 20px;
  overflow: hidden;
  /*这里是3行*/
  -webkit-line-clamp: 6;
  -webkit-box-orient: vertical;
  margin-right: 20px;
}
/* .sssssssss {
  margin-right: 40%;
} */
.el-form-item {
  margin-bottom: 0px;
}
.el-form--label-right {
  border-bottom: 1px solid red;
}
/deep/.el-table td.el-table__cell {
  border-bottom: 1px dashed #b5b8bc;
}
.cell {
  margin-left: 0%;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 20%;
}

.el-table--medium .el-table__cell {
  padding: 0;
}
</style>
