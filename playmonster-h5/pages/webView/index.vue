<template>
	<view class="mian">

		<web-view v-if="str" :webview-styles="webviewStyles" :src="str"></web-view>


	</view>

</template>
<script>
	export default {
		data() {
			return {
				str: '',
				webviewStyles: {
					progress: {
						color: '#0095ff'
					}
				}
			}
		},
		onLoad(option) {
			this.str = option.src
		},
		methods: {

		}
	}
</script>

<style lang="scss" scoped>

</style>