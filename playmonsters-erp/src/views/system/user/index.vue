<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px; width: 150px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          v-show="showSearch"
          label-width="68px"
          @keyup.enter.native="handleQuery"
        >
          <el-form-item label="登录账号" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="请输入登录账号"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="请输入手机号码"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="用户昵称" prop="nickName">
            <el-input
              v-model="queryParams.nickName"
              placeholder="请输入用户昵称"
              clearable
              size="small"
            />
          </el-form-item>
          <el-form-item label="用户类型" prop="userNewType">
            <el-select
              v-model="queryParams.userNewType"
              placeholder="请选择"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in userType"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
              clearable
              size="small"
              @change="handleQuery"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="handleQuery"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
              >新增</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
              >修改</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:user:remove']"
              >删除</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:user:import']"
              >导入</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              :loading="exportLoading"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
              >导出</el-button
            >
          </el-col>
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
            :columns="columns"
          ></right-toolbar>
        </el-row>

        <el-table
          height="calc(100vh - 360px)"
          v-loading="loading"
          :data="userList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column
            label="用户编号"
            align="center"
            key="userId"
            prop="userId"
            v-if="columns[0].visible"
          />
          <el-table-column
            label="登录账号"
            align="center"
            key="userName"
            prop="userName"
            v-if="columns[1].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="用户名称"
            align="center"
            key="nickName"
            prop="nickName"
            v-if="columns[2].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="部门"
            align="center"
            key="deptName"
            prop="dept.deptName"
            v-if="columns[3].visible"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="手机号码"
            align="center"
            key="phonenumber"
            prop="phonenumber"
            v-if="columns[4].visible"
            width="120"
          />
          <!-- <el-table-column label="QQ" align="center" key="qq" prop="qq" v-if="columns[4].visible"
            width="120" />
            <el-table-column label="微信" align="center" key="weixin" prop="weixin" v-if="columns[4].visible"
            width="120" /> -->
          <el-table-column
            label="供应商折扣"
            align="center"
            key="discountt"
            prop="discountt"
            v-if="columns[4].visible"
            width="120"
          />
          <el-table-column
            label="销售折扣"
            align="center"
            key="supydiscount"
            prop="supydiscount"
            v-if="columns[4].visible"
            width="120"
          />
          <el-table-column
            label="利润比例"
            align="center"
            key="rise"
            prop="rise"
            v-if="columns[4].visible"
            width="120"
          />
          <el-table-column
            label="自动获取次数"
            align="center"
            key="campNumber"
            prop="campNumber"
            v-if="columns[4].visible"
            width="120"
          />
          <el-table-column
            label="steam使用次数"
            align="center"
            prop="steamNumber"
            width="120"
          />
          <el-table-column label="是否实名" align="center" key="realStatus">
            <template slot-scope="scope">
              <div>
                <el-tag type="success" v-show="scope.row.realStatus == 2"
                  >已实名</el-tag
                >
                <el-tag type="warning" v-show="scope.row.realStatus == 1"
                  >未实名</el-tag
                >
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="状态"
            align="center"
            key="status"
            v-if="columns[5].visible"
          >
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column
            label="创建时间"
            align="center"
            prop="createTime"
            v-if="columns[6].visible"
            width="160"
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
                >删除</el-button
              >
              <el-dropdown
                size="mini"
                @command="(command) => handleCommand(command, scope.row)"
                v-hasPermi="['system:user:resetPwd', 'system:user:edit']"
              >
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    command="handleResetPwd"
                    icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']"
                    >重置密码</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="handleAuthRole"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >分配角色</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="deleteAccount"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >删除账号</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="addPrice"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >涨价</el-dropdown-item
                  >
                  <!-- <el-dropdown-item command="editCampNumber" icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']">自动获取次数</el-dropdown-item> -->
                  <el-dropdown-item
                    command="huishou"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >回收游戏</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="steamNumberaccount"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']"
                    >steam使用次数</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="1100px"
      append-to-body
      class="addDialog"
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        :inline="true"
        label-width="auto"
      >
        <el-form-item label="用户类型" prop="userNewType">
          <el-select v-model="form.userNewType" placeholder="请选择">
            <el-option
              v-for="dict in userType"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="发布类型"
          prop="userUse"
          v-if="this.form.userNewType === 1"
        >
          <el-select v-model="form.userUse" placeholder="请选择">
            <el-option
              v-for="dict in userFnType"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户名称" prop="nickName">
          <el-input
            v-model="form.nickName"
            placeholder="请输入用户名称"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item label="归属部门" prop="deptId">
          <treeselect
            style="width: 220px"
            v-model="form.deptId"
            :options="deptOptions"
            :show-count="true"
            placeholder="请选择归属部门"
          />
        </el-form-item>

        <el-form-item label="手机号码" prop="phonenumber">
          <el-input
            v-model="form.phonenumber"
            placeholder="请输入手机号码"
            maxlength="11"
          />
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input
            v-model="form.email"
            placeholder="请输入邮箱"
            maxlength="50"
          />
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="form.qq" placeholder="请输入QQ" maxlength="50" />
        </el-form-item>
        <el-form-item label="微信" prop="weixin">
          <el-input
            v-model="form.weixin"
            placeholder="请输入微信"
            maxlength="50"
          />
        </el-form-item>

        <el-form-item
          v-if="form.userId == undefined"
          label="登录账号"
          prop="userName"
        >
          <el-input
            v-model="form.userName"
            placeholder="请输入登录账号"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item
          v-if="form.userId == undefined"
          label="用户密码"
          prop="password"
        >
          <el-input
            v-model="form.password"
            placeholder="请输入用户密码"
            type="password"
            maxlength="20"
            show-password
          />
        </el-form-item>

        <el-form-item label="用户性别">
          <el-select v-model="form.sex" placeholder="请选择">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="岗位">
          <el-select v-model="form.postIds" multiple placeholder="请选择">
            <el-option
              v-for="item in postOptions"
              :key="item.postId"
              :label="item.postName"
              :value="item.postId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="角色">
          <el-select v-model="form.roleIds" multiple placeholder="请选择">
            <el-option
              v-for="item in roleOptions"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
              :disabled="item.status == 1"
            ></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="账号排序" prop="odr">
          <el-input-number
            controls-position="right"
            v-model="form.odr"
            placeholder="请输入账号排序"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item label="利润比例" prop="rise">
          <el-input-number
            controls-position="right"
            v-model="form.rise"
            placeholder="请输入账号排序"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item label="域名" prop="domain">
          <el-input
            controls-position="right"
            v-model="form.domain"
            placeholder="请输入域名"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item label="供应商折扣" prop="domain">
          <el-input
            controls-position="right"
            v-model="form.discountt"
            placeholder="请输入折扣"
            maxlength="30"
          />
        </el-form-item>

        <el-form-item label="销售折扣" prop="supydiscount">
          <el-input
            controls-position="right"
            v-model="form.supydiscount"
            placeholder="请输入折扣"
            maxlength="30"
          />
        </el-form-item>
        <el-form-item label="最低佣金" prop="supydiscountminamount">
          <el-input
            controls-position="right"
            v-model="form.supydiscountminamount"
            placeholder="请输入最低佣金"
            maxlength="30"
          />
        </el-form-item>
        <el-form-item label="包赔比例" prop="guaranteeRatio">
          <el-input
            controls-position="right"
            v-model="form.guaranteeRatio"
            placeholder="包赔费收取号价的百分比，0-100"
            maxlength="30"
          />
        </el-form-item>
        <el-form-item label="提成比例" prop="guaranteeRoyalty">
          <el-input
            controls-position="right"
            v-model="form.guaranteeRoyalty"
            placeholder="平台收取包赔商包赔费的百分比，0-100"
            maxlength="30"
          />
        </el-form-item>
        <br />
        <el-form-item
          label="子域名"
          prop="guaranteeRoyalty"
          v-if="form.userNewType == 1"
        >
          <el-input
            type="textarea"
            controls-position="right"
            v-model="form.host"
            placeholder="请输入子域名(xxx.xxx),多个域名之间用逗号分隔"
            style="width: 816px"
          />
        </el-form-item>
        <!--  -->
        <br />

        <el-form-item label="是否展示">
          <el-radio-group v-model="form.isshow">
            <el-radio
              v-for="dict in dict.type.isshowaccount"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group> </el-form-item
        ><br />
        <el-form-item label="真实姓名" prop="realName">
          <el-input
            v-model="form.realName"
            placeholder="请输入真实姓名"
            maxlength="20"
          />
        </el-form-item>
        <el-form-item label="身份证号" prop="idCard">
          <el-input
            v-model="form.idCard"
            placeholder="请输入身份证号"
            maxlength="18"
          /> </el-form-item
        ><br />
        <el-form-item
          label="身份证国徽面"
          prop="frontIdCardImg"
          :class="{ hiddenupload: frontIdCardImgList.length }"
        >
          <el-input v-model="form.frontIdCardImg" v-show="false"></el-input>
          <el-upload
            class
            :action="uploadImgUrl"
            :file-list="frontIdCardImgList"
            :show-file-list="true"
            list-type="picture-card"
            :limit="1"
            :on-success="handleAvatarSuccess3"
            :on-remove="handleAvatarRemove3"
            :on-preview="imgPreview"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <el-form-item
          label="身份证人像面"
          prop="reverseIdCardImg"
          :class="{ hiddenupload: reverseIdCardImgList.length }"
        >
          <el-input v-model="form.reverseIdCardImg" v-show="false"></el-input>
          <el-upload
            class
            :action="uploadImgUrl"
            :file-list="reverseIdCardImgList"
            :show-file-list="true"
            list-type="picture-card"
            :limit="1"
            :on-success="handleAvatarSuccess4"
            :on-remove="handleAvatarRemove4"
            :on-preview="imgPreview"
          >
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
        <div class="" v-if="this.form.userNewType === 1">
          <el-form-item label="地址" prop="adress">
            <el-input
              v-model="form.adress"
              placeholder="请输入地址"
              maxlength="100"
              style="width: 400px"
            />
          </el-form-item>
          <br />

          <el-form-item
            label="营业执照"
            prop="businessLicenseImg"
            :class="{ hiddenupload: businessLicenseImgList.length }"
          >
            <el-input
              v-model="form.businessLicenseImg"
              v-show="false"
            ></el-input>
            <el-upload
              class
              :action="uploadImgUrl"
              :file-list="businessLicenseImgList"
              :show-file-list="true"
              list-type="picture-card"
              :limit="1"
              :on-success="handleAvatarSuccess"
              :on-remove="handleAvatarRemove"
              :on-preview="imgPreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
          <el-form-item
            label="手持身份证"
            prop="handIdCardImg"
            :class="{ hiddenupload: handIdCardImgList.length }"
          >
            <el-input v-model="form.handIdCardImg" v-show="false"></el-input>
            <el-upload
              class
              :action="uploadImgUrl"
              :file-list="handIdCardImgList"
              :show-file-list="true"
              list-type="picture-card"
              :limit="1"
              :on-success="handleAvatarSuccess2"
              :on-remove="handleAvatarRemove2"
              :on-preview="imgPreview"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
          </el-form-item>
        </div>
        <div v-if="supplyConfig && form.userUse == 1">
          <el-form-item label="appId" prop="realName">
            <span>{{ supplyConfig.appId }}</span>
          </el-form-item>
          <el-form-item label="appSecret" prop="appSecret">
            <span>{{ supplyConfig.appSecret }}</span>
          </el-form-item>
          <el-form-item label="最大商品数" prop="goodsMaxnum">
            <span>{{ supplyConfig.goodsMaxnum }}</span>
          </el-form-item>
          <el-form-item label="" prop="">
            <el-button plain type="primary" size="mini" @click="copyKey"
              >复制</el-button
            >
          </el-form-item>
        </div>
        <el-form-item label="备注">
          <el-input
            style="width: 800px"
            v-model="form.remark"
            type="textarea"
            placeholder="请输入内容"
          ></el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
              >{{ dict.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 用户导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox
              v-model="upload.updateSupport"
            />是否更新已经存在的用户数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="addPriceOpen" width="400px" append-to-body>
      <el-form>
        <el-form-item label="涨价比例" prop="domain">
          <el-input
            controls-position="right"
            v-model="currentUser.rise"
            placeholder="请输入涨价比例"
            maxlength="30"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAddPriceForm">确 定</el-button>
        <el-button @click="addPriceOpen = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改自动获取次数弹窗 -->
    <el-dialog
      :visible.sync="editCampNumberShow"
      top="300px"
      width="400px"
      title="自动获取次数修改"
      append-to-body
    >
      <el-form>
        <el-form-item label="自动获取次数" prop="campNumber">
          <el-input
            type="number"
            controls-position="right"
            v-model="currentUser.campNumber"
            placeholder="请输入自动获取次数"
            max="999999999"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitEditCampNumberForm"
          >确 定</el-button
        >
        <el-button @click="editCampNumberShow = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改steam次数 -->
    <el-dialog
      :visible.sync="steamNumberOpen"
      title="steam使用次数"
      width="400px"
      append-to-body
    >
      <el-form>
        <el-form-item label="steam使用次数" prop="steamNumber">
          <el-input
            controls-position="right"
            v-model="currentUser.steamNumber"
            placeholder="请输入使用次数"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitsteamNumberForm"
          >确 定</el-button
        >
        <el-button @click="steamNumberOpen = false">取 消</el-button>
      </div>
    </el-dialog>
    <HuishouGame ref="HuishouGame"></HuishouGame>
  </div>
</template>

<script>
import {
  listUser,
  getUser,
  delUser,
  addUser,
  updateUser,
  exportUser,
  resetUserPwd,
  changeUserStatus,
  importTemplate,
  addprice,
  offAccount,
  getForUserId,
  upCampNumber,
  editSteamAccount,
} from "@/api/system/user";
import HuishouGame from "./huishouGame.vue";
import { getToken } from "@/utils/auth";
import { treeselect } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ["sys_normal_disable", "sys_user_sex", "isshowaccount"],
  components: { Treeselect, HuishouGame },
  data() {
    return {
      editCampNumberShow: false, //修改自动获取次数的弹窗是否弹出
      currentUser: {},
      // 遮罩层
      loading: true,
      addPriceOpen: false,
      steamNumberOpen: false,
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
      // 用户表格数据
      userList: null,
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 日期范围
      dateRange: [],
      // 岗位选项
      postOptions: [],
      // 角色选项
      roleOptions: [],
      // 表单参数
      form: {
        odr: 0,
      },
      supplyConfig: null,
      uploadImgUrl: "",
      businessLicenseImgList: [],
      handIdCardImgList: [],
      frontIdCardImgList: [],
      reverseIdCardImgList: [],
      userType: [
        {
          value: 1,
          label: "商户",
        },
        {
          value: 2,
          label: "C端用户",
        },
        {
          value: 3,
          label: "合作平台",
        },
      ],
      userFnType: [
        {
          value: 1,
          label: "api推送",
        },
        {
          value: 2,
          label: "手动发布",
        },
      ],

      rise: "",
      defaultProps: {
        children: "children",
        label: "label",
      },
      rise: "",
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/user/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined,
      },
      // 列信息
      columns: [
        { key: 0, label: `用户编号`, visible: true },
        { key: 1, label: `登录账号`, visible: true },
        { key: 2, label: `用户名称`, visible: true },
        { key: 3, label: `部门`, visible: true },
        { key: 4, label: `手机号码`, visible: true },
        { key: 5, label: `状态`, visible: true },
        { key: 6, label: `创建时间`, visible: true },
      ],
      // 表单校验
      rules: {
        userNewType: [
          { required: true, message: "请选择用户类型", trigger: "change" },
        ],
        userName: [
          { required: true, message: "登录账号不能为空", trigger: "blur" },
          {
            min: 2,
            max: 20,
            message: "登录账号长度必须介于 2 和 20 之间",
            trigger: "blur",
          },
        ],
        nickName: [
          { required: true, message: "用户名称不能为空", trigger: "blur" },
        ],
        password: [
          { required: true, message: "用户密码不能为空", trigger: "blur" },
          {
            min: 5,
            max: 20,
            message: "用户密码长度必须介于 5 和 20 之间",
            trigger: "blur",
          },
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        phonenumber: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getList();
    this.getTreeselect();
    this.getConfigKey("sys.user.initPassword").then((response) => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    // 一键复制秘钥
    copyKey() {
      let str = `appId：${this.supplyConfig.appId} \n appSecret：${
        this.supplyConfig.appSecret
      } \n 最大商品数：${
        this.supplyConfig.goodsMaxnum ? this.supplyConfig.goodsMaxnum : ""
      } \n 接口地址：http://back.feihengdaishou.com \n 接口文档： https://note.youdao.com/s/M3WKl3cb \n 后台地址：http://erp.feihengdaishou.com \n 登录账号：${
        this.form.userName
      } \n 初始密码：123456`;
      this.$copyText(str).then(
        (e) => {
          this.$message({
            type: "success",
            message: "已复制到剪切板！",
          });
        },
        function (e) {}
      );
    },
    submitAddPriceForm() {
      console.log(this.currentUser);
      addprice(this.currentUser).then((res) => {
        this.$modal.msgSuccess("操作成功");
        this.addPriceOpen = false;
      });
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询部门下拉树结构 */
    getTreeselect() {
      treeselect().then((response) => {
        this.deptOptions = response.data;
      });
    },
    // 筛选节点
    filterNode(value, data) {
      // console.log("%c Line:522 🍊 data", "color:#ed9ec7", data);
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.getList();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal
        .confirm('确认要"' + text + '""' + row.userName + '"用户吗？')
        .then(function () {
          return changeUserStatus(row.userId, row.status);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        userId: null,
        deptId: null,
        userName: null,
        nickName: null,
        password: null,
        phonenumber: null,
        email: null,
        sex: null,
        status: "0",
        remark: null,
        postIds: [],
        roleIds: [],
      };
      this.businessLicenseImgList = [];
      this.handIdCardImgList = [];
      this.frontIdCardImgList = [];
      this.reverseIdCardImgList = [];
      // this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      // this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 打开配置号商回收游戏的弹窗
    openHuishouGame(row) {
      this.$refs.HuishouGame.open(row);
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        case "deleteAccount":
          this.deleteAccount(row);
          break;
        case "addPrice":
          this.addPrice(row);
          break;
        case "editCampNumber":
          this.openEditCampNumber(row);
          break;
        case "huishou":
          this.openHuishouGame(row);
          break;
        case "steamNumberaccount":
          this.openSteam(row);
          break;
        default:
          break;
      }
    },
    openSteam(row) {
      this.currentUser = JSON.parse(JSON.stringify(row));
      this.steamNumberOpen = true;
    },
    async submitsteamNumberForm() {
      let obj = {
        userId: this.currentUser.userId,
        steamNumber: this.currentUser.steamNumber,
      };
      const res = await editSteamAccount(obj);
      console.log("res", res);
      this.$message.success("操作成功");
      this.steamNumberOpen = false;
      this.getList();
    },
    // 打开修改自动获取次数的弹窗
    openEditCampNumber(row) {
      this.currentUser = JSON.parse(JSON.stringify(row));
      this.editCampNumberShow = true;
    },
    // 编辑自动获取次数
    submitEditCampNumberForm() {
      let obj = {
        userId: this.currentUser.userId,
        campNumber: this.currentUser.campNumber,
      };
      upCampNumber(obj).then((res) => {
        this.$modal.msgSuccess(res.msg);
        this.editCampNumberShow = false;
        this.getList();
      });
    },
    addPrice(row) {
      this.currentUser = row;
      this.addPriceOpen = true;
      console.log(this.currentUser);
    },
    deleteAccount(row) {
      offAccount(row.userId).then((res) => {
        this.$modal.msgSuccess("删除成功");
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.supplyConfig = null;
      this.reset();
      this.getTreeselect();
      getUser().then((response) => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "添加用户";
        this.form.password = this.initPassword;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.supplyConfig = null;
      this.reset();
      this.getTreeselect();
      const userId = row.userId || this.ids;
      getUser(userId).then((response) => {
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.form.postIds = response.postIds;
        this.form.roleIds = response.roleIds;
        if (this.form.businessLicenseImg) {
          this.businessLicenseImgList = [{ url: this.form.businessLicenseImg }];
        }
        if (this.form.handIdCardImg) {
          this.handIdCardImgList = [{ url: this.form.handIdCardImg }];
        }
        if (this.form.frontIdCardImg) {
          this.frontIdCardImgList = [{ url: this.form.frontIdCardImg }];
        }
        if (this.form.reverseIdCardImg) {
          this.reverseIdCardImgList = [{ url: this.form.reverseIdCardImg }];
        }
        if (this.form.userNewType === 1) {
          this.getSupplyConfig(row);
        }
        this.open = true;
        this.title = "修改用户";
        this.form.password = "";
      });
    },
    // 获取供货商appId等信息
    getSupplyConfig(row) {
      console.log("%c Line:669 🥪 row", "color:#4fff4B", row);
      getForUserId({
        userId: row.userId,
      }).then((res) => {
        if (res.data && res.data.length) {
          this.supplyConfig = res.data[0];
        }
      });
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.userName + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
      })
        .then(({ value }) => {
          resetUserPwd(row.userId, value).then((response) => {
            this.$modal.msgSuccess("修改成功，新密码是：" + value);
          });
        })
        .catch(() => {});
    },
    /** 分配角色操作 */
    handleAuthRole: function (row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    // 图片预览
    imgPreview(file) {
      this.$viewerApi({
        images: [file.url],
      });
    },
    // 营业执照图片移除
    handleAvatarRemove() {
      this.form["businessLicenseImg"] = "";
      this["businessLicenseImgList"] = [];
    },
    //营业执照 图片上传成功
    handleAvatarSuccess(res) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this["businessLicenseImgList"].push(param);
        this.form["businessLicenseImg"] = res.data.filePath;
      }
    },
    // 手持身份证图片移除
    handleAvatarRemove2() {
      this.form["handIdCardImg"] = "";
      this["handIdCardImgList"] = [];
    },
    //手持身份证 图片上传成功
    handleAvatarSuccess2(res) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this["handIdCardImgList"].push(param);
        this.form["handIdCardImg"] = res.data.filePath;
      }
    },
    // 身份证国徽面图片移除
    handleAvatarRemove3() {
      this.form["frontIdCardImg"] = "";
      this["frontIdCardImgList"] = [];
    },
    //身份证国徽面 图片上传成功
    handleAvatarSuccess3(res) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this["frontIdCardImgList"].push(param);
        this.form["frontIdCardImg"] = res.data.filePath;
      }
    },
    // 身份证人像面 图片移除
    handleAvatarRemove4() {
      this.form["reverseIdCardImg"] = "";
      this["reverseIdCardImgList"] = [];
    },
    //身份证人像面 图片上传成功
    handleAvatarSuccess4(res) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this["reverseIdCardImgList"].push(param);
        this.form["reverseIdCardImg"] = res.data.filePath;
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.reset();
              this.getList();
            });
          } else {
            addUser(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.reset();
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal
        .confirm('是否确认删除用户编号为"' + userIds + '"的数据项？')
        .then(function () {
          return delUser(userIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有用户数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportUser(queryParams);
        })
        .then((response) => {
          this.$download.name(response.msg);
          this.exportLoading = false;
        })
        .catch(() => {});
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      importTemplate().then((response) => {
        this.$download.name(response.msg);
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
  },
};
</script>
<style scoped lang="scss">
::v-deep .addDialog .el-input {
  width: 220px;
}

.hiddenupload ::v-deep .el-upload--picture-card {
  display: none;
}

.app-container {
  // height: calc(100vh - 100px);
  // overflow-y: auto;
}

.addDialog .el-form {
  height: calc(100vh - 260px);
  overflow-y: auto;
}
</style>
