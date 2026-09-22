<template>
	<view class="mian">

		<view class="content-box">
			<view class="goods-box flex">
				<view class="goods-item2" v-for="item in list" @click="goDetail(item)">
					<view class="des">
						{{item.noticeTitle}}
					</view>
					<view class="  lin flex fbt">
						<view class="qufu">
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
			} else if (this.flag == 1) {
				// 公告列表
				this.getGonggao()
			}
		},

		methods: {
			// 获取找回列表
			getZhaohui() {
				homeApi.backDetails().then(res => {
					this.list = res
				})
			},
			// 获取公告列表
			getGonggao() {
				homeApi.gonggaoDetails().then(res => {
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
		width: 100%;
		flex-shrink: 0;
		margin-bottom: 10px;
		padding-bottom: 10px;
		background-color: #fff;
		border-radius: 30rpx;

		.img-box {
			width: 100%;
			height: 200px;
			border-radius: 30rpx 30rpx 0 0;
			overflow: hidden;
		}

		.lin {
			margin: 5px 0;
			margin-top: 10px;
			padding: 0 20rpx;
		}

		.price {
			font-weight: bold;
			font-size: 20px;
		}

		.des {
			padding: 0 20rpx;
			margin-top: 20rpx;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 1;
			font-size: 30rpx;
			font-weight: bold;
		}

		.qufu {
			color: #878787;
			font-size: 26rpx;

			text {
				margin-left: 10px;
			}
		}
	}
</style>