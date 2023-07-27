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
package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.extension.common.utils.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class ZookeeperConfig {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);


    @Bean(name="authorityRuleToStringConverter")
    public Converter<List<AuthorityRule>, String> authorityRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean("stringToAuthorityRuleConverter")
    public Converter<String, List<AuthorityRule>> authorityRuleEntityDecoder() {
        return s -> JSON.parseArray(s, AuthorityRule.class);
    }

    @Bean(name="flowRuleToStringConverter")
    public Converter<List<FlowRule>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean(name="stringToFlowRuleConverter")
    public Converter<String, List<FlowRule>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRule.class);
    }



    @Bean(name="degradeRuleToStringConverter")
    public Converter<List<DegradeRule>, String> degradeRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean(name="stringToDegradeRuleConverter")
    public Converter<String, List<DegradeRule>> degradeRuleEntityDecoder() {
        return s -> JSON.parseArray(s, DegradeRule.class);
    }

    @Bean(name="paramFlowRuleToStringConverter")
    public Converter<List<ParamFlowRule>, String> paramFlowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean(name="stringToParamFlowRuleConverter")
    public Converter<String, List<ParamFlowRule>> paramFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, ParamFlowRule.class);
    }

    @Bean(name="systemRuleToStringConverter")
    public Converter<List<SystemRule>, String> systemRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean(name="stringToSystemRuleConverter")
    public Converter<String, List<SystemRule>> systemRuleEntityDecoder() {
        return s -> JSON.parseArray(s, SystemRule.class);
    }

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