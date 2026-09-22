<template>
	<view class="goods-box">
		<view class="shop-box flex fbt" v-if="orderInfo&&orderInfo.des">
			<view class="flex" @click.stop="goShop">
				<image class="shop-img" :src="orderInfo.avatar||(shopdata&&shopdata.avatar)||avatar" mode=""></image>
				<view class="shop-name flex">
					<view class="">
						{{orderInfo.nickName||(shopdata&&shopdata.shopName) ||shopName}}
					</view>
					<view class="icon">
						<u-icon size="32rpx" color="#333" name="arrow-right"></u-icon>
					</view>
				</view>
			</view>
			<view class="type-name" v-if="type=='shouhouList'">
				{{afterTypeMap[orderInfo.afterType]}}
			</view>
			<view class="type-name" v-if="type=='orderList'">
				{{statusMap[orderInfo.orderStatus]}}
			</view>
		</view>
		<view class="goods-info flex">
			<image class="goods-img" :src="orderInfo.titleImg" mode=""></image>
			<view class="goods-cont">
				<view class="des">
					{{orderInfo.des}}
				</view>
				<view class="qufu flex">
					<goodsSign :signTxt="orderInfo.qufu"></goodsSign>
				</view>
				<view class="price flex fbt">
					<text class="flex alc">
						<text class="pricetip" v-if="type=='shouhouList'">退款 </text>
						<text :class="{price1:disPrice}"
							:style="{fontSize:showBtn||type=='orderList'?'28rpx':'40rpx'}">¥{{orderInfo.platformAmount}}</text>
						<text class="price2" v-if="disPrice">
							<text class="rmb">¥</text>
							<text>{{disPrice}}</text>
						</text>
					</text>
					<button v-if="showBtn" class="btns " plain @click.stop="openof(orderInfo)">查看详情</button>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import goodsSign from "@/components/myComponents/goods-sign.vue"
	export default {
		name: "",
		components: {
			goodsSign
		},
		props: ['orderInfo', 'shopdata', 'showBtn', 'type', 'avatar', 'shopName', 'disPrice'],
		data() {
			return {
				// 销售订单 售后类型
				afterTypeMap: {
					1: '仅退款',
					2: '退货退款'
				},
				statusMap: {
					1: '交易成功',
					2: '已取消',
					3: '待付款',
					4: '交易中',
					5: '已退款',
				},
			};

		},

		methods: {
			openof(item) {
				this.$emit('goDetail', item)
			},
			goShop() {
				console.log('orderInfo', this.orderInfo)
				console.log('shopdata', this.shopdata)
				let shopId = ''
				if (this.orderInfo && this.orderInfo.shopId) {
					shopId = this.orderInfo.shopId
				} else if (this.shopdata && this.shopdata.shopId) {
					shopId = this.shopdata.shopId
				}
				if (shopId) {
					uni.navigateTo({
						url: '/pages/detail/shangjia?id=' + shopId
					})
				}

			}
		}
	}
</script>

<style lang="scss" scoped>
	.goods-box {
		background-color: #fff;
		border-radius: 20rpx;
		padding: 30rpx;

		.shop-box {
			align-items: center;

			.type-name {
				color: red;
			}
		}

		.shop-img {
			width: 96rpx;
			height: 96rpx;
			border-radius: 50%;
		}

		.shop-name {
			align-items: center;
			margin-left: 16rpx;

			view {
				font-size: 32rpx;
				line-height: 32rpx;
				font-weight: 500;
			}

		}

		.goods-info {
			margin-top: 30rpx;

			.goods-img {
				width: 208rpx;
				height: 208rpx;
				border-radius: 8rpx;
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
				width: 450rpx;
				// min-height: 102rpx;
				font-size: 28rpx;
			}

			.qufu {
				margin-top: 16rpx;
			}

			.pricetip {
				color: #666666;
				font-size: 28rpx;
				margin-right: 10rpx;
			}

			.price {
				margin-top: 30rpx;
				font-size: 40rpx;
				font-weight: 500;
				color: #000000;
				line-height: 42rpx;
				align-items: center;
			}

			.btns {
				width: 180rpx;
				height: 60rpx;
				line-height: 60rpx;
				font-size: 28rpx;
				margin-left: 20rpx;
				margin-right: 0;
				background-color: #6297FF;
				color: #fff;
				border: none;
			}

			.price1 {
				color: #999;
				font-size: 30rpx !important;
				text-decoration: line-through;
			}

			.price2 {
				margin-left: 30rpx;

				.rmb {
					font-size: 28rpx;
				}

				color: red;
			}

		}
	}
</style>