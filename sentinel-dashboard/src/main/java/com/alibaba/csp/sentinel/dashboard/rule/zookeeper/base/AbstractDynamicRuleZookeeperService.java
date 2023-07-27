package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base;

import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class AbstractDynamicRuleZookeeperService {
    @Autowired
    protected CuratorFramework zkClient;

    @Autowired
    @Qualifier("sentinelRuleConfig")
    protected SentinelRuleConfig sentinelRuleConfig;

    public abstract String getDataIdPrefix();



    public boolean isExists(String path) throws Exception{
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat != null) {
            return true;
        }
        return false;
    }
}
