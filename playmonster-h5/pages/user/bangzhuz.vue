<template>
	<view class="main" :style="{height:gao + 'px'}">
		<view class="nav">
			<u-cell v-for="item in list" :key="item.noticeId" icon="/static/guaishouwan/help.png" size="large"
				:title="item.noticeTitle" isLink @click="tiaozhaun(item.noticeId)"></u-cell>
		</view>
	</view>
</template>

<script>
	import homeApi from '../../api/home/index.js'
	export default {
		data() {
			return {
				noticeType: 209,
				list: [],
				gao: ''
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
				const res = await homeApi.bangzhuzhongxin({
					noticeType: this.noticeType
				})
				//console.log(res);
				this.list = res.rows
			},
			tiaozhaun(id) {
				uni.navigateTo({
					url: `/pages/user/wentixiangqing?id=${id}`
				})
			}
		},
	}
</script>

<style lang="less" scoped>
	.main {
		padding: 30rpx;
		background-color: #383B56;
	}

	/deep/ .u-cell__left-icon-wrap {
		padding-right: 20rpx;
	}

	// /deep/ .u-icon__img{
	// 	width: 52rpx;
	// 	height: 52rpx;
	// }
	.seting-server {
		font-size: 26rpx;
	}

	/deep/ .u-cell__title-text {
		font-size: 24rpx;
		color: #fff;
	}
</style>