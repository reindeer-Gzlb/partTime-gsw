<template>
  <div>
    <el-card>
      <el-form
        :inline="true"
        :model="formInline"
        class="demo-form-inline"
        label-width="100px"
      >
        <el-form-item label="编号">
          <el-input
            style="width: 250px"
            v-model="formInline.code"
            placeholder="请输入编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="微信号">
          <el-input
            style="width: 250px"
            v-model="formInline.vx"
            placeholder="请输入微信号"
          ></el-input>
        </el-form-item>
        <el-form-item label="回收日期" prop="">
          <el-date-picker
            style="width: 300px"
            v-model="timer1"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="打款日期" prop="">
          <el-date-picker
            style="width: 300px"
            v-model="timer"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList()">搜索</el-button>
          <el-button type="info" @click="chongzhi">重置</el-button>
          <el-button type="primary" @click="addfn">新增</el-button>
          <el-button
            type="primary"
            style="background-color: orange; border: 1px solid orange"
            @click="daochu"
            ><i class="el-icon-download"></i>导出</el-button
          >
        </el-form-item>
      </el-form>
      <div>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="code" label="编号" width="100" align="center">
          </el-table-column>
          <el-table-column
            prop="recoveryTime"
            label="回收日期"
            width="180"
            align="center"
          >
          </el-table-column>
          <el-table-column prop="price" label="回收价格" align="center">
          </el-table-column>
          <el-table-column prop="vx" label="微信号" align="center">
          </el-table-column>
          <el-table-column prop="payTime" label="打款日期" align="center">
          </el-table-column>
          <el-table-column prop="remark" label="备注" align="center">
          </el-table-column>
          <el-table-column label="操作">
            <template #default="{ row }">
              <el-button type="text" @click="edit(row)">修改</el-button>
              <el-button type="text" style="color: red" @click="delfn(row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="text-align: right; margin-top: 20px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="formInline.pageNum"
          :page-sizes="[2, 10, 20, 30, 50]"
          :page-size="formInline.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>
    <!-- 添加或删除 -->
    <el-dialog
      title="回收管理"
      :visible.sync="dialogFormVisible"
      @close="close"
      width="500px"
    >
      <el-form :model="form" :rules="rules" ref="formName" label-width="81px">
        <el-form-item label="编号" prop="code">
          <el-input
            style="width: 250px"
            v-model="form.code"
            autocomplete="off"
            placeholder="请输入编号"
          ></el-input>
        </el-form-item>
        <el-form-item label="微信号" prop="vx">
          <el-input
            style="width: 250px"
            v-model="form.vx"
            autocomplete="off"
            placeholder="请输入微信号"
          ></el-input>
        </el-form-item>
        <el-form-item label="回收价格" prop="price">
          <el-input
            style="width: 250px"
            v-model="form.price"
            autocomplete="off"
            placeholder="请输入回收价格"
          ></el-input>
        </el-form-item>
        <el-form-item label="回收日期" prop="recoveryTime" class="">
          <el-date-picker
            style="width: 250px"
            v-model="form.recoveryTime"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="打款日期" prop="payTime" class="">
          <el-date-picker
            style="width: 250px"
            v-model="form.payTime"
            type="datetime"
            placeholder="选择日期时间"
            format="yyyy-MM-dd HH:mm:ss"
            value-format="yyyy-MM-dd HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            style="width: 333px"
            type="textarea"
            v-model="form.remark"
            autocomplete="off"
            placeholder="请输入备注"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="sureOk">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import { getList, addList, getoneListdetail, editList, deleteList, daochuList } from '../../api/recyclingmanagement/indexNew'
export default {
  data() {
    return {
      formInline: {
        pageSize: 10,
        pageNum: 1,
        code: null,
        vx: null,
        payTimeStart: null,
        payTimeEnd: null,
        recoveryTimeStart: null,
        recoveryTimeEnd: null
      },
      timer: [],
      timer1: [],
      form: {
        payTime: null,
        vx: null,
        price: null,
        recoveryTime: null,
        code: null,
        remark: null
      },
      dialogFormVisible: false,
      rules: {
        code: [
          { required: true, message: '请输入编号', trigger: 'blur' }
        ],
        payTime: [
          { required: true, message: '请选择打款日期', trigger: 'change' }
        ],
        vx: [
          { required: true, message: '请输入微信号', trigger: 'change' }
        ],
        price: [
          { required: true, message: '请输入回收价格', trigger: 'change' }
        ],
        recoveryTime: [
          { required: true, message: '请选择回收日期', trigger: 'change' }
        ],
        remark: [
          { required: true, message: '请输入备注', trigger: 'blur' }
        ]
      },
      total: 0,
      tableData: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //关闭
    close() {
      this.dialogFormVisible = false
      this.form = {
        payTime: null,
        vx: null,
        price: null,
        recoveryTime: null,
        code: null,
        remark: null
      }
    },
    //重置
    chongzhi() {
      this.formInline = {
        pageSize: 10,
        pageNum: 1,
        code: null,
        vx: null,
        createTimeStart: null,
        createTimeEnd: null
      }
      this.timer = []
      this.timer1 = []
      this.getList()
    },
    //获取列表
    async getList() {
      if (this.timer && this.timer.length == 2) {
        this.formInline.payTimeStart = this.timer[0] + " 00:00:00";
        this.formInline.payTimeEnd = this.timer[1] + " 23:59:59";
      } else {
        this.formInline.payTimeStart = "";
        this.formInline.payTimeEnd = "";
      }

      if (this.timer1 && this.timer1.length == 2) {
        this.formInline.recoveryTimeStart = this.timer1[0] + " 00:00:00";
        this.formInline.recoveryTimeEnd = this.timer1[1] + " 23:59:59";
      } else {
        this.formInline.recoveryTimeStart = "";
        this.formInline.recoveryTimeEnd = "";
      }
      const res = await getList(this.formInline)
      console.log(res);
      this.tableData = res.rows
      this.total = res.total
    },
    //新增
    addfn() {
      this.dialogFormVisible = true
    },
    //提交
    sureOk() {
      this.$refs.formName.validate(async (valid) => {
        if (valid) {
          // alert('submit!');
          if (this.form.id) {
            const res = await editList(this.form)
            console.log(res);
          } else {
            const res = await addList(this.form)
            console.log(res);
          }
          this.$message.success('操作成功')
          this.getList()
          this.dialogFormVisible = false
        }
      });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.formInline.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.formInline.pageNum = val
      this.getList()
    },
    //回显
    async edit(item) {
      this.dialogFormVisible = true
      const res = await getoneListdetail(item.id)
      console.log(res);
      this.form = res.data
    },
    //删除
    delfn(item) {
      this.$prompt('请输入删除密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S+/,
        inputErrorMessage: '请输入密码'
      }).then(({ value }) => {
        deleteList(item.id, value).then(res => {
          this.getList()
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
      // this.$confirm('此操作将永久删除该条数据, 是否继续?', '温馨提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // }).then(async () => {
      //   const res = await deleteList(item.id)
      //   console.log(res);
      //   this.getList()
      //   this.$message({
      //     type: 'success',
      //     message: '删除成功!'
      //   });
      // }).catch(() => {
      //   this.$message({
      //     type: 'info',
      //     message: '已取消删除'
      //   });
      // });
    },
    // 导出
    daochu() {
      daochuList(this.formInline).then(res => {
        this.$download.name(res.msg);
      })
    },
  }
}
  </script>
  
  <style scoped lang="scss">
</style>