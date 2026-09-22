<template>
  <section class="container">
    <div class="top-box">
      <div class="top-cont w1200">
        <div class="topbar location">
          <el-breadcrumb separator=">">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/index/accountOrder' }">全部游戏</el-breadcrumb-item>
            <el-breadcrumb-item>{{ goodDetail.gameName }}</el-breadcrumb-item>
            <el-breadcrumb-item>商品详情</el-breadcrumb-item>
          </el-breadcrumb>
        </div>

        <div class="good-content">
          <!-- 左侧商品图 -->
          <div class="video-imgs-wrapper">
            <!-- <el-carousel
              indicator-position="outside"
              style="width: 510px; height: 290px"
              v-if="goodDetail.urlArr && goodDetail.urlArr.length"
            >
              <el-carousel-item
                v-for="item in goodDetail.urlArr"
                :key="item"
                style="width: 510px; height: 290px"
              >
                <el-image :src="item.url" ></el-image>
              </el-carousel-item>
            </el-carousel> -->
            <div class="ziying" v-if="goodDetail.accountType == 1">
              <img style="width: 80px; height: 80px" src="../../assets/biaoqian/zybg.png" alt="" />
            </div>
            <div class="tejia" v-if="goodDetail.isOnSpecialOffer == 1">
              <img style="width: 80px; height: 80px" src="../../assets/biaoqian/tjbg.png" alt="" />
            </div>
            <img :src="goodDetail.titleImg" alt="" style="width: 510px; height: 290px" />
            <!-- <enlarge-images style="z-index: 100000000000000" :images="images" /> -->
          </div>

          <!-- 右侧商品信息 -->
          <div class="right">
            <div class="t_top space-between">
              <div class="left">
                <div class="text" style="color: #fff">{{ goodDetail.gameName }}</div>

                <div class="icon_box_all_one" style="margin-top: 16px">
                  <div class="icon_new" v-if="goodDetail.accountType == 2">
                    <img class="img_box_new" src="../../assets/biaoqian/gfds.png" alt="" />
                  </div>
                  <div class="icon_new" v-if="goodDetail.isGuarantee == 2">
                    <img class="img_box_new" src="../../assets/biaoqian/mfbp.png" alt="" />
                  </div>
                  <div class="icon_new" v-if="goodDetail.isGuarantee == 1">
                    <img class="img_box_new" src="../../assets/biaoqian/xybp.png" alt="" />
                  </div>
                </div>
                <!-- <img src="../../assets/taohaoqu/icon/zhichibaopei.png" alt="" />
                <img src="../../assets/taohaoqu/icon/pingtaifahuo.png" alt="" /> -->
              </div>
              <div class="right">
                <div class="shoucang alignItems" @click="shoucang">
                  <img
                    :src="goodDetail.isCollect == '1' ? Collet : noCollet"
                    alt=""
                    style="width: 16px; height: 16px"
                  />
                  <span :style="{ color: goodDetail.isCollect == '1' ? 'red' : '#fff' }">{{
                    goodDetail.isCollect == '1' ? '已收藏' : '加入收藏'
                  }}</span>
                </div>
              </div>
            </div>

            <div class="top" v-if="lomaerd == false">
              <p>{{ goodDetail.des }}</p>
            </div>

            <div class="" v-else style="min-height: 140px">
              <p style="font-size: 20px; font-weight: 400; line-height: 35px">
                {{ goodDetail.des }}
              </p>
            </div>

            <div class="chushuojiage">
              <p style="padding-left: 20px">
                <span>出售价格：</span>
                <span style="color: #e75d31; font-size: 24px">¥</span>
                <span style="color: #e75d31; font-size: 24px">{{ goodDetail.newPrice }}</span>
              </p>
              <div
                style="
                  display: flex;
                  align-items: center;
                  width: 126px;
                  height: 45px;
                  background-color: #e75d31;
                  justify-content: center;
                  border-radius: 30px;
                  margin-right: 20px;
                  cursor: pointer;
                "
                @click="fenxiang"
              >
                <img
                  style="width: 24px; height: 24px"
                  src="../../assets//guaishouwan/fenxiang.png"
                  alt=""
                />
                <p style="color: #fff; font-size: 24px; margin-left: 10px">分享</p>
              </div>
            </div>

            <div class="redu">
              <div style="color: #818f91; margin-top: 10px; font-size: 14px">热度：</div>
              <div style="margin-top: 0px">
                <el-rate size="large" v-model="redu" disabled text-color="#ff9900" />
              </div>
            </div>

            <div class="bum">
              <div class="conte">
                <div class="li p">
                  <!-- 商品编号： -->
                  <div class="niu">商品编号：</div>
                  <div class="lu">{{ goodDetail.id }}</div>
                </div>

                <div class="li p">
                  <!-- 商品类别： -->
                  <div class="niu">游戏名称：</div>
                  <div class="lu">{{ goodDetail.gameName }}</div>
                </div>

                <div class="li p">
                  <!-- 商品类别： -->
                  <div class="niu">上架时间：</div>
                  <div class="lu">{{ goodDetail.recoveryTime }}</div>
                </div>
              </div>
            </div>
            <div class="bum">
              <div class="conte">
                <div class="li p" v-if="goodDetail.f10 && goodDetail.gameName == '绝地求生'">
                  <div class="niu" style="width: 66px">豪华版：</div>
                  <div class="lu">{{ goodDetail.f10 == '56816' ? '是' : '否' }}</div>
                </div>

                <div class="li p" v-if="goodDetail.f9 && goodDetail.gameName == '绝地求生'">
                  <div class="niu">生存等级：</div>
                  <div class="lu" style="min-width: 56px">
                    {{
                      goodDetail.f9 == '58609'
                        ? '一阶段'
                        : goodDetail.f9 == '58610'
                        ? '二阶段'
                        : goodDetail.f9 == '58611'
                        ? '三阶段'
                        : goodDetail.f9 == '58612'
                        ? '四阶段'
                        : goodDetail.f9 == '58613'
                        ? '五阶段'
                        : ''
                    }}
                  </div>
                </div>

                <div class="li p" v-if="goodDetail.f8 && goodDetail.gameName == '绝地求生'">
                  <div class="niu">信用分：</div>
                  <div class="lu">
                    {{
                      goodDetail.f8 == '58676'
                        ? '信用分0'
                        : goodDetail.f8 == '58616'
                        ? '信用分1'
                        : goodDetail.f8 == '58617'
                        ? '信用分2'
                        : goodDetail.f8 == '58618'
                        ? '信用分3'
                        : goodDetail.f8 == '58619'
                        ? '信用分4'
                        : goodDetail.f8 == '58620'
                        ? '信用分5'
                        : ''
                    }}
                  </div>
                </div>
              </div>
            </div>
            <div class="but" v-if="goodDetail.saleState == 1">
              <el-button
                @click="openQQ"
                style="border: none; background-color: #e75d31; color: #fff; border-radius: 30px"
                >联系客服</el-button
              >
              <el-button
                @click="buyNow"
                style="border: none; background-color: #e75d31; color: #fff; border-radius: 30px"
                >立即购买</el-button
              >
            </div>
            <div class="yishou" v-else>该商品已出售，换个商品看看吧！</div>
          </div>
        </div>
      </div>
    </div>
    <!-- <div class="jiaoyijianjie">
      <div class="topo">交易流程简介</div>
      <div class="botom space-between">
        <div class="li">
          <img src="../../assets/taohaoqu/detail/xiadan1.png" alt="" />
          <p>下单</p>
        </div>
        <div class="li">
          <img src="../../assets//fh-icon/jyjj_0.png" alt="" />
        </div>
        <div class="li">
          <img src="../../assets/taohaoqu/detail/yanhao1.png" alt="" />
          <p>验号</p>
        </div>
        <div class="li">
          <img src="../../assets//fh-icon/jyjj_0.png" alt="" />
        </div>
        <div class="li">
          <img src="../../assets/taohaoqu/detail/huanbang1.png" alt="" />
          <p>换绑</p>
        </div>
        <div class="li">
          <img src="../../assets//fh-icon/jyjj_0.png" alt="" />
        </div>
        <div class="li">
          <img src="../../assets/taohaoqu/detail/fahuo1.png" alt="" />
          <p>发货</p>
        </div>
        <div class="li">
          <img src="../../assets//fh-icon/jyjj_0.png" alt="" />
        </div>
        <div class="li">
          <img src="../../assets/taohaoqu/detail/wancheng.png" alt="" />
          <p>完成</p>
        </div>
      </div>
    </div> -->

    <!-- 内容选项 -->
    <div class="w1200 flex fbt" style="margin-top: 10px">
      <div class="good-detail" v-loading="loading">
        <el-tabs class="custom-tabs">
          <el-tab-pane label="商品详情">
            <div></div>
            <div class="miaoshu">
              <div class="title">
                <span class="icon" style="width: 6px; background-color: #f7423f"></span>
                商品介绍
              </div>
              <div :class="zhankai ? 'txt-box1' : 'txt-box'" v-if="attrsTxt && gameDicts">
                <div>
                  <p style="color: #fff">
                    【基本信息】 {{ goodDetail.systemName }}&ensp;{{
                      goodDetail.serverName
                    }}&ensp;{{ goodDetail.groupName }}
                  </p>
                </div>
                <div class="p">
                  <p>【亮点描述】 {{ goodDetail.des }}</p>
                  <!-- <span @click="zhankai = !zhankai" v-text="zhankai ? '折叠' : '展开'"></span> -->
                </div>
              </div>
            </div>

            <div class="detail-img" v-if="goodDetail.urlArr && goodDetail.urlArr.length">
              <div class="title">
                <span style="width: 6px; background-color: #f7423f"></span>
                商品详情
              </div>
              <div>
                <el-image
                  v-for="(item, index) in goodDetail.urlArr"
                  :key="`gd${index}`"
                  :src="item.url"
                  alt="游戏截图"
                  :zoom-rate="1.2"
                  :preview-src-list="urlArr"
                  :initial-index="index"
                  fit="cover"
                  z-index="9999"
                  lazy
                  class="el-image"
                ></el-image>
                <!-- <div class="cooperate" v-for="(item, index) in goodDetail.urlArr" :key="item.id">
                    <div class="img" v-html="item.url"></div>
                  </div> -->
              </div>
            </div>
          </el-tab-pane>

          <el-tab-pane label="交易须知">
            <div class="cooperate">
              <div class="img" v-html="jyxzimg"></div>
            </div>
            <!-- <div class="liucheng ql-editor" v-html="jyxzimg"></div> -->
            <!-- <img style="width: 1160px" src="../../assets/guaishouwan/jiaoyi.png" alt="" />
            <img style="width: 1160px" src="../../assets/guaishouwan/jiaoyitwo.png" alt="" /> -->
          </el-tab-pane>
          <el-tab-pane label="包赔介绍">
            <div class="cooperate">
              <div class="img" v-html="baopeijieshao"></div>
            </div>
            <!-- <div class="liucheng ql-editor" v-html="baopeijieshao"></div> -->
            <!-- <img style="width: 1160px" src="../../assets/guaishouwan/baopei.png" alt="" /> -->
          </el-tab-pane>
          <el-tab-pane label="官方交流群">
            <div class="qun">
              <div class="li" v-for="(item, index) in qun_list" :key="index">
                <div class="left">
                  <h6>{{ item.name }}：</h6>
                  <p>{{ item.qq }}</p>
                </div>
                <div class="right" @click="copy(item.lianxi)">
                  点击加群
                  <!-- <img src="../../assets/fh-icon/cv.png" alt="" @click="copy(item.lianxi)" /> -->
                </div>
              </div>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>

    <!-- 底部 -->
    <Footer></Footer>
  </section>
</template>

<script>
import config from '@/api/config';
import { RefreshRight, CaretTop, Search, CaretBottom, House, View } from '@element-plus/icons-vue';
import Footer from '@/components/Footer.vue';
import { reactive, toRefs, onMounted, onUnmounted, ref, watch, onActivated, nextTick } from 'vue';
import { useRouter } from 'vue-router';
// import videojs from 'video.js';
// import 'video.js/dist/video-js.css';
import api from '@/api/index';
import kefu from '@/api/kefu';
import care0 from '../../assets/fh-icon/shoucang.png';
import care1 from '../../assets/fh-icon/shoucang2.png';
import { ElMessage } from 'element-plus';

export default {
  name: 'accountorder',
  components: { House, RefreshRight, Search, CaretBottom, CaretTop, View, Footer },
  setup(props, ctx) {
    const route = useRouter();
    const state = reactive({
      dialogVisible: false,
      checkboxValue1: false,
      lomaerd: false,
      goodDetails: {},
      types: 2,
      kefu_list: [],
      images: [],
      is_show3: false,
      qun_list: [
        {
          name: '怪兽玩代售和平精英交流Q群',
          lianxi: 591538954
        },
        {
          name: '怪兽玩代售和平精英交流Q群',
          lianxi: 591538954
        },
        {
          name: '怪兽玩代售和平精英交流Q群',
          lianxi: 591538954
        }
      ],
      visible1: false,
      visible2: false,
      // 商品详情tu
      urlArr: [],

      isshow_id: 0,
      redu: 5,
      accountType: null,
      zhankai: false,
      nameds: '',
      loading: false,
      noCollet: care0,
      Collet: care1,
      userInfo: null,
      goodDetail: {
        IMGS: [],
        videoUrl: '',
        YXLX: '',
        YXDQ: '',
        specialPrice: '',
        ifCollt: '2'
      },
      gameDicts: null, //游戏字典值
      attrs: null, //游戏自定义字段
      attrsTxt: null, //需要展示的 游戏自定义字段
      // tuijianList: null,//推荐商品
      QQgroup: [],
      jyxzimg: '',
      baopeijieshao: ''
    });

    const videoPlayer = ref('detail');
    const myPlayer = ref(null);

    const contactCustomer = () => {
      //console.log('联系客服');
      // window.open('https://tb.53kf.com/code/client/548e30ae833331416eeede707988c68c7/1')
    };
    // 查询字典
    const getDict = () => {
      api.getGameDict({ gameId: state.goodDetail.gameId }).then((res) => {
        state.gameDicts = res.data;
      });
    };
    // 查询自定义字段‘
    const getAttrs = () => {
      api.getAttrsSimp({ gameId: state.goodDetail.gameId }).then((res) => {
        state.attrs = res.rows;
        let tempArr = [];
        if (res.rows && res.rows.length) {
          res.rows.forEach((r) => {
            if (state.goodDetail[r.aavName] && r.isEnable == 0) {
              tempArr.push(r);
            }
          });
        }
        //console.log('tempArr', tempArr);
        state.attrsTxt = tempArr;
      });
    };

    // 监听路由变化
    watch(
      () => route.currentRoute.value.query,
      (newValue, oldValue) => {
        //console.log('%c Line:421 🍡 详情页路由监听', 'color:#465975', route.currentRoute.value);
        if (route.currentRoute.value.path == '/pages/detail/index') {
          getGoodDetail(route.currentRoute.value.query.id);
        }
      },
      { deep: true }
    );
    onActivated(() => {
      let doc = document.getElementById('maincontent');
      doc.scrollTop = 0;
    });

    const getQQ = () => {
      kefu.getQQGrop().then((res) => {
        //console.log('%c Line:293 🍑 res', 'color:#2eafb0', res);
        state.QQgroup = res;
      });
    };
    // 跳转
    const goLinks = (link, query) => {
      if (query) {
        route.push({ path: link, query: query });
      } else {
        route.push({ path: link });
      }
    };
    const goLink = (link, query) => {
      // route.push({ path: link,query:query })
      const { href } = route.resolve({ path: link, query: query });
      window.open(href, '_blank');
    };

    const shoucang = () => {
      //console.log('state.goodDetail.id', state.goodDetail.id);
      //console.log('state.userInfo.userId', state.userInfo.userId);
      //console.log('121212', state.goodDetail.id);
      if (!state.userInfo) {
        // 跳转登录页
        route.push({ path: '/login' });
        return;
      }
      if (state.goodDetail.isCollect == 1) {
        // 已收藏,取消收藏

        kefu
          .delCollect({
            accountId: state.goodDetail.id,
            createId: state.userInfo.userId
          })
          .then((res) => {
            state.goodDetail.isCollect = 0;
            ElMessage({
              type: 'success',
              message: res.msg
            });
          });
      } else {
        // 未收藏，进行收藏
        kefu
          .addCollect({
            accountId: state.goodDetail.id,
            createId: state.userInfo.userId
          })
          .then((res) => {
            state.goodDetail.isCollect = 1;
            ElMessage({
              type: 'success',
              message: res.msg
            });
          });
      }
    };
    const quxiao = () => {};
    const jiaqun = (qq) => {
      window.open(`https://wpa.qq.com/msgrd?v=3&uin=${qq}&site=qq&menu=yes`);
    };
    const getGoodDetail = async (id) => {
      // maincontent.scrollTo(0, 0);
      state.loading = true;
      let res = await api.getGoodDetail({
        id: id
      });
      //console.log('%c Line:343 🍇 res', 'color:#ccaa33', res);
      state.loading = false;
      state.goodDetail = res.data;

      // urlArr
      // res.data.urlArr有数据的话，就把urlArr里的url加到state.urlArr里
      if (res.data.urlArr) {
        // 遍历res.data.urlArr，把每一项的url加到state.urlArr里
        res.data.urlArr.forEach((item) => {
          state.urlArr.push(item.url);
        });
      }

      // state.images.push(state.goodDetail.titleImg)
      // state.images = state.urlArr.splice(0, 5)

      // 把goodsTypeValues转换成数组
      if (state.goodDetail.goodsTypeValues) {
        state.goodDetail.goodsTypeValues = state.goodDetail.goodsTypeValues.split(',');
      }
      getAccountInfo(state.goodDetail.createId);
      getDict();
      getAttrs();
      // if (state.goodDetail.videoUrl) {
      //   nextTick(()=>{
      //     myPlayer.value = videojs(
      //       videoPlayer.value,
      //       {
      //         // poster: state.goodDetail.IMGS[0] || '',
      //         controls: true,
      //         sources: [
      //           {
      //             src: state.goodDetail.videoUrl,
      //             type: 'video/mp4'
      //           }
      //         ],
      //         controlBar: {
      //           remainingTimeDisplay: {
      //             displayNegative: false
      //           }
      //         },
      //         playbackRates: [0.5, 1, 1.5, 2]
      //       },
      //       () => {
      //         myPlayer.value.log('play.....');
      //       }
      //     );
      //   })

      // }
      // nextTick(()=>{
      //   element.body.scrollTop = 0
      // })
    };
    // const getTuijian = () => {
    //   kefu.getRecommend(state.goodDetail.id).then(res => {
    //     //console.log("%c Line:533 🍩 res", "color:#3f7cff", res);
    //     state.tuijianList = res.data
    //   })
    // }

    onUnmounted(() => {
      if (myPlayer.value) {
        myPlayer.value.dispose();
      }
    });
    // 复制方法
    const copy = (text) => {
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

    const fenxiang = () => {
      const obj = location;
      console.log(obj);
      let input = document.createElement('input');
      input.setAttribute('readonly', 'readonly');
      input.setAttribute('value', obj.href);
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

    // 获取账号详情信息+皮肤
    const getAccountInfo = (id) => {
      // //console.log("%c Line:357 🍇 state.goodDetail.gameId", "color:#ccaa33", state.goodDetail.createId);
      kefu.getAccountInfo(id || state.goodDetail.createId).then((res) => {
        //console.log('%c Line:362 🍇 res', 'color:#ccaa33', res);
        // state.accountType = res.data
      });
    };

    // 获取官方交流群
    const getGroup = () => {
      kefu.getGroup().then((res) => {
        //console.log('%c Line:371 🍇 res', 'color:#ccaa33', res);
        state.qun_list = res;
      });
    };

    onMounted(() => {
      getGroup();
      let info = window.localStorage.getItem('userInfo');
      if (info) {
        state.userInfo = JSON.parse(info);
      }
      getQQ();
      // 监听游戏id 查询游戏详情
      getGoodDetail(route.currentRoute.value.query.id);
    });

    // 租用
    const zuyong = (item) => {
      //console.log(item);
      state.goodDetail.newPrice = item.jiage;
    };

    // 开启弹窗
    const show = (item) => {
      // if (item == 1) {
      //   state.is_show3 = true

      // }
      // // 让浏览器滚动条隐藏
      // document.documentElement.style.overflow = 'hidden';

      // refss.value.style.display = 'block'
      document.querySelector('.erweima').style.display = 'block';
    };
    const shows = () => {
      document.querySelector('.erweima1').style.display = 'block';
    };
    //关闭弹框
    const closefn = () => {
      // refss.value.style.display = 'none'
      document.querySelector('.erweima').style.display = 'none';
    };
    const closefn1 = () => {
      // refss.value.style.display = 'none'
      document.querySelector('.erweima1').style.display = 'none';
    };
    const on = () => {
      // state.is_show = true;
    };

    // 关闭弹窗
    const handleChildEvent = (item) => {
      if (item == 1) {
        state.is_show3 = false;
      }
      // 让浏览器滚动条显示
      document.documentElement.style.overflow = 'auto';
    };

    // 获取客服数据
    const getsecurity = () => {
      kefu.getsecurity().then((res) => {
        //console.log('获取客服数据', res);
        state.kefu_list = res;
      });
    };

    //获取客服二维码
    const getInfo = () => {
      kefu.getkefuer(state.types).then((res) => {
        //console.log('获取客服二维码', res);
        state.goodDetails = res[0];
      });
    };

    const zhankaione = () => {
      state.lomaerd = !state.lomaerd;
    };
    // 点击立即下单
    const orderNowClick = () => {
      let isShowBuyTip = localStorage.getItem('isShowBuyTip');
      console.log('%c Line:632 🍪 isShowBuyTip', 'color:#2eafb0', isShowBuyTip);
      if (isShowBuyTip == 2) {
        sureOrder();
      } else {
        state.dialogVisible = true;
      }
    };
    // 进入下单页面
    const sureOrder = () => {
      if (state.checkboxValue1 == true) {
        localStorage.setItem('isShowBuyTip', 2);
      } else {
        localStorage.setItem('isShowBuyTip', 1);
      }
      state.dialogVisible = false;
      route.push({
        path: '/index/sureOrder',
        query: {
          id: state.goodDetail.id,
          img: state.goodDetail.titleImg,
          newPrice: state.goodDetail.newPrice,
          gameName: state.goodDetail.gameName,
          qufu:
            state.goodDetail.gameType === 0
              ? state.goodDetail.systemName + '' + state.goodDetail.carrierName
              : state.goodDetail.groupName + state.goodDetail.serverName,
          des: state.goodDetail.des
        }
      });
    };

    const openQQ = (val) => {
      window.open(
        'https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI0OTMTcxMIzMTEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1'
      );
    };

    // 立即购买
    const buyNow = () => {
      console.log('%c test: ', 'background-color: #3756d4; line-height: 50px; ');
      let token = window.localStorage.getItem('token');
      if (!token) {
        route.replace({ path: '/login' });
      }
    };

    //获取交易须知
    const getjyxz = () => {
      kefu.getdetails('jyxz').then((res) => {
        //console.log(res);
        state.jyxzimg = res[0].noticeContent;
      });
    };

    //获取包赔介绍
    const getbpjs = () => {
      kefu.getdetails('bpjs').then((res) => {
        //console.log(res);
        state.baopeijieshao = res[0].noticeContent;
      });
    };

    onMounted(() => {
      getsecurity();
      getInfo();
      getjyxz();
      getbpjs();
    });

    return {
      ...toRefs(state),
      contactCustomer,
      getGoodDetail,
      getQQ,
      jiaqun,
      shoucang,
      quxiao,
      goLink,
      zhankaione,
      copy,
      goLinks,
      zuyong,
      show,
      on,
      handleChildEvent,
      getsecurity,
      closefn,
      closefn1,
      shows,
      getInfo,
      openQQ,
      buyNow,
      sureOrder,
      orderNowClick,
      getjyxz,
      getbpjs,
      fenxiang
    };
  }
};
</script>

<style scoped lang="less">
.ziying {
  position: absolute;
  top: 0;
  left: 0;
}

.tejia {
  position: absolute;
  top: 0;
  right: 16px;
}

.icon_box_all_one {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  margin-bottom: 10px;
  .icon_new {
    width: 100px;
    height: auto;
    margin-right: 5px;
    .img_box_new {
      width: 100%;
      height: 100%;
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

.is_show3 {
  width: 833px;

  h2 {
    text-align: center;
  }

  .content {
    min-height: 450px;
    overflow-y: auto;

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
        color: #ff8e00;
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
        color: #ff8e00;
        text-align: center;
      }
    }
  }
}

.alignItems {
  display: flex;
  align-items: center;
}

.space-between {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

ul {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
}

.container {
  background-color: #1c1632;
  .top-box {
    // background-image: url("../../assets/yudu/detail/topbg.png");
    // background-repeat: no-repeat;
    // background-size: 100% 346px;

    .top-cont {
      box-sizing: border-box;

      .zu_jiage {
        width: 1200px;
        margin: 0 auto;
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 15px;

        ul {
          width: 100%;
          display: flex;
          justify-content: space-between;
          align-items: center;

          li {
            width: 288px;
            height: 150px;
            background-color: #fff;
            box-shadow: 0 0 8px #ddd;
            border-radius: 5px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            position: relative;
            padding: 10px;
            box-sizing: border-box;

            h6 {
              margin: 10px 0;
              font-size: 15px;
              color: #999;
            }

            p:nth-child(2) {
              font-size: 20px;
              color: #000;

              span {
                font-size: 26px;
                color: #000;
                font-weight: 700;
              }
            }

            p:nth-child(3) {
              width: 250px;
              margin: 0 auto;
              display: block;
              font-size: 15px;
              color: #999;
            }

            img {
              width: 50px;
              height: 50px;
              display: block;
              position: absolute;
              right: 10px;
              bottom: 10px;
            }
          }

          .isshow_id {
            color: #999 !important;
            background-color: #ff8e00;

            h6 {
              color: #fff !important;
            }

            p {
              color: #fff !important;

              span {
                color: #fff !important;
              }
            }
          }
        }
      }
    }
  }

  .jiaoyijianjie {
    width: 1200px;
    height: 210px;
    background-color: #fff;
    margin: 20px auto;
    padding: 0 150px;
    box-sizing: border-box;
    overflow: hidden;

    .topo {
      width: 100%;
      text-align: center;
      margin: 30px 0;
      font-size: 18px;
    }

    .li:nth-of-type(2n-1) {
      width: 38px;
      height: 46px;
      margin-bottom: 17px;

      img {
        width: 100%;
      }

      p {
        width: 100%;
        font-size: 18px;
        text-align: center;
        color: #333;
      }
    }

    .li:nth-of-type(2n) {
      width: 32px;
      height: 11px;
      transform: translateY(-20px);

      img {
        width: 100%;
      }
    }
  }

  .location {
    display: flex;
    padding: 26px 0;
  }
}

.flex {
  align-items: center;

  .cont {
    margin: 0;
    background-color: none;
  }
}

.home-icon {
  margin-right: 5px;
}

.good-content {
  background-color: #1c1632;
  border: 1px solid #38324b;
  border-radius: 10px;
  padding: 15px;
  display: flex;

  .video-imgs-wrapper {
    min-width: 526px;
    height: 415px;
    border-radius: 5px;
    overflow: hidden;
    position: relative;

    .el-carousel__container {
      width: 380px !important;
    }

    .el-carousel__indicators--horizontal el-carousel__indicators--outside {
      bottom: 0px !important;
    }

    // flex: 44;
    .video-js {
      flex-shrink: 0;
      // width: 400px;
      // height: 227px;
      width: 100%;
      height: 100%;
      border-radius: 10px;
    }

    .imgs-wrapper {
      width: 400px;
      height: 227px;
      border-radius: 10px;

      .img {
        width: 100%;
        height: 100%;

        .el-image {
          min-height: 227px;

          .el-image__inner {
            min-height: 227px;
          }
        }
      }
    }
  }

  .imgs-wrapper :deep(.el-image__inner) {
    min-height: 227px;
  }

  .right-text {
    flex: 37;
    margin-left: 20px;

    .signs {
      align-items: center;
    }

    .signlist {
      .sign {
        width: 96px;
        height: 27px;
        background: linear-gradient(90deg, #3a4777 0%, #1b2242 100%);
        border-radius: 14px;
        color: #ffebc2;
        align-items: center;
        justify-content: center;
        font-size: 12px;
        margin-right: 20px;

        .icon {
          width: 13px;
          height: 14px;
          background-image: url('../../assets/yudu/detail/baopeishangjia.png');
          background-size: 100% 100%;
          margin-right: 5px;
        }

        &:nth-of-type(2) .icon {
          background-image: url('../../assets/yudu/detail/baopei.png');
        }

        &:nth-of-type(3) .icon {
          width: 14px;
          height: 13px;
          background-image: url('../../assets/yudu/detail/shouhou.png');
        }
      }
    }

    .good-name {
      margin-top: 10px;
      overflow: hidden;
      line-height: 30px;
      font-size: 18px;
      font-weight: 600;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 4;
    }

    .liulan {
      align-items: center;
      color: #777777;
      font-size: 12px;

      .icon {
        width: 15px;
        height: 11px;
        background-image: url('../../assets/yudu/detail/liulan.png');
        background-size: 100% 100%;
        margin-right: 6px;
      }
    }

    .info {
      margin-top: 15px;
      // justify-content: space-between;
      // border-top: 1px solid #DFDFDF;
      // padding-top: 6px;
      color: #777;
      font-size: 12px;

      .code {
        margin-right: 30px;
      }

      .label {
        color: #999999;
      }
    }

    .price-link {
      text-align: right;

      .kefu-btn {
        font-size: 18px;
        color: #f7f7f7;
        border: none;
        margin-top: 15px;
        width: 200px;
        height: 44px;
        background: linear-gradient(0deg, #ff6700 0%, #ff9400 100%);
        border-radius: 4px;
      }
    }
  }

  .right {
    z-index: 100;
    margin-left: 8px;
    width: 600px;

    .chushuojiage {
      width: 100%;
      height: 78px;
      // line-height: 78px;
      background-color: #2d2a42;
      border-radius: 10px;
      margin: 20px 0;
      display: flex;
      align-items: center;
      justify-content: space-between;
      // padding: 0 30px;
      p {
        span {
          color: #fff;
        }
      }
    }

    .redu {
      display: flex;
      align-items: start;
    }

    .t_top {
      width: 100%;
      margin-bottom: 17px;

      .left {
        display: flex;
        align-items: center;
        font-size: 22px;

        img {
          width: 100px;
          margin-left: 18px;
        }
      }

      .right {
        width: 90px;

        .alignItems {
          img {
            margin-right: 10px;
          }
        }
      }
    }

    .top {
      // height: 140px;
      overflow: hidden;

      p {
        margin: 0;
        color: #fff;
        display: inline-block;
        font-size: 20px;
        font-weight: 400;
        line-height: 30px;
        position: relative;
        // 只显示4行
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 4;
        margin-top: 0;
        // margin-bottom: 10px;
      }
    }

    .liulan {
      margin: 8px 0;

      img {
        margin-right: 10px;
      }
    }

    .bum {
      // background: url(@/assets/img/details_bg.png) #ffe5a5 no-repeat;
      // background-size: cover;
      display: flex;
      flex-direction: column;
      // height: 120px;
      justify-content: space-evenly;
      // padding: 0 10px 7px;
      padding-bottom: 10px;

      .h6 {
        display: flex;
        align-items: center;
        font-size: 18px;
        color: #333;

        p {
          font-weight: 700;
          font-size: 20px;
          color: red;
          margin: 10px 0;

          span:nth-child(2) {
            font-size: 28px;
          }
        }
      }

      .conte {
        display: flex;
        // flex-wrap: wrap;

        .p {
          // width: 280px;
          margin-left: 20px;
          font-size: 14px;
          color: #aaa;
          line-height: 20px;
          margin-top: 5px;
          display: flex;

          .lu {
            color: #fff;
          }

          .niu {
            width: 71px;
          }
        }

        .p:nth-child(1) {
          margin-left: 0;
        }
      }
    }

    .but {
      display: flex;
      justify-content: flex-end;
      align-items: center;
      margin-top: 10px;

      button {
        border: 1px solid #ff8e00;
        background-color: #fff;
        color: #ff8e00;
        margin-right: 21px;
        width: 150px;
        height: 50px;
        // line-height: 50px;
        font-weight: 500;
        font-size: 20px;
        border-radius: 8px;
      }

      button:nth-of-type(2) {
        background-color: #ff8e00;
        color: #fff;
        margin-right: 0px;
      }
    }

    .erweima {
      // float: right;
      margin-left: 251px;
      width: 186px;
      height: 178px;
      background-color: #fff;
      border: 1px solid #f3f0f0;
      border-radius: 8px;
      margin-right: 21px;
      text-align: center;
      position: relative;
      display: none;

      img {
        width: 130px;
        height: 130px;
      }

      span {
        font-size: 14px;
      }

      .anniu {
        width: 20px;
        height: 20px;
        border-radius: 10px;
        background-color: #f44252;
        color: #fff;
        position: absolute;
        top: 0;
        right: 0;
      }
    }

    .erweima1 {
      // float: right;
      margin-left: 429px;
      width: 186px;
      height: 178px;
      background-color: #fff;
      border: 1px solid #f3f0f0;
      border-radius: 8px;
      margin-right: 21px;
      text-align: center;
      position: relative;
      display: none;

      img {
        width: 130px;
        height: 130px;
      }

      span {
        font-size: 14px;
      }

      .anniu {
        width: 20px;
        height: 20px;
        border-radius: 10px;
        background-color: #f44252;
        color: #fff;
        position: absolute;
        top: 0;
        right: 0;
      }
    }
  }
}

.price-btns-box {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  // background-color: #FF8E00;
  // border-radius: 21px;
  padding: 20px 0;

  // margin-top: 20px;
  .left {
    display: flex;
    align-items: center;

    > div {
      display: flex;
      align-items: center;
      margin-right: 25px;

      img {
        width: 15px;
        height: 15px;
        display: block;
        margin-left: 5px;
      }
    }

    .but {
      color: #9370db;
      border: 1px solid #9370db;
      border-radius: 4px;
      padding: 1px 6px;
      font-size: 14px;
    }
  }

  .right {
    display: flex;
    align-items: center;

    > div {
      margin-left: 20px;
    }

    .but1 {
      border-radius: 5px;
      font-size: 17px;
      font-weight: 400;
      height: 50px;
      width: 127px;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #fff;
      background: linear-gradient(90deg, #ff8e00, #ff8e00);
    }

    .but2 {
      img {
        height: 19px;
        margin-right: 5px;
        width: 24px;
      }

      border-radius: 5px;
      font-size: 17px;
      font-weight: 400;
      height: 50px;
      width: 127px;
      display: flex;
      justify-content: center;
      align-items: center;
      background: #f6f2ff;
      border: 1px solid #866df2;
      border-radius: 5px;
      color: #866df2;
      outline: none;
      padding: 0 5px;
    }
  }

  .shoucang {
    // margin-top: 10px;
    cursor: pointer;
    width: 96px;
    height: 27px;
    background: #ffffff;
    border-radius: 14px;
    font-size: 12px;
    display: flex;
    align-items: center;
    justify-content: center;

    img {
      vertical-align: middle;
      margin-right: 5px;
    }

    span {
    }
  }

  .price-btns {
    background-color: #fff;
    border-radius: 20px;
    padding: 15px;

    .price-lc {
      align-items: center;
    }

    .price {
      color: #ff3c3c;
      font-size: 40px;
      display: flex;
      padding-left: 20px;
      line-height: 40px;
      margin-right: 20px;
      display: flex;
      align-items: baseline;

      .fh {
        height: 12px;
        font-size: 12px;
        padding-right: 5px;
      }
    }

    .btns {
      .chujia-btn {
        width: 132px;
        height: 48px;
        background: linear-gradient(90deg, #ba6fff 0%, #8c48ff 100%);
        border-radius: 24px;
      }

      .kefu-btn {
        width: 132px;
        height: 48px;
        background: linear-gradient(90deg, #ffb06f 0%, #ff783c 100%);
        border-radius: 24px;
      }

      .buy-btn {
        width: 132px;
        height: 48px;
        background: linear-gradient(90deg, #59acff 0%, #ff8e00 100%);
        border-radius: 24px;
      }
    }
  }
}

.good-detail {
  background: #1c1632;
  border: 1px solid #38324b;
  margin-top: 20px;
  width: 100%;
  box-sizing: border-box;
  padding: 0 20px;
  border-radius: 20px;
  overflow: hidden;

  .txt-box {
    .p {
      display: flex;
      align-items: center;
      justify-content: space-between;

      p {
        width: 1100px;
        font-size: 16px;
        color: #fff;
        line-height: 24px;
        // 只显示一行
        // overflow: hidden;
        // text-overflow: ellipsis;
        // white-space: nowrap;
      }

      span {
        color: #165dff;
        font-size: 16px;
        // 鼠标移入变成手指
        cursor: pointer;
      }
    }
  }

  .txt-box1 {
    .p {
      display: flex;
      align-items: center;
      justify-content: space-between;

      p {
        width: 1100px;
        font-size: 16px;
        color: #777777;
        line-height: 24px;
      }

      span {
        color: #165dff;
        font-size: 16px;
        // 鼠标移入变成手指
        cursor: pointer;
      }
    }
  }

  .title {
    font-size: 18px;
    font-weight: bold;
    color: #fff;
    line-height: 26px;
    margin: 10px 0;

    span {
      display: inline-block;
      vertical-align: middle;
      width: 4px;
      height: 15px;
      background-color: @primaryColor;
      border-radius: 2px;
      vertical-align: middle;
      margin-bottom: 2px;
      // border-top: 8px solid transparent;
      // border-right: 8px solid transparent;
      // border-bottom: 8px solid transparent;
      // border-left: 8px solid @primaryColor;
    }
  }

  .miaoshu {
    .cont {
      border-radius: 10px;
      padding: 10px 15px;
      font-size: 14px;
      color: #777;
      line-height: 24px;
      align-items: center;

      .des-box {
        overflow: hidden;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 6;
        transition: all 0.3s;
      }

      .item-box {
        display: flex;
        align-items: center;
      }
    }
  }

  .liucheng {
    img {
      width: 90%;
      margin: 0 5%;
    }
  }

  .qun {
    width: 100%;
    display: flex;
    flex-wrap: wrap;

    .li {
      background-color: #2d2a42;
      margin-right: 17px;
      margin-bottom: 17px;
      width: 277px;
      padding: 20px 25px;
      border-radius: 4px;
      // display: flex;
      // align-items: center;
      // justify-content: space-between;
      box-sizing: border-box;

      .left {
        width: 220px;
        display: flex;
        justify-content: center;
        h6 {
          font-size: 18px;
          font-weight: 500;
          color: #fff;
          margin-bottom: 16px;
          margin-top: 0px;
        }

        p {
          font-size: 18px;
          color: #fff;
          margin: 0;
          margin-top: 1px;
        }
      }

      .right {
        width: 100px;
        text-align: center;
        line-height: 38px;
        height: 38px;
        cursor: pointer;
        background-color: #e75d31;
        color: #fff;
        border-radius: 20px;
        margin: 0 auto;

        // img {
        //   width: 100%;
        //   height: 100%;
        // }
      }
    }

    .li:nth-of-type(2n) {
      margin-right: 0;
    }
  }

  .detail-img {
    display: flex;
    flex-direction: column;
    margin-top: 10px;

    .imgs {
      margin-top: 10px;
    }

    .imgs_item {
      width: 100%;
      display: flex;
      flex-wrap: wrap;
      justify-content: flex-start;

      .el-image {
        // width: 50%;
        // block-size: 100% auto;
        margin: 15px 25%;
        max-width: 100%;
        /* 设置图片最大宽度为页面宽度的90% */
        height: auto;
        /* 自动保持图片的宽高比 */

        img {
          width: 100%;
        }
      }
    }

    .el-image {
      width: 1160px;
      border-radius: 8px;
      margin-bottom: 10px;
      margin-bottom: 10px;
    }

    ::v-deep .el-image__inner {
      width: 1160px;
      border-radius: 8px;
      margin-bottom: 10px;
      margin-bottom: 10px;
    }
  }

  .shop-press {
    width: 99%;
    // border: 1px solid rebeccapurple;
    border-radius: 4px;
    background: #ffffff;
    box-shadow: 4px 4px 8px 0px #dcdfe6;
    padding: 38px;
    box-sizing: border-box;
    margin-bottom: 10px;

    .title {
      color: #165dff;
      font-size: 40.84px;
    }

    .content {
      color: #1d2129;
      font-size: 28.83px;
    }
  }
}

.tuijian-box {
  width: 360px;
  min-height: 800px;
  background-color: #fff;
  border-radius: 20px;
  box-sizing: border-box;
  padding: 10px 20px;

  .goods-item {
    margin-bottom: 20px;
    cursor: pointer;
  }

  .title {
    font-size: 18px;
    font-weight: bold;
    color: #333333;
    line-height: 26px;
    margin: 10px 0;
  }

  .img {
    flex-shrink: 0;
    width: 142px;
    height: 80px;
    border-radius: 5px;
    overflow: hidden;

    img {
      width: 100%;
      min-height: 100%;
    }
  }

  .right {
    margin-left: 10px;

    .tit {
      font-size: 14px;
      overflow: hidden;
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      font-weight: 900;
      line-height: 24px;
    }

    .bot {
      margin-top: 10px;
      justify-content: space-between;
    }

    .liulan {
      align-items: center;
      color: #777777;
      font-size: 12px;

      .icon {
        width: 15px;
        height: 11px;
        background-image: url('../../assets/yudu/detail/liulan.png');
        background-size: 100% 100%;
        margin-right: 5px;
      }
    }

    .price {
      color: #ff3c3c;
      font-size: 18px;
      display: flex;
      align-items: baseline;
      font-weight: 600;

      .fh {
        height: 12px;
        font-size: 12px;
        padding-right: 5px;
      }
    }
  }
}

.image-slot {
  width: 100%;
  height: 245px;
  line-height: 245px;
  text-align: center;
}
.yishou {
  line-height: 50px;
  font-size: 20px;
  color: #999;
}

// .sign {
//   height: 18px;
//   margin-top: 8px;

//   .kefu {
//     background-color: @primaryColor;
//     display: inline-block;
//     width: 18px;
//     height: 18px;
//     line-height: 18px;
//     font-size: 12px;
//     color: #fff;
//     text-align: center;
//     border-radius: 3px;
//     box-shadow: 0 0 3px 0 @primaryColor;
//     margin-right: 6px;
//   }
// }

:deep(.el-tabs__item) {
  font-size: 16px;
  height: 50px;
  line-height: 50px;
  padding: 0 40px;
}

:deep(.liucheng p) {
  margin: 0 !important;
}

:deep(.el-tabs__item.is-active) {
  font-weight: bold;
}
.dialog-box {
  text-align: center;
  font-size: 16px;
  .txt {
    margin-bottom: 15px;
  }
}
.dialog-footer {
  width: 100%;
  text-align: center;
}

:deep(.location .el-breadcrumb__inner) {
  color: #fff;
}

:deep(.location .el-breadcrumb__inner.is-link) {
  color: #fff !important;
}

:deep(.el-icon.el-rate__icon.is-active) {
  font-size: 22px !important;
}

/* 更改标签页激活状态下的颜色 */
::v-deep .custom-tabs .el-tabs__item.is-active {
  color: #fff; /* 你想要的颜色 */
  background-color: #e75d31;
  width: 100px;
}

::v-deep .custom-tabs #tab-0 {
  padding-left: 40px;
}

::v-deep .custom-tabs #tab-3 {
  padding-right: 40px;
}

::v-deep .custom-tabs .el-tabs__item {
  color: #ccc; /* 你想要的颜色 */
  border-radius: 10px 10px 0 0;
  // margin-left: 30px;
}

::v-deep .custom-tabs .el-tabs__item.is-active:hover {
  color: #fff; /* 你想要的颜色 */
}

::v-deep .el-tabs__nav-scroll {
  border-bottom: 3px solid #38344b !important;
}

/* 更改标签页下方的线条颜色 */
::v-deep .custom-tabs .el-tabs__active-bar {
  background-color: #e75d31; /* 你想要的颜色 */
}

::v-deep .custom-tabs .el-tabs__item:hover {
  color: #e75d31; /* 改变文字颜色 */
  // background-color: #f0f9ff; /* 改变背景颜色 */
}
</style>
