package com.alibaba.csp.sentinel.demo.zookeeper.common.utils;

public final class StringUtils {
    public static final boolean isBlank(String str) {
        if (str == null || str.equals("")){
            return true;
        }
        return false;
    }
}
