package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.DegradeRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.DegradeRuleExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.DegradeRuleMapperExt;
import com.alibaba.csp.sentinel.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DegradeRuleQueryManager {

    @Resource
    DegradeRuleMapperExt degradeRuleMapperExt;

    public DegradeRule getDegradeRuleById(Long id){
        if (id == null) {
            throw new DashboardBizException("id为id");
        }
        return degradeRuleMapperExt.selectByPrimaryKey(id);
    }

    public List<DegradeRule> getDegradeRuleListByApp(String app) {
        if (StringUtil.isEmpty(app)) {
            throw new DashboardBizException("app为null");
        }
        DegradeRuleExample degradeRuleExample = new DegradeRuleExample();
        degradeRuleExample.createCriteria().andAppEqualTo(app);
        return degradeRuleMapperExt.selectByExample(degradeRuleExample);
    }

    public Pagination<DegradeRule> queryDegradeRuleListWithPaging(DegradeRulePaginationQuery degradeRulePaginationQuery){
        if(degradeRulePaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<DegradeRule> pagination=new Pagination<DegradeRule>(degradeRulePaginationQuery.getCurrentPage(),degradeRulePaginationQuery.getPageSize());
        int totalCount=degradeRuleMapperExt.countDegradeRuleWithPaging(degradeRulePaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<DegradeRule> authorityRuleList=degradeRuleMapperExt.selectDegradeRuleWithPaging(degradeRulePaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(authorityRuleList);
        return pagination;
    }
}
