<template>
  <!-- 商户自己渠道回收列表 -->
  <div class="app-container jklis">
    <div class="zdy-main">
      <el-tabs type="border-card" v-model="actived" @tab-click="tabChange" v-loading="loading">
        <el-form
          :model="queryParams"
          ref="queryForm"
          :inline="true"
          class="zdy-form search-form-new"
          label-width="68px"
          @keyup.enter.native="search"
        >
          <el-form-item label="手机号" prop="id">
            <el-input v-model="queryParams.id" placeholder="请输入手机号" clearable size="small" />
          </el-form-item>
          <el-form-item label="编号" prop="des">
            <el-input v-model="queryParams.desQur" placeholder="请输入编号" clearable size="small" />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="search"
              class="zdy-btn"
            >搜索</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              icon="el-icon-refresh"
              type="primary"
              size="mini"
              plain
              @click="resetQuery"
              class="zdy-btn zdy-btn-refresh"
            >重置</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="openAdd"
              class="zdy-btn"
            >新增</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="gujiaLink"
              class="zdy-btn"
            >生成估价链接</el-button>
          </el-form-item>
        </el-form>
        <el-tab-pane :label="tab" :name="tab" v-for="tab in gameTypes" :key="tab">
          <el-table
            :data="tableData.records"
            @cell-dblclick="showImage"
            height="calc(100vh - 310px)"
            style="width: 100%"
          >
          <!-- id:'1',
            type:'王者荣耀',
            account:'123456',
            area:'安卓Q',
            spplierId:'tb3655422',
            spplierName:'蔡文昊',
            spplierIdCard:'411425199802035665',
            sys:'淘宝',
            kefuName:'客服1',
            kefuId:'tb3655422',
            des:'MK3629-安卓Q-贵族积分39392-V7-112英雄-233皮肤-300五级铭文-营地ID:537671723,-(可二次实名),17王者印记,339紫星币,251幸运值,2内测,蔷薇恋人,优雅恋人,神威,至尊宝,白虎志,怪兽玩代售,零号赤焰,逐梦之影,大圣娶亲,傲雪梅枪,拒霜思,孙行者,猪悟能,猎兽之王,星空之诺,银河之约,暗影游猎,诗剑行,原初追逐者,黎明之约,死神来了,梁祝,狂想玩偶喵,云间偶戏,未来纪元赵云,猫影幻舞,久胜战神,天秀音浪,原初探秘者,李小龙,水晶猎龙者,暗隐猎兽者,猫狗日记男,金属风暴,特工魅影,黄金分割率,修罗,魅语,千年之狐,紫霞仙子,功夫厨神,心灵骇客,圣诞恋歌,乘风破浪,圣诞狂欢,星夜王子,赏金猎手,节奏热浪,天才门将,偶像歌手,街头霸王,摇滚巨星,缤纷独角兽,鬼剑武藏,德古拉伯爵,赤莲之焰,单词大作战,花好人间,狰,饕餮,烛龙,重明,穷奇,御狮,御霄,御旌,御銮,朔风刀,混沌棋,疑决卦,醍醐杖,万华元夜,拳王,霸王丸,丁香结,科学大爆炸,打野34段,中路24段,省标,孙悟空,市标,赵云,百里守约,钟馗,区标很多',
            remark:'',
            price:'362.23',
            bindPhone:'13569885542',
            bindCode:'123456',
            reson:'卖家取消，价格磋商失败'-->
            <el-table-column prop="id" label="序号" width="80"></el-table-column>
            <el-table-column prop="type" label="游戏类型" width="120"></el-table-column>
            <el-table-column prop="account" label="游戏账号" width="120"></el-table-column>
            <el-table-column prop="psd" label="游戏密码" width="150"></el-table-column>
            <el-table-column prop="area" label="大区" width="150"></el-table-column>
            <el-table-column prop="des" label="介绍" min-width="260">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.des"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.des }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column prop="sys" label="回收渠道" width="100"></el-table-column>
            <el-table-column prop="spplierId" label="卖家id" width="100"></el-table-column>
            <el-table-column prop="spplierName" label="卖家姓名" width="100"></el-table-column>
            <el-table-column prop="spplierIdCard" label="卖家身份证号" width="170"></el-table-column>
            <el-table-column prop="kefuName" label="客服姓名" width="100"></el-table-column>
            <el-table-column prop="kefuId" label="客服id" width="100"></el-table-column>
            <el-table-column prop="" label="账号截图" width="100"></el-table-column>
            <el-table-column prop="" label="卖家信息附件" width="120"></el-table-column>
            <el-table-column prop="" label="附件" width="100"></el-table-column>
            <el-table-column prop="price" label="成交价格" width="100"></el-table-column>
            <el-table-column prop="bindPhone" label="绑定手机号" width="110"></el-table-column>
            <el-table-column prop="bindCode" label="绑定手机编号" width="100"></el-table-column>
            <el-table-column prop="reson" label="取消原因" width="150" v-if="actived=='取消回收'"></el-table-column>
            <el-table-column prop="remark" label="备注" min-width="200">
              <template slot-scope="scope">
                <el-tooltip
                  class="item"
                  effect="dark"
                  :content="scope.row.remark"
                  placement="top-start"
                >
                  <div class="zdy-des">{{ scope.row.remark }}</div>
                </el-tooltip>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120" v-if="actived=='回收中'">
              <template slot-scope="scope">
                <el-button type="text" size="mini" v-if="actived=='回收中'" @click="editOrder(scope.row)">下单</el-button>
                <el-button  type="text" style="color:red" size="mini" @click="cancelOrder(scope.row)">取消交易</el-button>
                <!-- <el-button
                  ref="copyBtn1"
                  type="text"
                  size="mini"
                  v-clipboard:copy="copyVlaue"
                  v-clipboard:error="onError"
                  v-clipboard:success="onCopy"
                  @click="keyCopy(scope.row)"
                >复制</el-button> -->
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
    <div class="pages-box">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryParams.pageNum"
        :page-sizes="[10, 20, 30, 40, 50, 60]"
        :page-size="queryParams.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="tableData.total"
      ></el-pagination>
    </div>
   <addDialog ref="addDialog"></addDialog>
  </div>
</template>

<script>
import addDialog from './components/add'
export default {
  name: "phoneManage",
  components: { addDialog},
  data() {
    return {
      gameTypes: [
      '回收中','回收完成','取消回收'
      ],
      tableData: {
        records: [
          // 游戏类型  游戏账号 游戏密码 大区 卖家id 卖家姓名  卖家身份证号  回收渠道  客服姓名  客服id  介绍  备注  账号截图  卖家信息附件  附件 成交价格 绑定手机号  绑定手机编号
          {
            id:'1',
            type:'王者荣耀',
            account:'123456',
            area:'安卓Q',
            spplierId:'tb3655422',
            spplierName:'蔡文昊',
            spplierIdCard:'411425199802035665',
            sys:'淘宝',
            kefuName:'客服1',
            kefuId:'tb3655422',
            des:'MK3629-安卓Q-贵族积分39392-V7-112英雄-233皮肤-300五级铭文-营地ID:537671723,-(可二次实名),17王者印记,339紫星币,251幸运值,2内测,蔷薇恋人,优雅恋人,神威,至尊宝,白虎志,怪兽玩代售,零号赤焰,逐梦之影,大圣娶亲,傲雪梅枪,拒霜思,孙行者,猪悟能,猎兽之王,星空之诺,银河之约,暗影游猎,诗剑行,原初追逐者,黎明之约,死神来了,梁祝,狂想玩偶喵,云间偶戏,未来纪元赵云,猫影幻舞,久胜战神,天秀音浪,原初探秘者,李小龙,水晶猎龙者,暗隐猎兽者,猫狗日记男,金属风暴,特工魅影,黄金分割率,修罗,魅语,千年之狐,紫霞仙子,功夫厨神,心灵骇客,圣诞恋歌,乘风破浪,圣诞狂欢,星夜王子,赏金猎手,节奏热浪,天才门将,偶像歌手,街头霸王,摇滚巨星,缤纷独角兽,鬼剑武藏,德古拉伯爵,赤莲之焰,单词大作战,花好人间,狰,饕餮,烛龙,重明,穷奇,御狮,御霄,御旌,御銮,朔风刀,混沌棋,疑决卦,醍醐杖,万华元夜,拳王,霸王丸,丁香结,科学大爆炸,打野34段,中路24段,省标,孙悟空,市标,赵云,百里守约,钟馗,区标很多',
            remark:'',
            price:'362.23',
            bindPhone:'13569885542',
            bindCode:'123456',
            reson:'卖家取消，价格磋商失败'
          }
        ],
        total: 0
      },
      actived: "回收中",
      loading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        
      },
    };
  },
  created() {
    // this.getGames();
    // this.getHost();
  },
  activated() {
    // this.getList();
  },
  methods: {
    // 获取游戏列表
    // getGames() {
    //   getGameList().then(res => {
    //     this.gameTypes = res.data;
    //     this.actived = res.data[0];
    //     this.queryParams.type = res.data[0];
    //     this.getList();
    //     this.getAreas();
    //   });
    // },
    // 获取列表
    getList() {
      this.loading = true;
      listAccount(this.queryParams).then(response => {
        this.tableData.records = response.rows;
        this.tableData.total = response.total;
        this.loading = false;
        if (response.rows && response.rows.length) {
          this.rise = response.rows[0].rise;
        }
      });
    },
  
    // tabs切换时触发
    tabChange() {
      this.queryParams.type = this.actived;
      // this.getList();
      // this.getAreas();
    },
  
    // 搜索
    search() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 20;
      this.getList();
    },
    // 打开新增弹窗
    openAdd(){
this.$refs.addDialog.open()
    },
    // 打开下单弹窗
    editOrder(row){
this.$refs.addDialog.open('edit',row)
    },
    // 
    // 估价链接
    gujiaLink(){
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
    docopy (val) {
      // console.log('%c 🍦 val: ', 'font-size:20px;background-color: #93C0A4;color:#fff;', val);
      let str = val
      console.log('%c 🥖 str: ', 'font-size:20px;background-color: #FCA650;color:#fff;', str);
    this.$copytext(str).then((e)=> {
     alert('复制成功')
     console.log(e)
    })
   },
    // 取消回收
    cancelOrder(){
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