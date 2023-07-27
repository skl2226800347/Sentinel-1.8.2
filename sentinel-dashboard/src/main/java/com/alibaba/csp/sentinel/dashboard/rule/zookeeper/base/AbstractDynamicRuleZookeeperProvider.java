package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base;

import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.extension.common.utils.ZookeeperConfigUtil;

import java.util.ArrayList;

public abstract class AbstractDynamicRuleZookeeperProvider<T> extends AbstractDynamicRuleZookeeperService implements DynamicRuleProvider<T> {

    public abstract Converter<String, T>  getConverter();

    @Override
    public T getRules(String appName) throws Exception{
        String dataId=ZookeeperConfigUtil.getDataId(appName,getDataIdPrefix());
        String zkPath = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getGroupId(),dataId);
        if (!isExists(zkPath)){
            return (T)new ArrayList<>(0);
        }
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return (T)new ArrayList<>();
        }
        String s = new String(bytes);

        return getConverter().convert(s);
    }

}
