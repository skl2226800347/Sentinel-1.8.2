package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;

public interface FlowRuleConfigService {

    int createFlowRule(FlowRuleEntity entity,AuthService.AuthUser authUser);

    int updateFlowRule(FlowRuleEntity entity,AuthService.AuthUser authUser);

    int deleteFlowRule(Long id);

    void updateConfigCenterRuleFromDbBatch();
}
