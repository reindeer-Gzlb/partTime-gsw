<template>
	<view class="main" :style="{minHeight:gao + 'px'}" style="background-color: #383B56;">
		<view class="cooperate">
			<view class="img" v-html="item.noticeContent">

			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				item: {},
				gao:''
			}
		},
		onShow() {
			let obj = uni.getStorageSync('gao')
			this.gao = obj - 44
		},
		onLoad(options) {
			// let res = JSON.parse(options.banners);
			//      //console.log(res); //
			//console.log('name', uni.getStorageSync('namessss'));
			this.item = uni.getStorageSync('namessss')
		},
		methods: {
			getInfo() {

			}
		},
	}
</script>

<style>
</style>