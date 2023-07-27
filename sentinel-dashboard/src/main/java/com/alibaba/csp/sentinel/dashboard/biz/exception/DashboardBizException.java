package com.alibaba.csp.sentinel.dashboard.biz.exception;

public class DashboardBizException extends RuntimeException{
    private String code;
    public DashboardBizException(String msg){
        super(msg);
    }
    public DashboardBizException(String code,String msg){
        super(msg);
        this.code =code;
    }

}
