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
package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.base.AbstractDynamicRuleZookeeperProvider;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("authorityRuleZookeeperProvider")
public class AuthorityRuleZookeeperProvider extends AbstractDynamicRuleZookeeperProvider<List<AuthorityRule>> {
    @Autowired
    private Converter<String, List<AuthorityRule>> stringToAuthorityRuleConverter;

    @Override
    public String getDataIdPrefix() {
        return sentinelRuleConfig.getAuthorityRuleDataIdPrefix();
    }

    @Override
    public Converter<String, List<AuthorityRule>> getConverter() {
        return stringToAuthorityRuleConverter;
    }

}