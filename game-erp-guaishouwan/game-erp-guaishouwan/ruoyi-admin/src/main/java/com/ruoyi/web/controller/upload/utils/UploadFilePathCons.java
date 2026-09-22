package com.ruoyi.web.controller.upload.utils;

public interface UploadFilePathCons {
    /**
     * 车型图片保存地址
     */
    String UPLOAD_VEHICLE_MODEL_IMAGE_PATH = "/download/carModel/pic";
    /**
     * 差分下载路径
     */
    String DIFF_PACKAGE_PATH = "/download/package";
    /**
     * 整包下载路径
     */
    String PACKAGE_PATH = "/download/package";

    String PACKAGE_CHUNK = "download/chunk"; // 整包下载路径

    String IMPORT_EXCEL_PART_TYPE_PATH = "/temp/import/partType";

    String IMPORT_EXCEL_PART_PATH = "/temp/import/part";

    String IMPORT_EXCEL_SYS_DIC_PATH = "/temp/import/sysDic";

    String IMPORT_EXCEL_VEHICLE_PATH = "/temp/import/vehicle";

    String UPLOAD_STRATEGY_APPLY_REPORT = "/download/strategy/apply/report";

    String UPLOAD_STRATEGY_TEST_REPORT = "/download/strategy/test/report";

    String IMPORT_EXCEL_PUSH_VEHICLE_PATH = "/temp/import/pushVehicle";

    /**
     * 任务名单路径
     */
    String TASK_UPGRADE_LIST = "/temp/import/task";

    /**
     * 车型指导入excel临时表
     */
    String IMPORT_EXCEL_VEHICLE_MODEL_PATH = "/temp/import/vehicleModel";

    /**
     * 统计车辆升级详情导出
     */
    String EXPORT_VEHICLE_UPGRADE_DETAIL = "/download/export/upgradeDetail/temp/";

}
