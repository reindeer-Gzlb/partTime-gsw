<template>
	<view class="main">
		<view class="">
			<view class="nav" v-for="item in list" :key="item.id" @click="tianzhaunxiaongqing(item)">

				<view class="box">
					<view class="flex fbt">
						<view class="xiaoxi">
							新闻公告
						</view>
						<view class="times">
							{{item.createTime}}
						</view>
					</view>
					<view class="wenben">
						{{item.noticeTitle}}
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js";
	export default {
		data() {
			return {
				list: [],
				noticeType: 'xwgg',
			}
		},
		onLoad() {
			this.getlist()
		},
		methods: {
			async getlist() {
				const res = await homeApi.bangzhuzhongxin({
					noticeType: this.noticeType
				})
				//console.log(res);
				// this.item = res.rows[0]
				this.list = res.rows
			},
			tianzhaunxiaongqing(item) {
				uni.navigateTo({
					url: "/pages/conversation/xinwenggdetails?obj=" + JSON.stringify(item)
				})
			}
		},
	}
</script>

<style lang="less" scoped>
	.main {
		width: 100%;
		min-height: 500rpx;

		.nav {
			width: 100%;
			min-height: 100rpx;
			box-sizing: border-box;
			text-align: center;
			padding: 20rpx;

			.times {
				font-size: 26rpx;
				color: #c2c2c4;
			}

			.box {
				width: 100%;
				min-height: 100rpx;
				box-sizing: border-box;
				background-color: #fff;
				border-radius: 20rpx;
				padding: 20rpx;
				text-align: left;

				.xiaoxi {
					font-size: 32rpx;
					font-weight: 700;
				}

				.wenben {
					padding-top: 10rpx;
					color: #666666;
				}
			}
		}

		.chat_noChat {
			position: fixed;
			top: 40%;
			left: 50%;
			transform: translate(-50%, -50%);
			text-align: center;

			.ctbg {
				width: 600rpx;
				// height: 1000rpx;
				block-size: 100% 100%;
			}
		}

	}
</style>