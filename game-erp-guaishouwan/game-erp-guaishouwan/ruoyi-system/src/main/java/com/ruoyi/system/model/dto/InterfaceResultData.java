package com.ruoyi.system.model.dto;

public class InterfaceResultData {
    /**
     * 状态编码code
     */
    private Integer code;
    /**
     * 状态对应值
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 返回数据
     */
    private Object errorMsg;


    public InterfaceResultData() {
    }

    public InterfaceResultData(Integer code, String msg, Object data) {
        super();
        init(code,msg,data);
    }

    public InterfaceResultData resultData(ResultEnum resultEnum) {
        init(resultEnum.getCode(),resultEnum.getMsg(),null);
        return this;
    }

    public InterfaceResultData resultData(ResultEnum resultEnum, Object data) {
        init(resultEnum.getCode(),resultEnum.getMsg(),data);
        return this;
    }
    private void init(Integer code,String msg, Object data){
        if(code!=null) {
            this.code = code;
        }
        if(msg!=null) {
            this.msg = msg;
        }
        if(data!=null) {
            this.data = data;
        }
    }
    public InterfaceResultData(ResultEnum resultEnum, Object data) {
        init(resultEnum.getCode(), resultEnum.getMsg(), data);
    }


    public Integer getCode() {
        return code;
    }

    public InterfaceResultData setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public InterfaceResultData setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public InterfaceResultData setData(Object data) {
        this.data = data;
        return this;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public static InterfaceResultData succ(Object data) {
        return new InterfaceResultData(ResultEnum.SUCCESS, data);
    }

    public InterfaceResultData setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
