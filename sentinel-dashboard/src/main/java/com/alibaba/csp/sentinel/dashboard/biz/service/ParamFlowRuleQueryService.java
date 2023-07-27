package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;

import java.util.List;

public interface ParamFlowRuleQueryService {
    List<ParamFlowRuleEntity> getParamFlowRuleListByApp(String app);
}
