package com.alibaba.csp.sentinel.dashboard.biz.service.impl;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.service.AbstractService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.ParamFlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.helper.ParamFlowRuleHelper;
import com.alibaba.csp.sentinel.dashboard.biz.service.ParamFlowRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ParamFlowRuleConfigServiceImpl extends AbstractService implements ParamFlowRuleConfigService {
    private static final Logger logger = LoggerFactory.getLogger(ParamFlowRuleConfigServiceImpl.class);


    @Override
    public int createParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity, AuthService.AuthUser authUser) {
        ParamFlowRuleHelper.checkInsertParamFlowRule(paramFlowRuleEntity);
        ParamFlowRule insertParamFlowRule =  ParamFlowRuleHelper.convertInsertParamFlowRule(paramFlowRuleEntity,authUser);
        int updateRows = paramFlowRuleMapperExt.insertSelective(insertParamFlowRule);
        paramFlowRuleEntity.setId(insertParamFlowRule.getId());
        return updateRows;
    }

    @Override
    public int updateParamFlowRule(ParamFlowRuleEntity paramFlowRuleEntity,AuthService.AuthUser authUser) {
        ParamFlowRuleHelper.checkUpdateParamFlowRule(paramFlowRuleEntity);
        ParamFlowRule dbParamFlowRule = paramFlowRuleQueryManager.getParamFlowRuleById(paramFlowRuleEntity.getId());
        if (dbParamFlowRule == null) {
            throw new DashboardBizException("热点规则在db不存在");
        }
        ParamFlowRule updateParamFlowRule = ParamFlowRuleHelper.convertUpdateParamFlowRule(paramFlowRuleEntity,authUser,dbParamFlowRule);
        int insertRows = paramFlowRuleMapperExt.updateByPrimaryKeySelective(updateParamFlowRule);
        return insertRows;
    }

    @Override
    public int deleteParamFlowRuleEntity(Long id) {
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        int updateRows = paramFlowRuleMapperExt.deleteByPrimaryKey(id);
        return updateRows;
    }

    @Override
    public void updateConfigCenterRuleFromDbBatch() {
        ParamFlowRulePaginationQuery paramFlowRulePaginationQuery = new ParamFlowRulePaginationQuery();
        int currentPage = 1;
        paramFlowRulePaginationQuery.setGroupByClause("app");
        paramFlowRulePaginationQuery.setCurrentPage(currentPage);
        paramFlowRulePaginationQuery.setPageSize(DEFAULT_PAGE_SIZE);
        Pagination<ParamFlowRule> pagination =  paramFlowRuleQueryManager.queryParamFlowRuleListWithPaging(paramFlowRulePaginationQuery);
        if (pagination == null) {
            return;
        }
        processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        int pageCount = pagination.getPageCount();
        for (int i=1;i<pageCount;i++) {
            paramFlowRulePaginationQuery.setCurrentPage(++currentPage);
            pagination =  paramFlowRuleQueryManager.queryParamFlowRuleListWithPaging(paramFlowRulePaginationQuery);
            if (pagination == null) {
                return;
            }
            processUpdateConfigCenterRuleFromDbBatch(pagination.getList());
        }
    }

    private void processUpdateConfigCenterRuleFromDbBatch(List<ParamFlowRule> paramFlowRuleList) {
        if (CollectionUtils.isEmpty(paramFlowRuleList)) {
            return ;
        }
        for (ParamFlowRule paramFlowRule : paramFlowRuleList) {
            try {
                List<ParamFlowRule> tempParamFlowRuleList = paramFlowRuleQueryManager.getParamFlowRuleListByApp(paramFlowRule.getApp());
                List<ParamFlowRuleEntity> tempParamFlowRuleEntityList = ParamFlowRuleHelper.convert(tempParamFlowRuleList);
                paramFlowRuleZookeeperPublisher.publish(paramFlowRule.getApp(), ParamFlowRuleHelper.convertParamFlowRuleByCore(tempParamFlowRuleEntityList));
            }catch (Exception e) {
                e.printStackTrace();
                logger.error("[processUpdateConfigCenterRuleFromDbBatch] authorityRule={} 从数据库向配置中心更新数据发生异常  e={},error={}", JSONObject.toJSONString(paramFlowRule),e,e.getMessage());
            }
        }
    }

}
