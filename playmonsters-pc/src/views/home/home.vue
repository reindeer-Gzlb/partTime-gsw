<template>
  <section class="wrapper">
    <!-- 轮播区 -->
    <div class="banner-box w1200 flex fbt">
      <div class="banner">
        <el-carousel
          trigger="click"
          height="468px"
          v-loading="lunboLoading"
          v-if="lunbo && lunbo.length"
        >
          <el-carousel-item v-for="item in lunbo" :key="item.id">
            <img :src="item.img" alt style="width: 100%; height: 468px" @click="goLink(item)" />
            <!-- @click="goLink(item)" -->
          </el-carousel-item>
        </el-carousel>
      </div>
      <div class="right-box">
        <!-- <div class="maihao">
          <div style="margin-left: 72px; cursor: pointer" @click="golinksss('/index/accountOrder')">
            我要买号
          </div>
        </div> -->
        <!-- <div class="maihaos">
          <div style="margin-left: 72px; cursor: pointer" @click="golinksss('/index/sell')">
            我要卖号
          </div>
        </div> -->
        <!-- <div class="zhinan">
          <div
            style="padding: 20px; display: flex; justify-content: space-around; align-items: center"
          >
            <div
              style="text-align: center"
              @click="gozhaohui('/index/zhaohui', 'gmzn', 5, '购买指南')"
            >
              <div>
                <img
                  style="width: 33px; height: 32px"
                  src="../../assets//guaishouwan/gouwuche.png"
                  alt=""
                />
              </div>
              <div style="color: #aaaaaa">购买指南</div>
            </div>
            <div style="width: 1px; height: 40px; background: #efefef"></div>
            <div
              style="text-align: center"
              @click="gozhaohui('/index/zhaohui', 'mhzn', 6, '卖号指南')"
            >
              <div>
                <img
                  style="width: 33px; height: 32px"
                  src="../../assets/guaishouwan/zhinanzhen.png"
                  alt=""
                />
              </div>
              <div style="color: #aaaaaa">卖号指南</div>
            </div>
          </div>
        </div> -->
        <div class="gs_title">怪兽玩</div>
        <div class="cj_wenzi flex" style="align-items: center; justify-content: center">
          安全成交<span class="gs_jy">
            <el-statistic value-style="color:#ff0000;fontSize:20px;" :value="outputValue" /></span
          >笔交易
        </div>
        <div class="gs_aq">怪兽玩使账号交易更安全</div>
        <div class="jinrichengjiao">
          <div class="gs_total">
            <div class="gs_name">昨日成交</div>
            <div class="gs_sun">
              <el-statistic value-style="color:#ff0000;fontSize:20px;" :value="outputValue1" />
            </div>
          </div>
          <div class="gs_icon"></div>
          <div class="gs_total">
            <div class="gs_name">商品数量</div>
            <div class="gs_sun">
              <el-statistic value-style="color:#ff0000;fontSize:20px;" :value="outputValue2" />
            </div>
          </div>
        </div>
        <div class="gs_btn flex">
          <el-button class="btn_mai" @click="golinksss('/index/accountOrder')">我要买</el-button>
          <el-button class="btn_mai" @click="golinksss('/index/sell')">我要卖</el-button>
        </div>

        <!-- 客服验证 -->
        <div class="heihaochaxun">
          <div class="top">
            <div
              :class="{ activeTitle: item.id == ids }"
              @click="changeyanzheng(item)"
              class="top-title"
              v-for="item in arrlist"
              :key="item.id"
            >
              {{ item.name }}
            </div>
          </div>
          <div class="chaxun" v-if="ids == 1">
            <div style="width: 223px; height: 32px; margin: 0 auto; margin-top: 15px">
              <el-input
                class="m-2"
                input-style="border: 1px solid #E4E0DF;"
                v-model="weixin"
                placeholder="请输入客服QQ号"
              />
            </div>
            <div>
              <el-button
                style="
                  background-color: #ed3b21;
                  color: #fff;
                  width: 146px;
                  border-radius: 20px;
                  border: 1px solid #e8542d;
                  height: 30px !important;
                  margin-top: 15px;
                "
                @click="kefu_weixin"
                >查询</el-button
              >
            </div>
          </div>

          <div class="chaxun" v-if="ids == 2">
            <div style="width: 223px; height: 32px; margin: 0 auto; margin-top: 15px">
              <el-input
                input-style="border: 1px solid #E4E0DF;"
                v-model="heihao"
                placeholder="请输入游戏账号"
              />
            </div>
            <div>
              <el-button
                style="
                  background-color: #ed3b21;
                  color: #fff;
                  width: 146px;
                  border-radius: 20px;
                  border: 1px solid #e8542d;
                  height: 30px !important;
                  margin-top: 15px;
                "
                @click="kefu_heihao"
                >查询</el-button
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- center start -->
    <div class="center">
      <!-- left -->
      <div class="left_box">
        <!-- 游戏推荐 -->
        <div class="game-type">
          <div class="topbar flex fbt">
            <div class="flex" style="align-items: center; margin-top: 10px; width: 150px">
              <img
                style="width: 24px; height: 24px; margin-top: -2px"
                src="../../assets/guaishouwan/yxlx.png"
                alt=""
              />
              <div style="color: #fff; font-size: 16px; margin-left: 10px; font-weight: 500">
                游戏推荐
              </div>
            </div>
            <!-- <div class="cont flex" style="margin-right: 130px">
              <span
                :class="{ active: typeActive == 1 }"
                @click="
                  typeActive = 1;
                  GetGameTypeChange();
                "
              >
                热门游戏<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 2 }"
                @click="
                  typeActive = 2;
                  GetGameTypeChange();
                "
                >端游<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 3 }"
                @click="
                  typeActive = 3;
                  GetGameTypeChange();
                "
                >手游<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 4 }"
                @click="
                  typeActive = 4;
                  GetGameTypeChange();
                "
                >最近浏览<i class="icon"></i
              ></span>
            </div> -->
            <!-- <div class="more" @click="golinksss('/index/accountOrder')">
              <span style="color: #999999">更多游戏</span>

              <div style="margin-top: 3px">
                <ArrowRight style="width: 20px; height: 18px" />
              </div>
            </div> -->
          </div>
          <!-- 26 -->
          <!-- <div class="bottom">
            <div
              class="li"
              :class="{ on: params.initial == String.fromCharCode(65 + index) }"
              v-for="(item, index) in 26"
              :key="index"
              @click="charChange(String.fromCharCode(65 + index))"
            >
              <span>{{ String.fromCharCode(65 + index) }}</span>
            </div>
          </div> -->

          <div
            class="flex"
            style="align-items: center; justify-content: space-between; margin-top: 12px"
          >
            <div class="cont flex" style="margin-left: 20px; align-items: center">
              <span
                :class="{ active: typeActive == 1 }"
                @click="
                  typeActive = 1;
                  GetGameTypeChange();
                "
              >
                热门游戏<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 2 }"
                @click="
                  typeActive = 2;
                  GetGameTypeChange();
                "
                >端游<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 3 }"
                @click="
                  typeActive = 3;
                  GetGameTypeChange();
                "
                >手游<i class="icon"></i
              ></span>
              <span
                :class="{ active: typeActive == 4 }"
                @click="
                  typeActive = 4;
                  GetGameTypeChange();
                "
                >最近浏览<i class="icon"></i
              ></span>
            </div>
            <div class="more" @click="golinksss('/index/accountOrder')">
              <span style="color: #b8b6c4">更多游戏</span>

              <div style="margin-top: 3px">
                <ArrowRight style="width: 20px; height: 18px; color: #b8b6c4" />
              </div>
            </div>
          </div>

          <div class="gamelist">
            <div class="game-items-wrapper">
              <div
                class="game-item"
                @click="gameTypeClick(item)"
                v-for="(item, index) in gameTypeList"
                :key="`game${index}`"
              >
                <div class="game-icon">
                  <img :src="item.icon" style="width: 100%; height: auto" alt />
                </div>
                <div class="name">{{ item.name }}</div>
              </div>
            </div>
          </div>
        </div>
        <!-- 合作主播 -->
        <div class="zhubo">
          <div class="topbar flex fbt">
            <div class="flex" style="align-items: center; margin-top: 10px; width: 150px">
              <img
                style="width: 24px; height: 24px; margin-top: -2px"
                src="../../assets/guaishouwan/hzzb.png"
                alt=""
              />
              <div style="color: #fff; font-size: 16px; margin-left: 10px; font-weight: 500">
                合作主播
              </div>
            </div>
            <div class="more">
              <span style="color: #999999" @click="golinksss('/index/accountOrder')">更多游戏</span>

              <div style="margin-top: 3px">
                <ArrowRight style="width: 20px; height: 18px" />
              </div>
            </div>
          </div>
          <div class="zhubo-cont">
            <el-carousel
              :interval="5000"
              height="131px"
              indicator-position="none"
              :autoplay="true"
              arrow="always"
              v-if="zhuboGroup && zhuboGroup.length"
            >
              <el-carousel-item v-for="(item, index) in zhuboGroup" :key="index">
                <div class="el-carousel-item-box" style="cursor: pointer">
                  <!-- <div>{{ item }}</div> -->
                  <img
                    :src="item2.img"
                    v-for="item2 in item"
                    :key="item2.id"
                    @click="openWebes(item2.pcJump)"
                  />
                </div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </div>
      </div>
      <!-- right -->
      <div class="right_box">
        <!-- 公告列表 -->
        <div class="gonggaoList gaongaos">
          <div class="onede topbarer">
            <div class="allboxs">
              <div class="flex" style="align-items: center; width: 150px; margin-top: 8px">
                <img
                  style="width: 24px; height: 24px; margin-top: -5px"
                  src="../../assets/guaishouwan/cjwt.png"
                  alt=""
                />
                <div class="titles">常见问题</div>
              </div>

              <div
                class="gengduos"
                style="cursor: pointer"
                @click="golinksss('/index/yewujieshao')"
              >
                更多
              </div>
            </div>
            <div
              class="onetap"
              v-for="item in list"
              :key="item.noticeId"
              @click="qudetailsgg('/index/yewujieshao', '常见问题', item.noticeId)"
            >
              <div class="diandian"></div>
              <div class="wenzhang">{{ item.noticeTitle }}</div>
            </div>
          </div>
        </div>
        <!-- 新闻公告 -->
        <div class="xinwengonggao gaongaos">
          <div class="onede topbarer">
            <div class="allboxs">
              <div class="flex" style="align-items: center; width: 150px; margin-top: 8px">
                <img
                  style="width: 24px; height: 24px; margin-top: -5px"
                  src="../../assets/guaishouwan/xwgg.png"
                  alt=""
                />
                <div class="titles">新闻公告</div>
              </div>

              <div
                class="gengduos"
                style="cursor: pointer"
                @click="golinksss('/index/yewujieshao')"
              >
                更多
              </div>
            </div>
            <div
              class="onetap"
              v-for="item in list1"
              :key="item.noticeId"
              @click="qudetailsgg('/index/yewujieshao', '新闻公告', item.noticeId)"
            >
              <div class="diandian"></div>
              <div class="wenzhang">{{ item.noticeTitle }}</div>
            </div>
          </div>
        </div>
        <!-- 售后案例 -->
        <div class="shouhouanli gaongaos">
          <div class="onede topbarer">
            <div class="allboxs">
              <div class="flex" style="align-items: center; width: 150px; margin-top: 8px">
                <img
                  style="width: 24px; height: 24px; margin-top: -5px"
                  src="../../assets/guaishouwan/zhal.png"
                  alt=""
                />
                <div class="titles">追回案例</div>
              </div>

              <div
                class="gengduos"
                style="cursor: pointer"
                @click="golinksss('/index/yewujieshao')"
              >
                更多
              </div>
            </div>
            <div
              class="onetap"
              v-for="item in list2"
              :key="item.noticeId"
              @click="qudetailsgg('/index/yewujieshao', '追回案例', item.noticeId)"
            >
              <div class="diandian"></div>
              <div class="wenzhang">{{ item.noticeTitle }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- center End -->

    <!-- 游戏账号分类+列表 -->
    <div class="dingji w1200">
      <div class="topbar flex fbt">
        <div class="cont flex" style="align-items: center">
          <div class="flex" style="align-items: center">
            <img
              style="width: 24px; height: 24px; margin-top: 4px"
              src="../../assets/guaishouwan/djzh.png"
              alt=""
            />
            <div class="dingjis">顶级账号</div>
          </div>
          <!-- <span
            :class="{ active: gameActive.id == game.id }"
            @click="
              gameActive = game;
              getDingjiList();
            "
            v-for="game in gameTypeListTabs.slice(0, 7)"
            :key="game.id"
            >{{ game.name }}<i class="icon"></i
          ></span> -->
        </div>
        <!-- <div class="more" @click="golinksss('/index/accountOrder')">
          <span style="color: #999999">更多游戏</span>
          <div style="margin-top: 3px">
            <ArrowRight style="width: 20px; height: 18px" />
          </div>
        </div> -->
      </div>

      <div
        class="flex"
        style="
          align-items: center;
          justify-content: space-between;
          margin-top: 12px;
          padding: 0 20px;
        "
      >
        <div class="cont flex" style="align-items: center">
          <span
            :class="{ active: gameActive.id == game.id }"
            @click="
              gameActive = game;
              getDingjiList();
            "
            v-for="game in gameTypeListTabs.slice(0, 7)"
            :key="game.id"
            >{{ game.name }}<i class="icon"></i
          ></span>
        </div>
        <div class="more" style="margin-top: 12px" @click="golinksss('/index/accountOrder')">
          <span style="color: #dad7d7">更多游戏</span>
          <div style="margin-top: 3px">
            <ArrowRight style="width: 20px; height: 18px; color: #dad7d7" />
          </div>
        </div>
      </div>

      <div class="djzh-cont">
        <div class="list" v-loading="loading">
          <div
            class="list-item"
            v-for="(item, i) in dingjiList"
            :key="i"
            @mouseenter="handleMouseEnter(item)"
            @mouseleave="handleMouseLeave(item)"
            @click="seeDetail(item)"
          >
            <!-- <div class="sign">
              <img
                class="image"
                src="../../assets/zhanghaoguai/kemaibaopei.png"
                alt=""
                style="width: 93px; height: 24px"
              />
            </div> -->
            <div class="ziying" v-if="item.accountType == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/zybg.png" alt="" />
            </div>
            <div class="tejia" v-if="item.isOnSpecialOffer == 1">
              <img style="width: 66px; height: 66px" src="../../assets/biaoqian/tjbg.png" alt="" />
            </div>
            <div class="img">
              <img class="image" :src="item.titleImg || noimg" alt />
            </div>
            <div>
              <div class="good-content">
                <el-popover
                  popper-class="user-pop-over"
                  ref="popover"
                  placement="right"
                  title="商品详情"
                  trigger="hover"
                  :width="444"
                  :content="item.des"
                >
                  <template #reference>
                    <p class="name" style="height: 39px">{{ item.des }}</p>
                  </template>
                </el-popover>

                <div class="icon_box_all">
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
                <!-- <p  class="name" style="height: 39px">{{ item.des }}</p> -->
                <div class="price-link flex fbt">
                  <div style="color: #999999; font-size: 14px">售价：</div>
                  <p class="price"><span class="yang">¥ </span>{{ item.newPrice }}</p>
                  <!-- <div>{{ item.createTime.substring(0, 10) }}</div> -->
                </div>
                <p
                  class="game-type"
                  style="
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    color: #999999;
                    font-size: 14px;
                  "
                >
                  游戏类型：<span>{{ item.gameName }}</span>
                </p>
                <!-- <p
                  class="game-type"
                  v-if="item.gameType == '1'"
                  style="
                    display: flex;
                    align-items: center;
                    justify-content: space-between;
                    color: #999999;
                    font-size: 14px;
                  "
                >
                游戏类型：{{ item.gameName }}
                </p> -->
              </div>
            </div>

            <!-- <el-popover
              :visible="visible"
              ref="popover"
              placement="right"
              title="商品详情"
              :width="200"
              :content="item.des"
            >
              <template #reference>
                <el-button class="m-2">Focus to activate</el-button>
              </template>
            </el-popover> -->
            <!-- 遮罩层 -->
            <!-- <div class="zhezhaoceng"></div> -->
          </div>
        </div>
        <!-- <div style="height: 50px">
          <el-pagination
            v-loading="loading"
            style="float: right"
            v-model:currentPage="obj.pageNum"
            v-model:page-size="obj.pageSize"
            :page-sizes="[5, 10, 20, 50, 100, 200]"
            :small="small"
            :disabled="pageDisabled"
            :background="pageBackground"
            layout="sizes, prev, pager, next,jumper"
            :total="accountListTotal"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div> -->
      </div>
      <!-- 分页 -->
      <!-- <div class="download-btn" v-if="showDownload">
        <img src="../../assets/fh-img/pc-downloadnew.png" alt="" style="width: 100%" />
        <div class="btn-icon" @click="showDownload = false">
          <el-icon>
            <CircleCloseFilled />
          </el-icon>
        </div>
      </div> -->
    </div>

    <!-- 底部 -->

    <Footer :home="true" />

    <!-- :style="{ display: homeright ? 'block' : 'none' }" -->

    <!-- <div class="lefted" :style="{ 'margin-left': homeleft ? '' : '-259px' }">
      <img
        @click="homeleft = !homeleft"
        style="width: 30px; height: 50px; position: absolute; margin-top: 266px; margin-left: 231px"
        :src="homeleft ? leftshou : leftzhan"
        alt=""
      />
      <leftBox />
    </div>
    <div v-if="showDownload" style="height: 113px"></div> -->
  </section>

  <!-- 模态框——组件插槽 -->
  <!-- 轮播图弹出 -->
  <ElMessageBox :is_show="is_show15" @update="handleChildEvent" :text="text">
    <div class="is_show1 is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="../../assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(15)"
      />
      <h2>{{ lunboInfo.name }}</h2>
      <div class="content cooperate" v-show="lunboInfo.pcJump == ''">
        <!-- <img src="../../assets/fh-img/is_show1.jpeg" alt=""> -->
        <!-- <img :src="jyxzimg" alt=""> -->
        <div v-html="lunboInfo.content"></div>
        <!-- {{ jyxzimg }} -->
      </div>
    </div>
  </ElMessageBox>
  <!-- 交易须知 -->
  <ElMessageBox :is_show="is_show1" @update="handleChildEvent" :text="text">
    <div class="is_show1 is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="../../assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(1)"
      />
      <h2>交易须知</h2>
      <div class="content cooperate">
        <!-- <img src="../../assets/fh-img/is_show1.jpeg" alt=""> -->
        <!-- <img :src="jyxzimg" alt=""> -->
        <div v-html="jyxzimg"></div>
        <!-- {{ jyxzimg }} -->
      </div>
    </div>
  </ElMessageBox>
  <!-- 业务介绍 -->
  <ElMessageBox :is_show="is_show2" @update="handleChildEvent" :text="text">
    <div class="is_show2 is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="../../assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(2)"
      />
      <h2>业务介绍</h2>
      <div class="content cooperate">
        <!-- <img src="../../assets/fh-img/is_show2.jpeg" alt="" /> -->
        <!-- <img :src="ywjsimg" alt=""> -->
        <div v-html="ywjsimg" class="ywjsimg"></div>
        <!-- {{ywjsimg}} -->
      </div>
    </div>
  </ElMessageBox>
  <!-- 官方视频号 -->
  <ElMessageBox :is_show="is_show5" @update="handleChildEvent" :text="text">
    <div class="is_show5 is_shows" @click.stop="on">
      <img
        class="icon_cha"
        src="../../assets/fh-icon/cha.png"
        alt=""
        @click.stop="handleChildEvent(5)"
      />
      <h2>官方视频号</h2>
      <div class="content">
        <h3 class="douyin">抖音官方账号</h3>
        <ul>
          <li
            v-for="(item, index) in shipin_list.douyin"
            :key="index"
            @click.stop="JumpTo(item.pcJump)"
          >
            <!-- <img src="../../assets//fh-img/shipin_touxiang.jpg" alt=""> -->
            <img :src="item.avatarImg ? item.avatarImg : imges" alt="" />
            <div class="conter_text">
              <h6>{{ item.name }}</h6>
              <p>粉丝：{{ item.fans }}</p>
              <p class="shouAcc" :style="{ color: item.pcJump ? 'blue' : '' }">
                账号：{{ item.account }}
              </p>
            </div>
            <button @click.stop="copy(item.account)">复制抖音号</button>
          </li>
        </ul>
        <h3 class="kuaishou">快手官方账号</h3>
        <ul>
          <li
            v-for="(item, index) in shipin_list.kuaishou"
            :key="index"
            @click.stop="JumpTo(item.pcJump)"
          >
            <img :src="item.avatarImg ? item.avatarImg : imges" alt="" />
            <div class="conter_text">
              <h6>{{ item.name }}</h6>
              <p>粉丝：{{ item.fans }}</p>
              <p class="shouAcc" :style="{ color: item.pcJump ? 'blue' : '' }">
                账号：{{ item.account }}
              </p>
            </div>
            <button @click.stop="copy(item.account)">复制快手号</button>
          </li>
        </ul>
      </div>
    </div>
  </ElMessageBox>
  <Check ref="check"></Check>

  <el-dialog v-model="resultShow" title="查询结果" @close="closeResult" width="400px">
    <div style="text-align: center">
      <img v-if="is_show6" src="../../assets/fh-icon/jinggao.png" alt="" />
      <img v-if="is_show66" src="../../assets/fh-icon/fangxin.png" alt="" />
      <p v-if="is_show6">该账号不是怪兽玩官方客服，谨防受骗！</p>
      <p v-if="is_show66">该账号是怪兽玩官方客服，请放心咨询！</p>
    </div>
  </el-dialog>

  <el-dialog
    v-model="resultShowheihao"
    title="查询结果"
    @close="resultShowheihao = false"
    width="400px"
  >
    <div style="text-align: center">
      <img v-if="is_show8" src="../../assets/fh-icon/jinggao.png" alt="" />
      <img v-if="is_show88" src="../../assets/fh-icon/fangxin.png" alt="" />
      <p v-if="is_show8">当前查询账号有找回记录，谨防受骗！</p>
      <p v-if="is_show88">当前查询账号没有找回记录，请放心咨询！</p>
    </div>
  </el-dialog>
</template>

<script>
import config from '@/api/config';
import { computed, reactive, watch, ref, toRefs, onMounted, markRaw } from 'vue';
import Footer from '@/components/Footer.vue';
import Check from '@/components/kefuCheck.vue';
import {
  WarningFilled,
  SuccessFilled,
  ArrowRight,
  CircleCloseFilled
} from '@element-plus/icons-vue';
// import leftBox from './leftBox.vue';
import ElMessageBox from '../../components/ElMessageBox.vue';
import api from '@/api/index';
import kefu from '@/api/kefu';
import huishouApi from '@/api/huishou';
import { ElMessage } from 'element-plus';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import swhz from '@/assets/yudu/home/swhz.png';
import zhpm from '@/assets/yudu/home/zhpm.png';
import dcwj from '@/assets/yudu/home/dcwj.png';
import shal from '@/assets/yudu/home/shal.png';
import xsbk from '@/assets/yudu/home/xsbk.png';
import zbzm from '@/assets/yudu/home/zbzm.png';

import Aratar from '../../assets/fh-img/shipin_touxiang.jpg';

import a from '@/assets/fh-img/ptkf.png';
import b from '@/assets/fh-img/kfyz.png';
import c from '@/assets/fh-img/gfsph.png';
import ywjstitle from '@/assets/fh-img/ywjs.png';
import jyxztitle from '@/assets/fh-img/jyxz.png';

import { useTransition } from '@vueuse/core';
export default {
  name: 'home',
  components: {
    Footer,
    ArrowRight,
    ElMessageBox,
    CircleCloseFilled,
    Check
    // rightBox,
    // leftBox
  },
  setup() {
    // const loading = ref(false)
    const store = useStore();
    const router = useRouter();
    const check = ref(null);
    const source = ref(0);
    const outputValue = useTransition(source, {
      duration: 1500
    });
    const source1 = ref(0);
    const outputValue1 = useTransition(source1, {
      duration: 1500
    });
    const source2 = ref(0);
    const outputValue2 = useTransition(source2, {
      duration: 1500
    });
    const state = reactive({
      number: '',
      outputValue: outputValue,
      outputValue1: outputValue1,
      outputValue2: outputValue2,
      homeright: true,
      homeleft: true,
      visible: false,
      arrlist: [
        { name: '客服验证', id: 1 },
        { name: '黑号查询', id: 2 }
      ],
      ids: 1,
      popId: '',
      showDownload: true,
      zuijinjiaoyi: {},
      obj: {
        pageSize: 12,
        pageNum: 1
      },
      weixin: null,
      heihao: null,
      resultShow: false,
      resultShowheihao: false,
      is_show66: false,
      is_show6: false,
      is_show88: false,
      is_show8: false,
      currentPage2: 1,
      pageSize2: 5,
      accountListTotal: 0, //总数
      pageBackground: true,
      small: false,
      pageDisabled: false,
      lunboInfo: {},
      imges: Aratar,
      jyxztitleImg: jyxztitle,
      ywjstitleImg: ywjstitle,
      // kfyztitleImg:'',
      jyxzimg: '',
      ywjsimg: '',
      kfyzimg: '',
      jyxzname: 'jyxz',
      ywjsname: 'ywjs',
      kfyzname: 'kfyz',
      nickName: '', // 昵称
      userInfo: {},
      text: '1',
      is_show1: false,
      is_show2: false,
      is_show3: false,
      is_show4: false,
      is_show5: false,
      is_show15: false,
      imgs: {
        1: a,
        2: b,
        3: c
      },
      fuwuList: [], //服务指南
      path: {
        11: '/index/other/IndemnityService',
        12: '/index/other/Installmentbusiness',
        13: '/index/other/AfterSaleProtection',
        14: '/index/other/merchantsEnter'
      },
      params: {
        type: '',
        initial: '',
        name: '',
        state: 0
      },
      gameActive: {},
      loading: false,
      kefuValue: '',
      heihaoValue: '',
      activeChaxun: '1',
      lunbo: [],
      lunboLoading: false,
      zhubo: [],
      zhuboGroup: [],
      typeActive: '1',
      gonggaoList: [],
      newsList: [],
      saleAfter: [],
      dingjiList: [],
      gameTypeList: [],
      gameTypeListTabs: [],
      noimg: 'https://game.ikbh.top/logo/no_img.jpg',
      otherBtns: [
        {
          name: '商务合作',
          icon: swhz,
          link: ''
        },
        {
          name: '账号拍卖',
          icon: zhpm,
          link: ''
        },
        {
          name: '调查问卷',
          icon: dcwj,
          link: ''
        },
        {
          name: '售后案例',
          icon: shal,
          link: ''
        },
        {
          name: '新手必看',
          icon: xsbk,
          link: ''
        },
        {
          name: '主播招募',
          icon: zbzm,
          link: ''
        }
      ],
      pageData: null,
      Fraud_prevention: [], //防骗指南
      shipin_list: {
        douyin: [],
        kuaishou: []
      }, //视频列表
      weixin: '', // 客服微信
      zhifubao: '', // 平台收款账号查询
      heihao: '', // 黑号查询
      kefu_list: [], // 客服列表
      list: [],
      list1: [],
      list2: [],
      topPrice: 0,
      goodsNum: {}
    });
    // 查询顶级账号价格
    const getTopPrice = async () => {
      huishouApi.getDict('topPrice').then((res) => {
        if (res.data && res.data.length) {
          state.topPrice = res.data[0].dictValue;
          console.log('%c Line:824 🥥 state.topPrice', 'color:#3f7cff', state.topPrice);
          getDingjiList();
        } else {
          state.topPrice = 6000;
          getDingjiList();
        }
      });
    };

    // 获取首页统计信息
    const getIndexData = async () => {
      const res = await api.getPCIndex();
      // state.number = res.data.goodsNum
      source.value = res.data.goodsNum;
      source1.value = res.data.goodsYesterdaySoldNum;
      source2.value = res.data.goodsSoldNum;
    };
    // 进入账号列表
    const gameTypeClick = async (item) => {
      // router.push({
      //   path: '/index/goodList',
      //   query: { name: item.name, type: item.type, id: item.id }
      // });
      let hisArr = await localStorage.getItem('historyViews');
      if (hisArr) {
        hisArr = JSON.parse(hisArr);
      } else {
        hisArr = [];
      }
      hisArr.unshift(item);
      hisArr = hisArr.filter((item, index) => {
        return hisArr.findIndex((item2) => item2.id === item.id) === index;
      });
      await localStorage.setItem('historyViews', JSON.stringify(hisArr));
      router.push({
        path: '/index/goodList',
        query: { name: item.name, type: item.type, id: item.id }
      });
    };
    // 跳转
    const goLink = (link) => {
      // //console.log(link,query);
      //console.log(link);
      state.lunboInfo = link;
      // if (query) {
      //   router.push({ path: link, query: query });
      // } else {
      //   router.push({ path: link });
      // }
      if (link.pcJump !== '' && link.content !== '') {
        window.open(`${link.pcJump}`, '_bank');
      } else if (link.pcJump == '' && link.content == '') {
        return;
      } else if (link.content !== '' && link.pcJump == '') {
        state.is_show15 = true;
      }
    };
    const openWebes = (link) => {
      if (link) {
        window.open(`${link}`, '_bank');
      }
    };
    const golinksss = (link) => {
      router.push(link);
    };
    //热门游戏跳转
    const gochange = () => {
      //console.log('111');
      setTimeout(() => {
        router.push('/index/accountOrder');
      }, 500);
    };

    // 进入详情
    const seeDetail = (item) => {
      router.push({ path: `/pages/detail/index`, query: { id: item.id } });
    };
    // 获取通知列表
    const getNotices = () => {
      kefu.getNoticeList('20').then((res) => {
        if (res && res.length > 3) {
          state.gonggaoList = res.slice(0, 3);
        } else {
          state.gonggaoList = res;
        }
      });
      kefu.getNoticeList('11').then((res) => {
        if (res && res.length > 3) {
          state.newsList = res.slice(0, 3);
        } else {
          state.newsList = res;
        }
      });
    };
    // 获取轮播图
    const getLunbo = (yxlx) => {
      state.lunboLoading = true;
      kefu.getImgs(1).then((res) => {
        state.zhubo = res.rows;
        let j = 0;
        let arr = [];
        arr[j] = [];
        res.rows.forEach((item, i) => {
          if (!arr[j]) {
            arr[j] = [];
          }
          arr[j].push(item);
          if ((i + 1) % 3 == 0) {
            j++;
          }
        });
        state.zhuboGroup = arr;
      });
      kefu.getImgs(2).then((res) => {
        state.lunbo = res.rows;
        state.lunboLoading = false;
      });
    };

    // 获取公告列表
    const getGonggao = () => {
      kefu.getNoticeLists(999).then((res) => {
        state.Fraud_prevention = res.rows;
      });
    };

    // // 监听路由参数
    // watch(
    //   () => router.currentRoute.value.query,
    //   (newValue, oldValue) => {
    //     if (router.currentRoute.value.query == '/index/home') {
    //       getTopPrice()
    //     }

    //   },
    //   { deep: true ,immediate:true}
    // );
    // 获取顶级账号列表
    const getDingjiList = () => {
      let obj = {
        gameId: state.gameActive.id,
        pageNum: 1,
        pageSize: 8,
        sortRank: 'pricedesc',
        priceStart: state.topPrice
      };
      state.loading = true;
      kefu.getDingji(obj).then((res) => {
        state.loading = false;
        state.dingjiList = res.rows;
      });
    };

    // 分页操作
    const handleSizeChange = (size) => {
      //console.log('size', size);
      state.obj.pageSize = size;
      //console.log('state.obj.pageSize', state.obj.pageSize);
      getDingjiList();
    };

    const handleCurrentChange = (value) => {
      //console.log('value', value);
      state.obj.pageNum = value;
      getDingjiList();
    };

    // 进入全部游戏列表页面
    const toGameType = () => {
      router.push({ path: `/accountOrder` });
    };
    // 获取游戏类型
    const GetGameType = async () => {
      let games = await api.GetGameType();
      state.gameTypeListTabs = JSON.parse(JSON.stringify(games.data));
      if (games.data && games.data.length) {
        games.data = games.data.splice(0, 12);
        state.gameActive = games.data[0];
        getTopPrice();
      }
      if (state.typeActive == 1) {
        state.gameTypeList = games.data;
      }
    };
    // 游戏类型切换
    const GetGameTypeChange = async () => {
      state.gametypeloading = true;
      if (state.typeActive == 4) {
        let games = await localStorage.getItem('historyViews');
        if (games) {
          games = JSON.parse(games);
          // const result = [...new Set(games)];
          games = games.splice(0, 12);
          state.gameTypeList = games;
        } else {
          state.gameTypeList = [];
        }
        state.gametypeloading = false;
        return;
      }
      let type = '';
      if (state.typeActive == 2) {
        type = 1;
      } else if (state.typeActive == 3) {
        type = 0;
      } else {
        type = '';
      }
      let games = await api.GetGameType({ type: type });
      state.gametypeloading = false;
      if (games.data && games.data.length) {
        games.data = games.data.splice(0, 12);
        state.gameTypeList = games.data;
      }
    };

    // 页面加载完成
    onMounted(() => {
      const isMobile = /Mobile|Android|iPhone/i.test(navigator.userAgent);
      if (isMobile) {
        // 移动端访问，跳转到移动端
        window.location.href = `${config.h5Url}`;
      } else {
        getIndexData();
        GetGameType();
        getNotices();
        getLunbo();
        getGonggao();
        // 获取本地存储的用户信息
        state.userInfo = JSON.parse(localStorage.getItem('userInfo'));
        if (state.userInfo) {
          if (state.userInfo.nickName && state.userInfo.nickName.length === 11) {
            //console.log(state.userInfo.nickName.slice(0, 3) + '****' + state.userInfo.nickName.slice(7))
            state.nickName =
              state.userInfo.nickName.slice(0, 3) + '****' + state.userInfo.nickName.slice(7);
          } else {
            state.nickName = state.userInfo.nickName;
          }
        }
      }
    });
    // 官方视频号
    const JumpTo = (type) => {
      //console.log(type);
      if (type.indexOf('http') != -1) {
        window.open(`${type}`, '_bank');
      } else if (!type.indexOf('http') != -1) {
        return ElMessage('this is a message.');
      }
    };
    // 关闭弹窗
    const handleChildEvent = (item) => {
      if (item == 1) {
        state.is_show1 = false;
      } else if (item == 2) {
        state.is_show2 = false;
      } else if (item == 3) {
        state.is_show3 = false;
      } else if (item == 4) {
        state.is_show4 = false;
      } else if (item == 5) {
        state.is_show5 = false;
      } else if ((item = 15)) {
        state.is_show15 = false;
      }
      // 让浏览器滚动条显示
      document.documentElement.style.overflow = 'auto';
    };
    const showone = () => {
      state.is_show2 = true;
      // state.text = item
    };
    const showtwo = () => {
      state.is_show1 = true;
    };
    // 开启弹窗
    const show = (item) => {
      if (item == 1) {
        //  调用一个需要登录权限的接口，校验是否登录
        kefu
          .getSystemInfo()
          .then((res) => {
            if (res.code == 200) {
              // 联系客服
              router.push({
                path: '/index/accountOrder',
                query: { to: 'kefu' }
              });
            }
          })
          .catch((err) => {
            //console.log(err);
          });
      } else if (item == 2) {
        // 客服验证
        check.value.open();
      } else if (item == 3) {
        state.is_show5 = true;
        state.text = item;
        // 让浏览器滚动条隐藏
        document.documentElement.style.overflow = 'hidden';
      }
    };
    //解决带标签的问题
    // filtersContent(e) {
    // 	return e.replace(/<[^>]+>/g, '').replace(/&nbsp;/gi, '')
    // },

    const on = () => {
      // state.is_show = true;
    };

    // 复制方法
    const copy = (text) => {
      //console.log(text);
      let input = document.createElement('input');
      input.setAttribute('readonly', 'readonly');
      input.setAttribute('value', text);
      document.body.appendChild(input);
      input.select();
      if (document.execCommand('copy')) {
        document.execCommand('copy');
        ElMessage({
          type: 'success',
          message: '复制成功'
        });
      }
      document.body.removeChild(input);
    };

    // 获取客服数据
    const getsecurity = () => {
      kefu.getsecurity().then((res) => {
        //console.log("获取客服数据", res)
        state.kefu_list = res;
      });
    };

    // 获取抖音快手
    const getDyks = () => {
      kefu.getDyks().then((res) => {
        //console.log("获取抖音快手", res)
        // 过滤res.rows中type为1的数据添加到state.shipin_list.douyin中，type为2的数据添加到state.shipin_list.kuaishou中
        res.rows.forEach((item) => {
          if (item.type == 1) {
            state.shipin_list.douyin.push(item);
          } else if (item.type == 2) {
            state.shipin_list.kuaishou.push(item);
          }
        });
      });
    };
    //获取交易须知
    const getjyxz = () => {
      kefu.getdetails(state.jyxzname).then((res) => {
        //console.log(res);
        state.jyxzimg = res[0].noticeContent;
        state.jyxztitleImg = res[0].titleImg;
      });
    };
    //获取业务介绍
    const getywjs = () => {
      kefu.getdetailywjs(state.ywjsname).then((res) => {
        //console.log(res);
        state.ywjsimg = res[0].noticeContent;
        state.ywjstitleImg = res[0].titleImg;
      });
    };

    //跳转到信息
    const goLinks = () => {
      router.push('/index/finish');
    };

    //获取最近交易
    const getJYdetail = () => {
      kefu.getRecentDeal(state.obj).then((res) => {
        //console.log(res);
        state.zuijinjiaoyi = res.rows;
      });
    };

    //即时通讯
    const openQQ = (val) => {
      //console.log(val);
      let token = window.localStorage.getItem('token');
      if (token) {
        token = JSON.parse(token);
      } else {
        token = '';
      }
      //console.log("%c Line:67 🍡 联系客服链接", "color:#7f2b82", `${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}&goodsInfo=${encodeURIComponent(state.goodsInfo)}`);
      window.open(`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}`);
    };

    // 点击首字母
    const charChange = async (txt) => {
      console.log('%c Line:164 🍒 txt', 'color:#4fff4B', txt);
      state.params.initial = txt;
      // GetGameType()
      let data = await api.GetGameType(state.params);
      console.log('%c Line:83 🧀 data', 'color:#ed9ec7', data);
      state.gameTypeList = data.data;
      store.commit('setGameTypeList', state.gameTypeList);
    };

    const changeyanzheng = (item) => {
      console.log(item);
      state.ids = item.id;
    };

    // 1，官方客服微信验证
    const kefu_weixin = () => {
      state.heihao = null;
      console.log('kefu_weixin', state.weixin);
      kefu.checkAccount({ weixin: state.weixin }).then((res) => {
        state.resultShow = true;
        if (res.code == 200) {
          state.is_show66 = true;
        } else {
          state.is_show6 = true;
        }
      });
    };

    // 3，黑号查询（QQ/微信账号有申诉/找回记录的用户）
    const kefu_heihao = () => {
      state.weixin = null;
      console.log('kefu_heihao', state.heihao);
      kefu.checkHeihao({ content: state.heihao }).then((res) => {
        if (res.code == 401) {
          return;
        }
        state.resultShowheihao = true;
        if (res.code == 200) {
          state.is_show88 = true;
        } else {
          state.is_show8 = true;
        }
      });
    };

    const gonggaolist = async () => {
      const res = await kefu.getNotices('cjwt');
      console.log(res);
      state.list = res.splice(0, 3);
    };

    const newsgonggao = async () => {
      const res = await kefu.getNotices('xwgg');
      console.log(res);
      state.list1 = res.splice(0, 3);
    };

    const shouhouanli = async () => {
      const res = await kefu.getNotices('zhgg');
      console.log(res);
      state.list2 = res.splice(0, 3);
    };

    const gozhaohui = (link, query, id, name) => {
      router.push({ path: link, query: { notice: query, id: id, name: name } });
    };

    const handleMouseEnter = (item) => {
      // console.log(item);
      // state.popId = item.id
      // state.visible = true
    };

    const handleMouseLeave = () => {
      // state.popId = ''
      // state.visible = false
    };

    const formatNumberWithCommas = () => {
      return Number(state.number.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,'));
      // var b = parseInt(state.number).toString();
      // var len = b.length;
      // console.log(len);
      // if (len <= 3) {
      //   console.log(1111);
      //   return b;
      // }
      // var r = len % 3;
      // return r > 0 ? b.slice(0, r) + "," + b.slice(r, len).match(/\d{3}/g).join(",") : b.slice(r, len).match(/\d{3}/g).join(",")
    };

    onMounted(() => {
      getsecurity();
      getDyks();
      getjyxz();
      getywjs();
      getJYdetail();
      gonggaolist();
      newsgonggao();
      shouhouanli();
      formatNumberWithCommas();
    });

    //  const userName = computed(()=>{
    //   shipin_list.kuaishou.map(item => {
    //     return  item.pcJump ? 'blue' : ''
    //    })
    //  })

    const qudetailsgg = (link, title, id) => {
      router.push({ path: link, query: { name: title, id: id } });
    };

    return {
      ...toRefs(state),
      getywjs,
      getjyxz,
      gameTypeClick,
      goLink,
      seeDetail,
      toGameType,
      GetGameType,
      getDingjiList,
      getNotices,
      getLunbo,
      GetGameTypeChange,
      getIndexData,
      getGonggao,
      show,
      handleChildEvent,
      on,
      copy,
      gochange,
      showone,
      showtwo,
      JumpTo,
      goLinks,
      getJYdetail,
      handleSizeChange,
      handleCurrentChange,
      openQQ,
      check,
      charChange,
      changeyanzheng,
      kefu_weixin,
      kefu_heihao,
      golinksss,
      qudetailsgg,
      gozhaohui,
      handleMouseEnter,
      handleMouseLeave,
      openWebes
    };
  }
};
</script>

<style scoped lang="less">
ol,
ul,
li {
  list-style: none;
}

.activeTitle {
  color: #fff !important;
  background-color: #ed3d22 !important;
  border-radius: 4px;
  position: relative;
  // padding-bottom: 10px; /* 为三角形留出空间 */
}

.activeTitle::after {
  content: '';
  position: absolute;
  bottom: -6px;
  left: 50%; /* 三角形水平居中 */
  transform: translateX(-50%);
  width: 0;
  height: 0;
  border-left: 7px solid transparent; /* 左边边框透明 */
  border-right: 7px solid transparent; /* 右边边框透明 */
  border-top: 7px solid #ed4024; /* 上边框为三角形的颜色 */
}

.center {
  width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  .left_box {
    width: 877px;
    min-height: 100px;
    // background-color: skyblue;
    margin-top: 20px;

    .game-type {
      width: 877px;
      height: 500px;
      background-size: 100% 192px;
      background-repeat: no-repeat;
      background-color: #211c38;
      border: 1px solid #2e2a44;
      border-radius: 10px;
      margin-top: 20px;

      .cont {
        .dingjis {
          font-size: 18px;
          font-family: Microsoft YaHei;
          font-weight: bold;
          color: #b8b6c4;
          margin-top: 6px;
          margin-left: 10px;
          margin-right: 46px;
        }
        span {
          // padding: 0 30px;
          // width: 90px;
          height: 46px;
          line-height: 46px;
          font-size: 16px;
          cursor: pointer;
          color: #b8b6c4;
          // border-radius: 10px 10px 0px 0px;
          margin-right: 66px;
          margin-top: 7px;

          .icon {
            display: none;
            width: 40px;
            height: 4px;
            // background: #3c80ff;
            border-radius: 2px;
            margin: 0 auto;
            margin-top: -6px;
          }

          &.active {
            // width: 105px;
            padding: 0 20px;
            height: 36px;
            text-align: center;
            line-height: 36px;
            background: linear-gradient(to right, #e8592f, #ee3c22);
            border-radius: 20px;
            color: #fff;

            // .icon {
            //   display: block;
            // }
          }
        }
      }

      .more {
        font-size: 16px;
        color: #777;
        cursor: pointer;
        display: flex;
        // align-items: center;
        margin-top: 6px;
        margin-right: 20px;
      }

      .bottom {
        display: flex;
        align-items: center;
        width: 760px;
        padding: 0 20px;
        // width: calc(100% - 40px);
        margin: 20px 0 10px 0;
        border-radius: 10px;
        box-sizing: border-box;
        justify-content: space-between;

        .li {
          cursor: pointer;
          color: #333333;
          font-weight: bold;
          font-size: 14px;
        }

        .on {
          color: #96ce20;
        }
      }

      .game-items-wrapper {
        padding-left: 20px;
        display: flex;
        flex-wrap: wrap;
        margin: 30px 30px 30px 25px;

        // justify-content:space-around;
        .game-item {
          width: 90px;
          // height: 76px;
          border-radius: 20px;
          margin-bottom: 30px;
          cursor: pointer;
          margin-right: 50px;

          &:nth-of-type(6n) {
            margin-right: 0;
          }

          &:hover {
            transform: scale(1.1);
            transition: all 0.3s;
          }

          .game-icon {
            width: 90px;
            height: 90px;
          }

          .name {
            text-align: center;
            padding: 10px 0;
            color: #fff;
            font-size: 14px;
          }
        }
      }
    }
  }

  .right_box {
    width: 303px;
    min-height: 100px;
    margin-top: 20px;

    // .gonggaoList {
    //   width: 303px;
    //   height: 280px;
    //   // background-image: url(../../assets//zhanghaoguai/gonggao.png);
    //   // background-size: 100% auto;
    // }
    // .xinwengonggao {
    //   width: 303px;
    //   height: 280px;
    //   // background-image: url(../../assets//zhanghaoguai/xinwen.png);
    //   // background-size: 100% auto;
    // }
    // .shouhouanli {
    //   width: 303px;
    //   height: 280px;
    //   // background-image: url(../../assets//zhanghaoguai/shouhou.png);
    //   // background-size: 100% auto;
    // }

    .gaongaos {
      margin-top: 20px;
      width: 303px;
      height: 234px;
      background-color: #211c38;
      border: 1px solid #2e2a44;
      border-radius: 10px;
    }

    .gaongaos:nth-child(3) {
      height: 280px;
    }
  }
}

.guanfangBox {
  width: 100%;
  height: 88px;
  background: #ffffff;
  padding: 20px 0;
  .one {
    display: flex;
    align-items: center;
    .pinpai {
      .titleses {
        font-size: 18px;
        font-family: Microsoft YaHei;
        font-weight: bold;
        color: #333333;
      }

      .renzheng {
        font-size: 14px;
        font-family: Microsoft YaHei;
        font-weight: 400;
        color: #999999;
        margin-top: 5px;
      }
    }
  }
}

.onede {
  // padding: 17px;
  .allboxs {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding-bottom: 16px;
    .titles {
      color: #fff;
      font-size: 16px;
      font-weight: 500;
      margin-left: 10px;
      margin-top: -4px;
    }
    .gengduos {
      color: #fff;
      font-size: 16px;
      margin-top: 3px;
    }
  }

  .onetap {
    display: flex;
    // justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    cursor: pointer;
    .diandian {
      width: 6px;
      height: 6px;
      border-radius: 50%;
      background: #fff;
    }
    .wenzhang {
      width: 256px;
      height: 38px;
      line-height: 38px;
      margin-left: 10px;
      font-size: 14px;
      color: #fff;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}

.is_shows {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fff;
  padding: 20px;
  border-radius: 5px;

  .icon_cha {
    width: 25px;
    height: 25px;
    position: absolute;
    top: 20px;
    right: 20px;
    cursor: pointer;
  }
}

.is_show1,
.is_show2 {
  width: 800px;
  height: 860px;
  box-sizing: border-box;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;

  .content {
    width: 100%;
    min-height: 750px;
    overflow-y: auto;

    .ywjsimg {
      width: 100%;
    }

    img {
      width: 100%;
    }
  }
}

.is_show3 {
  width: 833px;

  // h2 {
  //   text-align: center;
  // }

  .content {
    min-height: 450px;
    overflow-y: auto;

    .fhds {
      width: 800px;
      height: 100px;
    }

    p {
      width: 100%;
      text-align: center;

      span {
        color: #ff0000;
      }
    }

    ul {
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      margin-top: 28px;
      padding: 20px 0;
      display: flex;
      flex-wrap: wrap;

      li {
        margin: 16px 25px;

        img {
          width: 158px;
          height: 157px;
        }

        p {
          text-align: center;
        }
      }
    }

    .bottom {
      .red {
        color: #f44050;
      }

      .ke3 {
        width: 542px;
        height: 58px;
        line-height: 58px;
        background: #eee;
        border-radius: 8px;
        text-align: center;
        font-size: 18px;
        color: #666;
        margin: 42px auto 17px;
      }

      p {
        font-size: 17px;
        color: #f44050;
        text-align: center;
      }
    }
  }
}

.is_show5 {
  width: 1000px;
  min-height: 550px;
  box-sizing: border-box;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;

  .content {
    width: 918px;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;

    .douyin {
      position: absolute;
      top: 50px;
      left: 200px;
    }

    .kuaishou {
      position: absolute;
      top: 50px;
      right: 200px;
    }

    ul {
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      padding: 21px;
      margin-top: 24px;
      background: #fff;
      border-radius: 8px;
      overflow-y: scroll;
      height: 620px;

      li {
        margin-bottom: 35px;
        display: flex;
        align-items: center;

        img {
          width: 67px;
          height: 67px;
          border-radius: 50%;
          margin-right: 16px;
          -o-object-fit: cover;
          object-fit: cover;
        }

        .conter_text {
          width: 230px;

          h6 {
            font-size: 18px;
            color: #1d1d1d;
            text-align: left;
            margin: 0;
          }

          p {
            font-size: 17px;
            color: #666;
            margin-top: 10px;
            text-align: left;
          }

          .shouAcc {
            cursor: pointer;
          }
        }

        button {
          width: 83px;
          height: 33px;
          border: 1px solid #f44050;
          border-radius: 4px;
          color: #f44050;
          text-align: center;
          line-height: 33px;
          cursor: pointer;
          background-color: #fff;
        }
      }
    }
  }
}

.is_show00 {
  width: 500px;
  min-height: 220px;

  .content {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 40px 0;

    img {
      width: 100px;
      margin-bottom: 20px;
    }

    p {
      font-size: 18px;
    }
  }

  .icon_cha {
    width: 40px;
    height: 40px;
    top: 15px;
    right: 15px;
  }
}

.wrapper {
  font-size: 14px;
  width: 100%;
  // background-image: url('../../assets/yudu/home/main-bg.png');
  background-size: 100% 509px;
  background-repeat: no-repeat;
  padding-top: 30px;
  background-color: #1c1632;
}

.banner-box {
  display: flex;
  justify-content: space-between;

  .right-box {
    width: 303px;
    height: 408px;
    background-color: #211c38;
    border: 1px solid #2e2a44;
    border-radius: 10px;
    text-align: center;
    padding: 30px 0;

    .gs_title {
      color: #fff;
      font-size: 20px;
      font-weight: 700;
    }

    .cj_wenzi {
      color: #fff;
      font-size: 16px;
      margin-top: 20px;
      .gs_jy {
        color: #ff0000;
        font-size: 22px;
        font-weight: 700;
        padding: 0 6px;
      }
    }

    .gs_aq {
      color: #bdbbc3;
      font-size: 12px;
      margin-top: 10px;
    }

    .jinrichengjiao {
      // width: ;
      display: flex;
      justify-content: center;
      align-items: center;
      margin-top: 16px;

      .gs_icon {
        width: 2px;
        height: 33px;
        background-color: #fff;
      }

      .gs_total {
        width: 130px;
        height: 50px;
        .gs_name {
          color: #fff;
          font-size: 12px;
        }
        .gs_sun {
          color: #e53721;
          font-size: 22px;
          font-weight: 700;
          margin-top: 5px;
        }
      }
    }

    .gs_btn {
      margin-top: 20px;
      margin-bottom: 33px;
      align-items: center;
      justify-content: center;
      .btn_mai {
        width: 133px;
        height: 36px;
        border-radius: 30px;
        background-color: #ed4024;
        border: none;
        color: #fff;
      }
    }

    .heihaochaxun {
      width: 260px;
      margin: 0 auto;
      // height: 109px;
      // background: #ffffff;
      // border: 1px solid #efefef;
      border-radius: 10px;
      .top {
        display: flex;
        justify-content: space-around;

        .top-title {
          color: #bdbbc3;
          // padding: 10px 0px;
          width: 50%;
          height: 29px;
          line-height: 29px;
          text-align: center;
          font-size: 12px;
          cursor: pointer;
          // background-color: #f9f9f9;
          // border: 1px solid #efefef;
        }
      }

      .chaxun {
        width: 260px;
        height: 110px;
        background-color: #2d2943;
        border: 1px solid #2e2a44;
        margin-top: 10px;
        // padding: 15px 10px 10px 10px;
        // display: flex;
        // justify-content: space-evenly;
        ::v-deep .el-input__wrapper {
          background-color: #221f33;
        }
        .m-2 {
          --el-input-focus-border: #dcdfe6;
          --el-input-focus-border-color: #dcdfe6;
        }

        ::v-deep .el-input__inner {
          border: none !important;
          color: #fff;
        }
      }
    }

    // background-color: pink;
    // .maihao {
    //   width: 100%;
    //   height: 110px;
    //   background-image: url(../../assets/guaishouwan/woyaomai.png);
    //   background-size: 100% auto;
    //   text-align: center;
    //   color: #f9f9f9;
    //   font-size: 28px;
    //   line-height: 110px;
    // }

    // .maihaos {
    //   margin-top: 10px;
    //   width: 100%;
    //   height: 110px;
    //   background-image: url(../../assets/guaishouwan/woyaomaii.png);
    //   background-size: 100% auto;
    //   text-align: center;
    //   color: #f9f9f9;
    //   font-size: 28px;
    //   line-height: 110px;
    // }

    // .zhinan {
    //   margin-top: 10px;
    //   width: 303px;
    //   height: 90px;
    //   background: #ffffff;
    //   border: 1px solid #efefef;
    //   border-radius: 10px;
    // }
  }

  .tongji-box {
    height: 302px;
    width: 387px;
    background-image: linear-gradient(180deg, #5eaeff 0%, #3c80ff 100%);
    box-shadow: 0px 2px 6px 0px rgba(0, 0, 0, 0.04);
    border-radius: 20px;
    padding: 20px;
    padding-bottom: 0;
    box-sizing: border-box;

    .tit {
      width: 102px;
      height: 36px;
      margin: 0 auto;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .chengjiao {
      width: 347px;
      height: 51px;
      margin-top: 20px;
      line-height: 51px;
      background: rgba(255, 255, 255, 0.05);
      box-shadow: 0px 4px 12px 0px rgba(0, 0, 0, 0.04);
      border-radius: 20px;
      border: 1px solid rgba(255, 255, 255, 0.6);
      color: #fff;
      text-align: center;

      .num {
        color: #ffdd1c;
        font-size: 26px;
      }
    }

    .goods-yesday {
      margin-top: 20px;
      color: #fff;
      align-items: center;

      > div {
        font-size: 16px;
        text-align: center;
        width: 50%;
      }

      .shu {
        width: 2px;
        height: 45px;
        background-color: rgba(255, 255, 255, 0.2);
      }

      .goodsnum {
        // border-right: 2px solid rgba(255, 255, 255, 0.2);
      }

      .num {
        font-size: 24px;
        margin-top: 12px;
        font-family: '数字专用';
      }
    }

    .btns {
      margin-top: 20px;

      .el-button {
        width: 163px;
        height: 36px;
        color: #3c80ff;
        font-size: 16px;
        line-height: 17px;
      }

      .icon {
        display: inline-block;
        vertical-align: top;
        width: 16px;
        height: 16px;
        background-size: 100% 100%;
      }

      .woyaomai {
        .icon {
          background-image: url('../../assets/yudu/home/wym.png');
        }
      }

      .woyaomaii {
        .icon {
          background-image: url('../../assets/yudu/home/wymm.png');
        }
      }

      .zhinan {
        margin-top: 10px;
        font-size: 16px;
        color: rgba(255, 255, 255, 0.8);
        text-align: center;
        cursor: pointer;
      }
    }
  }

  .banner {
    width: 877px;
    height: 468px;
    border-radius: 0 0 10px 10px;
  }
}

// <!-- 消息通告 -->
.information {
  width: 1000px;
  height: 40px;
  background-color: #fff;
  margin: 0 auto;
  box-shadow: 0 0 8px #eee;
  margin-top: 10px;
  margin-bottom: 40px;
  border-radius: 5px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: relative;
  // margin-right: 50px;

  .le {
    display: flex;
    align-items: center;

    .title {
      min-width: 100px;
      display: block;
      margin-left: 20px;
      margin-right: -10px;
      margin-top: 0;

      p {
        font-size: 17px;
        font-family: YouSheBiaoTiHei;
        font-weight: 700;
        color: #f44050;
        font-style: oblique;

        span {
          width: 600px;
          font-size: 17px;
          font-family: YouSheBiaoTiHei;
          font-weight: 700;
          color: #323232;
          font-style: oblique;
        }
      }
    }

    .text {
      font-size: 14px;
      color: #000;
      border-left: 1px solid #ddd;
      padding-left: 25px;
      position: relative;

      .wenben {
        font-size: 16px;
        font-family: YouSheBiaoTiHei;
        font-style: oblique;
        font-weight: 700;
        margin-right: 30px;
      }

      .three {
        position: absolute;
        top: 0;
        right: 10px;
        padding-left: 50px;
        font-size: 15px;
        color: #181717;
        display: flex;
        align-items: center;

        .four {
          font-size: 20px;
          color: red;
        }
      }

      .desc {
        font-size: 15px;
        color: #181717;
        display: inline-block;
        width: 750px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }
  }
}

.youxi_list {
  width: 1000px;
  margin: 0 auto;

  .top {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;

    .le {
      display: flex;
      align-items: center;

      img {
        width: 20px;
      }

      span {
        margin-left: 15px;
        font-size: 22px;
        font-weight: 700;
        color: #1d1d1d;
      }
    }

    .ri {
      font-size: 16px;
      color: #777;
      cursor: pointer;
      display: flex;
      align-items: center;

      img {
        margin-left: 10px;
      }
    }
  }

  .list {
    background-color: #fff;
    padding: 47px 42px 22px;
    border-radius: 8px;

    .box {
      width: 92px;
      // height: 92px;
      border-radius: 17px;
      object-fit: cover;

      img {
        width: 100%;
      }

      .text {
        width: 100px;
        font-size: 20px;
        margin-top: 27px;
        color: #333;
        text-align: center;
      }
    }
  }
}

.f_j_c {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

// <!-- 业务区 -->
.business {
  width: 1000px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 20px;

  .li {
    display: block;
    width: 18.3%;
    border-radius: 0 0 7px 0;
    // 鼠标样式
    cursor: pointer;

    img {
      width: 100%;
      height: 100%;
    }
  }
}

.banner {
  width: 744px;
  height: 416px;
  // background-color: @bannerBg;
}

.hotgames-other {
  margin-top: 20px;

  .game-type {
    width: 793px;
    height: 346px;
    background-image: url('../../assets/yudu/home/model-bg.png');
    background-size: 100% 192px;
    background-repeat: no-repeat;
    background-color: #fff;
    border-radius: 20px;

    .game-items-wrapper {
      padding-left: 20px;
      display: flex;
      flex-wrap: wrap;
      margin: 20px 0 30px;

      // justify-content:space-around;
      .game-item {
        width: 109px;
        height: 109px;
        border-radius: 20px;
        margin-bottom: 20px;
        cursor: pointer;
        margin-right: 20px;

        &:nth-of-type(6n) {
          margin-right: 0;
        }

        &:hover {
          transform: scale(1.1);
          transition: all 0.3s;
        }

        .game-icon {
          width: 100%;
          height: 100%;
        }
      }
    }
  }

  .other-box {
    width: 387px;
    height: 346px;
    background-image: url('../../assets/yudu/home/rg-top-bg.png');
    background-size: 100% 192px;
    background-repeat: no-repeat;
    background-color: #fff;
    border-radius: 20px;

    .yanzheng {
      .input-kefu {
        padding: 10px;
        display: flex;
        justify-content: space-between;

        .el-input {
          width: 269px;
          height: 42px;
          // background: #F5F5F5;
          border-radius: 10px;
          // border: 1px solid #CCCCCC;
        }

        .el-button {
          height: 40px;
          color: #fff;
          width: 68px;
          background: #3c80ff;
          border-radius: 10px;
        }
      }
    }

    .other-btn-box {
      width: 100%;
      padding: 20px;
      padding-top: 10px;
      flex-wrap: wrap;

      .otherbtn-item {
        width: 70px;
        text-align: center;
        margin-right: 50px;
        margin-bottom: 20px;

        img {
          width: 70px;
          height: 70px;
        }

        .tit {
          margin-top: -15px;
        }
      }
    }
  }
}

.zhubo-news {
  margin-top: 18px;

  .news {
    .gonggao {
      width: 387px;
      height: 166px;
      box-sizing: border-box;
      background-image: url('../../assets/yudu/home/little-bg.png');
      background-size: 100% 140px;
      background-repeat: no-repeat;
      background-color: #fff;
      margin-bottom: 20px;
      padding: 10px;
      border-radius: 20px;

      .newstit {
        line-height: 32px;
        font-weight: bold;
        padding: 0 10px;

        .tit {
          color: #333;
          font-size: 18px;
          align-items: center;
        }

        .icon {
          width: 25px;
          height: 25px;
          background-image: url('../../assets/yudu/home/laba.png');
          background-size: 100% 100%;
          margin-right: 5px;
        }

        .icon2 {
          background-image: url('../../assets/yudu/home/rczm.png');
        }
      }

      .newlist {
        padding-top: 10px;
        box-sizing: border-box;
        color: #666;
        padding-left: 10px;

        .newitem {
          width: 340px;
          line-height: 30px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;

          .dia {
            display: inline-block;
            width: 4px;
            height: 4px;
            border-radius: 50%;
            background: @primaryColor;
            vertical-align: middle;
            margin-right: 10px;
          }
        }
      }
    }
  }
}

.zhubo {
  background: #211c38;
  // border: 1px solid #EFEFEF;
  border-radius: 20px;
  width: 877px;
  height: 280px;
  margin-top: 10px;
}

.zhubo-cont {
  padding: 20px;
  height: 211px;
  margin-top: 12px;
  // overflow: hidden;

  .el-carousel__container {
  }

  ::v-deep .el-carousel__arrow.el-carousel__arrow--left {
    color: #fff;
    background-color: #565268;
    .el-icon {
      font-size: 20px;
      margin-top: 3px;
    }
  }

  ::v-deep .el-carousel__arrow.el-carousel__arrow--right {
    color: #fff;
    background-color: #565268;
    .el-icon {
      font-size: 20px;
      margin-top: 3px;
    }
  }

  ::v-deep .el-carousel__arrow.el-carousel__arrow--left:hover {
    background-color: #ea4c29;
  }
  ::v-deep .el-carousel__arrow.el-carousel__arrow--right:hover {
    background-color: #ea4c29;
  }

  .el-carousel-item-box {
    width: 621px;
    margin: 0 auto;
  }

  img {
    width: 233px;
    height: 131px;
    margin-right: 10px;
    border-radius: 10px;
  }

  img:nth-of-type(3n) {
    margin-right: 0;
  }
}

.right {
  .buy-sale {
    font-size: 28px;
    font-weight: bold;
    color: #f9f9f9;
    text-indent: 110px;

    .buy,
    .sale {
      width: 248px;
      height: 118px;
      background-size: 248px 118px;
      line-height: 110px;
      cursor: pointer;
      margin-bottom: 3px;
    }

    .buy {
      background-image: url('../../assets/icon/buy-bg.png');
    }

    .sale {
      background-image: url('../../assets/icon/sale-icon.png');
    }

    .zhinan {
      text-indent: 0;
      width: 100%;
      font-size: 14px;
      text-align: center;
      color: #aaa;
      //         width: 230px;
      height: 90px;
      background: #ffffff;
      border: 1px solid #efefef;
      border-radius: 10px;
      padding-top: 15px;
      box-sizing: border-box;

      > div {
        width: 100px;
        cursor: pointer;
      }

      img {
        width: 34px;
        height: 34px;
      }
    }
  }

  .news {
    margin-top: 10px;
  }
}

.dingji {
  margin-top: 10px;
  background: #211c38;
  border: 1px solid #2e2a44;
  border-radius: 10px;
  .cont {
    .dingjis {
      font-size: 18px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #fff;
      margin-top: 6px;
      margin-left: 10px;
      margin-right: 46px;
    }
    span {
      // padding: 0 30px;
      width: 100px;
      height: 46px;
      line-height: 46px;
      font-size: 16px;
      cursor: pointer;
      text-align: center;
      color: #dad7d7;
      overflow: hidden;
      // border-radius: 10px 10px 0px 0px;
      margin-right: 50px;
      margin-top: 7px;

      .icon {
        display: none;
        width: 40px;
        height: 4px;
        // background: #3c80ff;
        border-radius: 2px;
        margin: 0 auto;
        margin-top: -6px;
      }

      &.active {
        width: 100px;
        // height: 46px;
        // text-align: center;
        // line-height: 46px;
        // background: #96ce20;
        // border-radius: 10px 10px 0px 0px;
        // color: #fff;
        // margin-top: 7px;
        // padding: 0 20px;
        height: 36px;
        text-align: center;
        line-height: 36px;
        overflow: hidden;
        background: linear-gradient(to right, #e8592f, #ee3c22);
        border-radius: 20px;
        color: #fff;

        // .icon {
        //   display: block;
        // }
      }
    }
  }

  .more {
    font-size: 16px;
    color: #777;
    cursor: pointer;
    display: flex;
    // align-items: center;
    margin-top: 2px;
  }

  .djzh-cont {
    // padding: 10px;
    padding: 20px;

    > .list {
      flex-wrap: wrap;
      display: flex;
    }

    .list-item {
      width: 276px;
      height: 321px;
      box-sizing: border-box;
      flex-shrink: 0;
      background: linear-gradient(to bottom, #462d37, #462d37, #211c38);
      margin-bottom: 15px;
      border-radius: 10px;
      cursor: pointer;
      margin-right: 18px;
      overflow: hidden;
      padding-bottom: 15px;
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

      // .zhezhaoceng {
      //   width: 276px;
      //   height: 288px;
      //   position: absolute;
      //   background-color: #fff;
      //   bottom: 0;
      //   right: 10px;
      // }

      &:nth-of-type(4n) {
        margin-right: 0;
      }

      .sign {
        width: 56px;
        height: 21px;
        line-height: 27px;
        text-align: center;
        background-image: url('../../assets/yudu/home/signbg.png');
        background-size: 100% 100%;
        position: absolute;
        top: 2%;
        left: 4%;
        right: 0;
        color: #fff;
        font-size: 12px;
      }

      .image {
        width: 100%; /* 设置图片宽度为容器宽度 */
        height: auto; /* 图片高度自动调整 */
        transition: transform 0.3s ease; /* 平滑变换 */
      }

      .img:hover .image {
        transform: scale(1.2); /* 鼠标悬停时放大1.2倍 */
      }

      .img {
        // width: 285px;
        // width: 100%; /* 设置图片宽度为容器宽度 */
        // // height: auto; /* 图片高度自动调整 */
        // transition: transform 0.3s ease; /* 平滑变换 */
        height: 165px;
        overflow: hidden;
        border-radius: 10px;

        img {
          width: 100%;
          min-height: 165px;
        }

        // border-radius: 10px 10px 0 0;
        // overflow: hidden;
      }

      .good-content {
        padding: 0 10px;
        padding-top: 10px;

        .name {
          // height: 66px;
          font-size: 14px;
          overflow: hidden;
          margin-bottom: 5px;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2; // 超出多少行
          -webkit-box-orient: vertical;
          // font-weight: 400;
          color: #fff;
          // line-height: 24px;
          margin-top: 5px;
        }

        .game-type {
          margin: 0;
          font-size: 12px;
          color: #777777;
          line-height: 17px;
          margin-bottom: 5px;
        }
      }

      .price-link {
        color: #777;
        font-size: 12px;
        align-items: center;
        margin-bottom: 7px;

        .yang {
          font-size: 12px;
        }

        .price {
          color: #ff0000;
          font-size: 18px;
          line-height: 0px;
          font-weight: bold;
          margin: 0;
        }
      }
    }
  }
}

// .topbar {
//   line-height: 42px;
//   // padding: 0 0 0 20px;
//   height: 42px;
//   border-radius: 10px;
//   margin: 30px 0 0px 0;

//   .tit {
//     font-size: 18px;
//     font-weight: bold;
//   }

//   .cont {
//     display: flex;
//     align-items: center;

//     img {
//       width: 28px;
//       height: 28px;
//       margin-right: 10px;
//     }

//     h4 {
//       margin: 0;
//       // font-size: 17px;
//       font-size: 18px;
//       font-weight: 700;
//       color: #1d1d1d;
//       // color: #333;
//       // font-weight: 700;
//       margin-right: 25px;
//       padding-left: 2px;
//     }
//   }

//   .more {
//     font-size: 16px;
//     color: #777;
//     cursor: pointer;
//     display: flex;
//     align-items: center;

//     img {
//       width: 16px;
//       margin-left: 10px;
//     }
//   }
// }
.topbar::after {
  content: '';
  position: absolute;
  left: 20px;
  bottom: 0;
  right: 120px;
  width: 100%;
  height: 2px; /* 这是边框的初始宽度 */
  background-image: linear-gradient(to right, #d95132, transparent);
}

.topbar {
  height: 53px;
  line-height: 53px;
  position: relative;
  // border-bottom: 3px solid #a94233;
  padding: 2px 10px 12px 20px;
  background-color: #211c38;
  border-radius: 10px 10px 0 0;

  .tit {
    font-size: 16px;
    font-weight: bold;
    color: #fff;
  }

  .cont {
    .dingjis {
      font-size: 16px;
      font-family: Microsoft YaHei;
      font-weight: 500;
      color: #fff;
      margin-top: 6px;
      margin-left: 10px;
      margin-right: 46px;
    }
    span {
      // padding: 0 30px;
      // width: 90px;
      height: 46px;
      line-height: 46px;
      font-size: 16px;
      cursor: pointer;
      color: #666666;
      // border-radius: 10px 10px 0px 0px;
      margin-right: 30px;
      margin-top: 7px;

      .icon {
        display: none;
        width: 40px;
        height: 4px;
        // background: #3c80ff;
        border-radius: 2px;
        margin: 0 auto;
        margin-top: -6px;
      }

      &.active {
        width: 90px;
        height: 46px;
        text-align: center;
        line-height: 46px;
        background: #96ce20;
        border-radius: 10px 10px 0px 0px;
        color: #fff;
        margin-top: 7px;

        // .icon {
        //   display: block;
        // }
      }
    }
  }

  .more {
    font-size: 16px;
    color: #777;
    cursor: pointer;
    display: flex;
    // align-items: center;
    margin-top: 2px;
  }

  // :deep(.more .el-icon.icon){
  //   padding-top: 10px !important;
  // }
}

.topbarer::after {
  content: '';
  position: absolute;
  left: 20px;
  bottom: 0;
  right: 120px;
  width: 271px;
  height: 2px; /* 这是边框的初始宽度 */
  background-image: linear-gradient(to right, #d95132, transparent);
}

.topbarer {
  width: 263px;
  height: 53px;
  line-height: 53px;
  position: relative;
  // border-bottom: 3px solid #a94233;
  padding: 2px 0px 12px 20px;
  background-color: #211c38;
  border-radius: 10px 10px 0 0;

  .tit {
    font-size: 18px;
    font-weight: bold;
    color: #fff;
  }

  .cont {
    .dingjis {
      font-size: 18px;
      font-family: Microsoft YaHei;
      font-weight: bold;
      color: #fff;
      margin-top: 6px;
      margin-left: 10px;
      margin-right: 46px;
    }
    span {
      // padding: 0 30px;
      // width: 90px;
      height: 46px;
      line-height: 46px;
      font-size: 16px;
      cursor: pointer;
      color: #666666;
      // border-radius: 10px 10px 0px 0px;
      margin-right: 30px;
      margin-top: 7px;

      .icon {
        display: none;
        width: 40px;
        height: 4px;
        // background: #3c80ff;
        border-radius: 2px;
        margin: 0 auto;
        margin-top: -6px;
      }

      &.active {
        width: 90px;
        height: 46px;
        text-align: center;
        line-height: 46px;
        background: #96ce20;
        border-radius: 10px 10px 0px 0px;
        color: #fff;
        margin-top: 7px;

        // .icon {
        //   display: block;
        // }
      }
    }
  }

  .more {
    font-size: 16px;
    color: #777;
    cursor: pointer;
    display: flex;
    // align-items: center;
    margin-top: 2px;
  }

  // :deep(.more .el-icon.icon){
  //   padding-top: 10px !important;
  // }
}

.title {
  text-align: left;
  font-size: 28px;
  line-height: 28px;
  color: #333;
  font-weight: 600;
  margin-top: 24px;
  // margin-bottom: 8px;
}

.goMore {
  line-height: 70px;
  color: #6c6c6c;
  font-size: 16px;
  cursor: pointer;
}

.link {
  cursor: pointer;
}

.fixed {
  position: fixed;
  right: 50px;
  // bottom: 44.2%;
  // transform: translateY(50%);
  top: 271px;
  z-index: 999;
}

.lefted {
  position: fixed;
  left: 26px;
  // bottom: 44.2%;
  // transform: translateY(50%);
  top: 236px;
  z-index: 999;
}
.download-btn {
  position: fixed;
  bottom: 0;
  width: 1200px;
  box-shadow: 0 0 5px 0 rgba(0, 0, 0, 0.1);
  .btn-icon {
    position: absolute;
    width: 20px;
    height: 20px;
    right: 5px;
    top: 5px;
    color: #000;
    font-size: 20px;
    cursor: pointer;
  }
}

:deep(.el-carousel__arrow) {
  // background-color: #3c80ff;
}

:deep(.el-carousel__arrow--right) {
  right: 5px;
}

:deep(.el-carousel__arrow--left) {
  left: 5px;
}

.icon_box_all {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 12px;
  .icon_new {
    width: 81px;
    height: 26px;
    margin-right: 5px;
    .img_box_new {
      width: 100%;
      height: 100%;
    }
  }

  .icon_new:nth-child(3n) {
    margin-right: 0;
  }
}
</style>
<style lang="less">
.user-pop-over[x-placement^='right'] .popper__arrow {
  border-right-color: #f56e48 !important;
}

.user-pop-over[x-placement^='right'] .popper__arrow::after {
  border-right-color: #f56e48 !important;
}
.user-pop-over.el-popover {
  color: white;
  height: 288px !important;
  overflow: hidden;
  margin-top: -56px !important;
  background-color: #1b1b1d !important;
  border-color: #1b1b1d !important;
}

.user-pop-over .el-popover__title {
  color: #f5a416 !important; /* 修改为红色 */
}
</style>