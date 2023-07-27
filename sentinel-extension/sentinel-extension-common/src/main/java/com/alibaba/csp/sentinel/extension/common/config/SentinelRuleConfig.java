package com.alibaba.csp.sentinel.extension.common.config;
import com.alibaba.csp.sentinel.util.StringUtil;
import java.io.Serializable;
/**
 * sentinel rule config class
 * @author shikelei
 */
public class SentinelRuleConfig implements Serializable {
    private static final String DEFAULT_ROOT="sentinel";
    private static final String DEFAULT_LOCK_PARENT="lock";
    private static final String GROUP_ID_DEFAULT="sentinel_group";
    private static final String DEFAULT_FLOW_RULE_DATA_ID_PREFIX="flow-rules";
    private static final String DEFAULT_PARAM_FLOW_RULE_DATA_ID_PREFIX="param-flow-rules";
    private static final String DEFAULT_AUTHORITY_RULE_DATA_ID_PREFIX="authority-rules";
    private static final String DEFAULT_DEGRADE_RULE_DATA_ID_PREFIX="degrade-rules";
    private static final String DEFAULT_SYSTEM_RULE_DATA_ID_PREFIX="system-rules";
    private static final String DEFAULT_CLUSTER_CLIENT_CONFIG_DATA_ID_PREFIX="cluster-client-config";
    private static final String DEFAULT_CLUSTER_MAP_DATA_ID_PREFIX="cluster-map";

    private String remoteAddress;
    private String root;
    private String lockParent;
    private String groupId;
    private String flowRuleDataIdPrefix;
    private String authorityRuleDataIdPrefix;
    private String degradeRuleDataIdPrefix;
    private String paramFlowRuleDataIdPrefix;
    private String systemRuleDataIdPrefix;
    private String clusterClientConfigDataIdPrefix;
    private String clusterMapDataIdPrefix;

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getRoot() {
        if (StringUtil.isEmpty(root)) {
            return DEFAULT_ROOT;
        }
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getLockParent() {
        if (StringUtil.isEmpty(lockParent)) {
            return DEFAULT_LOCK_PARENT;
        }
        return lockParent;
    }

    public void setLockParent(String lockParent) {
        this.lockParent = lockParent;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getGroupId() {
        if(StringUtil.isBlank(groupId)){
            return  GROUP_ID_DEFAULT;
        }
        return groupId;
    }
    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFlowRuleDataIdPrefix() {
        if (StringUtil.isBlank(flowRuleDataIdPrefix)) {
            return DEFAULT_FLOW_RULE_DATA_ID_PREFIX;
        }
        return flowRuleDataIdPrefix;
    }

    public void setFlowRuleDataIdPrefix(String flowRuleDataIdPrefix) {
        this.flowRuleDataIdPrefix = flowRuleDataIdPrefix;
    }

    public String getAuthorityRuleDataIdPrefix() {
        if (StringUtil.isBlank(authorityRuleDataIdPrefix)){
            return DEFAULT_AUTHORITY_RULE_DATA_ID_PREFIX;
        }
        return authorityRuleDataIdPrefix;
    }

    public void setAuthorityRuleDataIdPrefix(String authorityRuleDataIdPrefix) {
        this.authorityRuleDataIdPrefix = authorityRuleDataIdPrefix;
    }

    public String getDegradeRuleDataIdPrefix() {
        if (StringUtil.isBlank(degradeRuleDataIdPrefix)){
            return DEFAULT_DEGRADE_RULE_DATA_ID_PREFIX;
        }
        return degradeRuleDataIdPrefix;
    }

    public void setDegradeRuleDataIdPrefix(String degradeRuleDataIdPrefix) {
        this.degradeRuleDataIdPrefix = degradeRuleDataIdPrefix;
    }

    public String getParamFlowRuleDataIdPrefix() {
        if (StringUtil.isBlank(paramFlowRuleDataIdPrefix)){
            return DEFAULT_PARAM_FLOW_RULE_DATA_ID_PREFIX;
        }
        return paramFlowRuleDataIdPrefix;
    }

    public void setParamFlowRuleDataIdPrefix(String paramFlowRuleDataIdPrefix) {
        this.paramFlowRuleDataIdPrefix = paramFlowRuleDataIdPrefix;
    }

    public String getSystemRuleDataIdPrefix() {
        if(StringUtil.isBlank(systemRuleDataIdPrefix)) {
            return DEFAULT_SYSTEM_RULE_DATA_ID_PREFIX;
        }
        return systemRuleDataIdPrefix;
    }

    public void setSystemRuleDataIdPrefix(String systemRuleDataIdPrefix) {
        this.systemRuleDataIdPrefix = systemRuleDataIdPrefix;
    }

    public String getClusterClientConfigDataIdPrefix() {
        if(StringUtil.isBlank(clusterClientConfigDataIdPrefix)) {
            return DEFAULT_CLUSTER_CLIENT_CONFIG_DATA_ID_PREFIX;
        }
        return clusterClientConfigDataIdPrefix;
    }

    public void setClusterClientConfigDataIdPrefix(String clusterClientConfigDataIdPrefix) {
        this.clusterClientConfigDataIdPrefix = clusterClientConfigDataIdPrefix;
    }

    public String getClusterMapDataIdPostfix() {
        if(StringUtil.isBlank(clusterMapDataIdPrefix)) {
            return DEFAULT_CLUSTER_MAP_DATA_ID_PREFIX;
        }
        return clusterMapDataIdPrefix;
    }

    public void setClusterMapDataIdPrefix(String clusterMapDataIdPrefix) {
        this.clusterMapDataIdPrefix = clusterMapDataIdPrefix;
    }

}
