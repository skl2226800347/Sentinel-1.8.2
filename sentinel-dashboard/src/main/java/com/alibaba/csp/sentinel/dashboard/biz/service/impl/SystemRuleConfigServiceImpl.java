package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.SystemRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.SystemRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.SystemRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SystemRuleConfigServiceImpl extends AbstractService implements SystemRuleConfigService {
    private static final Logger logger = LoggerFactory.getLogger(SystemRuleConfigServiceImpl.class);


    @Override
    public int createSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser) {
        SystemRuleHelper.checkInsertSystemRule(systemRuleEntity);
        SystemRule insertSystemRule = SystemRuleHelper.convertInsertSystemRule(systemRuleEntity,authUser);
        int updateRows =systemRuleMapperExt.insertSelective(insertSystemRule);
        systemRuleEntity.setId(insertSystemRule.getId());
        return updateRows;
    }

    @Override
    public int updateSystemRule(SystemRuleEntity systemRuleEntity,AuthService.AuthUser authUser) {
        SystemRuleHelper.checkUpdateSystemRule(systemRuleEntity);
        SystemRule dbSystemRule =systemRuleQueryManager.getSystemRuleById(systemRuleEntity.getId());
        if (dbSystemRule == null) {
            throw new DashboardBizException("统保护规则不存在");
        }
        SystemRule updateSystemRule = SystemRuleHelper.convertUpdateSystemRule(systemRuleEntity,authUser,dbSystemRule);
        int updateRows = systemRuleMapperExt.updateByPrimaryKeySelective(updateSystemRule);
        return updateRows;
    }

    @Override
    public int deleteSystemRule(Long id) {
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        return systemRuleMapperExt.deleteByPrimaryKey(id);
    }

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        SystemRulePaginationQuery systemRulePaginationQuery= new SystemRulePaginationQuery();
        int currentPage = 1;
        systemRulePaginationQuery.setGroupByClause("app");
        systemRulePaginationQuery.setCurrentPage(currentPage);
        systemRulePaginationQuery.setPageSize(DEFAULT_PAGE_SIZE);
        Pagination<SystemRule> pagination =  systemRuleQueryManager.querySystemRuleListWithPaging(systemRulePaginationQuery);
        if (pagination == null) {
            return;
        }
        processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        int pageCount = pagination.getPageCount();
        for (int i=1;i<pageCount;i++) {
            systemRulePaginationQuery.setCurrentPage(++currentPage);
            pagination =  systemRuleQueryManager.querySystemRuleListWithPaging(systemRulePaginationQuery);
            if (pagination == null) {
                return;
            }
            processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        }
    }

    private void processUpdateConfigCenterRuleFromDbBatch(List<SystemRule> systemRuleList) {
        if (CollectionUtils.isEmpty(systemRuleList)) {
            return ;
        }
        for (SystemRule systemRule : systemRuleList) {
            try {
                List<SystemRule> tempSystemRuleList = systemRuleQueryManager.getSystemRuleListByApp(systemRule.getApp());
                List<SystemRuleEntity> tempSystemRuleEntityList = SystemRuleHelper.convert(tempSystemRuleList);
                systemRuleZookeeperPublisher.publish(systemRule.getApp(),SystemRuleHelper.convertSystemRuleListByCore(tempSystemRuleEntityList));
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("[processUpdateConfigCenterRuleFromDbBatch] authorityRule={} 从数据库向配置中心更新数据发生异常  e={},error={}", JSONObject.toJSONString(systemRule),e,e.getMessage());
            }
        }
    }
}
