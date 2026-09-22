<template>
	<view class="main" :style="{height: gao + 'px' }">
		<view class="nav">
			<view class="zhanghao">
				<text class="text"></text><text class="ziti">请在下方输入需要查询的账号</text>
			</view>
			<u--form labelPosition="top" :model="model1" ref="uForm" label-width="333" lab>
				<u-form-item label="官方客服微信验证" prop="name" ref="item1">
					<u--input v-model="model1.weixin" border="surround" :placeholder="`请输入客服微信号`"></u--input>
					<u-button
						style="margin-left: 20rpx;width: 20%;background-color: #B42C1D;color: #fff;border-radius: 15rpx;height: 78rpx;"
						@click="getweixinkefu">查询</u-button>
				</u-form-item>
				<!-- 	<u-form-item label="平台收款账号查询" prop="name" ref="item1">
					<u--input v-model="model1.collection" border="surround" :placeholder="`请输入平台收款账号`"></u--input>
					<u-button
						style="margin-left: 20rpx;width: 20%;background-color: #469dfd;color: #fff;border-radius: 15rpx;height: 78rpx;"
						@click="shoukuan">查询</u-button>
				</u-form-item> -->
				<u-form-item label="黑号查询(QQ/微信账号有申诉/找回记录的用户)" prop="name" ref="item1">
					<u--input v-model="model1.content" border="surround" :placeholder="`请输入黑号被举报人的手机/QQ/微信`"></u--input>
					<u-button
						style="margin-left: 20rpx;width: 20%;background-color: #B42C1D;color: #fff;border-radius: 15rpx;height: 78rpx;"
						@click="heihaochaxun">查询</u-button>
				</u-form-item>
			</u--form>
			<view class="error" style="color: #f2243c;margin: 50rpx 0;">
				提示：输入客服的联系方式/收款方式查询是否为官方账号，若无查询结果，请谨慎上当受骗！
			</view>
			<!-- <view class="zhanghao">
				<text class="text"></text><text class="ziti">如何辨別真伪教程</text>
			</view>
			<view class="cooperate" style="margin-top: 39rpx;">
				<view class="img" v-html="obj.noticeContent">

				</view>
			</view> -->
		</view>
		<!-- 官方微信 -->
		<!-- <u-modal :show="show" @confirm="confirms" :title="title" :content='content' confirmText="我知道了" confirmColor="#858689"></u-modal> -->
		<u-popup :show="show" mode="center" :closeable="true" closeIconPos="top-right" @close="confirms">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/lose.58097953.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					该账号不是怪兽玩代售官方客服，谨防受骗！
				</view>
			</view>
		</u-popup>
		<u-popup :show="shows" mode="center" :closeable="true" closeIconPos="top-right" @close="confirmss">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/succeed.35c4bfe4.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					该账号为怪兽玩代售官方客服，请放心咨询！
				</view>
			</view>
		</u-popup>
		<!-- <u-modal :show="shows" @confirm="confirmss" :title="title" :content='contents' confirmText="我知道了" confirmColor="#858689"></u-modal> -->
		<!-- 收款账号 -->
		<!-- 		<u-modal :show="shownav" @confirm="confirme" :title="title" :content='contente' confirmText="我知道了" confirmColor="#858689"></u-modal>
		<u-modal :show="showsnavto" @confirm="confirmee" :title="title" :content='contentee' confirmText="我知道了" confirmColor="#858689"></u-modal> -->
		<u-popup :show="shownav" mode="center" :closeable="true" closeIconPos="top-right" @close="confirme">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/lose.58097953.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					该账号不是怪兽玩代售官方账号，谨防受骗！
				</view>
			</view>
		</u-popup>
		<u-popup :show="showsnavto" mode="center" :closeable="true" closeIconPos="top-right" @close="confirmee">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/succeed.35c4bfe4.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					该账号为怪兽玩代售官方账号，请放心咨询！
				</view>
			</view>
		</u-popup>
		<!-- 黑号查询 -->
		<!-- <u-modal :show="showon" @confirm="confirmn" :title="title" :content='contentn' confirmText="我知道了" confirmColor="#858689"></u-modal>
		<u-modal :show="shownum" @confirm="confirmnn" :title="title" :content='contentnn' confirmText="我知道了" confirmColor="#858689"></u-modal> -->
		<u-popup :show="showon" mode="center" :closeable="true" closeIconPos="top-right" @close="confirmn">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/lose.58097953.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					当前查询账号有找回记录，谨慎受骗！
				</view>
			</view>
		</u-popup>
		<u-popup :show="shownum" mode="center" :closeable="true" closeIconPos="top-right" @close="confirmnn">
			<view class="" style="width: 600rpx;min-height: 100rpx;text-align: center;">
				<view class="img" style="margin-top: 60rpx;">
					<image style="width: 200rpx;
						height: 200rpx;" src="/static/feiheng/succeed.35c4bfe4.png" mode=""></image>
				</view>
				<view class="" style="font-size: 28rpx;height: 100rpx;margin-top: 30rpx;">
					当前查询账号没有找回记录，请放心咨询！
				</view>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		data() {
			return {
				obj: {},
				noticeType: 'kfyz',
				model1: {
					weixin: '',
					collection: '',
					content: ''
				},
				userInfo: null,
				show: false,
				shows: false,
				shownav: false,
				showsnavto: false,
				showon: false,
				shownum: false,
				title: '温馨提示',
				content: `该账号不是怪兽玩代售官方客服，谨防受骗！`,
				contents: `该账号为怪兽玩代售官方客服，请放心咨询！`,
				contente: `该账号不是怪兽玩代售官方账号，谨防受骗！`,
				contentee: `该账号为怪兽玩代售官方账号，请放心咨询！`,
				contentn: '当前查询账号有找回记录，谨慎受骗！',
				contentnn: '当前查询账号没有找回记录，请放心咨询！',
				gao: ''
			}
		},
		onLoad() {
			this.getlist()
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		methods: {
			// 获取个人信息
			async getUserInfo() {
				let res = await userApi.getInfo()
				//console.log(res)
				if (res.code == 200) {
					this.userInfo = res.data
					return true
				} else {
					this.userInfo = {}
					return false
				}
			},
			async getlist() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.noticeType
				})
				//console.log(res);
				this.obj = res[0]
			},
			async getweixinkefu() {
				if (this.model1.weixin == '') {
					uni.showToast({
						title: '内容不能为空！',
						icon: 'none'
					})
					return
				}
				const res = await homeApi.weixinyanzheng({
					weixin: this.model1.weixin
				})
				//console.log(res);
				if (res.code == 500) {
					this.show = true
				} else if (res.code == 200) {
					this.shows = true
				}
			},
			confirms() {
				this.show = false
			},
			confirmss() {
				this.shows = false
			},
			async shoukuan() {
				if (this.model1.collection == '') {
					uni.showToast({
						title: '内容不能为空！',
						icon: 'none'
					})
					return
				}
				const res = await homeApi.weixinyanzheng({
					collection: this.model1.collection
				})
				//console.log(res);
				if (res.code == 500) {
					this.shownav = true
				} else if (res.code == 200) {
					this.showsnavto = true
				}
			},
			confirme() {
				this.shownav = false
			},
			confirmee() {
				this.showsnavto = false
			},
			async heihaochaxun() {
				if (this.model1.content == '') {
					uni.showToast({
						title: '内容不能为空！',
						icon: 'none'
					})
					return
				}
				const res = await homeApi.heihaochaxun({
					content: this.model1.content
				})
				if (res.code == 401) {
					return
				}
				if (res.total != 0) {
					this.showon = true
				} else if (res.total == 0) {
					this.shownum = true
				}
			},
			confirmn() {
				this.showon = false
			},
			confirmnn() {
				this.shownum = false
			},
		},
	}
</script>

<style lang="less" scoped>
	.main {
		padding: 0 30rpx;
		background-color: #383B56;

		.nav {
			.zhanghao {
				.text {
					padding: 5rpx 5rpx;
					background-color: #B42C1D;
				}

				.ziti {
					font-size: 32rpx;
					font-weight: 700;
					color: #fff;
					margin-left: 15rpx;
				}
			}
		}
	}

	::v-deep .u-form-item__body__left__content__label {
		color: #fff;
	}
	
	/deep/ .uni-input-input {
		color: #fff;
	}
	
</style>