package com.alibaba.csp.sentinel.dashboard.biz.service.impl;
import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.FlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.helper.FlowRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.FlowRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class FlowRuleConfigServiceImpl extends AbstractService implements FlowRuleConfigService {
    private static final Logger logger = LoggerFactory.getLogger(FlowRuleConfigServiceImpl.class);



    @Override
    public int createFlowRule(FlowRuleEntity entity,AuthService.AuthUser authUser) {
        FlowRuleHelper.checkInsertFlowRule(entity);
        FlowRule insertFlowRule = FlowRuleHelper.converInsertFlowRule(entity,authUser);
        int updateRows = flowRuleMapperExt.insertSelective(insertFlowRule);
        entity.setId(insertFlowRule.getId());
        return updateRows;
    }

    @Override
    public int updateFlowRule(FlowRuleEntity entity,AuthService.AuthUser authUser) {
        FlowRuleHelper.checkUpdateFlowRule(entity);
        FlowRule dbFlowRule = flowRuleQueryManager.getFlowRuleById(entity.getId());
        if (dbFlowRule == null) {
            throw new IllegalArgumentException("dbFlowRule不存在");
        }
        FlowRule updateFlowRule = FlowRuleHelper.converUpdateFlowRule(entity,authUser,dbFlowRule);
        int updateRows = flowRuleMapperExt.updateByPrimaryKeySelective(updateFlowRule);
        return updateRows;
    }

    @Override
    public int deleteFlowRule(Long id) {
        if (id == null) {
            throw new IllegalStateException("id不能为null");
        }
        int updateRows = flowRuleMapperExt.deleteByPrimaryKey(id);
        return updateRows;
    }

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        FlowRulePaginationQuery flowRulePaginationQuery = new FlowRulePaginationQuery();
        int currentPage = 1;
        flowRulePaginationQuery.setCurrentPage(currentPage);
        flowRulePaginationQuery.setPageSize(DEFAULT_PAGE_SIZE);
        flowRulePaginationQuery.setGroupByClause("app");
        flowRulePaginationQuery.setOrderByClause(" gmt_modified desc");
        Pagination<FlowRule> pagination =  flowRuleQueryManager.queryFlowRuleListWithPaging(flowRulePaginationQuery);
        if (pagination == null) {
            return;
        }
        processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        int pageCount = pagination.getPageCount();
        for (int i=1;i<pageCount;i++) {
            flowRulePaginationQuery.setCurrentPage(++currentPage);
            pagination =  flowRuleQueryManager.queryFlowRuleListWithPaging(flowRulePaginationQuery);
            if (pagination == null) {
                return;
            }
            processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        }
    }

    private void processUpdateConfigCenterRuleFromDbBatch(List<FlowRule> flowRuleList) {
        if (CollectionUtils.isEmpty(flowRuleList)) {
            return ;
        }
        for (FlowRule flowRule : flowRuleList) {
            try {
                List<FlowRule> tempFlowRuleList = flowRuleQueryManager.getFlowRuleListByApp(flowRule.getApp());
                List<FlowRuleEntity> tempFlowRuleEntityList = FlowRuleHelper.convert(tempFlowRuleList);
                flowRuleZookeeperPublisher.publish(flowRule.getApp(), FlowRuleHelper.convertFlowRuleListByCore(tempFlowRuleEntityList));
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("[processUpdateConfigCenterRuleFromDbBatch] authorityRule={} 从数据库向配置中心更新数据发生异常  e={},error={}", JSONObject.toJSONString(flowRule),e,e.getMessage());
            }
        }
    }

}
