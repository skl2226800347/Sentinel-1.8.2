package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.FlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.FlowRuleExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.FlowRuleMapperExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlowRuleQueryManager {
    @Resource
    FlowRuleMapperExt flowRuleMapperExt;

    public FlowRule getFlowRuleById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("id不能为null");
        }
        return flowRuleMapperExt.selectByPrimaryKey(id);
    }

    public Pagination<FlowRule> queryFlowRuleListWithPaging(FlowRulePaginationQuery flowRulePaginationQuery){
        if(flowRulePaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<FlowRule> pagination=new Pagination<FlowRule>(flowRulePaginationQuery.getCurrentPage(),flowRulePaginationQuery.getPageSize());
        int totalCount=flowRuleMapperExt.countFlowRuleWithPaging(flowRulePaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<FlowRule> flowRuleList=flowRuleMapperExt.selectFlowRuleWithPaging(flowRulePaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(flowRuleList);
        return pagination;
    }

    public List<FlowRule> getFlowRuleListByApp(String app) {
        if (StringUtils.isEmpty(app)) {
            throw new DashboardBizException("app为null");
        }
        FlowRuleExample flowRuleExample = new FlowRuleExample();
        flowRuleExample.createCriteria().andAppEqualTo(app);
        return flowRuleMapperExt.selectByExample(flowRuleExample);
    }

}
