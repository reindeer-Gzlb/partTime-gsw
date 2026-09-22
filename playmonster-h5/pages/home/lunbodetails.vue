<template>
	<view class="main">
		<view class="cooperate">
			<view class="img" v-html="obj.content">
				
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
				title:this.obj.name
			})
		},
	}
</script>

<style>
</style>