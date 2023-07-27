package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FlowRuleQueryServiceImplTest extends AbstractTestCase {
    @Autowired
    FlowRuleQueryService flowRuleQueryService;
    @Test
    public void getFlowRuleEntityListByApp(){
        String app="ZookeeperFooProviderBootstrapV2";
        List<FlowRuleEntity> flowRuleEntities = flowRuleQueryService.getFlowRuleEntityListByApp(app);
        System.out.println(JSONObject.toJSONString(flowRuleEntities));
    }
}
