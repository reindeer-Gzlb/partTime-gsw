<template>
	<view class="main" :style="{height:gao + 'px'}">
		<view class="box" v-for="item in list" :key="item.id">
			<view class="titles">
				{{item.name}}
			</view>
			<view class="QQcha">
				{{item.qq}}
			</view>
			<view class="imgs">
				<image @click="copyInfos(item.qq)" style="width: 40rpx;height: 40rpx;"
					src="/static/feiheng/downloadb.png" mode=""></image>
			</view>
		</view>
	</view>
</template>

<script>
	import homeApi from '../../api/home/index.js'
	export default {
		data() {
			return {
				list: [],
				gao:''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad() {
			this.getList()
		},
		methods: {
			// 复制
			copyInfos(value) {
				//console.log(value);
				// #ifdef APP-PLUS
				uni.setClipboardData({
					data: value,
					success: function() {
						uni.showToast({
							title: "复制成功",
							icon: 'none',
							duration: 1500,

						})
					}
				});
				// #endif
				this.$copyText(value).then(function(e) {
					uni.showToast({
						title: "复制成功",
						icon: 'none',
						duration: 1500,
					})
				}, function(e) {
					//console.log(e)
				})

			},
			async getList() {
				const res = await homeApi.getqqDroup()
				//console.log(res);
				this.list = res
			}
		},
	}
</script>

<style lang="less" scoped>
	.main {
		padding: 30rpx;
		background-color: #383B56;

		.box {
			margin-top: 30rpx;
			width: 100%;
			min-height: 105rpx;
			background-color: #1C1632;
			border-radius: 20rpx;
			padding: 20rpx 30rpx;
			position: relative;

			.titles {
				font-size: 30rpx;
				font-weight: 700;
				color: #fff;
			}

			.QQcha {
				padding-top: 20rpx;
				font-size: 30rpx;
				font-weight: 700;
				padding-left: 5rpx;
				color: #fff;
			}

			.imgs {
				position: absolute;
				top: 35%;
				right: 10%;
			}
		}

		.box:nth-child(1) {
			margin-top: 0;
		}
	}
</style>