<template>
	<view class="card-box">
		<view class="title-box">
			{{title}}
		</view>
		<view class="cont-box">
			<slot></slot>
		</view>
	</view>
</template>

<script>
	export default {
		name: "",
		props: ['title'],
		data() {
			return {};
		},

		methods: {

		}
	}
</script>

<style lang="scss" scoped>
	.card-box {
		width: 100%;
		background-color: #fff;
		border-radius: 20rpx;
		margin: 20rpx auto;

		.title-box {
			font-size: 32rpx;
			font-weight: 500;
			color: #333333;
			line-height: 32rpx;
			padding: 20rpx 30rpx;
			border-bottom: 1px solid rgba(151, 151, 151, 0.13);
		}

		.cont-box {
			padding: 1px;
		}
	}
</style>