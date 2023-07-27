/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.demo.zookeeper.lock.config;

import com.alibaba.csp.sentinel.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.demo.zookeeper.common.utils.ZookeeperConfigUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ZookeeperConfig {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);


    @Bean(name="zkClient")
    public CuratorFramework zkClient(@Qualifier("sentinelRuleConfig") SentinelRuleConfig sentinelRuleConfig) {
        CuratorFramework zkClient =
                CuratorFrameworkFactory.newClient(sentinelRuleConfig.getRemoteAddress(),
                        new ExponentialBackoffRetry(ZookeeperConfigUtil.SLEEP_TIME, ZookeeperConfigUtil.RETRY_TIMES));
        zkClient.start();
        boolean isConnected = false;
        try {
            isConnected = zkClient.blockUntilConnected(ZookeeperConfigUtil.CONNECTED_TIME, TimeUnit.MILLISECONDS);
        }catch (Throwable e) {
            logger.error("[zkClient] e={},error={}",e,e.getMessage());
        }
        if (!isConnected) {
            throw new IllegalArgumentException("connected zk cluster failure");
        }

        return zkClient;
    }
}