<template>
	<view class="mian" :style="{height: gao + 'px' }">
		<u-sticky offsetTop="0" customNavHeight='0'>




			<view class="flex fbt topbar" style="">
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
		<view class="selects-box" style="border-bottom: 1px solid #1C1632;">
			<!-- <view class="title">
				<text class="titleicon"></text>
			</view> -->
			<view class="tabs-box flex" style="margin-left: 10rpx;">
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
		</view>
		<view class="game-box flex" ref="gamebox" v-if="gameList&&gameList.length">
			<view v-for="(item, index) in gameList" :key="index" class="game-item" @click="gameChange(item)">
				<image class="img" :src="item.icon"></image>
				<view class="text">
					{{item.name}}
				</view>
			</view>
		</view>
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		components: {},
		data() {
			return {
				show: false, //提示弹出
				loadMore: 'loadmore', //loading nomore
				id: null, //分销商id
				gameList: [], //展示的类型
				allList: [],
				keyWord: '',
				type: '',
				content: '',
				accountType: null,
				gao: '',
			}
		},
		onLoad(option) {
			this.getPhoneKG()
			const accountType = option.accountType
			if (accountType) {
				this.accountType = accountType
			}
			this.getGameList()
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
						that.gao = res.screenHeight - 56
						// that.kuan = res.screenWidth
					}
				});
			},
			// 查询游戏类型
			getGameList() {
				let obj = {
					userId: this.id,
					type: this.type
				}
				if (this.type == 2) {
					obj.type = 0
				}
				homeApi.getGames(obj).then(res => {
					this.allList = JSON.parse(JSON.stringify(res.data))
					this.gameList = res.data
				})
			},
			// 游戏分类切换
			typeChange(type) {
				this.type = type
				this.getGameList()
			},
			// 搜索
			searchList() {
				this.gameList = this.allList.filter(r => {
					return r.name.indexOf(this.keyWord) > -1
				})
			},
			// 选择游戏 进入列表页
			gameChange(item) {
				uni.navigateTo({
					url: "/pages/goodsList/index?type=" + item.id + "&gameType=" + item.type + '&gameName=' +
						item.name + "&accountType=" + this.accountType
				})
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
		background-color: #383B56;

		.selects-box {
			padding: 30rpx 30rpx;

			.tab-item {
				padding: 10rpx 30rpx;
				background: #383B56;
				color: #fff;
				border: 2px solid #fff;
				border-radius: 36rpx;
				margin-right: 40rpx;

				&.active {
					color: #fff;
					background: #B42C1D;
					border: 2px solid #B42C1D;
				}
			}
		}

		.game-box {
			margin-top: 30rpx;
			// padding: 0 20rpx 20rpx 7.2%;
			padding: 30rpx;
			position: relative;

			.game-item {
				width: 138rpx;
				text-align: center;
				flex-shrink: 0;
				text-align: center;
				margin-bottom: 30rpx;

				.img {
					width: 110rpx;
					height: 110rpx;
					margin-bottom: 5px;
				}

				.text {
					width: 100%;
					color: #fff;
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
		background-color: #1C1632;
		padding-left: 30rpx;
		padding-right: 30rpx;

		.icon {
			width: 40rpx;
			height: 28rpx;
			background-image: url('/static/guaishouwan/search.png');
			background-size: 28rpx 28rpx;
			background-repeat: no-repeat;
		}
	}
	
	::v-deep .uni-input-input{
		color: #fff;
	}
</style>