import request from "@/utils/request";

let imageApi = {
  // 获取图片列表
  getList(type) {
    return request({
      url: `/account/zhubo/list?type=${type}`,
      method: "get",
    });
  },
  getLists(type, createId) {
    return request({
      url: `/account/zhubo/list?type=${type}&createId=${createId}`,
      method: "get",
    });
  },
  // 查询QQ群组
  getgroupLists() {
    return request({
      url: `account/group/list?typeResource=2`,
      method: "get",
    });
  },
  // 新增QQ群组
  addgroupLists(data) {
    return request({
      url: `account/group`,
      method: "post",
      data:data
    });
  },
  // 删除QQ群组
  deleteGroupLists(id) {
    return request({
      url: `account/group/`+ id,
      method: "delete",
    });
  },
  // 修改QQ群组
  editGroupLists(data) {
    return request({
      url: `account/group`,
      method: "put",
      data: data,
    });
  },
  //新增图片
  addImage(data) {
    return request({
      url: "/account/zhubo",
      method: "post",
      data: data,
    });
  },
  //H5新增图片
  H5addImage(data) {
    return request({
      url: "/account/zhubo",
      method: "post",
      data: data,
    });
  },
  //H5新增图片
  H5EditImage(data) {
    return request({
      url: "/account/zhubo",
      method: "put",
      data: data,
    });
  },
  //查询H5新增图片
  getH5addImage() {
    return request({
      url: "/account/zhubo/list",
      method: "get",
    });
  },
  //删除H5图片
  H5delImage(id) {
    return request({
      url: "/account/zhubo/" + id,
      method: "delete",
    });
  },
  //删除图片
  delImage(id) {
    return request({
      url: "/account/zhubo/" + id,
      method: "delete",
    });
  },
  //修改
  editImage(data) {
    return request({
      url: "/account/zhubo",
      method: "put",
      data: data,
    });
  },
  //修改
  editImage(data) {
    return request({
      url: "account/zhubo/list",
      method: "post",
      data: data,
    });
  },
  //banner
  bannerEditImage(data) {
    return request({
      url: "/sys/logo",
      method: "post",
      data: data,
    });
  },
  //banner logo 查询
  bannerQueryImage() {
    return request({
      url: "/sys/logo/list",
      method: "get",
    });
  },
  //banner logo 删除
  bannerLogoDelete(id) {
    return request({
      url: "/sys/logo/" + id,
      method: "delete",
    });
  },
};
export default imageApi;
