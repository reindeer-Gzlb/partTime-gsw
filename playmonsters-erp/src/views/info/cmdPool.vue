<template>
  <div class="app-container jklis">
    <el-form
      :model="queryParams"
      ref="queryForm"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="编号" prop="width=150">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="游戏类型" prop="type">
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
      <el-form-item label="区服" prop="regional">
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
      <el-form-item label="实名认证" prop="level">
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
      <el-form-item
        label="供货客服"
        prop="supplyId"
      >
        <el-select
          multiple
          v-model="queryParams.supplyIdAddr"
          @change="selectGameDaQu"
          placeholder="请选择供货客服"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in supplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="价格区间" prop="price">
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.priceStart"></el-input>
        </el-col>
        <el-col :span="2"> ---</el-col>
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.priceEnd"></el-input>
        </el-col>
      </el-form-item>

      <el-form-item v-if="show.select.heroes" label="英雄数量" prop="heroes">
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.heroStart"></el-input>
        </el-col>
        <el-col :span="2"> ---</el-col>
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.heroEnd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item v-if="show.select.skins" label="皮肤数量" prop="skins">
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.skinStart"></el-input>
        </el-col>
        <el-col :span="1"> ---</el-col>
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.skinEnd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item v-if="show.select.suit" label="套装数量" prop="suit">
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.suitStart"></el-input>
        </el-col>
        <el-col :span="1"> ---</el-col>
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.suitEnd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item v-if="show.select.guns" label="枪皮" prop="guns">
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.gunsStart"></el-input>
        </el-col>
        <el-col :span="1"> ---</el-col>
        <el-col :span="5">
          <el-input size="small" v-model="queryParams.gunsEnd"></el-input>
        </el-col>
      </el-form-item>
      <el-form-item label="自定义" prop="accountId">
        <el-input
          v-model="queryParams.des"
          placeholder="请输入自定义关键词"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item
        label="导入时间"
        prop="createTime"
      >
        <el-date-picker
          @change="selectCreateTime"
          v-model="createTimeArr"
          size="small"
          format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
        >搜索
        </el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
        >重置
        </el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          v-if="showBtnOutType"
          :disabled="multiple"
          @click="addCmdAccount()"
        >添加我的账号
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-s-promotion"
          size="mini"
          v-if="showBtnOutType"
          @click="bacthAdd()"
        >批量添加
        </el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          v-if="outAccount"
          :disabled="multiple"
          @click="delAcount()"
        >下架账号
        </el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
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
      @sort-change="sortFun"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="right">
            <el-form-item :style="{marginRight: wihh + '%' }" label-width="110px">
              <!-- <span>{{ props.row.des }}</span> -->
              <template slot-scope="scope">
                <!--  <el-tooltip :content="props.row.des" placement="top">
                <span   class="showOverTooltip">{{props.row.des}}</span>
                 </el-tooltip> -->
                <span class="showOverTooltip">{{ props.row.des }}</span>
              </template>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="序号" sortable="custom" width="80" label-position="left" sort-by align="center" prop="id"/>
      <el-table-column
        label="游戏类型"
        width="100"
        align="center"
        prop="type"
      />
      <el-table-column
        label="区服"
        width="100"
        align="center"
        prop="regional"
      >
      </el-table-column>
      <el-table-column
        label="自编号"
        align="center"
        width="100"
        prop="gameName"
      />
      <el-table-column
        label="实名认证"
        align="center"
        width="100"
        prop="level"
      />
      <!-- <el-table-column
        sortable="custom" sort-by
        v-if="show.heroes"
        label="英雄"
        width="100"
        align="center"
        prop="heroes"
      />
      <el-table-column
        sortable="custom" sort-by
        v-if="show.skins"
        label="皮肤"
        width="100"
        align="center"
        prop="skins"
      /> -->
      <!-- <el-table-column
        sortable="custom" sort-by
        v-if="show.suit"
        label="套装"
        align="center"
        width="100"
        prop="suit"
      />
      <el-table-column
        sortable="custom" sort-by
        v-if="show.guns"
        label="枪皮"
        align="center"
        width="100"
        prop="guns"
      /> -->
      <el-table-column
        sortable="custom" sort-by
        label="售价"
        width="100"
        align="center"
        prop="price"
      />
      <el-table-column
        label="简介"
        align="center"
        :show-overflow-tooltip="true"
        prop="des"
      >
      </el-table-column>
      <!-- <el-table-column
        sortable="custom" sort-by
        label="导入时间"
        width="160"
        align="center"
        prop="createTime"
      /> -->
      <el-table-column
       width="160"
        label="供货客服"
        align="center"
        prop="supplyName"
      />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page-sizes="[10, 20, 30, 40, 50 ,60]"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

      <el-dialog  :visible.sync="dialogFormVisible" width="20%">
      <el-form :model="form">
        <el-form-item label="终端供货" :label-width="formLabelWidth">
           <el-select
          v-model="form.suplyId"
          placeholder="请选择终端供货"
          clearable
          size="small"
        >
          <el-option
            v-for="dict in supplyList"
            :key="dict.userId"
            :label="dict.nickName"
            :value="dict.userId"
          />
        </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmSub">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
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
import {
  cmdPoollist,getCmdSupplyList,bacthAddAccount
} from '@/api/info/info'
import { addMyAcount,addCmdAccount } from '@/api/info/account'
import { getDicts } from '@/api/system/dict/data'
import { getToken } from '@/utils/auth'
import moment from 'moment'

export default {
  name: 'Info',
  dicts: ['game_type', 'account_status'],
  data() {
    return {
      tuisongList: [],
      outAccount: false,
      showBtnOutType: false,
      wihh: 10,
      dialogGameTypeHp: false,
      intType: null,
      operation: null,
      showDialogTitle: '',
      formLabelWidth: '120px',
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
      title: '',
      // 是否显示弹出层
      open: false,
      saleTimeArr: [],
      createTimeArr: [],
      updateTimeArr: [],
      videoList: [],
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
      type: '',
      saleStatus: '',
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
        tableHeight: $(window).height() - 300
      },
      copyVlaue: '',
      copyTel: '',
      ifClick: true,
      hoste: '',
      outType: ''
    }
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + '/upload/image'
    this.outType = this.$route.query.type
    console.log(this.outType)
    if ('out' == this.outType) {
      this.showBtnOutType = false
      this.outAccount = true
    } else {
      this.showBtnOutType = true
      this.outAccount = false
    }
    this.getList()
    // this.getCmdSupplyList()
  },
  methods: {
    confirmSub(){
      this.loading = true
      bacthAddAccount(this.form).then(res =>{
        this.dialogFormVisible = false
        this.$modal.msgSuccess('操作成功')
        this.getList()
        this.loading = false
      })
    },
    bacthAdd(){
      this.dialogFormVisible = true
    },
    getCmdSupplyList() {
      getCmdSupplyList().then((res) => {
        console.log(res)
        this.supplyList = res
      })
    },
    sortFun(val) {
      this.queryParams.sort = val.order
      this.queryParams.sortColumn = val.prop
      this.getList()
    },
    // 转出金额校验
    checkPriceOut() {
      let checkPlan = '' + this.telFrom.realPrice
      checkPlan = checkPlan
        .replace(/[^\d.]/g, '') // 清除“数字”和“.”以外的字符
        .replace(/\.{2,}/g, '.') // 只保留第一个. 清除多余的
        .replace(/^\./g, '') // 保证第一个为数字而不是.
        .replace('.', '$#$')
        .replace(/\./g, '')
        .replace('$#$', '.')
      if (checkPlan.indexOf('.') < 0 && checkPlan !== '') {
        // 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        checkPlan = parseFloat(checkPlan) + ''
      } else if (checkPlan.indexOf('.') >= 0) {
        checkPlan = checkPlan.replace(/^()*(\d+)\.(\d\d).*$/, '$1$2.$3') // 只能输入两个小数
      }
      this.telFrom.realPrice = checkPlan
    },
    selectSaleTime() {
      if (this.saleTimeArr.length > 1) {
        this.queryParams.saleTimeStart = this.saleTimeArr[0]
        this.queryParams.saleTimeEnd = this.saleTimeArr[1]
      }
    },
    selectupdateTime() {
      if (this.updateTimeArr.length > 1) {
        var startDate = moment(this.updateTimeArr[0]).format('YYYY-MM-DD')
        this.queryParams.updateTimeStart = startDate + ' 00:00:00'
        var endDate = moment(this.updateTimeArr[1]).format('YYYY-MM-DD')
        this.queryParams.updateTimeEnd = endDate + ' 23:59:59'
      }
    },
    selectCreateTime() {
      if (this.createTimeArr.length > 1) {
        var startDate = moment(this.createTimeArr[0]).format('YYYY-MM-DD')
        this.queryParams.createTimeStart = startDate + ' 00:00:00'
        var endDate = moment(this.createTimeArr[1]).format('YYYY-MM-DD')
        this.queryParams.createTimeEnd = endDate + ' 23:59:59'
      }
    },
    selectGameType() {
      let gameType = this.queryParams.type
      console.log(gameType)
      getDicts(gameType + '段位').then((res) => {
        if (res.code == 200) {
          this.levelArr = res.data
        }
      })

      if ('和平精英' == gameType) {
        gameType = '王者荣耀'
        this.show.heroes = false
        this.show.skins = false
        this.show.suit = true
        this.show.guns = true
        this.show.select.heroes = false
        this.show.select.skins = false
        this.show.select.guns = true
        this.show.select.suit = true
      } else {
        this.show.select.heroes = true
        this.show.select.skins = true
        this.show.select.guns = false
        this.show.select.suit = false
        this.show.heroes = true
        this.show.skins = true
        this.show.suit = false
        this.show.guns = false
      }

      getDicts(gameType).then((res) => {
        if (res.code == 200) {
          this.regionalArr = res.data
        }
      })
      this.getList()
    },
    /** 查询游戏账号列表 */
    getList() {
      console.log(this.queryParams)
      let priceStart = typeof Number(this.queryParams.priceStart)
      let priceEnd = typeof Number(this.queryParams.priceEnd)
      if (priceStart > priceEnd) {
        this.$modal.msgError('价格数据不对')
        return true
      }
      let heroStart = this.queryParams.heroStart
      let heroEnd = this.queryParams.heroEnd
      if (typeof Number(heroStart) > typeof Number(heroEnd)) {
        this.$modal.msgError('英雄数量输入不对')
        return true
      }

      let skinStart = this.queryParams.skinStart
      let skinEnd = this.queryParams.skinEnd
      if (typeof Number(skinStart) > typeof Number(skinEnd)) {
        this.$modal.msgError('皮肤数量输入不对')
        return true
      }

      let suitStart = this.queryParams.suitStart
      let suitEnd = this.queryParams.suitEnd
      if (typeof Number(suitStart) > typeof Number(suitEnd)) {
        this.$modal.msgError('套装数量输入不对')
        return true
      }

      let gunsStart = this.queryParams.gunsStart
      let gunsEnd = this.queryParams.gunsEnd
      if (typeof Number(gunsStart) > typeof Number(gunsEnd)) {
        this.$modal.msgError('枪皮数量输入不对')
        return true
      }
      if (this.queryParams.saleState == 6) {
        this.queryParams.timeout = 30
        this.queryParams.saleState = 1
      }
      this.loading = true
      this.queryParams.saleState = 1
      cmdPoollist(this.queryParams).then((response) => {
        this.infoList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    selectLevel() {
      this.getList()
    },
    selectGameDaQu() {
      this.getList()
    },
    // 取消按钮
    cancel() {
      console.log(1)
      this.imgFileList = []
      this.imgShowList = []
      this.open = false
      this.reset()
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
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.priceStart = null
      this.queryParams.priceEnd = null
      this.queryParams.heroStart = null
      this.queryParams.heroEnd = null
      this.queryParams.skinStart = null
      this.queryParams.skinEnd = null
      this.queryParams.suitStart = null
      this.queryParams.suitEnd = null
      this.queryParams.gunsStart = null
      this.queryParams.gunsEnd = null
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 添加未我的账号 */
    addMyAcount() {
      const ids = this.ids
      addMyAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg)
          } else {
            this.$modal.msgSuccess('操作成功')
          }
          this.getList()
        }
      })
    },
    addCmdAccount(){
      const ids = this.ids
      addCmdAccount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg)
          } else {
            this.$modal.msgSuccess('操作成功')
          }
          this.getList()
        }
        this.loading = false
      })
    },
    pushAcount() {
      const ids = this.ids
      pushAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg)
          } else {
            this.$modal.msgSuccess('操作成功')
          }
          this.getList()
        }
      })
    },
    delAcount() {
      const ids = this.ids
      delAcount(ids).then((res) => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg)
          } else {
            this.$modal.msgSuccess('操作成功')
          }
          this.getList()
        }
      })
    },
    delete() {
      const ids = row.id || this.ids
      this.$modal
        .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
        .then(function() {
          return delInfo(ids)
        })
        .then(() => {
          this.getList()
          this.$modal.msgSuccess('删除成功')
        })
        .catch(() => {
        })
    }
  }
}
</script>
<style scoped>
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

/deep/ .el-table td.el-table__cell {
  border-bottom: none !important;
}

.cell {
  margin-left: 0%;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 20%;
}

.el-tooltip__popper {
  display: none;
}

.el-table--medium .el-table__cell {
  padding: 0;
}
</style>


