<template>
  <el-dialog :title="'账单类型'" :visible.sync="dialogVisible" :close-on-click-modal="false" width="800px">
    <div class="form-box" v-loading="loading">
      <div v-for="(item, ind) in list" :key="item.id" class="lin-item">
        <span class="tit">账单类型：</span>
        <el-input style="width:250px" placeholder="请输入内容" v-model="item.rname" clearable
          :disabled="curId != item.id"></el-input>
        <span class="tit">收支类型：</span>
        <el-select v-model="item.iotype" placeholder="请选择" :disabled="curId != item.id">
          <el-option v-for="item2 in type" :key="item2.value" :label="item2.name" :value="item2.value"></el-option>
        </el-select>
        <el-button class="btn" type="text" v-show="curId != item.id && !curId && item.rname != '自售' && item.rname != '平台销售'"
          @click="edit(item)">编辑</el-button>
        <el-button class="btn" type="text" v-show="curId == item.id" @click="sure(item)">确认</el-button>
        <el-button class="btn" type="text" v-show="curId == item.id" @click="cancel(item, ind)">取消</el-button>
        <el-button class="btn" type="text" style="color:red"
          v-show="curId != item.id && !curId && item.rname != '自售' && item.rname != '平台销售'" @click="del(item)">删除</el-button>
        <div class="childs-box" v-show="curId == item.id">
          <el-table :data="config" border style="width: 80%">
            <el-table-column prop="date" label="字段名称">
              <template slot-scope="scope">
                <el-input placeholder="请输入内容" v-model="scope.row.name" clearable :disabled="scope.row.id==1||scope.row.id==4"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="是否启用" width="180">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.isShow" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
              </template>
            </el-table-column>
            <el-table-column prop="address" label="是否必填">
              <template slot-scope="scope">
                <el-switch v-model="scope.row.isMust" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <div style="text-align:center">
        <el-button class="btn" type="text" icon="el-icon-plus" @click="add">新增</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import financeApi from "@/api/financeCenter/index.js";
export default {
  name: "typeConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      type: [
        {
          name: "收入",
          value: "2"
        },
        {
          name: "支出",
          value: "1"
        }
      ],
      initConfig: [
        {
          key: "field1",
          name: "账号",
          id: 1,
          isShow: false,
          isMust: false
        },
        {
          key: "field2",
          name: "自定义字段2",
          id: 2,
          isShow: false,
          isMust: false
        },
        {
          key: "field3",
          name: "自定义字段3",
          id: 3,
          isShow: false,
          isMust: false
        },
        {
          key: "field4",
          name: "平台编号",
          id: 4,
          isShow: false,
          isMust: false
        },
        {
          key: "field5",
          name: "自定义字段5",
          id: 5,
          isShow: false,
          isMust: false
        },
        {
          key: "field6",
          name: "自定义字段6",
          id: 6,
          isShow: false,
          isMust: false
        }
      ],
      config: [],
      list: [],
      curId: "",
      isAdd: false,
      rules: {}
    };
  },
  created() { },
  watch: {
    curId(val) {
      let obj = this.list[this.list.length - 1];
      if (!obj.id && val) {
        // 去掉新增但没保存的项
        this.list.splice(this.list.length - 1, 1);
        this.isAdd = false;
      }
    }
  },
  methods: {
    // 打开弹窗
    open(flag, data) {
      this.dialogVisible = true;
      this.getList();
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    // 获取列表
    getList() {
      this.loading = true;
      financeApi.getBillType().then(res => {
        this.list = res.rows;
        this.loading = false;
      });
    },
    edit(item) {
      this.curId = item.id;
      if (item.filids) {
        this.config = JSON.parse(item.filids);
      } else {
        this.config = JSON.parse(JSON.stringify(this.initConfig));
      }
    },
    del(item) {
      this.$confirm("此操作将永久删除该选项, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          // 删除
          financeApi.delType(item.id).then(res => {
            this.curId = null;
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getList();
          });
        })
        .catch(() => { });
    },
    sure(item) {
      this.isAdd = false;
      if (!item.id) {
        // 新增
        item.filids = JSON.stringify(this.config);
        financeApi.addType(item).then(res => {
          this.curId = null;
          this.$message.success("新增成功");
          this.getList();
          this.config = [];
        });
      } else {
        // 编辑
        item.filids = JSON.stringify(this.config);
        financeApi.editType(item).then(res => {
          this.$message.success("编辑成功");
          this.curId = null;
          this.getList();
          this.config = [];
        });
      }
    },
    //取消
    cancel(item, ind) {
      this.curId = null;
      if (!item.id) {
        this.list.splice(ind, 1);
        this.isAdd = false;
      }
    },
    add() {
      if (!this.isAdd) {
        this.isAdd = true;
        this.list.push({
          rname: "",
          iotype: "2"
        });
        this.config = JSON.parse(JSON.stringify(this.initConfig));
        this.curId = undefined;
      } else {
        this.$alert("请先完成上一组的添加！");
        return;
      }
    }
  }
};
</script>
<style scoped lang="scss">
.form-box {
  min-height: 600px;
}

.lin-item {
  // border: 1px solid #ccc;
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 10px;
  box-shadow: 0 0 7px 0 #ccc;

  .tit {
    font-weight: 600;
    margin-left: 20px;
  }

  .btn {
    margin-left: 10px;
  }
}

.childs-box {
  margin-top: 10px;
  margin-left: 20px;
}

.lin-item ::v-deep .el-table .el-table__cell {
  padding: 0 !important;
}
</style>
