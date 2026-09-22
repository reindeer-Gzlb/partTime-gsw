<template>
	<view class="mian" :style="{minHeight:gao + 'px'}">
		<u-sticky offsetTop="0">
			<view class="tab-box">
				<u-tabs :activeStyle="{
            color: '#fff',
            fontWeight: 'bold',
            transform: 'scale(1.05)'
        }" :inactiveStyle="{
            color: '#9d9d9d',
            transform: 'scale(1)'
        }" :current="index" lineColor="#B42C1D" :list="statusList" @click="tabChange"></u-tabs>
			</view>
		</u-sticky>
		<!-- 2222222222 -->
		<view class="" style="margin-top: 20rpx;" v-for="item in list" @click="goDetail(item)">
			<view class="nav">
				<text class="oness">商品编号：{{item.id}}</text>
				<text class="twoss" v-if="item.saleState==0" style="color: blue;">审核中</text>
				<text class="twoss" v-if="item.saleState==2" style="color: green;">交易中</text>
				<text class="twoss" v-if="item.saleState==1" style="color: orange;">已上架</text>
				<text class="twoss" v-if="item.saleState==3" style="color: blue;">已出售</text>
				<text class="twoss" v-if="item.saleState==4" style="color: orange;">已下架</text>
				<text class="twoss" v-if="item.saleState==5" style="color: red;">问题账号</text>
				<text class="twoss" v-if="item.saleState==6" style="color: red;">找回账号</text>
				<text class="twoss" v-if="item.saleState==99" style="color: red;">审核失败</text>
				<!-- <text class="twoss">交易成功</text> -->
			</view>
			<view class="goods_detail">
				<view class="img-box">
					<image class="imgss" :src="item.titleImg" mode="widthFix"></image>
				</view>
				<view class="texts">
					{{item.des}}
				</view>
				<!-- <view class="qufu">
					{{item.systemName}}{{item.carrierName}}{{item.groupName}}{{item.serverName}}-{{item.gameName}}
		 		</view> -->
				<view class="qufu" v-if="item.gameType == 1">
					{{item.systemName}}{{item.carrierName}}{{item.groupName}}{{item.serverName}}-{{item.gameName}}
				</view>
				<view class="qufu" v-if="item.gameType == 0">
					{{item.systemName}}{{item.carrierName}}-{{item.gameName}}
				</view>
				<!-- <view class="qufu" v-if="item.gameType == 0">
		 			{{item.systemName}}{{item.carrierName}}-{{item.gameName}}
		 		</view> -->
				<view class="eyes">
					<u-icon name="eye" label-color="#cdcdcd" :label="item.browseNum ? item.browseNum : 0"
						labelSize="12px" color="#cdcdcd"></u-icon>
				</view>
				<view class="bapei">
					<image class="baopeiimg" src="/static/taohaoqu/zhichibaopei.png" mode="widthFix">
					</image>
					<image class="baopeiimg" src="/static/taohaoqu/pingtaifahuo.png" mode="widthFix">
					</image>
				</view>
				<view class="prices red">
					¥<text class="pricets">{{item.price}}</text>
				</view>
			</view>
			<view class="btn-box">
				<button class="btns" @click.stop="openof">联系客服</button>
				<button v-if="item.saleState==1" class="btns btnq" @click.stop="showDowns(item.id)">下架</button>
				<button v-if="item.saleState==1" class="btns btne" @click.stop="showModal(item.id)">修改价格</button>
				<button v-if="item.saleState==4" class="btns btnw" @click.stop="showupof(item.id)">上架</button>
			</view>
		</view>
		<u-loadmore :status="loadMore" />
		<!-- 3333333333 -->
		<u-toast ref="uToast"></u-toast>
		<!-- 	<u-modal :closeOnClickOverlay="true" :showCancelButton="true" @confirm="sureBtn" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal> -->
		<!-- 客服弹出层 -->
		<u-popup :closeable="true" closeIconPos="top-right" :round="10" :show="shows" @close="close">
			<view class="boxese">
				<view class="title">
					微信扫码咨询客服审核进度
				</view>
				<view class="img-box">
					<image class="imgsss" :src="obj.qrCode" mode="widthFix"></image>
				</view>
				<view class="name">
					{{obj.name}}
				</view>
				<view class="timers">
					服务时间：{{obj.workTime}}
				</view>
			</view>
		</u-popup>
		<!-- 修改价格 -->
		<u-modal title="修改价格" @cancel="cancelwq" confirmColor="#f2243c" :showCancelButton="true" :show="showtotal"
			@confirm="confirm">
			<view class="slot-content">
				<u-input placeholder="请输入价格" border="bottom" clearable v-model="inputValue"></u-input>
			</view>
		</u-modal>
		<!-- 下架 -->
		<u-modal confirmColor="#f2243c" @cancel="showdowns = false" :showCancelButton="true" @confirm="confirms"
			:show="showdowns" :title="title" :content='contentdowns'>
			<view class="slot-content">
				<u-input placeholder="请输入下架备注" border="bottom" clearable v-model="downDes"></u-input>
			</view>
		</u-modal>
		<!-- 上架 -->
		<u-modal confirmColor="#f2243c" @cancel="showkeyup = false" :showCancelButton="true" @confirm="confirmup"
			:show="showkeyup" :title="titles" :content='contentup'></u-modal>
	</view>
</template>

<script>
	import homeApi from "../../../api/home/index.js"
	import huishouApi from "../../../api/home/huishou.js"
	export default {
		components: {},
		data() {
			return {
				idup: '',
				showkeyup: false,
				titles: '上架提示',
				contentup: '上架操作订单将在前端展示，确认继续操作吗？',
				iddown: '', //下架
				downDes: '',
				title: '下架提示：下架操作用户将看不到该订单，确认继续操作？',
				showdowns: false,
				contentdowns: '下架操作用户将看不到该订单，确认继续操作？',
				ids: '',
				inputValue: '',
				obj: {},
				showtotal: false, //修改价格
				shows: false,
				index: 0,
				num: '',
				listMode: 'img',
				list: [],
				content: '',
				currentBtn: '',
				currentData: {},
				show: false, //提示弹出
				content: '',
				userInfo: {},
				currentItem: null,
				searchParams: {
					pageNum: 1,
					pageSize: 10,
					saleState: ''
				},
				currentStatus: '1',

				statusList: [{
						name: "全部",
						value: ""
					},
					{
						name: "审核中",
						value: "0"
					},
					// {
					// 	name: "未出售",
					// 	value: "1"
					// },
					{
						name: "已上架",
						value: "1"
					},
					{
						name: "交易中",
						value: "2"
					},
					{
						name: "已出售",
						value: "3"
					},
					{
						name: "已下架",
						value: "4"
					},
					// {
					// 	name: "问题账号",
					// 	value: "5"
					// },
					// {
					// 	name: "找回账号",
					// 	value: "6"
					// },
					{
						name: "审核失败",
						value: "99"
					}
				],
				gao: ''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(option) {
			this.userInfo = uni.getStorageSync('userInfo')
			if (!this.userInfo) {
				uni.showToast({
					title: "请先登录",
					icon: 'none'
				})
				setTimeout(() => {
					uni.navigateTo({
						url: '/pages/login/index'
					})
				}, 1000)
			}
			if (option.saleState) {
				this.searchParams.saleState = option.saleState
				const index = this.statusList.findIndex((item, index) => item.value == this.searchParams.saleState)
				////console.log(index);
				this.index = index
				// if(index.length != 0){
				// 	this.index = index[0].value
				// }
			}

			this.getList()
			// this.tabInfo()

			// const seletedt = this.$route.query.saleState
		},
		onReachBottom() {
			this.nextPage()
		},
		onPullDownRefresh() {
			this.list = []
			this.searchParams.pageNum = 1
			this.getList()
		},
		mounted() {


			// this.getList()
		},
		methods: {
			//上架
			showupof(id) {
				////console.log(id);
				this.idup = id
				this.showkeyup = true
			},
			async confirmup() {
				const res = await homeApi.shangjiaGoods(this.idup)
				//console.log(res);
				this.list = []
				this.getList()
				this.showkeyup = false;
				uni.showToast({
					title: "上架成功",
					icon: 'none'
				})
			},
			//下架
			showDowns(id) {
				//console.log(id);
				this.iddown = id
				this.showdowns = true;
			},
			async confirms() {
				if (this.downDes == '') {
					uni.showToast({
						title: '请输入下架备注！',
						icon: 'none'
					})
					return
				}
				const res = await homeApi.xiajiaGoods({
					id: this.iddown,
					downDes: this.downDes
				})
				//console.log(res);
				this.list = []
				this.getList()
				this.showdowns = false;
				uni.showToast({
					title: "下架成功",
					icon: 'none'
				})
			},
			//修改价格
			showModal(id) {
				//console.log(id);
				this.ids = id
				this.showtotal = true;
				// this.confirm(id)

			},
			cancelwq() {
				this.showtotal = false;
			},
			async confirm() {
				if (this.inputValue == '') {
					uni.showToast({
						title: '请输入要修改的价格！',
						icon: 'none'
					})
					return
				}
				const res = await homeApi.changePrice({
					id: this.ids,
					price: this.inputValue
				})
				//console.log(res);
				this.list = []
				this.getList()
				this.showtotal = false;
				uni.showToast({
					title: "价格修改成功",
					icon: 'none'
				})
				// setTimeout(() => {
				// 	// 3秒后自动关闭
				// }, 500)
			},
			async openof() {
				uni.reLaunch({
					url: '/pages/message/index'
				})
				// const res = await homeApi.lianxikefus({
				// 	type: 3
				// })
				// //console.log('66666666666', res);
				// this.obj = res[0]
				// // this.shows = true
				// this.openMsg(res[0])


			},
			// 联系客服  打开聊天窗
			async openMsg(e) {
				var my = uni.getStorageSync("myUsername");
				var nameList = {
					myName: my,
					your: e.hxname,
					yourNickName: e.name
				};
				//console.log('nameList', nameList)
				uni.navigateTo({
					url: "../../chatroom/chatroom?username=" + JSON.stringify(nameList),
				});
			},
			//弹出层
			close() {
				this.shows = false
			},
			// 打开详情
			goDetail(item) {
				uni.navigateTo({
					url: '/pages/user/maidanxinagqingtiao?id=' + item.id,
				})
			},
			// tabInfo(){
			// 	this.searchParams.saleState = this.num
			// 	this.searchParams.pageNum = 1
			// 	this.searchParams.pageSize = 10
			// 	this.list = []
			// 	this.getList()
			// },
			tabChange(e) {
				//console.log(e);
				// debugger
				// //console.log(e);
				this.searchParams.saleState = e.value
				//console.log(this.searchParams)
				this.searchParams.pageNum = 1
				this.searchParams.pageSize = 10
				this.list = []
				this.getList()
			},
			// 获取列表
			getList() {
				this.loadMore = 'loading'
				huishouApi.myAccountList(this.searchParams).then(res => {
					//console.log(res)
					uni.stopPullDownRefresh()
					// this.list = res.rows
					// this.loadMore = 'nomore'
					if (res.rows.length >= this.searchParams.pageSize) {
						this.loadMore = 'loadmore'
					} else {
						this.loadMore = 'nomore'
					}
					this.list = this.list.concat(res.rows)
				})
			},
			// // 同意报价
			// tongyi(item) {
			// 	this.currentBtn = 'tongyi'
			// 	this.content = '同意后不可撤销，确认同意该价格吗？'
			// 	this.show = true
			// 	this.currentData = item
			// },
			// jujue(item) {
			// 	this.currentBtn = 'jujue'
			// 	this.content = '确认拒绝该价格吗？'
			// 	this.show = true
			// 	this.currentData = item
			// },
			// sureBtn() {
			// 	let obj = {
			// 		id: this.currentData.id
			// 	}
			// 	if (this.currentBtn == 'tongyi') {
			// 		kanjiaApi.accept(obj).then(res => {
			// 			uni.showToast({
			// 				title: res.msg,
			// 				icon: 'none'
			// 			})
			// 			this.currentBtn = ''
			// 			this.content = ''
			// 			this.show = false
			// 			this.currentData = {}
			// 			if (res.code == 200) {
			// 				this.getList()
			// 			}
			// 		})
			// 	} else if (this.currentBtn == 'jujue') {
			// 		kanjiaApi.refuse(obj).then(res => {
			// 			uni.showToast({
			// 				title: res.msg,
			// 				icon: 'none'
			// 			})
			// 			this.currentBtn = ''
			// 			this.content = ''
			// 			this.show = false
			// 			this.currentData = {}
			// 			if (res.code == 200) {
			// 				setTimeout(() => {
			// 					this.getList()
			// 				})

			// 			}
			// 		})
			// 	}
			// },
			// // 查看报价
			// viewPrice(item) {
			// 	uni.navigateTo({
			// 		url: `./priceDetail?id=${item.id}`,

			// 	})
			// },


			// 下一页
			nextPage() {
				if (this.loadMore == 'loadmore') {
					this.searchParams.pageNum += 1
					this.getList()
				}
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

		// view {
		// 	font-size: 24rpx;
		// }
		.tab-box {
			background-color: #383B56;
			padding: 0 30rpx;
			padding-bottom: 10rpx;
			// position: fixed;
			// top: 44px;
			// z-index: 99;
		}

		.content-box {
			padding: 0 30rpx;
			position: relative;
		}
	}


	.goods-box {
		margin-top: 10px;
	}


	.goods-item2 {
		view {
			font-size: 24rpx;
		}

		box-sizing: border-box;
		width: 100%;
		flex-shrink: 0;
		// margin-right: 30rpx;
		margin-bottom: 10px;
		padding-bottom: 10px;
		background-color: #fff;
		border-radius: 30rpx;

		.time {
			font-size: 24rpx;
		}

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
			align-items: center;
		}



		.baojia {
			margin-left: 20rpx;

			.price {
				font-weight: bold;
				font-size: 28rpx;
				margin-left: 5rpx;
			}
		}

		.price-box {
			justify-content: flex-end;
			align-items: center;
		}

		.yuanjia {
			margin-left: 20rpx;
			text-decoration: line-through;
		}

		.huan {
			margin-left: 20rpx;
		}

		.huanjia {
			margin-left: 5rpx;
			font-size: 30rpx;
			color: red;
			font-weight: 601;
		}

		.des {
			padding: 0 20rpx;
			margin-top: 20rpx;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 4;
			font-size: 26rpx;
		}

		.qufu {
			color: #f1924e;
			font-size: 26rpx;

			text {
				margin-left: 10px;
			}
		}
	}


	.btn {
		color: #ff9a51;
	}

	.nav {
		width: 92%;
		height: 100rpx;
		line-height: 100rpx;
		padding: 20rpx 30rpx;
		background-color: #1C1632;
		box-sizing: border-box;
		margin: 0 30rpx;
		border-bottom: 1px solid #5e5e5e;
		position: relative;
		border-radius: 20rpx 20rpx 0 0;

		.imgws {
			width: 58rpx;
			height: 58rpx;
		}

		.oness {
			position: absolute;
			bottom: 0;
			left: 3%;
			color: #fff;
		}

		.twoss {
			position: absolute;
			bottom: 0;
			right: 5%;
			color: #ff6400;
		}
	}

	.goods_detail {
		padding: 20rpx;
		width: 92%;
		height: 238rpx;
		background-color: #1C1632;
		margin: 0rpx 30rpx 0rpx 30rpx;
		// border-radius: 15rpx;
		// border-bottom:1px solid #f7f7f7;
		position: relative;

		.img-box {
			width: 192rpx;
			height: 184rpx;
			border-radius: 20rpx;
			overflow: hidden;
		}

		.imgss {
			width: 100%;
			min-height: 184rpx;
		}

		.texts {
			position: absolute;
			top: 6.5%;
			left: 33%;
			right: 3%;
			display: -webkit-box;
			-webkit-line-clamp: 2; // 指定要显示的行数
			-webkit-box-orient: vertical;
			overflow: hidden;
			text-overflow: ellipsis; // 显示省略号
			font-size: 28rpx;
			color: #fff;
		}

		.qufu {
			position: absolute;
			top: 45%;
			left: 33%;
			right: 3%;
			font-size: 24rpx;
			color: #cdcdcd;
		}

		.eyes {
			position: absolute;
			top: 45%;
			// left: 86%;
			right: 3%;
		}

		.bapei {
			position: absolute;
			top: 66%;
			left: 33%;
			right: 3%;

			.baopeiimg {
				width: 110rpx;
				margin-right: 16rpx;
			}
		}

		.prices {
			position: absolute;
			top: 68%;
			// left: 81%;
			right: 3%;
			font-weight: 500;

			.pricets {
				padding-left: 10rpx;
				font-size: 36rpx;
			}
		}

	}

	.btn-box {
		width: 690rpx;
		height: 108rpx;
		line-height: 108rpx;
		margin: 0 30rpx;
		padding: 20rpx;
		background-color: #1C1632;
		border-top: 1px solid #5e5e5e;
		// position: relative;
		display: flex;
		// flex-direction: row-reverse;
		justify-content: flex-end;
		border-radius: 0 0 20rpx 20rpx;

		.btns {
			flex-shrink: 0;
			width: 180rpx;
			height: 60rpx;
			line-height: 60rpx;
			font-size: 28rpx;
			border: 1px solid #cdcdcd;
			border-radius: 15rpx;
			background-color: #fff;
			margin: 0;
			margin-left: 20rpx;
		}

		.btne {
			border: 1px solid #B42C1D;
			color: #B42C1D;
		}

		.btnq {
			border: 1px solid #B42C1D;
			background-color: #B42C1D;
			color: #fff;
		}

		.btnw {

			border: 1px solid #B42C1D;
			background-color: #B42C1D;
			color: #fff;
		}
	}

	.boxese {
		padding: 20rpx;
		text-align: center;

		.title {
			padding-top: 50rpx;
			font-size: 36rpx;
			font-weight: 700;
		}

		.imgsss {
			padding-top: 50rpx;
			width: 232rpx;
			height: 232rpx;
		}

		.timers {
			padding-top: 30rpx;
			font-size: 26rpx;
			font-weight: 700;
			color: #516488;
		}
	}

	::v-deep .u-line-1.u-loadmore__content__text {
		color: #fff !important;
	}

	// ::v-deep .u-tabs__wrapper__nav__item__text{
	// 	color: #fff !important;
	// }
</style>