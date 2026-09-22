<template>
    <div class="hire">
        <YouxiList :golist="'hire'"></YouxiList>
    </div>
</template>

<script>
import { onMounted, reactive, toRefs, computed, watch } from 'vue';
import YouxiList from '@/components/YouxiList.vue'

export default {
    name: "Hire",
    components: {
        YouxiList
    },
    setup() {


    }
}
</script>

<style lang="less" scoped>
.hire {}
</style>