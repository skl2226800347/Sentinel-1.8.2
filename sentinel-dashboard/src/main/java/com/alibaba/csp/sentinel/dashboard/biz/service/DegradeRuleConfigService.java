package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;

public interface DegradeRuleConfigService {
    int createDegradeRule(DegradeRuleEntity degradeRuleEntity, AuthService.AuthUser authUser);

    int updateDegradeRule(DegradeRuleEntity degradeRuleEntity,AuthService.AuthUser authUser);

    int deleteDegradeRule(Long id);

    void updateConfigCenterRuleFromDbBatch();
}
