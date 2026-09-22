<template>
	<!-- <view class="mian">

		<view class="game-box flex" ref="gamebox" v-if="gameList&&gameList.length">
			<view v-for="(item, index) in gameList" :key="index" class="game-item" @click="gameChange(item)">
				<view class="img">
					<image :src="item.icon" style="width: 120rpx;height: 120rpx;"></image>
				</view>
				<text class="text">
					{{item.name}}
				</text>
			</view>
		</view>
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>
	</view> -->
	<view class="mian">
		<u-sticky offsetTop="0" customNavHeight='0'>

			<view class="flex fbt topbar" style="">
				<!-- <view class="" @click="back" style="margin-top: 16rpx;margin-left: -10rpx;">
					<u-icon name="arrow-left" size="22"></u-icon>
				</view> -->
				<view class="search-box flex grep">
					<!--注意：由于兼容性差异，如果需要使用前后插槽，nvue下需使用u--input，非nvue下需使用u-input -->
					<!-- #ifndef APP-NVUE -->
					<u-input :clearable="true" placeholder="请输入游戏名称" border="none" v-model="keyWord"
						@confirm="searchList" confirmType="search" @input="searchList">
					<!-- #endif -->
						<!-- #ifdef APP-NVUE -->
						<u--input placeholder="搜索游戏" border="none" v-model="keyWord" @confirm="searchList"
							confirmType="search" @input="searchList">
						<!-- #endif -->
							<template slot="prefix">

								<view class="icon">

								</view>
							</template>
					<!-- #ifndef APP-NVUE -->
					</u-input>
					<!-- #endif -->
					<!-- #ifdef APP-NVUE -->
					</u--input>
					<!-- #endif -->
				</view>
			</view>
		</u-sticky>
		<!-- <view class="selects-box">
			<view class="title">
				<text class="titleicon"></text>
				账号类型
			</view>
			<view class="tabs-box flex">
				<view class="tab-item" :class="{active:!type}" @click="typeChange('')">
					全部
				</view>
				<view class="tab-item" :class="{active:type==1}" @click="typeChange(1)">
					端游
				</view>
				<view class="tab-item" :class="{active:type==2}" @click="typeChange(2)">
					手游
				</view>
			</view>
		</view> -->
		<view class="game-box flex" ref="gamebox" v-if="gameList&&gameList.length">
			<view v-for="(item, index) in gameList" :key="index" class="game-item" @click="gameChange(item)">
				<view class="img">
					<image :src="item.icon" style="width: 100%;height: 100%;"></image>
				</view>
				<text class="text">
					{{item.name}}
				</text>
			</view>
		</view>
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>
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
				isSm: false

			}
		},
		onShow() {
			this.checkSm()
		},
		onLoad(option) {
			this.getGfds()
			this.getjshs()
			this.getGameList()
		},
		methods: {
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
			back() {
				uni.reLaunch({
					url: '/pages/saleGameList/maidetails'
				})
			},
			// 查询游戏类型
			getGameList() {
				homeApi.getGames(this.id).then(res => {
					this.gameList = res.data
					this.allList = JSON.parse(JSON.stringify(res.data))
				})

			},
			// 搜索
			searchList() {
				this.gameList = this.allList.filter(r => {
					return r.name.indexOf(this.keyWord) > -1
				})
			},
			// 选择游戏 进入填写信息页面
			gameChange(item) {
				// if (this.type == 1) {
				// 官网寄售
				uni.navigateTo({
					url: "/pages/retrieveAccount/accountInfo?type=" + item.name + '&gameId=' + item.id +
						'&gameType=' + item.type + '&gameIcon=' + item.icon
				})

				// } else{
				// 	// 号商回收
				// 	uni.navigateTo({
				// 		url: "/pages/retrieveAccount/inputInfo?type=" + item.id + '&gameId=' + item.id +
				// 			'&gameType=' + item.type + '&gameName=' + item.name + '&gameIcon=' + item.icon
				// 	})

				// }

			}
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
		min-height: 800rpx;
		background-color: #fff;

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
		width: 626rpx;
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