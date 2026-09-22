<template>
	<view class="mian">
		<u-sticky offsetTop="0" customNavHeight='0'>
			<view class="sticky-box" style="width:100%;">
				<view class="flex fbt topbar">
					<view class="search-box flex grep">
						<!--注意：由于兼容性差异，如果需要使用前后插槽，nvue下需使用u--input，非nvue下需使用u-input -->
						<!-- #ifndef APP-NVUE -->
						<u-input placeholder="多个关键字用空格隔开" border="none" v-model="searchParams.keyWord"
							@confirm="searchList" confirmType="search">
						<!-- #endif -->
							<!-- #ifdef APP-NVUE -->
							<u--input placeholder="多个关键字用空格隔开" border="none" v-model="searchParams.keyWord"
								@confirm="searchList" confirmType="search">
							<!-- #endif -->
								<template slot="prefix">
									<view class="type-box flex" @click="showGame = true">
										<text style="color: #fff;">{{currentGame.name}}</text>
										<u-icon bold name="play-right-fill" color="#fff" size="12"
											class="type-box-icon"></u-icon>
									</view>
								</template>
								<template slot="suffix">

									<view class="icon" @click="searchList"></view>
								</template>
						<!-- #ifndef APP-NVUE -->
						</u-input>
						<!-- #endif -->
						<!-- #ifdef APP-NVUE -->
						</u--input>
						<!-- #endif -->
					</view>
				</view>
				<view class="search-bar flex fbt">
					<view class="btn area-btn flex fbt" @click="showArea = true">
						<view class=""
							:style="{color:(searchParams.serverId||searchParams.groupId||searchParams.carrierId||searchParams.systemId)?'#B42C1D':'#fff'}">
							区服
						</view>
						<u-icon bold name="arrow-down"
							:color="(searchParams.serverId||searchParams.groupId||searchParams.carrierId||searchParams.systemId)?'#B42C1D':'#fff'"
							size="12" class="icon"></u-icon>
					</view>
					<view class="btn sort-btn flex fbt" @click="showSort = true">
						<view class="" :style="{color:selectSort!='排序'?'#B42C1D':'#fff'}">
							{{selectSort}}
						</view>
						<u-icon bold name="arrow-down" :color="selectSort!='排序'?'#B42C1D':'#fff'" size="12"
							class="icon"></u-icon>
					</view>
					<view class="btn select-btn flex fbt" @click="showFiled=true">
						<view class="" :style="{color:isFilter?'#B42C1D':'#fff'}">
							筛选
						</view>
						<u-icon bold name="list" :color="isFilter?'#B42C1D':'#fff'" size="12" class="icon">
						</u-icon>
					</view>
					<view class="btn imgtxt-btn flex fbt" @click="switchImgTxt">
						<view class="" :style="{color : listMode == 'img' ? '#fff' : '#B42C1D'}">
							图文
						</view>
						<view class="icon" v-if="listMode == 'img'">
							<image class="icon" src="/static/guaishouwan/tuwen.png" mode=""></image>m
						</view>
						<view class="icon" v-else>
							<image class="icon" src="/static/guaishouwan/tuwen-active.png" mode=""></image>m
						</view>
						<!-- <u-icon bold name="list" :color="'#ccc'" size="14" class="icon">
						</u-icon> -->
					</view>
				</view>
			</view>

		</u-sticky>
		<view class="content-box">
			<view class="goods-box flex">
				<view class="goods-item2" v-for="item in list" @click="goDetail(item)">
					<view class="hengban">

					</view>
					<view class="" style="width: 100%;">
						<view class="img-box" v-show="listMode=='img'">
							<view class="ziying" v-if="item.accountType == 1">
								<img src="/static/taohaoqu/label/label1.png" alt="" />
							</view>
							<view class="tejia" v-if="item.isOnSpecialOffer == 1">
								<img src="/static/taohaoqu/label/label2.png" alt="" />
							</view>

							<image :src="item.titleImg?item.titleImg:'https://game.ikbh.top/logo/no_img.jpg'"
								mode="widthFix" style="width: 100%;min-height: 410rpx;height: auto;"></image>
						</view>
						<view class="des-cont">
							<view class="des" style="width: 100%;">
								{{item.des}}
							</view>
							<!-- </view> -->
							<view class="title flex fbt">
								<view class="qufu" v-if="item.gameType == 1">
									{{item.systemName}}{{item.carrierName}}{{item.groupName}}{{item.serverName}}-{{item.gameName}}
								</view>
								<view class="qufu" v-if="item.gameType == 0">
									{{item.systemName}}{{item.carrierName}}-{{item.gameName}}
								</view>
								<view class="times flex">
									<u-icon size="26rpx" bold name="eye" color="#bdbdbd" class="icon">
									</u-icon>
									<view class="times-num" style="color: #bdbdbd;">
										{{item.lookCount?item.lookCount:0}}
									</view>
								</view>
							</view>

							<view class="bot-box flex fbt">
								<view style="height: 45rpx;">
									<image class="baopeiimg" src="/static/taohaoqu/zhichibaopei.png" mode="widthFix">
									</image>
									<image class="baopeiimg" src="/static/taohaoqu/pingtaifahuo.png" mode="widthFix">
									</image>
								</view>
								<view class="price">
									¥<text class="price-num">{{item.newPrice }}</text>
								</view>
							</view>
							<view class="goods_label_box">
								<view class="goods_label_icon" v-if="item.accountType == 2">
									<img src="/static/taohaoqu/label/label4.png" alt="" />
								</view>
								<view class="goods_label_icon" v-if="item.isGuarantee == 2">
									<img src="/static/taohaoqu/label/label7.png" alt="" />
								</view>
								<view class="goods_label_icon" v-if="item.isGuarantee == 1">
									<img src="/static/taohaoqu/label/label6.png" alt="" />
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
			<u-loadmore :status="loadMore" />
		</view>
		<u-picker :closeOnClickOverlay="true" round='10' keyName='name' :show="showGame" :columns="gameOptions"
			@confirm="selectGameFn" @close="showGame = false" @cancel="showGame = false"></u-picker>
		<!-- 区服弹出 -->
		<u-popup :show="showArea" mode="bottom" round="18" :closeOnClickOverlay="true" @close="showArea = false">
			<view class="pop-box area-pop">
				<view class="area-box" v-if="currentGame&&currentGame.type===0">
					<view class="tit">
						系统
					</view>
					<view class=" flex">
						<view class="sign-item" style="text-align: center;"
							:class="{active:selectArea.systemId==item.id}" v-for=" item in sysList"
							@click="areaClick(item,'systemId')">
							{{item.fieldName}}
						</view>
					</view>
				</view>
				<view class="area-box" v-if="currentGame&&currentGame.type===0">
					<view class="tit">
						运营商
					</view>
					<view class=" flex">
						<view class="sign-item" style="text-align: center;"
							:class="{active:selectArea.carrierId==item.id}" v-for=" item in carrierList"
							@click="areaClick(item,'carrierId')">
							{{item.fieldName}}
						</view>
					</view>
				</view>
				<!-- v-if="areaList&&areaList.length" -->
				<view class="area-box" v-if="currentGame&&currentGame.type===1">
					<view class="tit">
						大区
					</view>
					<view class=" flex">
						<view class="sign-item" :class="{active:selectArea.groupId==item.id}" v-for=" item in areaList"
							@click="areaClick(item,'groupId')">
							{{item.fieldName}}
						</view>
					</view>
				</view>
				<view class="area-box" v-if="serverList&&serverList.length">
					<view class="tit">
						服务器
					</view>
					<view class=" flex">
						<view class="sign-item" :class="{active:selectArea.serverId==item.id}"
							v-for=" item in serverList" @click="areaClick(item,'serverId')">
							{{item.fieldName}}
						</view>
					</view>
				</view>


				<view class="btns flex fbt">
					<u-button type="info" shape="circle" text="重置" @click="resetArea"></u-button>
					<u-button style="background-color: #E75D31;border: none;" type="primary" shape="circle" text="确定"
						@click="areaChange"></u-button>
				</view>
			</view>

		</u-popup>
		<!-- 排序弹出 -->
		<u-action-sheet @select="selectSortFn" :actions="sortOptions" :closeOnClickOverlay="true" :show="showSort"
			round='10' @close="showSort = false"></u-action-sheet>
		<!-- 筛选弹出 -->
		<u-popup :show="showFiled" :round="10" mode="bottom" :closeOnClickOverlay="true" @close="showFiled=false">
			<view class="pop-box filter-box">
				<view class="cont-box" v-if="searchAttrs&&searchAttrs.length">
					<view class="tit">
						<view class="" style="font-size: 28rpx;padding-bottom: 20rpx;color: #fff;">
							价格区间
						</view>
						<view class="price flex ">
							<view class="zidinbgyi">
								<u--input
									style="width: 290rpx;border: 0;background-color: #f3f5f9;border-radius: 15rpx;height: 60rpx;"
									:customStyle="inputStyle" placeholder="最低值" border="surround"
									v-model="filterData.priceStart"></u--input>
							</view>
							<view class="price-line">
								——
							</view>
							<view class="zidinbgyi">
								<u--input
									style="width: 290rpx;border: 0;background-color: #f3f5f9;border-radius: 15rpx;height: 60rpx;"
									:customStyle="inputStyle" placeholder="最高值" border="surround"
									v-model="filterData.priceEnd"></u--input>
							</view>
						</view>
					</view>
					<view class="" v-for="custom in searchAttrs"
						v-if="custom.aavName != 'f13' && custom.aavName != 'f17'">
						<view class="" v-if="custom.isEnable === '0'&&custom.isSearch == '1'">
							<view class="tit" style="position: relative;">
								{{custom.fieldName}}
								<!-- <view class="" @click="moreClick(custom)" style="position: absolute;top: 0;right: 10%;color: blue;" v-if="custom.fieldType=='checkbox'||custom.fieldType=='boxradio'">
									展开 <image style="width: 22rpx;height: 22rpx;position: absolute;top: 15%;margin-left: 10rpx;color: blue;" src="/static/feiheng/bottomto.png" mode=""></image>
								</view>
								<view class="" style="position: absolute;top: 0;right: 10%;color: blue;display: none;" v-if="custom.fieldType=='checkbox'||custom.fieldType=='boxradio'">
									收起 <image style="width: 22rpx;height: 22rpx;position: absolute;top: 15%;margin-left: 10rpx;color: blue;" src="/static/feiheng/topto.png" mode=""></image>
								</view> -->
								<view class="" v-if="custom.fieldType=='checkbox'||custom.fieldType=='boxradio'"
									style="position: absolute;top: -12rpx;right: 0%;color: blue;width: 150rpx;">
									<u-button @click="moreClick(custom)"
										style="width: 150rpx;height: 50rpx;border: 1px solid #fff;color: #fff;background-color: #344356;"
										type="info" :plain="true" text="确定">
										{{ heightList[custom.id] == 'auto' ? '收起' : '展开'}}
										<u-icon color="#fff"
											:name="heightList[custom.id] == 'auto' ? 'arrow-up': 'arrow-down'"></u-icon></u-button>
								</view>
							</view>

							<view class="" v-if="custom.fieldType=='number'">
								<view class="price flex ">
									<view class="">
										<u--input
											style="width: 290rpx;border: 0;background-color: #f3f5f9;border-radius: 15rpx;height: 60rpx;"
											:customStyle="inputStyle" placeholder="最低值" border="surround"
											v-model="filterData[custom.aavName + 'start']"></u--input>
									</view>
									<view class="price-line">
										——
									</view>
									<view class="">
										<u--input
											style="width: 290rpx;border: 0;background-color: #f3f5f9;border-radius: 15rpx;height: 60rpx;"
											:customStyle="inputStyle" placeholder="最高值" border="surround"
											v-model="filterData[custom.aavName + 'end']"></u--input>
									</view>
								</view>
							</view>
							<view class="" v-if="custom.fieldType=='input'">
								<u--input :customStyle="inputStyle" style="width: 650rpx;" placeholder="请输入"
									border="surround" v-model="filterData[custom.aavName]"></u--input>
							</view>
							<view class="" v-if="custom.fieldType=='radio'">
								<view class="area-box flex">
									<view class="sign-item" :class="{active:filterData[custom.aavName]==item.id}"
										v-for=" (item,ind) in custom.childAttributes"
										@click="radioClick(item,ind,custom)">
										<view class="sign-text">
											{{item.fieldName}}
										</view>
									</view>
								</view>
							</view>
							<!-- ||custom.fieldType=='boxradio' -->
							<view class="" v-if="custom.fieldType=='checkbox'">
								<view class="pifu">
									<view class="flex nav" :style="{ height: heightList[custom.id] || '0px' }">
										<view class="sign-item" v-for="(item,ind) in custom.childAttributes"
											:class="{active:filterData[custom.aavName+'rander']&&filterData[custom.aavName+'rander'].indexOf(item.id)>-1}"
											@click="checkboxClick(item,ind,custom)">
											<view class="sign-text">
												{{item.fieldName}}
											</view>
										</view>
									</view>
								</view>
							</view>
							<view class="" v-if="custom.fieldType=='boxradio'">
								<view class="pifu">
									<view class="flex"
										:style="{ height: heightList[custom.id] || '0px',overflow:heightList[custom.id] == 'auto' ? 'visible' : 'hidden'}">
										<view class="sign-item" v-for="(item,ind) in custom.childAttributes"
											:class="{active:item.ischeck||(filterMapTemp[item.id]&&filterMapTemp[item.id].trim())}"
											@click.stop="boxradioCheckClick(item,ind,custom)">
											<view class="sign-text">
												{{item.fieldName}}
											</view>
											<u-radio-group v-model="filterData[custom.aavName + ind]" placement="column"
												class="boxradioRadioBox" :class="{ 'show': item.id == boxradioIsShow }"
												@change="boxradioChange($event,item, custom, ind)">
												<u-radio size="12px"
													:customStyle="{marginBottom: '8px',fontSize:'12px'}"
													v-for="(item2, index) in item.childGameAttributes" :key="index"
													:label="item2.fieldName" :name="item2.id">
												</u-radio>
											</u-radio-group>
										</view>
									</view>
								</view>
							</view>

						</view>

					</view>
				</view>
				<view class="" style="height: 50px;">

				</view>
				<view class="btns flex fbt" style="width: 100%;background-color: #344356;">
					<u-button type="info" shape="circle" text="重置" @click="resetFilter"></u-button>
					<u-button style="background-color: #E75D31;border: none;" type="primary" shape="circle" text="确定"
						@click="filterChange"></u-button>
				</view>
			</view>
		</u-popup>
		<u-toast ref="uToast"></u-toast>
		<u-modal @confirm="show=false" :show="show" title="提示" :asyncClose="true" :content='content'></u-modal>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		components: {},
		data() {
			return {
				heightList: [],
				showGame: false,
				showArea: false,
				showSort: false,
				showFiled: false,
				show: true, //提示弹出
				loadMore: 'loadmore', //loading nomore
				id: null, //分销商id
				content: '',
				list: [],
				gameOptions: [],
				currentGame: null,
				searchParams: {
					gameId: '',
					pageSize: 10,
					pageNum: 1,
					sortRank: ''
				},
				searchAttrs: null,
				setScroll: 0,
				filterData: {
					// 筛选条件
					// hideKeyWordArr: [],
					priceStart: '',
					priceEnd: ''
				},
				selectArea: {
					carrierId: '',
					groupId: '',
					serverId: '',
					systemId: ''
				}, //系统大区服务器数据

				isFilter: false, //是否有筛选条件
				isReset: false,
				sortOptions: [{
						name: '排序',
						fontSize: '15',
						value: ''
					},
					{
						name: '价格降序',
						fontSize: '15',
						value: 'pricedesc'
					},
					{
						name: '价格升序',
						fontSize: '15',
						value: 'priceasc'
					},
					{
						name: '时间降序',
						fontSize: '15',
						value: 'timedesc'
					},
					{
						name: '时间升序',
						fontSize: '15',
						value: 'timeasc'
					},
				],
				selectSort: '排序',

				areaList: [],
				inputStyle: {
					"height": "60rpx",
				},
				sysList: [], //系统列表
				carrierList: [], //运营商列表
				areaList: [], //大区列表
				serverList: [], //服务器列表
				listMode: 'img',
				boxradioIsShow: '',
				filterMapTemp: {}
			}
		},
		onLoad(option) {
			this.show = false
			this.searchParams.gameId = option.type
			const accountType = option.accountType
			if (accountType) {
				this.searchParams.accountType = accountType
			}
			this.currentGame = {
				id: option.type,
				type: Number(option.gameType),
				name: option.gameName
			}
			this.getQufu()
			this.getList()
			this.getCustomAttrs()
			this.getGameList()
		},
		onReachBottom() {
			this.nextPage()
		},
		methods: {
			moreClick(item) {
				// console.log(item);
				if (this.heightList[item.id] == 'auto') {
					// this.heightList[item.id] = '30px'
					this.$set(this.heightList, item.id, '0px')
				} else {
					// this.heightList[item.id] = 'auto'
					this.$set(this.heightList, item.id, 'auto')
				}
				// this.$forceUpdate()
			},

			switchImgTxt() {
				if (this.listMode == 'txt') {
					this.listMode = 'img'
				} else {
					this.listMode = 'txt'
				}
			},
			back() {
				uni.navigateBack()
			},
			// 查询游戏类型
			getGameList() {
				homeApi.getGames().then(res => {
					this.gameOptions = [res.data]
				})
			},
			// 选择游戏类型
			selectGameFn(e) {
				//console.log('e', e.value[0])
				this.filterMapTemp = {}
				this.currentGame = e.value[0]
				this.searchParams = {
					gameId: '',
					pageSize: 10,
					pageNum: 1
				}
				this.searchParams.gameId = this.currentGame.id
				this.showGame = false
				this.getQufu()
				this.searchList()
				this.getCustomAttrs()
			},
			// 获取列表
			getList() {
				this.loadMore = 'loading'
				homeApi.getList(this.searchParams).then(res => {
					uni.stopPullDownRefresh()
					if (res.rows.length >= this.searchParams.pageSize) {
						this.loadMore = 'loadmore'
						this.list = this.list.concat(res.rows)
					} else {
						this.list = this.list.concat(res.rows)
						this.loadMore = 'nomore'
					}
				})
			},

			// 查询自定义属性
			getCustomAttrs() {
				let gameId = this.searchParams.gameId
				homeApi.getAttrs(gameId).then(res => {
					this.searchAttrs = res.rows
					this.searchAttrs.forEach(r => {
						this.filterData[r.aavName] = ''
					})
				})
			},
			// 搜索
			searchList() {
				this.loadMore = 'loadmore'
				this.searchParams.pageNum = 1
				this.list = []
				this.getList()
			},
			getQufu() {
				if (this.currentGame.type == 0) {
					// 手游
					// 获取系统列表和运营商列表
					this.getSysList()
					this.getYysList()
				} else {
					// 端游
					// 获取大区列表
					this.getAreaList()
				}
			},
			// 获取系统列表
			getSysList() {
				homeApi.getArea({
					gameId: this.searchParams.gameId,
					systemKey: 'system'
				}).then(res => {
					this.sysList = res.rows
				})
			},
			// 获取运营商列表
			getYysList() {
				homeApi.getArea({
					gameId: this.searchParams.gameId,
					systemKey: 'carrier'
				}).then(res => {
					this.carrierList = res.rows
				})
			},
			// 运营商改变  获取大区列表
			getAreaList(e) {
				let obj = {
					gameId: this.searchParams.gameId,
					systemKey: 'group'
				}
				this.selectArea.groupId = ''
				this.selectArea.serverId = ''

				// 运营商改变 找出选择的运营商的对象
				if (e) {
					this.carrierList.forEach(i => {
						if (i.id == e) {
							obj.systemKey = i.systemKey + 'group'
						}
					})
				}
				homeApi.getArea(obj).then(res => {
					this.areaList = res.rows
				})
			},
			// 大区改变  获取服务器列表
			getServiceList(e) {
				this.searchParams.serverId = ''

				if (e) {
					homeApi.getServeList({
						parentId: e
					}).then(res => {
						this.serverList = res.rows
					})
				} else {
					this.serverList = []
				}
			},

			//点击大区选择项
			areaClick(item, key) {
				//console.log('item', item)
				this.selectArea[key] = item.id
				if (key == 'carrierId') {
					this.getAreaList(item.id)
				}
				if (key == 'groupId') {
					this.getServiceList(item.id)
				}
			},

			// 选择大区
			areaChange() {
				this.searchParams = {
					...this.searchParams,
					...this.selectArea
				}
				this.searchList()
				this.showArea = false
			},
			// 重置大区
			resetArea() {
				this.selectArea = {
					carrierId: '',
					groupId: '',
					serverId: '',
					systemId: ''
				}
				this.searchParams = {
					...this.searchParams,
					...this.selectArea
				}
			},

			// 选择排序
			selectSortFn(val) {
				this.selectSort = val.name
				this.searchParams.sortRank = val.value
				this.searchList()
			},
			// 筛选框
			// 单选改变
			radioClick(item, ind, custom) {
				if (this.filterData[custom.aavName] == item.id) {
					this.filterData[custom.aavName] = ''
				} else {
					this.filterData[custom.aavName] = item.id
				}
				this.$forceUpdate()
			},
			// 多选改变
			checkboxClick(item, ind, custom) {
				let fieldName = item.fieldName
				if (fieldName.indexOf('-') > -1 && this.currentGame.name.indexOf('王者荣耀') > -1) {
					fieldName = fieldName.split('-')[1]
				}
				// 取消选中
				if (this.filterData[custom.aavName + 'rander'] && this.filterData[custom.aavName + 'rander'].indexOf(item
						.id) > -1) {
					let i = this.filterData[custom.aavName + 'rander'].indexOf(item.id)
					this.filterData[custom.aavName + 'rander'].splice(i, 1)

					// 组装多选搜索关键词数组
					// if (this.filterData['hideKeyWordArr'] && this.filterData['hideKeyWordArr'].indexOf(fieldName) > -1) {
					// 	let i = this.filterData['hideKeyWordArr'].indexOf(fieldName)
					// 	this.filterData['hideKeyWordArr'].splice(i, 1)
					// }
				} else {
					// 选中
					if (this.filterData[custom.aavName + 'rander'] && this.filterData[custom.aavName + 'rander'].length) {
						this.filterData[custom.aavName + 'rander'].push(item.id)
					} else {
						this.filterData[custom.aavName + 'rander'] = [item.id]
					}
					// 组装多选搜索关键词数组
					// if (this.filterData['hideKeyWordArr'] && this.filterData['hideKeyWordArr'].indexOf(fieldName) == -1) {

					// 	this.filterData['hideKeyWordArr'].push(fieldName)
					// }
				}

				this.filterData[custom.aavName] = this.filterData[custom.aavName + 'rander'].join(',')
				this.$forceUpdate()
			},
			// 元神多选框点击
			boxradioCheckClick(checkItem, ind, custom) {
				if (this.boxradioIsShow == checkItem.id) {
					this.boxradioIsShow = ''
				} else {
					this.boxradioIsShow = checkItem.id
				}
				// 组装筛选数据 filterMapTemp
				if (this.filterMapTemp[checkItem.id]) {
					// 
					delete this.filterMapTemp[checkItem.id]
				} else {
					this.filterMapTemp[checkItem.id] = checkItem.fieldName.split('(')[0]
				}
				checkItem.ischeck = false
				this.filterData[custom.aavName + ind] = ''
				checkItem.fieldName = checkItem.fieldName.split('(')[0]
				this.setValue(custom)
			},
			// 元神单选框点击
			boxradioChange(e, checkItem, custom, ind) {
				// 组装筛选数据 filterMapTemp
				if (this.filterMapTemp[checkItem.id]) {
					// 
					delete this.filterMapTemp[checkItem.id]
				}
				// 找出比当前选择项高级的所有单选项
				let curInd
				let arr = []
				checkItem.childGameAttributes.forEach((radioItem, radioIndex) => {
					if (radioItem.id == e) {
						curInd = radioIndex
					}
					if ((curInd || curInd === 0) && curInd <= radioIndex) {
						arr.push(radioItem.fieldName)
					}
				})
				this.filterMapTemp[checkItem.id] = arr.join('===')
				//console.log('e', e)
				this.boxradioIsShow = ''
				checkItem.ischeck = true
				checkItem.childGameAttributes.forEach(r => {
					if (r.id == e) {
						checkItem.fieldName = checkItem.fieldName.split('(')[0] + '(' + r.fieldName + ')'
					}
				})
				this.setValue(custom)
			},
			// 原神多选框赋值
			setValue(custom) {
				// 赋值
				let arr = []
				custom.childAttributes.forEach((item, ind) => {
					if (item.ischeck) {
						// 多选框选中 取子级单选选中的id
						arr.push(this.filterData[custom.aavName + ind])
					}
				})
				if (arr.length) {
					this.filterData[custom.aavName] = arr.join(',')
				} else {
					this.filterData[custom.aavName] = ''
				}
				// console.log('this.form[custom.aavName]', this.form[custom.aavName])
				this.$forceUpdate()
			},
			// 确认筛选
			filterChange() {
				//console.log('this.filterMapTemp', this.filterMapTemp)
				// 组装原神类多选项筛选 boxRadioValues
				// let boxRadioValues = []
				// for (let key in this.filterMapTemp) {
				// 	console.log(this.filterMapTemp[key]) // foo, bar
				// 	if (this.filterMapTemp[key].trim()) {
				// 		boxRadioValues.push(this.filterMapTemp[key])
				// 	}

				// }
				// this.searchParams.boxRadioValues = boxRadioValues
				if (this.searchAttrs.length) {
					this.searchAttrs.forEach(r => {
						// 将数字类型的字段拼接成 xx===xx 的形式
						if (r.fieldType == 'number') {
							let sta = this.filterData[r.aavName + 'start'] || ''
							let end = this.filterData[r.aavName + 'end'] || ''
							if (sta && end) {
								this.filterData[r.aavName] = sta + '===' + end
							} else if (sta) {
								this.filterData[r.aavName] = sta + '==='
							} else if (end) {
								this.filterData[r.aavName] = '===' + end
							} else {
								this.filterData[r.aavName] = ''
							}
						}
					});
				}
				//console.log(this.filterData)
				if (this.isFilter == 'reset') {
					this.isFilter = false
				} else {
					this.isFilter = true
				}
				this.searchParams = {
					...this.searchParams,
					...this.filterData
				}
				this.searchList()
				this.showFiled = false
			},
			// 筛选重置
			resetFilter() {
				this.isFilter = 'reset'
				this.filterMapTemp = {}
				this.filterData = {
					// 筛选条件
					f1: '',
					f2: '',
					f3: '',
					f4: '',
					f5: '',
					f6: '',
					f7: '',
					f8: '',
					f9: '',
					f10: '',
					f11: '',
					f12: '',
					f13: '',
					f14: '',
					f15: '',
					f16: '',
					f17: '',
					f18: '',
					f19: '',
					f20: '',
					// hideKeyWordArr: []
				}
				// this.searchAttrs.forEach(custom => {
				// 	if (custom.fieldType == 'boxradio') {
				// 		custom.childAttributes.forEach((item, ind) => {
				// 			if (item.ischeck) {
				// 				item.ischeck = false
				// 				item.fieldName = item.fieldName.split('(')[0]
				// 			}
				// 		})
				// 	}
				// })
				this.searchParams = {
					...this.searchParams,
					...this.filterData
				}
				// this.searchList()
				// this.showFiled = false
				// this.filterChange()
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
			// 打开搜索页
			goSearch() {
				uni.navigateTo({
					url: `../searchPage/index`
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

		.goods_label_icon img {
			width: 180rpx;
			height: auto;
		}
	}

	.tip {
		color: #f56c6c;
	}

	.baopeiimg {
		width: 110rpx;
		height: 30rpx;
		margin-right: 16rpx;
	}

	.mian {
		font-size: 30rpx;
		border-top: none;
		// background-image: url('/static/xyr2.0/home/homebg.png');
		background-color: #383B56;
		background-size: 750rpx auto;
		background-repeat: no-repeat;
		min-height: 1600rpx;

		.zhaoimg {
			position: fixed;
			bottom: 20%;
			right: 2%;
			width: 212rpx;
			height: 168rpx;
		}

		.content-box {
			padding: 0 30rpx;
			position: relative;
		}

	}

	.search-bar {
		height: 60rpx;
		background-color: #383B56;
		// margin: 30rpx;
		// border-radius: 20rpx;
		display: flex;
		z-index: 100;
		flex-wrap: nowrap;
		margin-top: -30rpx;
		padding: 40rpx 30rpx;

		.btn {
			// width: 25%;
			flex-wrap: nowrap;
			box-sizing: border-box;
			padding: 0 30rpx;
			font-size: 26rpx;
			align-items: center;
			// border-right: 1px solid #d8d8d8;
			border-radius: 45rpx;
			background-color: #fff;

			.u-icon {
				margin-left: 3px;
			}

			&.area-btn {
				// width: 128rpx;
			}

			&.imgtxt-btn {
				.icon {
					width: 30rpx;
					height: 30rpx;
					// background-image: url('/static/xyr2.0/goodslist/tuwen.png');
					// background-size: 100% 100%;
					margin-left: 8rpx;

				}
			}
		}

	}


	.goods-box {
		margin-top: 10px;
	}


	.goods-item2 {
		box-sizing: border-box;
		width: 100%;
		flex-wrap: nowrap;
		// flex-shrink: 0;
		// margin-right: 30rpx;
		margin-bottom: 30rpx;
		background-color: #1C1632;
		border-radius: 24rpx;
		position: relative;

		>.flex {
			flex-wrap: nowrap;
		}

		.img-box {
			width: 100%;
			height: 410rpx;
			border-radius: 20rpx 20rpx 0 0;
			overflow: hidden;
		}

		.title {
			align-items: center;
		}

		.price {

			color: #FF6958;

			.price-num {
				font-weight: 500;
				font-size: 38rpx;
				margin-left: 10rpx;
			}

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

		.des {
			// color: #999;
			overflow: hidden;
			display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 3;
			margin-bottom: 10rpx;
			width: 100%;
			// min-height: 102rpx;
			font-size: 26rpx;
			color: #fff;
		}

		.bot-box {
			align-items: center;
			margin-top: 10rpx;
		}

		.des-cont {
			padding: 0 20rpx;
			padding-bottom: 30rpx;
			padding-top: 30rpx;

			.qufu {
				color: #bdbdbd;
			}
		}
	}

	.pop-box {
		min-width: 100%;
		max-height: 80vh;
		height: 500px;
		overflow-y: auto;
		padding-left: 20rpx;
		background-color: #344356;
	}

	.area-pop {
		width: 350px;
		border-radius: 10px 10px 0 0;
		padding: 30rpx;

		.area-box {
			box-sizing: border-box;
			flex-wrap: wrap;

			.tit {
				margin-bottom: 10px;
				color: #fff;
			}

			// padding-bottom: 62px;
		}
	}

	.btns {
		position: absolute;
		left: 0;
		bottom: 0;
		width: 100%;
		padding-top: 10px;
		padding-bottom: 10px;
		// border-top: 1px solid #F3F5F9;
		// background-color: #f3f5f9;
		// box-shadow: 0 0 7px 0 #ccc;

		.u-button {
			width: 40%;
		}
	}

	.filter-box {
		width: 350px;

		.cont-box {
			padding: 15px;
			margin-bottom: 30rpx;
			// text-align: center;
			// margin-left: 9.8%;

			.tit {
				font-size: 14px;
				margin: 10px 0;
				color: #fff;
			}
		}
	}

	.sign-item {
		display: flex;
		justify-content: space-between;
		width: 46.5%;
		flex-shrink: 0;
		// display: inline-block;
		// text-align: center;
		border-radius: 8px;
		margin-right: 10px;
		margin-bottom: 10px;
		font-size: 14px;
		background: #F3F5F9;
		border: 1px solid #F3F5F9;
		// color: #fff;
		padding: 5px 0 5px 20rpx;
		// height: 32px;
		// line-height: 32px;
		font-size: 12px;
		color: #344356;
		// overflow: hidden;
		position: relative;

		.sign-text {
			width: 100%;
			height: 100%;
			overflow: hidden;
			color: #333;
		}

		&:nth-of-type(3n) {
			// margin-right: 0;
		}

		&.active {
			// background: rgba(84, 104, 255, .1);
			background-color: #FF6958;
			border: 1px solid #FF6958;
			color: #fff;

			.sign-text {
				width: 100%;
				height: 100%;
				overflow: hidden;
				color: #fff;
			}
		}

	}

	.price ::v-deep .u-input {
		width: 180rpx;
	}

	.price-line {
		color: #dadbde;
		line-height: 30px;
		margin: 0 5px;
	}

	.sticky-box {
		// background-image: url('/static/xyr2.0/home/homebg.png');
		background-size: 750rpx auto;
		background-repeat: no-repeat;
		padding-bottom: 10px;
	}

	.topbar {
		width: 100%;
		height: 50px;
		background-color: #f3f3f3;
		// padding: 0 30rpx;
		// padding-top: 8px;
		// padding-left: 20rpx;
	}

	.back {
		background-color: transparent;
		padding-top: 10rpx;
		padding-left: 0;
		color: #ccc;
	}

	.search-box {
		height: 35px;
		width: 100%;
		box-sizing: border-box;
		// border-radius: 20px;
		background-color: #383B56;
		padding-left: 50rpx;
		padding-right: 50rpx;
		padding-bottom: 30rpx;

		.icon {
			width: 28rpx;
			height: 28rpx;
			background-image: url('/static/guaishouwan/sousuo.png');
			background-size: 28rpx 28rpx;
		}

		.type-box {
			font-weight: 601;
		}

		.type-box-icon {
			transform: rotate(90deg) scale(0.6);
		}
	}

	/deep/ .topbar .uni-input-input {}

	.boxradioRadioBox {
		z-index: 999999999;
		min-width: 100px;
		white-space: nowrap;
		background: #fff;
		box-shadow: 0 0 7px #e3e3e3;
		padding: 10px;
		position: absolute;
		left: 0px;
		top: 30px;
		border-radius: 10px;
		display: none !important;

	}

	.boxradioRadioBox.show {
		display: block !important;
	}


	::v-deep .u-radio__text {
		font-size: 12px !important;
	}

	.nav {
		// min-height: 0px;
		overflow: hidden;
	}

	::v-deep .uni-input-input {
		color: #fff;
	}

	::v-deep .zidinbgyi .uni-input-input {
		color: #000;
	}

	::v-deep .u-action-sheet__item-wrap {
		background-color: #344356;
	}

	::v-deep .u-action-sheet__item-wrap__item__name {
		color: #fff !important;
	}
</style>