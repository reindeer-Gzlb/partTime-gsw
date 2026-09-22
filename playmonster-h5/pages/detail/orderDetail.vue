<template>
	<view class="page-box">
		<view class="order-box">
			<view class="tit-box">
				<view class="status-txt">
					交易中
				</view>
				<view class="status-txt-tip">
					请在 <text style="color: #FF2C00;">22：36</text> 分钟内付款，超时系统将取消订单
				</view>
			</view>
			<view class="goods-box ">
				<view class="code">
					商品编号：{{orderInfo.thirdId}}
				</view>
				<view class="flex" style="justify-content: space-between;" @click.stop="goLink">
					<view class="img-box">
						<image :src="orderInfo.titleImg" mode=""></image>
					</view>
					<view class="goods-cont">
						<view class="goods-des">
							{{orderInfo.des}}
						</view>
						<view class="goods-qufu flex fbt">
							<view class="">
								<view class="qufu">
									{{orderInfo.gameName}}
								</view>
							</view>
							<!-- <view class="times flex">
								<u-icon size="26rpx" bold name="eye" color="#999" class="icon">
								</u-icon>
								<view class="times-num">
									{{orderInfo.browseNum?orderInfo.browseNum:0}}
								</view>
							</view> -->
						</view>
						<!-- <view class="bot-box flex fbt">
							<view class="bapei">
								<image class="baopeiimg" src="/static/feiheng/baopeiIcon/zcbp.png" mode="widthFix">
								</image>
								<image class="baopeiimg" src="/static/feiheng/baopeiIcon/ptfh.png" mode="widthFix">
								</image>
							</view>
							<view class="price">
								¥<text class="price-num">{{orderInfo.goodsAmount}}</text>
							</view>
						</view> -->
					</view>
					<view class="rigth_box_New">
						<view class="price_box">
							￥{{orderInfo.newPrice}}
						</view>
						<view class="shop_num">
							共1件
						</view>
					</view>
				</view>
				<!-- <view class="code">
					商品编号：{{orderInfo.goodsId}}
				</view> -->
				<view class="price-list">
					<view class="price-line flex fbt">
						<view class="name">
							商品金额
						</view>
						<view class="price-txt">
							¥{{orderInfo.newPrice}}
						</view>
					</view>
					<view class="price-line flex fbt">
						<view class="name flex ">
							<text style="margin-right: 10rpx;">找回包赔服务费</text>
							<u-icon size="34rpx" name="question-circle-fill" color="#D28726" class="icon"
								>
							</u-icon>
						</view>
						<view class="price-txt">
							¥{{(orderInfo.newPrice * 0.1).toFixed(2)}}
						</view>
					</view>
					<view class="price-line flex fbt">
						<view class="name flex">
							<text style="margin-right: 10rpx;">平台服务费</text>
							<u-icon size="34rpx" name="question-circle-fill" color="#D28726" class="icon"
								>
							</u-icon>
						</view>
						<view class="price-txt">
							¥10
						</view>
					</view>
				</view>
				<view class="order-total">
					<text
						style="font-family: STHeitiSC, STHeitiSC;font-weight: 500;font-size: 28rpx;color: #333333;line-height: 42rpx;text-align: left;font-style: normal;">应付：</text>
					<text class="price-txt">¥{{Number(orderInfo.newPrice) + Number((orderInfo.newPrice * 0.1).toFixed(2)) + 10 }}</text>
				</view>
			</view>

			<view class="lianxi_kefu_new" @click="openof">
				<view class="" style="margin-top: 12rpx;">
					<image style="width: 32rpx;height: 32rpx;" src="/static/feihengNew/mai/lt_icon.png" mode=""></image>
				</view>
				<view class="kefu_lianxi">
					联系客服
				</view>
			</view>



			<view class="order-detail">
				<!-- <view class="title flex">
					<view class="tit-txt" style="font-family: STHeitiSC, STHeitiSC;">
						订单详情
					</view>
				</view> -->
				<view class="time-list">
					<view class="price-box goods-price flex">
						<view class="price-name">
							订单编号
						</view>
						<view class="price">
							xs202506291923165682056
						</view>
						<view class="fuzhi" @click="fuzhi('xs202506291923165682056')">
							复制
						</view>
					</view>
					<view class="price-box goods-price flex">
						<view class="price-name">
							下单时间
						</view>
						<view class="price">
							2025-06-29 16:51:33
						</view>
					</view>
					<view class="price-box goods-price flex" v-if="orderInfo.payEndTime">
						<view class="price-name">
							支付时间
						</view>
						<view class="price">
							{{orderInfo.payEndTime}}
						</view>
					</view>
					<view class="price-box goods-price flex" v-if="orderInfo.orderStatus==1">
						<view class="price-name">
							完成时间
						</view>
						<view class="price">
							{{orderInfo.finishTime}}
						</view>
					</view>
					<view class="price-box goods-price flex" v-if="orderInfo.orderStatus==2">
						<view class="price-name">
							取消时间
						</view>
						<view class="price">
							{{orderInfo.cancelTime}}
						</view>
					</view>
				</view>
			</view>
			<view class="qa"
				v-if="orderInfo.orderStatus==3||(orderInfo.orderStatus==4&&orderInfo.paidAmount<orderInfo.salePrice)">
				我已支付，<text style="color: cornflowerblue;" @click="checkStatus">点击刷新</text>
			</view>

		</view>
		<!-- v-if="djShow" -->
		<!-- <view class="dj-qk" v-if="djShow">
			<radio-group @change="djChange" class="flex">
				<label class="flex" style="align-items: center;">
					<view>
						<radio style="transform:scale(0.8)" value="1" :checked="isDj === '1'" />
					</view>
					<view class="dj">定金支付</view>
				</label>
				<label class="flex" style="align-items: center;">
					<view>
						<radio style="transform:scale(0.8)" value="3" :checked="isDj === '3'" />
					</view>
					<view class="dj">全款支付</view>
				</label>

			</radio-group>
		</view> -->
		<!-- v-if="djShow" -->
		<!-- <view class="" v-if="djShow" style="padding: 0 60rpx;">
			<view class="dj-box">
				{{isDj==1?`定金：¥${djPrice}`:`全款：¥${orderInfo.salePrice}`}}
			</view>
		</view> -->
		<view class="btn-box flex">
			<view class="quxiao">
				<u-button @click="cancelOrder" class=" custom-style" text="取消订单" plain shape="circle">取消订单</u-button>
			</view>
			<!-- <view class="quxiao">
				<u-button @click="openof" class="custom-style" text="联系客服" plain shape="circle"></u-button>
			</view> -->
			<view class="quxiao">
				<u-button @click="payNow" class="custom-style pay-btn" text="立即支付" shape="circle"></u-button>
			</view>
			<!-- <view class="quxiao" v-if="orderInfo.orderStatus==4&&orderInfo.paidAmount<orderInfo.salePrice">
				<u-button @click="payNow('wk')" class="custom-style pay-btn" text="支付尾款" shape="circle"></u-button>
			</view> -->
		</view>
		<!-- 弹出窗 -->
		<!-- <u-modal :show="show" :closeOnClickOverlay="true" @close="show = false;content = ''" :title="title"
			@confirm="show = false;content = ''" confirm-text="关闭">
			<view class="slot-content">
				<rich-text :nodes="content"></rich-text>
			</view>
		</u-modal> -->
		<!-- 立即购买弹出 -->
		<u-popup :closeable="false" mode='center' :round="10" :show="show">
			<view class="boxese">
				<view class="title">
					{{title}}
				</view>

				<view class="desc_info">
					<rich-text :nodes="content"></rich-text>
				</view>
				<view class="popbtns">
					<u-button style="background: linear-gradient(90deg, #FF6A35 0%, #FFB42A 100%);color: #fff;" type=""
						shape="circle" @click="show = false">确定</u-button>
				</view>
			</view>
		</u-popup>
		<!-- 选择支付方式弹出 -->
		<u-popup :show="showPayWay" :closeOnClickOverlay="false" :closeable="true" mode="bottom" @close="closePayWay">
			<view class="pay-way-box">
				<payWayList :SimpleMessage="SimpleMessage" @paySuccess="closePayWay" :orderId="payRecordId"
					:amountType="isDj"></payWayList>
			</view>
		</u-popup>
	</view>
</template>

<script>
	// import orderApi from "../../api/order/index.js"
	// import homeApi from "../../api/home/index.js"
	// import payWayList from "@/components/payWayList.vue"
	// import messageApi from "@/api/message/message.js"
	export default {
		components: {
			// payWayList
		},
		data() {
			return {
				djShow: false,
				isDj: '3',
				djPrice: 0,
				remainderTime: '', //订单支付剩余时间
				showPayWay: false,
				content: '',
				title: '',
				show: false,
				isCheckXieyi: false,
				goodsInfo: {},
				orderInfo: {},
				statusMap: {
					1: '交易成功',
					2: '已取消',
					3: '待支付',
					4: '交易中',
					5: '已退款',
				},
				checkIndex: 1,
				serviceList: [{
					name: '找回包赔服务',
					index: 1,
					key: 'ddzhbp'
				}, {
					name: '人脸包赔服务',
					index: 2,
					key: 'ddrlbp'
				}, {
					name: '不买包赔',
					index: 3,
					key: 'ddbmbp'
				}, ],
				orderId: '',
				payRecordId: '',
				intervalTimer: null,
				fuwufei: '',
				fuwufeiDes: '',
				SimpleMessage: {},
				flag: null,
				zfflag: null
			};
		},
		onLoad(option) {
			const orderInfo = uni.getStorageSync('tempOrderGoods')
			this.orderInfo = orderInfo
			// this.orderId = option.id
			// this.getDetail()
			// this.getServiceMoney()
			// if (option.flag == 'ljzf') {
			// 	this.zfflag = option.flag
			// 	// 111
			// }
		},
		onUnload() {
			//console.log('离开订单详情页')
			clearInterval(this.intervalTimer)
			//console.log('清除定时器')
		},
		onBackPress(e) {
			uni.reLaunch({
				url: '/pages/mine/index'
			})
			uni.navigateTo({
				url: `/pages/order/orderList?orderStatus=3`
			})
			//返回值为 true 时，才表示不执行默认的返回，自行处理此时的业务逻辑
			return true
		},
		methods: {
			baozhang() {
				uni.navigateTo({
					url: '/pages/home/SafetyGuarantee'
				})
			},
			goLink() {
				uni.navigateTo({
					url: `/pages/detail/index?id=${this.orderInfo.goodsId}`
				})
			},
			// 获取服务费金额
			getServiceMoney() {
				messageApi.getDict('serviceAmount').then(res => {
					if (res && res.data.length) {
						res.data.forEach(r => {
							if (r.dictLabel == 'serviceAmount') {
								this.fuwufei = r.dictValue
								this.fuwufeiDes = r.remark
							}
						})
					}
					this.computedTotal()
				})
			},
			// 定金选择改变
			djChange(e) {
				//console.log('e', e)
				this.isDj = e.target.value
				// this.computedTotal()
			},
			// 支付完成后查询状态
			checkStatus() {
				orderApi.queryOrder({
					id: this.orderId,
					force: true
				}).then(res => {
					this.orderInfo.orderStatus = res.data.oderstatus
					if (res.data.oderstatus == '4') {
						this.getDetail()
						clearInterval(this.intervalTimer)
					}
				})
			},
			// 立即支付
			payNow(flag) {
				if (flag == 'wk') {
					// 支付尾款
					this.isDj = '2'
				}
				uni.navigateTo({
					url: '/pages/order/checkstand'
				})
				let obj = {}
				obj.SimpleMessage = this.SimpleMessage
				obj.orderId = this.payRecordId
				obj.amountType = this.isDj
				// this.showPayWay = true
				uni.setStorageSync('zfdata', obj)
				// 打开选择支付方式弹窗
				// this.showPayWay = true  111
			},
			// 取消选择支付方式
			closePayWay() {
				//console.log('关闭选择支付方式弹出')
				// this.showPayWay = false  111
				this.checkStatus()
			},
			// 取消订单
			cancelOrder(item, i) {
				let that = this
				uni.showModal({
					title: "确认取消该订单吗？",
					confirmText: "确认",
					success: function(res) {
						if (res.confirm) {
							let obj = {
								id: that.orderId,
								remake: '用户取消订单'
							}
							orderApi.cancelOrder(obj).then(result => {
								if (result.code == 200) {
									uni.showToast({
										title: '订单已取消',
										icon: 'none'
									})
									that.getDetail()
								} else {
									uni.showToast({
										title: result.msg,
										icon: 'none'
									})
								}
							})
						}
					},
					fail: function(err) {
						// //console.log('删除列表', err);
					},
				});
			},
			// 计算定金
			djCompute() {
				if (this.orderInfo.goodsAmount < 200) {
					this.djPrice = 0
					this.isDj = '3'
					this.djShow = false
				} else {
					let num = Math.round(this.orderInfo.goodsAmount * 0.1)
					this.djPrice = num > 50 ? num : 50
					this.isDj = '3'
					this.djShow = true
				}
				// // 测试改为定金0.01
				// this.djShow = true
				// this.djPrice = 0.01

			},

			getDetail() {
				orderApi.getOrderDetail(this.orderId).then(res => {
					if (res.code == 200) {
						this.orderInfo = res.data
						this.SimpleMessage.closeTime = res.data.closeTime
						this.SimpleMessage.orderStatus = res.data.orderStatus
						this.SimpleMessage.salePrice = res.data.salePrice
						this.SimpleMessage.accountid = res.data.goodsId
						//console.log('查找了订单详情', this.orderInfo)
						this.payRecordId = this.orderInfo.orderId
						if (this.orderInfo.orderStatus == 3) {
							if (this.zfflag == 'ljzf') {
								uni.navigateTo({
									url: '/pages/order/checkstand'
								})
								let obj = {}
								obj.SimpleMessage = this.SimpleMessage
								obj.orderId = this.payRecordId
								obj.amountType = this.isDj
								// this.showPayWay = true
								uni.setStorageSync('zfdata', obj)
							}
							// 计算定金
							this.djCompute()
							// 显示待支付时间
							this.showtime()
							clearInterval(this.intervalTimer)
							this.intervalTimer = setInterval(() => {
								this.showtime()
							}, 1000)
						} else {
							this.djShow = false
						}
					}

				})
			},
			// 订单支付剩余时间
			showtime() {
				let that = this
				let closeTime = this.orderInfo.closeTime
				var nowtime = new Date(), //获取当前时间
					endtime = new Date(closeTime.replace(/\-/g, "/")); //定义结束时间
				var lefttime = Number(endtime.getTime()) - Number(nowtime.getTime()), //距离结束时间的毫秒数
					leftd = Math.floor(lefttime / (1000 * 60 * 60 * 24)), //计算天数
					lefth = Math.floor((lefttime / (1000 * 60 * 60) % 24) + leftd * 24) < 10 ? "0" + Math.floor((lefttime /
						(1000 * 60 * 60) % 24) + leftd * 24) : Math.floor((lefttime / (1000 * 60 * 60) % 24) + leftd *
						24), //计算小时数
					leftm = Math.floor(lefttime / (1000 * 60) % 60) < 10 ? "0" + Math.floor(lefttime / (1000 * 60) % 60) :
					Math.floor(lefttime / (1000 * 60) % 60), //计算分钟数
					lefts = Math.floor(lefttime / 1000 % 60) < 10 ? "0" + Math.floor(lefttime / 1000 % 60) : Math.floor(
						lefttime / 1000 % 60); //计算秒数

				this.remainderTime = leftm + ':' + lefts
				// 倒计时结束时，显示00:00:00
				if (lefttime < 0) {
					clearInterval(this.intervalTimer)
					this.remainderTime = "00:00"
					let obj = {
						id: that.orderId,
						remake: '超时取消订单'
					}
					this.orderInfo.orderStatus = 2
					// 超时关闭订单
					// orderApi.cancelOrder(obj).then(result => {
					// 	that.getDetail()
					// 	if (result.code == 200) {
					// 		uni.showToast({
					// 			title: '订单已取消',
					// 			icon: 'none'
					// 		})
					// 	}
					// })
				}
			},

			fuzhi(value) {
				//console.log(value);
				// #ifdef APP-PLUS
				uni.setClipboardData({
					data: value,
					success: function() {
						uni.showToast({
							title: "复制成功",
							icon: 'none',
							duration: 1500,

						})
					}
				});
				// #endif
				this.$copyText(value).then(function(e) {
					uni.showToast({
						title: "复制成功",
						icon: 'none',
						duration: 1500,
					})
				}, function(e) {
					//console.log(e)
				})
			},
			// 点击小问号
			showModel(item) {
				if (item == 'ptfff') {
					// 平台服务费
					this.content = this.fuwufeiDes
					this.title = '平台服务费'
					this.show = true
					return
				}
				homeApi.gonggaoDetails({
					noticeType: item
				}).then(res => {
					if (res && res[0]) {
						this.content = res[0].noticeContent
						this.title = res[0].noticeTitle
						this.show = true
					}
				})
				this.title
			},
			async openof() {
				let type = 2
				if (this.orderInfo.orderStatus == 4 || this.orderInfo.orderStatus == 1) {
					type = 4
				}
				const res = await homeApi.lianxikefus({
					type: type
				})
				// //console.log('66666666666', res);
				this.obj = res[0]
				// this.shows = true
				this.openMsg(res[0])
			},
			// 联系客服  打开聊天窗
			async openMsg(e) {
				var my = uni.getStorageSync("myUsername");
				var nameList = {
					myName: my,
					your: e.hxname,
					yourNickName: e.name
				};
				// //console.log('nameList', nameList)
				uni.navigateTo({
					url: "/pages/chatroom/chatroom?username=" + JSON.stringify(nameList),
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.page-box {
		min-height: 100vh;
		background-size: 100% 700rpx;
		background-color: #F5F5F5;
		font-family: STHeitiSC, STHeitiSC;

		view {
			font-size: 26rpx;
		}
	}

	.order-box {
		padding: 0 30rpx;
		padding-top: 0.1px;
		// background-size: 100% 700rpx;
		// background-color: #fff;
		// background-repeat: no-repeat;
		// background: linear-gradient(to bottom, #f3bd72, #fcf9f5, rgb(255, 255, 255));

		.tit-box {
			margin-top: 30rpx;

			view {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 40rpx;
				color: #333333;
				line-height: 42rpx;
			}

			.status-txt {
				font-size: 34rpx;
				font-weight: 601;
			}

			.status-txt-tip {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				line-height: 42rpx;
				margin-top: 8rpx;
			}

		}

		.baozhang_box {
			width: 690rpx;
			height: 222rpx;
			background: #FFFFFF;
			border-radius: 20rpx;
			margin-top: 32rpx;
			padding: 16rpx;

			.title {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				line-height: 42rpx;
				text-align: left;
				font-style: normal;
			}

			.center_box {
				width: 658rpx;
				height: 132rpx;
				background: #F7F7F7;
				border-radius: 20rpx;
				margin-top: 16rpx;
				padding: 22rpx 16rpx;
				display: flex;
				align-items: center;
				justify-content: space-between;

				.left_box {
					.title_account {
						font-family: STHeitiSC, STHeitiSC;
						font-weight: 500;
						font-size: 28rpx;
						color: #666666;
						line-height: 42rpx;
					}

					// .title_account{
					// 	font-family: STHeitiSC, STHeitiSC;
					// 	font-weight: 500;
					// 	font-size: 28rpx;
					// 	color: #666666;
					// 	line-height: 42rpx;
					// }
				}

				.dsx_box {
					display: flex;

					// align-items: center;
					.dsx_title {
						font-family: STHeitiSC, STHeitiSC;
						font-weight: 500;
						font-size: 32rpx;
						color: #FF2C00;
						line-height: 42rpx;
						text-align: left;
						font-style: normal;
					}

					.dsx_icon {
						margin-top: 5rpx;
						margin-left: 8rpx;
					}
				}
			}
		}

		.goods-box {
			background-color: #fff;
			border-radius: 20rpx;
			padding: 20rpx;
			padding-bottom: 40rpx;
			margin-top: 20rpx;

			.code {
				color: #999;
				line-height: 40rpx;
				padding-bottom: 20rpx;
				border-bottom: 1px solid #f3f3f3;
				margin-bottom: 20rpx;
				font-family: STHeitiSC, STHeitiSC;
				font-size: 26rpx;
			}

			.img-box {
				width: 156rpx;
				height: 156rpx;
				margin-right: 10rpx;

				image {
					width: 100%;
					height: 100%;
					border-radius: 10rpx;
				}
			}

			.goods-cont {
				width: 380rpx;
			}

			.goods-des {
				// color: #999;
				overflow: hidden;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 2;
				// width: 380rpx;
				// min-height: 102rpx;
				// font-size: 26rpx;
				// width: 366rpx;
				height: 84rpx;
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				line-height: 42rpx;
				text-align: left;
				font-style: normal;
			}

			.qufu {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 24rpx;
				color: #999999;
				line-height: 42rpx;
				text-align: left;
				font-style: normal;
			}

			.times {


				view {
					color: #999;
				}

				line-height: 46rpx;

				.icon {

					margin-right: 10rpx;
				}
			}

			.bapei {


				.baopeiimg {
					width: 120rpx;
					margin-right: 16rpx;
				}
			}


			.price {

				color: #f2243c;

				.price-num {
					font-weight: bold;
					font-size: 28rpx;
					margin-left: 10rpx;
				}

			}

			.bot-box {
				align-items: center;
				margin-top: 10rpx;
			}

			.goods-qufu {
				view {
					color: #999;
				}

				margin-top: 20rpx;
			}

		}

		.rigth_box_New {
			text-align: center;

			.price_box {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				line-height: 42rpx;
				font-style: normal;
			}

			.shop_num {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 24rpx;
				color: #999999;
				line-height: 42rpx;
				font-style: normal;
			}
		}


		.title {
			align-items: center;
		}

		.tit-txt {
			font-size: 30rpx;
			font-weight: 600;
		}

		.tit-icon {
			width: 11rpx;
			height: 30rpx;
			background-color: #FF0000;
			margin-right: 10rpx;
			font-family: STHeitiSC, STHeitiSC;
		}

		.price-list {
			margin-top: 18rpx;

			.price-line {
				view {
					font-family: STHeitiSC, STHeitiSC;
					font-weight: 500;
					font-size: 28rpx;
					color: #333333;
					line-height: 50rpx;
					text-align: left;
					font-style: normal;
				}
			}
		}

		.order-total {
			text-align: right;
			margin-top: 20rpx;
			padding-top: 18rpx;
			border-top: 1px solid #EDEDED;

			.price-txt {
				font-size: 38rpx;
				color: red;
			}
		}

		.lianxi_kefu_new {
			margin: 16rpx 0;
			width: 690rpx;
			height: 98rpx;
			background: #FFFFFF;
			border-radius: 20rpx;
			display: flex;
			align-items: center;
			justify-content: center;

			.kefu_lianxi {
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 32rpx;
				color: #333333;
				line-height: 42rpx;
				font-style: normal;
				margin-left: 12rpx;
			}
		}


		.order-detail {
			background-color: #fff;
			padding: 16rpx 30rpx;
			margin-top: 18rpx;
			border-radius: 20rpx;

			.time-list {
				padding-top: 22rpx;

				.fuzhi {
					width: 82rpx;
					height: 42rpx;
					border-radius: 8rpx;
					border: 2rpx solid #D2D2D2;
					line-height: 39rpx;
					text-align: center;
					font-family: STHeitiSC, STHeitiSC;
					font-weight: 500;
					font-size: 26rpx;
					color: #666666;
					margin-left: 26rpx;
				}
			}

			.price-box {
				margin-bottom: 12rpx;
				align-items: center;
			}

			.price-name {
				font-size: 26rpx;
				font-family: STHeitiSC, STHeitiSC;
				font-weight: 500;
				font-size: 28rpx;
				color: #333333;
				// margin-left: 18rpx;
				// font-weight: 601;
			}

			.price {
				font-size: 28rpx;
				margin-left: 33rpx;
			}
		}
	}

	.btn-box {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		padding: 33rpx 30rpx;
		// margin-top: 40rpx;
		justify-content: flex-end;
		width: 750rpx;
		height: 144rpx;
		background: #FFFFFF;

		.custom-style {
			width: 200rpx;
			font-size: 30rpx;
			border-color: #999999;
			margin-left: 20rpx;
			height: 80rpx;

			&.pay-btn {
				// background: linear-gradient(90deg, #FF6A35 0%, #FFB42A 100%);
				background-color: #383b56;
				color: #fff;
				border-color: #fff;
			}
		}

		/deep/ .u-button__text {
			font-size: 30rpx !important;
		}
	}

	.qa {
		padding: 0 50rpx;
		text-align: right;
		margin-bottom: 100px;
		margin-top: 30rpx;
	}

	.dj-qk {
		padding: 0 50rpx;
		margin-bottom: 30rpx;

		.dj {
			font-size: 28rpx;
		}
	}

	view.dj-box {
		font-size: 30rpx;
		font-weight: 601;
	}

	.boxese {
		width: 600rpx;
		padding: 30rpx;
		text-align: center;
		background-color: #fff;
		border-radius: 15px;

		.title {
			font-family: STHeitiSC, STHeitiSC;
			font-size: 32rpx;
			font-weight: 700;
		}

		.des {
			font-size: 30rpx;
			color: #666;
			line-height: 60rpx;
			margin-top: 30rpx;
		}

		.desc_info {
			line-height: 45rpx;
			margin-top: 16rpx;
		}

		.timers {
			margin: 30rpx auto;
			padding-left: 160rpx;
		}

		.popbtns {
			display: flex;
			justify-content: space-between;
			margin-top: 36rpx;

			button {
				height: 70rpx;
				width: 100%;
			}
		}
	}

	/deep/ .uni-radio-input-checked {
		background-color: #f2243c !important;
		border-color: #f2243c !important;
	}
</style>