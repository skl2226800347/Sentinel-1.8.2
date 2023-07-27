package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;

public interface ParamFlowRuleConfigService {
    int createParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity, AuthService.AuthUser authUser);

    int updateParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity,AuthService.AuthUser authUser);

    int deleteParamFlowRuleEntity(Long id);

    void updateConfigCenterRuleFromDbBatch();
}
