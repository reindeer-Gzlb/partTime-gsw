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
		instancesUrl.forEach(async (file, index) => {
			if (file.imgUrl) {
				let res = await getURLBase64(file.imgUrl)
				const image = new Image();
				image.src = res;
				image.onload = () => {
					// 图片实例化成功后存起来
					instances.push({
						image: image,
						name: file.fieldName,
					})
					if (index + 1 >= length) {
						resolve(drawImages(instances))
					}
				};
			}
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
    if(images.length%21==0){
      num = num-1
    }
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
          console.log("%c Line:114 🍉 pages[i]", "color:#ffdd4d", pages[0]);

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
          console.log('%c 🍏 详情图: ', 'font-size:20px;background-color: #465975;color:#fff;', base64Url);
					resolve(base64Url)
			}
		}).catch(err => {
      console.log('%c 🍑 err: ', 'font-size:20px;background-color: #33A5FF;color:#fff;', err);
			reject(err)
		})
	});
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
					let titlName = `区服 ${data.areaName||'--'}(贵族 V${data.vipLevel||'--'})`;
					context.fillStyle = "#ffff00";
					context.font = "normal bold 135px Adobe Ming Std";
					context.fillText(titlName, 842, 280);
					context.font = "100px Adobe Ming Std";
					context.fillStyle = "#ffff00";
					context.fillText(`英雄数量 ${data.heroNum||'--'}`, 200, 522);
					context.fillText(`皮肤数量 `, 200, 700);
					context.fillText(`贵族等级 `, 1245, 700);
					context.fillText(`水晶数量 `, 2050, 700);
					// 红色字体
					context.fillStyle = "#ff0000";
					context.fillText(`${data.skinNum||'--'}`, 640, 700);
					context.fillText(`${data.vipLevel||'--'}`, 1680, 700);
					context.fillText(`${data.shuijingnum||'--'}`, 2475, 700);
					context.fillText(`贵族积分 ${data.nobNum||'--'}`, 2050, 522);
					context.fillText(`五级铭文 ${data.inscriptions||'--'}`, 1245, 522);
					// 白色字体
					context.fillStyle = "#fff";
					context.fillText(`王者印记 ${data.yinji||'--'}`, 200, 900);
					context.fillText(` ${data.shiming==1?'可实名':'包人脸'}`, 2150, 900);
					context.fillText(`紫晶币 ${data.zijing||'--'}`, 200, 2300);
					context.fillText(`幸运值 ${data.xingyung||'--'}`, 2300, 2300);
					// 底部统计
					context.fillStyle = "#ffff00";
					context.fillText(
						`MVP次数 ${data.mvpNum||'--'} 游戏场次 ${data.totalCount||'--'} 战斗力 ${data.zhandouli||'--'}`, 522,
						2520);
					context.fillText(`省标 ${data.shengbiao||'--'} `, 1350, 2650);
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
// 生成长方形首图
function makeTitleImgChang(data, list) {
	
	let arr = JSON.parse(JSON.stringify(list))
	return new Promise((resolve, reject) => {
		if (!arr || arr == []) {
			reject('皮肤列表不能为空')
		}
		getURLBase64("https://game.ikbh.top/logo/back-img.jpg")
			.then((res) => {
				const imageaaa = new Image();
				imageaaa.src = res;
				imageaaa.onload = () => {
					const canvas = document.createElement("canvas");
					const context = canvas.getContext("2d");
					canvas.width = imageaaa.width;
					canvas.height = imageaaa.height;
					context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
					let titlName = `区服 ${data.areaName||'--'}(贵族 V${data.vipLevel||'--'})`;
					context.fillStyle = "#ffff00";
					context.font = "normal bold 36px Adobe Ming Std";
					context.fillText(titlName, 700, 110);
					context.font = "38px Adobe Ming Std";
					context.fillStyle = "#ffff00";
					context.fillText(`英雄数量 ${data.heroNum||'--'}`, 200, 201);
					context.fillText(`皮肤数量 `, 200, 251);
					context.fillText(`贵族等级 `, 550, 251);
					context.fillText(`水晶数量 `, 900, 251);
					// 红色字体
					context.fillStyle = "#ff0000";
					context.fillText(`${data.skinNum||'--'}`, 360, 251);
					context.fillText(`${data.vipLevel||'--'}`, 710, 251);
					context.fillText(`${data.shuijingnum||'--'}`, 1060, 251);
					context.fillText(`贵族积分 ${data.nobNum||'--'}`, 900, 201);
					context.fillText(`五级铭文 ${data.inscriptions||'--'}`, 550, 201);
					// 白色字体
					context.fillStyle = "#fff";
					context.fillText(`王者印记 ${data.yinji||'--'}`, 1250, 201);
					context.fillText(` ${data.shiming==1?'可实名':'包人脸'}`, 1250, 251);
					context.fillText(`紫晶币 ${data.zijing||'--'}`, 200, 301);
					context.fillText(`幸运值 ${data.xingyung||'--'}`, 1250, 301);
					// 底部统计
					context.fillStyle = "#ffff00";
					context.fillText(
						`MVP次数 ${data.mvpNum||'--'} 游戏场次 ${data.totalCount||'--'} 战斗力 ${data.zhandouli||'--'}省标 ${data.shengbiao||'--'}`, 450,
						782);
					// context.fillText(`省标 ${data.shengbiao||''} `, 800, 801);
					// 皮肤字体
					context.font = "normal bold 33px Adobe Ming Std";
					let skin = arr.splice(0, 35);
					var x1 = 200;
					var h = 321;
					skin.map((item, index) => {
						if (index < 35) {
							if (item.sort == -1) {
								context.fillStyle = "#00FFD9";
							}else
							if (item.sort === 0) {
								context.fillStyle = "#E6FF00";
							}else{
								context.fillStyle = "#ffff00";
							}
							if (index % 5 == 0) {
								x1 = 200;
								//控制行高
								h = h + 55;
							}
							context.fillText(item.fieldName, x1, h);
							//控制列间隔
							x1 = x1 + 270;
						}
					});
          const base64Url = canvas.toDataURL("image/jpeg", 0.3);
          
					resolve(base64Url);
				};
			})
			.catch((err) => {
				reject(err);
			});
	});
}
// 生成其他游戏首图
function makeTitleImgYjwj(data, list,attrs) {
  console.log("%c Line:338 🥖 attrs", "color:#e41a6a", attrs);
	console.log('其他游戏首图', data)
  let keys = attrs.filter(i=>{
    return i.fieldType=='input'||i.fieldType=='number'||i.fieldType=='radio'||i.fieldType=='regional'
  })

	let listArr = JSON.parse(JSON.stringify(list))
  console.log("%c Line:345 🥒 listArr", "color:#42b983", listArr);
  let arr=[]
  
	return new Promise((resolve, reject) => {
		if (!listArr || listArr == []) {
			reject('多选不能为空')
		}
    listArr.forEach(obj=>{
      if(Array.isArray(obj.list)){
        console.log("%c Line:354 🍔 obj.list", "color:#ffdd4d", obj.list);
        arr = arr.concat(obj.list)
      }
    })
    console.log("%c Line:341 🥟 arr", "color:#f5ce50", arr);
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
          /**英雄数量改 红皮数量
            皮肤数量改 金皮数量 
            五级铭文改 天赋等级 
            贵族积分改 幻丝数量 
            贵族等级改 段位 
            水晶数量改 编号  */
          let titlName = `${data.type}`;
					context.fillStyle = "#ffff00";
					context.font = "normal bold 135px Adobe Ming Std";
					context.fillText(titlName, 1200, 280);
					context.font = "100px Adobe Ming Std";
					context.fillStyle = "#ffff00";
          if(keys[0]&&data[keys[0].remark]!='qita'){
            context.fillText(`${keys[0]?keys[0].fieldName:''} ${keys[0]?data[keys[0].remark]:'--'}`, 200, 522);
          }
          if(keys[1]&&data[keys[1].remark]!='qita'){
            context.fillText(`${keys[1]?keys[1].fieldName:''} `, 200, 700);
            context.fillStyle = "#ff0000";
					  context.fillText(`${keys[1]?data[keys[1].remark]:'--'}`, 640, 700);
          }
					if(keys[2]&&data[keys[2].remark]!='qita'){
            context.fillStyle = "#ffff00";
            context.fillText(`${keys[2]?keys[2].fieldName:''} `, 1245, 700);
            context.fillStyle = "#ff0000";
					  context.fillText(`${keys[2]?data[keys[2].remark]:'--'}`, 1680, 700);
          }
          if(keys[3]&&data[keys[3].remark]!='qita'){
            context.fillStyle = "#ffff00";
            context.fillText(`${keys[3]?keys[3].fieldName:''} `, 2050, 700);
            context.fillStyle = "#ff0000";
					  context.fillText(`${keys[3]?data[keys[3].remark]:'--'}`, 2475, 700);
          }
					// 红色字体
					context.fillStyle = "#ff0000";
          if(keys[4]&&data[keys[4].remark]!='qita'){
            context.fillText(`${keys[4]?keys[4].fieldName:''} ${keys[4]?data[keys[4].remark]:'--'}`, 2050, 522);
          }
          if(keys[5]&&data[keys[5].remark]!='qita'){
            context.fillText(`${keys[5]?keys[5].fieldName:''} ${keys[5]?data[keys[5].remark]:'--'}`, 1245, 522);
          }
					// 白色字体
					// context.fillStyle = "#fff";
					// 底部统计
					context.fillStyle = "#ffff00";
          if(data.qita){
            context.fillText(
              `${data.qita||'--'} `, (3000-(data.qita?data.qita.length:0)*100)/2,
              2520);
          }
					
					// 皮肤字体
					context.font = "normal bold 100px Adobe Ming Std";
					let skin = arr.splice(0, 32);
					var x1 = 200;
					var h = 920;
					skin.map((item, index) => {
						if (index < 32) {
							// if (item.sort == -1) {
							// 	context.fillStyle = "#00FFD9";
							// }else
							// if (item.sort === 0) {
							// 	context.fillStyle = "#E6FF00";
							// }else{
							// 	context.fillStyle = "#ffff00";
							// }
							if (index % 4 == 0) {
								x1 = 200;
								//控制行高
								h = h + 150;
							}
							context.fillText(item, x1, h);
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
	makeTitleImg,
  makeTitleImgChang,
  makeTitleImgYjwj
}
export default obj
