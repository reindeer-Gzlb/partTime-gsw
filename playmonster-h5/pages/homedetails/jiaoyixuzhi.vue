<template>
	<view class="cooperate" style="background-color: #383b56;">
		<view class="img" v-html="obj.noticeContent">
			
		</view>
	</view>
</template>

<script>
	import homeApi from "../../api/home/index.js"
	export default {
		data() {
			return {
				obj:{},
				noticeType:'jyxz'
			}
		},
		onLoad() {
			this.getInfo()
		},
		methods: {
		 async	getInfo(){
			const res =await homeApi.jiaoyixuzhi({noticeType:this.noticeType})
			 //console.log(res);
			this.obj = res[0]
			}
		}
	}
</script>

<style scoped>
	/* /deep/ strong{
		color: #fff;
	}
	
	/deep/ span{
		color: #fff !important;
	} */
</style>
