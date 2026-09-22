<template>
    <div class="cooperate" v-loading="loading">
        <TopTitle :title="'商户合作'"></TopTitle>
        <div class="content" >
            <p id="img" v-html="list.noticeContent"></p>
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
        const route = useRouter();
        const state = reactive({
            title: '',
            loading: false,
            text: ``,
            list:{}
        });

        onMounted(() => {
            getData()
        });

        const getData = (val) => {
            state.loading = true
            kefuApi.getNotices("shhz").then(res => {
                state.loading = false
                //console.log("111111111",res)
                if (res.code == 200 || res != [] ) {
                    state.list = res[0]
                    //console.log("222222222222",state.list)
                }
            })
        };

        return {
            ...toRefs(state)
        };
    }
};
</script>

<style lang="less" scoped>
.cooperate {
    width: 100%;
    background-color: #fff;
    min-height: 700px;

    .content {
        padding: 0 15px;
        // width: 100%;
        #img {
            width: 100%;
            z-index: 22212;
            // overflow: hidden;
            word-wrap: break-word; 
            overflow-wrap: break-word;
            // overflow-y: scroll;
        }
       
    }

}
</style>