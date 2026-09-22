<template>
    <div class="commodityList">
        <div class="li" v-for="(item, index) in props.list" :key="item.id">
            <div class="to_p">
                <p>商品编号：{{ item.serverId }}</p>
                <p>商品类型：账号</p>
                <p>上架时间：{{ item.createTime }}</p>
            </div>
            <div class="botto_m">
                <div class="left" @click="seeDetail(item)">
                    <img :src="item.titleImg" alt="" v-if="item.titleImg">
                    <img :src="item.img" alt="" v-else>
                </div>
                <div class="conter" @click="seeDetail(item)">
                    <div class="t">{{ item.des }}</div>
                    <div class="ctr">
                        <img src="@/assets/icon/icon20.png" alt="">
                        <p>￥{{ item.price }}</p>
                    </div>
                    <div class="bom">
                        游戏区服：{{ item.groupName }}
                    </div>
                </div>
                <div class="right">
                    <div class="btn">
                        <!-- <div class="btn"> -->
                        <el-button type="primary" size="mini" v-if="props.button == '1'"
                            @click="dialogVisible(item)">下架</el-button>
                        <!-- <el-button type="primary" size="mini" v-if="props.button == '2'">查看结果</el-button> -->
                        <el-button type="danger" size="mini" v-if="props.button == '4'"
                            @click="shangjia(item)">上架</el-button>

                        <el-button type="danger" size="mini" v-if="item.auditDes" @click="open(item.auditDes)">失败原因</el-button>
                        <!-- <span v-if="item.auditDes">{{ item.auditDes }}</span> -->
                    </div>
                </div>
            </div>
        </div>
    </div>
    <el-dialog v-model="outerVisible" title="填写下架备注！">
        <template #footer>
            <div class="dialog-footer">
                <div class="top">
                    <el-input v-model="input" placeholder="请填写下架备注！" />
                </div>
                <div class="bottom">
                    <el-button @click="outerVisible = false">取消</el-button>
                    <el-button type="primary" @click="xiajia()">
                        确定
                    </el-button>
                </div>
            </div>
        </template>
    </el-dialog>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import kefu from '@/api/kefu';
import { ElMessage } from 'element-plus'
export default {
    name: 'commodityList',

    props: {
        list: {
            type: Array,
            default: () => []
        },
        button: {
            type: Number,
            default: 0
        }
    },
    setup(props, ctx) {
        const router = useRouter();
        const state = reactive({
            outerVisible: false,
            id: '',
            input: ''
        });
        // 查看详情
        const seeDetail = (item) => {
          const url = router.resolve({ path: `/pages/detail/index`, query: { id: item.id } })
          window.open(url.href, '_blank')
        };

        // 上架
        const shangjia = (item) => {
            kefu.getUpGoods(item.id).then(res => {
                console.log(res);
            });
        };

        // 下架
        const dialogVisible = (item) => {
            state.id = item.id;
            state.outerVisible = true
        };
        const xiajia = () => {
            console.log(state.input);
            console.log(state.id);
            kefu.getDownGoods({
                id: state.id,
                downDes: state.input
            }).then(res => {
                console.log(res);
            });
            state.outerVisible = false
        };

        onMounted(() => {
        });

        const open = (text) => {
            ElMessage({
                showClose: true,
                message: text,
                center: true,
            })
        }

        return {
            ...toRefs(state),
            props,
            seeDetail,
            shangjia,
            xiajia,
            dialogVisible,
            open
        };
    }
}

</script>

<style lang="less" scoped>
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
                width: 180px;

                .btn {
                    display: flex;
                    flex-direction: column;
                    align-items: center;

                    button {
                        width: 80px;
                        margin-top: 10px;
                    }

                    button:nth-child(2) {
                        margin: 0;
                        margin-top: 15px;
                    }
                }
            }
        }
    }
}

.dialog-footer {
    .top {
        width: 100%;
        height: 100px;
        display: flex;
    }
}
</style>