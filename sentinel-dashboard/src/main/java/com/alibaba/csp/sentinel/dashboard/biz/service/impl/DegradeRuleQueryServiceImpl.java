package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.biz.helper.DegradeRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.DegradeRuleQueryService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegradeRuleQueryServiceImpl extends AbstractService implements DegradeRuleQueryService {

    @Override
    public List<DegradeRuleEntity> getDegradeRuleEntityListByApp(String app) {
        List<DegradeRule> degradeRuleList = degradeRuleQueryManager.getDegradeRuleListByApp(app);
        return DegradeRuleHelper.convert(degradeRuleList);
    }
}
