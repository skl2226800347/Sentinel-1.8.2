package com.alibaba.csp.sentinel.dashboard.enums;
/**
 * 返回状态码
 * @author shikelei
 */
public enum ResultCodeEnum {
    SYSTEM_ERROR(-1,"系统错误"),
    DATA_ERROR(1,"数据错误"),
    NO_LOGIN(100,"未登陆"),
    USER_NAME_IS_NULL(101,"用户名为空"),
    USER_NAME_IS_ERROR(105,"用户名错误"),
    PASSWORD_IS_NULL(110,"密码为空"),
    PASSWORD_IS_ERROR(115,"密码错误"),
    USER_NOT_EXISTS(116,"用户不存在"),
    USER_PASSWORD_IS_ERROR(117,"用户名、密码错误"),
    SUCCESS(200,"成功"),
    APP_IS_NULL(1000,"app为空"),
    ID_IS_NULL(1002,"id为空"),
    APP_NAME_IS_EXIST(1004,"应用名已存在"),
    APP_IS_EXIST(1006,"应用已存在")
    ;
    int code;
    String msg;
    ResultCodeEnum(int code,String msg){
        this.code =code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
