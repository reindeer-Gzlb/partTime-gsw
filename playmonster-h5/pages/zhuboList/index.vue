<template>
	<view class="mian" :style="{minHeight:gao + 'px'}">

		<view class="content-box">
			<view class="goods-box flex">
				<view class="goods-item2" v-for="item in list">
					<image :src="item.img" mode="widthFix" style="width: 100%;border-radius: 10rpx;"></image>
				</view>
			</view>
			<u-loadmore :status="loadMore" />
		</view>
		<u-toast ref="uToast"></u-toast>
		<!-- <u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="sureQuxiao" :show="show" title="提示" :asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal> -->
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
				loadMore: 'nomore',
				gao: ''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad() {
			this.getZhaohui()
		},

		methods: {
			// 获取主播列表
			getZhaohui() {
				homeApi.getImgs(1).then(res => {
					//console.log('zhubo', res)
					this.list = res.rows
				})
			},
			// 获取公告列表
			getGonggao() {
				homeApi.gonggaoDetails(20).then(res => {
					if (res && res.length) {
						this.list = res
					}

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
	.tip {
		color: #f56c6c;
	}

	.mian {
		font-size: 30rpx;

		border-top: none;
		background-color: #383B56;

		// background-color: #f1f1f1;
		// background-image:  linear-gradient(135deg,#fafbf3,#ffffff);
		.content-box {
			padding: 0 30rpx;
			position: relative;
		}

	}


	.goods-box {
		// margin-top: 10px;
		padding-top: 10px;
	}

	.goods-item2 {
		box-sizing: border-box;
		width: 100%;
		flex-shrink: 0;
		// margin-right: 30rpx;
		margin-bottom: 10px;
	}

	.btn {
		color: #939393;
	}

	::v-deep .u-line-1.u-loadmore__content__text {
		color: #fff !important;
	}
</style>