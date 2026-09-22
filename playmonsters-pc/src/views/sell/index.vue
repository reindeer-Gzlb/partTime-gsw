<template>
    <div class="sell">
        <div class="location w1200">
            <el-breadcrumb :separator-icon="ArrowRight">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/index/sell' }">我要卖</el-breadcrumb-item>
                <el-breadcrumb-item>游戏列表</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <YouxiList :golist="gameListType" @select="selectGame"></YouxiList>
    </div>
    <Footer></Footer>
    <!-- <div class="fixed">
        <fixedBar />
    </div> -->

    <!-- 详情介绍 -->
    <ElMessageBox :is_show="is_show" @update="handleChildEvent" :text="text">
        <div class="is_shows" @click.stop="on">
            <img class="icon_cha" src="../../assets/fh-icon/cha.png" alt="" @click.stop="handleChildEvent">

            <h2>详情介绍</h2>
            <div class="content">
                <div class="bottom">
                  <!-- <button @click.stop="jingjiahuishou">竞价回收</button>
                    <p>（商户竞拍报价，卖家更多选择）</p>
                    <div class="text">
                        <p >1.填写账号信息发布商品；</p>
                        <p >2.多商户对账号报价，卖家决定是否回收。</p>
                    </div> -->
                    <button @click.stop="jingjiahuishou">官网代售</button>
                    <p>（官方代售寄卖）</p>
                    <div class="text">
                        <p v-html="dskfInfo.noticeContent"></p>
                    </div>
                    <!--  -->
                    <!-- <button @click.stop="on_isshows">快速回收</button>
                    <p>（客服实时在线，免费估值参考）</p>
                    <div class="text">
                        <p v-html="memaiInfo.noticeContent"></p>
                    </div> -->
                </div>
            </div>
        </div>
    </ElMessageBox>

    <!-- 极速回收 -->
    <ElMessageBox :is_show="is_show_s" @update="Event" :text="text">
        <div class="is_show_s" @click.stop="on">
            <img class="icon_cha" src="../../assets/fh-icon/cha.png" alt="" @click.stop="Event">
            <h2>{{memaiInfo.noticeTitle}}</h2>
            <div class="top">
                <img :src="memaiInfo.titleImg" alt="">
            </div>
            <div class="huishouxuzhi">
                <h4>{{'回收须知'}}</h4>
                <div class="huili">
                    <p v-html="memaiInfo.noticeContent"></p>
                    <!-- <p>2.换绑交接完成，当天就可收款</p>
                    <p>3.回收报价就是到手价格，没有任何手续费</p> -->
                </div>
            </div>
            <div class="erweima">
                <h4>服务时间：<span style="color: #FF8E00;">10:00 - 02:00</span></h4>
                <ul>
                    <li v-for="item in list" :key="item.id">
                        <img :src="item.qrCode" alt="">
                        <p>{{item.name}}</p>
                        <p><el-button style="width:150px;" type="primary" @click="openQQ(item)">立即联系</el-button></p>
                    </li>
                </ul>
            </div>
            <div class="bottom">
                <div class="ke3">
                    公司主管投诉/举报电话：
                    <span class="red">15778693755</span>
                </div>
                <p class="red">
                    温馨提示：非平台公示的任何联系方式请勿相信
                </p>
            </div>
        </div>
    </ElMessageBox>
</template>

<script>
import { ArrowRight } from '@element-plus/icons-vue'
import { reactive, toRefs, onMounted, onUnmounted, ref, watch } from 'vue';
import { useRouter } from 'vue-router';
import fixedBar from '../home/fixedBox.vue';
import YouxiList from '@/components/YouxiList.vue'
import Footer from '@/components/Footer.vue';
import ElMessageBox from '../../components/ElMessageBox.vue';
import kefu from '@/api/kefu';
import config from '@/api/config'

export default {
    name: 'sell',
    components: {
        YouxiList,
        Footer,
        fixedBar,
        ElMessageBox
    },
    setup(props, ctx) {
        const router = useRouter();
        const state = reactive({
            is_show: false,
            text: '',
            is_show_s: false,
            memai:'jshs',
            memaidskf:'gfds',
            memaiInfo:{},
            dskfInfo:{},
            types:1,
            list:[], //客服二维码
            gameListType:'sale',
            gameId:''
        });

        // 竞价回收
        const jingjiahuishou = ()=>{
          document.documentElement.style.overflow = 'auto';
            state.gameListType = 'sale'
            state.is_show = false
        }
        // 关闭弹窗
        const handleChildEvent = () => {
            state.gameListType = 'sale'
            // 让浏览器滚动条显示
            document.documentElement.style.overflow = 'auto';
            state.is_show = false;
        };

        // 打开弹窗
        const show = (text) => {
            // state.is_show = true;
            state.text = text;
            // 让浏览器滚动条隐藏
            document.documentElement.style.overflow = 'hidden';
        };
        // 打开极速回收弹窗
        const on_isshows = () => {
            // state.is_show_s = true;
            // //console.log(state.is_show_s);
            document.documentElement.style.overflow = 'auto';
            state.gameListType = 'huishoukefu'
            state.is_show = false
        };
        // 关闭极速回收弹窗
        const Event = () => {
            state.is_show_s = false;
        };
        onMounted(() => {
          jingjiahuishou()
        });

        const on = (e) => {
            state.is_show = true;
        };
        // 监听路由变化，为/index/sell是显示弹窗
        watch(() => router.currentRoute.value.path, (val) => {
            if (val === '/index/sell') {
                show();
            }
        });
        //我要买
        const getdetail = () => {
            kefu.getdetailwym(state.memai).then((res) => {
              //console.log(res);
              state.memaiInfo = res[0]
            })
        }
        //我要卖代售客服
        const getdetaildskf = () => {
            kefu.getdetaildskf(state.memaidskf).then((res) => {
              //console.log(res);
              state.dskfInfo = res[0]
            })
        }
        // 快速回收选择了游戏
        const selectGame = (val)=>{
          state.is_show_s = true;
          state.gameId = val
          getkefuer()

        }
        //获取客服二维码
      const getkefuer = () => {
        kefu.getkefuer({
          type:state.types,
          gameIdsArr:state.gameId
        }).then((res) => {
              //console.log(res);
              state.list = res
            })
      }
       //即时通讯
    const openQQ = (val) => {
      //console.log(val);
      let token = window.localStorage.getItem('token')
      if (token) {
        token = JSON.parse(token)
      } else {
        token = ''
      }
      window.open(`${config.imUrl}/chat/conversation?hxuser=${val.hxname}&str=${token}`)
    }

        //
        onMounted(() => {
            getdetail()
            getdetaildskf()
        })
        return {
            ...toRefs(state),
            handleChildEvent,
            show,
            on_isshows,
            Event,
            getdetail,
            getdetaildskf,
            getkefuer,
            selectGame,
            openQQ,
            jingjiahuishou
        }
    }
}

</script>

<style lang="less" scoped>
ul,
ol,
li {
    list-style: none;
}

.sell {
     background-color: #1c1632;
    // ...
    .location {
        margin: 0 auto;
        padding-top: 25px;
    }
}

.is_shows {
    width: 800px;
    min-height: 690px;
    box-sizing: border-box;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    border-radius: 5px;

    .icon_cha {
        width: 25px;
        height: 25px;
        position: absolute;
        top: 20px;
        right: 20px;
        cursor: pointer;
    }

    .content {
        width: 100%;
        display: flex;
        flex-wrap: wrap;
        justify-content: flex-start;
        height: 700px;
        overflow-y: auto;

        h3 {
            width: 100%;
            font-size: 20px;
            font-weight: 500;
            color: #1d1d1d;
        }

        H3:nth-child(2) {
            margin-top: 30px;
        }

        .text {
            width: 100%;
            background: #f7f7f7;
            border-radius: 8px;
            padding: 0 20px;
            margin-bottom: 20px;

            p {
                font-size: 18px;
                color: #666;
                line-height: 1;
                padding: 0 20px;
            }
        }

        .bottom {
            margin-top: 33px;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;

            button {
                border: none;
                width: 208px;
                text-align: center;
                height: 42px;
                line-height: 42px;
                border-radius: 8px;
                cursor: pointer;
            }

            button:nth-of-type(1) {
                background: #FF8E00;
                color: #fff;
            }

            button:nth-of-type(2) {
                border: 1px solid #FF8E00;
                color: #FF8E00;
                background-color: #fff;
            }

            p {
                font-size: 17px;
                margin-top: 17px;
            }
        }
    }
}

.is_show_s {
    width: 840px;
    height: calc(100vh - 100px);
    box-sizing: border-box;
    padding: 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    top: 3vh;
    left: 50%;
    transform: translateX(-50%);
    background-color: #fff;
    border-radius: 5px;
    overflow-y: auto;


    h4 {
        width: 100%;
        text-align: center;
        margin-top: 0;
        font-size: 18px;
        font-weight: 500;
    }

    .icon_cha {
        width: 25px;
        height: 25px;
        position: absolute;
        top: 20px;
        right: 20px;
        cursor: pointer;
    }

    .top {
        img {
            width: 783px;
            height: 250px;
        }
    }

    .huishouxuzhi {
        width: 100%;
        box-sizing: border-box;
        margin-top: 17px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
        margin-top: 28px;
        padding: 25px 33px;



        .huili {
            width: 100%;

            p {
                width: 100%;
                font-size: 16px;
                color: #666;
                line-height: 22px;
            }
        }
    }

    .erweima {
      width: 740px;
        margin-top: 17px;
        background: #fff;
        border-radius: 8px;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
        margin-top: 28px;
        padding: 25px 20px;
        text-align: center;

        ul {
            display: flex;
            flex-wrap: wrap;
            padding: 0;
            width: 100%;

            li {
                margin: 16px 15px;
                text-align: center;

                img {
                    width: 137px;
                    height: 137px;
                }

            }
        }
    }

    .bottom {
        .red {
            color: #FF8E00;
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
            color: #FF8E00;
            text-align: center;
        }
    }
    
}
.fixed {
    position: fixed;
    right: 0px;
    bottom: 44.2%;
    transform: translateY(50%);
    z-index: 999;
}

:deep(.el-breadcrumb__inner){
  color: #fff;
}

:deep(.el-breadcrumb__inner.is-link){
  color: #fff !important;
}
</style>
