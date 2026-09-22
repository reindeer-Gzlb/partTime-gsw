<template>
  <div>
    <el-button type="primary" @click="addfn">添加客服</el-button>
    <div style="margin-top: 20px">
      <el-table
        :data="tableData.records"
        height="calc(100vh - 310px)"
        style="width: 100%"
      >
        <el-table-column label="客服名称" prop="name" />
        <el-table-column label="QQ" prop="qq" />
        <el-table-column label="微信" prop="weixin" />
        <el-table-column label="图片" prop="imgUrl">
          <template slot-scope="scope">
            <el-image
              style="width: 66px; height: 66px; border-radius: 10px"
              :src="scope.row.qrCode"
              fit="cover"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="text" size="mini" @click="editItem(scope.row)"
              >编辑</el-button
            >
            <el-button
              type="text"
              style="color: red"
              size="mini"
              @click="delItem(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <div style="text-align: right; margin-top: 20px">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageData.pageNum"
          :page-sizes="[5, 10, 20, 30, 50]"
          :page-size="pageData.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="tableData.total"
        >
        </el-pagination>
      </div>
    </div>

    <!-- 弹出层 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      @close="close"
      width="850px"
    >
      <div class="form-box">
        <el-form
          ref="form"
          :model="form"
          :inline="true"
          :rules="rules"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item label="客服名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入名称" />
          </el-form-item>
          <el-form-item label="QQ" prop="qq">
            <el-input v-model="form.qq" placeholder="请输入QQ" />
          </el-form-item>
          <br />
          <el-form-item label="微信" prop="weixin">
            <el-input v-model="form.weixin" placeholder="请输入微信" />
          </el-form-item>
          <el-form-item label="排序" prop="sort">
            <el-input
              v-model="form.sort"
              placeholder="请输入排序"
              type="number"
            />
          </el-form-item>
          <br />
          <el-form-item label="上班时间" prop="workTime">
            <el-input v-model="form.workTime" placeholder="请输入上班时间" />
          </el-form-item>
          <el-form-item label="上班状态" prop="state">
            <el-switch
              v-model="form.state"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
            >
            </el-switch>
          </el-form-item>
          <el-row>
            <el-form-item
              label="图片"
              prop="img"
              :class="{ hiddenupload: imgShowList.length }"
            >
              <el-upload
                :action="uploadImgUrl"
                :file-list="imgShowList"
                :show-file-list="true"
                list-type="picture-card"
                :limit="1"
                :on-success="handleUploadSuccess"
                :on-remove="handleRemove"
                :on-preview="handlePictureCardPreview"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
            </el-form-item>
          </el-row>

          <div class="center">
            <el-button @click="close()">取消</el-button>
            <el-button
              type="primary"
              @click="submitForm('form')"
              v-loading="loading"
              >提交</el-button
            >
          </div>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>
  
  <script>
import { dellist, editlist, getOnelist, addlist, getlist } from '@/api/kefu/dict'
export default {
  data() {
    return {
      dialogVisible: false,
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      pageData: {
        pageSize: 10,
        pageNum: 1,
        typeResource: 2
      },
      title: "新增",
      isEdit: false,
      imgShowList: [],
      // 表单参数
      form: {
        name: '',
        qq: '',
        weixin: '',
        qrCode: '',
        workTime: '',
        sort: '',
        state: "0",
        typeResource: 2
      },
      rules: {
        systemType: [
          { required: true, message: "请选择系统类型", trigger: "change" }
        ],
        imgUrl: [
          { required: true, message: "请上传图片", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
  },
  methods: {
    close() {
      this.dialogVisible = false;
      this.form = {
        name: '',
        qq: '',
        weixin: '',
        qrCode: '',
        workTime: '',
        sort: '',
        state: "0",
        typeResource: 2
      }
      this.imgShowList = []
    },
    async getList() {
      const res = await getlist(this.pageData)
      console.log(res);
      this.tableData.records = JSON.parse(JSON.stringify(res.rows));
      this.tableData.total = res.total;
    },
    addfn() {
      this.dialogVisible = true;
    },
    // 打开弹窗
    editItem(data) {
      this.imgShowList = [];
      this.title = "修改";
      this.form = JSON.parse(JSON.stringify(data))
      this.imgShowList = [{ url: data.qrCode, name: "" }]
      this.dialogVisible = true;
    },
    // 重置
    reset() {
      this.$refs.form.resetFields();
    },
    submitForm(formName) {
      this.$refs["" + formName].validate(valid => {
        if (valid) {
          if (this.form.id) {
            // 编辑
            editlist(this.form).then(res => {
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.getList()
            });
          } else {
            // 新增
            console.log(this.form);
            addlist(this.form).then(res => {
              console.log(
                "%c 🍊 res: ",
                "font-size:20px;background-color: #3F7CFF;color:#fff;",
                res
              );
              this.$message.success(res.msg);
              this.dialogVisible = false;
              this.getList()
            });
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        console.log("%c Line:243 🍋 res", "color:#6ec1c2", res);
        // 取得新上传的文件
        this.imgShowList = [{ url: res.data.filePath, name: "" }];
        this.form.qrCode = res.data.filePath
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      this.imgShowList = []
      this.form.qrCode = ''
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传1张图片");
    },
    // 删除记录
    delItem(row) {
      this.$confirm("此操作将永久删除该记录, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          dellist(row.id).then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.getList()
          });
        })
        .catch(() => { });
    },
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pageData.pageSize = val
      this.getList()
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageData.pageNum = val
      this.getList()
    }
  }
}
  </script>
  
  <style scoped lang="css">
.center {
  text-align: center;
}
.form-box {
  max-height: calc(100vh - 20vh - 100px);
  overflow-y: auto;
  overflow-x: hidden;
  padding-top: 20px;
}
.form-box >>> .el-input {
  width: 250px;
}

.form-box >>> img {
  width: 100%;
}
.form-box >>> .el-upload-dragger {
  width: 148px;
  height: 148px;
}

.form-box >>> .el-form-item__content {
  max-width: 655px;
}
.hiddenupload >>> .el-upload--picture-card {
  display: none;
}
</style>
  