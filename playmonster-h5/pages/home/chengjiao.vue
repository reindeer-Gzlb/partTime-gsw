<template>
	<view class="mian" :style="{minHeight:gao + 'px'}">

		<!-- <view class="content-box"> -->
		<!-- 商品列表 -->
		<view class="goods_detail" v-for="item in list" @click="goDetail">
			<view>
				<image class="imgss" :src="item.titleImg" mode=""></image>
			</view>
			<view class="texts">
				{{item.des}}
			</view>
			<view class="qufu" v-if="item.gameType == 1">
				{{item.systemName}}{{item.carrierName}}{{item.groupName}}{{item.serverName}}-{{item.gameName}}
			</view>
			<view class="qufu" v-if="item.gameType == 0">
				{{item.systemName}}{{item.carrierName}}-{{item.gameName}}
			</view>
			<view class="eyes">
				<!-- <u-icon name="eye" :label="item.browseNum" labelSize="12px"></u-icon> -->
			</view>
			<view class="bapei">
				<image class="baopeiimg" src="/static/taohaoqu/zhichibaopei.png" mode=""></image>
			</view>
			<view class="prices red">
				¥<text class="pricets">{{item.newPrice }}</text>
			</view>
			<view>
				<image class="imgsenv" src="/static/feiheng/conclude.62d30cbe.png" mode=""></image>
			</view>
			<!-- <view class="goods_detailde"> -->
			<!-- <view class="message">
					商品已出售，请查看其它商品吧
				</view> -->
			<!-- </view> -->
		</view>
		<u-loadmore :status="loadMore" />

		<!-- <view class="goods-box flex">
				<view class="goods-item2" v-for="item in list">
					<view class="img-box" v-show="listMode=='img'" @click="goDetail(item)">
						<image :src="item.titleImg" mode="widthFix" style="width: 100%;">
						</image>
					</view>
					<view class="des" @click="goDetail(item)">
						{{item.des}}
					</view>
					<view class="  lin flex fbt">
						<view class="qufu">
							{{item.gameName}}
							<text>{{item.groupName}}{{item.serverName}}</text>

						</view>
					</view>
					<view class="  lin flex fbt">
						<view class="price red">
							¥{{item.newPrice}}
						</view>
						<view class="btn" @click="quxiao(item)">
							取消收藏
						</view>
					</view>

				</view>
			</view> -->

		<!-- </view> -->


		<u-toast ref="uToast"></u-toast>
		<u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="sureQuxiao" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		components: {},
		data() {
			return {
				loadMore: 'loadmore',
				list: [],
				content: '',
				show: false, //提示弹出
				content: '',
				userInfo: {},
				currentItem: null,
				form: {
					pageSize: 10,
					pageNum: 1
				},
				gao: ''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(option) {
			// this.userInfo = uni.getStorageSync('userInfo')
			// if (!this.userInfo) {
			// 	uni.showToast({
			// 		title: "请先登录",
			// 		icon: 'none'
			// 	})
			// 	setTimeout(() => {
			// 		uni.navigateTo({
			// 			url: '/pages/login/index'
			// 		})
			// 	}, 1000)
			// }
			this.getList()
		},
		onReachBottom() {
			this.nextPage()
		},
		methods: {
			// 下一页
			nextPage() {
				if (this.loadMore == 'loadmore') {
					this.form.pageNum += 1
					this.getList()
				}
			},
			// 获取列表
			async getList() {
				// this.loadMore = 'loading'
				const res = await homeApi.getGonggaoInfo(this.form)
				this.loadMore = 'nomore'
				if (res.rows.length >= this.form.pageSize) {
					this.loadMore = 'loadmore'
					this.list = this.list.concat(res.rows)
				} else {
					this.list = this.list.concat(res.rows)
					this.loadMore = 'nomore'
				}
			},
			// // 取消收藏
			// quxiao(item) {
			// 	this.content = '确认取消收藏该商品吗？'
			// 	this.show = true
			// 	this.currentItem = item
			// },
			// // 确认取消收藏
			// sureQuxiao() {
			// 	let obj = {
			// 		accountId: this.currentItem.id,
			// 		createId: this.userInfo.userId
			// 	}
			// 	userApi.removeCollct(obj).then(res => {
			// 		 //console.log(res)
			// 		if (res.code == 200) {
			// 			this.getList()
			// 		} else {
			// 			uni.showToast({
			// 				title: res.msg,
			// 				icon: 'none'
			// 			})
			// 		}
			// 		this.show = false
			// 	})
			// },

			// 下一页
			// nextPage() {
			// 	if (this.loadMore == 'loadmore') {
			// 		this.searchParams.page += 1
			// 		this.getList()
			// 	}
			// },
			// 打开详情
			goDetail(id) {
				// uni.navigateTo({
				// 	url: `../detail/index?id=${item.id}&shopId=`
				// })
				// uni.showToast({
				// 	title:'商品已经出售，请查看其他商品吧',
				// 	icon:'none'
				// })

				// const overlay = document.querySelector(".goods_detail .message");
				//  overlay.style.display = "block"; // 点击时显示遮罩层

			},
		}

	}
</script>

<style lang="scss" scoped>
	.tip {
		color: #f56c6c;
	}

	.mian {
		font-size: 30rpx;
		border-top: none;
		// margin-top: 20rpx;
		background-color: #383B56;

		// background-image:  linear-gradient(135deg,#fafbf3,#ffffff);
		.content-box {
			padding: 0 30rpx;
			position: relative;
		}

		// .title-box {
		// 	height: 50px;
		// 	width: 100%;
		// 	max-width: 1240px;
		// 	background-color: #3c9cff;
		// 	color: #fff;
		// 	line-height: 50px;
		// 	text-indent: 20px;
		// 	margin-bottom: 10px;
		// 	font-size: 18px;
		// 	font-weight: 601;
		// 	text-align: center;
		// 	position: absolute;
		// 	left: 0;
		// 	top: 0;
		// 	z-index: 10;
		// }

	}


	.goods-box {
		margin-top: 10px;
	}


	.goods-item2 {
		box-sizing: border-box;
		width: 100%;
		flex-shrink: 0;
		// margin-right: 30rpx;
		margin-bottom: 10px;
		padding-bottom: 10px;
		background-color: #fff;
		border-radius: 30rpx;

		.img-box {
			width: 100%;
			height: 200px;
			border-radius: 30rpx 30rpx 0 0;
			overflow: hidden;
		}

		.lin {
			margin: 5px 0;
			margin-top: 10px;
			padding: 0 20rpx;
		}

		.price {
			font-weight: bold;
			font-size: 20px;
		}

		.des {
			padding: 0 20rpx;
			margin-top: 20rpx;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 4;
			// margin-bottom: 10px;
			font-size: 26rpx;
		}

		.qufu {
			color: #f1924e;
			font-size: 26rpx;

			text {
				margin-left: 10px;
			}
		}
	}

	.btn {
		color: #939393;
	}

	.goods_detail {
		margin-top: 20rpx;
		padding: 20rpx;
		width: 92%;
		height: 238rpx;
		background-color: #1C1632;
		margin: 0rpx 30rpx 20rpx 30rpx;
		border-radius: 15rpx;
		position: relative;

		// .goods_detailde{
		// position: absolute;
		// top: 0;
		// left: 0;
		//  margin-top: 20rpx;
		//  // padding: 20rpx;
		//  width: 92%;
		//  height: 238rpx;
		//  // background-color: #000;
		//  background-color: #666666; /* 设置遮罩层的透明度 */
		//  margin: 0rpx 30rpx 20rpx 30rpx;
		//  opacity: 0.8;
		//  border-radius: 15rpx;
		//  // position: relative;
		//  display: none;
		// .message {
		//  width: 100%;
		//  text-align: center;
		//   position: absolute;
		//   top: 50%;
		//   left: 50%;
		//   transform: translate(-50%, -50%);
		//   color: white;
		//   font-size: 20px;
		//   font-weight: 700;
		//  display: none;
		// }
		// }
		.imgss {
			width: 192rpx;
			height: 184rpx;
			border-radius: 20rpx;
		}

		.texts {
			position: absolute;
			top: 6.5%;
			left: 33%;
			right: 3%;
			display: -webkit-box;
			-webkit-line-clamp: 2; // 指定要显示的行数
			-webkit-box-orient: vertical;
			overflow: hidden;
			text-overflow: ellipsis; // 显示省略号
			font-size: 28rpx;
			color: #fff;
		}

		.qufu {
			position: absolute;
			top: 45%;
			left: 33%;
			right: 3%;
			font-size: 24rpx;
			color: #cdcdcd;
		}

		.eyes {
			position: absolute;
			top: 45%;
			// left: 86%;
			right: 3%;
		}

		.bapei {
			position: absolute;
			top: 66%;
			left: 33%;
			right: 3%;

			.baopeiimg {
				width: 198rpx;
				height: 45rpx;
			}
		}

		.prices {
			position: absolute;
			top: 68%;
			// left: 81%;
			right: 3%;
			font-weight: 500;
			z-index: 999;

			.pricets {
				padding-left: 10rpx;
				font-size: 36rpx;
			}
		}

		.imgsenv {
			position: absolute;
			top: 33%;
			right: 18%;
			width: 140rpx;
			height: 126rpx;
		}
	}

	.goods_detail:after {
		border-radius: 15rpx;
		content: "商品已出售，请查看其它商品吧";
		text-align: center;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #fff;
		font-size: 39rpx;
		font-weight: 700;
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		line-height: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		opacity: 0;
		pointer-events: none;
		transition: opacity 0.3s ease-in-out;
		z-index: 9999;
	}

	.goods_detail:hover:after {
		opacity: 1;
	}

	::v-deep .u-line-1.u-loadmore__content__text {
		color: #fff !important;
	}
</style>