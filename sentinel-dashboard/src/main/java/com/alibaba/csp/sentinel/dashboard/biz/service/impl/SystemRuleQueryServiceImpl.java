package com.alibaba.csp.sentinel.dashboard.biz.service.impl;
import com.alibaba.csp.sentinel.dashboard.biz.helper.SystemRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.SystemRuleQueryService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SystemRuleQueryServiceImpl extends AbstractService implements SystemRuleQueryService {

    @Override
    public List<SystemRuleEntity> getSystemRuleEntityList(String app) {
        List<com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule> systemRuleList =systemRuleQueryManager.getSystemRuleListByApp(app);
        return SystemRuleHelper.convert(systemRuleList);
    }
}
