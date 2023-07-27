package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;

public interface SystemRuleConfigService {
    int createSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser);

    int updateSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser);

    int deleteSystemRule(Long id);

    void updateConfigCenterRuleFromDbBatch();
}
