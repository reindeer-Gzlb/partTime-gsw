<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="编号ID" prop="Id">
        <el-input v-model="queryParams.Id" placeholder="请输入编号ID" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="合作人" prop="salecode">
        <el-select filterable v-model="queryParams.salecode" placeholder="请选择合作人" size="small">
          <el-option v-for="dict in saleList" :key="dict.id" :label="dict.name" :value="dict.saleName" />
        </el-select>
      </el-form-item>
      <el-form-item label="接口类型" prop="inter">
        <el-select filterable v-model="queryParams.inter" placeholder="请选择接口类型" size="small">
          <el-option v-for="dict in statusList" :key="dict" :label="dict" :value="dict" />
        </el-select>
      </el-form-item>
      <el-form-item label="接口状态" prop="successCount">
        <el-select filterable v-model="queryParams.successCount" placeholder="请选择接口类型" size="small">
          <el-option v-for="dict in sucessList" :key="dict.id" :label="dict.status" :value="dict.id" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['add:price:remove']" type="warning" plain icon="el-icon-download" size="mini"
          :loading="exportLoading" @click="rmRec">删除记录</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" width="55" prop="id" />
      <el-table-column label="销售伙伴" align="center" prop="saleId" />
      <el-table-column label="推送结果" align="center" prop="result" />
      <el-table-column label="成功数量" align="center" prop="successCount" />
      <el-table-column label="失败数量" align="center" prop="errCount" />
      <el-table-column label="接口类型" align="center" prop="inter" />
      <el-table-column label="推送人" align="center" prop="createName" />
      <el-table-column label="推送时间" align="center" prop="createTime" />

      <el-table-column label="请求参数" align="center" prop="req" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showReq(scope.row)">请求参数</el-button>
        </template>
      </el-table-column>
      <el-table-column label="响应参数" align="center" prop="req" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" @click="showRes(scope.row)">响应参数</el-button>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="100">
        <template slot-scope="scope">
          <el-button type="primary" @click="correctPush(scope.row)" size="small" plain>校正重推</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <el-dialog title="参数" :visible.sync="showSinNumVisible">
      <span>{{ req }}</span>
    </el-dialog>
    <el-dialog title="删除记录" :visible.sync="rmRecVisible">
      <el-select v-model="delPartId" placeholder="请选择合作人" size="small">
        <el-option v-for="dict in saleList" :key="dict.id" :label="dict.name" :value="dict.saleName" />
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="rmRecSubmit">确 定</el-button>
        <el-button @click="rmRecVisible = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 标签配置  -->
    <el-dialog title="标签配置" :visible.sync="addLableVisible" v-if="addLableVisible" width="70%">
      <LableSelectCommon :type="formAccount.type" :des="formAccount.des" @selectLable="selectLable"></LableSelectCommon>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addLableVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmLable">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<style>
.el-table .warning-row {
  height: 50px;
}

.el-table .success-row {
  height: 50px;
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

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
<script>
import {
  listRec,
  getRec,
  delRec,
  addRec,
  updateRec,
  delRecNew,
  exportRec
} from "@/api/info/rec";
import { allSale, delAcount, getInfo, updateInfo } from "@/api/info/info";
import { allList } from "@/api/partner/partner";
import { getDicts } from "@/api/system/dict/data";
import { addLable } from "@/api/lable/lable";
import LableSelectCommon from "../components/LableSelectCommon.vue";
export default {
  name: "Rec",
  components: { LableSelectCommon },
  data() {
    return {
      delPartId: '',
      formAccount: {},
      addLableVisible: false,
      dialogVisible: false,
      Accountopen: false,
      uploadImgUrl: "",
      dialogGameType: "",
      dialogGameTypeHp: "",
      checkedSales: [],
      rmRecVisible: false,
      showSinNumVisible: false,
      statusList: ["add", "edit", "del"],
      sucessList: [
        { status: "成功", id: 1 },
        { status: "失败", id: 0 }
      ],
      saleList: [],
      // 遮罩层
      loading: true,
      req: "",
      imageUrl: "",
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
      // 推送记录表格数据
      recList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      configId: "",
      ifSpecial: true,
      regionalArr: [],
      levelArr: [],
      videoList: [],
      fileNameArr: [],
      imgFileList: [],
      dialogImageUrl: "",
      imgShowList: [], //传给后台的图片
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createId: null,
        saleId: null,
        result: null,
        strCode: null,
        isSucess: null,
        inter: null,
        accountid: null,
      },
      // 表单参数
      form: {},
      inter: '',
      // 表单校验
      rules: {}
    };
  },
  props: ['accountId'],
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
    this.allSale();
  },
  watch: {
    accountId: {
      handler(id) {
        this.queryParams.accountid = id
        this.getList()
      },
      immediate: true
    }
  },
  methods: {

    submit() {
      this.$refs["formAccount"].validate(valid => {
        if (valid) {
          if (this.inter == "编辑") {
            this.formAccount.interType = "edit";
          } else {
            this.formAccount.interType = "add";
          }
          const that = this;
          this.formAccount.configId = this.configId;
          this.formAccount.urlArr = this.imgFileList;

          updateInfo(this.formAccount).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.Accountopen = false;
            this.getList();
          });
        }
      });
    },

    selectLable(resultDate) {
      this.resultDate = resultDate;
    },
    showLable() {
      this.addLableVisible = true;
    },
    confirmLable() {
      let params = {};
      params.infoId = this.formAccount.id;
      params.lableList = this.resultDate.join(",");
      addLable(params).then(res => {
        if (200 == res.code) {
          this.$modal.msgSuccess("新增成功");
          this.addLableVisible = false;
        } else {
          this.$modal.msgError("新增失败");
        }
      });
    },
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;

      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function (element, keifSpecialy) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //传给后台的图片
        }
      });
      console.log(this.imgFileList);
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$$modal.msgError("最多只能上传20张图片");
    },
    changeSpecial() {
      console.log(this.formAccount.accType);
      if (1 == this.formAccount.accType) {
        this.ifSpecial = false;
      } else {
        this.formAccount.specialPrice = null;
      }
    },
    closeDialog() {
      this.imgFileList = [];
      this.imgShowList = [];
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleAvatarRemove(response, file) {
      console.log(file);
      console.log(response);
    },
    handleAvatarSuccess(res, file) {
      console.log(file);
      console.log(res);
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.formAccount.titleImg = res.data.filePath;
      this.imageUrl = res.data.filePath;
    },
    handleVideoSuccess(res, file) {
      if (res.code == 200) {
        this.formAccount.videoUrl = res.data.filePath;
        this.formAccount.videoName = res.data.fileName;
      }
    },
    handleVideoRemove(response, fileList, file) {
      this.formAccount.videoUrl = "";
      this.formAccount.videoName = "";
    },
    handleUploadSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.imgFileList.push(param);
        console.log(this.imgFileList);
      }
    },
    selectDialogGameType() {
      let gameType = this.formAccount.type;
      this.getSelectGameInfo(gameType);
    },
    getConfigId(saleName) {
      let params = {};
      params.name = saleName;
      params.status = 1;
      allList(params).then(res => {
        if (res.code == 200) {
          if (res.rows.length > 0) {
            this.configId = res.rows[0].id;
          } else {
            this.configId = 0;
          }
        } else {
          this.$modal.msgError("请求失败");
        }
      });
    },
    getSelectGameInfo(gameType) {
      console.log(gameType);
      // byColums("level", gameType).then((res) => {
      //   console.log(res);
      //   if (res.code == 200) {
      //     this.levelArr = res.data;
      //   }
      // });
      getDicts(gameType + "段位").then(res => {
        if (res.code == 200) {
          this.levelArr = res.data;
        }
      });
      if ("和平精英" == gameType) {
        gameType = "王者荣耀";
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    correctPush(row) {
      let params = {};
      params.name = row.saleId;
      params.status = 1;
      allList(params).then(res => {
        if (res.code == 200) {
          if (res.rows.length > 0) {
            this.configId = res.rows[0].id;
            let id = row.result.split(",id为")[1];
            // this.getConfigId(row.saleId)
            if (row.inter == "删除") {
              let configId = this.configId;
              delAcount(id, this.configId).then(res => {
                if (res.code == 200) {
                  if (res.msg) {
                    this.$modal.msgError(res.msg);
                  } else {
                    this.$modal.msgSuccess("操作成功");
                  }
                  this.getList();
                }
              });
            }
            if (row.inter == "编辑" || row.inter == "新增") {
              let configId = this.configId;
              //1、打开编辑页面
              this.Accountopen = true;
              //2、通过id回显数据
              if ("和平精英" == row.type) {
                this.dialogGameTypeHp = true;
              } else {
                this.dialogGameType = true;
              }
              getInfo(id).then(response => {
                this.formAccount = response.data;
                if (response.data.urlArr) {
                  this.imgFileList = response.data.urlArr;
                  this.imgShowList = JSON.parse(
                    JSON.stringify(response.data.urlArr)
                  );
                }
                this.imageUrl = this.formAccount.titleImg;
                let video = {};
                this.videoList = [];
                this.changeSpecial();
                if (response.data.videoUrl) {
                  video.name = response.data.videoName;
                  video.url = response.data.videoUrl;
                  this.videoList[0] = video;
                }
                this.title = "修改游戏账号";
                this.inter = row.inter
              });

              //3、调用编辑接口
            } else {
              if (!configId) {
                this.$modal.msgError("该接口已禁用或已删除");
                return true;
              }
            }
          } else {
            this.$modal.msgError("请求失败");
          }
        }
      });
    },
    rmRecSubmit() {
      delRecNew(this.delPartId)
        .then(() => {
          this.delPartId = ''
          this.rmRecVisible = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    rmRec() {
      this.delPartId = ''
      this.rmRecVisible = true;
    },
    showReq(row) {
      this.showSinNumVisible = true;
      this.req = row.req;
    },
    showRes(row) {
      this.showSinNumVisible = true;
      this.req = row.res;
    },
    allSale() {
      allList().then(res => {
        if (res.code == 200) {
          this.saleList = res.rows;
        }
      });
    },
    /** 查询推送记录列表 */
    getList() {
      this.loading = true;
      listRec(this.queryParams).then(response => {
        this.recList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    accountCancel() {
      this.Accountopen = false;
      this.resetForm("formAccount");
      this.formAccount = {};
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        saleId: null,
        result: null,
        strCode: null,
        isSucess: null,
        inter: null,
        createTime: null
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
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加推送记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getRec(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改推送记录";
      });
    },
    /** 提交按钮 */
    // submitForm() {
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.id != null) {
    //         updateRec(this.form).then(response => {
    //           this.$modal.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         addRec(this.form).then(response => {
    //           this.$modal.msgSuccess("新增成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       }
    //     }
    //   });
    // },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有推送记录数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportRec(queryParams);
        })
        .then(response => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => { });
    }
  }
};
</script>
