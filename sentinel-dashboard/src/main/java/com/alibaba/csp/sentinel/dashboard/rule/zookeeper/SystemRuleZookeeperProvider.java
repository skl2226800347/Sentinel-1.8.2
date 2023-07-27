package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base.AbstractDynamicRuleZookeeperProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("systemRuleZookeeperProvider")
public class SystemRuleZookeeperProvider extends AbstractDynamicRuleZookeeperProvider<List<SystemRule>> {

    @Autowired
    Converter<String,List<SystemRule>> stringToSystemRuleConverter;

    @Override
    public String getDataIdPrefix() {
        return sentinelRuleConfig.getSystemRuleDataIdPrefix();
    }

    @Override
    public Converter getConverter() {
        return stringToSystemRuleConverter;
    }
}
