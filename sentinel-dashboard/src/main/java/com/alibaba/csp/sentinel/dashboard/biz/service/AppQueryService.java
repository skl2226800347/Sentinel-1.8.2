package com.alibaba.csp.sentinel.dashboard.biz.service;

import com.alibaba.csp.sentinel.dashboard.auth.AuthService;
import com.alibaba.csp.sentinel.dashboard.biz.base.Pagination;
import com.alibaba.csp.sentinel.dashboard.biz.domain.query.AppPaginationQuery;
import com.alibaba.csp.sentinel.dashboard.biz.entity.App;
import com.alibaba.csp.sentinel.dashboard.discovery.AppInfo;

import java.util.List;

public interface AppQueryService {
    Pagination<App> queryAppListWithPaging(AppPaginationQuery appPaginationQuery);
    List<AppInfo> getAppInfoList(AuthService.AuthUser authUser);
    AppInfo getAppInfoByApp(String app);

}
