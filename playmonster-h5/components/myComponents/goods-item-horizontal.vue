<template>
	<view class="goods-item" :style="{marginBottom:margin?margin:'30rpx'}">
		<view class="img">
			<image v-if="goodsInfo.titleImg" @click="goDetail(goodsInfo)" :src="goodsInfo.titleImg" mode="widthFix">
			</image>
			<default-img v-else></default-img>

		</view>
		<view class="right">
			<view class="des" @click="goDetail(goodsInfo)">
				{{goodsInfo.des}}
			</view>
			<view class="qufu flex" @click="goDetail(goodsInfo)" v-if="goodsInfo.gameType == 0">
				<goodsSign :signTxt="goodsInfo.systemName"></goodsSign>
				<goodsSign :signTxt="goodsInfo.carrierName"></goodsSign>
			</view>
			<view class="qufu flex" @click="goDetail(goodsInfo)" v-if="goodsInfo.gameType == 1">
				<goodsSign :signTxt="goodsInfo.groupName"></goodsSign>
				<goodsSign :signTxt="goodsInfo.serverName"></goodsSign>
			</view>
			<view class="lin" @click="goDetail(goodsInfo)">
				<view class="price red">
					<text class="price-txt">¥{{goodsInfo.newPrice}}</text>
				</view>
				<view style="margin-top: 10rpx;color: #999999;margin-left: 30rpx;font-size: 20rpx;">
					{{goodsInfo.browseNum >= 999 ? '999+人想要' : goodsInfo.browseNum ? goodsInfo.browseNum + '人想要' : ''}}
				</view>
			</view>
			<view class="shangjiaa" @click="jindian(goodsInfo)">
				<view class="oo">
					商家
				</view>
				<view class="ww">
					<view class="" style="color: #999999;font-size: 24rpx;">
						{{goodsInfo.createName + '  >'}}
					</view>
				</view>
				<view class="qq">
					当前在线
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import defaultImg from "./defaultImg.vue"
	import goodsSign from "@/components/myComponents/goods-sign.vue"
	export default {
		name: "",
		components: {
			goodsSign,
			defaultImg
		},
		props: ['goodsInfo', 'margin'],
		data() {
			return {

			};

		},

		methods: {
			// 打开详情
			goDetail(item) {
				uni.navigateTo({
					url: `../detail/index?id=${item.id}&shopId=${this.id}`
				})
			},
			//进店
			jindian(item) {
				uni.navigateTo({
					url: `/pages/detail/shangjia?id=${item.createId}`
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.goods-item {
		box-sizing: border-box;
		width: 100%;
		background-color: #fff;
		border-radius: 30rpx;
		overflow: hidden;
		display: flex;
		padding: 20rpx;
		box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.1);

		.img {
			width: 192rpx;
			height: 192rpx;
			flex-shrink: 0;
			margin-right: 20rpx;
			border-radius: 20rpx;
			overflow: hidden;

			image {
				width: 100%;
				min-height: 192rpx;
			}
		}

		.right {
			width: 100%;
		}

		.lin {
			// margin: 5px 0;
			margin-top: 2px;
			padding: 0 10rpx;
			display: flex;
			justify-content: start;
		}

		.name-tag {
			// width: 124px;
			// height: 36px;
			padding: 0 10rpx;
			padding-top: 2rpx;
			border-radius: 6px;
			border: 1px solid #71A5FF;
			color: #71A5FF;
		}

		.price {
			color: #FF0000;

			.price-txt {
				font-weight: bold;

				font-size: 32rpx;
			}

		}

		.des {
			// padding: 0 10rpx;
			// margin-top: 10rpx;
			width: 100%;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 2;
			color: #666;
			font-size: 28rpx;
			text-align: justify;
		}

		.qufu {
			// font-weight: bold;
			margin-top: 4rpx;
			font-size: 24rpx;
		}

		.shangjiaa {
			width: 100%;
			margin-top: 4rpx;
			display: flex;
			justify-content: space-between;

			.oo {
				// padding: 3rpx 10rpx;
				width: 20%;
				height: 36rpx;
				text-align: center;
				line-height: 36rpx;
				background: rgba(255, 233, 98, 0.3);
				color: #FF8300;
				border-radius: 5rpx;
			}

			.ww {
				padding-left: 20rpx;
				width: 61%;
				display: flex;
				margin-right: 72rpx;
			}

			.qq {
				width: 30%;
				color: #FF8300;
			}
		}
	}
</style>