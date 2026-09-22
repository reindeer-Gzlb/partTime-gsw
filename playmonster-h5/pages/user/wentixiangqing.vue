<template>
	<view class="main" :style="{height:gao + 'px'}">
		<view class="cooperate">
			<view class="img" v-html="obj.noticeContent">

			</view>
		</view>
	</view>
</template>

<script>
	import homeApi from '../../api/home/index.js'
	export default {
		data() {
			return {
				id: '',
				obj: {},
				gao:''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(option) {
			//  //console.log(option.id)
			this.id = option.id
			this.getbangzhuInfo()
		},
		methods: {
			async getbangzhuInfo() {
				const res = await homeApi.getbangzhuzhongxinInfo(this.id)
				 //console.log(res);
				this.obj = res.data
			}
		},
	}
</script>

<style scoped>
	.main{
		background-color: #383B56;
	}
</style>