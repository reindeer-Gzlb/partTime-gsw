<template>
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
        @keyup.enter.native="search">
        <el-form-item label="游戏名称" prop="name">
          <el-input v-model="queryParams.name" placeholder='游戏名称'></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="search" class="zdy-btn">搜索</el-button>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-plus" type="primary" size="mini" @click="addACount"
            class="zdy-btn zdy-btn-refresh">新增</el-button>
        </el-form-item>
      </el-form>
      <div>
        <div class="tip">注意：已使用的属性不要随意删除或修改，以免旧商品数据匹配不到属性，造成不必要的麻烦。</div>
        <el-table v-if="tableData.records && tableData.records.length" :data="tableData.records"
          height="calc(100vh - 200px)" style="width: 100%">
          <el-table-column label="序号" width="80" label-position="left">
            <template slot-scope="scope">
              <span>{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column label="游戏名称" prop="name" />
          <el-table-column label="游戏类型" prop="type">
            <template slot-scope="scope">
              <span>{{ scope.row.type == 1 ? '端游' : '手游' }}</span>
            </template>
          </el-table-column>
          <el-table-column label="游戏图标">
            <template slot-scope="scope">
              <img :src="scope.row.icon" alt="" style="width: 80px;">
            </template>
          </el-table-column>
          <el-table-column label="排序" prop="sort" />
          <el-table-column label="启用状态" align="center" prop="state">
            <template slot-scope="scope">
              <el-switch active-color="#13ce66" @change="stateChange($event, scope.row)" inactive-color="#ff4949"
                :active-value="0" :inactive-value="1" v-model="scope.row.state"></el-switch>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="170">
            <template slot-scope="scope">
              <el-button type="text" size="mini" @click="editItem(scope.row)">编辑</el-button>
              <el-button type="text" style="color: red" size="mini" @click="opentypeConfig(scope.row)">属性配置</el-button>
              <el-button type="text" style="color: red" size="mini" @click="del(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>

    <!-- 一下是弹出框 -->
    <addDialog ref="addDialog"></addDialog>
    <configDialog ref="configDialog"></configDialog>
  </div>
</template>

<script>
import gameListApi from "@/api/gameConfig/gameList"
import addDialog from "./add.vue";
import configDialog from "./typeConfig.vue";
import { getDicts } from "@/api/system/dict/data";
export default {
  name: "gameConfig",
  dicts: ["game_type"],
  components: { configDialog, addDialog },
  data() {
    return {
      tableData: {
        records: [],
        total: 0
      },
      loading: false,
      queryParams: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 获取列表
    getList(userId) {
      gameListApi.getGameList(this.queryParams).then(res => {
        if (res.code == 200) {
          this.tableData.records = res.rows
        }
      });
    },
    // 搜索
    search() {
      this.getList();
    },
    // 打开配置弹窗
    opentypeConfig(row) {
      this.$refs.configDialog.open(row);
    },
    // 删除
    del(row) {
      this.$modal
        .confirm(
          "是否确认删除" + row.name + '"？'
        )
        .then(() => {
          gameListApi.delGame(row.id).then(res => {
            this.$modal.msgSuccess(res.msg);
            this.search()
          })
        })
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    // 编辑
    editItem(row) {
      this.$refs.addDialog.open("edit", row);
    },
    // 启用状态改变
    stateChange(e,row){
      // 编辑
      gameListApi.editGame(row).then(res => {
              if (res.code == 200) {
                this.$message.success(res.msg);
                this.getList();
              }
            });
    },
    // //重置
    // resetQuery() {
    //   this.queryParams = {
    //     pageNum: 1,
    //     pageSize: 20,
    //     billType: "",
    //     remark: "",
    //     sort: "descending",
    //     sortColumn: "createTime",
    //     startTime: '',
    //     endTime: ''
    //   }
    //   this.getList();
    // },

  }
};
</script>
<style lang="scss" scoped></style>
