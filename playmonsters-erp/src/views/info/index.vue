<template>
  <div class="app-container jklis">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" class="zdy-form" label-width="68px">
      <el-form-item label="编号" prop="width=150" class="mm-b-8">
        <el-input v-model="queryParams.id" placeholder="请输入编号" clearable size="small" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="游戏类型" prop="type" class="mm-b-8">
        <el-select @change="selectGameType" v-model="queryParams.type" placeholder="请选择游戏类型" clearable size="small">
          <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="区服" prop="regional" class="mm-b-8">
        <el-select @change="selectGameDaQu" multiple v-model="queryParams.regionalAddr" placeholder="请选择区服" size="small">
          <el-option v-for="dict in regionalArr" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="实名认证" prop="level" class="mm-b-8">
        <el-select @change="selectLevel" multiple v-model="queryParams.levelAddr" placeholder="请选择实名认证" clearable
          size="small">
          <el-option v-for="dict in levelArr" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
        </el-select>
      </el-form-item>
      <el-form-item label="销售状态" v-if="show.intParam.salesStatus" prop="type" class="mm-b-8">
        <el-select v-model="queryParams.saleState" placeholder="请选择销售状态" clearable size="small">
          <el-option v-for="dict in dict.type.account_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="价格区间" prop="price" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.priceStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.priceEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>

      <el-form-item v-if="show.select.heroes" label="英雄数量" prop="heroes" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.heroStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.heroEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.skins" label="皮肤数量" prop="skins" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.skinStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.skinEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.suit" label="套装数量" prop="suit" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.suitStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.suitEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.select.guns" label="枪皮" prop="guns" class="mm-b-8">
        <div class="two-input-content">
          <el-input size="small" v-model="queryParams.gunsStart" class="two-input-item"></el-input>
          <span></span>
          <el-input size="small" v-model="queryParams.gunsEnd" class="two-input-item"></el-input>
        </div>
      </el-form-item>
      <el-form-item v-if="show.intParam.extractAccount" label="账号" prop="extractAccount" class="mm-b-8">
        <el-input v-model="queryParams.extractAccount" placeholder="请输入账号" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="自定义" prop="accountId" class="mm-b-8">
        <el-input v-model="queryParams.des" placeholder="请输入自定义关键词" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="简介" prop="accountId" class="mm-b-8">
        <el-input v-model="queryParams.desQur" placeholder="请输入简介" clearable size="small"
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item v-if="show.intParam.supplyName" label="供货客服" prop="supplyId" class="mm-b-8">
        <el-select multiple filterable v-model="queryParams.supplyIdAddr" @change="selectGameDaQu" placeholder="请选择供货客服"
          clearable size="small">
          <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
        </el-select>
      </el-form-item>
      <el-form-item v-if="show.intParam.salesName" label="销售客服" prop="salesId" class="mm-b-8">
        <el-select multiple filterable v-model="queryParams.salesIdAddr" placeholder="请选择销售客服" clearable size="small">
          <el-option @change="selectGameDaQu" v-for="dict in saleList" :key="dict.userId" :label="dict.nickName"
            :value="dict.userId" />
        </el-select>
      </el-form-item>

      <el-form-item v-if="show.intParam.saleTime" label="销售时间" prop="extractPwd" class="mm-b-8">
        <el-date-picker @change="selectSaleTime" v-model="saleTimeArr" size="small" format="yyyy-MM-dd" type="daterange"
          range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item v-if="show.intParam.createTime" label="导入时间" prop="createTime" class="mm-b-8">
        <el-date-picker @change="selectCreateTime" v-model="createTimeArr" size="small" format="yyyy-MM-dd"
          type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item v-if="show.intParam.updateTime" label="更新时间" prop="createTime" class="mm-b-8">
        <el-date-picker @change="selectupdateTime" v-model="updateTimeArr" size="small" format="yyyy-MM-dd"
          type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <!--
            <el-form-item v-if="show.intParam.sinNum" label="自编号" prop="sinNum">
              <el-input
                v-model="queryParams.sinNum"
                placeholder="请输入自编号"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
      </el-form-item>-->
      <!--      <el-form-item v-if="show.intParam.campId" label="营地ID" prop="campId">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.campId"-->
      <!--          placeholder="请输入营地ID"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item v-if="show.intParam.timeout" label="超时天数" prop="sinNum" class="mm-b-8">
        <el-col :span="10">
          <el-input v-model="queryParams.timeout" clearable size="small" @keyup.enter.native="handleQuery" />
        </el-col>
      </el-form-item>

      <el-form-item class="mm-b-8">
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery" class="zdy-btn">搜索</el-button>
        <el-button icon="el-icon-refresh" type="primary" size="mini" @click="resetQuery"
          class="zdy-btn zdy-btn-refresh">重置</el-button>
      </el-form-item>
    </el-form>
    <div class="zdy-main">
      <div class="zdy-table-header">
        <div class="zdy-table-header-search">
          <el-input placeholder="请输入搜索内容" prefix-icon="el-icon-search" size="small"></el-input>
        </div>
        <div class="zdy-table-header-btn">
          <zdy-right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></zdy-right-toolbar>
          <div class="zdy-pifu-content">
            <img src="@/assets/images/pifu.png" alt @click="showField" />
          </div>
          <div class="zdy-header-btn-group">
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          v-if="jiajianjia"-->
            <!--          plain-->
            <!--          icon="el-icon-success"-->
            <!--          size="mini"-->
            <!--          @click="addOrsubPrice"-->
            <!--        >加减价-->
            <!--        </el-button-->
            <!--        >-->
            <!--      </el-col>-->
            <el-button v-if="showAudit" type="danger" plain icon="el-icon-success" size="mini" :disabled="multiple"
              @click="handleAudit(1, '审核')" v-hasPermi="['system:info:audit']">审核</el-button>
            <el-button type="danger" v-if="showAudit" plain icon="el-icon-success" size="mini"
              @click="oneKeyLable">一键打签</el-button>
            <!--      <el-col :span="1.5">-->
            <!--        <el-button-->
            <!--          v-if="addAcount"-->
            <!--          type="danger"-->
            <!--          plain-->
            <!--          icon="el-icon-success"-->
            <!--          size="mini"-->
            <!--          :disabled="multiple"-->
            <!--          @click="handleAudit(1, '审核')"-->
            <!--          v-hasPermi="['system:info:audit']"-->
            <!--        >审核-->
            <!--        </el-button-->
            <!--        >-->
            <!--      </el-col>-->
            <el-button v-clipboard:copy="copyVlaue" v-if="copyOpe" type="danger" plain icon="el-icon-document-copy"
              size="mini" v-clipboard:error="onError" v-clipboard:success="onCopy" class="zdy-plfz-btn"
              :disabled="multiple" v-hasPermi="['system:info:pwd']">批量复制</el-button>
            <el-button v-if="extractPwd" v-clipboard:copy="copyPwd" type="danger" plain icon="el-icon-lock"
              class="zdy-tqmm-btn" size="mini" :disabled="multiple" @click="handleAudit(2, '提取密码')"
              v-hasPermi="['system:info:pwd']">提号</el-button>
            <el-button v-if="sinceSale" type="danger" plain icon="el-icon-s-ticket" size="mini" :disabled="multiple"
              @click="handleAudit(7, '转自售')" v-hasPermi="['system:info:sinceSale']">下架</el-button>
            <el-button v-if="sinceSale" type="danger" plain icon="el-icon-s-ticket" size="mini" :disabled="multiple"
              @click="bacthOff()" v-hasPermi="['system:info:sinceSale']">批量下架</el-button>
            <el-button v-if="cancelIn" type="danger" plain icon="el-icon-lock" size="mini" :disabled="multiple"
              class="zdy-plfz-btn" @click="submitOrder" v-hasPermi="['system:info:cancel']">下单</el-button>
            <!-- <el-button
              v-if="extractTel"
              type="danger"
              plain
              icon="el-icon-phone"
              size="mini"
              :disabled="multiple"
              @click="extractTelFun"
              v-hasPermi="['system:info:tel']"
            >密保信息</el-button>-->
            <el-button v-if="cancelIn" type="danger" plain icon="el-icon-remove" size="mini" :disabled="multiple"
              @click="cancelSold" v-hasPermi="['system:info:cancel']">取消售中</el-button>
            <el-button v-if="transfer" type="danger" plain icon="el-icon-remove" size="mini" :disabled="multiple"
              @click="handleAudit(1, '转移未售')" v-hasPermi="['system:info:cancel']">转移未售</el-button>
            <el-button v-if="back" type="danger" plain icon="el-icon-remove" size="mini" :disabled="multiple"
              @click="handleAudit(5, '转移找回')" v-hasPermi="['system:info:back']">转移找回</el-button>
            <el-button v-if="problem" type="danger" plain icon="el-icon-warning" size="mini" :disabled="multiple"
              @click="transferProblem" v-hasPermi="['system:info:problem']">转为问题号</el-button>
            <el-button v-if="pending" type="danger" plain icon="el-icon-s-promotion" size="mini" :disabled="multiple"
              @click="handleAudit(1, '转为未售')" v-hasPermi="['system:info:pending']">转为未售</el-button>
            <el-col :span="1.5">
              <el-button v-if="show.shelves" type="danger" plain icon="el-icon-s-promotion" size="mini"
                :disabled="multiple" @click="handleAudit(7, '转自售')" v-hasPermi="['system:info:remove']">下架</el-button>
            </el-col>
            <!-- <el-button
              type="danger"
              plain
              icon="el-icon-s-promotion"
              size="mini"
              v-if="outAccountDel"
              @click="deleteAcount()"
            >删除账号</el-button>
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              v-if="outAccount"
              @click="handleDelete"
            >删除账号</el-button> -->
            <el-button type="danger" plain icon="el-icon-delete" size="mini" v-if="showAudit"
              @click="handleDelete">批量删除</el-button>
            <el-button type="danger" plain icon="el-icon-delete" size="mini" v-if="showAudit"
              @click="deleteAll">全部删除</el-button>
            <el-button type="primary" plain icon="el-icon-refresh-right" size="mini" v-if="outAccount" @click="polish"
              :loading="polishLoading" :disabled="isDisabled">擦亮</el-button>
            <el-button type="primary" plain icon="el-icon-circle-plus" size="mini" v-if="outAccount"
              @click="addACount()">新增</el-button>
            <el-button type="primary" plain icon="el-icon-circle-plus" size="mini" @click="pushJym()">交易猫推送</el-button>
            <!-- <el-button
              type="primary"
              plain
              icon="el-icon-circle-plus"
              size="mini"
              @click="pushBatch()"
            >批量推送</el-button>-->
            <el-dropdown @command="pushBatch">
              <el-button type="primary" size="mini">
                批量推送
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item v-for="item in pushApiList" :command="item.id">{{ item.name }}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <!-- <el-button
              type="danger"
              plain
              icon="el-icon-s-promotion"
              size="mini"
              v-if="outAccount"
              @click="onePushAcount()"
            >一键推送</el-button>-->
            <div v-if="show.onOfOff">
              <span size="small">隐藏无关属性</span>
              <el-switch @change="onOfOff" v-model="irreAttr" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
            </div>
            <!-- <el-col :span="1.5">
      <el-switch
      v-if="show.exportExelShow"
       inactive-text="上架"
       active-text="下架"
        v-model="queryParams.saleState"
        active-color="#13ce66"
        inactive-color="#ff4949"
        active-value="8"
        @change="onOfOff"
        inactive-value="1">
      </el-switch>
    </el-tooltip>
            </el-col>-->
          </div>
        </div>
      </div>
      <el-table v-loading="loading" ref="singleTable" highlight-current-row :height="table.tableHeight" :data="infoList"
        fit :header-cell-style="{
          textAlign: 'center',
          height: '20px',
        }" :border="true" :cell-style="cellStyle" :row-class-name="tableRowClassName" @sort-change="sortFun"
        @cell-dblclick="handleUpdate" @selection-change="handleSelectionChange">
        <el-table-column type="expand" width="40">
          <template slot-scope="props">
            <el-form label-position="right">
              <el-form-item :style="{ marginRight: wihh + '%' }" label-width="110px">
                <!-- <span>{{ props.row.des }}</span> -->
                <template slot-scope="scope">
                  <!--  <el-tooltip :content="props.row.des" placement="top">
                <span   class="showOverTooltip">{{props.row.des}}</span>
                  </el-tooltip>-->
                  <span class="showOverTooltip">{{ props.row.des }}</span>
                </template>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="序号" sortable="custom" width="80" label-position="left" sort-by align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.lable == 0" style="color: #ff6d6d">{{ scope.row.id }}</span>
            <span v-if="scope.row.lable == 1">{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="show.type" label="游戏类型" width="80" align="center" prop="type" />
        <el-table-column v-if="show.regional" label="区服" width="80" align="center" prop="regional"></el-table-column>
        <el-table-column v-if="show.gameName" label="自编号" align="center" width="80" prop="gameName" />
        <el-table-column v-if="show.level" label="实名认证" align="center" width="80" prop="level" />
        <el-table-column sortable="custom" sort-by v-if="show.heroes" label="英雄" width="80" align="center"
          prop="heroes" />
        <el-table-column sortable="custom" sort-by v-if="show.skins" label="皮肤" width="80" align="center" prop="skins" />
        <el-table-column sortable="custom" sort-by v-if="show.suit" label="套装" align="center" width="80" prop="suit" />
        <el-table-column sortable="custom" sort-by v-if="show.guns" label="枪皮" align="center" width="80" prop="guns" />
        <el-table-column sortable="custom" sort-by label="售价" v-if="show.newPrice" width="100" align="center"
          prop="newPrice">
          <template slot-scope="scope">
            <span>{{ scope.row.newPrice }}</span>
            <span>
              <el-popover @show="getMore(scope.row)" placement="left" title="更多" width="250" trigger="click">
                <el-table :data="morePrice">
                  <el-table-column width="100" property="saleName" label="销售名称"></el-table-column>
                  <el-table-column width="100" property="price" label="价格"></el-table-column>
                </el-table>

                <el-button slot="reference" size="mini">更多</el-button>
              </el-popover>
            </span>
          </template>
        </el-table-column>
        <!--      <el-table-column-->
        <!--        v-if="show.campId"-->
        <!--        label="营地ID"-->
        <!--        align="center"-->
        <!--        prop="campId"-->
        <!--      />-->
        <el-table-column v-if="show.supplyName" width="80" label="供货客服" align="center" prop="supplyName" />
        <el-table-column sortable="custom" sort-by v-if="show.price" label="供货价" width="100" align="center"
          prop="price" />
        <!-- <el-table-column
          v-if="show.saleStateName"
          label="销售状态"
          align="center"
          prop="saleStateName"
        />-->
        <el-table-column label="销售状态" width="80" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.saleStateName == '自售账号'" style="color: #ff6d6d">已下架</span>
            <span v-if="scope.row.saleStateName != '自售账号'">{{ scope.row.saleStateName }}</span>
          </template>
        </el-table-column>
        <!-- <span   class="showOverTooltip">{{props.row.des}}</span> -->
        <el-table-column label="简介" min-width="600" align="center" prop="des">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
              <div class="zdy-des">{{ scope.row.des }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column v-if="show.extractAccount" label="账号" width="200" align="center" prop="extractAccount" />
        <el-table-column v-if="show.extractPwd" label="密码" width="200" align="center" prop="extractPwd" />
        <el-table-column v-if="show.extractTel" label="密保信息" width="200" align="center" prop="extractTel" />
        <el-table-column sortable="custom" sort-by v-if="show.costPrice" label="成本价" align="center" prop="costPrice"
          width="100" />
        <el-table-column v-if="show.accountRemake" label="账号备注" align="center" prop="sinNum" width="100">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="showSinNum(scope.row)">备注</el-button>
          </template>
        </el-table-column>
        <el-table-column sortable="custom" sort-by v-if="show.rise" label="上浮" width="100" align="center" prop="rise" />

        <el-table-column v-if="show.salesName" label="销售客服" width="80" align="center" prop="salesName" />
        <el-table-column sortable="custom" sort-by v-if="show.createTime" label="导入时间" width="160" align="center"
          prop="createTime" />
        <el-table-column sortable="custom" sort-by v-if="show.saleTime" label="销售时间" width="160" align="center"
          min-width="100%" prop="saleTime" />
        <el-table-column v-if="show.updateName" label="更新人" align="center" prop="updateName" />
        <el-table-column sortable="custom" sort-by v-if="show.updateTime" label="更新时间" width="180" align="center"
          prop="updateTime" />
        <el-table-column v-if="show.buyId" label="下单信息" align="center" prop="buyId" />
        <el-table-column sortable="custom" sort-by v-if="show.realPrice" label="真实售价" width="100" align="center"
          prop="realPrice" />
        <el-table-column v-if="show.remake" label="购买备注" align="center" prop="remake" />
        <el-table-column v-if="show.auditName" label="审核人" align="center" prop="auditName" />
        <el-table-column v-if="show.auditState" label="审核状态" align="center" prop="auditState" />
        <el-table-column v-if="show.note" label="备注" align="center" prop="note" />
        <el-table-column label="日志" align="center" fixed="right" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" @click="showApiRec(scope.row)">接口记录</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination v-show="total > 0" :background="false" :total="total" :page-sizes="[10, 20, 30, 40, 50, 60]"
      :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
    <!-- 添加或修改游戏账号对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body @close="closeDialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="游戏类型" prop="type">
              <el-select v-model="form.type" @change="selectDialogGameType" placeholder="请选择游戏类型">
                <el-option v-for="dict in dict.type.game_type" :key="dict.value" :label="dict.label"
                  :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="区服" prop="regional">
              <el-select v-model="form.regional" filterable placeholder="请选择区服">
                <el-option v-for="dict in regionalArr" :key="dict.dictLabel" :label="dict.dictLabel"
                  :value="dict.dictLabel" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="showLable(1)">标签配置</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="自编号" prop="gameName">
              <el-input v-model="form.gameName" placeholder="请输入自编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <!--            <el-form-item label="实名认证" prop="level">-->
            <!--              <el-input v-model="form.level" placeholder="请输入实名认证" />-->
            <!--            </el-form-item>-->
            <el-form-item label="实名认证" prop="level">
              <el-select @change="selectLevel" v-model="form.level" placeholder="请选择实名认证" clearable size="small">
                <el-option v-for="dict in levelArr" :key="dict.dictValue" :label="dict.dictLabel"
                  :value="dict.dictValue" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item v-if="dialogGameType" label="英雄数量" prop="heroes">
              <el-input v-model="form.heroes" placeholder="请输入英雄数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="dialogGameType" label="皮肤数量" prop="skins">
              <el-input v-model="form.skins" placeholder="请输入皮肤数量" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item v-if="dialogGameTypeHp" label="套装" prop="suit">
              <el-input v-model="form.suit" placeholder="请输入套装" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="dialogGameTypeHp" label="枪皮" prop="guns">
              <el-input v-model="form.guns" placeholder="请输入枪皮" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出售价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入价格" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号" prop="extractAccount">
              <el-input v-model="form.extractAccount" placeholder="请输入账号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="密码" prop="extractPwd">
              <el-input v-model="form.extractPwd" placeholder="请输入密码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机" prop="extractAccount">
              <el-input v-model="form.extractTel" placeholder="请输入提取手机" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="成本价格" prop="costPrice">
              <el-input v-model="form.costPrice" placeholder="请输入成本价格" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="营地ID" prop="campId">
              <el-input v-model="form.campId" placeholder="请输入营地ID" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-button round @click="gengerRole()">一键获取</el-button>
          </el-col>
          <el-col :span="3">
            <!-- <el-button type="primary" @click="gengertiImge()" round>图片生成</el-button> -->
            <el-button type="primary" @click="showModel()" round>图片生成</el-button>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="是否特价" prop="accType">
              <el-switch @change="changeSpecial" v-model="form.accType" active-color="#13ce66" active-value="1"
                inactive-value="0" inactive-color="#ff4949"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="特价金额" prop="specialPrice">
              <el-input v-model="form.specialPrice" :disabled="ifSpecial" placeholder="请输入特价金额" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="wzry">
          <el-col :span="6">
            <el-form-item label="贵族等级" prop="guizuLevel">
              <el-input v-model="form.guizuLevel" placeholder="请输入贵族等级" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="贵族积分" prop="guizufen">
              <el-input v-model="form.guizufen" placeholder="请输入贵族积分" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="五级铭文" prop="wuji">
              <el-input v-model="form.wuji" placeholder="请输入五级铭文" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="王者印记" prop="yinji">
              <el-input v-model="form.yinji" placeholder="请输入王者印记" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="账号备注" prop="sinNum">
          <el-input v-model="form.sinNum" type="textarea" placeholder="请输入内容" :rows="4" />
        </el-form-item>
        <el-form-item label="热门皮肤" prop="jingme">
          <el-input v-model="jingmeie" type="textarea" placeholder="请输入热门皮肤" :rows="5" />
        </el-form-item>
        <el-form-item label="简介" prop="des">
          <el-input v-model="form.des" type="textarea" placeholder="请输入内容" :rows="8" />
        </el-form-item>

        <el-form-item label="视频" prop="des">
          <!--          <el-checkbox-group v-model="form.fileList"></el-checkbox-group>
          <PackageUpload @onUploadCompleted="onUploadCompleted"
                         @onFileRemoved="onFileRemoved"
          ></PackageUpload>-->

          <el-upload class="upload-demo" :action="uploadImgUrl" :on-remove="handleVideoRemove"
            :on-success="handleVideoSuccess" multiple :limit="1" accept="mp4" :file-list="videoList">
            <el-button size="small" type="primary">点击上传</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="标题图片" prop="titleImg">
          <el-row>
            <el-col :span="12">
              <el-upload class="avatar-uploader" :action="uploadImgUrl" :show-file-list="false"
                :on-success="handleAvatarSuccess" :on-remove="handleAvatarRemove">
                <img v-if="imageUrl" :src="imageUrl" class="avatar" />
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-col>
            <el-col :span="12">
              <el-button type="primary" @click="showLable(2)" round>标签图片</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item>
          <el-upload :action="uploadImgUrl" :file-list="imgShowList" list-type="picture-card"
            :on-preview="handlePictureCardPreview" :on-exceed="uploadNumber" :multiple="true"
            :on-success="handleUploadSuccess" :limit="50" :on-remove="handleRemove">
            <i class="el-icon-plus"></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <el-dialog :visible.sync="dialogVisible">
        <img width="100%" :src="dialogImageUrl" alt />
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="showDialogTitle" :visible.sync="dialogFormVisible">
      <el-form :model="unsoldFrom">
        <el-form-item label="备注" :label-width="formLabelWidth">
          <el-input v-model="unsoldFrom.note" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmSub">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="showDialogTitle" :visible.sync="dialogTelFormVisible">
      <el-form :model="telFrom">
        <el-form-item label="买家ID" :label-width="formLabelWidth">
          <el-input v-model="telFrom.buyId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="真实售价" :label-width="formLabelWidth">
          <el-input @change="checkPriceOut" v-model="telFrom.realPrice" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="购买备注" :label-width="formLabelWidth">
          <el-input v-model="telFrom.remake" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogTelFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmTel">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="skinFormVisible">
      <AllSkinSelect @skinType="skinType"></AllSkinSelect>
      <div slot="footer" class="dialog-footer">
        <el-button @click="canceSkin">取 消</el-button>
        <el-button type="primary" @click="confirmSkin">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="roleListVisible" v-loading="loadingSkin" width="40%">
      <el-table @cell-dblclick="gengerTitle" :data="roleList" style="width: 100%">
        <el-table-column prop="areaName" label="大区" width="180"></el-table-column>
        <el-table-column prop="grade" label="等级" width="180"></el-table-column>
        <el-table-column prop="roleJob" label="昵称"></el-table-column>
        <el-table-column prop="serverName" label="服务器"></el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog title="下单信息" :visible.sync="submitOrderDialog" width="30%" append-to-body>
      <el-form ref="sleetcData" :model="sleetcData">
        <el-form-item label="销售渠道" prop="saleChannel">
          <el-select v-model="sleetcData.saleChannel" placeholder="请选择销售渠道">
            <el-option v-for="dict in dict.type.sale_user" :key="dict.label" :label="dict.label"
              :value="dict.label"></el-option>
          </el-select>
        </el-form-item>

        <!-- <el-form-item label="供货渠道" prop="supplyChannel">
          <el-select v-model="sleetcData.supplyChannel" placeholder="请选择供货渠道">
            <el-option
              v-for="dict in dict.type.supply_user"
              :key="dict.label"
              :label="dict.label"
              :value="dict.label"
            ></el-option>
          </el-select>
        </el-form-item>-->

        <el-form-item label="销售金额" prop="salePrice">
          <el-input v-model="sleetcData.salePrice" placeholder="请输入销售金额" />
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" @click="inputSalePrice">计算</el-button>
        </el-form-item>-->
        <!-- <el-form-item label="销售折扣后金额" prop="saleDiscountPrice">
          <el-input v-model="sleetcData.saleDiscountPrice" placeholder="请输入销售折扣后金额" />
        </el-form-item>-->
        <!-- <el-form-item label="销售折扣" prop="saleDiscount">
          <el-input v-model="sleetcData.saleDiscount" placeholder="请输入销售折扣" />
        </el-form-item>-->
        <el-form-item label="供货金额" prop="suplyprice">
          <el-input v-model="sleetcData.suplyprice" placeholder="请输入供货折扣前金额" />
        </el-form-item>
        <!-- <el-form-item label="供货金额" prop="supplyDiscountPrice">
          <el-input v-model="sleetcData.supplyDiscountPrice" placeholder="请输入供货金额" />
        </el-form-item>-->
        <!-- <el-form-item label="供货折扣" prop="supplyDiscount">
          <el-input v-model="sleetcData.supplyDiscount" placeholder="请输入供货折扣" />
        </el-form-item>-->
        <el-form-item label="账号" prop="supplyDiscount">
          <el-input v-model="sleetcData.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="供货是否支付" prop="supllypay">
          <el-select v-model="sleetcData.supllypay" placeholder="请选择是否支付">
            <el-option v-for="dict in dict.type.ifpay" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="销售是否支付" prop="ifPay">
          <el-select v-model="sleetcData.ifPay" placeholder="请选择是否支付">
            <el-option v-for="dict in dict.type.ifpay" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input type="textarea" :rows="5" v-model="sleetcData.remake" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="submitOrderDialog = false">取 消</el-button>
        <el-button type="primary" @click="confirmSbmitOrder">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="加减价" :visible.sync="addOrsubPriceVisible" width="20%">
      <BacthAddPrice @addFrom="addFrom"></BacthAddPrice>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addOrsubPriceVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAddSub">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="全部删除" :visible.sync="deleteAllDialog" v-if="deleteAllDialog" width="20%">
      <el-form ref="deleteForm" :model="deleteForm">
        <el-form-item label="供货客服" prop="supllypay">
          <el-select multiple v-model="deleteForm.supplyIdAddr" placeholder="请选择供货客服" clearable size="small">
            <el-option v-for="dict in supplyList" :key="dict.userId" :label="dict.nickName" :value="dict.userId" />
          </el-select>
        </el-form-item>
        <el-form-item label="合作伙伴" prop="supllypay">
          <el-select multiple v-model="deleteForm.configArr" placeholder="请选择合作伙伴" clearable size="small">
            <el-option v-for="dict in interNameList" :key="dict.id" :label="dict.name" :value="dict.id" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="deleteAllDialog = false">取 消</el-button>
        <el-button type="primary" @click="deleteAllAccount">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="接口日志" :visible.sync="dialogApiRecVisible" width="70%">
      <el-form :model="queryRecParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="销售伙伴" prop="saleId">
          <el-select v-model="queryRecParams.saleId" placeholder="请选择销售伙伴" size="small">
            <el-option v-for="dict in saleArrList" :key="dict" :label="dict" :value="dict" />
          </el-select>
        </el-form-item>
        <el-form-item label="接口类型" prop="inter">
          <el-select v-model="queryRecParams.inter" placeholder="请选择接口类型" size="small">
            <el-option v-for="dict in statusList" :key="dict" :label="dict" :value="dict" />
          </el-select>
        </el-form-item>
        <el-form-item label="接口状态" prop="successCount">
          <el-select v-model="queryRecParams.successCount" placeholder="请选择接口类型" size="small">
            <el-option v-for="dict in sucessList" :key="dict.id" :label="dict.status" :value="dict.id" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="getRecList">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="recList">
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

      <pagination v-show="recTotal > 0" :total="recTotal" :page.sync="queryRecParams.pageNum"
        :limit.sync="queryRecParams.pageSize" @pagination="getRecList" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogApiRecVisible = false">取 消</el-button>
        <el-button type="primary" @click="gengertiImge">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="备注" :visible.sync="showSinNumVisible">
      <span>{{ sinNumShow }}</span>
    </el-dialog>
    <el-dialog title="加减价" :visible.sync="addOrsubPriceVisible" width="20%">
      <BacthAddPrice @addFrom="addFrom"></BacthAddPrice>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addOrsubPriceVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAddSub">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="参数" :visible.sync="showSinNumVisible">
      <span>{{ req }}</span>
    </el-dialog>
    <!-- 标签配置  -->
    <el-dialog title="标签配置" :visible.sync="addLableVisible" v-if="addLableVisible" width="70%">
      <LableSelectCommon :type="form.type" :des="form.des" :inputType="lableInput" @selectLable="selectLable"
        @selectInput="selectInput"></LableSelectCommon>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addLableVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmLable">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="提示" :visible.sync="dialogImgVisible" width="30%">
      <div style="text-align:center;margin-bottom: 20px">
        <el-button type="primary" @click="coptImg">合成下载</el-button>
      </div>
      <el-image v-for="(item, index) in imgList" :key="index" :src="item.imgUrl"></el-image>
    </el-dialog>

    <el-dialog title="模板类型" :visible.sync="dialogImgTypeVisible" width="30%">
      <el-radio-group v-model="modelType">
        <el-radio :label="1">正方模板</el-radio>
        <el-radio :label="2">长方模板</el-radio>
      </el-radio-group>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogImgTypeVisible = false">取 消</el-button>
        <el-button type="primary" @click="gengertiImge">确 定</el-button>
      </div>
    </el-dialog>
    <pushDialog ref="pushDialog"></pushDialog>
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
  onePushAcount,
  pushAcount,
  listInfo,
  getInfo,
  auditInfo,
  delInfo,
  addInfo,
  updateInfo,
  exportInfo,
  getSupplyList,
  importTemplateHp,
  delAccount,
  byColums,
  allSale,
  deleteAllAccount,
  getImage,
  jymPush,
  getPushList,
  polish,
  ifPolish,
  getMorePrice
} from "@/api/info/info";
import { getDicts } from "@/api/system/dict/data";
import { getToken } from "@/utils/auth";
import moment from "moment";
import PackageUpload from "../../components/ImageUpload/packageUpload.vue";
import BacthAddPrice from "../components/BacthAddPrice.vue";
import AllSkinSelect from "../components/AllSkinSelect.vue";
import pushDialog from "./push.vue";
import LableSelectCommon from "../components/LableSelectCommon.vue";
import spliceUploadHandle from "@/api/info/spliceUpload";
import { postUpload } from "@/api/upload";
import { roleList, gengerTitle } from "@/api/gentitle/black";
import { bacthAddPrice } from "@/api/info/account";
import { addLable, oneKeyLable } from "@/api/lable/lable";
import { allList } from "@/api/partner/partner";
import { listRec, getRec, delRec, updateRec, exportRec } from "@/api/info/rec";
import { addRec } from "@/api/order/rec";
export default {
  deleteAllDialog: false,
  name: "Info",
  dicts: [
    "game_type",
    "account_status",
    "sale_user",
    "supply_user",
    "game_type",
    "ifpay"
  ],
  components: {
    PackageUpload,
    AllSkinSelect,
    BacthAddPrice,
    LableSelectCommon,
    pushDialog
  },
  data() {
    return {
      morePrice: [],//更多价格
      testImg: '',
      polishLoading: false,//擦亮按钮loading
      isDisabled: false, //擦亮按钮是否禁用
      pushApiList: [],
      showSinNumVisible: false,
      saleArrList: [],
      dialogApiRecVisible: false,
      interNameList: [],
      deleteAllDialog: false,
      deleteForm: {},
      sleetcData: {
        saleChannel: '',
        salePrice: '',
        suplyprice: '',
        account: '',
        supllypay: '',
        ifPay: '',
        remake: ''
      },
      submitOrderDialog: false,
      lableInput: "1",
      lableType: 1,
      wzry: false,
      dialogImgTypeVisible: false,
      modelType: 1,
      cmdSupplyList: [],
      dialogImgVisible: false,
      outAccountDel: "",
      resultDate: [],
      imgList: [],
      addLableVisible: false,
      jiajianjia: false,
      addPriceparam: {},
      addOrsubPriceVisible: false,
      loadingSkin: false,
      formData: {},
      formGenData: {},
      skinList: [],
      instancesUrl: [],
      roleListVisible: false,
      roleList: [],
      skinFormVisible: false,
      imageUrl: "",
      irreAttr: false,
      sinNumShow: "",
      cellStyle: { color: " #000000", "font-size": "15px" },
      wihh: 60,
      uploadImgUrl: "",
      dialogImageUrl: "",
      dialogVisible: false,
      dialogGameType: false,
      dialogGameTypeHp: false,
      intType: null,
      operation: null,
      showDialogTitle: "",
      formLabelWidth: "120px",
      dialogFormVisible: false,
      dialogTelFormVisible: false,
      //游戏大区
      regionalArr: [],
      levelArr: [],
      supplyList: [],
      saleList: [],
      // 遮罩层
      loading: false,
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
      // 游戏账号表格数据
      infoList: [],
      // 弹出层标题
      title: "",
      jingmeie: "",
      guizuLevel: "V1",
      yinji: "",
      // 是否显示弹出层
      open: false,
      saleTimeArr: [],
      createTimeArr: [],
      updateTimeArr: [],
      videoList: [],
      outAccount: false,
      recTotal: 0,
      queryRecParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 查询参数
      queryParams: {
        skinList: [],
        sort: "desc",
        sortColumn: "id",
        pageNum: 1,
        pageSize: 10,
        campId: null,
        accountId: null,
        type: null,
        regional: null,
        timeout: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        priceStart: null,
        priceEnd: null,
        saleTimeStart: null,
        saleTimeEnd: null,
        createTimeStart: null,
        createTimeEnd: null,
        updateTimeStart: null,
        updateTimeEnd: null,
        heroStart: null,
        heroEnd: null,
        skinStart: null,
        skinEnd: null,
        suitStart: null,
        suitEnd: null,
        gunsStart: null,
        gunsEnd: null,
        desQur: null,
        regionalAddr: [],
        levelAddr: [],
        supplyIdAddr: [],
        salesIdAddr: []
      },
      show: {
        accountRemake: true,
        campId: true,
        shelves: false,
        exportExelShow: false,
        gameName: true,
        type: true,
        regional: true,
        level: true,
        extractTel: false,
        heroes: true,
        skins: true,
        suit: true,
        guns: true,
        des: false,
        price: false,
        supplyName: false,
        salesName: false,
        buyId: false,
        realPrice: false,
        remake: false,
        saleStateName: false,
        updateName: false,
        extractAccount: false,
        extractPwd: false,
        costPrice: false,
        sinNum: false,
        auditName: false,
        createTime: false,
        updateTime: false,
        note: false,
        saleTime: false,
        auditState: false,
        onOfOff: false,
        rise: true,
        select: {
          suit: true,
          guns: true,
          heroes: true,
          skins: true
        },
        intParam: {
          campId: true,
          extractAccount: false,
          saleTime: false,
          supplyName: true,
          salesName: false,
          sinNum: false,
          timeout: false,
          createTime: false,
          updateTime: false,
          salesStatus: false
        }
      },
      props: {
        isPreview: {
          type: Boolean,
          required: false,
          default: true
        }
      },
      ifSpecial: true,
      // 表单参数
      form: {
        guizufen: "0",
        wuji: "0"
      },
      unsoldFrom: {
        note: null
      },
      telFrom: {
        buyId: null,
        realPrice: null
      },
      // 表单校验
      rules: {},
      type: "",
      saleStatus: "",
      showAudit: false,
      extractPwd: false,
      sinceSale: false,
      extractTel: false,
      cancelIn: false,
      transfer: false,
      back: false,
      problem: false,
      edit: false,
      pending: false,
      handle: true,
      copyOpe: false,
      urlArr: [],
      fileNameArr: [],
      imgFileList: [],
      imgShowList: [], //传给后台的图片
      table: {
        tableData: [],
        tableHeight: $(window).height() - 370
      },
      copyVlaue: "",
      copyPwd: "",
      copyTel: "",
      ifClick: true,
      hoste: "",
      showSinNumVisible: false,
      bacthDelShow: false,
      skinList: [],
      formDel: {},
      rowiD: "",
      statusList: ["新增", "编辑", "删除"],
      sucessList: [
        { status: "成功", id: 1 },
        { status: "失败", id: 0 }
      ],
      recList: null,
      req: null
    };
  },
  created() {
    this.allSale();
    this.getInterNameList();
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    const saleStatus = this.$route.query.saleStatus;
    console.log(saleStatus);
    this.type = this.$route.query.type;
    this.queryParams.saleState = saleStatus;
    this.queryParams.operType = this.type;
    if ("audit" == this.type) {
      this.showAudit = true;
      this.show.saleStateName = false;
      this.show.intParam.createTime = true;
      this.show.saleTime = true;
    } else if ("admin" == this.type) {
      this.showAudit = true;
      this.show.newPrice = true;
      this.show.exportExelShow = true;
      this.show.intParam.salesName = true;
      this.show.intParam.supplyName = true;
      this.show.intParam.updateTime = true;
      this.show.intParam.createTime = true;
      this.show.intParam.salesStatus = true;
      this.show.saleTime = true;
    } else if ("sale" == this.type) {
      this.handle = false;
      if ("1" == saleStatus) {
        this.queryParams.saleState = null;
        this.show.intParam.salesStatus = true;
        this.extractPwd = true;
        this.sinceSale = true;
        this.copyOpe = true;
        this.show.exportExelShow = true;
        this.show.shelves = true;
        this.show.price = true;
      } else if ("2" == saleStatus) {
        this.extractTel = true;
        this.cancelIn = true;
        this.problem = true;
        this.show.saleTime = true;
        this.show.newPrice = true;
      } else if ("3" == saleStatus) {
        this.show.intParam.extractAccount = true;
        this.show.intParam.saleTime = true;
        this.show.saleTime = true;
      }
    } else if ("supply" == this.type) {
      if ("1" == saleStatus) {
        this.show.shelves = true;
        this.copyOpe = true;
        this.show.des = true;
        this.show.newPrice = false;
        this.show.exportExelShow = true;
        this.show.intParam.supplyName = false;
      } else if ("2" == saleStatus) {
        this.show.newPrice = true;
        this.handle = false;
        this.show.intParam.supplyName = false;
        this.show.intParam.salesName = true;
        this.show.saleTime = true;
      } else if ("3" == saleStatus) {
        this.problem = true;
        this.show.newPrice = false;
        this.show.intParam.saleTime = true;
        this.show.intParam.extractAccount = true;
        this.show.intParam.salesName = true;
        this.show.intParam.supplyName = false;
        this.show.intParam.sinNum = true;
        this.show.saleTime = true;
      } else if ("4" == saleStatus) {
        this.back = true;
        this.show.newPrice = false;
        this.show.intParam.supplyName = false;
        this.show.intParam.salesName = true;
        this.pending = true;
        this.show.saleTime = true;
      } else if ("6" == saleStatus) {
        this.show.newPrice = false;
        this.show.intParam.timeout = true;
        this.queryParams.timeout = 30;
        this.queryParams.saleState = 1;
        this.show.saleTime = true;
      } else if ("5" == saleStatus) {
        this.show.newPrice = false;
        this.pending = true;
        this.handle = false;
        this.show.intParam.supplyName = false;
      } else if ("7" == saleStatus) {
        this.transfer = true;
        this.show.intParam.supplyName = false;
        this.handle = false;
        this.show.newPrice = false;
      }
    }
    this.showDiffTitle();
    // this.getList();
    this.getSaleList();
    this.getSupplyList();
    this.getHost();
    this.getPushList();
    this.ifPolish();
    // this.getCmdSupplyList();
  },
  methods: {
    // 更多价格
    getMore(row) {
      console.log("%c Line:1303 🍐 row", "color:#33a5ff", row);
      getMorePrice({ infoid: row.id }).then(res => {
        console.log("%c Line:1304 🥚 res", "color:#7f2b82", res);
        this.morePrice = res.data
      })
    },
    //     testTitleImg(){
    // makeTitleImg.makeTitleImg().then(titleImg=>{
    //   this.testImg = titleImg
    //   console.log('%c 🥠 titleImg: ', 'font-size:20px;background-color: #42b983;color:#fff;', titleImg);

    // })
    //     },
    polish() {
      this.polishLoading = true
      polish().then(res => {
        this.isDisabled = true;
        this.polishLoading = false
        this.$message.success('擦亮成功过！')
      }).catch(err => {
        this.polishLoading = false
      })
    },
    ifPolish() {
      ifPolish().then(res => {
        this.isDisabled = !res.data;
      });
    },
    coptImg() {
      let imgArr = [].concat(...this.imgList.map(o => o.imgUrl));
      this.instancesUrl = imgArr;
      this.filesToInstances();
    },
    pushJym() {
      let params = {};
      params.id = this.sleetcData.id;
      jymPush(params).then(res => {
        console.log(res);
      });
    },
    // 获取推送接口列表
    getPushList() {
      getPushList({ status: 1 }).then(res => {
        console.log(
          "%c 🍲 res: ",
          "font-size:20px;background-color: #3F7CFF;color:#fff;",
          res
        );
        this.pushApiList = res.rows;
      });
    },
    // 推送
    pushBatch(item) {
      console.log(
        "%c 🍝 item: ",
        "font-size:20px;background-color: #93C0A4;color:#fff;",
        item
      );
      console.log(this.ids);
      if (this.ids.length > 1) {
        this.$modal.msgError("只能选择一条数据");
        return true;
      } else if (this.ids.length == 0) {
        this.$modal.msgError("请先选择一条数据");
        return true;
      }
      this.$refs.pushDialog.open({
        id: this.ids[0],
        configId: item
      });
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
      allSale().then(res => {
        if (res.code == 200) {
          this.saleArrList = res.data;
        }
      });
    },
    showApiRec(row) {
      this.dialogApiRecVisible = true;
      this.rowiD = row.id;
      this.getRecList();
    },
    getRecList() {
      this.loading = true;
      this.queryRecParams.accountid = this.rowiD;
      listRec(this.queryRecParams).then(response => {
        this.recList = response.rows;
        this.recTotal = response.total;
        this.loading = false;
      });
    },
    deleteAllAccount() {
      deleteAllAccount(this.deleteForm).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("操作成功");
          this.getList();
        }
      });
    },
    getInterNameList() {
      allList().then(res => {
        if (res.code == 200) {
          this.interNameList = res.rows;
        }
      });
    },
    deleteAll() {
      this.deleteAllDialog = true;
      this.deleteForm = {};
    },
    inputSalePrice() {
      //获取销售折扣
      let saleDiscount = 0;
      let supplyDiscount = 0;

      let sale_user = [];
      getDicts("sale_user").then(res => {
        if (res.code == 200) {
          sale_user = res.data;
          for (var i = 0; i < sale_user.length; i++) {
            if (this.sleetcData.saleChannel == sale_user[i].dictLabel) {
              saleDiscount = sale_user[i].dictValue;
              break;
            }
          }
          getDicts("supply_user").then(res => {
            if (res.code == 200) {
              supply_user = res.data;
              for (var i = 0; i < supply_user.length; i++) {
                if (this.sleetcData.supplyChannel == supply_user[i].dictLabel) {
                  supplyDiscount = supply_user[i].dictValue;
                  break;
                }
              }
              //销售折扣
              this.sleetcData.saleDiscount = saleDiscount;
              //供货折扣
              this.sleetcData.supplyDiscount = supplyDiscount;
              this.sleetcData.suplyprice = this.sleetcData.suplyprice;
              //销售折扣后金额
              let saleDiscountPrice =
                (saleDiscount / 10) * this.sleetcData.salePrice;
              let supplyDiscountPrice =
                (supplyDiscount / 10) * this.sleetcData.salePrice;

              this.sleetcData.supplyDiscountPrice = supplyDiscountPrice;

              this.sleetcData.saleDiscountPrice = saleDiscountPrice;

              let profits = saleDiscountPrice - supplyDiscountPrice;

              //计算利润
              this.sleetcData.profits = profits.toFixed(2);
              console.log(this.sleetcData);
            }
          });
        }
      });

      //获取供货渠道折扣
      let supply_user = this.getDictData("supply_user");
      for (var i = 0; i < supply_user; i++) {
        if (this.form.supplyChannel == supply_user[i].dictLabel) {
          supplyDiscount = supply_user[i].dictValue;
          break;
        }
      }
    },
    getDictData(type) {
      let data = [];
      getDicts(type).then(res => {
        if (res.code == 200) {
          data = res.data;
        }
      });
      return data;
    },
    confirmSbmitOrder() {
      let saleStates = this.sleetcData.saleState;
      // if (1 != saleStates) {
      //   this.$modal.msgError("状态不是未售，不允许下单");
      //   return;
      // }
      let orderRec = {};
      orderRec.saleChannel = this.sleetcData.saleChannel;
      orderRec.supplyChannel = this.sleetcData.supplyChannel;
      orderRec.saleDiscountPrice = this.sleetcData.saleDiscountPrice;
      orderRec.saleDiscount = this.sleetcData.saleDiscount;
      orderRec.supplyDiscountPrice = this.sleetcData.supplyDiscountPrice;
      orderRec.supplyDiscount = this.sleetcData.supplyDiscount;
      orderRec.salePrice = this.sleetcData.salePrice;
      orderRec.sumNum = this.sleetcData.gameName;
      orderRec.games = this.sleetcData.type;
      orderRec.ifPay = this.sleetcData.ifPay;
      orderRec.accountid = this.sleetcData.id;
      orderRec.account = this.sleetcData.account;
      orderRec.supllypay = this.sleetcData.supllypay;
      orderRec.suplyprice = this.sleetcData.suplyprice;
      orderRec.supllyId = this.sleetcData.createId;
      orderRec.remake = this.sleetcData.remake;

      addRec(orderRec).then(response => {
        this.$modal.msgSuccess("新增成功");
        this.submitOrderDialog = false;
        this.getList();
      });
    },
    submitOrder() {
      console.log("%c Line:2040 🌰 this.sleetcData", "color:#4fff4B", this.sleetcData);

      this.submitOrderDialog = true;
    },
    showField() {
      if (this.queryParams.type) {
        this.addLableVisible = true;
        this.form.type = this.queryParams.type;
        this.form.des = "";
        this.lableInput = 1;
      } else {
        this.$modal.msgError("清选择游戏类型");
      }
    },
    addACount() {
      this.reset();
      this.title = "修改游戏账号";
      this.open = true;
      if ("王者荣耀" == this.form.type) {
        this.wzry = true;
      }
      this.imgFileList = [];
      this.imgShowList = [];
      this.jingmeie = "";
      this.imageUrl = "";
    },
    confirmDel() {
      delAccount(this.formDel).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("操作成功");
          this.bacthDelShow = false;
          this.getList();
        }
      });
    },
    deleteAcount() {
      this.bacthDelShow = true;
    },
    getCmdSupplyList() {
      getCmdSupplyList().then(res => {
        console.log(res);
        this.cmdSupplyList = res;
      });
    },
    oneKeyLable() {
      this.loading = true;
      oneKeyLable().then(res => {
        this.$modal.msgSuccess("操作成功");
        this.loading = false;
      });
    },
    selectInput(resultInput) {
      console.log(resultInput);
      this.lableSeInput = resultInput;
    },
    selectLable(resultDate) {
      this.resultDate = resultDate;
      this.queryParams.desQur = this.resultDate.join(" ");
    },
    showLable(type) {
      if (1 == type) {
        this.lableInput = "2";
      } else {
        this.lableInput = "1";
      }
      this.lableType = type;
      this.addLableVisible = true;
    },
    confirmLable() {
      if (1 == this.lableType) {
        let params = {};
        params.infoId = this.form.id;
        params.lableList = this.resultDate.join(",");
        addLable(params).then(res => {
          if (200 == res.code) {
            this.$modal.msgSuccess("新增成功");
            this.addLableVisible = false;
            this.open = false;
            this.getList();
          } else {
            this.$modal.msgError("新增失败");
          }
        });
      }

      if (2 == this.lableType) {
        this.model3();
        this.addLableVisible = false;
      }
    },
    handleAudit(type, operation) {
      this.exportLoading = false;
      let params = {};
      if (null != this.intType) {
        params.type = this.intType;
      } else {
        params.type = type;
      }
      if (null != this.operation) {
        params.operation = this.operation;
      } else {
        params.operation = operation;
      }
      if ("" != this.unsoldFrom.note || null != this.unsoldFrom.note) {
        params.note = this.unsoldFrom.note;
      }
      if ("" != this.telFrom.remake || null != this.telFrom.remake) {
        params.remake = this.telFrom.remake;
      }
      if ("" != this.telFrom.realPrice || null != this.telFrom.realPrice) {
        params.realPrice = this.telFrom.realPrice;
      }
      if ("" != this.telFrom.buyId || null != this.telFrom.buyId) {
        params.buyId = this.telFrom.buyId;
      }
      const ids = this.ids;
      if ("审核" != operation) {
        if (this.ids.length > 1) {
          this.$modal.msgError("只能选择一条数据");
          return true;
        }
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
          if (2 == type) {
            this.excCopy(this.copyPwd);
          } else if (3 == type) {
            this.excCopy(this.copyTel);
          }
        })
        .catch(() => { });
      this.sleetcData.saleChannel = "";
      this.sleetcData.id = "";
    },
    addFrom(from) {
      this.addPriceparam = from;
    },
    confirmAddSub() {
      if (!this.addPriceparam.rise) {
        this.addPriceparam.rise = 0;
      }
      console.log(this.addPriceparam);
      const param = this.addPriceparam;
      let sap = this.addPriceparam.supplyIdAddr.join(",");
      param.supplyIdAddr = sap;
      console.log(param);
      param.saleType = "sale";
      this.loading = true;
      bacthAddPrice(param).then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("修改成功");
          this.loading = false;
        } else {
          this.$modal.msgError("修改失败");
          this.loading = false;
        }
        this.dialogFormVisible = false;
        this.getList();
      });
    },
    addOrsubPrice() {
      this.addOrsubPriceVisible = true;
    },
    gengerTitle(row) {
      this.loadingSkin = true;
      let queryParams = {};
      queryParams.campId = this.form.campId;
      queryParams.roleId = row.roleId;
      gengerTitle(queryParams).then(res => {
        if (res.code == 200) {
          this.instancesUrl = res.data.qiniuUrl;
          this.skinList = res.data.goodSkinName;
          this.formData = res.data.skinMap;
          var areaId = res.data.skinMap.areaId;
          if ("1" == areaId) {
            areaId = "安卓Q";
          } else if ("2" == areaId) {
            areaId = "苹果Q";
          } else if ("3" == areaId) {
            areaId = "安卓WX";
          } else if ("4" == areaId) {
            areaId = "苹果WX";
          }

          this.form.regional = areaId;
          this.form.heroes = res.data.skinMap.heroNum;
          this.form.skins = res.data.skinMap.skinNum;
          this.form.des = res.data.skinMap.ppxiangqing;
          this.skinList = res.data.goodSkinName;
          // this.filesToInstances();
          this.jingmeie = this.skinList.join(" ");
          console.log(this.jingmeie);
          if (res.data.skinMap.game_Imprint) {
            this.form.yinji = res.data.skinMap.game_Imprint;
          }
          if (res.data.skinMap.noble_level) {
            this.form.guizuLevel = res.data.skinMap.noble_level;
          }
          console.log(this.yinji);
          console.log(this.guizuLevel);
          this.roleListVisible = false;
        }
      });
    },
    showModel() {
      this.dialogImgTypeVisible = true;
    },
    gengertiImge() {
      if ("1" == this.modelType) {
        this.model1();
      }
      if ("2" == this.modelType) {
        this.model2();
      }
      this.dialogImgTypeVisible = false;
    },

    model2() {
      this.getURLBase64("https://game.ikbh.top/logo/back-img.jpg").then(res => {
        const imageaaa = new Image();
        imageaaa.src = res;
        imageaaa.onload = () => {
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.width = imageaaa.width;
          canvas.height = imageaaa.height;
          context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
          context.fillStyle = "#fff"; // 文字填充颜色
          context.font = "33px Adobe Ming Std";

          var areaId = this.formData.areaId;
          if ("1" == areaId) {
            areaId = "安卓Q";
          } else if ("2" == areaId) {
            areaId = "苹果Q";
          } else if ("3" == areaId) {
            areaId = "安卓WX";
          } else if ("4" == areaId) {
            areaId = "苹果WX";
          }

          let one =
            "英雄数量： " +
            this.formData.heroNum +
            "    " +
            "皮肤数量：  " +
            this.formData.skinNum +
            "      " +
            "战斗力：  " +
            this.formData.zhandouli +
            "      " +
            "水晶数量： " +
            this.formData.shuijingnum;

          let two =
            "贵族等级： " +
            this.formData.heroNum +
            "    " +
            "贵族积分  " +
            this.formData.skinNum +
            "      " +
            "五级铭文：  " +
            this.formData.zhandouli +
            "      " +
            "王者印记： " +
            this.formData.shuijingnum;

          let titlName = areaId;
          let skin = this.skinList;
          context.fillStyle = "#fff";

          context.font = "66px Adobe Ming Std";

          context.fillText(titlName, 750, 130);

          context.font = "45px Adobe Ming Std";
          context.fillStyle = "#6BE61A";
          context.fillText(one, 150, 210);
          context.fillText(two, 150, 270);

          context.font = "33px Adobe Ming Std";
          context.fillStyle = "#fff";
          var x1 = 150;
          var h = 310;
          skin.map((item, index) => {
            if (index < 35) {
              if (index % 7 == 0) {
                x1 = 150;
                h = h + 80;
              }
              console.log(x1);
              context.fillText(skin[index], x1, h);
              x1 = x1 + 220;
            }
          });

          context.font = "33px Adobe Ming Std";
          context.fillStyle = "#09F7F7";
          context.fillText("营地ID：" + this.form.campId, 700, 800);
          const base64Url = canvas.toDataURL("image/jpeg", this.quality);
          this.downloadFile("怪兽玩代售", base64Url);
          this.titleimgurl = base64Url;
          let blo = this.base64ToBlob(base64Url);
          let formData = new FormData();
          formData.append("file", blo);
          postUpload(formData)
            .then(res => {
              this.imageUrl = res.msg;
              this.form.titleImg = res.msg;
              this.loadingSkin = false;
            })
            .catch(e => { });
        };
      });
    },
    model3() {
      this.getURLBase64("https://game.ikbh.top/logo/back-img.jpg").then(res => {
        const imageaaa = new Image();
        imageaaa.src = res;
        imageaaa.onload = () => {
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.width = imageaaa.width;
          canvas.height = imageaaa.height;
          context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
          context.fillStyle = "#fff"; // 文字填充颜色
          context.font = "33px Adobe Ming Std";

          let titlName = "游戏信息";
          const arr = [...new Set(this.resultDate)];
          let skin = arr.splice(0, 70);
          console.log(skin);
          context.fillStyle = "#fff";

          context.font = "66px Adobe Ming Std";

          context.fillText(titlName, 750, 130);
          let contant = skin.join("、");
          context.font = "33px Adobe Ming Std";
          context.fillStyle = "#FFFF00";
          this.canvasTextAutoLine(contant, context, 140, 200, 50);
          // context.font = "45px Adobe Ming Std";
          // context.fillStyle = "#6BE61A";

          // context.font = "33px Adobe Ming Std";
          // context.fillStyle = "#fff";
          // var x1;
          // var h = 150;
          // skin.map((item, index) => {
          //   if (index % 7 == 0) {
          //     x1 = 200;
          //     h = h + 50;
          //   }
          //   console.log(x1);
          //   context.fillText(skin[index], x1, h);
          //   x1 = x1 + 200;
          // });

          if (this.lableSeInput) {
            context.textAlign = "center";
            context.font = "33px Adobe Ming Std";
            context.fillStyle = "#09F7F7";
            context.fillText(this.lableSeInput, 1500 / 2, 800);
          }
          const base64Url = canvas.toDataURL("image/jpeg", this.quality);
          this.downloadFile("怪兽玩代售", base64Url);
          this.titleimgurl = base64Url;
          let blo = this.base64ToBlob(base64Url);
          let formData = new FormData();
          formData.append("file", blo);
          postUpload(formData)
            .then(res => {
              this.imageUrl = res.msg;
              this.form.titleImg = res.msg;
              this.loadingSkin = false;
            })
            .catch(e => { });
        };
      });
    },
    canvasTextAutoLine(str, ctx, initX, initY, lineHeight) {
      var lineWidth = 0;
      var canvasWidth = 2400;
      var lastSubStrIndex = 0;
      for (let i = 0; i < str.length; i++) {
        lineWidth += ctx.measureText(str[i]).width;
        if (lineWidth > (canvasWidth / 8) * 5) {
          ctx.fillText(str.substring(lastSubStrIndex, i), initX, initY);
          initY += lineHeight;
          lineWidth = 0;
          lastSubStrIndex = i;
        }
        if (i == str.length - 1) {
          ctx.fillText(str.substring(lastSubStrIndex, i + 1), initX, initY);
        }
      }
    },
    model1() {
      if (!this.form.campId) {
        this.$modal.msgError("请输入营地ID");
      }
      this.getURLBase64("https://game.ikbh.top/logo/model1.jpg").then(res => {
        const imageaaa = new Image();
        imageaaa.src = res;
        imageaaa.onload = () => {
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.width = imageaaa.width;
          canvas.height = imageaaa.height;
          context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
          context.fillStyle = "#fff"; // 文字填充颜色
          context.font = "33px Adobe Ming Std";

          var areaId = this.formData.areaId;
          if ("1" == areaId) {
            areaId = "安卓Q";
          } else if ("2" == areaId) {
            areaId = "苹果Q";
          } else if ("3" == areaId) {
            areaId = "安卓WX";
          } else if ("4" == areaId) {
            areaId = "苹果WX";
          }
          // let level = this.formData.heroNum
          // let one = this.formData.heroNum
          // let one =
          //   "英雄数量： " +
          //   this.formData.heroNum +
          //   "    " +
          //   "皮肤数量：  " +
          //   this.formData.skinNum +
          //   "      " +
          //   "战斗力：  " +
          //   this.formData.zhandouli +
          //   "      " +
          //   "水晶数量： " +
          //   this.formData.shuijingnum;

          let titlName =
            areaId +
            "(" +
            this.form.guizuLevel +
            ")" +
            "   编号 " +
            this.form.id;
          let skin = this.jingmeie.split(" ").splice(0, 19);
          context.fillStyle = "#ffff00";

          context.font = "40px Adobe Ming Std";

          context.fillText(titlName, 210, 60);

          let heroNum = this.formData.heroNum;
          let level = this.formData.jobName;
          let skins = this.formData.skinNum;
          let yinji = this.form.yinji;
          let wuji = this.form.wuji;
          let guizu = this.form.guizufen;

          context.font = "35px Adobe Ming Std";
          context.fillStyle = "#ffff00";
          context.fillText(heroNum, 200, 195);
          context.fillText(level, 600, 195);
          context.fillText(skins, 200, 235);
          context.fillText(yinji, 600, 235);
          context.fillText(wuji, 200, 275);
          context.fillText(guizu, 600, 275);

          context.font = "normal bold 30px Adobe Ming Std";
          context.fillStyle = "#ff3399";
          var x1 = 15;
          var h = 360;
          skin.map((item, index) => {
            if (index < 35) {
              if (index % 4 == 0) {
                x1 = 60;
                //控制行高
                h = h + 60;
              }
              context.fillText(skin[index], x1, h);
              //控制列间隔
              x1 = x1 + 180;
            }
          });

          context.font = "normal bold 40px Adobe Ming Std";
          context.fillStyle = "#ffff00";
          //营地ID
          let campId = this.formData.yingdiIdsd;
          context.fillText(campId, 300, 770);
          const base64Url = canvas.toDataURL("image/jpeg", this.quality);
          this.titleimgurl = base64Url;
          this.downloadFile("怪兽玩代售", base64Url);
          let blo = this.base64ToBlob(base64Url);
          let formData = new FormData();
          formData.append("file", blo);
          postUpload(formData)
            .then(res => {
              this.imageUrl = res.msg;
              this.form.titleImg = res.msg;
              this.loadingSkin = false;
            })
            .catch(e => { });
        };
      });
    },
    downloadFile(fileName, content) {
      let aLink = document.createElement("a");
      let blob = this.base64ToBlob(content); // new Blob([content]);
      let evt = document.createEvent("HTMLEvents");
      evt.initEvent("click", true, true); // initEvent 不加后两个参数在FF下会报错  事件类型，是否冒泡，是否阻止浏览器的默认行为
      aLink.download = fileName;
      aLink.href = URL.createObjectURL(blob);
      aLink.dispatchEvent(
        new MouseEvent("click", {
          bubbles: true,
          cancelable: true,
          view: window
        })
      ); // 兼容火狐
    },

    getURLBase64(url) {
      return new Promise((resolve, reject) => {
        var xhr = new XMLHttpRequest();
        xhr.open("get", url, true);
        xhr.responseType = "blob";
        xhr.onload = function () {
          if (this.status === 200) {
            var blob = this.response;
            var fileReader = new FileReader();
            fileReader.onloadend = function (e) {
              var result = e.target.result;
              resolve(result);
            };
            fileReader.readAsDataURL(blob);
          }
        };
        xhr.onerror = function () {
          reject();
        };
        xhr.send();
      });
    },

    filesToInstances() {
      let instancesUrl = this.instancesUrl;
      const length = instancesUrl.length;
      let instances = [];
      let finished = 0;
      instancesUrl.forEach((file, index) => {
        this.getURLBase64(file).then(res => {
          const image = new Image();
          image.src = res;
          image.onload = () => {
            // 图片实例化成功后存起来
            instances[index] = image;
            finished++;
            if (finished === length) {
              this.drawImages(instances);
            }
          };
        });
      });

      // https://game.ikbh.top/logo/kuang.jpeg
    },
    drawImages(images) {
      const heights = images.map(item => item.height);
      const canvas = document.createElement("canvas");
      const widths = images.map(item => item.width);
      let sumWidth = Math.max.apply(null, widths);
      // canvas.width = sumWidth;
      canvas.width = 1500;
      // canvas.height = eval(heights.join("+"));
      // canvas.height = eval(heights.join("+"));
      canvas.height = heights.length * 1200;
      const context = canvas.getContext("2d");

      let y = 0;

      images.forEach((item, index) => {
        // const height = heights[index];
        // const width = widths[index];
        const height = 1200;
        const width = 1500;
        context.drawImage(item, 0, y, width, height);
        y += height;
      });
      const base64Url = canvas.toDataURL("image/jpeg", 0.2);
      this.downloadFile("11", base64Url);
    },
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
    gengerRole() {
      if (!this.form.campId) {
        this.$modal.msgError("请输入营地ID");
        return true;
      }
      let params = {};
      params.campId = this.form.campId;
      console.log(params);
      roleList(params).then(res => {
        if (res.code == 200) {
          this.roleList = res.data;
          this.roleListVisible = true;
        }
      });
    },
    canceSkin() {
      this.queryParams.skinList = [];
      this.skinFormVisible = false;
    },
    skinType(classTypeList) {
      console.log(classTypeList);
      this.queryParams.skinList = classTypeList;
    },
    confirmSkin() {
      this.skinFormVisible = false;
      this.getList();
    },
    showSkin() {
      this.skinFormVisible = true;
    },
    onePushAcount() {
      onePushAcount().then(res => {
        if (res.code == 200) {
          this.$modal.msgSuccess("操作成功");
        }
      });
    },
    handleAvatarRemove(response, file) {
      console.log(file);
      console.log(response);
    },
    handleAvatarSuccess(res, file) {
      console.log(file);
      console.log(res);
      // this.imageUrl = URL.createObjectURL(file.raw);
      this.form.titleImg = res.data.filePath;
      this.imageUrl = res.data.filePath;
      console.log(this.form.titleImg);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 5;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    pushAcount() {
      const ids = this.ids;
      pushAcount(ids).then(res => {
        if (res.code == 200) {
          if (res.msg) {
            this.$modal.msgError(res.msg);
          } else {
            this.$modal.msgSuccess("操作成功");
          }
          this.getList();
        }
      });
    },
    onUploadCompleted(fileInfo) {
      //当上传成功时启用按钮
      this.saveButton = false;
      this.form.videoName = fileInfo.fileName;
      this.form.videoUrl = fileInfo.filePath;
    },
    onFileRemoved(fileInfo) {
      this.uploadFlag = false; //加密为否   不可否选
      this.form.isEncrypt = 0;
      this.saveButton = false;
      this.packageName = "";
      this.path = "";
      this.hash = "";
      this.size = "";
      this.form.videoName = "";
      this.form.videoUrl = "";
    },
    uploadVideo(file, fileList) {
      console.log(file);
      spliceUploadHandle(file.raw, this.videoSuccess);
    },
    videoSuccess() {
      console.log("11111");
    },
    onOfOff() {
      if (this.irreAttr) {
        this.show.createTime = false;
        this.show.accountRemake = false;
        this.show.extractAccount = false;
        this.show.extractPwd = false;
        this.show.extractTel = false;
        this.show.costPrice = false;
        this.show.supplyName = false;
        this.show.salesName = false;
        this.show.saleStateName = false;
        this.show.saleTime = false;
        this.show.updateName = false;
        this.show.updateTime = false;
        this.show.buyId = false;
        this.show.realPrice = false;
        this.show.remake = false;
        this.show.auditName = false;
        this.show.auditState = false;
        this.show.note = false;
      } else {
        this.show.createTime = true;
        this.show.extractAccount = true;
        this.show.extractPwd = true;
        this.show.extractTel = true;
        this.show.costPrice = true;
        this.show.supplyName = true;
        this.show.saleStateName = true;
        this.show.updateName = true;
        this.show.updateTime = true;
        this.show.accountRemake = true;
      }
    },
    changeSpecial() {
      console.log(this.form.accType);
      if (1 == this.form.accType) {
        this.ifSpecial = false;
      } else {
        this.form.specialPrice = null;
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        // return "";
        return "success-row";
      } else {
        return "warning-row";
      }
    },
    getHost() {
      getDicts("apphost").then(res => {
        if (res.code == 200) {
          console.log(res);
          if (res.data.length > 0) {
            this.hoste = res.data[0].dictValue;
          }
        }
      });
    },
    // 在methods里面书写复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
    },
    onError() {
      this.$modal.msgError("复制失败");
    },
    setCurrent(row) {
      this.$refs.singleTable.setCurrentRow(row);
    },
    sortFun(val) {
      this.queryParams.sort = val.order;
      this.queryParams.sortColumn = val.prop;
      this.getList();
    },
    uploadNumber(file, fileList) {
      //限制图片数量
      this.$$modal.msgError("最多只能上传20张图片");
    },
    handleRemove(response, fileList, file) {
      const that = this;
      // 1.获取将要删除图片的临时路径
      const filePath = response.url;

      // 2.找到图片对应的索引值
      this.imgFileList.forEach(function (element, key) {
        if (element.url === filePath) {
          that.imgFileList.splice(key, 1); //传给后台的图片
        }
      });
      console.log(this.imgFileList);
    },
    handleVideoSuccess(res, file) {
      if (res.code == 200) {
        console.log(res);
        this.form.videoUrl = res.data.filePath;
        this.form.videoName = res.data.fileName;
      }
    },
    handleVideoRemove(response, fileList, file) {
      this.form.videoUrl = "";
      this.form.videoName = "";
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
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 转出金额校验
    checkPriceOut() {
      let checkPlan = "" + this.telFrom.realPrice;
      checkPlan = checkPlan
        .replace(/[^\d.]/g, "") // 清除“数字”和“.”以外的字符
        .replace(/\.{2,}/g, ".") // 只保留第一个. 清除多余的
        .replace(/^\./g, "") // 保证第一个为数字而不是.
        .replace(".", "$#$")
        .replace(/\./g, "")
        .replace("$#$", ".");
      if (checkPlan.indexOf(".") < 0 && checkPlan !== "") {
        // 以上已经过滤，此处控制的是如果没有小数点，首位不能为类似于 01、02的金额
        checkPlan = parseFloat(checkPlan) + "";
      } else if (checkPlan.indexOf(".") >= 0) {
        checkPlan = checkPlan.replace(/^()*(\d+)\.(\d\d).*$/, "$1$2.$3"); // 只能输入两个小数
      }
      this.telFrom.realPrice = checkPlan;
    },
    selectSaleTime() {
      if (this.saleTimeArr.length > 1) {
        var startDate = moment(this.saleTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.saleTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.saleTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.saleTimeEnd = endDate + " 23:59:59";
      }
    },
    selectupdateTime() {
      if (this.updateTimeArr.length > 1) {
        var startDate = moment(this.updateTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.updateTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.updateTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.updateTimeEnd = endDate + " 23:59:59";
      }
    },
    selectCreateTime() {
      if (this.createTimeArr.length > 1) {
        var startDate = moment(this.createTimeArr[0]).format("YYYY-MM-DD");
        this.queryParams.createTimeStart = startDate + " 00:00:00";
        var endDate = moment(this.createTimeArr[1]).format("YYYY-MM-DD");
        this.queryParams.createTimeEnd = endDate + " 23:59:59";
      }
    },
    getSupplyList() {
      getSupplyList("supplyCustomer").then(res => {
        if (res.code == 200) {
          this.supplyList = res.data;
          console.log(this.supplyList);
        }
      });
    },
    getSaleList() {
      getSupplyList("salesCustomer").then(res => {
        if (res.code == 200) {
          this.saleList = res.data;
        }
      });
    },
    selectDialogGameType() {
      let gameType = this.form.type;
      this.getSelectGameInfo(gameType);
      this.wzry = false;
      if ("王者荣耀" == gameType) {
        this.wzry = true;
      }
    },
    selectGameType() {
      let gameType = this.queryParams.type;
      if (gameType) {
        this.getSelectGameInfo(gameType);
        this.getList();
      }
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
        this.show.heroes = false;
        this.show.skins = false;
        this.show.suit = true;
        this.show.guns = true;
        this.show.select.heroes = false;
        this.show.select.skins = false;
        this.show.select.guns = true;
        this.show.select.suit = true;
      } else {
        this.show.select.heroes = true;
        this.show.select.skins = true;
        this.show.select.guns = false;
        this.show.select.suit = false;
        this.show.heroes = true;
        this.show.skins = true;
        this.show.suit = false;
        this.show.guns = false;
      }
      getDicts(gameType).then(res => {
        if (res.code == 200) {
          this.regionalArr = res.data;
        }
      });
    },
    showDiffTitle() {
      const saleStatus = this.$route.query.saleStatus;
      const type = this.$route.query.type;
      if ("admin" == type) {
        this.jiajianjia = true;
        this.show.price = true;
        this.show.extractAccount = true;
        this.show.extractPwd = true;
        this.show.extractTel = true;
        this.show.costPrice = true;
        this.show.supplyName = true;
        this.show.realPrice = true;
        this.show.createTime = true;
        this.show.salesName = true;
        this.show.intParam.saleTime = true;
        this.show.saleStateName = true;
        this.show.sinNum = true;
        this.show.updateTime = true;
        this.show.updateName = true;
        this.show.buyId = true;
        this.show.remake = true;
        this.show.saleTime = true;
        this.queryParams.accountType = "1,2";
      }
      if ("cmdSale" == type) {
        this.queryParams.accountType = "3";
        this.outAccountDel = true;
        this.show.supplyName = true;
        this.show.price = true;

        if (9 == saleStatus) {
          this.show.createTime = false;
          this.show.suit = false;
          this.show.skins = false;
          this.show.accountRemake = false;
          this.show.heroes = false;
          this.show.guns = false;
          this.show.level = false;
          this.show.saleTime = true;
          this.show.supplyName = true;
          this.show.salesName = true;
          this.show.realPrice = true;
          this.show.realPrice = true;
          this.show.buyId = true;
          this.show.exportExelShow = true;
        }
      }
      if ("audit" == type) {
        this.queryParams.accountType = "1,2";
        this.jiajianjia = true;
        this.show.price = true;
        this.show.extractAccount = true;
        this.show.extractPwd = true;
        this.show.extractTel = true;
        this.show.costPrice = true;
        this.show.supplyName = true;
        this.show.createTime = true;
        this.show.salesName = true;
        this.show.saleStateName = true;
        this.show.auditName = true;
        this.show.updateTime = true;
        this.show.note = true;
        this.show.auditState = true;
      }
      if ("sale" == type) {
        this.show.accountRemake = false;
        this.ifClick = false;
        if (1 == saleStatus) {
          this.show.extractAccount = true;
          this.show.price = true;
          this.show.newPrice = true;
          this.show.supplyName = true;
          this.show.des = true;
          this.show.createTime = true;
          this.wihh = 0;
        } else if (2 == saleStatus) {
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.salesName = true;
          this.show.saleStateName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
          this.show.newPrice = true;
        } else if (3 == saleStatus) {
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.salesName = true;
          this.show.saleStateName = true;
          this.show.saleTime = true;
        } else if (4 == saleStatus) {
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.salesName = true;
          this.show.saleStateName = true;
          this.show.updateTime = true;
          this.show.note = true;
        }
      } else if ("supply" == type) {
        if (1 == saleStatus) {
          this.outAccount = true;
          this.show.price = true;
          this.show.onOfOff = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
        } else if (2 == saleStatus) {
          this.ifClick = false;
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
        } else if (3 == saleStatus) {
          this.ifClick = false;
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.salesName = true;
          this.show.saleTime = true;
        } else if (4 == saleStatus) {
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.note = true;
          this.show.saleStateName = true;
          this.show.salesName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
        } else if (5 == saleStatus) {
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.salesName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
          this.show.note = true;
        } else if (6 == saleStatus) {
          this.ifClick = false;
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.salesName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
          this.show.note = true;
        } else if (7 == saleStatus) {
          this.ifClick = false;
          this.show.price = true;
          this.show.extractAccount = true;
          this.show.extractPwd = true;
          this.show.extractTel = true;
          this.show.costPrice = true;
          this.show.supplyName = true;
          this.show.createTime = true;
          this.show.sinNum = true;
          this.show.saleStateName = true;
          this.show.salesName = true;
          this.show.updateName = true;
          this.show.updateTime = true;
          this.show.note = true;
        }
      }
    },
    /** 查询游戏账号列表 */
    getList() {
      this.sleetcData.saleChannel = "";
      console.log(this.queryParams);
      let priceStart = typeof Number(this.queryParams.priceStart);
      let priceEnd = typeof Number(this.queryParams.priceEnd);
      if (priceStart > priceEnd) {
        this.$modal.msgError("价格数据不对");
        return true;
      }
      let heroStart = this.queryParams.heroStart;
      let heroEnd = this.queryParams.heroEnd;
      if (typeof Number(heroStart) > typeof Number(heroEnd)) {
        this.$modal.msgError("英雄数量输入不对");
        return true;
      }

      let skinStart = this.queryParams.skinStart;
      let skinEnd = this.queryParams.skinEnd;
      if (typeof Number(skinStart) > typeof Number(skinEnd)) {
        this.$modal.msgError("皮肤数量输入不对");
        return true;
      }

      let suitStart = this.queryParams.suitStart;
      let suitEnd = this.queryParams.suitEnd;
      if (typeof Number(suitStart) > typeof Number(suitEnd)) {
        this.$modal.msgError("套装数量输入不对");
        return true;
      }

      let gunsStart = this.queryParams.gunsStart;
      let gunsEnd = this.queryParams.gunsEnd;
      if (typeof Number(gunsStart) > typeof Number(gunsEnd)) {
        this.$modal.msgError("枪皮数量输入不对");
        return true;
      }
      if (this.queryParams.saleState == 6) {
        this.queryParams.timeout = 30;
        this.queryParams.saleState = 1;
      }
      this.loading = true;
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    selectLevel() {
      this.getList();
    },
    selectGameDaQu() {
      this.getList();
    },
    showSinNum(row) {
      console.log(row);
      this.sinNumShow = row.sinNum;
      this.showSinNumVisible = true;
    },
    // 取消按钮
    cancel() {
      console.log(1);
      this.imgFileList = [];
      this.imgShowList = [];
      this.open = false;
      this.reset();
    },
    closeDialog() {
      this.imgFileList = [];
      this.imgShowList = [];
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        createId: null,
        createTime: null,
        accountId: null,
        type: null,
        regional: null,
        level: null,
        heroes: null,
        skins: null,
        suit: null,
        guns: null,
        des: null,
        price: null,
        supplyId: null,
        salesId: null,
        saleState: null,
        updateId: null,
        updateTime: null,
        extractAccount: null,
        extractPwd: null,
        costPrice: null,
        sinNum: null,
        delFlag: null
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
      this.queryParams.priceStart = null;
      this.queryParams.priceEnd = null;
      this.queryParams.heroStart = null;
      this.queryParams.heroEnd = null;
      this.queryParams.skinStart = null;
      this.queryParams.skinEnd = null;
      this.queryParams.suitStart = null;
      this.queryParams.suitEnd = null;
      this.queryParams.gunsStart = null;
      this.queryParams.gunsEnd = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      if (selection.length > 0) {
        selection[0].salePrice = selection[0].newPrice
        selection[0].suplyprice = selection[0].price
        selection[0].supllypay = '2'
        selection[0].ifPay = '2'
        selection[0].account = selection[0].extractAccount
        this.sleetcData = JSON.parse(JSON.stringify(selection[0]))
        this.assembleCopy(selection);
        this.copyPwd =
          "编号：" +
          selection[0].id +
          "\n" +
          "  账号：" +
          selection[0].extractAccount +
          "\n" +
          "  密码：" +
          selection[0].extractPwd +
          "\n";

        this.copyTel =
          "   编号：" +
          selection[0].id +
          "\n" +
          "  账号：" +
          selection[0].extractAccount +
          "\n" +
          "  密码：" +
          selection[0].extractPwd +
          "\n" +
          "  手机号：" +
          selection[0].extractTel;
        this.ids = selection.map(item => item.id);
        this.single = selection.length !== 1;
        this.multiple = !selection.length;
      }
    },
    //拼装批量复制
    assembleCopy(selection) {
      console.log(selection);
      this.copyVlaue = "";
      selection.map(item => {
        let urlArr = "";
        if (item.urlArr) {
          item.urlArr.map(ite => {
            urlArr = urlArr + ite.url + "\n";
          });
        }
        let value = "";

        // http://xh.ikbh.top/show.html?YXLX_ID=%E7%8E%8B%E8%80%85%E8%8D%A3%E8%80%80&ZHID=313

        // let hostt = "http://xh.dev.zhaoyudu.com/show.html?ZHID=" + item.id;
        // let hostt = "http://xh.zhaoyudu.com/show.html?ZHID=" + item.id;
        let hostt = this.hoste + item.id;
        value =
          "编号：" +
          item.id +
          "\n" +
          "自编号：" +
          item.gameName +
          "\n" +
          "区服：" +
          item.regional +
          "\n" +
          "实名认证：" +
          item.level +
          "\n" +
          "英雄：" +
          item.heroes +
          "\n" +
          "皮肤：" +
          item.skins +
          "\n" +
          "简介：" +
          item.des +
          "\n" +
          "图片：" +
          hostt +
          "\n";
        let geduan =
          "\n" +
          "------------------------------" +
          "\n" +
          "------------------------------------" +
          "\n";
        this.copyVlaue = this.copyVlaue + value + geduan;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加游戏账号";
    },
    showImage(row) {
      let params = {};
      params.id = row.id;
      getImage(params).then(res => {
        if (res.code == 200) {
          this.imgList = res.data;
          this.dialogImgVisible = true;
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log(row);
      if (!this.ifClick) {
        this.showImage(row);
        return true;
      }
      this.reset();
      const id = row.id || this.ids || null;
      if ("和平精英" == row.type) {
        this.dialogGameTypeHp = true;
      } else {
        this.dialogGameType = true;
      }
      this.wzry = false;
      getInfo(id).then(response => {
        this.form = response.data;
        if (response.data.urlArr) {
          this.imgFileList = response.data.urlArr;
          this.imgShowList = JSON.parse(JSON.stringify(response.data.urlArr));
        }
        this.imageUrl = this.form.titleImg;
        let video = {};
        this.videoList = [];
        this.changeSpecial();
        if (response.data.videoUrl) {
          video.name = response.data.videoName;
          video.url = response.data.videoUrl;
          this.videoList[0] = video;
        }
        this.title = "修改游戏账号";
        this.open = true;
        if ("王者荣耀" == this.form.type) {
          this.wzry = true;
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      if ("3" == this.form) {
        this.$modal.msgError("终端数据无法编辑！！！,请联系供货商");
        return true;
      }
      this.$refs["form"].validate(valid => {
        this.form.urlArr = this.imgFileList;
        if (valid) {
          if (this.form.id != null) {
            const that = this;
            this.form.interType = "edit";
            updateInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.interType = "add";
            addInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除游戏账号编号为"' + ids + '"的数据项？')
        .then(function () {
          return delInfo(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
    handleAudit(type, operation) {
      this.exportLoading = false;
      let params = {};
      if (null != this.intType) {
        params.type = this.intType;
      } else {
        params.type = type;
      }
      if (null != this.operation) {
        params.operation = this.operation;
      } else {
        params.operation = operation;
      }
      if ("" != this.unsoldFrom.note || null != this.unsoldFrom.note) {
        params.note = this.unsoldFrom.note;
      }
      if ("" != this.telFrom.remake || null != this.telFrom.remake) {
        params.remake = this.telFrom.remake;
      }
      if ("" != this.telFrom.realPrice || null != this.telFrom.realPrice) {
        params.realPrice = this.telFrom.realPrice;
      }
      if ("" != this.telFrom.buyId || null != this.telFrom.buyId) {
        params.buyId = this.telFrom.buyId;
      }
      const ids = this.ids;
      if ("审核" != operation) {
        if (this.ids.length > 1) {
          this.$modal.msgError("只能选择一条数据");
          return true;
        }
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
          if (2 == type) {
            this.excCopy(this.copyPwd);
          } else if (3 == type) {
            this.excCopy(this.copyTel);
          }
        })
        .catch(() => { });
    },
    excCopy(data) {
      const url = data;
      const oInput = document.createElement("input");
      oInput.value = url;
      document.body.appendChild(oInput);
      oInput.select(); // 选择对象;
      console.log(oInput.value);
      document.execCommand("Copy"); // 执行浏览器复制命令
    },
    extractTelFun() {
      this.dialogTelFormVisible = true;
      this.showDialogTitle = "提取手机号";
      this.intType = 3;
      this.operation = "提取手机号";
    },
    confirmTel() {
      this.handleAudit(this.intType, this.operation);
      this.dialogTelFormVisible = false;
    },
    cancelSold() {
      this.dialogFormVisible = true;
      this.showDialogTitle = "转为未售";
      this.intType = 1;
      this.operation = "取消售中";
    },
    confirmSub() {
      this.handleAudit(this.intType, this.operation);
      this.dialogFormVisible = false;
    },
    transferProblem() {
      this.dialogFormVisible = true;
      this.showDialogTitle = "转为问题号";
      this.intType = 4;
      this.operation = "转为问题号";
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$modal
        .confirm("是否确认导出所有游戏账号数据项？")
        .then(() => {
          this.exportLoading = true;
          return exportInfo(queryParams);
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
<style lang="scss" scoped>
.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 6px 0;
}

::v-deep.pagination-container {
  padding: 0 !important;
  margin-bottom: 0;
  margin-top: 5px;
}

::v-deep.pagination-container .el-pagination {
  left: 0 !important;
}

::v-deep.el-table .el-table__header-wrapper th {
  background-color: #f8fafd;
  border-top: 1px solid #e5e5e5;
}

.zdy-plfz-btn,
.zdy-plfz-btn:hover,
.zdy-plfz-btn:focus,
.zdy-plfz-btn:active {
  color: #fff !important;
  background: #8333de !important;
  border: 1px solid #8333de !important;
}

.zdy-tqmm-btn,
.zdy-tqmm-btn:hover,
.zdy-tqmm-btn:focus,
.zdy-tqmm-btn:active {
  color: #fff !important;
  background: #4b8aff !important;
  border: 1px solid #4b8aff !important;
}

.zdy-dc-btn,
.zdy-dc-btn:hover,
.zdy-dc-btn:focus,
.zdy-dc-btn:active {
  color: #fff;
  background: #00c9db;
  border: 1px solid #00c9db;
}

.zdy-pifu-content {
  width: 25px;
  height: 25px;
  margin-left: 10px;
  cursor: pointer;

  img {
    width: 100%;
    height: 100%;
  }
}

.zdy-table-header-btn {
  display: flex;
  align-items: center;
}

.zdy-header-btn-group {
  float: right;
  padding: 0 10px 0 20px;
}

::v-deep .zdy-table-header .el-input__inner {
  border: none;
}

.zdy-table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 6px;
  box-sizing: border-box;
}

.zdy-main {
  margin-top: 10px;
  border: 1px solid #dfe6ec;
  box-sizing: border-box;
}

.zdy-form {
  padding: 8px;
  background-color: #f4f7f9;
  border-radius: 6px;
  box-sizing: border-box;
}

.zdy-btn-refresh {
  background-color: #94aab9;
  border-color: #94aab9;
}

.zdy-btn {
  padding: 7px 20px;
}

.shiming-label {
  width: 68px;
  padding-right: 12px;
  text-align: right;
}

.shiming-wrap {
  width: 283px;

  height: 37px;
  display: flex;
  align-items: center;
}

::v-deep .shiming-wrap .el-checkbox__inner {
  width: 20px;
  height: 20px;
  border-radius: 50%;
}

::v-deep .shiming-wrap .el-checkbox__inner::after {
  height: 9px;
  left: 7px;
  top: 3px;
}

.shiming-content {
  display: flex;
  align-items: center;
}

.shiming-content img {
  width: 13px;
  height: 15px;
  margin: 0 6px 0 0px;
}

.shiming-content p {
  font-size: 14px;
  color: #3b4b62;
}

.mm-b-8 {
  margin-bottom: 8px !important;
}

.two-input-content {
  display: flex;
  align-items: center;
}

.two-input-item {
  width: 90px;
}

.two-input-content span {
  width: 15px;
  height: 1px;
  margin: 0 10px;
  background: #aaaaaa;
}

.showOverTooltip {
  display: -webkit-box;
  text-overflow: ellipsis;
  line-height: 20px;
  overflow: hidden;
  /*这里是3行*/
  -webkit-line-clamp: 6;
  -webkit-box-orient: vertical;
  margin-right: 20px;
}

/* .sssssssss {
  margin-right: 40%;
} */
.el-form-item {
  margin-bottom: 0px;
}

.el-form--label-right {
  border-bottom: 1px solid red;
}

::v-deep .el-table td.el-table__cell {
  border-bottom: 1px dashed #b5b8bc;
}

.cell {
  margin-left: 0%;
}
</style>
<style>
.el-tooltip__popper {
  max-width: 20%;
}

/* .el-tooltip__popper {
  display: none;
} */

.el-table--medium .el-table__cell {
  padding: 0;
}

.el-dropdown-menu--medium {
  max-height: 700px;
  overflow: auto;
}
</style>
