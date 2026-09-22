<template>
  <div class="app-container jklis">
    <h2 style="text-align: center">实名检测</h2>
    <div style="text-align: center">
      <span style="color: red"
        >提示：本功能由第三方提供技术服务，0.9元/次 此功能支持定制
        充值微信：【yudu3518】。本功能剩余次数与自动获取剩余次数通用，每查询一次消耗3自动获取次数。</span
      >
      <br />
      简介：通过电信运营商验证手机号码与姓名是否一致。广泛用于实名注册、风控审核等场景，如电商、游戏、直播、金融等需要用户实名认证的场景。支持携号转网核验。
    </div>
    <div>
      <el-form
        :model="checkForm"
        ref="checkForm"
        :rules="rules"
        :inline="true"
        class="checkBox"
        label-width="auto"
      >
        <el-form-item label="姓名" prop="inputName">
          <el-input
            v-model="checkForm.inputName"
            placeholder="请输入"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="inputMobile">
          <el-input
            v-model="checkForm.inputMobile"
            placeholder="请输入"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="check"
            class="zdy-btn"
            >查询</el-button
          >
        </el-form-item>
        <el-form-item>
          <div>
            <shengyu ref="shengyu" />
          </div>
        </el-form-item>
      </el-form>
    </div>
    <div class="zdy-main">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        class="zdy-form search-form-new"
        label-width="auto"
        @keyup.enter.native="search"
      >
        <el-form-item label="姓名" prop="inputName">
          <el-input
            v-model="queryParams.inputName"
            placeholder="请输入"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item label="手机号" prop="inputMobile">
          <el-input
            v-model="queryParams.inputMobile"
            placeholder="请输入"
            clearable
            size="small"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="search"
            class="zdy-btn"
            >搜索</el-button
          >
        </el-form-item>
        <el-form-item>
          <el-button
            icon="el-icon-refresh"
            type="primary"
            size="mini"
            plain
            @click="resetQuery"
            class="zdy-btn zdy-btn-refresh"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
      <el-table
        :data="tableData.records"
        height="calc(100vh - 410px)"
        style="width: 100%"
        border
      >
        <el-table-column label="序号" width="80" label-position="left">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="查询姓名" prop="inputName" />
        <el-table-column label="查询手机号" prop="inputMobile" />
        <!-- <el-table-column label="消息说明" prop="bankMsg" width="100">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.bankMsg == '一致' ? 'success' : 'danger'"
              >{{ scope.row.bankMsg }}</el-tag
            >
          </template>
        </el-table-column> -->
        <el-table-column label="创建人" prop="createName" />
        <el-table-column label="验证结果" prop="bankCity">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.state == '1'" type="success"
              >核验一致</el-tag
            >
            <el-tag v-else type="danger">核验不一致</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="验证时间" prop="createTime" />
        <!-- <el-table-column label="归属地" prop="bankCity">
          <template slot-scope="scope">
            <span>{{ scope.row.bankProvince }}{{ scope.row.bankCity }}</span>
          </template>
        </el-table-column>
        <el-table-column label="运营商" prop="bankMobileType" width="100" />
        <el-table-column label="归属地编号" prop="bankAddrCode" />
        <el-table-column label="区号" prop="bankTelCode" /> -->
      </el-table>
      <div class="pages-box">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryParams.pageNum"
          :page-sizes="[10, 20, 30, 40, 50, 60]"
          :page-size="queryParams.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.total"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import shimingApi from "@/api/utilsPages/shimingTest.js"
import shengyu from '../../../components/yudu/shengyu.vue';
export default {
  name: "supplyConfig",
  components: { shengyu },
  data() {
    var phoneCheck = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('手机号不能为空'));
      } else if (!/^1[3-9]\d{9}$/.test(value)) {
        callback(new Error('手机号格式不正确'));
      } else {
        callback()
      }
    };
    return {
      tableData: {
        records: [],
        total: 0
      },
      loading: false,
      queryParams: {
        pageSize: 10,
        pageNum: 1
      },
      checkForm: {
        inputMobile: '',
        inputName: ''
      },
      rules: {
        inputMobile: [
          { required: true, message: "请输入", trigger: "blur" },
          { validator: phoneCheck, trigger: 'blur' }
        ],
        inputName: [
          { required: true, message: "请输入", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 查询实名
    check() {
      this.$refs['checkForm'].validate(valid => {
        if (valid) {
          shimingApi.check(this.checkForm).then(res => {
            // console.log("%c Line:117 🥓 res", "color:#ffdd4d", res);
            // this.$message.error(res.data.result_message)
            this.getList()
            this.$refs.shengyu.getNumber()
          })
        } else {
          this.$alert("请填写完整！");
        }
      });
    },




    // 获取列表
    getList() {
      this.tableData.records = [];
      this.loading = true;
      shimingApi
        .getList(this.queryParams)
        .then(response => {
          console.log("%c Line:151 🍬 response", "color:#4fff4B", response);
          this.tableData.total = response.total
          this.loading = false;
          this.tableData.records = response.rows;
        })
        .catch(err => {
          this.loading = false;
        });

    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1
      this.getList();
    },
    //重置
    resetQuery() {
      this.queryParams = {
        pageSize: 10,
        pageNum: 1
      }
      this.getList();
    },
    // 每页条数改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    // 页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    }

  }
};
</script>
<style lang="scss" scoped>
.checkBox {
  width: 850px;
  margin: 0 auto;
}
</style>
