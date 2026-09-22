<template>
  <div>
    <el-card>
      <el-form :inline="true" :model="form">
        <el-form-item label="账号">
          <el-input
            v-model="form.steamAccount"
            placeholder="请输入steam账号"
          ></el-input>
        </el-form-item>
        <el-form-item label="任务状态">
          <el-select v-model="form.status" placeholder="请选择活动区域">
            <el-option
              v-for="item in typeArr"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="timer"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList()">搜索</el-button>
          <el-button type="info" @click="chongzhi">重置</el-button>
          <el-button type="primary" @click="addfn">+ 新增</el-button>
          <el-button icon="el-icon-download" type="warning" @click="importExl"
            >导出</el-button
          >
        </el-form-item>
      </el-form>

      <el-table :data="list" border style="width: 100%">
        <el-table-column fixed prop="id" label="编号" width="88" align="center">
        </el-table-column>
        <el-table-column prop="steamAccount" label="Steam账号" align="center">
        </el-table-column>
        <el-table-column prop="status" label="任务状态" align="center">
          <template #default="{ row }">
            <div v-if="row.status == 1" style="color: green">正常</div>
            <div v-if="row.status == 2" style="color: red">异常</div>
            <div v-if="row.status == 3" style="color: red">回收</div>
          </template>
        </el-table-column>
        <el-table-column prop="steamEmail" label="邮箱" align="center">
        </el-table-column>
        <el-table-column prop="steamPhone" label="手机号" align="center">
        </el-table-column>
        <el-table-column prop="emailStatus" label="邮箱状态" align="center">
          <template #default="{ row }">
            <div v-if="row.emailStatus == 1" style="color: green">正常</div>
            <div v-if="row.emailStatus == 2" style="color: green">邮箱为空</div>
            <div v-if="row.emailStatus == 3" style="color: red">邮箱变化</div>
            <div v-if="row.emailStatus == 4" style="color: red">回收</div>
          </template>
        </el-table-column>
        <el-table-column prop="phoneStatus" label="手机号状态" align="center">
          <template #default="{ row }">
            <div v-if="row.phoneStatus == 1" style="color: green">正常</div>
            <div v-if="row.phoneStatus == 2" style="color: green">手机为空</div>
            <div v-if="row.phoneStatus == 3" style="color: red">手机变化</div>
            <div v-if="row.phoneStatus == 4" style="color: red">回收</div>
          </template>
        </el-table-column>
        <el-table-column prop="remark" label="备注" width="333" align="center">
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center">
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="226" align="center">
          <template slot-scope="scope">
            <el-button @click="handleback(scope.row)" type="text" size="small"
              >回收</el-button
            >
            <el-button
              @click="handleClickAgain(scope.row)"
              type="text"
              size="small"
              >重新校验</el-button
            >
            <el-button type="text" size="small" @click="editfn(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="text"
              style="color: red"
              size="small"
              @click="deletefn(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <div style="text-align: right; margin-top: 20px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="form.pageNum"
          :page-sizes="[10, 20, 30, 50, 100]"
          :page-size="form.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>

    <!-- 新增/删除 -->
    <el-dialog :visible.sync="dialogVisible" @close="onClose" title="执行任务">
      <el-form
        ref="elForm"
        :model="formData"
        :rules="rules"
        size="medium"
        label-width="120px"
        width="666px"
      >
        <el-form-item label="steam帐号" prop="steamAccount">
          <el-input
            v-model="formData.steamAccount"
            placeholder="请输入steam帐号"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="邮箱链接" prop="steamEmailReq">
          <el-input
            v-model="formData.steamEmailReq"
            placeholder="请输入邮箱链接"
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="steamEmail">
          <el-input
            v-model="formData.steamEmail"
            placeholder="请输入邮箱"
          ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="steamPhone">
          <el-input
            v-model="formData.steamPhone"
            placeholder="请输入手机号"
          ></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="">
          <el-input
            v-model="formData.remark"
            placeholder="请输入备注"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="onClose">取消</el-button>
        <el-button type="primary" @click="handelConfirm">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getList,
  daochurenwu,
  getDetail,
  addList,
  editList,
  backList,
  againRules,
  deleteList,
} from "@/api/steam/index";
export default {
  data() {
    return {
      dialogVisible: false,
      form: {
        pageNum: 1,
        pageSize: 10,
        steamAccount: null,
        status: null,
        createTimeStart: null,
        createTimeEnd: null,
      },
      timer: [],
      formData: {
        steamAccount: null,
        steamEmailReq: null,
        steamEmail: null,
        steamPhone: null,
        remark: null,
      },
      rules: {
        steamAccount: [
          {
            required: true,
            message: "请输入steam帐号",
            trigger: "blur",
          },
        ],
        steamEmailReq: [
          {
            required: true,
            message: "请输入steam邮箱链接",
            trigger: "blur",
          },
        ],
        steamEmail: [
          {
            required: true,
            message: "请输入steam邮箱",
            trigger: "blur",
          },
        ],
        steamPhone: [
          {
            required: true,
            message: "请输入steam手机号",
            trigger: "blur",
          },
        ],
      },
      list: [],
      total: 0,
      typeArr: [
        { name: "正常", id: 1 },
        { name: "异常", id: 2 },
        { name: "回收", id: 3 },
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    async getList() {
      if (this.timer && this.timer.length == 2) {
        this.form.createTimeStart = this.timer[0] + " 00:00:00";
        this.form.createTimeEnd = this.timer[1] + " 23:59:59";
      } else {
        this.form.createTimeStart = "";
        this.form.createTimeEnd = "";
      }
      const res = await getList(this.form);
      this.list = res.rows;
      this.total = res.total;
    },
    chongzhi() {
      this.form = {
        pageNum: 1,
        pageSize: 10,
        steamAccount: null,
        status: null,
        createTimeStart: null,
        createTimeEnd: null,
      };
      this.getList();
    },
    addfn() {
      this.dialogVisible = true;
    },
    onClose() {
      this.dialogVisible = false;
      this.formData = {
        steamAccount: null,
        steamEmailReq: null,
        steamEmail: null,
        steamPhone: null,
        remark: null,
      };
    },
    //编辑
    async editfn(item) {
      const res = await getDetail(item.id);
      this.formData = res.data;
      this.dialogVisible = true;
    },
    //删除
    deletefn(item) {
      this.$confirm("此操作将永久删除该条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await deleteList(item.id);
          this.getList();
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    handelConfirm() {
      this.$refs["elForm"].validate(async (valid) => {
        if (!valid) return;
        if (this.formData.id) {
          const res = await editList(this.formData);
          console.log("res", res);
        } else {
          const res = await addList(this.formData);
          console.log("res", res);
        }
        this.$message.success("操作成功");
        this.getList();
        this.onClose();
      });
    },
    //回收
    handleback(item) {
      this.$confirm("此操作会将该条数据转为回收, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await backList({ id: item.id });
          this.getList();
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作",
          });
        });
    },
    //重新校验
    handleClickAgain(item) {
      this.$confirm("此操作会将该条数据重新校验, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await againRules({ id: item.id });
          this.getList();
          this.$message({
            type: "success",
            message: "操作成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作",
          });
        });
    },
    importExl() {
      this.$modal
        .confirm("是否确认导出所有数据？")
        .then(() => {
          this.exportLoading = true;
          return daochurenwu(this.form);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.form.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.form.pageNum = val;
      this.getList();
    },
  },
};
</script>

<style>
</style>