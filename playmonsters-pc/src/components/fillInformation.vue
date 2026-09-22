<template>
    <div class="fillInformation">
        <div class="location">
            <el-icon class="home-icon" color="#4E5969" :size="15">
                <House />
            </el-icon>
            <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>信息填写</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="content">
            <div class="title">
                <img src="@/assets/icon/icon38.png" alt="">
                <span>当前选择</span>
            </div>
            <div class="Selected">
                <span>{{ Selected.name }}&ensp;---&ensp;</span>
                <span>{{ Selected.xitong }} &ensp;---&ensp;</span>
                <span v-if="Selected.shiming == '未实名'">可二次实名 &ensp;---&ensp;</span>
                <span v-else>不可二次实名 &ensp;---&ensp;</span>
                <span>无防沉迷</span>
                <button>重新选择</button>
            </div>
            <div class="title">
                <img src="@/assets/icon/icon39.png" alt="">
                <span>账号信息</span>
            </div>
        </div>
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { House } from '@element-plus/icons-vue';

export default {
    name: 'fillInformation',
    components: { House },

    setup(props, ctx) {
        const router = useRouter();
        const state = reactive({
            Selected: {
                id: "",
                laiyuan: "",
                name: "",
                pcIconUrl: "",
                shiming: "",
                xitong: "",
            },
            selectAttrs: [],//需要选择的类型的属性

        });

        // 监听路由变化
        watch(
            () => router.currentRoute.value.query,
            (newValue, oldValue) => {
                state.Selected = newValue;
            }
        );

        // 生命周期
        onMounted(() => {
            state.Selected = router.currentRoute.value.query;
        });
        return {
            ...toRefs(state),
        };
    }

}
</script>

<style lang="less" scoped>
 ul,il,ol,p{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    list-style: none;
}

.fillInformation {
    width: 1200px;
    margin: 0 auto;

    .content {
        width: 100%;
        min-height: 1000px;
        background-color: #fff;
        margin-top: 20px;
        box-sizing: border-box;
        border-radius: 10px;

        .title {
            width: 1200px;
            height: 45px;
            background-color: #565656;
            background-image: linear-gradient(90deg, #565656, #b6b6b6);
            margin: 0 auto;
            display: flex;
            align-items: center;
            border-radius: 4px;

            img {
                width: 20px;
                height: 20px;
                display: block;
                margin-left: 10px;
                margin-right: 5px;
            }

            span {
                font-size: 16px;
                color: #fff;
            }
        }

        .Selected {
            width: 1180px;
            margin: 0 auto;
            height: 60px;
            display: flex;
            align-items: center;
            padding-left: 15px;

            button {
                width: 100px;
                height: 30px;
                background-color: #FF8E00;
                margin-left: 20px;
                border-radius: 5px;
                line-height: 30px;
                text-align: center;
                font-size: 14px;
                color: #fff;
                cursor: pointer;
                border: none;
            }
        }
    }
}
</style>