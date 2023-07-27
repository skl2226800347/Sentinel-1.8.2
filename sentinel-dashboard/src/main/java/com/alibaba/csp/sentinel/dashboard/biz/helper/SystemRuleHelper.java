package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class SystemRuleHelper {

    public static <R> Result<R> checkBasicParams(String app, AuthService.AuthUser authUser) {
        Result result =checkBasicParams(app);
        if(result != null){
            return result;
        }
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }
    public static <R> Result<R> checkBasicParams(String app) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
       /* if (StringUtil.isEmpty(ip)) {
            return Result.ofFail(-1, "ip can't be null or empty");
        }
        if (port == null) {
            return Result.ofFail(-1, "port can't be null");
        }
        if (port <= 0 || port > 65535) {
            return Result.ofFail(-1, "port should be in (0, 65535)");
        }*/
        return null;
    }


    public static final void checkInsertSystemRule(SystemRuleEntity systemRuleEntity){
        if (systemRuleEntity == null) {
            throw new DashboardBizException("systemRuleEntity为null");
        }
        if (systemRuleEntity.getId() != null) {
            throw new DashboardBizException("id必须为空");
        }
    }
    public static final SystemRule convertInsertSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser){
        SystemRule insertSystemRule = new SystemRule();
        BeanUtils.copyProperties(systemRuleEntity,insertSystemRule);
        insertSystemRule.setSystemRule(JSONObject.toJSONString(systemRuleEntity));
        insertSystemRule.setApp(systemRuleEntity.getApp());
        insertSystemRule.setCreator(authUser.getLoginName());
        insertSystemRule.setOperId(authUser.getLoginName());
        return insertSystemRule;
    }


    public static final void checkUpdateSystemRule(SystemRuleEntity systemRuleEntity){
        if (systemRuleEntity == null) {
            throw new DashboardBizException("systemRuleEntity为null");
        }
        if (systemRuleEntity.getId() == null) {
            throw new DashboardBizException("id必须非null");
        }
    }

    public static final SystemRule convertUpdateSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser,SystemRule dbSystemRule){
        SystemRule updateSystemRule = new SystemRule();
        BeanUtils.copyProperties(systemRuleEntity,updateSystemRule);

        systemRuleEntity.setId(dbSystemRule.getId());

        updateSystemRule.setSystemRule(JSONObject.toJSONString(systemRuleEntity));
        updateSystemRule.setId(dbSystemRule.getId());
        updateSystemRule.setOperId(authUser.getLoginName());
        return updateSystemRule;
    }

    public static List<SystemRuleEntity> convert(List<SystemRule> systemRuleList) {
        List<SystemRuleEntity> systemRuleEntityList = Lists.newArrayList();
        for (SystemRule systemRule : systemRuleList) {
            SystemRuleEntity systemRuleEntity = JSONObject.parseObject(systemRule.getSystemRule(),SystemRuleEntity.class);
            systemRuleEntityList.add(systemRuleEntity);
        }
        return systemRuleEntityList;
    }

    public static final List<com.alibaba.csp.sentinel.slots.system.SystemRule> convertSystemRuleListByCore(List<SystemRuleEntity> systemRuleEntityList){
        if(CollectionUtils.isEmpty(systemRuleEntityList)){
            Lists.newArrayList();
        }
        List<com.alibaba.csp.sentinel.slots.system.SystemRule> systemRules = Lists.newArrayList();
        systemRuleEntityList.stream().forEach(e->{
            systemRules.add(e.toRule());
        });
        return systemRules;
    }
}
