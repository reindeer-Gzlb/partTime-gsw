<template>
  <!-- 商城设置  公告设置 -->
  <div class="gonggao-box" v-loading="loading">
    <div class="lunbo-box radius">
      <!-- <formSubTitVue
        :title="'公告设置'"
        :tip="'温馨提示:公告最多可添加三条,如有更多需求请联系管理员'"
      >
        <el-button class="btn" @click="add" size="mini" type="primary"
          >发布公告</el-button
        >
      </formSubTitVue> -->
      <el-button class="btn" @click="add" type="primary">发布公告</el-button>
      <div class="gonggao-list">
        <div class="list-item flex" v-for="(item, i) in list" :key="i">
          <span class="index">{{ i + 1 }}.</span>
          <span class="notice-tit">{{ item.noticeTitle }}</span>
          <span class="btns">
            <el-button type="primary" size="mini" @click="edit(item)"
              >编辑</el-button
            >
            <el-button type="warning" size="mini" @click="del(item)"
              >删除</el-button
            >
          </span>
        </div>
      </div>
    </div>
    <addNotice ref="addNotice" @success="getList"></addNotice>
  </div>
</template>

<script>
import addNotice from "./add.vue";
import { listNotice, delNotice } from "@/api/system/notice.js";
import { mapGetters } from "vuex";
import { getUserProfile } from '@/api/system/user'
export default {
  name: "shopSet",
  components: { addNotice },
  data() {
    return {
      loading: false,
      uploadImgUrl: "",
      titleImgList: [],
      imgList: [],
      showUpload: false,
      editCurrent: null,
      userInfo: null,
      list: []
    };
  },
  created() {
    this.uploadImgUrl = process.env.VUE_APP_BASE_API + "/upload/image";
    this.getUserInfo()
  },
  methods: {
    async getUserInfo() {
      const res = await getUserProfile()
      console.log('res', res);
      this.userInfo = res.data
      this.getList()
    },
    getList() {
      this.list = [];
      this.loading = true;
      let obj = {
        pageNum: 1,
        pageSize: 100,
        createId: this.userInfo.userId,
        typeResource: 2
      };
      listNotice(obj)
        .then((response) => {
          //console.log("%c Line:60 🥔 response", "color:#4fff4B", response);
          this.$nextTick(() => {
            this.loading = false;
            this.list = JSON.parse(JSON.stringify(response.rows));
            // this.tableData.total = response.total;
          });
        })
        .catch((err) => {
          this.loading = false;
        });
    },
    // 点击发布按钮
    add() {
      // if(this.list.length>=3){
      //   return this.$message.error('最多可添加三条公告!')
      // }
      this.$refs.addNotice.handleAdd('', this.userInfo.userId);
    },
    // 点击编辑按钮
    edit(item) {
      this.$refs.addNotice.handleUpdate(item);
    },
    // 点击删除
    del(item) {
      const noticeIds = item.noticeId;
      this.$modal
        .confirm('是否确认删除公告编号为"' + noticeIds + '"的数据项？')
        .then(function () {
          return delNotice(noticeIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => { });
    },
  },
};
</script>

<style scoped lang="scss">
.gonggao-box {
  margin-top: 16px;
  .lunbo-box {
    padding: 16px;
    background-color: #fff;
  }
  .btn {
    margin-left: 10px;
  }
  .gonggao-list {
    padding: 20px;
    .list-item {
      height: 44px;
      line-height: 44px;
      // color: $color666;
      .index {
        width: 25px;
      }
      .notice-tit {
        width: calc(100% - 200px);
        // border-bottom: 1px solid $grey30;
        cursor: pointer;
        margin-right: 15px;
        &:hover {
          // background-color: $grey10;
        }
      }
    }
  }
}
</style>
