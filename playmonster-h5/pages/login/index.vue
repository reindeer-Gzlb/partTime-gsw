<template>
	<view class="mian">


		<view class="content-box">
			<view class="titlea">
				<view class="logo">
					<image class="img" src="/static/guaishouwan/avatar.png" mode="widthFix"></image>
				</view>
				<view class="" style="margin-top: 10rpx;">
					<image class="img" style="width: 236rpx;height: 72rpx;margin: 0 auto;"
						src="/static/guaishouwan/txt.png" mode="widthFix"></image>
				</view>
				<!-- <view class="kouhao">
					专业的游戏账号交易平台
				</view> -->
			</view>
			<view class="form-box">
				<u--form labelPosition="left" :model="formData" :rules="rules" ref="uForm" label-width="75"
					:labelStyle="{fontWeight:600}">
					<u-form-item v-if="loginType=='account'" prop="username" ref="item0">
						<u--input shape="circle" clearable :placeholder="`输入账号`" border="surround"
							v-model="formData.username">
						</u--input>
					</u-form-item>
					<u-form-item v-if="loginType=='account'" prop="password">
						<u--input shape="circle" v-if="flag" placeholder="输入密码" border="surround"
							v-model="formData.password" type="password"></u--input>
						<u-input shape="circle" v-else placeholder="输入密码" border="surround" v-model="formData.password"
							:password="false" type="text"></u-input>

					</u-form-item>

					<u-form-item v-if="loginType=='phone'" prop="phone" ref="item0">
						<u--input shape="circle" :placeholder="`输入手机号`" border="surround" v-model="formData.phone">
						</u--input>
					</u-form-item>
					<u-form-item v-if="loginType=='phone'" prop="dxyzm">
						<view class="flex fbt" style="width: 100%;">
							<u--input class="yanzhengmas" placeholder="输入验证码" border="surround" v-model="formData.dxyzm"
								style="margin-right: 30rpx;"></u--input>


							<u-button class="btnsn" v-if="timer" style="width: 100px;" type="info" disabled>
								<text>{{ timer }}秒</text>
							</u-button>
							<u-button class="btnsn" v-else style="width: 100px;color: #333333;" @click="sendYzm"
								type="info">
								<text>获取验证码</text>
							</u-button>
						</view>
					</u-form-item>
				</u--form>
			</view>

			<view class="naver">
				<checkbox-group @change="checkChange">
					<view style="font-size: 24rpx;">
						<checkbox value="true" :checked="isCheckXieyi" />
						<text style="color: #22B300;" @click="tiaozhuanone('10')"><text
								style="color: #fff;">我已阅读并同意</text>《用户协议》</text><text style="color: #fff;">和</text>
						<text style="color: #22B300;" @click="tiaozhuantwo('9')">《隐私协议》</text>
					</view>
				</checkbox-group>
			</view>
			<view class="btns">
				<u-button
					style="color: #fff;border-radius: 50rpx;background:linear-gradient(to bottom,#fff,#fff);border: 1px solid #000;width: 400rpx;height: 66rpx;margin-top: 50rpx;color: #000;"
					@click="submit">登录</u-button>
			</view>
		</view>
		<view class="navs" v-if="loginType == 'account'" @click="switchType">

			<view class="ones">
				手机验证码登陆
			</view>

		</view>
		<view class="navs" v-if="loginType == 'phone'" @click="zhangbtn">

			<view class="ones">
				账号密码登陆
			</view>
		</view>

	</view>
</template>

<script>
	import userApi from "../../api/users/index.js"
	import homeApi from "../../api/home/index.js"
	import messageApi from "../../api/message/message.js"
	let disp = require("../../utils/IM/broadcast");

	export default {
		components: {

		},
		data() {
			return {
				showUniLogin: false,
				flag: true,
				isCheckXieyi: false,
				timer: 0,
				codeImg: {},
				codeImgUrl: '',
				redirectPath: '',
				formData: {
					code: '',
					username: '',
					password: '',
					uuid: '',
					loginType: 'h5'
				},
				loginType: 'phone',
				rules: {
					'password': {
						required: true,
						message: '请输入密码',
						trigger: ['blur', 'change']
					},
					'username': {
						required: true,
						message: '请填写账号',
						trigger: ['blur', 'change']
					},
					'code': {
						required: true,
						message: '请输入验证码',
						trigger: ['blur', 'change']
					},
					'dxyzm': {
						required: true,
						message: '请输入验证码',
						trigger: ['blur', 'change']
					},
					phone: [{
							required: true,
							message: '请输入手机号',
							trigger: ['change', 'blur'],
						},
						{
							// 自定义验证函数，见上说明
							validator: (rule, value, callback) => {
								// 上面有说，返回true表示校验通过，返回false表示不通过
								// uni.$u.test.mobile()就是返回true或者false的
								return uni.$u.test.mobile(value);
							},
							message: '手机号码不正确',
							// 触发器可以同时用blur和change
							trigger: ['change', 'blur'],
						}
					],
				},
			}

		},
		onLoad(option) {
			// 删除本地数据
			uni.removeStorageSync('token')
			uni.removeStorageSync('userInfo')
			uni.removeStorageSync('hxLoginInfo')
			uni.removeStorageSync('myUsername')
			uni.removeStorageSync('users')
			if (option.redirect) {
				this.redirectPath = option.redirect
			}


		},
		methods: {
			gotoback() {
				// uni.navigateBack()	
				uni.reLaunch({
					url: '/pages/home/index'
				})
			},
			// 选中改变
			checkChange(e) {

				if (e.detail.value && e.detail.value.length) {
					this.isCheckXieyi = true
				} else {
					this.isCheckXieyi = false
				}
			},
			//用户协议
			tiaozhuanone() {
				uni.navigateTo({
					url: '/pages/user/yonghuxy'
				})
			},
			//隐私协议
			tiaozhuantwo() {
				uni.navigateTo({
					url: '/pages/user/yinsixy'
				})
			},
			//显示与隐藏
			esys() {
				this.flag = false
				this.$forceUpdate()
			},
			esytrue() {
				this.flag = true
				this.$forceUpdate()
			},
			// 切换登录方式
			switchType() {
				if (this.loginType == 'account') {
					this.loginType = 'phone'
				} else {
					this.loginType = 'account'
				}
				this.$nextTick(r => {
					this.$refs.uForm.clearValidate()
				})
			},
			// 发送短信验证码
			sendYzm() {
				if (!uni.$u.test.mobile(this.formData.phone)) {
					uni.showToast({
						title: '手机号错误！',
						icon: 'none'
					})
					return
				}
				this.timer = 60
				this.changeTimer()
				let obj = {
					phone: this.formData.phone,
					type: 'phoneLogin'
				}
				homeApi.sendYzm(obj).then(res => {
					uni.showToast({
						title: res.msg,
						icon: 'none'
					})
					this.formData.yzmRankey = res.data.yzmRankey
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

			// 登录
			submit() {
				this.$refs.uForm.validate().then(res => {
					if (this.isCheckXieyi === false) {
						return this.$u.toast('请先勾选协议');
					}
					if (this.loginType == 'phone') {
						userApi.phoneLogin(this.formData).then(res => {
							if (res.code == 200) {
								uni.showToast({
									title: '登录成功',
									icon: 'none'
								})
								this.$refs.uForm.resetFields()
								uni.setStorageSync('token', res.token)
								this.getUserInfo(res.token)
							} else {
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
							}
						})
					} else {
						userApi.login(this.formData).then(res => {
							if (res.code == 200) {
								uni.showToast({
									title: '登录成功',
									icon: 'none'
								})
								uni.setStorageSync('token', res.token)
								this.getUserInfo(res.token)

							} else {
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
							}
						})
					}

				}).catch(errors => {

				})
			},
			// 登录环信
			loginHx(username, hxtoken) {
				uni.setStorageSync("myUsername", username)
				try {
					uni.WebIM.conn.close && uni.WebIM.conn.close();
					setTimeout(() => {
						getApp().globalData.conn.open({
							user: username,
							accessToken: hxtoken,
						})
					}, 500)
					//console.log("login success");
				} catch {
					//console.log("login feil");
				}
			},
			// 获取环信user token
			getHxToken(hxusername) {
				messageApi.getHxToken().then(res => {
					//console.log('res', res)
					let obj = {
						hxusername: hxusername,
						hxtoken: res.msg
					}
					uni.setStorageSync('hxLoginInfo', obj)
					this.loginHx(hxusername, res.msg)
				})
			},

			// 获取个人信息
			getUserInfo() {
				userApi.getInfo().then(async res => {
					uni.setStorageSync('userInfo', res.data)

					this.$refs.uForm.resetFields()
					if (this.redirectPath) {
						if (this.redirectPath ==
							'/pages/mine/index' || this.redirectPath ==
							'/pages/gameList/index' || this.redirectPath ==
							'/pages/home/index' || this.redirectPath == '/pages/saleGameList/maidetails') {
							uni.switchTab({
								url: this.redirectPath
							})
						} else {
							uni.redirectTo({
								url: this.redirectPath
							})
						}
					} else {
						uni.reLaunch({
							url: '/pages/home/index'
						})
					}
				})
			},

			goReg() {
				uni.navigateTo({
					url: "/pages/login/register"
				})
			},


			// 手机验证码登陆
			shoubtn() {
				//console.log(22222);
			},

			zhangbtn() {
				this.loginType = 'account'
			},

		}
	}
</script>

<style lang="scss" scoped>
	.mian {
		font-size: 16px;
		border-top: none;
		padding: 0 30rpx;
		padding-top: 30rpx;
		text-align: center;
		background-color: #383B56;
		min-height: 100vh;

		.content-box {
			// position: relative;
			margin-top: 180rpx;
			width: 100%;
			text-align: center;

			.logo {
				// margin-left: 37.5%;
				// position: absolute;
				// top: 0%;
				// left: 50%;
				// transform:translate(-50%,-50%);
				margin: 0 auto;
				width: 144rpx;

				.img {
					width: 100%;
				}
			}

			.kouhao {
				font-size: 26rpx;
				font-weight: 500;
				color: #333333;
				font-family: PangMenZhengDao;
			}
		}

	}

	.btns {
		margin: 20rpx auto;
		max-width: 300px;

	}


	.pointer {
		cursor: pointer;
		color: rgb(41, 121, 255);
		text-decoration: underline;
	}

	.form-box {
		margin-top: 26%;
		padding: 0 50rpx;


	}

	.tip {
		width: 100%;
		text-align: center;
		color: #333;
		margin-bottom: 10px;

		.link {
			color: #3c9cff;
		}
	}

	.zhaohui {
		text-align: right;
		color: #3c9cff;
	}


	.navs {
		padding: 26rpx 28.2% 0 28.2%;
		text-align: center;

		// display: flex;
		// justify-content: space-between;
		// align-items: center;
		.ones {
			color: #fff;
			font-size: 20rpx;
			padding-top: 6rpx;
			// border-right: 1px solid #ccc;
			// padding-right: 8%;
		}

		// .ones:nth-child(2){
		// 	border-right: 0px solid #000;
		// 	padding-right: 0rpx;
		// }
	}

	.other-login {
		// border-top: 1px solid #f3f3f3;
		margin-top: 30rpx;
		padding-top: 30rpx;
	}

	.naver {
		margin-top: 20rpx;
	}

	::v-deep .u-input {
		background-color: #fff;
		border: 1px solid #000 !important;
		width: 520rpx;
		height: 80rpx;
	}

	.yanzhengmas {
		border-radius: 100px 0px 0px 100px;
	}

	.btnsn {
		border-radius: 0px 100px 100px 0px;
		border: 1px solid #000 !important;
		height: 80rpx;
	}

	.qita {
		font-size: 20rpx;
		color: #000;
		padding-top: 20rpx;
	}

	::v-deep .uni-checkbox-input {
		border: 1px solid #000;
		width: 26rpx;
		height: 26rpx;
		margin-top: -2rpx;
	}

	.u-form-item {
		// display: block;
		// position: relative;
	}

	::v-deep .u-form-item__body__right__message {
		text-align: right;
		padding-right: 30rpx;
		// position: absolute;
		// top: 46rpx;
		// left: 30rpx;
	}
</style>