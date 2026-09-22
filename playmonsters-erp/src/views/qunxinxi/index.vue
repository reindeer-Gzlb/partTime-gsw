<template>
  <div class="main">
    <el-card>
      <el-form :inline="true" :model="form" class="demo-form-inline">
        <el-form-item label="群id">
          <el-input v-model="form.id" placeholder="请输入群id"></el-input>
        </el-form-item>
        <el-form-item label="群编号">
          <el-input v-model="form.toGroup" placeholder="群编号"></el-input>
        </el-form-item>
        <el-form-item label="群名称">
          <el-input
            v-model="form.groupName"
            placeholder="请输入群名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="群简介">
          <el-input
            v-model="form.groupDesc"
            placeholder="请输入群简介"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList()">搜索</el-button>
          <el-button type="info" @click="chongzhi" plain>重置</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格 -->
      <el-table border :data="list" style="width: 100%">
        <el-table-column prop="id" label="群id" width="100"> </el-table-column>
        <el-table-column prop="toGroup" label="群编号" width="166">
        </el-table-column>
        <el-table-column prop="groupName" label="群名称" width="399">
        </el-table-column>
        <el-table-column prop="groupDesc" label="群简介" width="500">
        </el-table-column>
        <el-table-column prop="announcement" label="群公告" width="200">
        </el-table-column>
        <el-table-column prop="numbers" label="群成员" width="366">
        </el-table-column>
        <el-table-column prop="del" label="群状态" width="100">
          <template #default="{ row }">
            <el-button
              v-if="row.del === 2"
              size="mini"
              type="danger"
              plain
              disabled
              >解散</el-button
            >
            <el-button
              v-if="row.del === 1"
              size="mini"
              type="success"
              plain
              disabled
              >正常</el-button
            >
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="群创建时间" width="200">
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button v-if="row.del === 1" type="text" @click="changefn(row)"
              >修改</el-button
            >
            <el-button
              v-if="row.del === 1"
              type="text"
              style="color: red"
              @click="detaleId(row)"
              >解散</el-button
            >
            <el-button
              v-if="row.del === 1"
              type="text"
              style="color: #7a6400"
              @click="chakanjilu(row)"
              >历史聊天记录</el-button
            >
            <el-button
              v-if="row.del === 2"
              type="text"
              style="color: green"
              @click="huifu(row)"
              >恢复</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align: right; margin-top: 20px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20, 30, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        >
        </el-pagination>
      </div>
    </el-card>

    <!-- 编辑弹出层 -->
    <el-dialog
      title="修改"
      :visible.sync="dialogVisible"
      width="40%"
      :before-close="handleClose"
    >
      <!-- <span>这是一段信息</span> -->
      <el-form :model="bianji" label-width="80px">
        <el-form-item label="群编号">
          <el-input
            v-model="bianji.groupId"
            placeholder="请输入群编号"
            disabled
          ></el-input>
        </el-form-item>
        <el-form-item label="群名称">
          <el-input
            v-model="bianji.groupname"
            placeholder="请输入群名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="群简介">
          <el-input
            v-model="bianji.description"
            placeholder="请输入群简介"
          ></el-input>
        </el-form-item>
        <el-form-item label=" 群公告">
          <el-input
            v-model="bianji.announcement"
            placeholder="请输入群公告"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editShuju">确 定</el-button>
      </span>
    </el-dialog>

    <MsgHistroy :showDialog="showDialog" @close="guanbi" :sessionId="toGroups"/>
  </div>
</template>

<script>
import qunxinxiApi from "../../api/qunxinxi";
import MsgHistroy from './msgHistory/index.vue'
export default {
  components:{
    MsgHistroy
  },
  data() {
    return {
      showDialog:false,
      keyword: "",
      pageSize: 10,
      pageNum: 1,
      pageSizes: 5,
      pageNums: 1,
      form: {
        id: "",
        groupDesc: "",
        groupName: "",
        toGroup: "",
      },
      bianji: {
        groupId: "",
        groupname: "",
        description: "",
        announcement: "",
      },
      list: [],
      liaotianList: [],
      total: 0,
      ltTotal: 0,
      dialogVisible: false,
      dialogVisibless: false,
      toGroups: "",
      viewImgUrls: "",
      type:''
    };
  },
  created() {
    this.getList();
  },
  computed: {},
  //监听消息列表滚动条，滚动顶部的时候获取上一页
  // watch() {
  //     (newVal, oldVal) => {
  //       console.log("%c Line:103 🍪 newVal", "color:#ed9ec7", newVal.value);
  //       if (newVal.value == 0) {
  //         // 请求上一页
  //         this.prePage();
  //       }
  //     },
  //     {
  //       immediate: true,
  //       deep: true,
  //     };
  // },
  methods: {
    //guanbi
    guanbi(){
     this.showDialog = false
    },
    // 上一页
    // prePage() {
    //   console.log("%c Line:108 🍿 滚动到顶^^^^^^^^^", "color:#42b983");
    //   let firstId;
    //   if (this.liaotianList && this.liaotianList.length) {
    //     firstId = this.liaotianList[0].id;
    //   }
    //   this.getLiaotian(firstId, 1, 20);
    // },
    // // 下一页
    // nextPage() {
    //   // 最后一个消息id
    //   // 滚动到底部
    //   console.log("%c Line:108 🍿 滚动到底部", "color:#42b983");
    //   if (this.liaotianList && this.liaotianList.length) {
    //     let lastId = this.liaotianList[this.liaotianList.length - 1].id;
    //     this.getLiaotian(lastId, 2, 20);
    //   }
    // },
    //聊天记录
    async chakanjilu(row) {
      console.log(row.toGroup);
      this.toGroups = row.toGroup;
      this.showDialog = true;
      // this.getLiaotian();
    },
    //获取聊天记录信息
    // async getLiaotian(id, type, limit) {
    //   const res = await getHistoryMessageCustom(this.toGroups, id, type, limit);
    //   console.log(res);
    //   // this.liaotianList = []
    //   this.liaotianList = res.rows;
    // },
    //编辑弹出层确认按钮
    async editShuju() {
      const res = await qunxinxiApi.editWord(this.bianji);
      console.log(res);
      this.dialogVisible = false;
      this.getList();
      this.$message({
        type: "success",
        message: "编辑成功!",
      });
    },
    //编辑弹层
    changefn(row) {
      this.dialogVisible = true;
      this.bianji.groupId = row.toGroup;
      this.bianji.groupname = row.groupName;
      this.bianji.description = row.groupDesc;
      this.bianji.announcement = row.announcement;
    },
    //编辑弹层关闭
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    //解散
    detaleId(row) {
      this.$confirm("此操作将解散当前聊天群, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await qunxinxiApi.delWord(row.id);
          console.log(res);
          this.getList();
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取删除",
          });
        });
    },
    //恢复
    huifu(row) {
      console.log(row.id);
      this.$confirm("此操作将恢复当前这条数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await qunxinxiApi.huifuId(row.id);
          console.log(res);
          this.getList();
          this.$message({
            type: "success",
            message: "恢复成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取恢复",
          });
        });
    },
    //分页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.getList();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageNum = val;
      this.getList();
    },
    //聊天记录分页
    handleSizeChanges(val) {
      console.log(`每页 ${val} 条`);
      this.pageSizes = val;
      this.getLiaotian();
    },
    handleCurrentChanges(val) {
      console.log(`当前页: ${val}`);
      this.pageNums = val;
      this.getLiaotian();
    },
    //重置
    chongzhi() {
      this.form = {
        pageSize: 10,
        pageNum: 1,
        id: "",
        groupDesc: "",
        groupName: "",
        toGroup: "",
      };

      this.getList();
    },
    //获取列表
    async getList() {
      const res = await qunxinxiApi.getList({
        pageSize: this.pageSize,
        pageNum: this.pageNum,
        ...this.form,
      });
      console.log(res);
      //   res.rows.map(item => {
      //     item.numbers = JSON.parse(item.numbers)
      //   })
      this.list = res.rows;
      this.total = res.total;
      console.log(this.list);
    },
  },
};
</script>

<style lang="scss" scoped>
.main {
  padding: 10px;
}

.search_box {
  display: flex;
  // margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;

  .btn {
    margin-left: 10px;
  }
}

.msg_history_box {
  padding: 10px;
  padding-top: 10px;
  width: 100%;
  height: calc(100vh - 400px);
  overflow-y: auto;
  // border: 1px solid #e0dfdf;
  box-sizing: border-box;
  // background-color: #fff;
  border-top: 3px solid #79bbff;
  background-color: #f9f9f9;

  .msg_item {
    display: flex;
    justify-content: space-between;
    margin-bottom: 5px;
  }

  .avatar {
    img {
      border-radius: 5px;
      width: 50px;
      height: 50px;
    }
  }

  .msg {
    display: flex;

    .msg_cont {
      padding-top: 5px;
      max-width: 500px;
    }

    .name {
      color: #999;
      margin-bottom: 5px;
    }
  }
  .msg_info {
    background-color: #fff;
    padding: 10px;
    border-radius: 5px;
    line-height: 22px;
  }

  .mine_msg {
    .avatar {
      margin-left: 10px;
    }

    .name {
      text-align: right;
    }
    .msg_info {
      background-color: #c1e3fc;
      padding: 10px;
      border-radius: 5px;
      line-height: 22px;
    }
  }

  .other_msg {
    .avatar {
      margin-right: 10px;
    }
    .msg_info {
      background-color: #fff;
      padding: 10px;
      border-radius: 5px;
      line-height: 22px;
    }
  }

  .img_msg {
    // max-height: 200px;
    // width: 200px;
    .img {
      height: 200px;
      width: 100%;
    }
    .video {
      width: 350px;
    }
  }
}

.msg_search_box {
  background-color: #fff;
  .avatar {
    margin-right: 10px;
  }
  .msg_info {
    padding-left: 0 !important;
    padding-top: 0 !important;
  }
  .msg_item {
    padding: 5px 15px;
    border-bottom: 1px solid #ccc;
  }

  .time {
    margin-left: 10px;
  }
}
</style>