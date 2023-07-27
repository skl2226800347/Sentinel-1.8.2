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
package com.alibaba.csp.sentinel.starter.zookeeper.init;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.zookeeper.ZookeeperDataSource;
import com.alibaba.csp.sentinel.extension.common.config.SentinelRuleConfig;
import com.alibaba.csp.sentinel.extension.common.utils.AuthorityRuleParseUtil;
import com.alibaba.csp.sentinel.extension.common.utils.FlowRuleParseUtil;
import com.alibaba.csp.sentinel.extension.common.utils.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.starter.zookeeper.bean.SpringBeanUtil;
import com.alibaba.csp.sentinel.starter.zookeeper.constants.DemoConstants;
import com.alibaba.csp.sentinel.starter.zookeeper.entity.ClusterGroupEntity;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Eric Zhao
 */
public class StarterZookeeperInitFunc implements InitFunc {

    private static final String APP_NAME = AppNameUtil.getAppName();
    private static final String SEPARATOR = "@";
    private  String remoteAddress ;
    private  String groupId ;
    private  String flowRuleDataId ;
    private  String paramFlowRuleDataId;
    private String authorityRuleDataId;
    private String degradeRuleDataId;
    private String systemRuleDataId;
    private  String clusterClientConfigDataId;
    private  String clusterMapDataId;

    @Override
    public void init() throws Exception {
        SentinelRuleConfig sentinelRuleConfig = SpringBeanUtil.getBean(SentinelRuleConfig.class);
        this.remoteAddress =sentinelRuleConfig.getRemoteAddress();
        this.groupId = sentinelRuleConfig.getGroupId();
        this.flowRuleDataId =ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getFlowRuleDataIdPrefix());
        this.paramFlowRuleDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getParamFlowRuleDataIdPrefix());
        this.authorityRuleDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getAuthorityRuleDataIdPrefix());
        this.degradeRuleDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getDegradeRuleDataIdPrefix());
        this.systemRuleDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getSystemRuleDataIdPrefix());
        this.clusterClientConfigDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getClusterClientConfigDataIdPrefix());
        this.clusterMapDataId = ZookeeperConfigUtil.getDataId(APP_NAME,sentinelRuleConfig.getClusterMapDataIdPostfix());
        // Register client dynamic rule data source.
        initDynamicRuleProperty();

        // Register token client related data source.
        // Token client common config:
        initClientConfigProperty();
        // Token client assign config (e.g. target token server) retrieved from assign map:
        initClientServerAssignProperty();

        // Register token server related data source.
        // Register dynamic rule data source supplier for token server:
        registerClusterRuleSupplier();
        // Token server transport config extracted from assign map:
        initServerTransportConfigProperty();

        // Init cluster state property for extracting mode from cluster map data source.
        initStateProperty();
    }

    private void initDynamicRuleProperty() {
        //之前解析方式
        ReadableDataSource<String, List<FlowRule>> ruleSource = new ZookeeperDataSource<>(remoteAddress, groupId,
                flowRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
        FlowRuleManager.register2Property(ruleSource.getProperty());

        ReadableDataSource<String, List<ParamFlowRule>> paramRuleSource = new ZookeeperDataSource<>(remoteAddress, groupId,
                paramFlowRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
        ParamFlowRuleManager.register2Property(paramRuleSource.getProperty());


        ReadableDataSource<String, List<AuthorityRule>> authorityRuleSource = new ZookeeperDataSource<>(remoteAddress, groupId,
                authorityRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {}));
        AuthorityRuleManager.register2Property(authorityRuleSource.getProperty());

        ReadableDataSource<String, List<DegradeRule>> degradeRuleSource = new ZookeeperDataSource<>(remoteAddress, groupId,
                degradeRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {}));
        DegradeRuleManager.register2Property(degradeRuleSource.getProperty());

        ReadableDataSource<String, List<SystemRule>> systemRuleSource = new ZookeeperDataSource<>(remoteAddress, groupId,
                systemRuleDataId, source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {}));
        SystemRuleManager.register2Property(systemRuleSource.getProperty());
    }

    private void initClientConfigProperty() {
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs = new ZookeeperDataSource<>(remoteAddress, groupId,
                clusterClientConfigDataId, source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {}));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());
    }

    private void initServerTransportConfigProperty() {
        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new ZookeeperDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                .flatMap(this::extractServerTransportConfig)
                .orElse(null);
        });
        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());
    }

    private void registerClusterRuleSupplier() {
        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new ZookeeperDataSource<>(remoteAddress, groupId,
                namespace + DemoConstants.FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {}));
            return ds.getProperty();
        });
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new ZookeeperDataSource<>(remoteAddress, groupId,
                namespace + DemoConstants.PARAM_FLOW_POSTFIX, source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {}));
            return ds.getProperty();
        });
    }

    private void initClientServerAssignProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new ZookeeperDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                .flatMap(this::extractClientAssignment)
                .orElse(null);
        });
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
    }

    private void initStateProperty() {
        // Cluster map format:
        // [{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, Integer> clusterModeDs = new ZookeeperDataSource<>(remoteAddress, groupId,
            clusterMapDataId, source -> {
            List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {});
            return Optional.ofNullable(groupList)
                .map(this::extractMode)
                .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
        });
        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
    }

    private int extractMode(List<ClusterGroupEntity> groupList) {
        // If any server group machineId matches current, then it's token server.
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return ClusterStateManager.CLUSTER_SERVER;
        }
        // If current machine belongs to any of the token server group, then it's token client.
        // Otherwise it's unassigned, should be set to NOT_STARTED.
        boolean canBeClient = groupList.stream()
            .flatMap(e -> e.getClientSet().stream())
            .filter(Objects::nonNull)
            .anyMatch(e -> e.equals(getCurrentMachineId()));
        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
    }

    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
        return groupList.stream()
            .filter(this::machineEqual)
            .findAny()
            .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
    }

    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return Optional.empty();
        }
        // Build client assign config from the client set of target server group.
        for (ClusterGroupEntity group : groupList) {
            if (group.getClientSet().contains(getCurrentMachineId())) {
                String ip = group.getIp();
                Integer port = group.getPort();
                return Optional.of(new ClusterClientAssignConfig(ip, port));
            }
        }
        return Optional.empty();
    }

    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
        return getCurrentMachineId().equals(group.getMachineId());
    }

    private String getCurrentMachineId() {
        // Note: this may not work well for container-based env.
        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
    }

}