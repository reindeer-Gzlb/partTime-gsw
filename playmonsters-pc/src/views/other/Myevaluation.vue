<template>
    <div class="Myevaluation">
        <TopTitle :title="'我的评估'"></TopTitle>
        <div class="commodityList">
            <div class="li" v-for="(item, index) in list" :key="item.id">
                <div class="to_p">
                    <p>商品编号：{{ item.serverId }}</p>
                    <p>商品类型：账号</p>
                    <p>上架时间：{{ item.createTime }}</p>
                </div>
                <div class="botto_m">
                    <div class="left">
                        <img :src="item.titleImg" alt="" v-if="item.titleImg">
                        <img :src="item.img" alt="" v-else>
                    </div>
                    <div class="conter">
                        <div class="t">{{ item.des }}</div>
                        <div class="ctr">
                            <img src="@/assets/icon/icon20.png" alt="">
                        </div>
                        <div class="bom">
                            游戏区服：{{ item.region }}
                        </div>
                    </div>
                    <div class="right">
                        <div>期望价：{{ item.expprice }}</div>
                        <button v-if="!item.maxPrice">待评估</button>
                        <p v-else>评估价：{{ item.maxPrice }}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import commodityList from '@/components/commodityList.vue'
import { useRouter } from 'vue-router';
// 导入top_title
import TopTitle from '@/components/TopTitle.vue'
import kefu from '@/api/kefu'
export default {
    name: 'Myevaluation',
    components: {
        TopTitle,
        commodityList
    },
    setup(props, ctx) {
        const route = useRouter();
        const state = reactive({
            list: [],
        });

        // 获取我的评估
        const getMyevaluation = () => {
            kefu.getMyAssess().then(res => {
                if (res.code == 200) {
                    state.list = res.rows
                    // 处理数据中img
                    state.list.forEach(item => {
                        // 截取item.img中的第一个图片,开始位置https://,结束位置.jpg
                        let start = item.img.indexOf("https://")
                        let end = item.img.indexOf(".jpg")
                        item.img = item.img.substring(start, end + 4)
                    })
                }
            })
        }

        onMounted(() => {
            getMyevaluation()
        })

        return {
            ...toRefs(state)
        }
    }
}
</script>

<style lang="less" scoped>
.Myevaluation {
    width: 100%;
    min-height: 100%;

    .commodityList {
        width: 100%;
        min-height: 100%;

        .li {
            width: 100%;
            background-color: #fff;
            border-radius: 10px;
            margin-top: 10px;
            padding: 0 20px;
            box-shadow: 0 0 5px #f1f1f1;
            padding-bottom: 15px;
            cursor: pointer;

            .to_p {
                width: 960px;
                display: flex;
                align-items: center;
                height: 50px;

                p {
                    font-size: 14px;
                    color: #999;
                    width: 250px;
                }
            }

            .botto_m {
                display: flex;
                align-items: center;
                justify-content: space-between;


                .left {
                    width: 185px;
                    height: 120px;
                    display: block;
                    border-radius: 5px;
                    overflow: hidden;

                    img {
                        width: 100%;
                        height: 100%;
                        display: block;
                    }
                }

                .conter {
                    padding: 0 20px;
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    justify-content: flex-start;

                    .t {
                        // 只显示二行
                        overflow: hidden;
                        text-overflow: ellipsis;
                        display: -webkit-box;
                        -webkit-box-orient: vertical;
                        -webkit-line-clamp: 2;

                    }

                    .ctr {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;

                        img {
                            width: 80px;
                            height: 26px;
                            display: block;
                        }

                        p {
                            font-size: 16px;
                            color: #FF8E00;
                            font-weight: 700;
                        }
                    }

                    .bom {
                        font-size: 14px;
                        color: #999;
                    }
                }

                .right {
                    width: 150px;

                    button {
                        width: 90px;
                        height: 28px;
                        border-radius: 5px;
                        font-size: 14px;
                        color: #aaa;
                        background-color: #eee;
                        line-height: 28px;
                        text-align: center;
                        border: none;
                    }
                    p {
                        font-size: 14px;
                        color: #FF8E00;
                        font-weight: 700;
                    }
                }
            }
        }
    }
}
</style>