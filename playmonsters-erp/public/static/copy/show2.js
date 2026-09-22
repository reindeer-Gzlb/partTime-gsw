// window.alert = function (name) {
//     var iframe = document.createElement("IFRAME");
//     iframe.style.display = "none";
//     iframe.setAttribute("src", 'data:text/plain,');
//     document.documentElement.appendChild(iframe);
//     window.frames[0].window.alert(name);
//     iframe.parentNode.removeChild(iframe);
// }


// _copy('.copy', '.accountshow');

// var YXLX_ID = getQueryVariable("YXLX_ID");
// var ZHID = getQueryVariable("ZHID");
// var id = getQueryVariable("id");

// var cokie = $.cookie("pathname");
// var path = url + '/H5/GetZHInfo?zhid=' + ZHID
// if (id){
//     path = url + '/H5/GetZHInfo?zhid=' + ZHID +'&id='+id;
// }else {
//     path = url + '/H5/GetZHInfo?zhid=' + ZHID
// }
var copybox = document.getElementById('copy'); //复制该dom里的图片和文字
var clipboard = new ClipboardJS(copybox, {
  target: function (e) {
      return document.querySelector('.accountshow');
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
var copyDatas = {}
function randerInfo () {
  var data = {
    otherList : ['https://game.ikbh.top/ff8080818658f3d201865adcb6c702b121031FKU44S6-0-lp.jpg','https://game.ikbh.top/ff8080818658f3d201865adcbd1402b2210416122252-1-1200.jpg'],
    jietuList : ['https://game.ikbh.top/ff8080818658f3d201865adcb6c702b121031FKU44S6-0-lp.jpg','https://game.ikbh.top/ff8080818658f3d201865adcbd1402b2210416122252-1-1200.jpg'],
    des:'简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介简介'
  } 
  console.log(copyDatas)
  // $(".showid").text("商品ID：" + data.id)
  // $(".showname").text("游戏类型：" + data.type)
  // $(".showarea").text("大区：" + data.regional)
  // if ('1' == data.accType) {
  //     let dom = "<div><div class='te-text'><div >特</div></div> </div>￥" + data.specialPrice
  //     $(".showprice").append(dom)
  // } else {
  //     let dom = "￥" + data.price
  //     $(".showprice").append(dom)
  // }

  // YXLX_ID = decodeURI(YXLX_ID);
  // console.log(YXLX_ID)
  // var yxlm = "英雄联盟";
  // var wzry = "王者荣耀";
  // $(".showhero").text("英雄数量：" + data.heros)
  // $(".shownum").text("皮肤数量：" + data.skins)
  $(".showintro").text("简介：" + data.des)
  var imgArr = new Array();
  imgArr = data.jietuList.concat(data.otherList)
  $("#showimg").text("")
  for (var i = 0; i < imgArr.length; i++) {
      let imgDom = "<img class='imgDetails' src='"+ imgArr[i] +"'>"
      $("#showimg").append(imgDom);
  }
}
//定义一个函数来触发复制
function domakeimg(){
  // writing.style.display='none' //该行是另一个需要复制的盒子 这里不复制它 所以隐藏
  //  canvasimg.src=makeimg //在这里 主页面已经给makeimg 赋值了
  randerInfo()
  // setTimeout(function(){
  //   copybox.click() //触发复制
  // },1000)
   
 }
 domakeimg()

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) { return pair[1]; }
    }
    return (false);
}




