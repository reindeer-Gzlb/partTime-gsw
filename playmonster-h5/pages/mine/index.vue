<template>
	<view class="mian" :style="{height: gao + 'px' }">
		<view class="top-info flex">
			<view class="" style="margin-top: -6rpx;margin-right: 30rpx;">
				<u-upload :previewFullImage="false" :fileList="fileList1" @afterRead="afterRead" :deletable="false"
					name="1" :maxCount="1">
					<image :src="imgs ? imgs : '/static/guaishouwan/avatar.png'"
						style="width: 60px !important;height: 60px !important;border-radius: 50% !important;box-sizing: 100% 100%;">
					</image>
				</u-upload>
			</view>
			<view class="name-box">
				<view class="name" v-show="userInfo.nickName">
					{{userInfo.nickName}}
				</view>
				<view class="name" v-show="!userInfo.nickName" @click="goLink('/pages/login/index')">
					<text>未登录，</text>
					<text style="color: dodgerblue;">立即登录</text>

				</view>
				<view class="nameid">
					ID: {{userInfo.userId}}
					<!-- <text class="tag">{{userInfo.realstatus==1?'已实名':'未实名'}}</text> -->
				</view>
			</view>
		</view>

		<view class="top-menus ">
			<view class="inner-box flex fbt">
				<view class="myshoucang item-menu" @click="goLink('/pages/collect/index')">
					<image src="/static/guaishouwan/shoucang.png"
						style="width:80rpx;height: 80rpx;vertical-align: middle;" mode=""></image>
					<view class="name">我的收藏</view>
				</view>
				<view class="yanzheng item-menu" @click="gomessage('/pages/message/index')">
					<image src="/static/guaishouwan/kefumine.png"
						style="width:80rpx;height: 80rpx;vertical-align: middle;" mode=""></image>
					<view class="name">官方客服</view>
				</view>
				<view class="shouhou item-menu" @click="goLink('/pages/fankui/index')">
					<image src="/static/guaishouwan/tousu.png" style="width:80rpx;height: 80rpx;vertical-align: middle;"
						mode=""></image>
					<view class="name">投诉建议</view>
				</view>
				<view class="shouhou item-menu" @click="goLink('/pages/mine/myAccount/index')">
					<image src="/static/guaishouwan/huishou.png"
						style="width:80rpx;height: 80rpx;vertical-align: middle;" mode=""></image>
					<view class="name">我的代售</view>
				</view>
			</view>
		</view>

		<view class="content-boxss">

			<u-cell icon="/static/guaishouwan/myaccount.png" size="large" title="我的账号" isLink
				url="/pages/mine/myAccount/index"></u-cell>
			<u-cell icon="/static/guaishouwan/people.png" size="large" title="个人中心" isLink
				url='/pages/mine/setting'></u-cell>
			<u-cell icon="/static/guaishouwan/shangwu.png" size="large" title="商户合作" isLink url="#"
				@click="hezuo"></u-cell>
			<u-cell :border="false" icon="/static/guaishouwan/bangzhu.png" size="large" title="帮助中心" isLink
				url='/pages/user/bangzhuz'></u-cell>

		</view>
		<view class="content-box">
			<view class="outlogin-btn" v-if="token">
				<u-button style="background-color: #B42C1D;border: none;" type="primary" text="退出登录"
					@click="content='确定要退出登录吗？';show=true"></u-button>
			</view>
		</view>
		<u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="lgout" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal>
	</view>

</template>
<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		data() {
			return {
				token: '',
				show: false,
				content: '',
				userInfo: {},
				fileList1: [],
				imgs: '',
				lastInfo: null,
				huishouMenu: [{
						name: '报价中',
						path: '/pages/retrieveAccount/jingjiahuishou/myRetrieve?index=0',
						icon: '/static/icon/baojiazhong.png'
					},
					{
						name: '交易中',
						path: '/pages/retrieveAccount/jingjiahuishou/myRetrieve?index=1',
						icon: '/static/icon/jiaoyizhong.png'
					},
					{
						name: '待审核',
						path: '/pages/retrieveAccount/jingjiahuishou/myRetrieve?index=2',
						icon: '/static/icon/daishenhe.png'
					},
					{
						name: '已审核',
						path: '/pages/retrieveAccount/jingjiahuishou/myRetrieve?index=3',
						icon: '/static/icon/yishenhe.png'
					},
					{
						name: '交易成功',
						path: '/pages/retrieveAccount/jingjiahuishou/myRetrieve?index=4',
						icon: '/static/icon/jiaoyichenggong.png'
					},
				],
				token: '',
				gao: '',
				// kuan: ''
			}
		},
		onShow() {

			this.show = false
			this.getUserInfo()
		},
		onLoad() {
			this.getPhoneKG()
			const token = uni.getStorageSync('token')
			if (token) {
				this.token = token
			}
		},
		methods: {
			//获取手机屏幕宽高
			async getPhoneKG() {
				let that = this
				await uni.getSystemInfo({
					success(res) {
						console.log(res);
						console.log(res.brand) //手机牌子
						console.log(res.model) //手机型号
						console.log(res.screenWidth) //屏幕宽度
						console.log(res.screenHeight) //屏幕高度
						that.gao = res.screenHeight - 56
						// that.kuan = res.screenWidth
					}
				});
			},
			hezuo() {
				// gonggaoDetails
				homeApi.jiaoyixuzhi({
					noticeType: 'shhz'
				}).then(res => {
					//console.log('商户合作res', res)
					if (res && res.length) {
						uni.setStorageSync('tempNotice', res[0])
						uni.navigateTo({
							url: `/pages/noticeList/noticeDetail`
						})
					}
				})
			},
			// 修改个人信息
			editUserInfo() {
				userApi.editInfo(this.token, this.userInfo).then(res => {
					//console.log(res)
					uni.showToast({
						title: "修改成功",
						icon: 'none'
					})
					this.getUserInfo()
				})
			},
			// 新增图片
			async afterRead(event) {
				// console.log('event',event)
				// event.file
				const result = await this.uploadFilePromise(event.file.url)
				this.userInfo.avatar = result
				this.editUserInfo()
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: this.apiUrl + '/upload/image', // 头像上传
						filePath: url,
						name: 'file',
						formData: {},
						success: (res) => {
							//console.log('res', JSON.parse(res.data))
							setTimeout(() => {
								resolve(JSON.parse(res.data).data.filePath)
							}, 1000)
						}
					});
				})
			},
			//跳转到实名认证
			renzhengtiaozhuan() {
				uni.navigateTo({
					url: "/pages/user/shimingrz"
				})
			},
			renzhengtiao() {
				uni.navigateTo({
					url: "/pages/user/shimingrz?type=2"
				})
			},
			//设置页面
			shezhi() {
				uni.navigateTo({
					url: '/pages/mine/myshezhi'
				})
			},
			qidai() {
				uni.showToast({
					title: "敬请期待...",
					icon: 'none'
				})
			},
			// 单聊
			into_singleChatRoom() {
				var my = uni.getStorageSync("myUsername");
				var nameList = {
					myName: my,
					your: 'ydadmin',
				};
				const friendUserInfoMap = getApp().globalData.friendUserInfoMap;
				if (friendUserInfoMap.has(nameList.your) && friendUserInfoMap.get(nameList.your)?.nickname) {
					nameList.yourNickName = friendUserInfoMap.get(nameList.your).nickname;
				}
				uni.navigateTo({
					url: "../chatroom/chatroom?username=" + JSON.stringify(nameList),
				});
			},
			// 获取个人信息
			async getUserInfo() {
				let res = await userApi.getInfo()
				//console.log(res)
				if (res.code == 200) {
					this.userInfo = res.data
					uni.setStorageSync('users', res.data)
					if (res.data.avatar) {
						// this.fileList2 = [{
						// 	url: res.data.avatar
						// }]
						this.imgs = res.data.avatar
						//console.log('12312232', this.imgs);
					}
					this.getLastOne()
					return true
				} else {
					this.userInfo = {}
					return false
				}
			},
			// 获取最新申请商户数据
			getLastOne() {
				userApi.lastOne().then(res => {
					if (res.data) {
						this.lastInfo = res.data
					} else {
						this.lastInfo = null
					}

				})
			},
			// 联系客服
			goKefu() {
				uni.reLaunch({
					url: '/pages/message/index'
				})
			},
			// 成为商户
			async goshanghu() {
				if (!this.lastInfo || (this.lastInfo && this.lastInfo.auditState == 2)) {
					// 被拒或没申请
					if (await this.getUserInfo()) {
						uni.navigateTo({
							url: '/pages/shanghu/applySupplier'
						})
					}

				} else if (this.lastInfo && this.lastInfo.auditState == 1) {
					// 申请已通过，查看商家后台地址
					uni.navigateTo({
						url: '/pages/mine/erpInfo'
					})
				}
			},
			// 跳转notice详情
			goDetail(val) {
				homeApi.gonggaoDetails(val).then(res => {
					if (res[0]) {
						uni.setStorageSync('tempNotice', res[0])
					}
					uni.navigateTo({
						url: `/pages/noticeList/noticeDetail`
					})

				})
			},
			// 图片相关结束
			preview(cur, list) {
				uni.previewImage({
					current: cur,
					urls: list
				})
			},
			goLink(link) {
				uni.navigateTo({
					url: link
				})
			},
			goTab(link) {
				uni.switchTab({
					url: link
				})
			},
			// 退出登录
			lgout() {
				uni.clearStorage()
				this.show = false
				uni.navigateTo({
					url: '/pages/login/index'
				})
			},
			gomessage(link) {
				console.log(link);
				uni.reLaunch({
					url: link
				})
			},
		}

	}
</script>

<style lang="scss" scoped>
	.tip {
		color: #f3f5f9;
	}


	.app-status-bar {
		// background-position: 0 -90rpx;
	}

	.mian {
		// height: 1500rpx;
		padding-top: 50rpx;
		/*#ifdef APP-PLUS*/
		padding-top: 20rpx;
		/* #endif */
		// background-image: url('/static/taohaoqu/userbg.png');
		// background: linear-gradient(to bottom, #FFEC98, #FFEC98, #fff, #F9F9F9);
		// background-size: 750rpx auto;
		// background-size: 100% 100%;
		background-color: #383B56;
		position: relative;
		// background-color: #fff;
		// background-position: 0 -88rpx;

		.cell {
			background-color: #fff;
			padding: 0 30rpx;
			border-radius: 20rpx;
			margin-bottom: 30rpx;
		}

		.shezhi {
			text-align: right;
			padding-right: 30rpx;
			// margin-bottom: 20rpx;
		}

		.shezhi-icon {
			width: 48rpx;
			height: 48rpx;
			// position: absolute;
			// top: 2%;
			// right: 5%;
		}
	}

	.top-info {
		height: 190rpx;
		padding: 0 30rpx;
		position: relative;

		.headimg {
			margin-right: 30rpx;
		}

		.name-box {
			padding-top: 10rpx;
		}

		.renzheng {
			width: 200rpx;
			height: 44rpx;
			position: absolute;
			top: 60rpx;
			right: 0;
		}

		.shimingzi {
			position: absolute;
			top: 65rpx;
			right: 10rpx;
			font-size: 28rpx;
			font-family: STHeitiSC, STHeitiSC;
			font-weight: 500;
			color: #333333;
		}

		.shimingzis {
			position: absolute;
			top: 65rpx;
			right: 10rpx;
			font-size: 28rpx;
			font-family: STHeitiSC, STHeitiSC;
			font-weight: 500;
			color: #B36A00;
		}

		.name {
			min-width: 150rpx;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
			font-size: 32rpx;
			font-weight: bold;
			margin-bottom: 20rpx;
			color: #fff;
		}

		.nameid {
			font-size: 26rpx;
			color: #dedede;
		}

		.tag {
			background-color: #0440df;
			padding: 3px 10px;
			border-radius: 20rpx;
			margin-left: 10px;
		}
	}

	.box-shoucang {
		width: 100%;
		height: 132rpx;
		display: flex;
		justify-content: space-between;
		box-sizing: border-box;
		padding: 0 30rpx 0 30rpx;
		margin-bottom: 30rpx;

		.shoucang-left {
			width: 333rpx;
			height: 132rpx;
		}
	}

	.my-boxs {
		width: 92.1%;
		height: 220rpx;
		background-color: #fff;
		box-sizing: border-box;
		margin: 30rpx;
		border-radius: 20rpx;
		box-shadow: 0px 0px 12px 0px rgba(30, 64, 84, 0.13);
	}

	// .top-menus {
	// 	margin-bottom: 50rpx;
	// 	padding: 0 30rpx;

	// 	.inner-box {
	// 		// padding: 0rpx 40rpx;
	// 		border-radius: 20rpx;
	// 	}

	// 	.item-menu {
	// 		text-align: center;

	// 		.name {
	// 			margin-top: 10rpx;
	// 		}
	// 	}

	// }
	.top-menus {
		margin-top: -50rpx;
		margin-bottom: 50rpx;
		padding: 0 0 0 30rpx;

		.inner-box {
			width: 690rpx;
			background-color: #1C1632;
			height: 172rpx;
			background-repeat: no-repeat;
			// background-color: #fff;
			padding: 20rpx 30rpx;
			border-radius: 20rpx;
			justify-content: space-between;
			border: 1.2px solid #6C6D78;
			border-bottom: 0px solid #000;
		}

		// padding: 0 60rpx;


		.item-menu {
			text-align: center;
			// margin-right: 45rpx;

			.name {
				margin-top: 15rpx;
				color: #fff;
			}
		}

	}

	// .top-menus {
	// 	padding: 0 60rpx;
	// 	margin-bottom: 50rpx;

	// 	text {
	// 		color: #fff;
	// 		font-size: 28rpx;
	// 		margin-left: 20rpx;
	// 	}

	// 	.item-menu {
	// 		border-right: 2px solid #a8abad;
	// 		padding-right: 30rpx;

	// 		&:last-of-type {
	// 			border: none;
	// 		}
	// 	}

	// }
	.content-boxss {
		padding: 10rpx 30rpx 10rpx 30rpx;
		background-color: #1C1632;
		margin: 0 30rpx;
		box-sizing: border-box;
		border-radius: 20rpx;
		margin-bottom: 20rpx;
		// box-shadow: 0px 0px 12px 0px rgba(30, 64, 84, 0.13);
		border: 1.2px solid #6C6D78;
		border-bottom: 0px solid #000;
	}

	.content-box {
		padding: 0 30rpx;
		// background-color: #fff;
		// margin: 0 30rpx;
		border-radius: 20rpx;
		position: fixed;
		bottom: 12%;
		left: 50%;
		transform: translateX(-50%);
	}

	.cell-item {
		line-height: 120rpx;

		.icon {
			padding-top: 40rpx;
		}
	}

	.tit {
		// width: 140rpx;
		flex-shrink: 0;
		font-size: 32rpx;
		font-weight: 500;
		color: #444;
	}

	.cont {
		width: 350rpx;
		color: #999;
		text-align: right;
	}

	.outlogin-btn {
		margin-top: 50rpx;
		margin-bottom: 100rpx;

		.u-button {
			width: 400rpx;
			height: 81rpx;
			background: #3E96FF;
			border-radius: 30rpx;
			font-size: 32rpx;
		}

	}



	/deep/ .u-upload__wrap__preview__image {
		width: 70px !important;
		height: 70px !important;
		border-radius: 70px;
	}

	/deep/ .u-upload__deletable {
		.u-upload__deletable__icon {
			transform: scale(1);
		}

	}

	/deep/ input {
		height: 92rpx;
	}

	/deep/ .u-button__text {
		font-size: 32rpx !important;
	}


	/deep/ .my-boxs .u-cell__body {
		padding: 20rpx 0;

		.u-cell__value {
			color: #0079FF;
		}

		.u-icon__icon {
			color: #0079FF;
		}
	}

	/deep/ .content-boxss .u-cell__body {
		padding: 20rpx 0;

		.u-icon__img {
			width: 40rpx !important;
			height: 40rpx !important;
			margin-right: 10rpx;
		}

		.u-cell__title-text {
			font-size: 28rpx;
			font-family: STHeitiSC, STHeitiSC;
			font-weight: 500;
			color: #fff;
		}
	}
</style>