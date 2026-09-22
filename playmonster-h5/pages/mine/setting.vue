<template>
	<view class="mian" :style="{height:gao + 'px'}">
		<view class="cell">
			<view class="cell-item flex fbt">
				<view class="tit">
					登录账号
				</view>
				<view class='cont' style="font-size: 32rpx;">
					{{userInfo.userName}}
				</view>
				<!-- 		<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view> -->
			</view>
			<view class="cell-item flex fbt">
				<view class="tit">
					{{userInfo.userNewType==1?'商户名称':'昵称'}}
				</view>
				<view class='cont'>
					<!-- {{userInfo.nickName}} -->
					<input border="none" v-model="userInfo.nickName" @confirm="editUserInfo" @blur="editUserInfo"
						confirm-type="done" :disabled="userInfo.userNewType==1">
					</input>
				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view>
			<view class="cell-item flex fbt" @click="openPhone">
				<view class="tit">
					手机号
				</view>
				<view class='cont'>
					<input border="none" placeholder="点击此处填写手机号" v-model="userInfo.phonenumber" disabled
						confirm-type="done">
					</input>
				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view>
			<view class="cell-item flex fbt">
				<view class="tit">
					邮箱
				</view>
				<view class='cont'>
					<input border="none" placeholder="点击此处填写邮箱" v-model="userInfo.email" @confirm="editUserInfo"
						@blur="editUserInfo" confirm-type="done">
					</input>
				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view>

			<!-- 	<view class="cell-item flex fbt" @click="goLink('/pages/collect/index')">
				<view class="tit">
					我的收藏
				</view>
				<view class='cont'>
					点击查看
				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view> -->
			<!-- <view class="cell-item flex fbt" @click="goLink('/pages/retrieveAccount/mySaled/index')">
				<view class="tit">
					我的回收
				</view>
				<view class='cont'>
					点击查看
				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view>
			<view class="cell-item flex fbt" @click="goApply">
				<view class="tit">
					成为商户
				</view>
				<view v-if="!lastInfo" class='cont'>
					点击申请成为商户
				</view>
				<view v-if="lastInfo&&lastInfo.auditState==2" class='cont'>
					申请被拒，查看原因
				</view>
				<view v-if="lastInfo&&lastInfo.auditState==1" class='cont'>
					申请已通过
				</view>
				<view v-if="lastInfo&&lastInfo.auditState===0" class='cont'>
					审核中
				</view>
				<view v-if="!lastInfo||(lastInfo&&lastInfo.auditState==2)||lastInfo&&lastInfo.auditState==1"
					class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view> -->
			<!-- <view class="cell-item flex fbt" @click="content='确定要退出登录吗？';show=true">
				<view class="tit">
					退出登录
				</view>
				<view class='cont'>

				</view>
				<view class="icon">
					<u-icon name="arrow-right" color="#ccc" size="22"></u-icon>
				</view>
			</view> -->

		</view>
		<u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="lgout" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal>
		<!-- 手机号修改 -->
		<u-popup :show="showPhone" mode="center" round="10" :closeOnClickOverlay="true" @close="closePhone">
			<view class="pop-box ">
				<view class="">
					<view class="cell-item flex">
						<view class="tit">
							新手机号码
						</view>
						<view class='cont'>
							<input placeholder="填写手机号" v-model="changeData.phonenumber" confirm-type="done">
							</input>
						</view>
					</view>
					<view class="cell-item flex fbt">
						<view class="tit">
							验证码
						</view>
						<view class='cont' style="width: 200rpx;">
							<input placeholder="验证码" v-model="changeData.dxyzm" confirm-type="done">
							</input>
						</view>
						<u-button v-if="timer" style="width: 100px;" type="" disabled>
							<text>{{ timer }}秒</text>
						</u-button>
						<u-button v-else style="width: 100px;color: #3c9cff;" @click="sendYzm" type="text">
							<text>发送验证码</text>
						</u-button>
					</view>
				</view>
				<view class="btns flex fbt">
					<u-button type="info" shape="circle" text="取消" @click="closePhone"></u-button>
					<u-button type="primary" shape="circle" text="确定" @click="phoneSure"></u-button>
				</view>
			</view>

		</u-popup>
	</view>

</template>
<script>
	import userApi from "../../api/users/index.js"
	import homeApi from "../../api/home/index.js"
	// #ifdef APP-PLUS
	import APPUpdate from '@/uni_modules/zhouWei-APPUpdate/js_sdk/appUpdate';
	// #endif
	export default {
		data() {
			return {
				version: "", // 版本号
				showPhone: false,
				token: '',
				show: false,
				content: '',
				userInfo: {},
				fileList1: [],
				lastInfo: null,
				timer: 0,
				changeData: {
					userId: '',
					phonenumber: '',
					dxyzm: '',
					yzmRankey: ''
				},
				gao:''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
			this.token = uni.getStorageSync('token')
			if (!this.token) {
				this.content = '暂未登录，是否去登录？'
				this.show = true

			} else {
				this.show = false
				this.getUserInfo()

			}

		},
		onLoad() {
			// #ifdef APP-PLUS
			// 获取本地应用资源版本号
			plus.runtime.getProperty(plus.runtime.appid, (info) => {
				this.version = info.version
			})
			// #endif
		},
		methods: {
			// 检查APP是否有新版本
			onAPPUpdate() {
				// true 没有新版本的时候有提示，默认：false
				APPUpdate(true);
			},
			// 获取个人信息
			getUserInfo() {
				userApi.getInfo(this.token).then(res => {
					this.userInfo = res.data
					this.getLastOne()
					if (res.data.avatar) {
						this.fileList1 = [{
							url: res.data.avatar
						}]
					}
				})
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
			// 打开手机号弹窗
			openPhone() {
				console.log(111);
				this.showPhone = true
				this.changeData.userId = this.userInfo.userId
			},
			// 发送短信验证码
			sendYzm() {
				if (!uni.$u.test.mobile(this.changeData.phonenumber)) {
					uni.showToast({
						title: '手机号错误！',
						icon: 'none'
					})
					return
				}
				let obj = {
					phone: this.changeData.phonenumber,
					type: 'public'
				}
				homeApi.sendYzm(obj).then(res => {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					if (res.code == 200) {
						this.timer = 60
						this.changeTimer()
						this.changeData.yzmRankey = res.data.yzmRankey
					}

				})
			},
			changeTimer() {
				if (this.timer > 0) {
					this.timer -= 1
					setTimeout(() => {
						this.changeTimer()
					}, 1000)
				}
			},
			// 修改手机号
			phoneSure() {
				if (!uni.$u.test.mobile(this.changeData.phonenumber)) {
					uni.showToast({
						title: '手机号错误！',
						icon: 'none'
					})
					return
				}
				if (!this.changeData.dxyzm) {
					uni.showToast({
						title: '请输入验证码！',
						icon: 'none'
					})
					return
				}
				userApi.editPhone(this.changeData).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: "修改成功",
							icon: 'none'
						})
						this.closePhone()
						this.getUserInfo()
					} else {
						uni.showToast({
							title: res.msg,
							icon: 'none'
						})
					}

				})
			},
			closePhone() {
				this.showPhone = false
				this.changeData = {
					userId: '',
					phonenumber: '',
					dxyzm: '',
					yzmRankey: ''
				}
			},
			// 修改个人信息
			editUserInfo() {
				userApi.editInfo(this.token, this.userInfo).then(res => {
					uni.showToast({
						title: "修改成功",
						icon: 'none'
					})
					// this.getUserInfo()
				})
			},
			// 图片相关
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				//  //console.log('event',event)
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
			// 图片相关结束
			preview(cur, list) {
				uni.previewImage({
					current: cur,
					urls: list
				})
			},
			// 进入商户申请页
			goApply() {
				if (!this.lastInfo || (this.lastInfo && this.lastInfo.auditState == 2)) {
					// 被拒或没申请
					uni.navigateTo({
						url: '/pages/shanghu/applySupplier'
					})
				} else if (this.lastInfo && this.lastInfo.auditState == 1) {
					// 申请已通过，查看商家后台地址
					uni.navigateTo({
						url: '/pages/mine/erpInfo'
					})

				}
			},
			goLink(link) {
				uni.navigateTo({
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
			}
		}

	}
</script>

<style lang="scss" scoped>
	.mian {
		background-color: #383B56;
		view {
			font-size: 30rpx;
		}

		font-size: 16px;
		// padding: 30rpx;
		// background-color: #14132A;

		// background-image: url('/static/img/recovery-bg.png');
		// background-size: 100% 100%;
		.cell {
			background-color: #383B56;
			padding-top: 15px;

		}
	}

	.cell-item {
		line-height: 50px;
		border-bottom: 1px solid #f0f0f0;
		padding-left: 30rpx;
		padding-right: 30rpx;
		flex-wrap: nowrap;
		color: #fff;

		// &:last-of-type {
		// 	border-bottom: none;
		// }

		.icon {
			padding-top: 14px;
		}

		&.head-cell {
			line-height: 80px;

			.cont {
				display: flex;
				flex-direction: row-reverse;

			}

			.icon {
				padding-top: 25px;
			}
		}
	}

	.tit {
		width: 140rpx;
		flex-shrink: 0;
		color: #fff;
	}

	.cont {
		width: 420rpx;
		color: #fff;
		text-align: right;
		padding-right: 20rpx;
	}

	.pop-box {
		width: 680rpx;
		padding: 20rpx;

		.tit {
			width: 180rpx;
			color: #333;
			text-align: right;
			padding-right: 20rpx;

		}

		.cont {
			border: 1px solid #ccc;
			border-radius: 20rpx;
			height: 80rpx;
			padding-left: 20rpx;
			width: 400rpx;
		}

		.cell-item {
			border: none;
		}
	}

	.btns {
		flex-wrap: nowrap;
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

	/deep/ .pop-box input {
		height: 80rpx;
		text-align: left;
		color: #333;
	}
</style>