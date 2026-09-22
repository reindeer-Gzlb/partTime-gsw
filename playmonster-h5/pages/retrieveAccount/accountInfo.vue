<template>
	<!-- 用户填写游戏信息 官方代售  -->
	<view class="recovery-content" :style="{minHeight:gao + 'px'}">
		<view class="select-area-box" v-if="currentGame">
			<view class="item-box">
				<view class="tit-box flex">
					<view class="icon"></view>
					<view class="tit">
						当前游戏
					</view>
				</view>
				<view class="game-box flex fbt">
					<view class="game-img-name flex">
						<image :src="currentGame.icon" mode="" style="width: 100rpx;height: 100rpx;"></image>
						<view class="gamename">
							{{currentGame.name}}
						</view>
					</view>
					<view class="btn" @click="back">
						重新选择
					</view>
				</view>
			</view>
			<view class="item-box system" v-if="currentGame.type!=1&&sysList&&sysList.length&&stepIndex==1">
				<view class="tit-box flex">
					<view class="icon">

					</view>
					<view class="tit">
						操作系统
					</view>
				</view>
				<view class="area-box flex">
					<view class="select-item" @click="areaClick(system,'system')"
						:class="{active:form.systemId==system.id}" v-for="system in sysList" :key="system.id">
						{{system.fieldName}}
					</view>
				</view>
			</view>
			<view class="item-box carrier" v-if="currentGame.type!=1&&carrierList&&carrierList.length&&stepIndex==1">
				<view class="tit-box flex">
					<view class="icon">

					</view>
					<view class="tit">
						运营商
					</view>
				</view>
				<view class="area-box flex">
					<view class="select-item" @click="areaClick(item,'carrierId')"
						:class="{active:form.carrierId==item.id}" v-for="item in carrierList" :key="item.id">
						{{item.fieldName}}
					</view>
				</view>
			</view>
			<!-- v-if="areaList&&areaList.length&&stepIndex==1" -->
			<view class="item-box carrier" v-if="currentGame.type==1&&areaList&&areaList.length&&stepIndex==1">
				<view class="tit-box flex">
					<view class="icon">

					</view>
					<view class="tit">
						大区
					</view>
				</view>
				<view class="area-box flex">
					<view class="select-item" @click="areaClick(item,'area')" :class="{active:form.groupId==item.id}"
						v-for="item in areaList" :key="item.id">
						{{item.fieldName}}
					</view>
				</view>
			</view>
			<!-- v-if="serverList&&serverList.length&&stepIndex==1" -->
			<view class="item-box carrier" v-if="currentGame.type==1&&serverList&&serverList.length&&stepIndex==1">
				<view class="tit-box flex">
					<view class="icon">

					</view>
					<view class="tit">
						服务器
					</view>
				</view>
				<view class="area-box flex">
					<view class="select-item" @click="areaClick(item,'server')" :class="{active:form.serverId==item.id}"
						v-for="item in serverList" :key="item.id">
						{{item.fieldName}}
					</view>
				</view>
			</view>
			<view class="nextStep" v-if="stepIndex==1">
				<u-button type="primary" text="下一步" @click="nextStep" class="next-btn"></u-button>
			</view>
		</view>
		<view class="" v-if="stepIndex==3">
			<view class="shangjiaBtn" @click="isImgOnSelf=true;stepIndex=2">
				自己上架
			</view>
			<view class="shangjiaBtn" @click="isImgOnSelf=false;stepIndex=2">
				客服上架
			</view>
		</view>
		<view class="page-content" v-show="stepIndex==2">
			<u--form labelWidth="140rpx" :labelStyle="{color:'#333','font-size':'28rpx','font-weight':'bold'}"
				:model="form" ref="uForm1" :rules="rules">
				<view class="account-info" style="margin-bottom: 30rpx;margin-top: -30rpx;">
					<view class="tit-box flex">
						<view class="icon">

						</view>
						<view class="tit">
							基本信息
						</view>
					</view>
					<!-- <view class="" v-if="currentGame.name=='王者荣耀'||(currentGame.name=='和平精英'&& isImgOnSelf)">
						<u-form-item label="截图备注" prop="jietubeizhu"  style="margin-top: -150rpx;">
							<u--textarea   style="margin-top: 140rpx;" v-model="form.jietubeizhu" border="surround" maxlength="-1" color="#333"
								placeholder="请输入截图备注">
							</u--textarea>
						</u-form-item>
					</view> -->
					<u-form-item :label="'商品价格'" prop="price">
						<u--input v-model="form.price" border="surround" color="#333" placeholder="请输入价格"
							@change="priceChange"></u--input><br />
					</u-form-item>
					<view class="tip price-tip">
						<text>手续费标准：5%（平台收取售价的5%，单笔最低收取50）</text><br />
						<text>当前商品交易服务费<text style="color: #f75352;">{{fuwufei}}</text>元，实际交易服务费以商品最终成交价为计算标准。</text>
					</view>
					<!-- <u-form-item label="商品标题" prop="title"
						v-if="currentGame.name!='王者荣耀'&&(currentGame.name!='和平精英'||(currentGame.name=='和平精英'&&isImgOnSelf))">
						<u--input v-model="form.title" border="surround" maxlength="200" color="#333"
							placeholder="请填写商品标题">
						</u--input>
					</u-form-item> -->
					<u-form-item label="商品描述" prop="des">

						<u--textarea v-model="form.des" border="surround" maxlength="-1" color="#333"
							placeholder="请简单的描述一下你的账号吧~">
						</u--textarea>

					</u-form-item>
					<u-form-item label="商品主图" prop="" ref="item3">
						<view class="drag-box">
							<my-drag-files @drop="ondrop($event,'1')">
								<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic"
									accept="image" name="1" multiple uploadText="点击上传" :maxCount="1">
								</u-upload>
								<view class="tip">
									该图会成为用户浏览商品时的缩略图<br />
								</view>
							</my-drag-files>
						</view>
					</u-form-item>
					<u-form-item label="详情图" prop="" ref="item4">
						<view class="drag-box">
							<my-drag-files @drop="ondrop($event,'2')">
								<u-upload :fileList="fileList2" @afterRead="afterRead" @delete="deletePic"
									accept="image" name="2" multiple uploadText="点击上传" :maxCount="50">
								</u-upload>
								<view class="tip">
									请上传游戏内有价值的装备皮肤等截图<br />
								</view>
							</my-drag-files>
						</view>
					</u-form-item>
				</view>
				<view class="attr-list input-ml">
					<view class="tit-box flex" style="padding-left: 30rpx;">
						<view class="icon">

						</view>
						<view class="tit">
							游戏属性
						</view>
					</view>
					<view class="cont-box" v-if="attrList&&attrList.length" style="padding-left: 30rpx;">
						<view class="" v-for="custom in attrList">
							<view class="" style=""
								v-if="(currentGame.name!='王者荣耀'||(currentGame.name=='王者荣耀'&&custom.aavName!='f17'))&&(currentGame.name!='和平精英'||(currentGame.name=='和平精英'&&custom.aavName!='f13'))">
								<u-form-item :label="custom.fieldName" :prop="custom.aavName"
									v-if="custom.fieldType=='number'||custom.fieldType=='input'">
									<u--input v-model="form[custom.aavName]" border="surround" color="#333"
										:placeholder="custom.aavName != 'f17' ? '请输入' + custom.fieldName : '请输入您觉得比较有亮点的内容'"
										@change="change">
									</u--input>
								</u-form-item>
								<u-form-item :label="custom.fieldName" :prop="custom.aavName" class="attr-input-content"
									:key="custom.id" v-if="custom.fieldType=='radio'" style="">

									<view class="">
										<!-- <u--input :ref="custom.aavName" v-model="form[custom.aavName]" border="surround"
											color="#333">
										</u--input> -->
										<uni-data-checkbox selectedColor="#B42C1D" v-model="form[custom.aavName]"
											:localdata="custom.childAttributes" mode="tag"
											:map="{text:'fieldName',value:'id'}" @change="radiochange($event,custom)"
											style="">
										</uni-data-checkbox>
										<view class="flex sm-box" v-if="custom.fieldKey=='sm'">

											<u-upload :fileList="fileList3" @afterRead="afterRead" @delete="deletePic"
												accept="image" name="3" multiple uploadText="实名截图" :maxCount="1">
											</u-upload>
											<!-- <view class="sm_btn" @click="viewSmInfo">

											</view> -->
										</view>
									</view>

								</u-form-item>
								<view class="multiple-content" style="position: relative;"
									v-if="custom.fieldType == 'checkbox'" :key="custom.id">
									<view class=""
										style="position: absolute;top: 0%;right: 0%;color: blue;width: 150rpx;margin-top: -10rpx;">
										<u-button @click="moreClick(custom)"
											style="width: 150rpx;height: 50rpx;border: 1px solid #B42C1D;color: #B42C1D;"
											:plain="true" text="确定">
											{{ heightList[custom.id] == 'auto' ? '收起' : '展开'}}
											<u-icon color="#B42C1D"
												:name="heightList[custom.id] == 'auto' ? 'arrow-up': 'arrow-down'"></u-icon></u-button>
									</view>
									<view class="multiple-title">
										{{custom.fieldName}}
									</view>
									<view class="multiple-list-content"
										:style="{ height: heightList[custom.id] || '0px' }">
										<u-form-item :prop="custom.aavName">
											<uni-data-checkbox multiple v-model="form[custom.aavName+'rander']"
												:localdata="custom.childAttributes" :map="{text:'fieldName',value:'id'}"
												@change="checkboxchange($event,custom)">
											</uni-data-checkbox>
										</u-form-item>
									</view>
								</view>
								<view class="multiple-content" style="position: relative;"
									v-if="custom.fieldType=='boxradio'">
									<view class=""
										style="position: absolute;top: 0%;right: 0%;color: blue;width: 150rpx;margin-top: -10rpx;">
										<u-button @click="moreClick(custom)"
											style="width: 150rpx;height: 50rpx;border: 0px solid #000;" type="primary"
											:plain="true" text="确定">
											{{ heightList[custom.id] == 'auto' ? '收起' : '展开'}}
											<u-icon
												:name="heightList[custom.id] == 'auto' ? 'arrow-up': 'arrow-down'"></u-icon></u-button>
									</view>
									<view class="multiple-title">
										{{custom.fieldName}}
									</view>
									<view class="multiple-list-content"
										:class="{'multiple-list-content-visit': heightList[custom.id]== 'auto',}"
										:style="{ height: heightList[custom.id] || '0px'}">
										<view class=" flex ">
											<view class="sign-item" v-for="(item,ind) in custom.childAttributes"
												:class="{active:item.ischeck}"
												@click.stop="boxradioCheckClick(item,ind,custom)">
												<view class="sign-text">
													{{item.fieldName}}
												</view>
												<u-radio-group v-model="form[custom.aavName + ind]" placement="column"
													class="boxradioRadioBox"
													:class="{ 'show': item.id == boxradioIsShow }"
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
					<!-- <u-form-item label="手机号" prop="tel">
						<u--input v-model="form.tel" border="surround" color="#333" placeholder="请输入您的手机号"></u--input>
					</u-form-item> -->
					<!-- <u-form-item label="验证码" prop="dxyzm">
						<u--input v-model="form.dxyzm" border="surround" color="#333" placeholder="短信验证码"></u--input>
						<u-button v-if="timer" style="width: 100px;color: #3c9cff;" type="text" disabled>
							<text>{{ timer }}秒</text>
						</u-button>
						<u-button v-else style="width: 100px;color: #3c9cff;" @click="sendYzm" type="text">
							<text>发送验证码</text>
						</u-button>
					</u-form-item> -->
					<!-- <u-form-item label="游戏账号" prop="accountNumber">
						<u--input v-model="form.accountNumber" border="surround" color="#333" placeholder="请输入游戏账号">
						</u--input>
					</u-form-item> -->
				</view>
				<view class="account-info">
					<view class="tit-box flex">
						<view class="icon">

						</view>
						<view class="tit">
							账号信息
						</view>
					</view>
					<u-form-item label="游戏账号" prop="accountNumber">
						<u--input v-model="form.accountNumber" border="surround" color="#333"
							placeholder="请输入QQ账号或者微信账号">
						</u--input>
					</u-form-item>
					<u-form-item label="营地ID" prop="f17" v-if="currentGame.name=='王者荣耀'">
						<u--input v-model="form.f17" border="surround" color="#333" placeholder="请输入营地ID">
						</u--input>
					</u-form-item>
					<u-form-item label="营地ID" prop="f13" v-if="currentGame.name=='和平精英'">
						<u--input v-model="form.f13" border="surround" color="#333" placeholder="请输入营地ID">
						</u--input>
					</u-form-item>
				</view>
				<view class="account-info">
					<view class="tit-box flex">
						<view class="icon">

						</view>
						<view class="tit">
							保障信息
						</view>
					</view>
					<u-form-item class="textright" label-width="auto" label="能否提供包赔材料" prop="assurance" borderBottom
						ref="item1">
						<u-icon @click="showModel(item)" name="question-circle-fill"
							style="margin-top: 10rpx;"></u-icon>
						<text @click="showSex = true;"
							style="margin:0 30rpx;text-align: right;font-size: 26rpx; width: 100%;">{{form.assurance||'请选择'}}</text>
						<!-- <u--input @click="showSex = true;" placeholderStyle="text-align: right"
							style="margin-left: 30rpx;text-align: right" v-model=" form.assurance" disabled
							disabledColor="#ffffff" placeholder="请选择" border="none"></u--input> -->
						<u-icon @click="showSex = true;" slot="right" name="arrow-right"></u-icon>
					</u-form-item>
					<u-form-item label="联系QQ" prop="qq">
						<u--input v-model="form.qq" border="surround" color="#333" placeholder="请输入联系QQ">
						</u--input>
					</u-form-item>
					<u-form-item label="联系微信" prop="weixin">
						<u--input v-model="form.weixin" border="surround" color="#333" placeholder="请输入联系微信">
						</u--input>
					</u-form-item>
					<u-form-item label="联系电话" prop="tel">
						<u--input v-model="form.tel" border="surround" color="#333" placeholder="请输入联系电话">
						</u--input>
					</u-form-item>

				</view>
				<!-- 生成简介 -->
				<!-- <view class="" v-if="currentGame.name!='王者荣耀'">
					<u-form-item label="账号简介" prop="des">
						<u--textarea v-model="form.des" border="surround" maxlength="-1" color="#333"
							placeholder="请填写简介...">
						</u--textarea>
					</u-form-item>
					<u-form-item label="截图" prop="" ref="item3">
						<view class="drag-box">
							<u-button size="small" type="primary" plain text="自动生成" @click="makeImgBtn"
								style="margin-bottom: 10px;"></u-button>
							<my-drag-files @drop="ondrop($event,'1')">
								<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic"
									accept="image" name="1" multiple uploadText="点击上传" :maxCount="20">
								</u-upload>
								<view class="tip">
									提示：请上传游戏截图、实名截图等<br />
								</view>
							</my-drag-files>
						</view>
					</u-form-item>
				</view> -->
			</u--form>
			<view class="" style="margin-top:30rpx" v-if="stepIndex==2||stepIndex==3">
				<u-button plain text="上一步" @click="preStep" class=" width-w"></u-button>
			</view>
			<view class="">
				<u-button style="background-color: #B42C1D;border: none;" type="primary" text="提交" @click="submit"
					class="merge-sub width-w"></u-button>
			</view>
		</view>
		<!-- 提示 -->
		<u-modal :closeOnClickOverlay="false" :showCancelButton="false" @confirm="back" :show="show" title="提示"
			:asyncClose="true" :content='content' @cancel="show=false" @close="show=false"></u-modal>

		<u-action-sheet :show="showSex" :actions="actions" title="包赔材料" description="请选择能否提供包赔材料"
			@close="showSex = false" @select="sexSelect">
		</u-action-sheet>
		<!-- 包赔资料介绍弹出窗 -->
		<u-modal :show="showBpzl" :closeOnClickOverlay="true" @close="showBpzl = false;content = ''" :title="'包赔材料说明'"
			@confirm="showBpzl = false;content = ''" confirm-text="关闭">
			<view class="slot-content">
				<rich-text :nodes="contentBpzl"></rich-text>
			</view>
		</u-modal>
	</view>
</template>

<script>
	// import * as api from "./api.js"
	import homeApi from "../../api/home/index.js"
	import huishouApi from "../../api/home/huishou.js"
	import myDragFiles from "@/components/my-dragfiles.vue"
	import uniDataCheckbox from '@/components/uni-data-checkbox'
	export default {
		components: {
			myDragFiles,
			uniDataCheckbox
		},
		data() {
			return {
				showBpzl: false,
				contentBpzl: '',
				showSex: false,
				flag: true,
				// statuse:false,
				heightList: [],
				gameInfo: {},
				isImgOnSelf: false, //是否自己上传图片
				fuwufei: 0,

				stepIndex: 1,
				content: '',
				show: false,
				timer: 0,
				showGameType: false, //游戏类型弹窗
				gameTypeList: [], //游戏类型列表
				qufuList: [], //区服列表，多级
				showAreaBox: false, //区服选择框
				loading: false, //区服选择框等待

				form: {
					type: "",
					gameId: '',
					des: "",
					roleValue: "",
					shiming: '',
					price: '',
					importantRemark: '',
					tel: '',
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
					f20: ''
				},
				rules: {
					gameName: {
						required: true,
						message: '请选择游戏类型',
						trigger: ['change']
					},
					assurance: {
						required: true,
						message: '请选择能否提供包赔材料',
						trigger: ['change', 'blur']
					},
					tel: [{
							required: true,
							message: '请输入手机号',
							trigger: ['change', 'blur'],
						},
						{
							// 自定义验证函数，见上说明
							validator: (rule, value, callback) => {
								// 上面有说，返回true表示校验通过，返回false表示不通过
								// uni.$u.test.mobile()就是返回true或者false的
								return uni.$u.test.mobile(value);
							},
							message: '手机号码不正确',
							// 触发器可以同时用blur和change
							trigger: ['change', 'blur'],
						}
					],
					accountNumber: {
						required: true,
						message: '请输入QQ账号或者微信账号',
						trigger: ['blur', 'change']
					},
					price: {
						required: true,
						message: '请输入商品价格',
						trigger: ['blur', 'change']
					},
					qq: {
						required: true,
						message: '请输入联系QQ',
						trigger: ['blur', 'change']
					},
					weixin: {
						required: true,
						message: '请输入联系微信',
						trigger: ['blur', 'change']
					},
					// region: {
					// 	required: true,
					// 	message: '请选择区服',
					// 	trigger: ['blur', 'change']
					// },
					des: {
						required: true,
						message: '请输入商品描述',
						trigger: ['blur', 'change']
					}
				},
				attrList: [],
				valuationList: [], //估值列表
				codeImg: null,
				isWin: false,
				showRoleList: false, //角色弹窗
				roleList: [], //角色数据
				isNeed: [{
						label: '生成',
						value: 1
					},
					{
						label: '不生成',
						value: 0
					},
				],
				selectList: [], //王者选中的皮肤
				titleImg: '',
				imgs: [], //生成的皮肤图片
				isUploading: false,
				fileList1: [],
				fileList2: [],
				fileList3: [],
				boxradioIsShow: '',
				currentGame: null, //当前选择的游戏
				sysList: [], //系统列表
				carrierList: [], //运营商列表
				areaList: [], //大区列表
				serverList: [], //服务器列表
				// supplyUserId: '', //回收商的id
				actions: [{
						name: '能提供',
						value: 1
					},
					{
						name: '不能提供',
						value: 2
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
			let gameInfo = {}
			gameInfo.name = option.type
			gameInfo.id = option.gameId
			gameInfo.type = option.gameType
			gameInfo.icon = option.gameIcon
			this.areaList = []
			this.currentGame = gameInfo
			this.form = {
				gameName: this.currentGame.name,
				gameId: this.currentGame.id,
				type: this.currentGame.name,
				des: "",
				roleValue: "",
				shiming: '',
				price: '',
				importantRemark: '',
				tel: '',
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
				f20: ''
			}
			this.getAttrList()
			this.getAreaAllList()

		},
		onBackPress() {
			if (this.flag) {
				uni.showModal({
					title: "确定退出编辑？",
					content: "退出后内容将全部清空，您确定要退出编辑吗？",
					success: (res) => {
						if (res.confirm) {
							// //console.log("确定返回");
							this.flag = false
							uni.navigateBack({
								delta: 1, //返回层数，2则上上页
							})
							return true
						} else {
							this.flag = true
							// //console.log("取消");
							return true
						}
					}
				})
				return true
			} else {
				return false
			}
		},
		methods: {
			viewSmInfo() {
				uni.previewImage({
					urls: ['/static/feiheng/sm-detail2.jpg'],
				})
			},
			moreClick(item) {
				// //console.log(item);
				if (this.heightList[item.id] == 'auto') {
					// this.heightList[item.id] = '30px'
					this.$set(this.heightList, item.id, '0px')
				} else {
					// this.heightList[item.id] = 'auto'
					this.$set(this.heightList, item.id, 'auto')
				}
				// this.$forceUpdate()
			},
			//弹出层
			// tanchuceng(item){
			// 	//console.log(item);
			// },
			back() {
				// uni.navigateBack()
				// this.form = {}
				uni.navigateTo({
					url: '/pages/gameList/otherGameList?type=guanwang'
					// url:'/pages/mine/myAccount/index'
				})
			},
			// 价格改变
			priceChange(e) {
				//console.log(e)
				let num = (e * 0.05).toFixed(2)
				if (num < 50) {
					num = 50
				}
				this.fuwufei = num
				// //console.log(e)
				// let num = e * 0.05
				// if (this.form.price < 51) {
				// 	uni.showToast({
				// 		title:'亲!商品价格不能低于50元',
				// 		icon:'none'
				// 	})
				// 	return 
				// }else{
				// 	if (this.form.price > 50){
				// 		uni.showToast({
				// 			title:'亲!商品价格大于50元，您可以继续操作',
				// 			icon:'none'
				// 		})
				// 	}
				// }
				// // if()
				// this.fuwufei = num
			},
			// 选择区服等
			areaClick(item, type) {
				if (type == 'system') {
					this.$set(this.form, 'systemId', item.id)
				} else if (type == 'carrierId') {
					this.$set(this.form, 'carrierId', item.id)
					// 获取大区列表
					this.getAreaList(item.id)
				} else if (type == 'area') {
					this.$set(this.form, 'groupId', item.id)
					// 获取服务器列表

					this.getServiceList(item.id)
					this.$set(this.form, 'serverId', '')
				} else {
					// 选择服务器
					this.$set(this.form, 'serverId', item.id)
				}
			},
			// 下一步

			nextStep() {
				if ((this.currentGame.type != 1 && this.form.systemId && this.form.carrierId) || (this.currentGame
						.type ==
						1 && this.form.groupId && this.form.serverId)) {
					this.stepIndex = 2

					// if (this.currentGame.name == '和平精英') {
					// 	this.stepIndex = 3
					// } else {
					// 	this.stepIndex = 2
					// }
					this.fileList1 = []
					this.fileList2 = []
					this.form.titImg = ''
					this.form.urlArr = []
				} else {
					uni.showToast({
						title: '请填写完整',
						icon: 'none'
					})
				}

			},

			// 选择游戏
			//选择游戏类型
			gameTypeSelect(e) {
				this.qufuList = []
				this.currentGame = e.value[0]
				this.showGameType = false
				this.form = {
					gameName: e.value[0].name,
					gameId: e.value[0].id,
					type: e.value[0].name,
					des: "",
					roleValue: "",
					shiming: '',
					price: '',
					tel: '',
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
					f20: ''
				}
				this.getAttrList()

			},


			// 获取区服列表
			async getAreaAllList() {
				//console.log('this.currentGame', this.currentGame)
				if (this.currentGame.type == 0) {
					// 手游
					await this.getSysList()
					await this.getYysList()
				} else if (this.currentGame.type == 1) {
					// 端游
					this.getAreaList()
				}
			},
			// 查询系统列表
			// 获取系统列表
			async getSysList() {
				let res = await homeApi.getArea({
					gameId: this.currentGame.id,
					systemKey: 'system'
				})
				this.sysList = res.rows
			},
			// 获取运营商列表
			async getYysList() {

				let res = await homeApi.getArea({
					gameId: this.currentGame.id,
					systemKey: 'carrier'
				})
				this.carrierList = res.rows
				this.qufuList = [this.sysList, this.carrierList]
				// await this.getAreaList(res.rows[0].id)
			},
			// 运营商改变  获取大区列表
			async getAreaList(e) {
				let obj = {
					gameId: this.currentGame.id,
					systemKey: 'group'
				}
				// 运营商改变 找出选择的运营商的对象
				if (e) {
					this.carrierList.forEach(i => {
						if (i.id == e) {
							obj.systemKey = i.systemKey + 'group'
						}
					})
				}
				//console.log('this.$refs.uPicker', this.$refs.uPicker)
				let res = await homeApi.getArea(obj)
				this.areaList = res.rows
			},
			// 大区改变  获取服务器列表
			async getServiceList(e) {
				if (e) {
					let res = await homeApi.getServeList({
						parentId: e
					})
					this.serverList = res.rows

				} else {
					this.serverList = []
				}
			},
			// 查询自定义属性
			getAttrList() {
				let gameId = this.form.gameId
				let attrs = []
				homeApi.getAttrs(gameId).then(res => {
					//console.log('rrrrrrrrrrrrrrrrr', res);
					res.rows.forEach(r => {

						if (r.isEnable === '0' && r.hsisShow == 1) {
							// 属性启用并且在发布页显示
							this.form[r.aavName] = ''
							if (r.fieldType == 'checkbox') {
								this.form[r.aavName + 'rander'] = []
							} else if (r.fieldType == 'input' || r.fieldType == 'number') {
								if (r.must == 'true') {
									this.rules[r.aavName] = {
										required: true,
										message: `请输入${r.fieldName}`,
										trigger: ['change', 'blur']
									}
								}
							}
							attrs.push(r)

						}

					})
					this.attrList = attrs
					//console.log('this.attrList', this.attrList)
				})
			},
			//生成简介
			subForm() {
				// uni.showLoading({
				// 	title: '生成中...'
				// })
				// let str = ''
				// if (this.form.type) {
				// 	str += `游戏类型:${this.form.type} `
				// }
				// this.attrList.forEach(item => {
				// 	//  {
				// 	if (item.fieldType != 'checkbox' && item.fieldType != 'boxradio' && item.fieldType !=
				// 		'radio') {
				// 		if (this.form[item.aavName]) {
				// 			str += `${item.fieldName}: `
				// 			str += this.form[item.aavName]
				// 			str += " "
				// 		}

				// 	} else if (item.fieldType == 'radio') {
				// 		if (this.form[item.aavName + 'name']) {
				// 			str += `${item.fieldName}: `
				// 			str += this.form[item.aavName + 'name']
				// 			str += " "
				// 		}

				// 	} else if (item.fieldType == 'boxradio') {
				// 		if (this.form[item.aavName + 'name']) {
				// 			str += `${item.fieldName}: `
				// 			str += this.form[item.aavName + 'name']
				// 			str += " "
				// 		}

				// 	} else {
				// 		//console.log('this.form[item.aavName name]', this.form[item.aavName + 'name'])
				// 		if (this.form[item.aavName + 'name']) {
				// 			str += `${item.fieldName}: `
				// 			str += this.form[item.aavName + 'name']
				// 			str += " "
				// 		}

				// 	}
				// 	// }
				// })
				// this.$set(this.form, "des", str)
				// uni.hideLoading()
			},
			// 根据简介自动识别多选项
			zidongChange() {
				let arr = []
				let tempArr = []
				if (this.form.des) {
					let desStr = this.form.des
					desStr
					// //console.log("%c Line:1305 🍔 desStr", "color:#ffdd4d", desStr);
					arr = desStr.split(' ')
					// //console.log("%c Line:1283 🍷 arr", "color:#e41a6a", arr);
					tempArr = JSON.parse(JSON.stringify(arr))
					if (arr && arr.length) {
						arr.forEach(r => {
							if (r && r.indexOf(',')) {
								tempArr = tempArr.concat(r.split(','))
							}
						})
					}
					arr = tempArr
				}
				// //console.log("%c Line:426 🍎 识别出的选项arr", "color:#465975", arr);
				// 回显勾选
				this.attrList.forEach(item => {
					// if (this.form[item.aavName]) {
					if (item.fieldType == "checkbox" && item.childAttributes) {
						let nameArr = []
						let idArr = []
						item.childAttributes.forEach(t2 => {
							arr.forEach(t3 => {

								if (t3 && (t2.fieldName.indexOf(t3) > -1) && idArr.indexOf(
										t2
										.id) == -1) {
									// if (this.form[item.aavName + 'rander'].indexOf(t2.id) == -1) {
									idArr.push(t2.id)
									nameArr.push(t2.fieldName)
									// }
								}
							})
						});
						this.form[item.aavName + 'name'] = nameArr.join(' ')

						this.$set(this.form, item.aavName + 'rander', idArr)
						this.form[item.aavName] = this.form[item.aavName + 'rander'].join(',')
						// //console.log('开始组装简介')
						this.subForm()
						// //console.log('this.form[item.aavName', item.aavName, this.form[item
						// 	.aavName])
						// this.$forceUpdate()
					}
					//  else if (item.fieldType == "boxradio" && item.childAttributes) {
					// 	item.childAttributes.forEach((t2, t2Ind) => {
					// 		t2.childGameAttributes.forEach(t4 => {
					// 			arr.forEach(t3 => {
					// 				// t2.fieldName.indexOf(t3) > -1
					// 				// t4.fieldName == t3
					// 				if (t3 && (t4.fieldName.indexOf(t3) > -1) && !t2
					// 					.ischeck) {
					// 					this.form[item.aavName + t2Ind] = t4.id
					// 					t2.ischeck = true
					// 					if (this.form[item.aavName].indexOf(t4.id) == -1) {
					// 						t2.fieldName = t2.fieldName.split('(')[0]
					// 						t2.fieldName = t2.fieldName + '(' + t4
					// 							.fieldName + ')'
					// 					}
					// 				}
					// 			})
					// 		})
					// 	});
					// 	this.setValue(item)
					// }
					// }
				});
				// this.subForm()
			},

			change(e, fieldName, id) {
				this.subForm()
			},
			// 单选框点击
			radiochange(e, custom) {
				this.$set(this.form, custom.aavName + 'name', e.detail.data.fieldName)
				this.subForm()
				this.$nextTick(() => {
					this.$set(this.form, custom.aavName, e.detail.data.id)
					this.$refs.uForm1.validateField(custom.aavName)
				})

			},
			// 多选框点击
			checkboxchange(e, custom) {
				this.form[custom.aavName] = e.detail.value.join(',')
				let nameArr = []
				if (e.detail.data && e.detail.data.length) {
					e.detail.data.forEach(r => {
						nameArr.push(r.fieldName)
					})
				}
				this.$set(this.form, custom.aavName + 'name', nameArr.join(' '))
				this.subForm()
			},
			// 元神多选框点击
			boxradioCheckClick(checkItem, ind, custom) {
				if (this.boxradioIsShow == checkItem.id) {
					this.boxradioIsShow = ''
				} else {
					this.boxradioIsShow = checkItem.id
				}
				checkItem.ischeck = false
				this.form[custom.aavName + ind] = ''
				checkItem.fieldName = checkItem.fieldName.split('(')[0]
				this.setValue(custom)
			},
			// 元神单选框点击
			boxradioChange(e, checkItem, custom, ind) {
				//console.log(e, checkItem, custom, ind);
				this.form[custom.aavName + ind] = e
				this.boxradioIsShow = ''
				checkItem.ischeck = true
				checkItem.childGameAttributes.forEach(r => {
					if (r.id == e) {
						checkItem.fieldName = checkItem.fieldName.split('(')[0] + '(' + r.fieldName + ')'
						this.form[custom.aavName + ind + 'name'] = r.fieldName
					}
				})
				this.setValue(custom)
			},
			// 原神多选框赋值
			setValue(custom) {
				// 赋值
				let arr = []
				let nameArr = []
				custom.childAttributes.forEach((item, ind) => {
					if (item.ischeck) {
						// 多选框选中 取子级单选选中的id
						arr.push(this.form[custom.aavName + ind])
						// 多选框选中 取子级单选选中的名称
						nameArr.push(this.form[custom.aavName + ind + 'name'])
					}
				})
				if (arr.length) {
					this.form[custom.aavName] = arr.join(',')
					this.form[custom.aavName + 'name'] = nameArr.join(' ')
				} else {
					this.form[custom.aavName] = ''
					this.form[custom.aavName + 'name'] = ''
				}
				this.$forceUpdate()
				this.subForm()
			},
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			// 新增图片
			async afterRead(event, flag) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式

				let arr = event.file.map((item) => item.name)
				if (arr.join(' ').indexOf(',') > -1) {
					uni.showToast({
						title: '图片名称不能包含特殊字符',
						icon: 'none'
					})
					return
				}
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				this.isUploading = true
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i], flag)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result.data.filePath
					}))
					fileListLen++
				}
				this.isUploading = false
				// //console.log(this.fileList1)
			},
			uploadFilePromise(item, flag) {
				//console.log('item', item)
				if (flag) {
					return new Promise((resolve, reject) => {
						let a = uni.uploadFile({
							url: this.apiUrl + '/upload/image', // 图片上传接口
							filePath: item.url,
							name: 'file',
							formData: {},
							success: (res) => {
								setTimeout(() => {
									resolve(JSON.parse(res.data))
								}, 1000)
							}
						});
					})
				} else {
					return new Promise((resolve, reject) => {
						let a = uni.uploadFile({
							url: this.apiUrl + '/upload/image', // 图片上传接口
							filePath: item.url,
							name: 'file',
							formData: {},
							success: (res) => {
								setTimeout(() => {
									resolve(JSON.parse(res.data))
								}, 1000)
							}
						});
					})
				}

			},
			// 拖动上传
			ondrop(e, name) {
				let file = e.detail.files
				//console.log(e)
				let fileArr = []
				if (file && file.length) {
					for (let i = 0; i < file.length; i++) {
						fileArr.push({
							name: file[i].name,
							url: this.getTempFilePath(file[i])
						})
					}
				}
				let obj = {
					file: fileArr,
					name: name
				}
				this.afterRead(obj)
			},
			async getTempFilePath(file) {

				let path;
				if (window.createObjectURL) path = window.createObjectURL(file);
				else if (window.webkitURL) path = window.webkitURL.createObjectURL(file);
				else if (window.URL) path = window.URL.createObjectURL(file);
				else console.error('getTempFilePath has error');
				return path;
			},
			// 上一步
			preStep() {

				if (this.stepIndex == 2) {
					this.stepIndex = 1
				}
			},
			// 选择能否包赔
			sexSelect(val) {
				//console.log('选择的项', val)
				this.form.assurance = val.name
			},
			// 点击小问号
			showModel() {
				homeApi.gonggaoDetails({
					noticeType: 'bpzl'
				}).then(res => {
					if (res && res[0]) {
						this.contentBpzl = res[0].noticeContent
						this.showBpzl = true
					}
				})
			},
			submit() {
				// 填写完成后提交到后台
				this.$refs.uForm1.validate().then(res => {

					if (this.currentGame.name != '王者荣耀' && (!this.fileList2 || this.fileList2.length <
							2)) {
						return uni.showToast({
							title: "详情图请上传至少两张！",
							icon: 'none'
						})
					}
					if (this.currentGame.name != '王者荣耀' && (!this.fileList1.length)) {
						return uni.showToast({
							title: "请上传商品主图！",
							icon: 'none'
						})
					}
					let flag = false
					this.attrList.forEach(item => {
						if (item.fieldType == "checkbox" || item.fieldType == 'radio') {
							if (item.must == 'true' && (!this.form[item.aavName] || this.form[item
										.aavName]
									.length == 0)) {
								flag = true
								return uni.showToast({
									title: "请选择" + item.fieldName,
									icon: 'none'
								})
							}
						}
					});
					if (flag) {
						return
					}

					if (res) {
						if (this.isUploading) {
							uni.showToast({
								title: "正在上传图片...",
								icon: 'none'
							})
							return
						}
						//console.log('this.form', this.form)
						if (this.fileList2.length) {
							this.form.urlArr = JSON.parse(JSON.stringify(this.fileList2))
						}
						if (this.fileList1.length) {
							// 提取首图 this.fileList1
							this.form.titleImg = this.fileList1[0].url
						}
						if (this.fileList3.length) {
							// 提取实名截图
							this.form.shimingImg = this.fileList3[0].url
						} else {
							this.form.shimingImg = ''
						}
						this.form.gameType = this.currentGame.type

						// 配置重要信息备注
						this.form.importantRemark =
							`联系QQ：${this.form.qq};联系微信：${this.form.weixin};联系电话：${this.form.tel}`

						// if (this.form.jietubeizhu) {
						// 	this.form.importantRemark = `截图备注：${this.form.jietubeizhu};` + this.form
						// 		.importantRemark
						// }
						// zhanghao 
						huishouApi.addQThuishou(this.form).then(res => {
							if (res.code == 200) {
								this.content = '提交成功，请等待平台审核！'
								this.show = true
							} else {
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
							}
						})
					}
				}).catch(err => {
					//console.log('err', err)
					uni.showToast({
						title: err[0].message,
						icon: 'none'
					})
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.select-area-box {
		padding: 30rpx;

		view {
			font-size: 26rpx;
		}

		.item-box {
			background-color: #1C1632;
			padding: 30rpx;
			margin-bottom: 30rpx;
			border-radius: 20rpx;


			.select-item {
				display: flex;
				justify-content: center;
				// align-items: center;
				background-color: #f6f6f6;
				border-radius: 10rpx;
				// padding: 10rpx 50rpx;
				width: 30%;
				height: 50rpx;
				line-height: 50rpx;
				margin-right: 20rpx;
				margin-bottom: 10rpx;
				color: #666;

				&.active {
					background-color: #B42C1D;
					color: #fff;
				}
			}

		}





		.game-box {
			align-items: center;

			.game-img-name {
				align-items: center;

				.gamename {
					font-size: 30rpx;
					font-weight: 601;
					margin-left: 30rpx;
					color: #fff;
				}
			}

			.btn {
				background-color: #B42C1D;
				color: #fff;
				padding: 10rpx 20rpx;
				border-radius: 30rpx;
			}
		}

		.next-btn {
			width: 100%;
			background-color: #B42C1D;
			border: none;
			border-radius: 20rpx;
		}


	}

	.shangjiaBtn {
		width: 690rpx;
		margin: 0 auto;
		text-align: center;
		line-height: 50px;
		background-color: #B42C1D;
		color: #fff;
		border-radius: 20rpx;
		margin-top: 80rpx;
	}

	.tit-box {
		align-items: center;
		margin-bottom: 20rpx;

		.icon {
			width: 8rpx;
			height: 30rpx;
			background-color: #B42C1D;
			margin-right: 10rpx;
		}

		.tit {
			font-size: 30rpx;
			color: #fff;
		}
	}

	.account-info {
		background-color: #1C1632;
		margin-top: 30rpx;
		padding: 0 30rpx;
		padding-top: 30rpx;
	}

	.tip {
		color: #a5a5a5;
	}
	
	::v-deep .u-form-item__body__left__content__label{
		color: #fff !important;
	}
	
	/deep/ .uni-input-input {
		color: #fff;
	}
	
	.recovery-content {
		// min-height: 100vh;
		// max-width: 500px;
		// margin: 0 auto;
		background-color: #383B56;
	}

	.page-content {
		padding: 0 30rpx;
		// padding-bottom: 80rpx;
		// background: url(/static/recovery-bg.png)no-repeat;
		// background-size: 100% auto;
		box-sizing: border-box;
		margin-top: -30rpx;
		border-radius: 15rpx;

		// .recovery-header {
		// 	width: 100%;
		// 	padding: 32rpx;
		// 	background: #fff;
		// 	border-radius: 15rpx;
		// 	box-sizing: border-box;

		// }
		height: auto;

		.attr-list {
			background-color: #1C1632;
			// margin-top: 20rpx;
			padding: 0 30rpx 0 0;
			padding-top: 30rpx;

		}

	}

	.multiple-content {
		flex-wrap: nowrap;
		text-align: left;
		// min-height: 100rpx;
		// position: relative;
		// padding: 0 10rpx;

		.multiple-title {
			font-size: 30rpx;
			font-weight: bold;
			margin-top: 32rpx;
			color: #fff;
		}

		.multiple-list-content {
			margin-top: 10px;
			overflow: hidden;
			margin-left: -20rpx;
		}

		.multiple-list-content-visit {
			overflow: visible;
		}

		/deep/ .checklist-group {
			display: flex;
			text-align: center;
			justify-content: space-between;
		}

		/deep/ .checklist-box {
			flex-direction: row-reverse !important;
			margin-right: 22rpx !important;

			.checklist-text {
				width: 300rpx;
				font-size: 24rpx;
			}
		}

		/deep/ .checklist-box:nth-of-type(n) {
			margin-left: 90rpx !important;
		}

		/deep/ .checklist-box:nth-of-type(2n) {
			margin-right: 0 !important;
			margin-left: 50rpx !important;
		}


		/deep/ .checklist-text {
			// text-align: left;
			font-size: 24rpx !important;
			/* line-height: 15px; */
			background: #F6F6F6;
			border-radius: 10rpx 10rpx 10rpx 10rpx;
			opacity: 1;
			/* border: 1px solid #DDDDDD; */
			line-height: 56rpx !important;
			padding: 0rpx 20rpx;
			color: #666 !important;
			height: 60rpx;
			// border: 1px solid #000;
			border-right: none;
			margin-left: 0px !important;
			box-sizing: border-box;
			overflow: hidden;
		}

		/deep/ .checkbox__inner {
			height: 60rpx !important;
			width: 60rpx !important;
			background-color: #fff !important;
			border-radius: 0px 10rpx 10rpx 0px !important;
			display: none;
		}

		// /deep/ .checkbox__inner-icon {
		// 	width: 16rpx !important;
		// 	height: 32rpx !important;
		// 	top: 6rpx !important;
		// 	left: 20rpx !important;
		// 	border-right-color: #40CBEB !important;
		// 	border-bottom-color: #40CBEB !important;
		// }

		/deep/ .is-checked {
			.checklist-text {
				color: #fff !important;
				background: #B42C1D;
			}

			// .checkbox__inner {
			// 	border-color: #DCDFE6 !important;
			// }
		}

	}

	/deep/ .u-textarea {
		background-color: #fff;
		border-color: #3c9cff;
	}




	.attr-input-content {
		/deep/ .u-form-item__body__right__content {
			width: 94%;
		}

	}

	.input-ml {
		padding-left: 32rpx;
	}

	.merge-sub {
		width: 150rpx;
		height: 78rpx;
		background: #469dfd;
		border-color: #469dfd;
		// border-radius: 15rpx;
		font-size: 30rpx;
		font-weight: 500;
		color: #FFFFFF;
		line-height: 76rpx;
		text-align: center;
		margin: 0 auto;
	}

	.width-w {
		width: 300rpx;
		margin-bottom: 50rpx;
	}


	.input-btn {
		width: 100%;
		display: flex;
	}

	.radio-label {
		margin-right: 40rpx;
	}


	.topBar {
		height: 90rpx;
		background-color: transparent;
	}

	.drag-box {
		width: 100%;
		padding: 20px;
		border: 1px dashed #ccc;
		border-radius: 8px;
		background-color: #fff;

	}

	.drag-box:hover {
		border-color: #3c9cff;
		cursor: pointer;
	}

	.drag-box ::v-deep .u-upload__button {
		width: 200px !important;
		height: 110px !important;
	}

	.drag-box ::v-deep .u-upload__button__text {
		font-size: 14px;
	}

	.drag-box ::v-deep .u-upload__wrap__preview__image {
		height: 110px !important;
	}

	.boxradioRadioBox {
		z-index: 9999999;
		min-width: 100px;
		min-height: 100rpx;
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

	.sign-item {
		width: 290rpx;
		flex-shrink: 0;
		display: inline-block;
		text-align: center;
		border-radius: 15rpx;
		margin-right: 20rpx;
		margin-bottom: 20rpx;
		font-size: 26rpx;
		background: #F3F5F9;
		border: 1px solid #F3F5F9;
		padding: 5px 0;
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
		}

		// &:nth-of-type(3n) {
		// 	margin-right: 0;
		// }

		&.active {
			background: #469dfd;
			border: 1px solid #469dfd;

			view {
				color: #fff !important;
			}

		}

	}


	//endif 
	.price-tip {
		padding-bottom: 30rpx;

		flex-shrink: 0
	}

	// ::v-deep .checklist-box {
	// 	width: 350rpx;
	// }
	::v-deep .checklist-box .checklist-content .checklist-text {
		// font-size: 24rpx;
		padding: 0 0 0 10rpx;
		// overflow: hidden;
	}

	::v-deep .checklist-group .checklist-box {
		width: 36%;
	}

	::v-deep .cont-box .u-form-item__body {
		align-items: flex-start;
	}

	::v-deep .page-content .u-form-item__body__left {
		padding-top: 10rpx;
		align-items: flex-start;
	}

	.sm-box {
		margin-top: 20rpx;
	}

	::v-deep .sm-box .u-upload__wrap {
		width: 200rpx;
	}

	::v-deep .sm-box .u-upload {
		width: 200rpx !important;
		display: block;
		margin-right: 20rpx;
		flex: 0;
	}

	::v-deep .sm-box .u-upload__button {
		width: 200rpx !important;
		height: 200rpx !important;
	}

	::v-deep .sm-box .u-upload__wrap__preview,
	::v-deep .sm-box .u-upload__wrap__preview .u-upload__wrap__preview__image {
		width: 200rpx !important;
		height: 200rpx !important;
	}

	.sm_btn {
		width: 200rpx;
		height: 200rpx;
		// background-color: #3c9cff;
		background-image: url('/static/feiheng/sm-btn.png');
		background-size: 100% 100%;
	}

	.slot-content {
		height: calc(100vh - 400rpx);
		overflow-y: auto;

		/deep/ img {
			width: 100%;

		}
	}

	.textright /deep/ .uni-input-input {
		text-align: right;
	}
	
	::v-deep .u-form-item__body__right__content__slot{
		color: #fff !important;
	}
</style>