<template>
	<view class="img-box1">
		<image class="img" src="/static/img/default-img.png" mode=""></image>
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
	.img-box1 {
		width: 100%;
		height: 100%;
		background-color: rgba(245, 249, 255, 1);
		display: flex;
		justify-content: center;
		align-items: center;
		overflow: hidden;

		.img {
			width: 192rpx;
			height: 104rpx;
		}
	}
</style>