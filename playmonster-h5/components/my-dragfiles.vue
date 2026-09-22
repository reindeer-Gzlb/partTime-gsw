<template>
	<view class="my-drag-files" id="my-drag-files">
		<slot></slot>
	</view>
</template>
<script>
	export default {
		name: "my-drag-files",
		props:['index'],
		data() {
			return {};
		},
		mounted() {
			//添加监听
			let elem = document.getElementById('my-drag-files');
			elem.addEventListener('drop', this.ondrop, false);
			elem.addEventListener('dragleave', this.ondropprevent);
			elem.addEventListener('dragenter', this.ondropprevent);
			elem.addEventListener('dragover', this.ondropprevent);
			// 监听粘贴
			// document.addEventListener('paste',
			elem.addEventListener('paste', this.onPaste, false);
			this.elem = elem;
		},
		// 销毁时
		beforeDestroy() {
			const {
				elem
			} = this;
			//移除监听
			elem.removeEventListener('drop', this.ondrop);
			elem.removeEventListener('dragleave', this.ondropprevent);
			elem.removeEventListener('dragenter', this.ondropprevent);
			elem.removeEventListener('dragover', this.ondropprevent);
			// 移除粘贴监听
			elem.removeEventListener('paste', this.onPaste);
		},
		methods: {
			//...
			ondrop(event) {
				this.ondropprevent(event);
				this.$emit('drop', {
					detail: {
						files: event.dataTransfer.files
					},
					currentTarget: {
						dataset: {}
					}
				});
			},
			ondropprevent(event) {
				event.stopPropagation();
				event.preventDefault();
			},
			onPaste(e){
				console.log('粘贴')
				// DataTransferItemList
				var isChrome = false;
				        if (event.clipboardData || event.originalEvent) {
				            //某些chrome版本使用的是event.originalEvent
				            var clipboardData = (event.clipboardData || event.originalEvent.clipboardData);
				            if(clipboardData.items){
				                // for chrome
				                var  items = clipboardData.items,
				                    len = items.length,
				                    blob = null;
				                isChrome = true;
								let arr = []
				                for (var i = 0; i < len; i++) {
				                    console.log(items[i]);
				                    if (items[i].type.indexOf("image") !== -1) {
				                        //getAsFile() 此方法只是living standard firefox ie11 并不支持
				                        blob = items[i].getAsFile();
										arr.push(blob)
				                    }
				                }
								console.log('文件列表')
								console.log(arr)
								this.$emit('drop', {
									detail: {
										files: arr
									},
									currentTarget: {
										dataset: {}
									}
								});
				            }
				        }
			}
		}
	}
</script>
<style lang="scss" scoped>
	.my-drag-files {
		height: 100%;
	}
</style>
