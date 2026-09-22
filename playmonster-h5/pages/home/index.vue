<template>
	<view class="mian">

		<!-- 筛选栏 -->
		<!--  -->
		<view class="flex fbt logo-box pad30">
			<view class="logo">
			</view>
			<view class="search-box flex grep">
				<view class="zhezhao" @click="goTabs('/pages/gameList/index')">

				</view>
				<u-input disabled confirmType="search" style="background-color: #fff;" placeholder="搜索关键字或商品编号"
					border="none">
					<template slot="suffix">
						<view class="icon">
							<image class="icon" src="/static/guaishouwan/search.png" mode=""></image>
						</view>
					</template>
				</u-input>
			</view>
			<view class="kefu-logo" @click="gomessage('/pages/message/index')">
				<!-- into_singleChatRoom -->
			</view>
		</view>

		<view class="top-content">
			<!-- 轮播图 -->
			<view class="lunbo-box pad30">
				<swiper style="height: 248rpx;" class="swiper" circular :autoplay="autoplay" :interval="interval"
					:duration="duration">
					<swiper-item v-for="item in lunbo" :key="item.id" @click="lunboclick(item)">
						<image style="width: 100%;height: 248rpx;border-radius: 20rpx;" :src="item.h5Img" mode="">
						</image>
					</swiper-item>
				</swiper>
			</view>
			<!-- 顶部菜单 -->
			<view class="top-menus flex fbt">
				<view class="item-menu" v-for="(item,i) in menus" @click="goLink(item.link,i)">
					<image :src="item.icon" mode="" class="icon"></image>
					<view class="menu-name">
						{{item.name}}
					</view>
				</view>
			</view>


		</view>
		<view class=""
			style="background-color: #383B56;border-radius: 30rpx 30rpx 0 0;margin-top: -36rpx;border-top: 2px solid #888690;padding-top: 20rpx;">
			<!-- 公告 -->
			<view class="flex gonggao-cont" @click="goGonggao">
				<u-notice-bar :text="gonggao" fontSize="13" speed="50" bgColor="transparent" color="#fff" icon="近期成交">
				</u-notice-bar>
			</view>

			<view class="dibu-bottom">
				<!-- 全部游戏 -->
				<view class="all-game">
					<view class="game-box" ref="gamebox" v-if="gameList&&gameList.length">
						<view class="flex fbt modelTop" style="width: 100%;">
							<view class="title flex" style="align-items: center;">
								<image style="width: 32rpx;" src="../../static/guaishouwan/youxileixing.png"
									mode="widthFix"></image>
								<text class="titleicon" style="color: #fff;padding-left: 10rpx;">游戏类型</text>
							</view>
							<view class="view-more flex" style="padding-top: 8rpx;" @click="goGameList">
								<text style="color: #FFA297;">更多游戏</text>
								<image class="icon" src="/static/guaishouwan/right.png" mode="widthFix"></image>
							</view>
						</view>
						<view class=" gameicon-box flex" style="margin-top: 10rpx;">
							<view v-for="(item, index) in gameList" :key="index" class="game-item"
								@click="goGoodsList(item)">
								<image class="img" :src="item.icon" mode="widthFix"></image>

								<!-- <view class="text">
									{{item.name}}
								</view> -->
							</view>
						</view>
					</view>
				</view>
				<!-- 图片按钮 -->
				<view class="img-btns flex fbt">
					<view class="left" @click="goLink('/pages/homedetails/yewujieshao')"></view>
					<view class="right flex">
						<!-- /pages/fankui/index -->
						<view class="rg-top" @click="goLink('/pages/fankui/index')"></view>
						<view class="rg-bot" @click="goLink('/pages/goodsList/zhaohuibaopeiindex')"></view>
					</view>

				</view>




				<!-- 合作主播 -->
				<view class="zhubo-box all-game">
					<view class="game-box" v-if="zhubo&&zhubo.length">
						<view class="flex fbt modelTop" style="width: 100%;margin-top: 30rpx;">
							<view class="title flex" style="align-items: center;">
								<image style="width: 26rpx;height: 33rpx;" src="../../static/guaishouwan/zhubo.png"
									mode=""></image>
								<text class="titleicon" style="color: #fff;padding-left: 10rpx;">合作主播</text>
							</view>
							<view class="view-more flex" style="padding-top: 8rpx;" @click="gotozhubo">
								<text style="color: #FFA297;">更多主播</text>
								<u-icon name="arrow-right" color="#FFA297" size="24rpx"></u-icon>
							</view>
						</view>

						<view class="zhubo-scroll">
							<view class="flex img-box">
								<view class="zhubo-item" @click="gotozhubo" v-for="(item,index) in zhubo" :key="index">
									<image class="img" :src="item.img" mode=""></image>
								</view>
							</view>
						</view>
					</view>
				</view>

				<view class="flex fbt modelTop" style="width: 100%;padding: 0 30rpx;margin-top: 30rpx;">
					<view class="title flex" style="align-items: center;">
						<image style="width: 190rpx;height: 50rpx;" src="../../static/guaishouwan/miaosha.png" mode="">
						</image>
						<!-- <text class="titleicon" style="color: #fff;padding-left: 10rpx;">合作主播</text> -->
					</view>
					<view class="view-more flex" style="padding-top: 6rpx;" @click="goGameList">
						<text style="color: #FFA297;">更多秒杀</text>
						<u-icon name="arrow-right" color="#FFA297" size="24rpx"></u-icon>
					</view>
				</view>

				<view class="all-game">
					<view class="game-scrolls">
						<scroll-view :scroll-x="true" :show-scrollbar="true" :scroll-left="setScroll">
							<view class="game-scroll-cont flex">
								<view v-for="(item, index) in allGameList" :key="index" class="game-items"
									:class="{active:item.id==searchParams.gameId}" @click="gameChange(item)">
									<text class="text">
										{{item.name}}
									</text>
								</view>
							</view>
						</scroll-view>
					</view>
				</view>


				<!-- 商品列表 -->
				<view class="addall">
					<view class="goods_detail" v-for="item in list" @click="goDetail(item)">
						<view class="img-box">
							<image class="imgss" :src="item.titleImg" mode="widthFix"></image>
						</view>
						<view class="texts">
							{{item.des}}
						</view>
						<view class="qufu-eye flex fbt">
							<view class="qufu" v-if="item.gameType == 1">
								{{item.systemName}}{{item.carrierName}}{{item.groupName}}{{item.serverName}}-{{item.gameName}}
							</view>
							<view class="qufu" v-if="item.gameType == 0">
								{{item.systemName}}{{item.carrierName}}-{{item.gameName}}
							</view>
							<view class="eyes">
								<u-icon name="eye" size="12px" color="#dfdfdf" labelColor="#dfdfdf"
									:label="item.lookCount" labelSize="10px"></u-icon>
							</view>
						</view>
						<view class="price-sign flex fbt">
							<view class="bapei">
								<image class="baopeiimg" src="/static/taohaoqu/zhichibaopei.png" mode="widthFix">
								</image>
								<image class="baopeiimg" src="/static/taohaoqu/pingtaifahuo.png" mode="widthFix">
								</image>
							</view>
						</view>
						<view class="goods_footer">
							<view class="goods_label_icon" v-if="item.accountType == 1">
								<img src="/static/taohaoqu/label/label3.png" alt="" />
							</view>
							<view class="prices red">
								¥<text class="pricets">{{item.newPrice }}</text>
							</view>
						</view>

					</view>
				</view>
			</view>
			<u-loadmore :status="loadMore" />
		</view>

		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>
		<view class="kefu-btn" @click="gomessage('/pages/message/index')">

		</view>

		<!-- #ifdef H5 -->
		<!-- <view class="dowmload-btn" v-if="showDown" @click="goDownLoad">
			<view class="close-icon" @click.stop="showDown = false">
				<image style="width: 50rpx;height: 50rpx;background-color: #85898d;border-radius: 50%;"
					src="../../static/taohaoqu/chahaoahh.png" mode=""></image>
			</view>
		</view> -->
		<!-- #endif -->
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	import userApi from "../../api/users/index.js"
	export default {
		components: {},
		data() {
			return {
				qidongtuxianshi: getApp().globalData.qidongtuxianshi,
				// background: ['color1', 'color2', 'color3'],
				indicatorDots: true,
				autoplay: true,
				interval: 2000,
				duration: 500,
				showDown: true,

				show: true, //提示弹出
				loadMore: 'loadmore', //loading nomore
				id: null, //分销商id
				menus: [{
						name: '我要买号',
						icon: '/static/guaishouwan/icon-buy.png',
						id: 1,
						link: '/pages/gameList/index'
					},
					{
						name: '快速回收',
						icon: '/static/guaishouwan/kshs.png',
						id: 2,
						link: '/pages/saleGameList/maidetails'
					},
					{
						name: '客服验证',
						icon: '/static/guaishouwan/kfyz.png',
						id: 3,
						link: '/pages/homedetails/kefuyanzheng'
					},
					{
						name: '黑号查询',
						icon: '/static/guaishouwan/hhcx.png',
						id: 4,
						link: '/pages/homedetails/kefuyanzheng'
					},
				],
				content: '',
				list: [],
				gameList: [],
				allGameList: [],
				lunbo: [],
				zhubo: [],
				gonggao: '【怪兽玩代售】为保护未成年身心健康，将严格遵守国家《关于进一步严格管理 切实防止未成年人沉迷网络游戏的通知》',
				list1: [{
					name: '精品专区',
				}, {
					name: '特价专区',
				}],
				activeTab: 0,
				searchParams: {
					gameId: '',
					pageSize: 10,
					pageNum: 1,
					sortRank: '',
					keyWord: '',
					accountType: 1,
					// isTop: 1
				},
				setScroll: 0,
				isRt: '',
				kefuList: [],
				form: {
					pageSize: 5,
					pageNum: 1
				},
				count: 6
			}
		},
		onShow() {
			this.getPhoneKG()
		},
		onLoad() {
			// // #ifdef H5
			// //console.log('现在是h5端');
			// this.qidongtuxianshi = getApp().globalData.qidongtuxianshi
			// // 需条件编译的代码
			// //console.log('getApp().globalData', getApp().globalData.qidongtuxianshi)
			// setTimeout(() => {
			// 	//console.log('隐藏图片')
			// 	getApp().globalData.qidongtuxianshi = false
			// 	this.qidongtuxianshi = false
			// }, 2000);
			// // 尝试唤起怪兽玩代售APP
			// // let platform = uni.getSystemInfoSync().platform;
			// // if (platform == 'android') {
			// // 	setTimeout(() => {
			// // 		const iframe = document.createElement('iframe')
			// // 		iframe.style.display = 'none'
			// // 		iframe.src = 'taohaoqu://'
			// // 		document.body.appendChild(iframe)
			// // 	}, 1000)
			// // } else {
			// // 	this.showDown = false
			// // }

			// // #endif
			this.show = false
			this.getGameList()
			// this.getKefuList()
			this.getGonggao()
			this.getLunbo()
			this.getZhubo()

			// this.initIM()

		},
		onReachBottom() {
			// uni.reLaunch({
			// 	url: '/pages/home/index'
			// })
			this.nextPage()
		},
		onPullDownRefresh() {
			// if (this.isRt > -1) {
			// 	if (!this.id) {
			// 		this.content = '参数错误，请联系管理员！'
			// 		this.show = true
			// 		return
			// 	}
			// } else {
			// 	this.id = ''
			// }
			this.searchList()
			// this.getList()
			// uni.stopPullDownRefresh()
		},
		methods: {
			//获取手机屏幕宽高
			async getPhoneKG() {
				let that = this
				await uni.getSystemInfo({
					success(res) {
						console.log(res);
						console.log(res.brand) //手机牌子
						console.log(res.model) //手机型号
						console.log(res.screenWidth) //屏幕宽度
						console.log(res.screenHeight) //屏幕高度
						// that.gao = res.screenHeight - 56
						uni.setStorageSync('gao', res.screenHeight)
						// that.kuan = res.screenWidth
					}
				});
			},
			// initIM() {
			// 	// 从本地获取环信登录信息
			// 	let hxUser = uni.getStorageSync('hxLoginInfo')
			// 	if (hxUser) {
			// 		//console.log('首页获取本地环信登录信息', hxUser)
			// 		getApp().globalData.conn.open({
			// 			user: hxUser.hxusername,
			// 			accessToken: hxUser.hxtoken,
			// 		})
			// 	}
			// },

			// GoJump() {
			// 	//console.log('11111111111');
			// 	this.flag = false
			// },
			// // 搜索
			// searchList() {
			// 	this.loadMore = 'loadmore'
			// 	this.searchParams.pageNum = 1
			// 	this.list = []
			// 	this.getList()
			// },
			//去APP下载页面
			goDownLoad() {
				uni.navigateTo({
					url: './appdownload'
				})
			},
			// 单聊
			// into_singleChatRoom() {
			// 	var my = uni.getStorageSync("myUsername");
			// 	var nameList = {
			// 		myName: my,
			// 		your: 'ydadmin',
			// 	};
			// 	const friendUserInfoMap = getApp().globalData.friendUserInfoMap;
			// 	if (friendUserInfoMap.has(nameList.your) && friendUserInfoMap.get(nameList.your)?.nickname) {
			// 		nameList.yourNickName = friendUserInfoMap.get(nameList.your).nickname;
			// 	}
			// 	uni.navigateTo({
			// 		url: "../chatroom/chatroom?username=" + JSON.stringify(nameList),
			// 	});
			// },
			// 获取轮播图
			getLunbo() {

				homeApi.getImgs(2).then(res => {
					//console.log('lunbo', res)
					this.lunbo = res.rows
				})
			},
			// 获取主播
			getZhubo() {
				homeApi.getImgs(1).then(res => {
					console.log('zhubo', res)
					this.zhubo = res.rows.splice(0, 3)
				})
			},
			gotozhubo() {
				uni.navigateTo({
					url: '/pages/zhuboList/index'
				})
			},
			// 敬请期待
			qidai() {
				uni.showToast({
					title: '敬请期待...',
					icon: 'none'
				})
			},
			// 跳转文章详情
			goArt(id) {
				uni.navigateTo({
					url: `/pages/noticeList/noticeDetail?id=${id}`
				})
			},
			// 我要买
			goGameList() {
				uni.reLaunch({
					url: '/pages/gameList/index?accountType=' + 'null'
				})
			},
			gengduomiaosha() {
				uni.reLaunch({
					url: `/pages/gameList/index?accountType=${this.searchParams.accountType}`
				})
			},
			// 我要卖
			goSale() {
				uni.navigateTo({
					url: `/pages/retrieveAccount/index`
				})
			},
			// 我要卖
			goGonggao() {
				// uni.navigateTo({
				// 	url: `/pages/noticeList/index?flag=1`
				// })
				uni.navigateTo({
					url: '/pages/home/chengjiao'
				})
			},
			// 获取公告列表
			async getGonggao() {
				const res = await homeApi.getGonggaoInfo(this.form)
				if (res.rows && res.rows.length) {

					let str = res.rows[0].des

					let str2 = str.replace(/\r|\n/ig, " ")
					this.gonggao = str2
				}
			},
			// 查询游戏类型
			getGameList() {
				homeApi.getGames(this.id).then(res => {
					this.allGameList = JSON.parse(JSON.stringify(res.data))
					this.gameList = res.data.splice(0, 10)
					this.searchParams.gameId = this.gameList[0].id
					// this.activeGame = this.searchParams.gameId
					this.searchParams.pageNum = 1
					this.getList()
				})
			},
			// 查询客服列表
			getKefuList() {
				homeApi.getKefuList().then(res => {
					this.kefuList = res.data
				})
			},
			// 获取列表
			getList(flag) {
				let obj
				obj = {
					gameId: this.searchParams.gameId,
					pageSize: this.searchParams.pageSize,
					pageNum: this.searchParams.pageNum,
					sortRank: 'timedesc',
					accountType: '1'
					// isTop: 1
				}
				this.loadMore = 'loading'
				if (this.searchParams.keyWord) {
					homeApi.getList(obj).then(res => {
						uni.stopPullDownRefresh()
						if (res.rows.length >= this.searchParams.pageSize) {
							this.loadMore = 'loadmore'
							if (flag == 'reset') {
								this.list = []
							}
							this.list = this.list.concat(res.rows)
						} else {
							if (flag == 'reset') {
								this.list = []
							}
							this.list = this.list.concat(res.rows)
							this.loadMore = 'nomore'
						}
					})
				} else {
					homeApi.getList(obj).then(res => {
						uni.stopPullDownRefresh()
						if (res.rows.length >= this.searchParams.pageSize) {
							this.loadMore = 'loadmore'
							if (flag == 'reset') {
								this.list = []
							}
							this.list = this.list.concat(res.rows)
						} else {
							if (flag == 'reset') {
								this.list = []
							}
							this.list = this.list.concat(res.rows)
							this.loadMore = 'nomore'
						}
					})
				}
			},
			// 搜索
			searchList() {
				this.loadMore = 'loadmore'
				this.searchParams.pageNum = 1
				this.list = []
				this.getList()

			},
			// 选择游戏
			gameChange(item) {
				// this.activeGame = item
				this.searchParams.gameId = item.id
				this.searchParams.pageNum = 1
				// this.searchParams.isTop = null
				this.getList('reset')

			},
			goGoodsList(item) {
				uni.navigateTo({
					url: "/pages/goodsList/index?type=" + item.id + "&gameType=" + item.type + '&gameName=' + item
						.name
				})
			},
			goTabs(url) {
				uni.switchTab({
					url: url
				})
			},
			// 点击轮播图
			lunboclick(item) {
				// console.log(item);
				// console.log('点击轮播图', this.lunbo[link])
				if (item.mobileJump !== '' && item.content !== '') {
					// #ifdef APP-PLUS
					plus.runtime.openURL(item.mobileJump, (err) => {
						//console.log('err', err)
					});
					// #endif
					// #ifndef APP-PLUS
					window.open(`${item.mobileJump}`, '_bank')
					// #endif
				} else if (item.mobileJump == '' && item.content == '') {
					return
				} else if (item.content !== '' && item.mobileJump == '') {
					uni.navigateTo({
						url: '/pages/home/lunbodetails?obj=' + JSON.stringify(item),
					});
				}
			},
			goLink(val, flag) {
				console.log(val, flag);
				if (flag == 0) {
					uni.reLaunch({
						url: val
					})
				} else {
					uni.navigateTo({
						url: val
					})
				}

			},
			gomessage(link) {
				console.log(link);
				uni.reLaunch({
					url: link
				})
			},
			// 下一页
			nextPage() {
				if (this.loadMore == 'loadmore') {
					this.searchParams.pageNum += 1
					this.getList()
				}
			},
			// 打开详情
			goDetail(item) {
				uni.navigateTo({
					url: `../detail/index?id=${item.id}&shopId=${this.id}`
				})
			},
			// // 复制
			// copy(val) {
			// 	// #ifdef APP-PLUS
			// 		uni.setClipboardData({
			// 			data: val,
			// 			success: function () {
			// 				uni.showToast({
			// 					title: "已复制到剪切板",
			// 					icon: 'none',
			// 					duration: 1500,

			// 				})
			// 			}
			// 		});
			// 		// #endif
			// 	this.$copyText(val).then(function(e) {
			// 		uni.showToast({
			// 			title: "已复制，请联系客服",
			// 			icon: 'none',
			// 			duration: 1500,
			// 		})
			// 	}, function(e) {
			// 		console.log(e)
			// 	})
			// }
		}

	}
</script>

<style lang="scss" scoped>
	.tip {
		color: #f56c6c;
	}

	.pad30 {
		padding: 0 30rpx;
	}

	.title {
		font-weight: bold;
		font-size: 32rpx;
		margin-bottom: 20rpx;
	}

	.content {
		height: 100vh;
	}

	.view-more {
		height: 38rpx;
		line-height: 34rpx;
		padding-left: 14rpx;
		padding-right: 8rpx;
		align-items: center;

		.icon {
			width: 24rpx;
			height: 24rpx;
			margin-left: 5px;
		}
	}



	.mian {
		// max-width: 960px;
		// margin: 0 auto;
		color: #fff;
		font-size: 30rpx;
		min-height: calc(100vh - 20px);
		border-top: none;
		background: #fff;
	}

	.logo-box {
		height: 120rpx;
		align-items: center;
		background-color: #1C1632;
		// border-bottom: 1px solid #1C1632;

		.logo {
			width: 88rpx;
			height: 68rpx;
			background-image: url('/static/guaishouwan/logo-txt.png');
			background-size: 100% 100%;
		}

		.kefu-logo {
			width: 80rpx;
			height: 80rpx;
			background-image: url('/static/guaishouwan/kefu.png');
			background-size: 100% 100%;
		}
	}




	.search-box {
		background: #FFFFFF;
		border-radius: 50rpx;
		border: 2px solid #fff;
		height: 72rpx;
		width: 500rpx;
		box-sizing: border-box;
		padding-left: 30rpx;
		padding-right: 30rpx;
		background-color: #fff;
		position: relative;
		margin: 0 auto;

		.zhezhao {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			z-index: 99;
		}

		.icon {
			width: 36rpx;
			height: 36rpx;
		}

		/deep/ .u-icon__icon .uicon-search {
			background-size: 36rpx 36rpx;
			width: 36rpx;
			height: 36rpx;
		}

		/deep/ .u-input .u-input--square {
			padding-left: 30rpx;
		}
	}

	.top-content {
		background: linear-gradient(to bottom, #1C1632, #1C1632, #45394C);
		padding-bottom: 33rpx;
	}

	.lunbo-box {
		height: 248rpx;
		border-radius: 20rpx;
		overflow: hidden;
		// margin-top: 20rpx;
	}

	.top-menus {
		width: 750rpx;
		height: 184rpx;
		// background: #FFFFFF;
		// box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.13);
		background: linear-gradient(to bottom, #1C1632, #45394C);
		// border-radius: 20px;

		margin-top: 20rpx;
		// padding-bottom: 50rpx;
		text-align: center;
		padding: 26rpx;

		.item-menu {
			width: 160rpx;
			text-align: center;

			.menu-name {
				color: #fff;
			}
		}

		.icon {
			width: 96rpx;
			height: 78rpx;
			margin-bottom: 5px;
		}
	}

	.gonggao-cont {
		width: 688rpx;
		height: 80rpx;
		line-height: 62rpx;
		margin: 0 auto;
		// margin-top: 30rpx;
		background-image: url('/static/guaishouwan/noticebar-bg.png');
		background-size: 100% 100%;
		border-radius: 20rpx;
		padding-left: 50rpx;
		padding-right: 60rpx;
	}

	.u-notice-bar {
		padding: 14rpx;
	}


	.dibu-bottom {
		margin-top: 30rpx;
	}

	.img-btns {
		padding: 0 30rpx;
		margin-top: 10rpx;

		.left {
			width: 294rpx;
			height: 292rpx;
			background-image: url('/static/guaishouwan/img-gsjj.png');
			background-size: 100% 100%;
		}

		.right {
			flex-direction: column;
			justify-content: space-between;

			.rg-top {
				width: 376rpx;
				height: 140rpx;
				background-image: url('/static/guaishouwan/tsjy.png');
				background-size: 100% 100%;
			}

			.rg-bot {
				width: 376rpx;
				height: 140rpx;
				background-image: url('/static/guaishouwan/img-zjdb.png');
				background-size: 100% 100%;
			}
		}


	}

	.zhubo-box {
		// padding-left: 30rpx;
		margin-top: 30rpx;

		.modelTop {
			padding: 0 30rpx;
			// padding: 0 20rpx;
			// margin-bottom: 10rpx;
		}


	}

	.zhubo-scroll {
		// height: 336rpx;
		margin-top: 12rpx;

		.img-box {
			margin-left: 30rpx;
			flex-wrap: nowrap;
		}

		.zhubo-item {
			width: 269rpx;
			border-radius: 10rpx;

			.img {
				width: 218rpx;
				height: 172rpx;
				border-radius: 30rpx;
			}
		}
	}


	.all-game {
		margin-top: 18rpx;

		.modelTop {
			padding: 0 30rpx;
			position: relative;
		}

		.gameicon-box {
			// background-color: #fff;
			padding-left: 60rpx;
			overflow: hidden;

			.game-item {
				// margin-bottom: 20rpx;
				width: 130rpx;
				margin-bottom: 20rpx;

				.img {
					// margin-bottom: 10rpx;
					width: 100rpx;
					height: 100rpx;
				}
			}


		}



		.game-scrolls {
			width: 690rpx;
			margin: 0 auto;

			.game-scroll-cont {
				// margin-left: 30rpx;
				// padding:0 30rpx;
				// margin: 0 30rpx;
				flex-wrap: nowrap;

			}
		}







		.game-items {

			transition: all 0.5s;
			height: 48rpx;
			line-height: 40rpx;
			border-radius: 30rpx;
			font-size: 24rpx;
			flex-shrink: 0;
			margin-right: 10rpx;
			padding: 0 20rpx;
			margin-bottom: 18rpx;
			color: #fff;
			background-color: #383B56;
			border: 2px solid #D5D5D5;

			&.active {
				color: #fff;
				background-color: #B42C1D;
				border: 2px solid #B42C1D;
			}
		}


	}



	.addall {
		padding: 0 30rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
	}


	.goods_detail {
		width: 340rpx;
		// background-color: #fff;
		// margin: 0rpx 20rpx 20rpx 10rpx;
		// margin-left: 22rpx;
		border-radius: 20rpx;
		padding-bottom: 20rpx;
		box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.1);
		// background: #FFFFFF;
		background-color: #1C1632;
		padding: 20rpx;
		margin-bottom: 16rpx;

		.img-box {
			width: 300rpx;
			height: 246rpx;
			overflow: hidden;
			margin-bottom: 20rpx;
			text-align: center;
			border-radius: 20rpx;
		}

		.imgss {
			width: 100%;
			min-height: 246rpx;
			// overflow: hidden;
			// border-radius: 20rpx;
		}

		.texts {
			display: -webkit-box;
			-webkit-line-clamp: 2; // 指定要显示的行数
			-webkit-box-orient: vertical;
			overflow: hidden;
			text-overflow: ellipsis; // 显示省略号
			font-size: 28rpx;
			color: #fff;
			margin-top: -12rpx;
		}

		.qufu-eye {
			padding: 5rpx 0;
		}

		.qufu {
			font-size: 20rpx;
			// color: #9a9a9a;
			color: #dfdfdf;
		}

		.price-sign {
			// padding: 0 20rpx;
			padding: 2rpx 0;
		}

		.bapei {
			.baopeiimg {
				width: 110rpx;
				height: 30rpx;
				margin-right: 16rpx;
			}
		}

		.prices {
			font-weight: 500;
			text-align: right;

			.pricets {
				padding-left: 10rpx;
				font-size: 38rpx;
				color: #FF6958;

			}
		}

	}

	.dowmload-btn {
		width: 750rpx;
		height: 540rpx;
		// background-image: url('/static/taohaoqu/xiazai-bg.png');
		// background-size: 100% auto;
		// background-repeat: no-repeat;
		position: fixed;
		bottom: 118rpx;
		left: 0;
		z-index: 9999999;
		// background-color: rgba(0, 0, 0, 0.2);

		.close-icon {
			position: absolute;
			top: 60rpx;
			right: 150rpx;
		}
	}

	.kefu-btn {
		width: 126rpx;
		height: 132rpx;
		background-image: url('/static/guaishouwan/xuanfu.png');
		background-size: 126rpx auto;
		position: fixed;
		right: 5rpx;
		bottom: 282rpx;
	}
	.goods_footer {
		.goods_label_icon img {
			float: left;
			margin-top: 10rpx;
			width: 140rpx;
			height: auto;
		}
	}
	

	//#ifdef H5
	.navs {
		z-index: 99999999;
		// display: none;
		width: 100%;
		background-color: #fbefef;
		position: fixed;
		right: 0;
		bottom: 0;
		top: 0;
		left: 0;
		display: none;

		// position: relative;
		&.isShow {
			display: block;
		}
	}


	.jumpTo {
		position: fixed;
		top: 20px;
		right: 30px;
	}

	.app-status-bar {
		background-color: #F9F9F9;
	}

	::v-deep .u-icon__icon.uicon-近期成交 {
		color: #74DDFE !important;
		font-size: 16px !important;
		font-weight: bold !important;
	}

	::v-deep .u-loadmore {
		margin-bottom: 0px !important;
		padding-bottom: 66rpx !important;
	}

	// #endif
</style>