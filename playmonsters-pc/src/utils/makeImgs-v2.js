/*首图传参示例：
参数1：
{areaName: null
campId: "2139980403"
detailsImg: 0
heroNum: "4"
input37490: ""
inscriptions: "7"
nobNum: "67"
roleId: "2016312283"
rongyaoCount: "0"
shengbiao: "0"
shiming: 1
shuijingnum: "0"
skinNum: "3"
textImg: 1
type: "王者荣耀"
vipLevel: "5"
xingyung: "9"
yinji: "8"
zijing: "8"}

参数2：
[
	{
	    "fieldName": "时之愿境",
	    "sort": -1,
	    "imgUrl": "https://pvppic.kohsocialapp.qq.com/wzzs_pic/0/77cb0b36c6b99c0a95269a901b6d89f7/0",
	}
]

**详情图传参只接受list，数据格式同首图参数2

*/

// 加载图片
function getURLBase64(url) {
	return new Promise((resolve, reject) => {
		var xhr = new XMLHttpRequest();
		xhr.open("get", url, true);
		xhr.responseType = "blob";
		xhr.onload = function() {
			if (this.status === 200) {
				var blob = this.response;
				var fileReader = new FileReader();
				fileReader.onloadend = function(e) {
					var result = e.target.result;
					resolve(result);
				};
				fileReader.readAsDataURL(blob);
			}
		};
		xhr.onerror = function(err) {
			reject(err);
		};
		xhr.send();
	});
}

// 生成详情图
function makeDetailImg(skins) {
	return new Promise((resolve, reject) => {
		let instancesUrl = skins;
		console.log('皮肤列表', skins)
		const length = instancesUrl.length;
		let instances = [];
		// file.szBigIcon
		instancesUrl.forEach((file, index) => {
			getURLBase64(file.imgUrl).then((res) => {
				const image = new Image();
				image.src = res;
				image.onload = () => {
					// 图片实例化成功后存起来
					instances[index] = {
						image: image,
						name: file.fieldName,
					};
					if (index+1 === length) {
            // 
						resolve(drawImages(instances))
					}
				};
			}).catch(err=>{
        console.log('%c 🍶 err: ', 'font-size:20px;background-color: #6EC1C2;color:#fff;', err);

      })
		});
	})
}
// 写入图片
function drawImages(images) {
	return new Promise((resolve, reject) => {
		const canvas = document.createElement("canvas");
		const context = canvas.getContext("2d");
		// 21个皮肤为一组
		let num = parseInt(images.length / 21) + 1; //一共有多少组
		console.log(num);
		canvas.width = 1737;
		canvas.height = 920 * num;
		context.fillStyle = "#fff"; // 文字填充颜色
		context.font = "26px Adobe Ming Std";
		let initWith = 158;
		let height = 210;
		// 获取背景图
		// let bgImg
		getURLBase64("https://game.ikbh.top/logo/back-img.jpg").then((res) => {
			const image = new Image();
			image.src = res;
			image.onload = () => {
				// 图片实例化成功后存起来
				let bgImg = image;
				let setH = 0;
				let pages = [];
				for (let i = 0; i < num; i++) {
					pages[i] = images.slice(i * 21, i * 21 + 21);
				}
				pages.forEach((page, k) => {
					context.drawImage(bgImg, 0, setH, 1737, 889);
					let i = 0;
					let line = 1;
					let y = 80 + 210 + 15 + k * 920;
					let imgy = 60 + k * 920;
					page.forEach((item) => {
						if (i == 0) {
							context.drawImage(item.image, 150, imgy, initWith,
								height);
							context.fillText(item.name, initWith / 10 + 150, y);
						} else {
							context.drawImage(
								item.image,
								initWith * i + 150 + 60 * i,
								imgy,
								initWith,
								height
							);
							context.fillText(
								item.name,
								(initWith / 10) * (i * 4 - 1) + 30 + 150 +
								initWith * i,
								y
							);
						}
						i++;
						if (i != 0 && i % 7 == 0) {
							if (line == 1) {
								imgy = 80 + height * line + 30 + k * 920;
								y = 80 + height * 2 + 60 + k * 920;
							} else if (line == 2) {
								imgy = 80 + height * line + 40 * line + k * 920;
								y = 80 + height * (line + 1) + 55 * line + k * 920;
							} else if (line == 3) {
								imgy = 80 + height * line + 700 + k * 920;
								y = 80 + height * (line + 1) + 70 + k * 920;
							}
							line++;
							i = 0;
						}
					});
					setH += 920;
				});
        const base64Url = canvas.toDataURL("image/jpeg", 0.2);
        // let img = base64ToBlob(base64Url)
          console.log('%c 🍏 详情图: ', 'font-size:20px;background-color: #465975;color:#fff;', base64Url);
					resolve(base64Url)
			}
		}).catch(err => {
      console.log('%c 🍑 err: ', 'font-size:20px;background-color: #33A5FF;color:#fff;', err);
			reject(err)
		})
	});
}
// 图片转换
function base64ToBlob(code) {
  let parts = code.split(";base64,");
  let contentType = parts[0].split(":")[1];
  let raw = window.atob(parts[1]);
  let rawLength = raw.length;
  let uInt8Array = new Uint8Array(rawLength);

  for (let i = 0; i < rawLength; ++i) {
    uInt8Array[i] = raw.charCodeAt(i);
  }
  return new Blob([uInt8Array], { type: contentType });
}
// 生成首图
function makeTitleImg(data, list) {
	console.log('首图数据', data)
	let arr = JSON.parse(JSON.stringify(list))
	return new Promise((resolve, reject) => {
		if (!arr || arr == []) {
			reject('皮肤列表不能为空')
		}
		getURLBase64("https://game.ikbh.top/logo/yjwjjj.jpg")
			.then((res) => {
				const imageaaa = new Image();
				imageaaa.src = res;
				imageaaa.onload = () => {
					const canvas = document.createElement("canvas");
					const context = canvas.getContext("2d");
					canvas.width = imageaaa.width;
					canvas.height = imageaaa.height;
					context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
					let titlName = `区服 ${data.areaName||''}(贵族 V${data.vipLevel||''})`;
					context.fillStyle = "#ffff00";
					context.font = "normal bold 135px Adobe Ming Std";
					context.fillText(titlName, 842, 280);
					context.font = "100px Adobe Ming Std";
					context.fillStyle = "#ffff00";
					context.fillText(`英雄数量 ${data.heroNum||''}`, 200, 522);
					context.fillText(`皮肤数量 `, 200, 700);
					context.fillText(`贵族等级 `, 1245, 700);
					context.fillText(`水晶数量 `, 2050, 700);
					// 红色字体
					context.fillStyle = "#ff0000";
					context.fillText(`${data.skinNum||''}`, 640, 700);
					context.fillText(`${data.vipLevel||''}`, 1680, 700);
					context.fillText(`${data.shuijingnum||''}`, 2475, 700);
					context.fillText(`贵族积分 ${data.inscriptions||''}`, 2050, 522);
					context.fillText(`五级铭文 ${data.nobNum||''}`, 1245, 522);
					// 白色字体
					context.fillStyle = "#fff";
					context.fillText(`王者印记 ${data.yinji||''}`, 200, 900);
					context.fillText(` ${data.shiming==1?'可实名':'包人脸'}`, 2150, 900);
					context.fillText(`紫晶币 ${data.zijing||''}`, 200, 2300);
					context.fillText(`幸运值 ${data.xingyung||''}`, 2300, 2300);
					// 底部统计
					context.fillStyle = "#ffff00";
					context.fillText(
						`MVP次数 ${data.mvpNum||''} 游戏场次 ${data.totalCount||''} 战斗力 ${data.zhandouli||''}`, 522,
						2520);
					context.fillText(`省标 ${data.shengbiao||''} `, 1350, 2650);
					// 皮肤字体
					context.font = "normal bold 80px Adobe Ming Std";
					let skin = arr.splice(0, 32);
					var x1 = 200;
					var h = 920;
					skin.map((item, index) => {
						if (index < 32) {
							if (item.sort == -1) {
								context.fillStyle = "#00FFD9";
							}else
							if (item.sort === 0) {
								context.fillStyle = "#E6FF00";
							}else{
								context.fillStyle = "#ffff00";
							}
							if (index % 4 == 0) {
								x1 = 200;
								//控制行高
								h = h + 150;
							}
							context.fillText(item.fieldName, x1, h);
							//控制列间隔
							x1 = x1 + 700;
						}
					});
          const base64Url = canvas.toDataURL("image/jpeg", 0.3);
          console.log('%c 🍏 base64Url: ', 'font-size:20px;background-color: #465975;color:#fff;', base64Url);
					resolve(base64Url);
				};
			})
			.catch((err) => {
				reject(err);
			});
	});
}
let obj = {
	makeDetailImg,
	makeTitleImg
}
export default obj
