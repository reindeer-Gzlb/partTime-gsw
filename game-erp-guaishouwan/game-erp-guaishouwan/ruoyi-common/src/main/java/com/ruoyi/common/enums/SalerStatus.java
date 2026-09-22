package com.ruoyi.common.enums;

/**
 * 用户状态
 *
 * @author ruoyi
 */
public enum SalerStatus
{
//     0、待审核 1、未出售 2、出售中 3、已售 4、问题账号 5、找回账号 6、超时未售 7、自售账号

    audit(0, "待审核"),
    unsold(1, "未出售"),
    sold(2, "出售中"),
    InTheSale(3, "已售"),
    problem(4, "问题账号"),
    back(5, "找回账号"),
    timeout(6, "超时未售"),
    sinceSale(7, "自售账号"),
    shelves(8, "已下架"),
    ;


    private final Integer code;
    private final String info;

    SalerStatus(Integer code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public static String getInfo(Integer code){
        if (code == audit.code){
            return audit.getInfo();
        }else if (code == unsold.code){
            return unsold.getInfo();
        }else if (code == sold.getCode()){
            return sold.getInfo();
        }else if (code == InTheSale.code){
            return InTheSale.getInfo();
        }else if (code == problem.code){
            return problem.getInfo();
        }else if (code == back.code){
            return back.getInfo();
        }else if (code == timeout.code){
            return timeout.getInfo();
        }else if (code == sinceSale.code){
            return sinceSale.getInfo();
        }else if (code == shelves.code){
            return shelves.getInfo();
        }
        return null;
    }

    public Integer getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
