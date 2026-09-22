<template>
  <el-dialog
    class
    :title="title"
    :visible.sync="dialogVisible"
    :close-on-click-modal="false"
    width="80%"
    @close="close"
    :close-on-press-escape="false"
  >
    <el-tabs type="border-card" v-model="actived" @tab-click="tabChange">
      <el-tab-pane
        :label="tab.name"
        :name="tab.name"
        v-for="(tab, index) in gameList"
        :key="tab.id"
        :disabled="isEdit || isView"
        v-loading="loading"
      >
        <div
          class="form-box"
          v-if="actived == tab.name"
          @click="boxradioIsShow = ''"
        >
          <el-form
            :ref="'form' + index"
            :model="form"
            :inline="true"
            :rules="rules"
            label-width="auto"
            class="demo-ruleForm"
          >
            <!-- <el-divider content-position="left">基础信息</el-divider> -->
            <div class="step-box" v-if="!isEdit && !isView">
              <el-steps :active="step" align-center>
                <el-step
                  title="温馨提示"
                  description="填写游戏信息此信息将展示给买家并用于接口推送，请认真准确填写"
                ></el-step>
                <!-- <el-step
                  title="第二步"
                  description="请填写重要信息，此区域只做数据储存不做展示，请谨慎填写"
                ></el-step> -->
              </el-steps>
            </div>

            <div class="step1" v-if="step == 1 || isEdit || isView">
              <el-divider content-position="left">基础信息</el-divider>
              <!-- <br v-if="form.gameName != '和平精英' && !isView" /> -->
              <!-- 王者荣耀个性化属性 start -->
              <el-form-item
                prop="f17"
                v-if="form.gameName == '王者荣耀' && !isView"
                label="营地ID"
                class="attr-input-content"
              >
                <div class="input-btn flex">
                  <el-input
                    v-model="form.f17"
                    color="#333"
                    placeholder="请输入营地ID加载数据"
                    @keyup.enter.native="getRoleList"
                    style="flex-shrink: 0"
                  ></el-input>
                  <el-button
                    type="primary"
                    size="mini"
                    plain
                    @click="getRoleList"
                    style="margin-left: 20px"
                    :loading="btnLoading"
                    >加载数据</el-button
                  >
                  <shengyu ref="shengyu" />
                </div>
              </el-form-item>
              <!-- <br v-if="form.gameName == '王者荣耀' && !isView" /> -->
              <el-form-item
                prop="roleId"
                v-if="form.gameName == '王者荣耀' && !isView"
                label="角色列表"
                class="attr-input-content"
              >
                <el-select
                  placeholder="输入营地ID加载数据"
                  v-model="form.roleId"
                  @change="roleSelect"
                >
                  <el-option
                    v-for="item2 in roleList"
                    :key="item2.zdytext"
                    :label="item2.zdytext"
                    :value="item2.roleId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <br v-if="form.gameName == '王者荣耀' && !isView" />
              <!-- 王者荣耀个性化属性 end -->
              <!-- 和平精英个性化属性 start -->
              <el-form-item
                prop="f13"
                v-if="form.gameName == '和平精英' && !isView"
                label=""
                class="attr-input-content"
              >
                <div class="input-btn flex" style="margin-left: 72px">
                  <el-button
                    type="primary"
                    size="mini"
                    plain
                    @click="getQRcode"
                    style="margin-left: 20px"
                    :loading="btnLoading"
                    >授权</el-button
                  >
                  <shengyu ref="shengyu" />
                </div>
              </el-form-item>
              <br v-if="form.gameName == '和平精英' && !isView" />
              <!-- 和平精英个性化属性 end -->
              <el-form-item
                label="系统"
                prop="systemId"
                v-if="form.gameType === 0"
              >
                <el-select
                  v-model="form.systemId"
                  filterable
                  placeholder="请选择"
                  @change="sysChange"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in sysList"
                    :key="dict.id"
                    :label="dict.fieldName"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item
                label="运营商"
                prop="carrierId"
                v-if="form.gameType === 0"
              >
                <el-select
                  v-model="form.carrierId"
                  filterable
                  placeholder="请选择"
                  @change="getAreaList"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in carrierList"
                    :key="dict.id"
                    :label="dict.fieldName"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="大区" prop="groupId">
                <el-select
                  v-model="form.groupId"
                  filterable
                  placeholder="请选择"
                  @change="getServiceList"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in areaList"
                    :key="dict.id"
                    :label="dict.fieldName"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="服务器" prop="serverId">
                <el-select
                  v-model="form.serverId"
                  filterable
                  placeholder="请选择"
                  @change="serverChange"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in serverList"
                    :key="dict.id"
                    :label="dict.fieldName"
                    :value="dict.id"
                  />
                </el-select>
              </el-form-item>
              <!-- <el-form-item label="分路情况" prop="branchRoadTypeId" class="lattle-input2">
                <div class="flex">
                  <el-select v-model="form.branchRoadTypeId" filterable placeholder="请选择" @change="fenluChange"
                    :disabled="isView">
                    <el-option v-for="dict in fenluList" :key="dict.id" :label="dict.fieldName" :value="dict.id" />
                  </el-select>
                  <el-input style="width: 110px;" v-model="form.branchRoadValue" placeholder="分段数" :disabled="isView" />
                  <div style="margin-left: 5px;">段</div>
                </div>
              </el-form-item> -->
              <el-form-item label="出售价格" prop="price">
                <el-input
                  v-model="form.price"
                  placeholder="请输入价格"
                  :disabled="isView"
                />
              </el-form-item>

              <el-form-item label="是否出租" prop="hireState">
                <el-select
                  v-model="form.hireState"
                  filterable
                  placeholder="请选择"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in hireStateOptions"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item
                label="押金"
                prop="deposit"
                v-if="form.hireState == 2"
              >
                <el-input
                  v-model="form.deposit"
                  placeholder="请输入出租押金"
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item
                label="时租金"
                prop="hireHour"
                v-if="form.hireState == 2"
              >
                <el-input
                  v-model="form.hireHour"
                  placeholder="请输入时租金"
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item
                label="包夜租金"
                prop="hireNight"
                v-if="form.hireState == 2"
              >
                <el-input
                  v-model="form.hireNight"
                  placeholder="请输入包夜租金"
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item
                label="日租金"
                prop="hireDay"
                v-if="form.hireState == 2"
              >
                <el-input
                  v-model="form.hireDay"
                  placeholder="请输入日租金"
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item
                label="周租金"
                prop="hireWeek"
                v-if="form.hireState == 2"
              >
                <el-input
                  v-model="form.hireWeek"
                  placeholder="请输入周租金"
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item label="商品类型" prop="goodsTypeValues" class="">
                <el-select
                  multiple
                  filterable
                  clearable
                  v-model="form.goodsTypeValues"
                  placeholder="请选择，可多选"
                  @change="goodsTypeChanges"
                  :disabled="isView"
                >
                  <el-option
                    v-for="(dict, i) in goodsTypeList"
                    :key="i"
                    :label="dict.fieldName"
                    :value="dict.fieldName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="归属" prop="" class="">
                <el-select
                  v-model="form.attributione"
                  placeholder="请选择归属"
                  :disabled="isView"
                >
                  <el-option
                    v-for="(dict, i) in AttributioneList"
                    :key="i"
                    :label="dict.fieldName"
                    :value="dict.fieldName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item
                v-if="
                  (pegaType == 'salePort' &&
                    admins == 'admin' &&
                    activedId == 2) ||
                  (pegaType == 'salePort' &&
                    guaishouMax == 'guai_shou_max' &&
                    activedId == 2) ||
                  title == '新增游戏账号' ||
                  (pegaType == 'salePort' && activedId == 2) ||
                  pegaType != 'salePort'
                "
                label="微信号"
                prop=""
                class=""
              >
                <el-input
                  v-model="form.vxNickName"
                  placeholder="请输入微信号"
                  style="width: 250px"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item
                label="邮箱"
                prop=""
                class=""
                v-if="
                  (pegaType == 'salePort' &&
                    admins == 'admin' &&
                    activedId == 2) ||
                  (pegaType == 'salePort' &&
                    guaishouMax == 'guai_shou_max' &&
                    activedId == 2) ||
                  title == '新增游戏账号' ||
                  (pegaType == 'salePort' && activedId == 2) ||
                  pegaType != 'salePort'
                "
              >
                <el-input
                  v-model="form.email"
                  placeholder="请输入邮箱"
                  style="width: 250px"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item label="17 位" prop="" class="">
                <el-input
                  v-model="form.businessCode"
                  placeholder="请输入17位"
                  style="width: 250px"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item label="回收时间" prop="" class="">
                <el-date-picker
                  v-model="form.recoveryTime"
                  type="datetime"
                  placeholder="选择日期时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item
                label="一手回收时间"
                prop=""
                class=""
                v-if="
                  (pegaType == 'salePort' &&
                    admins == 'admin' &&
                    activedId == 2) ||
                  (pegaType == 'salePort' &&
                    guaishouMax == 'guai_shou_max' &&
                    activedId == 2) ||
                  title == '新增游戏账号' ||
                  (pegaType == 'salePort' && activedId == 2) ||
                  pegaType != 'salePort'
                "
              >
                <el-date-picker
                  v-model="form.recoveryFirstTime"
                  type="datetime"
                  placeholder="选择日期时间"
                  format="yyyy-MM-dd HH:mm:ss"
                  value-format="yyyy-MM-dd HH:mm:ss"
                >
                </el-date-picker>
              </el-form-item>
              <el-form-item label="游戏ID" prop="" class="">
                <el-input
                  v-model="form.gameThirdId"
                  placeholder="请输入游戏ID"
                  style="width: 250px"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item label="需要上架平台" prop="" class="">
                <el-input
                  v-model="form.toPlatform"
                  placeholder="请输入需要上架平台"
                  style="width: 250px"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <br />
              <div class="step2">
                <el-divider content-position="left">全球信息</el-divider>
                <el-form-item label="最低出售价" prop="" class="">
                  <el-input
                    v-model="form.downPrice"
                    placeholder="请输入最低出售价"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="全球账户" prop="" class="">
                  <el-input
                    v-model="form.quanqiuAccount"
                    placeholder="请输入全球账户"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="全球密码" prop="" class="">
                  <el-input
                    v-model="form.quanqiuPassword"
                    placeholder="请输入全球密码"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="全球邮箱" prop="" class="">
                  <el-input
                    v-model="form.quanqiuEmail"
                    placeholder="请输入全球邮箱"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="全球邮箱密码" prop="" class="">
                  <el-input
                    v-model="form.quanqiuEmailPassword"
                    placeholder="请输入全球邮箱密码"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="全球备注" prop="" class="">
                  <el-input
                    v-model="form.quanqiuRemark"
                    placeholder="请输入全球备注"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="浏览量" prop="" class="">
                  <el-input
                    v-model="form.lookCount"
                    placeholder="请输入浏览量"
                    style="width: 250px"
                    show-word-limit
                    :disabled="isView"
                  />
                </el-form-item>
                <br />
              </div>

              <div class="step2">
                <el-divider content-position="left">重要信息</el-divider>
                <el-form-item label="自编号" prop="thirdId">
                  <el-input
                    type="text"
                    v-model="form.thirdId"
                    placeholder="自己编写的编号,限6位数字"
                    maxlength="6"
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="账号" prop="accountNumber">
                  <el-input
                    v-model="form.accountNumber"
                    placeholder="请输入账号"
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item
                  label="密码"
                  prop="accountPassword"
                  v-if="
                    (pegaType == 'salePort' &&
                      admins == 'admin' &&
                      activedId == 2) ||
                    (pegaType == 'salePort' &&
                      guaishouMax == 'guai_shou_max' &&
                      activedId == 2) ||
                    title == '新增游戏账号' ||
                    (pegaType == 'salePort' && activedId == 2) ||
                    pegaType != 'salePort'
                  "
                >
                  <el-input
                    v-model="form.accountPassword"
                    placeholder="请输入密码"
                    :disabled="isView"
                  /> </el-form-item
                ><br />
                <el-form-item label="成本价" prop="costPrice">
                  <el-input
                    v-model="form.costPrice"
                    placeholder="请输入成本价以便财务系统自动统计"
                    :disabled="isView"
                  />
                </el-form-item>
                <el-form-item label="一手回收价" prop="fastPrice">
                  <el-input
                    v-model="form.fastPrice"
                    placeholder="请输入一手回收价"
                    :disabled="isView"
                  /> </el-form-item
                ><br />
                <!-- <el-form-item label="绑定手机" prop="telid">
                  <div style="width: 300px">
                    <span v-if="selectedPhone" style="margin-right: 5px">
                      {{ selectedPhone }}
                      <i
                        @click="delPhone"
                        class="el-icon-error"
                        style="color: red; margin-left: 5px; cursor: pointer"
                      ></i>
                    </span>
                    <el-button
                      size="small"
                      plain
                      type="primary"
                      @click="openSelectPhone"
                      v-if="!isView"
                      >选择手机</el-button
                    >
                  </div>
                </el-form-item> -->

                <el-form-item
                  label="绑定手机号"
                  prop="securityPhone"
                  v-if="
                    (pegaType == 'salePort' &&
                      admins == 'admin' &&
                      activedId == 2) ||
                    (pegaType == 'salePort' &&
                      guaishouMax == 'guai_shou_max' &&
                      activedId == 2) ||
                    title == '新增游戏账号' ||
                    (pegaType == 'salePort' && activedId == 2) ||
                    pegaType != 'salePort'
                  "
                >
                  <el-input
                    v-model="form.securityPhone"
                    placeholder="请输入绑定手机号"
                    :disabled="isView"
                  />
                </el-form-item>

                <el-form-item
                  label="ARS绑定"
                  prop="arsPhone"
                  v-if="
                    (pegaType == 'salePort' &&
                      admins == 'admin' &&
                      activedId == 2) ||
                    (pegaType == 'salePort' &&
                      guaishouMax == 'guai_shou_max' &&
                      activedId == 2) ||
                    title == '新增游戏账号' ||
                    (pegaType == 'salePort' && activedId == 2) ||
                    pegaType != 'salePort'
                  "
                >
                  <el-input
                    v-model="form.arsPhone"
                    placeholder="请输入ARS绑定"
                    :disabled="isView"
                  />
                </el-form-item>

                <el-form-item
                  label="绑定邮箱"
                  prop="bindEmail"
                  v-if="
                    (pegaType == 'salePort' &&
                      admins == 'admin' &&
                      activedId == 2) ||
                    (pegaType == 'salePort' &&
                      guaishouMax == 'guai_shou_max' &&
                      activedId == 2) ||
                    title == '新增游戏账号' ||
                    (pegaType == 'salePort' && activedId == 2) ||
                    pegaType != 'salePort'
                  "
                >
                  <el-input
                    v-model="form.bindEmail"
                    placeholder="请输入绑定邮箱"
                    :disabled="isView"
                  />
                </el-form-item>

                <el-form-item label="账号类型" prop="accountType" class="">
                  <el-select
                    v-model="form.accountType"
                    placeholder="请选择账号类型"
                    :disabled="isView"
                  >
                    <el-option
                      v-for="(dict, i) in accountList"
                      :key="i"
                      :label="dict.name"
                      :value="dict.id"
                    />
                  </el-select>
                </el-form-item>

                <el-form-item label="包赔类型" prop="isGuarantee" class="">
                  <el-select
                    v-model="form.isGuarantee"
                    placeholder="请选择包赔类型"
                    :disabled="isView"
                  >
                    <el-option
                      v-for="(dict, i) in IsBaopei"
                      :key="i"
                      :label="dict.name"
                      :value="dict.id"
                    />
                  </el-select>
                </el-form-item>

                <el-row>
                  <el-form-item label="重要信息备注" prop="importantRemark">
                    <el-input
                      v-model="form.importantRemark"
                      type="textarea"
                      placeholder="可以记录密保信息，采购渠道 包赔信息 卖家信息等，任意重要信息都可以放到此处 方便查找"
                      :rows="8"
                      style="width: 700px"
                      :disabled="isView"
                    />
                  </el-form-item>
                </el-row>
              </div>
              <!-- <el-switch :disabled="actived != 1 || (pageType != 'myAccount' && pageType != 'admin')"
                  @change="changeIstop($event, scope.row.id)" v-model="scope.row.isTop" active-color="#13ce66"
                  :active-value="1" :inactive-value="2" inactive-color="#ff4949">
                </el-switch> -->
              <el-form-item label="商品标题" prop="" class="">
                <el-input
                  v-model="form.title"
                  placeholder="请输入标题，用于接口对接，可选填，若不填将自动截取商品描述作为标题"
                  style="width: 600px"
                  maxlength="200"
                  show-word-limit
                  :disabled="isView"
                />
              </el-form-item>
              <el-form-item label="精品账号" prop="isTop" class="">
                <el-switch
                  :disabled="isView"
                  v-model="form.isTop"
                  active-color="#13ce66"
                  :active-value="1"
                  :inactive-value="2"
                  inactive-color="#ff4949"
                >
                </el-switch>
              </el-form-item>
              <el-row>
                <el-form-item label="商品描述" prop="des">
                  <el-input
                    v-model="form.des"
                    type="textarea"
                    placeholder="请按顺序编写简介信息内容，例如：王者荣耀 安卓Q 有二次 英雄数量 皮肤数量 皮肤简介..."
                    :rows="8"
                    style="width: 700px"
                    :disabled="isView"
                  />
                  <el-button
                    type="primary"
                    plain
                    @click="zidongChange"
                    size="mini"
                    v-if="!isView"
                    >自动识别</el-button
                  >
                </el-form-item>
              </el-row>
              <el-divider content-position="left">属性信息</el-divider>
              <!-- 渲染自定义字段 -->
              <div v-if="attrs && attrs.length">
                <span v-for="custom in attrs" :key="custom.id">
                  <div
                    v-if="
                      custom.fieldType == 'checkbox' ||
                      custom.fieldType == 'boxradio'
                    "
                  ></div>
                  <el-form-item
                    :label="custom.fieldName"
                    :prop="custom.aavName"
                    v-if="custom.isEnable === '0'"
                  >
                    <el-input
                      v-model="form[custom.aavName]"
                      :placeholder="`请填写${custom.fieldName}`"
                      v-if="custom.fieldType == 'input'"
                      :disabled="isView"
                    />
                    <el-input-number
                      v-model="form[custom.aavName]"
                      :step="0.1"
                      :max="99999"
                      :controls="false"
                      style="text-align: left"
                      :placeholder="`请填写${custom.fieldName}`"
                      v-if="custom.fieldType == 'number'"
                      :disabled="isView"
                    ></el-input-number>
                    <el-date-picker
                      v-model="form[custom.aavName]"
                      type="datetime"
                      :placeholder="`请选择${custom.fieldName}`"
                      value-format="yyyy-MM-dd hh:mm:ss"
                      v-if="custom.fieldType == 'date'"
                      :disabled="isView"
                    >
                    </el-date-picker>
                    <!-- <el-input v-model="form[custom.aavName]" v-show="false"></el-input> -->
                    <el-checkbox-group
                      v-model="form[custom.aavName + 'rander']"
                      v-if="custom.fieldType == 'checkbox'"
                      @change="checkboxChange($event, custom.aavName)"
                    >
                      <el-checkbox
                        :label="checkItem.id"
                        v-for="checkItem in custom.childAttributes"
                        :value="checkItem.id"
                        :key="checkItem.id"
                        :disabled="isView"
                        >{{ checkItem.fieldName }}</el-checkbox
                      >
                    </el-checkbox-group>

                    <div v-if="custom.fieldType == 'boxradio'">
                      <el-checkbox
                        v-for="(checkItem, i) in custom.childAttributes"
                        :key="checkItem.id"
                        :value="checkItem.ischeck"
                        @change="
                          boxradioCheckClick($event, custom, checkItem, i)
                        "
                      >
                        {{ checkItem.fieldName }}
                        <el-radio-group
                          class="boxradioRadioBox"
                          :class="{ show: checkItem.id == boxradioIsShow }"
                          v-model="form[custom.aavName + i]"
                          @change="boxradioChange($event, checkItem, custom, i)"
                        >
                          <el-radio
                            v-for="item2 in checkItem.childGameAttributes"
                            :key="item2.id"
                            :label="item2.id"
                            >{{ item2.fieldName }}</el-radio
                          >
                        </el-radio-group>
                      </el-checkbox>
                    </div>
                    <div class="flex">
                      <el-select
                        v-model="form[custom.aavName]"
                        placeholder="请选择"
                        v-if="custom.fieldType == 'radio'"
                        :disabled="isView"
                      >
                        <el-option
                          v-for="item in custom.childAttributes"
                          :key="item.id"
                          :label="item.fieldName"
                          :value="item.id"
                        >
                        </el-option>
                      </el-select>
                      <div
                        class="sm_box"
                        style="margin-left: 10px"
                        v-if="custom.fieldKey == 'sm' && form.shimingImg"
                      >
                        <el-image
                          :preview-src-list="[form.shimingImg]"
                          :src="form.shimingImg"
                          alt=""
                          style="width: 100px; height: 100px; cursor: pointer"
                        />
                      </div>
                    </div>
                  </el-form-item>
                  <div
                    v-if="
                      custom.fieldType == 'checkbox' ||
                      custom.fieldType == 'boxradio'
                    "
                  ></div>
                </span>
              </div>
              <el-divider content-position="left">图片信息</el-divider>
              <el-row>
                <el-form-item label="视频" prop>
                  <el-upload
                    class="upload-demo"
                    :headers="headers"
                    :action="uploadVideoUrl"
                    :on-remove="handleVideoRemove"
                    :on-success="handleVideoSuccess"
                    multiple
                    :limit="1"
                    accept="mp4"
                    :file-list="videoList"
                  >
                    <el-button size="small" type="primary" v-if="!isView"
                      >点击上传</el-button
                    >
                  </el-upload>
                </el-form-item>
              </el-row>
              <el-row :class="{ hiddenupload: titleImgList.length }">
                <el-form-item label="商品首图" prop="">
                  <div class="flex">
                    <el-input v-model="form.titleImg" v-show="false"></el-input>
                    <el-upload
                      class
                      :action="uploadImgUrl"
                      :file-list="titleImgList"
                      :show-file-list="true"
                      list-type="picture-card"
                      :limit="1"
                      :on-success="handleAvatarSuccess"
                      :on-remove="handleAvatarRemove"
                      :on-preview="handlePictureCardPreview"
                      :disabled="isView"
                    >
                      <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-button
                      plain
                      style="height: 35px; margin-left: 10px"
                      type="primary"
                      @click="openGetInfoDialog"
                      :loading="makeTitleImgBtnLoading"
                      v-if="!isView"
                    >
                      首图生成</el-button
                    >
                  </div>
                </el-form-item>
              </el-row>
              <el-row>
                <el-form-item label="详情图片" prop>
                  <div class="flex">
                    <el-upload
                      :action="uploadImgUrl"
                      :file-list="imgShowList"
                      list-type="picture-card"
                      :on-preview="handlePictureCardPreview"
                      :on-exceed="uploadNumber"
                      :multiple="true"
                      :drag="true"
                      :on-success="handleUploadSuccess"
                      :limit="50"
                      :on-remove="handleRemove"
                      :http-request="uploadImgs"
                      :disabled="isView"
                    >
                      <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-button
                      plain
                      style="height: 35px; margin-left: 10px"
                      type="primary"
                      @click="getDetailImgs"
                      :loading="makeDetailImgBtnLoading"
                      v-if="!isView"
                    >
                      详情图生成</el-button
                    >
                  </div>
                </el-form-item>
              </el-row>
              <el-form-item label="图片展示方式" prop="imgShow">
                <el-select
                  v-model="form.imgShow"
                  filterable
                  placeholder="请选择"
                  :disabled="isView"
                >
                  <el-option
                    v-for="dict in imgOptions"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </div>
            <div style="height: 60px"></div>
            <!-- <div class="center" v-if="!isView"> -->
            <!-- <el-button v-if="step != 1 && !isEdit" @click="preStep"
                >上一步</el-button
              >
              <el-button
                type="primary"
                @click="nextStep('form' + index)"
                v-if="step != 2 && !isEdit"
                >下一步</el-button
              > -->
            <div style="text-align: center">
              <el-button @click="close()">取消</el-button>
              <el-button
                type="primary"
                @click="submitForm('form' + index)"
                :loading="loading"
                >提交</el-button
              >
            </div>
            <!-- </div> -->
          </el-form>
        </div>
      </el-tab-pane>
    </el-tabs>
    <!-- 展示和平营地授权二维码 -->
    <el-dialog
      title="营地授权"
      :visible.sync="roleVisible"
      width="310px"
      append-to-body
    >
      <img
        style="width: 290px; height: 290px; text-align: center"
        :src="poxiaoHpQrcode"
        frameborder="0"
      />
      <div style="text-align: center; margin-top: 30px">
        <el-button
          type="primary"
          size="mini"
          plain
          @click="getHpInfo"
          style="margin-left: 20px"
          :loading="btnLoading"
          >我已授权</el-button
        >
      </div>
    </el-dialog>

    <!-- <GetInfoDialog ref="getInfoDialog" @autoGetSubmit="autoGetSubmit"></GetInfoDialog> -->
    <selectPhone ref="selectPhone" @change="getSelectPhone"></selectPhone>
  </el-dialog>
</template>

<script>
import ImageCompressor from 'image-compressor.js'
import { postUpload } from "@/api/upload";
import selectPhone from "./selectPhone.vue"
import gameConfigApi from "../../../api/gameConfig";
import goodsListApi from "../../../api/gameConfig/goodsList";
import gameListApi from "@/api/gameConfig/gameList"
import gameDictionaryApi from "../../../api/gameConfig/gameDictionary";
import infoApi from "@/api/autoProduction/information.js";
import makeImgsV3 from "@/utils/makeImgs-v3";
import shengyu from '../../../components/yudu/shengyu.vue';
import { getToken } from '@/utils/auth'
import { getInfo } from '@/api/login'
export default {
  name: "addGoods",
  components: { selectPhone, shengyu },
  data() {
    var validatePass = (rule, value, callback) => {
      if (!value) {
        callback();
      } else if (!/^1[3-9][0-9]\d{8}$/.test(value)) {
        callback(new Error('手机号不正确!'));
      } else {
        callback();
      }
    };
    return {
      step: 1,//发布账号步骤
      title: "新增游戏账号",
      pushAllList: [],
      pushGroupList: [],
      pushGroup: '',//选择的渠道分组
      checkboxGroup1: [],
      pegaType: null,
      guaishouMax: null,
      admins: null,
      activedId: null,
      qufuSelect: [],
      qufuList: [],
      goodsTypeList: [],
      AttributioneList: [],
      hireStateOptions: [
        {
          label: '不出租',
          value: 1
        },
        {
          label: '待出租',
          value: 2
        },
        {
          label: '出租中',
          value: 3
        },
      ],
      imgOptions: [
        {
          label: '小图',
          value: 1
        },
        {
          label: '大图',
          value: 2
        }
      ],


      dialogVisible: false,
      btnLoading: false,
      makeTitleImgBtnLoading: false,
      makeDetailImgBtnLoading: false,
      loading: false,
      isEdit: false,
      isView: false,
      roleVisible: false,
      poxiaoHpQrcode: '',
      actived: "",
      activedIndex: 0,
      roleList: [],//王者荣耀角色列表
      gameList: [],
      // 表单参数
      form: {
        type: "",
        des: '',
        securityPhone: '',
        bindEmail: '',
        serverId: '',
        hireState: 1,
      },
      selectedPhone: '',
      sysList: [],//系统列表
      carrierList: [],//运营商列表
      areaList: [],//大区列表
      serverList: [],//服务器列表
      fenluList: [],//分路列表
      IsBaopei: [
        { name: '默认', id: 0 },
        { name: '需要包赔', id: 1 },
        { name: '免费包赔', id: 2 }
      ],
      accountList: [
        { name: '官方自营', id: 1 },
        { name: '官方代售', id: 2 }
      ],
      imgFileList: [],
      imgShowList: [],
      titleImgList: [],
      videoList: [],
      ifSpecial: false,
      uploadImgUrl: "",
      uploadVideoUrl: '',
      headers: {},
      customAttr: null,
      newImages: [],//编辑的时候新增加的图片，新增的时候新增加的图片
      attrs: [],
      boxradioIsShow: '',
      qrsig: '',
      rules: {
        title: [
          { required: true, message: "请输入商品标题", trigger: "change" }
        ],
        systemId: [
          { required: true, message: "请选择系统", trigger: "change" }
        ],
        carrierId: [
          { required: true, message: "请选择运营商", trigger: "change" }
        ],
        groupId: [
          { required: true, message: "请选择大区", trigger: "blur" }
        ],
        serverId: [
          { required: true, message: "请选择服务器", trigger: "blur" }
        ],
        price: [{ required: true, message: "请输入售价", trigger: "change" }],
        des: [{ required: true, message: "请输入简介", trigger: "change" }],
        accountNumber: [{ required: true, message: "请输入账号", trigger: "blur" }],
        // securityPhone: [{ validator: validatePass, trigger: 'blur' }]
        // level
      },

    };
  },
  created() {
    this.headers = {
      Authorization: 'Bearer ' + getToken()
    }
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.uploadVideoUrl = process.env.VUE_APP_BASE_API + "/upload/video";
    // this.getPushList()
    // this.getPushGroupList()
  },
  methods: {
    // 打开弹窗
    async open(flag, data, activedId) {
      this.pegaType = null
      this.guaishouMax = null
      this.admins = null
      this.activedId = activedId
      this.form = {
        thirdId: '',
        f1: '',
        f2: '',
        f3: '',
        f4: '',
        f5: '',
        f6: '',
        f7: '',
        f8: '',
        f9: '',
        f10: '',
        f11: '',
        f12: '',
        f13: '',
        f14: '',
        f15: '',
        f16: '',
        f17: '',
        f18: '',
        f19: '',
        f20: '',
        f21: '',
        f22: '',
        f23: '',
        f24: '',
        f25: '',
        f26: '',
        f27: '',
        f28: '',
        f29: '',
        f30: '',
        f31: '',
        f32: '',
        f33: '',
        f34: '',
        f35: '',
        f36: '',
        f37: '',
        f38: '',
        f39: '',
        f40: '',
        systemId: '',
        groupId: '',
        carrierId: '',
        serverId: '',
        goodsTypeValues: [],
        accountType: 1
      };
      if(data && data.accountType)  {
        this.form.accountType = data.accountType
      }
      let lodaInfo = this.$route.query
      console.log('lodaInfo', lodaInfo);
      this.pegaType = lodaInfo.type
      await this.getGameList()
      this.dialogVisible = true;
      this.getLodinRoles()
      if (flag == "edit") {
        this.title = "修改游戏账号";
        this.isEdit = true;
        this.isView = false
        this.handleUpdate(data);
      } else if (flag == "view") {
        this.title = "查看游戏账号";
        this.isEdit = false;
        this.isView = true
        this.handleUpdate(data);
      } else {
        this.title = "新增游戏账号";
        this.tabChange();
        this.isEdit = false;
        this.isView = false
      }
    },
    async getLodinRoles() {
      const res = await getInfo()
      console.log('res', res);
      const admins = res.roles.filter(user => user === 'admin');
      console.log(admins);
      this.admins = admins[0]
      const guaishouMax = res.roles.filter(user => user === 'guai_shou_max');
      console.log(guaishouMax);
      this.guaishouMax = guaishouMax[0]
    },
    //下一步
    nextStep(formName) {
      this.$refs["" + formName][0].validate(valid => {
        if (valid) {
          // 如果标题为空 生成标题
          if (!this.form.title) {
            this.form.title = this.form.des.substr(0, 50)
          }
          this.step += 1
        }
        else {
          this.$alert("请填写完整！");
        }
      })

    },
    // 上一步
    preStep() {
      this.step -= 1
    },
    // 获取分路情况列表
    getFenluList() {
      gameDictionaryApi.getChildList({
        gameId: this.form.gameId,
        systemKey: 'branchroad'
      }).then(res => {
        this.fenluList = res.rows

      })
    },
    // fenluChange 分路改变
    fenluChange(e) {
      if (e) {
        let arr = this.fenluList.filter(t => {
          return t.id == e
        })
        this.form.branchRoadTypeName = arr[0].fieldName
        // this.form.branchRoadValue = ' '

      } else {
        this.form.branchRoadTypeName = ''
        // this.form.branchRoadValue = ''
      }
    },
    // 获取商品类型列表
    getGoodsTypeList() {
      gameDictionaryApi.getChildList({
        gameId: this.form.gameId,
        systemKey: 'goodstype'
      }).then(res => {
        this.goodsTypeList = res.rows
        console.log("%c Line:501 🥐 this.goodsTypeList", "color:#7f2b82", this.goodsTypeList);
      })
    },
    getAttributionList() {
      gameDictionaryApi.getChildList({
        gameId: this.form.gameId,
        systemKey: 'Attribution'
      }).then(res => {
        this.AttributioneList = res.rows
      })
    },
    // 商品类型改变
    goodsTypeChanges(e) {
      console.log("%c Line:500 🥑 e", "color:#42b983", e);

    },
    // 获取推送渠道
    getPushList() {
      goodsListApi.pushAllList().then(res => {
        this.pushAllList = res.rows
      })
    },
    // 获取渠道分组列表
    getPushGroupList() {
      goodsListApi.pushGroupList().then(res => {
        this.pushGroupList = res.rows
      })
    },
    // 保存分组
    addGroup() {
      if (this.checkboxGroup1.length == 0) {
        return this.$alert('请先选择推送渠道！')
      }
      this.$prompt('分组名称', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请填写分组名称'
      }).then(({ value }) => {
        let names = []
        this.pushAllList.forEach(r => {
          if (this.checkboxGroup1.indexOf(r.id) > -1) {
            names.push(r.name)
          }
        })
        let obj = {
          name: value,
          configIds: this.checkboxGroup1.join(','),
          configNames: names.join(',')
        }
        goodsListApi.addPushGroup(obj).then(res => {
          this.checkboxGroup1 = []
          this.getPushGroupList()
        })
      }).catch(() => {

      });



    },
    // 打开选择手机弹窗
    openSelectPhone() {
      this.$refs.selectPhone.open()
    },
    // 选择绑定手机
    getSelectPhone(item) {
      this.form.telid = item.id
      this.selectedPhone = item.type + '/' + (item.num ? item.num + '/' : '') + (item.tel ? item.tel : '')
      this.form.telnote = this.selectedPhone
      if (!this.form.securityPhone) {
        this.form.securityPhone = item.tel ? item.tel : ''
      }
    },
    // 取消绑定手机
    delPhone() {
      this.selectedPhone = ''
      this.form.telid = ''
      this.form.telnote = ''
    },
    // 游戏列表
    async getGameList() {
      let res = await gameListApi.getGameList()
      // console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows

      if (res.rows && res.rows.length) {
        this.actived = res.rows[0].name
        this.form.gameId = res.rows[0].id
        this.form.gameType = res.rows[0].type
        this.form.gameName = res.rows[0].name

        this.getSysList()
        this.getYysList()

        if (res.rows[0].type == 1) {
          // 端游
          this.getAreaList()
        }
      }
    },
    // 游戏类型切换
    // tabs切换时触发
    tabChange(e) {
      this.activedIndex = e ? e.index : 0
      // this.$refs["form" + this.activedIndex][0].resetFields()
      this.form.gameName = this.actived;
      if (this.gameList && this.gameList.length) {
        this.gameList.forEach(item => {
          // console.log("%c Line:231 🍿 item", "color:#42b983", item);
          if (item.name == this.actived) {
            this.form.gameId = item.id
            this.form.gameType = item.type
            this.getGoodsTypeList()
            this.getAttributionList()
            this.getFenluList()
            if (item.type == 1) {
              // 端游
              this.getAreaList()
            } else {
              this.getSysList()
              this.getYysList()
            }
          }
        })
      }
      this.selectDialogGameType();
    },
    // 选择区服改变
    handleChange(e) {
      console.log("%c Line:543 🍒 选择区服改变", "color:#465975", e);
      if (this.form.gameType == 1) {
        // 端游
        this.form.groupId = e[0]
        this.form.serverId = e[1]
        this.handleChangeexpand('cas2')
      } else {
        // 手游
        if (e.length == 4) {
          this.form.systemId = e[0]
          this.form.carrierId = e[1]
          this.form.groupId = e[2]
          this.form.serverId = e[3]
          this.handleChangeexpand('cas1')
        } else {
          this.$alert('区服信息配置错误，请联系管理员反馈！')
        }
      }
    },
    // 区服选择 展开改变
    handleChangeexpand(str) {
      console.log("%c Line:596 🍡 获取选中节点", "color:#33a5ff", this.$refs[str][0].getCheckedNodes()[0]);
      let labels = this.$refs[str][0].getCheckedNodes()[0].pathLabels
      if (str == 'cas1') {
        this.form.systemName = labels[0]
        this.form.carrierName = labels[1]
        this.form.groupName = labels[2]
        this.form.serverName = labels[3]
      } else if (str == 'cas2') {
        this.form.groupName = labels[0]
        this.form.serverName = labels[1]
      }

    },
    /**
     *
     *  王者荣耀个性化营地获取 start
     */

    //获取角色列表 f17是营地ID
    getRoleList() {
      // this.$refs.shengyu
      console.log("%c Line:644 🍰 this.$refs.shengyu", "color:#f5ce50", this.$refs.shengyu);
      if (this.form.f17) {
        this.btnLoading = true;
        infoApi
          .getRoleList({
            campId: this.form.f17
          })
          .then(res => {
            if (res.data) {
              this.btnLoading = false;
              this.roleList = res.data;
              if (this.roleList.length) {
                //遍历添加展示数据
                this.roleList.forEach(item => {
                  item.zdytext = `${item.areaName}/${item.roleJob}/${item.roleName}`;
                });
                // this.formData.roleValue = this.roleList[0].zdytext;
                this.form.roleId = this.roleList[0].roleId;
                this.getRoleInfo(this.roleList[0].roleId);
              }
            }
          })
          .catch(err => {
            this.btnLoading = false;
          });
      } else {
        this.$alert("请先填写营地ID！");
      }
    },
    //角色选中
    roleSelect(e) {
      this.getRoleInfo(e);
    },
    //获取角色详情
    getRoleInfo(roleId) {
      if (roleId) {
        this.btnLoading = true;
        infoApi
          .getRoleInfo({
            campId: this.form.f17,
            roleId: roleId
          })
          .then(res => {
            this.btnLoading = false;
            this.showCheckbox(res)
            this.$refs.shengyu[0].getNumber()
          }).catch(err => {
            this.$alert(err);
          })
      } else {
        this.$alert("请先选择角色信息！");
      }
    },
    //  回显  拼装简介
    showCheckbox(res) {
      console.log("%c Line:436 🌰 res", "color:#e41a6a", res);
      let data = res.data
      let str = ''
      if (data.listJson && data.listJson.length) {
        data.listJson.forEach(r => {
          str = str + r.szHeroTitle + '-' + r.szTitle + ' '
        })
      }
      this.$set(this.form, 'systemId', data.skinMap.systemId)
      this.$set(this.form, 'carrierId', data.skinMap.carrierId)
      this.$set(this.form, 'groupId', data.skinMap.groupId)
      this.$set(this.form, 'serverId', data.skinMap.serverId)
      // this.form.systemId = data.skinMap.systemId
      // this.form.carrierId = data.skinMap.carrierId
      // this.form.groupId = data.skinMap.groupId
      // this.form.serverId = data.skinMap.serverId
      // this.qufuSelect = [data.skinMap.systemName,data.skinMap.carrierName,data.skinMap.groupName,data.skinMap.serverName]
      this.qufuSelect = [data.skinMap.systemId, data.skinMap.carrierId, data.skinMap.groupId, data.skinMap.serverId]

      this.$set(this.form, 'f6', data.skinMap.rongyaoCount)
      this.$set(this.form, 'f8', data.skinMap.skinNum)
      this.$set(this.form, 'f7', data.skinMap.heroNum)
      this.$set(this.form, 'f4', data.skinMap.totalCount)

      let str2 = `${data.skinMap.areaName}-贵族积分${data.skinMap.totalCount}-V${data.skinMap.vipLevel}-${data.skinMap.skinNum}皮肤-${data.skinMap.heroNum}英雄 `
      this.$set(this.form, 'des', str2 + str + ` 营地ID:${this.form.f17} `)
      this.zidongChange()
      if (data.skinMap.carrierId && data.skinMap.groupId) {
        this.getYysList(true, data.skinMap.carrierId, data.skinMap.groupId)
      }
      if (this.sysList && this.sysList.length) {
        this.sysList.forEach(r => {
          if (r.id == this.form.systemId) {
            this.form.systemName = r.fieldName
          }
        })
      }
      // 找出游戏下标
      let index = 0
      this.gameList.forEach((t, i) => {
        if (t.name == this.actived) {
          index = i
        }
      })
      this.$refs["form" + index][0].validate(valid => {

      })
    },
    /**
         *
         *  王者荣耀个性化营地获取 end
         */
    /**
     *
     *  和平精英个性化营地获取 start
     */

    //获取角色列表 f13是营地ID
    getQRcode() {
      console.log("%c Line:644 🍰 this.$refs.shengyu", "color:#f5ce50", this.$refs.shengyu);
      if (!this.form.carrierId) {
        this.$alert("请先选择运营商！");
        return
      }
      this.btnLoading = true;
      infoApi
        .getHpQRcode({
          yingdi: this.form.f13,
          authType: this.form.carrierId == '38838' ? '1' : '2'
        })
        .then(res => {
          console.log("%c Line:465 🍎 和平营地授权二维码res", "color:#6ec1c2", res);
          if (res.data) {
            this.btnLoading = false;
            this.roleVisible = true
            this.poxiaoHpQrcode = "data:image/png;base64," + res.data.qrcode;
            this.qrsig = res.data.qrsig

          }
        })
        .catch(err => {
          this.btnLoading = false;
        });
    },
    getHpInfo() {
      this.btnLoading = true;
      infoApi.getHPDatacode({
        qrsig: this.qrsig,
        authType: this.form.carrierId == '38838' ? '1' : '2'
      }).then(res => {
        if (res.code == 200) {
          this.$refs.shengyu[0].getNumber()
          this.roleVisible = false
          this.btnLoading = false;
          // this.form.f11 = res.data.fenseNum
          this.form.f16 = res.data.wangpaiLevel
          this.form.f9 = res.data.qiangxie
          this.form.f12 = res.data.texiao
          this.form.f8 = res.data.shizhuang
          this.form.f7 = res.data.zaiju
          this.form.systemId = res.data.systemGroup.systemId
          this.form.carrierId = res.data.systemGroup.carrierId
          this.form.groupId = res.data.systemGroup.groupId
          this.form.serverId = res.data.systemGroup.serverId
          if (res.data.skilList) {
            res.data.skilList.forEach(r => {
              if (r.list && r.list.length) {
                if (r.type == '粉装') {
                  this.form.f11 = r.list.length
                }
              }
            })
          }
          if (this.form.carrierId && this.form.groupId) {
            this.getYysList(true, this.form.carrierId, this.form.groupId)
          }
          if (this.sysList && this.sysList.length) {
            this.sysList.forEach(r => {
              if (r.id == this.form.systemId) {
                this.form.systemName = r.fieldName
              }
            })
          }
          this.showHpCheckbox(res.data)
        }
      }).catch(err => {
        this.$alert(err);
      })
    },
    //  回显  拼装简介
    showHpCheckbox(res) {
      let data = res
      let str = ''
      let arr
      let arrOnce = []
      if (data.skilList) {
        arr = data.skilList
        console.log("%c Line:518 🍡 营地数据arr", "color:#4fff4B", arr);
      }
      let zaijushu = 0
      if (arr && arr.length) {
        arr.forEach(r => {
          if (r.list && r.list.length) {
            str = `${str} 【${r.list.length}${r.type}】`
            if (r.type == '载具' && r.list.length) {
              zaijushu = r.list.length
            }
            r.list.forEach(r2 => {
              arrOnce.push(r2)
              if (r2.level) {
                str = `${str} ${r2.name}-${r2.level}级`
              } else {
                str = `${str} ${r2.name}`
              }
            })
          }
        })
      }
      let str2 = `${this.form.f16}王牌印记-${this.form.f11}粉装-${data.texiao}特效枪-${data.qiangxie}枪皮-${data.shizhuang}套装-${zaijushu}载具 `
      this.form.des = str2 + str + ` 营地ID:${this.form.f13} `
      // 回显
      // 回显勾选
      this.attrs.forEach(item => {
        // if (this.form[item.aavName]) {
        if (item.fieldType == "checkbox") {
          item.childAttributes.forEach(t2 => {
            arrOnce.forEach(t3 => {
              if (t3 && (t2.fieldName == t3.name)) {
                if (this.form[item.aavName + 'rander'].indexOf(t2.id) == -1) {
                  this.form[item.aavName + 'rander'].push(t2.id)
                }
              }
            })
          });
          this.form[item.aavName] = this.form[item.aavName + 'rander'].join(',')
          this.$forceUpdate()
        }
        // }
      });
      // 找出游戏下标
      let index = 0
      this.gameList.forEach((t, i) => {
        if (t.name == this.actived) {
          index = i
        }
      })
      this.$refs["form" + index][0].validate(valid => {

      })
    },
    /**
         *
         *  和平精英个性化营地获取 end
         */

    // 获取系统列表
    getSysList() {
      gameDictionaryApi.getChildList({
        gameId: this.form.gameId,
        systemKey: 'system'
      }).then(res => {
        this.sysList = res.rows

        this.sysList.forEach(i => {
          if (this.form.systemId && i.id == this.form.systemId) {
            this.form.systemName = i.fieldName
            i.leaf = false
          }
        })
        if (this.form.gameType == 1) {
          // 端游
        } else {
          // 手游
          this.qufuList = this.sysList
        }
      })
    },
    // 编辑时回显手游区服

    // 编辑时回显 大区 服务器
    reViewArea() {
      this.form.gameType = Number(this.form.gameType)
      /**  this.form.systemId = res.data.systemGroup.systemId
          this.form.carrierId = res.data.systemGroup.carrierId
          this.form.groupId = res.data.systemGroup.groupId
          this.form.serverId = res.data.systemGroup.serverId */
      // this.qufuSelect = [this.form.systemId,this.form.carrierId, this.form.groupId,this.form.serverId]
      if (this.form.gameType == 1) {
        // 端游
        // let obj = {
        //         gameId: this.form.gameId,
        //         systemKey: 'group'
        //       }
        // gameDictionaryApi.getChildList(obj).then(res => {
        //   let index = 0
        //   res.rows.forEach((r, ind) => {
        //     r.leaf = false
        //     if (r.id == this.form.groupId) {
        //       index = ind
        //     }
        //   })
        //   this.qufuList = res.rows
        //   gameDictionaryApi.getList({
        //     parentId: this.form.groupId
        //   }).then(res2 => {
        //     res2.rows.forEach(i => {
        //       i.leaf = true
        //     })
        //     this.qufuList[index].children = res2.rows

        //   })
        // })


        this.getAreaList('', true, this.form.groupId)
      } else {
        // // 手游
        // let carrierKey = ''
        // // 获取系统
        // gameDictionaryApi.getChildList({
        //   gameId: this.form.gameId,
        //   systemKey: 'system'
        // }).then(res => {
        //   this.qufuList = res.rows
        //   // let arr = this.qufuList.filter(t => {
        //   //   return t.id == this.form.systemId
        //   // })
        //   gameDictionaryApi.getChildList({
        //     gameId: this.form.gameId,
        //     systemKey: 'carrier'
        //   }).then(res2 => {
        //     this.qufuList.forEach(r=>{
        //       if(r.id==this.form.systemId){
        //         r.children = res2.rows
        //       }
        //     })
        //     // this.$set(arr, 'children', res2.rows)
        //     let carrierItem = ''
        //     res2.rows.forEach(item2 => {
        //       if (item2.id == this.form.carrierId) {
        //         carrierKey = item2.systemKey
        //         carrierItem = item2
        //       }
        //       // 获取大区
        //       let obj = {
        //         gameId: this.form.gameId,
        //         systemKey: carrierKey + 'group'
        //       }
        //       gameDictionaryApi.getChildList(obj).then(res3 => {
        //         this.$set(carrierItem, 'children', res3.rows)

        //         let groupItem =  res3.rows.filter(t=>{
        //           return t.id==this.form.groupId
        //         })
        //         // 获取服务器
        //         gameDictionaryApi.getList({
        //           parentId: this.form.groupId
        //         }).then(res4 => {
        //           res4.rows.forEach(i => {
        //             i.leaf = true
        //           })
        //           this.$set(groupItem, 'children', res4.rows)
        //           console.log('qufuList',this.qufuList)

        //         })

        //       })

        //     })
        //   })
        // })

        this.getSysList()
        this.getYysList(true, this.form.carrierId, this.form.groupId)
      }

    },
    // 系统改变
    sysChange(e) {
      if (e) {
        this.sysList.forEach(i => {
          if (i.id == e) {
            this.form.systemName = i.fieldName
          }
        })
      } else {
        this.form.systemName = ''
      }
    },
    // 服务器改变
    serverChange(e) {
      // console.log("%c Line:353 🍺 e", "color:#7f2b82", e);
      if (e) {
        this.serverList.forEach(i => {
          if (i.id == e) {
            this.form.serverName = i.fieldName
            this.$set(this.form, 'serverId', e)
            this.$forceUpdate()
            // this.form.serverId = e
          }
        })
      } else {
        this.form.serverName = ''
      }
    },
    // 获取运营商列表
    getYysList(flag, yysId, areaId) {
      gameDictionaryApi.getChildList({
        gameId: this.form.gameId,
        systemKey: 'carrier'
      }).then(res => {
        this.carrierList = res.rows
        if (flag) {
          // 回显的时候，获取下一级
          this.getAreaList(yysId, true, areaId)
        }
      })
    },
    // 运营商改变  获取大区列表
    getAreaList(e, flag, areaId) {
      let obj = {
        gameId: this.form.gameId,
        systemKey: 'group'
      }
      if (!flag) {
        // 不是回显的时候，再清除后面的选中
        this.form.groupName = ''
        this.form.groupId = ''
        this.form.serverName = ''
        this.form.serverId = ''
      } else {
        // 回显时，请求服务器列表
        this.getServiceList(areaId, true)
      }

      // 运营商改变 找出选择的运营商的对象
      if (e) {
        this.carrierList.forEach(i => {
          if (i.id == e) {
            obj.systemKey = i.systemKey + 'group'
            this.form.carrierName = i.fieldName
          }
        })
      } else {
        this.form.carrierName = ''
      }
      gameDictionaryApi.getChildList(obj).then(res => {
        this.areaList = res.rows
        this.areaList.forEach(r => {
          r.leaf = false
          if (this.form.groupId && r.id == this.form.groupId) {
            this.form.groupName = r.fieldName
          }
        })
        if (this.form.gameType == 1) {
          // 端游
          this.qufuList = this.areaList
        }
      })
    },
    // 大区改变  获取服务器列表
    getServiceList(e, flag) {
      if (!flag) {
        // 不是回显的时候，再清除后面的选中
        this.form.serverName = ''
        this.form.serverId = ''
      }

      if (e) {
        gameDictionaryApi.getList({
          parentId: e
        }).then(res => {
          this.serverList = res.rows
          if (this.form.serverId) {
            this.serverList.forEach(i => {
              if (i.id == this.form.serverId) {
                this.form.serverName = i.fieldName
              }
            })
          }
        })
        this.areaList.forEach(i => {
          if (i.id == e) {
            this.form.groupName = i.fieldName
          }
        })
      } else {
        this.form.groupName = ''
        this.serverList = []
      }
    },
    // 获取游戏属性列表
    getAttrList() {
      this.loading = true
      gameConfigApi.getGameConfig({ gameId: this.form.gameId }).then(res => {
        this.loading = false
        if (res.code == 200) {
          let attrs = res.rows
          if (attrs.length) {
            attrs.forEach(r => {
              if (r.fieldType == 'checkbox') {
                this.$set(this.form, r.aavName + 'rander', [])
                if (r.fieldType == 'boxradio') {
                  r.childAttributes.forEach(first => {
                    first.ischeck = false
                  })
                }
              } else {
                this.$set(this.form, r.aavName, '')
              }
              if (r.isEnable == '0' && r.must == 'true') {
                if (r.fieldType == 'checkbox') {
                  this.rules[r.aavName + 'rander'] = [
                    {
                      required: true,
                      message: `请选择${r.fieldName}`,
                      trigger: "change"
                    }
                  ];
                } else {
                  this.$set(this.rules, r.aavName, [
                    {
                      required: true,
                      message: `请输入${r.fieldName}`,
                      trigger: "change"
                    }
                  ])
                }

              } else {
                if (this.rules[r.aavName]) {
                  delete this.rules[r.aavName]
                }
              }
            });

          }

          this.attrs = JSON.parse(JSON.stringify(attrs))
          // this.$nextTick(()=>{
          //   this.$refs["form" + this.activedIndex][0].resetFields()
          // })

        }
      });
    },
    // 多选项改变
    checkboxChange(e, key) {
      if (e && e.length) {
        this.form[key] = e.join(',')
      } else {
        this.form[key] = ''
      }
    },
    // 点击多选框单选的多选框点击事件
    boxradioCheckClick(e, custom, checkItem, i) {
      if (this.boxradioIsShow == checkItem.id) {
        this.boxradioIsShow = ''
      } else {
        this.boxradioIsShow = checkItem.id
      }
      checkItem.ischeck = false
      checkItem.fieldName = checkItem.fieldName.split('(')[0]
      this.setValue(custom)
    },
    // 点击多选单选框中的单选框事件
    boxradioChange(e, checkItem, custom, i) {
      this.$nextTick(() => {
        this.boxradioIsShow = ''
        checkItem.ischeck = true
        checkItem.childGameAttributes.forEach(r => {
          if (r.id == this.form[custom.aavName + i]) {
            checkItem.fieldName = checkItem.fieldName.split('(')[0] + '(' + r.fieldName + ')'
          }
        })
        this.setValue(custom)
      })

    },
    // 原神多选框赋值
    setValue(custom) {
      // 赋值
      let arr = []
      custom.childAttributes.forEach((item, ind) => {
        if (item.ischeck) {
          // 多选框选中 取子级单选选中的id
          arr.push(this.form[custom.aavName + ind])
        }
      })
      if (arr.length) {
        this.form[custom.aavName] = arr.join(',')
      } else {
        this.form[custom.aavName] = ''
      }
      console.log('this.form[custom.aavName]', this.form[custom.aavName])
      this.$forceUpdate()
    },
    // 重置
    reset() {
      this.step = 1
      this.btnLoading = false
      this.makeTitleImgBtnLoading = false
      this.makeDetailImgBtnLoading = false
      this.dialogVisible = false
      this.resetForm()
      // this.form = {};
      this.form.goodsTypeValues = []
      // this.$refs["form" + this.activedIndex][0].resetFields()
      // console.log("%c Line:1161 🥓 this.$refs[this.activedIndex]", "color:#f5ce50", this.$refs);
      this.selectedPhone = ''
      this.imgFileList = [];
      this.imgShowList = [];
      this.titleImgList = [];
      this.videoList = [];
      this.newImages = []
      this.attrs = []
    },
    /** 查询详情进行回显 */
    handleUpdate(row) {
      const id = row.id || null;
      // this.wzry = false;
      this.loading = true
      goodsListApi.getGoodsDetail(id).then(response => {
        // console.log("%c Line:435 🍧 response", "color:#b03734", response);
        // 请求自定义字段
        gameConfigApi.getGameConfig({ gameId: response.data.gameId }).then(res => {
          this.loading = false
          if (res.code == 200) {
            let attrs = res.rows
            if (attrs.length) {
              attrs.forEach(r => {
                if (r.fieldType == 'checkbox') {
                  this.$set(response.data, r.aavName + 'rander', response.data[r.aavName] ? response.data[r.aavName].split(',') : [])
                  let arr = []
                  if (response.data[r.aavName + 'rander'] && response.data[r.aavName + 'rander'].length) {
                    response.data[r.aavName + 'rander'].forEach(t => {
                      arr.push(Number(t))
                    })
                  }
                  response.data[r.aavName + 'rander'] = arr
                } else if (r.fieldType == 'boxradio') {
                  // 原神的特殊多选，多选套单选的数据处理
                  if (response.data[r.aavName]) {
                    // 如果有选中项
                    let idsArr = response.data[r.aavName].split(',')
                    if (r.childAttributes && r.childAttributes.length != 0) {
                      r.childAttributes.forEach((first, ind) => {
                        if (first.childGameAttributes && first.childGameAttributes.length != 0) {
                          first.childGameAttributes.forEach((sec) => {
                            if (idsArr.includes(String(sec.id))) {
                              response.data[r.aavName + ind] = sec.id
                              first.ischeck = true
                              first.fieldName = first.fieldName + '(' + sec.fieldName + ')'
                            }
                          })
                        }
                      })
                    }
                  } else {
                    r.childAttributes.forEach(first => {
                      first.ischeck = false
                    })
                  }
                } else if (r.fieldType == 'radio') {
                  this.$set(response.data, r.aavName, response.data[r.aavName] ? Number(response.data[r.aavName]) : '')
                }
                if (r.isEnable == '0' && r.must == 'true') {
                  this.rules[r.aavName] = [
                    {
                      required: true,
                      message: `请输入${r.fieldName}`,
                      trigger: "change"
                    }
                  ];
                } else {
                  if (this.rules[r.aavName]) {
                    delete this.rules[r.aavName]
                  }
                }
              });
            }
            // 处理商品类型
            if (response.data.goodsTypeValues) {
              response.data.goodsTypeValues = response.data.goodsTypeValues.split(',')
            }
            this.attrs = JSON.parse(JSON.stringify(attrs))
            console.log("%c Line:913 🍡 this.attrs", "color:#2eafb0", this.attrs);
            this.form = JSON.parse(JSON.stringify(response.data))
            this.form.isGuarantee = Number(response.data.isGuarantee)
            console.log("%c Line:907 🍕 this.form", "color:#465975", this.form);
            this.selectedPhone = this.form.telnote
            this.actived = this.form.gameName
            this.reViewArea()
            this.getGoodsTypeList()
            this.getAttributionList()
            this.getFenluList()
            // this.props.lazyLoad = this.reViewArea

          }
        });
        if (response.data.urlArr) {
          this.imgFileList = response.data.urlArr;
          this.imgShowList = JSON.parse(JSON.stringify(response.data.urlArr));
        }
        if (response.data.titleImg) {
          this.titleImgList = [{ url: response.data.titleImg, name: "" }];
        }
        let video = {};
        this.videoList = [];
        if (response.data.videoUrl) {
          video.name = response.data.videoName;
          video.url = response.data.videoUrl;
          this.videoList[0] = video;
        }
      });
    },
    // 是否特价
    // changeSpecial() {
    //   if (1 == this.form.accType) {
    //     this.ifSpecial = false;
    //   } else {
    //     this.form.specialPrice = null;
    //   }
    // },
    // 游戏类型改变 初始化
    selectDialogGameType() {
      this.getAttrList()
    },

    // 根据简介自动识别多选项
    zidongChange() {
      let arr = []
      let tempArr = []
      if (this.form.des) {
        let desStr = this.form.des
        desStr = desStr.replace(/-/g, "")
        console.log("%c Line:1305 🍔 desStr", "color:#ffdd4d", desStr);
        arr = desStr.split(' ')
        console.log("%c Line:1283 🍷 arr", "color:#e41a6a", arr);
        tempArr = JSON.parse(JSON.stringify(arr))
        if (arr && arr.length) {
          arr.forEach(r => {
            if (r && r.indexOf(',')) {
              tempArr = tempArr.concat(r.split(','))
            }
          })
        }
        arr = tempArr
      }
      // console.log("%c Line:426 🍎 识别出的选项arr", "color:#465975", arr);
      // 回显勾选
      this.attrs.forEach(item => {
        // if (this.form[item.aavName]) {
        if (item.fieldType == "checkbox" && item.childAttributes) {
          item.childAttributes.forEach(t2 => {
            arr.forEach(t3 => {
              if (t3 && (t2.fieldName.replace(/-/g, "").indexOf(t3) > -1)) {
                if (this.form[item.aavName + 'rander'].indexOf(t2.id) == -1) {
                  this.form[item.aavName + 'rander'].push(t2.id)
                }
              }
            })
          });
          this.form[item.aavName] = this.form[item.aavName + 'rander'].join(',')
          this.$forceUpdate()
        } else if (item.fieldType == "boxradio" && item.childAttributes) {
          item.childAttributes.forEach((t2, t2Ind) => {
            t2.childGameAttributes.forEach(t4 => {
              arr.forEach(t3 => {
                // t2.fieldName.indexOf(t3) > -1
                // t4.fieldName == t3
                if (t3 && (t4.fieldName.indexOf(t3) > -1) && !t2.ischeck) {
                  this.form[item.aavName + t2Ind] = t4.id
                  t2.ischeck = true
                  if (this.form[item.aavName].indexOf(t4.id) == -1) {
                    t2.fieldName = t2.fieldName.split('(')[0]
                    t2.fieldName = t2.fieldName + '(' + t4.fieldName + ')'
                  }
                }
              })
            })
          });
          this.setValue(item)
        }
        // }
      });
    },
    /** 一键生成主图 */
    async makeTitleImg() {
      // 处理多选项，拼接duoxuan详情的LIST
      let list = []
      let img = ''
      let str = ''
      this.attrs.forEach(t => {
        if (t.fieldType == 'checkbox' || t.fieldType == 'boxradio') {
          if (this.form[t.aavName]) {
            str = str + this.form[t.aavName] + ','
          }
        }
      })
      // let res = await infoApi.getSelectList({
      //   ids: str
      // })
      // list = res.data
      // 生成
      let obj = JSON.parse(JSON.stringify(this.form))
      let list2 = JSON.parse(JSON.stringify(this.attrs))
      img = await makeImgsV3.makeTitleImgYjwj(obj, str, list2)
      return img
    },
    /** 一键生成详情图 */
    async makeDetailImgs() {
      // 处理多选项，拼接duoxuan详情的LIST
      let list = []
      let img = ''
      let str = ''
      this.attrs.forEach(t => {
        if (t.fieldType == 'checkbox') {
          if (this.form[t.aavName]) {
            str = str + this.form[t.aavName] + ','
          }
        }
      })
      let res = await infoApi.getSelectList({
        ids: str
      })
      list = res.data
      // 生成
      img = await makeImgsV3.makeDetailImg(list)
      return img
    },
    // 一键获取首图
    async openGetInfoDialog() {
      this.makeTitleImgBtnLoading = true
      let img = ''
      try {
        img = await this.makeTitleImg()
        this.makeTitleImgBtnLoading = false
      } catch {
        this.makeTitleImgBtnLoading = false
      }
      // 上传生成的base64图片
      let titImg = this.base64ToBlob(img);
      let formData = new FormData();
      formData.append("file", titImg);
      postUpload(formData)
        .then(res => {
          this.form.titleImg = res.msg
          this.titleImgList = [{ url: res.msg, name: "" }];
        })
        .catch(e => { });
    },
    // 一键获取详情图
    async getDetailImgs() {
      this.makeDetailImgBtnLoading = true
      let img = ''
      try {
        img = await this.makeDetailImgs()
        this.makeDetailImgBtnLoading = false
      } catch {
        this.makeDetailImgBtnLoading = false
      }
      // 上传生成的base64图片
      let detailImg = this.base64ToBlob(img);
      let formData = new FormData();
      formData.append("file", detailImg);
      postUpload(formData)
        .then(res => {
          this.imgFileList = [{ url: res.msg, name: "" }];
          this.imgShowList = [{ url: res.msg, name: "" }];
        })
        .catch(e => { });
    },
    // 图片转换
    base64ToBlob(code) {
      let parts = code.split(";base64,");
      let contentType = parts[0].split(":")[1];
      let raw = window.atob(parts[1]);
      let rawLength = raw.length;
      let uInt8Array = new Uint8Array(rawLength);

      for (let i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i);
      }
      return new Blob([uInt8Array], { type: contentType });
    },
    /**以下是图片上传相关 */
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$modal.msgError("最多只能上传50张图片");
    },

    handleVideoSuccess(res, file) {
      if (res.code == 200) {

        this.form.videoUrl = res.data.filePath;
        this.form.videoName = res.data.fileName;
      }
    },
    handleVideoRemove(response, fileList, file) {
      this.form.videoUrl = "";
      this.form.videoName = "";
    },
    // 压缩图片
    compressImage(file) {

      return new Promise((resolve, reject) => {
        new ImageCompressor(file.file, {
          quality: 0.6,
          checkOrientation: false,
          success(result) {

            resolve(result);
          },
          error(e) {
            reject();
          }
        });
      });
    },
    // 上传图片
    async uploadImgs(file) {
      console.log('22222', file);
      // console.log("%c Line:466 🍆 压缩前file", "color:#2eafb0", file);
      // 获取压缩后的文件流
      // let compreeBolb = await this.compressImage(file);
      // console.log("%c Line:468 🎂 压缩后compreeBolb", "color:#33a5ff", compreeBolb);
      const formData = new FormData();
      formData.append('file', file.file);//压缩后的文件会自动转换成二进制文件流类型
      //上传图片到后台
      try {
        let uploadImg = await postUpload(formData)
        file.data = {
          filePath: uploadImg.msg
        }
        file.code = 200
        return (file)
      }
      catch {
        file.code = 500
        return (file)
      }
    },
    // 详情图片上传成功
    handleUploadSuccess(res, file, fileList) {
      if (res.code == 200) {
        // 取得新上传的文件
        this.newImages = fileList.filter(t => {
          return t.url.indexOf('blob:') > -1
        })
      }
    },
    // 详情图片删除
    handleRemove(response, fileList, file) {
      // console.log("%c Line:495 🍎 response", "color:#4fff4B", response);
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;
      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //回显的图片
        }
      });
      this.newImages.forEach(function (element, key) {
        if (element.url === filePath) {
          that.newImages.splice(key, 1); //新上传的图片
        }
      });
    },
    // 图片预览
    handlePictureCardPreview(file) {
      this.$viewerApi({
        images: [file.url]
      });
    },
    imgPreview(url) {
      this.$viewerApi({
        images: [url]
      });
    },
    // 标题图片移除
    handleAvatarRemove(response, file) {
      this.form.titleImg = "";
      this.titleImgList = [];
    },
    // 标题图片上传成功
    handleAvatarSuccess(res, file) {
      if (res.code == 200) {
        let url = res.data.filePath;
        let name = res.data.fileName;
        let param = {};
        param.url = url;
        param.name = name;
        this.titleImgList.push(param);
        this.form.titleImg = res.data.filePath;
      }
    },

    // handleSelectionChange(val) {
    //   this.checkSuperList = val;
    // },
    // 提交
    submitForm(formName) {
      // if ("3" == this.form) {
      console.log("%c Line:1213 🍐 this.form", "color:#b03734", this.form);
      //   this.$modal.msgError("终端数据无法编辑！！！,请联系供货商");
      //   return true;
      // }
      this.$refs["" + formName][0].validate(valid => {
        // this.form.urlArr = this.imgFileList;
        if (valid) {
          // if (!this.pushGroup) {
          //   return this.$alert('请选择推送渠道分组！')
          // }
          // 拼接新上传的图片
          if (this.newImages && this.newImages.length) {
            let newArr = []
            this.newImages.forEach(img => {
              newArr.push({
                name: img.name,
                url: img.response.data.filePath
              })
            })
            this.form.urlArr = this.imgFileList.concat(newArr)
          } else {
            this.form.urlArr = this.imgFileList
          }
          // 将选择的商品类型转换成JSON字符串
          if (this.form.goodsTypeValues) this.form.goodsTypeValues = this.form.goodsTypeValues.join(',')

          if (this.isEdit) {
            // 编辑
            goodsListApi.editGoods(this.form).then(response => {
              this.$modal.msgSuccess("编辑成功");
              this.attrs = []
              this.$refs[formName][0].resetFields();
              this.reset()
              this.dialogVisible = false;
              this.$parent.getList();
            }).catch(() => {
              this.form.goodsTypeValues = this.form.goodsTypeValues.split(',')
            })
          } else {
            goodsListApi.addGoods(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.attrs = []
              this.$refs[formName][0].resetFields();
              this.reset()
              this.dialogVisible = false;
              this.$parent.getList();
            }).catch(() => {
              this.form.goodsTypeValues = this.form.goodsTypeValues.split(',')
            })
          }
        } else {
          this.$alert("请填写完整！");
        }
      });
    },
    close() {
      this.reset()
    },
    resetForm() {
      // 找出游戏下标
      let index = 0
      this.gameList.forEach((t, i) => {
        if (t.name == this.actived) {
          index = i
        }
      })
      this.$refs["form" + index][0].validate(valid => {

      })
    }
  }
};
</script>
<style scoped lang="css">
.center {
  text-align: center;
  position: absolute;
  bottom: 0;
  left: 0;
  z-index: 99;
  width: 100%;
  padding: 10px 0;
  background: #fff;
  box-shadow: 0 0 7px 0 rgba(0, 0, 0, 0.2);
}

.form-box {
  height: calc(100vh - 180px);
  overflow-y: auto;
  overflow-x: hidden;
}

.form-box >>> .el-input {
  width: 250px;
}

.form-box >>> .lattle-input .el-input {
  width: 170px;
}

.form-box >>> .lattle-input2 .el-input {
  width: 140px;
}

.form-box >>> .big-input .el-input {
  width: 100%;
}

.form-box >>> img {
  width: 100%;
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

.boxradioitem {
  padding: 15px;
  border: 1px dashed #999;
  border-radius: 10px;
}

.el-radio {
  display: block;
  line-height: 22px;
}

.boxradioRadioBox {
  background: #fff;
  box-shadow: 0 0 7px #e3e3e3;
  padding: 10px;
  position: absolute;
  z-index: 99;
  left: 20px;
  top: 30px;
  display: none;
}

.boxradioRadioBox.show {
  display: block;
}

.hiddenupload >>> .el-upload--picture-card {
  display: none;
}

/* .form-box >>> .el-upload{
  text-align: left;
} */
.form-box >>> .el-upload-dragger {
  width: 148px;
  height: 148px;
}

>>> .el-dialog__body {
  padding: 10px;
}

>>> .el-tabs__nav-next,
.el-tabs__nav-prev {
  font-size: 18px;
}

.form-box >>> .el-form-item__content {
  max-width: calc(100% - 120px);
}

.form-box >>> .el-input-number {
  width: 250px;
}

.form-box >>> .el-input-number .el-input__inner {
  text-align: left;
}

.form-box >>> .el-checkbox {
  width: 230px;
  margin: 0;
}

.step-box {
  width: 100%;
  padding: 10px 50px;
}

.fbt {
  justify-content: space-around;
}
</style>
