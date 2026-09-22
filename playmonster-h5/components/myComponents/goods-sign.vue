<template>
	<view class="goods-sign">
		{{signTxt}}
	</view>
</template>

<script>
	export default {
		name: "",
		props: ['signTxt'],
		data() {
			return {};
		},

		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.goods-sign {
		margin-right: 10rpx;
		background-color: rgba(98, 151, 255, 0.30);
		padding: 4rpx 10rpx;
		border-radius: 4rpx;
		text-align: center;
		color: rgba(0, 86, 255, 1);
		font-size: 22rpx;
	}
</style>