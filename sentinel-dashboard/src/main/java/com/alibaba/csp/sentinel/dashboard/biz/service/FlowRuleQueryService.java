package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;

import java.util.List;

public interface FlowRuleQueryService {
    List<FlowRuleEntity> getFlowRuleEntityListByApp(String app);
}
