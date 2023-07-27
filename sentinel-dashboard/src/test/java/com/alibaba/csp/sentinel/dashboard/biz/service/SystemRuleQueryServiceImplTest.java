package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SystemRuleQueryServiceImplTest extends AbstractTestCase {
    @Autowired
    private SystemRuleQueryService systemRuleQueryService;
    @Test
    public void getSystemRuleQueryListByApp(){
        String app="ZookeeperFooProviderBootstrapV2";
        List<SystemRuleEntity> systemRuleEntityList = systemRuleQueryService.getSystemRuleEntityList(app);
        System.out.println(JSONObject.toJSONString(systemRuleEntityList));
    }
}
