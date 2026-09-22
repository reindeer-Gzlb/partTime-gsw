<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="游戏类型" prop="type">
        <el-select
          v-model="queryParams.type"
          @change="selectGameType"
          placeholder="请选择游戏类型"
          clearable size="small">
          <el-option
            v-for="dict in dict.type.game_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="游戏大区" prop="regional">
        <el-select
          @change="selectGameDaQu"
          multiple
          v-model="queryParams.regionalAddr"
          placeholder="请选择游戏大区"
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
      <el-form-item label="段位" prop="level">
        <el-select
          @change="selectLevel"
          multiple
          v-model="queryParams.levelAddr"
          placeholder="请选择段位"
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
      <el-form-item
        label="提取账号"
        prop="extractAccount"
      >
        <el-input
          v-model="queryParams.extractAccount"
          placeholder="请输入提取账号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="卖家手机号"
        prop="extractTel"
      >
        <el-input
          v-model="queryParams.extractTel"
          placeholder="请输入卖家手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <!--<el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleRepeatedUpdate"
          v-hasPermi="['account:details:edit']"
        >编辑</el-button>-->
        <el-button type="primary"
                   plain
                   icon="el-icon-success"
                   size="mini"
                   :disabled="single"
                   v-clipboard:copy="copyVlaue"
                   v-clipboard:error="onError"
                   v-clipboard:success="onCopy"
                   @click="keyCopy">一键复制</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      ref="singleTable"
      highlight-current-row
      :height="table.tableHeight"
      :data="infoList"
      :border="true"
      fit
      :header-cell-style="{
            textAlign: 'center',
            height: '20px',
        }"
      :cell-style="cellStyle"
      :row-class-name="tableRowClassName"
      @sort-change="sortFun"
      @cell-dblclick="handleRepeatedUpdate"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right">
            <el-form-item :style="{marginRight: wihh + '%' }"  label-width="110px" >
              <!-- <span>{{ props.row.des }}</span> -->
              <template slot-scope="scope">
                <!--  <el-tooltip :content="props.row.des" placement="top">
                <span   class="showOverTooltip">{{props.row.des}}</span>
                 </el-tooltip> -->
                <span  class="showOverTooltip">{{props.row.des}}</span>
              </template>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="竞价编号" align="center" prop="id" width="180"/>
      <el-table-column label="游戏类型" align="center" prop="type" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.game_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <!--<el-table-column label="游戏大区" align="center" prop="regional" />-->
      <el-table-column label="提取账号" align="center" prop="extractAccount" width="180"/>
      <el-table-column label="成本价格" align="center" prop="costPrice" width="180"/>
      <el-table-column label="提交人" align="center" prop="recoveryName" width="180"/>
      <el-table-column label="卖家手机号" align="center" prop="extractTel" width="180"/>
      <el-table-column label="审核人" align="center" prop="verifyName" width="180"/>
      <el-table-column label="审核状态" align="center" prop="verifyState" width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.audit_status" :value="scope.row.verifyState"/>
        </template>
      </el-table-column>
      <el-table-column label="审核备注" align="center" prop="auditNote" width="180"/>
      <el-table-column label="审核日期" align="center" prop="auditTime" width="180"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="auditTitle" :visible.sync="auditOpen" width="800px" append-to-body>
      <div style="height: 400px;overflow-y:auto" >
        <div>
          <el-form ref="auditForm" :model="auditForm" :rules="rules" label-width="80px">
            <el-row>
              <el-col span="12">
                <el-form-item label="竞价编号" prop="accountId">
                  <el-input v-model="auditForm.accountId" placeholder="请输入游戏账号账号表id" :readonly="true" />
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item label="姓名" prop="name">
                  <el-input v-model="auditForm.name" placeholder="请输入姓名" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="12">
                <el-form-item label="性别" prop="gender">
                  <el-radio-group v-model="auditForm.gender">
                    <el-radio :label= 0>男</el-radio>
                    <el-radio :label= 1>女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item label="身份证号" prop="idcard">
                  <el-input v-model="auditForm.idcard" placeholder="请输入身份证号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="12">
                <el-form-item label="民族" prop="nation">
                  <el-input v-model="auditForm.nation" placeholder="请输入民族" />
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item label="出生日期" prop="birthday">
                  <el-date-picker clearable size="small"
                                  v-model="auditForm.birthday"
                                  type="date"
                                  value-format="yyyy-MM-dd"
                                  placeholder="选择出生日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="12">
                <el-form-item label="家庭地址" prop="address">
                  <el-input v-model="auditForm.address" placeholder="请输入家庭地址" />
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item label="手机号" prop="phone">
                  <el-input v-model="auditForm.phone" placeholder="请输入手机号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col span="12">
                <el-form-item label="父亲手机号" prop="fatherPhone">
                  <el-input v-model="auditForm.fatherPhone" placeholder="请输入父亲手机号" />
                </el-form-item>
              </el-col>
              <el-col span="12">
                <el-form-item label="母亲手机号" prop="motherPhone">
                  <el-input v-model="auditForm.motherPhone" placeholder="请输入母亲手机号" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="备注" prop="reamrk">
              <el-input v-model="auditForm.reamrk" type="textarea" placeholder="请输入备注" />
            </el-form-item>
          </el-form>
        </div>
        <div class="">
          <el-upload
            :action="uploadImgUrl"
            :file-list="imgShowList"
            list-type="picture-card"
            :on-preview="handlePictureCardPreview"
            :on-exceed='uploadNumber'
            :multiple="true"
            :on-success="handleUploadSuccess"
            :limit="50"
            :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
          <el-dialog :visible.sync="dialogVisible">
            <img width="100%" :src="dialogImageUrl" alt="">
          </el-dialog>
        </div>
      </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitrepeatedForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo,updateDetails } from "@/api/reviewed/info";
import { getDicts } from "@/api/system/dict/data";
import PElImage from '@/components/p-el-image';

export default {
  components: {
    'p-el-image': PElImage,
  },
  name: "Info",
  dicts: ['game_type','recovery_status','audit_status',"auditFlag"],
  data() {
    return {
      // 遮罩层
      loading: false,
      cellStyle: "color: #000000;font-size: 15px",
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      extractAccount: [],
      saleStates: [],
      dialogVisible: false,
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
      //游戏大区
      regionalArr: [],
      levelArr: [],
      // 弹出层标题
      title: "",
      auditTitle: "",
      // 是否显示弹出层
      open: false,
      auditOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
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
        salesIdAddr: [],
        recoveryState: null,
        recoveryId: null,
        auditState: null,
        auditFlag: null,
        recoveryNote: null,
        auditNote: null,
        verifyState: null,
        verifyId: null
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
        des: true,
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
          skins: true,
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
          salesStatus: false,
        },
      },
      // 表单参数
      form: {},
      auditForm: {},
      // 表单校验
      rules: {
        verifyState: [
          { required: true, message: '请选择审核是否通过', trigger: 'change' }
        ],
        auditNote: [
          { required: true, message: '请填审核原因', trigger: 'blur' }
        ]
      },
      uploadImgUrl: "",
      dialogImageUrl: "",
      fileNameArr: [],
      imgFileList: [],
      imgShowList: [], //传给后台的图片
      table: {
        tableData: [],
        tableHeight: $(window).height() - 300,
      },
      copyVlaue: "",
      copyPwd: "",
      copyTel: "",
      ifClick: true,
      hoste: ""
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    //this.getList();
    this.getHost();
  },
  methods: {
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$$modal.msgError("最多只能上传20张图片");
    },
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;

      // 2.找到图片对应的索引值
      const spliceIndex =  this.imgShowList.findIndex((element) =>{
          return element.url === filePath
           //传给后台的图片

      });
      that.imgShowList.splice(spliceIndex,1);
      console.log(that.imgShowList)


    },
    handleUploadSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.imgShowList.push(param);
        console.log(this.imgFileList);
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    getHost() {
      getDicts("audit_url").then((res) => {
        if (res.code == 200) {
          console.log(res);
          if (res.data.length > 0) {
            this.hoste = res.data[0].dictLabel;
          }
        }
      });
    },
    // 在methods里面书写复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
      this.copyVlaue = "";
    },
    onError() {
      this.$modal.msgError("复制失败");
      this.copyVlaue = "";
    },
    /** 查询游戏账号列表 */
    getList() {
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
        debugger;
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
      this.loading = true;
      this.queryParams.verifyState = 1;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    selectGameType() {
      let gameType = this.queryParams.type;
      console.log(gameType);
      getDicts(gameType + "段位").then((res) => {
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
      getDicts(gameType).then((res) => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
      this.getList();
    },
    selectLevel() {
      this.getList();
    },
    selectGameDaQu() {
      this.getList();
    },
    // 取消按钮
    cancel() {
      this.imgFileList = [];
      this.imgShowList = [];
      this.open = false;
      this.auditOpen = false;
      this.reset();
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
        delFlag: null,
      };
      this.auditForm = {};
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
      this.queryParams.saleState = null;
      this.queryParams.gunsStart = null;
      this.queryParams.gunsEnd = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.saleStates = selection.map(item => item.saleState)
      this.extractAccount = selection.map(item => item.extractAccount)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        // return "";
        return "success-row";
      } else {
        return "warning-row";
      }
    },
    /** 修改按钮操作 */
    handleRepeatedUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getInfo(id).then(response => {
        if (response.data == undefined){
          this.$modal.msgWarning("此账号未填写个人信息，请联系卖家填写个人信息!!");
          return;
        }
        let data = response.data;
        console.log(data)
        this.imgShowList=data.infoImgs;
        this.imgShowList.forEach(item=> {
          item.url = item.imgUrl
          item.name = item.fileName
        })
        console.log(this.imgShowList)
        this.auditForm = data;
        this.auditOpen = true;
        this.auditTitle = "编辑";
      });
    },
    /** 提交按钮 */
    submitrepeatedForm() {
      this.$refs["auditForm"].validate(valid => {
        if (valid) {
          //不需要新增，只需要审核
          //重新回到待审核状态
          //之前你删除操作的是一个叫什么file数组，提交时候用的是imgShowList，就这个问题
          this.auditForm.urlArr =this.imgShowList
          updateDetails(this.auditForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.auditOpen = false;
            this.getList();
          });
        }
      });
    },

    //一键复制
    keyCopy(){
      const id = this.ids;
      const extractAccount = this.extractAccount;
      var value =
        "系统id：" +
        id +
        "\n" +
        "审核包赔url：" +
        this.hoste+"?id="+id[0];
      "\n";
      this.copyVlaue = this.copyVlaue + value;
      this.$modal.msgSuccess("复制成功!");
    }
  }
};
</script>
<style lang="scss" scoped>
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

  .zdy-dc-btn,
  .zdy-dc-btn:hover,
  .zdy-dc-btn:focus,
  .zdy-dc-btn:active {
    color: #fff;
    background: #00c9db;
    border: 1px solid #00c9db;
  }

  .zdy-pifu-content {
    width: 25px;
    height: 25px;
    margin-left: 10px;
    cursor: pointer;

    img {
      width: 100%;
      height: 100%;
    }
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

  .zdy-form {
    padding: 8px;
    background-color: #f4f7f9;
    border-radius: 6px;
    box-sizing: border-box;
  }

  .zdy-btn-refresh {
    background-color: #94aab9;
    border-color: #94aab9;
  }

  .zdy-btn {
    padding: 7px 20px;
  }

  .shiming-label {
    width: 68px;
    padding-right: 12px;
    text-align: right;
  }

  .shiming-wrap {
    width: 283px;

    height: 37px;
    display: flex;
    align-items: center;
  }

  ::v-deep .shiming-wrap .el-checkbox__inner {
    width: 20px;
    height: 20px;
    border-radius: 50%;
  }

  ::v-deep .shiming-wrap .el-checkbox__inner::after {
    height: 9px;
    left: 7px;
    top: 3px;
  }

  .shiming-content {
    display: flex;
    align-items: center;
  }

  .shiming-content img {
    width: 13px;
    height: 15px;
    margin: 0 6px 0 0px;
  }

  .shiming-content p {
    font-size: 14px;
    color: #3b4b62;
  }

  .mm-b-8 {
    margin-bottom: 8px !important;
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

  ::v-deep .el-table td.el-table__cell {
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

  /* .el-tooltip__popper {
    display: none;
  } */

  .el-table--medium .el-table__cell {
    padding: 0;
  }
</style>
