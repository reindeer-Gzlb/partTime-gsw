<template>
  <div>
    <div class="app-container" v-if="parterShow">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="销售伙伴" prop="saleName">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入销售伙伴"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="请选择状态"
            clearable
            size="small"
            @click="handleQuery"
          >
            <el-option
              v-for="dict in statusList"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button plain icon="el-icon-plus" size="mini" @click="back"
            >返回</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            >修改</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            >删除</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain size="mini" @click="doneDelJob"
            >手动执行编辑删除任务池</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button type="danger" size="mini" @click="openDelWrong()"
            >删除错误记录</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" size="mini" @click="openDelPushed()"
            >删除已推送</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" size="mini" @click="openBatchOffshelf()"
            >批量下架</el-button
          >
        </el-col>

        <el-col :span="1.5">
          <el-button type="warning" size="mini" @click="openBatchEdit()"
            >批量编辑</el-button
          >
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table
        :border="true"
        v-loading="loading"
        :data="partnerList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="id" />
        <el-table-column label="销售伙伴" align="center" prop="name" />

        <el-table-column label="CODE" align="center" prop="saleName" />

        <el-table-column label="KEY" align="center" prop="saleKey" />

        <el-table-column label="其他属性1" align="center" prop="other" />
        <el-table-column label="其他属性2" align="center" prop="twoOther" />
        <el-table-column label="其他属性3" align="center" prop="threeOther" />
        <el-table-column label="版本号" align="center" prop="vsion" />
        <el-table-column label="状态" align="center" prop="status" />
        <el-table-column label="创建人" align="center" prop="createName" />
        <el-table-column label="创建时间" align="center" prop="createTime" />
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="openDetail(scope.row)"
              >查看</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
      <!-- 添加或修改销售伙伴对话框 -->
      <el-dialog
        :title="title"
        :visible.sync="open"
        width="500px"
        @close="cancel"
      >
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="销售伙伴" prop="name">
            <el-input v-model="form.name" placeholder="请输入销售伙伴" />
          </el-form-item>
          <el-form-item label="CODE" prop="saleName">
            <el-input v-model="form.saleName" placeholder="请输入销售伙伴" />
          </el-form-item>
          <el-form-item label="key" prop="saleKey">
            <el-input v-model="form.saleKey" placeholder="请输入key" />
          </el-form-item>
          <el-form-item label="其他属性1" prop="other">
            <el-input v-model="form.other" placeholder="请输入其他属性1" />
          </el-form-item>
          <el-form-item label="其他属性2" prop="twoOther">
            <el-input v-model="form.twoOther" placeholder="请输入其他属性2" />
          </el-form-item>
          <el-form-item label="其他属性3" prop="threeOther">
            <el-input v-model="form.threeOther" placeholder="请输入其他属性3" />
          </el-form-item>
          <!-- <el-form-item label="上调比例" prop="seleep">
            <el-input v-model="form.seleep" placeholder="上调比例" />
          </el-form-item> -->
          <el-form-item label="一键加价" prop="seleep">
            <!-- <el-input v-model="form.seleep" placeholder="上调比例" /> -->
            <el-button
              style="background-color: #5982ff; color: #fff"
              @click="yijianjiajiabtn"
              >一键加价</el-button
            >
          </el-form-item>
          <!-- <el-form-item label="版本号" prop="vsion">
            <span>{{ form.vsion }}</span>
          </el-form-item> -->
          <el-form-item label="状态" prop="status">
            <!-- <el-input v-model="form.status" placeholder="请输入其他属性3" /> -->
            <el-select
              v-model="form.status"
              placeholder="请选择状态"
              clearable
              size="small"
            >
              <el-option
                v-for="dict in statusList"
                :key="dict.id"
                :label="dict.name"
                :value="dict.id"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
        <el-dialog
          title="一键加价"
          :visible.sync="dialogVisibleYiJian"
          width="911px"
          @close="closeder"
          append-to-body
        >
          <!-- <span>这是一段信息</span> -->
          <div>
            <el-table
              ref="multipleTable"
              :data="peizhiList"
              style="width: 100%"
              :cell-style="rowStyle"
            >
              <el-table-column
                align="center"
                label="最小值"
                width="166"
                prop="down"
              >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.down" placeholder="0"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="up"
                align="center"
                label="最大值"
                width="166"
              >
                <template slot-scope="scope">
                  <el-input v-model="scope.row.up" placeholder="0"></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="type"
                align="center"
                label="类型"
                width="200px"
              >
                <template slot-scope="scope">
                  <el-radio-group
                    size="mini"
                    v-model="scope.row.type"
                    fill="#5982ff"
                  >
                    <el-radio-button label="1">固定加价</el-radio-button>
                    <el-radio-button label="2">比例加价</el-radio-button>
                  </el-radio-group>
                </template>
              </el-table-column>
              <el-table-column
                prop="raise"
                align="center"
                label="加价"
                width="166"
              >
                <template slot-scope="scope">
                  <el-input
                    v-model="scope.row.raise"
                    placeholder="0"
                  ></el-input>
                </template>
              </el-table-column>
              <el-table-column
                prop="boundaryPirce"
                align="center"
                label="操作"
                width="166"
              >
                <template slot-scope="scope">
                  <div
                    style="
                      display: flex;
                      align-items: center;
                      justify-content: center;
                    "
                  >
                    <div style="margin-right: 20px">
                      <i
                        @click="addfn"
                        style="color: #5982ff"
                        class="el-icon-plus"
                      ></i>
                    </div>
                    <div>
                      <i
                        @click="deletefn(scope.row)"
                        style="color: #5982ff"
                        class="el-icon-delete"
                      ></i>
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="closeder">取 消</el-button>
            <el-button type="primary" @click="tongyiyijainbtn">确 定</el-button>
          </span>
        </el-dialog>
      </el-dialog>
    </div>

    <div v-if="intername" class="app-container">
      <el-form
        :model="queryNameParams"
        ref="queryNameForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
      >
        <el-form-item label="合作名称" prop="name">
          <el-input
            v-model="queryNameParams.name"
            placeholder="请输入合作名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="合作编码" prop="code">
          <el-input
            v-model="queryNameParams.code"
            placeholder="请输入合作编码"
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
            @click="handleQueryName"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetNameQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleNameAdd"
            v-hasPermi="['account:name:add']"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleNameUpdate"
            v-hasPermi="['account:name:edit']"
            >修改</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleNameDelete"
            v-hasPermi="['account:name:remove']"
            >删除</el-button
          >
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        :data="nameList"
        @selection-change="handleNameSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" align="center" prop="id" />
        <el-table-column label="合作名称" align="center" prop="name" />
        <el-table-column label="合作编码" align="center" prop="code">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="showParter(scope.row)">{{
              scope.row.code
            }}</el-button>
          </template>
        </el-table-column>
        <el-table-column label="创建人" align="center" prop="createName" />
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <el-dialog
        :title="title"
        :visible.sync="openName"
        width="500px"
        append-to-body
      >
        <el-form
          ref="formName"
          :model="formName"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="合作名称" prop="name">
            <el-input v-model="formName.name" placeholder="请输入合作名称" />
          </el-form-item>
          <el-form-item label="合作编码" prop="code">
            <el-input
              :disabled="codeIs"
              v-model="formName.code"
              placeholder="请输入合作编码"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitNameForm">确 定</el-button>
          <el-button @click="cancelName">取 消</el-button>
        </div>
      </el-dialog>
    </div>
    <delPushedDialog ref="delPushedDialog"></delPushedDialog>
    <supplyconfig ref="supplyconfig"></supplyconfig>
  </div>
</template>

<script>
import {
  listPartner,
  getPartner,
  delPartner,
  addPartner,
  updatePartner,
  exportPartner,
  delInter,
  delAccount,
  execuDel,
  batchShelves,
  editBachthApi
} from "@/api/partner/partner";
import { getSupplyList } from "@/api/info/info";
import { addName, delName, getName, listName, updateName } from '@/api/intername/name';
import delPushedDialog from "./delPushedDialog.vue";
import supplyconfig from "./supplyconfig.vue";
export default {
  name: "Partner",
  components: { delPushedDialog, supplyconfig },
  data() {
    return {
      codeIs: false,
      formName: {},
      openName: false,
      titleName: '',
      nameList: [],
      intername: false,
      parterShow: true,
      statusList: [
        {
          id: 1,
          name: '可用'
        },
        {
          id: 2,
          name: '禁用'
        }
      ],
      supplyList: [],
      saleNameDis: false,
      // 遮罩层
      loading: true,
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
      // 销售伙伴表格数据
      partnerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      queryNameParams: {
        pageNum: 1,
        name: null,
        code: null
      },
      interNameRow: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        updateId: null,
        name: null,
        saleKey: null,
        other: null,
        twoOther: null,
        threeOther: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      dialogVisibleYiJian: false,
      peizhiList: [
        {
          down: "",
          up: "",
          type: "1",
          raise: "",
        },
      ]
    };
  },
  created() {
    // this.nameAllList();
    this.getList()
    this.getSupplyList();
  },
  methods: {
    closeder() {
      this.flag = false;
      this.dialogVisibleYiJian = false;
    },
    // 一键加价
    async tongyiyijainbtn() {
      const data = JSON.stringify(this.peizhiList)
      this.form.seleepConfig = data
      console.log('222', data);
      this.dialogVisibleYiJian = false;
    },
    rowStyle() {
      return "text-align:center";
    },
    addfn() {
      this.peizhiList.push({ down: "", up: "", type: "1", raise: "" });
    },
    deletefn(row) {
      if (this.peizhiList.length <= 1) {
        return this.$message.warning("请至少留有一条！");
      }
      const indexs = this.peizhiList.indexOf(row);
      console.log("当前点击的行的index为：", indexs);
      this.peizhiList = this.peizhiList.filter(
        (item, index) => index != indexs
      );
    },
    yijianjiajiabtn() {
      this.dialogVisibleYiJian = true;
    },
    // 删除错误的记录
    delWrong(form) {
      this.$modal.confirm(`是否确认"${form.name}"删除错误的记录?`).then(() => {
        delInter(form).then(r => {
          console.log("%c Line:252 🥕 r", "color:#e41a6a", r);
          this.$message({
            type: 'success',
            message: r.msg
          })
        })
      }).catch(() => { });
    },
    // 打开删除错误记录弹窗
    openDelWrong() {
      let obj = {
        title: '删除错误的记录'
      }
      this.$refs.delPushedDialog.open(obj, '错误记录')
    },
    // 删除推送过的账号 createId  configId saleStatus
    delPushed(form) {
      console.log("%c Line:250 🍎 删除推送过的账号form", "color:#ffdd4d", form);
      this.$modal.confirm(`是否确认"${form.name}"删除推送过的账号?`).then(() => {
        delAccount(form).then(r => {
          console.log("%c Line:252 🥕 r", "color:#e41a6a", r);
          this.$message({
            type: 'success',
            message: r.msg
          })
        })
      }).catch(() => { });
    },
    // 打开删除推送过的账号弹窗
    openDelPushed(row) {
      let obj = {
        title: '删除已推送账号'
      }
      this.$refs.delPushedDialog.open(obj)
    },
    // 手动执行任务池
    doneDelJob() {
      this.$modal.confirm(`是否确认手动执行任务池?`).then(() => {
        execuDel().then(r => {
          console.log("%c Line:252 🥕 r", "color:#e41a6a", r);
          this.$message({
            type: 'success',
            message: r.msg
          })
        })
      }).catch(() => { });
    },
    // 批量下架 createId  config
    batchOffshelf(form) {
      console.log("%c Line:301 🍢 批量下架form", "color:#42b983", form);
      this.$modal.confirm(`是否确认"${form.name}"批量下架?`).then(() => {
        batchShelves(form).then(r => {
          console.log("%c Line:252 🥕 r", "color:#e41a6a", r);
          this.$message({
            type: 'success',
            message: r.msg
          })
        })
      }).catch(() => { });
    },
    // 打开批量下架弹窗
    openBatchOffshelf(row) {
      let obj = {
        title: '批量下架'
      }
      this.$refs.delPushedDialog.open(obj, '下架')
    },
    // 批量编辑 createId  config
    batchEdit(form) {
      console.log("%c Line:317 🥓 批量编辑form", "color:#fca650", form);
      this.$modal.confirm(`是否确认"${form.name}"批量编辑?`).then(() => {
        editBachthApi(form).then(r => {
          console.log("%c Line:252 🥕 r", "color:#e41a6a", r);
          this.$message({
            type: 'success',
            message: r.msg
          })
        })
      }).catch(() => { });
    },
    // 打开批量编辑弹窗
    openBatchEdit(row) {
      let obj = {
        title: '批量编辑'
      }
      this.$refs.delPushedDialog.open(obj, '编辑')
    },

    // 查看配置的供货商
    openDetail(row) {
      this.$refs.supplyconfig.open(row)
    },






    back() {
      this.parterShow = false
      this.intername = true
    },
    showParter(row) {
      this.interNameRow = row
      this.parterShow = true
      this.intername = false
      this.queryParams.saleName = row.code
      this.getList()
    },
    // 多选框选中数据
    handleNameSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    submitNameForm() {
      this.$refs["formName"].validate(valid => {
        if (valid) {
          if (this.formName.id != null) {
            updateName(this.formName).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openName = false;
              this.nameAllList();
            });
          } else {
            addName(this.formName).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.openName = false;
              this.nameAllList();
            });
          }
        }
      });
    },
    handleNameDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除编号为"' + ids + '"的数据项？').then(function () {
        return delName(ids);
      }).then(() => {
        this.nameAllList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    handleNameUpdate(row) {
      this.reset();
      this.codeIs = true
      const id = row.id || this.ids
      getName(id).then(response => {
        this.formName = response.data;
        this.openName = true;
        this.title = "修改";
      });
    },
    handleNameAdd() {
      this.resetName();
      this.codeIs = false
      this.openName = true;
      this.title = "添加";
    },
    resetName() {
      this.formName = {
        id: null,
        createId: null,
        updateId: null,
        createTime: null,
        updateTime: null,
        name: null,
        code: null
      };
      this.resetForm("formName");
    },
    handleQueryName() {
      this.queryNameParams.pageNum = 1;
      this.nameAllList();
    },
    // 取消按钮
    cancelName() {
      this.openName = false;
      this.reset();
    },
    nameAllList() {
      this.loading = true;
      listName(this.queryNameParams).then(response => {
        this.nameList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询销售伙伴列表 */
    getList() {
      this.loading = true;
      let params = this.queryParams
      listPartner(this.queryParams).then((response) => {
        this.partnerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then((res) => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.peizhiList = [
        {
          down: "",
          up: "",
          type: "1",
          raise: "",
        },
      ]
      this.reset();
    },
    resetNameQuery() {
      this.resetForm("queryNameForm");
      this.handleQueryName();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        updateId: null,
        saleName: null,
        name: null,
        saleKey: null,
        other: null,
        twoOther: null,
        threeOther: null,
        createTime: null,
        updateTime: null,
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.saleNameDis = false;
      this.reset();
      this.form.name = this.interNameRow.name
      this.form.saleName = this.interNameRow.code
      this.open = true;
      this.title = "添加销售伙伴";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.saleNameDis = true;
      this.reset();
      const id = row.id || this.ids;
      getPartner(id).then((response) => {
        this.form.name = this.interNameRow.name
        this.form.saleKey = this.interNameRow.code
        this.form = response.data;
        this.peizhiList = JSON.parse(response.data.seleepConfig)
        this.open = true;
        this.title = "修改销售伙伴";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updatePartner(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPartner(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除销售伙伴编号为"' + ids + '"的数据项？')
        .then(function () {
          return delPartner(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有销售伙伴数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportPartner(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => { });
    },
  },
};
</script>
