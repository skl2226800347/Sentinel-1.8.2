package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

public class FlowRuleHelper {
    private static final String OPER_ID="SYSTEM";

    public static final  <R> Result<R> checkEntityInternal(FlowRuleEntity entity) {
        if (entity == null) {
            return Result.ofFail(-1, "invalid body");
        }
        if (StringUtil.isBlank(entity.getApp())) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        if (StringUtil.isBlank(entity.getLimitApp())) {
            return Result.ofFail(-1, "limitApp can't be null or empty");
        }
        if (StringUtil.isBlank(entity.getResource())) {
            return Result.ofFail(-1, "resource can't be null or empty");
        }
        if (entity.getGrade() == null) {
            return Result.ofFail(-1, "grade can't be null");
        }
        if (entity.getGrade() != 0 && entity.getGrade() != 1) {
            return Result.ofFail(-1, "grade must be 0 or 1, but " + entity.getGrade() + " got");
        }
        if (entity.getCount() == null || entity.getCount() < 0) {
            return Result.ofFail(-1, "count should be at lease zero");
        }
        if (entity.getStrategy() == null) {
            return Result.ofFail(-1, "strategy can't be null");
        }
        if (entity.getStrategy() != 0 && StringUtil.isBlank(entity.getRefResource())) {
            return Result.ofFail(-1, "refResource can't be null or empty when strategy!=0");
        }
        if (entity.getControlBehavior() == null) {
            return Result.ofFail(-1, "controlBehavior can't be null");
        }
        int controlBehavior = entity.getControlBehavior();
        if (controlBehavior == 1 && entity.getWarmUpPeriodSec() == null) {
            return Result.ofFail(-1, "warmUpPeriodSec can't be null when controlBehavior==1");
        }
        if (controlBehavior == 2 && entity.getMaxQueueingTimeMs() == null) {
            return Result.ofFail(-1, "maxQueueingTimeMs can't be null when controlBehavior==2");
        }
        if (entity.isClusterMode() && entity.getClusterConfig() == null) {
            return Result.ofFail(-1, "cluster config should be valid");
        }
        return null;
    }

    public static final void checkInsertFlowRule(FlowRuleEntity flowRuleEntity){
        if (flowRuleEntity == null) {
            throw new IllegalArgumentException("flowRuleEntity为空");
        }
        if (flowRuleEntity.getId() != null) {
            throw new IllegalArgumentException("id必须为null");
        }
    }

    public static final void checkUpdateFlowRule(FlowRuleEntity flowRuleEntity){
        if (flowRuleEntity == null) {
            throw new IllegalArgumentException("flowRuleEntity为空");
        }
        if (flowRuleEntity.getId() == null) {
            throw new IllegalArgumentException("id为空");
        }
    }

    public static final FlowRule converInsertFlowRule(FlowRuleEntity flowRuleEntity,AuthService.AuthUser authUser){
        FlowRule insertFlowRule = new FlowRule();
        BeanUtils.copyProperties(flowRuleEntity,insertFlowRule);
        insertFlowRule.setClusterConfig(JSONObject.toJSONString(flowRuleEntity.getClusterConfig()));
        insertFlowRule.setFlowRule(JSONObject.toJSONString(flowRuleEntity));
        Date now = new Date();
        if(insertFlowRule.getGmtCreate() == null) {
            insertFlowRule.setGmtCreate(now);
        }
        if (insertFlowRule.getGmtModified() == null) {
            insertFlowRule.setGmtModified(now);
        }
        insertFlowRule.setCreator(authUser.getLoginName());
        insertFlowRule.setOperId(authUser.getLoginName());
        return insertFlowRule;
    }

    public static final FlowRule converUpdateFlowRule(FlowRuleEntity flowRuleEntity,AuthService.AuthUser authUser,FlowRule dbFlowRule){
        FlowRule updaeFlowRule = new FlowRule();
        BeanUtils.copyProperties(flowRuleEntity,updaeFlowRule);

        Date now = new Date();
        if (updaeFlowRule.getGmtModified() == null) {
            updaeFlowRule.setGmtModified(now);
        }
        updaeFlowRule.setId(dbFlowRule.getId());
        updaeFlowRule.setOperId(authUser.getLoginName());

        flowRuleEntity.setId(dbFlowRule.getId());
        updaeFlowRule.setClusterConfig(JSONObject.toJSONString(flowRuleEntity.getClusterConfig()));
        updaeFlowRule.setFlowRule(JSONObject.toJSONString(flowRuleEntity));
        return updaeFlowRule;
    }

    public static List<FlowRuleEntity> convert(List<FlowRule> flowRuleList) {
        List<FlowRuleEntity> flowRuleEntityList = Lists.newArrayList();
        for (FlowRule flowRule : flowRuleList) {
            FlowRuleEntity flowRuleEntity = JSONObject.parseObject(flowRule.getFlowRule(),FlowRuleEntity.class);
            flowRuleEntityList.add(flowRuleEntity);
        }
        return flowRuleEntityList;
    }

    public static final List<com.alibaba.csp.sentinel.slots.block.flow.FlowRule> convertFlowRuleListByCore( List<FlowRuleEntity> flowRuleEntityList){
        if(CollectionUtils.isEmpty(flowRuleEntityList)){
            return Lists.newArrayList();
        }
        List<com.alibaba.csp.sentinel.slots.block.flow.FlowRule> flowRules = Lists.newArrayList();
        flowRuleEntityList.stream().forEach(entity->{
            flowRules.add(entity.toRule());
        });
        return flowRules;
    }
}
