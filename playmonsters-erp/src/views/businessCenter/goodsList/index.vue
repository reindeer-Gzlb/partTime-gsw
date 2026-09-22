<template>
  <div class="app-container jklis">
    <div class="zdy-main customTab">
      <el-tabs
        type="border-card"
        v-model="actived"
        @tab-click="tabChange"
        v-loading="loading"
        class="kkk"
      >
        <div class="topNav">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            class="zdy-form search-form-new"
            label-width="68px"
            @keyup.enter.native="search"
          >
            <el-form-item label="" prop="id">
              <el-input
                v-model="queryParams.id"
                placeholder="商品编号:请输入商品编号"
                clearable
                size="small"
              />
            </el-form-item>
            <el-form-item label="" prop="gameId">
              <el-select
                v-model="queryParams.gameId"
                placeholder="游戏名称:请选择游戏"
                size="small"
                clearable
                @change="selectGameType"
              >
                <el-option
                  v-for="dict in gameList"
                  :key="dict.id"
                  :label="dict.name"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <!-- 出租状态 -->
            <el-form-item label="" prop="hireState">
              <el-select
                @change="search"
                clearable
                v-model="queryParams.hireState"
                filterable
                placeholder="出租状态:请选择"
              >
                <el-option
                  v-for="dict in hireStateOptions"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="goodsTypes" v-if="queryParams.gameId">
              <el-select
                @change="search"
                clearable
                multiple
                v-model="queryParams.goodsTypes"
                filterable
                placeholder="商品类型:请选择"
              >
                <el-option
                  v-for="dict in goodsTypeList"
                  :key="dict.id"
                  :label="dict.fieldName"
                  :value="dict.fieldName"
                />
              </el-select>
            </el-form-item>

            <el-form-item
              label=""
              prop="systemId"
              v-if="GameSelectedType === 0"
            >
              <el-select
                v-model="queryParams.systemId"
                placeholder="系统:请选择"
                size="small"
                clearable
                @change="search"
                filterable
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
              label=""
              prop="carrierId"
              v-if="GameSelectedType === 0"
            >
              <el-select
                v-model="queryParams.carrierId"
                placeholder="运营商:请选择"
                size="small"
                clearable
                @change="getAreaList"
                filterable
              >
                <el-option
                  v-for="dict in carrierList"
                  :key="dict.id"
                  :label="dict.fieldName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="groupId">
              <el-select
                v-model="queryParams.groupId"
                placeholder="大区:请选择"
                size="small"
                clearable
                @change="getServiceList"
                filterable
              >
                <el-option
                  v-for="dict in areaList"
                  :key="dict.id"
                  :label="dict.fieldName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="serverId">
              <el-select
                v-model="queryParams.serverId"
                placeholder="服务器:请选择"
                size="small"
                clearable
                @change="search"
                filterable
              >
                <el-option
                  v-for="dict in serverList"
                  :key="dict.id"
                  :label="dict.fieldName"
                  :value="dict.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="" prop="price" class="mm-b-8">
              <div class="two-input-content" style="margin-right: 2px">
                <el-input
                  size="small"
                  v-model="queryParams.priceStart"
                  class="two-input-item"
                  style="width: 97px !important"
                  placeholder="最小值"
                ></el-input>
                <span>-</span>
                <el-input
                  size="small"
                  v-model="queryParams.priceEnd"
                  class="two-input-item"
                  style="width: 97px !important"
                  placeholder="最大值"
                ></el-input>
              </div>
            </el-form-item>
            <el-form-item
              v-hasPermi="['supply:list:search']"
              label=""
              prop="createIds"
            >
              <el-select
                multiple
                v-model="queryParams.createIds"
                placeholder="供货商：请选择"
                size="small"
                clearable
                filterable
              >
                <el-option
                  v-for="dict in supplyList"
                  :key="dict.userId"
                  :label="dict.nickName"
                  :value="dict.userId"
                />
              </el-select>
            </el-form-item>

            <!-- <el-form-item label="商品描述" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入商品描述" clearable size="small" />
          </el-form-item> -->
            <el-form-item label="" prop="">
              <el-input
                v-model="queryParams.goodsTypeValues"
                placeholder="商品类型：请输入"
                clearable
                size="small"
              />
            </el-form-item>
            <el-form-item label="" prop="">
              <el-date-picker
                style="width: 300px"
                v-model="timer"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="回收开始日期"
                end-placeholder="回收结束日期"
                value-format="yyyy-MM-dd"
              >
              </el-date-picker>
            </el-form-item>
            <br />
            <el-form-item label="" prop="keyWord">
              <el-input
                v-model="queryParams.keyWord"
                placeholder="关键字：多个关键字用空格隔开"
                clearable
                size="small"
              />
            </el-form-item>
            <el-form-item>
              <el-button
                style="background-color: #fff"
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
                style="background-color: #fff"
                size="mini"
                plain
                @click="resetQuery"
                class="zdy-btn zdy-btn-refresh"
                >重置</el-button
              >
            </el-form-item>
            <el-form-item>
              <el-button
                style="background-color: #fff"
                icon="el-icon-search"
                size="mini"
                @click="openMoreSearch"
                class="zdy-btn"
                >更多搜索</el-button
              >
            </el-form-item>
            <el-form-item v-if="pageType == 'check'">
              <el-button
                icon="el-icon-plus"
                style="background-color: #fff"
                size="mini"
                @click="checkAccountFn(false)"
                class="zdy-btn zdy-btn-refresh"
                >批量通过</el-button
              >
            </el-form-item>
            <el-form-item
              v-if="
                pageType != 'check' &&
                pageType != 'pool' &&
                pageType != 'salePort'
              "
            >
              <el-button
                icon="el-icon-plus"
                style="background-color: #fff"
                size="mini"
                @click="addACount"
                class="zdy-btn zdy-btn-refresh"
                >发布</el-button
              >
            </el-form-item>

            <!-- <el-form-item v-if="pageType != 'check'&&pageType!='pool'">
            <el-button icon="el-icon-document-copy" type="primary" size="mini" @click="addACountMore"
              class="zdy-btn zdy-btn-refresh">批量发布</el-button>
          </el-form-item> -->
            <el-form-item v-if="pageType == 'salePort'">
              <el-button
                style="background-color: #fff"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="copyBatch"
                >批量复制</el-button
              >
            </el-form-item>
            <el-form-item v-if="pageType == 'admin'">
              <el-button
                style="background-color: #fff"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="piliangXiajia"
                >批量下架</el-button
              >
            </el-form-item>

            <el-form-item>
              <el-button
                style="background-color: #fff"
                plain
                icon="el-icon-delete"
                size="mini"
                v-if="pageType == 'admin'"
                @click="deleteAll"
                >全部下架</el-button
              >
            </el-form-item>
            <el-form-item class="tuisongbtn">
              <!-- <el-dropdown @command="pushBatch" v-if="pageType == 'admin'"> -->
              <el-dropdown @command="pushBatch" v-hasPermi="['bacth:push']">
                <el-button style="background-color: #fff" size="mini">
                  一键推送
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-for="item in pushApiList"
                    :command="item.id"
                    :key="item.id"
                    >{{ item.name }}</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </el-form-item>

            <!-- <el-form-item>
            <span v-if="actived == 1 || actived == 7" style="margin-left:10px">
              <span size="small">隐藏</span>
              <el-switch @change="onOfOff" v-model="irreAttr" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </span>
          </el-form-item> -->
          </el-form>
        </div>
        <el-tab-pane
          :label="tab.name"
          :name="tab.value"
          v-for="tab in statusList"
          :key="tab.value"
        >
          <div
            v-if="!tableData.records || tableData.records.length == 0"
            style="height: calc(100vh - 360px)"
          ></div>
          <el-table
            :border="true"
            :data="tableData.records"
            @cell-dblclick="handleUpdate"
            @sort-change="sortFun"
            style="width: 100%"
            v-if="tableData.records && tableData.records.length"
            @selection-change="handleSelection"
            :header-cell-style="{ backgroundColor: '#3F9EFF', color: 'white' }"
            :row-class-name="tableRowClassName"
          >
            <el-table-column type="selection" width="55" align="center">
            </el-table-column>

            <el-table-column
              sort-by
              sortable="custom"
              label="自编号"
              width="100"
              prop="thirdId"
              align="center"
            />
            <el-table-column
              label="状态"
              width="100"
              label-position="left"
              align="center"
            >
              <template slot-scope="scope">
                <div class="zhuangtai" v-if="scope.row.saleState == 0">
                  待审核
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 1">
                  未出售
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 2">
                  出售中
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 3">
                  已售出
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 4">
                  已下架
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 5">
                  问题账号
                </div>
                <div class="zhuangtai" v-if="scope.row.saleState == 6">
                  找回账号
                </div>
              </template>
            </el-table-column>
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="归属"
              width="126"
              prop="attributione"
              align="center"
            />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="需要上架平台"
              width="126"
              prop="toPlatform"
              align="center"
            />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="17 位"
              width="180"
              prop="businessCode"
              align="center"
            />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="游戏ID"
              width="150"
              prop="gameThirdId"
              align="center"
            />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="邮箱"
              width="188"
              prop="email"
              align="center"
            />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="绑定手机号"
              width="166"
              prop="securityPhone"
              align="center"
            />
            <el-table-column
              sortable="custom"
              sort-by
              label="回收价"
              width="100"
              prop="costPrice"
              align="center"
            />
            <el-table-column
              label="售价"
              width="100"
              prop="newPrice"
              align="center"
            />
            <el-table-column
              label="商品描述"
              min-width="500"
              prop="des"
              align="center"
            >
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.des"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.des }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              v-if="!irreAttr && pageType != 'pool'"
              label="类型"
              width="166"
              prop="goodsTypeValues"
            />
            <el-table-column
              align="center"
              label="重要信息备注"
              width="150"
              prop="importantRemark"
            >
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.importantRemark"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.importantRemark }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="序号"
              sortable="custom"
              width="100"
              label-position="left"
              sort-by
            >
              <template slot-scope="scope">
                <span
                  v-show="
                    (actived == 3 ||
                      actived == 16 ||
                      actived == 17 ||
                      actived == 11) &&
                    scope.row.selltype
                  "
                  class="sign"
                  :class="{ ping: scope.row.selltype == 1 }"
                  >{{ scope.row.selltype == 1 ? "平" : "自" }}</span
                >
                <!-- <span v-if="scope.row.lable == 0" style="color: #ff6d6d">{{scope.row.id}}</span> -->
                <span>{{ scope.row.id }}</span>
              </template>
            </el-table-column>
            <el-table-column
              align="center"
              v-if="!irreAttr && pageType != 'pool'"
              label="账号"
              width="126"
              prop="accountNumber"
            />
            <el-table-column
              align="center"
              v-if="!irreAttr && pageType != 'pool'"
              label="密码"
              width="100"
              prop="accountPassword"
            />
            <el-table-column
              align="center"
              sortable="custom"
              sort-by
              v-if="!irreAttr"
              label="回收日期"
              width="160"
              prop="recoveryTime"
            />
            <el-table-column
              align="center"
              sortable="custom"
              sort-by
              v-if="!irreAttr"
              label="创建时间"
              width="160"
              prop="createTime"
            />
            <el-table-column
              align="center"
              v-if="!irreAttr && pageType != 'pool'"
              label="微信号"
              width="188"
              prop="vxNickName"
            />

            <el-table-column
              align="center"
              label="名字"
              width="100"
              prop="supplyName"
              v-if="pageType == 'admin' || pageType == 'salePort'"
            >
              <template slot-scope="scope">
                <div>
                  <el-popover
                    @show="getSupPhone(scope.row)"
                    placement="right"
                    :title="scope.row.supplyName"
                    width="250"
                    trigger="click"
                  >
                    <div>{{ supPhoneNum }}</div>
                    <el-button type="text" slot="reference" size="mini">{{
                      scope.row.supplyName
                    }}</el-button>
                  </el-popover>
                </div>
              </template>
            </el-table-column>

            <el-table-column
              align="center"
              v-if="actived == 4"
              label="下架原因"
              width="160"
              prop="downDes"
            />
            <el-table-column
              align="center"
              v-if="actived == 5"
              label="问题备注"
              width="160"
              prop="problemDes"
            />
            <el-table-column
              align="center"
              v-if="actived == 6"
              label="找回备注"
              width="160"
              prop="retrieveDes"
            />
            <!-- <el-table-column
              v-if="actived == 0 || actived == 99"
              label="状态"
              width="120"
              prop="saleState"
            >
              <template slot-scope="scope">
                <el-tag
                  :type="
                    statusMap[scope.row.saleState]
                      ? statusMap[scope.row.saleState].type
                      : ''
                  "
                  >{{
                    statusMap[scope.row.saleState]
                      ? statusMap[scope.row.saleState].name
                      : ""
                  }}</el-tag
                >
              </template>
            </el-table-column> -->

            <el-table-column
              align="center"
              label="游戏名称"
              width="100"
              prop="gameName"
            />
            <!-- <el-table-column label="商品标题" width="200" prop="title">
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" :content="scope.row.title" placement="top-start">
                  <div class="zdy-des">{{ scope.row.title }}</div>
                </el-tooltip>
              </template>
            </el-table-column> -->
            <el-table-column
              label="区服"
              align="center"
              width="130"
              prop="systemName"
            >
              <template slot-scope="scope">
                <span>{{
                  scope.row.systemName ? scope.row.systemName + "/" : ""
                }}</span>
                <span>{{
                  scope.row.carrierName ? scope.row.carrierName + "/" : ""
                }}</span>
                <span>{{
                  scope.row.groupName ? scope.row.groupName + "/" : ""
                }}</span>
                <span>{{ scope.row.serverName }}</span>
              </template>
            </el-table-column>

            <!-- <el-table-column label="包赔材料" width="100" prop="assurance" />
            <el-table-column label="实名" width="130" prop="shiming" /> -->
            <!-- <el-table-column
              label="售价"
              width="100"
              prop="newPrice"
              v-if="pageType == 'salePort' || pageType == 'admin'"
            >
              <template slot-scope="scope">
                <div>{{ scope.row.newPrice }}</div>
                <div>
                  <el-popover
                    @show="getMore(scope.row)"
                    placement="left"
                    title="更多"
                    width="250"
                    trigger="click"
                  >
                    <el-table
                      :data="morePrice"
                      v-if="morePrice && morePrice.length"
                    >
                      <el-table-column
                        width="100"
                        property="saleName"
                        label="销售名称"
                      ></el-table-column>
                      <el-table-column
                        width="100"
                        property="price"
                        label="价格"
                      ></el-table-column>
                    </el-table>
                    <el-button slot="reference" size="mini">更多</el-button>
                  </el-popover>
                </div>
              </template>
            </el-table-column> -->
            <el-table-column
              align="center"
              label="三方ID"
              width="100"
              prop=""
              v-if="pageType == 'salePort' || pageType == 'admin'"
            >
              <template slot-scope="scope">
                <div>
                  <el-popover
                    @show="getoutId(scope.row)"
                    placement="left"
                    title="三方ID"
                    width="350"
                    trigger="click"
                  >
                    <el-table
                      :data="outIdsData"
                      height="400"
                      v-if="outIdsData && outIdsData.length"
                    >
                      <el-table-column
                        property="name"
                        label="三方名称"
                      ></el-table-column>
                      <el-table-column
                        property="outid"
                        label="ID"
                      ></el-table-column>
                    </el-table>
                    <el-button
                      style="height: 24px; line-height: 10px"
                      slot="reference"
                      size="mini"
                      >点击查看</el-button
                    >
                  </el-popover>
                </div>
              </template>
            </el-table-column>

            <!-- <el-table-column
              label="供货客服"
              width="100"
              prop="supplyName"
              v-if="pageType == 'admin' || pageType == 'salePort'"
            >
              <template slot-scope="scope">
                <div>
                  <el-popover
                    @show="getSupPhone(scope.row)"
                    placement="right"
                    :title="scope.row.supplyName"
                    width="250"
                    trigger="click"
                  >
                    <div>{{ supPhoneNum }}</div>
                    <el-button type="text" slot="reference" size="mini">{{
                      scope.row.supplyName
                    }}</el-button>
                  </el-popover>
                </div>
              </template>
            </el-table-column> -->
            <el-table-column
              align="center"
              label="销售客服"
              width="100"
              prop="salaName"
              v-if="pageType == 'admin' || pageType == 'salePort'"
            />
            <!-- <el-table-column
              sortable="custom"
              sort-by
              label="供货价"
              width="100"
              prop="price"
            />
            <el-table-column label="成本价" width="100" prop="costPrice" />
            <el-table-column
              v-if="!irreAttr && pageType != 'pool'"
              label="手机号"
              width="110"
              prop="securityPhone"
            /> -->

            <el-table-column
              align="center"
              sortable="custom"
              sort-by
              v-if="
                actived == 3 || actived == 16 || actived == 17 || actived == 11
              "
              label="销售时间"
              width="160"
              prop="saletime"
            />
            <!-- <el-table-column
              fixed="right"
              label="精品账号"
              width="100"
              prop="isTop"
            >
              <template slot-scope="scope">
                <el-switch
                  :disabled="
                    actived != 1 ||
                    (pageType != 'myAccount' && pageType != 'admin')
                  "
                  @change="changeIstop($event, scope.row.id)"
                  v-model="scope.row.isTop"
                  active-color="#13ce66"
                  :active-value="1"
                  :inactive-value="2"
                  inactive-color="#ff4949"
                >
                </el-switch>
              </template>
            </el-table-column> -->
            <el-table-column
              fixed="right"
              label="是否推送"
              width="88"
              prop="isPush"
              align="center"
            >
              <template slot-scope="scope">
                <el-switch
                  :disabled="actived != 1 && scope.row.saleState != 1"
                  @change="changeIspush($event, scope.row.id)"
                  v-model="scope.row.isPush"
                  active-color="#13ce66"
                  :active-value="1"
                  :inactive-value="2"
                  inactive-color="#ff4949"
                >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="是否特价"
              width="88"
              prop="isPush"
              align="center"
            >
              <template slot-scope="scope">
                <el-switch
                  :disabled="actived != 1 && scope.row.saleState != 1"
                  @change="changeTePriceAccount($event, scope.row.id)"
                  v-model="scope.row.isOnSpecialOffer"
                  active-color="#13ce66"
                  :active-value="1"
                  :inactive-value="0"
                  inactive-color="#ff4949"
                >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column
              fixed="right"
              label="操作"
              width="313"
              align="center"
            >
              <template slot-scope="scope">
                <div class="caozuo_item">
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="actived == 1"
                    @click="zhuanGuashou(scope.row.id)"
                    >转挂售</el-button
                  >

                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="
                      (pageType == 'myAccount' && actived == 1) ||
                      actived == 2 ||
                      actived == 10 ||
                      actived == 11
                    "
                    @click="openOnSelf(scope.row)"
                    >转已售</el-button
                  >
                  <!-- <el-button type="text" style="color:red" size="mini" v-if="actived == 5"
                  @click="handleDelete(scope.row)">删除</el-button> -->
                  <el-button
                    type="text"
                    style="color: #fd7d01"
                    size="mini"
                    v-if="
                      (actived == 1 && pageType != 'pool') ||
                      (actived == 10 && pageType != 'pool') ||
                      (actived == 16 && pageType != 'pool') ||
                      (actived == 17 && pageType != 'pool')
                    "
                    @click="downSale(scope.row.id)"
                    >下架</el-button
                  >
                  <el-button
                    style="color: #1e9e00"
                    type="text"
                    size="mini"
                    v-if="
                      actived == 4 ||
                      actived == 5 ||
                      actived == 2 ||
                      actived == 10 ||
                      actived == 12 ||
                      actived == 13
                    "
                    @click="upSale(scope.row.id)"
                    >上架</el-button
                  >
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="
                      actived == 4 ||
                      actived == 3 ||
                      actived == 6 ||
                      actived == 2 ||
                      actived == 12 ||
                      actived == 14 ||
                      actived == 15 ||
                      actived == 16 ||
                      actived == 17 ||
                      actived == 11
                    "
                    @click="transQues(scope.row.id)"
                    >转为问题号</el-button
                  >
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="
                      (actived == 4 || actived == 5 || actived == 6) &&
                      (pageType == 'myAccount' || pageType == 'admin')
                    "
                    @click="handleDelete(scope.row)"
                    >删除</el-button
                  >
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="
                      actived == 5 &&
                      (pageType == 'myAccount' || pageType == 'admin')
                    "
                    @click="transZhaohui(scope.row.id)"
                    >转为找回</el-button
                  >
                  <!-- <el-button type="text" size="mini" v-if="actived == 2 && pageType == 'salePort'"
                  @click="commitOrder(scope.row)">完成交易</el-button> -->
                  <el-button
                    type="text"
                    size="mini"
                    style="color: #1e9e00"
                    v-if="
                      (actived == 1 && pageType == 'salePort') ||
                      (actived == 10 && pageType == 'salePort')
                    "
                    @click="addOrder(scope.row)"
                    >提号</el-button
                  >
                  <el-button
                    type="text"
                    size="mini"
                    v-if="actived == 0 && pageType == 'check'"
                    @click="checkAccountFn(scope.row)"
                    >通过</el-button
                  >
                  <!-- checkAccountFaileFn -->
                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="actived == 0 && pageType == 'check'"
                    @click="checkAccountFaileFn(scope.row)"
                    >不通过</el-button
                  >
                  <el-button
                    ref="copyBtn1"
                    type="text"
                    size="mini"
                    v-if="actived == 1 || actived == 0 || actived == 10"
                    v-clipboard:copy="copyVlaue"
                    v-clipboard:error="onError"
                    v-clipboard:success="onCopy"
                    @click="keyCopy(scope.row)"
                    >复制</el-button
                  >

                  <el-button
                    type="text"
                    style="color: red"
                    size="mini"
                    v-if="actived == 1 || actived == 10"
                    @click="zhuanfenqiNew(scope.row)"
                    >转分期</el-button
                  >

                  <el-button
                    style="color: red"
                    v-if="
                      (pageType == 'admin' || pageType == 'myAccount') &&
                      actived == 5
                    "
                    type="text"
                    size="mini"
                    @click="lahei(scope.row.id)"
                    >拉黑</el-button
                  >
                  <el-button
                    style="color: #1e9e00"
                    v-if="
                      pageType == 'myAccount' ||
                      pageType == 'admin' ||
                      actived == 10
                    "
                    type="text"
                    size="mini"
                    @click="showLogs(scope.row.id)"
                    >日志</el-button
                  >
                  <el-dropdown
                    class="weishouAll"
                    @command="handleCommand($event, scope.row.id)"
                    v-if="actived == 1 || actived == 5"
                  >
                    <span class="el-dropdown-link">
                      更多操作<i class="el-icon-arrow-down el-icon--right"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                      <!-- <el-dropdown-item command="a">转分期</el-dropdown-item> -->
                      <el-dropdown-item command="b">转封禁</el-dropdown-item>
                      <el-dropdown-item command="c">转私下</el-dropdown-item>
                      <el-dropdown-item command="d">转赔付</el-dropdown-item>
                      <el-dropdown-item command="e">转永久</el-dropdown-item>
                      <el-dropdown-item command="f"
                        >转在手封禁</el-dropdown-item
                      >
                      <el-dropdown-item command="g"
                        >转红锁赔付</el-dropdown-item
                      >
                    </el-dropdown-menu>
                  </el-dropdown>
                  <!-- <el-button type="text" size="mini" v-if="actived == 1 && (pageType == 'myAccount' || pageType == 'admin')"
                  @click="addCheck(scope.row)">加入监测</el-button> -->
                </div>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box" style="text-align: center">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 60]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.total"
        background
      ></el-pagination>
    </div>
    <!-- 一下是弹出框 -->
    <h5Dialog ref="h5Dialog"></h5Dialog>
    <addDialog ref="addDialog"></addDialog>
    <onSelfDialog ref="onSelfDialog"></onSelfDialog>
    <importDialog ref="importDialog"></importDialog>
    <deleteDialog ref="deleteDialog"></deleteDialog>
    <pushDialog ref="pushDialog"></pushDialog>
    <moreSearch ref="moreSearch" @sureSearch="sureSearch"></moreSearch>
    <showLogs ref="showLogs"></showLogs>
    <addCheckPhone ref="addCheckPhone"></addCheckPhone>
    <tihaoDialog ref="tihaoDialog"></tihaoDialog>
    <onSelfDialogFQ ref="onSelfDialogFQ"></onSelfDialogFQ>

    <el-dialog title="备注" :visible.sync="showSinNumVisible">
      <span>{{ sinNumShow }}</span>
    </el-dialog>
    <!-- 改变状态的确认备注 -->
    <el-dialog :title="showDialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="unsoldFrom">
        <el-form-item
          :label="showDialogTitle == '下架原因' ? '下架原因' : '备注'"
          label-width="auto"
        >
          <el-input v-model="unsoldFrom.note" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmSub">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getSupplyList,
  getMorePrice,
  getOutIds,
  getSupPhoneNumber,
} from "@/api/info/info";
import h5Dialog from "../saleList/h5Dialog.vue";
import addDialog from "./add.vue";
import showLogs from "./showLogs";
import onSelfDialog from "./onSelfDialog.vue";
import onSelfDialogFQ from "./onSelfDialogFQ.vue";
import importDialog from "./importExel.vue";
import deleteDialog from "./allDelete.vue";
import tihaoDialog from "./tihaoDialog.vue";
import pushDialog from "./push.vue";
import moreSearch from "./moreSearch.vue";
import addCheckPhone from "../checkPhonePx/add.vue";
import goodsListApi from "../../../api/gameConfig/goodsList";
import gameDictionaryApi from "../../../api/gameConfig/gameDictionary";
import gameListApi from "@/api/gameConfig/gameList";
import { auditInfo, getPushList, getSupTePriceAccount } from "@/api/info/info";
import { getDicts } from "@/api/system/dict/data";
import { addBlackAccount } from "@/api/black/black";
// import checkPhoneApi from "../../../api/gameConfig/checkPhone";
export default {
  name: "GoodsList",
  components: {
    addDialog,
    importDialog,
    onSelfDialog,
    onSelfDialogFQ,
    moreSearch,
    deleteDialog,
    pushDialog,
    h5Dialog,
    showLogs,
    addCheckPhone,
    tihaoDialog,
  },
  data() {
    return {
      outIdsData: [],
      supPhoneNum: "",
      gameList: [], //游戏列表
      tableData: {
        records: [],
        total: 0,
      },
      goodsTypeList: [], //商品类型
      hireStateOptions: [
        {
          value: "1",
          label: "不出租",
        },
        {
          value: "2",
          label: "未出租",
        },
        {
          value: "3",
          label: "出租中",
        },
      ], //出租状态
      pageType: "",
      statusMap: {
        0: {
          name: "待审核",
          type: "warning",
        },
        1: {
          name: "未出售",
          type: "success",
        },
        2: {
          name: "交易中",
          type: "warning",
        },
        3: {
          name: "已出售",
          type: "",
        },
        4: {
          name: "已下架",
          type: "danger",
        },
        5: {
          name: "问题账号",
          type: "danger",
        },
        6: {
          name: "找回账号",
          type: "danger",
        },
        99: {
          name: "审核未通过",
          type: "danger",
        },
      },
      statusList: [
        {
          name: "全部",
          value: "",
        },
        {
          name: "未出售",
          value: "1",
        },
        {
          name: "挂售区",
          value: "10",
        },
        {
          name: "交易中",
          value: "2",
        },
        {
          name: "已出售",
          value: "3",
        },
        {
          name: "分期中",
          value: "11",
        },
        {
          name: "已封禁",
          value: "12",
        },
        {
          name: "在手封禁",
          value: "16",
        },
        {
          name: "已私下",
          value: "13",
        },
        {
          name: "赔付中",
          value: "14",
        },
        {
          name: "红锁赔付",
          value: "17",
        },
        {
          name: "永久出售",
          value: "15",
        },
        {
          name: "已下架",
          value: "4",
        },
        {
          name: "问题账号",
          value: "5",
        },
        {
          name: "找回账号",
          value: "6",
        },
        // {
        //   name: "找回账号",
        //   value: "6"
        // },
      ],
      actived: "1",
      GameSelectedType: "", //筛选的游戏的类型
      sysList: [],
      carrierList: [], //运营商列表
      areaList: [], //大区列表
      serverList: [], //服务器列表
      selects: [], //表格的选中项
      ids: [], //表格选中的ids
      pushApiList: [],
      morePrice: [], //更多价格

      supplyList: [],
      levelArr: [],
      irreAttr: false, //隐藏无关字段
      showSinNumVisible: false, //查看备注
      sinNumShow: "",
      unsoldFrom: {
        note: null,
      },
      showDialogTitle: "",
      dialogFormVisible: false,
      tempData: null,
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        saleState: "1",
        createIds: [],
        sort: "",
        sortColumn: "",
        dataType: "all",
        recoveryTimeEnd: null,
        recoveryTimeStart: null,
      },
      regionalArr: [], //区服列表
      copyVlaue: "",
      customAttr: null,
      retailDomainList: [],
      timer: [],
    };
  },
  created() {
    if (this.$route.query.type) {
      this.pageType = this.$route.query.type;
      if (this.pageType == "check") {
        this.statusList = [
          {
            name: "待审核",
            value: "0",
          },
          {
            name: "审核未通过",
            value: "99",
          },
        ];
        this.actived = "0";
        this.queryParams.saleState = this.actived;
      } else if (this.pageType == "pool") {
        this.statusList = [
          {
            name: "未出售",
            value: "1",
          },
        ];
        this.actived = "1";
        this.queryParams.saleState = this.actived;
      }
      if (
        this.pageType == "pool" ||
        this.pageType == "admin" ||
        this.pageType == "salePort" ||
        this.pageType == "check"
      ) {
        this.queryParams.dataType = "all";
      } else {
        this.queryParams.dataType = "one";
      }
    }
    this.getList();
    this.getGameList();
    this.getPushList();
    this.getDomain();
    this.getUserList();
  },
  methods: {
    //拉黑
    lahei(id) {
      this.$confirm("此操作将永久拉黑该账号, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          await addBlackAccount({ accountId: id });
          this.getList();
          this.$message({
            type: "success",
            message: "拉黑成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消拉黑",
          });
        });
    },
    handleCommand(comman, item) {
      // this.$message('click on item ' + comman + item);
      if (comman == "a") {
        this.zhuanfenqi(item);
      } else if (comman == "b") {
        this.zhuanfengjin(item);
      } else if (comman == "c") {
        this.zhuansixia(item);
      } else if (comman == "d") {
        this.zhuanpeifu(item);
      } else if (comman == "e") {
        this.zhuanyjcs(item);
      } else if (comman == "f") {
        this.zhuanZSFJ(item);
      } else if (comman == "g") {
        this.zhuanHSPF(item);
      }
    },
    //转分期
    zhuanfenqi(id) {
      this.$modal
        .confirm("是否确认转分期编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanfenqi(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转封禁
    zhuanfengjin(id) {
      this.$modal
        .confirm("是否确认转封禁编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertYFJ(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转私下
    zhuansixia(id) {
      this.$modal
        .confirm("是否确认转私下编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertYSX(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转赔付
    zhuanpeifu(id) {
      this.$modal
        .confirm("是否确认转赔付编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertPFZ(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转永久出售
    zhuanyjcs(id) {
      this.$modal
        .confirm("是否确认转永久编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertYJCS(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转在手封禁
    zhuanZSFJ(id) {
      this.$modal
        .confirm("是否确认转在手封禁编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertZSFJ(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    //转红锁赔付
    zhuanHSPF(id) {
      this.$modal
        .confirm("是否确认转红锁赔付编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanconvertHSPF(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    tableRowClassName({ row, rowIndex }) {
      // console.log('333', row, rowIndex);
      for (let i = 0; i < this.selects.length; i++) {
        if (row.id === this.selects[i].id) {
          return "xuanzhong-row";
        }
      }

      if (rowIndex % 2 === 1) {
        return "warning-row";
      } else {
        return "success-row";
      }
    },
    openUrl(url) {
      window.open(url);
    },
    // 供货手机号
    getSupPhone(row) {
      this.supPhoneNum = "";
      getSupPhoneNumber({ userId: row.createId }).then((res) => {
        this.supPhoneNum = res.msg;
      });
    },
    // 更多价格
    getMore(row) {
      console.log("%c Line:1303 🍐 row", "color:#33a5ff", row);
      getMorePrice({ infoid: row.id }).then((res) => {
        console.log("%c Line:1304 🥚 res", "color:#7f2b82", res);
        this.morePrice = res.data;
      });
    },
    // 查询账号的三方ID
    getoutId(row) {
      getOutIds(row.id).then((res) => {
        console.log("%c Line:490 🍪 res", "color:#f5ce50", res);
        this.outIdsData = res.data;
      });
    },
    // 获取分销站域名
    getDomain() {
      getDicts("retailDomain").then((res) => {
        if (res.code == 200) {
          this.retailDomainList = res.data;
        }
      });
    },

    // 游戏列表
    getGameList() {
      gameListApi.getGameList().then((res) => {
        this.gameList = res.rows;
      });
    },
    // 获取列表
    getList() {
      // this.queryParams
      console.log(
        "%c Line:509 🥓 this.queryParams",
        "color:#ea7e5c",
        this.queryParams
      );
      this.tableData.records = [];
      this.loading = true;
      if (this.timer && this.timer.length == 2) {
        this.queryParams.recoveryTimeStart = this.timer[0] + " 00:00:00";
        this.queryParams.recoveryTimeEnd = this.timer[1] + " 23:59:59";
      } else {
        this.queryParams.recoveryTimeStart = "";
        this.queryParams.recoveryTimeEnd = "";
      }
      if (this.pageType == "pool") {
        // getGoodsListPool 账号池
        goodsListApi
          .getGoodsListPool(this.queryParams)
          .then((response) => {
            this.$nextTick(() => {
              this.loading = false;
              this.tableData.records = JSON.parse(
                JSON.stringify(response.rows)
              );
              this.tableData.total = response.total;
            });
          })
          .catch((err) => {
            this.loading = false;
          });
      } else if (this.pageType == "myAccount") {
        // 我的账号
        goodsListApi
          .myAccount(this.queryParams)
          .then((response) => {
            this.$nextTick(() => {
              this.loading = false;
              this.tableData.records = JSON.parse(
                JSON.stringify(response.rows)
              );
              this.tableData.total = response.total;
            });
          })
          .catch((err) => {
            this.loading = false;
          });
      } else {
        goodsListApi
          .getGoodsAllList(this.queryParams)
          .then((response) => {
            // console.log("%c Line:544 🍣 response", "color:#fca650", response);
            // this.loading = false;
            //   this.tableData.records = response.rows;
            //   this.tableData.total = response.total;
            this.$nextTick(() => {
              this.loading = false;
              this.tableData.records = JSON.parse(
                JSON.stringify(response.rows)
              );
              this.tableData.total = response.total;
            });
          })
          .catch((err) => {
            this.loading = false;
          });
      }
    },
    //获取供货商接口
    getUserList() {
      getSupplyList("supplyCustomer").then((res) => {
        if (res.code == 200) {
          this.supplyList = res.data;
        }
      });
    },
    // 获取推送接口列表
    getPushList() {
      getPushList({ status: 1 }).then((res) => {
        this.pushApiList = res.rows;
      });
    },
    // 打开更多搜索弹窗
    openMoreSearch() {
      if (!this.queryParams.gameId) {
        return this.$alert("请先选择游戏名称！");
      }
      this.$refs.moreSearch.open(this.queryParams.gameId);
    },
    // 更多搜索确认
    sureSearch(form) {
      this.queryParams = { ...this.queryParams, ...form };
      this.search();
    },
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    // 隐藏无关属性
    onOfOff() {
      const list = JSON.parse(JSON.stringify(this.tableData.records));
      this.tableData.records = [];
      this.loading = true;
      setTimeout(() => {
        this.$nextTick(() => {
          this.tableData.records = JSON.parse(JSON.stringify(list));
          this.loading = false;
        });
      }, 300);
    },
    // 表格排序
    sortFun(val) {
      console.log(val);
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    // tabs切换时触发
    tabChange() {
      this.queryParams.saleState = this.actived;
      if (this.actived == 0 && this.pageType == "check") {
        this.queryParams.saleState = "0";
      } else if (this.actived == 0) {
        this.queryParams.saleState = "";
      }

      this.getList();
    },
    // 新增
    addACount() {
      this.$refs.addDialog.open("add");
    },
    showLogs(id) {
      this.$refs.showLogs.open(id);
    },
    // 批量发布
    addACountMore() {
      // importDialog
      this.$refs.importDialog.open();
      // this.$router.push({
      //   name:'Import'
      // })
    },
    // 转自售
    openOnSelf(row) {
      this.$refs.onSelfDialog.open(row);
    },
    //转分期
    zhuanfenqiNew(row) {
      this.$refs.onSelfDialogFQ.open(row);
    },
    // 表格选择项改变
    handleSelection(arr) {
      console.log("%c Line:442 🍆 表格选择改变", "color:#f5ce50", arr);
      this.selects = arr;
      this.ids = [];
      if (arr.length == 0) {
        this.ids = [];
      } else {
        arr.forEach((t) => {
          this.ids.push(t.id);
        });
      }
    },
    // 审核账号
    checkAccountFn(row) {
      let ids = [];
      if (row) {
        ids = row.id;
      } else {
        if (this.ids && this.ids.length > 0) {
          ids = this.ids.join(",");
        } else {
          this.$alert("请先选择账号！");
          return;
        }
      }
      if (ids) {
        this.$modal
          .confirm("确认审核通过吗？")
          .then(() => {
            goodsListApi.auditGoods(ids).then((res) => {
              this.$alert(res.msg);
              this.getList();
            });
          })
          .catch(() => {});
      }
    },
    // 审核不通过
    checkAccountFaileFn(row) {
      let ids = [];
      if (row) {
        ids = [row.id];
      } else {
        if (this.ids && this.ids.length > 0) {
        } else {
          this.$alert("请先选择账号！");
          return;
        }
      }
      if (ids) {
        this.$prompt("审核备注", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          inputPattern: /\S/,
          inputErrorMessage: "请填写审核备注",
        })
          .then(({ value }) => {
            let obj = {
              ids: ids,
              auditDes: value,
            };
            goodsListApi.auditGoodsFaile(obj).then((res) => {
              this.$alert(res.msg);
              this.getList();
            });
          })
          .catch(() => {});
      }
    },
    // 打开全部删除弹窗
    deleteAll() {
      this.$refs.deleteDialog.open();
    },
    // 批量下架
    piliangXiajia() {
      const ids = this.ids.join(",");
      if (!ids) {
        return this.$alert("请先选择数据！");
      }
      let that = this;
      this.$modal
        .confirm('是否确认下架编号为"' + ids + '"的数据项？')
        .then(function () {
          that.loading = true;
          goodsListApi
            .batchDown(ids)
            .then((res) => {
              that.loading = false;
              that.getList();
              that.$modal.msgSuccess(res.msg);
            })
            .catch((err) => {
              that.$modal.msgSuccess(err.msg);
            });
        })
        .catch(() => {});
    },
    // 批量复制
    copyBatch() {
      if (this.selects.length) {
        let str = "";

        this.selects.forEach((item) => {
          let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${item.id}`;
          str = `${str}商品ID：${item.id}\n自编号：${item.thirdId}\n区服：${
            item.systemName ? item.systemName + "/" : ""
          }${item.carrierName ? item.carrierName + "/" : ""}${item.groupName}${
            item.serverName
          }\n简介：${item.des}\n图片：${hostt}\n ===================== \n`;
        });
        this.$copyText(str).then(
          (e) => {
            this.$message({
              type: "success",
              message: "已复制到剪切板！",
            });
          },
          function (e) {}
        );
      } else {
        return this.$alert("请先选择数据！");
      }
    },
    // 删除
    /** 删除按钮操作 */
    handleDelete(row) {
      // const ids = row.id || this.ids.join(',');
      // if (!ids) {
      //   return this.$alert('请先选择数据！')
      // }
      this.$prompt("请输入删除密码", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /\S+/,
        inputErrorMessage: "请输入密码",
      })
        .then(({ value }) => {
          goodsListApi.delGoods(row.id, value).then((res) => {
            this.getList();
            this.$message({
              type: "success",
              message: "删除成功!",
            });
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消输入",
          });
        });
      // this.$modal
      //   .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
      //   .then(function () {
      //     that.loading = true;
      //     goodsListApi.delGoods(ids).then(res => {
      //       that.$modal.msgSuccess(res.msg);
      //       that.getList();
      //     }).catch((err) => { that.loading = false; that.getList(); });
      //   })
      //   .catch(() => { });
    },
    // 批量推送
    // 推送
    pushBatch(item) {
      if (this.ids.length > 1) {
        this.$modal.msgError("只能选择一条数据");
        return true;
      } else if (this.ids.length == 0) {
        this.$modal.msgError("请先选择一条数据");
        return true;
      }
      this.$refs.pushDialog.open({
        id: this.ids[0],
        configId: item,
      });
    },
    // 完成交易
    commitOrder(item) {
      this.$refs.tihaoDialog.open(item, "edit");
    },
    // 销售端客服提号
    addOrder(item) {
      this.$refs.tihaoDialog.open(item);
      // this.$modal
      //   .confirm(
      //     "是否确认提取编号为" + row.id + '"的游戏账号？'
      //   )
      //   .then(() => {
      //     goodsListApi.salePickUp(row.id).then(res => {
      //       this.$modal.msgSuccess(res.msg);
      //       this.getList()
      //     })
      //   })
    },
    // 顶级账号改变
    changeIstop(e, id) {
      goodsListApi
        .topOnOrOff({
          id: id,
          isTop: e,
        })
        .then((response) => {
          if (response.code == 200) {
            this.$modal.msgSuccess("修改成功");
            this.getList();
          }
        });
    },
    // 是否推荐
    changeIspush(e, id) {
      goodsListApi
        .tuisongOff({
          id: id,
          isPush: e,
        })
        .then((response) => {
          if (response.code == 200) {
            this.$modal.msgSuccess("修改成功");
            this.getList();
          }
        });
    },
    // 是否特价
    changeTePriceAccount(e, id) {
      getSupTePriceAccount({
        id: id,
        isOnSpecialOffer: e,
      }).then((response) => {
        if (response.code == 200) {
          this.$modal.msgSuccess("修改成功");
          this.getList();
        }
      });
    },
    // 下架商品
    downSale(id) {
      this.$prompt("下架原因", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /\S/,
        inputErrorMessage: "请填写下架原因",
      })
        .then(({ value }) => {
          this.$modal
            .confirm("是否确认下架编号为" + id + '"的游戏账号？')
            .then(() => {
              goodsListApi
                .downGoods({
                  downDes: value,
                  id: id,
                })
                .then((res) => {
                  this.$modal.msgSuccess(res.msg);
                  this.getList();
                });
            });
        })
        .catch(() => {});
    },
    //转挂售
    zhuanGuashou(id) {
      this.$modal
        .confirm("是否确认上架编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.zhuanGuashou(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },
    // 上架商品
    upSale(id) {
      this.$modal
        .confirm("是否确认转挂售编号为" + id + '"的游戏账号？')
        .then(() => {
          goodsListApi.upGoods(id).then((res) => {
            this.$modal.msgSuccess(res.msg);
            this.getList();
          });
        });
    },

    // 转为问题号
    transQues(id) {
      this.$prompt("问题描述", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /\S/,
        inputErrorMessage: "请填写问题描述",
      })
        .then(({ value }) => {
          this.$modal
            .confirm(
              "如果该账号已出售，关联订单将会销毁，是否确认编号为" +
                id +
                '"的游戏账号转为问题账号？'
            )
            .then(() => {
              goodsListApi
                .problemGoods({
                  problemDes: value,
                  id: id,
                })
                .then((res) => {
                  this.$modal.msgSuccess(res.msg);
                  this.getList();
                });
            });
        })
        .catch(() => {});
    },
    // 转为找回
    transZhaohui(id) {
      this.$prompt("找回备注", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /\S/,
        inputErrorMessage: "请填写找回备注",
      })
        .then(({ value }) => {
          this.$modal
            .confirm("是否确认编号为" + id + '"的游戏账号转为找回账号？')
            .then(() => {
              goodsListApi
                .retrieveGoods({
                  retrieveDes: value,
                  id: id,
                })
                .then((res) => {
                  this.$modal.msgSuccess(res.msg);
                  this.getList();
                });
            });
        })
        .catch(() => {});
    },
    // 改变状态
    handleAudit(type, operation, id, flag) {
      const ids = id;
      let params = {};
      params.type = type;
      params.operation = operation;

      // 转为问题号需要备注
      if (type == 4 && !flag) {
        this.showDialogTitle = "转为问题号";
        this.tempData = params;
        this.tempData.ids = ids;
        this.dialogFormVisible = true;
        return;
      }
      // 下架需要填写原因
      if (type == 7 && !flag) {
        this.showDialogTitle = "下架原因";
        this.tempData = params;
        this.tempData.ids = ids;
        this.dialogFormVisible = true;
        return;
      }
      if (type == 4) {
        params.note = this.unsoldFrom.note;
      }
      if (type == 7) {
        params.offres = this.unsoldFrom.note;
      }
      this.$modal
        .confirm(
          "是否确认" + operation + '游戏账号编号为"' + ids + '"的数据项？'
        )
        .then(function () {
          return auditInfo(ids, params);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(operation + "成功");
        })
        .catch(() => {});
    },
    // // 上架
    // upAccountCommit(id) {
    //   this.$modal
    //     .confirm("是否确认上架" + id + '"？')
    //     .then(function () {
    //       return upAccount(id);
    //     })
    //     .then(() => {
    //       this.getList();
    //       this.$modal.msgSuccess("上架成功");
    //     })
    //     .catch(() => { });
    // },
    // 转为问题号，下架
    confirmSub(flag) {
      this.dialogFormVisible = false;
      this.handleAudit(
        this.tempData.type,
        this.tempData.operation,
        this.tempData.ids,
        true
      );
    },
    // 双击编辑信息
    handleUpdate(row) {
      if (
        (this.actived != 1 && this.actived != 0) ||
        this.pageType == "pool" ||
        this.pageType == "salePort"
      ) {
        this.$refs.addDialog.open("edit", row, this.actived);
      } else if (row.saleState != 1 && row.saleState != 0) {
        // this.$refs.addDialog.open("view", row);
        this.$refs.addDialog.open("edit", row, this.actived);
      } else {
        this.$refs.addDialog.open("edit", row, this.actived);
      }
    },
    // 查看备注
    showSinNum(row) {
      this.sinNumShow = row.sinNum;
      this.showSinNumVisible = true;
    },
    // 获取商品类型列表
    getGoodsTypeList() {
      this.goodsTypeList = [];
      gameDictionaryApi
        .getChildList({
          gameId: this.queryParams.gameId,
          systemKey: "goodstype",
        })
        .then((res) => {
          this.goodsTypeList = res.rows;
          console.log(
            "%c Line:501 🥐 this.goodsTypeList",
            "color:#7f2b82",
            this.goodsTypeList
          );
        });
    },
    // 选择游戏
    selectGameType(val) {
      if (!val) {
        val = null;
        this.sysList = [];
        this.carrierList = [];
        this.areaList = [];
        return;
      }
      this.gameList.forEach((r) => {
        if (r.id == val) {
          this.GameSelectedType = r.type;
        }
      });
      if (this.GameSelectedType === 0) {
        // 手游
        // 获取系统列表和运营商列表
        this.getSysList();
        this.getYysList();
      } else {
        // 端游
        // 获取大区列表
        this.getAreaList2();
      }
      this.getGoodsTypeList();
      this.search();
    },
    // 获取系统列表
    getSysList() {
      gameDictionaryApi
        .getChildList({
          gameId: this.queryParams.gameId,
          systemKey: "system",
        })
        .then((res) => {
          this.sysList = res.rows;
        });
    },
    // 获取运营商列表
    getYysList() {
      gameDictionaryApi
        .getChildList({
          gameId: this.queryParams.gameId,
          systemKey: "carrier",
        })
        .then((res) => {
          this.carrierList = res.rows;
        });
    },
    // 运营商改变  获取大区列表
    getAreaList(e) {
      this.search();
      let obj = {
        gameId: this.queryParams.gameId,
        systemKey: "group",
      };
      this.queryParams.groupId = "";
      this.queryParams.serverId = "";

      // 运营商改变 找出选择的运营商的对象
      if (e) {
        this.carrierList.forEach((i) => {
          if (i.id == e) {
            obj.systemKey = i.systemKey + "group";
          }
        });
      }
      gameDictionaryApi.getChildList(obj).then((res) => {
        this.areaList = res.rows;
      });
    },
    // 大区改变  获取服务器列表
    getServiceList(e) {
      this.search();
      this.queryParams.serverId = "";

      if (e) {
        gameDictionaryApi
          .getList({
            parentId: e,
          })
          .then((res) => {
            this.serverList = res.rows;
          });
      } else {
        this.serverList = [];
      }
    },
    //端游 直接获取大区
    getAreaList2() {
      let obj = {
        gameId: this.queryParams.gameId,
        systemKey: "group",
      };
      gameDictionaryApi.getChildList(obj).then((res) => {
        this.areaList = res.rows;
      });
    },
    // 加入验证手机号
    addCheck(row) {
      this.$refs.addCheckPhone.open("myAccount", row);
      // checkPhoneApi.add({
      //   accountId: row.id,
      //   account: row.accountNumber,
      //   checkTel: row.securityPhone
      // }).then(res => {
      //   this.$message.success(res.msg);
      //   this.getList();
      // });
    },
    // 复制商品信息
    keyCopy(item) {
      let hostt = `${this.retailDomainList[0].dictValue}/#/pages/detail/index?id=${item.id}`;
      this.copyVlaue = `商品ID：${item.id}\n自编号：${item.thirdId}\n区服：${
        item.systemName ? item.systemName + "/" : ""
      }${item.carrierName ? item.carrierName + "/" : ""}${item.groupName}${
        item.serverName
      }\n简介：${item.des}\n图片：${hostt}`;
    },
    // 复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
      this.copyVlaue = "";
    },
    onError() {
      this.$modal.msgError("复制失败");
      this.copyVlaue = "";
    },
    //重置
    resetQuery() {
      let dataType = this.queryParams.dataType;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        saleState: "",
        // operType: "supply",
        sort: "",
        sortColumn: "",
        dataType: dataType,
        recoveryTimeEnd: null,
        recoveryTimeStart: null,
      };
      this.timer = [];
      this.queryParams.saleState = this.actived ? this.actived : "";
      this.getList();
    },
    // getHost() {
    //   getDicts("apphost").then(res => {
    //     if (res.code == 200) {
    //       console.log(res);
    //       if (res.data.length > 0) {
    //         this.hoste = res.data[0].dictValue;
    //       }
    //     }
    //   });
    // },
    // 每页条数改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    // 页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    },
  },
};
</script>
<style lang="scss" scoped>
::v-deep .el-table .warning-row {
  background: #f2f6f9;
}
::v-deep .el-table .success-row {
  background: #fff;
}

::v-deep .el-table .xuanzhong-row {
  color: #3f9eff;
}

.topNav {
  background-color: #3f9eff;
}

.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 1;
  // margin: 6px 0;
}

::v-deep .caozuo_item {
  overflow-x: auto !important;
  width: 300px;
}

#qrCode {
  display: inline-block;
  margin: 0 auto;
  /*水平居中*/
  position: relative;
  top: 15%;

  /* img: {
    width: 200px;
    height: 200px;
    background-color: #fff; //设置白色背景色
    padding: 6px; // 利用padding的特性，挤出白边
  } */
}

.search-form-new ::v-deep .el-input {
  width: 200px !important;
}

.weishouAll {
  margin-left: 10px;
  .el-dropdown-link {
    cursor: pointer;
    color: red;
  }
  .el-icon-arrow-down {
    font-size: 12px;
    color: red;
  }
}

.sign {
  display: inline-block;
  width: 20px;
  height: 20px;
  background-color: #ffcc66;
  border-radius: 50%;
  font-size: 12px;
  line-height: 20px;
  text-align: center;
  color: #fff;
  margin-right: 5px;

  &.ping {
    background-color: #1890ff;
  }
}

// .tuisongbtn ::v-deep .el-dropdown-menu {
//   max-height: 600px;
//   overflow-y: hidden;
// }

.zhuangtai {
  width: 66px;
  height: 26px;
  text-align: center;
  line-height: 26px;
  background-color: rgba(247, 181, 0, 0.1);
  color: #f7b500;
  border-radius: 5px;
  margin-left: 6px;
}

::v-deep .el-table {
  border-radius: 10px;
  .el-table__row {
    height: 30px !important;
    .cell {
      height: 26px;
      line-height: 26px;
    }
  }

  .el-table__cell {
    padding: 10px 0 !important;
  }
}

::v-deep .el-tabs__nav-scroll {
  border-bottom: none !important;
  // border: 1px dashed #919191;
}

::v-deep .kkk {
  background-color: #f0f0f0;
}

.app-container {
  background-color: #f0f0f0;
}

::v-deep .el-step__head {
  display: none;
}
</style>
<style>
.el-dropdown-menu {
  max-height: 600px;
  overflow-y: auto;
}
</style>
