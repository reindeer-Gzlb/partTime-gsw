<template>
	<view class="mian">

		<view class="content-box">
			<view class="goods-box flex fbt">
				<view class="goods-item2" v-for="item in list" @click="goDetail(item)">
					<view class="img-box">
						<image :src="item.img" mode="widthFix" style="width: 100%;min-height: 200rpx;"></image>
					</view>
					<view class="des">
						{{item.noticeTitle}}
					</view>
					<view class="  lin flex fbt">
						<view class="qufu">
							<image src="/static/icon/shijian.png" mode=""
								style="width: 24rpx;height: 24rpx;margin-right: 5px;vertical-align: middle;"></image>
							{{item.createTime}}
						</view>
					</view>
				</view>
			</view>
			<u-loadmore :status="loadMore" />
		</view>
		<u-toast ref="uToast"></u-toast>
		<u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="sureQuxiao" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		components: {},
		data() {
			return {
				listMode: 'img',
				list: [],
				content: '',
				show: false, //提示弹出
				content: '',
				userInfo: {},
				currentItem: null,
				flag: 1,
				loadMore: 'nomore'
			}
		},
		onLoad(option) {
			// 获取url参数 1 公告列表 2.找回案例 
			if (option.flag) {
				this.flag = option.flag
			}
			if (this.flag == 2) {
				// 找回案例
				this.getZhaohui()
				uni.setNavigationBarTitle({
					title: '找回案例'
				});
			}
		},

		methods: {
			// 获取找回列表
			getZhaohui() {
				homeApi.backDetails().then(res => {
					if (res && res.length) {
						res.forEach(t => {
							if (t.noticeContent.indexOf('<img') > -1) {
								let arr = t.noticeContent.split('<img')
								let url = arr[1].split('\"')[1]
								t.img = url
							} else {
								t.img = 'https://game.ikbh.top/logo/no_img.jpg'
							}
						})
					}
					this.list = res
				})
			},


			// 下一页
			nextPage() {
				if (this.loadMore == 'loadmore') {
					this.searchParams.page += 1
					this.getList()
				}
			},
			// 打开详情
			goDetail(item) {
				uni.setStorageSync('tempNotice', item)
				uni.navigateTo({
					url: `/pages/noticeList/noticeDetail`
				})
			},
		}

	}
</script>

<style lang="scss" scoped>
	.mian {
		font-size: 30rpx;
		border-top: none;

		.content-box {
			padding: 0 30rpx;
			position: relative;
		}
	}


	.goods-box {
		margin-top: 10px;
	}


	.goods-item2 {
		box-sizing: border-box;
		width: 340rpx;
		flex-shrink: 0;
		// margin-right: 30rpx;
		margin-bottom: 10px;
		padding-bottom: 10px;
		background-color: #fff;
		border-radius: 30rpx;

		.img-box {
			width: 100%;
			height: 200rpx;
			border-radius: 10rpx;
			overflow: hidden;
		}

		.lin {
			margin: 5px 0;
			margin-top: 10px;
			padding: 0 20rpx;
		}


		.des {
			padding: 0 20rpx;
			margin-top: 20rpx;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 2;
			font-size: 28rpx;
			font-weight: bold;
		}

		.qufu {
			color: #8686aa;
			font-size: 24rpx;
			line-height: 36rpx;

			text {
				margin-left: 10px;
			}
		}
	}

	.btn {
		color: #939393;
	}
</style>