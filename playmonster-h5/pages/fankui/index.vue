<template>
	<view class="mian" :style="{height:gao + 'px'}">
		<!-- <view class="title-box">交易订单资料补充</view> -->
		<!-- <view style="height: 50px;"></view> -->
		<view class="content-box">
			<!-- <view class="logo">
				<image class="img" src="/static/qqt/qiuqiutonglogo2.png" mode=""></image>
			</view> -->
			<view class="form-box">
				<u--form labelPosition="left" :model="formData" :rules="rules" ref="uForm" label-width="75"
					:labelStyle="{fontWeight:600}">
					<u-form-item label="反馈类型" prop="typeName" @click="showGameType = true">
						<u--input v-model="formData.typeName" border="surround" suffixIcon="arrow-down" readonly
							color="#333" placeholder="请选择反馈类型">
						</u--input>
					</u-form-item>

					<u-form-item label="反馈内容" prop="content">
						<u--textarea v-model="formData.content" border="surround" maxlength="-1" color="#333"
							placeholder="反馈内容...">
						</u--textarea>
					</u-form-item>
					<u-form-item label="联系电话" prop="tel" ref="item0">
						<u--input :placeholder="`请填写账号/手机号`" border="surround" v-model="formData.tel">
						</u--input>
					</u-form-item>
					<u-form-item label="QQ" prop="qq" ref="item0">
						<u--input :placeholder="`请填写qq`" border="surround" v-model="formData.qq">
						</u--input>
					</u-form-item>
					<u-form-item label="图片" prop="" ref="item3">
						<view class="drag-box">
							<my-drag-files @drop="ondrop($event,'1')">
								<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic"
									accept="image" name="1" multiple uploadText="点击上传" :maxCount="20">
								</u-upload>
								<!-- <view class="tip">
									提示：请上传tupi等<br />
								</view> -->
							</my-drag-files>

						</view>

					</u-form-item>
				</u--form>
			</view>
			<view class="btns">
				<u-button @click="submit" style="background-color: #B42C1D;border:none;color: #fff;">提交</u-button>
			</view>
			<!-- 	<view class="btns">
				<u-button @click="submit" type="primary">登录</u-button>
			</view>
			<view class="tip">
				没有账号，<text class="link" @click="goReg">立即注册</text>
			</view> -->
		</view>
		<!-- 反馈类型弹窗 -->
		<u-picker :show="showGameType" :columns="typeOption" keyName="name" closeOnClickOverlay
			@close="showGameType = false" @cancel="showGameType = false" @confirm="typeChange"></u-picker>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import myDragFiles from "@/components/my-dragfiles.vue"
	export default {
		components: {
			myDragFiles
		},
		data() {
			return {
				showGameType: false,
				userInfo: null,
				gao: '',
				formData: {
					qq: '',
					tel: '',
					imgurl: '',
					createId: '',
					title: '',
					content: '',
					type: 1,
					typeName: '改进建议'
				},
				typeOption: [
					[{
							name: '改进建议',
							id: 1
						},
						{
							name: '问题反馈',
							id: 2
						},
						{
							name: '投诉举报',
							id: 3
						},
					]
				],
				fileList1: [],
				isUploading: false,
				rules: {
					'content': {
						required: true,
						message: '请填写反馈内容',
						trigger: ['blur', 'change']
					},
					'tel': {
						required: true,
						message: '请填写联系电话',
						trigger: ['blur', 'change']
					},
					'qq': {
						required: true,
						message: '请输入QQ',
						trigger: ['blur', 'change']
					}
				},
			}

		},
		onLoad(option) {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
			// this.getCode()
			let user = uni.getStorageSync('userInfo')
			this.userInfo = user
		},
		methods: {
			typeChange(e) {
				//  e.value[0]
				//console.log('e.value[0]', e.value[0])
				this.formData.typeName = e.value[0].name
				this.formData.type = e.value[0].id
				this.showGameType = false
			},
			// 提交
			submit() {
				if (this.isUploading) {
					uni.showToast({
						title: '正在上传...',
						icon: 'none'
					})
					return
				}
				this.formData.createId = this.userInfo.userId
				this.$refs.uForm.validate().then(res => {
					//console.log(this.fileList1)
					//console.log(this.formData)
					if (this.fileList1 && this.fileList1.length) {
						let arr = []
						this.fileList1.map(i => {
							arr.push(i.url)
						})
						// this.formData.imgurl = JSON.stringify(arr)
						this.formData.imgurl = arr.join(',')
						//console.log(this.formData.imgurl);
					}

					homeApi.addproblem(this.formData).then(res => {
						if (res.code == 200) {
							uni.showToast({
								title: '提交成功',
								icon: 'none'
							})

							this.$refs.uForm.resetFields()
							// uni.navigateBack()
							setTimeout(function() {
								uni.reLaunch({
									url: '/pages/mine/index'
								})
							}, 500);
						} else {
							uni.showToast({
								title: res.msg,
								icon: 'none'
							})
						}
					})
				}).catch(errors => {

				})
			},
			// // 获取个人信息
			// getUserInfo(token){
			// 	userApi.getInfo(token).then(res=>{
			// 		 //console.log(res)
			// 		uni.setStorageSync('userInfo',res.data)
			// 		uni.switchTab({
			// 			url:'/pages/home/index'
			// 		})
			// 	})
			// },
			// goReg(){
			// 	uni.navigateTo({
			// 		url:"/pages/login/register"
			// 	})
			// },

			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				//console.log(event)
				let arr = event.file.map((item) => item.name)
				if (arr.join(' ').indexOf(',') > -1) {
					uni.showToast({
						title: '图片名称不能包含特殊字符',
						icon: 'none'
					})
					return
				}
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				this.isUploading = true
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i])
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result.data.filePath
					}))
					fileListLen++
				}
				this.isUploading = false
				//console.log(this.fileList1)
			},
			uploadFilePromise(item) {
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: this.apiUrl + '/upload/image', // 图片上传接口
						filePath: item.url,
						name: 'file',
						formData: {},
						success: (res) => {
							setTimeout(() => {
								resolve(JSON.parse(res.data))
							}, 1000)
						}
					});
				})
			},
			// 拖动上传
			ondrop(e, name) {
				let file = e.detail.files
				//console.log(e)
				let fileArr = []
				if (file && file.length) {
					for (let i = 0; i < file.length; i++) {
						fileArr.push({
							name: file[i].name,
							url: this.getTempFilePath(file[i])
						})
					}
				}
				let obj = {
					file: fileArr,
					name: name
				}
				this.afterRead(obj)
			},
			getTempFilePath(file) {
				let path;
				if (window.createObjectURL) path = window.createObjectURL(file);
				else if (window.webkitURL) path = window.webkitURL.createObjectURL(file);
				else if (window.URL) path = window.URL.createObjectURL(file);
				else console.error('getTempFilePath has error');
				return path;
			},
		}
	}
</script>

<style lang="scss" scoped>
	.mian {
		font-size: 16px;
		height: calc(100vh - 20px);
		overflow-y: auto;
		position: relative;
		// background-image: url('/static/img/recovery-bg.png');
		// background-size: 100% auto;
		background-color: #383B56;
		background-repeat: no-repeat;
		border-top: none;
		padding: 0 30rpx;

		.content-box {

			.logo {
				width: 420rpx;
				height: 140rpx;
				margin: 0 auto;
				margin-top: 80rpx;
				background-color: #3c9cff;

				.img {
					width: 100%;
					height: 100%;
				}
			}
		}

		// .title-box {
		// 	height: 50px;
		// 	width: 100%;
		// 	max-width: 1240px;
		// 	background-color: #3c9cff;
		// 	color: #fff;
		// 	line-height: 50px;
		// 	text-indent: 20px;
		// 	margin-bottom: 10px;
		// 	font-size: 18px;
		// 	font-weight: 601;
		// 	text-align: center;
		// 	position: absolute;
		// 	left: 0;
		// 	top: 0;
		// 	z-index: 10;
		// }

	}

	.btns {
		margin: 20rpx auto;
		max-width: 300px;

	}

	.btns /deep/ .u-button {
		margin-top: 30rpx;
		background-color: #3c9cff;
		border-color: #3c9cff;
	}


	.pointer {
		cursor: pointer;
		color: rgb(41, 121, 255);
		text-decoration: underline;
	}

	.form-box {
		background-color: #272540;
		margin-top: 15px;
		// border: 1px solid #e3e3e3;
		border-radius: 10px;
		padding: 15px;

	}

	.tip {
		width: 100%;
		text-align: center;
		color: #666;

		.link {
			color: #3c9cff;
		}
	}

	.drag-box {
		width: 100%;
		padding: 20px;
		border: 1px dashed #ccc;
		border-radius: 8px;

	}

	.drag-box:hover {
		border-color: #3c9cff;
		cursor: pointer;
	}

	.drag-box ::v-deep .u-upload__button {
		width: 200px !important;
		height: 110px !important;
	}

	.drag-box ::v-deep .u-upload__button__text {
		font-size: 14px;
	}

	.drag-box ::v-deep .u-upload__wrap__preview__image {
		height: 110px !important;
	}


	/deep/ .u-form-item__body__left__content__label {
		color: #fff;
	}

	/deep/ .uni-input-input {
		color: #fff;
	}
	

</style>