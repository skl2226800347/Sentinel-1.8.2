package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base;

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.extension.common.utils.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.util.AssertUtil;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;

import java.util.List;

public abstract class AbstractDynamicRuleZookeeperPublisher<T> extends AbstractDynamicRuleZookeeperService  implements DynamicRulePublisher<T> {

    @Autowired
    @Qualifier("sentinelRuleConfig")
    protected SentinelRuleConfig sentinelRuleConfig;

     public abstract Converter<T, String>  getConverter();

    @Override
    public void publish(String app, T rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        String dataId=ZookeeperConfigUtil.getDataId(app,getDataIdPrefix());
        String zkPath = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getGroupId(),dataId);
        if (!isExists(zkPath)) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(zkPath, null);
        }
        byte[] data = CollectionUtils.isEmpty((List)rules) ? "[]".getBytes() : getConverter().convert(rules).getBytes();
        zkClient.setData().forPath(zkPath, data);
    }

}
