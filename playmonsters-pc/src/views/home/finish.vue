<template>
    <div class="finish">
        <div class="li" v-for="(item, index) in list" :key="item.id">
            <div class="zhegai"><span>商品已售出，请查看其他商品吧</span></div>

            <div class="img">
                <img :src="item.titleImg" alt="">
            </div>
            <div class="text">
                <div class="title align-items">
                    <img src="../../assets//taohaoqu/icon/zhichibaopei.png" alt="">
                    <span>{{ item.des }}</span>
                </div>
                <div class="bom">
                    <div class="le">
                        <h5>
                            {{ item.systemName }} - {{ item.groupName }} - {{ item.gameName }}
                            <img src="../../assets//fh-icon/liulan.png" alt="">
                            <span>{{ item.browseNum }}</span>
                        </h5>
                        <p>销售时间：{{ item.saletime }}</p>
                        <h4><span>￥</span> {{ item.newPrice.toFixed(2) }}元</h4>
                    </div>
                    <div class="ri">
                        <img src="../../assets//fh-img/yichengjiao.png" alt="">
                    </div>
                </div>
            </div>
        </div>

        <div class="feiye">
            <el-pagination small background layout="prev, pager, next" :total="total" class="mt-4"
               :current-page="pageNum" :page-sizes="[4, 8, 12, 16]"
                v-model:page-size="pageSize" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
        </div>
    </div>
</template>

<script>
import { reactive, watch, ref, toRefs, onMounted, markRaw } from 'vue';
import Footer from '@/components/Footer.vue';
import kefu from '@/api/kefu';

export default {
    name: 'finish',
    components: {
        Footer
    },
    setup() {
        const state = reactive({
            list: [],
            pageSize: 5,   // 每页显示条数
            total: 0,   // 总条数
            pageNum: 1,
        });

        const getList = async () => {
            var obj = {
                pageSize: state.pageSize,
                pageNum: state.pageNum
            }
            const res = await kefu.getRecentDeal(obj);
            if (res.code == 200) {
                //console.log("11111111111111111", res);
                state.total = res.total;
                state.list = res.rows;
            }
        };

        onMounted(() => {
            getList();
        });

        // 分页
        const handleCurrentChange = (val) => {
            state.pageNum = val
            getList()
        }
        // 分页操作
        const handleSizeChange = (val) => {
            state.pageSize = val
        }

        return {
            ...toRefs(state),
            getList,
            handleCurrentChange,
            handleSizeChange

        };
    }
}


</script>

<style lang="less" scoped>
.align-items {
    display: flex;
    align-items: center;
}

.finish {
    position: relative;
    width: 1200px;
    margin: 0 auto;
    margin-top: 20px;
    min-height: 1000px;
    background-color: #fff;
    box-sizing: border-box;
    padding: 20px;

    .li {
        position: relative;
        width: 100%;
        height: 200px;
        display: flex;
        align-items: center;
        padding: 20px 0;
        box-sizing: border-box;

        .zhegai {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: #000;
            opacity: 0;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #000;

            span {
                font-size: 22px;
                font-weight: 500;
                opacity: 1;
                color: #fff;
            }
        }

        .img {
            width: 242px;
            height: 158px;
            border-radius: 8px;
            margin-right: 25px;
            -o-object-fit: cover;
            object-fit: cover;

            img {
                width: 100%;
                height: 100%;
                border-radius: 8px;
            }
        }

        .text {
            width: 880px;
            height: 155px;
            // background-color: pink;

            .title {
                width: 100%;
                min-height: 30px;
                background-color: #fff;
                padding: 5px 0;
                font-size: 17px;
                // 字只显示一行，超出吧显示省略号
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;


                img {
                    width: 125px;
                    height: 30px;
                    margin-right: 12px;
                }
            }

            .bom {
                display: flex;
                justify-content: space-between;
                align-items: center;

                .le {
                    h5 {
                        margin: 0;
                        font-size: 17px;
                        color: #666;
                        margin-right: 49px;
                        font-weight: 500;

                        img {
                            width: 20px;
                            // height: 20px;
                            margin-left: 40px;
                            margin-right: 5px;
                        }
                    }

                    p {
                        font-size: 17px;
                        color: #666;
                        margin-top: 16px;
                    }

                    h4 {
                        font-size: 25px;
                        font-weight: 500;
                        color: #FF8E00;
                        margin-top: 16px;

                        span {
                            font-size: 17px;
                            font-weight: 400;
                            margin-right: -10px;
                        }
                    }
                }

                .ri {
                    img {
                        width: 127px;
                        height: 111px;
                    }
                }
            }
        }

        &:hover {
            .zhegai {
                opacity: 0.5;
            }
        }
    }

    .feiye {
        width: 100%;
        height: 100px;
        margin: 0 auto;
        position: absolute;
        bottom: 0px;
    }
}
</style>