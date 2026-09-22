<template>
	<view class="main">
		<view class="cooperate">
			<view class="img" v-html="obj.noticeContent">

			</view>
		</view>
	</view>
</template>

<script>
	import homeApi from '../../api/home/index.js'
	export default {
		data() {
			return {
				noticeType: 'yinsizhengce',
				obj: {}
			}
		},
		onLoad() {
			this.getbangzhuInfo()
		},
		methods: {
			async getbangzhuInfo() {
				const res = await homeApi.jiaoyixuzhi({
					noticeType: this.noticeType
				})
				 //console.log(res);
				this.obj = res[0]
			}
		},
	}
</script>

<style scoped>
	/deep/ .ql-size-small {
		font-size: 12px !important;
	}

	/deep/ .ql-size-large {
		font-size: 18px !important;
	}

	/deep/ .ql-size-huge {
		font-size: 32px !important;
	}

	/deep/ .ql-align-center {
		text-align: center !important;
	}
</style>