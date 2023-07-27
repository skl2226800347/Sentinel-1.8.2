package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DegradeRuleQueryServiceImplTest extends AbstractTestCase {
    @Autowired
    private DegradeRuleQueryService degradeRuleQueryService;

    @Test
    public void getDegradeRuleEntityListByApp(){
        String app="ZookeeperFooProviderBootstrapV2";
        List<DegradeRuleEntity> degradeRuleEntityList =degradeRuleQueryService.getDegradeRuleEntityListByApp(app);
        System.out.println(JSONObject.toJSONString(degradeRuleEntityList));
    }
}
