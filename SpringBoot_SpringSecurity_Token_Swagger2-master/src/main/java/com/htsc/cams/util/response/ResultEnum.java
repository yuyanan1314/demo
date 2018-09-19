package com.htsc.cams.util.response;

/**
 * Created by shiliangdong on 2017/4/15.
 */
public enum ResultEnum {
    UNKNOW_ERROR(-1, "系统内部错误"),
    SUCCESS(0, "成功");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

//	public void setCode(Integer code){
//		this.code = code;
//	}

    public String getMsg() {
        return msg;
    }

//	public void setMsg(String msg){
//		this.msg = msg;
//	}

}
