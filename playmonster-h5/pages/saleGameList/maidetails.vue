<template>
	<view class="mian" :style="{height: gao + 'px' }">
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>

		<view class="box">
			<view class="">
				<view class="tishi" style="text-align: center;font-size: 24rpx;color: #f2243c;">
					温馨提示：根据国家规定，本平台禁止未成年交易。<br>
					所有用户均须使用有效身份证信息方可交易游戏虚拟物品。
				</view>
				<!-- <view class="tishi" v-if="!isSm">
					您还未进行实名认证，点击 <text class="goSm" @click="goSmrz">前往认证</text> 或 <text class="goSm"
						@click="goHome">返回首页</text>
				</view> -->
			</view>
			<view class="title">
				{{obj.noticeTitle}}：
			</view>
			<view class="hezi">
				<view class="centen" v-html="obj.noticeContent">
					1.上架对应的游戏商品通过官网发布
				</view>
			</view>
			<view class="title">
				{{object.noticeTitle}}：
			</view>
			<view class="hezi">
				<view class="centen" v-html="object.noticeContent">
					1.上架对应的游戏商品通过官网发布
				</view>
			</view>
			<!-- <view class="btnred" style="margin-top: 50rpx;">
				<u-button type="primary" style="width: 36%;margin-bottom: 20rpx;" @click="tiaozhuan(2)">竞价回收</u-button>
			</view>
			<view class="texts">
				(百位回收商在线报价，即刻专卖)
			</view> -->
			<view class="btnred" style="margin-top: 50rpx;">
				<u-button type="primary" style="width: 36%;background-color: #1C1632;border: none;margin-bottom: 20rpx;"
					@click="tiaozhuan(3)">官方代售</u-button>
			</view>
			<view class="texts">
				(发布官网寄卖，迅速匹配买家)
			</view>
			<view class="btnred" style="margin-top: 50rpx;">
				<u-button type="primary" plain
					style="width: 36%;border: 1px solid #1C1632;color: #1C1632;margin-bottom: 20rpx;"
					@click="tiaozhuan(1)">极速回收</u-button>
			</view>
			<view class="texts">
				(官网客服对接，立即转卖换钱)
			</view>
		</view>
	</view>
</template>

<script>
	import userApi from "../../api/users/index.js"
	import homeApi from "../../api/home/index.js"
	export default {
		components: {},
		data() {
			return {
				showArea: false,
				showSort: false,
				showFiled: false,
				show: false, //提示弹出
				loadMore: 'loadmore', //loading nomore
				id: null, //分销商id
				gameList: [], //展示的类型
				allList: [],
				keyWord: '',
				setScroll: 0,
				content: '',
				inputStyle: {
					"height": "20px",
				},
				type: 1, //回收类型 1官网寄售  2号商回收
				noticeType: 'gfds',
				notice: 'jshs',
				obj: {},
				object: {},
				isSm: false,
				gao: '',

			}
		},
		onShow() {
			this.getPhoneKG()
			this.checkSm()
		},
		onLoad(option) {
			this.getGfds()
			this.getjshs()
		},
		methods: {
			//获取手机屏幕宽高
			async getPhoneKG() {
				let that = this
				await uni.getSystemInfo({
					success(res) {
						console.log(res);
						console.log(res.brand) //手机牌子
						console.log(res.model) //手机型号
						console.log(res.screenWidth) //屏幕宽度
						console.log(res.screenHeight) //屏幕高度
						that.gao = res.screenHeight - 44
						// that.kuan = res.screenWidth
					}
				});
			},
			// 判断是否实名
			async checkSm() {
				let userInfo = await this.getUserInfo()
				if (userInfo) {
					//console.log('userInfo', userInfo)
					if (userInfo.realStatus == 2) {
						this.isSm = true
					} else {
						this.isSm = false
						// uni.showModal({
						// 	title: '提示',
						// 	content: '您还未实名，请先进行实名认证！',
						// 	showCancel: false,
						// 	success: function(res) {
						// 		if (res.confirm) {
						// 			uni.navigateTo({
						// 				url: '/pages/user/shimingrz'
						// 			})
						// 		}
						// 	}
						// });
					}

				}
			},
			goSmrz() {
				uni.navigateTo({
					url: '/pages/user/shimingrz'
				})
			},
			goHome() {
				uni.switchTab({
					url: '/pages/home/index'
				})
			},
			// 获取个人信息
			async getUserInfo() {
				try {
					let res = await userApi.getInfo()
					if (res.code == 200) {
						return res.data
					} else {
						return false
					}
				} catch {
					return false
				}
			},
			async getGfds() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.noticeType
				})
				//console.log(res);
				this.obj = res[0]
			},
			async getjshs() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.notice
				})
				//console.log(res);
				this.object = res[0]
			},
			//极速回收
			tiaozhuan(flag) {
				// if (this.isSm) {
				if (flag == 1) {
					uni.navigateTo({
						url: '/pages/gameList/otherGameList?type=jisu'
					})
				} else if (flag == 2) {
					uni.navigateTo({
						url: '/pages/gameList/otherGameList?type=jingjia'
					})
				} else if (flag == 3) {
					// 官网寄售
					uni.navigateTo({
						url: '/pages/gameList/otherGameList?type=guanwang'
					})
				}
				// } else {
				// 	uni.showToast({
				// 		title: '请先进行实名认证!',
				// 		icon: 'none'
				// 	})
				// }

			},
		}

	}
</script>

<style lang="scss" scoped>
	.tip {
		color: #f56c6c;
	}

	.title {
		font-weight: bold;
		font-size: 32rpx;
		margin-bottom: 20rpx;
		// background-image: url('/static/xyr2.0/home/tit-bg.png');
		background-size: 64rpx auto;
		background-repeat: no-repeat;
		background-position: left bottom;

	}

	.mian {
		// background-image: url('/static/xyr2.0/home/homebg.png');
		background-size: 750rpx auto;
		background-repeat: no-repeat;
		// min-height: 800rpx;
		background-color: #383B56;

		.selects-box {
			padding: 30rpx 30rpx;

			.tab-item {
				padding: 10rpx 30rpx;
				background: #FFFFFF;
				border-radius: 36rpx;
				margin-right: 40rpx;

				&.active {
					color: #FF7316;
					background: #FFFBF9;
					border: 1px solid #FF7316;
				}
			}
		}

		.game-box {
			margin-top: 30rpx;
			// padding: 0 20rpx 20rpx 7.2%;
			padding: 30rpx;
			position: relative;

			.game-item {
				width: 100rpx;
				text-align: center;
				flex-shrink: 0;
				margin-right: 47rpx;

				&:nth-of-type(5n) {
					margin-right: 0;
				}

				margin-bottom: 20rpx;

				.img {
					width: 96rpx;
					height: 96rpx;
					margin-bottom: 5px;
				}

				.text {
					display: -webkit-box;
					-webkit-line-clamp: 1; // 指定要显示的行数
					-webkit-box-orient: vertical;
					overflow: hidden;
					text-overflow: ellipsis; // 显示省略号
				}
			}
		}
	}

	.topbar {
		width: 100%;
		padding: 0 30rpx;
		padding-top: 20rpx;
		padding-bottom: 20rpx;
		// background-image: url('/static/xyr2.0/home/homebg.png');
		background-size: 750rpx auto;
		// background-repeat: no-repeat;
		// background-color: #f5f5f5;
	}

	.back {
		background-color: transparent;
		padding-top: 10rpx;
		padding-left: 20rpx;
		color: #333;
	}

	.search-box {
		height: 85rpx;
		width: 100%;
		box-sizing: border-box;
		border-radius: 10px;
		background-color: #f5f5f5;
		padding-left: 30rpx;
		padding-right: 30rpx;

		.icon {
			width: 40rpx;
			height: 28rpx;
			background-image: url('/static/xyr2.0/home/search-icon.png');
			background-size: 28rpx 28rpx;
			background-repeat: no-repeat;
		}
	}

	//彈出層
	.box {
		width: 99%;
		padding: 30rpx;
		margin-bottom: 30rpx;
	}

	.tan {
		text-align: center;
		font-size: 36rpx;
		font-weight: 700;
	}

	.title {
		margin-top: 30rpx;
		font-size: 30rpx;
		font-weight: 700;
		color: #FFF;
	}

	.hezi {
		width: 100%;
		min-height: 120rpx;
		background-color: #f6f8ff;
		border-radius: 20rpx;
		padding: 20rpx;
		text-align: left;

		.centen {
			margin-top: 10rpx;
		}

		.centen:nth-child(1) {
			margin-top: 0;
		}
	}

	.texts {
		margin-top: 10rpx;
		margin-left: 26.5%;
		color: #fff;
	}

	.tishi {
		text-align: center;
		color: #f2243c;
		margin-top: 20rpx;

		.goSm {
			color: #1890ff;
			margin: 0 10rpx;
			// text-decoration: underline;
		}

	}

	// .tip {
	// 	color: #f56c6c;
	// }

	// .mian {
	// 	font-size: 30rpx;
	// 	background-color: #fff;

	// 	.game-box {
	// 		// padding: 30rpx;
	// 		// margin-top: 10px;
	// 		// background-color: #f1f1f1;
	// 		margin-top: 30rpx;
	// 		padding: 0 20rpx 20rpx 7.2%;
	// 		position: relative;

	// 		.game-item {
	// 			// width: 155rpx;
	// 			// font-size: 26rpx;
	// 			// text-align: center;
	// 			// flex-shrink: 0;
	// 			// margin-right: 20rpx;
	// 			// color: #ccc;
	// 			width: 138rpx;
	// 			text-align: center;
	// 			flex-shrink: 0;
	// 			margin-right: 32rpx;

	// 			&:nth-of-type(4n) {
	// 				margin-right: 0;
	// 			}

	// 			margin-bottom: 20rpx;

	// 			.img {
	// 				width: 138rpx;
	// 				height: 138rpx;
	// 				margin-bottom: 5px;
	// 			}

	// 			.text {
	// 				// color: #333;
	// 				display: -webkit-box;
	// 				 -webkit-line-clamp: 1; // 指定要显示的行数
	// 				 -webkit-box-orient: vertical;
	// 				 overflow: hidden;
	// 				 text-overflow: ellipsis;
	// 			}

	// 		}
	// 	}


	// }

	// .topbar {
	// 	// width: 100%;
	// 	// background-color: #fff;
	// 	// height: 50px;
	// 	// padding: 0 30rpx;
	// 	// padding-top: 8px;
	// 	width: 100%;
	// 	padding: 0 30rpx;
	// 	padding-top: 20rpx;
	// 	padding-bottom: 20rpx;
	// 	background-size: 750rpx auto;
	// }

	// .back {
	// 	background-color: transparent;
	// 	padding-top: 10rpx;
	// 	padding-left: 20rpx;
	// 	color: #333;
	// }

	// .search-box {
	// 	// height: 35px;
	// 	// width: calc(100% - 200rpx);
	// 	// box-sizing: border-box;
	// 	// border-radius: 20px;
	// 	// background-color: #28285f;
	// 	// padding-left: 30rpx;
	// 	// padding-right: 30rpx;
	// 	// box-shadow: 0 0 7px 0 rgba(0, 0, 0, 0.2);
	// 	height: 85rpx;
	// 	width: 100%;
	// 	box-sizing: border-box;
	// 	border-radius: 10px;
	// 	background-color: #f5f5f5;
	// 	padding-left: 30rpx;
	// 	padding-right: 30rpx;

	// 	.icon {
	// 		width: 36rpx;
	// 		height: 36rpx;
	// 		background-image: url('../../static/icon/search-icon.png');
	// 		background-size: 36rpx 36rpx;
	// 	}
	// }

	// /deep/ .uni-input-input {
	// 	color: #fff;
	// }
</style>