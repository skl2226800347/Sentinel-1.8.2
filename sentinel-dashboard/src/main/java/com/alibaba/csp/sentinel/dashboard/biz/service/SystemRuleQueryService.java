package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;

import java.util.List;

public interface SystemRuleQueryService {

    List<SystemRuleEntity> getSystemRuleEntityList(String app);
}
