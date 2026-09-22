
function _copy(anniu, content) {
  var clipboard = new ClipboardJS(anniu, {
      target: function (e) {
          return document.querySelector(content);
      }
  });

  clipboard.on('success', function (e) {
      alert('复制成功发送给客服下单!');
      console.info('Action:', e.action);
      console.info('Text:', e.text);
      console.info('Trigger:', e.trigger);
      e.clearSelection();
  });

  clipboard.on('error', function (e) {
      alert('复制账号失败!请联系客服！')
      console.error('Action:', e.action);
      console.error('Trigger:', e.trigger);
  });
}

_copy('.accountshow', '.accountshow');

var copyDatas = {}

function randerInfo(){
  var data = copyDatas
  $(".showid").text("商品ID：" + data.id)
  $(".showname").text("游戏类型：" + data.type)
  $(".showarea").text("大区：" + data.regional)
  $(".realname").text("实名状态：" + data.realName)
  $(".heros").text("英雄数量：" + data.heros)
  $(".skins").text("皮肤数量：" + data.skins)
  $(".price").text("售价：" + data.price)
  $(".des").text("简介内容：" + data.des)
  $(".account").text("账号：" + data.account)
  $(".pwd").text("密码：" + data.pwd)
  $(".tel").text("换绑手机号：" + data.tel)
  $(".remark").text("买家备注：" + data.remark)
  $(".supyremake").text("注意事项：" + data.supyremake)
  var imgArr = new Array();
  imgArr = data.jietuList.concat(data.otherList)
  $("#showimg").text("")
  for (var i = 0; i < imgArr.length; i++) {
      let imgDom = "<img class='imgDetails' src='"+ imgArr[i].replace('https','http') +"'>"
      $("#showimg").append(imgDom);
  }
}
var copybox = document.getElementById('accountshow'); //复制该dom里的图片和文字
function domakeimg(){
  randerInfo()
  setTimeout(function(){
    copybox.click() //触发复制
  },1000)
   
 }




