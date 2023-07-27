package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AuthorityRulePaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRule;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AuthorityRuleExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.AuthorityRuleMapperExt;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthorityRuleQueryManager {
    @Resource
    AuthorityRuleMapperExt authorityRuleMapperExt;

    public AuthorityRule getAuthorityRuleById(Long id){
        if (id == null) {
            throw new DashboardBizException("id为null");
        }
        return authorityRuleMapperExt.selectByPrimaryKey(id);
    }

    public List<AuthorityRule> getAuthorityRuleByApp(String app){
        if(StringUtils.isEmpty(app)) {
            throw new DashboardBizException("app不能主空");
        }
        AuthorityRuleExample authorityRuleExample = new AuthorityRuleExample();
        authorityRuleExample.createCriteria().andAppEqualTo(app);
        return authorityRuleMapperExt.selectByExample(authorityRuleExample);
    }

    public Pagination<AuthorityRule> queryAuthorityRuleListWithPaging(AuthorityRulePaginationQuery authorityRulePaginationQuery){
        if(authorityRulePaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<AuthorityRule> pagination=new Pagination<AuthorityRule>(authorityRulePaginationQuery.getCurrentPage(),authorityRulePaginationQuery.getPageSize());
        int totalCount=authorityRuleMapperExt.countAuthorityRuleWithPaging(authorityRulePaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<AuthorityRule> authorityRuleList=authorityRuleMapperExt.selectAuthorityRuleWithPaging(authorityRulePaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(authorityRuleList);
        return pagination;
    }


}
