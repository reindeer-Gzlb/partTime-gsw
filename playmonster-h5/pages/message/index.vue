<template>
	<view class="mian">
		<view class="qidai">
			<web-view :webview-styles="webviewStyles"
				src="https://www30.53kf.com/webCompany.php?arg=10825571&kf_sign=zI1MjMTcxMM4MzEyNDY4NDcyNzMxMDAxNzI4MjU1NzE%253D&style=1"></web-view>
		</view>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		components: {},
		data() {
			return {
				str: '',
				token: '',
				content: '',
				kefuList: [],
				webviewStyles: {
					progress: {
						color: '#0095ff'
					}
				},
			}
		},
		onLoad(option) {
			//console.log(option)
			this.str = option.str
		},


		methods: {
			// 退出登录
			lgout() {
				uni.clearStorage()
				this.show = false
				uni.navigateTo({
					url: '/pages/login/index'
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.mian {
		font-size: 30rpx;
		padding: 30rpx 30rpx 0;
		min-height: 800rpx;
		// background-image: url('/static/xyr2.0/home/homebg.png');
		background-size: 750rpx auto;
		background-repeat: no-repeat;

		.qidai {
			line-height: 400rpx;
			text-align: center;
		}

		.tip-txt {
			font-size: 26rpx;
			line-height: 40rpx;
			margin-top: 20rpx;
		}

		.huishou-item {
			width: 690rpx;
			height: 240rpx;
			// background-image: url('/static/xyr2.0/huishou/kshs-bg.png');
			background-size: 100% auto;
			align-items: center;
			margin-bottom: 30rpx;

			&:last-child {
				// background-image: url('/static/xyr2.0/huishou//gfds-bg.png');
			}

			.txt-box {
				padding-left: 182rpx;
				height: 100%;

				.tit {

					font-size: 36rpx;
					font-weight: 601;
					margin-top: 30rpx;
				}

				.cont {
					width: 364rpx;
					margin-top: 14rpx;
					font-size: 28rpx;
					color: #C2C2C2;
					line-height: 40rpx;
				}
			}

			.rg-icon {
				width: 32rpx;
				height: 32rpx;
				// background-image: url('/static/xyr2.0/huishou/rt-icon.png');
				background-size: 100% 100%;
				margin-right: 30rpx;
			}

			&:last-child {
				// background-image: url('/static/xyr2.0/huishou//gfds-bg.png');
				padding-top: 20rpx;

				.cont {
					margin-top: 24rpx;
				}
			}

		}
	}
</style>