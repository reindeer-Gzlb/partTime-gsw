<template>
	<!-- 回收订单 商品组件 -->
	<view class="goods-box">

		<view class="goods-info flex">
			<view class="goods-img">
				<image class="" v-if="goodsInfo.img&&goodsInfo.img[0]"
					:src="goodsInfo.img[0].url.replace('https','http')" mode="widthFix"></image>
				<default-img v-else></default-img>
			</view>


			<view class="goods-cont">
				<view class="des">
					{{goodsInfo.des}}
				</view>
				<view class="qufu flex">
					<goodsSign :signTxt="goodsInfo.region"></goodsSign>
				</view>
				<view class="flex fbt">
					<view class="account ">
						<text>游戏账号：</text>
						<text>{{goodsInfo.account}}</text>
					</view>
					<view class="yajin " v-if="goodsInfo.sellerDepositAmount">
						<text>押金：</text>
						<text>¥{{goodsInfo.sellerDepositAmount}}</text>
					</view>
				</view>
				<view v-if="goodsInfo.sellerDepositState" class=""
					:style="{color:goodsInfo.sellerDepositState==1||goodsInfo.sellerDepositState==2?'green':'red',textAlign:'right'}">
					{{yajinMap[goodsInfo.sellerDepositState]}}
				</view>

				<slot></slot>
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
		props: ['goodsInfo'],
		data() {
			return {
				yajinMap: {
					1: '押金已缴纳',
					2: '押金已退',
					3: '押金不退',
				},
			};

		},

		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.goods-box {
		background-color: #fff;
		border-radius: 20rpx;
		padding: 20rpx;

		.goods-info {
			flex-wrap: nowrap;

			.goods-img {
				width: 192rpx;
				height: 192rpx;
				border-radius: 8rpx;
				flex-shrink: 0;
				overflow: hidden;

				image {
					width: 100%;
					min-height: 192rpx;
				}
			}

			.goods-cont {
				margin-left: 16rpx;
			}

			.des {
				color: #666;
				overflow: hidden;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				width: 100%;
				// min-height: 102rpx;
				font-size: 28rpx;
			}

			.qufu {
				margin-top: 10rpx;
			}

			.account {
				margin-top: 8rpx;
				font-size: 24rpx;
				font-weight: 500;
				color: #999999;
			}




		}
	}
</style>