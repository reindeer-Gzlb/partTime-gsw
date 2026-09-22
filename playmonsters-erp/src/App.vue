<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default  {
  name:  'App',
    metaInfo() {
        return {
            title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
            titleTemplate: title => {
                return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
            }
        }
    }
}
</script>
<style scoped>
@import "https://g.alicdn.com/de/prismplayer/2.9.7/skins/default/aliplayer-min.css";
</style>
<style>
/* 弹出框公共样式 */
.el-dialog__header{
  background-color: rgb(48, 65, 86);
  padding-top: 10px;
}
.el-dialog__title{
  color: #fff;
}
.el-dialog__headerbtn{
  top: 10px;
}
.pages-box{
  margin-top: 10px;
}
/* 表格最小高度 */
.el-table .el-table__cell{
  padding: 12px 0 !important;
}
/* tabs 样式 */
.customTab .el-tabs__nav-scroll{
  background-color:#fff ;
  border-bottom: 2px solid #1890ff;
 
}
.customTab .el-tabs__item{
  /* border: 1px solid rgba(24,144,255,0.7) !important; */
  font-weight: 600;
  /* border-bottom: 2px solid #1890ff !important; */
}
.customTab .el-tabs--border-card > .el-tabs__header .el-tabs__item{
  color: #333;
}
.customTab .el-tabs--border-card > .el-tabs__header .el-tabs__item.is-active{
  color: #fff ;
  background-color: #1890ff;
}
.zdy-main{
  position: relative;
  
}
.zdy-main .tip{
    color: #d11010;
    text-decoration: underline;
    cursor: pointer;
    font-weight: 600;
    font-size: 14px;
    position: absolute;
    top: 10px;
    right: 50px;
    z-index: 99;
  }
  .el-input--medium .el-input__inner{
    text-align: left ;
  }
  /* 表格提示弹出 */
  .el-tooltip__popper.is-dark{
  max-height: 500px !important;
  overflow-y: auto !important;
}
</style>
