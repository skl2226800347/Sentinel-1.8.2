package com.alibaba.csp.sentinel.dashboard.biz.service;
/**
 *
 */
public interface RuleSyncService {

    /**
     * 配置中心批量从db同步规则数据
     */
    void updateConfigCenterRuleFromDbBatch();
}
