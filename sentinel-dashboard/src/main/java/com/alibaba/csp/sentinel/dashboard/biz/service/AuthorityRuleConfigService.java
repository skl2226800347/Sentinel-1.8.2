package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;

import java.util.List;

public interface AuthorityRuleConfigService {

    int createAuthorityRule(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser);

    int updateAuthorityRule(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser);

    int deleteAuthorityRule(Long id);

    void updateConfigCenterRuleFromDbBatch();

    void updateConfigCenterFromDb(List<AuthorityRuleEntity> authorityRuleEntityList);

    void updateConfigCenter(List<AuthorityRuleEntity> authorityRuleEntityList);

}
