package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class ParamFlowRuleHelper {

    public static final  <R> Result<R> checkEntityInternal(ParamFlowRuleEntity entity, AuthService.AuthUser authUser) {
        if (entity == null) {
            return Result.ofFail(-1, "bad rule body");
        }
        if (StringUtil.isBlank(entity.getApp())) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        /*if (StringUtil.isBlank(entity.getIp())) {
            return Result.ofFail(-1, "ip can't be null or empty");
        }
        if (entity.getPort() == null || entity.getPort() <= 0) {
            return Result.ofFail(-1, "port can't be null");
        }*/
        if (entity.getRule() == null) {
            return Result.ofFail(-1, "rule can't be null");
        }
        if (StringUtil.isBlank(entity.getResource())) {
            return Result.ofFail(-1, "resource name cannot be null or empty");
        }
        if (entity.getCount() < 0) {
            return Result.ofFail(-1, "count should be valid");
        }
        if (entity.getGrade() != RuleConstant.FLOW_GRADE_QPS) {
            return Result.ofFail(-1, "Unknown mode (blockGrade) for parameter flow control");
        }
        if (entity.getParamIdx() == null || entity.getParamIdx() < 0) {
            return Result.ofFail(-1, "paramIdx should be valid");
        }
        if (entity.getDurationInSec() <= 0) {
            return Result.ofFail(-1, "durationInSec should be valid");
        }
        if (entity.getControlBehavior() < 0) {
            return Result.ofFail(-1, "controlBehavior should be valid");
        }

        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }

    public static final void checkInsertParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity){
        if (paramFlowRuleEntity == null) {
            throw new DashboardBizException("paramFlowRuleEntity为null");
        }
        if (paramFlowRuleEntity.getId() != null) {
            throw new DashboardBizException("id为null");
        }
    }

    public static final ParamFlowRule convertInsertParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity, AuthService.AuthUser authUser){
        ParamFlowRule insertParamFlowRule = new ParamFlowRule();
        BeanUtils.copyProperties(paramFlowRuleEntity,insertParamFlowRule);
        insertParamFlowRule.setCreator(authUser.getLoginName());
        insertParamFlowRule.setOperId(authUser.getLoginName());
        insertParamFlowRule.setParamFlowRule(JSONObject.toJSONString(paramFlowRuleEntity));
        insertParamFlowRule.setRule(JSONObject.toJSONString(paramFlowRuleEntity.getRule()));
        return insertParamFlowRule;
    }

    public static final void checkUpdateParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity){
        if (paramFlowRuleEntity == null) {
            throw new DashboardBizException("paramFlowRuleEntity为null");
        }
        if (paramFlowRuleEntity.getId() == null) {
            throw new DashboardBizException("id为null");
        }
    }

    public static final ParamFlowRule convertUpdateParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity,AuthService.AuthUser authUser,ParamFlowRule dbParamFlowRule){
        ParamFlowRule updateParamFlowRule = new ParamFlowRule();
        BeanUtils.copyProperties(paramFlowRuleEntity,updateParamFlowRule);

        paramFlowRuleEntity.setId(dbParamFlowRule.getId());

        updateParamFlowRule.setId(dbParamFlowRule.getId());
        updateParamFlowRule.setOperId(authUser.getLoginName());
        updateParamFlowRule.setParamFlowRule(JSONObject.toJSONString(paramFlowRuleEntity));
        updateParamFlowRule.setRule(JSONObject.toJSONString(paramFlowRuleEntity.getRule()));
        return updateParamFlowRule;
    }

    public static List<ParamFlowRuleEntity> convert(List<ParamFlowRule> paramFlowRuleList) {
        List<ParamFlowRuleEntity> paramFlowRuleEntityList = Lists.newArrayList();
        for (ParamFlowRule paramFlowRule : paramFlowRuleList) {
            ParamFlowRuleEntity paramFlowRuleEntity =JSONObject.parseObject(paramFlowRule.getParamFlowRule(),ParamFlowRuleEntity.class);
            paramFlowRuleEntityList.add(paramFlowRuleEntity);
        }
        return paramFlowRuleEntityList;
    }

    public static final List<com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule> convertParamFlowRuleByCore(List<ParamFlowRuleEntity> paramFlowRuleEntityList){
        if(CollectionUtils.isEmpty(paramFlowRuleEntityList)){
            return Lists.newArrayList();
        }
        List<com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule> paramFlowRuleList = Lists.newArrayList();
        paramFlowRuleEntityList.stream().forEach(e->{
            paramFlowRuleList.add(e.toRule());
        });
        return paramFlowRuleList;
    }
}
