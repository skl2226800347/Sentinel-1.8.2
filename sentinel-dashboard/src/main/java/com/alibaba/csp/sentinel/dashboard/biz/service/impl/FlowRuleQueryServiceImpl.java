package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.helper.FlowRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.FlowRuleQueryService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlowRuleQueryServiceImpl extends AbstractService implements FlowRuleQueryService {
    private static final Logger logger = LoggerFactory.getLogger(FlowRuleQueryServiceImpl.class);

    @Override
    public List<FlowRuleEntity> getFlowRuleEntityListByApp(String app) {
        List<FlowRule> flowRuleList = flowRuleQueryManager.getFlowRuleListByApp(app);
        return FlowRuleHelper.convert(flowRuleList);
    }
}
