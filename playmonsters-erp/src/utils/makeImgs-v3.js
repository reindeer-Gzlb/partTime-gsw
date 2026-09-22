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
参数3：自定义字段的属性详情

**详情图传参只接受list，数据格式同首图参数2

*/

// 加载图片
function getURLBase64(url) {
  return new Promise((resolve, reject) => {
    var xhr = new XMLHttpRequest();
    xhr.open("get", url, true);
    xhr.responseType = "blob";
    xhr.onload = function () {
      if (this.status === 200) {
        var blob = this.response;
        var fileReader = new FileReader();
        fileReader.onloadend = function (e) {
          var result = e.target.result;
          resolve(result);
        };
        fileReader.readAsDataURL(blob);
      }
    };
    xhr.onerror = function (err) {
      reject(err);
    };
    xhr.send();
  });
}

// 生成详情图
function makeDetailImg(skins) {
  return new Promise((resolve, reject) => {
    let instancesUrl = skins;
    console.log("皮肤列表", skins);
    const length = instancesUrl.length;
    let instances = [];
    // file.szBigIcon
    instancesUrl.forEach(async (file, index) => {
      // if (file.imgUrl) {
      let res = await getURLBase64(file.imgUrl);
      const image = new Image();
      image.src = res;
      image.onload = async () => {
        // 图片实例化成功后存起来
        instances.push({
          image: image,
          name: file.fieldName,
        });
        if (index + 1 >= length) {
          resolve(drawImages(instances));
        }
      };
      image.onerror = async () => {
        if (index + 1 >= length) {
          resolve(drawImages(instances));
        }
      };
      // }
    });
  });
}
// 写入图片
function drawImages(images) {
  console.log("%c Line:94 🥓 images", "color:#ffdd4d", images);
  return new Promise((resolve, reject) => {
    const canvas = document.createElement("canvas");
    const context = canvas.getContext("2d");
    // 21个皮肤为一组
    let num = parseInt(images.length / 21) + 1; //一共有多少组
    if (images.length % 21 == 0) {
      num = num - 1;
    }
    canvas.width = 1737;
    canvas.height = 920 * num;
    context.fillStyle = "#fff"; // 文字填充颜色
    context.font = "24px Adobe Ming Std";
    let initWith = 158;
    let height = 210;
    // 获取背景图
    // let bgImg
    getURLBase64("https://game.ikbh.top/logo/back-img.jpg")
      .then((res) => {
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
                context.drawImage(item.image, 150, imgy, initWith, height);
                context.fillText(item.name, 150, y);
              } else {
                context.drawImage(
                  item.image,
                  initWith * i + 150 + 60 * i,
                  imgy,
                  initWith,
                  height
                );
                context.fillText(item.name, initWith * i + 150 + 60 * i, y);
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
          console.log(
            "%c 🍏 详情图: ",
            "font-size:20px;background-color: #465975;color:#fff;",
            base64Url
          );
          resolve(base64Url);
        };
      })
      .catch((err) => {
        console.log(
          "%c 🍑 err: ",
          "font-size:20px;background-color: #33A5FF;color:#fff;",
          err
        );
        reject(err);
      });
  });
}
// 生成首图 暂时废弃
function makeTitleImg(data, list) {
  console.log("首图数据", data);
  let arr = JSON.parse(JSON.stringify(list));
  return new Promise((resolve, reject) => {
    if (!arr || arr == []) {
      reject("皮肤列表不能为空");
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
          let titlName = `区服 ${data.areaName || "--"}(贵族 V${
            data.vipLevel || "--"
          })`;
          context.fillStyle = "#ffff00";
          context.font = "normal bold 135px Adobe Ming Std";
          context.fillText(titlName, 842, 280);
          context.font = "100px Adobe Ming Std";
          context.fillStyle = "#ffff00";
          context.fillText(`英雄数量 ${data.heroNum || "--"}`, 200, 522);
          context.fillText(`皮肤数量 `, 200, 700);
          context.fillText(`贵族等级 `, 1245, 700);
          context.fillText(`水晶数量 `, 2050, 700);
          // 红色字体
          context.fillStyle = "#ff0000";
          context.fillText(`${data.skinNum || "--"}`, 640, 700);
          context.fillText(`${data.vipLevel || "--"}`, 1680, 700);
          context.fillText(`${data.shuijingnum || "--"}`, 2475, 700);
          context.fillText(`贵族积分 ${data.nobNum || "--"}`, 2050, 522);
          context.fillText(`五级铭文 ${data.inscriptions || "--"}`, 1245, 522);
          // 白色字体
          context.fillStyle = "#fff";
          context.fillText(`王者印记 ${data.yinji || "--"}`, 200, 900);
          context.fillText(
            ` ${data.shiming == 1 ? "可实名" : "包人脸"}`,
            2150,
            900
          );
          context.fillText(`紫晶币 ${data.zijing || "--"}`, 200, 2300);
          context.fillText(`幸运值 ${data.xingyung || "--"}`, 2300, 2300);
          // 底部统计
          context.fillStyle = "#ffff00";
          context.fillText(
            `MVP次数 ${data.mvpNum || "--"} 游戏场次 ${
              data.totalCount || "--"
            } 战斗力 ${data.zhandouli || "--"}`,
            522,
            2520
          );
          context.fillText(`省标 ${data.shengbiao || "--"} `, 1350, 2650);
          // 皮肤字体
          context.font = "normal bold 80px Adobe Ming Std";
          let skin = arr.splice(0, 32);
          var x1 = 200;
          var h = 920;
          skin.map((item, index) => {
            if (index < 32) {
              if (item.sort == -1) {
                context.fillStyle = "#00FFD9";
              } else if (item.sort === 0) {
                context.fillStyle = "#E6FF00";
              } else {
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
          console.log(
            "%c 🍏 base64Url: ",
            "font-size:20px;background-color: #465975;color:#fff;",
            base64Url
          );
          resolve(base64Url);
        };
      })
      .catch((err) => {
        reject(err);
      });
  });
}
// 生成长方形首图  暂时废弃
function makeTitleImgChang(data, list) {
  let arr = JSON.parse(JSON.stringify(list));
  return new Promise((resolve, reject) => {
    if (!arr || arr == []) {
      reject("皮肤列表不能为空");
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
          let titlName = `区服 ${data.areaName || "--"}(贵族 V${
            data.vipLevel || "--"
          })`;
          context.fillStyle = "#ffff00";
          context.font = "normal bold 36px Adobe Ming Std";
          context.fillText(titlName, 700, 110);
          context.font = "38px Adobe Ming Std";
          context.fillStyle = "#ffff00";
          context.fillText(`英雄数量 ${data.heroNum || "--"}`, 200, 201);
          context.fillText(`皮肤数量 `, 200, 251);
          context.fillText(`贵族等级 `, 550, 251);
          context.fillText(`水晶数量 `, 900, 251);
          // 红色字体
          context.fillStyle = "#ff0000";
          context.fillText(`${data.skinNum || "--"}`, 360, 251);
          context.fillText(`${data.vipLevel || "--"}`, 710, 251);
          context.fillText(`${data.shuijingnum || "--"}`, 1060, 251);
          context.fillText(`贵族积分 ${data.nobNum || "--"}`, 900, 201);
          context.fillText(`五级铭文 ${data.inscriptions || "--"}`, 550, 201);
          // 白色字体
          context.fillStyle = "#fff";
          context.fillText(`王者印记 ${data.yinji || "--"}`, 1250, 201);
          context.fillText(
            ` ${data.shiming == 1 ? "可实名" : "包人脸"}`,
            1250,
            251
          );
          context.fillText(`紫晶币 ${data.zijing || "--"}`, 200, 301);
          context.fillText(`幸运值 ${data.xingyung || "--"}`, 1250, 301);
          // 底部统计
          context.fillStyle = "#ffff00";
          context.fillText(
            `MVP次数 ${data.mvpNum || "--"} 游戏场次 ${
              data.totalCount || "--"
            } 战斗力 ${data.zhandouli || "--"}省标 ${data.shengbiao || "--"}`,
            450,
            782
          );
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
              } else if (item.sort === 0) {
                context.fillStyle = "#E6FF00";
              } else {
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
// 生成游戏首图
function makeTitleImgYjwj(data, list, attrs) {
  if (data.gameName == "和平精英") {
    return makeTitleImgHpjy(data, list, attrs);
  }
  let keys = attrs.filter((i) => {
    return (
      i.fieldType == "input" || i.fieldType == "number" || i.fieldType == "date"
    );
  });
  return new Promise((resolve, reject) => {
    if (!list || list == []) {
      reject("多选不能为空");
    }
    // 取出已选择项
    let arr = [];
    list = list.split(",");
    if (list && list.length) {
      attrs.forEach(async (item1) => {
        if (item1.fieldType == "checkbox" && item1.isEnable === "0") {
          item1.childAttributes.forEach(async (item2) => {
            if (list.indexOf(String(item2.id)) > -1) {
              arr.push(item2);
            }
          });
        } else if (item1.fieldType == "boxradio" && item1.isEnable === "0") {
          item1.childAttributes.forEach(async (item2) => {
            item2.childGameAttributes.forEach(async (item3) => {
              if (list.indexOf(String(item3.id)) > -1) {
                arr.push(item3);
              }
            });
          });
        }
      });
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
          /**  */
          let titlName = `${data.gameName}`;
          context.fillStyle = "#ffff00";
          context.font = "normal bold 135px Adobe Ming Std";
          context.fillText(
            titlName,
            (3000 - (titlName ? titlName.length : 0) * 135) / 2,
            280
          );
          context.font = "100px Adobe Ming Std";
          if (keys[0] && keys[0].aavName != "qita") {
            context.fillText(
              `${keys[0] ? keys[0].fieldName : ""} ${
                data[keys[0].aavName] ? data[keys[0].aavName] : "--"
              }`,
              150,
              522
            );
          }
          if (keys[1] && keys[1].aavName != "qita") {
            context.fillText(
              `${keys[1] ? keys[1].fieldName : ""} ${
                data[keys[1].aavName] ? data[keys[1].aavName] : "--"
              } `,
              150,
              700
            );
          }
          if (keys[2] && keys[2].aavName != "qita") {
            context.fillText(
              `${keys[2] ? keys[2].fieldName : ""} ${
                data[keys[2].aavName] ? data[keys[2].aavName] : "--"
              }`,
              1100,
              522
            );
          }
          if (keys[3] && keys[3].aavName != "qita") {
            context.fillText(
              `${keys[3] ? keys[3].fieldName : ""} ${
                data[keys[3].aavName] ? data[keys[3].aavName] : "--"
              }`,
              1100,
              700
            );
          }

          if (keys[4] && keys[4].aavName != "qita") {
            context.fillText(
              `${keys[4] ? keys[4].fieldName : ""} ${
                data[keys[4].aavName] ? data[keys[4].aavName] : "--"
              }`,
              2100,
              522
            );
          }
          if (keys[5] && keys[5].aavName != "qita") {
            context.fillText(
              `${keys[5] ? keys[5].fieldName : ""} ${
                data[keys[5].aavName] ? data[keys[5].aavName] : "--"
              }`,
              2100,
              700
            );
          }
          // 底部
          if (keys[6]) {
            context.fillText(
              `${data[keys[6].aavName] ? data[keys[6].aavName] : "--"}`,
              (3000 -
                (data[keys[6].aavName] ? data[keys[6].aavName].length : 0) *
                  90) /
                2,
              2520
            );
          }

          // 皮肤字体
          context.font = "normal bold 100px Adobe Ming Std";
          let skin = arr.splice(0, 32);
          console.log("%c Line:431 🥤 skin", "color:#3f7cff", skin);
          var x1 = 150;
          var h = 920;
          skin.map((item, index) => {
            if (index < 32) {
              if (index % 4 == 0) {
                x1 = 150;
                //控制行高
                h = h + 150;
              }
              let nametxt = "";
              if (data.gameName == "王者荣耀") {
                nametxt = item.fieldName.split("-")[1]
                  ? item.fieldName.split("-")[1]
                  : item.fieldName.split("-")[0];
              } else {
                nametxt = item.fieldName.slice(0, 7);
              }

              context.fillText(nametxt, x1, h);
              //控制列间隔
              x1 = x1 + 700;
            }
          });
          const base64Url = canvas.toDataURL("image/jpeg", 0.3);
          console.log(
            "%c 🍏 base64Url: ",
            "font-size:20px;background-color: #465975;color:#fff;",
            base64Url
          );
          resolve(base64Url);
        };
      })
      .catch((err) => {
        reject(err);
      });
  });
}
// 生成和平精英首图
function makeTitleImgHpjy(data, list, attrs) {
 
  return new Promise((resolve, reject) => {
    if (!list || list == []) {
      reject("多选不能为空");
    }
    // 取出已选择项
    let arr = [];
    let xysz = [];
    let xyzj = [];
    let xyqx = [];
    let smtext = "--";
    let zsktext = "--";
    list = list.split(",");
    if (list && list.length) {
      attrs.forEach(async (item1) => {
        if (item1.aavName == "f10") {
          item1.childAttributes.forEach(async (item2) => {
            if (data.f10 == item2.id) {
              smtext = item2.fieldName;
            }
          });
        }
        if (item1.aavName == "f14") {
          item1.childAttributes.forEach(async (item2) => {
            if (data.f14 == item2.id) {
              zsktext = item2.fieldName;
            }
          });
        }
        if (item1.fieldType == "checkbox" && item1.isEnable === "0") {
          item1.childAttributes.forEach(async (item2) => {
            if (list.indexOf(String(item2.id)) > -1) {
              if (item2.parentId == 38666) {
                // 稀有时装
                xysz.push(item2.fieldName);
              } else if (item2.parentId == 38445) {
                // 稀有载具
                xyzj.push(item2.fieldName);
              } else if (item2.parentId == 38249) {
                // 稀有枪械
                xyqx.push(item2.fieldName);
              } 
            }
          });
        }
      });
    }
    let bg = "/static/img/hpjybg.png";
    getURLBase64(bg)
      .then((res) => {
        const imageaaa = new Image();
        imageaaa.src = res;
        imageaaa.onload = () => {
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.width = imageaaa.width;
          canvas.height = imageaaa.height;
          context.drawImage(imageaaa, 0, 0, imageaaa.width, imageaaa.height);
          /**  */
          context.fillStyle = "#945424";
          context.font = "normal bold 100px Adobe Ming Std";
          // 设置文字阴影的颜色为黑色，透明度为50%
          context.shadowColor = "rgba(0,0,0,0.5)";
          // 轻微模糊阴影
          context.shadowBlur = 10;
          // 绘制头部8个字段
          context.fillText(data.f7, 633, 446);//载具数量
          context.fillText(data.f11, 1463, 446);
          context.fillText(data.f12, 2340, 446);
          context.font = "normal bold 70px Adobe Ming Std";
          context.fillText(smtext, 3150, 430);//实名
          context.font = "normal bold 100px Adobe Ming Std";
          context.fillText(data.f8, 633, 626); //套装数量
          context.fillText(data.f9, 1463, 626);
          context.fillText(zsktext, 2340, 616);//战神框
          context.fillText(data.f16, 3170, 626);

         
          // 皮肤字体
          context.font = "normal 70px Adobe Ming Std";
          // 换行绘制
          let tempy = 764;
          // 绘制已毕业地图
          if (xysz && xysz.length) {
            let maptxt = xysz.join("，");
            context.fillStyle = "#945424";
            tempy = drawTextAutoLine(context, maptxt, 180, 915, 3150, 1135,100);
          }
          // 绘制已毕业季节
          if (xyzj && xyzj.length) {
            let maptxt = xyzj.join("，");
            context.fillStyle = "#945424";
            tempy = drawTextAutoLine(context, maptxt, 180, 1382, 3150, 1616,100);
          }
          // 绘制稀有礼包
          if (xyqx && xyqx.length) {
            let maptxt = xyqx.join("，");
            context.fillStyle = "#945424";
            tempy = drawTextAutoLine(context, maptxt, 180, 1840, 3150, 2097,100);
          }

          const base64Url = canvas.toDataURL("image/jpeg", 0.7);
          console.log(
            "%c 🍏 base64Url: ",
            "font-size:20px;background-color: #465975;color:#fff;",
            base64Url
          );
          resolve(base64Url);
        };
      })
      .catch((err) => {
        reject(err);
      });
  });
}
// 按照宽度自动换行绘制文本  ^ 符号换行
function drawTextAutoLine(context, t, x, y, w, botY,lineheight) {
  var chr = t.split("");
  var temp = "";
  var row = [];
  for (var a = 0; a < chr.length; a++) {
    if (context.measureText(temp).width >= w || chr[a] == "^") {
      row.push(temp);
      temp = "";
    }
    temp += chr[a];
  }
  row.push(temp);
  for (var b = 0; b < row.length; b++) {
    context.fillText(row[b], x, y + b * lineheight);
    if (b == row.length - 1 || y + (b + 1) * lineheight > botY) {
      return y + (b + 1) * lineheight;
    }
  }
}
let obj = {
  makeDetailImg,
  makeTitleImg,
  makeTitleImgChang,
  makeTitleImgYjwj,
  makeTitleImgHpjy,
};
export default obj;
