<template>
	<view class="mian" :style="{height:gao + 'px'}" id="copy">
		<view class="copy" v-if="detail">
			<view class="info">
				<!-- <view :style="{'backgroundImage' : `url(${detail.titleImg})` }" v-if="detail.titleImg" class="img-box"
					@click="preview(0,[{url:detail.titleImg}])">

				</view> -->
				<view class="img-box">
					<view class="ziying" v-if="detail.accountType == 1">
						<img src="/static/taohaoqu/label/label1.png" alt="" />
					</view>
					<view class="tejia" v-if="detail.isOnSpecialOffer == 1">
						<img src="/static/taohaoqu/label/label2.png" alt="" />
					</view>
					<image :src="detail.titleImg" mode="widthFix" style="width:100%;min-height: 548rpx;"
						@click="preview(0,[{url:detail.titleImg}])" />
				</view>
				<view class="info-des">
					<view class="box1">
						<view class="price flex fbt">
							<text>￥<text class="prices">{{detail.newPrice }}</text></text>
							<view class="names">
								{{detail.gameName}}
							</view>
						</view>
						<view class="goods_label_box">
							<view class="goods_label_icon" v-if="detail.accountType == 2">
								<img src="/static/taohaoqu/label/label4.png" alt="" />
							</view>
							<view class="goods_label_icon" v-if="detail.isGuarantee == 2">
								<img src="/static/taohaoqu/label/label7.png" alt="" />
							</view>
							<view class="goods_label_icon" v-if="detail.isGuarantee == 1">
								<img src="/static/taohaoqu/label/label6.png" alt="" />
							</view>
						</view>
						<view class="flex fbt imgas">
							<view class="">
								<!-- <image class="imgs" src="/static/taohaoqu/zhichibaopei.png" mode=""></image> -->
								<image class="imgs" src="/static/taohaoqu/zhichibaopei.png" mode="widthFix">
								</image>
								<image class="imgs" src="/static/taohaoqu/pingtaifahuo.png" mode="widthFix">
								</image>
							</view>

							<view class="eyes">
								<u-icon name="eye" :label="detail.lookCount" labelSize="14px" color="#c7c7c7"
									label-color="#c7c7c7"></u-icon>
							</view>
						</view>
						<view class="textwen">
							<!-- {{detail.des}} -->
							<u-read-more :shadowStyle="shadowStyle" style="font-size: 28rpx;background-color: #1C1632"
								textIndent="0" :toggle="true" showHeight="81">
								<rich-text style="font-size: 13px;color: #fff;" :nodes="detail.des"></rich-text>
							</u-read-more>
						</view>
						<view class="box2">
							<view class="tops">
								<view class="lefts">
									商品编号&nbsp;&nbsp;{{detail.id}}
								</view>
								<image class="topimg" src="/static/banner/downloadb.png" mode=""
									@click="copyInfos(`${detail.id}`)"></image>
								<view class="lefts" style="margin-left: 11%;">
									系统大区&nbsp;&nbsp;{{detail.systemName}}{{detail.carrierName}}
								</view>
							</view>
							<view class="tops">
								<view class="lefts">
									实名状况&nbsp;&nbsp;{{detail.shiming}}
								</view>
								<view class="lefts">
									上架时间&nbsp;&nbsp;{{detail.recoveryTime}}
								</view>
							</view>
						</view>
					</view>
					<view class="box3">
						<view class="titles">
							交易流程
						</view>
						<view class="lincheng">
							<view class="agains">
								<view class="ones">
									<image class="img-one" src="/static/taohaoqu/xiadan1.png" mode=""></image>
								</view>
								<view class="a">
									下单
								</view>
							</view>
							<view class="xiahua">
								<image class="xiahus" src="/static/feiheng/downloadxia.png" mode=""></image>
							</view>
							<view class="agains">
								<view class="ones">
									<image class="img-one" src="/static/taohaoqu/yanhao1.png" mode=""></image>
								</view>
								<view class="a">
									验号
								</view>
							</view>
							<view class="xiahua">
								<image class="xiahus" src="/static/feiheng/downloadxia.png" mode=""></image>
							</view>
							<view class="agains">
								<view class="ones">
									<image class="img-one" src="/static/taohaoqu/huanbang1.png" mode=""></image>
								</view>
								<view class="a">
									换绑
								</view>
							</view>
							<view class="xiahua">
								<image class="xiahus" src="/static/feiheng/downloadxia.png" mode=""></image>
							</view>
							<view class="agains">
								<view class="ones">
									<image class="img-one" src="/static/taohaoqu/fahuo1.png" mode=""></image>
								</view>
								<view class="a">
									发货
								</view>
							</view>
							<view class="xiahua">
								<image class="xiahus" src="/static/feiheng/downloadxia.png" mode=""></image>
							</view>
							<view class="agains">
								<view class="ones">
									<image class="img-one" src="/static/taohaoqu/wancheng.png" mode=""></image>
								</view>
								<view class="a">
									完成
								</view>
							</view>

						</view>
					</view>
					<view class="box4">
						<u-cell-group :border="false">
							<u-cell :border="false" size="mini" title="交易须知" isLink @click="buyxuzhi"></u-cell>
							<u-cell :border="false" size="mini" title="官方交易群" isLink @click="Guanfang"></u-cell>
						</u-cell-group>
					</view>
					<view class="box5">
						<view class="big" v-for="(item,i) in attrs" :key="i">
							<view class="jbxx"
								v-if="(item.fieldType=='checkbox'||item.fieldType=='boxradio')&&detail[item.aavName]">
								<view v-if="detail.gameName=='王者荣耀'">
									<text style="color:#fff;font-size: 16px;">{{item.fieldName}}</text>
									<view class="jbxx-box wangzhe"
										v-if="detail[item.aavName]&&detail[item.aavName].split(',').length">
										<view class="boxStyle" v-for="(itemId,index) in detail[item.aavName].split(',')"
											:key="index">
											<image v-if="gameDicts[itemId]&&gameDicts[itemId].img"
												:src="gameDicts[itemId].img" mode="scaleToFill"
												style="width: 188rpx;height: 240rpx;margin-top: 10rpx;">
											</image>
											<view style="color: #fff;">
												{{gameDicts[itemId]&&gameDicts[itemId].name}}
											</view>
										</view>
									</view>
								</view>
								<view class="jbxx" v-else>
									<text>{{item.fieldName}}</text>
									<view class="jbxx-box"
										v-if="detail[item.aavName]&&detail[item.aavName].split(',').length">
										<text v-for="(itemId,index) in detail[item.aavName].split(',')" :key="index">
											<text v-if="index!==0">，</text>
											{{gameDicts[itemId]&&gameDicts[itemId].name}}
										</text>
									</view>
								</view>
							</view>
							<view class="jbxx"
								v-else-if="item.fieldType=='radio'&&detail[item.aavName]&&(detail.gameName!='和平精英'||(detail.gameName=='和平精英'&&item.aavName!='f10'&&item.aavName!='f14'))">
								<text>{{item.fieldName}}</text>
								<view class="jbxx-box" v-if="detail[item.aavName]">
									{{gameDicts[detail[item.aavName]].name}}
								</view>
							</view>
							<view class="jbxx"
								v-else-if="detail[item.aavName]&&(detail.gameName!='和平精英'||(detail.gameName=='和平精英'&&item.aavName=='f17'))">
								<text>{{item.fieldName}}</text>
								<view class="jbxx-box">{{detail[item.aavName]}}</view>
							</view>
						</view>
					</view>
					<view class="content">
						<view class="imgs-box">
							<view class="imgs-box">
								<view class="video-box" v-if="detail.videoUrl">
									<video class="video" autoplay loop :src="detail.videoUrl" controls></video>
								</view>
								<view class="imgs" v-if="detail.urlArr&&detail.urlArr.length">

									<view class="detail-img-box" v-for="(img,ind) in detail.urlArr"
										@click="preview(ind,detail.urlArr)" :key="ind">
										<u--image :src="img.url" mode="widthFix" width="100%" height="auto">
										</u--image>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- <view class="copy-btn" id="copy-btn" v-if="btnActived==1">
			<u-button type="warning" shape="circle" text="复制信息" :customStyle="{fontWeight:600,fontSize:'16px'}"
				@click="copyInfo"></u-button>
		</view> -->
		<!-- 底部按钮 -->
		<!-- v-if="detail.saleState==1" -->
		<view class="fixedBtm flex fbt">
			<view class="left">
				<view class="shoucang" v-if="detail&&detail.isCollect==1">
					<image src="/static/guaishouwan/collect-active.png" mode="" style="width: 44rpx;height: 44rpx;"
						@click="shoucang"></image>
					<view style="color: #fff;" @click="shoucang">已收藏</view>
				</view>
				<view class="shoucang " v-else-if="detail">
					<image src="/static/guaishouwan/ncollect1.png" mode="" style="width: 44rpx;height: 44rpx;"
						@click="shoucang"></image>
					<view style="color: #fff;" @click="shoucang">收藏</view>
				</view>
			</view>
			<view class="right flex" v-if="detail&&detail.saleState==1">
				<!-- {{detail.saleState}} -->
				<u-button class="buybtn contactbtn" style="border: 1px solid #383B56;" plain type="warning" text="咨询客服"
					@click="contactKefuMsg" color="#1C1632"></u-button>
				<!-- contactKefu -->
				<u-button class="buybtn" type="error" text="立即购买" @click="buyNow" color="#C53142"></u-button>
			</view>
			<view class="right flex view_other" v-else>
				该商品已出售，看看其他商品吧
			</view>
		</view>
		<!-- <view class="fixedBtm noBtn" v-else>
			<text class="">该商品{{saleStateMap[detail.saleState]}}</text>
		</view> -->
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" :title="title" :asyncClose="true" :content='content'></u-modal>

		</u-popup>
		<!-- 客服弹出层 -->
		<u-popup :closeable="true" closeIconPos="top-right" :round="10" :show="shows" @close="close">
			<view class="boxese">
				<view class="title">
					微信直接扫码联系发货客服
				</view>
				<view class="">
					<image class="imgsss" :src="obj.qrCode" mode=""></image>
				</view>
				<view class="name">
					{{obj.name}}
				</view>
				<view class="timers">
					服务时间：{{obj.workTime}}
				</view>
				<!-- <view class="">
					<u-button @click="openMsg(obj)">立即联系</u-button>
				</view> -->
			</view>
		</u-popup>
	</view>

</template>
<script>
	import userApi from "../../api/users/index.js"
	import homeApi from "../../api/home/index.js"
	export default {
		components: {},
		data() {
			return {
				saleStateMap: {
					0: '待审核',
					1: '未出售',
					2: '交易中',
					3: '已出售',
					4: '已下架',
				},
				id: null,
				detail: null,
				btnActived: 1,
				content: '',
				title: '提示',
				show: false,
				shopId: null,
				userInfo: null,
				qqGroup: [],
				showPop: false,
				inputValue: '',
				gameDicts: null, //游戏字典值
				attrs: [], //游戏自定义字段
				tabactive: 1,
				userInfo: {},
				obj: {},
				shows: false,
				gao: '',
				shadowStyle: {
					backgroundImage: "none",
					paddingTop: "0",
					marginTop: "20rpx"
				}
				// time:''
			}
		},
		onLoad(option) {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
			//console.log(option)
			this.id = option.id || ''
			this.shopId = option.shopId || ''
			if (this.shopId === null || this.shopId === 'null') {
				this.shopId == ''
			}
			this.userInfo = uni.getStorageSync('userInfo')
			this.getList()
			this.getQQGroup()
		},
		onShow() {

		},
		methods: {
			// 联系客服  打开聊天窗
			async openMsg(e) {
				let userInfo = await this.getUserInfo()
				if (userInfo) {
					//console.log('userInfo', userInfo)
					if (userInfo.realStatus == 2) {
						let href
						let str

						// #ifdef H5
						// href = window.location.href
						href = 'http://taohaoqu.com/index/goodDetail?id=' + this.detail.id
						// #endif
						// #ifdef APP-PLUS
						let pages = getCurrentPages()
						href = 'http://taohaoqu.com/index/goodDetail?id=' + this.detail.id
						// #endif
						// 组装要发送的商品文本
						str =
							`titleImg@#$${this.detail.titleImg}&&&title@#$${this.detail.title?this.detail.title.substr(0,100):''}&&&des@#$${this.detail.des?this.detail.des.substr(0,100):''}&&&link@#$${href}&&&id@#$${this.detail.id}`
						if (uni.getStorageSync('goodsToKefu') == this.detail.id) {
							str = ''
							uni.setStorageSync('viewingGoods', '')
						} else {
							uni.setStorageSync('goodsToKefu', this.detail.id)
							uni.setStorageSync('viewingGoods', str)
						}
						var my = uni.getStorageSync("myUsername");
						var nameList = {
							myName: my,
							your: e.hxname,
							yourNickName: e.name
						};

						uni.navigateTo({
							url: "/pages/chatroom/chatroom?username=" + JSON.stringify(nameList),
						});
					} else {
						uni.showModal({
							title: '提示',
							content: '您还未实名，请先进行实名认证！',
							showCancel: false,
							success: function(res) {
								if (res.confirm) {
									uni.navigateTo({
										url: '/pages/user/shimingrz'
									})
								}
							}
						});
					}

				}
			},
			//弹出层
			close() {
				this.shows = false
			},
			copyInfos(value) {
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
			//交易须知跳转
			buyxuzhi() {
				uni.navigateTo({
					url: '/pages/homedetails/jiaoyixuzhi'
				})
			},
			Guanfang() {
				uni.navigateTo({
					url: '/pages/detail/demo'
				})
			},
			// 查询QQ群组
			getQQGroup() {
				homeApi.getQQGroup().then(res => {
					//console.log(res)
					this.qqGroup = res
				})
			},
			// 获取个人信息
			async getUserInfo() {
				try {
					let res = await userApi.getInfo()
					if (res.code == 200) {
						return res.data
					} else {
						return false
					}
				} catch {
					return false
				}
			},
			// 查询详情
			getList() {
				homeApi.getDetail(this.id, this.shopId).then(res => {
					this.detail = JSON.parse(JSON.stringify(res.data))
					if (this.detail.gameName == '和平精英') {
						this.detail.f13 = ''
					}

					if (this.detail.gameName == '王者荣耀') {
						this.detail.f17 = ''
					}
					this.getDict()
					this.getAttrs()
					// this.$nextTick(() => {
					// 	this.$refs.uReadMore.init();
					// })
				})
			},
			// 查询字典
			getDict() {
				homeApi.getGameDict(this.detail.gameId).then(res => {
					this.gameDicts = res.data
				})
			},
			// 查询自定义字段‘
			getAttrs() {
				homeApi.getAttrsSimp(this.detail.gameId).then(res => {
					this.attrs = res.rows
				})
			},
			shoucang() {
				userApi.getInfo(this.token).then(res => {
					if (res.code == 200) {
						if (this.detail.isCollect == 1) {
							// 已收藏 取消收藏
							this.detail.isCollect = null
							let obj = {
								accountId: this.detail.id,
								createId: this.userInfo.userId
							}
							userApi.removeCollct(obj).then(res => {
								//console.log(res)
								uni.showToast({
									title: '已取消收藏',
									icon: 'none'
								})
							})
						} else {
							// 未收藏  进行收藏
							this.detail.isCollect = 1
							let obj = {
								accountId: this.detail.id,
								createId: this.userInfo.userId
							}
							userApi.addCollect(obj).then(res => {
								//console.log('res', res)
								uni.showToast({
									title: "收藏成功",
									icon: "none"
								})
							})
						}
					}
				})
			},
			// 立即购买
			async buyNow() {
				// uni.reLaunch({
				// 	url: '/pages/message/index'
				// })

				uni.setStorageSync('tempOrderGoods', this.detail)
				// 进入确认订单页面
				uni.navigateTo({
					url: '/pages/detail/orderDetail'
				})

				// let userInfo = await this.getUserInfo()
				// if (userInfo) {
				// 	if (userInfo.realStatus == 2) {
				// 		uni.setStorageSync('tempOrderGoods', this.detail)
				// 		// 进入确认订单页面
				// 		uni.navigateTo({
				// 			url: '/pages/order/sureOrder'
				// 		})
				// 	} else {
				// 		uni.showModal({
				// 			title: '提示',
				// 			content: '您还未实名，请先进行实名认证！',
				// 			showCancel: false,
				// 			success: function(res) {
				// 				if (res.confirm) {
				// 					uni.navigateTo({
				// 						url: '/pages/user/shimingrz'
				// 					})
				// 				}
				// 			}
				// 		});
				// 	}
				// }
			},
			// 点击联系客服立即聊天
			async contactKefuMsg() {
				uni.reLaunch({
					url: '/pages/message/index'
				})

				// const res = await homeApi.lianxikefus({
				// 	type: 2
				// })
				// this.obj = res[0]
				// this.openMsg(this.obj)
			},
			// 联系客服
			async contactKefu() {
				const res = await homeApi.lianxikefus({
					type: 2
				})
				//console.log('66666666666', res);
				this.obj = res[0]
				this.shows = true
			},
			// 提交订单
			submitTel() {
				if (this.inputValue) {
					if (this.inputValue.length != 11) {
						uni.showToast({
							title: '手机号不正确',
							icon: 'none'
						})
						return
					}
					let obj = {
						tel: this.inputValue,
						accountId: this.detail.id,
						des: this.detail.des,
						price: this.detail.newPrice,
						qprice: '',
						state: 1
					}
					// 组装用户信息
					if (this.userInfo) {
						obj.userId = this.userInfo.userId
						obj.nikeName = this.userInfo.nickName
					}
					// 提交到后台
					//console.log(obj)
					homeApi.submitPrice(obj).then(res => {
						//console.log('res', res)
						if (res.code == 200) {
							// 成功
							this.showPop = false
							this.inputValue = ''
							this.title = '提示'
							this.content = '提交成功，请耐心等待客服与您联系。'
							this.show = true
						}
					})
				} else {
					uni.showToast({
						title: '请填写手机号',
						icon: 'none'
					})
				}
			},
			preview(cur, list) {
				let arr = []
				list.forEach(r => {
					arr.push(r.url)
				})
				uni.previewImage({
					current: cur,
					urls: arr
				})
			},
			copyInfo() {
				// #ifdef APP-PLUS
				uni.setClipboardData({
					data: this.detail.ZHID + '-' + this.detail.JJ,
					success: function() {
						uni.showToast({
							title: "已复制到剪切板",
							icon: 'none',
							duration: 1500,

						})
					}
				});
				// #endif
				this.$copyText(this.detail.ZHID + '-' + this.detail.JJ).then(function(e) {
					uni.showToast({
						title: "已复制到剪切板",
						icon: 'none',
						duration: 1500,
					})
				}, function(e) {
					//console.log(e)
				})

			}
		}

	}
</script>

<style lang="scss" scoped>
	.ziying {
		position: absolute;
		z-index: 99;

		img {
			width: 150rpx;
			height: auto;
		}
	}

	.tejia {
		position: absolute;
		z-index: 99;
		right: 0rpx;

		img {
			width: 150rpx;
			height: auto;
		}
	}

	.goods_label_box {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		margin-top: 10rpx;

		.goods_label_icon img {
			width: 180rpx;
			height: auto;
		}
	}

	.tip {
		color: #f3f5f9;
	}

	.mian {
		font-size: 16px;
		// min-height: calc(100vh - 20px);
		max-width: 500px;
		margin: 0 auto;
		padding-bottom: 90px;
		background-color: #383B56;
	}

	.info {
		.img-box {
			width: 100%;
			height: 548rpx;
			overflow: hidden;
			border-radius: 0;
			background-image: url('https://game.ikbh.top/logo/no_img.jpg');
			background-size: 100% 100%;
		}

		.info-des {
			padding: 0 30rpx 160rpx 30rpx;
			margin-top: 30rpx;
			background-color: #383B56;

			.box1 {
				width: 100%;
				min-height: 166rpx;
				background-color: #1C1632;
				border-radius: 20rpx;
				// margin-top: -12%;
				margin-bottom: 30rpx;
				box-sizing: border-box;
				padding: 20rpx;
				position: relative;

				.price {
					color: #f2253d;

					.prices {
						font-size: 36rpx;
						font-weight: 700;
					}
				}

				.imgas {
					margin-top: 20rpx;

					.imgs {
						width: 110rpx;
						height: 30rpx;
						margin-right: 16rpx;
						// margin-top: 20rpx;
					}
				}

				.names {
					color: #fff;
					font-size: 28rpx;
					font-weight: 700;
				}

				.eyes {
					// position: absolute;
					// top: 88rpx;
					// right: 5%;
				}

				.textwen {
					margin-top: 20rpx;
					color: #fff;
					font-size: 28rpx;
					overflow: hidden;
					text-overflow: ellipsis;
					display: -webkit-box;
					-webkit-line-clamp: 5;
					-webkit-box-orient: vertical;
				}

				.box2 {
					min-width: 650rpx;
					height: 112rpx;
					background-color: #1C1632;
					margin-top: 20rpx;
					border-radius: 15rpx;
					padding: 20rpx 20rpx;

					.tops {
						display: flex;
						justify-content: space-between;

						.lefts {
							width: 372rpx;
							height: 32rpx;
							line-height: 32rpx;
							overflow: hidden;
							color: #fff;
						}

						.topimg {
							margin-left: -15%;
							width: 32rpx;
							height: 32rpx;

						}
					}

					.tops:nth-child(2) {
						margin-top: 10rpx;
					}
				}
			}

			.box3 {
				margin-top: 20rpx;
				width: 100%;
				height: 196rpx;
				background-color: #1C1632;
				border-radius: 20rpx;
				// padding: 20rpx 30rpx;
				padding: 20rpx 50rpx;
				box-sizing: border-box;

				// padding-left: 50rpx;
				.titles {
					font-size: 30rpx;
					color: #fff;
					font-weight: 700;
					text-align: center;
				}

				.lincheng {
					margin-top: 30rpx;
					display: flex;
					justify-content: space-between;

					.agains {
						text-align: center;

						.ones {

							// text-align: center;
							.img-one {
								width: 36rpx;
								height: 38rpx;
							}
						}

						.a {
							color: #fff;
						}
					}

					.xiahua {
						margin-top: 20rpx;

						.xiahus {
							width: 36rpx;
							height: 38rpx;
						}
					}
				}
			}

			.box4 {
				margin-top: 20rpx;
				width: 100%;
				height: 168rpx;
				background-color: #1C1632;
				border-radius: 20rpx;
			}

			::v-deep .u-cell__title-text {
				color: #fff;
			}

			.box5 {
				margin-top: 20rpx;
				width: 100%;
				min-height: 146rpx;
				// background-image: url('/static/taohaoqu/taohaoqubg.png');
				// background-size: 100% 100%;
				// background-color: rgba(255, 207, 0,.3) ;
				background-color: #383B56;
				box-sizing: border-box;
				padding: 20rpx;
				text-align: center;
				margin-bottom: 30rpx;

				.big {
					margin-top: 30rpx;

					.jbxx {
						// text-align: center;
						font-size: 32rpx;
						font-weight: 700;
						color: #fff;

						.jbxx-box {
							// display: flex;
							// justify-content: start;
							flex: 4;
							margin-top: 10rpx;
							width: 100%;
							min-height: 50rpx;
							background-color: #1C1632;
							border-radius: 20rpx;
							box-sizing: border-box;
							padding: 20rpx;
							text-align: left;
							font-size: 24rpx;
							color: #ffffff;
							overflow: hidden;

							&.wangzhe {
								display: flex;
								flex-wrap: wrap;
								color: #fff;
							}
						}
					}

				}
			}

			.boxStyle {
				// float: left;
				text-align: center;
				margin: 0 10rpx;
				width: 180rpx;
				margin-right: 20rpx;

			}

			.boxStyle:nth-child(3n) {
				margin-right: 0;
			}









			.tit-price {
				flex-wrap: nowrap;

				.gameimg-tit {
					flex-wrap: nowrap;

					.gameimg {
						width: 120rpx;
						height: 120rpx;
						flex-shrink: 0;
						margin-right: 20rpx;
						border-radius: 20rpx;
						overflow: hidden;
					}

					.tit-tag {
						.tit {
							width: 400rpx;
							white-space: nowrap;
							overflow-y: hidden;
							text-overflow: ellipsis;
							font-size: 32rpx;
							font-weight: 601;
						}

						.tag-box {
							margin-top: 20rpx;
						}

						.tag-item {
							padding: 3rpx 10rpx;
							color: #71A5FF;
							border-radius: 6rpx;
							border: 1px solid #71A5FF;

						}
					}


				}

				.price {
					text-align: right;
					color: #FF882D;
					font-size: 28rpx;

					.price-num {
						font-weight: bold;
						font-size: 56rpx;
					}

				}

				.time {
					color: #9E9E9E;
				}

			}

			.chengnuo-box {
				align-items: center;
				margin-top: 20rpx;

				.chengnuo {
					view {
						color: #00CC19;
						line-height: 30rpx;
					}

				}

				.icon {
					width: 32rpx;
					height: 28rpx;
					background-image: url('/static/xyr2.0/detail/baopei.png');
					background-size: 100% 100%;
					margin-right: 4rpx;

				}

				.daqu {
					color: #9e9e9e;
				}
			}
		}

		.des {
			line-height: 20px;
			margin-top: 10px;
		}

	}

	.bot-info {
		padding: 0 30rpx;
	}

	.btns {
		height: 74rpx;
		margin: 30rpx 0;
		flex-wrap: nowrap;
		background-color: #fff;
		border-radius: 37rpx;
		padding: 4rpx;

		.btn {
			width: 116px;
			text-align: center;
			font-size: 28rpx;
			line-height: 64rpx;
			border-radius: 37rpx;

			&.actived {
				color: #fff;
				background-color: #71A5FF;
			}
		}
	}

	.main-info {
		height: 156rpx;
		background: linear-gradient(314deg, #FFF2DD 0%, #FFF9F2 100%);
		border-radius: 24rpx;
		box-sizing: border-box;
		padding: 30rpx;

		.main-info-child {
			width: 30%;
		}

		.toptxt {

			font-size: 32rpx;
			font-weight: 601;
			color: #814429;
			line-height: 44rpx;
			text-align: center;
		}

		.bottxt {
			color: #B48C6E;
			text-align: center;
			margin-top: 20rpx;
		}
	}

	.account-info {
		background-color: #fff;
		border-radius: 24rpx;
		margin: 30rpx 0;
		padding: 20rpx;
		padding-bottom: 0;
		height: 250rpx;
		overflow: hidden;

		.tit2 {
			font-size: 32rpx;
			font-weight: bold;
			height: 60rpx;
		}

		.account-info-cont {
			padding-left: 20rpx;

			view {
				font-size: 28rpx;
			}

			.info-item {
				width: 300rpx;
				flex-wrap: nowrap;
				display: flex;
				height: 60rpx;


				.tit {
					width: 120rpx;
					white-space: nowrap;
					text-overflow: ellipsis;
					overflow-y: hidden;
					flex-shrink: 0;
					color: #B3B3B3;
					font-weight: 400;
					margin-right: 20rpx;
				}

				.value-txt {
					width: 160rpx;
					white-space: nowrap;
					text-overflow: ellipsis;
					overflow-y: hidden;
					font-weight: 601;
				}

			}



		}

	}

	.tabs-box {
		margin-bottom: 30rpx;

		view {
			color: #747474;
			font-size: 32rpx;
		}

		.tab-item {
			margin-right: 60rpx;

			&.active {
				view {
					color: #568CFC;
				}

				.icon {
					width: 44rpx;
					height: 8rpx;
					margin: 0 auto;
					// background-image: url('/static/xyr2.0/detail/titbg.png');
					background-size: 100% 100%;
				}
			}
		}
	}

	.detail-img-box {
		margin-bottom: 10px;
		border-radius: 10px;
		overflow: hidden;
	}

	.info-box {
		font-size: 14px;
		line-height: 22px;

		.group-item {
			background-color: #fff;
			margin-right: 20rpx;
			padding: 10px 20rpx;
			width: 208rpx;
		}
	}

	.info-box {
		.item-box {
			flex-wrap: nowrap;
			margin-bottom: 5px;
		}

		.tit {
			color: #B3B3B3;
			font-weight: 400;
			flex-shrink: 0;
			margin-right: 10px;
		}

		.cont-box {
			width: 100%;
			flex-wrap: wrap;
		}

		.line-item {
			margin-top: 5px;
		}
	}

	.msg-box {
		// color: #ccc;
	}

	.copy-btn {
		margin-top: 10px;
	}

	.video-box {
		width: 100%;

		.video {
			width: 100%;
			border-radius: 10px;
		}
	}



	.fixedBtm {
		position: fixed;
		bottom: 0;
		left: 0;
		height: 130rpx;
		width: 750rpx;
		background-color: #1C1632;
		z-index: 99;
		padding-top: 20rpx;

		.shoucang {
			font-size: 26rpx;
			text-align: center;
			padding-top: 7px;
			background-color: transparent;
		}

		.right {
			width: 600rpx;
			padding-top: 10rpx;

			.buybtn {
				width: 224rpx;
				height: 76rpx;
				border-radius: 20rpx;
			}
		}

		.left {
			margin-top: -10rpx;
			width: 150rpx;
			flex-shrink: 0;
		}
	}

	.noBtn {
		text-align: center;
		font-size: 30rpx;
		color: #999;
		line-height: 50px;
	}

	.pop-box {
		padding: 30rpx;
		overflow-y: auto;

		.title {
			font-weight: 601;
			margin-bottom: 10px;
		}

		.area-box {
			width: 600rpx;
		}

		.btns2 {
			height: 44px;
			margin-top: 10px;
			flex-wrap: nowrap;
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

	.view_other {
		font-size: 30rpx;
		line-height: 70rpx;
		padding-left: 30rpx;
		color: #999;
	}

	::v-deep .u-read-more .u-read-more__content {
		background-color: #1C1632 !important;
	}
</style>