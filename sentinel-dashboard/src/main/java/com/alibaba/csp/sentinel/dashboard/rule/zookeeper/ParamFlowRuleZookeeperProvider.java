package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base.AbstractDynamicRuleZookeeperProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("paramFlowRuleZookeeperProvider")
public class ParamFlowRuleZookeeperProvider extends AbstractDynamicRuleZookeeperProvider<List<ParamFlowRule>> {

    @Autowired
    Converter<String,List<ParamFlowRule>> stringToParamFlowRuleConverter;

    @Override
    public String getDataIdPrefix() {
        return sentinelRuleConfig.getParamFlowRuleDataIdPrefix();
    }

    @Override
    public Converter getConverter() {
        return stringToParamFlowRuleConverter;
    }
}
