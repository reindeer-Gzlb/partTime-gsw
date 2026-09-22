<template>
	<view class="main">
		<view class="info">
			<view :style="{'backgroundImage' : `url(${detail.titleImg})` }" v-if="detail.titleImg" class="img-box"
				@click="preview(0,[{url:detail.titleImg}])">

			</view>
			<view class="img-box" v-else>
				<!-- <image src="https://game.ikbh.top/logo/no_img.jpg" mode="widthFix"
					style="width:100%;min-height: 548rpx;height: auto;" /> -->
			</view>
		</view>
		<view class="info-des">
			<view class="box1">
				<view class="title">
					{{detail.noticeTitle}}
				</view>
				<view class="wenben" v-html="detail.noticeContent">
					<!-- 1.额U盾噶顺丰发顺丰阿双方飞洒发巴斯夫 -->
				</view>
			</view>
		</view>
		<view class="box">
			<view class="title">
				平台客服联系方式
			</view>
			<view class="times">
				服务时间：10:00 - 02:00
			</view>
			<view class="ones" v-for="item in list" :key="item.id">
				<view>
					<image class="imgs" :src="item.qrCode" mode=""></image>
				</view>
				<view class="name">
					{{item.name}}
				</view>
				<view class="btn">
					<u-button type="primary" size="small" @click="openMsg(item)">立即联系</u-button>
				</view>
			</view>
			<view class="webxin">
				温馨提示：非平台公示的任何联系方式请勿相信
			</view>
		</view>
		<view class="gongsi" @click.stop="show = true">
			公司主管投诉/举报电话：<a href="#" style="color: #5b8fe8;">19913250073</a>
		</view>
		<u-popup :closeable="true" :show="show" mode="bottom" @close="close">
			<view class="" style="margin-top: 50rpx;text-align: center;">
				<u-button style="border: 0;" @click="copyInfos(mobile)">复制</u-button>
				<view class="" style="color: #c0c4cc;padding-bottom: 20rpx;">
					{{mobile}}
				</view>
				<u-button @click="dialNumber">呼叫</u-button>
				<u-button @click="close">取消</u-button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		data() {
			return {
				notice: 'jshs',
				type: 1,
				list: [],
				detail: {},
				show: false,
				mobile: '19913250073'
			}
		},
		onLoad() {
			this.getGfds()
			this.getKEfu()
		},
		methods: {
			// 联系客服  打开聊天窗
			async openMsg(e) {
				uni.setStorageSync('goodsToKefu', '')
				var my = uni.getStorageSync("myUsername");
				var nameList = {
					myName: my,
					your: e.hxname,
					yourNickName: e.name
				};
				// const friendUserInfoMap = getApp().globalData.friendUserInfoMap;
				uni.navigateTo({
					url: "/pages/chatroom/chatroom?username=" + JSON.stringify(nameList),
				});
			},
			// 获取个人信息
			async getUserInfo() {
				try {
					let res = await userApi.getInfo()
					if (res.code == 200) {
						return res.data
					} else {
						return false
					}
				} catch {
					return false
				}
			},
			async getGfds() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.notice
				})
				 //console.log(res);
				this.detail = res[0]
			},
			async getKEfu() {
				const res = await homeApi.lianxikefus({
					type: this.type
				})
				 //console.log(res);
				this.list = res
			},
			close() {
				this.show = false
				//  //console.log('close');
			},
			//呼叫
			dialNumber() {
				uni.makePhoneCall({
					phoneNumber: this.mobile
				});
			},
			//复制手机号
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
				this.close()

			},

		}
	}
</script>

<style lang="less" scoped>
	.mian {
		font-size: 16px;
		min-height: calc(100vh - 20px);
		max-width: 500px;
		margin: 0 auto;
		padding-bottom: 90px;
		background-color: #f4f5f8;
	}

	.info {
		.img-box {
			height: 300rpx;
			overflow: hidden;
			background-image: url('https://game.ikbh.top/logo/no_img.jpg');
			background-size: auto 100%;
			background-repeat: no-repeat;
		}
	}

	.info-des {
		padding: 0 30rpx;
		margin-top: 30rpx;
		margin-bottom: 100rpx;

		.box1 {
			width: 100%;
			min-height: 166rpx;
			background-color: #fff;
			border-radius: 20rpx;
			margin-top: -8%;
			margin-bottom: 30rpx;
			box-sizing: border-box;
			padding: 20rpx;
			position: relative;

			.title {
				text-align: center;
				font-size: 32rpx;
				font-weight: 700;
				padding-bottom: 20rpx;
			}

			.wenben {
				padding-top: 20rpx;
				font-size: 24rpx;
				color: #8d8b92;
			}
		}
	}

	.box {
		width: 92%;
		min-height: 360rpx;
		background-color: #fff;
		border-radius: 20rpx;
		padding: 30rpx 50rpx;
		display: flex;
		// justify-content: space-around;
		flex-wrap: wrap;
		margin: 0 30rpx;
		text-align: center;

		.title {
			width: 100%;
			height: 50rpx;
			font-size: 36rpx;
			font-weight: 700;
			text-align: center;
		}

		.times {
			padding-top: 10rpx;
			width: 100%;
			height: 30rpx;
			font-size: 28rpx;
			color: #9e89ac;
			text-align: center;
		}

		.ones {
			text-align: center;
			margin-top: 50rpx;
			margin-left: 30rpx;

			.imgs {
				width: 250rpx;
				height: 250rpx;
			}

			.name {
				font-size: 28rpx;
				margin-top: 30rpx;
			}
		}

		.ones:nth-child(1) {
			margin-top: 0;
		}

		.ones:nth-child(2) {
			margin-top: 0;
		}

		.webxin {
			margin-top: 50rpx;
			width: 100%;
			height: 70rpx;
			line-height: 70rpx;
			text-align: center;
			background-color: #6983eb;
			color: #fff;
			border-radius: 10rpx;
		}
	}

	.gongsi {
		width: 92%;
		height: 100rpx;
		line-height: 100rpx;
		text-align: center;
		background-color: #fff;
		border-radius: 20rpx;
		// margin-top: 30rpx;
		margin: 30rpx 30rpx 20rpx 30rpx;
	}

	.btn {
		margin-top: 20rpx;
	}
</style>