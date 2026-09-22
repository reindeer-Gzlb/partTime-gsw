<template>
	<view class="main">
		<view class="cooperate">
			<view class="img" style="text-align: center;" v-html="obj.noticeContent">
				
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				obj: {}
			}
		},
		onLoad(options) {
			let obj = JSON.parse(options.obj);
			 //console.log(obj);
			this.obj = obj
			uni.setNavigationBarTitle({
				title:this.obj.noticeTitle
			})
		},
	}
</script>

<style>
</style>