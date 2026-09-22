<template>
	<view class="cooperate" :style="{minHeight:gao + 'px'}" style="background-color: #383B56;">
		<view class="img" v-html="obj.noticeContent">

		</view>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		data() {
			return {
				obj: {},
				noticeType: 'ywjs',
				gao:""
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad() {
			this.getInfo()
		},
		methods: {
			async getInfo() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.noticeType
				})
				//console.log(res);
				this.obj = res[0]
			}
		}
	}
</script>

<style lang="scss" scoped>
	/* .cooperate {
        img {
            width: 100%;
        }
     } */
</style>