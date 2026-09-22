<template>
    <div class="ElMessageBox" v-if="show" @click.stop="show_s">

        <slot @click="show_no">

            
        </slot>
    </div>
</template>

<script>
import { defineComponent } from "vue";
import { reactive, watch, ref, toRefs, onMounted, markRaw } from 'vue';
import { useStore } from "vuex";
export default {
    name: "ElMessageBox",
    props: {
        is_show: {
            type: Boolean,
            default: false
        },
        text: {
            type: Number,
            default: 1
        }
    },
    setup(props, { emit }) {
        const store = useStore();
        const state = reactive({
            show: props.is_show
        })

        // 监听父组件传来的is_show
        watch(() => props.is_show, (newVal, oldVal) => {
            state.show = newVal;
        })

        // 关闭模态框事件传给父组件
        const show_s = () => {
            emit('update', props.text);
        }

        return {
            ...toRefs(state),
            show_s
        }
    },
}
</script>

<style lang="less" scoped>
.ElMessageBox {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 99;
    width: 100vw;
    height: 100vh;
    background-color: rgba(125, 125, 125, 0.7);

}
</style>