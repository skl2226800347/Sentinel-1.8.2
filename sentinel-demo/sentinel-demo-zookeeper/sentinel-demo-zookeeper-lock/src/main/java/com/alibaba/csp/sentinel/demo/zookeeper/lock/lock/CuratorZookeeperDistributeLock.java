package com.alibaba.csp.sentinel.demo.zookeeper.lock.lock;

import com.alibaba.csp.sentinel.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.demo.zookeeper.common.utils.ZookeeperConfigUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("curatorZookeeperDistributeLock")
public class CuratorZookeeperDistributeLock {
    @Autowired
    @Qualifier("sentinelRuleConfig")
    SentinelRuleConfig sentinelRuleConfig;

    @Autowired
    @Qualifier("zkClient")
    private CuratorFramework zkClient;

    /**
     * 获取分页式可重入排它锁
     * @param key key
     * @return 锁
     */
    public InterProcessMutex acquireLock(String key) {
        String path = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getRoot(),sentinelRuleConfig.getLockParent(),key);
        InterProcessMutex interProcessMutex = new InterProcessMutex(zkClient,path);
        return interProcessMutex;
    }
    /**
     * 获取分布式排它锁
     * @param key key
     * @return 锁
     */
    public InterProcessSemaphoreMutex acquireSemaphoreLock(String key){
        String path = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getRoot(),sentinelRuleConfig.getLockParent(),key);
        InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(zkClient,path);
        return interProcessSemaphoreMutex;
    }

    /**
     * 获取颁布式读写锁
     * @param key key
     * @return 锁
     */
    public InterProcessReadWriteLock acquireReadWriteLock(String key){
        String path = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getRoot(),sentinelRuleConfig.getLockParent(),key);
        InterProcessReadWriteLock interProcessReadWriteLock = new InterProcessReadWriteLock(zkClient,path);
        return interProcessReadWriteLock;
    }
    /**
     *将多个锁作为单个实体管理内容
     * @param keys key集合。
     */
    public InterProcessMultiLock acquireMultiLock(List<String> keys) {
        List<String> paths = new ArrayList<>();
        keys.stream().forEach(key->{
            String path = ZookeeperConfigUtil.getPath(sentinelRuleConfig.getRoot(),sentinelRuleConfig.getLockParent(),key);
            paths.add(path);
        });
        InterProcessMultiLock interProcessMultiLock = new InterProcessMultiLock(zkClient,paths);
        return interProcessMultiLock;
    }

}
