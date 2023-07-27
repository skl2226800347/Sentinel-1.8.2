package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.DegradeRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.DegradeRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.DegradeRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DegradeRuleConfigServiceImpl extends AbstractService implements DegradeRuleConfigService {
    private static final Logger logger = LoggerFactory.getLogger(DegradeRuleConfigServiceImpl.class);


    @Override
    public int createDegradeRule(DegradeRuleEntity degradeRuleEntity, AuthService.AuthUser authUser) {
        DegradeRuleHelper.checkInsertDegradeRule(degradeRuleEntity,authUser);
        DegradeRule insertDegradeRule = DegradeRuleHelper.convertInsertDegradeRule(degradeRuleEntity,authUser);
        int updateRows =degradeRuleMapperExt.insertSelective(insertDegradeRule);
        degradeRuleEntity.setId(insertDegradeRule.getId());
        return updateRows;
    }

    @Override
    public  int updateDegradeRule(DegradeRuleEntity degradeRuleEntity ,AuthService.AuthUser authUser) {
        DegradeRuleHelper.checkUpdateDegradeRule(degradeRuleEntity,authUser);
        DegradeRule dbDegradeRule = degradeRuleQueryManager.getDegradeRuleById(degradeRuleEntity.getId());
        if (dbDegradeRule == null) {
            throw new DashboardBizException("熔断规则不存在");
        }
        DegradeRule updateDegradeRule = DegradeRuleHelper.convertUpdateDegradeRule(degradeRuleEntity,authUser,dbDegradeRule);
        return degradeRuleMapperExt.updateByPrimaryKeySelective(updateDegradeRule);
    }

    @Override
    public int deleteDegradeRule(Long id) {
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        int updateRows = degradeRuleMapperExt.deleteByPrimaryKey(id);
        return updateRows;
    }

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        DegradeRulePaginationQuery degradeRulePaginationQuery = new DegradeRulePaginationQuery();
        int currentPage = 1;
        degradeRulePaginationQuery.setGroupByClause("app");
        degradeRulePaginationQuery.setCurrentPage(currentPage);
        degradeRulePaginationQuery.setPageSize(DEFAULT_PAGE_SIZE);
        Pagination<DegradeRule> pagination =  degradeRuleQueryManager.queryDegradeRuleListWithPaging(degradeRulePaginationQuery);
        if (pagination == null) {
            return;
        }
        processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        int pageCount = pagination.getPageCount();
        for (int i=1;i<pageCount;i++) {
            degradeRulePaginationQuery.setCurrentPage(++currentPage);
            pagination =  degradeRuleQueryManager.queryDegradeRuleListWithPaging(degradeRulePaginationQuery);
            if (pagination == null) {
                return;
            }
            processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        }
    }
    private void processUpdateConfigCenterRuleFromDbBatch(List<DegradeRule> degradeRuleList) {
        if (CollectionUtils.isEmpty(degradeRuleList)) {
            return ;
        }
        for (DegradeRule degradeRule : degradeRuleList) {
            try {
                List<DegradeRule> tempDegradeRuleList = degradeRuleQueryManager.getDegradeRuleListByApp(degradeRule.getApp());
                List<DegradeRuleEntity> tempDegradeRuleEntityList = DegradeRuleHelper.convert(tempDegradeRuleList);
                degradeRuleZookeeperPublisher.publish(degradeRule.getApp(),DegradeRuleHelper.convertDegradeRuleListByCore(tempDegradeRuleEntityList));
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("[processUpdateConfigCenterRuleFromDbBatch] authorityRule={} 从数据库向配置中心更新数据发生异常  e={},error={}", JSONObject.toJSONString(degradeRule),e,e.getMessage());
            }
        }
    }
}
