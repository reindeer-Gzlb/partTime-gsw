<template>
  <!-- 平台自有渠道回收列表,我的回收  ，回收联盟 -->
  <div class="app-container ">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="zdy-form search-form-new" label-width="68px"
          @keyup.enter.native="search" >
          <el-form-item label="游戏类型" prop="type">
            <el-select v-model="queryParams.type" placeholder="请选择游戏" size="small" clearable @change="search">
              <el-option v-for="dict in gameList" :key="dict.id" :label="dict.name" :value="dict.name" />
            </el-select>
          </el-form-item>
          <el-form-item label="账号" prop="account" >
            <el-input v-model="queryParams.account" placeholder="请输入账号" clearable size="small" />
          </el-form-item>
          <el-form-item label="简介" prop="des">
            <el-input v-model="queryParams.des" placeholder="请输入关键字" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="small" @click="search" class="zdy-btn">搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-refresh" type="primary" size="small" plain @click="resetQuery"
              class="zdy-btn zdy-btn-refresh">重置</el-button>
          </el-form-item>
       
        </el-form>
    <div class="tipRole" v-if="!isHuishouRole">
      您还没有该权限，请联系系统管理员开通！
    </div>
    <div class=" customTab" v-loading="loading">
      <!-- <div class="tip" @click="openUrl('https://game.ikbh.top/videoDoc/%E5%9B%9E%E6%94%B6%E8%81%94%E7%9B%9F%E7%B3%BB%E7%BB%9F%E8%AE%B2%E8%A7%A3.mp4')">不会操作？查看视频教程
      </div> -->
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        

        <el-tab-pane :label="tab" :name="tab" v-for="tab in gameTypes" :key="tab">
        
            <el-table :data="tableData.records" 
              style="width: 100%">
              <el-table-column prop="id" label="ID" width="80"></el-table-column>
              <el-table-column v-if="actived == '已拒收'" prop="rmsg" label="拒收原因" width="120"></el-table-column>
             
              <el-table-column prop="type" label="游戏名称" width="120"></el-table-column>
              <el-table-column prop="region" label="区服" width="100"></el-table-column>
              
              <el-table-column prop="account" label="游戏账号" width="100" v-if="actived == '已报价'"></el-table-column>
              <el-table-column prop="relname" label="实名" width="100"></el-table-column>
            
              <!-- isAgreeBp -->
              <el-table-column prop="des" label="商品详情" min-width="260">
                <template slot-scope="scope">
                  <el-tooltip class="item" effect="dark" :content="scope.row.des" placement="top-start">
                    <div class="zdy-des">{{ scope.row.des }}</div>
                  </el-tooltip>
                </template>
              </el-table-column>
              <el-table-column prop="createTime" label="发布时间" width="160"></el-table-column>
              
              <el-table-column prop="" label="黑号提示" width="100">
                <template slot-scope="scope">
                  <el-tag type="danger" v-show="scope.row.isBlack==1">{{ scope.row.isBlack==1?'黑号':'' }}</el-tag>
                </template>
              </el-table-column>
              <el-table-column prop="bprice" label="报价金额" width="100" ></el-table-column>
              <el-table-column prop="btime" label="报价时间" width="160"></el-table-column>
              <el-table-column prop="" label="状态" width="100" >
                <template slot-scope="scope">
                  <span v-if="userInfo&&userInfo.userId==scope.row.supplyUserId">{{commonMaps.bstatusMap[scope.row.status||0].label}}</span>
                  <span v-else>{{scope.row.status!=0?'未中标':commonMaps.bstatusMap[0].label}}</span>
                
                </template>
              </el-table-column>
              <el-table-column prop="reason"  label="取消原因" width="150"></el-table-column>
              <el-table-column label="操作" width="120">
                <template slot-scope="scope">
                  <el-button type="text" style="color:#af0c0c" v-if="actived == '待报价'"
                    @click="refuseLianmeng(scope.row)">拒收</el-button>
                  <el-button type="text" v-if="actived == '待报价'" @click="editOrder(scope.row,'edit')">报价</el-button>
                  <el-button type="text" v-if="actived != '待报价'"  @click="editOrder(scope.row,'view')">查看</el-button>
                </template>
              </el-table-column>
            </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum" :page-sizes="[10, 20, 30, 40, 50, 60]" :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="tableData.total"></el-pagination>
    </div>
    <addDialog ref="addDialog"></addDialog>
  </div>
</template>

<script>
import addDialog from './components/add'
import onSelfApi from '@/api/retrieveManage/onSelf.js'
import { getInfo } from '@/api/login'
import { getDicts } from "@/api/system/dict/data";
import Cookies from 'js-cookie'
import { getUserProfile } from "@/api/system/user";
import gameListApi from "@/api/gameConfig/gameList"
export default {
  name: "RetrieveOnself",
  components: { addDialog },
  data() {
    return {
      gameList: [],//全部游戏
      gameTypes: [
      '待报价', '已报价','已拒收','已中标','未中标'
      ],
      loading: false,
      tableData: {
        records: [],
        total: 0
      },
      actived: "待报价",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: '',
        des: '',
        // backState: 1,
        id: ''

      },
      userInfo: null,
      pageType: '',
      h5BasicUrl: '',
      currentData: {},
      IMhost: '',
      isHuishouRole: true

    };
  },
  created() {
    // this.getIMhost()
    this.getUserRoles()
    // this.getList();
    // this.getUserInfo()
    // this.getH5BasicUrl()
    this.getGameList()

    console.log("%c Line:123 🍅 this.pageType", "color:#e41a6a", this.pageType);
    // this.getHost();
  },
  activated() {
    // this.getList();
  },
  methods: {
    // 查询当前用户的权限
    getUserRoles() {
      getUserProfile().then(response => {
        this.userInfo = response.data
        if (response.roleGroup.indexOf('竞价回收') > -1 || response.roleGroup.indexOf('超级管理员') > -1) {
          this.getList();
          this.isHuishouRole = true
        } else {
          this.isHuishouRole = false
        }
      })
    },
    // 获取IM域名
    getIMhost() {
      getDicts('IMhost').then(res => {
        if (res.code == 200) {
          this.IMhost = res.data[0]?.dictValue
        }
      });
    },
    // 跳转IM页面
    goIM(IMid) {
      let token = Cookies.get('Admin-Token')
      window.open(`${this.IMhost}/chat/conversation?hxuser=${IMid}&str=${token}&chatType=groupChat`)

    },
    // 获取全部游戏
    async getGameList() {
      let res = await gameListApi.getGameList()
      // console.log("%c Line:278 🍫 res", "color:#465975", res);
      this.gameList = res.rows
    },
    openUrl(url) {
      window.open(url)
    },
    // 获取列表
    getList() {
      this.loading = true;
      if (this.actived == '待报价') {
          this.queryParams.status = ''
          onSelfApi.getList(this.queryParams).then(response => {
            this.tableData.records = response.rows;
            this.tableData.total = response.total;
            this.loading = false;
          });

        } else if (this.actived == '已报价') {
          this.queryParams.status = ''
          this.queryParams.statusSign = ''
          this.getQuotationList()
        }else if (this.actived == '已拒收'){
          // 已拒收
          this.queryParams.status = 5
          this.getQuotationList()
        }else if (this.actived == '已中标'){
          // 已拒收
          this.queryParams.status = ''
          this.queryParams.statusSign = 2
          this.getQuotationList()
        }else if (this.actived == '未中标'){
          // 已拒收
          this.queryParams.status = ''
          this.queryParams.statusSign = 1
          this.getQuotationList()
        }


    },
    // 获取已报价列表
    getQuotationList(){
      onSelfApi.getQuotationList(this.queryParams).then(response => {
            this.tableData.records = response.rows;
            this.tableData.total = response.total;
            this.loading = false;
          });
    },
    // 获取用户信息
    getUserInfo() {
      getInfo().then(res => {
        console.log("%c Line:270 🥔 res", "color:#33a5ff", res);
        this.userInfo = res.user
      })
    },
    // 打开编辑信息弹窗
    editGoods(row) {
      if (row.gid) {
        this.$refs.goodsEdit.open('edit', { id: row.gid })
      } else {
        this.$message.error('参数错误')
      }
    },
    // 拉包赔进群  打开选择包赔
    pullBaopei(row) {
      // 打开包赔商选择框
      this.currentData = row
      this.$refs.selectBaopei.open()
    },
    // 确认拉包赔进群
    pullBaopeiSure(cpsId) {
      let params = {
        id: this.currentData.id,
        cpsId: cpsId
      }
      onSelfApi.pullBaopeiGroup(params).then(res => {
        console.log("%c Line:194 🍩 res", "color:#ffdd4d", res);
        if (res.code == 200) {
          this.$alert(res.msg)
        } else {
          this.$alert(res.msg)
        }
        this.getList()
      })
    },
    // // 转到已回收
    // zdyhs(row) {
    //   this.$confirm('确定将该回收数据转到已回收吗？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     let obj = {
    //       backState: 3,
    //       id: row.id
    //     }
    //     onSelfApi.editHuishou(obj).then(res => {
    //       this.$alert(res.msg)
    //       this.getList()

    //     })
    //   }).catch(() => { });
    // },
    // 一键上架商品
    // YJSJ(row) {
    //   this.$confirm('发布前请确认已完善商品信息，确定将该回收数据发布到商品列表吗？', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     let obj = {
    //       id: row.id
    //     }
    //     onSelfApi.yjfb(obj).then(res => {
    //       console.log("%c Line:341 🎂 res", "color:#3f7cff", res);
    //       if (res.code == 200) {
    //         this.$message.success(res.msg)
    //         this.getList()
    //       }
    //     })
    //     // let obj = {
    //     //   backState: 3,
    //     //   id: row.id
    //     // }
    //     // onSelfApi.editHuishou(obj).then(res => {
    //     //   this.$alert(res.msg)
    //     //   this.getList()

    //     // })
    //   }).catch(() => { });
    // },

    // tabs切换时触发
    tabChange() {
      // this.queryParams.type = this.actived;
      this.search();
      // this.getAreas();
    },

    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    // 转到联盟
    transLianmeng(row) {
      this.$confirm('确定将该回收数据转到回收联盟吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let obj = {
          transfer: 2,
          id: row.id
        }
        onSelfApi.transition(obj).then(res => {
          this.$alert(res.msg)
          this.getList()

        })
      }).catch(() => { });
    },
    // 打开报价弹窗-回收联盟
    editOrder(row,flag) {

      this.$refs.addDialog.open(flag, row,this.userInfo.userId)

    },
    // 我的回收-拒收
    refuse(row) {
      this.$prompt('请输入拒收原因', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        // inputErrorMessage: '请输入卖家ID',
        type: 'warning'
      }).then(({ value }) => {
        let obj = {
          id: row.id,
          rmsg: value,
          status: 5,
        }
        onSelfApi.refauseMyOrder(obj).then(res => {
          console.log("%c Line:518 🍎拒收-我的回收 res", "color:#ea7e5c", res);
          if (res.code == 200) {
            this.getList()
            this.$message({
              type: "success",
              message: res.msg
            })
          }
        })
      }).catch(() => {

      });
    },
    // 回收联盟-拒收
    refuseLianmeng(row) {
      this.$prompt('请输入拒收原因', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        // inputPattern: /\S/,
        // inputErrorMessage: '请输入卖家ID',
        type: 'warning'
      }).then(({ value }) => {
        let obj = {
          backId: row.id,
          rmsg: value,
          status: 5,
        }
        onSelfApi.refauseLianmengOrder(obj).then(res => {
          console.log("%c Line:518 🍎拒收-回收联盟 res", "color:#ea7e5c", res);
          if (res.code == 200) {
            this.getList()
            this.$message({
              type: "success",
              message: res.msg
            })
            
          }
        })
      }).catch(() => {

      });
    },
    // 打开报价弹窗-我的回收
    editOrderMy(row) {
      console.log("%c Line:297 🍺 row", "color:#fca650", row);
      this.$refs.merchantRetrievePrice.open('edit', row)

    },
    // 打开查看图片弹窗
    viewImgs(row) {
      let arr = []
      if (row.img) {
        let list = JSON.parse(row.img)
        if (list.length) {
          list.forEach(element => {
            arr.push(element.url)
          });
        } else {
          this.$alert('暂无图片')
        }
      } else {
        this.$alert('暂无图片')
      }
      this.$refs.imageDialog.open(arr)
    },
    // 估价链接
    gujiaLink() {
      this.$prompt('请输入卖家ID', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请输入卖家ID',
        type: 'warning'
      }).then(({ value }) => {
        console.log('%c 🥜 value: ', 'font-size:20px;background-color: #465975;color:#fff;', value);
        // this.docopy(value)
        navigator.clipboard.writeText(value);
        alert('复制成功')

      }).catch(() => {

      });
    },
    docopy(val) {
      // console.log('%c 🍦 val: ', 'font-size:20px;background-color: #93C0A4;color:#fff;', val);
      let str = val
      console.log('%c 🥖 str: ', 'font-size:20px;background-color: #FCA650;color:#fff;', str);
      this.$copytext(str).then((e) => {
        alert('复制成功')
        console.log(e)
      })
    },
    // 获取h5链接
    getH5BasicUrl() {
      getDicts("finance_h5_host").then(res => {
        if (res.code == 200) {
          if (res.data.length > 0) {
            this.h5BasicUrl = res.data[0].dictValue

          }
        }
      });
    },
    // 复制回收链接
    huishouLink() {
      let str = this.h5BasicUrl + '/pages/merchantRecycling/selectGame?shopId=' + this.userInfo.userId
      this.$copyText(str).then((e) => {
        this.$alert('复制成功')
        console.log(e)
      })
    },
    // 打开回收配置弹窗
    huishouConfig() {
      getDicts("finance_h5_host").then(res => {
        if (res.code == 200) {
          if (res.data.length > 0) {
            let str = this.h5BasicUrl + '/pages/retrieveAccount/huishou-h5?shopId=' + this.userInfo.userId
            // let str = res.data[0].dictValue + '/pages/retrieveAccount/huishou-h5?shopId=' + this.userInfo.userId
            this.$refs.huishouConfig.open(this.userInfo, str)
          }
        }
      });

    },

    // 取消回收
    cancelOrder() {
      this.$prompt('请输入取消原因', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /\S/,
        inputErrorMessage: '请输入原因',
        type: 'warning'
      }).then(({ value }) => {
        console.log('%c 🥜 value: ', 'font-size:20px;background-color: #465975;color:#fff;', value);

      }).catch(() => {

      });

    },
    // 复制商品信息
    keyCopy(item) {
      var hoste = this.hoste.replace("xh.", "");
      hoste = hoste.replace("www.", "");
      hoste = hoste.replace("//", "//rt.");
      hoste = hoste.replace("/show.html?ZHID=", "");
      hoste = hoste + "/show.html?id=" + this.user.userId;

      let hostt = hoste + "&ZHID=" + item.id;
      this.copyVlaue =
        "商品ID：" +
        item.id +
        "\n" +
        "自编号：" +
        item.gameName +
        "\n" +
        "大区：" +
        item.regional +
        "\n" +
        "段位：" +
        item.level +
        "\n" +
        "英雄：" +
        item.heroes +
        "\n" +
        "皮肤：" +
        item.skins +
        "\n" +
        "售价：" +
        item.newPrice +
        "\n" +
        "简介：" +
        item.des +
        "\n" +
        "商品截图：" +
        hostt;
    },
    // 复制成功和失败的方法
    onCopy(e) {
      this.$modal.msgSuccess("复制成功");
      this.copyVlaue = "";
    },
    onError() {
      this.$modal.msgError("复制失败");
      this.copyVlaue = "";
    },
    //重置
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 20,
        type: '',
        des: '',
        id: '',

      };
      this.getList();
    },

    // 提号
    openPickUp(row) {
      this.$refs.pickDialog.open(row);
    },
    // 双击行查看详情图片
    showImage(row) {
      // var hoste = this.hoste.replace("xh.", "");
      // hoste = hoste.replace("www.", "");
      // hoste = hoste.replace("//", "//rt.");
      // hoste = hoste.replace("/show.html?ZHID=", "");
      // hoste = hoste + "/show.html?id=" + this.user.userId;

      // let hostt = hoste + "&ZHID=" + row.id;
      // this.$refs.h5Dialog.open(hostt);
    },
    // 每页条数改变
    handleSizeChange(val) {
      this.queryParams.pageSize = val;
      this.getList();
    },
    // 页码改变
    handleCurrentChange(val) {
      this.queryParams.pageNum = val;
      this.getList();
    }
  }
};
</script>
<style lang="scss" scoped>

.search-box{
  padding: 10px 5px 0;
 
}

.tipRole {
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 99;
  font-size: 30px;
  color: #fff;
  text-align: center;
  padding-top: 300px;
}

.zdy-des {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  margin: 6px 0;
}

.search-form-new ::v-deep .el-input {
  width: 200px !important;
}
</style>
