<template>
  <section class="container">
    <div class="top_nav">
      <div class="search-wrapper" @click="boxradioIsShow = null" v-loading="loading">
        <div class="location w1200">
          <!-- <el-icon class="home-icon" color="#4E5969" :size="15">
            <House />
          </el-icon> -->
          <el-breadcrumb separator=">">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/index/accountOrder' }">游戏类型</el-breadcrumb-item>
            <el-breadcrumb-item>{{ selectGameType.name }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <!-- 最上 -->
        <!-- <div class="zhanghao_nav"> -->
        <!-- <div class="t_fooer dis_flex_ai_c">
            <div class="h4">在售账号</div>
            <div class="ul">
              <div class="li" @click="accountTypeChange(item)" v-for="(item, index) in t_fooer" :key="item.id"
                :class="{ 'selected-items': searchParams['accountType'] == item.id }">
                {{ item.name }}
              </div>
            </div>
          </div> -->
        <!-- <div class="con_fooer dis_flex_ai_c"> -->
        <!-- <div class="h4">商品类型</div>
            <div class="ul">
              <div class="li" @click="goodsTypeChange({ fieldName: '' })"
                :class="{ 'selected-items': !searchParams['goodsTypes'] || searchParams['goodsTypes'].length == 0 }">
                所有账号
              </div>
              <div class="li" @click="goodsTypeChange(item)" v-for="(item, index) in con_fooer" :key="item.id"
                :class="{ 'selected-items': searchParams['goodsTypes'] && searchParams['goodsTypes'].indexOf(item.fieldName) > -1 }">
                {{ item.fieldName }}
              </div>
            </div> -->
        <!-- </div> -->

        <!-- <div class="price-filter qufu">
            <span class="selected-titile" style="width: auto;" v-if="selectGameType && selectGameType.type == 0">系统</span>
            <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 0">
              <el-select style="width: 130px;" v-model="searchParams['systemId']" class="m-2" placeholder="请选择"
                size="small" clearable>
                <el-option v-for="item in sysList" :key="item.id" :label="item.fieldName" :value="item.id" />
              </el-select>
            </div>

            <span class="selected-titile" style="width: auto;"
              v-if="selectGameType && selectGameType.type == 0">运营商</span>
            <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 0">
              <el-select style="width: 130px;" v-model="searchParams['carrierId']" class="m-2" placeholder="请选择"
                size="small" @change="areaClick($event, 'carrierId')" clearable>
                <el-option v-for="item in carrierList" :key="item.id" :label="item.fieldName" :value="item.id" />
              </el-select>
            </div>

            <span v-if="selectGameType && selectGameType.type == 1" class="selected-titile" style="width: auto;">大区</span>
            <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 1">
              <el-select style="width: 130px;" v-model="searchParams['groupId']" class="m-2" placeholder="请选择"
                size="small" @change="areaClick($event, 'groupId')" clearable>
                <el-option v-for="item in areaList" :key="item.id" :label="item.fieldName" :value="item.id" />
              </el-select>
            </div>

            <span v-if="selectGameType && selectGameType.type == 1" class="selected-titile" style="width: auto;">服务器</span>
            <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 1">
              <el-select style="width: 130px;" v-model="searchParams['serverId']" class="m-2" placeholder="请选择"
                size="small" clearable>
                <el-option v-for="item in serverList" :key="item.id" :label="item.fieldName" :value="item.id" />
              </el-select>
            </div>
          </div> -->

        <!-- <div class="bomm_fooer dis_flex_ai_c">
            <div class="screening">
              <el-select v-model="screening_value" class="m-2" placeholder="筛选开关" size="small">
                <el-option v-for="item in screening" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </div>
          </div> -->
        <!-- 总展开 -->
        <!-- <div class="zhankai">
            <el-button size="small" @click="showMoreSearch">{{ isShowMoreSearch ? '收起筛选' : '展开筛选' }}
              <el-icon class="no-inherit" :class="{ up: isShowMoreSearch }">
                <DArrowRight />
              </el-icon>
            </el-button>
          </div> -->
        <!-- </div> -->

        <div class="filter-wrapper w1200" v-if="attrs && attrs.length">
          <!-- 系统 -->

          <div class="title">
            <!-- <div class="con dis_flex_ai_c">
              <div class="line"></div>
              高级筛选
            </div> -->
            <!-- <div class="ri_but dis_flex_ai_c">
              <el-button size="small" @click="resetSelected" class="confirm-btn" type="danger">
                <el-icon class="home-icon" color="#ffffff" :size="15">
                  <DeleteFilled />
                </el-icon>清空
              </el-button>
              <el-button size="small" @click="confirmSelected" class="confirm-btn" type="primary">
                <el-icon class="home-icon" color="#ffffff" :size="15">
                  <Search />
                </el-icon>搜索
              </el-button>
            </div> -->
          </div>

          <div class="flex qujian-box">
            <!-- style="border-bottom: 1px solid #38324b" -->
            <div class="price-filter qufu" style="display: flex; flex-direction: column">
              <div style="display: flex">
                <span
                  class="selected-titile"
                  style="width: 100px; padding-right: 0; margin-right: 10px"
                  >类型</span
                >
                <div class="filter-item-box">
                  <!-- <el-select style="width: 130px;background-color: #eee;" v-model="searchParams['systemId']" class="m-2" placeholder="请选择"
                    size="small" clearable>
                    <el-option v-for="item in sysList" :key="item.id" :label="item.fieldName" :value="item.id" />
                  </el-select> -->
                  <div class="flex radio-cont">
                    <div
                      class="item"
                      v-for="item in AccountTypeList"
                      :key="item.value"
                      :class="{ 'selected-items': typeId == item.value }"
                      @click="changeTePriceAccount(item.value)"
                    >
                      <p class="items">{{ item.label }}</p>
                    </div>
                  </div>
                </div>
              </div>

              <div style="display: flex">
                <span
                  class="selected-titile"
                  style="width: 100px; padding-right: 0; margin-right: 10px"
                  v-if="selectGameType && selectGameType.type == 0"
                  >系统</span
                >
                <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 0">
                  <!-- <el-select style="width: 130px;background-color: #eee;" v-model="searchParams['systemId']" class="m-2" placeholder="请选择"
                    size="small" clearable>
                    <el-option v-for="item in sysList" :key="item.id" :label="item.fieldName" :value="item.id" />
                  </el-select> -->
                  <div class="flex radio-cont">
                    <div
                      class="item"
                      :class="{ 'selected-items': searchParams['systemId'] == item.id }"
                      v-for="(item, index) in sysList"
                      :key="`selected${index}`"
                      @click="radioClick2(item, 'systemId')"
                    >
                      <!-- <el-tooltip :content="item.fieldName" placement="top" effect="customized"> -->
                      <p class="items">{{ item.fieldName }}</p>
                      <!-- </el-tooltip> -->
                    </div>
                  </div>
                </div>
              </div>

              <div style="display: flex">
                <span
                  class="selected-titile"
                  style="width: 100px; padding-right: 0; margin-right: 10px"
                  v-if="selectGameType && selectGameType.type == 0"
                  >运营商</span
                >
                <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 0">
                  <!-- <el-select style="width: 130px;" v-model="searchParams['carrierId']" class="m-2" placeholder="请选择"
                    size="small" @change="areaClick($event, 'carrierId')" clearable>
                    <el-option v-for="item in carrierList" :key="item.id" :label="item.fieldName" :value="item.id" />
                  </el-select> -->
                  <div class="flex radio-cont">
                    <div
                      class="item"
                      :class="{ 'selected-items': searchParams['carrierId'] == item.id }"
                      v-for="(item, index) in carrierList"
                      :key="`selected${index}`"
                      @click="radioClick2(item, 'carrierId')"
                    >
                      <!-- <el-tooltip :content="item.fieldName" placement="top" effect="customized"> -->
                      <p class="items">{{ item.fieldName }}</p>
                      <!-- </el-tooltip> -->
                    </div>
                  </div>
                </div>
              </div>

              <div style="display: flex">
                <span
                  v-if="selectGameType && selectGameType.type == 1"
                  class="selected-titile"
                  style="width: 100px; padding-right: 0; margin-right: 10px"
                  >大区</span
                >
                <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 1">
                  <!-- <el-select style="width: 130px;" v-model="searchParams['groupId']" class="m-2" placeholder="请选择"
                    size="small" @change="areaClick($event, 'groupId')" clearable>
                    <el-option v-for="item in areaList" :key="item.id" :label="item.fieldName" :value="item.id" />
                  </el-select> -->
                  <div class="flex radio-cont">
                    <div
                      class="item"
                      :class="{ 'selected-items': searchParams['groupId'] == item.id }"
                      v-for="(item, index) in areaList"
                      :key="`selected${index}`"
                      @click="radioClick2(item, 'groupId', $event)"
                    >
                      <!-- <el-tooltip :content="item.fieldName" placement="top" effect="customized"> -->
                      <p class="items">{{ item.fieldName }}</p>
                      <!-- </el-tooltip> -->
                    </div>
                  </div>
                </div>
              </div>

              <div style="display: flex">
                <span
                  v-if="selectGameType && selectGameType.type == 1"
                  class="selected-titile"
                  style="width: 100px; padding-right: 0; margin-right: 10px; margin-bottom: 18px"
                  >服务器</span
                >
                <div class="filter-item-box" v-if="selectGameType && selectGameType.type == 1">
                  <!-- <el-select style="width: 130px;" v-model="searchParams['serverId']" class="m-2" placeholder="请选择"
                    size="small" clearable>
                    <el-option v-for="item in serverList" :key="item.id" :label="item.fieldName" :value="item.id" />
                  </el-select> -->
                  <div class="flex radio-cont">
                    <div
                      class="item"
                      :class="{ 'selected-items': searchParams['serverId'] == item.id }"
                      v-for="(item, index) in serverList"
                      :key="`selected${index}`"
                      @click="radioClick2(item, 'serverId')"
                    >
                      <!-- <el-tooltip :content="item.fieldName" placement="top" effect="customized"> -->
                      <p class="items">{{ item.fieldName }}</p>
                      <!-- </el-tooltip> -->
                    </div>
                  </div>
                </div>
              </div>

              <!-- 筛选 -->
              <div style="display: flex">
                <span class="selected-titile" style="padding-right: 0; margin-right: 10px"
                  >筛选条件</span
                >
                <div class="filter-item-box">
                  <div
                    class="newFiledName flex"
                    v-for="custom in attrs"
                    :key="custom.id"
                    @click="getOneCostom(custom)"
                    v-show="
                      custom.isEnable === '0' &&
                      custom.isSearch == '1' &&
                      custom.fieldType != 'input'
                    "
                    :class="{
                      tanchuactive:
                        custom.id == idToo ||
                        searchParams[custom.aavName + 'start'] ||
                        searchParams[custom.aavName]
                    }"
                  >
                    <div class="flex">
                      <div>
                        {{ custom.fieldName }}
                        <span
                          v-if="custom.fieldType == 'checkbox' || custom.fieldType == 'boxradio'"
                          >({{
                            searchParams[custom.aavName + 'rander'].length != 0
                              ? searchParams[custom.aavName + 'rander'].length
                              : '0'
                          }})</span
                        >
                      </div>
                      <div style="padding-top: 3px">
                        <el-icon
                          size="14"
                          class="xia_icon"
                          :class="{ xia_iconActive: custom.id == idToo && flagType }"
                          style="margin-left: 6px"
                          color="#ffffff"
                        >
                          <CaretBottom />
                        </el-icon>
                      </div>
                    </div>
                  </div>

                  <div class="tanchuzizhi" v-if="flagType">
                    <div v-for="custom in radioList" :key="custom.id">
                      <div
                        class="flex qujian-item"
                        style="padding: 10px 0"
                        v-if="
                          custom.isEnable === '0' &&
                          custom.isSearch == '1' &&
                          custom.fieldType == 'number'
                        "
                      >
                        <!-- border-bottom: 1px solid #38324b -->
                        <!-- 选项标题 -->
                        <!-- <span class="selected-titile" style="width: 100px">{{
                        custom.fieldName
                      }}</span> -->
                        <!-- 输入区 -->
                        <div class="filter-item-box" style="margin-right: 10px">
                          <span v-if="custom.fieldType == 'number'" class="number-input">
                            <el-input
                              class="my-input"
                              size="mini"
                              v-model="searchParams[custom.aavName + 'start']"
                              style="text-align: left; width: 183px !important"
                              :placeholder="`最低值`"
                            ></el-input>
                            <span> - </span>
                            <el-input
                              class="my-input"
                              size="mini"
                              v-model="searchParams[custom.aavName + 'end']"
                              style="text-align: left; width: 183px !important"
                              :placeholder="`最高值`"
                            ></el-input>
                          </span>
                        </div>
                      </div>
                    </div>
                    <div>
                      <div v-for="(custom, i) in checkBoxList" :key="custom.id">
                        <div
                          class="price-filter custom-attr-item"
                          v-if="custom.isEnable === '0' && custom.isSearch == '1'"
                        >
                          <!-- 筛选标题 -->
                          <!-- <span class="selected-titile">{{ custom.fieldName }}</span> -->
                          <!-- 选项列表 -->
                          <div class="filter-item-box">
                            <!-- 前三 -->
                            <div class="flex radio-cont" v-if="custom.fieldType == 'radio'">
                              <div
                                class="item"
                                :class="{
                                  'selected-items': searchParams[custom.aavName] == item.id
                                }"
                                v-for="(item, index) in custom.childAttributes"
                                :key="`selected${index}`"
                                @click="radioClick(item, custom)"
                              >
                                <p class="items">{{ item.fieldName }}</p>
                              </div>
                            </div>
                            <!-- 可展开选项列表 -->
                            <el-checkbox-group
                              v-model="searchParams[custom.aavName + 'rander']"
                              v-if="custom.fieldType == 'checkbox'"
                              @change="checkboxChange($event, custom.aavName)"
                            >
                              <el-checkbox
                                v-for="checkItem in custom.childAttributes"
                                :label="checkItem.id"
                                :value="checkItem.id"
                                :key="checkItem.id"
                              >
                                {{ checkItem.fieldName }}
                              </el-checkbox>
                            </el-checkbox-group>
                            <!-- 不可展开选项列表 -->
                            <div v-if="custom.fieldType == 'boxradio'" @click="setOffset($event)">
                              <el-checkbox
                                v-for="(checkItem, i) in custom.childAttributes"
                                :key="checkItem.id"
                                v-model="checkItem.ischeck"
                                @change="boxradioCheckClick($event, custom, checkItem, i)"
                              >
                                {{ checkItem.fieldName }}

                                <el-radio-group
                                  class="boxradioRadioBox"
                                  :style="{ left: boxradioRadioBoxLeft, top: boxradioRadioBoxTop }"
                                  :class="{ show: checkItem.id == boxradioIsShow }"
                                  v-model="searchParams[custom.aavName + i]"
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
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- <div v-for="custom in attrs" :key="custom.id">
              <div class="flex qujian-item"
                v-if="custom.isEnable === '0' && custom.isSearch == '1' && custom.fieldType == 'number'"> -->
            <!-- 选项标题 -->
            <!-- <span class="selected-titile" style="width: 100px;">{{ custom.fieldName }}</span> -->
            <!-- 输入区 -->
            <!-- <div class="filter-item-box" style="margin-right: 10px;">
                  <span v-if="custom.fieldType == 'number'" class="number-input">
                    <el-input size="mini" v-model="searchParams[custom.aavName + 'start']"
                      style="text-align: left;width: 80px!important;" :placeholder="`请填写`"></el-input>
                    <span> - </span>
                    <el-input size="mini" v-model="searchParams[custom.aavName + 'end']"
                      style="text-align: left;width: 80px!important;" :placeholder="`请填写`"></el-input>
                  </span>
                </div>
              </div>
            </div> -->
          </div>

          <!--  -->
        </div>
      </div>
      <!-- 商品列表 -->
      <div class="good-list w1200">
        <div class="header-filter" v-loading="loading">
          <div class="left-filter">
            <!-- <div class="sort-filter">
              <p>综合排序</p>
            </div> -->
            <div class="price-filter">
              <p class style="color: #fff">价格由高到低</p>
              <div class="up-down-icon">
                <el-icon
                  style="padding: 10px 0 2px 0"
                  :color="picreOdr == 'priceasc' ? '#ffffff' : ''"
                  @click="priceFilter('priceasc')"
                >
                  <CaretTop />
                </el-icon>
                <el-icon
                  style="padding: 2px 0 10px 0"
                  :color="picreOdr == 'pricedesc' ? '#fff' : ''"
                  @click="priceFilter('pricedesc')"
                >
                  <CaretBottom />
                </el-icon>
              </div>
            </div>
            <div class="updown-filter">
              <p style="color: #fff">时间升序</p>
              <div class="up-down-icon">
                <el-icon
                  style="padding: 10px 0 2px 0"
                  :color="picreOdr == 'timeasc' ? '#fff' : ''"
                  @click="priceFilter('timeasc')"
                >
                  <CaretTop />
                </el-icon>
                <el-icon
                  style="padding: 2px 0 10px 0"
                  :color="picreOdr == 'timedesc' ? '#fff' : ''"
                  @click="priceFilter('timedesc')"
                >
                  <CaretBottom />
                </el-icon>
              </div>
            </div>
          </div>
          <div class="flex">
            <div class="flex qujian-item flex_sousuo">
              <span class="selected-titile" style="width: 50px; margin-top: -2px">价格</span>
              <div class="filter-item-box">
                <el-input
                  class="my-input"
                  size="mini"
                  v-model="searchParams['priceStart']"
                  style="text-align: left; width: 80px !important"
                  :placeholder="`请填写`"
                ></el-input>
                <span> - </span>
                <el-input
                  class="my-input"
                  size="mini"
                  v-model="searchParams['priceEnd']"
                  style="text-align: left; width: 80px !important"
                  :placeholder="`请填写`"
                ></el-input>
                <!-- <el-input style="width:300px" class="input-with-select" size="mini" v-model="searchInput"
                  placeholder="请输入搜索关键词" @keyup.enter.native="confirmSelected">
                </el-input> -->
              </div>
            </div>
            <div class="flex qujian-item flex_sousuo">
              <span class="selected-titile" style="width: 70px; margin-left: 30px; margin-top: -2px"
                >关键词</span
              >
              <div class="filter-item-box">
                <el-input
                  style="width: 220px"
                  class="input-with-select my-input"
                  size="mini"
                  v-model="searchInput"
                  placeholder="请输入搜索关键词"
                  @keyup.enter.native="confirmSelected"
                >
                </el-input>
              </div>
            </div>
            <div class="ri_but dis_flex_ai_c">
              <el-button
                style="background-color: #b42c1d !important; color: #fff; border: 0"
                size="small"
                @click="confirmSelected"
                class="confirm-btn"
              >
                <el-icon class="home-icon" color="#ffffff" :size="15"> <Search /> </el-icon>搜索
              </el-button>
              <el-button size="small" @click="resetSelected" class="confirm-btn" type="info">
                <el-icon class="home-icon" color="#ffffff" :size="15"> <DeleteFilled /> </el-icon
                >清空
              </el-button>
            </div>
          </div>

          <div class="list_type">
            <span :class="listType == 0 ? 'on' : ''" @click="setlistType(0)">
              <el-icon>
                <Menu />
              </el-icon>
            </span>
            <span :class="listType == 1 ? 'on' : ''" @click="setlistType(1)">
              <el-icon>
                <Expand />
              </el-icon>
            </span>
            <span :class="listType == 2 ? 'on' : ''" @click="setlistType(2)">文</span>
          </div>
        </div>
        <!-- 列表 -->
        <div class="goods-box" style="background: #1c1632 !important" v-show="listType == 0">
          <div class="list-wrapper" v-for="(item, index) in accountList" :key="`acc${index}`">
            <!-- 图片 -->
            <div class="img">
              <img @click="seeDetail(item)" :src="item.titleImg || noimg" alt />
            </div>
            <div class="con_tent">
              <!-- 文本 -->
              <div class="good-content">
                <p class="name" @click="seeDetail(item)">{{ item.des }}</p>
              </div>

              <div class="icon_box_all_one" style="margin-top: 16px">
                <div class="icon_new" v-if="item.accountType == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/gfds.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/mfbp.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 1">
                  <img class="img_box_new" src="../../assets/biaoqian/xybp.png" alt="" />
                </div>
              </div>

              <div class="right flex fbt">
                <div style="color: #c0bdc3">售价：</div>
                <p class="price">
                  ￥
                  <span class="price-num">
                    {{ item.newPrice }}
                  </span>
                </p>
                <!-- <div class="game-type">{{ item.createTime.substring(0, 10) }}</div> -->
              </div>

              <!-- 支持包赔+安卓QQ -->
              <div
                class="game-type"
                style="display: flex; justify-content: space-between; color: #c0bdc3"
              >
                <div>游戏类型：</div>
                <div>{{ item.gameName }}</div>
              </div>

              <!-- 价钱  -->
            </div>
            <div class="ziying" v-if="item.accountType == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/zybg.png" alt="" />
            </div>
            <div class="tejia" v-if="item.isOnSpecialOffer == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/tjbg.png" alt="" />
            </div>
          </div>
        </div>
        <!-- 列表 -->
        <div class="goods-box-one" v-show="listType == 1">
          <div class="list-wrapper" v-for="(item, index) in accountList" :key="`acc${index}`">
            <!-- 图片 -->
            <div class="img">
              <img @click="seeDetail(item)" :src="item.titleImg || noimg" alt />
            </div>
            <div class="con_tent">
              <!-- 文本 -->
              <div class="good-content">
                <p class="name" @click="seeDetail(item)">{{ item.des }}</p>
              </div>

              <!-- 支持包赔+安卓QQ -->
              <div class="icon_box_all" style="margin-right: 12px">
                <div class="icon_new" v-if="item.accountType == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/gfds.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/mfbp.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 1">
                  <img class="img_box_new" src="../../assets/biaoqian/xybp.png" alt="" />
                </div>
                <div class="game-type">游戏类型：{{ item.gameName }}</div>
              </div>

              <!-- 价钱  -->
              <div
                style="
                  color: #d0cdcd;
                  display: flex;
                  align-items: center;
                  justify-content: space-between;
                  width: 886px;
                "
              >
                <div class="right">
                  <p class="price">
                    ￥
                    <span class="price-num">
                      {{ item.newPrice }}
                    </span>
                  </p>
                  <!-- <div class="game-type">{{ item.recoveryTime }}</div> -->
                </div>
              </div>
              <!-- <div class="right flex fbt" style="height: 50px;line-height: 50px;">
                <div>￥{{ item.newPrice }}</div>
                <div class="game-type">{{ item.recoveryTime }}</div>
              </div> -->
            </div>
            <div class="ziying" v-if="item.accountType == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/zybg.png" alt="" />
            </div>
            <div class="tejia" v-if="item.isOnSpecialOffer == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/tjbg.png" alt="" />
            </div>
          </div>
        </div>
        <!-- 列表 -->
        <div class="goods-box-two" v-show="listType == 2">
          <!-- :class="listType == 0 ? 'goods-box' : listType == 1 ? 'goods-box-one' : listType == 2 ? 'goods-box-two' : ''"> -->
          <div class="list-wrapper" v-for="(item, index) in accountList" :key="`acc${index}`">
            <!-- 图片 -->
            <div class="img">
              <img @click="seeDetail(item)" :src="item.titleImg || noimg" alt />
            </div>
            <div class="con_tent">
              <!-- 文本 -->
              <div class="good-content">
                <p class="name" @click="seeDetail(item)">{{ item.des }}</p>
              </div>

              <!-- 支持包赔+安卓QQ -->
              <!-- <div class="name-bot" style="align-items: center">
                
              </div> -->
              <div class="icon_box_all" style="margin-right: 12px">
                <div class="icon_new" v-if="item.accountType == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/gfds.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 2">
                  <img class="img_box_new" src="../../assets/biaoqian/mfbp.png" alt="" />
                </div>
                <div class="icon_new" v-if="item.isGuarantee == 1">
                  <img class="img_box_new" src="../../assets/biaoqian/xybp.png" alt="" />
                </div>
                <div class="game-type">游戏类型：{{ item.gameName }}</div>
              </div>

              <!-- 价钱  -->
              <div class="right flex fbt" style="width: 500px">
                <p class="price">
                  <span class="price-num">￥{{ item.newPrice }}</span>
                </p>
                <!-- <div class="game-type">{{ item.recoveryTime }}</div> -->
              </div>
            </div>
            <div class="ziying" v-if="item.accountType == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/zybg.png" alt="" />
            </div>
            <div class="tejia" v-if="item.isOnSpecialOffer == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/tjbg.png" alt="" />
            </div>
          </div>
        </div>
        <!-- 分页 -->
        <div class="pagination-block">
          <el-pagination
            v-loading="loading"
            style="float: right; --el-color-primary: #b42c1d"
            v-model:currentPage="searchParams.pageNum"
            v-model:page-size="searchParams.pageSize"
            :page-sizes="[10, 20, 50, 100, 200]"
            :small="small"
            :disabled="pageDisabled"
            :background="pageBackground"
            layout="total,sizes, prev, pager, next,jumper"
            :total="accountListTotal"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>

    <!-- 底部 -->
    <Footer />
  </section>
</template>
<script>
import {
  RefreshRight,
  CaretTop,
  Search,
  DeleteFilled,
  CaretBottom,
  Expand,
  Menu,
  House,
  View,
  DArrowRight
} from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, computed, watch, onMounted, getCurrentInstance } from 'vue';
import { useRouter } from 'vue-router';
import api from '@/api/index';
import { useStore } from 'vuex';
import { ElMessageBox } from 'element-plus';
import { useRoute } from 'vue-router';

export default {
  name: 'accountorder',
  components: {
    Footer,
    House,
    RefreshRight,
    DeleteFilled,
    Search,
    CaretBottom,
    Expand,
    Menu,
    CaretTop,
    View,
    DArrowRight
  },
  setup(props, ctx) {
    const router = useRouter();
    const store = useStore();
    const internalInstance = getCurrentInstance();
    const state = reactive({
      t_fooer: [
        {
          name: '所有账号',
          id: ''
        },
        {
          name: '自营账户',
          id: 1
        },
        {
          name: '代售账号',
          id: 2
        }
      ],
      con_fooer: [
        {
          fieldName: '所有账号',
          id: ''
        }
      ],
      screening: [
        {
          value: '0',
          label: '关闭筛选'
        },
        {
          value: '1',
          label: '开启筛选'
        }
      ],
      // 是否筛选
      screening_value: '',
      // 游戏区服
      distinguish_value: '',
      clothes_value: '',
      solid_name_value: '',
      solid_list: [
        {
          value: '0',
          label: '全部'
        },
        {
          value: '1',
          label: '已实名'
        },
        {
          value: '2',
          label: '未实名'
        }
      ],
      AccountTypeList: [
        {
          value: '1',
          label: '官方自营'
        },
        {
          value: '2',
          label: '官方代售'
        },
        {
          value: '3',
          label: '特价捡漏区'
        }
      ],

      // 列表类型
      listType: 1,
      loading: false,
      activeNames: '',
      imgOrText: '图文',
      priceList: [],
      noimg: 'https://game.ikbh.top/logo/no_img.jpg',
      daquFilterList: [],
      otherFilterList: [],
      selectedDaquFilterList: [],
      selectedPriceList: [],
      selectedOtherFilterList: [],
      currentPage2: 1,
      pageSize2: 20,
      small: false,
      pageDisabled: false,
      pageBackground: true,
      accountListTotal: 0,
      accountList: [],
      labelTypeList: [],
      selectPriceListIndex: null,
      selectDaquItem: null,
      selectedPrice: {
        CSJGMax: '',
        CSJGMin: ''
      },
      DWFilterList: [], //实名选项
      selectedDWFilterList: [], //选择的实名选项
      selectDwItem: null, //选择的实名选项
      picreOdr: 'timedesc',
      skinList: '',
      searchInput: '',
      selectGameType: '',
      gameSelects: '', //游戏类型列表
      activeIndex: 0,
      moreIndex: null, //更多筛选的标题选中项index
      moreCurrentItem: null, //更多筛选的选中大类
      id: '', //分销商id

      searchParams: {
        gameId: '',
        pageSize: 20,
        pageNum: 1,
        sortRank: '',
        priceStart: '',
        priceEnd: '',
        accountType: '',
        isOnSpecialOffer: ''
      },
      attrs: [], //全部属性
      selectAttrs: [], //需要选择的类型的属性
      heightList: [],
      sysList: [], //系统列表
      carrierList: [], //运营商列表
      areaList: [], //大区列表
      serverList: [], //服务器列表
      isShowMoreSearch: true,
      boxradioIsShow: '',
      boxradioRadioBoxLeft: '',
      boxradioRadioBoxTop: '',
      filterMapTemp: {},
      pageType: 'buy',
      flagType: false,
      radioList: [],
      checkBoxList: [],
      idToo: null,
      SJWQList: [],
      typeId: null
    });

    //
    const changeTePriceAccount = (id) => {
      state.typeId = id;
      state.searchParams.pageNum = 1;
      state.searchParams.pageSize = 20;
      if (id == 1) {
        state.searchParams.accountType = id;
        state.searchParams.isOnSpecialOffer = null;
        GetZHList();
      } else if (id == 2) {
        state.searchParams.accountType = id;
        state.searchParams.isOnSpecialOffer = null;
        GetZHList();
      } else if (id == 3) {
        state.searchParams.isOnSpecialOffer = 1;
        state.searchParams.accountType = null;
        GetZHList();
      }
    };
    // 账号类型改变
    const accountTypeChange = (item) => {
      //console.log("%c Line:559 🍺 item", "color:#2eafb0", item);
      if (state.searchParams.accountType == item.id) {
        state.searchParams.accountType = '';
      } else {
        state.searchParams.accountType = item.id;
      }
    };
    // 商品类型改变

    const goodsTypeChange = (item) => {
      //console.log("%c Line:559 🍺 item", "color:#2eafb0", item);
      if (
        item.fieldName == '' ||
        (state.searchParams.goodsTypes &&
          state.searchParams.goodsTypes.indexOf(item.fieldName) > -1)
      ) {
        state.searchParams.goodsTypes = [];
      } else {
        state.searchParams.goodsTypes = [item.fieldName];
      }
    };
    // 获取商品类型
    const getGoodsType = () => {
      api
        .getArea({
          gameId: state.searchParams.gameId,
          systemKey: 'goodstype'
        })
        .then((res) => {
          state.con_fooer = res.rows;
          // state.con_fooer
          //console.log("%c Line:551 🥑 state.con_fooer", "color:#fca650", state.con_fooer);
        });
    };

    const showMoreSearch = () => {
      // state.isShowMoreSearch = !state.isShowMoreSearch
    };
    // 获取自定义字段
    // 获取游戏属性列表
    const constgetAttrList = () => {
      state.loading = true;
      api.getGameConfig({ gameId: state.searchParams.gameId }).then((res) => {
        state.loading = false;
        if (res.code == 200) {
          state.selectAttrs = [];
          let selectAttrs = [];
          let attrs = res.rows;
          if (attrs.length) {
            attrs.forEach((r) => {
              if (
                r.fieldType == 'checkbox' ||
                r.fieldType == 'radio' ||
                r.fieldType == 'boxradio'
              ) {
                selectAttrs.push(r);
              }
              if (r.fieldType == 'checkbox') {
                state.searchParams[r.aavName + 'rander'] = [];
                state.searchParams[r.aavName] = '';
              } else if (r.fieldType == 'date') {
                state.searchParams[r.aavName + 'temp'] = [];
                state.searchParams[r.aavName] = '';
              } else if (r.fieldType == 'number') {
                state.searchParams[r.aavName + 'start'] = '';
                state.searchParams[r.aavName + 'end'] = '';
                state.searchParams[r.aavName] = '';
              } else {
                state.searchParams[r.aavName] = '';
                state.searchParams[r.aavName + 'rander'] = [];
              }
            });
          }
          state.selectAttrs = selectAttrs;
          state.attrs = JSON.parse(JSON.stringify(attrs));
        }
      });
    };

    // 查询系统列表
    // 获取系统列表
    const getSysList = () => {
      api
        .getArea({
          gameId: state.searchParams.gameId,
          systemKey: 'system'
        })
        .then((res) => {
          state.sysList = res.rows;
        });
    };

    // 获取运营商列表
    const getYysList = () => {
      api
        .getArea({
          gameId: state.searchParams.gameId,
          systemKey: 'carrier'
        })
        .then((res) => {
          state.carrierList = res.rows;
        });
    };
    // 运营商改变  获取大区列表
    const getAreaList = (e) => {
      let obj = {
        gameId: state.searchParams.gameId,
        systemKey: 'group'
      };
      state.searchParams.groupId = '';
      state.searchParams.serverId = '';

      // 运营商改变 找出选择的运营商的对象
      if (e) {
        state.carrierList.forEach((i) => {
          if (i.id == e) {
            obj.systemKey = i.systemKey + 'group';
          }
        });
      }
      api.getArea(obj).then((res) => {
        state.areaList = res.rows;
      });
    };
    // 大区改变  获取服务器列表
    const getServiceList = (e, id) => {
      state.searchParams.serverId = '';

      if (e) {
        api
          .getServeList({
            parentId: id
          })
          .then((res) => {
            state.serverList = res.rows;
          });
      } else {
        state.serverList = [];
      }
    };
    //端游 直接获取大区
    const getAreaList2 = () => {
      let obj = {
        gameId: state.searchParams.gameId,
        systemKey: 'group'
      };
      api.getArea(obj).then((res) => {
        state.areaList = res.rows;
      });
    };
    //点击大区选择项
    const areaClick = (e, key, id) => {
      //console.log("1111111111", e)
      if (key == 'carrierId') {
        getAreaList(e);
      }
      if (key == 'groupId') {
        getServiceList(e, id);
      }
    };

    // 单选项改变
    const radioClick = (item, custom) => {
      if (state.searchParams[custom.aavName] == item.id) {
        state.searchParams[custom.aavName] = '';
      } else {
        state.searchParams[custom.aavName] = item.id;
      }
    };
    // 系统、大区、运营商等单选
    const radioClick2 = (item, key, e) => {
      //console.log(item.id)
      //console.log(key)
      //console.log(state.searchParams[key])
      //console.log('--------------------------')
      if (state.searchParams[key] == item.id) {
        state.searchParams[key] = '';
      } else {
        state.searchParams[key] = item.id;
      }
      if (key == 'groupId') {
        areaClick(e, key, item.id);
      }
    };
    // 多选项改变
    const checkboxChange = (e, key) => {
      if (e && e.length) {
        state.searchParams[key] = e.join(',');
      } else {
        state.searchParams[key] = '';
      }
    };
    // 获取鼠标位置
    const setOffset = (event) => {
      let e2 = event || window.event;
      state.boxradioRadioBoxLeft = e2.clientX + 'px';
      state.boxradioRadioBoxTop = e2.clientY + 'px';
    };
    // 点击多选框单选的多选框点击事件
    const boxradioCheckClick = (e, custom, checkItem, i) => {
      if (state.boxradioIsShow == checkItem.id) {
        state.boxradioIsShow = '';
      } else {
        state.boxradioIsShow = checkItem.id;
      }

      // 组装筛选数据 filterMapTemp
      // if (state.filterMapTemp[checkItem.id]) {
      //   //
      //   delete state.filterMapTemp[checkItem.id]
      // } else {
      //   state.filterMapTemp[checkItem.id] = checkItem.fieldName.split('(')[0]
      // }

      if (e) {
        state.filterMapTemp[checkItem.id] = checkItem.fieldName.split('(')[0];
      } else {
        if (state.filterMapTemp[checkItem.id]) {
          //
          delete state.filterMapTemp[checkItem.id];
        }
      }

      // checkItem.ischeck = false
      checkItem.fieldName = checkItem.fieldName.split('(')[0];
      setValue(custom);
      internalInstance.ctx.$forceUpdate();
    };
    // 点击多选单选框中的单选框事件
    const boxradioChange = (e, checkItem, custom, i) => {
      console.log('333111', e, checkItem, custom);

      // 组装筛选数据 filterMapTemp
      if (state.filterMapTemp[checkItem.id]) {
        //
        delete state.filterMapTemp[checkItem.id];
      }
      // 找出比当前选择项高级的所有单选项
      let curInd;
      let arr = [];
      checkItem.childGameAttributes.forEach((radioItem, radioIndex) => {
        if (radioItem.id == e) {
          curInd = radioIndex;
        }
        if ((curInd || curInd === 0) && curInd <= radioIndex) {
          arr.push(radioItem.fieldName);
        }
      });
      console.log('arr', arr);
      state.filterMapTemp[checkItem.id] = arr.join('===');

      state.boxradioIsShow = '';
      checkItem.ischeck = true;
      checkItem.childGameAttributes.forEach((r) => {
        if (r.id == e) {
          checkItem.fieldName = checkItem.fieldName.split('(')[0] + '(' + r.fieldName + ')';
        }
      });
      setValue(custom);
    };
    // 原神多选框赋值
    const setValue = (custom) => {
      // 赋值
      let arr = [];
      custom.childAttributes.forEach((item, ind) => {
        if (item.ischeck) {
          // 多选框选中 取子级单选选中的id
          arr.push(state.searchParams[custom.aavName + ind]);
        }
      });
      if (arr.length) {
        state.searchParams[custom.aavName] = arr.join(',');
        state.searchParams[custom.aavName + 'rander'] = arr;
      } else {
        state.searchParams[custom.aavName] = '';
        state.searchParams[custom.aavName + 'rander'] = [];
      }
    };

    // 获取游戏类型
    // 获取游戏类型
    const GetGameType = async () => {
      let res = await api.GetGameType();
      state.gameSelects = res.data;
      // //console.log("%c Line:257 🥖 state.gameSelects", "color:#ea7e5c", state.gameSelects);
      if (state.gameSelects && state.gameSelects.length) {
        state.selectGameType = state.gameSelects[0];
        state.searchParams.gameId = state.gameSelects[0].id;
        GetZHList();
        constgetAttrList();
        if (state.selectGameType.type === 0) {
          // 手游
          // 获取系统列表和运营商列表
          getSysList();
          getYysList();
        } else {
          // 端游
          // 获取大区列表
          getAreaList2();
        }
      }
    };
    // 更多筛选
    // 展开关闭
    const moreClick = (item) => {
      if (state.heightList[item.id] == 'auto') {
        state.heightList[item.id] = '30px';
      } else {
        state.heightList[item.id] = 'auto';
      }
    };
    // 选择游戏
    const gameClick = (item, index) => {
      state.activeIndex = index;
      state.selectGameType = item;
      state.pageSize2 = 10;
      state.currentPage2 = 1;
      state.searchParams.gameId = state.selectGameType.id;
      resetSelected();
      constgetAttrList();
      if (state.selectGameType.type === 0) {
        // 手游
        // 获取系统列表和运营商列表
        getSysList();
        getYysList();
      } else {
        // 端游
        // 获取大区列表
        getAreaList2();
      }
    };
    // 获取账号列表
    const GetZHList = async () => {
      state.loading = true;
      // maincontent.scrollTop = 0
      // state.searchParams.retailId = state.id
      state.searchParams.saleState = 1;
      if (state.pageType == 'hire') {
        state.searchParams.hireState = 2;
      } else {
        state.searchParams.hireState = null;
      }
      // saleState
      let res = await api.GetZHList(state.searchParams);
      //console.log("%c Line:295 🥑 res", "color:#3f7cff", res);
      state.loading = false;
      state.accountList = res.rows;
      state.accountListTotal = res.total;
      //console.log("res.total", res.total)
    };
    // 总选项
    state.allSelectedList = computed(() => {
      return [
        ...state.selectedPriceList,
        ...state.selectedOtherFilterList,
        ...state.selectedDaquFilterList,
        ...state.selectedDWFilterList
      ];
    });

    // 选中更多筛选的标题
    const moreItemClick = (item, index) => {
      state.moreIndex = index;
      state.moreCurrentItem = item;
    };

    // 调整价格和上下架排序
    const priceFilter = (value) => {
      state.searchParams.sortRank = value;
      state.picreOdr = value;
      state.pageSize2 = 20;
      state.currentPage2 = 1;
      GetZHList();
    };

    // 查看详情
    const seeDetail = (item) => {
      const url = router.resolve({
        path: `/pages/detail/index`,
        query: { id: item.id, pageType: state.pageType }
      });
      window.open(url.href, '_blank');
    };

    // 分页操作
    const handleSizeChange = (size) => {
      //console.log('size', size);
      state.pageSize2 = size;
      GetZHList();
    };

    const handleCurrentChange = (value) => {
      //console.log('value',value);
      state.currentPage2 = value;
      GetZHList();
    };

    const resetSelected = () => {
      state.filterMapTemp = {};
      state.typeId = null;
      state.searchParams = {
        gameId: '',
        pageSize: 20,
        pageNum: 1,
        sortRank: '',
        priceStart: '',
        priceEnd: '',
        accountType: '',
        isOnSpecialOffer: ''
      };
      state.idToo = null;
      state.flagType = false;
      state.searchInput = '';
      if (state.attrs.length) {
        state.attrs.forEach((r) => {
          if (r.fieldType == 'checkbox') {
            state.searchParams[r.aavName + 'rander'] = [];
            state.searchParams[r.aavName] = '';
          }
          if (r.fieldType == 'number') {
            state.searchParams[r.aavName + 'start'] = '';
            state.searchParams[r.aavName + 'end'] = '';
            state.searchParams[r.aavName] = '';
          }
          if (r.fieldType == 'boxradio') {
            state.searchParams[r.aavName + 'rander'] = [];
            state.searchParams[r.aavName] = '';
            if (r.childAttributes && r.childAttributes.length) {
              r.childAttributes.forEach((item, ind) => {
                if (item.ischeck) {
                  item.ischeck = false;
                  item.fieldName = item.fieldName.split('(')[0];
                }
              });
            }
          }
        });
      }
      state.searchParams.gameId = state.selectGameType.id;
      console.log('state.searchParams', state.searchParams);

      state.pageSize2 = 10;
      state.currentPage2 = 1;
      GetZHList();
    };

    // 确认筛选
    const confirmSelected = () => {
      state.pageSize2 = 10;
      state.currentPage2 = 1;
      // 组装原神类多选项筛选 boxRadioValues
      let boxRadioValues = [];
      for (let key in state.filterMapTemp) {
        //console.log(state.filterMapTemp[key]) // foo, bar
        if (state.filterMapTemp[key].trim()) {
          boxRadioValues.push(state.filterMapTemp[key]);
        }
      }
      state.searchParams.boxRadioValues = boxRadioValues;
      state.searchParams.keyWord = state.searchInput;

      // 将自定义字段的多选项转成字符串
      if (state.attrs.length) {
        state.attrs.forEach((r) => {
          // 将数字类型的字段拼接成 xx===xx 的形式
          if (r.fieldType == 'number') {
            let sta = state.searchParams[r.aavName + 'start'] || '';
            let end = state.searchParams[r.aavName + 'end'] || '';
            if (sta && end) {
              state.searchParams[r.aavName] = sta + '===' + end;
            } else {
              state.searchParams[r.aavName] = '';
            }
          }
        });
      }
      // //console.log(state.searchParams)
      //console.log("%c Line:531 🍻 state.searchParams", "color:#3f7cff", state.searchParams);
      GetZHList();
    };
    // 监听路由变化
    watch(
      () => router.currentRoute.value.query,
      (newValue, oldValue) => {
        // //console.log('router.currentRoute.value', router.currentRoute.value);
        if (router.currentRoute.value.path == '/index/goodList') {
          let obj = router.currentRoute.value.query;
          state.selectGameType = {
            type: obj.type,
            id: obj.id,
            name: obj.name
          };
          state.searchParams = {
            gameId: '',
            pageSize: 20,
            pageNum: 1,
            sortRank: '',
            priceStart: '',
            priceEnd: '',
            accountType: '',
            isOnSpecialOffer: ''
          };
          state.pageType = obj.pageType;
          // if (obj.pageType == 'hire') {
          //   state.searchParams.hireState = 2
          // } else {
          //   state.searchParams.hireState = null
          // }
          state.searchParams.gameId = obj.id;
          getGoodsType();
          GetZHList();
          constgetAttrList();
          if (state.selectGameType.type == 0) {
            // 手游
            // 获取系统列表和运营商列表
            getSysList();
            getYysList();
          } else {
            // 端游
            // 获取大区列表
            getAreaList2();
          }
        }
      },
      { deep: true, immediate: true }
    );

    // 获取游戏列表
    const setlistType = (type) => {
      state.listType = type;
    };

    onMounted(() => {
      setlistType(1);
      //console.log("%c Line:310 🥓 onMounted", "color:#f5ce50");
      //console.log('$router:', router.currentRoute.value.query);
      let obj = router.currentRoute.value.query;
      state.selectGameType = {
        type: obj.type,
        id: obj.id,
        name: obj.name
      };
      state.pageType = obj.pageType;
      // if (obj.pageType == 'hire') {
      //   state.searchParams.hireState = 2
      // } else {
      //   state.searchParams.hireState = null
      // }
      state.searchParams.gameId = obj.id;
      getGoodsType();
      GetZHList();
      constgetAttrList();
      if (state.selectGameType.type == 0) {
        // 手游
        // 获取系统列表和运营商列表
        getSysList();
        getYysList();
      } else {
        // 端游
        // 获取大区列表
        getAreaList2();
      }
    });

    const getOneCostom = (item) => {
      console.log('item', item);

      if (item.id == state.idToo) {
        console.log('222', state.idToo, item.id);
        state.flagType = !state.flagType;
      } else {
        state.flagType = true;
        console.log('333', state.idToo, item.id);
      }
      state.idToo = item.id;

      if (item.fieldType == 'number') {
        state.radioList = [];
        state.checkBoxList = [];
        state.radioList.push(item);
      } else {
        state.checkBoxList = [];
        state.radioList = [];
        state.checkBoxList.push(item);
      }
    };

    return {
      ...toRefs(state),
      priceFilter,
      seeDetail,
      handleSizeChange,
      handleCurrentChange,
      GetZHList,
      confirmSelected,
      resetSelected,
      gameClick,
      moreItemClick,
      radioClick,
      radioClick2,
      checkboxChange,
      moreClick,
      getAreaList,
      getServiceList,
      getAreaList2,
      areaClick,
      showMoreSearch,
      boxradioCheckClick,
      boxradioChange,
      setOffset,
      setlistType,
      accountTypeChange,
      getGoodsType,
      goodsTypeChange,
      getOneCostom,
      changeTePriceAccount
    };
  }
};
</script>

<style scoped lang="less">
.container {
  background-color: #1c1632;
  .top_nav {
    .search-wrapper {
      .zhanghao_nav {
        width: 1200px;
        margin: 0 auto;
        background-color: #1c1632;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 10px;
        box-sizing: border-box;

        .h4 {
          font-weight: bold;
          margin-right: 30px;
        }

        .ul {
          display: flex;

          .li {
            width: 100px;
            height: 28px;
            border: 1px solid #e2e2e2;
            margin-right: 25px;
            border-radius: 5px;
            font-size: 13px;
            color: #000;
            line-height: 28px;
            text-align: center;
            cursor: pointer;
          }
        }

        .t_fooer,
        .con_fooer {
          margin-bottom: 15px;

          .li.selected-items {
            border: 1px solid #fff;
            box-shadow: 0 0 4px #ff5e25;
            color: red;
          }
        }

        .bomm_fooer {
          .screening {
            width: 120px;
          }

          .Game_zone_server {
            margin-left: 100px;

            span {
              margin-right: 20px;
            }

            .distinguish {
              margin-right: 10px;
            }
          }

          .solid_name {
            margin-left: 70px;

            span {
              margin-right: 20px;
            }
          }
        }
      }

      .filter-wrapper {
        background-color: #1c1632;
        border: 1px solid #38324b;
        .title {
          font-size: 16px;
          font-weight: bold;
          margin: 0 0 10px 0px;
          display: flex;
          align-items: center;
          justify-content: space-between;
          padding-right: 10px;

          .con {
            .line {
              width: 3px;
              height: 16px;
              background: #ff8e00;
              margin-right: 10px;
              color: #fff;
            }
          }

          .confirm-btn {
            margin-left: 20px;
          }
        }

        .con {
          .li {
            display: flex;
            align-items: center;
            margin-bottom: 10px;

            .le {
              height: 30px;
            }
          }
        }

        .con_xitong {
          .li {
            display: flex;
            align-items: center;
            margin-bottom: 10px;

            .le {
              height: 30px;
            }
          }
        }

        .con_xitong {
          padding: 0 45px;
          display: flex;
          justify-content: space-between;

          .li {
            span {
              margin-right: 10px;
              font-size: 14px;
              font-weight: bold;
              line-height: 36px;
            }
          }
        }
      }
    }
  }
}

.dis_flex_ai_c {
  display: flex;
  align-items: center;
}

.no-inherit {
  transform: rotate(90deg);
}

.no-inherit.up {
  transform: rotate(-90deg);
}

.container {
  .location {
    display: flex;
    padding: 26px 0;
  }

  .banner {
    // width: 1000px;
    margin: 0 auto;

    img {
      width: 100%;
      height: 150px;
      margin: 10px 0;
      border-radius: 10px;
    }
  }

  .search-wrapper {
    // width: 1200px;
    // margin: 0 auto;
    // margin-top: 15px;
    overflow: hidden;
    box-sizing: border-box;
    // box-shadow: 0px 4px 8px 0px #0000000c;
    // padding: 20px;
    // padding-top: 10px;
    // background-color: #fff;
    border-radius: 5px;
    background-repeat: no-repeat;
    background-size: 100% 100%;

    .filter-wrapper {
      // background-color: #fff;
      // padding: 0 20px;
      // padding-top: 20px;
      background-color: #fff;
      padding: 15px;
      border-radius: 10px;
      box-sizing: border-box;

      .zhanghao_nav {
        width: 1200px;
        margin: 0 auto;
      }
    }

    .price-filter.active {
      height: auto;
    }

    .price-filter {
      display: flex;
      // align-items: center;
      flex-wrap: nowrap;
      margin-bottom: 8px;
      // border-bottom: 1px dashed #bbb;
      // height: 100px;
      overflow: hidden;

      .selected-titile {
        width: 90px;
        // height: 100px;
        color: #fff;
        font-size: 14px;
        margin-right: 20px;
        font-weight: 600;
        line-height: 30px;
        // width: px;
        flex-shrink: 0;
        text-align: left;
      }

      // .selected-titile:nth-child(5){
      //   padding-left: 20px;
      // }

      .filter-item-box {
        width: 1000px;
        display: flex;
        flex-wrap: wrap;

        // padding-bottom: 10px;
        :deep(.el-checkbox) {
          // font-size: 24px;
          width: 175px;
          height: 30px;
          margin-bottom: 10px;
          line-height: 50px;
          text-align: center;
          background-color: #eeeeee;
          //  display:block;
          overflow: hidden;
          word-break: keep-all;
          white-space: nowrap;
          text-overflow: ellipsis;
        }
      }

      .item {
        width: 185px;
        height: 30px;
        line-height: 50px;
        text-align: center;
        background-color: #1c1632;
        display: block;
        overflow: hidden;
        word-break: keep-all;
        white-space: nowrap;
        text-overflow: ellipsis;
        color: #fff;

        p {
          margin: 0;
        }

        line-height: 26px;
        display: flex;
        align-items: center;
        flex-wrap: nowrap;
        margin-right: 10px;
        margin-bottom: 10px;
        border-radius: 5px;
        color: #fff;
        font-size: 14px;
        padding: 0px 12px;
        box-sizing: border-box;
        cursor: pointer;
      }

      .selected-items {
        background: #b42c1d;
        color: #fff;
      }
    }
    .custom-attr-item {
      // border-bottom: 1px solid #38324b;
      padding: 6px 0;
    }

    .radio-cont {
      flex-wrap: wrap;
    }

    .qufu.price-filter {
      position: relative;
      // margin-left: 60px;
      text-align: left;

      .selected-titile {
        width: 100px;
        padding-right: 65px;
        padding-left: 15px;
      }
      .selected-titile:nth-child(1) {
        padding-left: 0;
      }
      .filter-item-box {
        width: auto;
        margin-right: 20px;
        padding-bottom: 10px;
      }

      .clearBtn {
        position: absolute;
        top: 0;
        right: 0;
        display: flex;
        align-items: center;
        cursor: pointer;

        span {
          font-size: 14px;
          color: #777777;
          line-height: 20px;
        }

        .icon {
          width: 13px;
          height: 14px;
          background: url('../../assets/yudu/goodsList/del.png') 100% 100% no-repeat;
          background-size: 100% 100%;
          margin-right: 5px;
        }
      }
    }
  }

  .bgfff {
    width: 1200px;
    margin: 0 auto;
    padding: 10px;
    border-radius: 10px;
    background-color: #fff;
    margin-bottom: -10px;
    margin-top: 10px;
    padding-left: 20px;

    .btn-wrapper {
      margin: 0;

      .title {
        font-size: 16px;
        font-weight: bold;
        margin: 0 0 10px 0px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding-right: 10px;

        .con {
          .line {
            width: 3px;
            height: 16px;
            background: #ff8e00;
            margin-right: 10px;
          }
        }

        .confirm-btn {
          margin-left: 20px;
        }
      }

      .con {
        .li {
          display: flex;
          align-items: center;
          margin-bottom: 10px;

          .le {
            height: 30px;
          }
        }
      }

      .con_xitong {
        .li {
          display: flex;
          align-items: center;
          margin-bottom: 10px;

          .le {
            height: 30px;
          }
        }
      }

      .con_xitong {
        padding: 0 45px;
        display: flex;
        justify-content: space-between;

        .li {
          width: 242px;
          height: 36px;

          span {
            margin-right: 10px;
            font-size: 14px;
            font-weight: bold;
            line-height: 36px;
          }
        }
      }
    }
  }

  .zhankai {
    // width: 103px;
    // height: 32px;
    // background: rgba(60, 128, 255, 0.1);
    // border-radius: 4px;
    // margin: 0 auto;
    // text-align: center;
    margin-bottom: 10px;
    // border: 1px solid #e2e2e2;
    color: #333 !important;
  }
}

.zhankai /deep/ .el-button--text {
  color: #333;
}

// .qujian-box /deep/ input {
//   background-color: #eee;
// }

.home-icon {
  margin-right: 5px;
}

// .reset-icon {
//   background: #f2f3f5;
//   color: #4e5969;
// }

.btn-wrapper {
  margin-top: 20px;

  .title {
    font-size: 14px;
    font-weight: bold;
    margin-bottom: 10px;
  }

  .input-with-select {
    width: 400px;
    margin-right: 10px;
  }
}

.qujian-box {
  flex-wrap: wrap;
}

.qujian-item {
  margin-right: 0px;
  margin-bottom: 10px;

  .selected-titile {
    width: 80px;
    text-align: left;
    margin-right: 10px;
    font-size: 14px;
    font-weight: bold;
    line-height: 36px;
    color: #fff;
    // padding-left: 20px;
  }
  input {
    background-color: #eee;
  }
}

.flex_sousuo {
  .filter-item-box {
    width: auto;

    .input-with-select {
      width: 215px;
    }
  }
}

// 综合排序
.good-list {
  width: 1200px;
  margin-top: 20px;
  padding-top: 10px;
  box-sizing: border-box;
  min-height: 500px;

  // 综合排序
  .header-filter {
    height: 30px;
    display: flex;
    justify-content: space-between;
    background-color: #1c1632;
    padding: 10px;

    .left-filter {
      display: flex;
      color: #4e5969;
      font-size: 14px;
      align-items: center;

      .sort-filter {
        margin-right: 20px;
        color: #f23e59;
      }

      .price-filter,
      .updown-filter {
        display: flex;
        align-items: center;
        margin-right: 20px;

        .up-down-icon {
          display: flex;
          flex-direction: column;
          justify-content: center;
          align-items: center;

          i {
            cursor: pointer;
            margin: -2px 0;
          }

          .el-icon {
            margin: -4px 0;
          }
        }
      }
    }

    .Price_range {
      display: flex;
      align-items: center;
      color: #4e5969;

      input {
        width: 80px;
        height: 30px;
        border: 1px solid #b7b7b7;
        border-radius: 5px;
        padding: 0 10px;
        margin-right: 10px;
        border: none;
        background-color: #eee;
        outline: none;
      }

      p {
        margin-right: 10px;
      }
    }

    .search_for {
      display: flex;
      align-items: center;
      color: #4e5969;

      input {
        width: 150px;
        height: 30px;
        border: 1px solid #b7b7b7;
        border-radius: 5px;
        padding: 0 10px;
        margin-right: 10px;
        border: none;
        background-color: #eee;
        outline: none;
      }
    }

    .list_type {
      display: flex;
      align-items: center;
      color: #4e5969;
      font-size: 14px;
      border: 1px solid #b7b7b7;
      padding: 5px 0;
      border-radius: 5px;

      span {
        width: 24px;
        height: 24px;
        cursor: pointer;
        color: #4e5969;
        font-size: 14px;
        display: flex;
        align-items: center;
        justify-content: space-around;
        padding: 2px;
        border-radius: 2px;
      }

      .on {
        color: #fff;
        font-weight: bold;
        background: #b42c1d;
      }
    }
  }

  .goods-box {
    display: flex;
    flex-wrap: wrap;
    justify-content: flex-start;
    margin-bottom: 30px;

    .list-wrapper {
      width: 21.9%;
      display: flex;
      position: relative;
      background: #1c1632;
      border: 1px solid #38324b;
      padding: 10px;
      margin-top: 15px;
      margin-right: 15px;
      box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.04);
      border-radius: 5px;
      flex-direction: column;
      align-content: space-between;

      .ziying {
        position: absolute;
        top: 0;
        left: 0;
      }

      .tejia {
        position: absolute;
        top: 0;
        right: 0;
      }

      .sign {
        width: 56px;
        height: 21px;
        line-height: 27px;
        text-align: center;
        background-image: url('../../assets/yudu/home/signbg.png');
        background-size: 100% 100%;
        position: absolute;
        left: 3.5%;
        top: 46%;
        right: 0;
        color: #fff;
        font-size: 12px;
      }
      .img {
        flex-shrink: 0;
        border-radius: 5px;
        // margin-right: 10px;
        overflow: hidden;

        // img {
        //   width: 264px;
        //   height: 165px;
        // }

        img {
          width: 264px;
          height: 165px;
          transition: transform 0.3s ease; /* 平滑变换 */
        }
      }

      .img:hover img {
        transform: scale(1.2); /* 鼠标悬停时放大1.2倍 */
      }

      .good-content {
        flex: 42;
        // padding-right: 10px;
        height: 60px;
        .name {
          width: 100%;
          font-size: 14px;
          line-height: 20px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 3; // 超出多少行
          -webkit-box-orient: vertical;
          cursor: pointer;
          font-weight: bold;
          color: #fff;
        }

        .game-type {
          color: #777;
          font-size: 12px;
          margin: 5px 0;
          margin-right: 20px;
        }

        .liulan-box {
          align-items: center;

          .icon {
            width: 15px;
            height: 11px;
            background-image: url('../../assets/yudu/detail/liulan.png');
            background-size: 100% 100%;
            margin-right: 5px;
          }
        }
      }

      .name-bot {
        display: flex;
        align-items: center;

        .baopei {
          padding: 0 8px;
          height: 18px;
          border-radius: 3px;
          border: 1px solid #5468ff;
          font-size: 12px;
          color: #5468ff;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 7px;
        }

        .game-type {
          // padding-top: 21px;
          font-size: 12px;
          color: #d0cdcd;
        }
      }

      .right {
        display: flex;
        align-items: center;
        justify-content: space-between;

        .price {
          font-size: 15px;
          color: #f23e59;
          font-weight: 700;
        }

        .game-type {
          font-size: 13px;
          color: #d0cdcd;
        }
      }
    }

    .price-link {
      display: flex;
      // flex-direction: column;
      justify-content: space-between;
      align-items: center;
      margin-left: 20px;

      .price {
        color: #ff3c3c;

        .price-num {
          font-size: 24px;
          line-height: 0px;
          font-weight: bold;
        }
      }

      // .detail-btn {
      //   color: #fff;
      //   background-color: #e23530;
      //   border-color: #e23530;
      //   // margin-top: 50px;
      // }
    }

    .sign-box {
      display: flex;
      flex-direction: column;
      justify-content: center;

      .sign {
        width: 96px;
        height: 27px;
        align-items: center;
        justify-content: center;
        background: linear-gradient(90deg, #3a4777 0%, #1b2242 100%);
        border-radius: 14px;
        color: #ffebc2;
        margin: 5px;

        .icon {
          width: 13px;
          height: 14px;
          background-image: url('../../assets/yudu/detail/baopeishangjia.png');
          background-size: 100% 100%;
        }
      }

      .kemai {
        .icon {
          background-image: url('../../assets/yudu/detail/baopei.png');
        }
      }
    }
  }

  //
  .goods-box-one {
    display: block;
    margin: 10px 0 30px 0;

    .list-wrapper {
      position: relative;

      .ziying {
        position: absolute;
        top: 0;
        left: 0;
      }

      .tejia {
        position: absolute;
        top: 0;
        right: 0;
      }
    }

    > div {
      display: flex;
      margin-bottom: 20px;
      background-color: #1c1632;
      border: 1px solid #38324b;
      padding: 15px;
      border-radius: 10px;

      .img {
        flex-shrink: 0;
        border-radius: 5px;
        margin-right: 10px;
        overflow: hidden;

        // img {
        //   width: 264px;
        //   height: 165px;
        // }
        img {
          width: 264px;
          height: 165px;
          transition: transform 0.3s ease; /* 平滑变换 */
        }
      }

      .img:hover img {
        transform: scale(1.2); /* 鼠标悬停时放大1.2倍 */
      }

      .con_tent {
        display: flex;
        height: 165px;
        flex-direction: column;
        justify-content: space-between;

        .good-content {
          flex: 42;
          padding-right: 10px;

          .name {
            width: 100%;
            font-size: 14px;
            line-height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 4; // 超出多少行
            -webkit-box-orient: vertical;
            cursor: pointer;
            font-weight: bold;
            color: #fff;
          }

          .game-type {
            color: #777;
            font-size: 12px;
            margin: 5px 0;
            margin-right: 20px;
          }

          .liulan-box {
            align-items: center;

            .icon {
              width: 15px;
              height: 11px;
              background-image: url('../../assets/yudu/detail/liulan.png');
              background-size: 100% 100%;
              margin-right: 5px;
            }
          }
        }

        .name-bot {
          display: flex;
          align-items: center;
          height: 29px;

          .baopei {
            padding: 0 8px;
            height: 18px;
            border-radius: 3px;
            border: 1px solid #5468ff;
            font-size: 12px;
            color: #5468ff;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 7px;
          }

          .game-type {
            font-size: 12px;
            color: #d0cdcd;
          }
        }

        .right {
          display: flex;
          align-items: center;
          justify-content: space-between;

          .price {
            font-size: 15px;
            color: #f23e59;
            font-weight: 700;
            // width: 266px;
          }

          .game-type {
            font-size: 13px;
            color: #d0cdcd;
            // margin-left: 500px;
          }
        }
      }
    }
  }

  .goods-box-two {
    display: block;
    margin: 10px 0 30px 0;

    .list-wrapper {
      position: relative;

      .ziying {
        position: absolute;
        top: 0;
        left: 0;
      }

      .tejia {
        position: absolute;
        top: 0;
        right: 0;
      }
    }

    > div {
      display: flex;
      margin-bottom: 20px;
      background-color: #1c1632;
      border: 1px solid #38324b;
      padding: 10px;
      border-radius: 10px;

      .img {
        flex-shrink: 0;
        border-radius: 5px;
        margin-right: 10px;
        overflow: hidden;

        img {
          display: none;
          width: 264px;
          height: 165px;
        }
      }

      .con_tent {
        display: flex;
        width: 100%;
        height: 165px;
        flex-direction: column;
        justify-content: space-between;

        .good-content {
          flex: 42;
          padding-right: 10px;

          .name {
            width: 100%;
            font-size: 14px;
            line-height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 4; // 超出多少行
            -webkit-box-orient: vertical;
            cursor: pointer;
            font-weight: bold;
            color: #fff;
          }

          .game-type {
            color: #777;
            font-size: 19px;
            // margin: 5px 0;
            margin-right: 20px;
          }

          .liulan-box {
            align-items: center;

            .icon {
              width: 15px;
              height: 11px;
              background-image: url('../../assets/yudu/detail/liulan.png');
              background-size: 100% 100%;
              margin-right: 5px;
            }
          }
        }

        .name-bot {
          display: flex;
          align-items: center;
          height: 29px;

          .baopei {
            padding: 0 8px;
            height: 18px;
            border-radius: 3px;
            border: 1px solid #5468ff;
            font-size: 12px;
            color: #5468ff;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 7px;
          }

          .game-type {
            font-size: 12px;
            color: #d0cdcd;
          }
        }

        .right {
          display: flex;
          align-items: center;
          justify-content: space-between;

          .price {
            font-size: 15px;
            color: #f23e59;
            font-weight: 700;
          }

          .game-type {
            font-size: 13px;
            color: #d0cdcd;
          }
        }
      }
    }
  }
}

:deep .el-collapse-item__header {
  font-size: 16px;
  font-weight: bold !important;
}

.el-pagination {
  margin: 0 auto;
  margin-bottom: 20px;
  float: none !important;
}

.line {
  height: 1px;
  background-color: #e3e3e3;
  margin: 10px 0;
}

.el-input {
  width: 200px;
}

.morebtn {
  // padding-top: 3px;
}

.boxradioRadioBox {
  background: #fff;
  box-shadow: 0 0 7px #e3e3e3;
  padding: 10px;
  position: fixed;
  z-index: 99;
  display: none;
  border-radius: 8px;
}

.boxradioRadioBox.show {
  display: block;
}

.el-radio {
  display: block;
  height: 26px;
  text-align: left;
}

.ri_but {
  margin-left: 10px;
}

.icon_box_all {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  // margin-top: 12px;
  .icon_new {
    width: 81px;
    height: 26px;
    margin-right: 5px;
    .img_box_new {
      width: 100%;
      height: 100%;
    }
  }

  .game-type {
    // padding-top: 21px;
    font-size: 12px;
    color: #d0cdcd;
    margin-left: 12px;
    // margin-top: -12px;
  }
}

.icon_box_all_one {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  // margin-top: 12px;
  .icon_new {
    width: 81px;
    height: 26px;
    margin-right: 5px;
    .img_box_new {
      width: 100%;
      height: 100%;
    }
  }

  .game-type {
    // padding-top: 21px;
    font-size: 12px;
    color: #d0cdcd;
    margin-left: 12px;
    // margin-top: -12px;
  }
}

.morebtn .el-button {
  border: 1px solid #fff;
  color: #fff; /* 鼠标悬停时的文本颜色 */
  background-color: #1c1632; /* 鼠标悬停时的背景色 */
}

.morebtn .el-button:hover {
  border: 1px solid #b42c1d;
  color: #b42c1d; /* 鼠标悬停时的文本颜色 */
}

.no-inherit:hover {
  background-color: #fff; /* 鼠标悬停时的背景色 */
  color: #b42c1d; /* 鼠标悬停时的文本颜色 */
}

:deep(.location .el-breadcrumb__inner) {
  color: #fff;
}

:deep(.location .el-breadcrumb__inner.is-link) {
  color: #fff !important;
}
.pagination-block {
  margin-left: 18%;
  :deep(.el-input__inner) {
    color: #fff;
  }

  :deep(.el-input__wrapper) {
    background-color: #211c38;
  }

  :deep(.el-pagination__total.is-first) {
    color: #fff;
  }
  :deep(.el-pagination__goto) {
    color: #fff;
  }
  :deep(.el-pagination__classifier) {
    color: #fff;
  }
}
</style>
<style lang="less">
.my-input {
  --el-input-border-color: #302d45;
  --el-input-focus-border: #d2d2d2;
  --el-input-focus-border-color: #d2d2d2;
}
// 区服样式
.filter-item-box {
  .el-input__inner {
    // background: #F5F5F5;
    height: 32px;
    // border: none;
  }

  // .el-select .el-input__inner {
  //   background: #fff;
  //   border: 1px solid #f3f3f3;
  // }
}

.search-wrapper {
  .el-checkbox,
  .el-checkbox-group {
    line-height: 28px !important;
    // text-align: center !important;
  }

  .el-checkbox {
    height: 28px !important;
  }

  .el-checkbox {
    // margin-right: 10px;
    margin-left: 20px;
    // padding-right: 10px;
  }

  .el-checkbox:nth-child(1n) {
    margin-left: 0;
  }

  .el-checkbox__input {
    display: none;
  }

  .el-checkbox.is-checked {
    background: rgba(60, 128, 255, 0.1);
    border-radius: 5px;
    color: #ff8e00;
  }
}

.el-popper.is-customized {
  /* Set padding to ensure the height is 32px */
  padding: 6px 12px;
  background: linear-gradient(90deg, rgb(159, 229, 151), rgb(204, 229, 129));
}

.el-popper.is-customized .el-popper__arrow::before {
  background: linear-gradient(45deg, #b2e68d, #bce689);
  right: 0;
}

.el-input .el-input__inner {
  background-color: #1c1632;
  border: none;
}

.my-input ::placeholder {
  color: #606266;
  font-size: 13px;
}

.my-input .el-input__wrapper {
  background-color: #1c1632;
}

.my-input .el-input__inner {
  color: #fff;
}

/deep/ .el-popper.is-customized {
  padding: 6px 12px;
  background-color: #b42c1d;
}

/deep/ .el-checkbox.is-checked {
  color: #b42c1d !important;
}

.el-checkbox__label {
  padding: 0 12px;
  color: #fff !important;
}
.el-checkbox {
  background-color: #1c1632 !important;
  color: #fff;
}
.el-checkbox__input.is-checked + .el-checkbox__label {
  color: #fff !important;
  background-color: #b42c1d;
  // width: 160px;
  min-width: 161px;
  text-align: left;
  height: 30px;
  line-height: 30px;
  border-radius: 5px;
}

.newFiledName {
  width: 151px;
  height: 36px;
  line-height: 36px;
  text-align: center;
  color: #fff;
  border: 1px solid #dddddd;
  margin-right: 18px;
  margin-bottom: 18px;
  border-radius: 5px;
  cursor: pointer;
  position: relative;
  justify-content: center;
}

// .newFiledName:nth-child(6n) {
//   margin-right: 0;
// }

.newFiledName:hover {
  border: 1px solid #b42c1d;
  color: #b42c1d;
  .xia_icon {
    color: #b42c1d;
  }
}

.tanchuzizhi {
  width: 1071px;
  min-height: 166px;
  background-color: #1c1632;
  margin-left: -110px;
  border-radius: 10px;
  border: 2px solid #38324b;
  padding: 12px 0;
  padding-left: 88px;
}

.tanchuactive {
  border: 1px solid #b42c1d;
  color: #b42c1d;
  .xia_icon {
    color: #b42c1d;
  }
}

.xia_iconActive {
  transform: rotate(180deg);
  color: #b42c1d;
}
</style>