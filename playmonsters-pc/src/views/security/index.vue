<template>
    <div class="security">
        <div class="location w1200">
            <el-breadcrumb :separator-icon="ArrowRight">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>消息</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="content">
            <div class="li" v-for="(item, index) in SystemInfo_list" :key="index" @click="getRouter(item.sendType)">
                <div class="title">{{ item.sendTypeName }}</div>
                <div class="conter">
                    <div class="text">{{ item.sendTitle }}</div>
                    <div class="gengduo">
                        <!-- <span>查看更多</span>
                        <img src="../../assets//fh-icon/youjiantou01.png" alt=""> -->
                    </div>
                </div>
                <div class="time">{{ item.sendTime }}</div>
            </div>
        </div>
    </div>
    <Footer />
    <!-- <div class="fixed">
        <fixedBar />
    </div> -->
</template>
<script>
import { reactive, toRefs, onMounted, onUnmounted, ref, watch, onActivated } from 'vue';
import Footer from '@/components/Footer.vue';
import fixedBar from '../home/fixedBox.vue';
import { useRouter } from 'vue-router';
import kefu from '@/api/kefu'
export default {
    name: 'security',
    components: {
        Footer,
        fixedBar
    },
    setup(props, ctx) {
        const router = useRouter();
        const state = reactive({
            SystemInfo_list: [],
            list: [
                {
                    title: '防伪必看',
                    text: '“企业微信”和“普通微信”客服如何辨别真伪？交易前来认真看！粉丝不会再受骗！',
                    time: '2023-04-28 11:24:15'
                },
                {
                    title: '防伪必看',
                    text: '“企业微信”和“普通微信”客服如何辨别真伪？交易前来认真看！粉丝不会再受骗！',
                    time: '2023-04-28 11:24:15'
                },
                {
                    title: '防伪必看',
                    text: '“企业微信”和“普通微信”客服如何辨别真伪？交易前来认真看！粉丝不会再受骗！',
                    time: '2023-04-28 11:24:15'
                },
            ]
        })

         // 获取数据
         const getData = () => {
            kefu.getSystemInfo().then((res) => {
                if (res.code == 200) {
                    state.SystemInfo_list = res.rows;
                }
                //console.log("%c Line:37 🌽 系统信息", "color:#f5ce50;background-color: pink;", res);
            })
                .catch((err) => {
                    //console.log(err);
                })
        };

        /// 跳转
        const getRouter = (item) => {
            if(item == 1){
                router.push({path:'/index/other/Mybrowsing',query:{type:1}})
            }else if(item == 3){
              router.push({path: '/index/other/Mybrowsing', query:{type:3}})
            }else  if(item == 2){
                router.push({path:'/index/other/Mybrowsing',query:{type:2}})
            } else if(item == 4) {
                router.push({path: '/index/other/MyProducts'})
            }
        }


        onMounted(() => {
            getData()
        })


        return {
            ...toRefs(state),
            getData,
            getRouter
        }
    }
}
</script>

<style lang="less" scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Microsoft YaHei';
    font-size: 14px;
    color: #333;
}

.security {
    width: 1200px;
    margin: 0 auto;
    margin-top: 20px;
    min-height: 60px;
    margin-bottom: 20px;

    .content {
        width: 1200px;
        margin: 0 auto;
        min-height: 300px;
        margin-top: 20px;
        background-color: #fff;
        padding: 30px;
        box-sizing: border-box;

        .li {
            border-bottom: 1px solid #eaeaea;
            padding: 33px 0 26px;
            cursor: pointer;

            .title {
                font-size: 20px;
                font-weight: 500;
            }

            .conter {
                display: flex;
                align-items: center;
                justify-content: space-between;

                .text {
                    font-size: 18px;
                    font-weight: 400;
                    color: #666;
                    line-height: 30px;
                    margin-top: 16px;
                }

                .gengduo {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-top: 16px;
                    cursor: pointer;

                    span {
                        font-size: 16px;
                        color: #529fe7;
                        margin-right: 10px;
                    }
                }
            }

            .time {
                font-size: 18px;
                color: #999;
                margin-top: 23px;
            }
            // &:hover {
            //     .title {
            //         color: #529fe7;
            //     }
            //     .conter {
            //         .text {
            //             color: #529fe7;
            //         }
            //     }
            //     .time {
            //         color: #529fe7;
            //     }
            // }
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
</style>