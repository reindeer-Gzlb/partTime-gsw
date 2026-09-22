package com.ruoyi.web.controller.upload.vo;

/**
 * 上传状态：0：未上传，1：部分上传，2：全部上传
 */
public enum UploadStatusEnum {

    NONE(0, "未上传"),
    PART_COMPLETE(1, "部分上传"),
    ALL_COMPLETE(2, "全部上传");

    UploadStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }


    public static boolean isPartCompleted(Integer statusCode) {
        return UploadStatusEnum.PART_COMPLETE.getCode().equals(statusCode);
    }

    public static boolean isAllCompleted(Integer statusCode) {
        return UploadStatusEnum.ALL_COMPLETE.getCode().equals(statusCode);
    }

    public static boolean isNoneCompleted(Integer statusCode) {
        return UploadStatusEnum.NONE.getCode().equals(statusCode);
    }
}
