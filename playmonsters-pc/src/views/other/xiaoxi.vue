<template>
    <div class="xiaoxi">
        <div class="top">
            <img src="@/assets/icon/icon21.png" alt="">
            <span>系统消息</span>
        </div>
        <ul class="content">
            <li v-for="(item, index) in SystemInfo_list" :key="item.id" @click="goDetail(item)">
                <div class="left">
                    <span :style="item.readState == 1 ? '' : 'background-color:#1588f5'"></span>
                    <p>{{ item.sendTitle }}</p>
                </div>
                <div class="right">
                    {{ item.sendTime }}
                </div>
            </li>
        </ul>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref } from 'vue';
import kefu from '@/api/kefu';
import { useRouter } from 'vue-router';
export default {
    name: 'xinxi',
    setup(props, ctx) {
        const route = useRouter();
        const state = reactive({
            list: [
                {
                    id: 1,
                    // 状态
                    status: 1,
                    text: '您的订单已发货，请注意查收',
                    time: '2021-05-12 12:00:00'
                },
                {
                    id: 2,
                    // 状态
                    status: 1,
                    text: '您的订单已发货，请注意查收',
                    time: '2021-05-12 12:00:00'
                },
                {
                    id: 3,
                    // 状态
                    status: 1,
                    text: '您的订单已发货，请注意查收',
                    time: '2021-05-12 12:00:00'
                },
                {
                    id: 4,
                    // 状态
                    status: 1,
                    text: '您的订单已发货，请注意查收',
                    time: '2021-05-12 12:00:00'
                },
            ],
            SystemInfo_list: []
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

        // 点击跳转,修改状态
        const goDetail = (item) => {
            kefu.getSystemInfoDetail(item.id).then((res) => {
                if (res.code == 200) {
                    // 跳转详情页
                    
                    const url = router.resolve({ path: `/pages/detail/index`, query: { id: res.data.relatedDataId } })
                    window.open(url.href, '_blank')
                }
                //console.log("%c Line:37 🌽 系统信息详情", "color:#f5ce50;background-color: #00f;", res);
            })
                .catch((err) => {
                    //console.log(err);
                })
        }

        onMounted(() => {
            getData()
        })
        return {
            ...toRefs(state),
            getData,
            goDetail
        };
    }
};

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

.xiaoxi {
    width: 100%;
    background-color: #fff;
    min-height: 600px;

    .top {
        width: 100%;
        height: 50px;
        background-color: #434343;
        display: flex;
        align-items: center;
        border-radius: 10px 10px 0 0;

        img {
            width: 24px;
            height: 24px;
            display: block;
            margin-right: 5px;
            margin-left: 8px;
            margin-top: 3px;
        }

        span {
            font-size: 18px;
            color: #fff;
        }
    }

    .content {
        li {
            width: 100%;
            display: flex;
            align-items: center;
            justify-content: space-between;
            height: 60px;
            border-top: 1px solid #eee;

            .left {
                display: flex;
                align-items: center;
                margin-left: 15px;

                span {
                    width: 10px;
                    height: 10px;
                    background-color: #ff0000;
                    border-radius: 50%;
                    display: block;
                    margin-right: 10px;
                }
            }

            .right {
                font-size: 14px;
                color: #aaa;
                margin-right: 15px;
            }
        }
    }
}
</style>