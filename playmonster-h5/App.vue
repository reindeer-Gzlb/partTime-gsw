<script>
	let logout = false;
	import userApi from "./api/users/index.js"
	import messageApi from "./api/message/message.js"
	// #ifdef H5
	import vconsole from 'vconsole';
	// #endif

	export default {
		onLaunch(e) {
			// 处理tabBar大按钮
			uni.onTabBarMidButtonTap(() => {
				uni.navigateTo({
					url: "/pages/saleGameList/maidetails"
				})
			})
		},
		methods: {

		},

		onShow() {

		},
		onHide: function() {
			//console.log('App Hide')

		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	/* 注意要写在第一行，同时给style标签加入lang="scss"属性 */
	@import "uview-ui/index.scss";

	body {
		font-family: Microsoft Yahei, -apple-system, BlinkMacSystemFont, Helvetica Neue, Helvetica, Segoe UI, Arial, Roboto, PingFang SC, miui, Hiragino Sans GB, sans-serif;
		background-color: #f9f9f9 !important;
	}

	/* #ifdef H5 */
	body {
		// max-width: 500px;
		margin: auto;
	}

	/* #endif */
	view {
		user-select: text;
		color: #333;
		box-sizing: border-box;
		font-size: 24rpx;
	}

	.u-border {
		// border-color: #8585A9 !important;
	}

	.red {
		color: rgb(255, 70, 70)
	}

	.blue {
		color: #2979ff;
	}

	.back-blue {
		background-color: #2979ff;
	}

	.grep {
		color: #c4c9cf;
	}

	.tit {
		font-weight: bold
	}

	.back {
		background-color: #f9f9f9;
	}

	.flex {
		display: flex;
		flex-wrap: wrap;
	}

	.fbt {
		justify-content: space-between;
	}

	// // APP自定义状态栏
	// .app-status-bar {
	// 	height: var(--status-bar-height);
	// 	width: 100%;
	// 	bacbackground-color: #f2f2f2;
	// 	// background-image: url('/static/xyr2.0/home/homebg.png');
	// 	// background-size: 750rpx auto;
	// 	// background-repeat: no-repeat;
	// }

	// .status_bar {
	// 	height: var(--status-bar-height);
	// 	width: 100%;
	// }

	// .uni-page-head{
	// 	padding: 0 !important;
	// }
	// .uni-page-head .uni-page-head__title{
	// 	max-width: 450px;
	// 	height: calc(44px + env(safe-area-inset-top));
	// 	line-height: calc(44px + env(safe-area-inset-top));
	// 	margin: 0 auto;
	// 	background-color: #f4f6df;
	// }
	// .uni-page-head .uni-page-head-bd{
	// 	left: 0;
	// 	right: 0;
	// }
	// .uni-page-head .uni-page-head-hd{
	// 	z-index: 999;
	// }
	.cooperate {

		// background-color: #383B56;
		img {
			width: 100%;
		}

		image {
			width: 100%;
		}
	}

	.vc-switch {
		display: none !important;
	}


	// /deep/ .uni-tabbar__item:nth-of-type(4) .uni-tabbar__bd img {
	// 	width: 120rpx !important;
	// 	height: 120rpx !important;
	// 	margin-left: -36rpx;
	// 	margin-top: -52rpx;
	// }
	::v-deep .u-line-1.u-loadmore__content__text {
		color: #fff !important;
	}
</style>