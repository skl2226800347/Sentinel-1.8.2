package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.ParamFlowRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.ParamFlowRuleExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.ParamFlowRuleMapperExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ParamFlowRuleQueryManager {
    @Resource
    ParamFlowRuleMapperExt paramFlowRuleMapperExt;

    public ParamFlowRule getParamFlowRuleById(Long id){
        if(id == null) {
            throw new DashboardBizException("id为null");
        }
        return paramFlowRuleMapperExt.selectByPrimaryKey(id);
    }

    public Pagination<ParamFlowRule> queryParamFlowRuleListWithPaging(ParamFlowRulePaginationQuery paramFlowRulePaginationQuery){
        if(paramFlowRulePaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<ParamFlowRule> pagination=new Pagination<ParamFlowRule>(paramFlowRulePaginationQuery.getCurrentPage(),paramFlowRulePaginationQuery.getPageSize());
        int totalCount=paramFlowRuleMapperExt.countParamFlowRuleWithPaging(paramFlowRulePaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<ParamFlowRule> paramFlowRuleList=paramFlowRuleMapperExt.selectParamFlowRuleWithPaging(paramFlowRulePaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(paramFlowRuleList);
        return pagination;
    }

    public List<ParamFlowRule> getParamFlowRuleListByApp(String app) {
        if (StringUtils.isEmpty(app)) {
            throw new DashboardBizException("app为null");
        }
        ParamFlowRuleExample paramFlowRuleExample = new ParamFlowRuleExample();
        paramFlowRuleExample.createCriteria().andAppEqualTo(app);
        return paramFlowRuleMapperExt.selectByExample(paramFlowRuleExample);
    }

}
