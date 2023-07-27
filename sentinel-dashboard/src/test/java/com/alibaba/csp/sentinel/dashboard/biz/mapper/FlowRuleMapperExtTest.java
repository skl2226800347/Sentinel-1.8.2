package com.alibaba.csp.sentinel.dashboard.biz.mapper;

import com.alibaba.csp.sentinel.dashboard.biz.AbstractTestCase;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

public class FlowRuleMapperExtTest extends AbstractTestCase {
    @Resource
    FlowRuleMapperExt flowRuleMapperExt ;
    @Test
    public void create(){
        FlowRule flowRule = new FlowRule();
        Date now = new Date();
        flowRule.setGmtCreate(now);
        flowRule.setGmtModified(now);
        flowRuleMapperExt.insertSelective(flowRule);
        System.out.println(JSONObject.toJSONString(flowRule));
    }
}
