package com.alibaba.csp.sentinel.dashboard.biz.manager;

import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.constants.CommonConstant;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AppPaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;
import com.alibaba.csp.sentinel.dashboard.biz.entity.AppExample;
import com.alibaba.csp.sentinel.dashboard.biz.exception.DashboardBizException;
import com.alibaba.csp.sentinel.dashboard.biz.mapper.AppMapperExt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AppQueryManager {
    @Resource
    AppMapperExt appMapperExt;

    public Pagination<App> queryAppListWithPaging(AppPaginationQuery appPaginationQuery){
        if(appPaginationQuery==null){
            throw new DashboardBizException("参数不能为空");
        }
        Pagination<App> pagination=new Pagination<App>(appPaginationQuery.getCurrentPage(),appPaginationQuery.getPageSize());
        int totalCount=appMapperExt.countAppWithPaging(appPaginationQuery);
        if(totalCount<=0){
            return pagination;
        }
        List<App> authorityRuleList=appMapperExt.selectAppWithPaging(appPaginationQuery);
        pagination.setTotalCount(totalCount);
        pagination.setList(authorityRuleList);
        return pagination;
    }

    public List<App> getAllAppList(){
        AppExample appExample = new AppExample();
        List<App> appList =appMapperExt.selectByExample(appExample);
        return appList;
    }
    public App getAppByApp(String app){
        AppExample appExample = new AppExample();
        appExample.createCriteria().andAppEqualTo(app);
        List<App> appList =appMapperExt.selectByExample(appExample);
        if(CollectionUtils.isEmpty(appList)){
            return null;
        }
        return appList.get(CommonConstant.ZERO);
    }
}
