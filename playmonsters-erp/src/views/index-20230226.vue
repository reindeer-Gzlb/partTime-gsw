<template>
  <div class="app-container home">
    <el-row :gutter="20" style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <el-col :sm="24" :lg="24">

          <!-- <div class="chart-wrapper">
            <raddar-chart />
          </div> -->

        <blockquote class="text-danger" style="font-size: 19px">
        买卖号开发性游戏账号资源共享平台，专门为游戏账号商家打造的一体化账号管理分销系统，为大家提供合作便捷服务，百分之八十的电商商家的拿货平台
        <!-- 买卖号开放性资源共享平台，专门服务游戏账号交易上架，为大家合作提供便捷服务,百分之80的电商商家拿货系统 -->
        <!-- <br />
          <el-link
            href="https://www.aliyun.com/minisite/goods?userCode=brki8iof"
            type="primary"
            target="_blank"
            >https://www.aliyun.com/minisite/goods?userCode=brki8iof</el-link
        >-->
        <br />
        <!-- <el-link
            href="https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console"
            type="primary"
            target="_blank"
            >https://cloud.tencent.com/redirect.php?redirect=1025&cps_key=198c8df2ed259157187173bc7f4f32fd&from=console</el-link
        > -->
         <br />如需要自营版系统请联系微信：jiaoyitang88
          <br />
          <br />商务合作： +VX：jiaoyitang88，备注来意
          <br />
          <br />商家版系统介绍视频（必看）：
          <el-link
            href="https://game.ikbh.top/video/newshop.mp4"
            type="primary"
            target="_blank"
          >点击查看</el-link>
        </blockquote>

        <hr />
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px;">
        <h2 style="text-align: center;font-weight: bold ">
          商家版【适用对象1-8人团队】/
          <span style="color:red">￥199R</span>
        </h2>

        <table class="tableClass">
          <tr>
            <td rowspan="3" style="width:200px">
              <b>分销功能</b>
            </td>
            <td>账号池</td>
            <td>账号池数据为共享型数据，你可以在账号池添加任何一个账号到自己的选号链接上面并且可以进行加价售价</td>
          </tr>
          <tr>
            <td>我的账号</td>
            <td>
              1.是用来管理在售账号的，可以用它来给客户找号，用来管理选号站
              <br />2.我的链接是独立的选号站，显示内容【我的账号】所有账号。
            </td>
          </tr>
          <tr>
            <td>供货分配</td>
            <td>用来添加供货人 和游戏种类，包括设置溢价比例的。</td>
          </tr>
          <tr>
            <td rowspan="2" style="width:200px">
              <b>供货端</b>
            </td>
            <td>批量上传</td>
            <td>批量上传，用来批量上架账号</td>
          </tr>
          <tr>
            <td>未售</td>
            <td>
              1.是用来管理在售的账号你可以理解为【已上架的账号】
              <br />2.新增是发布功能
              <br />3.下架：点击下架则账号下架之后进入自售状态，此状态不展示
              <br />4.导出：文本导出
              <br />5.复制，详情内容复制
            </td>
          </tr>
          <tr>
            <td>
              <b>AIP接口</b>
            </td>
            <td
              colspan="2"
            >API接口是用来与多个网站同步上架下架编辑的功能，比如说你对接了交易猫的接口。你在这里发布账号，编辑，下架账号交易猫那边也会跟着同步，只要有网站的都支持对接。减少了一个号多个平台发布的困惑</td>
          </tr>
          <tr>
            <td>
              <b>系统优势</b>
            </td>
            <td colspan="2">本版本免费，如果没有自己的游戏种类可以联系管理员添加，系统支持所有游戏，系统内部设置自动生成图片，可以很好的管理我们自己的账号，防止丢失。</td>
          </tr>
          <tr>
            <td colspan="3">
              <h1>
                <b style="red">
                  系统介绍
                  <span>（必看）</span>：
                </b>
                <el-link
                  href="https://game.ikbh.top/video/newshop.mp4"
                  type="primary"
                  target="_blank"
                >点我查看</el-link>
              </h1>
            </td>
          </tr>
          <tr>
            <td colspan="3">
              <h1>
                <b>授权客服微信：</b>
                jiaoyitang88
              </h1>
            </td>
          </tr>
          <tr>
            <td colspan="3">
              <h1>
                <b>商家服务微信交流群：</b>
              </h1>
              <div style="padding-left: 300px;">
                <img src="https://game.ikbh.top/.img0demo/wx.jpg" />
              </div>
              <div style="padding-left: 300px;color: red">
                <h2>如二维码过期，请加微信：jiaoyitang88</h2>
              </div>
            </td>
          </tr>
        </table>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px;">
        <el-row>
          <h2 style="text-align: center;font-weight: bold ">
            自营版【适用对象10人以上团队】/
            <span style="color:red">￥9800R</span>
          </h2>
          <table>
            <tr>
              <td>
                <b>系统管理</b>
              </td>
              <td>
                1.用户管理：创建用户和授权
                <br />2.角色管理：分配角色权限
                <br />3.菜单管理：管理菜单设置
                <br />4.部门管理：设置权限以及授权
                <br />5.岗位管理
                <br />6.系统设置：创建游戏类目，设置选号站
                <br />7.参数设置
                <br />8.公告设置
                <br />9.日志管理查看所有的系统操作日志
              </td>
            </tr>
            <tr>
              <td>
                <b>管理端</b>
              </td>
              <td>
                1.用户管理：创建用户和授权
                1.审核账号：用来审核上架的账号
                <br />2.账号管理：用来管理全站账号
                <br />3.接口管理：用来管理对接的API接口
                <br />4.终端管理：用来调度终端的货源，终端有一万多个一手账号可以随意添加到自己系统上面加价去卖
                <br />5.导入历史
                <br />6.字段配置：用来配置筛选标签，和发布勾选实现自动识别自动打标，可以在选号站筛选功能展示方便客户找号，并且可以自动生成标题图片
                <br />7.操作记录查询
                <br />8.推送记录用来查看对接接口的同步上架的数据
                <br />9.价格管理：用来批量设置某个用户的价格进行加减价
              </td>
            </tr>
            <tr>
              <td>
                <b>分销端</b>
              </td>
              <td>
                1.用户管理：创建用户和授权
                <br />2.审核账号：用来审核上架的账号
                <br />3.账号管理：用来管理全站账号
                <br />4.接口管理：用来管理对接的API接口
                <br />5.终端管理：用来调度终端的货源，终端有一万多个一手账号可以随意添加到自己系统上面加价去卖
                <br />6.导入历史
                <br />7.字段配置：用来配置筛选标签，和发布勾选实现自动识别自动打标，可以在选号站筛选功能展示方便客户找号，并且可以自动生成标题图片
                <br />8.操作记录查询
                <br />9.推送记录用来查看对接接口的同步上架的数据
                <br />10.价格管理：用来批量设置某个用户的价格进行加减价
              </td>
            </tr>
            <tr>
              <td>
                <b>销售端</b>
              </td>
              <td>
                1.主要功能销售客服操作台。
                <br />2.销售客服操作工作台，给销售人员授权
                <br />3.没什么特别复杂的功能自己研究一下就知道了就不介绍了
              </td>
            </tr>
            <tr>
              <td>
                <b>供货端</b>
              </td>
              <td>用来管理，上架，下架账号，给回收人员使用开设的授权</td>
            </tr>
            <tr>
              <td>
                <b>回收系统</b>
              </td>
              <td>自定义回收内容，所有内容都可以自定义功能很强大</td>
            </tr>
            <tr>
              <td>
                <b>选号站样式</b>
              </td>
              <td>手机端：两种不同风格，pc端一种，支持定制</td>
            </tr>
            <tr>
              <td>
                <b>API接口</b>
              </td>
              <td>支持进项接口【别人的账号同步接入系统】支持出项接口【将自己的号接入多个平台实现同步】</td>
            </tr>
            <tr>
              <td>
                <b>售后服务</b>
              </td>
              <td>
                1.免费维护1年
                <br />2.支持2次开发功能新增
                <br />3.突发问题免费处理
                <br />4.免费教学使用
              </td>
            </tr>
            <tr>
              <td rowspan="2">
                <h1>
                  <b>教学视频：</b>
                  <el-link href="https://game.ikbh.top/video/newshop.mp4" type="primary" target="_blank">点我查看</el-link>
                </h1>
              </td>
            </tr>
            <tr>
              <td rowspan="2">
                <h1>
                  <b>购买客服：</b>
                  jiaoyitang88
                </h1>
              </td>
            </tr>
          </table>
        </el-row>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>常见问题</span>
          </div>
          <div class="body">
            <span>1、授权问题，联系微信：jiaoyitang88</span>
            <br />
            <br />
            <span>2、系统使用问题，联系微信：jiaoyitang88</span>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v1.0.2 - 2022-11-01">
              <ol>
                <li>H5选号站去除小数点</li>
                <li>展示自己未添加的供货商</li>
                <li>新增供货商折扣排名</li>
                <li>新增财务管理</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.1 - 2022-10-25">
              <ol>
                <li>新增妖气山接口</li>
                <li>优化交易猫接口</li>
                <li>优化定时推送接口相关任务</li>
                <li>新增接口相关加价</li>
                <li>开放账号池功能</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v1.0.0 初始版免费开放">
              <ol>
                <li>怪兽玩代售账号管理系统</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>问题反馈</span>
          </div>
          <div class="body">
            <span style="display: inline-block; height: 30px; line-height: 30px">
              <a href="https://docs.qq.com/sheet/DY3dDcUltdk5pZ25U?tab=BB08J2">我要反馈</a>
            </span>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      :destroy-on-close="true"
      @close="playerVisible=false"
      style="width: 100%"
    >
      <player :video="video" v-if="playerVisible"></player>
    </el-dialog>
  </div>
</template>
<style scoped>
table,
th,
td {
  border: 1px solid black;
  font-size: 17px;
}
</style>
<script>
import player from "./components/player";
import RaddarChart from "./dashboard1/RaddarChart";
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.8.4",
      dialogVisible: false,
      playerVisible: false,
      video: {
        vid: "111",
        playauth: "https://game.ikbh.top/video/newshop.mp4"
      }
    };
  },
  components: {
    player,RaddarChart
  },
  methods: {
    playVideo(row) {
      //console.log(row)
      this.dialogVisible = true;
      this.video = {};
      courseApi
        .getPlayAuth(row.vid)
        .then(response => {
          this.video.vid = row.vid;
          this.video.playauth = response.data.data.res.playAuth;
          this.playerVisible = true;
        })
        .catch(error => {});
    },

    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

