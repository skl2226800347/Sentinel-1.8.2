package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class DegradeRuleHelper {
    public static final <R> Result<R> checkDelete(Long id, AuthService.AuthUser authUser ){
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        if (id == null) {
            return Result.ofFail(-1, "id can't be null");
        }
        return null;
    }
    public static final <R> Result<R> checkApiUpdateRule(Long id,AuthService.AuthUser authUser ){
        if (id == null || id <= 0) {
            return Result.ofFail(-1, "id can't be null or negative");
        }
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }
    public static final  <R> Result<R> checkEntityInternal(DegradeRuleEntity entity, AuthService.AuthUser authUser) {
        if (StringUtil.isBlank(entity.getApp())) {
            return Result.ofFail(-1, "app can't be blank");
        }
        /*if (StringUtil.isBlank(entity.getIp())) {
            return Result.ofFail(-1, "ip can't be null or empty");
        }
        if (entity.getPort() == null || entity.getPort() <= 0) {
            return Result.ofFail(-1, "invalid port: " + entity.getPort());
        }*/
        if (StringUtil.isBlank(entity.getLimitApp())) {
            return Result.ofFail(-1, "limitApp can't be null or empty");
        }
        if (StringUtil.isBlank(entity.getResource())) {
            return Result.ofFail(-1, "resource can't be null or empty");
        }
        Double threshold = entity.getCount();
        if (threshold == null || threshold < 0) {
            return Result.ofFail(-1, "invalid threshold: " + threshold);
        }
        Integer recoveryTimeoutSec = entity.getTimeWindow();
        if (recoveryTimeoutSec == null || recoveryTimeoutSec <= 0) {
            return Result.ofFail(-1, "recoveryTimeout should be positive");
        }
        Integer strategy = entity.getGrade();
        if (strategy == null) {
            return Result.ofFail(-1, "circuit breaker strategy cannot be null");
        }
        if (strategy < CircuitBreakerStrategy.SLOW_REQUEST_RATIO.getType()
                || strategy > RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT) {
            return Result.ofFail(-1, "Invalid circuit breaker strategy: " + strategy);
        }
        if (entity.getMinRequestAmount()  == null || entity.getMinRequestAmount() <= 0) {
            return Result.ofFail(-1, "Invalid minRequestAmount");
        }
        if (entity.getStatIntervalMs() == null || entity.getStatIntervalMs() <= 0) {
            return Result.ofFail(-1, "Invalid statInterval");
        }
        if (strategy == RuleConstant.DEGRADE_GRADE_RT) {
            Double slowRatio = entity.getSlowRatioThreshold();
            if (slowRatio == null) {
                return Result.ofFail(-1, "SlowRatioThreshold is required for slow request ratio strategy");
            } else if (slowRatio < 0 || slowRatio > 1) {
                return Result.ofFail(-1, "SlowRatioThreshold should be in range: [0.0, 1.0]");
            }
        } else if (strategy == RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO) {
            if (threshold > 1) {
                return Result.ofFail(-1, "Ratio threshold should be in range: [0.0, 1.0]");
            }
        }
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN.getCode(),ResultCodeEnum.NO_LOGIN.getMsg());
        }
        return null;
    }


    public static void checkInsertDegradeRule(DegradeRuleEntity degradeRuleEntity,AuthService.AuthUser authUser){
        if(authUser == null){
            throw new DashboardBizException("未登陆");
        }
        if (degradeRuleEntity == null){
            throw new DashboardBizException("degradeRuleEntity入参为null");
        }
    }

    public static DegradeRule convertInsertDegradeRule(DegradeRuleEntity degradeRuleEntity, AuthService.AuthUser authUser){
        DegradeRule insertDegradeRule = new DegradeRule();
        BeanUtils.copyProperties(degradeRuleEntity,insertDegradeRule);
        insertDegradeRule.setDegradeRule(JSONObject.toJSONString(degradeRuleEntity));
        insertDegradeRule.setCreator(authUser.getLoginName());
        insertDegradeRule.setOperId(authUser.getLoginName());
        return insertDegradeRule;
    }

    public static void checkUpdateDegradeRule(DegradeRuleEntity degradeRuleEntity,AuthService.AuthUser authUser){
        if (degradeRuleEntity == null) {
            throw new DashboardBizException("degradeRuleEntity为null");
        }
        if (degradeRuleEntity.getId() == null) {
            throw new DashboardBizException("id为null");
        }
        if(authUser == null){
            throw new DashboardBizException("未登陆");
        }
    }

    public static DegradeRule convertUpdateDegradeRule(DegradeRuleEntity degradeRuleEntity,AuthService.AuthUser authUser,DegradeRule dbDegradeRule) {
        DegradeRule updateDegradeRule = new DegradeRule();
        BeanUtils.copyProperties(degradeRuleEntity,updateDegradeRule);
        updateDegradeRule.setOperId(authUser.getLoginName());
        updateDegradeRule.setId(dbDegradeRule.getId());

        degradeRuleEntity.setId(dbDegradeRule.getId());
        updateDegradeRule.setDegradeRule(JSONObject.toJSONString(degradeRuleEntity));
        return updateDegradeRule;
    }

    public static List<DegradeRuleEntity> convert(List<DegradeRule> degradeRuleList) {
        if(CollectionUtils.isEmpty(degradeRuleList)){
            return Lists.newArrayList();
        }

        List<DegradeRuleEntity> degradeRuleEntityList = Lists.newArrayList();
        for (DegradeRule degradeRule : degradeRuleList) {
            DegradeRuleEntity degradeRuleEntity = JSONObject.parseObject(degradeRule.getDegradeRule(),DegradeRuleEntity.class);
            degradeRuleEntityList.add(degradeRuleEntity);
        }
        return degradeRuleEntityList;
    }

    public static final List<com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule> convertDegradeRuleListByCore(List<DegradeRuleEntity> degradeRuleEntityList){
        if(CollectionUtils.isEmpty(degradeRuleEntityList)){
            return Lists.newArrayList();
        }
        List<com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule> degradeRules = Lists.newArrayList();
        degradeRuleEntityList.stream().forEach(e->{
            degradeRules.add(e.toRule());
        });
        return degradeRules;
    }
}
