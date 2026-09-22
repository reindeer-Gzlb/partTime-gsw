<template>
	<view class="mian" :style="{height:gao + 'px'}">
		<view v-if="info" class="content-box" v-html="info.noticeContent">

		</view>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		components: {},
		data() {
			return {
				info: null,
				gao: ''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(option) {
			//console.log('option', option)
			if (option.id) {
				this.getDetail(option.id)
			} else {
				let data = uni.getStorageSync('tempNotice')
				//console.log(data)
				if (data) {
					this.info = data
				}
			}
		},

		methods: {
			getDetail(id) {
				homeApi.noticeDetails(id).then(res => {
					//console.log(res)
					if (res.code == 200) {
						this.info = res.data
						uni.setNavigationBarTitle({
							title: res.data.noticeTitle
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.tip {
		color: #f56c6c;
	}

	.mian {
		font-size: 30rpx;
		border-top: none;
		background-color: #383B56;
		min-height: calc(100vh - 20px);
		padding: 30rpx 0;

		.content-box {
			// padding: 0 30rpx;
			position: relative;

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

	/deep/ img {
		width: 100% !important;
	}
</style>
<style scoped>
	/deep/ .ql-size-small {
		font-size: 12px !important;
	}

	/deep/ .ql-size-large {
		font-size: 18px !important;
	}

	/deep/ .ql-size-huge {
		font-size: 32px !important;
	}
</style>