<template>
  <!-- 通知公告等 文章详情 -->
    <div class="helpdetail cooperate w1200">
        <h2>{{list.noticeTitle}}</h2>
        <div class="content" >
            <p id="img" class="ql-editor" v-html="list.noticeContent"></p>
        </div>
        <!-- {{ list }} -->
    </div>
</template>

<script>
import { reactive, toRefs, onMounted, onUnmounted, ref, watch, onActivated } from 'vue';
import { useRouter } from 'vue-router';
import kefu from '@/api/kefu';
export default {
    name: 'helpdetail',
    setup() {
        const router = useRouter();
        const state = reactive({
            id: '',
            list: {},
        });

        // 获取数据
        const getData = () => {
            // getNoticeList_xq
            kefu.getNoticeList_xq(state.id).then(res => {
                if (res.code == 200) {
                    //console.log("11111111111111111", res);
                    state.list = res.data;
                }
            })
        };

        // 监听路由变化
        watch(() => router.currentRoute.value.query, (newValue, oldValue) => {
            state.id = router.currentRoute.value.query.id
            getData()
        }, { deep: true })

        onMounted(() => {
            state.id = router.currentRoute.value.query.id
            getData()
        });





        return {
            ...toRefs(state),

        };
    }
};
</script>

<style lang="less" scoped>
.helpdetail {
    box-sizing: border-box;
    background-color: #fff;
    min-height: 570px;
    padding: 20px;
    box-sizing: border-box;
    padding-bottom: 50px;
    margin: 0 auto;
    margin-top: 15px;
    h2{
      text-align: center;
    }
    .content {
        padding: 0 15px;
        // width: 100%;
        #img {
            width: 100%;
            z-index: 22212;
            // overflow: hidden;
            word-wrap: break-word; 
            overflow-wrap: break-word;
            text-align: center;
            // overflow-y: scroll;
        }
       
    }
}
</style>
<!-- <style>
</style> -->