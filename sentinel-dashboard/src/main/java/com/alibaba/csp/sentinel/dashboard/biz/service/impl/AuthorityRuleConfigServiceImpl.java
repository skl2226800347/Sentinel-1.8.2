package com.alibaba.csp.sentinel.dashboard.biz.service.impl;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AuthorityRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.AuthorityRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AuthorityRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorityRuleConfigServiceImpl extends AbstractService implements AuthorityRuleConfigService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorityRuleConfigServiceImpl.class);

    @Override
    public int createAuthorityRule(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser) {
        AuthorityRuleHelper.checkInsertAuthorityRule(authorityRuleEntity);
        AuthorityRule insertAuthorityRule = AuthorityRuleHelper.convertInsertAuthorityRuleEntity(authorityRuleEntity,authUser);
        int insertRows = authorityRuleMapperExt.insertSelective(insertAuthorityRule);
        authorityRuleEntity.setId(insertAuthorityRule.getId());
        return insertRows;
    }

    @Override
    public int updateAuthorityRule(AuthorityRuleEntity authorityRuleEntity,AuthService.AuthUser authUser) {
        AuthorityRuleHelper.checkUpdateAuthorityRule(authorityRuleEntity);
        AuthorityRule dbAuthorityRule = authorityRuleQueryManager.getAuthorityRuleById(authorityRuleEntity.getId());
        if (dbAuthorityRule == null) {
            throw new DashboardBizException("授权规则为null");
        }
        AuthorityRule updateAuthorityRule = AuthorityRuleHelper.convertUpdateAuthorityRule(authorityRuleEntity,authUser,dbAuthorityRule);
        return authorityRuleMapperExt.updateByPrimaryKeySelective(updateAuthorityRule);
    }

    @Override
    public int deleteAuthorityRule(Long id) {
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        return authorityRuleMapperExt.deleteByPrimaryKey(id);
    }

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        AuthorityRulePaginationQuery authorityRulePaginationQuery = new AuthorityRulePaginationQuery();
        int currentPage = 1;
        authorityRulePaginationQuery.setGroupByClause("app");
        authorityRulePaginationQuery.setOrderByClause(" gmt_modified desc");
        authorityRulePaginationQuery.setCurrentPage(currentPage);
        authorityRulePaginationQuery.setPageSize(DEFAULT_PAGE_SIZE);
        Pagination<AuthorityRule> pagination =  authorityRuleQueryManager.queryAuthorityRuleListWithPaging(authorityRulePaginationQuery);
        if (pagination == null) {
            return;
        }
        doUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        int pageCount = pagination.getPageCount();
        for (int i=1;i<pageCount;i++) {
            authorityRulePaginationQuery.setCurrentPage(++currentPage);
            pagination =  authorityRuleQueryManager.queryAuthorityRuleListWithPaging(authorityRulePaginationQuery);
            if (pagination == null) {
                return;
            }
            doUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        }
    }
    private void doUpdateConfigCenterRuleFromDbBatch(List<AuthorityRule> authorityRuleList) {
        if (CollectionUtils.isEmpty(authorityRuleList)) {
            return ;
        }
        for (AuthorityRule authorityRule : authorityRuleList) {
            processUpdateConfigCenterRuleFromDbBatch(authorityRule.getApp());
        }
    }

    protected void processUpdateConfigCenterRuleFromDbBatch(String app){
        try {
            List<AuthorityRule> tempAuthorityRuleList = authorityRuleQueryManager.getAuthorityRuleByApp(app);
            List<AuthorityRuleEntity> tempAuthorityRuleEntityList = AuthorityRuleHelper.convertToAuthorityRuleEntity(tempAuthorityRuleList);
            authorityRuleZookeeperPublisher.publish(app,AuthorityRuleHelper.convertAuthorityRuleListByCore(tempAuthorityRuleEntityList));
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("[processUpdateConfigCenterRuleFromDbBatch] app={} 从数据库向配置中心更新数据发生异常  e={},error={}", app,e,e.getMessage());
        }
    }

    @Override
    public void updateConfigCenterFromDb(List<AuthorityRuleEntity> authorityRuleEntityList) {
        if(CollectionUtils.isEmpty(authorityRuleEntityList)){
            return;
        }
        Map<String,List<AuthorityRuleEntity>> authorityRuleEntityMap = new HashMap<>();
        authorityRuleEntityList.stream().forEach(authorityRuleEntity->{
            authorityRuleEntityMap.computeIfAbsent(authorityRuleEntity.getApp(),app->{
               return  new ArrayList<>();
            }).add(authorityRuleEntity);
        });
        //为后面分页查询准备
        for(String app: authorityRuleEntityMap.keySet()){
            processUpdateConfigCenterRuleFromDbBatch(app);
        }
    }

    @Override
    public void updateConfigCenter(List<AuthorityRuleEntity> authorityRuleEntityList) {
        if(CollectionUtils.isEmpty(authorityRuleEntityList)){
            return;
        }
        Map<String,List<AuthorityRuleEntity>> authorityRuleEntityMap = new HashMap<>();
        authorityRuleEntityList.stream().forEach(authorityRuleEntity->{
            authorityRuleEntityMap.computeIfAbsent(authorityRuleEntity.getApp(),app->{
                return  new ArrayList<>();
            }).add(authorityRuleEntity);
        });
        //为后面分页查询准备
        for(String app: authorityRuleEntityMap.keySet()){
            try {
                authorityRuleZookeeperPublisher.publish(app, AuthorityRuleHelper.convertAuthorityRuleListByCore(authorityRuleEntityMap.get(app)));
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("[updateConfigCenter] app={} 从数据库向配置中心更新数据发生异常  e={},error={}", app,e,e.getMessage());
            }
        }
    }
}
