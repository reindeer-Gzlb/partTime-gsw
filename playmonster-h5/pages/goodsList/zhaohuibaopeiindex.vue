<template>
	<view class="main" :style="{minHeight:gao + 'px'}" style="background-color: #383B56;">
		<view class="nav">
			<view>
				<image class="imgs" src="/static/taohaoqu/zhaohuibaopei.png" mode="widthFix"></image>
			</view>
		</view>
		<view class="tabs" style="margin-top: 30rpx;border-bottom: 1px solid #dbdbdb;">
			<u-tabs lineWidth="50" lineColor="#B42C1D" :activeStyle="{
			             color: '#fff',
			             fontWeight: 'bold',
			             transform: 'scale(1.05)'
			         }" :inactiveStyle="{
			             color: '#95989e',
			             transform: 'scale(1)'
			         }" itemStyle="padding-left: 50px; padding-right: 50px;height: 34px;padding-bottom:20px;" :list="list1"
				@click="click"></u-tabs>
		</view>
		<view class="box" v-for="item in list" :key="item.id">
			<view>
				<image class="imgs" :src="item.titleImg" mode=""></image>
			</view>
			<view class="titless">
				{{item.noticeTitle}}
			</view>
			<view class="Timesr">
				{{item.createTime}}
			</view>
			<view class="" @click="tiaozhuana(item)">
				<view class="details">
					查询详情
				</view>
				<view class="icons">
					<image style="width: 32rpx;height: 32rpx;position: absolute;top: 76.5%;right: 3.9%;color: #999999;"
						src="/static/guaishouwan/jiantou11.png" mode=""></image>
				</view>
			</view>
		</view>
		<!-- <view class="box2" v-if="type == 2">
			22222
		</view> -->
	</view>
</template>

<script>
	import homeApi from '../../api/home/index.js'
	export default {
		data() {
			return {
				type: 0,
				list: [],
				noticeType: 'lassws',
				noticeTypes: 'djzhal',
				obj: {},
				list1: [{
					name: '打击找回案例',
					value: '1',
				}, {
					name: '立案/诉讼文书',
					value: '2',
				}],
				gao: ""
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad() {
			this.getlist()
		},
		methods: {
			tiaozhuana(item) {
				//console.log(item);
				uni.navigateTo({
					url: '/pages/goodsList/anlidetails',
				})
				uni.setStorageSync('namessss', item)
				//  //console.log('name', uni.getStorageSync('namessss'));
			},
			async getlist() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.noticeTypes
				})
				//console.log(res);
				this.list = res
			},
			async click(item) {
				//console.log('item', item);
				this.type = item.value
				if (item.value == 2) {
					const res = await homeApi.jiaoyixuzhi({
						noticeType: this.noticeType
					})
					//console.log(res);
					this.list = res
				} else if (item.value == 1) {
					this.getlist()
				}
			}
		},
	}
</script>

<style lang="less" scoped>
	.main {

		// width: 100%;
		.imgs {
			width: 100%;
			height: 416rpx;
		}

		.box {
			width: 100%;
			padding: 20rpx 30rpx;
			border-bottom: 1px solid #b6b6b6;
			position: relative;

			.imgs {
				width: 210rpx;
				height: 150rpx;
				border-radius: 20rpx;
			}

			.titless {
				position: absolute;
				top: 10%;
				left: 35%;
				right: 2%;
				font-size: 28rpx;
				font-weight: 700;
				overflow: hidden;
				display: -webkit-box;
				-webkit-box-orient: vertical;
				-webkit-line-clamp: 3;
				color: #fff;
			}

			.Timesr {
				position: absolute;
				top: 75%;
				left: 35%;
				color: #d3d3d3;
			}

			.details {
				position: absolute;
				top: 75%;
				right: 8%;
				color: #d3d3d3;
			}
		}
	}
</style>