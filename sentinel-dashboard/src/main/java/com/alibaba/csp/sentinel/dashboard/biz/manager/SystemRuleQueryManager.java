package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.SystemRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.SystemRuleExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.SystemRuleMapperExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemRuleQueryManager {
    @Resource
    SystemRuleMapperExt systemRuleMapperExt;

    public SystemRule getSystemRuleById(Long id) {
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        return systemRuleMapperExt.selectByPrimaryKey(id);
    }

    public Pagination<SystemRule> querySystemRuleListWithPaging(SystemRulePaginationQuery systemRulePaginationQuery){
        if(systemRulePaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<SystemRule> pagination=new Pagination<SystemRule>(systemRulePaginationQuery.getCurrentPage(),systemRulePaginationQuery.getPageSize());
        int totalCount=systemRuleMapperExt.countSystemRuleWithPaging(systemRulePaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<SystemRule> systemRuleList=systemRuleMapperExt.selectSystemRuleWithPaging(systemRulePaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(systemRuleList);
        return pagination;
    }

    public List<SystemRule> getSystemRuleListByApp(String app) {
        if (StringUtils.isEmpty(app)) {
            throw new DashboardBizException("app为null");
        }
        SystemRuleExample systemRuleExample = new SystemRuleExample();
        systemRuleExample.createCriteria().andAppEqualTo(app);
        return systemRuleMapperExt.selectByExample(systemRuleExample);
    }


}
