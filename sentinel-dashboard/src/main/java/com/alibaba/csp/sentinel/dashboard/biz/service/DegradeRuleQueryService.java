package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;

import java.util.List;

public interface DegradeRuleQueryService {
    List<DegradeRuleEntity> getDegradeRuleEntityListByApp(String app);
}
