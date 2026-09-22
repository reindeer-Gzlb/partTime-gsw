<template>
    <div class="bangzhu">
        <TopTitle :title="'欢迎来到新闻公告'"></TopTitle>
        <div class="content cooperate">
            <h4>公告详情</h4>
            <div class="li" v-for="(item, index) in list" :key="index" @click="goLink('/index/other/helpdetail',{id:item.noticeId})">
                {{ item.noticeTitle }}

                <img src="../../assets//fh-icon/toujiantou.png" alt="">
                <!-- <div class="img" v-html="item.noticeContent"></div> -->
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, markRaw, watch } from 'vue';
import { useRouter } from 'vue-router';
import TopTitle from '@/components/TopTitle.vue'
import kefuApi from '@/api/kefu';
export default {
    name: 'cooperate',
    components: {
        TopTitle,
    },
    setup(props, ctx) {
        const router = useRouter();
        const state = reactive({
            title: '',
            loading: false,
            text: ``,
            type: 209,
            list: []
        });

        onMounted(() => {
            getData()
        });

        const getData = (val) => {
            state.loading = true
            kefuApi.getNoticeLists('xwgg').then(res => {
                if (res.code == 200 || res != []) {
                    state.list = res.rows
                }
            })
        };

        // 跳转
        const goLink = (link, query) => {
            if (query) {
                router.push({ path: link, query: query });
            } else {
                router.push({ path: link });
            }
        };

        return {
            ...toRefs(state),
            goLink,

        };
    }
};
</script>

<style lang="less" scoped>
ul,
ol,
li {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Microsoft YaHei';
    font-size: 14px;
    color: #333;
}

.bangzhu {
    width: 100%;
    background-color: #fff;
    min-height: 600px;
    padding-bottom: 100px;

    .content {
        padding: 0 15px;
        h4 {
            font-size: 17px;
            font-weight: 500;
            color: #333;
            margin-bottom: 29px;
        }

        .li {
            padding-bottom: 16px;
            border-bottom: 1px solid #eaeaea;
            margin-bottom: 25px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            font-size: 17px;
            color: #666;
            cursor: pointer;

            img {
                width: 8px;
                height: 15px;
            

            }

            &:hover {
                color: #FF8E00;
            }
        }
    }

}
</style>

