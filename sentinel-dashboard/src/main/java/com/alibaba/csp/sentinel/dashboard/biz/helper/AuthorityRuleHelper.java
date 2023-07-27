package com.alibaba.csp.sentinel.dashboard.biz.helper;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.enums.ResultCodeEnum;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class AuthorityRuleHelper {

    public static final  <R> Result<R> checkEntityInternal(AuthorityRuleEntity entity,AuthService.AuthUser authUser) {
        if (entity == null) {
            return Result.ofFail(-1, "bad rule body");
        }
        if (StringUtil.isBlank(entity.getApp())) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
       /* if (StringUtil.isBlank(entity.getIp())) {
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
        if (StringUtil.isBlank(entity.getLimitApp())) {
            return Result.ofFail(-1, "limitApp should be valid");
        }
        if (entity.getStrategy() != RuleConstant.AUTHORITY_WHITE
                && entity.getStrategy() != RuleConstant.AUTHORITY_BLACK) {
            return Result.ofFail(-1, "Unknown strategy (must be blacklist or whitelist)");
        }
        if(authUser == null){
            return Result.ofFail(ResultCodeEnum.NO_LOGIN);
        }
        return null;
    }


    public static void checkInsertAuthorityRule(AuthorityRuleEntity authorityRuleEntity){
        if (authorityRuleEntity == null) {
            throw new DashboardBizException("authorityRuleEntity为空");
        }
        if (authorityRuleEntity.getId() != null) {
            throw new DashboardBizException("id必须为null");
        }
    }

    public static void checkUpdateAuthorityRule(AuthorityRuleEntity authorityRuleEntity) {
        if (authorityRuleEntity == null) {
            throw new DashboardBizException("authorityRuleEntity为null");
        }
        if (authorityRuleEntity.getId() == null) {
            throw new DashboardBizException("id为null");
        }
    }

    public static AuthorityRule convertInsertAuthorityRuleEntity(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser){
        AuthorityRule insertAuthorityRule = new AuthorityRule();
        BeanUtils.copyProperties(authorityRuleEntity,insertAuthorityRule);
        if(authorityRuleEntity.getRule() != null){
            insertAuthorityRule.setResource(authorityRuleEntity.getRule().getResource());
            insertAuthorityRule.setLimitApp(authorityRuleEntity.getRule().getLimitApp());
            insertAuthorityRule.setStrategy(authorityRuleEntity.getRule().getStrategy());
        }
        insertAuthorityRule.setAuthorityRule(JSONObject.toJSONString(authorityRuleEntity));
        insertAuthorityRule.setRule(JSONObject.toJSONString(authorityRuleEntity.getRule()));
        insertAuthorityRule.setCreator(authUser.getLoginName());
        insertAuthorityRule.setOperId(authUser.getLoginName());
        return insertAuthorityRule;
    }

    public static AuthorityRule convertUpdateAuthorityRule(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser,AuthorityRule dbAuthorityRule) {
        AuthorityRule updateAuthorityRule = new AuthorityRule();
        BeanUtils.copyProperties(authorityRuleEntity,updateAuthorityRule);
        if(authorityRuleEntity.getRule() != null){
            updateAuthorityRule.setResource(authorityRuleEntity.getRule().getResource());
            updateAuthorityRule.setLimitApp(authorityRuleEntity.getRule().getLimitApp());
            updateAuthorityRule.setStrategy(authorityRuleEntity.getRule().getStrategy());
        }
        authorityRuleEntity.setId(dbAuthorityRule.getId());
        updateAuthorityRule.setId(dbAuthorityRule.getId());
        updateAuthorityRule.setOperId(authUser.getLoginName());
        updateAuthorityRule.setAuthorityRule(JSONObject.toJSONString(authorityRuleEntity));
        updateAuthorityRule.setRule(JSONObject.toJSONString(authorityRuleEntity.getRule()));
        return updateAuthorityRule;
    }


    public static List<AuthorityRuleEntity> convertToAuthorityRuleEntity( List<AuthorityRule> authorityRuleList){
        if(CollectionUtils.isEmpty(authorityRuleList)){
            return null;
        }
        List<AuthorityRuleEntity> authorityRuleEntityList = Lists.newArrayList();
        authorityRuleList.stream().forEach(authorityRuleDb->{
            AuthorityRuleEntity authorityRuleEntity = JSONObject.parseObject(authorityRuleDb.getAuthorityRule(),AuthorityRuleEntity.class);
            authorityRuleEntity.getRule().setResource(authorityRuleDb.getResource());
            authorityRuleEntity.getRule().setStrategy(authorityRuleDb.getStrategy());
            authorityRuleEntity.getRule().setLimitApp(authorityRuleDb.getLimitApp());
            authorityRuleEntity.setId(authorityRuleDb.getId());
            authorityRuleEntityList.add(authorityRuleEntity);
        });
        return authorityRuleEntityList;
    }

    public static final List<com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule> convertAuthorityRuleListByCore(List<AuthorityRuleEntity> authorityRuleEntityList){
        if(CollectionUtils.isEmpty(authorityRuleEntityList)){
            return Lists.newArrayList();
        }
        List<com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule> authorityRuleList = Lists.newArrayList();
        for(AuthorityRuleEntity authorityRuleEntity : authorityRuleEntityList){
            authorityRuleList.add(authorityRuleEntity.toRule());
        }
        return authorityRuleList;
    }
    public static final com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule convertAuthorityRuleByCore(AuthorityRuleEntity authorityRuleEntity){
        if(authorityRuleEntity == null){
            return null;
        }
        return authorityRuleEntity.toRule();
    }
}
