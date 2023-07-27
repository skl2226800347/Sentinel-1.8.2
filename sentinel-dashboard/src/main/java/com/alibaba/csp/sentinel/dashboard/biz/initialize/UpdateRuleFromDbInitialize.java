package com.alibaba.csp.sentinel.dashboard.biz.initialize;

import com.alibaba.csp.sentinel.dashboard.biz.lock.CuratorZookeeperDistributeLock;
import com.alibaba.csp.sentinel.dashboard.biz.service.RuleSyncService;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class UpdateRuleFromDbInitialize {
    private static final Logger logger = LoggerFactory.getLogger(UpdateRuleFromDbInitialize.class);
    private static final String KEY="UpdateRuleFromDbInitialize";
    private static final int LOCK_TIMEOUT=180000;
    @Resource
    RuleSyncService ruleSyncService;

    @Autowired
    @Qualifier("curatorZookeeperDistributeLock")
    private CuratorZookeeperDistributeLock curatorZookeeperDistributeLock;

    @PostConstruct
    public void init()throws Exception{
        /**
         * use distribute lock
         */
        InterProcessMutex interProcessMutex = curatorZookeeperDistributeLock.acquireLock(KEY);
        boolean acquireLock = interProcessMutex.acquire(LOCK_TIMEOUT, TimeUnit.MILLISECONDS);
        logger.info("[init]  acquireLock={}",acquireLock);
        if (!acquireLock) {
            return ;
        }
        try {
            if (true) return;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    ruleSyncService.updateConfigCenterRuleFromDbBatch();
                }
            };
            new Thread(runnable).start();
        }finally {
            interProcessMutex.release();
        }
    }
}
