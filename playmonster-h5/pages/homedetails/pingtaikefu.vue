<template>
	<view class="main" :style="{height:gao + 'px'}">
		<view class="nav">
			<view class="title">
				平台客服联系方式
			</view>
			<view class="times">
				服务时间：10:00 - 02:00
			</view>
		</view>
		<view class="box">
			<view class="ones" v-for="item in list" :key="item.id">
				<view>
					<image class="imgs" :src="item.qrCode" mode="aspectFill"></image>
				</view>
				<view class="name">
					{{item.name}}
				</view>
				<view class="btn">
					<u-button style="background-color: #B42C1D;border: none;" type="primary" size="small"
						@click="golink">立即联系</u-button>
				</view>
			</view>
			<view class="webxin">
				温馨提示：非平台公示的任何联系方式请勿相信
			</view>
		</view>
		<view class="gongsi" @click.stop="show = true">
			公司主管投诉/举报电话：<a href="#" style="color: #5b8fe8;">{{mobile}}</a>
		</view>
		<!-- 弹出层 -->
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
	import userApi from "../../api/users/index.js"
	import homeApi from "../../api/home/index.js"
	export default {
		data() {
			return {
				list: [],
				show: false,
				mobile: '123456789',
				gameId: '',
				type: '',
				gao:''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(option) {
			if (option.gameId) {
				this.gameId = option.gameId
			}
			if (option.type) {
				this.type = option.type
			}
			this.getInfo()
		},
		methods: {
			// 客服列表
			async getInfo() {
				let arr = [this.gameId]
				let obj = {
					gameIdsArr: arr
				}
				if (this.type == 'jisu') {
					obj.type = 1
				}
				const res = await homeApi.lianxikefu(obj)
				//console.log(res);
				this.list = res
			},
			// 联系客服  打开聊天窗
			async openMsg(e) {
				let userInfo = await this.getUserInfo()
				if (userInfo) {
					uni.setStorageSync('goodsToKefu', '')
					var my = uni.getStorageSync("myUsername");
					var nameList = {
						myName: my,
						your: e.hxname,
						yourNickName: e.customerName
					};
					// const friendUserInfoMap = getApp().globalData.friendUserInfoMap;
					uni.navigateTo({
						url: "../chatroom/chatroom?username=" + JSON.stringify(nameList),
					});
				}
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

			close() {
				this.show = false
				//  //console.log('close');
			},
			golink(){
				uni.reLaunch({
					url:'/pages/message/index'
				})
			}
		},
	}
</script>

<style lang="less" scoped>
	.main {
		width: 100%;
		// background-image: url('/static/taohaoqu/taohaoqubg.png');
		// box-sizing: border-box;
		// background-size:100%;
		// background-repeat: no-repeat;
		// background-size: 100% auto;
		/* 图片会被缩放以适应整个背景区域，同时保持纵横比 */
		// background-repeat: no-repeat;
		// background-position: center center;
		padding: 30rpx 30rpx;
		// background: rgba(255, 207, 0, .3);
		background-color: #383B56;

		.nav {
			padding-top: 20rpx;
			padding-left: 20rpx;

			.title {
				font-size: 36rpx;
				font-weight: 700;
				color: #fff;
			}

			.times {
				margin-top: 30rpx;
				font-size: 28rpx;
				color: #e0e3dd;
			}
		}

		.box {
			margin-top: 8%;
			width: 100%;
			min-height: 360rpx;
			background-color: #fff;
			border-radius: 20rpx;
			padding: 30rpx 50rpx;
			display: flex;
			// justify-content: space-around;
			flex-wrap: wrap;

			.ones {
				text-align: center;
				margin-top: 50rpx;
				margin-left: 30rpx;
				width: 260rpx;

				.imgs {
					width: 250rpx;
					height: 250rpx;
				}

				.name {
					font-size: 28rpx;
					margin-top: 10rpx;
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
				background-color: #B42C1D;
				color: #fff;
				border-radius: 10rpx;
			}
		}

		.gongsi {
			width: 100%;
			height: 100rpx;
			line-height: 100rpx;
			text-align: center;
			background-color: #fff;
			border-radius: 20rpx;
			margin-top: 30rpx;
		}
	}

	.btn {
		margin-top: 20rpx;
	}
</style>