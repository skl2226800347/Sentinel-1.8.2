package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.helper.ParamFlowRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.ParamFlowRuleQueryService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParamFlowRuleQueryServiceImpl extends AbstractService implements ParamFlowRuleQueryService {
    @Override
    public List<ParamFlowRuleEntity> getParamFlowRuleListByApp(String app) {
        List<ParamFlowRule> paramFlowRuleList = paramFlowRuleQueryManager.getParamFlowRuleListByApp(app);
        return ParamFlowRuleHelper.convert(paramFlowRuleList);
    }
}
