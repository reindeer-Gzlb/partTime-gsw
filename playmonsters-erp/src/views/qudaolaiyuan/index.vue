<template>
  <div>
    <el-card>
      <el-form
        :inline="true"
        :model="formInline"
        class="demo-form-inline"
        label-width="100px"
      >
        <el-form-item label="类型">
          <el-select v-model="formInline.type" placeholder="请选择类型">
            <el-option
              v-for="item in payType"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList()">搜索</el-button>
          <el-button type="info" @click="chongzhi">重置</el-button>
          <el-button type="primary" @click="addfn">新增</el-button>
        </el-form-item>
      </el-form>
      <div>
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="id" label="编号" width="100" align="center">
          </el-table-column>
          <el-table-column prop="name" label="渠道名称" align="center">
          </el-table-column>
          <el-table-column label="类型" align="center">
            <template #default="{ row }">
              <div style="color: green" v-if="row.type == 2">收入</div>
              <div style="color: red" v-if="row.type == 1">支出</div>
            </template>
          </el-table-column>
          <el-table-column prop="sort" label="排序" align="center">
          </el-table-column>
          <el-table-column prop="createTime" label="创建时间" align="center">
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
      title="渠道管理"
      :visible.sync="dialogFormVisible"
      @close="close"
      width="500px"
    >
      <el-form :model="form" :rules="rules" ref="formName" label-width="81px">
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="item in payType"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="渠道名称" prop="name">
          <el-input
            style="width: 250px"
            v-model="form.name"
            autocomplete="off"
            placeholder="请输入渠道名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="排序" prop="">
          <el-input
            style="width: 250px"
            v-model="form.sort"
            placeholder="请输入排序"
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
import { getList, addList, getoneListdetail, editList, deleteList, daochuList } from '../../api/qudaolaiyuan/index'
export default {
  data() {
    return {
      formInline: {
        pageSize: 10,
        pageNum: 1,
        type: null
      },
      form: {
        name: null,
        sort: null,
        type: null
      },
      dialogFormVisible: false,
      rules: {
        type: [
          { required: true, message: '请选择类型', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入渠道名称', trigger: 'change' }
        ]
      },
      total: 0,
      tableData: [],
      payType: [
        { name: '支出', id: 1 },
        { name: '收入', id: 2 }
      ]
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
        name: null,
        sort: null,
        type: null
      }
    },
    //重置
    chongzhi() {
      this.formInline = {
        pageSize: 10,
        pageNum: 1,
        type: null
      }
      this.getList()
    },
    //获取列表
    async getList() {
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
      //   this.$prompt('请输入删除密码', '提示', {
      //     confirmButtonText: '确定',
      //     cancelButtonText: '取消',
      //     inputPattern: /\S+/,
      //     inputErrorMessage: '请输入密码'
      //   }).then(({ value }) => {
      //     deleteList(item.id, value).then(res => {
      //       this.getList()
      //       this.$message({
      //         type: "success",
      //         message: "删除成功!"
      //       });
      //     });
      //   }).catch(() => {
      //     this.$message({
      //       type: 'info',
      //       message: '取消输入'
      //     });
      //   });

      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteList(item.id).then(res => {
          this.getList()
          this.$message({
            type: "success",
            message: "删除成功!"
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
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