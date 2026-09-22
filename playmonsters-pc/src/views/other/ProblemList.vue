<template>
    <div class="ProblemList">
        <TopTitle title="投诉记录">
            <div class="right">
                <img src="../../assets/fh-icon/tousu_list.png" alt="" @click="goLink('/index/other/suggest')">
            </div>
        </TopTitle>
        <div class="content">
            <div :class="shoe == item.id ? 'li on' : 'li'" v-for="(item, index) in list" :key="item.createId">
                <div class="con">
                    <div class="left">
                        <h4>内容：{{ item.content }}</h4>
                        <p>分类：{{ item.type == 1 ? '改进建议' : item.type == 2 ? '问题反馈' : "投诉举报" }}</p>
                        <p>{{ item.createTime }}</p>
                    </div>
                    <div class="right">
                        <img src="../../assets/fh-icon/yjt.png" alt="" @click="getshoe(item)">
                    </div>
                </div>
                <div class="kong" v-if="!item.imgurl && !item.content">
                    <span>暂无数据~~</span>
                </div>
                <div :class="!item.imgurl ? 'bom' : 'jian'" v-else>
                    <p>{{ item.content }}</p>
                    <img :src="item.imgurl" alt="">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="P">联系方式：{{ item.tel }}</div>
                    <div class="P">QQ：{{ item.qq }}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref, markRaw } from 'vue';
import { useRouter } from 'vue-router';
import kefu from "@/api/kefu"
import TopTitle from '@/components/TopTitle.vue'
export default {
    name: 'ProblemList',
    components: {
        TopTitle
    },
    setup() {
        const router = useRouter();
        const state = reactive({
            shoe: -1,
            list: [],
            num: 0
        })

        // 获取列表
        const getList = () => {
            // 获取本地存储的用户信息
            const userInfo = JSON.parse(localStorage.getItem('userInfo'));

            kefu.getProblemList({ createId: userInfo.userId }).then(res => {
                //console.log(res)
                if (res.code == 200) {
                    state.list = res.rows
                }
            })
        }
        onMounted(() => {
            getList()
        })

        // 跳转
        const goLink = (link, query) => {
            if (query) {
                router.push({ path: link, query: query });
            } else {
                router.push({ path: link });
            }
        };

        // 查看
        const getshoe = (item) => {
            state.num += 1
            if (state.num % 2) {
                state.shoe = item.id
            } else {
                state.shoe = 0
            }
        }

        return {
            ...toRefs(state),
            goLink,
            getshoe
        }
    }
}
</script>

<style lang="less" scoped>
.ProblemList {
    width: 100%;
    min-height: 550px;
    background: #fff;

    .content {
        width: 100%;
        height: 100%;
        padding: 20px 30px;
        box-sizing: border-box;

        .li {
            width: 100%;
            height: 110px;
            box-sizing: border-box;
            box-shadow: 0 2px 12px 0 rgba(0, 0, 0, .1);
            transition: all .3s;
            overflow: hidden;
            margin-bottom: 20px;
            padding: 10px 20px;

            .con {
                display: flex;
                align-items: center;
                justify-content: space-between;
                box-sizing: border-box;

                .left {
                    width: 80%;
                    height: 100%;

                    h4 {
                        font-size: 16px;
                        color: #000;
                        font-weight: 500;
                        margin: 10px 0;
                    }

                    p {
                        margin: 0;
                        line-height: 2;
                        font-size: 14px;
                        color: #999;
                    }
                }

                .right {
                    // width: 20%;
                    // height: 100%;
                    display: flex;
                    align-items: center;
                    justify-content: flex-end;

                    img {
                        width: 15px;
                        height: 25px;
                    }
                }
            }

            .bom {
                height: 60%;
                display: flex;
                align-items: center;
                flex-direction: column;
                align-content: center;
                justify-content: center;
                flex-wrap: wrap;
            }

            .jian {
                font-size: 18px;

                img {
                    width: 220px;
                }
            }

            .P {
                margin: 10px 0;
            }
        }

        .on {
            min-height: 500px !important;

            .con {
                .right {
                    img {
                        transform: rotate(90deg);
                    }
                }
            }
        }
    }
}</style>
