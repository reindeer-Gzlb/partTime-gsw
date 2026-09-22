<template>
  <el-dialog
    :title="'游戏配置'"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="1300px"
    v-loading="loading"
  >
    <div class="form-box">
      <h3>游戏：{{ gameName }}</h3>
      <div class="tip">
        自定义key作为系统特定字段的key，不可随意填写。填写规则如下（未标出的可不填）：<br />
        1.实名状态：sm
      </div>

      <el-table :data="config" border style="width: 100%">
        <el-table-column prop="fieldName" label="字段名称">
          <template slot-scope="scope">
            <el-input
              placeholder="请输入内容"
              v-model="scope.row.fieldName"
              clearable
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="aavName" label="字段key" width="80">
          <template slot-scope="scope">
            <el-input
              placeholder="请输入内容"
              v-model="scope.row.aavName"
              disabled
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="fieldKey" label="自定义key" width="100">
          <template slot-scope="scope">
            <el-input
              placeholder="请输入内容"
              v-model="scope.row.fieldKey"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column prop="fieldType" label="字段类型">
          <template slot-scope="scope">
            <el-select
              v-model="scope.row.fieldType"
              placeholder="请选择"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            >
              <el-option
                v-for="item2 in type"
                :key="item2.value"
                :label="item2.name"
                :value="item2.value"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="attributeId" label="绑定属性">
          <template slot-scope="scope">
            <el-select
              clearable
              v-model="scope.row.attributeId"
              placeholder="请选择"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
              @change="attrChange($event, scope.row)"
            >
              <el-option
                v-for="item2 in attrs"
                :key="item2.id"
                :label="item2.fieldName"
                :value="item2.id"
              ></el-option>
            </el-select>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="sort" label="排序" width="100">
          <template slot-scope="scope">
            <el-input-number placeholder="请输入" v-model="scope.row.sort" :disabled="curId != scope.row.id && !(curId == 'add' && !scope.row.id)"></el-input-number>
          </template>
        </el-table-column> -->
        <el-table-column prop="sort" label="排序" width="150">
          <template slot-scope="scope">
            <el-input-number
              placeholder="请输入内容"
              v-model="scope.row.sort"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-input-number>
          </template>
        </el-table-column>
        <el-table-column prop="isEnable" label="是否启用" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isEnable"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="must" label="是否必填" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.must"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="true"
              inactive-value="false"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="isSearch" label="搜索条件" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.isSearch"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="2"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="hsisShow" label="回收页面" width="100">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.hsisShow"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="1"
              inactive-value="2"
              :disabled="
                curId != scope.row.id && !(curId == 'add' && !scope.row.id)
              "
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="" label="操作" width="120">
          <template slot-scope="scope">
            <el-button
              class="btn"
              type="text"
              v-show="scope.row.id && curId != scope.row.id"
              @click="edit(scope.row)"
              >编辑</el-button
            >
            <el-button
              class="btn"
              type="text"
              v-show="
                curId == scope.row.id || (curId == 'add' && !scope.row.id)
              "
              @click="sure(scope.row)"
              >确认</el-button
            >
            <el-button
              class="btn"
              type="text"
              v-show="
                curId == scope.row.id || (curId == 'add' && !scope.row.id)
              "
              @click="cancel(scope.row, scope.$index)"
              >取消</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: center">
        <el-button class="btn" type="text" icon="el-icon-plus" @click="add"
          >新增</el-button
        >
      </div>
    </div>
  </el-dialog>
</template>

<script>
import gameConfigApi from "../../../api/gameConfig";
export default {
  name: "typeConfig",
  data() {
    return {
      dialogVisible: false,
      loading: false,
      type: [
        {
          name: "输入框",
          value: "input"
        },
        {
          name: "多选",
          value: "checkbox"
        },
        {
          name: "单选",
          value: "radio"
        },
        {
          name: "数值",
          value: "number"
        },
        {
          name: "日期/时间",
          value: "date"
        },
        {
          name: "多选>单选",
          value: "boxradio"
        }
      ],
      config: [],
      list: [],
      curId: "",
      isAdd: false,
      rules: {},
      gameName: '',
      gameId: '',
      attrs: []
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
    open(data) {
      this.gameName = data.name
      this.gameId = data.id
      this.dialogVisible = true;
      this.isAdd = false;
      this.getList();
      this.getAttrList()
    },
    // 获取游戏属性列表
    getAttrList() {
      // type="王者荣耀" parentId=0
      let obj = {
        gameId: this.gameId,
        parentId: 0
      }
      gameConfigApi.getAttrList(obj).then(res => {
        console.log("%c Line:144 🥑 getAttrListres", "color:#f5ce50", res);
        if (res.code == 200) {
          this.attrs = res.rows
        }
      })
    },
    // 绑定属性改变
    attrChange(e, row) {
      if (!row.fieldName) {
        this.attrs.forEach(r => {
          if (r.id == e) {
            row.fieldName = r.fieldName
          }
        })
      }
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    // 获取列表
    getList() {
      this.loading = true;
      gameConfigApi.getGameConfig({ gameId: this.gameId }).then(res => {
        this.config = res.rows;
        this.loading = false;
      });
    },
    edit(item) {
      if (this.curId) {
        return this.$message({
          type: 'error',
          message: '请先确认正在编辑的条目'
        })
      }
      this.curId = item.id;
      // if (item.filids) {
      //   this.config = JSON.parse(item.filids);
      // } else {
      //   this.config = JSON.parse(JSON.stringify(this.initConfig));
      // }
    },
    // del(item) {
    //   this.$confirm("此操作将永久删除该选项, 是否继续?", "提示", {
    //     confirmButtonText: "确定",
    //     cancelButtonText: "取消",
    //     type: "warning"
    //   })
    //     .then(() => {
    //       // 删除
    //       financeApi.delType(item.id).then(res => {
    //         this.curId = null;
    //         this.$message({
    //           type: "success",
    //           message: "删除成功!"
    //         });
    //         this.getList();
    //       });
    //     })
    //     .catch(() => { });
    // },
    sure(item) {
      if (!item.fieldName) {
        return this.$message({
          type: 'error',
          message: '请填写字段名称'
        })
      }
      this.isAdd = false;
      this.curId = null;
      if (!item.id) {
        // 新增
        this.loading = true
        gameConfigApi.addGameConfig(item).then(res => {
          this.$message.success("新增成功");
          this.getList();
        });
      } else {
        // 编辑
        this.loading = false
        gameConfigApi.editGameConfig(item).then(res => {
          this.$message.success("编辑成功");
          this.getList();
        });
      }
    },
    //取消
    cancel(item, ind) {
      this.curId = null;
      if (!item.id) {
        this.config.splice(ind, 1);
        this.isAdd = false;
      }
      this.getList()
    },
    add() {
      if (!this.isAdd) {
        let newKey = ''
        if (this.config.length) {
          let arr = []
          this.config.forEach(r => {
            arr.push(Number(r.aavName.replace('f', '')))
          })
          let key = Math.max(...arr)
          newKey = key + 1
          if (newKey > 20) {
            return this.$message({
              type: 'error',
              message: '字段最多添加20个'
            })
          }
        } else {
          newKey = 1
        }
        this.isAdd = true;
        this.config.push({
          aavName: 'f' + newKey,
          gameId: this.gameId,
          gameName: this.gameName,
          fieldType: 'input',
          fieldName: ''
        })
        this.curId = 'add'
      } else {
        this.$alert("请先完成上一组的添加！");
        return;
      }
    }
  }
};
</script>
<style scoped lang="scss">
.tip {
  color: red;
}
.form-box {
  height: 700px;
  overflow-y: auto;
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
::v-deep .el-input-number--medium {
  width: 100% !important;
}
::v-deep .el-input-number {
  width: 100%;
}
</style>
